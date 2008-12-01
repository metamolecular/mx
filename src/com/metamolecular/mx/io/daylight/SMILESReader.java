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
package com.metamolecular.mx.io.daylight;

import com.metamolecular.mx.model.Molecule;
import java.util.regex.Pattern;

/**
 * @author Richard L. Apodaca
 */
public class SMILESReader
{
  private static Pattern atomPattern = Pattern.compile("^(([A-Z][a-z]?)|[a-z])");
  private static Pattern ringIdentifierPattern = Pattern.compile("[1-9]|\\%[1-9][0-9]");
  private static String openParen = "(";
  private static String closeParen = ")";
  
  public SMILESReader()
  {

  }

  public void read(Molecule molecule, String smiles)
  {
    SMILESTokenizer tokenizer = new SMILESTokenizer(smiles);
    SMILESBuilder builder = new SMILESBuilder(molecule);

    while (tokenizer.hasNextToken())
    {
      String token = tokenizer.nextToken();

      handleToken(token, builder);
    }
  }

  private void handleToken(String token, SMILESBuilder builder)
  {
    if (atomPattern.matcher(token).matches())
    {
      handleAtom(token, builder);
      
      return;
    }
    
    if (ringIdentifierPattern.matcher(token).matches())
    {
      handleRingIdentifier(token, builder);
      
      return;
    }
    
    if (openParen.equals(token))
    {
      builder.openBranch();
      
      return;
    }
    
    if (closeParen.equals(token))
    {
      builder.closeBranch();
      
      return;
    }
    
    throw new IllegalArgumentException("Unknown SMILES token \"" + token + "\"");
  }
  
  private void handleAtom(String token, SMILESBuilder builder)
  {
    builder.addHead(token);
  }
  
  private void handleRingIdentifier(String token, SMILESBuilder builder)
  {
    builder.ring(token);
  }
}
