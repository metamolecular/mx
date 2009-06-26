/*
 * MX Cheminformatics Tools for Java
 *
 * Copyright (c) 2007-2008 Metamolecular, LLC
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
import com.metamolecular.mx.model.Bond;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathWriter
{
  private Set<Atom> sp2;
  private Set<Atom> sp;
  private List<Integer> ringClosures;
  
  public PathWriter()
  {
    sp2 = new HashSet();
    sp = new HashSet();
    ringClosures = new ArrayList();
  }

  public void write(List<Atom> path, Collection<String> strings, Collection<Atom> aromatics)
  {
    loadUnsaturatedBonds(path);
    StringBuffer pathString = new StringBuffer();

    for (Atom atom : path)
    {
      appendAtom(atom, pathString, aromatics);
      strings.add(pathString.toString());
    }

    appendRingClosures(pathString, strings);
  }

  private void appendAtom(Atom atom, StringBuffer pathString, Collection<Atom> aromatics)
  {
    String key = atom.getSymbol();

    pathString.append(key);

    if (aromatics.contains(atom) || sp2.contains(atom))
    {
      pathString.append("%");
    }
    
    if (sp.contains(atom))
    {
      pathString.append("#");
    }
  }

  private void appendRingClosures(StringBuffer rootPathString, Collection<String> pathStrings)
  {
    for (Integer closure : ringClosures)
    {
      pathStrings.add(rootPathString + "-" + closure);
    }
  }

  private void loadUnsaturatedBonds(List<Atom> path)
  {
    sp2.clear();
    sp.clear();
    ringClosures.clear();
    
    for (int i = 1; i < path.size(); i++)
    {
      Atom atom = path.get(i);
      Atom left = path.get(i-1);
      Bond bond = atom.getBond(left);
      
      if (bond.getType() == 2)
      {
        sp2.add(left);
        sp2.add(atom);
      }
      
      if (bond.getType() == 3)
      {
        sp.add(left);
        sp.add(atom);
      }
    }
    
    Atom tail = path.get(path.size() - 1);
    
    for (int i = path.size() - 3; i >= 0; i--)
    {
      Atom atom = path.get(i);
      
      if (atom.isConnectedTo(tail))
      {
        ringClosures.add(path.size() - i);
        Bond bond = atom.getBond(tail);
        
        if (bond.getType() == 2)
        {
          sp2.add(atom);
          sp2.add(tail);
        }
        
        if (bond.getType() == 3)
        {
          sp.add(atom);
          sp.add(tail);
        }
      }
    }
  }
}
