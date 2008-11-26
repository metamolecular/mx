/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.VirtualHydrogenCounter;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class VirtualHydrogenCounterTest extends TestCase
{
  private VirtualHydrogenCounter counter;

  @Override
  protected void setUp() throws Exception
  {
    counter = new VirtualHydrogenCounter();
  }

  public void testItShouldSayMethanolOxygenHasOneVirtualHydrogen()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c = molecule.addAtom("C");
    Atom o = molecule.addAtom("O");

    molecule.connect(c, o, 1);

    assertEquals(1, counter.countVirtualHydrogens(o));
  }

  public void testItShouldSayHydrogenChlorideHasOneVirtualHydrogen()
  {
    Molecule molecule = new DefaultMolecule();
    Atom cl = molecule.addAtom("Cl");

    assertEquals(1, counter.countVirtualHydrogens(cl));
  }

  public void testItShoudSayMethylamineNitrogenHasTwoVirtualHydrogens()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c = molecule.addAtom("C");
    Atom n = molecule.addAtom("N");

    molecule.connect(c, n, 1);

    assertEquals(2, counter.countVirtualHydrogens(n));
  }

  public void testItShouldSayEthaneCarbonHasThreeVirtualHydrogens()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");

    molecule.connect(c1, c2, 1);

    assertEquals(3, counter.countVirtualHydrogens(c1));
  }

  public void testItShouldSayHydroxylSingletRadicalHasOneVirtualHydrogen()
  {
    Molecule molecule = new DefaultMolecule();
    Atom o = molecule.addAtom("O");

    o.setRadical(1);

    assertEquals(1, counter.countVirtualHydrogens(o));
  }

  public void testItShouldSayMethylamineSingletRadicalNitrogenHasOneVirtualHydrogen()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c = molecule.addAtom("C");
    Atom n = molecule.addAtom("N");

    n.setRadical(1);
    molecule.connect(c, n, 1);

    assertEquals(1, counter.countVirtualHydrogens(n));
  }

  public void testItShouldSayEthylRadicalCarbonHasTwoHydrogens()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");

    c2.setRadical(1);
    molecule.connect(c1, c2, 1);

    assertEquals(2, counter.countVirtualHydrogens(c2));
  }

  public void testItShouldSayMethylDoubletRadicalCarbonHasTwoHydrogens()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c = molecule.addAtom("C");

    c.setRadical(2);

    assertEquals(2, counter.countVirtualHydrogens(c));
  }

  public void testItShouldSayChlorineRadicalHasNoHydrogens()
  {
    Molecule molecule = new DefaultMolecule();
    Atom cl = molecule.addAtom("Cl");

    cl.setRadical(1);

    assertEquals(0, counter.countVirtualHydrogens(cl));
  }
}
