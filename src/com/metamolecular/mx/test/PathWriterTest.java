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

import com.metamolecular.mx.fingerprint.PathWriter;
import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathWriterTest extends TestCase
{
  private Set<String> paths;
  private PathWriter writer;
  private List<Atom> path;

  @Override
  protected void setUp() throws Exception
  {
    paths = new HashSet();
    path = new ArrayList();
    writer = new PathWriter();
  }

  public void testItWritesLinearChain()
  {
    hexane();
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      "C", "CC", "CCC", "CCCC", "CCCCC", "CCCCCC")), paths);
  }

  public void testItWritesCycle()
  {
    cyclohexane();
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      "C", "CC", "CCC", "CCCC", "CCCCC", "CCCCCC", "CCCCCC-6")), paths);
  }

  public void testItWritesDoublyClosedCycle()
  {
    bicyclo220hexane();
    doWrite();
    assertEquals(new HashSet(Arrays.asList(
      "C", "CC", "CCC", "CCCC", "CCCCC", "CCCCCC", "CCCCCC-4", "CCCCCC-6")), paths);
  }

  public void testItWritesTriplyClosedCycle()
  {
    tricyclo2002hexane();
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      "C", "CC", "CCC", "CCCC", "CCCCC", "CCCCCC",
      "CCCCCC-3", "CCCCCC-4", "CCCCCC-6")), paths);
  }

  public void testItWritesSP2()
  {
    benzene();
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      "C%", "C%C%", "C%C%C%", "C%C%C%C%", "C%C%C%C%C%",
      "C%C%C%C%C%C%", "C%C%C%C%C%C%-6")), paths);
  }

  public void testItWritesSP3()
  {
    ethyne();
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      "C%", "C%C%")), paths);
  }

  private void doWrite()
  {
    writer.write(path, paths);
  }

  private void ethyne()
  {
    Molecule molecule = new DefaultMolecule();
    molecule.connect(molecule.addAtom("C"), molecule.addAtom("C"), 3);
    moleculeToPath(molecule);
  }

  private void benzene()
  {
    moleculeToPath(Molecules.createBenzene());
  }

  private void hexane()
  {
    moleculeToPath(Molecules.createHexane());
  }

  private void cyclohexane()
  {
    moleculeToPath(Molecules.createCyclohexane());
  }

  private void bicyclo220hexane()
  {
    Molecule molecule = Molecules.createCyclohexane();

    molecule.connect(molecule.getAtom(2), molecule.getAtom(5), 1);
    moleculeToPath(molecule);
  }

  private void tricyclo2002hexane()
  {
    Molecule molecule = Molecules.createCyclohexane();

    molecule.connect(molecule.getAtom(2), molecule.getAtom(5), 1);
    molecule.connect(molecule.getAtom(3), molecule.getAtom(5), 1);
    moleculeToPath(molecule);
  }

  private void moleculeToPath(Molecule molecule)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      path.add(molecule.getAtom(i));
    }
  }
}
