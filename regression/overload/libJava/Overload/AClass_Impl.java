/*
 * File:          AClass_Impl.java
 * Symbol:        Overload.AClass-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.AClass
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Overload;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Overload.AClass._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Overload.AClass._imports)

/**
 * Symbol "Overload.AClass" (version 1.0)
 * 
 * This class is passed into the overloaded method as an example
 * of passing classes.
 */
public class AClass_Impl extends AClass
{

  // DO-NOT-DELETE splicer.begin(Overload.AClass._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Overload.AClass._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Overload.AClass._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Overload.AClass._load)

  }

  /**
   * User defined constructor
   */
  public AClass_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Overload.AClass.AClass)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Overload.AClass.AClass)

  }

  /**
   * Back door constructor
   */
  public AClass_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Overload.AClass._wrap)
    // Insert-Code-Here {Overload.AClass._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Overload.AClass._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.AClass._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Overload.AClass._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Overload.AClass.finalize)
    // Insert-Code-Here {Overload.AClass.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Overload.AClass.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  getValue[]
   */
  public int getValue_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Overload.AClass.getValue)
    // insert implementation here
    return 2;
    // DO-NOT-DELETE splicer.end(Overload.AClass.getValue)

  }


  // DO-NOT-DELETE splicer.begin(Overload.AClass._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Overload.AClass._misc)

} // end class AClass

