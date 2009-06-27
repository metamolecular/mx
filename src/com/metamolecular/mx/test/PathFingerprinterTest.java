/*
 * MX Cheminformatics Tools for Java
 *
 * Copyright (c) 2007-2008 Metamolecular, LLC
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

import com.metamolecular.mx.fingerprint.PathFingerprinter;
import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import java.util.BitSet;
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

  public void testItShouldCreateANonemptyFingerprint()
  {
    Molecule benzene = Molecules.createBenzene();

    BitSet fingerprint = fingerprinter.getFingerprint(benzene);

    assertFalse(fingerprint.isEmpty());
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

  public void testItMatchesPropaneToAcetone()
  {
    BitSet propane = fingerprinter.getFingerprint(Molecules.createPropane());
    BitSet acetone = fingerprinter.getFingerprint(Molecules.createAcetone());

    assertTrue(match(propane, acetone));
  }

  private boolean match(BitSet bitset, BitSet other)
  {
    BitSet intersection = (BitSet) other;

    intersection.and(bitset);

    return intersection.equals(bitset);
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