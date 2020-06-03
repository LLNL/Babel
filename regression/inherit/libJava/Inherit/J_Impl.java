/*
 * File:          J_Impl.java
 * Symbol:        Inherit.J-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Inherit.J
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Inherit;

import Inherit.A;
import Inherit.B;
import Inherit.C;
import Inherit.E2;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Inherit.J._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Inherit.J._imports)

/**
 * Symbol "Inherit.J" (version 1.1)
 */
public class J_Impl extends J
{

  // DO-NOT-DELETE splicer.begin(Inherit.J._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Inherit.J._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Inherit.J._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Inherit.J._load)

  }

  /**
   * User defined constructor
   */
  public J_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Inherit.J.J)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Inherit.J.J)

  }

  /**
   * Back door constructor
   */
  public J_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Inherit.J._wrap)
    // Insert-Code-Here {Inherit.J._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Inherit.J._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.J._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Inherit.J._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Inherit.J.finalize)
    // Insert-Code-Here {Inherit.J.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Inherit.J.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  j[]
   */
  public java.lang.String j_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.J.j)
    // insert implementation here
    return "J.j";
    // DO-NOT-DELETE splicer.end(Inherit.J.j)

  }

  /**
   * Method:  e[]
   */
  public java.lang.String e_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.J.e)
    // insert implementation here
    return "J." + super_e();
    // DO-NOT-DELETE splicer.end(Inherit.J.e)

  }

  /**
   * Method:  c[]
   */
  public java.lang.String c_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.J.c)
    // insert implementation here
    return "J." + super_c();
    // DO-NOT-DELETE splicer.end(Inherit.J.c)

  }

  /**
   * Method:  a[]
   */
  public java.lang.String a_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.J.a)
    // insert implementation here
    return "J.a";
    // DO-NOT-DELETE splicer.end(Inherit.J.a)

  }

  /**
   * Method:  b[]
   */
  public java.lang.String b_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Inherit.J.b)
    // insert implementation here
    return "J.b";
    // DO-NOT-DELETE splicer.end(Inherit.J.b)

  }


  // DO-NOT-DELETE splicer.begin(Inherit.J._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Inherit.J._misc)

} // end class J

