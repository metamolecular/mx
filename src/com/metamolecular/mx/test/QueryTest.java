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
import com.metamolecular.mx.io.mdl.MolfileWriter;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.DefaultAtomMatcher;
import com.metamolecular.mx.query.DefaultQuery;
import com.metamolecular.mx.query.Edge;
import com.metamolecular.mx.query.Node;
import com.metamolecular.mx.query.Query;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class QueryTest extends TestCase
{
  private DefaultQuery query;

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

  public void testItShouldReturnAnEdgeWithTheCorrectNodesAfterConnecting()
  {
    Node node1 = query.addNode(new DefaultAtomMatcher());
    Node node2 = query.addNode(new DefaultAtomMatcher());

    Edge edge = query.connect(node1, node2);

    assertEquals(node1, edge.getSource());
    assertEquals(node2, edge.getTarget());
  }

  public void testItShouldHaveOneEdgeAfterConnectingTwoNodes()
  {
    Node node1 = query.addNode(new DefaultAtomMatcher());
    Node node2 = query.addNode(new DefaultAtomMatcher());

    query.connect(node1, node2);

    assertEquals(1, query.countEdges());
  }

  public void testItShouldReturnANodeThatIteratesThreeNodes()
  {
    Node node1 = query.addNode(new DefaultAtomMatcher());
    Node node2 = query.addNode(new DefaultAtomMatcher());
    Node node3 = query.addNode(new DefaultAtomMatcher());
    Node node4 = query.addNode(new DefaultAtomMatcher());

    query.connect(node1, node2);
    query.connect(node1, node3);
    query.connect(node1, node4);

    List neighbors = new ArrayList();

    for (Node node : node1.neighbors())
    {
      neighbors.add(node);
    }

    assertEquals(3, neighbors.size());
  }

  public void testItShouldReturnATemplateQueryWithSevenNodesFromPhenol()
  {
    Molecule phenol = Molecules.createPhenol();
    query = new DefaultQuery(phenol);

    assertEquals(7, query.countNodes());
  }

  public void testItShouldReturnATemplateQueryWithSevenEdgesFromPhenol()
  {
    Molecule phenol = Molecules.createPhenol();
    query = new DefaultQuery(phenol);

    assertEquals(7, query.countEdges());
  }

  public void testItShouldReturnATemplateQueryThatMatchesPhenol()
  {
    Molecule phenol = Molecules.createPhenol();
    query = new DefaultQuery(phenol);

    assertTrue(matches(query, phenol));
  }

  public void testItShouldReturnABenzeneTemplateThatMatchesPhenol()
  {
    Molecule phenol = Molecules.createPhenol();
    Molecule benzene = Molecules.createBenzene();
    query = new DefaultQuery(benzene);

    assertTrue(matches(query, phenol));
  }

  public void testItShouldReturnABenzeneTemplateThatMatchesToluene()
  {
    Molecule toluene = Molecules.createToluene();
    Molecule benzene = Molecules.createBenzene();
    query = new DefaultQuery(benzene);

    assertTrue(matches(query, toluene));
  }

  private boolean matches(Query query, Molecule molecule)
  {
    for (int i = 0; i < query.countNodes(); i++)
    {
      Node node = query.getNode(i);
      Atom atom = molecule.getAtom(i);

      if (!node.getAtomMatcher().matches(atom))
      {
        return false;
      }
    }

    return true;
  }
}
