/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rich
 */
public interface State
{
  public boolean hasNextAtom();
  
  public Atom nextAtom();
  
  public boolean canVisit(Atom atom);
  
  public State nextState(Atom atom);
  
  public Set<Atom> getVisitedAtoms();
  
  public List<Atom> getPath();
  
  public void backTrack();
}
