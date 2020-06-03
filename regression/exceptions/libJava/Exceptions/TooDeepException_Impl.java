/*
 * File:          TooDeepException_Impl.java
 * Symbol:        Exceptions.TooDeepException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Exceptions.TooDeepException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Exceptions;

import Exceptions.FibException;
import sidl.BaseClass;
import sidl.BaseException;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import sidl.SIDLException;
import sidl.io.Deserializer;
import sidl.io.Serializable;
import sidl.io.Serializer;

// DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._imports)

/**
 * Symbol "Exceptions.TooDeepException" (version 1.0)
 * 
 * This exception is thrown if the Fibonacci recursion is too deep.
 */
public class TooDeepException_Impl extends TooDeepException
{

  // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._load)

  }

  /**
   * User defined constructor
   */
  public TooDeepException_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException.TooDeepException)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException.TooDeepException)

  }

  /**
   * Back door constructor
   */
  public TooDeepException_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._wrap)
    // Insert-Code-Here {Exceptions.TooDeepException._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException.finalize)
    // Insert-Code-Here {Exceptions.TooDeepException.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._misc)

} // end class TooDeepException

