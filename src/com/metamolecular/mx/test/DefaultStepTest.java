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

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.walk.DefaultStep;
import com.metamolecular.mx.walk.Step;
import java.util.Arrays;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultStepTest extends TestCase
{
  private DefaultStep step;
  private Atom atom;
  private Bond bond;

  @Override
  protected void setUp() throws Exception
  {
    step = null;
    atom = mock(Atom.class);
    bond = mock(Bond.class);
  }

  public void testItHasAtomInInitialState()
  {
    atomWithOneBond();
    assertEquals(atom, step.getAtom());
  }

  public void testItHasPathWithAtomInInitialState()
  {
    atomWithOneBond();

    assertEquals(Arrays.asList(atom), step.getPath());
  }

  public void testItHasNextBondWhenAtomHasOneBond()
  {
    atomWithOneBond();
    assertTrue(step.hasNextBond());
  }

  public void testItReturnsNextBondWhenAtomHasOneBond()
  {
    atomWithOneBond();
    assertEquals(bond, step.nextBond());
  }

  public void testItLacksNextBondWhenAllBondsUsed()
  {
    atomWithOneBond();

    step.nextBond();

    assertFalse(step.hasNextBond());
  }

  public void testItDoesntHaveNextBondWhenAtomHasNoBonds()
  {
    atomWithNoBonds();
    assertFalse(step.hasNextBond());
  }

  public void testItThrowsWhenNextBondMissing()
  {
    atomWithNoBonds();
    
    try
    {
      step.nextBond();
      fail();
    }
    
    catch (RuntimeException ignore)
    {
      assertEquals("Attempt to get nonexistant bond.", ignore.getMessage());
    }
  }
  
  public void testItCreatesNextStepWithBondMateAsAtom()
  {
    atomWithOneBond();
    Atom mate = mock(Atom.class);
    Bond[] mateBonds = new Bond[]
    {
    };
    when(mate.getBonds()).thenReturn(mateBonds);
    when(bond.getMate(atom)).thenReturn(mate);

    Step nextStep = step.nextStep(step.nextBond());

    assertEquals(mate, nextStep.getAtom());
  }

  public void testItAddsNewAtomToPathOfNextStep()
  {
    atomWithOneBond();
    Atom mate = mock(Atom.class);
    Bond[] mateBonds = new Bond[]
    {
    };
    when(mate.getBonds()).thenReturn(mateBonds);
    when(bond.getMate(atom)).thenReturn(mate);

    Step nextStep = step.nextStep(step.nextBond());

    assertEquals(Arrays.asList(atom, mate), nextStep.getPath());
  }

  public void testItExcludesConnectingBondFromBondsOfNewStep()
  {
    atomWithOneBond();
    Atom mate = mock(Atom.class);
    Bond mateBond = mock(Bond.class);
    Bond[] mateBonds = new Bond[]
    {
      bond, mateBond
    };
    when(mate.getBonds()).thenReturn(mateBonds);
    when(bond.getMate(atom)).thenReturn(mate);

    Step nextStep = step.nextStep(step.nextBond());

    assertEquals(mateBond, nextStep.nextBond());
    assertFalse(nextStep.hasNextBond());
  }

  public void testItClosesRingWhenBondMateInPath()
  {
    atomWithOneBond();

    Bond closure = mock(Bond.class);
    when(closure.getMate(atom)).thenReturn(atom);

    assertTrue(step.closesRingWith(closure));
  }

  private void atomWithNoBonds()
  {
    Bond[] bonds = new Bond[]
    {
    };

    when(atom.getBonds()).thenReturn(bonds);
    step = new DefaultStep(atom);
  }

  private void atomWithOneBond()
  {
    Bond[] bonds = new Bond[]
    {
      bond
    };

    when(atom.getBonds()).thenReturn(bonds);
    step = new DefaultStep(atom);
  }
}
