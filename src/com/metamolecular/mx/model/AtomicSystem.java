/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
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

import java.util.List;
import java.util.ArrayList;

/**
 * @author Richard L. Apodaca
 */
public class AtomicSystem
{
  private static AtomicSystem instance = null;
  
  private List symbols;
  
  private AtomicSystem()
  {
    symbols = new ArrayList();
    
    loadSymbols();
  }
  
  public boolean validateSymbol(String symbol)
  {
    return symbols.contains(symbol);
  }
  
  public static AtomicSystem getInstance()
  {
    if (instance == null)
    {
      instance = new AtomicSystem();
    }
    
    return instance;
  }
  
  private void loadSymbols()
  {
    String[] symbolArray =
    {"H", "He",
     "Li", "Be", "B", "C", "N", "O", "F", "Ne",
     "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar",
     "K", "Ca", "Ga", "Ge", "As", "Se", "Br", "Kr",
     "Rb", "Sr", "In", "Sn", "Sb", "Te", "I", "Xe",
     "Cs", "Ba", "Tl", "Pb", "Bi", "Po", "At", "Rn",
     "Fr", "Ra",
     "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn",
     "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd",
     "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg",
     "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb",
     "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No",
     "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg"};
    
    for (int i = 0; i < symbolArray.length; i++)
    {
      symbols.add(symbolArray[i]);
    }
  }
}
