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
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultStep implements Step
{

  private List<Atom> path;
  private Atom focus;
  private Set<Bond> visited;
  private List<Bond> bonds;

  public DefaultStep(Atom focus)
  {
    this.focus = focus;
    path = new ArrayList();
    visited = new HashSet();
    bonds = new ArrayList();
    
    loadBonds();
  }
  
  private DefaultStep(Step step, Bond bond)
  {
    
  }

  public Atom getAtom()
  {
    return focus;
  }

  public List<Atom> getPath()
  {
    return path;
  }

  public boolean hasNextBond()
  {
    for (Bond bond : bonds)
    {
      if (!visited.contains(bond))
      {
        return true;
      }
    }

    return false;
  }

  public Bond nextBond()
  {
    return bonds.remove(bonds.size() - 1);
  }

  public Step nextStep(Bond bond)
  {
    return new DefaultStep(this, bond);
  }

  public boolean closesRingWith(Bond bond)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  private void loadBonds()
  {
    for (Bond bond : focus.getBonds())
    {
      if (!path.contains(bond))
      {
        bonds.add(bond);
      }
    }
  }
}
