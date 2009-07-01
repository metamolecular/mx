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

import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author Richard L. Apodaca
 */
public class MXTest
{

  public static void main(String[] args)
  {
    TestSuite suite = new TestSuite();

    suite.addTestSuite(MoleculeTest.class);
    suite.addTestSuite(AtomTest.class);
    suite.addTestSuite(BondTest.class);
    suite.addTestSuite(MolfileReaderTest.class);
    suite.addTestSuite(StepTest.class);
    suite.addTestSuite(PathFinderTest.class);
    suite.addTestSuite(VirtualHydrogenCounterTest.class);
    suite.addTestSuite(SMILESTokenizerTest.class);
    suite.addTestSuite(SMILESReaderTest.class);
    suite.addTestSuite(SMILESBuilderTest.class);
    suite.addTestSuite(AtomicSystemTest.class);
    suite.addTestSuite(MassCalculatorTest.class);
    suite.addTestSuite(MoleculeKitTest.class);
    suite.addTestSuite(SDFileReaderTest.class);
    suite.addTestSuite(SDFileWriterTest.class);
    suite.addTestSuite(PathEdgeTest.class);
    suite.addTestSuite(PathGraphTest.class);
    suite.addTestSuite(HanserRingFinderTest.class);
    suite.addTestSuite(DefaultAtomMatcherTest.class);
    suite.addTestSuite(QueryTest.class);
    suite.addTestSuite(DefaultMapperTest.class);
    suite.addTestSuite(DefaultStateTest.class);
    suite.addTestSuite(SuperatomTest.class);
    suite.addTestSuite(MolfileWriterTest.class);
    suite.addTestSuite(ReducerTest.class);
    suite.addTestSuite(TemplateCompilerTest.class);
    suite.addTestSuite(PathFingerprinterTest.class);
    suite.addTestSuite(PathWriterTest.class);    
    suite.addTestSuite(DefaultBondMatcherTest.class);
    suite.addTestSuite(RingFilterTest.class);
    suite.addTestSuite(AromaticAtomFilterTest.class);
    suite.addTestSuite(DefaultWalkerTest.class);
    suite.addTestSuite(DefaultStepTest.class);
    suite.addTestSuite(BloomFilterTest.class);
    suite.addTestSuite(NewPathWriterTest.class);

    TestRunner.run(suite);
  }
}
