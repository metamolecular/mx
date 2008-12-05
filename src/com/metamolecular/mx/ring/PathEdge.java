/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.ring;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rich
 */
public class PathEdge
{
  private List<Atom> path;
  private Atom source;
  private Atom target;
  
  public PathEdge(Atom source, Atom target)
  {
    this.source = source;
    this.target = target;
    path = new ArrayList<Atom>();
    
    path.add(source);
    path.add(target);
  }
  
  public Atom getSource()
  {
    return source;
  }
  
  public Atom getTarget()
  {
    return target;
  }
  
  public PathEdge(PathEdge edge1, PathEdge edge2)
  {
    source = edge1.getSource();
    target = edge2.getTarget();
    path = new ArrayList<Atom>();
    
    path.addAll(edge1.getPath());
    
    for (int i = 1; i < edge2.getPath().size(); i++)
    {
      path.add(edge2.getPath().get(i));
    }
  }
  
  public boolean isLoop()
  {
    return path.get(0).equals(path.get(path.size() - 1));
  }
  
  public List<Atom> getPath()
  {
    return path;
  }
}
