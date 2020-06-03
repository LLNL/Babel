/*
 * File:          User_Impl.java
 * Symbol:        wrapper.User-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for wrapper.User
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package wrapper;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;
import wrapper.Data;

// DO-NOT-DELETE splicer.begin(wrapper.User._imports)
// Insert-Code-Here {wrapper.User._imports} (additional imports)
// DO-NOT-DELETE splicer.end(wrapper.User._imports)

/**
 * Symbol "wrapper.User" (version 1.0)
 */
public class User_Impl extends User
{

  // DO-NOT-DELETE splicer.begin(wrapper.User._data)
  // Insert-Code-Here {wrapper.User._data} (private data)
  // DO-NOT-DELETE splicer.end(wrapper.User._data)


  static { 
  // DO-NOT-DELETE splicer.begin(wrapper.User._load)
  // Insert-Code-Here {wrapper.User._load} (class initialization)
  // DO-NOT-DELETE splicer.end(wrapper.User._load)

  }

  /**
   * User defined constructor
   */
  public User_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(wrapper.User.User)
    // Insert-Code-Here {wrapper.User.User} (backdoor)
    // DO-NOT-DELETE splicer.end(wrapper.User.User)

  }

  /**
   * Back door constructor
   */
  public User_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(wrapper.User._wrap)
    // Insert-Code-Here {wrapper.User._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(wrapper.User._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(wrapper.User._dtor)
    // Insert-Code-Here {wrapper.User._dtor} (destructor)
    // DO-NOT-DELETE splicer.end(wrapper.User._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(wrapper.User.finalize)
    // Insert-Code-Here {wrapper.User.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(wrapper.User.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  accept[]
   */
  public void accept_Impl (
    /*in*/ wrapper.Data data ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(wrapper.User.accept)
    data.setString("Hello World!");
    data.setInt(3);
    return ;
    // DO-NOT-DELETE splicer.end(wrapper.User.accept)

  }


  // DO-NOT-DELETE splicer.begin(wrapper.User._misc)
  // Insert-Code-Here {wrapper.User._misc} (miscellaneous)
  // DO-NOT-DELETE splicer.end(wrapper.User._misc)

} // end class User

