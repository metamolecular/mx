/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
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
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.DefaultAtomMatcher;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultAtomMatcherTest extends TestCase
{
  private DefaultAtomMatcher matcher;
  private Molecule phenol;

  public DefaultAtomMatcherTest()
  {
    phenol = Molecules.createPhenol();
  }

  @Override
  protected void setUp() throws Exception
  {
    matcher = new DefaultAtomMatcher();
  }

  public void testItMatchesBasedOnAtomLabel()
  {
    matcher.setSymbol("O");

    assertTrue(matcher.matches(phenol.getAtom(6)));
  }

  public void testItFailsMatchBasedOnAtomLabel()
  {
    matcher.setSymbol("O");

    assertFalse(matcher.matches(phenol.getAtom(0)));
  }

  public void testItMatchesBasedOnMaximumNeighbors()
  {
    matcher.setMaximumNeighbors(1);

    assertTrue(matcher.matches(phenol.getAtom(6)));
  }

  public void testItFailsMatchBasedOnMaximumNeighbors()
  {
    matcher.setMaximumNeighbors(1);

    assertFalse(matcher.matches(phenol.getAtom(0)));
  }
  
  public void testItShouldCreateATemplateMatcherThatMatchesPhenolOxygen()
  {
    matcher = new DefaultAtomMatcher(phenol.getAtom(6));
  
    assertTrue(matcher.matches(phenol.getAtom(6)));
  }
  
  public void testItShouldCreateATemplateMatcherThatMatchesPhenolQuatCarbon()
  {
    matcher = new DefaultAtomMatcher(phenol.getAtom(5));
    
    assertTrue(matcher.matches(phenol.getAtom(5)));
  }
  
  public void testItDoesntMatchDoublyBlockedSecondaryCarbonToQuatCarbon()
  {
    Molecule neopentane = Molecules.createNeopentane();
    matcher = new DefaultAtomMatcher(Molecules.createPropane().getAtom(1), 2);
    
    assertFalse(matcher.matches(neopentane.getAtom(0)));
  }
  
  private Molecule createEthylene()
  {
    Molecule result = new DefaultMolecule();
    result.connect(result.addAtom("C"), result.addAtom("C"), 2);
    
    return result;
  }
  
  private Molecule createAcetylene()
  {
    Molecule result = createEthylene();
    result.getBond(0).setType(3);
    
    return result;
  }
  
  private Molecule createAllene()
  {
    Molecule result = createEthylene();
    result.connect(result.getAtom(1), result.addAtom("C"), 2);
    
    return result;
  }
  
  private Molecule createIsopropanol()
  {
    Molecule result = Molecules.createAcetone();
    result.getBond(2).setType(1);
    
    return result;
  }
}
