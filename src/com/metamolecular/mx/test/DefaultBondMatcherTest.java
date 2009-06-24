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

import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.DefaultBondMatcher;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultBondMatcherTest extends TestCase
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
