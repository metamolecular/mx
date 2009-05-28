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
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.DefaultMapper;
import com.metamolecular.mx.query.DefaultQuery;
import com.metamolecular.mx.query.Mapper;
import com.metamolecular.mx.query.Query;
import junit.framework.TestCase;

/**
 *
 * @author Richard L. Apodaca
 */
public class DefaultQueryMapperTest extends TestCase
{
  private Molecule hexane;
  private Molecule benzene;
  private Query hexaneQuery;
  
  @Override
  protected void setUp() throws Exception
  {
    hexane = Molecules.createHexane();
    benzene = Molecules.createBenzene();
    hexaneQuery = new DefaultQuery(hexane);
  }

  /**
   * The problem is that DefaultMapper uses DefaultAtomMatcher.
   * DAM matches any atoms with same symbol and neighbor count
   * equal to or greater than its own. So sp2 query with 2 neighbors
   * matches sp3 query with 2 neighbors.
   */
  public void testItShouldNotMatchHexaneToBenzene()
  {
    Mapper mapper = new DefaultMapper(hexaneQuery);

    assertFalse(mapper.hasMap(benzene));
  }
}
