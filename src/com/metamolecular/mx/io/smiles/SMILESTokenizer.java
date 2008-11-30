/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
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
package com.metamolecular.mx.io.smiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Richard L. Apodaca
 */
public class SMILESTokenizer
{
  private static String or = "|";
  private static String element = "[A-Z][a-z]?" + or + "[a-z]";
  private static String bond = "[-=#:\\.]";
  private static String ring = "[1-9]" + or + "\\%[1-9][0-9]";
  private static String bracket = "\\[[^\\]]*\\]";
  private static String parens = "[\\(\\)]";
  private static String regex = element + or + bracket + or + bond + or + ring + or + parens;
  private static Pattern pattern = Pattern.compile(regex);
  private static Pattern blacklist = Pattern.compile(".*[^A-Za-z0-9\\(\\)\\[\\]\\-=#:\\.\\%].*");
  
  private Iterator<String> iterator;

  public SMILESTokenizer(String smiles)
  {
    assertNoBlacklistedCharacters(smiles);
    
    List<String> tokens = fragment(smiles);
    
    this.iterator = tokens.iterator();
  }

  public boolean hasNextToken()
  {
    return iterator.hasNext();
  }

  public String nextToken()
  {
    return iterator.next();
  }

  public List<String> fragment(String string)
  {
    Matcher matcher = pattern.matcher(string);
    String[] foundStrings = new String[string.length()];
    int foundStringCount = 0;
    int index = 0;

    while (matcher.find(index))
    {
      int start = matcher.start();
      int end = matcher.end();

      if (start == 0 && end == 0)
      {
        break;
      }

      foundStrings[foundStringCount] = string.substring(start, end);
      index = end;
      foundStringCount++;
    }

    List<String> result = new ArrayList<String>();

    for (int i = 0; i < foundStringCount; i++)
    {
      result.add(foundStrings[i]);
    }

    return result;
  }
  
  private void assertNoBlacklistedCharacters(String smiles)
  {
    if (blacklist.matcher(smiles).matches())
    {
      throw new IllegalArgumentException("Invalid SMILES character in \"" + smiles + "\"");
    }
  }
}
