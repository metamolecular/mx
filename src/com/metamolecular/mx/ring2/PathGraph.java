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
package com.metamolecular.mx.ring2;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class PathGraph
{

  private List<Atom> atoms;
  private List<List<Atom>> paths;

  public PathGraph(Molecule molecule)
  {
    atoms = new ArrayList();
    paths = new ArrayList();

    loadAtoms(molecule);
    loadPaths(molecule);
  }

  public boolean isEmpty()
  {
    return atoms.isEmpty();
  }

  public Atom getLeastConnectedAtom()
  {
    Atom result = null;

    for (Atom atom : atoms)
    {
      if (result == null)
      {
        result = atom;

        continue;
      }

      if (countConnections(atom) < countConnections(result))
      {
        result = atom;
      }
    }

    return result;
  }

  public int countConnections(Atom atom)
  {
    int result = 0;

    for (List<Atom> path : paths)
    {
      if (path.get(0) == atom)
      {
        result++;
      }

      if (path.get(path.size() - 1) == atom)
      {
        result++;
      }
    }

    return result;
  }

  public List<List<Atom>> remove(Atom atom)
  {
    List<List<Atom>> oldPaths = getPaths(atom);
    List<List<Atom>> newPaths = new ArrayList();

    for (List<Atom> source : oldPaths)
    {
      for (List<Atom> target : oldPaths)
      {
        if (source == target)
        {
          continue;
        }
        
        newPaths.add(splice(orient(source, atom), orient(target, atom)));
      }
    }
    
    paths.removeAll(oldPaths);
    paths.addAll(newPaths);

    List<List<Atom>> result = getPaths(atom);
    
    paths.removeAll(result);
    atoms.remove(atom);

    return result;
  }

  public List<List<Atom>> getPaths(Atom atom)
  {
    List<List<Atom>> result = new ArrayList();

    for (List<Atom> path : paths)
    {
      if (path.get(0) == atom || path.get(path.size() - 1) == atom)
      {
        result.add(path);
      }
    }

    return result;
  }
  
  private List<Atom> orient(List<Atom> path, Atom anchor)
  {
    if (path.get(0) == anchor)
    {
      return new ArrayList(path);
    }
    
    List<Atom> result = new ArrayList(path);
    
    Collections.reverse(result);
    
    return result;
  }
  
  private List<Atom> splice(List<Atom> source, List<Atom> target)
  {
    List<Atom> result = new ArrayList();
    
    result.addAll(source);
    result.remove(source.get(source.size() - 1));
    result.addAll(target);
    
    return result;
  }

  private void loadAtoms(Molecule molecule)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      atoms.add(molecule.getAtom(i));
    }
  }

  private void loadPaths(Molecule molecule)
  {
    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);
      List<Atom> path = new ArrayList();

      path.add(bond.getSource());
      path.add(bond.getTarget());

      paths.add(path);
    }
  }
}
