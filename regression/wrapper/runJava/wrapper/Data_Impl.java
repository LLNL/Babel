/*
 * File:          Data_Impl.java
 * Symbol:        wrapper.Data-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for wrapper.Data
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package wrapper;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(wrapper.Data._imports)
// Insert-Code-Here {wrapper.Data._imports} (additional imports)
// DO-NOT-DELETE splicer.end(wrapper.Data._imports)

/**
 * Symbol "wrapper.Data" (version 1.0)
 */
public class Data_Impl extends Data
{

  // DO-NOT-DELETE splicer.begin(wrapper.Data._data)
  //We can pull the data right out of this Impl, if we can access it...
  public String d_string;
  public int d_int;
  public String d_ctorTest;

  // DO-NOT-DELETE splicer.end(wrapper.Data._data)


  static { 
  // DO-NOT-DELETE splicer.begin(wrapper.Data._load)
  // Insert-Code-Here {wrapper.Data._load} (class initialization)
  // DO-NOT-DELETE splicer.end(wrapper.Data._load)

  }

  /**
   * User defined constructor
   */
  public Data_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(wrapper.Data.Data)
    // DO-NOT-DELETE splicer.end(wrapper.Data.Data)

  }

  /**
   * Back door constructor
   */
  public Data_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(wrapper.Data._wrap)
    d_ctorTest = "ctor was run";
    // DO-NOT-DELETE splicer.end(wrapper.Data._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(wrapper.Data._dtor)
    // Insert-Code-Here {wrapper.Data._dtor} (destructor)
    // DO-NOT-DELETE splicer.end(wrapper.Data._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(wrapper.Data.finalize)
    // Insert-Code-Here {wrapper.Data.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(wrapper.Data.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  setString[]
   */
  public void setString_Impl (
    /*in*/ java.lang.String s ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(wrapper.Data.setString)
    d_string = s;
    return ;
    // DO-NOT-DELETE splicer.end(wrapper.Data.setString)

  }

  /**
   * Method:  setInt[]
   */
  public void setInt_Impl (
    /*in*/ int i ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(wrapper.Data.setInt)
    d_int = i;
    return ;
    // DO-NOT-DELETE splicer.end(wrapper.Data.setInt)

  }


  // DO-NOT-DELETE splicer.begin(wrapper.Data._misc)
  // Insert-Code-Here {wrapper.Data._misc} (miscellaneous)
  // DO-NOT-DELETE splicer.end(wrapper.Data._misc)

} // end class Data

