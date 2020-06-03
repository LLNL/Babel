/*
 * File:          SortTest_Impl.java
 * Symbol:        sorting.SortTest-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SortTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package sorting;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import sorting.SortingAlgorithm;

// DO-NOT-DELETE splicer.begin(sorting.SortTest._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.SortTest._imports)

/**
 * Symbol "sorting.SortTest" (version 0.1)
 * 
 * Run a bunch of sorts through a stress test.
 */
public class SortTest_Impl extends SortTest
{

  // DO-NOT-DELETE splicer.begin(sorting.SortTest._data)
  static int[] s_testSizes = {  
  0,
  1,
  2,
  3,
  4,
  7,
  10,
  51,
  64,
  -1
};

  static boolean notSorted(sorting.Container cont,
              sorting.Comparator comp)
  {
    int length = cont.getLength();
    int i;
    for(i = 1 ; i < length ; ++i ){
      if (cont.compare(i-1, i, comp) > 0) return true;
    }
    return false;
  }

  static boolean sortAndReport(sorting.SortingAlgorithm alg,
                     sorting.Container cont,
                        sorting.Comparator comp,
                        boolean result)
  {
   
    synch.RegOut tracker = synch.RegOut.getInstance();
    sorting.Counter swpCnt = null;
    sorting.Counter cmpCnt = null;
    alg.reset();
    alg.sort(cont, comp);
    swpCnt = alg.getSwapCounter();
    cmpCnt = alg.getCompareCounter();
    java.lang.String buffer = new 
      java.lang.String("compares ("+cmpCnt.getCount()+") swaps ("+swpCnt.getCount()+")");
    tracker.writeComment(buffer);
    if (notSorted(cont, comp)) {
      tracker.writeComment("sort failed!!");
      result = false;
    }
    return result;
  }

  // DO-NOT-DELETE splicer.end(sorting.SortTest._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.SortTest._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.SortTest._load)

  }

  /**
   * User defined constructor
   */
  public SortTest_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.SortTest.SortTest)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.SortTest.SortTest)

  }

  /**
   * Back door constructor
   */
  public SortTest_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.SortTest._wrap)
    // Insert-Code-Here {sorting.SortTest._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.SortTest._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.SortTest._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.SortTest._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.SortTest.finalize)
    // Insert-Code-Here {sorting.SortTest.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.SortTest.finalize)

  }

  // user defined static methods:
  /**
   * Perform the array stress test.
   * 
   * Return true if all the algorithms work okay.
   */
  public static boolean stressTest_Impl (
    /*in*/ sorting.SortingAlgorithm.Array1 algs ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SortTest.stressTest)
    // insert implementation here
    boolean result = false;
    if (algs != null) {
      int lower = algs._lower(0);
      int upper = algs._upper(0);
      int i,j;
      result = true;
      for(i = lower; i <= upper; ++i) {
        sorting.SortingAlgorithm alg =
          algs.get(i);
        if (alg != null) {
          synch.RegOut tracker = synch.RegOut.getInstance();
          sorting.IntegerContainer data = new sorting.IntegerContainer();
          //sorting.Container cont = (sorting.Container) sorting.Container.Wrapper._cast(data);
          sorting.CompInt intcomp = new sorting.CompInt();
          sorting.Comparator comp = (sorting.Comparator) sorting.Comparator.Wrapper._cast(intcomp);
          java.lang.String name = alg.getName();
          java.lang.String buffer = null;
          j = 0;
          buffer = new java.lang.String("****ALGORITHM IS "+name+"****");
          tracker.writeComment(buffer);
          buffer = null;
          System.gc();
          while (s_testSizes[j] >= 0) {
            intcomp.setSortIncreasing(true);
            buffer = new java.lang.String("DATA SIZE " + s_testSizes[j]);
            tracker.writeComment(buffer);
            data.setLength(s_testSizes[j]);
            result = sortAndReport(alg, data, comp, result);
            tracker.writeComment("pre-sorted list");
            result = sortAndReport(alg, data, comp, result);
            tracker.writeComment("reverse sorted list");
            intcomp.setSortIncreasing(false);
            result = sortAndReport(alg, data, comp, result);
            ++j;
          }
          buffer = null;
        }
        else{
          result = false;
        }
      }
    }
    return result;
    
    // DO-NOT-DELETE splicer.end(sorting.SortTest.stressTest)

  }


  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(sorting.SortTest._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.SortTest._misc)

} // end class SortTest

