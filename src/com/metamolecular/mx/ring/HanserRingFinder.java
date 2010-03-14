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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class HanserRingFinder implements RingFinder
{
  private List<List<Atom>> rings;
  private int maxRingSize;
  
  public HanserRingFinder()
  {
    rings = new ArrayList();
    maxRingSize = -1;
  }

  public void setMaximumRingSize(int max)
  {
    this.maxRingSize = max;
  }

  public int getMaximumRingSize()
  {
    return this.maxRingSize;
  }
  
  public Collection<List<Atom>> findRings(Molecule molecule)
  {
    rings.clear();

    PathGraph graph = new PathGraph(molecule);

    graph.setMaximumRingSize(maxRingSize);

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      List<PathEdge> edges = graph.remove(molecule.getAtom(i));
      for (PathEdge edge : edges)
      {
        List<Atom> ring = edge.getAtoms();
        
        rings.add(ring);
      }
    }
    
    return rings;
  }
}
