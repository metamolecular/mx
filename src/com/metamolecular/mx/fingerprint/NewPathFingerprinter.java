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

package com.metamolecular.mx.fingerprint;

import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.AtomMatcher;
import com.metamolecular.mx.ring.HanserRingFinder;
import com.metamolecular.mx.ring.RingFilter;
import com.metamolecular.mx.ring.RingFinder;
import java.util.BitSet;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class NewPathFingerprinter implements Fingerprinter
{
  private RingFilter ringFilter;
  private AtomMatcher atomFilter;
  private RingFinder ringFinder;
  
  public NewPathFingerprinter()
  {
    ringFinder = new HanserRingFinder();
    ringFilter = new RingFilter(atomFilter, ringFinder);
  }
  
  public void setRingFinder(RingFinder ringFinder)
  {
    this.ringFinder = ringFinder;
  }
  
  public RingFinder getRingFinder()
  {
    return ringFinder;
  }
  
  public BitSet getFingerprint(Molecule molecule)
  {
    detectAromaticAtoms(molecule);
    
    return null;
  }
  
  private void detectAromaticAtoms(Molecule molecule)
  {
    ringFinder.findRings(molecule);
  }
}
