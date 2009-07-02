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

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class BondTest extends TestCase
{
  public void testItShouldChangeItsPropertiesWhenBondOrderIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");
    Bond bond = molecule.connect(c1, c2, 1);

    assertEquals(1, bond.getType());
    assertEquals(1, bond.getSource().getValence());

    bond.setType(2);

    assertEquals(2, bond.getType());
    assertEquals(2, bond.getSource().getValence());
  }

  public void testItShouldChangeItsPropertiesWhenBondStereoIsSet()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");
    Bond bond = molecule.connect(c1, c2, 1);

    assertEquals(0, bond.getStereo());

    bond.setStereo(1);

    assertEquals(1, bond.getStereo());
  }

  public void testItShouldChangeItsPropertiesWhenReversed()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");
    Bond bond = molecule.connect(c1, c2, 1);

    assertEquals(c1, bond.getSource());

    bond.reverse();

    assertEquals(c2, bond.getSource());
  }

  public void testItShouldThrowWhenSettingAnInvalidType()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");
    Bond bond = molecule.connect(c1, c2, 1);

    try
    {
      bond.setType(0);
      fail();
    }
    catch (IllegalStateException ignore)
    {
    }
  }

  public void testItShouldThrowWhenSettingAnInvalidStereo()
  {
    Molecule molecule = new DefaultMolecule();
    Atom c1 = molecule.addAtom("C");
    Atom c2 = molecule.addAtom("C");
    Bond bond = molecule.connect(c1, c2, 1);
    int[] nonos = new int[]
    {
      -1, 2, 5, 7
    };

    for (int i = 0; i < nonos.length; i++)
    {
      try
      {
        bond.setStereo(nonos[i]);
        fail();
      }
      catch (IllegalStateException ignore)
      {
      }
    }
  }
}
