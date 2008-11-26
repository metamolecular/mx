/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rich
 */
public class PathFinder
{
  private List<List<Atom>> paths;
  
  public PathFinder()
  {
    paths = new ArrayList<List<Atom>>();
  }
  
  public List<List<Atom>> findAllPaths(Atom atom)
  {
    State state = new DepthFirstState(atom);
    
    paths.clear();
    walk(state);
    
    return paths;
  }
  
  public void walk(State state)
  {
    if (!state.hasNextAtom())
    {
      paths.add(new ArrayList<Atom>(state.getPath()));
      
      return;
    }
    
    while(state.hasNextAtom())
    {
      Atom next = state.nextAtom();
      
      if (state.canVisit(next))
      {
        walk(state);
        
        state.backTrack();
      }
    }
  }
}
