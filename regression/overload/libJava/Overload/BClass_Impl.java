/*
 * File:          BClass_Impl.java
 * Symbol:        Overload.BClass-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.BClass
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Overload;

import Overload.AClass;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Overload.BClass._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Overload.BClass._imports)

/**
 * Symbol "Overload.BClass" (version 1.0)
 * 
 * This class is passed into the overloaded method as another example
 * of passing classes.
 */
public class BClass_Impl extends BClass
{

  // DO-NOT-DELETE splicer.begin(Overload.BClass._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Overload.BClass._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Overload.BClass._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Overload.BClass._load)

  }

  /**
   * User defined constructor
   */
  public BClass_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Overload.BClass.BClass)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Overload.BClass.BClass)

  }

  /**
   * Back door constructor
   */
  public BClass_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Overload.BClass._wrap)
    // Insert-Code-Here {Overload.BClass._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Overload.BClass._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.BClass._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Overload.BClass._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.BClass.finalize)
    // Insert-Code-Here {Overload.BClass.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Overload.BClass.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods: (none)

  // DO-NOT-DELETE splicer.begin(Overload.BClass._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Overload.BClass._misc)

} // end class BClass

