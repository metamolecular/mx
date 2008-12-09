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

package com.metamolecular.mx.ring;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class PathEdge
{

  private List<Atom> path;
  private Atom source;
  private Atom target;
  
  public PathEdge(Bond bond)
  {
    this(bond.getSource(), bond.getTarget());
  }

  public PathEdge(Atom source, Atom target)
  {
    this.source = source;
    this.target = target;
    path = new ArrayList<Atom>();

    path.add(source);
    path.add(target);
  }

  public Atom getSource()
  {
    return source;
  }

  public Atom getTarget()
  {
    return target;
  }

  public PathEdge(PathEdge edge1, PathEdge edge2)
  {
    Atom link = findLink(edge1, edge2);
    this.path = new ArrayList<Atom>();
    
    readPath(path, edge1, edge1.getSource() == link);
    path.add(link);
    readPath(path, edge2, edge2.getSource() == link);
    
    this.source = path.get(0);
    this.target = path.get(path.size() - 1);
  }

  public boolean isLoop()
  {
    return source == target;
  }

  public List<Atom> getPath()
  {
    return path;
  }
  
  private void readPath(List<Atom> path, PathEdge edge, boolean reverse)
  {
    if (reverse)
    {
      for (int i = edge.getPath().size() - 1; i > 0; i--)
      {
        path.add(edge.getPath().get(i));
      }
    }
    
    else
    {
      for (int i = 0; i < edge.getPath().size() - 1; i++)
      {
        path.add(edge.getPath().get(i));
      }
    }
  }
  
  private Atom findLink(PathEdge edge1, PathEdge edge2)
  {
    if (edge1.getSource() == edge2.getSource() || edge1.getSource() == edge2.getTarget())
    {
      return edge1.getSource();
    }
    
    return edge1.getTarget();
  }
}
