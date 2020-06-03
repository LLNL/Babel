/*
 * File:          E2_Impl.java
 * Symbol:        Inherit.E2-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.E2
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.C;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.E2._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.E2._imports)

/**
 * Symbol "Inherit.E2" (version 1.1)
 */
public class E2_Impl extends E2
{

  // DO-NOT-DELETE splicer.begin(Inherit.E2._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.E2._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.E2._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.E2._load)

  }

  /**
   * User defined constructor
   */
  public E2_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.E2.E2)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.E2.E2)

  }

  /**
   * Back door constructor
   */
  public E2_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.E2._wrap)
    // Insert-Code-Here {Inherit.E2._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.E2._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.E2._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.E2._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.E2.finalize)
    // Insert-Code-Here {Inherit.E2.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.E2.finalize)

  }

  // user defined static methods:
  /**
   * Method:  m[]
   */
  public static java.lang.String m_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.E2.m)
    return "E2.m";
    // DO-NOT-DELETE splicer.end(Inherit.E2.m)

  }


  // user defined non-static methods:
  /**
   * Method:  c[]
   */
  public java.lang.String c_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.E2.c)
    // insert implementation here
    return new java.lang.String("E2.c");
    // DO-NOT-DELETE splicer.end(Inherit.E2.c)

  }

  /**
   * Method:  e[]
   */
  public java.lang.String e_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.E2.e)
    // insert implementation here
    return new java.lang.String("E2.e");
    // DO-NOT-DELETE splicer.end(Inherit.E2.e)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.E2._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.E2._misc)

} // end class E2

