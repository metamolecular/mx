/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public interface AtomMatcher
{
  boolean matches(Atom atom);
}
