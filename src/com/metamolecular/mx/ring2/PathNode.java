/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.ring2;

import com.metamolecular.mx.model.Atom;

/**
 *
 * @author rich
 */
public interface PathNode
{
  public Atom getAtom();
  
  public int countConnections();
}
