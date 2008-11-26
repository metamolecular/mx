/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.walk.PathFinder;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class PathFinderTest extends TestCase
{
  private Molecule hexane;

  @Override
  protected void setUp() throws Exception
  {
    hexane = Molecules.createHexane();
  }

  public void testItShouldFindOneHexanePathsStartingFromPrimaryCarbon()
  {
    PathFinder finder = new PathFinder();

    List<List<Atom>> paths = finder.findAllPaths(hexane.getAtom(0));

    assertEquals(1, paths.size());

    List<Atom> path = paths.get(0);

    for (int i = 0; i < path.size(); i++)
    {
      assertEquals(hexane.getAtom(0), path.get(i));
    }
  }
}
