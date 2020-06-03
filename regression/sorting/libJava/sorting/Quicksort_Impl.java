/*
 * File:          Quicksort_Impl.java
 * Symbol:        sorting.Quicksort-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Quicksort
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

// DO-NOT-DELETE splicer.begin(sorting.Quicksort._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.Quicksort._imports)

/**
 * Symbol "sorting.Quicksort" (version 0.1)
 * 
 * Quick sort
 */
public class Quicksort_Impl extends Quicksort
{

  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._data)
  int choosePivot(sorting.Container  elems,
                sorting.Comparator comp,
                sorting.Counter    cmp,
                int         start,
                int         end)
  {
    int pivot = (start + end) >> 1;
    if ((end - start) > 4) {
      int mid = pivot;
      cmp.inc();
      if (elems.compare(start, mid, comp) <= 0) {
        cmp.inc();
        if (elems.compare(mid, end - 1, comp) > 0) {
          cmp.inc();
          if (elems.compare(start, end - 1, comp) < 0) {
            pivot = end - 1;
          }
          else {
            pivot = start;
          }
        }
      }
      else {
        cmp.inc();
        if (elems.compare(mid, end - 1, comp) < 0) {
          cmp.inc();
          if (elems.compare(start, end - 1, comp) > 0) {
            pivot = end - 1;
          }
          else {
            pivot = start;
          }
        }
      }
    }
    return pivot;
  }
  
  void quickSort(sorting.Container  elems,
              sorting.Comparator comp,
              sorting.Counter    cmp,
              sorting.Counter    swp,
              int         start,
              int         end)
  {
    if ((end - start) > 1) {
      int pivot = choosePivot(elems, comp, cmp, start, end);
      int i = start;
      int j = end;
      if (pivot != start) {
        swp.inc();
        elems.swap(start, pivot);
      }
      for(;;) {
        do {
          --j;
          cmp.inc();
        } while (elems.compare(start, j, comp) < 0);
        while (++i < j) {
          cmp.inc();
          if (elems.compare(start, i, comp) < 0) break;
        }
        if (i >= j) break;
        swp.inc();
        elems.swap(i, j);
      }
      if (j != start) {
        swp.inc();
        elems.swap(start, j);
      }
      quickSort(elems, comp, cmp, swp, start, j);
      quickSort(elems, comp, cmp, swp, j + 1, end);
    }
  }

  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._load)

  }

  /**
   * User defined constructor
   */
  public Quicksort_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.Quicksort.Quicksort)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.Quicksort.Quicksort)

  }

  /**
   * Back door constructor
   */
  public Quicksort_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.Quicksort._wrap)
    // Insert-Code-Here {sorting.Quicksort._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.Quicksort._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Quicksort._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.Quicksort._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Quicksort.finalize)
    // Insert-Code-Here {sorting.Quicksort.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.Quicksort.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Sort elements using Quick Sort.
   */
  public void sort_Impl (
    /*in*/ sorting.Container elems,
    /*in*/ sorting.Comparator comp ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Quicksort.sort)
    // insert implementation here
    int num = elems.getLength();
    sorting.Counter cmp = getCompareCounter();
    sorting.Counter swp = getSwapCounter();
    quickSort(elems, comp, cmp, swp, 0, num);
    return ;
    // DO-NOT-DELETE splicer.end(sorting.Quicksort.sort)

  }

  /**
   * Return quick sorting.
   */
  public java.lang.String getName_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Quicksort.getName)
    // insert implementation here
    return new java.lang.String("Quick sort");
    // DO-NOT-DELETE splicer.end(sorting.Quicksort.getName)

  }


  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._misc)

} // end class Quicksort

