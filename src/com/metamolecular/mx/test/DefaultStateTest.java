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
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.map.DefaultState;
import com.metamolecular.mx.map.Match;
import com.metamolecular.mx.query.Node;
import com.metamolecular.mx.query.Query;
import com.metamolecular.mx.map.State;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.query.AtomMatcher;
import com.metamolecular.mx.query.BondMatcher;
import com.metamolecular.mx.query.DefaultQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultStateTest extends TestCase
{
  private Molecule benzene;
  private Query benzeneQuery;
  private Molecule toluene;
  private Query tolueneQuery;
  private Molecule naphthalene;
  private Molecule hexane;
  private Query hexaneQuery;
  private Molecule acetone;
  private Query acetoneQuery;

  public DefaultStateTest()
  {
    benzene = Molecules.createBenzene();
    toluene = Molecules.createToluene();
    naphthalene = Molecules.createNaphthalene();
    hexane = Molecules.createHexane();
    acetone = Molecules.createAcetone();
    acetoneQuery = createQuery(acetone);
    benzeneQuery = createQuery(benzene);
    tolueneQuery = createQuery(toluene);
    hexaneQuery = createQuery(hexane);
  }

  @Override
  protected void setUp() throws Exception
  {
  }

  public void testIsMatchFeasibleCallsAtomMatchInPrimaryState()
  {
    State primary = new DefaultState(hexaneQuery, hexane);
    AtomMatcher matcher = hexaneQuery.getNode(0).getAtomMatcher();
    Match match = new Match(hexaneQuery.getNode(0), hexane.getAtom(0));
    primary.isMatchFeasible(match);

    verify(matcher).matches(hexane.getAtom(0));
  }

  public void testIsMatchFeasibleCallsBondMatchWhenAtomsMatchInSecondaryState()
  {
    State primary = new DefaultState(hexaneQuery, hexane);
    Match match = new Match(hexaneQuery.getNode(0), hexane.getAtom(0));
    State secondary = primary.nextState(match);
    BondMatcher matcher = hexaneQuery.getEdge(0).getBondMatcher();
    match = new Match(hexaneQuery.getNode(1), hexane.getAtom(1));

    secondary.isMatchFeasible(match);

    verify(matcher).matches(hexane.getBond(0));
  }

  public void testItFindsAllMatchCandidatesInTheRootState()
  {
    State state = new DefaultState(benzeneQuery, benzene);
    int count = 0;

    while (state.hasNextCandidate())
    {
      state.nextCandidate();

      count++;
    }

    assertEquals(benzene.countAtoms() * benzene.countAtoms(), count);
  }

  public void testItFindsAllMatchCandidatesInThePrimaryState()
  {
    State state = new DefaultState(benzeneQuery, benzene);
    Match match = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));
    State newState = state.nextState(match);
    List<Match> candidates = new ArrayList<Match>();

    while (newState.hasNextCandidate())
    {
      candidates.add(newState.nextCandidate());
    }

    assertEquals(4, candidates.size());
  }

  public void testItFindsAllMatchCandidatesInTheSecondaryState()
  {
    State state0 = new DefaultState(benzeneQuery, benzene);
    Match match0 = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzeneQuery.getNode(1), benzene.getAtom(1));
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
    State state0 = new DefaultState(benzeneQuery, benzene);
    Match match0 = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzeneQuery.getNode(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);

    Map<Node, Atom> map = state2.getMap();

    assertEquals(2, map.size());
    assertEquals(benzene.getAtom(0), map.get(benzeneQuery.getNode(0)));
    assertEquals(benzene.getAtom(1), map.get(benzeneQuery.getNode(1)));
  }

  public void testItShouldFindAllMatchCandidatesFromTheTeriaryState()
  {
    State state0 = new DefaultState(benzeneQuery, benzene);
    Match match0 = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzeneQuery.getNode(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzeneQuery.getNode(2), benzene.getAtom(2));
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
    State state0 = new DefaultState(benzeneQuery, benzene);
    Match match0 = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzeneQuery.getNode(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzeneQuery.getNode(2), benzene.getAtom(2));
    State state3 = state2.nextState(match2);
    Map<Node, Atom> map = state3.getMap();

    assertEquals(3, map.size());
    assertEquals(benzene.getAtom(0), map.get(benzeneQuery.getNode(0)));
    assertEquals(benzene.getAtom(1), map.get(benzeneQuery.getNode(1)));
    assertEquals(benzene.getAtom(2), map.get(benzeneQuery.getNode(2)));
  }

  public void testItShouldReachGoalWhenAllAtomsAreMapped()
  {
    State state0 = new DefaultState(benzeneQuery, benzene);
    Match match0 = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzeneQuery.getNode(1), benzene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzeneQuery.getNode(2), benzene.getAtom(2));
    State state3 = state2.nextState(match2);
    Match match3 = new Match(benzeneQuery.getNode(3), benzene.getAtom(3));
    State state4 = state3.nextState(match3);
    Match match4 = new Match(benzeneQuery.getNode(4), benzene.getAtom(4));
    State state5 = state4.nextState(match4);

    assertFalse(state5.isGoal());

    Match match5 = new Match(benzeneQuery.getNode(5), benzene.getAtom(5));
    State state6 = state5.nextState(match5);

    assertTrue(state6.isGoal());
  }

  public void testItShouldDetermineFeasibilityByConnectivity()
  {
    State state = new DefaultState(benzeneQuery, toluene);

    for (int i = 0; i < 6; i++)
    {
      Match pass = new Match(benzeneQuery.getNode(0), toluene.getAtom(i));
      Match fail = new Match(benzeneQuery.getNode(i), toluene.getAtom(6));

      assertTrue(state.isMatchFeasible(pass));
      assertFalse(state.isMatchFeasible(fail));
    }

    Match fail = new Match(benzeneQuery.getNode(0), toluene.getAtom(6));

    assertFalse(state.isMatchFeasible(fail));
  }

  public void testItRejectsSecondaryAtomAsFeasibleMatchForPrimaryAtom()
  {
    Query query = createQuery(hexane);
    State state = new DefaultState(query, hexane);
    Match match = new Match(query.getNode(1), hexane.getAtom(0));

    assertFalse(state.isMatchFeasible(match));
  }

  public void testItIsDeadIfQueryHasMoreAtomsThanTarget()
  {
    State state = new DefaultState(tolueneQuery, benzene);

    assertTrue(state.isDead());
  }

  public void testItShouldHaveANextCandidateInTheSecondaryState()
  {
    State state = new DefaultState(benzeneQuery, benzene);
    Match match = new Match(benzeneQuery.getNode(0), benzene.getAtom(0));

    State nextState = state.nextState(match);

    assertTrue(nextState.hasNextCandidate());
  }

  public void testItShouldNotMatchACrossRingClosure()
  {
    State state0 = new DefaultState(benzeneQuery, naphthalene);
    Match match0 = new Match(benzeneQuery.getNode(0), naphthalene.getAtom(0));
    State state1 = state0.nextState(match0);
    Match match1 = new Match(benzeneQuery.getNode(1), naphthalene.getAtom(1));
    State state2 = state1.nextState(match1);
    Match match2 = new Match(benzeneQuery.getNode(2), naphthalene.getAtom(2));
    State state3 = state2.nextState(match2);
    Match match3 = new Match(benzeneQuery.getNode(3), naphthalene.getAtom(3));
    State state4 = state3.nextState(match3);
    Match match4 = new Match(benzeneQuery.getNode(4), naphthalene.getAtom(4));
    State state5 = state4.nextState(match4);
    Match match5Fail = new Match(benzeneQuery.getNode(5), naphthalene.getAtom(6));

    assertFalse(state5.isMatchFeasible(match5Fail));

    Match match5Pass = new Match(benzeneQuery.getNode(5), naphthalene.getAtom(5));

    assertTrue(state5.isMatchFeasible(match5Pass));
  }

  public void testItShouldStoreMappedAtomsForItsChildren()
  {
    State state0 = new DefaultState(hexaneQuery, hexane);
    Match match0 = new Match(hexaneQuery.getNode(0), hexane.getAtom(0));
    State state1 = state0.nextState(match0);

    assertEquals(1, state0.getMap().size());
    assertEquals(1, state1.getMap().size());

    Match match1 = new Match(hexaneQuery.getNode(1), hexane.getAtom(1));
    State state2 = state1.nextState(match1);

    assertEquals(2, state0.getMap().size());
    assertEquals(2, state1.getMap().size());
    assertEquals(2, state2.getMap().size());

    Match match2 = new Match(hexaneQuery.getNode(2), hexane.getAtom(2));
    State state3 = state2.nextState(match2);

    assertEquals(3, state0.getMap().size());
    assertEquals(3, state1.getMap().size());
    assertEquals(3, state2.getMap().size());
    assertEquals(3, state3.getMap().size());
  }

  public void testItShouldClearAtomMappingsWhenAChildIsBacktracked()
  {
    Molecule m = create2MethylPentane();
    Query mQuery = createQuery(m);
    State state0 = new DefaultState(mQuery, m);
    Match match0 = new Match(mQuery.getNode(0), m.getAtom(0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(mQuery.getNode(1), m.getAtom(1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(mQuery.getNode(2), m.getAtom(2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(mQuery.getNode(3), m.getAtom(3));

    State state4 = state3.nextState(match3);

    assertEquals(4, state0.getMap().size());

    state2.backTrack();

    assertEquals(1, state0.getMap().size());
    assertTrue(state0.getMap().get(mQuery.getNode(0)).equals(m.getAtom(0)));
  }

  public void testItShouldNotRemoveAnyAtomMappingsWhenHeadOfChildIsFullyMapped()
  {
    Molecule m = create2MethylPentane();
    Query mQuery = createQuery(m);
    State state0 = new DefaultState(mQuery, m);
    Match match0 = new Match(mQuery.getNode(0), m.getAtom(0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(mQuery.getNode(1), m.getAtom(1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(mQuery.getNode(2), m.getAtom(2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(mQuery.getNode(3), m.getAtom(3));

    State state4 = state3.nextState(match3);
    Match match4 = new Match(mQuery.getNode(4), m.getAtom(4));

    State state5 = state4.nextState(match4);

    assertEquals(5, state0.getMap().size());

    state2.backTrack();

    assertEquals(5, state0.getMap().size());
  }

  public void testItDoesNotMatchACandidateIfQueryAtomHasAlreadyBeenMapped()
  {
    State state0 = new DefaultState(acetoneQuery, acetone);
    Match match0 = new Match(acetoneQuery.getNode(3), acetone.getAtom(3));

    assertTrue(state0.isMatchFeasible(match0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(acetoneQuery.getNode(1), acetone.getAtom(1));

    assertTrue(state1.isMatchFeasible(match1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(acetoneQuery.getNode(2), acetone.getAtom(2));

    assertTrue(state2.isMatchFeasible(match2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(acetoneQuery.getNode(2), acetone.getAtom(0));

    assertEquals(3, state3.getMap().size());
    assertFalse(state3.isMatchFeasible(match3));
  }

  public void testItDoesNotMatchACandidateIfTargetAtomHasAlreadyBeenMapped()
  {
    State state0 = new DefaultState(acetoneQuery, acetone);
    Match match0 = new Match(acetoneQuery.getNode(3), acetone.getAtom(3));

    assertTrue(state0.isMatchFeasible(match0));

    State state1 = state0.nextState(match0);
    Match match1 = new Match(acetoneQuery.getNode(1), acetone.getAtom(1));

    assertTrue(state1.isMatchFeasible(match1));

    State state2 = state1.nextState(match1);
    Match match2 = new Match(acetoneQuery.getNode(2), acetone.getAtom(2));

    assertTrue(state2.isMatchFeasible(match2));

    State state3 = state2.nextState(match2);
    Match match3 = new Match(acetoneQuery.getNode(0), acetone.getAtom(2));

    assertEquals(3, state3.getMap().size());
    assertFalse(state3.isMatchFeasible(match3));
  }

  public void testItMapsADeepSymmetricallyBranchingMolecule()
  {
    Molecule m = create2MethylPentane();
    Query mQuery = createQuery(m);
    State state = new DefaultState(mQuery, m);
    Match match = new Match(mQuery.getNode(0), m.getAtom(0));
    
    for (int i = 1; i < 5; i++)
    {
      assertTrue(state.isMatchFeasible(match));
      
      state = state.nextState(match);
      match = new Match(mQuery.getNode(i), m.getAtom(i));
    }
    
    assertFalse(state.hasNextCandidate());
  }

  private AtomMatcher mockAtomMatcher()
  {
    AtomMatcher result = mock(AtomMatcher.class);

    when(result.matches(any(Atom.class))).thenReturn(true);

    return result;
  }

  private BondMatcher mockBondMatcher()
  {
    BondMatcher result = mock(BondMatcher.class);

    when(result.matches(any(Bond.class))).thenReturn(true);

    return result;
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

  private Query createQuery(Molecule molecule)
  {
    DefaultQuery result = new DefaultQuery();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      result.addNode(mockAtomMatcher());
    }

    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);

      result.connect(result.getNode(bond.getSource().getIndex()), result.getNode(bond.getTarget().getIndex()), mockBondMatcher());
    }

    return result;
  }
}
