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

import com.metamolecular.mx.io.daylight.SMILESTokenizer;
import junit.framework.TestCase;

/**
 * @author Richard L. Apodaca
 */
public class SMILESTokenizerTest extends TestCase
{

  @Override
  protected void setUp() throws Exception
  {
  }

  public void testItShouldFindAllTokensInALinearChain()
  {
    SMILESTokenizer tokenizer = new SMILESTokenizer("CCCCCC");
    String[] tokens = new String[]
    {
      "C", "C", "C", "C", "C", "C"
    };
    int index = 0;

    while (tokenizer.hasNextToken())
    {
      assertEquals(tokens[index], tokenizer.nextToken());

      index++;
    }
  }

  public void testItShouldFindAllTokensInACycle()
  {
    SMILESTokenizer tokenizer = new SMILESTokenizer("C1CCCCC1");
    String[] tokens = new String[]
    {
      "C", "1", "C", "C", "C", "C", "C", "1"
    };
    int index = 0;

    while (tokenizer.hasNextToken())
    {
      assertEquals(tokens[index], tokenizer.nextToken());

      index++;
    }
  }

  public void testItShouldFindAllTokensInABranch()
  {
    SMILESTokenizer tokenizer = new SMILESTokenizer("C(C)C");
    String[] tokens = new String[]
    {
      "C", "(", "C", ")", "C"
    };
    int index = 0;

    while (tokenizer.hasNextToken())
    {
      assertEquals(tokens[index], tokenizer.nextToken());

      index++;
    }
  }

  public void testItShouldFindAllTokensInANestedBranch()
  {
    SMILESTokenizer tokenizer = new SMILESTokenizer("C(C(C))C");
    String[] tokens = new String[]
    {
      "C", "(", "C", "(", "C", ")", ")", "C"
    };
    int index = 0;

    while (tokenizer.hasNextToken())
    {
      assertEquals(tokens[index], tokenizer.nextToken());

      index++;
    }
  }

  public void testItShouldFindAllTokensInANestedCyclicBranch()
  {
    SMILESTokenizer tokenizer = new SMILESTokenizer("C(C1CCCCCC1)C");
    String[] tokens = new String[]
    {
      "C", "(", "C", "1", "C", "C", "C", "C", "C", "C", "1", ")", "C"
    };
    int index = 0;

    while (tokenizer.hasNextToken())
    {
      assertEquals(tokens[index], tokenizer.nextToken());

      index++;
    }
  }

  public void testItShouldThrowWhenInvalidTokensArePresent()
  {
    try
    {
      new SMILESTokenizer("C!C");
      
      fail();
    }
    
    catch (Exception ignore)
    {
      
    }
    
    try
    {
      new SMILESTokenizer("*");
      
      fail();
    }
    
    catch (Exception ignore)
    {
      
    }
  }
}
