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

package com.metamolecular.mx.io.smiles;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Molecule;

/**
 * @author Richard L. Apodaca
 */
public class SMILESBuilder
{
  private Atom head;
  private int bondType;
  private Molecule molecule;

  public SMILESBuilder(Molecule molecule)
  {
    this.molecule = molecule;
    this.bondType = 1;
    head = null;
  }

  public void addHead(String token)
  {
    Atom atom = molecule.addAtom(token);
    
    if (head != null)
    {
      molecule.connect(head, atom, bondType);
    }
    
    head = atom;
    bondType = 1;
  }
  
  public void addBond(int type)
  {
    this.bondType = type;
  }
}
