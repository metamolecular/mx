/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamolecular.mx.test;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.query.DefaultQuery;
import com.metamolecular.mx.query.DefaultState;
import com.metamolecular.mx.query.Query;
import com.metamolecular.mx.query.State;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class QueryDefaultStateTest extends TestCase
{
  private Molecule benzene;
  private Query benzeneQuery;
  
  public QueryDefaultStateTest()
  {
    benzene = Molecules.createBenzene();
    benzeneQuery = new DefaultQuery(benzene);
  }

  @Override
  protected void setUp() throws Exception
  {
  }

  public void testItShouldFindAllMatchCandidatesInTheRootState()
  {
    State state = new DefaultState(benzeneQuery, benzene);
    int count = 0;

    while (state.hasNextCandidate())
    {
      state.nextCandidate();
      
      count++;
    }

    assertEquals(benzene.countAtoms() * benzene.countAtoms(), count);
  }
}
