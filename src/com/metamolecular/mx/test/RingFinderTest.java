/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.RingFinder;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class RingFinderTest extends TestCase
{
  public void testItShouldFindOneRingInAMonocyclicMolecule()
  {
    Molecule benzene = Molecules.createBenzene();
    RingFinder finder = new RingFinder();
    
    List<List<Atom>> rings = finder.getRings(benzene);
    
    assertEquals(1, rings.size());
  }
}
