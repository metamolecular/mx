/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class Foober
{
  private Reducer reducer;
  private Map<Atom, Integer> reductions;

  public Foober()
  {
    reducer = new Reducer();
    reductions = new HashMap();
  }

  public Query foo(Molecule molecule)
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
