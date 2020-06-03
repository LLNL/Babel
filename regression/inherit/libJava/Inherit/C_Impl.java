/*
 * File:          C_Impl.java
 * Symbol:        Inherit.C-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.C
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.C._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.C._imports)

/**
 * Symbol "Inherit.C" (version 1.1)
 */
public class C_Impl extends C
{

  // DO-NOT-DELETE splicer.begin(Inherit.C._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.C._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.C._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.C._load)

  }

  /**
   * User defined constructor
   */
  public C_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.C.C)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.C.C)

  }

  /**
   * Back door constructor
   */
  public C_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.C._wrap)
    // Insert-Code-Here {Inherit.C._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.C._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.C._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.C._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.C.finalize)
    // Insert-Code-Here {Inherit.C.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.C.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  c[]
   */
  public java.lang.String c_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.C.c)
    // insert implementation here
    return new java.lang.String("C.c");
    // DO-NOT-DELETE splicer.end(Inherit.C.c)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.C._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.C._misc)

} // end class C

