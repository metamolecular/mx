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

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.query.AromaticAtomFilter;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class AromaticAtomFilterTest extends TestCase
{
  private AromaticAtomFilter filter;

  public AromaticAtomFilterTest()
  {
    filter = new AromaticAtomFilter();
  }

  @Override
  protected void setUp() throws Exception
  {
  }

  public void testItMatchesSP2()
  {
    assertTrue(filter.matches(mockUnsaturatedAtom()));
  }

  public void testItDoesntMatchSP3Carbon()
  {
    assertFalse(filter.matches(mockSP3Carbon()));
  }

  public void testItMatchesSP3Oxygen()
  {
    assertTrue(filter.matches(mockSP3Oxygen()));
  }

  public void testItMatchesSP3Nitrogen()
  {
    assertTrue(filter.matches(mockSP3Nitrogen()));
  }

  private Atom mockUnsaturatedAtom()
  {
    Atom result = mock(Atom.class);

    when(result.countNeighbors()).thenReturn(2);
    when(result.getValence()).thenReturn(3);

    return result;
  }

  private Atom mockSP3Carbon()
  {
    Atom result = mock(Atom.class);

    when(result.countNeighbors()).thenReturn(2);
    when(result.countVirtualHydrogens()).thenReturn(2);
    when(result.getValence()).thenReturn(2);

    return result;
  }

  private Atom mockSP3Oxygen()
  {
    Atom result = mock(Atom.class);

    when(result.countNeighbors()).thenReturn(2);
    when(result.getValence()).thenReturn(2);
    when(result.countVirtualHydrogens()).thenReturn(0);

    return result;
  }

  private Atom mockSP3Nitrogen()
  {
    Atom result = mock(Atom.class);

    when(result.countNeighbors()).thenReturn(2);
    when(result.getValence()).thenReturn(2);
    when(result.countVirtualHydrogens()).thenReturn(1);

    return result;
  }
}
