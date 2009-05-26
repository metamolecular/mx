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
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class MolfileReaderTest extends TestCase
{
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
    sgroup = "N-Boc piperazine.mol\n  ChemDraw05180908572D\n\n 13 13  0  0  0  0  0  0  0  0999 V2000\n    1.7862   -0.4678    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.5006   -0.8803    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.0717   -0.8803    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    0.3572   -0.4678    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n    0.7697    0.2467    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -0.0553   -1.1822    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -0.3572   -0.0553    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.0717   -0.4678    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.7862   -0.0553    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.7862    0.7697    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.5006    1.1822    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.0717    1.1822    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -0.3572    0.7697    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  1  2  1  0      \n  3  4  1  0      \n  4  5  2  0      \n  4  6  2  0      \n  4  7  1  0      \n  7  8  1  0      \n  8  9  2  0      \n  9 10  1  0      \n 10 11  1  0      \n 10 12  2  0      \n 12 13  1  0      \n  7 13  2  0      \n  1  3  1  0      \nM  STY  1   1 SUP\nM  SLB  1   1   1\nM  SAL   1 11   3   4   5   6   7   8   9  10  11  12  13\nM  SBL   1  1  13\nM  SMT   1 ^OTs\nM  SCL   1  \nM  SBV   1  13    0.7145    0.4125\nM  END";
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
    assertEquals(1,mr.countSgroups());
  }

}
