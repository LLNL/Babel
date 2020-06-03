/*
 * File:          AnException_Impl.java
 * Symbol:        Overload.AnException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.AnException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Overload;

import sidl.BaseClass;
import sidl.BaseException;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import sidl.SIDLException;
import sidl.io.Deserializer;
import sidl.io.Serializable;
import sidl.io.Serializer;

// DO-NOT-DELETE splicer.begin(Overload.AnException._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Overload.AnException._imports)

/**
 * Symbol "Overload.AnException" (version 1.0)
 * 
 * This exception is passed into the overloaded method as an example
 * of passing classes.
 */
public class AnException_Impl extends AnException
{

  // DO-NOT-DELETE splicer.begin(Overload.AnException._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Overload.AnException._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Overload.AnException._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Overload.AnException._load)

  }

  /**
   * User defined constructor
   */
  public AnException_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Overload.AnException.AnException)
    // add construction details here
    setNote("AnException");
    // DO-NOT-DELETE splicer.end(Overload.AnException.AnException)

  }

  /**
   * Back door constructor
   */
  public AnException_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Overload.AnException._wrap)
    // Insert-Code-Here {Overload.AnException._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Overload.AnException._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.AnException._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Overload.AnException._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.AnException.finalize)
    // Insert-Code-Here {Overload.AnException.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Overload.AnException.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(Overload.AnException._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Overload.AnException._misc)

} // end class AnException

