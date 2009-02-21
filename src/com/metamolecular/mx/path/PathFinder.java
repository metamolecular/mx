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
    Step step = new DefaultStep(atom);
    
    paths.clear();
    walk(step);
    
    return paths;
  }
  
  public void walk(Step step)
  {
    if (!step.hasNextBranch())
    {
      paths.add(new ArrayList<Atom>(step.getPath()));
      
      return;
    }
    
    while(step.hasNextBranch())
    {
      Atom next = step.nextBranch();
      
      if (step.isBranchFeasible(next))
      {
        walk(step.nextStep(next));
        
        step.backTrack();
      }
    }
  }
}
