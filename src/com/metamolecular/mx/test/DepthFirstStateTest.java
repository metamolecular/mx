/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
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
import com.metamolecular.mx.walk.DepthFirstState;
import com.metamolecular.mx.walk.State;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class DepthFirstStateTest extends TestCase
{
  private Molecule benzene = null;
  private Molecule methane = null;

  @Override
  protected void setUp() throws Exception
  {
    benzene = Molecules.createBenzene();
    methane = Molecules.createMethane();
  }

  public void testItShouldHaveANextAtomWhenRootHasNeighbors()
  {
    State state = new DepthFirstState(benzene.getAtom(0));

    assertTrue(state.hasNextBranch());
  }

  public void testItShouldNotHaveANextAtomWhenRootHasNoNeighbors()
  {
    State state = new DepthFirstState(methane.getAtom(0));

    assertFalse(state.hasNextBranch());
  }

  public void testItShouldGiveANextAtomThatIsANeighborOfRoot()
  {
    State state = new DepthFirstState(benzene.getAtom(0));
    Atom next = state.nextBranch();

    assertTrue(benzene.getAtom(0).isConnectedTo(next));
  }

  public void testItShouldAdvanceToNeigbhorOfRoot()
  {
    State state = new DepthFirstState(benzene.getAtom(0));
    Atom next = state.nextBranch();

    assertTrue(state.isValidBranch(next));
  }

  public void testItShouldGiveNextStateForNeighborAtom()
  {
    State state0 = new DepthFirstState(benzene.getAtom(0));
    State state1 = state0.nextState(benzene.getAtom(1));

    assertNotNull(state1);
  }

  public void testItShouldNotBacktrackAsSecondaryState()
  {
    State state0 = new DepthFirstState(benzene.getAtom(0));
    State state1 = state0.nextState(benzene.getAtom(1));
    
    assertTrue(state1.hasNextBranch());
    
    Atom next = state1.nextBranch();
    
    assertNotSame(next, benzene.getAtom(0));
    assertFalse(state1.hasNextBranch());
  }

  public void testItShouldStopAtARingClosure()
  {
    State state0 = new DepthFirstState(benzene.getAtom(0));
    State state1 = state0.nextState(benzene.getAtom(1));
    State state2 = state1.nextState(benzene.getAtom(2));
    State state3 = state2.nextState(benzene.getAtom(3));
    State state4 = state3.nextState(benzene.getAtom(4));
    State state5 = state4.nextState(benzene.getAtom(5));
    
    assertFalse(state5.hasNextBranch());
  }
}
