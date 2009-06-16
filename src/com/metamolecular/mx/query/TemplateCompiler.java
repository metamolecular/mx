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

package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.model.Reducer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class TemplateCompiler implements QueryCompiler
{
  private Reducer reducer;
  private Map<Atom, Integer> reductions;
  private Molecule molecule;

  public TemplateCompiler()
  {
    reducer = new Reducer();
    reductions = new HashMap();
  }
  
  public static Query compile(Molecule molecule)
  {
    TemplateCompiler compiler = new TemplateCompiler();

    compiler.setMolecule(molecule);
    
    return compiler.compile();
  }
  
  public void setMolecule(Molecule molecule)
  {
    this.molecule = molecule;
  }
  
  public Molecule getMolecule()
  {
    return molecule;
  }
  
  public Query compile()
  {
    Molecule copy = molecule.copy();

    reductions.clear();
    reducer.reduce(copy, reductions);

    return build(copy, reductions);
  }

  private Query build(Molecule molecule, Map<Atom, Integer> reductions)
  {
    DefaultQuery result = new DefaultQuery();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      AtomMatcher matcher = createMatcher(atom, reductions);

      if (matcher != null)
      {
        result.addNode(matcher);
      }
    }

    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);

      result.connect(result.getNode(bond.getSource().getIndex()), result.getNode(bond.getTarget().getIndex()));
    }

    return result;
  }

  private AtomMatcher createMatcher(Atom atom, Map<Atom, Integer> reductions)
  {
    if (reductions == null)
    {
      return new DefaultAtomMatcher(atom);
    }
    
    Integer reduction = reductions.get(atom);
    
    if (reduction == null)
    {
      return new DefaultAtomMatcher(atom);
    }
    
    return new DefaultAtomMatcher(atom, reduction);
  }
}
