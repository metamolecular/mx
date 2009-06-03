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

import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.Substructure;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.io.Molecules;

import junit.framework.TestCase;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * @author Duan Lian
 */
public class SubstructureTest extends TestCase
{
  public void testAddAtomFromAnotherMolecule(){
      Molecule benzene = Molecules.createBenzene();
      Substructure substructure = benzene.addSubstructure();
      Molecule acetone = Molecules.createAcetone();
      try
      {
          substructure.addAtom(acetone.getAtom(0));
          fail();
      }catch(IllegalStateException e){

      }
  }
  
  public void testAddBondFromAnotherMolecule()
  {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Molecule acetone = Molecules.createAcetone();
       try
       {
           substructure.addCrossingBond(acetone.getBond(0));
           fail();
       }catch(IllegalStateException e){

       }
  }

  public void testAddAtomTwiceThrows()
  {
      Molecule benzene = Molecules.createBenzene();
      Substructure substructure = benzene.addSubstructure();
      try
      {
          substructure.addAtom(benzene.getAtom(0));
          substructure.addAtom(benzene.getAtom(0));
          fail();
      }catch(RuntimeException e){

      }
  }

  public void testAddCrossingBondTwiceThrows()
  {
      Molecule benzene = Molecules.createBenzene();
      Substructure substructure = benzene.addSubstructure();
      try
      {
          substructure.addCrossingBond(benzene.getBond(0));
          substructure.addCrossingBond(benzene.getBond(0));
          fail();
      }catch(RuntimeException e){

      }
  }

  public void testRemoveNonExistantAtom()
  {
      Molecule benzene = Molecules.createBenzene();
      Substructure substructure = benzene.addSubstructure();
      try
      {
          substructure.removeAtom(benzene.getAtom(0));
          fail();
      }catch(RuntimeException e){

      }
  }

  public void testRemoveNonExistantCrossingBond()
  {
      Molecule benzene = Molecules.createBenzene();
      Substructure substructure = benzene.addSubstructure();
      try
      {
          substructure.removeCrossingBond(benzene.getBond(0));
          fail();
      }catch(RuntimeException e){

      }
  }

  public void testSetVectorForNonExistantCrossingBond(){
      Molecule benzene = Molecules.createBenzene();
      Substructure substructure = benzene.addSubstructure();
      try
      {
          substructure.setCrossingVector(benzene.getBond(0),0.1,0.1);
          fail();
      }catch(RuntimeException e){

      }
   }

   public void testShouldRemoveAtomFromSubstructure()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Atom atom=benzene.getAtom(0);
       substructure.addAtom(atom);
       benzene.removeAtom(atom);
       assertEquals(0,substructure.countAtoms());
   }
  
   public void testShouldRemoveCrossingBondFromSubstructure()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Bond bond=benzene.getBond(0);
       substructure.addCrossingBond(bond);
       benzene.removeBond(bond);
       assertEquals(0,substructure.countCrossingBonds());
   }

   public void testAddAtomShouldFiresEvent()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Listener listener = new Listener();
       benzene.addChangeListener(listener);
       substructure.addAtom(benzene.getAtom(0));
       assertEquals(1, listener.count);
   }

   public void testRemoveAtomShouldFiresEvent()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Atom atom = benzene.getAtom(0);
       substructure.addAtom(atom);
       Listener listener = new Listener();
       benzene.addChangeListener(listener);
       substructure.removeAtom(atom);
       assertEquals(1, listener.count);
   }


   public void testAddCrossingBondShouldFiresEvent()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Listener listener = new Listener();
       benzene.addChangeListener(listener);
       substructure.addCrossingBond(benzene.getBond(0));
       assertEquals(1, listener.count);
   }

   public void testRemoveCrossingBondShouldFiresEvent()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Bond bond = benzene.getBond(0);
       substructure.addCrossingBond(bond);
       Listener listener = new Listener();
       benzene.addChangeListener(listener);
       substructure.removeCrossingBond(bond);
       assertEquals(1, listener.count);
   }

   public void testSetCrossingVectorShouldFiresEvent()
   {
       Molecule benzene = Molecules.createBenzene();
       Substructure substructure = benzene.addSubstructure();
       Bond bond = benzene.getBond(0);
       substructure.addCrossingBond(bond);

       Listener listener = new Listener();
       benzene.addChangeListener(listener);       
       substructure.setCrossingVector(bond,0.1,0.1);
       assertEquals(1, listener.count);
   }
  
  public void testAddSgroup()
  {
      Molecule molecule = Molecules.createBenzene();
      Substructure substructure = molecule.addSubstructure();
      assertEquals(1, molecule.countSubstructures());

      for (int i = 0; i < molecule.countAtoms(); i++) {
        substructure.addAtom(molecule.getAtom(i));
      }
      for (int i = 0; i < molecule.countBonds(); i++) {
        substructure.addCrossingBond(molecule.getBond(i));
      }
      
      assertEquals(6, substructure.countAtoms());
      assertEquals(6, substructure.countCrossingBonds());

      //Make a toluene molecule
      Atom methyl = molecule.addAtom("C");
      Bond bondOfMethyl = molecule.connect(methyl, molecule.getAtom(0), 1);

      assertEquals(false, substructure.contains(methyl));
      assertEquals(false, substructure.contains(bondOfMethyl));
  }

  public void testShouldContainsSgroupWhenCopied()
  {
      Molecule molecule = Molecules.createBenzene();
      Substructure substructure = molecule.addSubstructure();
      Bond bond=molecule.getBond(0);
      substructure.addAtom(bond.getSource());
      substructure.addAtom(bond.getTarget());
      substructure.addCrossingBond(bond);

      Molecule moleculeCopy=molecule.copy();
      assertEquals(1,moleculeCopy.countSubstructures());
      assertEquals(moleculeCopy.getBond(0),moleculeCopy.getSubstructure(0).getCrossingBond(0));

      moleculeCopy.copy(molecule);
      assertEquals(1,moleculeCopy.countSubstructures());
      assertEquals(moleculeCopy.getBond(0),moleculeCopy.getSubstructure(0).getCrossingBond(0));
  }


    //TODO: test removing nonexistant substructure (from same molecule) throws - i.e.,
    //      the substructure was already deleted once before
  public void testGetSubstructureWithInalidIdThrows()
  {
      Molecule molecule = Molecules.createEthylbenzeneWithSubstructure();
      try
      {
          molecule.getSubstructure(2);
          fail();
      }catch(IndexOutOfBoundsException e)
      {

      }
  }
  public void testRemoveSubstructureFromDifferentMoleculeThrows()
  {
      Molecule molecule = Molecules.createEthylbenzeneWithSubstructure();
      Molecule anotherMolecule = Molecules.createEthylbenzeneWithSubstructure();
      try
      {
         molecule.removeSubstructure(anotherMolecule.getSubstructure(0));
         fail();
      }catch(IllegalStateException e)
      {

      }
  }

  public void testRemovingNonExistantSubstructureThrows()
  {
      Molecule molecule = Molecules.createEthylbenzeneWithSubstructure();
      Substructure substructure = molecule.getSubstructure(0);
      molecule.removeSubstructure(substructure);
      try
      {
          molecule.removeSubstructure(substructure);
          fail();
      }catch(Exception e)
      {

      }
  }

  private class Listener implements ChangeListener
  {
      private int count = 0;

      public void stateChanged(ChangeEvent e) {
          count++;
      }
  }
}