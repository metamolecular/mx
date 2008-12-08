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
package com.metamolecular.mx.io.mdl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class SDFileReader
{
  private static String RECORD_END = "$$$$";
  private static String LINEFEED = "\n";
  private BufferedReader reader;
  private Reader file;
  private String record;
  private Pattern keyPattern;
  private Map<String, Pattern> keyPatterns;
  
  public SDFileReader(String filename) throws IOException
  {
    record = null;
    file = new FileReader(filename);
    reader = new BufferedReader(file);
    keyPattern = Pattern.compile("^> *?<(.*?)>", Pattern.MULTILINE);
    keyPatterns = new HashMap<String, Pattern>();
    
    try
    {
      nextRecord();
    }
    
    catch (Exception e)
    {
      throw new IOException("The file " + filename + " does not appear to be a valid SD file.");
    }
  }

  public void close()
  {
    try
    {
      file.close();
    }
    catch (IOException e)
    {
      throw new RuntimeException("Error closing file.", e);
    }
  }

  public boolean hasNextRecord()
  {
    try
    {
      return reader.ready();
    }
    catch (IOException ignore)
    {
    }

    return false;
  }

  public void nextRecord()
  {
    StringBuffer buff = new StringBuffer();

    try
    {
      String line = reader.readLine();

      while (!RECORD_END.equals(line))
      {
        buff.append(line + LINEFEED);

        line = reader.readLine();
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException("An unexpected IO error occurred while reading file.", e);
    }

    record = buff.toString();
  }
  
  public String getData(String key)
  {
    Pattern pattern = keyPatterns.get(key);
    
    if (pattern == null)
    {
      pattern = Pattern.compile(".*^> *?<" + key + ">$.(.*?)$.*", Pattern.MULTILINE | Pattern.DOTALL);
    
      keyPatterns.put(key, pattern);
    }
    
    Matcher matcher = pattern.matcher(record);
    
    return matcher.matches() ? matcher.group(1) : "";
  }
  
  public List<String> getKeys()
  {
    List<String> result = new ArrayList<String>();
    Matcher m = keyPattern.matcher(record);
    
    while (m.find())
    {
      result.add(m.group(1));
    }

    return result;
  }
}
