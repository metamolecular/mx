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

import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.Superatom;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.io.Molecules;

import junit.framework.TestCase;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * @author Duan Lian
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class SuperatomTest extends TestCase
{

  public void testAddAtomFromAnotherMolecule()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Molecule acetone = Molecules.createAcetone();
    try
    {
      superatom.addAtom(acetone.getAtom(0));
      fail();
    } catch (IllegalStateException e)
    {
    }
  }

  public void testAddBondFromAnotherMolecule()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Molecule acetone = Molecules.createAcetone();
    try
    {
      superatom.addCrossingBond(acetone.getBond(0));
      fail();
    } catch (IllegalStateException e)
    {
    }
  }

  public void testAddAtomTwiceThrows()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    try
    {
      superatom.addAtom(benzene.getAtom(0));
      superatom.addAtom(benzene.getAtom(0));
      fail();
    } catch (RuntimeException e)
    {
    }
  }

  public void testAddCrossingBondTwiceThrows()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    try
    {
      superatom.addCrossingBond(benzene.getBond(0));
      superatom.addCrossingBond(benzene.getBond(0));
      fail();
    } catch (RuntimeException e)
    {
    }
  }

  public void testRemoveNonExistantAtom()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    try
    {
      superatom.removeAtom(benzene.getAtom(0));
      fail();
    } catch (RuntimeException e)
    {
    }
  }

  public void testRemoveNonExistantCrossingBond()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    try
    {
      superatom.removeCrossingBond(benzene.getBond(0));
      fail();
    } catch (RuntimeException e)
    {
    }
  }

  public void testSetVectorForNonExistantCrossingBond()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    try
    {
      superatom.setCrossingVector(benzene.getBond(0), 0.1, 0.1);
      fail();
    } catch (RuntimeException e)
    {
    }
  }

  public void testShouldRemoveAtomFromsuperatom()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Atom atom = benzene.getAtom(0);
    superatom.addAtom(atom);
    benzene.removeAtom(atom);
    assertEquals(0, superatom.countAtoms());
  }

  public void testShouldRemoveCrossingBondFromsuperatom()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Bond bond = benzene.getBond(0);
    superatom.addCrossingBond(bond);
    benzene.removeBond(bond);
    assertEquals(0, superatom.countCrossingBonds());
  }

  public void testAddAtomShouldFiresEvent()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Listener listener = new Listener();
    benzene.addChangeListener(listener);
    superatom.addAtom(benzene.getAtom(0));
    assertEquals(1, listener.count);
  }

  public void testRemoveAtomShouldFiresEvent()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Atom atom = benzene.getAtom(0);
    superatom.addAtom(atom);
    Listener listener = new Listener();
    benzene.addChangeListener(listener);
    superatom.removeAtom(atom);
    assertEquals(1, listener.count);
  }

  public void testAddCrossingBondShouldFiresEvent()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Listener listener = new Listener();
    benzene.addChangeListener(listener);
    superatom.addCrossingBond(benzene.getBond(0));
    assertEquals(1, listener.count);
  }

  public void testRemoveCrossingBondShouldFiresEvent()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Bond bond = benzene.getBond(0);
    superatom.addCrossingBond(bond);
    Listener listener = new Listener();
    benzene.addChangeListener(listener);
    superatom.removeCrossingBond(bond);
    assertEquals(1, listener.count);
  }

  public void testSetCrossingVectorShouldFiresEvent()
  {
    Molecule benzene = Molecules.createBenzene();
    Superatom superatom = benzene.addSuperatom();
    Bond bond = benzene.getBond(0);
    superatom.addCrossingBond(bond);

    Listener listener = new Listener();
    benzene.addChangeListener(listener);
    superatom.setCrossingVector(bond, 0.1, 0.1);
    assertEquals(1, listener.count);
  }

  public void testAddSgroup()
  {
    Molecule molecule = Molecules.createBenzene();
    Superatom superatom = molecule.addSuperatom();
    assertEquals(1, molecule.countSuperatoms());

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      superatom.addAtom(molecule.getAtom(i));
    }
    for (int i = 0; i < molecule.countBonds(); i++)
    {
      superatom.addCrossingBond(molecule.getBond(i));
    }

    assertEquals(6, superatom.countAtoms());
    assertEquals(6, superatom.countCrossingBonds());

    //Make a toluene molecule
    Atom methyl = molecule.addAtom("C");
    Bond bondOfMethyl = molecule.connect(methyl, molecule.getAtom(0), 1);

    assertEquals(false, superatom.contains(methyl));
    assertEquals(false, superatom.contains(bondOfMethyl));
  }

  public void testShouldContainsSgroupWhenCopied()
  {
    Molecule molecule = Molecules.createBenzene();
    Superatom superatom = molecule.addSuperatom();
    Bond bond = molecule.getBond(0);
    superatom.addAtom(bond.getSource());
    superatom.addAtom(bond.getTarget());
    superatom.addCrossingBond(bond);

    Molecule moleculeCopy = molecule.copy();
    assertEquals(1, moleculeCopy.countSuperatoms());
    assertEquals(moleculeCopy.getBond(0), moleculeCopy.getSuperatom(0).getCrossingBond(0));

    moleculeCopy.copy(molecule);
    assertEquals(1, moleculeCopy.countSuperatoms());
    assertEquals(moleculeCopy.getBond(0), moleculeCopy.getSuperatom(0).getCrossingBond(0));
  }
  //TODO: test removing nonexistant superatom (from same molecule) throws - i.e.,
  //      the superatom was already deleted once before
  public void testGetsuperatomWithInalidIdThrows()
  {
    Molecule molecule = Molecules.createEthylbenzeneWithSuperatom();
    try
    {
      molecule.getSuperatom(2);
      fail();
    } catch (IndexOutOfBoundsException e)
    {
    }
  }

  public void testRemovesuperatomFromDifferentMoleculeThrows()
  {
    Molecule molecule = Molecules.createEthylbenzeneWithSuperatom();
    Molecule anotherMolecule = Molecules.createEthylbenzeneWithSuperatom();
    try
    {
      molecule.removeSuperatom(anotherMolecule.getSuperatom(0));
      fail();
    } catch (IllegalStateException e)
    {
    }
  }

  public void testRemovingNonExistantsuperatomThrows()
  {
    Molecule molecule = Molecules.createEthylbenzeneWithSuperatom();
    Superatom superatom = molecule.getSuperatom(0);
    molecule.removeSuperatom(superatom);
    try
    {
      molecule.removeSuperatom(superatom);
      fail();
    } catch (Exception e)
    {
    }
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