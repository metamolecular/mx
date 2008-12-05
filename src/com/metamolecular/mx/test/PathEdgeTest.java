/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.PathEdge;
import junit.framework.TestCase;

/**
 *
 * @author rich
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

  public void testCollapsedEdgeShouldHavePathAtomsInCorrectOrder()
  {
    Molecule benzene = Molecules.createBenzene();
    PathEdge edge1 = new PathEdge(benzene.getAtom(0), benzene.getAtom(1));
    PathEdge edge2 = new PathEdge(benzene.getAtom(1), benzene.getAtom(2));
  }
}
