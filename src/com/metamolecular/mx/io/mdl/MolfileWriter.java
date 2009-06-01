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

package com.metamolecular.mx.io.mdl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.ArrayList;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;

/**
 * @author Richard L. Apodaca
 */
public class MolfileWriter
{
  private static String ZERO2 = " 0";
  private static String ZERO3 = "  0";
  private static String CHIRAL_OFF = ZERO3;
  private static String VERSION = " V2000";
  private static String NEWLINE = "\n";
  private static DecimalFormat COORD = new DecimalFormat("####0.0000");
  private static String BLANK = " ";
  private static String M = "M  ";
  private static String M_END = M + "END";
  
  static
  {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    
    symbols.setDecimalSeparator(".".charAt(0));
    COORD.setDecimalFormatSymbols(symbols);
  }
  
  public MolfileWriter()
  {
    
  }

  public String write(Molecule molecule)
  {
    Writer writer = new Writer();
    
    writeHeader(molecule, writer);
    writeCounts(molecule, writer);
    writeAtoms(molecule, writer);
    writeBonds(molecule, writer);
    writeProperties(molecule, writer);
    
    return writer.getString();
  }
  
  private void writeHeader(Molecule molecule, Writer writer)
  {
    writeName(molecule, writer);
    writeParameters(molecule, writer);
    writeComments(molecule, writer);
  }
  
  private void writeName(Molecule molecule, Writer writer)
  {
    writer.writeLine("[NO NAME]");
  }
  
  private void writeParameters(Molecule molecule, Writer writer)
  {
    writer.writeLine("  CHEMWRIT          2D");
  }
  
  private void writeComments(Molecule molecule, Writer writer)
  {
    writer.writeLine("Created with ChemWriter - http://metamolecular.com/chemwriter");
  }
  
  private void writeCounts(Molecule molecule, Writer writer)
  {
    writer.write(MDLStringKit.padLeft(String.valueOf(molecule.countAtoms()), 3));
    writer.write(MDLStringKit.padLeft(String.valueOf(molecule.countBonds()), 3));
    writer.write(ZERO3);
    writer.write(ZERO3);
    writer.write(CHIRAL_OFF);
    
    for (int i = 0; i < 5; i++)
    {
      writer.write(ZERO3);
    }
    
    writer.write(MDLStringKit.padLeft(String.valueOf(0), 3));
    writer.write(VERSION);
    writer.write(NEWLINE);
  }
  
  private void writeAtoms(Molecule molecule, Writer writer)
  {
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      
      writer.write(MDLStringKit.padLeft(COORD.format(atom.getX()), 10));
      writer.write(MDLStringKit.padLeft(COORD.format(atom.getY()), 10));
      writer.write(MDLStringKit.padLeft(COORD.format(atom.getZ()), 10));
      writer.write(BLANK);
      writer.write(MDLStringKit.padRight(atom.getSymbol().toString(), 3));
      writer.write(ZERO2);

      String chargeString = "0";
      
      switch (atom.getCharge())
      {
        case 1:  chargeString = "3"; break;
        case 2:  chargeString = "2"; break;
        case 3:  chargeString = "1"; break;
        case -1:  chargeString = "5"; break;
        case -2:  chargeString = "6"; break;
        case -3:  chargeString = "7"; break;
      }
      
      writer.write(MDLStringKit.padLeft(chargeString, 3));
      
      for (int j = 0; j < 10; j++)
      {
        writer.write(ZERO3);
      }
      
      writer.writeLine();
    }
  }
  
  private void writeBonds(Molecule molecule, Writer writer)
  {
    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);
      Atom source = bond.getSource();
      Atom target = bond.getTarget();
      int sourceIndex = source.getIndex() + 1;
      int targetIndex = target.getIndex() + 1;
      
      writer.write(MDLStringKit.padLeft(String.valueOf(sourceIndex), 3));
      writer.write(MDLStringKit.padLeft(String.valueOf(targetIndex), 3));
      writer.write(MDLStringKit.padLeft(String.valueOf(bond.getType()), 3));
      writer.write(MDLStringKit.padLeft(String.valueOf(bond.getStereo()), 3));
      
      for (int j = 0; j < 3; j++)
      {
        writer.write(ZERO3);
      }
      
      writer.writeLine();
    }
  }
  
  private void writeProperties(Molecule molecule, Writer writer)
  {
    writeChargeProperty(molecule, writer);
    writeIsotopicProperty(molecule, writer);
    writeRadicalProperty(molecule, writer);
    writeSgroupProperty(molecule, writer);

    writer.write(M_END);
  }
  
  private void writeChargeProperty(Molecule molecule, Writer writer)
  {
    List charges = new ArrayList();
    List row = new ArrayList();
    
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      if (molecule.getAtom(i).getCharge() != 0)
      {
        if (row.size() == 8)
        {
          charges.add(row);
          
          row = new ArrayList();
        }
        
        row.add(molecule.getAtom(i));
      }
    }
    
    if (!charges.contains(row) && !row.isEmpty()) charges.add(row);
    if (charges.isEmpty()) return;
    
    for (int i = 0; i < charges.size(); i++)
    {
      row = (List) charges.get(i);
      
      writer.write(M + "CHG" + MDLStringKit.padLeft(String.valueOf(row.size()), 3));
      
      for (int j = 0; j < row.size(); j++)
      {
        Atom atom = (Atom) row.get(j);
        int index = atom.getIndex() + 1;
        //int index = molecule.getAtomIndex(atom) + 1;
        int charge = atom.getCharge();
        
        writer.write(" " + MDLStringKit.padLeft(String.valueOf(index), 3) + " " + MDLStringKit.padLeft(String.valueOf(charge), 3));
      }
      
      writer.writeLine();
    }
  }
  
  private void writeIsotopicProperty(Molecule molecule, Writer writer)
  {

  }
  
  private void writeRadicalProperty(Molecule molecule, Writer writer)
  {
    
  }

  private void writeSgroupProperty(Molecule molecule, Writer writer)
  {
      int substructureCount = molecule.countSubstructures();
      if(substructureCount >0)
     {
         writer.write(M + "STY" + MDLStringKit.padLeft(String.valueOf(substructureCount), 3));
     }
     for(int i=0;i< substructureCount;i++){
         writer.write(MDLStringKit.padLeft(String.valueOf(i+1), 4));
         writer.write(MDLStringKit.padLeft("SUP", 4));    
     }

  }
  
  private class Writer
  {
    private StringBuffer buffer;
    
    private Writer()
    {
      buffer = new StringBuffer();
    }
    
    public void write(String string)
    {
      buffer.append(string);
    }
    
    public void writeLine(String string)
    {
      buffer.append(string + "\n");
    }
    
    public void writeLine()
    {
      buffer.append("\n");
    }
    
    public String getString()
    {
      return buffer.toString();
    }
  }
}