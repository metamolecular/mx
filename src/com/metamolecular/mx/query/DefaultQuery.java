/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    private List<NodeImpl> neighbors;
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

    public DefaultAtomMatcher getAtomMatcher()
    {
      return matcher;
    }
  }
  
  private class EdgeImpl implements Edge
  {
    private EdgeImpl(NodeImpl source, NodeImpl target)
    {
      
    }
  }
}
