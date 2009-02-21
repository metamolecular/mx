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

import com.metamolecular.mx.io.daylight.SMILESBuilder;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class SMILESBuilderTest extends TestCase
{

  public void testItShouldConnectTwoAtoms()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C");
    builder.addHead("C");

    assertEquals(2, result.countAtoms());
    assertEquals(1, result.countBonds());
    assertEquals(1, result.getBond(result.getAtom(0), result.getAtom(1)).getType());
  }

  public void testItShouldConnectTwoAtomsThroughADoubleBond()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C");
    builder.addBond(2);
    builder.addHead("C");

    assertEquals(2, result.countAtoms());
    assertEquals(1, result.countBonds());
    assertEquals(2, result.getBond(result.getAtom(0), result.getAtom(1)).getType());
  }

  public void testItShouldBranchAtSecondaryAtom()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C");
    builder.addHead("C");
    builder.openBranch();
    builder.addHead("C");
    builder.closeBranch();
    builder.addHead("C");

    assertEquals(4, result.countAtoms());
    assertEquals(3, result.countBonds());
    assertEquals(3, result.getAtom(1).countNeighbors());
  }

  public void testItShouldBranchAtTertiaryAtom()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C");
    builder.addHead("C");
    builder.openBranch();
    builder.addHead("C");
    builder.closeBranch();
    builder.openBranch();
    builder.addHead("C");
    builder.closeBranch();
    builder.addHead("C");

    assertEquals(5, result.countAtoms());
    assertEquals(4, result.countBonds());
    assertEquals(4, result.getAtom(1).countNeighbors());
  }

  public void testItShouldCreateANestedBranch()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C"); //0
    builder.addHead("C"); //1
    builder.openBranch();
    builder.addHead("C"); //2
    builder.openBranch();
    builder.addHead("C"); //3
    builder.closeBranch();
    builder.addHead("C"); //4
    builder.closeBranch();
    builder.addHead("C"); //5

    assertEquals(6, result.countAtoms());
    assertEquals(5, result.countBonds());
    assertEquals(3, result.getAtom(1).countNeighbors());
    assertEquals(3, result.getAtom(2).countNeighbors());
    assertEquals(1, result.getAtom(5).countNeighbors());

    try
    {
      builder.closeBranch();

      fail();
    } catch (IllegalStateException ignore)
    {
    }
  }

  public void testItShouldThrowWhenClosingANonexistantBranch()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    try
    {
      builder.closeBranch();

      fail();
    } catch (IllegalStateException ignore)
    {
    }
  }

  public void testItShouldCloseACycle()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C");
    builder.ring("1");
    builder.addHead("C");
    builder.addHead("C");
    builder.ring("1");

    assertEquals(3, result.countAtoms());
    assertEquals(3, result.countBonds());
  }

  public void testItShouldReuseARingLabel()
  {
    Molecule result = new DefaultMolecule();
    SMILESBuilder builder = new SMILESBuilder(result);

    builder.addHead("C"); //0
    builder.ring("1");
    builder.addHead("C"); //1
    builder.addHead("C"); //2
    builder.ring("1");
    builder.addHead("C"); //3
    builder.ring("1");
    builder.addHead("C"); //4
    builder.addHead("C"); //5
    builder.ring("1");

    assertEquals(6, result.countAtoms());
    assertEquals(7, result.countBonds());
    assertEquals(3, result.getAtom(2).countNeighbors());
    assertEquals(3, result.getAtom(3).countNeighbors());
  }
}
