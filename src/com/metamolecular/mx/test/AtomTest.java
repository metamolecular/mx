/*
 * MX - Essential Cheminformatics
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com/mx
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

package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class AtomTest extends TestCase
{
  public void testItReturnsBondToNeighbor()
  {
    Molecule propane = Molecules.createPropane();
    Atom atom1 = propane.getAtom(1);
    
    assertEquals(propane.getBond(atom1, propane.getAtom(0)), atom1.getBond(propane.getAtom(0)));
  }
  
  public void testItThrowsWhenFindingBondToNonexistantNeighbor()
  {
    Molecule propane = Molecules.createPropane();
    Atom atom0 = propane.getAtom(0);
    
    try
    {
      atom0.getBond(propane.getAtom(2)); 
      fail();
    }
    
    catch(RuntimeException ignore)
    {
      
    }
  }
  
  public void testItShouldUpdateItsPropertiesWhenChargeIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("O");

    assertEquals(0, atom.getCharge());
    assertEquals(2, atom.countVirtualHydrogens());

    atom.setCharge(-1);

    assertEquals(-1, atom.getCharge());
    assertEquals(1, atom.countVirtualHydrogens());
  }

  public void testItShouldUpdateItsPropertiesWhenIsotopeIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    assertFalse(atom.hasSingleIsotope());
    assertEquals(0, atom.getIsotope());

    atom.setIsotope(-1);

    assertTrue(atom.hasSingleIsotope());
    assertEquals(-1, atom.getIsotope());
  }

  public void testItShouldUpdateItsPropertiesWhenRadicalIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    assertEquals(0, atom.getRadical());
    assertEquals(4, atom.countVirtualHydrogens());

    atom.setRadical(1);

    assertEquals(1, atom.getRadical());

  //TODO: account for radical in VH count
  //assertEquals(3, atom.countVirtualHydrogens());
  }

  public void testItShouldThrowWhenIllegalRadicalIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("O");

    try
    {
      atom.setRadical(-1);
      fail();
    }
    catch (IllegalStateException ignore)
    {
    }

    try
    {
      atom.setRadical(4);
      fail();
    }
    catch (IllegalStateException ignore)
    {
    }
  }

  public void testItShouldChangeItsSymbolWhenGivenAValidNewOne()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    atom.setSymbol("O");

    assertEquals("O", atom.getSymbol());
  }

  public void testItShouldUpdateItsCoordinatesWhenMoved()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    atom.move(100, 100, 0);

    assertEquals(100d, atom.getX());
    assertEquals(100d, atom.getY());
    assertEquals(0d, atom.getZ());
  }

  public void testItShouldInduceMoleculeToFireWhenMoved()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");
    Listener listener = new Listener();

    molecule.addChangeListener(listener);

    atom.move(0, 100, 0);

    assertEquals(1, listener.count);
  }

  public void testItShouldThrowWhenSettingAnIllegalAtomLabel()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    try
    {
      atom.setSymbol("bailout");
      fail();
    }
    catch (IllegalStateException e)
    {
    }
  }

  public void testItShouldInduceMoleculeToFireWhenChargeIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("O");
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    atom.setCharge(-1);

    assertEquals(1, listener.count);
  }

  public void testItShouldInduceMoleculeToFireWhenIsotopeIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    atom.setIsotope(-1);

    assertEquals(1, listener.count);
  }

  public void testItShouldInduceMoleculeToFireWhenRadicalIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("O");
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    atom.setRadical(1);

    assertEquals(1, listener.count);
  }

  public void testItShouldReportValenceTwoForAPrimaryDoubleBondTerminal()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");

    molecule.connect(c1, c2, 2);

    assertEquals(2, c1.getValence());
  }

  public void testItShouldReportValenceFourForAQuaternaryCarbon()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");
    Atom c3 = molecule.addAtom("C");
    Atom c4 = molecule.addAtom("C");
    Atom c5 = molecule.addAtom("C");
    
    molecule.connect(c1, c2, 1);
    molecule.connect(c1, c3, 1);
    molecule.connect(c1, c4, 1);
    molecule.connect(c1, c5, 1);
    
    assertEquals(4, c1.getValence());
  }

  private class Listener implements ChangeListener
  {
    private int count = 0;

    public void stateChanged(ChangeEvent e)
    {
      count++;
    }
  }
}
