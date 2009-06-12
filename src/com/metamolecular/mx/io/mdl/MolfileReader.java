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

import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.Superatom;

/**
 * @author Richard L. Apodaca
 */
public class MolfileReader
{

  private boolean readChargesInAtomBlock;

  public MolfileReader()
  {
    readChargesInAtomBlock = false;
  }

  public void read(Molecule mol, String molfile)
  {
    if ("".equals(molfile))
    {
      mol.clear();

      return;
    }

    LineNumberReader bufferedReader = getBufferedReader(molfile);

    mol.beginModify();

    try
    {
      setup(molfile);
      readHeader(mol, bufferedReader);
      int startLine = readConnectionTable(mol, bufferedReader);
      readProperties(mol, bufferedReader, startLine);
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    }

    mol.endModify();
  }

  private void setup(String molfile)
  {
    if (molfile.indexOf("M  CHG") != -1)
    {
      readChargesInAtomBlock = false;
    }
    else
    {
      readChargesInAtomBlock = true;
    }
  }

  private void readHeader(Molecule mol, LineNumberReader reader) throws IOException
  {
    readLine(reader);
    readLine(reader);
    readLine(reader);
  }

  private int readConnectionTable(Molecule mol, LineNumberReader reader) throws IOException
  {
    String countsLine = MDLStringKit.padRight(readLine(reader), 84);
    int atomCount = 0;

    try
    {
      atomCount = MDLStringKit.extractInt(countsLine, 0, 3);
    } catch (Exception e)
    {
      throw new RuntimeException("Error parsing atom count on line 4:\n" + countsLine);
    }

    int bondCount = 0;

    try
    {
      bondCount = MDLStringKit.extractInt(countsLine, 3, 6);
    } catch (Exception e)
    {
      throw new RuntimeException("Error parsing bond count in counts line:\n" + countsLine);
    }

    String[] atomBlock = readLines(atomCount, reader);
    String[] bondBlock = readLines(bondCount, reader);

    createAtoms(mol, atomBlock, 5);
    createBonds(mol, bondBlock, 5 + atomCount);

    return 5 + atomCount + bondCount;
  }

  private void createAtoms(Molecule mol, String[] lines, int startLine)
  {
    for (int i = 0; i < lines.length; i++)
    {
      String line = MDLStringKit.padRight(lines[i], 69);

      try
      {
        createAtom(mol, line);
      } catch (Exception e)
      {
        throw new RuntimeException("Can't parse line " + (startLine + i) + " as atom.\n" + e.getLocalizedMessage() + "\n" + line, e);
      }
    }
  }

  private void createAtom(Molecule mol, String line)
  {
    float x = MDLStringKit.extractFloat(line, 0, 10);
    float y = MDLStringKit.extractFloat(line, 10, 20);
    float z = MDLStringKit.extractFloat(line, 20, 30);
    String symbol = MDLStringKit.extractString(line, 31, 34);

    Atom atom = mol.addAtom(symbol, x, y, z);

    if (readChargesInAtomBlock)
    {
      int chargeType = MDLStringKit.extractInt(line, 36, 39);
      int charge = 0;

      switch (chargeType)
      {
        case 0:
          break;
        case 1:
          charge = 3;
          break;
        case 2:
          charge = 2;
          break;
        case 3:
          charge = 1;
          break;
        case 5:
          charge = -1;
          break;
        case 6:
          charge = -2;
          break;
        case 7:
          charge = -3;
          break;
      }

      if (charge != 0)
      {
        atom.setCharge(charge);
      //mol.setCharge(atom, charge);
      }
    }
  }

  private void createBonds(Molecule mol, String[] lines, int startLine) throws IOException
  {
    for (int i = 0; i < lines.length; i++)
    {
      String line = lines[i];

      try
      {
        createBond(mol, line);
      } catch (Exception e)
      {
        throw new RuntimeException("Can't parse line " + (i + startLine) + " as bond.\n" + e.getLocalizedMessage() + "\n" + line, e);
      }
    }
  }

  private void createBond(Molecule mol, String line)
  {
    int source = MDLStringKit.extractInt(line, 0, 3);
    int target = MDLStringKit.extractInt(line, 3, 6);
    int bondType = MDLStringKit.extractInt(line, 6, 9);
    int stereo = MDLStringKit.extractInt(line, 9, 12);

    mol.connect(mol.getAtom(source - 1), mol.getAtom(target - 1), bondType, stereo);
  }

  private void readProperties(Molecule mol, LineNumberReader reader, int startLine) throws IOException
  {
    createProperties(mol, readLinesUntil("M  END", reader), startLine);
  }

  private void createProperties(Molecule mol, String[] lines, int startLine)
  {
    for (int i = 0; i < lines.length; i++)
    {
      try
      {
        //Support for old version ISIS abbreviations
        if (lines[i].startsWith("G"))
        {
          createGroupOldVersion(mol, lines[i], lines[i++]);
        }
        else if (lines[i].matches("M  (CHG|RAD|ISO).*"))
        {
          createAtomProperty(mol, lines[i]);
        }
        else if (lines[i].matches("M  (STY|SLB|SAL|SBL|SMT|SBV).*"))
        {
          createSgroupProperty(mol, lines[i]);
        }

      } catch (Exception e)
      {
        throw new RuntimeException("Can't create property at line " + (startLine + i) + ".\n" + e.getLocalizedMessage() + "\n" + lines[i]);
      }
    }
  }

  private void createGroupOldVersion(Molecule mol, String line, String nextLine)
  {
    //does nothing
  }

  private void createSgroupProperty(Molecule mol, String line)
  {
    String property = MDLStringKit.extractString(line, 0, 6);
    if ("M  STY".equals(property))
    {
      int entryCount = MDLStringKit.extractInt(line, 6, 9);
      for (int i = 0; i < entryCount; i++)
      {
        int offset = i * 8;
        int sgroupIndex = MDLStringKit.extractInt(line, offset + 10, offset + 13);
        String sgroupType = MDLStringKit.extractString(line, offset + 14, offset + 17);
        if (!"SUP".equals(sgroupType))
        {
          throw new RuntimeException("Error parsing Substructure, only Superatom is supported.");
        }
        mol.addSuperatom();
      }
    }
    else if ("M  SLB".equals(property))
    {//        Ignore Identifier

//          int entryCount = MDLStringKit.extractInt(line, 6, 9);
//          for (int i = 0; i < entryCount; i++)
//          {
//            int offset = i * 8;
//            int sgroupIndex = MDLStringKit.extractInt(line, offset + 10, offset + 13);
//            int sgroupIdentifier = MDLStringKit.extractInt(line, offset + 14, offset + 17);
//            Substructure substructure = mol.getSubstructure(sgroupIndex-1);
//          }
      }
    else if ("M  SAL".equals(property))
    {
      int sgroupIndex = MDLStringKit.extractInt(line, 6, 10);
      Superatom substructure = mol.getSuperatom(sgroupIndex - 1);
      int entryCount = MDLStringKit.extractInt(line, 10, 13);
      for (int i = 0; i < entryCount; i++)
      {
        int offset = i * 4;
        int atomIndex = MDLStringKit.extractInt(line, offset + 13, offset + 17);
        substructure.addAtom(mol.getAtom(atomIndex - 1));
      }
    }
    else if ("M  SBL".equals(property))
    {
      int sgroupIndex = MDLStringKit.extractInt(line, 6, 10);
      Superatom substructure = mol.getSuperatom(sgroupIndex - 1);
      int entryCount = MDLStringKit.extractInt(line, 10, 13);
      for (int i = 0; i < entryCount; i++)
      {
        int offset = i * 4;
        int bondIndex = MDLStringKit.extractInt(line, offset + 13, offset + 17);
        substructure.addCrossingBond(mol.getBond(bondIndex - 1));
      }
    }
    else if ("M  SMT".equals(property))
    {
      int sgroupIndex = MDLStringKit.extractInt(line, 6, 10);
      Superatom substructure = mol.getSuperatom(sgroupIndex - 1);
      String label = MDLStringKit.extractString(line, 10, line.length());
      substructure.setLabel(label);
    }
    else if ("M  SBV".equals(property))
    {
      int sgroupIndex = MDLStringKit.extractInt(line, 6, 10);
      Superatom substructure = mol.getSuperatom(sgroupIndex - 1);
      int bondIndex = MDLStringKit.extractInt(line, 10, 14);
      double x = MDLStringKit.extractFloat(line, 14, 24);
      double y = MDLStringKit.extractFloat(line, 24, 34);
      substructure.setCrossingVector(mol.getBond(bondIndex - 1), x, y);
    }
  }

  private void createAtomProperty(Molecule mol, String line)
  {
    String property = MDLStringKit.extractString(line, 0, 6);
    int entryCount = MDLStringKit.extractInt(line, 6, 9);

    for (int i = 0; i < entryCount; i++)
    {
      int offset = i * 8;
      int atomIndex = MDLStringKit.extractInt(line, offset + 10, offset + 13);
      int value = MDLStringKit.extractInt(line, offset + 14, offset + 17);
      Atom atom = mol.getAtom(atomIndex - 1);

      if ("M  CHG".equals(property))
      {
        setChargeProperty(mol, atom, value);
      }
      else if ("M  RAD".equals(property))
      {
        setRadicalProperty(mol, atom, value);
      }
      else if ("M  ISO".equals(property))
      {
        setIsotopeProperty(mol, atom, value);
      }
    }
  }

  private void setChargeProperty(Molecule mol, Atom atom, int charge)
  {
    atom.setCharge(charge);
  }

  private void setRadicalProperty(Molecule mol, Atom atom, int radical)
  {
    atom.setRadical(radical);
  }

  private void setIsotopeProperty(Molecule mol, Atom atom, int isotope)
  {
    atom.setIsotope(isotope);
  }

  private LineNumberReader getBufferedReader(String molfile)
  {
    LineNumberReader result =
            new LineNumberReader(new StringReader(molfile));

    return result;
  }

  private String readLine(LineNumberReader input) throws IOException
  {
    String line = input.readLine();

    if (line == null)
    {
      throw new IOException("Unexpected EOF at line " + input.getLineNumber() + ".\n");
    }

    /*
    if (line.length() > 80)
    {
    throw new RuntimeException("Line exceeds 80 characters in length at line " + input.getLineNumber() + ".\n" + line);
    }
     */

    return line;
  }

  private String[] readLines(int count, LineNumberReader input) throws IOException
  {
    String[] result = new String[count];

    for (int i = 0; i < count; i++)
    {
      result[i] = readLine(input);
    }

    return result;
  }

  private String[] readLinesUntil(String stop, LineNumberReader input) throws IOException
  {
    List strings = new ArrayList();

    while (input.ready())
    {
      String line = null;

      try
      {
        line = readLine(input);
      } catch (IOException e)
      {
        throw new RuntimeException("Expected stop token \"" + stop + "\" not found.");
      }

      if (stop.equals(line))
      {
        break;
      }

      strings.add(line);
    }

    return (String[]) strings.toArray(new String[0]);
  }
}