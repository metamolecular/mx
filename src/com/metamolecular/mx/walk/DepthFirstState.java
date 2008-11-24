/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Richard L. Apodaca
 */
public class DepthFirstState implements State
{
  private Atom root;
  private List<Atom> neighbors;
  private List<Atom> path;
  private Set<Atom> visited;

  public DepthFirstState(Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>();
    this.visited = new HashSet<Atom>();

    path.add(root);
    visited.add(root);

    this.neighbors = loadNeighbors();
  }

  private DepthFirstState(DepthFirstState state, Atom root)
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

  public Set<Atom> getVisitedAtoms()
  {
    return visited;
  }

  public boolean hasNextAtom()
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

  public Atom nextAtom()
  {
    return neighbors.remove(neighbors.size() - 1);
  }

  public boolean canVisit(Atom atom)
  {
    if (!getHead().isConnectedTo(atom))
    {
      return false;
    }

    return !path.contains(atom);
  }

  public State nextState(Atom atom)
  {
    return new DepthFirstState(this, atom);
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
