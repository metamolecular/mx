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

package com.metamolecular.mx.ring;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.AtomMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class RingFilter
{
  private RingFinder ringFinder;
  private Comparator comparator;
  private AtomMatcher filter;

  public RingFilter(AtomMatcher filter, RingFinder finder)
  {
    ringFinder = finder;
    comparator = new RingSizeComparator();
    this.filter = filter;
  }

  public void filterAtoms(Molecule molecule, Collection<Atom> atoms)
  {
    List<List<Atom>> rings = new ArrayList(ringFinder.findRings(molecule));
    Collections.sort(rings, comparator);

    for (List<Atom> ring : rings)
    {
      if (atoms.size() == molecule.countAtoms())
      {
        break;
      }

      if (ringMatches(ring))
      {
        atoms.addAll(ring);
      }
    }
  }

  private boolean ringMatches(List<Atom> ring)
  {
    for (Atom atom : ring)
    {
      if (!filter.matches(atom))
      {
        return false;
      }
    }

    return true;
  }

  private class RingSizeComparator implements Comparator
  {
    public int compare(Object o1, Object o2)
    {
      return ((List) o1).size() - ((List) o2).size();
    }
  }
}
