/*
 * File:          SimpleCounter_Impl.java
 * Symbol:        sorting.SimpleCounter-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for sorting.SimpleCounter
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package sorting;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import sorting.Counter;

// DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(sorting.SimpleCounter._imports)

/**
 * Symbol "sorting.SimpleCounter" (version 0.1)
 * 
 * Simple counter
 */
public class SimpleCounter_Impl extends SimpleCounter
{

  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._data)
  private int d_count = 0;
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._data)


  static { 
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._load)

  }

  /**
   * User defined constructor
   */
  public SimpleCounter_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.SimpleCounter)
    // add construction details here
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.SimpleCounter)

  }

  /**
   * Back door constructor
   */
  public SimpleCounter_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._wrap)
    // Insert-Code-Here {sorting.SimpleCounter._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.finalize)
    // Insert-Code-Here {sorting.SimpleCounter.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Set the count to zero.
   */
  public void reset_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.reset)
    d_count = 0;
    return ;
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.reset)

  }

  /**
   * Return the current count.
   */
  public int getCount_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.getCount)
    // insert implementation here
    return d_count;
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.getCount)

  }

  /**
   * Increment the count (i.e. add one).
   */
  public int inc_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.inc)
    // insert implementation here
    return ++d_count;
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter.inc)

  }


  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._misc)

} // end class SimpleCounter

