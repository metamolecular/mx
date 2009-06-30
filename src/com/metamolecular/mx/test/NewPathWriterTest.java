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
package com.metamolecular.mx.test;

import com.metamolecular.mx.walk.PathWriter;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class NewPathWriterTest extends TestCase
{
  private PathWriter foober;
  private Collection<String> paths;
  private Atom atom;
  private Map<Atom, String> dictionary;

  @Override
  protected void setUp() throws Exception
  {
    atom = mock(Atom.class);
    paths = mock(Set.class);
    foober = null;
    dictionary = new HashMap();

    when(atom.getSymbol()).thenReturn(".");
  }

  public void testItWritesAtomAfterAtomFound()
  {
    doNew();
    foober.atomFound(atom);

    verify(paths, times(1)).add(".");
  }

  public void testItWritesTwoAtoms()
  {
    doNew();
    foober.atomFound(atom);
    foober.atomFound(atom);

    InOrder sequence = inOrder(paths);

    sequence.verify(paths).add(".");
    sequence.verify(paths).add("..");
  }

  public void testItBacktracksWhenBranchStarted()
  {
    doNew();

    Atom atom1 = mockAtom("1");
    Atom atom2 = mockAtom("2");
    Atom atom3 = mockAtom("3");

    foober.atomFound(atom1);
    foober.atomFound(atom2);
    foober.branchStart(atom1);
    foober.atomFound(atom3);

    InOrder sequence = inOrder(paths);

    sequence.verify(paths, times(1)).add("1");
    sequence.verify(paths, times(1)).add("12");
    sequence.verify(paths, times(1)).add("13");
  }

  public void testItThrowsWhenBranchingFromNonexistantAtom()
  {
    doNew();

    Atom atom1 = mockAtom("1");
    Atom atom2 = mockAtom("2");
    Atom atom3 = mockAtom("3");

    foober.atomFound(atom1);
    foober.atomFound(atom2);

    try
    {
      foober.branchStart(atom3);
      fail("Exception not thrown");
    }
    catch (RuntimeException e)
    {
    }
  }

  public void testItWritesAtomInDictionary()
  {
    doNew();
    dictionary.put(atom, ".%");
    foober.setDictionary(dictionary);
    foober.atomFound(atom);

    verify(paths, times(1)).add(".%");
  }

  public void testItWritesRingClosure()
  {
    doNew();

    Atom atom1 = mockAtom("1");
    Atom atom2 = mockAtom("2");
    Atom atom3 = mockAtom("3");
    Bond closure = mock(Bond.class);

    when(closure.getMate(atom3)).thenReturn(atom1);

    foober.atomFound(atom1);
    foober.atomFound(atom2);
    foober.atomFound(atom3);
    foober.ringClosed(closure);

    InOrder sequence = inOrder(paths);

    sequence.verify(paths, times(1)).add("1");
    sequence.verify(paths, times(1)).add("12");
    sequence.verify(paths, times(1)).add("123");
    sequence.verify(paths, times(1)).add("123-3");
  }

  public void testItRaisesWhenClosingToEmptyPath()
  {
    doNew();

    Bond closure = mock(Bond.class);

    try
    {
      foober.ringClosed(closure);

      fail("Exception not thrown");
    }
    catch (Exception e)
    {
      assertEquals("Attempt to close empty path.", e.getMessage());
    }
  }

  public void testItRaisesWhenClosingToNonexistantAtom()
  {
    doNew();

    Bond closure = mock(Bond.class);
    Atom maverick = mock(Atom.class);

    when(closure.getMate(atom)).thenReturn(maverick);

    foober.atomFound(atom);
    foober.atomFound(atom);
    foober.atomFound(atom);

    try
    {
      foober.ringClosed(closure);

      fail("Exception not thrown");
    }
    catch (Exception e)
    {
      assertEquals("Attempt to close nonexistant atom" + maverick, e.getMessage());
    }
  }

  public void testItRaisesWhenClosingToNonRingAtom()
  {
    doNew();

    Bond closure = mock(Bond.class);
    Atom shorty = mock(Atom.class);

    when(closure.getMate(atom)).thenReturn(shorty);

    foober.atomFound(shorty);
    foober.atomFound(atom);

    try
    {
      foober.ringClosed(closure);

      fail("Exception not thrown");
    }
    catch (Exception e)
    {
      assertEquals("Atom closes rings with size less than three " + shorty, e.getMessage());
    }
  }

  private void doNew()
  {
    foober = new PathWriter(paths);
  }

  private Atom mockAtom(String label)
  {
    Atom result = mock(Atom.class);

    when(result.getSymbol()).thenReturn(label);

    return result;
  }
}
