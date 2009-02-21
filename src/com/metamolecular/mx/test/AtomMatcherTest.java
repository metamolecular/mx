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
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.AtomMatcher;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class AtomMatcherTest extends TestCase
{
  private AtomMatcher matcher;
  private Molecule phenol;

  public AtomMatcherTest()
  {
    phenol = Molecules.createPhenol();
  }

  @Override
  protected void setUp() throws Exception
  {
    matcher = new AtomMatcher();
  }

  public void testItShouldMatchBasedOnAtomLabel()
  {
    matcher.setSymbol("O");

    assertTrue(matcher.matches(phenol.getAtom(6)));
  }

  public void testItShouldNotMatchBasedOnAtomLabel()
  {
    matcher.setSymbol("O");

    assertFalse(matcher.matches(phenol.getAtom(0)));
  }

  public void testItShouldMatchBasedOnMaximumNeighbors()
  {
    matcher.setMaximumNeighbors(1);

    assertTrue(matcher.matches(phenol.getAtom(6)));
  }

  public void testItShouldNotMatchBasedOnMaximumNeighbors()
  {
    matcher.setMaximumNeighbors(1);

    assertFalse(matcher.matches(phenol.getAtom(0)));
  }

  public void testItShouldMatcBasedOnMinimumNeighbors()
  {
    matcher.setMinimumNeighbors(1);

    assertTrue(matcher.matches(phenol.getAtom(6)));
  }

  public void testItShouldNotMatchBasedOnMinimumNeighbors()
  {
    matcher.setMinimumNeighbors(2);

    assertFalse(matcher.matches(phenol.getAtom(6)));
  }

  public void testItShouldThrowWhenMaximumNeighborsExceedsMinimumNeighbors()
  {
    matcher.setMinimumNeighbors(2);

    try
    {
      matcher.setMaximumNeighbors(1);

      fail();
    }
    catch (IllegalStateException ignore)
    {
    }
  }

  public void testItShouldThrowWhenMinimumNeighborsExceedsMaximumNeighbors()
  {
    matcher.setMaximumNeighbors(2);

    try
    {
      matcher.setMinimumNeighbors(3);

      fail();
    }
    catch (IllegalStateException ignore)
    {
    }
  }
}
