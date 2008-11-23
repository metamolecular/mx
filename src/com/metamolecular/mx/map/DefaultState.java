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
package com.metamolecular.mx.map;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Richard L. Apodaca
 */
public class DefaultState implements State
{
  private Map<Atom, Atom> map;
  private List<Atom> queryPath;
  private List<Atom> targetPath;
  private List<Match> candidates;
  private Molecule query;
  private Molecule target;

  public DefaultState(Molecule query, Molecule target)
  {
    this.query = query;
    this.target = target;
    this.map = new HashMap<Atom, Atom>();
    this.queryPath = new ArrayList<Atom>();
    this.targetPath = new ArrayList<Atom>();
    candidates = new ArrayList<Match>();

    loadRootCandidates();
  }

  private DefaultState(DefaultState state, Match match)
  {
    candidates = new ArrayList<Match>();
    this.queryPath = new ArrayList<Atom>(state.queryPath);
    this.targetPath = new ArrayList<Atom>(state.targetPath);
    this.map = state.map;
    this.query = state.query;
    this.target = state.target;

    map.put(match.getQueryAtom(), match.getTargetAtom());
    queryPath.add(match.getQueryAtom());
    targetPath.add(match.getTargetAtom());

    loadCandidates(match);
  }

  public void backTrack()
  {
    if (queryPath.isEmpty() || isGoal())
    {
      map.clear();

      return;
    }

    if (isHeadMapped())
    {
      return;
    }

    map.clear();

    for (int i = 0; i < queryPath.size() - 1; i++)
    {
      map.put(queryPath.get(i), targetPath.get(i));
    }
  }

  public State nextState(Match match)
  {
    return new DefaultState(this, match);
  }

  public boolean isMatchFeasible(Match match)
  {
    if (map.containsKey(match.getQueryAtom()) || map.containsValue(match.getTargetAtom()))
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

  public boolean isDead()
  {
    return query.countAtoms() > target.countAtoms();
  }

  public boolean isGoal()
  {
    return map.size() == query.countAtoms();
  }

  public Map<Atom, Atom> getMap()
  {
    return new HashMap<Atom, Atom>(map);
  }

  public boolean hasNextCandidate()
  {
    return !candidates.isEmpty();
  }

  public Match nextCandidate()
  {
    return candidates.remove(candidates.size() - 1);
  }

  private void loadRootCandidates()
  {
    for (int i = 0; i < query.countAtoms(); i++)
    {
      for (int j = 0; j < target.countAtoms(); j++)
      {
        Match match = new Match(query.getAtom(i), target.getAtom(j));

        candidates.add(match);
      }
    }
  }

  private void loadCandidates(Match lastMatch)
  {
    Atom[] queryNeighbors = lastMatch.getQueryAtom().getNeighbors();
    Atom[] targetNeighbors = lastMatch.getTargetAtom().getNeighbors();

    for (Atom q : queryNeighbors)
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
    for (Atom queryAtom : map.keySet())
    {
      if (queryAtom.equals(candidate.getQueryAtom()) ||
        map.get(queryAtom).equals(candidate.getTargetAtom()))
      {
        return false;
      }
    }

    return true;
  }

  private boolean matchAtoms(Match match)
  {
    if (match.getQueryAtom().countNeighbors() > match.getTargetAtom().countNeighbors())
    {
      return false;
    }
    
    if (match.getQueryAtom().getValence() > match.getTargetAtom().getValence())
    {
      return false;
    }

    return match.getQueryAtom().getSymbol().equals(match.getTargetAtom().getSymbol());
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
      Bond queryBond = query.getBond(queryPath.get(i), match.getQueryAtom());
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
    Atom queryHead = getQueryPathHead();
    Atom targetHead = getTargetPathHead();

    Bond queryBond = query.getBond(queryHead, match.getQueryAtom());
    Bond targetBond = target.getBond(targetHead, match.getTargetAtom());

    if (queryBond == null || targetBond == null)
    {
      return false;
    }

    return matchBond(queryBond, targetBond);
  }

  private boolean matchBond(Bond queryBond, Bond targetBond)
  {
    return true;
  }

  private boolean isHeadMapped()
  {
    Atom head = queryPath.get(queryPath.size() - 1);
    Atom[] neighbors = head.getNeighbors();

    for (Atom neighbor : neighbors)
    {
      if (!map.containsKey(neighbor))
      {
        return false;
      }
    }

    return true;
  }

  private Atom getQueryPathHead()
  {
    return queryPath.get(queryPath.size() - 1);
  }

  private Atom getTargetPathHead()
  {
    return targetPath.get(targetPath.size() - 1);
  }
}
