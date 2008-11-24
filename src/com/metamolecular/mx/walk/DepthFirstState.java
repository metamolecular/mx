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
  private List<Atom> walked;

  public DepthFirstState(Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>();
    this.walked = new ArrayList<Atom>();

    path.add(root);
    walked.add(root);

    this.neighbors = loadNeighbors();
  }

  private DepthFirstState(DepthFirstState state, Atom root)
  {
    this.root = root;
    this.path = new ArrayList<Atom>(state.path);
    this.walked = state.walked;

    path.add(root);
    walked.add(root);

    this.neighbors = loadNeighbors();
  }

  public boolean hasNextBranch()
  {
    for (Atom neighbor : neighbors)
    {
      if (!walked.contains(neighbor))
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

  public boolean isValidBranch(Atom atom)
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
