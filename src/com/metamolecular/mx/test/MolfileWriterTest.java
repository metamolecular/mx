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
package com.metamolecular.mx.test;

import junit.framework.TestCase;
import com.metamolecular.mx.model.*;
import com.metamolecular.mx.io.Molecules;

/**
 * @author Duan Lian
 */
public class MolfileWriterTest extends TestCase
{
    public void testWriter()
    {
        Molecule benzene = Molecules.createBenzene();
        Atom carbon1=benzene.addAtom("C");
        Atom carbon2=benzene.addAtom("C");
        Bond crossingBond=benzene.connect(benzene.getAtom(0),carbon1,1);
        benzene.connect(carbon1,carbon2,1);

        Substructure substructure=benzene.addSubstructure();
        substructure.addAtom(carbon1);
        substructure.addAtom(carbon2);
        substructure.addCrossingBond(crossingBond);
        substructure.setCrossingVector(crossingBond,0.1,0.1);
        substructure.setLabel("Ethyl");

        System.out.println(MoleculeKit.writeMolfile(benzene));

    }
}
