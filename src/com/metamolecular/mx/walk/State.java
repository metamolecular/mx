/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;

/**
 *
 * @author rich
 */
public interface State
{
  public boolean hasNextBranch();
  
  public Atom nextBranch();
  
  public boolean isValidBranch(Atom atom);
  
  public State nextState(Atom atom);
}
