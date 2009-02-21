/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.metamolecular.mx.model;

/**
 * @author Richard L. Apodaca
 */
public interface Atom
{
  public String getSymbol();
  
  public void setSymbol(String newSymbol);
  
  public int getIndex();
  
  public void setIsotope(int isotope);
  
  public int getIsotope();
  
  public boolean hasSingleIsotope();
  
  public void setCharge(int charge);
  
  public int getCharge();
  
  public void setRadical(int radical);
  
  public int getRadical();
  
  public Atom[] getNeighbors();
  
  public Bond[] getBonds();
  
  public int countNeighbors();
  
  public boolean isConnectedTo(Atom atom);
  
  public int countVirtualHydrogens();
  
  public double getX();
  
  public double getY();
  
  public double getZ();
  
  public void move(double x, double y, double z);
  
  public int getValence();
  
  public Molecule getMolecule();
}
