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

package com.metamolecular.mx.test;

import com.metamolecular.mx.io.smiles.SMILESReader;
import com.metamolecular.mx.model.DefaultMolecule;
import com.metamolecular.mx.model.Molecule;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class SMILESReaderTest extends TestCase
{
  private SMILESReader reader;

  @Override
  protected void setUp() throws Exception
  {
    reader = new SMILESReader();
  }
  
  public void testItShouldReadALinearChain()
  {
    Molecule input = new DefaultMolecule();
    
    reader.read(input, "CCCCCC");
    
    assertEquals(6, input.countAtoms());
    assertEquals(5, input.countBonds());
  }
  
  public void testItShouldReadACycle()
  {
    Molecule input = new DefaultMolecule();
    
    reader.read(input, "C1CCCCC1");
    
    assertEquals(6, input.countAtoms());
    assertEquals(6, input.countBonds());
  }
  
  public void testItShouldReadCubane()
  {
    Molecule input = new DefaultMolecule();
    
    reader.read(input, "C12C3C4C1C5C4C3C25");
    
    assertEquals(8, input.countAtoms());
  }
  
  public void testItShouldThrowWhenGivenIllegalAtomSymbol()
  {
    Molecule input = new DefaultMolecule();
    
    try
    {
      reader.read(input, "C!C");
      fail();
    }
    
    catch (IllegalArgumentException ignore)
    {
    }
  }
}