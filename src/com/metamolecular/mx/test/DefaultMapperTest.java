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
import com.metamolecular.mx.io.daylight.SMILESReader;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.MoleculeKit;
import com.metamolecular.mx.map.DefaultMapper;
import com.metamolecular.mx.map.Mapper;
import com.metamolecular.mx.query.Node;
import com.metamolecular.mx.query.Query;
import com.metamolecular.mx.query.TemplateCompiler;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Richard L. Apodaca
 */
public class DefaultMapperTest extends TestCase
{
  private Molecule hexane;
  private Molecule benzene;
  private Molecule pyridine;
  private Molecule toluene4;
  private Molecule pyridazine;
  private Molecule naphthalene;
  private Molecule chlorobenzene;
  private Molecule chloroisoquinoline4;
  private Molecule toluene;
  private Molecule phenol;
  private Molecule acetone;
  private Molecule propane;
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
    naphthalene = Molecules.createNaphthalene();
    toluene = Molecules.createToluene();
    phenol = Molecules.createPhenol();
    acetone = Molecules.createAcetone();
    propane = Molecules.createPropane();
    cyclopropane = Molecules.createCyclopropane();
  }

  public void testItShouldMatchHexaneToHexane()
  {
    Mapper mapper = new DefaultMapper(hexane);

    assertTrue(mapper.hasMap(hexane));
  }

  public void testItShouldMatchHexaneToHexaneWhenUsingMolecule()
  {
    Mapper mapper = new DefaultMapper(hexane);

    assertTrue(mapper.hasMap(hexane));
  }

  public void testItShouldMatchBenzeneToBenzene()
  {
    Mapper mapper = new DefaultMapper(benzene);

    assertTrue(mapper.hasMap(benzene));
  }

  public void testItShouldNotMatchHexaneToBenzene()
  {
    Mapper mapper = new DefaultMapper(hexane);

    assertFalse(mapper.hasMap(benzene));
  }

  public void testItShouldNotMatchPyridazineToNaphthalene()
  {
    Mapper mapper = new DefaultMapper(pyridazine);

    assertFalse(mapper.hasMap(naphthalene));
  }

  public void testItShouldNotMatchChlorobenzeneTo4ChloroIsoquinoline()
  {
    Mapper mapper = new DefaultMapper(chlorobenzene);

    assertFalse(mapper.hasMap(chloroisoquinoline4));
  }

  public void testItShouldNotMatchBenzeneToPyridine()
  {
    Mapper mapper = new DefaultMapper(benzene);

    assertFalse(mapper.hasMap(pyridine));

    mapper = new DefaultMapper(pyridine);

    assertFalse(mapper.hasMap(benzene));
  }

  public void testItShouldNotMatchTolueneToBenzene()
  {
    Mapper mapper = new DefaultMapper(toluene);

    assertFalse(mapper.hasMap(benzene));
  }

  public void testItShouldMatchAcetoneToAcetone()
  {
    Mapper mapper = new DefaultMapper(acetone);

    assertTrue(mapper.hasMap(acetone));
  }

  public void testItShouldMatchPropaneToCyclopropane()
  {
    Mapper mapper = new DefaultMapper(propane);

    assertTrue(mapper.hasMap(cyclopropane));
  }

  public void testItShouldFindTwoMapsFromHexaneToHexane()
  {
    Mapper mapper = new DefaultMapper(hexane);

    List<Map<Node, Atom>> maps = mapper.getMaps(hexane);

    assertEquals(2, maps.size());
  }

  public void testItShouldNotMatchTolueneToPhenol()
  {
    Mapper mapper = new DefaultMapper(toluene);

    assertFalse(mapper.hasMap(phenol));
  }

  public void testItShouldMapSixAtomsOfBenzeneOntoBenzene()
  {
    Mapper mapper = new DefaultMapper(benzene);
    Map<Node, Atom> map = mapper.getFirstMap(benzene);

    assertEquals(6, map.size());
  }

  public void testItShouldCountTwelveMapsForBenzeneOntoBenzene()
  {
    Mapper mapper = new DefaultMapper(benzene);

    assertEquals(12, mapper.countMaps(benzene));
  }

  public void testItShouldCountTwoMapsForTolueneOntoToluene()
  {
    Mapper mapper = new DefaultMapper(toluene);

    assertEquals(2, mapper.countMaps(toluene));
  }

  public void testItShouldFindTwelveMapsForBenzeneOntoBenzene()
  {
    Mapper mapper = new DefaultMapper(benzene);
    List<Map<Node, Atom>> maps = mapper.getMaps(benzene);

    assertEquals(12, maps.size());
  }

  public void testItShouldFindTwentyFourMapsForBenzeneOntoNaphthalene()
  {
    Mapper mapper = new DefaultMapper(benzene);
    List<Map<Node, Atom>> maps = mapper.getMaps(naphthalene);

    assertEquals(24, maps.size());
  }

  public void testItShouldFindAMapForEquivalentFormsOfToluene()
  {
    Mapper mapper = new DefaultMapper(toluene);
    Map<Node, Atom> map = mapper.getFirstMap(toluene4);

    assertEquals(7, map.size());
  }

  public void testItShouldFindTwoMapsForEquivalentFormsOfToluene()
  {
    Mapper mapper = new DefaultMapper(toluene);
    List<Map<Node, Atom>> maps = mapper.getMaps(toluene4);

    assertEquals(2, maps.size());
  }

  public void testItMapsBlockedPropaneOntoPropane()
  {
    Molecule blockedPropane = Molecules.createPropane();

    blockedPropane.connect(blockedPropane.addAtom("H"), blockedPropane.getAtom(1), 1);

    Mapper mapper = new DefaultMapper(blockedPropane);

    assertTrue(mapper.hasMap(propane));
  }

  public void testItMapsBlockedBenzaldehydeOntoBenzaldehyde()
  {
    Molecule blockedBenzaldehyde = this.createBlockedBenzaldehyde();
    Mapper mapper = new DefaultMapper(blockedBenzaldehyde);

    assertTrue(mapper.hasMap(createBenzaldehyde()));
  }

  public void testItDoesntMapBlockedBenzaldehydeOntoBenzoicAcid()
  {
    Molecule blockedBenzaldehyde = this.createBlockedBenzaldehyde();
    Mapper mapper = new DefaultMapper(blockedBenzaldehyde);

    assertFalse(mapper.hasMap(createBenzoicAcid()));
  }

  public void testDoesntMapImineToAmine()
  {
    Mapper mapper = new DefaultMapper(createSimpleImine());
    Map<Node, Atom> map = mapper.getFirstMap(createSimpleAmine());
    
    assertEquals(0, map.size());
  }
  
  private Molecule createSimpleImine()
  {
    Molecule result = new DefaultMolecule();
    result.connect(result.addAtom("C"), result.addAtom("N"), 2);
    
    return result;
  }
  
  private Molecule createSimpleAmine()
  {
    Molecule result = new DefaultMolecule();
    result.connect(result.addAtom("C"), result.addAtom("N"), 1);
    
    return result;
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

  private Molecule createBenzaldehyde()
  {
    Molecule result = new DefaultMolecule();
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");
    Atom c7 = result.addAtom("C");
    Atom o8 = result.addAtom("O");

    result.connect(c1, c2, 1);
    result.connect(c2, c3, 2);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 2);
    result.connect(c5, c6, 1);
    result.connect(c6, c1, 2);
    result.connect(c7, c1, 1);
    result.connect(c7, o8, 2);

    return result;
  }

  private Molecule createBenzoicAcid()
  {
    Molecule result = createBenzaldehyde();

    result.connect(result.getAtom(6), result.addAtom("O"), 1);

    return result;
  }

  private Molecule createBlockedBenzaldehyde()
  {
    Molecule result = createBenzaldehyde();

    result.connect(result.getAtom(6), result.addAtom("H"), 1);

    return result;
  }
}
