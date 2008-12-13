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
package com.metamolecular.mx.io.mdl;

import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.MoleculeKit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Richard L. Apodaca
 */
public class SDFileWriter
{
  private FileWriter file;
  private BufferedWriter writer;
  private int recordCount;

  public SDFileWriter(String filename) throws IOException
  {
    file = new FileWriter(filename);
    writer = new BufferedWriter(file);
    recordCount = 0;
  }

  public void writeMolecule(Molecule molecule)
  {
    String molfile = MoleculeKit.writeMolfile(molecule);

    try
    {
      writer.write(molfile);
      writer.newLine();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void writeData(String key, String value)
  {
    try
    {
      writer.write("> <" + key + ">");
      writer.newLine();
      writer.write(value);
      writer.newLine();
      writer.newLine();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void nextRecord()
  {
    try
    {
      writer.write("$$$$");
      writer.newLine();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }

    recordCount++;
  }

  public void close()
  {
    if (recordCount == 0)
    {
      nextRecord();
    }

    try
    {
      writer.flush();
      file.close();
    }
    catch (IOException e)
    {
      throw new RuntimeException("IO exception occurred while closing.", e);
    }
  }
}
