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
package com.metamolecular.mx.test;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class MoleculeTest extends TestCase
{
  //TODO: test getSubstructure with invalid id throws
  
  //TODO: test removing substructure from different molecule throws
  
  //TODO: test removing nonexistant substructure (from same molecule) throws - i.e.,
  //      the substructure was already deleted once before
  
  //TODO: test adding substructure fires event
  
  //TODO: test removing substructure fires event
  
  public void testItShouldAddAValidAtom()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    assertEquals(1, molecule.countAtoms());
    assertEquals("C", molecule.getAtom(0).getSymbol());
    assertEquals(atom, molecule.getAtom(0));
  }

  public void testItShouldLeavAnAtomWithANullMoleculeWhenDeletingIt()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom = molecule.addAtom("C");

    molecule.removeAtom(atom);

    assertEquals(0, molecule.countAtoms());
    assertNull(atom.getMolecule());
  }

  public void testItShouldConnectTwoValidAtomsWithAValidBondType()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom1 = molecule.addAtom("C");
    Atom atom2 = molecule.addAtom("C");
    Bond bond = molecule.connect(atom1, atom2, 1);

    assertEquals(atom1, bond.getSource());
    assertEquals(atom2, bond.getTarget());
    assertEquals(1, atom1.countNeighbors());
    assertEquals(1, atom2.countNeighbors());
    assertEquals(1, bond.getType());
    assertEquals(1, molecule.countBonds());
  }

  public void testItShouldDisconnectAtomsLeavingNoBondBetweenThem()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom1 = molecule.addAtom("C");
    Atom atom2 = molecule.addAtom("C");

    molecule.connect(atom1, atom2, 1);
    molecule.disconnect(atom1, atom2);

    assertEquals(2, molecule.countAtoms());
    assertEquals(0, atom1.countNeighbors());
    assertEquals(0, atom2.countNeighbors());
    assertEquals(0, molecule.countBonds());
  }

  public void testItShouldDeleteAllBondsToAnAtomItDeletes()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom1 = molecule.addAtom("C");
    Atom atom2 = molecule.addAtom("C");

    molecule.connect(atom1, atom2, 1);
    molecule.removeAtom(atom2);

    assertEquals(1, molecule.countAtoms());
    assertEquals(0, atom1.countNeighbors());
    assertEquals(0, atom2.countNeighbors());
    assertEquals(0, molecule.countBonds());
  }

  public void testItShouldThrowWhenConnectingAnAtomThatDoesntBelong()
  {
    Molecule molecule1 = new DefaultMolecule();
    Molecule molecule2 = new DefaultMolecule();
    Atom atom1 = molecule1.addAtom("C");
    Atom atom2 = molecule2.addAtom("C");
    boolean thrown = false;

    try
    {
      molecule1.connect(atom1, atom2, 1);
    }
    catch (Exception e)
    {
      thrown = true;
    }

    assertTrue(thrown);
  }

  public void testItShouldRemoveAnAtomThatBelongs()
  {
    Molecule molecule = new DefaultMolecule();

    Atom atom = molecule.addAtom("C");

    molecule.removeAtom(atom);

    assertEquals(0, molecule.countAtoms());
  }

  public void testItShouldThrowWhenRemovingAnAtomThatDoesntBelong()
  {
    Molecule molecule1 = new DefaultMolecule();
    Molecule molecule2 = new DefaultMolecule();
    Atom atom2 = molecule2.addAtom("C");
    boolean thrown = false;

    molecule1.addAtom("C");

    try
    {
      molecule1.removeAtom(atom2);
    }
    catch (Exception e)
    {
      thrown = true;
    }

    assertTrue(thrown);
  }

  public void testItShouldThrowWhenAddingInvalidAtom()
  {
    Molecule molecule = new DefaultMolecule();
    boolean thrown = false;

    try
    {
      molecule.addAtom("fail");
    }
    catch (Exception e)
    {
      thrown = true;
    }

    assertTrue(thrown);
  }

  public void testItShouldFireAtomAddEvent()
  {
    Molecule molecule = new DefaultMolecule();
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    molecule.addAtom("C");

    assertEquals(1, listener.count);
  }

  public void testItShouldFireBondAddEvent()
  {
    Molecule molecule = new DefaultMolecule();
    Listener listener = new Listener();

    molecule.addChangeListener(listener);

    Atom atom1 = molecule.addAtom("C");
    Atom atom2 = molecule.addAtom("C");

    molecule.connect(atom1, atom2, 1);

    assertEquals(3, listener.count);
  }

  public void testItShouldIgnoreAtomAddChangesWhenBeginModifySet()
  {
    Molecule molecule = new DefaultMolecule();
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    molecule.beginModify();
    molecule.addAtom("C");

    assertEquals(0, listener.count);
  }

  public void testItShouldFireSingleChangeEventAfterEndModify()
  {
    Molecule molecule = new DefaultMolecule();
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    molecule.beginModify();
    molecule.addAtom("C");
    molecule.addAtom("C");
    molecule.addAtom("C");
    molecule.endModify();

    assertEquals(1, listener.count);
  }

  public void testItShouldRemoveAChangeListener()
  {
    Molecule molecule = new DefaultMolecule();
    Listener listener = new Listener();

    molecule.addChangeListener(listener);
    molecule.removeChangeListener(listener);

    molecule.addAtom("C");

    assertEquals(0, listener.count);
  }

  public void testItShouldThrowWhenConnectingAnAtomToItself()
  {
    Molecule molecule = new DefaultMolecule();
    Atom atom1 = molecule.addAtom("C");

    try
    {
      molecule.connect(atom1, atom1, 1);
      fail();
    }
    catch (IllegalStateException ignore)
    {
    }
  }
  
  public void testItShouldThrowWhenRemovingANonmemberAtom()
  {
    Molecule m1 = new DefaultMolecule();
    Molecule m2 = new DefaultMolecule();
    Atom a1 = m1.addAtom("C");
    Atom a2 = m2.addAtom("C");
    
    try
    {
      m1.removeAtom(a2);
      fail();
    }
    catch(IllegalStateException ignore)
    {
      
    }
  }
  
  public void testCopiedMethaneGivesAtomWithCorrectIndex()
  {
    Molecule molecule = new DefaultMolecule();
    molecule.addAtom("C");
    
    Molecule copy = molecule.copy();
    
    assertEquals(0, copy.getAtom(0).getIndex());
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
