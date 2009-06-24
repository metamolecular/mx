/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.query2;

import com.metamolecular.mx.model.Atom;

/**
 *
 * @author rich
 */
public class DefaultAtomMatcher
{
  private String symbol;
  
  public DefaultAtomMatcher()
  {
    symbol = null;
  }
  
  public boolean matches(Atom atom)
  {
    return symbol.equals(atom.getSymbol());
  }
  
  public void setSymbol(String symbol)
  {
    this.symbol = symbol;
  }
}
