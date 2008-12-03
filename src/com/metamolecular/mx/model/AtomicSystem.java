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

import com.metamolecular.mx.calc.Measurement;
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
public class AtomicSystem
{

  private static AtomicSystem instance;
  private Document document;
  private Map<String, Entry> entries;

  private AtomicSystem()
  {
    this.document = null;
    this.entries = new HashMap<String, Entry>();

    loadFile();
  }
  
  public boolean hasElement(String atomicSymbol)
  {
    return entries.containsKey(atomicSymbol);
  }

  public int getAtomicNumber(String atomicSymbol)
  {
    Entry entry = getEntry(atomicSymbol);

    return entry.atomicNumber;
  }

  public List<Isotope> getIsotopes(String atomicSymbol)
  {
    Entry entry = getEntry(atomicSymbol);

    return entry.getIsotopes();
  }

  public Isotope getIsotope(String atomicSymbol, int massNumber)
  {
    Entry entry = getEntry(atomicSymbol);

    return entry.getIsotope(massNumber);
  }

  public Measurement getAverageMass(String atomicSymbol)
  {
    Entry entry = getEntry(atomicSymbol);

    return entry.getAverageMass();
  }

  public static AtomicSystem getInstance()
  {
    if (instance == null)
    {
      instance = new AtomicSystem();
    }

    return instance;
  }

  private void loadFile()
  {
    InputStream in = getClass().getResourceAsStream("atomic_system.xml");

    try
    {
      this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
    } catch (Exception e)
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

  private Entry getEntry(String symbol)
  {
    Entry entry = entries.get(symbol);

    if (entry == null)
    {
      throw new IllegalArgumentException("No such element: " + symbol);
    }

    return entry;
  }

  private class Entry
  {

    private int atomicNumber;
    private List<Isotope> isotopes;
    private Measurement averageMass;

    private Entry(Node node)
    {
      this.atomicNumber = Integer.parseInt(node.getAttributes().getNamedItem("atomic-number").getNodeValue());
      this.isotopes = new ArrayList<Isotope>();

      parseEntry(node);
    }

    public List<Isotope> getIsotopes()
    {
      return isotopes;
    }

    public Isotope getIsotope(int massNumber)
    {
      for (Isotope isotope : isotopes)
      {
        if (isotope.getMassNumber() == massNumber)
        {
          return isotope;
        }
      }

      throw new IllegalArgumentException("No such isotope: " + atomicNumber + "-" + massNumber);
    }

    public Measurement getAverageMass()
    {
      return averageMass;
    }

    private void parseEntry(Node entry)
    {
      Node abundance = findNaturalAbundance(entry);
      NodeList children = abundance.getChildNodes();

      for (int i = 0; i < children.getLength(); i++)
      {
        Node test = children.item(i);

        if ("isotope".equals(test.getNodeName()))
        {
          IsotopeImpl isotope = new IsotopeImpl(test);

          isotopes.add(isotope);
        }

        if ("mass".equals(test.getNodeName()))
        {
          recordMass(test);
        }
      }
    }

    private void recordMass(Node mass)
    {
      this.averageMass = new MeasurementImpl(mass, "u");
    }

    private Node findNaturalAbundance(Node entry)
    {
      NodeList children = entry.getChildNodes();

      for (int i = 0; i < children.getLength(); i++)
      {
        Node test = children.item(i);

        if ("natural-abundance".equals(test.getNodeName()))
        {
          return test;
        }
      }

      return null;
    }
  }

  private class MeasurementImpl implements Measurement
  {

    private double value;
    private double error;
    private String units;

    private MeasurementImpl(Node measurement, String units)
    {
      this.value = Double.parseDouble(measurement.getAttributes().getNamedItem("value").getNodeValue());
      this.error = Double.parseDouble(measurement.getAttributes().getNamedItem("error").getNodeValue());
      this.units = units;
    }

    public double getError()
    {
      return error;
    }

    public String getUnits()
    {
      return units;
    }

    public double getValue()
    {
      return value;
    }
  }

  private class IsotopeImpl implements Isotope
  {

    private int massNumber;
    private Measurement abundance;
    private Measurement mass;

    private IsotopeImpl(Node isotopeNode)
    {
      this.massNumber = Integer.parseInt(isotopeNode.getAttributes().getNamedItem("mass-number").getNodeValue());

      parseNode(isotopeNode);
    }

    public int getMassNumber()
    {
      return massNumber;
    }

    public Measurement getAbundance()
    {
      return abundance;
    }

    public Measurement getMass()
    {
      return mass;
    }

    private void parseNode(Node isotope)
    {
      NodeList children = isotope.getChildNodes();

      for (int i = 0; i < children.getLength(); i++)
      {
        Node child = children.item(i);

        if ("mass".equals(child.getNodeName()))
        {
          this.mass = new MeasurementImpl(child, "u");
        }

        if ("abundance".equals(child.getNodeName()))
        {
          this.abundance = new MeasurementImpl(child, "percent");
        }
      }
    }
  }

}
