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
    Step state = new DefaultStep(atom);
    
    paths.clear();
    walk(state);
    
    return paths;
  }
  
  public void walk(Step state)
  {
    if (!state.hasNextBranch())
    {
      paths.add(new ArrayList<Atom>(state.getPath()));
      
      return;
    }
    
    while(state.hasNextBranch())
    {
      Atom next = state.nextBranch();
      
      if (state.isBranchFeasible(next))
      {
        walk(state.nextStep(next));
        
        state.backTrack();
      }
    }
  }
}
