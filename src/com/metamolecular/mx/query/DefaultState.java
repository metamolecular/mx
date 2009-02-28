/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
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
    return new HashMap<Node, Atom>(map);
  }

  public boolean hasNextCandidate()
  {
    return !candidates.isEmpty();
  }

  public boolean isDead()
  {
    return query.countNodes() > target.countAtoms();
  }

  public boolean isGoal()
  {
    return map.size() == query.countNodes();
  }

  public boolean isMatchFeasible(Match match)
  {
    if (map.containsKey(match.getQueryNode()) || map.containsValue(match.getTargetAtom()))
    {
      return false;
    }

    if (!matchAtoms(match))
    {
      return false;
    }

    if (!matchBonds(match))
    {
      return false;
    }

    return true;
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

  private boolean matchAtoms(Match match)
  {
    return match.getQueryNode().getAtomMatcher().matches(match.getTargetAtom());
//    if (match.getQueryAtom().countNeighbors() > match.getTargetAtom().countNeighbors())
//    {
//      return false;
//    }
//
//    if (match.getQueryAtom().getValence() > match.getTargetAtom().getValence())
//    {
//      return false;
//    }
//
//    int totalQueryNeighbors = match.getQueryAtom().countNeighbors() +
//      match.getQueryAtom().countVirtualHydrogens();
//    int totalTargetNeighbors = match.getTargetAtom().countNeighbors() +
//      match.getTargetAtom().countVirtualHydrogens();
//
//    if (totalQueryNeighbors > totalTargetNeighbors)
//    {
//      return false;
//    }
//
//    return match.getQueryAtom().getSymbol().equals(match.getTargetAtom().getSymbol());
  }

  private boolean matchBonds(Match match)
  {
    if (queryPath.isEmpty())
    {
      return true;
    }

    if (!matchBondsToHead(match))
    {
      return false;
    }

    for (int i = 0; i < queryPath.size() - 1; i++)
    {
      Edge queryBond = query.getEdge(queryPath.get(i), match.getQueryNode());
      Bond targetBond = target.getBond(targetPath.get(i), match.getTargetAtom());

      if (queryBond == null)
      {
        continue;
      }

      if (targetBond == null)
      {
        return false;
      }

      if (!matchBond(queryBond, targetBond))
      {
        return false;
      }
    }

    return true;
  }

  private boolean matchBondsToHead(Match match)
  {
    Node queryHead = getQueryPathHead();
    Atom targetHead = getTargetPathHead();

    Edge queryBond = query.getEdge(queryHead, match.getQueryNode());
    Bond targetBond = target.getBond(targetHead, match.getTargetAtom());

    if (queryBond == null || targetBond == null)
    {
      return false;
    }

    return matchBond(queryBond, targetBond);
  }

  private Node getQueryPathHead()
  {
    return queryPath.get(queryPath.size() - 1);
  }

  private Atom getTargetPathHead()
  {
    return targetPath.get(targetPath.size() - 1);
  }

  private boolean matchBond(Edge edge, Bond targetBond)
  {
    return true;
  }
}
