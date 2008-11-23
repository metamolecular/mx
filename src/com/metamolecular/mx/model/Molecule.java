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

import javax.swing.event.ChangeListener;

public interface Molecule
{  
  public int countAtoms();
  
  public int countBonds();
  
  public Atom getAtom(int index);
  
  public Bond getBond(int index);
  
  public Bond getBond(Atom source, Atom target);
  
  public Atom addAtom(String symbol, double x, double y, double z);
  
  public Atom addAtom(String symbol);
  
  public void removeAtom(Atom atom);
  
  public Bond connect(Atom source, Atom target, int type, int stereo);
  
  public Bond connect(Atom source, Atom target, int type);
  
  public void removeBond(Bond bond);
  
  public void disconnect(Atom source, Atom target);
  
  public void clear();
  
  public void beginModify();
  
  public void endModify();

  public void addChangeListener(ChangeListener listener);

  public void removeChangeListener(ChangeListener listener);
  
  public Molecule copy();
  
  public void copy(Molecule molecule);
}

