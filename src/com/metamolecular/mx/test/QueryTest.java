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

import com.metamolecular.mx.query.DefaultAtomMatcher;
import com.metamolecular.mx.query.DefaultQuery;
import com.metamolecular.mx.query.Node;
import com.metamolecular.mx.query.Query;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class QueryTest extends TestCase
{
  private Query query;

  @Override
  protected void setUp() throws Exception
  {
    query = new DefaultQuery();
  }

  public void testItShouldHaveOneNodeAfterAddingOne()
  {
    query.addNode(new DefaultAtomMatcher());

    assertEquals(1, query.countNodes());
  }

  public void testItShouldReturnANodeHavingNoNeighborsAsFirst()
  {
    Node node = query.addNode(new DefaultAtomMatcher());

    assertEquals(0, node.countNeighbors());
  }
  
  public void testItShouldReturnANodeWithCorrectAtomMatcherAfterAdding()
  {
    DefaultAtomMatcher matcher = new DefaultAtomMatcher();
    Node node = query.addNode(matcher);
    
    assertEquals(matcher, node.getAtomMatcher());
  }

  public void testItShouldUpdateNodeNeighborCountAfterConnectingTwoNodes()
  {
    Node node1 = query.addNode(new DefaultAtomMatcher());
    Node node2 = query.addNode(new DefaultAtomMatcher());

    query.connect(node1, node2);

    assertEquals(1, node1.countNeighbors());
    assertEquals(1, node2.countNeighbors());
  }
}
