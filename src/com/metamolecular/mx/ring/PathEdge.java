/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
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

package com.metamolecular.mx.ring;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Richard L. Apodaca
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

  public PathEdge splice(PathEdge other)
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
    
    return new PathEdge(newAtoms);
  }
  
  private Atom getIntersection(List<Atom> others)
  {
    if (atoms.get(0) == others.get(0) || atoms.get(0) == others.get(others.size() - 1))
    {
      return atoms.get(0);
    }
    
    if (atoms.get(atoms.size() - 1) == others.get(0) || atoms.get(atoms.size() - 1) == others.get(others.size() - 1))
    {
      return atoms.get(atoms.size() -1);
    }
    
    throw new RuntimeException("Couldn't splice - no intersection.");
  }
}
