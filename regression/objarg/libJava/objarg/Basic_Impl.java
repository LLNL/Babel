/*
 * File:          Basic_Impl.java
 * Symbol:        objarg.Basic-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.Basic
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package objarg;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(objarg.Basic._imports)
// Insert-Code-Here {objarg.Basic._imports} (additional imports)
// DO-NOT-DELETE splicer.end(objarg.Basic._imports)

/**
 * Symbol "objarg.Basic" (version 0.5)
 */
public class Basic_Impl extends Basic
{

  // DO-NOT-DELETE splicer.begin(objarg.Basic._data)
  // Insert-Code-Here {objarg.Basic._data} (private data)
  // DO-NOT-DELETE splicer.end(objarg.Basic._data)


  static { 
  // DO-NOT-DELETE splicer.begin(objarg.Basic._load)
  // Insert-Code-Here {objarg.Basic._load} (class initialization)
  // DO-NOT-DELETE splicer.end(objarg.Basic._load)

  }

  /**
   * User defined constructor
   */
  public Basic_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(objarg.Basic.Basic)
    // Insert-Code-Here {objarg.Basic.Basic} (constructor)
    // DO-NOT-DELETE splicer.end(objarg.Basic.Basic)

  }

  /**
   * Back door constructor
   */
  public Basic_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(objarg.Basic._wrap)
    // Insert-Code-Here {objarg.Basic._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(objarg.Basic._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(objarg.Basic._dtor)
    // Insert-Code-Here {objarg.Basic._dtor} (destructor)
    // DO-NOT-DELETE splicer.end(objarg.Basic._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(objarg.Basic.finalize)
    // Insert-Code-Here {objarg.Basic.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(objarg.Basic.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Return inNotNull == (o != NULL).
   */
  public boolean passIn_Impl (
    /*in*/ sidl.BaseClass o,
    /*in*/ boolean inNotNull ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Basic.passIn)
    return inNotNull == (o != null);
    // DO-NOT-DELETE splicer.end(objarg.Basic.passIn)

  }

  /**
   * Return inNotNull == (o != NULL).  If outNotNull, the outgoing
   * value of o should not be NULL; otherwise, it will be NULL.
   * If outNotNull is true, there are two cases, it retSame is true
   * the incoming value of o will be returned; otherwise, a new
   * object will be allocated and returned.
   */
  public boolean passInOut_Impl (
    /*inout*/ sidl.BaseClass.Holder o,
    /*in*/ boolean inNotNull,
    /*in*/ boolean outNotNull,
    /*in*/ boolean retSame ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Basic.passInOut)
    boolean retval = (inNotNull == (o.get() != null));
    if (outNotNull) {
      if (!retSame || (o.get() == null)) {
        o.set(new sidl.BaseClass());
      }
    }
    else {
      o.set(null);
    }
    return retval;
    // DO-NOT-DELETE splicer.end(objarg.Basic.passInOut)

  }

  /**
   * If passOutNull is true, a NULL value of o will be returned; otherwise,
   * a newly allocated object will be returned.
   */
  public void passOut_Impl (
    /*out*/ sidl.BaseClass.Holder o,
    /*in*/ boolean passOutNull ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Basic.passOut)
    if (passOutNull) {
      o.set(null);
    }
    else {
      o.set(new sidl.BaseClass());
    }
    // DO-NOT-DELETE splicer.end(objarg.Basic.passOut)

  }

  /**
   * Return a NULL or non-NULL object depending on the value of retNull.
   */
  public sidl.BaseClass retObject_Impl (
    /*in*/ boolean retNull ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(objarg.Basic.retObject)
    return retNull ? null : new sidl.BaseClass();
    // DO-NOT-DELETE splicer.end(objarg.Basic.retObject)

  }


  // DO-NOT-DELETE splicer.begin(objarg.Basic._misc)
  // Insert-Code-Here {objarg.Basic._misc} (miscellaneous)
  // DO-NOT-DELETE splicer.end(objarg.Basic._misc)

} // end class Basic

