/*
 * MX - Essential Cheminformatics
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com/mx
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
import com.metamolecular.mx.map.Mapper;
import com.metamolecular.mx.query.Node;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
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
  private Molecule bigmol;

  public DefaultMapperTest()
  {
    // see: http://gist.github.com/144912
    bigmol = MoleculeKit.readMolfile("241\n  -OEChem-07100913442D\n\n 67 72  0  0  1  0            999 V2000\n    5.3950   -3.1602    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n    4.9853   -3.8822    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n    5.4045   -4.6006    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n    6.2342   -4.5947    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n    6.6482   -3.8727    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n    6.2284   -3.1565    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    7.4799   -3.8687    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    4.9958   -5.3274    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    4.9776   -2.4425    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    4.1536   -3.8873    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    6.6567   -5.3161    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    5.3878   -1.7207    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    4.1459   -2.4468    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   13.2577   -4.4546    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n   13.6743   -3.7340    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n   13.2533   -3.0100    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n   12.4237   -3.0128    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n   12.0114   -3.7375    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n   12.4243   -4.4553    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   11.1796   -3.7385    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   13.6691   -2.2889    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   13.6766   -5.1737    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   14.5060   -3.7320    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   12.0081   -2.2941    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   13.2596   -5.8941    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   14.5084   -5.1726    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   12.8766   -8.3196    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   13.5177   -8.8032    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   13.1451   -7.5594    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n   14.1838   -8.3419    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   13.9476   -7.5771    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.8674   -8.3480    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.2145   -8.8214    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.6186   -7.5808    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    4.5139   -8.3857    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.2048   -9.6520    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    4.7732   -7.6213    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    4.4315   -6.8694    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   10.1699   -8.3375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.4138   -7.5717    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.3511   -8.7486    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.8181   -8.8045    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n   11.2181   -7.5717    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.2013   -6.7887    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.5504   -8.3375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   11.4662   -8.3375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   11.6152   -6.8705    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.4104   -6.0055    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2980   -7.5787    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    7.8973   -8.8158    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n   12.1571   -8.7382    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.1943   -5.2154    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    7.5009   -7.5787    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.5043   -6.7992    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    7.2456   -8.3375    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.7266   -5.2224    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    7.0932   -6.8775    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2910   -6.0161    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.5549   -8.7486    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.4931   -5.2328    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.0141   -5.2224    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   13.4995   -9.6348    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   14.2100  -10.0685    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.7211   -8.6383    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.5455   -9.4504    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   14.9696   -8.6151    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   14.4518   -6.9145    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n 16 17  1  0  0  0  0\n 17 18  1  0  0  0  0\n 18 19  1  0  0  0  0\n 32 33  1  0  0  0  0\n 32 34  1  0  0  0  0\n 33 35  2  0  0  0  0\n 33 36  1  0  0  0  0\n 34 37  1  0  0  0  0\n 37 38  2  0  0  0  0\n 35 37  1  0  0  0  0\n 19 14  1  0  0  0  0\n  3  4  1  0  0  0  0\n 18 20  1  1  0  0  0\n  2 10  1  6  0  0  0\n 16 21  1  1  0  0  0\n  4  5  1  0  0  0  0\n 14 22  1  1  0  0  0\n  4 11  1  6  0  0  0\n 15 23  1  6  0  0  0\n  5  6  1  0  0  0  0\n 17 24  1  6  0  0  0\n  9 12  1  0  0  0  0\n 22 25  1  0  0  0  0\n  6  1  1  0  0  0  0\n 22 26  2  0  0  0  0\n  9 13  2  0  0  0  0\n  5  7  1  1  0  0  0\n  1  2  1  0  0  0  0\n  3  8  1  1  0  0  0\n 27 28  1  0  0  0  0\n 27 29  1  0  0  0  0\n 28 30  2  0  0  0  0\n 39 40  2  0  0  0  0\n 39 41  1  0  0  0  0\n 39 42  1  0  0  0  0\n 40 43  1  0  0  0  0\n 40 44  1  0  0  0  0\n 41 45  1  0  0  0  0\n 42 46  1  0  0  0  0\n 43 47  1  0  0  0  0\n 44 48  1  0  0  0  0\n 45 49  2  0  0  0  0\n 45 50  1  0  0  0  0\n 46 51  1  0  0  0  0\n 48 52  1  0  0  0  0\n 49 53  1  0  0  0  0\n 49 54  1  0  0  0  0\n 50 55  1  0  0  0  0\n 52 20  1  0  0  0  0\n 52 56  2  0  0  0  0\n 53 57  1  0  0  0  0\n 54 58  1  0  0  0  0\n 55 59  1  0  0  0  0\n 58 60  1  0  0  0  0\n 59 32  2  0  0  0  0\n 60  7  1  0  0  0  0\n 60 61  2  0  0  0  0\n 43 46  2  0  0  0  0\n 53 55  2  0  0  0  0\n 51 27  2  0  0  0  0\n 29 31  1  0  0  0  0\n 28 62  1  0  0  0  0\n 30 31  1  0  0  0  0\n 62 63  2  0  0  0  0\n  2  3  1  0  0  0  0\n 35 64  1  0  0  0  0\n  1  9  1  1  0  0  0\n 64 65  2  0  0  0  0\n 14 15  1  0  0  0  0\n 30 66  1  0  0  0  0\n 15 16  1  0  0  0  0\n 31 67  2  0  0  0  0\nM  END");
  }

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

  public void testItMapsDimethylsulfideToChargelessDMSO()
  {
    Mapper mapper = new DefaultMapper(Molecules.createDimethylsulfide());

    assertTrue(mapper.hasMap(Molecules.createChargelessDMSO()));
  }

  public void testItMapsDimethylsulfideToChargedDMSO()
  {
    Mapper mapper = new DefaultMapper(Molecules.createDimethylsulfide());

    assertTrue(mapper.hasMap(Molecules.createChargedDMSO()));
  }

//  public void testItMapsChargelessDMSOToChargeledDMSO()
//  {
//    Mapper mapper = new DefaultMapper(Molecules.createChargelessDMSO());
//
//    assertTrue(mapper.hasMap(Molecules.createChargedDMSO()));
//  }
  public void testItMapsPropaneToAcetone()
  {
    Mapper mapper = new DefaultMapper(Molecules.createPropane());

    assertTrue(mapper.hasMap(Molecules.createAcetone()));
  }

  public void testDoesntMapImineToAmine()
  {
    Mapper mapper = new DefaultMapper(createSimpleImine());
    Map<Node, Atom> map = mapper.getFirstMap(createSimpleAmine());

    assertEquals(0, map.size());
  }

  public void testItMapsBigmolToItself()
  {
    Mapper mapper = new DefaultMapper(bigmol);

    assertEquals(bigmol.countAtoms(), mapper.getFirstMap(bigmol).size());
  }
  
  public void testBigmolHasOneMap()
  {
    Mapper mapper = new DefaultMapper(bigmol);
    
    assertEquals(1, mapper.countMaps(bigmol));
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
