/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

  public SDFileWriter(String filename) throws IOException
  {
    file = new FileWriter(filename);
    writer = new BufferedWriter(file);
  }

  public void writeMolecule(Molecule molecule)
  {
    String molfile = MoleculeKit.writeMolfile(molecule);

    try
    {
      writer.write(molfile);
      writer.newLine();
    } catch (IOException e)
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
    } catch (IOException e)
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
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void close()
  {
    nextRecord();

    try
    {
      writer.flush();
      file.close();
    } catch (IOException e)
    {
      throw new RuntimeException("IO exception occurred while closing.", e);
    }
  }
}
