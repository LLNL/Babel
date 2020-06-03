/*
 * File:          D_Impl.java
 * Symbol:        Inherit.D-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.D
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.D._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.D._imports)

/**
 * Symbol "Inherit.D" (version 1.1)
 */
public class D_Impl extends D
{

  // DO-NOT-DELETE splicer.begin(Inherit.D._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.D._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.D._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.D._load)

  }

  /**
   * User defined constructor
   */
  public D_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.D.D)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.D.D)

  }

  /**
   * Back door constructor
   */
  public D_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.D._wrap)
    // Insert-Code-Here {Inherit.D._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.D._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.D._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.D._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.D.finalize)
    // Insert-Code-Here {Inherit.D.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.D.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.D.a)
    // insert implementation here
    return new java.lang.String("D.a");
    // DO-NOT-DELETE splicer.end(Inherit.D.a)

  }

  /**
   * Method:  d[]
   */
  public java.lang.String d_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.D.d)
    // insert implementation here
    return new java.lang.String("D.d");
    // DO-NOT-DELETE splicer.end(Inherit.D.d)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.D._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.D._misc)

} // end class D

