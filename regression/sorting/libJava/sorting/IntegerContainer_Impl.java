/*
 * File:          IntegerContainer_Impl.java
 * Symbol:        sorting.IntegerContainer-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.IntegerContainer
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package sorting;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import sorting.Comparator;
import sorting.Container;

// DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._imports)
// Put additional imports here...
import java.util.Random;
import java.lang.Math;
// DO-NOT-DELETE splicer.end(sorting.IntegerContainer._imports)

/**
 * Symbol "sorting.IntegerContainer" (version 0.1)
 * 
 * Integer container.
 */
public class IntegerContainer_Impl extends IntegerContainer
{

  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._data)
  private sorting.Integer.Array1 d_elements;
  private Random generator = new Random();
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._load)

  }

  /**
   * User defined constructor
   */
  public IntegerContainer_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.IntegerContainer)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.IntegerContainer)

  }

  /**
   * Back door constructor
   */
  public IntegerContainer_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._wrap)
    // Insert-Code-Here {sorting.IntegerContainer._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.finalize)
    // Insert-Code-Here {sorting.IntegerContainer.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * This sets the container length and pseudo-randomly orders the
   * Integer elements contained.
   */
  public void setLength_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.setLength)
    if (len >= 0) {
      int i, j;
      if (d_elements != null) {
        d_elements = null;
      }
      d_elements = new sorting.Integer.Array1(len, true);
      for(i = 0; i < len ; ++i ) {
        sorting.Integer iptr = new sorting.Integer();
        iptr.setValue(i+1);
        d_elements.set(i, iptr);
        iptr = null;
      }
      /* shuffle the list */
      for(i = len - 1; i > 0; --i) {
        j = (java.lang.Math.abs(generator.nextInt()) % (i + 1));
        if (j != i) {
          swap(i, j);
        }
      }
    }
    return ;
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.setLength)

  }

  /**
   * Return the number of elements in the container.
   */
  public int getLength_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.getLength)
    // insert implementation here
    return d_elements.length();
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.getLength)

  }

  /**
   * Return -1 if element i is less than element j, 0 if element i
   * is equal to element j, or otherwise 1.
   */
  public int compare_Impl (
    /*in*/ int i,
    /*in*/ int j,
    /*in*/ sorting.Comparator comp ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.compare)
    // insert implementation here
    int result = 0;
    if (d_elements != null) {
      sorting.Integer i1 = d_elements.get(i);
      sorting.Integer i2 = d_elements.get(j);
      result = comp.compare((sidl.BaseInterface)i1,
                            (sidl.BaseInterface)i2);
    }
    return result;
    
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.compare)

  }

  /**
   * Swap elements i and j.
   */
  public void swap_Impl (
    /*in*/ int i,
    /*in*/ int j ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.swap)
    // insert implementation here
    if (d_elements != null) {
      int len = getLength();
      if ((i >= 0) && (j >= 0) && (i < len) && (j < len)) {
        sorting.Integer i1 = d_elements.get(i);
        sorting.Integer i2 = d_elements.get(j);
        d_elements.set(i, i2);
        d_elements.set(j, i1);
      } else {
        synch.RegOut tracker = synch.RegOut.getInstance();
        System.err.println("sorting::IntegerContainer::swap index out of bounds swap("
                           +i+", "+j+") len ("+len+")\n");
      tracker.forceFailure();
    }
  }
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.swap)

  }

  /**
   * Print elements s through e-1
   */
  public void output_Impl (
    /*in*/ int s,
    /*in*/ int e ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.output)
    // insert implementation here
    if (d_elements != null) {
      synch.RegOut tracker = synch.RegOut.getInstance();
      java.lang.String text = new java.lang.String("list");
      while (s < e) {
        text = text + " " + d_elements.get(s++).getValue();
      }
      tracker.writeComment(text);
    }
    return ;
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.output)

  }


  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._misc)

} // end class IntegerContainer

