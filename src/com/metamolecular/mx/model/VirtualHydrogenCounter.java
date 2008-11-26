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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Apodaca
 */
public class VirtualHydrogenCounter
{
  private Map groups;

  public VirtualHydrogenCounter()
  {
    groups = new HashMap();

    buildGroups();
  }

  public boolean isVirtualizableHeavyAtom(Atom atom)
  {
    return groups.containsKey(atom.getSymbol());
  }

  public int countVirtualHydrogens(Atom atom)
  {
    int group = getGroup(atom);

    if (group == -1)
    {
      return 0;
    }
    switch (group)
    {
      case 13:
        return countGroup13VirtualHydrogens(atom);
      case 14:
        return countGroup14VirtualHydrogens(atom);
      case 15:
        return countGroup15VirtualHydrogens(atom);
      case 16:
        return countGroup16VirtualHydrogens(atom);
      case 17:
        return countGroup17VirtualHydrogens(atom);
    }

    throw new RuntimeException("Unrecognized group: " + group);
  }

  private int countGroup13VirtualHydrogens(Atom atom)
  {
    if (atom.getCharge() != 0)
    {
      return 0;
    }
    return countGroup15VirtualHydrogens(atom);
  }

  private int countGroup14VirtualHydrogens(Atom atom)
  {
    if (atom.getValence() > 3)
    {
      return 0;
    }
    if (Math.abs(atom.getCharge()) > 1)
    {
      return 0;
    }
    return (atom.getValence() > 4) ? 0 : 4 - (atom.getValence() + Math.abs(atom.getCharge()));
  }

  private int countGroup15VirtualHydrogens(Atom atom)
  {
    if (atom.getValence() > 3)
    {
      return 0;
    }
    if (Math.abs(atom.getCharge()) > 1)
    {
      return 0;
    }

    int radicalElectronCount = 0;

    switch (atom.getRadical())
    {
      case 1:
        radicalElectronCount = 1;
        break;
      case 2:
        radicalElectronCount = 2;
        break;
      case 3:
        radicalElectronCount = 2;
        break;
    }
    return 3 - (atom.getValence() - atom.getCharge() + radicalElectronCount);
  }

  private int countGroup16VirtualHydrogens(Atom atom)
  {
    if (atom.getValence() >= 2)
    {
      return 0;
    }
    if (Math.abs(atom.getCharge()) >= 2)
    {
      return 0;
    }

    int radicalElectronCount = 0;

    switch (atom.getRadical())
    {
      case 1:
        radicalElectronCount = 1;
        break;
      case 2:
        radicalElectronCount = 2;
        break;
      case 3:
        radicalElectronCount = 2;
        break;
    }

    return 2 - (atom.getValence() - atom.getCharge() + radicalElectronCount);
  }

  private int countGroup17VirtualHydrogens(Atom atom)
  {
    if (atom.getValence() != 0)
    {
      return 0;
    }
    return (atom.getValence() > 1) ? 0 : 1 - (atom.getValence() - atom.getCharge());
  }

  private void buildGroups()
  {
    String[] g13 =
    {
      "B", "Al", "Ga", "In", "Tl"
    };
    String[] g14 =
    {
      "C", "Si", "Ge", "Sn", "Pb"
    };
    String[] g15 =
    {
      "N", "P", "As", "Sb", "Bi"
    };
    String[] g16 =
    {
      "O", "S", "Se", "Te", "Po"
    };
    String[] g17 =
    {
      "F", "Cl", "Br", "I", "At"
    };

    buildGroup(g13, 13);
    buildGroup(g14, 14);
    buildGroup(g15, 15);
    buildGroup(g16, 16);
    buildGroup(g17, 17);
  }

  private void buildGroup(String[] atoms, int group)
  {
    Integer g = new Integer(group);

    for (int i = 0; i < atoms.length; i++)
    {
      groups.put(atoms[i], g);
    }
  }

  private int getGroup(Atom atom)
  {
    Object result = groups.get(atom.getSymbol());

    return (result == null) ? -1 : ((Integer) result).intValue();
  }
}
