/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
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
import com.metamolecular.mx.map.DefaultMapper;
import com.metamolecular.mx.map.Mapper;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.MoleculeKit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class MapperTest extends TestCase
{
  private Molecule benzene;
  private Molecule toluene;
  private Molecule toluene4;
  private Molecule phenol;
  private Molecule pyridine;
  private Molecule chloroisoquinoline4;
  private Molecule chlorobenzene;
  private Molecule acetone;
  private Molecule hexane;
  private Molecule naphthalene;
  private Molecule propane;
  private Molecule cyclopropane;
  private Molecule pyridazine;

  @Override
  protected void setUp() throws Exception
  {
    benzene = Molecules.createBenzene();
    toluene = Molecules.createToluene();
    toluene4 = create4Toluene();
    phenol = Molecules.createPhenol();
    pyridine = Molecules.createPyridine();
    chloroisoquinoline4 = MoleculeKit.readMolfile("[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n 11 12  0  0  0  0  0  0  0  0  0 V2000\r\n    0.8800   -1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.7460   -2.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6121   -1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6121   -0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.7460   -0.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    0.8800   -0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.4781   -2.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.3442   -1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.3442   -0.7400    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.4781   -0.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.4781   -3.2400    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  2  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  2  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  2  0  0  0  0\r\n  6  1  1  0  0  0  0\r\n  3  7  1  0  0  0  0\r\n  7  8  2  0  0  0  0\r\n  8  9  1  0  0  0  0\r\n  9 10  2  0  0  0  0\r\n 10  4  1  0  0  0  0\r\n  7 11  1  0  0  0  0\r\nM  END");
    chlorobenzene = MoleculeKit.readMolfile("[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n  7  7  0  0  0  0  0  0  0  0  0 V2000\r\n   -3.3359    0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.4699    0.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.6038    0.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.6038    1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.4699    2.2400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -3.3359    1.7400    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -0.7378    2.2400    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  2  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  2  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  2  0  0  0  0\r\n  6  1  1  0  0  0  0\r\n  4  7  1  0  0  0  0\r\nM  END");
    pyridazine = MoleculeKit.readMolfile("[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n  6  6  0  0  0  0  0  0  0  0  0 V2000\r\n    1.8322   -5.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6982   -5.5815    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.5643   -5.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.5643   -4.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.6982   -3.5815    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.8322   -4.0815    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  2  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  2  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  2  0  0  0  0\r\n  6  1  1  0  0  0  0\r\nM  END");
    acetone = Molecules.createAcetone();
    hexane = Molecules.createHexane();
    naphthalene = Molecules.createNaphthalene();
    propane = Molecules.createPropane();
    cyclopropane = Molecules.createCyclopropane();
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

  public void testItShouldMatchBenzeneToBenzene()
  {
    Mapper mapper = new DefaultMapper(benzene);

    assertTrue(mapper.hasMap(benzene));
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

    List<Map<Atom, Atom>> maps = mapper.getMaps(hexane);

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
    Map<Atom, Atom> map = mapper.getFirstMap(benzene);

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
    List<Map<Atom, Atom>> maps = mapper.getMaps(benzene);

    assertTrue(containsAutomap(maps, benzene));
    assertEquals(12, maps.size());
  }

  public void testItShouldFindTwoMapsForTolueneOntoToluene()
  {
    Mapper mapper = new DefaultMapper(toluene);
    List<Map<Atom, Atom>> maps = mapper.getMaps(toluene);

    assertTrue(containsAutomap(maps, toluene));
    assertEquals(2, maps.size());
    assertFalse(maps.get(0).equals(maps.get(1)));
  }

  public void testItShouldFindTwelveMapsForBenzeneOntoToluene()
  {
    Mapper mapper = new DefaultMapper(benzene);
    List<Map<Atom, Atom>> maps = mapper.getMaps(toluene);

    assertEquals(12, maps.size());
  }
  
  public void testItShouldFindTwentyFourMapsForBenzeneOntoNaphthalene()
  {
    Mapper mapper = new DefaultMapper(benzene);
    List<Map<Atom, Atom>> maps = mapper.getMaps(naphthalene);
    
    assertEquals(24, maps.size());
  }

  public void testItShouldFindAMapForEquivalentFormsOfToluene()
  {
    Mapper mapper = new DefaultMapper(toluene4);
    Map<Atom, Atom> map = mapper.getFirstMap(toluene);

    assertEquals(7, map.size());
  }

  public void testItShouldFindTwoMapsForEquivalentFormsOfToluene()
  {
    Mapper mapper = new DefaultMapper(toluene4);
    List<Map<Atom, Atom>> maps = mapper.getMaps(toluene);

    assertEquals(2, maps.size());
    assertTrue(maps.get(0).get(toluene4.getAtom(6)).countVirtualHydrogens() == 3);
    assertFalse(containsAutomap(maps, toluene4));
  }

  private boolean containsAutomap(List<Map<Atom, Atom>> maps, Molecule molecule)
  {
    Map<Atom, Atom> map = new HashMap<Atom, Atom>();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);

      map.put(atom, atom);
    }

    return maps.contains(map);
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
