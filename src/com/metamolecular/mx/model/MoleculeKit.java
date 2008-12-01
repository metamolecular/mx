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

package com.metamolecular.mx.model;

import java.util.ArrayList;
import java.util.List;

import com.metamolecular.mx.io.mdl.MolfileReader;
import com.metamolecular.mx.io.mdl.MolfileWriter;

/**
 * @author Richard L. Apodaca
 */
public class MoleculeKit
{
  private static MolfileReader reader;
  private static MolfileWriter writer;
  private static VirtualHydrogenCounter hydrogenCounter;
  private static String hydrogen = "H";
  
  static
  {
    reader = new MolfileReader();
    writer = new MolfileWriter();
    hydrogenCounter = new VirtualHydrogenCounter();
  }
  
  /**
   * 
   */
  private MoleculeKit()
  {
    
  }

  public static Molecule readMolfile(String molfile)
  {
    Molecule result = new DefaultMolecule();
    
    reader.read(result, molfile.replaceAll("\\\\n", "\n"));
    
    return result;
  }
  
  public static Molecule readMolfile(String molfile, boolean virtualizeHydrogens)
  {
    Molecule mol = readMolfile(molfile);
    
    if (virtualizeHydrogens)
    {
      virtualizeHydrogens(mol);
    }
    
    return mol;
  }
  
  public static String writeMolfile(Molecule molecule)
  {
    return writer.write(molecule);
  }
  
  public static void virtualizeHydrogens(Molecule molecule)
  {
    List toStrip = new ArrayList();
    
    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      
      recordVirtualizableHydrogens(atom, toStrip);
    }
    
    if (toStrip.isEmpty()) return;
    
    molecule.beginModify();
    
    for (int i = 0; i < toStrip.size(); i++)
    {
      Atom atom = (Atom) toStrip.get(i);
      
      molecule.removeAtom(atom);
    }
    
    molecule.endModify();
  }
  
  public static boolean isVirtualizableHydrogen(Atom atom)
  {
    if (!hydrogen.equals(atom.getSymbol()))
    {
      return false;
    }
    
    if (atom.hasSingleIsotope() || atom.countNeighbors() != 1)
    {
      return false;
    }
    
    Bond[] bonds = atom.getBonds();
    
    return bonds[0].getStereo() == 0 && bonds[0].getType() == 1;
  }
  
  public static boolean isVirtualizableHydrogenBond(Bond bond)
  {
    if (!(hydrogen.equals(bond.getSource().getSymbol()) || hydrogen.equals(bond.getTarget().getSymbol())))
    {
      return false;
    }
    
    if (hydrogen.equals(bond.getSource().getSymbol()) && hydrogen.equals(bond.getTarget().getSymbol()))
    {
      return false;
    }
    
    return bond.getStereo() == 0 && bond.getType() == 1;
  }
  
  private static void recordVirtualizableHydrogens(Atom atom, List toStrip)
  {
    if (!hydrogenCounter.isVirtualizableHeavyAtom(atom)) return;
    
    Bond[] bonds = atom.getBonds();
    
    for (int i = 0; i < bonds.length; i++)
    {
      Bond bond = bonds[i];
      Atom neighbor = bond.getMate(atom);
      
      if (hydrogen.equals(neighbor.getSymbol()))
      {
        if (neighbor.hasSingleIsotope()) continue;
        if (bond.getStereo() != 0) continue;
        
        toStrip.add(neighbor);
      }
    }
  }
}