/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.ring;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rich
 */
public class RingFinder
{
  private List<List<Atom>> rings;
  
  public RingFinder()
  {
    rings = new ArrayList<List<Atom>>();
  }
  
  public List<List<Atom>> getRings(Molecule molecule)
  {
    return rings;
  }
}
