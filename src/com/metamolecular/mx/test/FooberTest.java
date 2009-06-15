/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.Foober;
import com.metamolecular.mx.query.Query;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class FooberTest extends TestCase
{
  private Foober foober;
  
  @Override
  protected void setUp() throws Exception
  {
    foober = new Foober();
  }
  
  public void testItConvertsBlockedPropaneToThreeNodeQuery()
  {
    Molecule propane = createBlockedPropane();
    Query query = foober.foo(propane);
    
    assertEquals(3, query.countNodes());
  }
  
    private Molecule createBlockedPropane()
  {
    Molecule result = Molecules.createPropane();

    result.connect(result.getAtom(1), result.addAtom("H"), 1);

    return result;
  }

  private Molecule createDoubleBlockedPropane()
  {
    Molecule result = createBlockedPropane();

    result.connect(result.getAtom(1), result.addAtom("H"), 1);

    return result;
  }

  private Molecule createChiralBlockedPropane()
  {
    Molecule result = createBlockedPropane();
    Bond bond = result.getBond(result.getAtom(1), result.getAtom(3));

    bond.setStereo(1);

    return result;
  }

  private Molecule createIsotopeBlockedPropane()
  {
    Molecule result = createBlockedPropane();
    Atom h = result.getAtom(3);

    h.setIsotope(1);

    return result;
  }
}
