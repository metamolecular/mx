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

import junit.framework.TestCase;
import com.metamolecular.mx.model.*;
import com.metamolecular.mx.io.Molecules;

import java.io.*;

/**
 * @author Duan Lian
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class MolfileWriterTest extends TestCase
{
  public void testCommentLineShouldGiveMXNameAndURL() throws Exception
  {
    Molecule benzene = Molecules.createBenzene();
    String molfile = MoleculeKit.writeMolfile(benzene);
    LineNumberReader reader =
      new LineNumberReader(new StringReader(molfile));
    
    reader.readLine();
    reader.readLine();
    
    assertEquals("Created with MX - http://rapodaca.github.com/mx", reader.readLine());
  
  }

  public void testWriterOutputsSubstructure()
  {
    Molecule ethylbenzene = Molecules.createEthylbenzeneWithSuperatom();

    String molfile = MoleculeKit.writeMolfile(ethylbenzene);
    Molecule molecule = MoleculeKit.readMolfile(molfile);
    assertEquals(1, molecule.countSuperatoms());
  }

  public void testWriterOutputReaderInput() throws IOException
  {
    File[] molfiles = new File("../resources/molfiles").listFiles();
    for (File molfile : molfiles)
    {
      String content = null;
      content = getFileContent(molfile);
      Molecule molecule1 = MoleculeKit.readMolfile(content);
      String molfile1 = MoleculeKit.writeMolfile(molecule1);
      Molecule molecule2 = MoleculeKit.readMolfile(molfile1);
      String molfile2 = MoleculeKit.writeMolfile(molecule2);
      assertEquals(molfile1, molfile2);
    }
  }

  private String getFileContent(File file) throws IOException
  {
    StringBuffer stringBuffer = new StringBuffer();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = bufferedReader.readLine()) != null)
    {
      stringBuffer.append(line + "\n");
    }
    return stringBuffer.toString();
  }
}
