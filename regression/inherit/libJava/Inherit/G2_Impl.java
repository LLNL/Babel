/*
 * File:          G2_Impl.java
 * Symbol:        Inherit.G2-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.G2
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import Inherit.D;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.G2._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.G2._imports)

/**
 * Symbol "Inherit.G2" (version 1.1)
 */
public class G2_Impl extends G2
{

  // DO-NOT-DELETE splicer.begin(Inherit.G2._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.G2._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.G2._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.G2._load)

  }

  /**
   * User defined constructor
   */
  public G2_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.G2.G2)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.G2.G2)

  }

  /**
   * Back door constructor
   */
  public G2_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.G2._wrap)
    // Insert-Code-Here {Inherit.G2._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.G2._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.G2._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.G2._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.G2.finalize)
    // Insert-Code-Here {Inherit.G2.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.G2.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.G2.a)
    // insert implementation here
    return new java.lang.String("G2.a");
    // DO-NOT-DELETE splicer.end(Inherit.G2.a)

  }

  /**
   * Method:  d[]
   */
  public java.lang.String d_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.G2.d)
    // insert implementation here
    return new java.lang.String("G2.d");
    // DO-NOT-DELETE splicer.end(Inherit.G2.d)

  }

  /**
   * Method:  g[]
   */
  public java.lang.String g_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.G2.g)
    // insert implementation here
    return new java.lang.String("G2.g");
    // DO-NOT-DELETE splicer.end(Inherit.G2.g)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.G2._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.G2._misc)

} // end class G2

