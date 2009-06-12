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
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.MoleculeKit;
import com.metamolecular.mx.map.DefaultMapper;
import com.metamolecular.mx.query.DefaultQuery;
import com.metamolecular.mx.map.Mapper;
import com.metamolecular.mx.query.Node;
import com.metamolecular.mx.query.Query;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Richard L. Apodaca
 */
public class DefaultQueryMapperTest extends TestCase
{

  private Molecule hexane;
  private Molecule benzene;
  private Molecule pyridine;
  private Molecule toluene4;
  private Query benzeneQuery;
  private Query hexaneQuery;
  private Molecule pyridazine;
  private Query pyridazineQuery;
  private Molecule naphthalene;
  private Molecule chlorobenzene;
  private Query chlorobenzeneQuery;
  private Molecule chloroisoquinoline4;
  private Query pyridineQuery;
  private Molecule toluene;
  private Molecule phenol;
  private Query tolueneQuery;
  private Molecule acetone;
  private Query acetoneQuery;
  private Molecule propane;
  private Query propaneQuery;
  private Molecule cyclopropane;

  @Override
  protected void setUp() throws Exception
  {
    hexane = Molecules.createHexane();
    benzene = Molecules.createBenzene();
    pyridine = Molecules.createPyridine();
    toluene4 = create4Toluene();
    pyridazine = MoleculeKit.readMolfile("[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n  6  6  0  0  0  0  0  0  0  0  0 V2000\r\n    1.8322   -5.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6982   -5.5815    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.5643   -5.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.5643   -4.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6982   -3.5815    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.8322   -4.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  2  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  2  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  2  0  0  0  0\r\n  6  1  1  0  0  0  0\r\nM  END");
    chloroisoquinoline4 = MoleculeKit.readMolfile("[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n 11 12  0  0  0  0  0  0  0  0  0 V2000\r\n    0.8800   -1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.7460   -2.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6121   -1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6121   -0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.7460   -0.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    0.8800   -0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.4781   -2.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.3442   -1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.3442   -0.7400    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.4781   -0.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.4781   -3.2400    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  2  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  2  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  2  0  0  0  0\r\n  6  1  1  0  0  0  0\r\n  3  7  1  0  0  0  0\r\n  7  8  2  0  0  0  0\r\n  8  9  1  0  0  0  0\r\n  9 10  2  0  0  0  0\r\n 10  4  1  0  0  0  0\r\n  7 11  1  0  0  0  0\r\nM  END");
    chlorobenzene = MoleculeKit.readMolfile("[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n  7  7  0  0  0  0  0  0  0  0  0 V2000\r\n   -3.3359    0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.4699    0.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.6038    0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.6038    1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.4699    2.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -3.3359    1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -0.7378    2.2400    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  2  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  2  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  2  0  0  0  0\r\n  6  1  1  0  0  0  0\r\n  4  7  1  0  0  0  0\r\nM  END");
    benzeneQuery = new DefaultQuery(benzene);
    chlorobenzeneQuery = new DefaultQuery(chlorobenzene);
    hexaneQuery = new DefaultQuery(hexane);
    pyridazineQuery = new DefaultQuery(pyridazine);
    naphthalene = Molecules.createNaphthalene();
    pyridineQuery = new DefaultQuery(pyridine);
    toluene = Molecules.createToluene();
    phenol = Molecules.createPhenol();
    tolueneQuery = new DefaultQuery(toluene);
    acetone = Molecules.createAcetone();
    acetoneQuery = new DefaultQuery(acetone);
    propane = Molecules.createPropane();
    propaneQuery = new DefaultQuery(propane);
    cyclopropane = Molecules.createCyclopropane();
  }

  public void testItShouldMatchHexaneToHexane()
  {
    Mapper mapper = new DefaultMapper(hexaneQuery);

    assertTrue(mapper.hasMap(hexane));
  }
  
  public void testItShouldMatchHexaneToHexaneWhenUsingMolecule()
  {
    Mapper mapper = new DefaultMapper(hexane);
    
    assertTrue(mapper.hasMap(hexane));
  }

  public void testItShouldMatchBenzeneToBenzene()
  {
    Mapper mapper = new DefaultMapper(benzeneQuery);

    assertTrue(mapper.hasMap(benzene));
  }

  public void testItShouldNotMatchHexaneToBenzene()
  {
    Mapper mapper = new DefaultMapper(hexaneQuery);

    assertFalse(mapper.hasMap(benzene));
  }

  public void testItShouldNotMatchPyridazineToNaphthalene()
  {
    Mapper mapper = new DefaultMapper(pyridazineQuery);

    assertFalse(mapper.hasMap(naphthalene));
  }

  public void testItShouldNotMatchChlorobenzeneTo4ChloroIsoquinoline()
  {
    Mapper mapper = new DefaultMapper(chlorobenzeneQuery);

    assertFalse(mapper.hasMap(chloroisoquinoline4));
  }

  public void testItShouldNotMatchBenzeneToPyridine()
  {
    Mapper mapper = new DefaultMapper(benzeneQuery);

    assertFalse(mapper.hasMap(pyridine));

    mapper = new DefaultMapper(pyridineQuery);

    assertFalse(mapper.hasMap(benzene));
  }

  public void testItShouldNotMatchTolueneToBenzene()
  {
    Mapper mapper = new DefaultMapper(tolueneQuery);

    assertFalse(mapper.hasMap(benzene));
  }

  public void testItShouldMatchAcetoneToAcetone()
  {
    Mapper mapper = new DefaultMapper(acetoneQuery);

    assertTrue(mapper.hasMap(acetone));
  }

  public void testItShouldMatchPropaneToCyclopropane()
  {
    Mapper mapper = new DefaultMapper(propaneQuery);

    assertTrue(mapper.hasMap(cyclopropane));
  }

  public void testItShouldFindTwoMapsFromHexaneToHexane()
  {
    Mapper mapper = new DefaultMapper(hexaneQuery);

    List<Map<Node, Atom>> maps = mapper.getMaps(hexane);

    assertEquals(2, maps.size());
  }

  public void testItShouldNotMatchTolueneToPhenol()
  {
    Mapper mapper = new DefaultMapper(tolueneQuery);

    assertFalse(mapper.hasMap(phenol));
  }

  public void testItShouldMapSixAtomsOfBenzeneOntoBenzene()
  {
    Mapper mapper = new DefaultMapper(benzeneQuery);
    Map<Node, Atom> map = mapper.getFirstMap(benzene);

    assertEquals(6, map.size());
  }

  public void testItShouldCountTwelveMapsForBenzeneOntoBenzene()
  {
    Mapper mapper = new DefaultMapper(benzeneQuery);

    assertEquals(12, mapper.countMaps(benzene));
  }

  public void testItShouldCountTwoMapsForTolueneOntoToluene()
  {
    Mapper mapper = new DefaultMapper(tolueneQuery);

    assertEquals(2, mapper.countMaps(toluene));
  }

  public void testItShouldFindTwelveMapsForBenzeneOntoBenzene()
  {
    Mapper mapper = new DefaultMapper(benzeneQuery);
    List<Map<Node, Atom>> maps = mapper.getMaps(benzene);

    assertEquals(12, maps.size());
  }

  public void testItShouldFindTwentyFourMapsForBenzeneOntoNaphthalene()
  {
    Mapper mapper = new DefaultMapper(benzeneQuery);
    List<Map<Node, Atom>> maps = mapper.getMaps(naphthalene);

    assertEquals(24, maps.size());
  }

  public void testItShouldFindAMapForEquivalentFormsOfToluene()
  {
    Mapper mapper = new DefaultMapper(tolueneQuery);
    Map<Node, Atom> map = mapper.getFirstMap(toluene4);

    assertEquals(7, map.size());
  }

  public void testItShouldFindTwoMapsForEquivalentFormsOfToluene()
  {
    Mapper mapper = new DefaultMapper(tolueneQuery);
    List<Map<Node, Atom>> maps = mapper.getMaps(toluene4);

    assertEquals(2, maps.size());
  }

  private Molecule create4Toluene()
  {
    Molecule result = new DefaultMolecule();
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");
    Atom c7 = result.addAtom("C");

    result.connect(c1, c2, 1);
    result.connect(c2, c3, 2);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 2);
    result.connect(c5, c6, 1);
    result.connect(c6, c1, 2);
    result.connect(c7, c4, 1);

    return result;
  }
}
