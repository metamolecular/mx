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

package com.metamolecular.mx.fingerprint;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.AromaticAtomFilter;
import com.metamolecular.mx.ring.HanserRingFinder;
import com.metamolecular.mx.ring.RingFilter;
import com.metamolecular.mx.walk.DefaultWalker;
import com.metamolecular.mx.walk.PathWriter;
import com.metamolecular.mx.walk.Walker;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathFingerprinter implements Fingerprinter
{
  private PathWriter writer;
  private BloomFilter bloomFilter;
  private Walker walker;
  private RingFilter filter;
  private Set<Atom> aromatics;

  public PathFingerprinter()
  {
    this(new RingFilter(new AromaticAtomFilter(), new HanserRingFinder()));
  }

  public PathFingerprinter(RingFilter filter)
  {
    this.bloomFilter = new BloomFilter(1024);
    this.writer = new PathWriter(bloomFilter);
    this.walker = new DefaultWalker();
    this.filter = filter;
    this.aromatics = new HashSet();
  }

  public RingFilter getRingFilter()
  {
    return filter;
  }

  public void setRingFilter(RingFilter filter)
  {
    this.filter = filter;
  }

  public void setMaximumPathDepth(int maxDepth)
  {
    walker.setMaximumDepth(maxDepth);
  }

  public int getMaximumPathDepth()
  {
    return walker.getMaximumDepth();
  }

  public void setFingerprintLength(int length)
  {
    this.bloomFilter = new BloomFilter(length);
  }

  public int getFingerprintLength()
  {
    return bloomFilter.getBitArraySize();
  }

  public BitSet getFingerprint(Molecule molecule)
  {
    bloomFilter.clear();
    findAromatics(molecule);

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);

      walker.walk(atom, writer);
    }

    return bloomFilter.toBitSet();
  }

  private void findAromatics(Molecule molecule)
  {
    aromatics.clear();
    filter.filterAtoms(molecule, aromatics);

    for (Atom atom : aromatics)
    {
      aromatics.add(atom);
    }

    writer.setAromatics(aromatics);
  }
}
