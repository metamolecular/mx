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

import com.metamolecular.mx.io.mdl.MolfileReader;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.Bond;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class MolfileReaderTest extends TestCase
{
  //TODO: test invalid substructure-containing molfile throws expected
  //      exceptions (~3-4 different cases)
  
  //TODO: test files using unimplemented portions of the sgroup spec (e.g.,
  //      polymers) throw exception (~2-3 different cases)
  
  private String TEMPO = null;
  private String methylRadical = null;
  private String sgroup = null;
  private MolfileReader reader = null;
  
  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    
    reader = new MolfileReader();
    methylRadical = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n  1  0  0  0  0  0  0  0  0  0  0 V2000\n    6.4000   -6.2000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\nM  RAD  1   1   1\nM  END";
    TEMPO = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n 11 11  0  0  0  0  0  0  0  0  0 V2000\n    7.3600   -6.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2260   -7.1800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.0921   -6.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.0921   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2260   -5.1800    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    7.3600   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.5921   -4.8140    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.0921   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.8600   -4.8140    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.3600   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2260   -4.1800    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n  1  2  1  0  0  0  0\n  2  3  1  0  0  0  0\n  3  4  1  0  0  0  0\n  4  5  1  0  0  0  0\n  5  6  1  0  0  0  0\n  6  1  1  0  0  0  0\n  4  7  1  0  0  0  0\n  4  8  1  0  0  0  0\n  6  9  1  0  0  0  0\n  6 10  1  0  0  0  0\n  5 11  1  0  0 \nM  RAD  1  11   1\nM  END";
    sgroup = "\n  -ISIS-  05190920462D\n\n 27 30  0  0  0  0  0  0  0  0999 V2000\n    7.0400   -1.2042    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.3256   -1.6167    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.3256   -2.4417    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    7.0400   -2.8542    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    7.7545   -2.4417    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    7.7545   -1.6167    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.5409   -1.3618    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.0560   -2.0292    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.5409   -2.6966    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    4.2310   -2.0292    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.8185   -1.3147    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.9935   -1.3147    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.5810   -2.0292    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.9935   -2.7437    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.8185   -2.7437    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.4690   -1.2042    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    7.0400   -3.6792    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.3256   -4.0917    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.6119   -4.5042    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    1.7560   -2.0292    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    0.9330   -2.0292    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.5750   -3.4542    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.7495   -3.4450    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.7337   -4.8715    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.5592   -4.8806    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.3311   -4.1514    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.9821   -4.1697    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  7  8  2  0  0  0  0\n  8  9  1  0  0  0  0\n  9  3  1  0  0  0  0\n  8 10  1  0  0  0  0\n 10 11  2  0  0  0  0\n 11 12  1  0  0  0  0\n 12 13  2  0  0  0  0\n 13 14  1  0  0  0  0\n 14 15  2  0  0  0  0\n 15 10  1  0  0  0  0\n 13 20  1  0  0  0  0\n  6 16  1  0  0  0  0\n  4 17  1  0  0  0  0\n 17 18  1  0  0  0  0\n 18 19  3  0  0  0  0\n 14 22  1  0  0  0  0\n  1  2  2  0  0  0  0\n 20 21  1  0  0  0  0\n  2  3  1  0  0  0  0\n 22 23  2  0  0  0  0\n  3  4  2  0  0  0  0\n 24 25  1  0  0  0  0\n  4  5  1  0  0  0  0\n  5  6  2  0  0  0  0\n  6  1  1  0  0  0  0\n  2  7  1  0  0  0  0\n 22 27  1  0  0  0  0\n 23 26  1  0  0  0  0\n 26 24  2  0  0  0  0\n 25 27  2  0  0  0  0\nG   20 13\nOMe\nG   22 14\nPh\nM  STY  2   1 SUP   2 SUP\nM  SLB  2   1   1   2   2\nM  SAL   1  2  20  21\nM  SBL   1  1  11\nM  SMT   1 OMe\nM  SBV   1  11    0.8200    0.0000\nM  SAL   2  6  22  23  24  25  26  27\nM  SBL   2  1  16\nM  SMT   2 Ph\nM  SBV   2  16    0.4200    0.7100\nM  END";
  }
  
  public void testItShouldReadSingletMethylRadical()
  {
    Molecule mr = new DefaultMolecule();
    
    reader.read(mr, methylRadical);
    
    assertEquals(1, mr.countAtoms());
    assertEquals(1, mr.getAtom(0).getRadical());
  }
  
  public void testItShouldReadTheOxygenCenteredRadicalInNMO()
  {
    Molecule tempo = new DefaultMolecule();
    
    reader.read(tempo, TEMPO);
    
    Atom o = null;
    
    for (int i = 0; i < tempo.countAtoms(); i++)
    {
      Atom test = tempo.getAtom(i);

      if (test.getSymbol().equals("O"))
      {
        o = test;
        
        break;
      }
    }
    
    assertNotNull(o);
    assertEquals(1, o.getRadical());
  }

  public void testSgroupReading()
  {
    Molecule mr = new DefaultMolecule();
    reader.read(mr, sgroup);
    assertEquals(2,mr.countSgroups());

    assertEquals(2,mr.getSgroup(0).countAtoms());
    assertEquals(1,mr.getSgroup(0).countBonds());
    assertEquals("OMe",mr.getSgroup(0).getLabel());


    assertEquals(6,mr.getSgroup(1).countAtoms());
    assertEquals(1,mr.getSgroup(1).countBonds());
    assertEquals("Ph",mr.getSgroup(1).getLabel());


    Bond connectBond = mr.getSgroup(0).getCrossingBond(0);
    assertEquals(0.8200, mr.getSgroup(0).getCrossingVectorX(connectBond),0.01);
    assertEquals(0.0000, mr.getSgroup(0).getCrossingVectorY(connectBond),0.01);
  }

}
