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

/**
 * @author Richard L. Apodaca
 */
public class MDLStringKit
{
  private MDLStringKit()
  {
    
  }
  
  /**
   * Returns the integer value of the specified substring.
   * 
   * @param string the string
   * @param start the start index
   * @param stop the end index
   * 
   * @return the int value
   */
  public static int extractInt(String string, int start, int stop)
  {
    string = string.substring(start, stop);
    string = string.trim();
    int value = 0;
    
    if ("".equals(string))
    {
      return 0;
    }

    try
    {
      value = Integer.parseInt(string);
    }

    catch(NumberFormatException e)
    {
      throw new IllegalArgumentException("Error trying to parse: " + string + " as an integer.");
    }

    return value;
  }
  
  /**
   * Returns a <code>String</code> to which is appended enough space (" ")
   * characters to make the total length equal to <code>length</code>. If
   * the length of <code>string</code> is greater than <code>length</code>,
   * then <code>string</code> is returned with no modifications.
   * 
   * @param string a string to operate on
   * @param length the desired final length of the string
   * @return a string with spaces appended to it, or just <code>string</code>
   */
  public static String padRight(String string, int length)
  {
    String result = string;
    int spaceCount = length - string.length();
    
    for (int i = 0; i < spaceCount; i++)
    {
      result += ' ';
    }
    
    return result;
  }
  
  /**
   * Returns a <code>String</code> to which is prepended enough space (" ")
   * characters to make the total length equal to <code>length</code>. If
   * the length of <code>string</code> is greater than <code>length</code>,
   * then <code>string</code> is returned with no modifications.
   * 
   * @param string a string to operate on
   * @param length the desired final length of the string
   * @return a string with spaces prepended to it, or just <code>string</code>
   */
  public static String padLeft(String string, int length)
  {
    String result = string;
    int spaceCount = length - string.length();
    
    for (int i = 0; i < spaceCount; i++)
    {
      result = ' ' + result;
    }
    
    return result;
  }
  /**
   * Returns the float value of the specified substring.
   * 
   * @param string the string
   * @param start the start index
   * @param stop the end index
   * 
   * @return the float value
   */
  public static float extractFloat(String string, int start, int stop)
  {
    string = string.substring(start, stop);
    string = string.trim();
    float value = 0;
    
    if ("".equals(string))
    {
      return 0;
    }
    try
    {
      value = Float.parseFloat(string);
    }

    catch(NumberFormatException e)
    {
      throw new IllegalArgumentException("Error trying to parse: " + string + " as a float.");
    }

    return value;
  }
  
  /**
   * Returns the specified substring, trimming of whitespace.
   * 
   * @param string the string
   * @param start the start index
   * @param stop the end index
   * 
   * @return the substring
   */
  public static String extractString(String string, int start, int stop)
  {
    string = string.substring(start, stop);
    string = string.trim();

    return string;
  }
}
