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
package com.metamolecular.mx.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Richard L. Apodaca
 */
public class AtomicMassSystem
{

  private static AtomicMassSystem instance;
  private Document document;
  private Map<String, Entry> entries;

  private AtomicMassSystem()
  {
    this.document = null;
    this.entries = new HashMap<String, Entry>();
    
    loadFile();
  }

  public int getAtomicNumber(String atomicSymbol)
  {
    return entries.get(atomicSymbol).atomicNumber;
  }
  
  public List<Isotope> getIsotopes(String atomicNumber)
  {
    Entry entry = entries.get(atomicNumber);
    
    return entry.getIsotopes();
  }

  public static AtomicMassSystem getInstance()
  {
    if (instance == null)
    {
      instance = new AtomicMassSystem();
    }

    return instance;
  }

  private void loadFile()
  {
    InputStream in = getClass().getResourceAsStream("atomic_system.xml");
    
    try
    {
      this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
    }
    
    catch (Exception e)
    {
      throw new RuntimeException("Error reading atomic_system.xml.");
    }
    
    NodeList nodes = document.getElementsByTagName("entry");
    
    for (int i = 0; i < nodes.getLength(); i++)
    {
      Node node = nodes.item(i);
      
      String symbol = node.getAttributes().getNamedItem("symbol").getNodeValue();
      
      entries.put(symbol, new Entry(node));
    }
  }
  
  private class Entry
  {
    private int atomicNumber;
    private List<Isotope> isotopes;
    
    private Entry(Node node)
    {
      this.atomicNumber = Integer.parseInt(node.getAttributes().getNamedItem("atomic-number").getNodeValue());
      this.isotopes = new ArrayList<Isotope>();
      
      loadIsotopes(node);
    }
    
    public List<Isotope> getIsotopes()
    {
      return isotopes;
    }
    
    private void loadIsotopes(Node node)
    {
      //Node abundance = node.getFirstChild();
      NodeList children = node.getChildNodes();
      
      System.out.println(node.getNodeName());
      System.out.println(children.getLength());
      
//      while (sibbling != null)
//      {
//        System.out.println(sibbling.getNodeName());
//        if (!sibbling.getNodeName().equals("isotope"))
//        {
//          continue;
//        }
//        
//        isotopes.add(new IsotopeImpl());
//        
//        sibbling = abundance.getNextSibling();
//      }
    }
  }
  
  private class IsotopeImpl implements Isotope
  {
    
  }
}
