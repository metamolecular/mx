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

import com.metamolecular.mx.fingerprint.PathFingerprinter;
import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.MoleculeKit;
import com.metamolecular.mx.ring.HanserRingFinder;
import com.metamolecular.mx.ring.RingFinder;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;


/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathFingerprinterTest extends TestCase
{
  private PathFingerprinter fingerprinter;

  @Override
  protected void setUp() throws Exception
  {
    fingerprinter = new PathFingerprinter();
  }

//  public void testItStartsWithMaxDepthSeven()
//  {
//    assertEquals(7, fingerprinter.getMaximumPathDepth());
//  }
//
//  public void testItStartsWithRingBitsTwenty()
//  {
//    assertEquals(20, fingerprinter.getRingBitCount());
//  }
//
//  public void testItStartsWithFingerprintLength1024()
//  {
//    assertEquals(1024, fingerprinter.getFingerprintLength());
//  }
//
//  public void testItRaisesWhenReservingMoreRingBitsThanTotalBitCount()
//  {
//    try
//    {
//      fingerprinter.reserveRingBits(1025);
//      fail();
//    }
//    catch (IllegalStateException e)
//    {
//    }
//  }
//
//  public void testItRaisesWhenSettingFingerprintLengthBelowRingBitCount()
//  {
//    try
//    {
//      fingerprinter.setFingerprintLength(19);
//      fail();
//    }
//    catch (IllegalStateException e)
//    {
//    }
//  }
//
//  public void testItDoesntChangeFingerprintLengthAfterReservingRingBits()
//  {
//    fingerprinter.reserveRingBits(24);
//
//    assertEquals(1024, fingerprinter.getFingerprintLength());
//  }
//
//  public void testItIsCreatedWithACustomRingFilter()
//  {
//    RingFilter filter = mock(RingFilter.class);
//    PathFingerprinter custom = new PathFingerprinter(filter);
//
//    assertEquals(filter, custom.getRingFilter());
//  }
//
//  public void testItShouldCreateANonemptyFingerprint()
//  {
//    Molecule benzene = Molecules.createBenzene();
//
//    BitSet fingerprint = fingerprinter.getFingerprint(benzene);
//
//    assertFalse(fingerprint.isEmpty());
//  }
//  
//  public void testItCreatesA2024BitFingerprint()
//  {
//    BitSet fingerprint = fingerprinter.getFingerprint(Molecules.createBenzene());
//    
//    assertEquals(1024, fingerprint.size());
//  }
//
//  public void testItShouldReturnTheSameFingerprintForTheSameMolecule()
//  {
//    Molecule benzene = Molecules.createBenzene();
//    BitSet firstFingerprint = fingerprinter.getFingerprint(benzene);
//
//    for (int i = 0; i < 10; i++)
//    {
//      BitSet newFingerprint = fingerprinter.getFingerprint(benzene);
//
//      assertEquals(firstFingerprint, newFingerprint);
//    }
//  }
//
//  public void testItShouldFullyIntersectTheFingerprintDerivedFromASuperstructure()
//  {
//    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
//    BitSet phenol = fingerprinter.getFingerprint(Molecules.createPhenol());
//
//    assertTrue(match(benzene, phenol));
//  }
//
//  public void testItShouldNotFullyIntersectTheFingerprintDerivedFromASubstructure()
//  {
//    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
//    BitSet phenol = fingerprinter.getFingerprint(Molecules.createPhenol());
//    BitSet intersection = (BitSet) phenol.clone();
//
//    intersection.and(benzene);
//
//    assertFalse(match(phenol, benzene));
//  }
//
//  public void testItShouldCreateANonemptyFingerprintWhenPathDepthIsZero()
//  {
//    Molecule benzene = Molecules.createBenzene();
//
//    fingerprinter.setMaximumPathDepth(0);
//
//    BitSet fingerprint = fingerprinter.getFingerprint(benzene);
//
//    assertFalse(fingerprint.isEmpty());
//  }
//
//  public void testItShouldProduceADifferentFingerprintWhenThePathDepthIsChanged()
//  {
//    Molecule naphthalene = Molecules.createNaphthalene();
//
//    fingerprinter.setMaximumPathDepth(6);
//
//    BitSet fp6 = fingerprinter.getFingerprint(naphthalene);
//
//    fingerprinter.setMaximumPathDepth(0);
//
//    BitSet fpall = fingerprinter.getFingerprint(naphthalene);
//
//    assertFalse(fp6.equals(fpall));
//  }
//
//  /**
//   * stopped here
//   */
//  public void testItShouldGiveAFingerprintFromCyclohexaneThatDoesntMatchOneFromBenzene()
//  {
//    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
//    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());
//
//    assertFalse(match(benzene, cyclohexane));
//  }
//
//  public void testItGivesTheSameFingerprintFromOXyleneForAlternateKekuleForm()
//  {
//    BitSet xylene = fingerprinter.getFingerprint(createXylene());
//    BitSet kekule = fingerprinter.getFingerprint(createKekuleXylene());
//
//    assertEquals(xylene, kekule);
//  }
//
//  public void testItShouldGiveAFingerprintFromBenzeneThatDoesntMatchCyclohexane()
//  {
//    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
//    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());
//
//    assertFalse(match(cyclohexane, benzene));
//  }
//
//  public void testItShouldGiveAFingerprintFromBenzeneThatMatchesOneFromPhenol()
//  {
//    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
//    BitSet phenol = fingerprinter.getFingerprint(Molecules.createPhenol());
//
//    assertTrue(match(benzene, phenol));
//  }
//
//  public void testItShouldMatchPropaneToHexane()
//  {
//    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
//    BitSet hexane = fingerprinter.getFingerprint(Molecules.createHexane());
//
//    assertTrue(match(propane, hexane));
//  }
//
//  public void testItShouldMatchBenzeneToNaphthalene()
//  {
//    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
//    BitSet naphthalene = fingerprinter.getFingerprint(Molecules.createNaphthalene());
//
//    assertTrue(match(benzene, naphthalene));
//  }
//
//  public void testItShouldNotMatchCyclohexaneToHexane()
//  {
//    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());
//    BitSet hexane = fingerprinter.getFingerprint(Molecules.createHexane());
//
//    assertFalse(match(cyclohexane, hexane));
//  }
//
//  public void testItShouldMatchHexaneToCyclohexane()
//  {
//    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());
//    BitSet hexane = fingerprinter.getFingerprint(Molecules.createHexane());
//
//    assertTrue(match(hexane, cyclohexane));
//  }
//
//  public void testItShouldNotMatchCyclopropaneToPropane()
//  {
//    BitSet cyclopropane = fingerprinter.getFingerprint(Molecules.createCyclopropane());
//    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
//
//    assertFalse(match(cyclopropane, propane));
//  }
//
//  public void testItShouldMatchPropaneToCyclopropane()
//  {
//    BitSet cyclopropane = fingerprinter.getFingerprint(Molecules.createCyclopropane());
//    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
//
//    assertTrue(match(propane, cyclopropane));
//  }
//
//  public void testItShouldNotMatchEtheneToEthyne()
//  {
//    BitSet ethene = fingerprinter.getFingerprint(createEthene());
//    BitSet ethyne = fingerprinter.getFingerprint(createEthyne());
//
//    assertFalse(match(ethene, ethyne));
//  }
//
//  public void testItDoesntMatchEthaneToEthyne()
//  {
//    BitSet ethane = fingerprinter.getFingerprint(createEthane());
//    BitSet ethyne = fingerprinter.getFingerprint(createEthyne());
//
//    assertFalse(match(ethane, ethyne));
//  }
//
//  public void testItMatchesPropaneToAcetone()
//  {
//    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
//    BitSet acetone = fingerprinter.getFingerprint(Molecules.createAcetone());
//
//    assertTrue(match(propane, acetone));
//  }
//  
//  public void testItDoesntMatchCyclooctaneToOctane()
//  {
//    BitSet cyclooctane = fingerprinter.getFingerprint(createCyclooctane());
//    BitSet octane = fingerprinter.getFingerprint(createOctane());
//    
//    assertFalse(match(cyclooctane, octane));
//  }
  
  public void testItWorks()
  {
    BitSet query = fingerprinter.getFingerprint(query());
    
    System.out.println("query");
    System.out.println(query);
    System.out.println("target");
    BitSet target = fingerprinter.getFingerprint(target());
    System.out.println(target);
    
    for (int i = 0; i < query.size(); i++)
    {
      if (query.get(i) == true && !target.get(i) == true)
      {
        System.out.println("missing: " + i);
      }
    }
//    Molecule foo = foo();
//    RingFinder f = new HanserRingFinder();
//    Collection<List<Atom>> rings = f.findRings(foo);
//    
//    for (List<Atom> ring : rings)
//    {
//      System.out.println(ring.size());
//      
//      for (Atom atom : ring)
//      {
//        System.out.print(atom.getIndex() + "-");
//      }
//      
//      System.out.println();
//    }
  }
  
  private Molecule target()
  {
    String m = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n 30 32  0  0  0  0  0  0  0  0  0 V2000\n    1.0607    2.5607    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    1.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.0607   -1.0607    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    2.5095   -0.6724    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.5702   -1.7331    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    4.9860   -1.2377    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.2561   -2.0357    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.4241   -3.5263    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.3634   -4.5869    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.8728   -4.4190    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.0748   -3.1489    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266   -0.4635    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n   -3.8083    0.7500    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.9230    1.7537    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.9230   -0.2537    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266    1.9635    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -3.3901    3.3901    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.3864    4.5048    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.8573    3.7020    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -5.3209    5.1286    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -6.7881    5.4404    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -7.2516    6.8670    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -6.2479    7.9817    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -8.7188    7.1789    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -9.1824    8.6055    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  -10.2042    6.9701    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -8.7712    5.6798    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    1.5000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n  2  1  2  0  0  0  0\n  3  2  1  0  0  0  0\n  4  3  1  0  0  0  0\n  5  4  2  0  0  0  0\n  6  5  1  0  0  0  0\n  7  6  1  0  0  0  0\n  8  7  1  0  0  0  0\n  9  8  1  0  0  0  0\n 10  9  1  0  0  0  0\n 11 10  1  0  0  0  0\n 12 11  1  0  0  0  0\n 12  6  1  0  0  0  0\n 13  3  1  0  0  0  0\n 14 13  1  0  0  0  0\n 15 14  1  0  0  0  0\n 16 15  1  0  0  0  0\n 17 15  1  0  0  0  0\n 18 15  1  0  0  0  0\n 19 18  1  0  0  0  0\n 20 19  2  0  0  0  0\n 21 19  1  0  0  0  0\n 22 21  1  0  0  0  0\n 23 22  1  0  0  0  0\n 24 23  1  0  0  0  0\n 25 24  2  0  0  0  0\n 26 24  1  0  0  0  0\n 27 26  1  0  0  0  0\n 28 26  1  0  0  0  0\n 29 26  1  0  0  0  0\n 30 18  1  0  0  0  0\n 30  2  1  0  0  0  0\n 30 13  1  0  0  0  0\nM  END";
//    String m= "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n 11 12  0  0  0  0  0  0  0  0  0 V2000\n    0.9900    4.9544    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.9900    3.5544    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.3900    3.5544    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    2.3900    4.9544    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    5.9443    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    3.3799    5.9443    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    0.5573    2.2229    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n    1.6900    1.4000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.8226    2.2229    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.6900    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.9374    0.7644    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  1  2  1  0  0  0  0\n  2  3  1  0  0  0  0\n  3  4  1  0  0  0  0\n  4  1  1  0  0  0  0\n  1  5  1  0  0  0  0\n  4  6  2  0  0  0  0\n  2  7  1  0  0  0  0\n  7  8  1  0  0  0  0\n  8  9  1  0  0  0  0\n  9  3  1  0  0  0  0\n  8 10  1  0  0  0  0\n  8 11  1  0  0  0  0\nM  END";
    
    return MoleculeKit.readMolfile(m);
  }
  
  private Molecule query()
  {
    String m = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n 11 12  0  0  0  0  0  0  0  0  0 V2000\n    0.9900    4.9544    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.9900    3.5544    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.3900    3.5544    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    2.3900    4.9544    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    5.9443    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    3.3799    5.9443    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    0.5573    2.2229    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n    1.6900    1.4000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.8226    2.2229    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.6900    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    2.9374    0.7644    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  1  2  1  0  0  0  0\n  2  3  1  0  0  0  0\n  3  4  1  0  0  0  0\n  4  1  1  0  0  0  0\n  1  5  1  0  0  0  0\n  4  6  2  0  0  0  0\n  2  7  1  0  0  0  0\n  7  8  1  0  0  0  0\n  8  9  1  0  0  0  0\n  9  3  1  0  0  0  0\n  8 10  1  0  0  0  0\n  8 11  1  0  0  0  0\nM  END";
//    String m = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n  7  8  0  0  0  0  0  0  0  0  0 V2000\n    0.0000    1.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266   -0.4635    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n   -3.8083    0.7500    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266    1.9635    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    1.5000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n  2  1  1  0  0  0  0\n  3  2  1  0  0  0  0\n  4  3  1  0  0  0  0\n  5  4  1  0  0  0  0\n  6  5  1  0  0  0  0\n  7  6  1  0  0  0  0\n  7  1  1  0  0  0  0\n  7  3  1  0  0  0  0\nM  END";
//    String m = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n  9 10  0  0  0  0  0  0  0  0  0 V2000\n    0.0000    1.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266   -0.4635    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n   -3.8083    0.7500    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.9230    1.7537    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.9230   -0.2537    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266    1.9635    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    1.5000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n  2  1  1  0  0  0  0\n  3  2  1  0  0  0  0\n  4  3  1  0  0  0  0\n  5  4  1  0  0  0  0\n  6  5  1  0  0  0  0\n  7  5  1  0  0  0  0\n  8  5  1  0  0  0  0\n  9  8  1  0  0  0  0\n  9  1  1  0  0  0  0\n  9  3  1  0  0  0  0\nM  END";
//    String m = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n 30 32  0  0  0  0  0  0  0  0  0 V2000\n    1.0607    2.5607    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    1.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    0.0000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    1.0607   -1.0607    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    2.5095   -0.6724    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.5702   -1.7331    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    4.9860   -1.2377    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.2561   -2.0357    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.4241   -3.5263    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    5.3634   -4.5869    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.8728   -4.4190    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    3.0748   -3.1489    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266   -0.4635    0.0000 S   0  0  0  0  0  0  0  0  0  0  0  0\n   -3.8083    0.7500    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.9230    1.7537    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.9230   -0.2537    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.9266    1.9635    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -3.3901    3.3901    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -2.3864    4.5048    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -4.8573    3.7020    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -5.3209    5.1286    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -6.7881    5.4404    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -7.2516    6.8670    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -6.2479    7.9817    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n   -8.7188    7.1789    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -9.1824    8.6055    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n  -10.2042    6.9701    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -8.7712    5.6798    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   -1.5000    1.5000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n  2  1  2  0  0  0  0\n  3  2  1  0  0  0  0\n  4  3  1  0  0  0  0\n  5  4  2  0  0  0  0\n  6  5  1  0  0  0  0\n  7  6  1  0  0  0  0\n  8  7  1  0  0  0  0\n  9  8  1  0  0  0  0\n 10  9  1  0  0  0  0\n 11 10  1  0  0  0  0\n 12 11  1  0  0  0  0\n 12  6  1  0  0  0  0\n 13  3  1  0  0  0  0\n 14 13  1  0  0  0  0\n 15 14  1  0  0  0  0\n 16 15  1  0  0  0  0\n 17 15  1  0  0  0  0\n 18 15  1  0  0  0  0\n 19 18  1  0  0  0  0\n 20 19  2  0  0  0  0\n 21 19  1  0  0  0  0\n 22 21  1  0  0  0  0\n 23 22  1  0  0  0  0\n 24 23  1  0  0  0  0\n 25 24  2  0  0  0  0\n 26 24  1  0  0  0  0\n 27 26  1  0  0  0  0\n 28 26  1  0  0  0  0\n 29 26  1  0  0  0  0\n 30 18  1  0  0  0  0\n 30  2  1  0  0  0  0\n 30 13  1  0  0  0  0\nM  END";
    
    return MoleculeKit.readMolfile(m);
  }

  private boolean match(BitSet bitset, BitSet other)
  {
    BitSet intersection = (BitSet) other;

    intersection.and(bitset);

    return intersection.equals(bitset);
  }
  
  private Molecule createOctane()
  {
    Molecule result = new DefaultMolecule();
    Atom first = result.addAtom("C");
    
    for (int i = 0; i < 7; i++)
    {
      Atom next = result.addAtom("C");
      result.connect(first, next, 1);
      
      first = next;
    }
    
    return result;
  }
  
  private Molecule createCyclooctane()
  {
    Molecule result = createOctane();
    
    result.connect(result.getAtom(0), result.getAtom(7), 1);
    
    return result;
  }

  private Molecule createEthane()
  {
    Molecule result = new DefaultMolecule();

    result.connect(result.addAtom("C"), result.addAtom("C"), 1);

    return result;
  }

  private Molecule createEthene()
  {
    Molecule result = new DefaultMolecule();

    result.connect(result.addAtom("C"), result.addAtom("C"), 2);

    return result;
  }

  private Molecule createEthyne()
  {
    Molecule result = createEthene();
    result.getBond(0).setType(3);

    return result;
  }

  private Molecule createXylene()
  {
    Molecule result = Molecules.createCyclohexane();
    result.getBond(result.getAtom(0), result.getAtom(1)).setType(2);
    result.getBond(result.getAtom(2), result.getAtom(3)).setType(2);
    result.getBond(result.getAtom(4), result.getAtom(5)).setType(2);

    result.connect(result.getAtom(0), result.addAtom("C"), 1);
    result.connect(result.getAtom(5), result.addAtom("C"), 1);

    return result;
  }

  private Molecule createKekuleXylene()
  {
    Molecule result = Molecules.createCyclohexane();

    result.getBond(result.getAtom(1), result.getAtom(2)).setType(2);
    result.getBond(result.getAtom(3), result.getAtom(4)).setType(2);
    result.getBond(result.getAtom(5), result.getAtom(0)).setType(2);

    result.connect(result.getAtom(0), result.addAtom("C"), 1);
    result.connect(result.getAtom(5), result.addAtom("C"), 1);

    return result;
  }
}