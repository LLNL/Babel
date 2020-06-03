/*
 * File:          Heapsort_Impl.java
 * Symbol:        sorting.Heapsort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Heapsort
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
import sorting.Counter;
import sorting.SortingAlgorithm;

// DO-NOT-DELETE splicer.begin(sorting.Heapsort._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.Heapsort._imports)

/**
 * Symbol "sorting.Heapsort" (version 0.1)
 * 
 * Heap sort
 */
public class Heapsort_Impl extends Heapsort
{

  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._data)
  // Put additional private data here...
  static void remakeHeap(sorting.Container elem,
                         sorting.Comparator comp,
                         sorting.Counter cmp,
                         sorting.Counter swp,
                         int last,
                         int first)
  {
    int half = (last >> 1) - 1;
    int child;
    while (first <= half) {
      child = first + first + 1;
      if ((child+1) < last) {
        cmp.inc();
        if (elem.compare(child, child+1, comp) < 0) ++child;
      }
      cmp.inc();
      if (elem.compare(first, child,comp) >= 0) break;
      swp.inc();
      elem.swap(first, child);
      first = child;
    }
  }
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._load)

  }

  /**
   * User defined constructor
   */
  public Heapsort_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.Heapsort.Heapsort)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.Heapsort.Heapsort)

  }

  /**
   * Back door constructor
   */
  public Heapsort_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.Heapsort._wrap)
    // Insert-Code-Here {sorting.Heapsort._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.Heapsort._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Heapsort._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.Heapsort._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Heapsort.finalize)
    // Insert-Code-Here {sorting.Heapsort.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.Heapsort.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Sort elements using Heap Sort.
   */
  public void sort_Impl (
    /*in*/ sorting.Container elems,
    /*in*/ sorting.Comparator comp ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Heapsort.sort)
    // insert implementation here
     int i;
     int num = elems.getLength();
     sorting.Counter cmp = getCompareCounter();
     sorting.Counter swp = getSwapCounter();
     /* make the heap */
     for(i = ((num/2) - 1); i >= 0; --i) {
       remakeHeap(elems, comp, cmp, swp, num, i);
     }
     /* put top of heap at back and remake the heap */
     i = num - 1;
     while (i > 0) {
       swp.inc();
       elems.swap(0, i);
       remakeHeap(elems, comp, cmp, swp, i--, 0);
     }
     return ;
    // DO-NOT-DELETE splicer.end(sorting.Heapsort.sort)

  }

  /**
   * Return heap sorting.
   */
  public java.lang.String getName_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Heapsort.getName)
    // insert implementation here
    return new java.lang.String("Heap sort");

    // DO-NOT-DELETE splicer.end(sorting.Heapsort.getName)

  }


  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._misc)

} // end class Heapsort

