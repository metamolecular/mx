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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultQuery implements Query
{
  private List nodes;
  
  public DefaultQuery()
  {
    nodes = new ArrayList();
  }
  
  public Node addNode(DefaultAtomMatcher matcher)
  {
    NodeImpl node = new NodeImpl(matcher);
    
    nodes.add(node);
    
    return node;
  }

  public int countNodes()
  {
    return nodes.size();
  }

  public void connect(Node source, Node target)
  {
    NodeImpl sourceImpl = (NodeImpl) source;
    NodeImpl targetImpl = (NodeImpl) target;
    
    sourceImpl.neighbors.add(targetImpl);
    targetImpl.neighbors.add(sourceImpl);
  }
  
  private class NodeImpl implements Node
  {
    private List<Node> neighbors;
    private DefaultAtomMatcher matcher;
    
    private NodeImpl(DefaultAtomMatcher matcher)
    {
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

    public DefaultAtomMatcher getAtomMatcher()
    {
      return matcher;
    }
  }
}
