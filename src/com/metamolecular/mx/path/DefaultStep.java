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

package com.metamolecular.mx.path;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Richard L. Apodaca
 */
public class DefaultStep implements Step
{
  private Atom root;
  private List<Atom> neighbors;
  private List<Atom> path;
  private Set<Atom> visited;

  public DefaultStep(Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>();
    this.visited = new HashSet<Atom>();

    path.add(root);
    visited.add(root);

    this.neighbors = loadNeighbors();
  }

  private DefaultStep(DefaultStep state, Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>(state.path);
    this.visited = state.visited;

    path.add(root);
    visited.add(root);

    this.neighbors = loadNeighbors();
  }

  public void backTrack()
  {
    visited.clear();
    
    for (Atom atom : path)
    {
      visited.add(atom);
    }
    
    visited.add(root);
  }

  public List<Atom> getPath()
  {
    return path;
  }

  public Set<Atom> getSteppedAtoms()
  {
    return visited;
  }

  public boolean hasNextBranch()
  {
    for (Atom neighbor : neighbors)
    {
      if (!visited.contains(neighbor))
      {
        return true;
      }
    }
    
    return false;
  }

  public Atom nextBranch()
  {
    return neighbors.remove(neighbors.size() - 1);
  }

  public boolean isBranchFeasible(Atom atom)
  {
    if (!getHead().isConnectedTo(atom))
    {
      return false;
    }

    return !path.contains(atom);
  }

  public Step nextStep(Atom atom)
  {
    return new DefaultStep(this, atom);
  }

  private Atom getHead()
  {
    return path.get(path.size() - 1);
  }

  private List<Atom> loadNeighbors()
  {
    List result = new ArrayList();

    for (Atom atom : root.getNeighbors())
    {
      if (!path.contains(atom))
      {
        result.add(atom);
      }
    }

    return result;
  }
}
