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
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.PathEdge;
import com.metamolecular.mx.ring.PathGraph;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class PathGraphTest2 extends TestCase
{
  public void testItShouldHaveTheSameNumberOfEdgesAsBondsOnCreation()
  {
    Molecule benzene = Molecules.createBenzene();
    PathGraph graph = new PathGraph(benzene);
    
    assertEquals(6, graph.getEdges().size());
  }
  
  public void testItShouldHaveNextAtomAfterCreation()
  {
    Molecule benzene = Molecules.createBenzene();
    PathGraph pathGraph = new PathGraph(benzene);

    assertEquals(true, pathGraph.hasNextAtom());
  }

  public void testItShouldReturnLeastConnectedAtomAtFirst()
  {
    Molecule toluene = Molecules.createToluene();
    PathGraph pathGraph = new PathGraph(toluene);
    Atom next = pathGraph.nextAtom();

    assertEquals(6, next.getIndex());
  }

  public void testItShouldRemoveATerminalAtomWithoutCreatingANewEdge()
  {
    Molecule toluene = Molecules.createToluene();
    PathGraph pathGraph = new PathGraph(toluene);
    Atom next = pathGraph.nextAtom();

    pathGraph.remove(next);

    assertEquals(6, pathGraph.getAtoms().size());
    assertFalse(pathGraph.getAtoms().contains(next));
  }

  public void testItShouldReturnTwoEdgesWhenRemovingFirstAtomFromSimpleCycle()
  {
    Molecule benzene = Molecules.createBenzene();
    PathGraph graph = new PathGraph(benzene);

    List<PathEdge> edges = graph.remove(benzene.getAtom(0));

    assertEquals(2, edges.size());
  }
  
    
  private void printPath(PathEdge edge)
  {
    for (Atom atom : edge.getPath())
    {
      System.out.print(atom.getIndex());
      System.out.print("-");
    }
    
    System.out.println();
  }
}
