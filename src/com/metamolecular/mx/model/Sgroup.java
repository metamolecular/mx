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
package com.metamolecular.mx.model;

/**
 * @author Duan Lian
 */
public interface Sgroup {
    public String getLabel();

    public void setLabel(String label);

    public int countAtoms();

    public int countBonds();

    public Atom getAtom(int index);

    public Bond getBond(int index);

    public void addAtom(Atom atom);

    public void removeAtom(Atom atom);

    public void addBond(Bond bond);

    public void removeBond(Bond bond);    

    public Bond getSuperatomBond();

    public void setSuperatomBond(Bond bond);

    public double getSuperatomBondX();

    public void setSuperatomBondX(double x);

    public double getSuperatomBondY();

    public void setSuperatomBondY(double y);

    public int getIdentifier();

    public void setIdentifier(int identifier);

    public Molecule getMolecule();    

}
