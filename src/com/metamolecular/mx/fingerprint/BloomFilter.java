/*
 * MX - Essential Cheminformatics
 * 
 * Copyright (c) 2007-2009 Metamolecular, LLC
 * Copyright (c) Ian Clarke
 * 
 * http://metamolecular.com/mx
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
package com.metamolecular.mx.fingerprint;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * A simple Bloom Filter (see http://en.wikipedia.org/wiki/Bloom_filter) that
 * uses java.util.Random as a primitive hash function, and which implements
 * Java's Set interface for convenience.
 * 
 * Only the add(), addAll(), contains(), and containsAll() methods are
 * implemented. Calling any other method will yield an 
 * UnsupportedOperationException.
 * 
 * Based on a method described described by Ian Clarke in:
 * 
 * http://blog.locut.us/2008/01/12/a-decent-stand-alone-java-bloom-filter-implementation/
 * 
 * @author Ian Clarke <ian@uprizer.com>
 * @author Richard L. Apodaca <rapodaca at metamolecular.com>
 * 
 * @param <E> The type of object the BloomFilter should contain
 */
public class BloomFilter<E> implements Set<E>
{
  protected int k;
  BitSet bitSet;
  int bitArraySize, expectedElements;
  
  /**
   * Simplified constructor that sets the number of expected elements equal to
   * the number of bits.
   * 
   * @param bitArraySize
   *            The number of bits in the bit array (often called 'm' in the
   *            context of bloom filters).
   */
  public BloomFilter(int bitArraySize)
  {
    this(bitArraySize, bitArraySize);
  }

  /**
   * You must specify the number of bits in the
   * Bloom Filter, and also you should specify the number of items you
   * expect to add. The latter is used to choose some optimal internal values to
   * minimize the false-positive rate (which can be estimated with
   * expectedFalsePositiveRate()).
   * 
   * @param bitArraySize
   *            The number of bits in the bit array (often called 'm' in the
   *            context of bloom filters).
   * @param expectedElements
   *            The typical number of items you expect to be added to the
   *            SimpleBloomFilter (often called 'n').
   */
  public BloomFilter(int bitArraySize, int expectedElements)
  {
    this.bitArraySize = bitArraySize;
    this.expectedElements = expectedElements;
    this.k = (int) Math.ceil((bitArraySize / expectedElements) * Math.log(2.0));
    bitSet = new BitSet(bitArraySize);
  }

  /**
   * Calculates the approximate probability of the contains() method returning
   * true for an object that had not previously been inserted into the bloom
   * filter. This is known as the "false positive probability".
   * 
   * @return The estimated false positive rate
   */
  public double expectedFalsePositiveProbability()
  {
    return Math.pow((1 - Math.exp(-k * (double) expectedElements / (double) bitArraySize)), k);
  }

  /*
   * @return This method will always return false
   * 
   * @see java.util.Set#add(java.lang.Object)
   */
  public boolean add(E o)
  {
    Random r = new Random(o.hashCode());
    for (int i = 0; i < k; i++)
    {
      bitSet.set(r.nextInt(bitArraySize), true);
    }
    return false;
  }

  /**
   * @return This method will always return false
   */
  public boolean addAll(Collection<? extends E> c)
  {
    for (E o : c)
    {
      add(o);
    }
    return false;
  }

  /**
   * Clear the Bloom Filter
   */
  public void clear()
  {
    for (int x = 0; x < bitSet.length(); x++)
    {
      bitSet.set(x, false);
    }
  }

  /**
   * @return False indicates that o was definitely not added to this Bloom Filter, 
   *         true indicates that it probably was.  The probability can be estimated
   *         using the expectedFalsePositiveProbability() method.
   */
  public boolean contains(Object o)
  {
    Random r = new Random(o.hashCode());
    for (int x = 0; x < k; x++)
    {
      if (!bitSet.get(r.nextInt(bitArraySize)))
      {
        return false;
      }
    }
    return true;
  }

  public boolean containsAll(Collection<?> c)
  {
    for (Object o : c)
    {
      if (!contains(o))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Not implemented
   */
  public boolean isEmpty()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public Iterator<E> iterator()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public boolean remove(Object o)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public boolean removeAll(Collection<?> c)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public boolean retainAll(Collection<?> c)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public int size()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public Object[] toArray()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Not implemented
   */
  public <T> T[] toArray(T[] a)
  {
    throw new UnsupportedOperationException();
  }
  
  public BitSet toBitSet()
  {
    BitSet result = new BitSet(bitArraySize);
    
    result.or(bitSet);
    
    return result;
  }
  
  public int getBitArraySize()
  {
    return bitArraySize;
  }
}
