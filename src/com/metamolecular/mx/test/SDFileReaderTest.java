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

import com.metamolecular.mx.io.mdl.SDFileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class SDFileReaderTest extends TestCase
{
  private SDFileReader reader;
  private String[] keys =
  {
    "PUBCHEM_COMPOUND_CID", "PUBCHEM_COMPOUND_CANONICALIZED", "PUBCHEM_CACTVS_COMPLEXITY", "PUBCHEM_CACTVS_HBOND_ACCEPTOR", "PUBCHEM_CACTVS_HBOND_DONOR", "PUBCHEM_CACTVS_ROTATABLE_BOND", "PUBCHEM_CACTVS_SUBSKEYS", "PUBCHEM_IUPAC_OPENEYE_NAME", "PUBCHEM_IUPAC_CAS_NAME", "PUBCHEM_IUPAC_NAME", "PUBCHEM_IUPAC_SYSTEMATIC_NAME", "PUBCHEM_IUPAC_TRADITIONAL_NAME", "PUBCHEM_NIST_INCHI", "PUBCHEM_EXACT_MASS", "PUBCHEM_MOLECULAR_FORMULA", "PUBCHEM_MOLECULAR_WEIGHT", "PUBCHEM_OPENEYE_CAN_SMILES", "PUBCHEM_OPENEYE_ISO_SMILES", "PUBCHEM_CACTVS_TPSA", "PUBCHEM_MONOISOTOPIC_WEIGHT", "PUBCHEM_TOTAL_CHARGE", "PUBCHEM_HEAVY_ATOM_COUNT", "PUBCHEM_ATOM_DEF_STEREO_COUNT", "PUBCHEM_ATOM_UDEF_STEREO_COUNT", "PUBCHEM_BOND_DEF_STEREO_COUNT", "PUBCHEM_BOND_UDEF_STEREO_COUNT", "PUBCHEM_ISOTOPIC_ATOM_COUNT", "PUBCHEM_COMPONENT_COUNT", "PUBCHEM_CACTVS_TAUTO_COUNT", "PUBCHEM_BONDANNOTATIONS"
  };

  @Override
  protected void setUp() throws Exception
  {
    reader = new SDFileReader("../resources/pubchem_sample_33.sdf");
  }

  @Override
  protected void tearDown() throws Exception
  {
    reader.close();
  }

  public void testItShouldIterateOverEveryRecord()
  {
    int count = 0;

    while (reader.hasNextRecord())
    {
      reader.nextRecord();

      count++;
    }

    assertEquals(32, count);
  }

  public void testItShoudReadAllKeysInTheFirstRecord()
  {
    for (String key : keys)
    {
      assertFalse("".equals(reader.getData(key)));
    }
  }

  public void testItShouldFetchAllKeysForTheFirstRecord()
  {
    List<String> fields = reader.getKeys();

    assertEquals(Arrays.asList(keys), fields);
  }

  public void testItShouldReturnAnEmptyStringWhenKeyNotFound()
  {
    assertEquals("", reader.getData("PUBCHEM_FOO_BAR"));
  }

  public void testItShouldThrowIOExceptionWhenCreatedFromANonexistentFile()
  {
    try
    {
      new SDFileReader("bar.sdf");

      fail();
    }
    catch (IOException ignore)
    {
      assertTrue(true);
    }
  }
}
