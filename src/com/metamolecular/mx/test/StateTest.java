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
import com.metamolecular.mx.map.Match;
import com.metamolecular.mx.map.DefaultState;
import com.metamolecular.mx.map.State;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 * @author Richard Apodaca
 */
public class StateTest extends TestCase
{
  private Molecule benzene;
  private Molecule naphthalene;
  private Molecule toluene;
  private Molecule phenol;
  private Molecule acetone;
  private Molecule methylPentane2;
  private Molecule hexane;

  @Override
  protected void setUp() throws Exception
  {
    benzene = Molecules.createBenzene();
    naphthalene = Molecules.createNaphthalene();
    toluene = Molecules.createToluene();
    phenol = Molecules.createPhenol();
    acetone = Molecules.createAcetone();
    methylPentane2 = create2MethylPentane();
    hexane = Molecules.createHexane();
  }

  public void testItShouldFindAllMatchCandidatesInTheRootState()
  {
    State state = new DefaultState(benzene, benzene);
    List<Match> candidates = new ArrayList<Match>();

    while (state.hasNextCandidate())
    {
      candidates.add(state.nextCandidate());
    }

    assertEquals(benzene.countAtoms() * benzene.countAtoms(), candidates.size());
  }

  public void testItShoudFindAllMatchCandidatesInThePrimaryState()
  {
    State state = new DefaultState(benzene, benzene);
    Match match = new Match(benzene.getAtom(0), benzene.getAtom(0));
    State newState = state.nextState(match);
    List<Match> candidates = new ArrayList<Match>();

    while (newState.hasNextCandidate())
    {
      candidates.add(newState.nextCandidate());
    }

    assertEquals(4, candidates.size());
  }

  public void testItShouldFindAllMatchCandidatesInTheSecondaryState()
  {
    State state0 = new DefaultState(benzene, benzene);
    Match match0 = new Match(benzene.getAtom(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzene.getAtom(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    List<Match> candidates = new ArrayList<Match>();

    while (state2.hasNextCandidate())
    {
      candidates.add(state2.nextCandidate());
    }

    assertEquals(1, candidates.size());
  }

  public void testItShouldMapAllAtomsInTheSecondaryState()
  {
    State state0 = new DefaultState(benzene, benzene);
    Match match0 = new Match(benzene.getAtom(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzene.getAtom(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);

    Map<Atom, Atom> map = state2.getMap();

    assertEquals(2, map.size());
    assertEquals(benzene.getAtom(0), map.get(benzene.getAtom(0)));
    assertEquals(benzene.getAtom(1), map.get(benzene.getAtom(1)));
  }

  public void testItShouldFindAllMatchCandidatesFromTheTeriaryState()
  {
    State state0 = new DefaultState(benzene, benzene);
    Match match0 = new Match(benzene.getAtom(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzene.getAtom(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzene.getAtom(2), benzene.getAtom(2));
    State state3 = state2.nextState(match2);
    List<Match> candidates = new ArrayList<Match>();

    while (state3.hasNextCandidate())
    {
      candidates.add(state3.nextCandidate());
    }

    assertEquals(1, candidates.size());
  }

  public void testItShouldMapAllAtomsInTheTertiaryState()
  {
    State state0 = new DefaultState(benzene, benzene);
    Match match0 = new Match(benzene.getAtom(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzene.getAtom(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzene.getAtom(2), benzene.getAtom(2));
    State state3 = state2.nextState(match2);
    Map<Atom, Atom> map = state3.getMap();

    assertEquals(3, map.size());
    assertEquals(benzene.getAtom(0), map.get(benzene.getAtom(0)));
    assertEquals(benzene.getAtom(1), map.get(benzene.getAtom(1)));
    assertEquals(benzene.getAtom(2), map.get(benzene.getAtom(2)));
  }

  public void testItShouldReachGoalWhenAllAtomsAreMapped()
  {
    State state0 = new DefaultState(benzene, benzene);
    Match match0 = new Match(benzene.getAtom(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzene.getAtom(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzene.getAtom(2), benzene.getAtom(2));
    State state3 = state2.nextState(match2);
    Match match3 = new Match(benzene.getAtom(3), benzene.getAtom(3));
    State state4 = state3.nextState(match3);
    Match match4 = new Match(benzene.getAtom(4), benzene.getAtom(4));
    State state5 = state4.nextState(match4);

    assertFalse(state5.isGoal());

    Match match5 = new Match(benzene.getAtom(5), benzene.getAtom(5));
    State state6 = state5.nextState(match5);

    assertTrue(state6.isGoal());
  }

  public void testItShouldDetermineFeasibilityByConnectivity()
  {
    State state = new DefaultState(benzene, toluene);

    for (int i = 0; i < 6; i++)
    {
      Match pass = new Match(benzene.getAtom(0), toluene.getAtom(i));
      Match fail = new Match(benzene.getAtom(i), toluene.getAtom(6));

      assertTrue(state.isMatchFeasible(pass));
      assertFalse(state.isMatchFeasible(fail));
    }

    Match fail = new Match(benzene.getAtom(0), toluene.getAtom(6));

    assertFalse(state.isMatchFeasible(fail));
  }

  public void testItShouldDetermineFeasibilityByAtomSymbol()
  {
    State state = new DefaultState(toluene, phenol);
    Match fail = new Match(toluene.getAtom(6), phenol.getAtom(6));

    assertFalse(state.isMatchFeasible(fail));
  }

  public void testItShouldBeDeadIfQueryHasMoreAtoms()
  {
    State state = new DefaultState(toluene, benzene);

    assertTrue(state.isDead());
  }

  public void testItShouldHaveANextCandidateInTheSecondaryState()
  {
    State state = new DefaultState(benzene, benzene);
    Match match = new Match(benzene.getAtom(0), benzene.getAtom(0));

    State nextState = state.nextState(match);

    assertTrue(nextState.hasNextCandidate());
  }

  public void testItShouldNotMatchACrossRingClosure()
  {
    State state0 = new DefaultState(benzene, naphthalene);
    Match match0 = new Match(benzene.getAtom(0), naphthalene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzene.getAtom(1), naphthalene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzene.getAtom(2), naphthalene.getAtom(2));
    State state3 = state2.nextState(match2);
    Match match3 = new Match(benzene.getAtom(3), naphthalene.getAtom(3));
    State state4 = state3.nextState(match3);
    Match match4 = new Match(benzene.getAtom(4), naphthalene.getAtom(4));
    State state5 = state4.nextState(match4);
    Match match5Fail = new Match(benzene.getAtom(5), naphthalene.getAtom(6));

    assertFalse(state5.isMatchFeasible(match5Fail));

    Match match5Pass = new Match(benzene.getAtom(5), naphthalene.getAtom(5));

    assertTrue(state5.isMatchFeasible(match5Pass));
  }

  public void testItShouldStoreMappedAtomsForItsChildren()
  {
    State state0 = new DefaultState(hexane, hexane);
    Match match0 = new Match(hexane.getAtom(0), hexane.getAtom(0));
    State state1 = state0.nextState(match0);

    assertEquals(1, state0.getMap().size());
    assertEquals(1, state1.getMap().size());

    Match match1 = new Match(hexane.getAtom(1), hexane.getAtom(1));
    State state2 = state1.nextState(match1);

    assertEquals(2, state0.getMap().size());
    assertEquals(2, state1.getMap().size());
    assertEquals(2, state2.getMap().size());

    Match match2 = new Match(hexane.getAtom(2), hexane.getAtom(2));
    State state3 = state2.nextState(match2);

    assertEquals(3, state0.getMap().size());
    assertEquals(3, state1.getMap().size());
    assertEquals(3, state2.getMap().size());
    assertEquals(3, state3.getMap().size());
  }

  public void testItShouldClearAtomMappingsWhenAChildIsBacktracked()
  {
    Molecule m = methylPentane2;
    State state0 = new DefaultState(m, m);
    Match match0 = new Match(m.getAtom(0), m.getAtom(0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(m.getAtom(1), m.getAtom(1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(m.getAtom(2), m.getAtom(2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(m.getAtom(3), m.getAtom(3));

    State state4 = state3.nextState(match3);

    assertEquals(4, state0.getMap().size());

    state2.backTrack();

    assertEquals(1, state0.getMap().size());
    assertTrue(state0.getMap().get(m.getAtom(0)).equals(m.getAtom(0)));
  }

  public void testItShouldNotRemoveAnyAtomMappingsWhenHeadOfChildIsFullyMapped()
  {
    Molecule m = methylPentane2;
    State state0 = new DefaultState(m, m);
    Match match0 = new Match(m.getAtom(0), m.getAtom(0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(m.getAtom(1), m.getAtom(1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(m.getAtom(2), m.getAtom(2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(m.getAtom(3), m.getAtom(3));

    State state4 = state3.nextState(match3);
    Match match4 = new Match(m.getAtom(4), m.getAtom(4));

    State state5 = state4.nextState(match4);

    assertEquals(5, state0.getMap().size());

    state2.backTrack();

    assertEquals(5, state0.getMap().size());
  }

  public void testItShouldNotMatchACandidateIfQueryAtomHasAlreadyBeenMapped()
  {
    State state0 = new DefaultState(acetone, acetone);
    Match match0 = new Match(acetone.getAtom(3), acetone.getAtom(3));

    assertTrue(state0.isMatchFeasible(match0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(acetone.getAtom(1), acetone.getAtom(1));

    assertTrue(state1.isMatchFeasible(match1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(acetone.getAtom(2), acetone.getAtom(2));

    assertTrue(state2.isMatchFeasible(match2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(acetone.getAtom(2), acetone.getAtom(0));

    assertEquals(3, state3.getMap().size());
    assertFalse(state3.isMatchFeasible(match3));
  }

  public void testItShouldNotMatchACandidateIfTargetAtomHasAlreadyBeenMapped()
  {
    State state0 = new DefaultState(acetone, acetone);
    Match match0 = new Match(acetone.getAtom(3), acetone.getAtom(3));

    assertTrue(state0.isMatchFeasible(match0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(acetone.getAtom(1), acetone.getAtom(1));

    assertTrue(state1.isMatchFeasible(match1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(acetone.getAtom(2), acetone.getAtom(2));

    assertTrue(state2.isMatchFeasible(match2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(acetone.getAtom(0), acetone.getAtom(2));

    assertEquals(3, state3.getMap().size());
    assertFalse(state3.isMatchFeasible(match3));
  }

  public void testItShouldBeAbleToMapADeepSymmetricallyBranchingMolecule()
  {
    Molecule m = methylPentane2;

    State state0 = new DefaultState(m, m);
    Match match0 = new Match(m.getAtom(0), m.getAtom(0));

    assertTrue(state0.isMatchFeasible(match0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(m.getAtom(1), m.getAtom(1));

    assertTrue(state1.isMatchFeasible(match1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(m.getAtom(2), m.getAtom(2));

    assertTrue(state2.isMatchFeasible(match2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(m.getAtom(3), m.getAtom(3));

    assertTrue(state3.isMatchFeasible(match3));

    State state4 = state3.nextState(match3);
    Match match4 = new Match(m.getAtom(4), m.getAtom(4));

    assertFalse(state4.isMatchFeasible(match4));
    assertTrue(state2.isMatchFeasible(match4));
  }

  public void testItShouldBeAbleToMapAShallowSymmetricallyBranchingMolecule()
  {
    State state0 = new DefaultState(acetone, acetone);
    Match match0 = new Match(acetone.getAtom(0), acetone.getAtom(0));

    assertTrue(state0.isMatchFeasible(match0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(acetone.getAtom(1), acetone.getAtom(1));

    assertTrue(state1.isMatchFeasible(match1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(acetone.getAtom(2), acetone.getAtom(2));

    assertTrue(state2.isMatchFeasible(match2));

    Match failMatch2 = new Match(acetone.getAtom(2), acetone.getAtom(3));

    assertFalse(state2.isMatchFeasible(failMatch2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(acetone.getAtom(3), acetone.getAtom(3));

    assertTrue(state2.isMatchFeasible(match3));

    State state4 = state2.nextState(match3);

    assertTrue(state4.isGoal());
  }

  private Molecule create2MethylPentane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c2, c3, 1);
    result.connect(c1, c4, 1);
    result.connect(c4, c5, 1);

    return result;
  }
}
