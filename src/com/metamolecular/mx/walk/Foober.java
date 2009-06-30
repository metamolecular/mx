/*
 * MX Cheminformatics Tools for Java
 *
 * Copyright (c) 2007-2008 Metamolecular, LLC
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
package com.metamolecular.mx.walk;

import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class Foober implements Reporter
{
  private Collection output;
  private Map<Atom, String> overrides;
  private List<Atom> path;

  public Foober(Collection output)
  {
    this.output = output;
    this.overrides = new HashMap();
    path = new ArrayList();
  }

  public void setDictionary(Map<Atom, String> overrides)
  {
    this.overrides.clear();
    this.overrides.putAll(overrides);
  }

  public void walkEnd(Atom root)
  {
    writePath();
  }

  public void walkStart(Atom atom)
  {
  }

  public void atomFound(Atom atom)
  {
    path.add(atom);
  }

  public void branchEnd(Atom atom)
  {
    writePath();
  }

  public void branchStart(Atom atom)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void ringClosed(Bond bond)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  private void writePath()
  {
    StringBuffer buffer = new StringBuffer();
    
    for (Atom atom : path)
    {
      write(atom, buffer);
    }
    
    output.add(buffer.toString());
  }

  private void write(Atom atom, StringBuffer buffer)
  {
    String type = overrides.get(atom);

    if (type == null)
    {
      type = atom.getSymbol();
    }

    buffer.append(type);
  }
}
