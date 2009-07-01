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

import com.metamolecular.mx.fingerprint.NewPathFingerprinter;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.RingFinder;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class NewPathFingerprinterTest extends TestCase
{
  private NewPathFingerprinter fingerprinter;
  private RingFinder ringFinder;
  private Molecule molecule;

  @Override
  protected void setUp() throws Exception
  {
    molecule = mock(Molecule.class);
    ringFinder = mock(RingFinder.class);
    fingerprinter = new NewPathFingerprinter();
  }
  
  public void testItHasARingFinder()
  {
    assertNotNull(fingerprinter.getRingFinder());
  }
  
  public void testItSetsRingFilter()
  {
    fingerprinter.setRingFinder(ringFinder);
    
    assertEquals(ringFinder, fingerprinter.getRingFinder());
  }

  
  public void testItFiltersRingAtoms()
  {
    fingerprinter.setRingFinder(ringFinder);
    fingerprinter.getFingerprint(molecule);
    
    verify(ringFinder, times(1)).findRings(molecule);
  }
}
