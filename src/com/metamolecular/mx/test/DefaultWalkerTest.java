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
    when(step.getAtom()).thenReturn(atom);
  }

  public void testItSetsMaximumDepth()
  {
    walker.setMaximumDepth(5);

    assertEquals(5, walker.getMaximumDepth());
  }

  public void testItDoesntRequestNextBondWhenMaximumDepthReached()
  {
    walker.setMaximumDepth(2);
    when(path.size()).thenReturn(2);
    doStep();
    verify(step, never()).nextBond();
  }

  public void testItRequestsNextBondWhenMaximumDepthNotReached()
  {
    Step branch1 = mock(Step.class);
    
    walker.setMaximumDepth(2);
    when(path.size()).thenReturn(1);
    when(step.hasNextBond()).thenReturn(true, false);
    when(step.nextBond()).thenReturn(bond);
    when(step.nextStep(bond)).thenReturn(branch1);
    doStep();
    verify(step, times(1)).nextBond();
  }

  public void testItRequestsNextBondWhenMaximumDepthNotSet()
  {
    Step branch1 = mock(Step.class);

    walker.setMaximumDepth(0);

    when(path.size()).thenReturn(2);
    when(step.hasNextBond()).thenReturn(true, false);
    when(step.nextBond()).thenReturn(bond);
    when(branch1.getAtom()).thenReturn(atom);
    when(step.nextStep(bond)).thenReturn(branch1);

    doStep();
    verify(step, times(1)).nextBond();
  }

  public void testItDoesntCheckPathDepthWhenNotSet()
  {
    doStep();
    verify(path, never()).size();
  }

  public void testItReportsAtomWhenNextBondUnavailable()
  {
    doStep();
    verify(reporter, times(1)).atomFound(atom);
  }

  public void testItReporstNoBranchStartWhenStepHasOneBond()
  {
    Step branch1 = mock(Step.class);

    when(step.hasNextBond()).thenReturn(true, false);
    when(step.nextBond()).thenReturn(bond);
    when(branch1.getAtom()).thenReturn(atom);
    when(step.nextStep(any(Bond.class))).thenReturn(branch1);

    doStep();
    verify(reporter, times(0)).branchStart();
  }

  public void testItReportsNoBranchEndWhenStepHasOneBond()
  {
    Step branch1 = mock(Step.class);

    when(step.hasNextBond()).thenReturn(true, false);
    when(step.nextBond()).thenReturn(bond);
    when(branch1.getAtom()).thenReturn(atom);
    when(step.nextStep(any(Bond.class))).thenReturn(branch1);

    doStep();
    verify(reporter, times(0)).branchEnd();
  }

  public void testItReportsTwoBranchStartsWhenStepHasThreeBonds()
  {
    when(step.hasNextBond()).thenReturn(true, true, true, false);
    when(step.nextBond()).thenReturn(bond);

    Step branch1 = mock(Step.class);
    Step branch2 = mock(Step.class);
    Step branch3 = mock(Step.class);

    when(branch1.getAtom()).thenReturn(atom);
    when(branch2.getAtom()).thenReturn(atom);
    when(branch3.getAtom()).thenReturn(atom);

    when(step.nextStep(any(Bond.class))).thenReturn(branch1, branch2, branch3);

    doStep();
    verify(reporter, times(2)).branchStart();
  }

  public void testItReportsTwoBranchEndsWhenStepHasThreeBonds()
  {
    when(step.hasNextBond()).thenReturn(true, true, true, false);
    when(step.nextBond()).thenReturn(bond);

    Step branch1 = mock(Step.class);
    Step branch2 = mock(Step.class);
    Step branch3 = mock(Step.class);

    when(branch1.getAtom()).thenReturn(atom);
    when(branch2.getAtom()).thenReturn(atom);
    when(branch3.getAtom()).thenReturn(atom);

    when(step.nextStep(any(Bond.class))).thenReturn(branch1, branch2, branch3);

    doStep();
    verify(reporter, times(2)).branchEnd();
  }

  public void testItReportsRingClosureWhenStepNextBondClosesRing()
  {
    when(step.hasNextBond()).thenReturn(true, false);
    when(step.nextBond()).thenReturn(bond);

    Step branch1 = mock(Step.class);
    when(branch1.getAtom()).thenReturn(atom);
    when(step.nextStep(bond)).thenReturn(branch1);
    when(step.closesRingWith(bond)).thenReturn(true);

    doStep();
    verify(reporter, times(1)).ringClosed(bond);
  }

  public void testItDoesntReportRingClosureWhenStepNextBondDoesntCloseRing()
  {
    when(step.hasNextBond()).thenReturn(true, false);
    when(step.nextBond()).thenReturn(bond);

    Step branch1 = mock(Step.class);
    when(branch1.getAtom()).thenReturn(atom);
    when(step.nextStep(bond)).thenReturn(branch1);
    when(step.closesRingWith(bond)).thenReturn(false);

    doStep();
    verify(reporter, never()).ringClosed(bond);
  }

  private void doStep()
  {
    walker.step(step, reporter);
  }
}
