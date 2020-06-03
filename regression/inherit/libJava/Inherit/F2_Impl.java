/*
 * File:          F2_Impl.java
 * Symbol:        Inherit.F2-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.F2
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

// DO-NOT-DELETE splicer.begin(Inherit.F2._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.F2._imports)

/**
 * Symbol "Inherit.F2" (version 1.1)
 */
public class F2_Impl extends F2
{

  // DO-NOT-DELETE splicer.begin(Inherit.F2._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.F2._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.F2._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.F2._load)

  }

  /**
   * User defined constructor
   */
  public F2_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.F2.F2)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.F2.F2)

  }

  /**
   * Back door constructor
   */
  public F2_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.F2._wrap)
    // Insert-Code-Here {Inherit.F2._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.F2._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.F2._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.F2._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.F2.finalize)
    // Insert-Code-Here {Inherit.F2.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.F2.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  c[]
   */
  public java.lang.String c_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F2.c)
    // insert implementation here
    return new java.lang.String("F2.c");
    // DO-NOT-DELETE splicer.end(Inherit.F2.c)

  }

  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F2.a)
    // insert implementation here
    return new java.lang.String("F2.a");
    // DO-NOT-DELETE splicer.end(Inherit.F2.a)

  }

  /**
   * Method:  b[]
   */
  public java.lang.String b_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F2.b)
    // insert implementation here
    return new java.lang.String("F2.b");
    // DO-NOT-DELETE splicer.end(Inherit.F2.b)

  }

  /**
   * Method:  f[]
   */
  public java.lang.String f_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.F2.f)
    // insert implementation here
    return new java.lang.String("F2.f");
    // DO-NOT-DELETE splicer.end(Inherit.F2.f)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.F2._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.F2._misc)

} // end class F2

