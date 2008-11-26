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

package com.metamolecular.mx.path;

import com.metamolecular.mx.model.Atom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class PathFinder
{
  private List<List<Atom>> paths;
  
  public PathFinder()
  {
    paths = new ArrayList<List<Atom>>();
  }
  
  public List<List<Atom>> findAllPaths(Atom atom)
  {
    Step state = new DefaultStep(atom);
    
    paths.clear();
    walk(state);
    
    return paths;
  }
  
  public void walk(Step state)
  {
    if (!state.hasNextBranch())
    {
      paths.add(new ArrayList<Atom>(state.getPath()));
      
      return;
    }
    
    while(state.hasNextBranch())
    {
      Atom next = state.nextBranch();
      
      if (state.isBranchFeasible(next))
      {
        walk(state.nextStep(next));
        
        state.backTrack();
      }
    }
  }
}
