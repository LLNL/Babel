/*
 * File:          CompInt_Impl.java
 * Symbol:        sorting.CompInt-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.CompInt
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

// DO-NOT-DELETE splicer.begin(sorting.CompInt._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.CompInt._imports)

/**
 * Symbol "sorting.CompInt" (version 0.1)
 * 
 * Compare two Integer's.  By default, this will sort in increasing order.
 */
public class CompInt_Impl extends CompInt
{

  // DO-NOT-DELETE splicer.begin(sorting.CompInt._data)
  private boolean d_increasing;
  // DO-NOT-DELETE splicer.end(sorting.CompInt._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.CompInt._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.CompInt._load)

  }

  /**
   * User defined constructor
   */
  public CompInt_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.CompInt.CompInt)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.CompInt.CompInt)

  }

  /**
   * Back door constructor
   */
  public CompInt_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.CompInt._wrap)
    // Insert-Code-Here {sorting.CompInt._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.CompInt._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.CompInt._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.CompInt.finalize)
    // Insert-Code-Here {sorting.CompInt.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.CompInt.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * If increasing is true, this will cause the comparator to
   * report a normal definition of less than; otherwise, it will
   * reverse the normal ordering.
   */
  public void setSortIncreasing_Impl (
    /*in*/ boolean increasing ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing)
    // insert implementation here
    d_increasing = increasing; 
    return ;
    // DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing)

  }

  /**
   * This method is used to define an ordering of objects.  This method
   * will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
   */
  public int compare_Impl (
    /*in*/ sidl.BaseInterface i1,
    /*in*/ sidl.BaseInterface i2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.CompInt.compare)
    // insert implementation here
    int result = 0;
    sorting.Integer int1 = (sorting.Integer) sorting.Integer._cast((sidl.BaseInterface.Wrapper)i1);
    sorting.Integer int2 = (sorting.Integer) sorting.Integer._cast((sidl.BaseInterface.Wrapper)i2);
    if ((int1 != null) && (int2 != null)) {
        int val1 = int1.getValue();
        int val2 = int2.getValue();
        if (val1 < val2) result = -1;
        if (val1 > val2) result = 1;
        if (!d_increasing) {
          result = -result;
        }
      }
    return result;
    // DO-NOT-DELETE splicer.end(sorting.CompInt.compare)

  }


  // DO-NOT-DELETE splicer.begin(sorting.CompInt._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.CompInt._misc)

} // end class CompInt

