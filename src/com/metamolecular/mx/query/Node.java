/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.metamolecular.mx.query;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public interface Node
{
  public int countNeighbors();
  
  public AtomMatcher getAtomMatcher();
}
