/*
 * File:          K_Impl.java
 * Symbol:        Inherit.K-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.K
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import Inherit.A2;
import Inherit.H;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.K._imports)
// Insert-Code-Here {Inherit.K._imports} (additional imports)
// DO-NOT-DELETE splicer.end(Inherit.K._imports)

/**
 * Symbol "Inherit.K" (version 1.1)
 */
public class K_Impl extends K
{

  // DO-NOT-DELETE splicer.begin(Inherit.K._data)
  // Insert-Code-Here {Inherit.K._data} (private data)
  // DO-NOT-DELETE splicer.end(Inherit.K._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.K._load)
  // Insert-Code-Here {Inherit.K._load} (class initialization)
  // DO-NOT-DELETE splicer.end(Inherit.K._load)

  }

  /**
   * User defined constructor
   */
  public K_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.K.K)
    // Insert-Code-Here {Inherit.K.K} (backdoor)
    // DO-NOT-DELETE splicer.end(Inherit.K.K)

  }

  /**
   * Back door constructor
   */
  public K_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.K._wrap)
    // Insert-Code-Here {Inherit.K._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.K._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.K._dtor)
    // Insert-Code-Here {Inherit.K._dtor} (destructor)
    // DO-NOT-DELETE splicer.end(Inherit.K._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.K.finalize)
    // Insert-Code-Here {Inherit.K.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.K.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  a[2]
   */
  public java.lang.String a2_Impl (
    /*in*/ int i ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.K.a2)
    // Insert-Code-Here {Inherit.K.a2} (a)
    return new java.lang.String("K.a2");
    // DO-NOT-DELETE splicer.end(Inherit.K.a2)

  }

  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.K.a)
    // Insert-Code-Here {Inherit.K.a} (a)
    return new java.lang.String("K.a");
    // DO-NOT-DELETE splicer.end(Inherit.K.a)

  }

  /**
   * Method:  h[]
   */
  public java.lang.String h_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.K.h)
    // Insert-Code-Here {Inherit.K.h} (h)
    return new java.lang.String("K.h");
    // DO-NOT-DELETE splicer.end(Inherit.K.h)

  }

  /**
   * Method:  k[]
   */
  public java.lang.String k_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.K.k)
    // Insert-Code-Here {Inherit.K.k} (k)
    return new java.lang.String("K.k");
    // DO-NOT-DELETE splicer.end(Inherit.K.k)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.K._misc)
  // Insert-Code-Here {Inherit.K._misc} (miscellaneous)
  // DO-NOT-DELETE splicer.end(Inherit.K._misc)

} // end class K

