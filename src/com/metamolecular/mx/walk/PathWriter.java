/*
 * MX - Essential Cheminformatics
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com/mx
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
package com.metamolecular.mx.walk;

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
public class PathWriter implements Reporter
{
  private Collection output;
  private Set<Atom> aromatics;
  private List<Atom> atomPath;
  private List<Bond> bondPath;
  private boolean pathDirty;

  public PathWriter(Collection output)
  {
    this.output = output;
    this.aromatics = new HashSet();
    atomPath = new ArrayList();
    bondPath = new ArrayList();
    pathDirty = false;
  }

  public void setAromatics(Collection<Atom> aromatics)
  {
    this.aromatics.clear();
    this.aromatics.addAll(aromatics);
  }

  public void walkEnd(Atom root)
  {
    writePaths(0);
  }

  public void walkStart(Atom atom)
  {
    atomPath.clear();
    bondPath.clear();

    pathDirty = false;
  }

  public void atomFound(Atom atom)
  {
//    System.out.println("atomFound: " + atom.getIndex());
    if (atomPath.size() > 0 && (atomPath.size() != bondPath.size()))
    {
      throw new RuntimeException("Attempt to add Atom without first adding Bond");
    }
    atomPath.add(atom);
    
//    for (Atom inpath : atomPath)
//    {
//      System.out.print(inpath.getIndex() + "-");
//    }
//    
//    System.out.println();

    pathDirty = true;
  }

  public void bondFound(Bond bond)
  {
    bondPath.add(bond);
  }

  public void branchEnd(Atom atom)
  {
  }

  public void branchStart(Atom atom)
  {
    writePaths(0);

    int index = atomPath.indexOf(atom);

    if (index == -1)
    {
      throw new RuntimeException("Attempt to branch from nonexistant atom " + atom);
    }

    chop(index);
  }

  public void ringClosed(Bond bond)
  {
    if (atomPath.isEmpty())
    {
      throw new RuntimeException("Attempt to close empty path.");
    }

    Atom last = atomPath.get(atomPath.size() - 1);
    Atom inPath = bond.getMate(last);
    int index = atomPath.indexOf(inPath);

    if (index == -1)
    {
      throw new RuntimeException("Attempt to close nonexistant atom" + inPath);
    }

    int ringSize = atomPath.size() - index;

    if (ringSize < 3)
    {
      throw new RuntimeException("Atom closes rings with size less than three " + inPath);
    }
    
//    System.out.println("ringClosed: " + ringSize);
    
    pathDirty = true;

    writePaths(ringSize);
  }

  private void chop(int index)
  {
    int atomChopCount = atomPath.size() - index - 1;

    for (int i = 0; i < atomChopCount; i++)
    {
      atomPath.remove(atomPath.size() - 1);
    }

    int bondChopCount = bondPath.size() - index;

    for (int i = 0; i < bondChopCount; i++)
    {
      bondPath.remove(bondPath.size() - 1);
    }
  }

  private void writePaths(int ringSize)
  {
    if (!pathDirty)
    {
      return;
    }

    StringBuffer buffer = new StringBuffer();

    for (Atom atom : atomPath)
    {
      write(atom, buffer);

      if (aromatics.contains(atom))
      {
        buffer.append("%");
      }
      else
      {
        for (Bond bond : atom.getBonds())
        {
          if (bond.getType() == 2 && bondPath.contains(bond))
          {
            buffer.append("%");

            break;
          }

          if (bond.getType() == 3 && bondPath.contains(bond))
          {
            buffer.append("#");

            break;
          }
        }
      }
      
//      System.out.println(buffer);
      output.add(buffer.toString());
    }

    if (ringSize != 0)
    {
//      System.out.println(buffer);
      output.add(buffer.toString() + "-" + ringSize);
    }
    
    else
    {
//      System.out.println("ring size 0: " +buffer);
    }

    pathDirty = false;
  }

  private void write(Atom atom, StringBuffer buffer)
  {
    String type = atom.getSymbol();

    buffer.append(type);
  }
}
