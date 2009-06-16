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
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.TemplateCompiler;
import com.metamolecular.mx.query.Query;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class TemplateCompilerTest extends TestCase
{
  private TemplateCompiler compiler;
  
  @Override
  protected void setUp() throws Exception
  {
    compiler = new TemplateCompiler();
  }
  
  public void testItShouldReturnATemplateQueryWithSevenNodesFromPhenol()
  {
    compiler.setMolecule(Molecules.createPhenol());
    Query query = compiler.compile();

    assertEquals(7, query.countNodes());
  }

  public void testItShouldReturnATemplateQueryWithSevenEdgesFromPhenol()
  {
    compiler.setMolecule(Molecules.createPhenol());
    Query query = compiler.compile();

    assertEquals(7, query.countEdges());
  }
  
  public void testItConvertsBlockedPropaneToThreeNodeQuery()
  {
    compiler.setMolecule(createBlockedPropane());
    Query query = compiler.compile();
    
    assertEquals(3, query.countNodes());
  }
  
  public void testItDoesntReduceIsotopeBlockedPropane()
  {
    compiler.setMolecule(createIsotopeBlockedPropane());
    Query query = compiler.compile();
    
    assertEquals(4, query.countNodes());
  }
  
  public void testItDoesntReduceChiralBlockedPropane()
  {
    compiler.setMolecule(createChiralBlockedPropane());
    Query query = compiler.compile();
    
    assertEquals(4, query.countNodes());
  }
  
  public void testBlockedPropaneReducedNodeMatchesUnreducedAtom()
  {
    compiler.setMolecule(createBlockedPropane());
    Query query = compiler.compile();
    
    assertTrue(query.getNode(1).getAtomMatcher().matches(Molecules.createPropane().getAtom(1)));
  }
  
  public void testDoubleBlockedPropaneReducedNodeMatchesUnreducedAtom()
  {
    compiler.setMolecule(createDoubleBlockedPropane());
    Query query = compiler.compile();
        
    assertTrue(query.getNode(1).getAtomMatcher().matches(Molecules.createPropane().getAtom(1)));
  }
  
  public void testBlockedSecondaryCarbonDoesntMatchQuatCarbon()
  {
    compiler.setMolecule(createBlockedPropane());
    Query query = compiler.compile();
    
    assertFalse(query.getNode(1).getAtomMatcher().matches(Molecules.createNeopentane().getAtom(0)));
  }
  
  private Molecule createBlockedPropane()
  {
    Molecule result = Molecules.createPropane();

    result.connect(result.getAtom(1), result.addAtom("H"), 1);

    return result;
  }

  private Molecule createDoubleBlockedPropane()
  {
    Molecule result = createBlockedPropane();

    result.connect(result.getAtom(1), result.addAtom("H"), 1);

    return result;
  }

  private Molecule createChiralBlockedPropane()
  {
    Molecule result = createBlockedPropane();
    Bond bond = result.getBond(result.getAtom(1), result.getAtom(3));

    bond.setStereo(1);

    return result;
  }

  private Molecule createIsotopeBlockedPropane()
  {
    Molecule result = createBlockedPropane();
    Atom h = result.getAtom(3);

    h.setIsotope(1);

    return result;
  }
}
