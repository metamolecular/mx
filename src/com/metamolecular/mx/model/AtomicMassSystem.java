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
import org.w3c.dom.NamedNodeMap;
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

  public List<Isotope> getIsotopes(String atomicSymbol)
  {
    Entry entry = entries.get(atomicSymbol);

    return entry.getIsotopes();
  }

  public Isotope getIsotope(String atomicSymbol, int massNumber)
  {
    Entry entry = entries.get(atomicSymbol);

    return entry.getIsotope(massNumber);
  }

  public Measurement getAverageMass(String atomicSymbol)
  {
    Entry entry = entries.get(atomicSymbol);

    return entry.getAverageMass();
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

      return null;
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
      NamedNodeMap attributes = mass.getAttributes();

      double value = Double.parseDouble(attributes.getNamedItem("value").getNodeValue());
      double error = Double.parseDouble(attributes.getNamedItem("error").getNodeValue());

      this.averageMass = new MeasurementImpl(value, error, "u");
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

    private MeasurementImpl(double value, double error, String units)
    {
      this.value = value;
      this.error = error;
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
          parseMass(child);
        }

        if ("abundance".equals(child.getNodeName()))
        {
          parseAbundance(child);
        }
      }
    }

    private void parseMass(Node massNode)
    {
      double value = Double.parseDouble(massNode.getAttributes().getNamedItem("value").getNodeValue());
      double error = Double.parseDouble(massNode.getAttributes().getNamedItem("error").getNodeValue());

      this.mass = new MeasurementImpl(value, error, "u");
    }

    private void parseAbundance(Node abundanceNode)
    {
      double value = Double.parseDouble(abundanceNode.getAttributes().getNamedItem("value").getNodeValue());
      double error = Double.parseDouble(abundanceNode.getAttributes().getNamedItem("error").getNodeValue());

      this.abundance = new MeasurementImpl(value, error, "percent");
    }
  }

}
