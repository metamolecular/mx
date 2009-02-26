/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.DefaultQuery;
import com.metamolecular.mx.query.DefaultState;
import com.metamolecular.mx.query.Match;
import com.metamolecular.mx.query.Query;
import com.metamolecular.mx.query.State;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class QueryDefaultStateTest extends TestCase
{
  private Molecule benzene;
  private Query benzeneQuery;

  public QueryDefaultStateTest()
  {
    benzene = Molecules.createBenzene();
    benzeneQuery = new DefaultQuery(benzene);
  }

  @Override
  protected void setUp() throws Exception
  {
  }

  public void testItShouldFindAllMatchCandidatesInTheRootState()
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

  public void testItShoudFindAllMatchCandidatesInThePrimaryState()
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
}
