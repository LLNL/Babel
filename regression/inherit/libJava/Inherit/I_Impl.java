/*
 * File:          I_Impl.java
 * Symbol:        Inherit.I-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.I
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import Inherit.H;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.I._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.I._imports)

/**
 * Symbol "Inherit.I" (version 1.1)
 */
public class I_Impl extends I
{

  // DO-NOT-DELETE splicer.begin(Inherit.I._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.I._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.I._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.I._load)

  }

  /**
   * User defined constructor
   */
  public I_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.I.I)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.I.I)

  }

  /**
   * Back door constructor
   */
  public I_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.I._wrap)
    // Insert-Code-Here {Inherit.I._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.I._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.I._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.I._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.I.finalize)
    // Insert-Code-Here {Inherit.I.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.I.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.I.a)
    // insert implementation here
    return new java.lang.String("I.a");
    // DO-NOT-DELETE splicer.end(Inherit.I.a)

  }

  /**
   * Method:  h[]
   */
  public java.lang.String h_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.I.h)
    // insert implementation here
    return new java.lang.String("I.h");
    // DO-NOT-DELETE splicer.end(Inherit.I.h)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.I._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.I._misc)

} // end class I

