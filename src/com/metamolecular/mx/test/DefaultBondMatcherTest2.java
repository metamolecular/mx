/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query2.DefaultBondMatcher;
import junit.framework.TestCase;

/**
 *
 * @author rich
 */
public class DefaultBondMatcherTest2 extends TestCase
{
  private DefaultBondMatcher matcher;

  @Override
  protected void setUp() throws Exception
  {
    
  }
  
  public void testItMatchesEthaneCCSingleBond()
  {
    Molecule ethane = createEthane();
    matcher = new DefaultBondMatcher(ethane.getBond(0));
    
    assertTrue(matcher.matches(ethane.getBond(0)));
  }
  
  public void testItDoesntMatchCCSingleToCCDouble()
  {
    Molecule ethane = createEthane();
    Molecule ethene = createEthene();
    matcher = new DefaultBondMatcher(ethane.getBond(0));
    
    assertFalse(matcher.matches(ethene.getBond(0)));
  }
  
  public void testItDoesntMatchCCDoubleToCCSingle()
  {
    Molecule ethane = createEthane();
    Molecule ethene = createEthene();
    matcher = new DefaultBondMatcher(ethene.getBond(0));
    
    assertFalse(matcher.matches(ethane.getBond(0)));
  }
  
  private Molecule createEthane()
  {
    Molecule result = new DefaultMolecule();
    result.connect(result.addAtom("C"), result.addAtom("C"), 1);
    
    return result;
  }
  
  private Molecule createEthene()
  {
    Molecule result = createEthane();
    result.getBond(0).setType(2);
    
    return result;
  }
}
