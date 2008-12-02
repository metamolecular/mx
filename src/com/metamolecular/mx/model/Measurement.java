/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.model;

/**
 *
 * @author rich
 */
public interface Measurement
{
  public double getValue();
  
  public double getError();
  
  public String getUnits();
}
