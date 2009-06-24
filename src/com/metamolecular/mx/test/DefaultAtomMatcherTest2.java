/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query2.DefaultAtomMatcher;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class DefaultAtomMatcherTest2 extends TestCase
{

  private DefaultAtomMatcher matcher;
  private Molecule phenol;

  public DefaultAtomMatcherTest2()
  {
    phenol = Molecules.createPhenol();
  }

  @Override
  protected void setUp() throws Exception
  {
    matcher = new DefaultAtomMatcher();
  }

  public void testItShouldMatchBasedOnAtomLabel()
  {
    matcher.setSymbol("O");

    assertTrue(matcher.matches(phenol.getAtom(6)));
  }

  public void testItShouldNotMatchBasedOnAtomLabel()
  {
    matcher.setSymbol("O");

    assertFalse(matcher.matches(phenol.getAtom(0)));
  }
}
