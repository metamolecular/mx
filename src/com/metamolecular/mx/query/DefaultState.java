/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.HashMap;
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
  private List<Node> queryPath;
  private List<Atom> targetPath;
  private Map<Node, Atom> map;

  public DefaultState(Query query, Molecule target)
  {
    this.map = new HashMap();
    this.queryPath = new ArrayList<Node>();
    this.targetPath = new ArrayList<Atom>();

    this.query = query;
    this.target = target;
    candidates = new ArrayList<Match>();

    loadRootCandidates();
  }

  private DefaultState(DefaultState state, Match match)
  {
    candidates = new ArrayList<Match>();
    this.queryPath = new ArrayList<Node>(state.queryPath);
    this.targetPath = new ArrayList<Atom>(state.targetPath);
    this.map = state.map;
    this.query = state.query;
    this.target = state.target;

    map.put(match.getQueryNode(), match.getTargetAtom());
    queryPath.add(match.getQueryNode());
    targetPath.add(match.getTargetAtom());

    loadCandidates(match);
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
    return new DefaultState(this, match);
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

  private void loadCandidates(Match lastMatch)
  {
    Atom[] targetNeighbors = lastMatch.getTargetAtom().getNeighbors();

    for (Node q : lastMatch.getQueryNode().neighbors())
    {
      for (Atom t : targetNeighbors)
      {
        Match match = new Match(q, t);

        if (candidateFeasible(match))
        {
          candidates.add(match);
        }
      }
    }
  }

  private boolean candidateFeasible(Match candidate)
  {
    for (Node queryAtom : map.keySet())
    {
      if (queryAtom.equals(candidate.getQueryNode()) ||
        map.get(queryAtom).equals(candidate.getTargetAtom()))
      {
        return false;
      }
    }

    return true;
  }//  private void loadCandidates(Match lastMatch)
//  {
//    Atom[] queryNeighbors = lastMatch.getQueryNode().getNeighbors();
//    Atom[] targetNeighbors = lastMatch.getTargetAtom().getNeighbors();
//
//    for (Atom q : queryNeighbors)
//    {
//      for (Atom t : targetNeighbors)
//      {
//        Match match = new Match(q, t);
//
//        if (candidateFeasible(match))
//        {
//          candidates.add(match);
//        }
//      }
//    }
//  }
}
