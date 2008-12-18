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
import com.metamolecular.mx.ring.PathGraph;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class PathGraphTest extends TestCase
{

  public void testItShouldReturnEmptyListWhenRemovingFirstAtomOfMonocycle()
  {
    Molecule cyclohexane = Molecules.createCyclohexane();
    PathGraph graph = new PathGraph(cyclohexane);
    List<PathEdge> cycles = graph.remove(cyclohexane.getAtom(0));

    assertEquals(0, cycles.size());
  }

  public void testItShouldReturnOneCycleWhenRemovingLastAtomOfMonocycle()
  {
    Molecule cyclohexane = Molecules.createCyclohexane();
    PathGraph graph = new PathGraph(cyclohexane);

    graph.remove(cyclohexane.getAtom(0));
    graph.remove(cyclohexane.getAtom(1));
    graph.remove(cyclohexane.getAtom(2));
    graph.remove(cyclohexane.getAtom(3));
    graph.remove(cyclohexane.getAtom(4));
   
    List<PathEdge> cycles = graph.remove(cyclohexane.getAtom(5));
    
    assertEquals(1, cycles.size());
  }
}
