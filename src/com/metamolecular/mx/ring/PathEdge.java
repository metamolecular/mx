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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathEdge
{
  private List<Atom> atoms;

  public PathEdge(List<Atom> atoms)
  {
    this.atoms = atoms;
  }

  public List<Atom> getAtoms()
  {
    return atoms;
  }

  public Atom getSource()
  {
    return atoms.get(0);
  }

  public Atom getTarget()
  {
    return atoms.get(atoms.size() - 1);
  }

  public boolean isCycle()
  {
    return (atoms.size() > 2) && atoms.get(0).equals(atoms.get(atoms.size() - 1));
  }

  public PathEdge splice(PathEdge other)
  {
    return splice(other, 15);
  }

  public PathEdge splice(PathEdge other, int maxLength)
  {
    Atom intersection = getIntersection(other.atoms);
    List<Atom> newAtoms = new ArrayList(atoms);

    if (atoms.get(0) == intersection)
    {
      Collections.reverse(newAtoms);
    }

    if (other.atoms.get(0) == intersection)
    {
      for (int i = 1; i < other.atoms.size(); i++)
      {
        newAtoms.add(other.atoms.get(i));
      }
    }
    
    else
    {
      for (int i = other.atoms.size() - 2; i >= 0; i--)
      {
        newAtoms.add(other.atoms.get(i));
      }
    }

    if (newAtoms.size() > maxLength && maxLength > 0)
    {
      return null;
    }
    
    if (!isRealPath(newAtoms))
    {
      return null;
    }

    return new PathEdge(newAtoms);
  }

  private boolean isRealPath(List<Atom> atoms)
  {
    for (int i = 1; i < atoms.size() - 1; i++)
    {
      for (int j = 1; j < atoms.size() - 1; j++)
      {
        if (i == j)
        {
          continue;
        }

        if (atoms.get(i) == atoms.get(j))
        {
          return false;
        }
      }
    }

    return true;
  }

  private Atom getIntersection(List<Atom> others)
  {
    if (atoms.get(atoms.size() - 1) == others.get(0) || atoms.get(atoms.size() - 1) == others.get(others.size() - 1))
    {
      return atoms.get(atoms.size() - 1);
    }

    if (atoms.get(0) == others.get(0) || atoms.get(0) == others.get(others.size() - 1))
    {
      return atoms.get(0);
    }

    throw new RuntimeException("Couldn't splice - no intersection.");
  }
}
