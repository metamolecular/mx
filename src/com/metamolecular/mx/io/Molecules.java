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
package com.metamolecular.mx.io;

import com.metamolecular.mx.model.*;

/**
 * @author Richard L. Apodaca
 * @author Duan Lian
 */
public class Molecules
{

  private Molecules()
  {
  }

  public static Molecule createMethane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");

    return result;
  }

  public static Molecule createPropane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);

    return result;
  }

  public static Molecule createCyclopropane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c2, c0, 1);

    return result;
  }

  public static Molecule createHexane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c2, c3, 1);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 1);

    return result;
  }

  public static Molecule createCyclohexane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c2, c3, 1);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 1);
    result.connect(c5, c0, 1);

    return result;
  }

  public static Molecule createBenzene()
  {
    Molecule result = new DefaultMolecule();
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");

    result.connect(c1, c2, 1);
    result.connect(c2, c3, 2);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 2);
    result.connect(c5, c6, 1);
    result.connect(c6, c1, 2);

    return result;
  }

  public static Molecule createPyridine()
  {
    Molecule result = new DefaultMolecule();
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("N");

    result.connect(c1, c2, 1);
    result.connect(c2, c3, 2);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 2);
    result.connect(c5, c6, 1);
    result.connect(c6, c1, 2);

    return result;
  }

  public static Molecule createToluene()
  {
    Molecule result = new DefaultMolecule();
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");
    Atom c7 = result.addAtom("C");

    result.connect(c1, c2, 1);
    result.connect(c2, c3, 2);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 2);
    result.connect(c5, c6, 1);
    result.connect(c6, c1, 2);
    result.connect(c7, c1, 1);

    return result;
  }

  public static Molecule createPhenol()
  {
    Molecule result = new DefaultMolecule();
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");
    Atom c7 = result.addAtom("O");

    result.connect(c1, c2, 1);
    result.connect(c2, c3, 2);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 2);
    result.connect(c5, c6, 1);
    result.connect(c6, c1, 2);
    result.connect(c7, c1, 1);

    return result;
  }

  public static Molecule createNaphthalene()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");
    Atom c7 = result.addAtom("C");
    Atom c8 = result.addAtom("C");
    Atom c9 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 2);
    result.connect(c2, c3, 1);
    result.connect(c3, c4, 2);
    result.connect(c4, c5, 1);
    result.connect(c5, c0, 2);
    result.connect(c4, c6, 1);
    result.connect(c6, c7, 2);
    result.connect(c7, c8, 1);
    result.connect(c8, c9, 2);
    result.connect(c9, c5, 1);

    return result;
  }

  public static Molecule createAcetone()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom o3 = result.addAtom("O");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c1, o3, 2);

    return result;
  }

  public static Molecule createNeopentane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c0, c2, 1);
    result.connect(c0, c3, 1);
    result.connect(c0, c4, 1);

    return result;
  }

  public static Molecule createCubane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    Atom c6 = result.addAtom("C");
    Atom c7 = result.addAtom("C");

    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c2, c3, 1);
    result.connect(c3, c0, 1);

    result.connect(c4, c5, 1);
    result.connect(c5, c6, 1);
    result.connect(c6, c7, 1);
    result.connect(c7, c4, 1);

    result.connect(c0, c4, 1);
    result.connect(c1, c5, 1);
    result.connect(c2, c6, 1);
    result.connect(c3, c7, 1);

    return result;
  }

  public static Molecule createBicyclo220hexane()
  {
    Molecule result = new DefaultMolecule();
    Atom c0 = result.addAtom("C");
    Atom c1 = result.addAtom("C");
    Atom c2 = result.addAtom("C");
    Atom c3 = result.addAtom("C");
    Atom c4 = result.addAtom("C");
    Atom c5 = result.addAtom("C");
    
    result.connect(c0, c1, 1);
    result.connect(c1, c2, 1);
    result.connect(c2, c3, 1);
    result.connect(c3, c4, 1);
    result.connect(c4, c5, 1);
    result.connect(c5, c0, 1);
    result.connect(c2, c5, 1);
    
    return result;
  }

  public static Molecule createEthylbenzeneWithSuperatom()
  {
    Molecule result = Molecules.createBenzene();
    Atom carbon1=result.addAtom("C");
    Atom carbon2=result.addAtom("C");
    Bond crossingBond=result.connect(result.getAtom(0),carbon1,1);
    result.connect(carbon1,carbon2,1);

    Superatom substructure=result.addSuperatom();
    substructure.addAtom(carbon1);
    substructure.addAtom(carbon2);
    substructure.addCrossingBond(crossingBond);
    substructure.setCrossingVector(crossingBond,0.1,0.1);
    substructure.setLabel("Ethyl");

    return result;
  }

}
