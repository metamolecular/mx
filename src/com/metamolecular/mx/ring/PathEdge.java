/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.ring;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rich
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
