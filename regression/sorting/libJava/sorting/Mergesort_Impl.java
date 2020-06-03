/*
 * File:          Mergesort_Impl.java
 * Symbol:        sorting.Mergesort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Mergesort
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

// DO-NOT-DELETE splicer.begin(sorting.Mergesort._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.Mergesort._imports)

/**
 * Symbol "sorting.Mergesort" (version 0.1)
 * 
 * Merge sort
 */
public class Mergesort_Impl extends Mergesort
{

  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._data)
  static void
    mergeLists(sorting.Container  elems,
               sorting.Comparator comp,
               sorting.Counter    cmp,
               sorting.Counter    swp,
               int             start,
               int             mid,
               int             end)
{
  int j;
  while ((start < mid) && (mid < end)) {
    cmp.inc();
    if (elems.compare(start, mid, comp) > 0) {
      /* move first element of upper list into place */
      for(j = mid;j > start; --j) {
        swp.inc();
        elems.swap(j, j - 1);
      }
      ++mid;
    }
    ++start;
  }
}
  /**
 * end is one past the end
 */
  static void
    mergeSort(sorting.Container  elems,
              sorting.Comparator comp,
              sorting.Counter    cmp,
              sorting.Counter    swp,
              int             start,
              int             end)
{
  if ((end - start) > 1) {
    int mid = (start + end) >> 1;
    mergeSort(elems, comp, cmp, swp, start, mid);
    mergeSort(elems, comp, cmp, swp, mid, end);
    mergeLists(elems, comp, cmp, swp, start, mid, end);
  }
}

  // DO-NOT-DELETE splicer.end(sorting.Mergesort._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.Mergesort._load)

  }

  /**
   * User defined constructor
   */
  public Mergesort_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.Mergesort.Mergesort)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.Mergesort.Mergesort)

  }

  /**
   * Back door constructor
   */
  public Mergesort_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.Mergesort._wrap)
    // Insert-Code-Here {sorting.Mergesort._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.Mergesort._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Mergesort.finalize)
    // Insert-Code-Here {sorting.Mergesort.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.Mergesort.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Sort elements using Merge Sort.
   */
  public void sort_Impl (
    /*in*/ sorting.Container elems,
    /*in*/ sorting.Comparator comp ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort)
    // insert implementation here
    int num = elems.getLength();
    sorting.Counter cmp = getCompareCounter();
    sorting.Counter swp = getSwapCounter();
    mergeSort(elems, comp, cmp, swp, 0, num);
    return ;
    // DO-NOT-DELETE splicer.end(sorting.Mergesort.sort)

  }

  /**
   * Return merge sorting.
   */
  public java.lang.String getName_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName)
    // insert implementation here
    return new java.lang.String("Merge sort");
    // DO-NOT-DELETE splicer.end(sorting.Mergesort.getName)

  }


  // DO-NOT-DELETE splicer.begin(sorting.Mergesort._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.Mergesort._misc)

} // end class Mergesort

