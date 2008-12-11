/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    dir = "test/" + getClass().getSimpleName().replace("Test", "");
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
  
  private String getFilename()
  {
    StackTraceElement[] stack = new Throwable().getStackTrace();
    String methodName =  stack[1].getMethodName().replace("test", "");
    
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
