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
import com.metamolecular.mx.model.Molecule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Richard L. Apodaca
 */
public class DefaultMapper implements Mapper
{
  private Molecule query;
  private List<Map<Atom, Atom>> maps;

  public DefaultMapper(Molecule query)
  {
    this.query = query;
    this.maps = new ArrayList<Map<Atom, Atom>>();
  }

  public List<Map<Atom, Atom>> getMaps(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    mapAll(state);

    return new ArrayList<Map<Atom, Atom>>(maps);
  }

  public int countMaps(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    mapAll(state);

    return maps.size();
  }

  public Map<Atom, Atom> getFirstMap(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    mapFirst(state);

    return maps.isEmpty() ? new HashMap<Atom, Atom>() : maps.get(0);
  }

  public boolean hasMap(Molecule target)
  {
    DefaultState state = new DefaultState(query, target);

    maps.clear();

    return mapFirst(state);
  }

  private void mapAll(State state)
  {
    if (state.isDead())
    {
      return;
    }

    if (state.isGoal())
    {
      Map<Atom, Atom> map = state.getMap();

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

  private boolean hasMap(Map<Atom, Atom> map)
  {
    for (Map<Atom, Atom> test : maps)
    {
      if (test.equals(map))
      {
        return true;
      }
    }

    return false;
  }
}
