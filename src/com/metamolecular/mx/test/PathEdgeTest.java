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

package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.PathEdge;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathEdgeTest extends TestCase
{

  public void testItShouldReturnAValidPathFromAListOfAtoms()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path = new PathEdge(Arrays.asList(hexane.getAtom(0), hexane.getAtom(1)));

    assertEquals(2, path.getAtoms().size());
  }
  
  public void testItShouldReturnAppropriateSourceAndTarget()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge edge = new PathEdge(Arrays.asList(hexane.getAtom(0), hexane.getAtom(1)));
    
    assertEquals(hexane.getAtom(0), edge.getSource());
    assertEquals(hexane.getAtom(1), edge.getTarget());
  }

  public void testItShouldSpliceHeadToHead()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path1 = new PathEdge(Arrays.asList(hexane.getAtom(0), hexane.getAtom(1)));
    PathEdge path2 = new PathEdge(Arrays.asList(hexane.getAtom(1), hexane.getAtom(2)));
    PathEdge splice = path1.splice(path2);

    assertEquals(Arrays.asList(hexane.getAtom(0),
            hexane.getAtom(1),
            hexane.getAtom(2)), splice.getAtoms());
  }

  public void testItShouldSpliceHeadToTail()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path1 = new PathEdge(Arrays.asList(hexane.getAtom(0), hexane.getAtom(1)));
    PathEdge path2 = new PathEdge(Arrays.asList(hexane.getAtom(2), hexane.getAtom(1)));
    PathEdge splice = path1.splice(path2);


    assertEquals(Arrays.asList(hexane.getAtom(0),
            hexane.getAtom(1),
            hexane.getAtom(2)), splice.getAtoms());
  }

  public void testItShouldSpliceHeadToTailWithMaxLength()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path1 = new PathEdge(Arrays.asList(hexane.getAtom(0), hexane.getAtom(1)));
    PathEdge path2 = new PathEdge(Arrays.asList(hexane.getAtom(2), hexane.getAtom(1)));
    PathEdge splice = path1.splice(path2, 2);

    assertNull(splice);
  }

  public void testItShouldSpliceTailToHead()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path1 = new PathEdge(Arrays.asList(hexane.getAtom(1), hexane.getAtom(0)));
    PathEdge path2 = new PathEdge(Arrays.asList(hexane.getAtom(1), hexane.getAtom(2)));
    PathEdge splice = path1.splice(path2);


    assertEquals(Arrays.asList(hexane.getAtom(0),
            hexane.getAtom(1),
            hexane.getAtom(2)), splice.getAtoms());
  }

  public void testItShouldSpliceTailToTail()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path1 = new PathEdge(Arrays.asList(hexane.getAtom(1), hexane.getAtom(0)));
    PathEdge path2 = new PathEdge(Arrays.asList(hexane.getAtom(2), hexane.getAtom(1)));
    PathEdge splice = path1.splice(path2);


    assertEquals(Arrays.asList(hexane.getAtom(0),
            hexane.getAtom(1),
            hexane.getAtom(2)), splice.getAtoms());
  }

  public void testItShouldSpliceLinearChainToSinlgePath()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path0 = new PathEdge(Arrays.asList(hexane.getAtom(1), hexane.getAtom(0)));
    PathEdge path1 = new PathEdge(Arrays.asList(hexane.getAtom(2), hexane.getAtom(1)));
    PathEdge path2 = new PathEdge(Arrays.asList(hexane.getAtom(2), hexane.getAtom(3)));
    PathEdge path3 = new PathEdge(Arrays.asList(hexane.getAtom(3), hexane.getAtom(4)));
    PathEdge path4 = new PathEdge(Arrays.asList(hexane.getAtom(4), hexane.getAtom(5)));

    PathEdge splice01 = path0.splice(path1);
    PathEdge splice34 = path3.splice(path4);
    PathEdge splice012 = splice01.splice(path2);
    PathEdge splice01234 = splice012.splice(splice34);

    assertEquals(Arrays.asList(hexane.getAtom(0),
            hexane.getAtom(1),
            hexane.getAtom(2),
            hexane.getAtom(3),
            hexane.getAtom(4),
            hexane.getAtom(5)), splice01234.getAtoms());
  }

  public void testItShouldSpliceMonocycleToSinglePath()
  {
    Molecule cyclohexane = Molecules.createCyclohexane();
    PathEdge path0 = new PathEdge(Arrays.asList(cyclohexane.getAtom(1), cyclohexane.getAtom(0)));
    PathEdge path1 = new PathEdge(Arrays.asList(cyclohexane.getAtom(2), cyclohexane.getAtom(1)));
    PathEdge path2 = new PathEdge(Arrays.asList(cyclohexane.getAtom(2), cyclohexane.getAtom(3)));
    PathEdge path3 = new PathEdge(Arrays.asList(cyclohexane.getAtom(3), cyclohexane.getAtom(4)));
    PathEdge path4 = new PathEdge(Arrays.asList(cyclohexane.getAtom(4), cyclohexane.getAtom(5)));
    PathEdge path5 = new PathEdge(Arrays.asList(cyclohexane.getAtom(5), cyclohexane.getAtom(0)));

    PathEdge splice01 = path0.splice(path1);
    PathEdge splice34 = path3.splice(path4);
    PathEdge splice012 = splice01.splice(path2);
    PathEdge splice01234 = splice012.splice(splice34);
    PathEdge splice012345 = splice01234.splice(path5);

    assertEquals(Arrays.asList(cyclohexane.getAtom(0),
            cyclohexane.getAtom(1),
            cyclohexane.getAtom(2),
            cyclohexane.getAtom(3),
            cyclohexane.getAtom(4),
            cyclohexane.getAtom(5),
            cyclohexane.getAtom(0)), splice012345.getAtoms());
  }

  public void testItShouldBeACycleWhenContainingACyclicPath()
  {
    Molecule cyclohexane = Molecules.createCyclohexane();
    PathEdge edge = new PathEdge(Arrays.asList(
            cyclohexane.getAtom(0),
            cyclohexane.getAtom(1),
            cyclohexane.getAtom(2),
            cyclohexane.getAtom(3),
            cyclohexane.getAtom(4),
            cyclohexane.getAtom(5),
            cyclohexane.getAtom(0)));
    
    assertTrue(edge.isCycle());
  }
}
