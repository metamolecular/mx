/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.ring2;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rich
 */
public class PathGraph
{
  private List<PathNode> nodes;
  private List<PathEdge> edges;
  
  public PathGraph(Molecule molecule)
  {
    nodes = new ArrayList();
    edges = new ArrayList();
    
    loadNodes(molecule);
    loadEdges(molecule);
  }
  
  public int countNodes()
  {
    return nodes.size();
  }
  
  public int countEdges()
  {
    return edges.size();
  }
  
  public PathNode getLeastConnectedNode()
  {
    PathNode result = nodes.get(0);
    
    for (PathNode node : nodes)
    {
      if (node.countConnections() > result.countConnections())
      {
        result = node;
      }
    }
    
    return result;
  }
  
  private void loadNodes(Molecule molecule)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      NodeImpl node = new NodeImpl(atom);
      
      nodes.add(node);
    }
  }
  
  private void loadEdges(Molecule molecule)
  {
    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);
      EdgeImpl edge = new EdgeImpl(bond);
      
      edges.add(edge);
    }
  }
  
  private class NodeImpl implements PathNode
  {
    private Atom atom;
    
    private NodeImpl(Atom atom)
    {
      this.atom = atom;
    }

    public Atom getAtom()
    {
      return atom;
    }

    public int countConnections()
    {
      return 0;
    }
  }
  
  private class EdgeImpl implements PathEdge
  {
    private EdgeImpl(Bond bond)
    {
      
    }
  }
}
