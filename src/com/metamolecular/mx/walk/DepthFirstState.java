/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class DepthFirstState implements State
{
  private Atom root;
  private List<Atom> neighbors;
  private List<Atom> path;

  public DepthFirstState(Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>();

    path.add(root);

    this.neighbors = loadNeighbors();
  }

  private DepthFirstState(DepthFirstState state, Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>(state.path);

    path.add(root);

    this.neighbors = loadNeighbors();
  }

  public boolean hasNextBranch()
  {
    return !neighbors.isEmpty();
  }

  public Atom nextBranch()
  {
    return neighbors.remove(neighbors.size() - 1);
  }

  public boolean isValidBranch(Atom atom)
  {
    if (!getHead().isConnectedTo(atom))
    {
      return false;
    }

    if (path.size() >= 2 && path.get(path.size() - 2).equals(atom))
    {
      return false;
    }

    return true;
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
