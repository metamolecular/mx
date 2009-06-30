/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * Copyright (c) Ian Clarke
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

import com.metamolecular.mx.fingerprint.BloomFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import junit.framework.TestCase;

/**
 * @author Ian Clarke <ian@uprizer.com>
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class BloomFilterTest extends TestCase
{
  private BloomFilter<String> filter;
  private int length;

  @Override
  protected void setUp() throws Exception
  {
    length = 1024;
    filter = null;
  }

  public void testItCreatesABitSetWithTheSpecifiedLength()
  {
    doNew();

    assertEquals(1024, filter.toBitSet().size());
  }

  public void testItContainsAStringAfterAddingIt()
  {
    doNew();

    filter.add("Hello");

    assertTrue(filter.contains("Hello"));
  }

  public void testItLacksAStringNotIncluded()
  {
    doNew();

    filter.add("Hello");

    assertFalse(filter.contains("Hell"));
  }

  public void testItContainsAllBulkAddedStrings()
  {
    doNew();

    filter.addAll(Arrays.asList("C", "CC", "CCC", "CCCC"));

    assertTrue(filter.containsAll(Arrays.asList("C", "CC", "CCC", "CCCC")));
  }

  public void testItLacksAStringNotIncludedInBulkAdd()
  {
    doNew();

    filter.addAll(Arrays.asList("C", "CC", "CCC", "CCCC"));

    assertFalse(filter.contains("CCCCC"));
  }

  public void testItGivesTheExpectedFalsePositiveRate()
  {
    Random r = new Random(124445l);
    int bfSize = 400000;
    for (int i = 8; i < 9; i++)
    {
      int addCount = 10000 * (i + 1);
      BloomFilter<Integer> bf = new BloomFilter<Integer>(
        bfSize, addCount);
      HashSet<Integer> added = new HashSet<Integer>();
      for (int x = 0; x < addCount; x++)
      {
        int num = r.nextInt();
        added.add(num);
      }
      bf.addAll(added);
      assertTrue("Assert that there are no false negatives", bf.containsAll(added));

      int falsePositives = 0;
      for (int x = 0; x < addCount; x++)
      {
        int num = r.nextInt();

        // Ensure that this random number hasn't been added already
        if (added.contains(num))
        {
          continue;
        }

        // If necessary, record a false positive
        if (bf.contains(num))
        {
          falsePositives++;
        }
      }
      double expectedFP = bf.expectedFalsePositiveProbability();
      double actualFP = (double) falsePositives / (double) addCount;
      double ratio = expectedFP / actualFP;
      assertTrue(
        "Assert that the actual false positive rate doesn't deviate by more than 10% from what was predicted",
        ratio > 0.9 && ratio < 1.1);
    }
  }

  private void doNew()
  {
    filter = new BloomFilter(length);
  }
}
