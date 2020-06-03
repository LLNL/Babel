/*
 * File:          G_Impl.java
 * Symbol:        Inherit.G-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.G
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

// DO-NOT-DELETE splicer.begin(Inherit.G._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.G._imports)

/**
 * Symbol "Inherit.G" (version 1.1)
 */
public class G_Impl extends G
{

  // DO-NOT-DELETE splicer.begin(Inherit.G._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.G._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.G._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.G._load)

  }

  /**
   * User defined constructor
   */
  public G_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.G.G)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.G.G)

  }

  /**
   * Back door constructor
   */
  public G_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.G._wrap)
    // Insert-Code-Here {Inherit.G._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.G._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.G._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.G._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.G.finalize)
    // Insert-Code-Here {Inherit.G.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.G.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  g[]
   */
  public java.lang.String g_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.G.g)
    // insert implementation here
    return new java.lang.String("G.g");
    // DO-NOT-DELETE splicer.end(Inherit.G.g)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.G._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.G._misc)

} // end class G

