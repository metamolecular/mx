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
import java.util.Arrays;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class PathGraph
{

  private List<PathEdge> edges;
  private List<Atom> atoms;

  public PathGraph(Molecule molecule)
  {
    edges = new ArrayList();
    atoms = new ArrayList();

    loadEdges(molecule);
    loadNodes(molecule);
  }

  public void printPaths()
  {
    for (PathEdge edge : edges)
    {
      if (edge.isCycle())
      {
        System.out.print("*");
      }

      for (Atom atom : edge.getAtoms())
      {
        System.out.print(atom.getIndex() + "-");
      }

      System.out.println();
    }
  }

  public List<PathEdge> remove(Atom atom)
  {
    List<PathEdge> oldEdges = getEdges(atom);
    List<PathEdge> result = new ArrayList();

    for (PathEdge edge : oldEdges)
    {
      if (edge.isCycle())
      {
        result.add(edge);
      }
//      if (edge.isCycle() && edge.getSource().equals(atom))
//      {
//        result.add(edge);
//      }
    }

    oldEdges.removeAll(result);
    edges.removeAll(result);

    List<PathEdge> newEdges = spliceEdges(oldEdges);

    edges.removeAll(oldEdges);
    edges.addAll(newEdges);
    atoms.remove(atom);

    return result;
  }

  private List<PathEdge> spliceEdges(List<PathEdge> edges)
  {
    List<PathEdge> result = new ArrayList();

    for (int i = 0; i < edges.size(); i++)
    {
      for (int j = i + 1; j < edges.size(); j++)
      {
        PathEdge splice = edges.get(j).splice(edges.get(i));

        if (splice != null)
        {
          result.add(splice);
        }
      }
    }

    return result;
  }

  private List<PathEdge> getEdges(Atom atom)
  {
    List<PathEdge> result = new ArrayList();

    for (PathEdge edge : edges)
    {
//      if ((edge.getSource() == atom) || (edge.getTarget() == atom))
//      {
//        result.add(edge);
//      }
      if (edge.isCycle())
      {
        if (edge.getAtoms().contains(atom))
        {
          result.add(edge);
        }
      }
      else
      {
        if ((edge.getSource() == atom) || (edge.getTarget() == atom))
        {
          result.add(edge);
        }
      }
    }

    return result;
  }

  private void loadEdges(Molecule molecule)
  {
    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);

      edges.add(new PathEdge(Arrays.asList(bond.getSource(), bond.getTarget())));
    }
  }

  private void loadNodes(Molecule molecule)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      atoms.add(molecule.getAtom(i));
    }
  }
}
