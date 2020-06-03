/*
 * File:          NegativeValueException_Impl.java
 * Symbol:        Exceptions.NegativeValueException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Exceptions.NegativeValueException
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

// DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._imports)

/**
 * Symbol "Exceptions.NegativeValueException" (version 1.0)
 * 
 * This exception is thrown if the value for which the Fibonacci number
 * is requested is negative.
 */
public class NegativeValueException_Impl extends NegativeValueException
{

  // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._load)

  }

  /**
   * User defined constructor
   */
  public NegativeValueException_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException.NegativeValueException)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException.NegativeValueException)

  }

  /**
   * Back door constructor
   */
  public NegativeValueException_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._wrap)
    // Insert-Code-Here {Exceptions.NegativeValueException._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException.finalize)
    // Insert-Code-Here {Exceptions.NegativeValueException.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._misc)

} // end class NegativeValueException

