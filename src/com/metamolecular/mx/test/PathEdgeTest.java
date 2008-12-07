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

package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.PathEdge;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class PathEdgeTest extends TestCase
{

  public void testItShouldHaveAPathLengthOfTwoWhenCopiedFromTwoAtoms()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge = new PathEdge(benzene.getAtom(0), benzene.getAtom(1));

    assertEquals(2, edge.getPath().size());
  }

  public void testItShouldDetectALoopIfSourceMatchesTarget()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge = new PathEdge(benzene.getAtom(0), benzene.getAtom(0));

    assertTrue(edge.isLoop());
  }

  public void testItShouldCreateACollapsedEdgeFromTwoEdges()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(0), benzene.getAtom(1));
    PathEdge edge2 = new PathEdge(benzene.getAtom(1), benzene.getAtom(2));
    PathEdge edge3 = new PathEdge(edge1, edge2);

    assertEquals(3, edge3.getPath().size());
  }

  public void testItShouldCreateCollapedEdgePathInCorrectOrderWhenParentsOrdered()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(0), benzene.getAtom(1));
    PathEdge edge2 = new PathEdge(benzene.getAtom(1), benzene.getAtom(2));
    PathEdge edge3 = new PathEdge(edge1, edge2);

    for (int i = 0; i < edge3.getPath().size(); i++)
    {
      assertEquals(benzene.getAtom(i).getIndex(), edge3.getPath().get(i).getIndex());
    }
  }

  public void testItShouldCreateCollapsedEdgePathInCorrectOrderWhenParentsOpposite()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(1), benzene.getAtom(0));
    PathEdge edge2 = new PathEdge(benzene.getAtom(2), benzene.getAtom(1));
    PathEdge edge3 = new PathEdge(edge1, edge2);

    for (int i = 0; i < edge3.getPath().size(); i++)
    {
      assertEquals(benzene.getAtom(i).getIndex(), edge3.getPath().get(i).getIndex());
    }
  }

  public void testItShouldCreateCollapsedEdgePathInCorrectOrderWhenFirstParentReversed()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(1), benzene.getAtom(0));
    PathEdge edge2 = new PathEdge(benzene.getAtom(1), benzene.getAtom(2));
    PathEdge edge3 = new PathEdge(edge1, edge2);

    for (int i = 0; i < edge3.getPath().size(); i++)
    {
      assertEquals(benzene.getAtom(i).getIndex(), edge3.getPath().get(i).getIndex());
    }
  }

  public void testItShouldCreateCollapsedEdgePathInCorrectOrderFromThreeEdges()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(1), benzene.getAtom(0));
    PathEdge edge2 = new PathEdge(benzene.getAtom(1), benzene.getAtom(2));
    PathEdge edge3 = new PathEdge(benzene.getAtom(2), benzene.getAtom(3));
    PathEdge edge4 = new PathEdge(edge1, edge2);
    PathEdge edge5 = new PathEdge(edge4, edge3);
    
    assertEquals(4, edge5.getPath().size());

    for (int i = 0; i < edge5.getPath().size(); i++)
    {
      assertEquals(benzene.getAtom(i).getIndex(), edge5.getPath().get(i).getIndex());
    }
  }
  
    public void testItShouldCreateALoopByCollapsingSixEdges()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(1), benzene.getAtom(0));
    PathEdge edge2 = new PathEdge(benzene.getAtom(1), benzene.getAtom(2));
    PathEdge edge3 = new PathEdge(benzene.getAtom(2), benzene.getAtom(3));
    PathEdge edge4 = new PathEdge(benzene.getAtom(3), benzene.getAtom(4));
    PathEdge edge5 = new PathEdge(benzene.getAtom(4), benzene.getAtom(5));
    PathEdge edge6 = new PathEdge(benzene.getAtom(5), benzene.getAtom(0));
    PathEdge edge012 = new PathEdge(edge1, edge2);
    PathEdge edge0123 = new PathEdge(edge012, edge3);
    PathEdge edge01234 = new PathEdge(edge0123, edge4);
    PathEdge edge012345 = new PathEdge(edge01234, edge5);
    PathEdge edge0123450 = new PathEdge(edge012345, edge6);
    
    assertEquals(7, edge0123450.getPath().size());
    assertEquals(true, edge0123450.isLoop());
    
    for (int i = 0; i < edge0123450.getPath().size() - 1; i++)
    {
      assertTrue(edge0123450.getPath().get(i).isConnectedTo(edge0123450.getPath().get(i+1)));
    }
  }
}
