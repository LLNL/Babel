/*
 * File:          E_Impl.java
 * Symbol:        Inherit.E-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.E
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

// DO-NOT-DELETE splicer.begin(Inherit.E._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.E._imports)

/**
 * Symbol "Inherit.E" (version 1.1)
 */
public class E_Impl extends E
{

  // DO-NOT-DELETE splicer.begin(Inherit.E._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.E._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.E._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.E._load)

  }

  /**
   * User defined constructor
   */
  public E_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.E.E)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.E.E)

  }

  /**
   * Back door constructor
   */
  public E_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.E._wrap)
    // Insert-Code-Here {Inherit.E._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.E._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.E._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.E._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.E.finalize)
    // Insert-Code-Here {Inherit.E.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.E.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  e[]
   */
  public java.lang.String e_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.E.e)
    // insert implementation here
    return new java.lang.String("E.e");
    // DO-NOT-DELETE splicer.end(Inherit.E.e)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.E._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.E._misc)

} // end class E

