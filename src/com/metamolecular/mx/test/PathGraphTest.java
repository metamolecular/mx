/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring2.PathGraph;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class PathGraphTest extends TestCase
{
  public void testItShouldHaveTheSameNumberOfEdgesAndNodesAsParent()
  {
    Molecule cyclohexane = Molecules.createHexane();
    PathGraph graph = new PathGraph(cyclohexane);
    
    assertEquals(cyclohexane.countAtoms(), graph.countNodes());
    assertEquals(cyclohexane.countBonds(), graph.countEdges());
  }
}
