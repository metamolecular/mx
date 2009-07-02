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

package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Reducer;
import com.metamolecular.mx.model.Molecule;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class ReducerTest extends TestCase
{
  private Reducer reducer;
  private Map<Atom, Integer> reductions;

  @Override
  protected void setUp() throws Exception
  {
    reducer = new Reducer();
    reductions = new HashMap();
  }

  public void testItCanReduceNonstereoHydrogen()
  {
    Molecule propane = createBlockedPropane();

    assertTrue(reducer.canReduce(propane.getAtom(3)));
  }

  public void testItCanNotReduceStereoHydrogen()
  {
    Molecule propane = createChiralBlockedPropane();

    assertFalse(reducer.canReduce(propane.getAtom(3)));
  }

  public void testItCanNotReduceIsotopicHydrogen()
  {
    Molecule propane = createIsotopeBlockedPropane();

    assertFalse(reducer.canReduce(propane.getAtom(3)));
  }

  public void testItReducesBlockedPropaneToThreeAtoms()
  {
    Molecule propane = createBlockedPropane();

    reducer.reduce(propane, reductions);

    assertEquals(3, propane.countAtoms());
  }

  public void testItRemovesVirtualizableHydrogensFromBlockedPropane()
  {
    Molecule propane = createBlockedPropane();

    reducer.reduce(propane, reductions);
    boolean found = false;

    for (int i = 0; i < propane.countAtoms(); i++)
    {
      if (reducer.canReduce(propane.getAtom(i)))
      {
        found = true;
        break;
      }
    }

    assertFalse(found);
  }

  public void testItReportsReductionForBlockedPropane()
  {
    Molecule propane = createBlockedPropane();

    reducer.reduce(propane, reductions);

    assertEquals(1, reductions.get(propane.getAtom(1)).intValue());
  }

  public void testItReportsDoubleReductionForDoubleBlockedPropane()
  {
    Molecule propane = createDoubleBlockedPropane();

    reducer.reduce(propane, reductions);

    assertEquals(2, reductions.get(propane.getAtom(1)).intValue());
  }

  public void testDoesntThrowWithNullMap()
  {
    Molecule propane = createBlockedPropane();
    boolean pass = false;
    
    try
    {
      reducer.reduce(propane, null);
      pass = true;
    }
    catch (NullPointerException e)
    {
    }

    assertTrue(pass);
  }

  private Molecule createBlockedPropane()
  {
    Molecule result = Molecules.createPropane();

    result.connect(result.getAtom(1), result.addAtom("H"), 1);

    return result;
  }

  private Molecule createDoubleBlockedPropane()
  {
    Molecule result = createBlockedPropane();

    result.connect(result.getAtom(1), result.addAtom("H"), 1);

    return result;
  }

  private Molecule createChiralBlockedPropane()
  {
    Molecule result = createBlockedPropane();
    Bond bond = result.getBond(result.getAtom(1), result.getAtom(3));

    bond.setStereo(1);

    return result;
  }

  private Molecule createIsotopeBlockedPropane()
  {
    Molecule result = createBlockedPropane();
    Atom h = result.getAtom(3);

    h.setIsotope(1);

    return result;
  }
}
