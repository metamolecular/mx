/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.PathEdge;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class PathEdgeTest extends TestCase
{

  public void testItShouldReturnAValidPathFromAListOfAtoms()
  {
    Molecule hexane = Molecules.createHexane();
    PathEdge path = new PathEdge(Arrays.asList(hexane.getAtom(0), hexane.getAtom(1)));

    assertEquals(2, path.getAtoms().size());
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
}
