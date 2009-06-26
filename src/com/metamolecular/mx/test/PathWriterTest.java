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

import com.metamolecular.mx.path.PathWriter;
import com.metamolecular.mx.model.Atom;
import com.metamolecular.mx.model.Bond;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 */
public class PathWriterTest extends TestCase
{
  private Set<String> paths;
  private PathWriter writer;
  private List<Atom> path;
  private List<Atom> aromatics;

  @Override
  protected void setUp() throws Exception
  {
    paths = new HashSet();
    path = new ArrayList();
    writer = new PathWriter();
    aromatics = new ArrayList();
  }

  public void testItWritesAllIntermediatePathsOfLinearChain()
  {
    chain(6);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".", "..", "...", "....", ".....", "......")), paths);
  }

  public void testItWritesAllIntermediatePathsAndRingClosureOfCycle()
  {
    cycle(6);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".", "..", "...", "....", ".....", "......", "......-6")), paths);
  }

  public void testItWritesAllIntermediatePathsAndBothRingClosuresOfBicycle()
  {
    cycle(6);
    when(path.get(2).isConnectedTo(path.get(5))).thenReturn(true);
    when(path.get(5).isConnectedTo((path.get(2)))).thenReturn(true);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".", "..", "...", "....", ".....", "......", "......-4", "......-6")), paths);
  }

  public void testItWritesAllIntermediatePathsAndThreeRingClosuresOfTricycle()
  {
    cycle(6);
    when(path.get(2).isConnectedTo(path.get(5))).thenReturn(true);
    when(path.get(5).isConnectedTo((path.get(2)))).thenReturn(true);
    when(path.get(3).isConnectedTo(path.get(5))).thenReturn(true);
    when(path.get(5).isConnectedTo((path.get(3)))).thenReturn(true);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".", "..", "...", "....", ".....", "......",
      "......-3", "......-4", "......-6")), paths);
  }

  public void testItWritesAllIntermediatePathsAndUnsaturatedRingClosureOfCycle()
  {
    cycle(6);
    Bond db = mock(Bond.class);
    when(db.getType()).thenReturn(2);
    when(path.get(0).getBond(path.get(5))).thenReturn(db);
    when(path.get(5).getBond(path.get(0))).thenReturn(db);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".%", ".%.", ".%..", ".%...", ".%....", ".%.....%", ".%.....%-6")), paths);
  }

  public void testItWritesAromaticAtoms()
  {
    chain(3);
    aromatics.add(path.get(0));
    aromatics.add(path.get(1));
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".%", ".%.%", ".%.%.")), paths);
  }

  public void testItWritesTerminalSP2()
  {
    chain(4);
    Bond db = mock(Bond.class);
    when(db.getType()).thenReturn(2);
    when(path.get(0).getBond(path.get(1))).thenReturn(db);
    when(path.get(1).getBond(path.get(0))).thenReturn(db);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".%", ".%.%", ".%.%.", ".%.%..")), paths);
  }

  public void testItWritesInternalSP2()
  {
    chain(4);
    Bond db = mock(Bond.class);
    when(db.getType()).thenReturn(2);
    when(path.get(1).getBond(path.get(2))).thenReturn(db);
    when(path.get(2).getBond(path.get(1))).thenReturn(db);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".", "..%", "..%.%", "..%.%.")), paths);
  }

  public void testItWritesTerminalSP3()
  {
    chain(4);
    Bond db = mock(Bond.class);
    when(db.getType()).thenReturn(3);
    when(path.get(0).getBond(path.get(1))).thenReturn(db);
    when(path.get(1).getBond(path.get(0))).thenReturn(db);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".#", ".#.#", ".#.#.", ".#.#..")), paths);
  }

  public void testItWritesInternalSP3()
  {
    chain(4);
    Bond db = mock(Bond.class);
    when(db.getType()).thenReturn(3);
    when(path.get(1).getBond(path.get(2))).thenReturn(db);
    when(path.get(2).getBond(path.get(1))).thenReturn(db);
    doWrite();

    assertEquals(new HashSet(Arrays.asList(
      ".", "..#", "..#.#", "..#.#.")), paths);
  }

  public void testItClearsSP2BetweenInvocations()
  {
    testItWritesInternalSP2();
    path.clear();
    paths.clear();
    testItWritesTerminalSP2();
  }

  private void doWrite()
  {
    writer.write(path, paths, aromatics);
  }

  private void chain(int length)
  {
    Bond single = mock(Bond.class);

    when(single.getType()).thenReturn(1);

    for (int i = 0; i < length; i++)
    {
      Atom atom = mock(Atom.class);

      when(atom.getSymbol()).thenReturn(".");
      when(atom.isConnectedTo(any(Atom.class))).thenReturn(false);
      when(atom.getBond(any(Atom.class))).thenReturn(single);

      path.add(atom);
    }
  }

  private void cycle(int size)
  {
    chain(size);
    when(path.get(0).isConnectedTo(path.get(size - 1))).thenReturn(true);
    when(path.get(size - 1).isConnectedTo((path.get(0)))).thenReturn(true);
  }
}
