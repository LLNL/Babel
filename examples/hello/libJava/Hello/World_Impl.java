/*
 * File:          World_Impl.java
 * Symbol:        Hello.World-v1.2
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7399M trunk)
 * Description:   Server-side implementation for Hello.World
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Hello;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Hello.World._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Hello.World._imports)

/**
 * Symbol "Hello.World" (version 1.2)
 */
public class World_Impl extends World
{

  // DO-NOT-DELETE splicer.begin(Hello.World._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Hello.World._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Hello.World._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Hello.World._load)

  }

  /**
   * User defined constructor
   */
  public World_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Hello.World.World)
    // add construction details here
    // DO-NOT-DELETE splicer.end(Hello.World.World)

  }

  /**
   * Back door constructor
   */
  public World_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Hello.World._wrap)
    // Insert-Code-Here {Hello.World._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Hello.World._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Hello.World._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Hello.World._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Hello.World.finalize)
    // Insert-Code-Here {Hello.World.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Hello.World.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  getMsg[]
   */
  public java.lang.String getMsg_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Hello.World.getMsg)
    // insert implementation here
    return "Hello World";
    // DO-NOT-DELETE splicer.end(Hello.World.getMsg)

  }

  /**
   * Method:  foo[]
   */
  public int foo_Impl (
    /*in*/ int i,
    /*out*/ sidl.Integer.Holder o,
    /*inout*/ sidl.Integer.Holder io ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Hello.World.foo)
    // Insert-Code-Here {Hello.World.foo} (foo)
    return 0;
    // DO-NOT-DELETE splicer.end(Hello.World.foo)

  }


  // DO-NOT-DELETE splicer.begin(Hello.World._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Hello.World._misc)

} // end class World

