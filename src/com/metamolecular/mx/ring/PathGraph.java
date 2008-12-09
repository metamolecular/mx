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
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class PathGraph
{

  private List<Atom> atoms;
  private List<PathEdge> edges;

  public PathGraph(Molecule molecule)
  {
    atoms = new ArrayList<Atom>();
    edges = new ArrayList<PathEdge>();

    loadAtoms(molecule);
    loadEdges(molecule);
  }
  
  public List<Atom> getAtoms()
  {
    return atoms;
  }
  
  public List<PathEdge> getEdges()
  {
    return edges;
  }

  public boolean hasNextAtom()
  {
    return !atoms.isEmpty();
  }
  
  public List<PathEdge> remove(Atom atom)
  {
    List<PathEdge> oldEdges = getEdges(atom);
    List<PathEdge> newEdges = new ArrayList<PathEdge>();
    
    for (PathEdge edge1 : oldEdges)
    {
      for (PathEdge edge2 : oldEdges)
      {
        if (edge1 == edge2)
        {
          continue;
        }
        
        newEdges.add(new PathEdge(edge1, edge2));
      }
    }
    
    edges.removeAll(oldEdges);
    edges.addAll(newEdges);
    atoms.remove(atom);
    
    return oldEdges;
  }

  public Atom nextAtom()
  {
    return findLeastConnectedAtom();
  }

  private void loadAtoms(Molecule molecule)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      atoms.add(molecule.getAtom(i));
    }
  }

  private void loadEdges(Molecule molecule)
  {
    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);
      PathEdge edge = new PathEdge(bond);

      edges.add(edge);
    }
  }

  private Atom findLeastConnectedAtom()
  {
    if (atoms.size() == 0)
    {
      return null;
    }

    Atom leastConnected = atoms.get(0);
    int lowestNeighborCount = countEdges(leastConnected);

    for (int i = 1; i < atoms.size(); i++)
    {
      Atom test = atoms.get(i);
      int testCount = countEdges(test);

      if (testCount < lowestNeighborCount)
      {
        lowestNeighborCount = testCount;
        leastConnected = test;
      }
    }

    return leastConnected;
  }

  private int countEdges(Atom atom)
  {
    int neighbors = 0;

    for (int i = 0; i < edges.size(); i++)
    {
      PathEdge edge = edges.get(i);

      if (edge.getSource() == atom || edge.getTarget() == atom)
      {
        neighbors++;
      }
    }

    return neighbors;
  }
  
  private List<PathEdge> getEdges(Atom atom)
  {
    List<PathEdge> result = new ArrayList<PathEdge>();
    
    for (PathEdge edge : edges)
    {
      if (edge.getSource() == atom || edge.getTarget() == atom)
      {
        result.add(edge);
      }
    }
    
    return result;
  }
}
