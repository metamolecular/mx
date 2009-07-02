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

package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultStep implements Step
{
  private Atom focus;
  private List<Atom> path;
  private List<Bond> bonds;

  public DefaultStep(Atom focus)
  {
    this.focus = focus;
    path = new ArrayList();
    bonds = new ArrayList();

    loadBonds(null);
    path.add(focus);
  }

  private DefaultStep(DefaultStep step, Bond bond)
  {
    this.focus = bond.getMate(step.getAtom());
    this.path = new ArrayList(step.path);
    this.bonds = new ArrayList();

    path.add(focus);    
    loadBonds(bond);
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
    return bonds.size() != 0;
  }

  public Bond nextBond()
  {
    Bond result = null;
    
    try
    {
      result = bonds.remove(bonds.size() - 1);
    }
    
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new RuntimeException("Attempt to get nonexistant bond.", e);
    }
    
    return result;
  }

  public Step nextStep(Bond bond)
  {
    return new DefaultStep(this, bond);
  }

  public boolean closesRingWith(Bond bond)
  {
    Atom mate = bond.getMate(focus);
    
    return path.contains(mate);
  }

  private void loadBonds(Bond exclude)
  {
    for (Bond bond : focus.getBonds())
    {
      if (bond != exclude)
      {
        bonds.add(bond);
      }
    }
  }
}
