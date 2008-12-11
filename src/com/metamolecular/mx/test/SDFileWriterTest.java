/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.io.mdl.SDFileWriter;
import com.metamolecular.mx.model.Molecule;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class SDFileWriterTest extends TestCase
{
  public void testItShouldWriteASingleMoleculWithoutData() throws Exception
  {
    SDFileWriter writer = new SDFileWriter("ItShouldWriteASingleMoleculWithoutData.sdf");
    Molecule benzene = Molecules.createBenzene();
    
    writer.writeMolecule(benzene);
    writer.close();
  }
  
  public void testItShouldWriteASingleRecordWithDataAndMolfile() throws Exception
  {
    SDFileWriter writer = new SDFileWriter("testItShouldWriteASingleRecordWithDataAndMolfile");
    Molecule benzene = Molecules.createBenzene();
    
    writer.writeMolecule(benzene);
    writer.writeData("key", "value");
    writer.close();
  }
}
