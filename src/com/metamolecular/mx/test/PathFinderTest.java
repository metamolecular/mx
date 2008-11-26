/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.walk.PathFinder;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class PathFinderTest extends TestCase
{
  private Molecule hexane;
  private Molecule acetone;

  @Override
  protected void setUp() throws Exception
  {
    hexane = Molecules.createHexane();
    acetone = Molecules.createAcetone();
  }

  public void testItShouldFindOneHexanePathsStartingFromPrimaryCarbon()
  {
    PathFinder finder = new PathFinder();

    List<List<Atom>> paths = finder.findAllPaths(hexane.getAtom(0));

    assertEquals(1, paths.size());
    assertEquals(hexane.countAtoms(), paths.get(0).size());

    List<Atom> path = paths.get(0);

    for (int i = 0; i < hexane.countAtoms(); i++)
    {
      assertEquals(hexane.getAtom(i).getIndex(), path.get(i).getIndex());
    }
  }

  public void testItShouldFindBothHexanePathsStartingFromSecondaryCarbon()
  {
    PathFinder finder = new PathFinder();

    List<List<Atom>> paths = finder.findAllPaths(hexane.getAtom(1));

    assertEquals(2, paths.size());

    List<Atom> test = new ArrayList<Atom>();

    test.add(hexane.getAtom(1));
    test.add(hexane.getAtom(0));

    assertTrue(paths.contains(test));

    test.clear();

    test.add(hexane.getAtom(1));
    test.add(hexane.getAtom(2));
    test.add(hexane.getAtom(3));
    test.add(hexane.getAtom(4));
    test.add(hexane.getAtom(5));

    assertTrue(paths.contains(test));
  }

  public void testItShouldFindAllThreeAcetonePathsStartingFromCarbonylCarbon()
  {
    PathFinder finder = new PathFinder();
    List<List<Atom>> paths = finder.findAllPaths(acetone.getAtom(1));

    assertEquals(3, paths.size());

    List<Atom> test = new ArrayList<Atom>();
    
    test.add(acetone.getAtom(1));
    test.add(acetone.getAtom(0));
    
    assertTrue(paths.contains(test));
    
    test.clear();
    test.add(acetone.getAtom(1));
    test.add(acetone.getAtom(2));
    
    assertTrue(paths.contains(test));
    
    test.clear();
    
    test.add(acetone.getAtom(1));
    test.add(acetone.getAtom(3));
    
    assertTrue(paths.contains(test));
  }
}
