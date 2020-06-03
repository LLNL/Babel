/*
 * File:          Integer_Impl.java
 * Symbol:        sorting.Integer-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.Integer
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package sorting;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(sorting.Integer._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.Integer._imports)

/**
 * Symbol "sorting.Integer" (version 0.1)
 * 
 * An object to hold a simple integer.
 */
public class Integer_Impl extends Integer
{

  // DO-NOT-DELETE splicer.begin(sorting.Integer._data)
  private int d_num;
  // DO-NOT-DELETE splicer.end(sorting.Integer._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.Integer._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.Integer._load)

  }

  /**
   * User defined constructor
   */
  public Integer_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.Integer.Integer)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.Integer.Integer)

  }

  /**
   * Back door constructor
   */
  public Integer_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.Integer._wrap)
    // Insert-Code-Here {sorting.Integer._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.Integer._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Integer._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.Integer._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.Integer.finalize)
    // Insert-Code-Here {sorting.Integer.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.Integer.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  getValue[]
   */
  public int getValue_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Integer.getValue)
    // insert implementation here
    return d_num;
    // DO-NOT-DELETE splicer.end(sorting.Integer.getValue)

  }

  /**
   * Method:  setValue[]
   */
  public void setValue_Impl (
    /*in*/ int value ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.Integer.setValue)
    // insert implementation here
    d_num = value;
    return ;
    // DO-NOT-DELETE splicer.end(sorting.Integer.setValue)

  }


  // DO-NOT-DELETE splicer.begin(sorting.Integer._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.Integer._misc)

} // end class Integer

