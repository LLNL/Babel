/*
 * File:          FibException_Impl.java
 * Symbol:        Exceptions.FibException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Exceptions.FibException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Exceptions;

import sidl.BaseClass;
import sidl.BaseException;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import sidl.SIDLException;
import sidl.io.Deserializer;
import sidl.io.Serializable;
import sidl.io.Serializer;

// DO-NOT-DELETE splicer.begin(Exceptions.FibException._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Exceptions.FibException._imports)

/**
 * Symbol "Exceptions.FibException" (version 1.0)
 * 
 * This exception is a base class for the Fibonacci Exceptions that are
 * thrown if the value is too large or the recursion depth is too deep.
 */
public class FibException_Impl extends FibException
{

  // DO-NOT-DELETE splicer.begin(Exceptions.FibException._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Exceptions.FibException._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Exceptions.FibException._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Exceptions.FibException._load)

  }

  /**
   * User defined constructor
   */
  public FibException_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Exceptions.FibException.FibException)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Exceptions.FibException.FibException)

  }

  /**
   * Back door constructor
   */
  public FibException_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Exceptions.FibException._wrap)
    // Insert-Code-Here {Exceptions.FibException._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Exceptions.FibException._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.FibException._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Exceptions.FibException._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.FibException.finalize)
    // Insert-Code-Here {Exceptions.FibException.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Exceptions.FibException.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(Exceptions.FibException._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Exceptions.FibException._misc)

} // end class FibException

