/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultState implements State
{
  private List<Match> candidates;
  private Query query;
  private Molecule target;

  public DefaultState(Query query, Molecule target)
  {
    this.query = query;
    this.target = target;
    candidates = new ArrayList<Match>();

    loadRootCandidates();
  }

  public void backTrack()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Map<Node, Atom> getMap()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public boolean hasNextCandidate()
  {
    return !candidates.isEmpty();
  }

  public boolean isDead()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public boolean isGoal()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public boolean isMatchFeasible(Match match)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Match nextCandidate()
  {
    return candidates.remove(candidates.size() - 1);
  }

  public State nextState(Match match)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  private void loadRootCandidates()
  {
    for (int i = 0; i < query.countNodes(); i++)
    {
      for (int j = 0; j < target.countAtoms(); j++)
      {
        Match match = new Match(query.getNode(i), target.getAtom(j));

        candidates.add(match);
      }
    }
  }
}
