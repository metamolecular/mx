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
package com.metamolecular.mx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Richard L. Apodaca
 */
public class DefaultMolecule implements Molecule
{
  private VirtualHydrogenCounter hCounter;
  private List listeners;
  private List atoms;
  private List bonds;
  private List substructures;
  private int modifyDepth;
  private boolean changed;
  private ChangeEvent event;

  public DefaultMolecule()
  {
    hCounter = new VirtualHydrogenCounter();
    atoms = new ArrayList();
    bonds = new ArrayList();
    substructures = new ArrayList();
    listeners = null;
    modifyDepth = 0;
    changed = false;
    event = new ChangeEvent(this);
  }

  public Atom addAtom(String symbol, double x, double y, double z)
  {
    assertAtomSymbolValid(symbol);

    AtomImpl atom = new AtomImpl(this);
    atom.symbol = symbol;
    atom.x = x;
    atom.y = y;
    atom.z = z;

    atoms.add(atom);

    fireChange();

    return atom;
  }

  public Atom addAtom(String symbol)
  {
    return addAtom(symbol, 0, 0, 0);
  }

  public void addChangeListener(ChangeListener listener)
  {
    if (listeners == null)
    {
      listeners = new ArrayList();
    }
    listeners.add(listener);
  }

  public void removeChangeListener(ChangeListener listener)
  {
    listeners.remove(listener);
  }

  public void beginModify()
  {
    modifyDepth++;
  }

  public void clear()
  {
    atoms.clear();
    bonds.clear();
    substructures.clear();

    fireChange();
  }

  public Bond connect(Atom source, Atom target, int type, int stereo)
  {
    assertAtomBelongs(source);
    assertAtomBelongs(target);
    assertAtomsAreDifferent(source, target);
    assertBondTypeValid(type);
    assertBondStereoValid(stereo);

    BondImpl bond = new BondImpl(this);

    bond.source = source;
    bond.target = target;
    bond.type = type;
    bond.stereo = stereo;

    bonds.add(bond);

    AtomImpl sourceImpl = (AtomImpl) source;
    AtomImpl targetImpl = (AtomImpl) target;

    sourceImpl.neighbors.add(target);
    sourceImpl.bonds.add(bond);
    targetImpl.neighbors.add(source);
    targetImpl.bonds.add(bond);

    fireChange();

    return bond;
  }

  public Bond connect(Atom source, Atom target, int type)
  {
    return connect(source, target, type, 0);
  }

  public void removeBond(Bond bond)
  {
      for (int i = 0; i < substructures.size(); i++) {
          Substructure substructure = (Substructure) substructures.get(i);
          if (substructure.contains(bond)) {
              substructure.removeCrossingBond(bond);
          }
      }

      disconnect(bond.getSource(), bond.getTarget());

  }

  public void disconnect(Atom source, Atom target)
  {
    BondImpl bond = (BondImpl) getBond(source, target);

    if (bond == null)
    {
      throw new RuntimeException("Attempt to disconnect unconnected atoms.");
    }

    AtomImpl sourceImpl = (AtomImpl) source;
    AtomImpl targetImpl = (AtomImpl) target;

    sourceImpl.bonds.remove(bond);
    sourceImpl.neighbors.remove(target);
    targetImpl.bonds.remove(bond);
    targetImpl.neighbors.remove(source);

    bonds.remove(bond);

    fireChange();
  }

    private void addSgroup(Substructure substructure) {
        if (substructure.getMolecule() != this)
        {
          throw new IllegalStateException("Attempt to add substructure of another molecule.");
        }
        substructures.add(substructure);
    }

    public void removeSubstructure(Substructure substructure) {
        if (substructure.getMolecule() != this)
        {
          throw new IllegalStateException("Attempt to remove substructure of another molecule.");
        }
        substructures.remove(substructures);
    }

    public void endModify()
  {
    modifyDepth--;

    if (modifyDepth == 0)
    {
      if (changed)
      {
        changed = false;

        fireChange();
      }
    }
  }

  public void removeAtom(Atom atom)
  {
    assertAtomBelongs(atom);

    Atom[] neighbors = atom.getNeighbors();

    beginModify();

    for (int i = 0; i < neighbors.length; i++)
    {
      disconnect(atom, neighbors[i]);
    }

    for (int i = 0; i < substructures.size(); i++)
    {
        Substructure substructure = (Substructure) substructures.get(i);
        if(substructure.contains(atom))
        {
            substructure.removeAtom(atom);
        }
    }

    ((AtomImpl) atom).molecule = null;

    atoms.remove(atom);

    // do this so that deleting an unconnected atom will
    // fire event
    if (neighbors.length == 0)
    {
      fireChange();
    }

    endModify();
  }

  public int countAtoms()
  {
    return atoms.size();
  }

  public int countBonds()
  {
    return bonds.size();
  }

  public int countSubstructures() {
    return substructures.size();
  }

  public Atom getAtom(int index)
  {
    return (Atom) atoms.get(index);
  }

  public int getAtomIndex(Atom atom)
  {
    return atoms.indexOf(atom);
  }

  /* (non-Javadoc)
   * @see com.metamolecular.firefly.model.Molecule#getCrossingBond(int)
   */
  public Bond getBond(int index)
  {
    return (Bond) bonds.get(index);
  }

  public Bond getBond(Atom source, Atom target)
  {
    if (source.getMolecule() != this || target.getMolecule() != this)
    {
      return null;
    }

    if (source == target)
    {
      return null;
    }

    AtomImpl sourceImpl = (AtomImpl) source;

    for (int i = 0; i < sourceImpl.bonds.size(); i++)
    {
      Bond bond = (Bond) sourceImpl.bonds.get(i);

      if (bond.getSource() == target || bond.getTarget() == target)
      {
        return bond;
      }
    }

    return null;
  }

  public Substructure getSubstructure(int i)
  {
    return (Substructure) substructures.get(i);
  }

  public Substructure addSubstructure()
  {
      SubstructureImpl sgroup = new SubstructureImpl(this);
      addSgroup(sgroup);
      return sgroup;
  }

  public int getBondIndex(Bond bond)
  {
    return bonds.indexOf(bond);
  }

  public Molecule copy()
  {
    DefaultMolecule result = new DefaultMolecule();

    for (int i = 0; i < atoms.size(); i++)
    {
      AtomImpl oldAtom = (AtomImpl) atoms.get(i);

      result.atoms.add(new AtomImpl(result, oldAtom));
    }

    for (int i = 0; i < bonds.size(); i++)
    {
      BondImpl oldBond = (BondImpl) bonds.get(i);
      int sourceIndex = atoms.indexOf(oldBond.source);
      int targetIndex = atoms.indexOf(oldBond.target);
      BondImpl newBond = (BondImpl) result.connect((AtomImpl) result.atoms.get(sourceIndex),
        (AtomImpl) result.atoms.get(targetIndex),
        oldBond.type);

      newBond.stereo = oldBond.stereo;
    }

      for (int i = 0; i < this.countSubstructures(); i++)
      {
          Substructure substructure = this.getSubstructure(i);
          Substructure newSubstructure = result.addSubstructure();
          for(int j=0;j< substructure.countAtoms();j++)
          {
            newSubstructure.addAtom(result.getAtom(substructure.getAtom(j).getIndex()));
          }
          for(int j=0;j< substructure.countCrossingBonds();j++)
          {
            Bond crossingBond = substructure.getCrossingBond(j);
            Bond bond = result.getBond(crossingBond.getIndex());
            newSubstructure.addCrossingBond(bond);
            newSubstructure.setCrossingVector(bond, substructure.getCrossingVectorX(crossingBond), substructure.getCrossingVectorY(crossingBond));
          }
          newSubstructure.setLabel(substructure.getLabel());
      }

    return result;
  }

  public void copy(Molecule molecule)
  {
    beginModify();
    clear();

    for (int i = 0; i < molecule.countAtoms(); i++)
    {
      Atom atom = molecule.getAtom(i);
      Atom copy = addAtom(atom.getSymbol(), atom.getX(), atom.getY(), atom.getZ());

      copy.setCharge(atom.getCharge());

      if (atom.hasSingleIsotope())
      {
        copy.setIsotope(atom.getIsotope());
      }

      copy.setRadical(atom.getRadical());
    }

    for (int i = 0; i < molecule.countBonds(); i++)
    {
      Bond bond = molecule.getBond(i);
      Atom source = getAtom(bond.getSource().getIndex());
      Atom target = getAtom(bond.getTarget().getIndex());

      connect(source, target, bond.getType(), bond.getStereo());
    }

    for (int i = 0; i < molecule.countSubstructures(); i++)
    {
        Substructure substructure = molecule.getSubstructure(i);
        Substructure newSubstructure = this.addSubstructure();
        for(int j=0;j< substructure.countAtoms();j++)
        {
          newSubstructure.addAtom(this.getAtom(substructure.getAtom(j).getIndex()));
        }
        for(int j=0;j< substructure.countCrossingBonds();j++)
        {
            Bond crossingBond = substructure.getCrossingBond(j);
            Bond bond = this.getBond(crossingBond.getIndex());
            newSubstructure.addCrossingBond(bond);
            newSubstructure.setCrossingVector(bond, substructure.getCrossingVectorX(crossingBond), substructure.getCrossingVectorY(crossingBond));
        }
        newSubstructure.setLabel(substructure.getLabel());
    }

    endModify();
  }

  protected void fireChange()
  {
    if (modifyDepth != 0)
    {
      changed = true;

      return;
    }

    if (listeners == null)
    {
      return;
    }
    for (int i = 0; i < listeners.size(); i++)
    {
      ChangeListener l = (ChangeListener) listeners.get(i);

      l.stateChanged(event);
    }
  }

  protected void assertAtomBelongs(Atom atom)
  {
    if (atom.getMolecule() != this)
    {
      throw new IllegalStateException("Attempt to use a non-member atom.");
    }
  }

  protected void assertBondBelongs(Bond bond)
  {
    if (bond.getMolecule() != this)
    {
      throw new IllegalStateException("Attempt to use a non-member bond.");
    }
  }

  private void assertBondTypeValid(int type)
  {
    if (type < 1 || type > 3)
    {
      throw new IllegalStateException("Invalid bond type " + type + " (1-3)");
    }
  }

  private void assertBondStereoValid(int stereo)
  {
    if (stereo >= 0 && stereo <= 6)
    {
      if (stereo != 2 && stereo != 5)
      {
        return;
      }
    }

    throw new IllegalStateException("Invalid bond stereo " + stereo + " (0, 1, 3, 5, 6)");
  }

  private void assertAtomsAreDifferent(Atom source, Atom target)
  {
    if (source == target)
    {
      throw new IllegalStateException("Attempt to connect Atom to itself");
    }
  }

  private void assertAtomSymbolValid(String symbol)
  {
    if (!AtomicSystem.getInstance().hasElement(symbol))
    {
      throw new IllegalStateException("Unsupported atom type \"" + symbol + "\"");
    }
  }

  private void assertRadicalValid(int radical)
  {
    if (radical < 0 || radical > 3)
    {
      throw new IllegalStateException("Valid radical values are 0-3.");
    }
  }

    private class AtomImpl implements Atom
  {
    private List neighbors;
    private List bonds;
    private Molecule molecule;
    private String symbol;
    private double x;
    private double y;
    private double z;
    private int massDifference;
    private int charge;
    private int radical;
    private boolean hasSingleIsotope;

    private AtomImpl(Molecule parent, AtomImpl copyFrom)
    {
      this(parent);

      symbol = copyFrom.symbol;
      x = copyFrom.x;
      y = copyFrom.y;
      z = copyFrom.z;
      massDifference = copyFrom.massDifference;
      charge = copyFrom.charge;
      radical = copyFrom.radical;
      hasSingleIsotope = copyFrom.hasSingleIsotope;
    }

    private AtomImpl(Molecule parent)
    {
      neighbors = new ArrayList();
      bonds = new ArrayList();
      molecule = parent;
      symbol = "";
    }

    public void setRadical(int radical)
    {
      assertRadicalValid(radical);

      this.radical = radical;

      fireChange();
    }

    public void setIsotope(int isotope)
    {
      this.massDifference = isotope;
      this.hasSingleIsotope = true;

      fireChange();
    }

    public void setCharge(int charge)
    {
      this.charge = charge;

      fireChange();
    }

    public boolean isConnectedTo(Atom atom)
    {
      return neighbors.contains(atom);
    }

    public int getIndex()
    {
      return DefaultMolecule.this.atoms.indexOf(this);
    }

    public int getValence()
    {
      int result = 0;

      for (int i = 0; i < bonds.size(); i++)
      {
        result += ((Bond) bonds.get(i)).getType();
      }

      return result;
    }

    public int countNeighbors()
    {
      return neighbors.size();
    }

    public int countVirtualHydrogens()
    {
      return hCounter.countVirtualHydrogens(this);
    }

    public Bond[] getBonds()
    {
      return (Bond[]) bonds.toArray(new Bond[0]);
    }

    public int getCharge()
    {
      return charge;
    }

    public int getIsotope()
    {
      return massDifference;
    }

    public Molecule getMolecule()
    {
      return molecule;
    }

    public Atom[] getNeighbors()
    {
      return (Atom[]) neighbors.toArray(new Atom[0]);
    }

    public int getRadical()
    {
      return radical;
    }

    public String getSymbol()
    {
      return symbol;
    }

    public void setSymbol(String newSymbol)
    {
      assertAtomSymbolValid(newSymbol);

      this.symbol = newSymbol;

      fireChange();
    }

    public double getX()
    {
      return x;
    }

    public double getY()
    {
      return y;
    }

    public double getZ()
    {
      return z;
    }

    public void move(double x, double y, double z)
    {
      if (this.x == x && this.y == y && this.z == z)
      {
        return;
      }

      this.x = x;
      this.y = y;
      this.z = z;

      fireChange();
    }

    public boolean hasSingleIsotope()
    {
      return hasSingleIsotope;
    }
  }

  private class BondImpl implements Bond
  {
    private Atom source;
    private Atom target;
    private int type;
    private int stereo;
    private Molecule molecule;

    private BondImpl(Molecule parent)
    {
      source = null;
      target = null;
      molecule = parent;
    }

    public void reverse()
    {
      Atom oldSource = source;
      Atom oldTarget = target;

      source = oldTarget;
      target = oldSource;

      fireChange();
    }

    public void setStereo(int stereo)
    {
      assertBondStereoValid(stereo);

      this.stereo = stereo;

      fireChange();
    }

    public void setType(int type)
    {
      assertBondTypeValid(type);

      this.type = type;

      fireChange();
    }

    public int getIndex()
    {
      return DefaultMolecule.this.bonds.indexOf(this);
    }

    public boolean contains(Atom atom)
    {
      return (atom == source || atom == target);
    }

    public Atom getMate(Atom atom)
    {
      if (source.equals(atom))
      {
        return target;
      }

      if (target.equals(atom))
      {
        return source;
      }

      return null;
    }

    public Molecule getMolecule()
    {
      return molecule;
    }

    public Atom[] getNeighborAtoms()
    {
      Atom[] result = new Atom[source.countNeighbors() + target.countNeighbors() - 2];
      int counter = 0;

      Atom[] sourceNeighbors = source.getNeighbors();
      Atom[] targetNeighbors = target.getNeighbors();

      for (int i = 0; i < sourceNeighbors.length; i++)
      {
        Atom neighbor = sourceNeighbors[i];

        if (neighbor == source || neighbor == target)
        {
          continue;
        }
        result[counter] = neighbor;
        counter++;
      }

      for (int i = 0; i < targetNeighbors.length; i++)
      {
        Atom neighbor = targetNeighbors[i];

        if (neighbor == source || neighbor == target)
        {
          continue;
        }
        result[counter] = neighbor;
        counter++;
      }

      return result;
    }

    public Atom getSource()
    {
      return source;
    }

    public int getStereo()
    {
      return stereo;
    }

    public Atom getTarget()
    {
      return target;
    }

    public int getType()
    {
      return type;
    }
  }

  private class SubstructureImpl implements Substructure {
      private List atoms;
      private List bonds;
      private String label;
      private Molecule molecule;
      private Map bondVectorMap;

      public SubstructureImpl(Molecule parent)
      {
         molecule = parent;
         atoms = new ArrayList();
         bonds = new ArrayList();
         bondVectorMap = new HashMap();
         label = "";
      }

      public String getLabel()
      {
          return label;
      }

      public void setLabel(String label)
      {
         this.label=label;
         fireChange();
      }

      public int countAtoms()
      {
          return atoms.size();
      }

      public int countCrossingBonds()
      {
          return bonds.size();
      }

      public Atom getAtom(int index)
      {
          return (Atom) atoms.get(index);
      }

      public Bond getCrossingBond(int index)
      {
          return (Bond) bonds.get(index);
      }

      public boolean contains(Atom atom)
      {
          return atoms.contains(atom);
      }

      public boolean contains(Bond bond)
      {
          return bonds.contains(bond);  
      }

      public void addAtom(Atom atom)
      {
          if(contains(atom))
          {
            throw new RuntimeException("Trying to add the same atom twice");    
          }

          assertAtomBelongs(atom);
          atoms.add(atom);
          fireChange();          
      }

      public void removeAtom(Atom atom)
      {
          if(!contains(atom))
          {
            throw new RuntimeException("Trying to remove the non-existant atom");
          }
          
          assertAtomBelongs(atom);
          atoms.remove(atom);
          fireChange();
      }

      public void addCrossingBond(Bond bond)
      {
          assertBondBelongs(bond);
          if(contains(bond))
          {
            throw new RuntimeException("Trying to add the same crossing bond twice");    
          }

          bonds.add(bond);
          //add default bond vector
          double x=bond.getTarget().getX()-bond.getSource().getX();
          double y=bond.getTarget().getY()-bond.getSource().getY();
          bondVectorMap.put(bond, new double[]{x,y});

          fireChange();
      }

      public void removeCrossingBond(Bond bond)
      {
          assertCrossingBondBelongs(bond);
          bonds.remove(bond);
          fireChange();                   
      }

      public void setCrossingVector(Bond bond, double x, double y)
      {
          assertCrossingBondBelongs(bond);
          bondVectorMap.put(bond, new double[]{x,y});
          fireChange();
      }

      public double getCrossingVectorX(Bond bond)
      {
          assertCrossingBondBelongs(bond);                    
          return  ((double[])bondVectorMap.get(bond))[0];
      }

      public double getCrossingVectorY(Bond bond)
      {
          assertCrossingBondBelongs(bond);                              
          return  ((double[])bondVectorMap.get(bond))[1];
      }

      public int getIndex()
      {
          return DefaultMolecule.this.substructures.indexOf(this);
      }

      public Molecule getMolecule()
      {
          return molecule;  
      }

      private void assertCrossingBondBelongs(Bond bond){
          assertBondBelongs(bond);
          if (!bonds.contains(bond))
          {
            throw new IllegalStateException("Attempt to use a non-crossing bond.");
          }
      }
  }
}