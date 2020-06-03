/*
 * File:          F_Impl.java
 * Symbol:        Inherit.F-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.F
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import Inherit.B;
import Inherit.C;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.F._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.F._imports)

/**
 * Symbol "Inherit.F" (version 1.1)
 */
public class F_Impl extends F
{

  // DO-NOT-DELETE splicer.begin(Inherit.F._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.F._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.F._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.F._load)

  }

  /**
   * User defined constructor
   */
  public F_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.F.F)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.F.F)

  }

  /**
   * Back door constructor
   */
  public F_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.F._wrap)
    // Insert-Code-Here {Inherit.F._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.F._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.F._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.F._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.F.finalize)
    // Insert-Code-Here {Inherit.F.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.F.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  f[]
   */
  public java.lang.String f_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F.f)
    // insert implementation here
    return new java.lang.String("F.f");
    // DO-NOT-DELETE splicer.end(Inherit.F.f)

  }

  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F.a)
    // insert implementation here
    return new java.lang.String("F.a");
    // DO-NOT-DELETE splicer.end(Inherit.F.a)

  }

  /**
   * Method:  b[]
   */
  public java.lang.String b_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F.b)
    // insert implementation here
    return new java.lang.String("F.b");
    // DO-NOT-DELETE splicer.end(Inherit.F.b)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.F._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.F._misc)

} // end class F

