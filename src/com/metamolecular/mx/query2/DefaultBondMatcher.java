/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.query2;

import com.metamolecular.mx.model.Bond;

/**
 *
 * @author rich
 */
public class DefaultBondMatcher
{
  private int bondOrder;
  
  public DefaultBondMatcher(Bond bond)
  {
    this.bondOrder = bond.getType();
  }
  
  public boolean matches(Bond bond)
  {
    return bondOrder == bond.getType();
  }
}
