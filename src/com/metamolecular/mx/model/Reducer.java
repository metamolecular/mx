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

package com.metamolecular.mx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Removes explicit hydrogens from a Molecules.
 * 
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class Reducer
{
  private static String hydrogen = "H";
  private VirtualHydrogenCounter hydrogenCounter;

  public Reducer()
  {
    hydrogenCounter = new VirtualHydrogenCounter();
  }

  public boolean canReduce(Atom atom)
  {
    if (!hydrogen.equals(atom.getSymbol()))
    {
      return false;
    }

    if (atom.hasSingleIsotope() || atom.countNeighbors() != 1)
    {
      return false;
    }

    Bond[] bonds = atom.getBonds();

    return bonds[0].getStereo() == 0 && bonds[0].getType() == 1;
  }

  /**
   * Removes all reducible hydrogens from molecule.
   * 
   * @param molecule the Molecule to reduce
   * @param reductions can be null
   */
  public void reduce(Molecule molecule, Map<Atom, Integer> reductions)
  {
    List toStrip = new ArrayList();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);

      recordReductions(atom, toStrip, reductions);
    }

    if (toStrip.isEmpty())
    {
      return;
    }

    molecule.beginModify();

    for (int i = 0; i < toStrip.size(); i++)
    {
      Atom atom = (Atom) toStrip.get(i);

      molecule.removeAtom(atom);
    }

    molecule.endModify();
  }

  private void recordReductions(Atom atom, List toStrip, Map<Atom, Integer> reductions)
  {
    if (!hydrogenCounter.isVirtualizableHeavyAtom(atom))
    {
      return;
    }

    Bond[] bonds = atom.getBonds();

    for (Bond bond : bonds)
    {
      Atom neighbor = bond.getMate(atom);

      if (canReduce(neighbor))
      {
        recordReduction(atom, neighbor, toStrip, reductions);
      }
    }
  }

  private void recordReduction(Atom atom, Atom hydrogen, List toStrip, Map<Atom, Integer> reductions)
  {
    toStrip.add(hydrogen);

    if (reductions == null)
    {
      return;
    }

    Integer count = reductions.get(atom);

    reductions.put(atom, count == null ? 1 : count + 1);
  }
}
