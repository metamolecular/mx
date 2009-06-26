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
  
  public PathWriter()
  {
    sp2 = new HashSet();
  }

  public void write(List<Atom> path, Collection<String> strings, Collection<Atom> aromatics)
  {
    loadSP2(path);
    StringBuffer pathString = new StringBuffer();

    for (Atom atom : path)
    {
      appendAtom(atom, pathString, aromatics);
      strings.add(pathString.toString());
    }

    appendRingClosures(path, pathString, strings);
  }

  private void appendAtom(Atom atom, StringBuffer pathString, Collection<Atom> aromatics)
  {
    String key = atom.getSymbol();

    pathString.append(key);

    if (aromatics.contains(atom) || sp2.contains(atom))
    {
      pathString.append("%");
    }
  }

  private void appendRingClosures(List<Atom> path, StringBuffer rootPathString, Collection<String> pathStrings)
  {
    Atom tail = path.get(path.size() - 1);

    for (int i = path.size() - 3; i >= 0; i--)
    {
      Atom atom = path.get(i);

      if (atom.isConnectedTo(tail))
      {
        pathStrings.add(rootPathString + "-" + (path.size() - i));
      }
    }
  }

  private void loadSP2(List<Atom> path)
  {
    for (int i = 0; i < path.size(); i++)
    {
      Atom atom = path.get(i);
    }
  }
}
