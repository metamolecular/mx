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
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.AtomMatcher;
import com.metamolecular.mx.ring.RingFilter;
import com.metamolecular.mx.ring.RingFinder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class RingFilterTest extends TestCase
{
  private RingFilter ringFilter;
  private Molecule molecule;
  private AtomMatcher matcher;
  private RingFinder finder;
  private List<List<Atom>> rings;
  private Set<Atom> matches;

  @Override
  protected void setUp() throws Exception
  {
    matcher = mock(AtomMatcher.class);
    finder = mock(RingFinder.class);
    molecule = mock(Molecule.class);
    rings = new ArrayList();
    ringFilter = new RingFilter(matcher, finder);
    matches = new HashSet();
    
    when(finder.findRings(molecule)).thenReturn(rings);
  }

  public void testItFindsAllAtomsWhenMatcherMatchesAllAtomsInSingleRing()
  {
    matchAll();
    addRing(6);
    molecule(6);

    ringFilter.filterAtoms(molecule, matches);
    assertEquals(6, matches.size());
    assertTrue(matches.containsAll(rings.get(0)));
  }
  
  public void testItFindsAllAtomsWhenMatcherMatchesAllAtomsInTwoRings()
  {
    matchAll();
    addRing(6);
    addRing(6);
    molecule(12);
    
    ringFilter.filterAtoms(molecule, matches);
    assertEquals(12, matches.size());
    assertTrue(matches.containsAll(rings.get(0)));
    assertTrue(matches.containsAll(rings.get(1)));
  }

  public void testItFindsNoAtomsWhenMatcherMatchesNoAtomsInSingleRing()
  {
    matchNone();
    addRing(6);
    molecule(6);
    
    ringFilter.filterAtoms(molecule, matches);
    assertEquals(0, matches.size());
  }
  
  private void addRing(int size)
  {
    List<Atom> ring = new ArrayList();
    
    for (int i = 0; i < size; i++)
    {
      ring.add(mock(Atom.class));
    }
    
    rings.add(ring);
  }

  private void matchAll()
  {
    when(matcher.matches(any(Atom.class))).thenReturn(true);
  }
  
  private void matchNone()
  {
    when(matcher.matches(any(Atom.class))).thenReturn(false);
  }

  private void molecule(int size)
  {
    when(molecule.countAtoms()).thenReturn(size);
  }
}
