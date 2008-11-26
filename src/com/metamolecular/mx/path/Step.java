/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.path;

import com.metamolecular.mx.model.Atom;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rich
 */
public interface Step
{
  public boolean hasNextBranch();
  
  public Atom nextBranch();
  
  public boolean isBranchFeasible(Atom branch);
  
  public Step nextStep(Atom branch);
  
  public Set<Atom> getSteppedAtoms();
  
  public List<Atom> getPath();
  
  public void backTrack();
}
