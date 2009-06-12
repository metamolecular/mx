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
package com.metamolecular.mx.map;

import com.metamolecular.mx.query.*;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Richard L. Apodaca
 */
public class DefaultMapper implements Mapper
{

  private Query query;
  private List<Map<Node, Atom>> maps;

  public DefaultMapper(Query query)
  {
    this.query = query;
    this.maps = new ArrayList<Map<Node, Atom>>();
  }
  
  public DefaultMapper(Molecule molecule)
  {
    this.query = new DefaultQuery(molecule);
    this.maps = new ArrayList<Map<Node, Atom>>();
  }

  public boolean hasMap(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    return mapFirst(state);
  }

  public List<Map<Node, Atom>> getMaps(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    mapAll(state);

    return new ArrayList<Map<Node, Atom>>(maps);
  }

  public Map<Node, Atom> getFirstMap(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    mapFirst(state);

    return maps.isEmpty() ? new HashMap<Node, Atom>() : maps.get(0);
  }

  public int countMaps(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    mapAll(state);

    return maps.size();
  }

  private void mapAll(State state)
  {
    if (state.isDead())
    {
      return;
    }

    if (state.isGoal())
    {
      Map<Node, Atom> map = state.getMap();

      if (!hasMap(map))
      {
        maps.add(state.getMap());
      }

      return;
    }

    while (state.hasNextCandidate())
    {
      Match candidate = state.nextCandidate();

      if (state.isMatchFeasible(candidate))
      {
        State nextState = state.nextState(candidate);

        mapAll(nextState);

        nextState.backTrack();
      }
    }
  }

  private boolean mapFirst(State state)
  {
    if (state.isDead())
    {
      return false;
    }

    if (state.isGoal())
    {
      maps.add(state.getMap());

      return true;
    }

    boolean found = false;

    while (!found && state.hasNextCandidate())
    {
      Match candidate = state.nextCandidate();

      if (state.isMatchFeasible(candidate))
      {
        State nextState = state.nextState(candidate);
        found = mapFirst(nextState);

        nextState.backTrack();
      }
    }

    return found;
  }

  private boolean hasMap(Map<Node, Atom> map)
  {
    for (Map<Node, Atom> test : maps)
    {
      if (test.equals(map))
      {
        return true;
      }
    }

    return false;
  }
}
