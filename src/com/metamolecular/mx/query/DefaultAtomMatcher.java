/*
 * MX - Essential Cheminformatics
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com/mx
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

package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultAtomMatcher implements AtomMatcher
{

  private String symbol;
  private int maximumNeighbors;

  public DefaultAtomMatcher()
  {
    symbol = null;
    maximumNeighbors = -1;
  }

  public DefaultAtomMatcher(Atom atom)
  {
    this();

    this.symbol = atom.getSymbol();
  }
  
  public DefaultAtomMatcher(Atom template, int blockedPositions)
  {
    this(template);
    
    this.maximumNeighbors = template.countNeighbors() + template.countVirtualHydrogens() - blockedPositions;
  }

  public boolean matches(Atom atom)
  {
    if (!matchSymbol(atom))
    {
      return false;
    }

    if (!matchMaximumNeighbors(atom))
    {
      return false;
    }
    
    return true;
  }

  public void setMaximumNeighbors(int maximum)
  {
    this.maximumNeighbors = maximum;
  }

  public void setSymbol(String symbol)
  {
    this.symbol = symbol;
  }

  private boolean matchSymbol(Atom atom)
  {
    if (symbol == null)
    {
      return true;
    }

    return symbol.equals(atom.getSymbol());
  }

  private boolean matchMaximumNeighbors(Atom atom)
  {
    if (maximumNeighbors == -1)
    {
      return true;
    }

    return atom.countNeighbors() <= maximumNeighbors;
  }
}
