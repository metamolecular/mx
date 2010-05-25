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
import java.util.BitSet;
import junit.framework.TestCase;


/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathFingerprinterTest extends TestCase
{
  private PathFingerprinter fingerprinter;
  private String brevetoxinMolfile="[NO NAME]\r\n  CHEMWRIT          2D\r\nCreated with ChemWriter - http://metamolecular.com/chemwriter\r\n 79 89  0  0  0  0  0  0  0  0  0 V2000\r\n   -3.0354    1.3057    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -3.1845    0.3169    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.5043   -0.4162    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.5071   -0.3414    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -0.9438    0.4848    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.2386    1.4403    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.1694    1.8057    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -0.0130    0.1195    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n    0.8530    0.6194    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.0021    1.6083    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    0.3219    2.3413    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -0.6753    2.2666    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -3.8172    1.9292    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -4.7481    1.5638    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -4.8971    0.5750    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -4.1153   -0.0485    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -5.5299    2.1873    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -6.4608    1.8220    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -6.6098    0.8332    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -5.8280    0.2097    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -7.2426    2.4454    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -8.1734    2.0801    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -8.3225    1.0913    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -7.5407    0.4678    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.6349   -0.0040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.5657    0.3613    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.7147    1.3501    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.9329    1.9736    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.3475   -0.2622    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.2784    0.1032    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.4274    1.0920    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    3.6456    1.7154    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.8722   -0.7014    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    5.8610   -0.8505    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    6.6656   -0.2567    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    6.8146    0.7322    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    6.2208    1.5367    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    5.2320    1.6858    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n    7.8083    0.8441    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n    8.2082    1.7607    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    7.6144    2.5652    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    6.6207    2.4533    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    9.2019    1.8726    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    9.6018    2.7892    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    9.0080    3.5937    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    8.0143    3.4818    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   10.5955    2.9011    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   10.9953    3.8177    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   10.4015    4.6222    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    9.4078    4.5103    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -9.2533    0.7260    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -7.0935    3.4343    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -6.7588   -0.1557    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -5.0462   -0.4138    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -3.3335   -0.6719    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -0.4438   -0.3812    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    0.8530   -0.3805    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.4167   -0.6275    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.1293   -0.8857    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    7.5964    0.1087    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    8.8020    0.9561    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   10.1956    1.9846    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   11.9890    3.9296    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    8.8140    5.3149    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -6.3117    2.8108    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -4.5991    2.5526    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -2.8864    2.2945    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.5333    2.3959    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    1.1511    2.5971    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    2.8638    2.3389    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n    4.5765    2.0808    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    5.6270    2.3413    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    7.0206    3.3698    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\r\n    8.4141    4.3983    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   12.5828    3.1250    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   13.5765    3.2370    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   12.1829    2.2085    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n   13.9764    4.1536    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n   -1.0071   -1.2074    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n  1  2  1  0  0  0  0\r\n  2  3  1  0  0  0  0\r\n  3  4  1  0  0  0  0\r\n  4  5  1  0  0  0  0\r\n  5  6  1  0  0  0  0\r\n  6  7  1  0  0  0  0\r\n  7  1  1  0  0  0  0\r\n  5  8  1  0  0  0  0\r\n  8  9  1  0  0  0  0\r\n  9 10  1  0  0  0  0\r\n 10 11  1  0  0  0  0\r\n 11 12  1  0  0  0  0\r\n 12  6  1  0  0  0  0\r\n  1 13  1  0  0  0  0\r\n 13 14  1  0  0  0  0\r\n 14 15  1  0  0  0  0\r\n 15 16  1  0  0  0  0\r\n 16  2  1  0  0  0  0\r\n 14 17  1  0  0  0  0\r\n 17 18  1  0  0  0  0\r\n 18 19  1  0  0  0  0\r\n 19 20  1  0  0  0  0\r\n 20 15  1  0  0  0  0\r\n 18 21  1  0  0  0  0\r\n 21 22  2  0  0  0  0\r\n 22 23  1  0  0  0  0\r\n 23 24  1  0  0  0  0\r\n 24 19  1  0  0  0  0\r\n  9 25  1  0  0  0  0\r\n 25 26  1  0  0  0  0\r\n 26 27  1  0  0  0  0\r\n 27 28  1  0  0  0  0\r\n 28 10  1  0  0  0  0\r\n 26 29  1  0  0  0  0\r\n 29 30  1  0  0  0  0\r\n 30 31  1  0  0  0  0\r\n 31 32  1  0  0  0  0\r\n 32 27  1  0  0  0  0\r\n 30 33  1  0  0  0  0\r\n 33 34  1  0  0  0  0\r\n 34 35  2  0  0  0  0\r\n 35 36  1  0  0  0  0\r\n 36 37  1  0  0  0  0\r\n 37 38  1  0  0  0  0\r\n 38 31  1  0  0  0  0\r\n 36 39  1  0  0  0  0\r\n 39 40  1  0  0  0  0\r\n 40 41  1  0  0  0  0\r\n 41 42  1  0  0  0  0\r\n 42 37  1  0  0  0  0\r\n 40 43  1  0  0  0  0\r\n 43 44  1  0  0  0  0\r\n 44 45  1  0  0  0  0\r\n 45 46  1  0  0  0  0\r\n 46 41  1  0  0  0  0\r\n 44 47  1  0  0  0  0\r\n 47 48  1  0  0  0  0\r\n 48 49  1  0  0  0  0\r\n 49 50  1  0  0  0  0\r\n 50 45  1  0  0  0  0\r\n 23 51  2  0  0  0  0\r\n 21 52  1  0  0  0  0\r\n 19 53  1  1  0  0  0\r\n 15 54  1  1  0  0  0\r\n  2 55  1  1  0  0  0\r\n  5 56  1  1  0  0  0\r\n  9 57  1  1  0  0  0\r\n 26 58  1  1  0  0  0\r\n 30 59  1  1  0  0  0\r\n 36 60  1  1  0  0  0\r\n 40 61  1  1  0  0  0\r\n 44 62  1  1  0  0  0\r\n 48 63  1  1  0  0  0\r\n 50 64  1  1  0  0  0\r\n 18 65  1  6  0  0  0\r\n 14 66  1  6  0  0  0\r\n  1 67  1  6  0  0  0\r\n  6 68  1  6  0  0  0\r\n 10 69  1  6  0  0  0\r\n 27 70  1  6  0  0  0\r\n 31 71  1  6  0  0  0\r\n 37 72  1  6  0  0  0\r\n 41 73  1  6  0  0  0\r\n 45 74  1  6  0  0  0\r\n 63 75  1  0  0  0  0\r\n 75 76  1  0  0  0  0\r\n 75 77  2  0  0  0  0\r\n 76 78  2  0  0  0  0\r\n  4 79  1  6  0  0  0\r\nM  END";

  @Override
  protected void setUp() throws Exception
  {
    fingerprinter = new PathFingerprinter();
  }

  public void testItStartsWithMaxDepthSeven()
  {
    assertEquals(7, fingerprinter.getMaximumPathDepth());
  }

  public void testItStartsWithRingBitsTwenty()
  {
    assertEquals(20, fingerprinter.getRingBitCount());
  }

  public void testItStartsWithFingerprintLength1024()
  {
    assertEquals(1024, fingerprinter.getFingerprintLength());
  }

  public void testItRaisesWhenReservingMoreRingBitsThanTotalBitCount()
  {
    try
    {
      fingerprinter.reserveRingBits(1025);
      fail();
    }
    catch (IllegalStateException e)
    {
    }
  }

  public void testItRaisesWhenSettingFingerprintLengthBelowRingBitCount()
  {
    try
    {
      fingerprinter.setFingerprintLength(19);
      fail();
    }
    catch (IllegalStateException e)
    {
    }
  }

  public void testItReturnsWithBrevetoxin()
  {
    Molecule bt = MoleculeKit.readMolfile(brevetoxinMolfile);
    fingerprinter.setMaximumRingSize(10);
    fingerprinter.getFingerprint(bt);
  }

  public void testItDoesntChangeFingerprintLengthAfterReservingRingBits()
  {
    fingerprinter.reserveRingBits(24);

    assertEquals(1024, fingerprinter.getFingerprintLength());
  }

//  public void testItIsCreatedWithACustomRingFilter()
//  {
//    RingFilter filter = mock(RingFilter.class);
//    PathFingerprinter custom = new PathFingerprinter(filter);
//
//    assertEquals(filter, custom.getRingFilter());
//  }

  public void testItShouldCreateANonemptyFingerprint()
  {
    Molecule benzene = Molecules.createBenzene();

    BitSet fingerprint = fingerprinter.getFingerprint(benzene);

    assertFalse(fingerprint.isEmpty());
  }

  public void testItCreatesA2024BitFingerprint()
  {
    BitSet fingerprint = fingerprinter.getFingerprint(Molecules.createBenzene());

    assertEquals(1024, fingerprint.size());
  }

  public void testItShouldReturnTheSameFingerprintForTheSameMolecule()
  {
    Molecule benzene = Molecules.createBenzene();
    BitSet firstFingerprint = fingerprinter.getFingerprint(benzene);

    for (int i = 0; i < 10; i++)
    {
      BitSet newFingerprint = fingerprinter.getFingerprint(benzene);

      assertEquals(firstFingerprint, newFingerprint);
    }
  }

  public void testItShouldFullyIntersectTheFingerprintDerivedFromASuperstructure()
  {
    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
    BitSet phenol = fingerprinter.getFingerprint(Molecules.createPhenol());

    assertTrue(match(benzene, phenol));
  }

  public void testItShouldNotFullyIntersectTheFingerprintDerivedFromASubstructure()
  {
    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
    BitSet phenol = fingerprinter.getFingerprint(Molecules.createPhenol());
    BitSet intersection = (BitSet) phenol.clone();

    intersection.and(benzene);

    assertFalse(match(phenol, benzene));
  }

  public void testItShouldCreateANonemptyFingerprintWhenPathDepthIsZero()
  {
    Molecule benzene = Molecules.createBenzene();

    fingerprinter.setMaximumPathDepth(0);

    BitSet fingerprint = fingerprinter.getFingerprint(benzene);

    assertFalse(fingerprint.isEmpty());
  }

  public void testItShouldProduceADifferentFingerprintWhenThePathDepthIsChanged()
  {
    Molecule naphthalene = Molecules.createNaphthalene();

    fingerprinter.setMaximumPathDepth(6);

    BitSet fp6 = fingerprinter.getFingerprint(naphthalene);

    fingerprinter.setMaximumPathDepth(0);

    BitSet fpall = fingerprinter.getFingerprint(naphthalene);

    assertFalse(fp6.equals(fpall));
  }

  /**
   * stopped here
   */
  public void testItShouldGiveAFingerprintFromCyclohexaneThatDoesntMatchOneFromBenzene()
  {
    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());

    assertFalse(match(benzene, cyclohexane));
  }

  public void testItGivesTheSameFingerprintFromOXyleneForAlternateKekuleForm()
  {
    BitSet xylene = fingerprinter.getFingerprint(createXylene());
    BitSet kekule = fingerprinter.getFingerprint(createKekuleXylene());

    assertEquals(xylene, kekule);
  }

  public void testItShouldGiveAFingerprintFromBenzeneThatDoesntMatchCyclohexane()
  {
    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());

    assertFalse(match(cyclohexane, benzene));
  }

  public void testItShouldGiveAFingerprintFromBenzeneThatMatchesOneFromPhenol()
  {
    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
    BitSet phenol = fingerprinter.getFingerprint(Molecules.createPhenol());

    assertTrue(match(benzene, phenol));
  }

  public void testItShouldMatchPropaneToHexane()
  {
    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
    BitSet hexane = fingerprinter.getFingerprint(Molecules.createHexane());

    assertTrue(match(propane, hexane));
  }

  public void testItShouldMatchBenzeneToNaphthalene()
  {
    BitSet benzene = fingerprinter.getFingerprint(Molecules.createBenzene());
    BitSet naphthalene = fingerprinter.getFingerprint(Molecules.createNaphthalene());

    assertTrue(match(benzene, naphthalene));
  }

  public void testItShouldNotMatchCyclohexaneToHexane()
  {
    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());
    BitSet hexane = fingerprinter.getFingerprint(Molecules.createHexane());

    assertFalse(match(cyclohexane, hexane));
  }

  public void testItShouldMatchHexaneToCyclohexane()
  {
    BitSet cyclohexane = fingerprinter.getFingerprint(Molecules.createCyclohexane());
    BitSet hexane = fingerprinter.getFingerprint(Molecules.createHexane());

    assertTrue(match(hexane, cyclohexane));
  }

  public void testItShouldNotMatchCyclopropaneToPropane()
  {
    BitSet cyclopropane = fingerprinter.getFingerprint(Molecules.createCyclopropane());
    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());

    assertFalse(match(cyclopropane, propane));
  }

  public void testItShouldMatchPropaneToCyclopropane()
  {
    BitSet cyclopropane = fingerprinter.getFingerprint(Molecules.createCyclopropane());
    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());

    assertTrue(match(propane, cyclopropane));
  }

  public void testItShouldNotMatchEtheneToEthyne()
  {
    BitSet ethene = fingerprinter.getFingerprint(createEthene());
    BitSet ethyne = fingerprinter.getFingerprint(createEthyne());

    assertFalse(match(ethene, ethyne));
  }

  public void testItDoesntMatchEthaneToEthyne()
  {
    BitSet ethane = fingerprinter.getFingerprint(createEthane());
    BitSet ethyne = fingerprinter.getFingerprint(createEthyne());

    assertFalse(match(ethane, ethyne));
  }

  public void testItMatchesPropaneToAcetone()
  {
    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
    BitSet acetone = fingerprinter.getFingerprint(Molecules.createAcetone());

    assertTrue(match(propane, acetone));
  }

  public void testItDoesntMatchCyclooctaneToOctane()
  {
    BitSet cyclooctane = fingerprinter.getFingerprint(createCyclooctane());
    BitSet octane = fingerprinter.getFingerprint(createOctane());

    assertFalse(match(cyclooctane, octane));
  }

  public void testItCreatesCorrectFingerprintAfterFingerprintLengthSet()
  {
    BitSet fp1 = fingerprinter.getFingerprint(Molecules.createNaphthalene());
    
    fingerprinter.setFingerprintLength(1024);

    BitSet fp2 = fingerprinter.getFingerprint(Molecules.createNaphthalene());

    assertEquals(fp1, fp2);
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