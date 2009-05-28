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
import com.metamolecular.mx.model.Sgroup;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.io.Molecules;

import junit.framework.TestCase;

/**
 * @author Duan Lian
 */
public class SgroupTest extends TestCase
{
  //TODO: test adding an atom from another molecule throws
  
  //TODO: test adding crossing bond from another molecule throws
  
  //TODO: test adding the same atom twice throws
  
  //TODO: test adding the same crossing bond twice throws
  
  //TODO: test removing nonexistant atom throws
  
  //TODO: test removing nonexitant crossing bond throws
  
  //TODO: test setting crossing vector for nonexistant bond throws
  
  //TODO: test deleting atom from molecule deletes atom from substructure as well
  
  //TODO: test deleting bond from molecule deletes crossing bond from substructure
  
  //TODO: test adding atom fires event
  
  //TODO: test removing atom fires event
  
  //TODO: test adding crossing bond fires event
  
  //TODO: test removing crossing bond fires event
  
  //TODO: test changing crossing vector fires event
  
  public void testAddSgroup()
  {
      Molecule molecule = Molecules.createBenzene();
      Sgroup sgroup = molecule.addSgroup();
      assertEquals(1, molecule.countSgroups());

      for (int i = 0; i < molecule.countAtoms(); i++) {
        sgroup.addAtom(molecule.getAtom(i));
      }
      for (int i = 0; i < molecule.countBonds(); i++) {
        sgroup.addBond(molecule.getBond(i));
      }
      
      assertEquals(6,sgroup.countAtoms());
      assertEquals(6,sgroup.countBonds());

      //Make a toluene molecule
      Atom methyl = molecule.addAtom("C");
      Bond bondOfMethyl = molecule.connect(methyl, molecule.getAtom(0), 1);

      assertEquals(false,sgroup.contains(methyl));
      assertEquals(false,sgroup.contains(bondOfMethyl));
  }

  public void testShouldFailIfAddSgroupOfAnotherMolecule(){
      Molecule benzene = Molecules.createBenzene();
      Sgroup sgroup = benzene.addSgroup();
      Molecule acetone = Molecules.createAcetone();
      try
      {
          acetone.addSgroup(sgroup);
          fail();
      }catch(RuntimeException e){

      }
  }

  public void testShouldContainsSgroupWhenCopied()
  {
      Molecule molecule = Molecules.createBenzene();
      Sgroup sgroup = molecule.addSgroup();
      Bond bond=molecule.getBond(0);
      sgroup.addAtom(bond.getSource());
      sgroup.addAtom(bond.getTarget());
      sgroup.addBond(bond);

      Molecule moleculeCopy=molecule.copy();
      assertEquals(1,moleculeCopy.countSgroups());
      assertEquals(moleculeCopy.getBond(0),moleculeCopy.getSgroup(0).getBond(0));

      moleculeCopy.copy(molecule);
      assertEquals(1,moleculeCopy.countSgroups());
      assertEquals(moleculeCopy.getBond(0),moleculeCopy.getSgroup(0).getBond(0));
  }

    


}