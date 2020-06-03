/*
 * File:          Cstring_Impl.java
 * Symbol:        Strings.Cstring-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Strings.Cstring
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Strings;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Strings.Cstring._imports)
// Put additional imports here...
// DO-NOT-DELETE splicer.end(Strings.Cstring._imports)

/**
 * Symbol "Strings.Cstring" (version 1.1)
 * 
 * Class to allow testing of string passing using every possible mode.
 */
public class Cstring_Impl extends Cstring
{

  // DO-NOT-DELETE splicer.begin(Strings.Cstring._data)
  // Put additional private data here...
  // DO-NOT-DELETE splicer.end(Strings.Cstring._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Strings.Cstring._load)
  // Put load function implementation here...
  // DO-NOT-DELETE splicer.end(Strings.Cstring._load)

  }

  /**
   * User defined constructor
   */
  public Cstring_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.Cstring)
    // DO-NOT-DELETE splicer.end(Strings.Cstring.Cstring)

  }

  /**
   * Back door constructor
   */
  public Cstring_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Strings.Cstring._wrap)
    // Insert-Code-Here {Strings.Cstring._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Strings.Cstring._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(Strings.Cstring._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.finalize)
    // Insert-Code-Here {Strings.Cstring.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Strings.Cstring.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * If <code>nonNull</code> is <code>true</code>, this will
   * return "Three"; otherwise, it will return a NULL or empty string.
   */
  public java.lang.String returnback_Impl (
    /*in*/ boolean nonNull ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback)
    // insert implementation here
      if(nonNull)
	  return "Three";
      else
	  return null;
    // DO-NOT-DELETE splicer.end(Strings.Cstring.returnback)

  }

  /**
   * This will return <code>true</code> iff <code>c</code> equals "Three".
   */
  public boolean passin_Impl (
    /*in*/ java.lang.String c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.passin)
    // insert implementation here
      if(c == null)
	  return false;
      else
	  return (c.equals("Three"));
    // DO-NOT-DELETE splicer.end(Strings.Cstring.passin)

  }

  /**
   * If <code>nonNull</code> is <code>true</code>, this will return
   * "Three" in <code>c</code>; otherwise, it will return a null or
   * empty string. The return value is <code>false</code> iff 
   * the outgoing value of <code>c</code> is <code>null</code>.
   */
  public boolean passout_Impl (
    /*in*/ boolean nonNull,
    /*out*/ sidl.String.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.passout)
    // insert implementation here
      if(nonNull) {
	  //	  c.set("Three"); 
	  c.set(returnback(true));
      }
      else {
	  c.set("");
      }
      return nonNull;
    // DO-NOT-DELETE splicer.end(Strings.Cstring.passout)

  }

  /**
   * Method:  passinout[]
   */
  public boolean passinout_Impl (
    /*inout*/ sidl.String.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout)
    // insert implementation here
      if (c == null)
	  return false;
      if(c.get() == null)
	  return false;
      if(c.get().length() == 0)
	  return false;
      char h = c.get().charAt(0);
      if ( h >= 'a' && h <= 'z' ) {
	  c.set("Threes");
      } else if ( h >= 'A' && h <= 'Z' ) {
	  c.set("threes");
      }
      return true;
    // DO-NOT-DELETE splicer.end(Strings.Cstring.passinout)

  }

  /**
   * Method:  passeverywhere[]
   */
  public java.lang.String passeverywhere_Impl (
    /*in*/ java.lang.String c1,
    /*out*/ sidl.String.Holder c2,
    /*inout*/ sidl.String.Holder c3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere)
    // insert implementation here
      c2.set("");
      if(c3.get().length() == 0)
	 return "";
      char h = c3.get().charAt(0);
      if ( h >= 'a' && h <= 'z' ) {
	  c3.set("Three");
      } else if ( h >= 'A' && h <= 'Z' ) {
	  c3.set("three");
      }
      c2.set("Three");
      if(c1.equals("Three"))
	  return "Three";
      else
	  return "";
    // DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere)

  }

  /**
   *  return true iff s1 == s2 and c1 == c2 
   */
  public boolean mixedarguments_Impl (
    /*in*/ java.lang.String s1,
    /*in*/ char c1,
    /*in*/ java.lang.String s2,
    /*in*/ char c2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments)
    // insert implementation here
      return (s1.equals(s2) && c1 == c2);
    // DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments)

  }


  // DO-NOT-DELETE splicer.begin(Strings.Cstring._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(Strings.Cstring._misc)

} // end class Cstring

