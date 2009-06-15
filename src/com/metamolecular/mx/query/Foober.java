/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class Foober
{
  public Query foo(Molecule molecule)
  {
    Molecule copy = molecule.copy();
    
    return build(copy);
  }

  private Query build(Molecule molecule)
  {
    DefaultQuery result = new DefaultQuery();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      AtomMatcher matcher = createMatcher(atom);

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

  private AtomMatcher createMatcher(Atom atom)
  {
    return new DefaultAtomMatcher(atom);
  }
}
