/*
 * MX Cheminformatics Tools for Java
 *
 * Copyright (c) 2007-2008 Metamolecular, LLC
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

import com.metamolecular.mx.path.PathWriter;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.path.PathFinder;
import com.metamolecular.mx.query.AromaticAtomFilter;
import com.metamolecular.mx.ring.HanserRingFinder;
import com.metamolecular.mx.ring.RingFilter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathFingerprinter implements Fingerprinter
{
  private int length;
  private PathFinder pathFinder;
  private PathWriter writer;
  private RingFilter filter;
  Set<Atom> aromatics;

  public PathFingerprinter()
  {
    this.length = 1024;
    pathFinder = new PathFinder();
    writer = new PathWriter();
    filter = new RingFilter(new AromaticAtomFilter(), new HanserRingFinder());
    aromatics = null;
  }

  public void setMaximumPathDepth(int maxDepth)
  {
    pathFinder.setMaximumDepth(maxDepth);
  }

  public int getMaximumPathDepth()
  {
    return pathFinder.getMaximumDepth();
  }

  public void setFingerprintLength(int length)
  {
    this.length = length;
  }

  public int getFingerprintLength()
  {
    return length;
  }

  public BitSet getFingerprint(Molecule molecule)
  {
    aromatics = filter.filterAtoms(molecule);
    BitSet result = new BitSet(length);
    Set<String> paths = getPaths(molecule);

    for (String path : paths)
    {
      int position = new Random(path.hashCode()).nextInt(length);

      result.set(position);
    }

    return result;
  }

  private Set<String> getPaths(Molecule molecule)
  {
    List<List<Atom>> paths = new ArrayList();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      pathFinder.findAllPaths(molecule.getAtom(i), paths);
    }

    return compilePaths(paths);
  }

  private Set<String> compilePaths(List<List<Atom>> paths)
  {
    Set<String> result = new HashSet();

    for (List<Atom> path : paths)
    {
      writer.write(path, result, aromatics);
    }

    return result;
  }
}
