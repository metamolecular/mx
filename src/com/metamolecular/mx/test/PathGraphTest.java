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
import com.metamolecular.mx.ring2.PathGraph;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class PathGraphTest extends TestCase
{

  public void testItShouldNotBeEmptyAfterCreation()
  {
    Molecule cyclohexane = Molecules.createHexane();
    PathGraph graph = new PathGraph(cyclohexane);

    assertFalse(graph.isEmpty());
  }

  public void testItShouldReturnMethylCarbonAsLeastConnectedNodeInToluene()
  {
    Molecule toluene = Molecules.createToluene();
    PathGraph graph = new PathGraph(toluene);
    Atom atom = graph.getLeastConnectedAtom();

    assertEquals(6, atom.getIndex());
    assertEquals(1, graph.countConnections(atom));
  }

  public void testItShouldReturnEmptyCollectionWhenRemovingTerminalAtom()
  {
    Molecule toluene = Molecules.createToluene();
    PathGraph graph = new PathGraph(toluene);
    Atom atom = graph.getLeastConnectedAtom();
    List<List<Atom>> cycles = graph.remove(atom);
    
    assertTrue(cycles.isEmpty());
  }
  
  public void testItShouldReturnAllPathsTerminatedByAnAtomCorrectlyOriented()
  {
    Molecule toluene = Molecules.createToluene();
    PathGraph graph = new PathGraph(toluene);
    List<List<Atom>> paths = graph.getPaths(toluene.getAtom(0));
    
    assertEquals(3, paths.size());
    assertTrue(paths.contains(Arrays.asList(toluene.getAtom(0), toluene.getAtom(1))));
    assertTrue(paths.contains(Arrays.asList(toluene.getAtom(5), toluene.getAtom(0))));
    assertTrue(paths.contains(Arrays.asList(toluene.getAtom(6), toluene.getAtom(0))));
  }
  
  public void testItShouldReturnACycleOnRemovalofLastBenzeneAtom()
  {
    Molecule benzene = Molecules.createBenzene();
    PathGraph graph = new PathGraph(benzene);
    
    System.out.println(graph.remove(benzene.getAtom(0)));
    graph.remove(benzene.getAtom(1));
    graph.remove(benzene.getAtom(2));
    graph.remove(benzene.getAtom(3));
    graph.remove(benzene.getAtom(4));
    
    List<List<Atom>> cycles = graph.remove(benzene.getAtom(5));
    
    for (List<Atom> cycle : cycles)
    {
      System.out.println("Cycle:");
      for (Atom atom : cycle)
      {
        System.out.print(atom.getIndex() + "-");
      }
      
      System.out.println();
    }
    
    assertTrue(graph.isEmpty());
    assertFalse(cycles.isEmpty());
  }
}
