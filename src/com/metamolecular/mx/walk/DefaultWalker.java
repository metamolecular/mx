/*
 * MX - Essential Cheminformatics
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * 
 * http://metamolecular.com/mx
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

package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultWalker implements Walker
{
  private int maximumDepth;

  public int getMaximumDepth()
  {
    return maximumDepth;
  }

  public void setMaximumDepth(int depth)
  {
    this.maximumDepth = depth;
  }

  public void walk(Atom atom, Reporter reporter)
  {
    Step step = new DefaultStep(atom);

    reporter.walkStart(atom);
    step(step, reporter);
    reporter.walkEnd(atom);
  }

  public void step(Step step, Reporter reporter)
  {
    reporter.atomFound(step.getAtom());

    if (abort(step))
    {
      return;
    }

    boolean inBranch = false;

    while (true)
    {
      if (step.hasNextBond())
      {
        Bond bond = step.nextBond();

        if (inBranch)
        {
          reporter.branchStart(step.getAtom());
        }

        reporter.bondFound(bond);

        if (step.closesRingWith(bond))
        {
          reporter.ringClosed(bond);
        }
        else
        {
          step(step.nextStep(bond), reporter);
        }

        if (inBranch)
        {
          reporter.branchEnd(step.getAtom());
        }

        inBranch = true;
      }
      else
      {
        break;
      }
    }
  }

  private boolean abort(Step step)
  {
    return (maximumDepth != 0 && step.getPath().size() >= maximumDepth);
  }
}
