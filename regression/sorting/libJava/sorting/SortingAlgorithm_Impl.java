/*
 * File:          SortingAlgorithm_Impl.java
 * Symbol:        sorting.SortingAlgorithm-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SortingAlgorithm
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

// DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._imports)

/**
 * Symbol "sorting.SortingAlgorithm" (version 0.1)
 * 
 * An abstract sorting algorithm.
 */
public class SortingAlgorithm_Impl extends SortingAlgorithm
{

  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._data)
  sorting.Counter d_cmp;
  sorting.Counter d_swp;
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._load)

  }

  /**
   * User defined constructor
   */
  public SortingAlgorithm_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.SortingAlgorithm)
    // add construction details here
    d_cmp = (sorting.Counter) sorting.Counter.Wrapper._cast(new sorting.SimpleCounter());
    d_swp = (sorting.Counter) sorting.Counter.Wrapper._cast(new sorting.SimpleCounter());
    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.SortingAlgorithm)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.finalize)
    // Insert-Code-Here {sorting.SortingAlgorithm.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Return the comparison counter.
   */
  public sorting.Counter getCompareCounter_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getCompareCounter)
    // insert implementation here
    sorting.Counter result = null;
    result = d_cmp;
    return result;

    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getCompareCounter)

  }

  /**
   * Return the swap counter.
   */
  public sorting.Counter getSwapCounter_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getSwapCounter)
    // insert implementation here
    sorting.Counter result = null;
    result = d_swp;
    return result;

    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getSwapCounter)

  }

  /**
   * Reset the comparison and swap counter.
   */
  public void reset_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.reset)
    // insert implementation here
    d_swp.reset();
    d_cmp.reset();
    return ;
    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.reset)

  }


  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._misc)

} // end class SortingAlgorithm

