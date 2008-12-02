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

import com.metamolecular.mx.model.AtomicMassSystem;
import com.metamolecular.mx.model.Isotope;
import com.metamolecular.mx.model.Measurement;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class AtomicMassSystemTest extends TestCase
{

  private AtomicMassSystem system;

  @Override
  protected void setUp() throws Exception
  {
    system = AtomicMassSystem.getInstance();
  }

  public void testItShouldFindAnAtomicNumber()
  {
    int atomicNumber = system.getAtomicNumber("H");

    assertEquals(1, atomicNumber);
  }

  public void testItShouldFindAllIsotopes()
  {
    List<Isotope> isotopes = system.getIsotopes("H");

    assertEquals(2, isotopes.size());
  }
  
  public void testItShouldFindAnAverageMass()
  {
    Measurement averageMass = system.getAverageMass("H");
    
    assertEquals(1.00794, averageMass.getValue());
    assertEquals(0.00007, averageMass.getError());
    assertEquals("u", averageMass.getUnits());
  }
  
  public void testItShouldFindAnIsotopeWithAllProperties()
  {
    Isotope isotope = system.getIsotope("H", 1);
    
    assertEquals(1, isotope.getMassNumber());
    
    Measurement mass = isotope.getMass();
    
    assertNotNull(mass);
    assertEquals(1.0078250319, mass.getValue());
    assertEquals(0.00000000006, mass.getError());
    assertEquals("u", mass.getUnits());
    
    Measurement abundance = isotope.getAbundance();
    
    assertNotNull(abundance);
    assertEquals(0.999885, abundance.getValue());
    assertEquals(0.000070, abundance.getError());
    assertEquals("percent", abundance.getUnits());
  }
}
