/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultQuery implements Query
{
  private List<Node> nodes;
  private List<Edge> edges;

  public DefaultQuery()
  {
    nodes = new ArrayList();
    edges = new ArrayList();
  }

  public DefaultQuery(Molecule molecule)
  {
    this();

    build(molecule);
  }

  public Iterable<Edge> edges()
  {
    return edges;
  }

  public Iterable<Node> nodes()
  {
    return nodes;
  }

  public Node getNode(int index)
  {
    return nodes.get(index);
  }

  public Edge getEdge(int index)
  {
    return edges.get(index);
  }

  public Edge getEdge(Node source, Node target)
  {
    if (source == target)
    {
      return null;
    }

    NodeImpl sourceImpl = (NodeImpl) source;

    for (Edge edge : sourceImpl.edges)
    {
      if (edge.getSource() == target || edge.getTarget() == target)
      {
        return edge;
      }
    }

    return null;
  }

  public Node addNode(AtomMatcher matcher)
  {
    NodeImpl node = new NodeImpl(matcher);

    nodes.add(node);

    return node;
  }

  public int countNodes()
  {
    return nodes.size();
  }

  public int countEdges()
  {
    return edges.size();
  }

  public Edge connect(Node source, Node target)
  {
    NodeImpl sourceImpl = (NodeImpl) source;
    NodeImpl targetImpl = (NodeImpl) target;
    EdgeImpl edge = new EdgeImpl(sourceImpl, targetImpl);

    sourceImpl.neighbors.add(targetImpl);
    targetImpl.neighbors.add(sourceImpl);

    sourceImpl.edges.add(edge);
    targetImpl.edges.add(edge);

    edges.add(edge);

    return edge;
  }

  private void build(Molecule molecule)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      AtomMatcher matcher = createMatcher(atom);

      if (matcher != null)
      {
        addNode(matcher);
      }
    }

    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);

      connect(nodes.get(bond.getSource().getIndex()), nodes.get(bond.getTarget().getIndex()));
    }
  }

  private AtomMatcher createMatcher(Atom atom)
  {
    return new DefaultAtomMatcher(atom);
  }

  private class NodeImpl implements Node
  {
    private List<Node> neighbors;
    private List<Edge> edges;
    private AtomMatcher matcher;

    private NodeImpl(AtomMatcher matcher)
    {
      edges = new ArrayList();
      neighbors = new ArrayList();
      this.matcher = matcher;
    }

    public int countNeighbors()
    {
      return neighbors.size();
    }

    public Iterable<Node> neighbors()
    {
      return neighbors;
    }

    public AtomMatcher getAtomMatcher()
    {
      return matcher;
    }
  }

  private class EdgeImpl implements Edge
  {
    private NodeImpl source;
    private NodeImpl target;

    private EdgeImpl(NodeImpl source, NodeImpl target)
    {
      this.source = source;
      this.target = target;
    }

    public Node getSource()
    {
      return source;
    }

    public Node getTarget()
    {
      return target;
    }
  }
}
