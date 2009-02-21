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

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.path.DefaultStep;
import com.metamolecular.mx.path.Step;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class StepTest extends TestCase
{
  private Molecule benzene = null;
  private Molecule methane = null;
  private Molecule hexane = null;

  @Override
  protected void setUp() throws Exception
  {
    benzene = Molecules.createBenzene();
    methane = Molecules.createMethane();
    hexane = Molecules.createHexane();
  }

  public void testItShouldHaveANextAtomWhenRootHasNeighbors()
  {
    Step state = new DefaultStep(benzene.getAtom(0));

    assertTrue(state.hasNextBranch());
  }

  public void testItShouldNotHaveANextAtomWhenRootHasNoNeighbors()
  {
    Step state = new DefaultStep(methane.getAtom(0));

    assertFalse(state.hasNextBranch());
  }

  public void testItShouldGiveANextAtomThatIsANeighborOfRoot()
  {
    Step state = new DefaultStep(benzene.getAtom(0));
    Atom next = state.nextBranch();

    assertTrue(benzene.getAtom(0).isConnectedTo(next));
  }

  public void testItShouldAdvanceToNeigbhorOfRoot()
  {
    Step state = new DefaultStep(benzene.getAtom(0));
    Atom next = state.nextBranch();

    assertTrue(state.isBranchFeasible(next));
  }

  public void testItShouldGiveNextStepForNeighborAtom()
  {
    Step state0 = new DefaultStep(benzene.getAtom(0));
    Step state1 = state0.nextStep(benzene.getAtom(1));

    assertNotNull(state1);
  }

  public void testItShouldNotBacktrackAsSecondaryState()
  {
    Step state0 = new DefaultStep(benzene.getAtom(0));
    Step state1 = state0.nextStep(benzene.getAtom(1));

    assertTrue(state1.hasNextBranch());

    Atom next = state1.nextBranch();

    assertNotSame(next, benzene.getAtom(0));
    assertFalse(state1.hasNextBranch());
  }

  public void testItShouldStopAtARingClosure()
  {
    Step state0 = new DefaultStep(benzene.getAtom(0));
    Step state1 = state0.nextStep(benzene.getAtom(1));
    Step state2 = state1.nextStep(benzene.getAtom(2));
    Step state3 = state2.nextStep(benzene.getAtom(3));
    Step state4 = state3.nextStep(benzene.getAtom(4));
    Step state5 = state4.nextStep(benzene.getAtom(5));

    assertFalse(state5.hasNextBranch());
  }

  public void testItShouldNotHaveANextAtomAfterAChildVisitsLastAtom()
  {
    Step ancestor = new DefaultStep(benzene.getAtom(0));
    Step child1 = ancestor.nextStep(benzene.getAtom(1));
    Step child2 = child1.nextStep(benzene.getAtom(2));
    Step child3 = child2.nextStep(benzene.getAtom(3));
    Step child4 = child3.nextStep(benzene.getAtom(4));
    Step child5 = child4.nextStep(benzene.getAtom(5));

    assertFalse(child5.hasNextBranch());
    assertFalse(ancestor.hasNextBranch());
  }

  public void testItShouldKnowAllAtomsSteppedByChildren()
  {
    Step state0 = new DefaultStep(benzene.getAtom(0));
    Step state1 = state0.nextStep(benzene.getAtom(1));
    Step state2 = state1.nextStep(benzene.getAtom(2));
    Step state3 = state2.nextStep(benzene.getAtom(3));
    Step state4 = state3.nextStep(benzene.getAtom(4));
    Step state5 = state4.nextStep(benzene.getAtom(5));

    assertEquals(6, state0.getSteppedAtoms().size());
  }

  public void testItShouldStoreAllPreviousAtomsInPathInOrder()
  {
    Step state0 = new DefaultStep(benzene.getAtom(0));
    Step state1 = state0.nextStep(benzene.getAtom(1));
    Step state2 = state1.nextStep(benzene.getAtom(2));
    Step state3 = state2.nextStep(benzene.getAtom(3));
    Step state4 = state3.nextStep(benzene.getAtom(4));
    Step state5 = state4.nextStep(benzene.getAtom(5));

    List<Atom> path = state5.getPath();

    assertEquals(6, path.size());

    for (int i = 0; i < path.size(); i++)
    {
      assertEquals(benzene.getAtom(i), path.get(i));
    }
  }

  public void testItShouldClearAtomsSteppedByChildrenWhenBacktracked()
  {
    Step state0 = new DefaultStep(benzene.getAtom(0));
    Step state1 = state0.nextStep(benzene.getAtom(1));
    Step state2 = state1.nextStep(benzene.getAtom(2));
    Step state3 = state2.nextStep(benzene.getAtom(3));
    Step state4 = state3.nextStep(benzene.getAtom(4));
    Step state5 = state4.nextStep(benzene.getAtom(5));
    
    assertEquals(6, state4.getSteppedAtoms().size());
    
    state4.backTrack();
    
    assertEquals(5, state4.getSteppedAtoms().size());
    assertEquals(5, state3.getSteppedAtoms().size());
    
    state3.backTrack();
    
    assertEquals(4, state3.getSteppedAtoms().size());
    assertEquals(4, state2.getSteppedAtoms().size());
    
    state2.backTrack();
    
    assertEquals(3, state2.getSteppedAtoms().size());
  }
}
