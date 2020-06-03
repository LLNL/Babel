/*
 * File:          L_Impl.java
 * Symbol:        Inherit.L-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.L
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import Inherit.A2;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.L._imports)
// Insert-Code-Here {Inherit.L._imports} (additional imports)
// DO-NOT-DELETE splicer.end(Inherit.L._imports)

/**
 * Symbol "Inherit.L" (version 1.1)
 */
public class L_Impl extends L
{

  // DO-NOT-DELETE splicer.begin(Inherit.L._data)
  // Insert-Code-Here {Inherit.L._data} (private data)
  // DO-NOT-DELETE splicer.end(Inherit.L._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.L._load)
  // Insert-Code-Here {Inherit.L._load} (class initialization)
  // DO-NOT-DELETE splicer.end(Inherit.L._load)

  }

  /**
   * User defined constructor
   */
  public L_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.L.L)
    // Insert-Code-Here {Inherit.L.L} (backdoor)
    // DO-NOT-DELETE splicer.end(Inherit.L.L)

  }

  /**
   * Back door constructor
   */
  public L_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.L._wrap)
    // Insert-Code-Here {Inherit.L._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.L._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.L._dtor)
    // Insert-Code-Here {Inherit.L._dtor} (destructor)
    // DO-NOT-DELETE splicer.end(Inherit.L._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.L.finalize)
    // Insert-Code-Here {Inherit.L.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.L.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  a[a]
   */
  public java.lang.String aa_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.L.aa)
    // Insert-Code-Here {Inherit.L.aa} (a)
    return new java.lang.String("L.a");
    // DO-NOT-DELETE splicer.end(Inherit.L.aa)

  }

  /**
   * Method:  a[2]
   */
  public java.lang.String a2_Impl (
    /*in*/ int i ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.L.a2)
    // Insert-Code-Here {Inherit.L.a2} (a)
    return new java.lang.String("L.a2");
    // DO-NOT-DELETE splicer.end(Inherit.L.a2)

  }

  /**
   * Method:  l[]
   */
  public java.lang.String l_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.L.l)
    // Insert-Code-Here {Inherit.L.l} (l)
    return new java.lang.String("L.l");
    // DO-NOT-DELETE splicer.end(Inherit.L.l)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.L._misc)
  // Insert-Code-Here {Inherit.L._misc} (miscellaneous)
  // DO-NOT-DELETE splicer.end(Inherit.L._misc)

} // end class L

