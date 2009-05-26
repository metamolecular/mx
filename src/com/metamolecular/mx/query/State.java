/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import java.util.Map;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public interface State
{
  /**
   * Returns the current mapping of query atoms onto target atoms.
   * This map is shared among all states obtained through nextState.
   * 
   * @return the current mapping of query atoms onto target atoms
   */
  public Map<Node, Atom> getMap();

  /**
   * Returns true if another candidate match can be found or
   * false otherwise.
   * 
   * @return true if another candidate mapping can be found or
   * false otherwise
   */
  public boolean hasNextCandidate();

  /**
   * Returns the next candidate match.
   * 
   * @return the next candidate match
   */
  public Match nextCandidate();
  
  /**
   * Returns true if the given match will work with the current
   * map, or false otherwise.
   * 
   * @param match the match to consider
   * @return true if the given match will work with the current
   * map, or false otherwise
   */
  public boolean isMatchFeasible(Match match);
  
  /**
   * Returns true if all atoms in the query molecule have been
   * mapped.
   * 
   * @return true if all atoms in the query molecule have been
   * mapped
   */
  public boolean isGoal();
  
  /**
   * Returns true if no match will come from this State.
   * 
   * @return true if no match will come from this State
   */
  public boolean isDead();
  
  /**
   * Returns a state in which the atoms in match have been
   * added to the current mapping.
   * 
   * @param match the match to consider
   * @return  a state in which the atoms in match have been
   * added to the current mapping
   */
  public State nextState(Match match);
  
  /**
   * Returns this State's atom map to its original condition.
   */
  public void backTrack();
}
