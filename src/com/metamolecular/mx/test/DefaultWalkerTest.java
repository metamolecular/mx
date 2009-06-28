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
package com.metamolecular.mx.test;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import com.metamolecular.mx.walk.Step;
import com.metamolecular.mx.walk.DefaultWalker;
import com.metamolecular.mx.walk.Reporter;
import java.util.List;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class DefaultWalkerTest extends TestCase
{
  private DefaultWalker walker;
  private Reporter reporter;
  private Step step;
  private List path;
  private Atom atom;
  private Bond bond;

  @Override
  protected void setUp() throws Exception
  {
    walker = new DefaultWalker();
    reporter = mock(Reporter.class);
    step = mock(Step.class);
    path = mock(List.class);
    atom = mock(Atom.class);
    bond = mock(Bond.class);

    when(step.getPath()).thenReturn(path);
  }

  public void testItSetsMaximumDepth()
  {
    walker.setMaximumDepth(5);

    assertEquals(5, walker.getMaximumDepth());
  }

  public void testItStopsBranchingWhenMaximumDepthReached()
  {
    walker.setMaximumDepth(5);
    when(path.size()).thenReturn(0, 1, 2, 3, 4, 5);
    when(step.hasNextBranch()).thenReturn(true, true, true, true, true, false);
    when(step.nextStep(atom)).thenReturn(step);
    when(step.nextBranch()).thenReturn(atom);
    doStep();

    verify(step, times(5)).nextStep(atom);
  }

  public void testItIgnoresMaxDepthWhenSetToZero()
  {
    walker.setMaximumDepth(0);
    when(path.size()).thenReturn(0, 1, 2);
    when(step.hasNextBranch()).thenReturn(true, true, true, false);
    when(step.nextStep(atom)).thenReturn(step);
    when(step.nextBranch()).thenReturn(atom);
    doStep();

    verify(step, times(3)).nextStep(atom);
  }

  public void testItRequestsNextStepUntilNoneLeft()
  {
    walker.setMaximumDepth(6);

    when(step.hasNextBranch()).thenReturn(true, true, true, true, true, false);
    when(step.nextBranch()).thenReturn(atom);
    when(step.nextStep(atom)).thenReturn(step);
    doStep();

    verify(step, times(5)).nextStep(atom);
  }

  public void testItDoesntReportBranchEndForUnbranchedAtom()
  {
    unbranchedAtom();
    doStep();

    verify(reporter, never()).branchEnd();
  }

  public void testItDoesntReportBranchStartForUnbranchedAtom()
  {
    unbranchedAtom();
    doStep();

    verify(reporter, never()).branchStart();
  }

  public void testItReportsBranchStartOnceForSingleBranchedAtom()
  {
    singleBranchedAtom();
    doStep();

    verify(reporter, times(1)).branchStart();
  }

  public void testItReportsBranchEndOnceForSingleBranchedAtom()
  {
    singleBranchedAtom();
    doStep();

    verify(reporter, times(1)).branchEnd();
  }

//  public void testItReportsRingClosureTerminationForCyclicAtom()
//  {
//    cyclicAtom();
//    doStep();
//
//    verify(reporter, times(1)).ringClosed(bond);
//  }

  public void testItReportsAtomWhenTerminal()
  {
    when(step.getAtom()).thenReturn(atom);
    doStep();

    verify(reporter, times(1)).atomFound(atom);
  }

  public void testItFindsAllAtomsInChain()
  {
    when(step.hasNextBranch()).thenReturn(true, true, true, true, true, false);
    when(step.nextBranch()).thenReturn(atom);
    when(step.nextStep(atom)).thenReturn(step);
    when(step.getAtom()).thenReturn(atom);
    doStep();

    verify(reporter, times(6)).atomFound(atom);
  }

  private void singleBranchedAtom()
  {
    when(step.hasNextBranch()).thenReturn(true, true, false, true, false, false);
    when(step.nextBranch()).thenReturn(atom, atom);
    when(step.nextStep(atom)).thenReturn(step, step);
    when(path.size()).thenReturn(1);
  }

  private void unbranchedAtom()
  {
    when(step.hasNextBranch()).thenReturn(true, false);
    when(step.nextBranch()).thenReturn(atom);
    when(step.nextStep(atom)).thenReturn(step);
    when(path.size()).thenReturn(1);
  }

  private void cyclicAtom()
  {
    when(step.hasNextBranch()).thenReturn(true, true, true, false, false, false);
    when(step.nextBranch()).thenReturn(atom);
    when(step.nextStep(atom)).thenReturn(step);
    when(path.size()).thenReturn(1);
  }

  private void doStep()
  {
    walker.step(step, reporter);
  }
}
