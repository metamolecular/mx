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
import com.metamolecular.mx.io.mdl.SDFileWriter;
import com.metamolecular.mx.model.Molecule;
import java.io.File;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class SDFileWriterTest extends TestCase
{

  private String dir;
  private File outputDirectory;

  public SDFileWriterTest()
  {
    dir = "test" + File.separator + getClass().getSimpleName().replace("Test", "");
    outputDirectory = new File(dir);

    outputDirectory.mkdir();
    cleanDir(outputDirectory);
  }

  public void testItShouldWriteASingleMoleculeWithoutData() throws Exception
  {
    SDFileWriter writer = new SDFileWriter(getFilename());
    Molecule benzene = Molecules.createBenzene();

    writer.writeMolecule(benzene);
    writer.close();
  }

  public void testItShouldWriteASingleRecordWithDataAndMolfile() throws Exception
  {
    SDFileWriter writer = new SDFileWriter(getFilename());
    Molecule benzene = Molecules.createBenzene();

    writer.writeMolecule(benzene);
    writer.writeData("key", "value");
    writer.close();
  }

  public void testItShouldWriteTwoRecordsWithDataAndMolfile() throws Exception
  {
    SDFileWriter writer = new SDFileWriter(getFilename());
    Molecule benzene = Molecules.createBenzene();
    
    for (int i = 0; i < 2; i++)
    {
      writer.writeMolecule(benzene);
      writer.writeData("key", "value");
      writer.nextRecord();
    }

    writer.close();
  }

  private String getFilename()
  {
    StackTraceElement[] stack = new Throwable().getStackTrace();
    String methodName = stack[1].getMethodName().replace("test", "");

    return dir + File.separator + methodName + ".sdf";
  }

  private void cleanDir(File dir)
  {
    File[] children = dir.listFiles();

    for (File file : children)
    {
      file.delete();
    }
  }
}
