/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.metamolecular.mx.query;

import com.metamolecular.mx.model.Atom;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class Match
{
  private Node query;
  private Atom target;

  public Match(Node query, Atom target)
  {
    this.query = query;
    this.target = target;
  }

  public Node getQueryNode()
  {
    return query;
  }

  public Atom getTargetAtom()
  {
    return target;
  }
}
