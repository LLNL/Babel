/*
 * File:          StructTest_Impl.java
 * Symbol:        s.StructTest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for s.StructTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package s;

import s.Combined;
import s.Empty;
import s.Hard;
import s.Rarrays;
import s.Simple;
import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(s.StructTest._imports)
// insert code here (additional imports)
// DO-NOT-DELETE splicer.end(s.StructTest._imports)

/**
 * Symbol "s.StructTest" (version 1.0)
 */
public class StructTest_Impl extends StructTest
{

  // DO-NOT-DELETE splicer.begin(s.StructTest._data)
  // insert code here (private data)
  static final double eps = 1.E-6;

  private static void initSimple(s.Simple _s)
  {
    _s.d_bool = true;
    _s.d_char = '3';
    _s.d_dcomplex = new sidl.DoubleComplex(3.14, 3.14);
    _s.d_fcomplex = new sidl.FloatComplex(3.1F, 3.1F);
    _s.d_double = 3.14;
    _s.d_float = 3.1F;
    _s.d_int = 3;
    _s.d_long = 3;
    _s.d_opaque = 0;
    _s.d_enum = s.Color.blue;
  }

  private static void initHard(s.Hard h) {
    java.lang.String[] _string_init = { "Three" };
    h.d_string = new sidl.String.Array1(_string_init);
    h.d_object = new sidl.BaseClass();
    h.d_interface = h.d_object;
    double [] _double_init = {1.0D, 2.0D, 3.0D};
    h.d_array = new sidl.Double.Array1(_double_init);
    h.d_objectArray = new sidl.BaseClass.Array1(3, true);
    h.d_objectArray.set(0, new sidl.BaseClass());
    h.d_objectArray.set(1, new sidl.BaseClass());
    h.d_objectArray.set(2, new sidl.BaseClass());
  }

  private static void initCombined(s.Combined c)
  {
    c.d_simple = new s.Simple();
    initSimple(c.d_simple);
    c.d_hard = new s.Hard();
    initHard(c.d_hard);
  }

  private static void invertSimple(s.Simple _s) {
    _s.d_bool = _s.d_bool ? false : true;
    _s.d_char = Character.isLowerCase(_s.d_char) ?
      Character.toUpperCase(_s.d_char) :
      Character.toLowerCase(_s.d_char);
    _s.d_dcomplex.set(_s.d_dcomplex.real(), -_s.d_dcomplex.imag());
    _s.d_fcomplex.set(_s.d_fcomplex.real(), -_s.d_fcomplex.imag());
    _s.d_double = -_s.d_double;
    _s.d_float = -_s.d_float;
    _s.d_int = - _s.d_int;
    _s.d_long = - _s.d_long;
    _s.d_enum = s.Color.red;
  }

  private static void invertHard(s.Hard h) {
    if(h.d_string != null && h.d_string.length() == 1)
      h.d_string.set(0, "three");

    sidl.BaseClass bc = h.d_object;
    sidl.BaseInterface bi = h.d_interface;
    if(bc != null && bi != null) {
      if(bc.isSame(bi))
        h.d_interface = new sidl.BaseClass();
      else
        h.d_interface = bc;
    }

    if(h.d_array != null && h.d_array.length() == 3) {
      double tmp = h.d_array.get(2);
      h.d_array.set(2, h.d_array.get(0));
      h.d_array.set(0, tmp);
    }

    if(h.d_objectArray != null && h.d_objectArray.length() == 3) {
      if(h.d_objectArray.get(1) != null)
        h.d_objectArray.set(1, null);
      else
        h.d_objectArray.set(1, new sidl.BaseClass());
    }
  }

  private static void invertCombined(s.Combined ref) {
    invertSimple(ref.d_simple);
    invertHard(ref.d_hard);
  }
  
  private static boolean checkSimple(s.Simple _s) {
    return ((_s.d_bool == true) &&
            (_s.d_char == '3') &&
            (Math.abs(_s.d_dcomplex.real() - 3.14) < eps) &&
            (Math.abs(_s.d_dcomplex.imag() - 3.14) < eps) &&
            (Math.abs(_s.d_double - 3.14) < eps) &&
            (Math.abs(_s.d_fcomplex.real() - 3.1F) < eps) &&
            (Math.abs(_s.d_fcomplex.imag() - 3.1F) < eps) &&
            (Math.abs(_s.d_float - 3.1) < eps) &&
            (_s.d_int == 3) &&
            (_s.d_long == 3) &&
            (_s.d_opaque == 0) &&
            (_s.d_enum == s.Color.blue));
  }

  private static boolean checkSimpleInv(s.Simple _s)
  {
    return ((_s.d_bool == false &&
             (_s.d_char == '3') &&
             (Math.abs(_s.d_dcomplex.real() - 3.14) < eps) &&
             (Math.abs(_s.d_dcomplex.imag() + 3.14) < eps) &&
             (Math.abs(_s.d_double + 3.14) < eps) &&
             (Math.abs(_s.d_fcomplex.real() - 3.1F) < eps) &&
             (Math.abs(_s.d_fcomplex.imag() + 3.1F) < eps) &&
             (Math.abs(_s.d_float + 3.1F) < eps) &&
             (_s.d_int == -3) &&
             (_s.d_long == -3) &&
             (_s.d_opaque == 0) &&
             (_s.d_enum == s.Color.red)));
  }

  private static boolean checkHard(s.Hard h) {
    boolean result = h.d_string != null;
    
    if(result) {
      result &= h.d_string.length() == 1;
      result &= h.d_string.get(0).compareTo("Three") == 0;
    }
    
    result &= h.d_object != null;
    result &= h.d_interface != null;

    if(result)
      result &= h.d_object.isSame(h.d_interface);

    result &= h.d_array != null;
    if (result) {
      result &= h.d_array.length() == 3;
      result &= h.d_array.get(0) == 1.0;
      result &= h.d_array.get(1) == 2.0;
      result &= h.d_array.get(2) == 3.0;
    }
    
    result &= h.d_objectArray != null;
    if (result) {
      result &= h.d_objectArray.length() == 3;
      result &= h.d_objectArray.get(0).isType("sidl.BaseClass");
      result &= h.d_objectArray.get(1).isType("sidl.BaseClass");
      result &= h.d_objectArray.get(2).isType("sidl.BaseClass");
    }
    return result;
  }

  private static boolean checkHardInv(s.Hard h) {
    boolean result = h.d_string != null;
    
    if(result) {
      result &= h.d_string.length() == 1;
      result &= h.d_string.get(0).compareTo("three") == 0;
    }
    
    result &= h.d_object != null;
    result &= h.d_interface != null;

    if(result)
      result &= ! h.d_object.isSame(h.d_interface);

    result &= h.d_array != null;
    if (result) {
      result &= h.d_array.length() == 3;
      result &= h.d_array.get(0) == 3.0;
      result &= h.d_array.get(1) == 2.0;
      result &= h.d_array.get(2) == 1.0;
    }
    
    result &= h.d_objectArray != null;
    if (result) {
      result &= h.d_objectArray.length() == 3;
      result &= h.d_objectArray.get(0) != null &&
        h.d_objectArray.get(0).isType("sidl.BaseClass");
      result &= h.d_objectArray.get(1) == null;
      result &= h.d_objectArray.get(2).isType("sidl.BaseClass");
    }
    return result;
  }

  private static boolean checkCombined(s.Combined c) {
    return checkSimple(c.d_simple) && checkHard(c.d_hard);
  }

  private static boolean checkCombinedInv(s.Combined c) {
    return checkSimpleInv(c.d_simple) && checkHardInv(c.d_hard);
  }

  private static void initRarrays(s.Rarrays h) {
    h.d_int = 3;
    h.d_rarrayRaw = new sidl.Double.Array1(h.d_int, false);
    h.d_rarrayFix = new sidl.Double.Array1(h.d_int, false);
    for(int i=0; i < h.d_int; ++i) {
      h.d_rarrayRaw.set(i, (double)(i+1));
      h.d_rarrayFix.set(i, (double)((i+1)*5));
    }
  }

  private static boolean checkRarrays(s.Rarrays r) {
    boolean result = r.d_rarrayRaw != null && r.d_rarrayFix != null;
    if (result) {
      result = result && (r.d_rarrayRaw.get(0) == 1.0);
      result = result && (r.d_rarrayRaw.get(1) == 2.0);
      result = result && (r.d_rarrayRaw.get(2) == 3.0);
      result = result && (r.d_rarrayFix.get(0) == 5.0);
      result = result && (r.d_rarrayFix.get(1) == 10.0);
      result = result && (r.d_rarrayFix.get(2) == 15.0);
    }
    return result;
  }

  private static boolean checkRarraysInv(s.Rarrays r) {
    boolean result = r.d_rarrayRaw != null && r.d_rarrayFix != null;
    if (result) {
      result = result && (r.d_rarrayRaw.get(0) == 3.0);
      result = result && (r.d_rarrayRaw.get(1) == 2.0);
      result = result && (r.d_rarrayRaw.get(2) == 1.0);
      result = result && (r.d_rarrayFix.get(0) == 15.0);
      result = result && (r.d_rarrayFix.get(1) == 10.0);
      result = result && (r.d_rarrayFix.get(2) == 5.0);
    }
    return result;
  }

  private static void invertRarrays(s.Rarrays h) {
    if(h.d_rarrayRaw != null && h.d_rarrayRaw.length() == 3) {
      double tmp = h.d_rarrayRaw.get(2);
      h.d_rarrayRaw.set(2, h.d_rarrayRaw.get(0));
      h.d_rarrayRaw.set(0, tmp);
    }

    if(h.d_rarrayFix != null && h.d_rarrayFix.length() == 3) {
      double tmp = h.d_rarrayFix.get(2);
      h.d_rarrayFix.set(2, h.d_rarrayFix.get(0));
      h.d_rarrayFix.set(0, tmp);
    }
  }

  // DO-NOT-DELETE splicer.end(s.StructTest._data)


  static { 
  // DO-NOT-DELETE splicer.begin(s.StructTest._load)
  // insert code here (class initialization)
  // DO-NOT-DELETE splicer.end(s.StructTest._load)

  }

  /**
   * User defined constructor
   */
  public StructTest_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(s.StructTest.StructTest)
    // insert code here (constructor)
    // DO-NOT-DELETE splicer.end(s.StructTest.StructTest)

  }

  /**
   * Back door constructor
   */
  public StructTest_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(s.StructTest._wrap)
    // insert code here (_wrap)
    // DO-NOT-DELETE splicer.end(s.StructTest._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(s.StructTest._dtor)
    // insert code here (destructor)
    // DO-NOT-DELETE splicer.end(s.StructTest._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(s.StructTest.finalize)
    // insert code here (finalize)
    // DO-NOT-DELETE splicer.end(s.StructTest.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  returnEmpty[]
   */
  public s.Empty returnEmpty_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty)
    // insert code here (returnEmpty)
    return new s.Empty();
    // DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty)

  }

  /**
   * Method:  passinEmpty[]
   */
  public boolean passinEmpty_Impl (
    /*in*/ s.Empty s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty)
    // insert code here (passinEmpty)
    return true;
    // DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty)

  }

  /**
   * Method:  passoutEmpty[]
   */
  public boolean passoutEmpty_Impl (
    /*out*/ s.Empty.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty)
    // insert code here (passoutEmpty)
    if(s1 == null)
      return false;

    s.Empty ret = new s.Empty();
    s1.set(ret);
    return true;
    // DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty)

  }

  /**
   * Method:  passinoutEmpty[]
   */
  public boolean passinoutEmpty_Impl (
    /*inout*/ s.Empty.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty)
    // insert code here (passinoutEmpty)    
    if(s1 == null)
      return false;
    else 
      return true;
    // DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty)

  }

  /**
   * Method:  passeverywhereEmpty[]
   */
  public s.Empty passeverywhereEmpty_Impl (
    /*in*/ s.Empty s1,
    /*out*/ s.Empty.Holder s2,
    /*inout*/ s.Empty.Holder s3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty)
    // insert code here (passeverywhereEmpty)

    if(s2 != null) {
      s.Empty _s2 = new s.Empty();
      s2.set(_s2);
    }
    
    return new s.Empty();
    // DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty)

  }

  /**
   * Method:  returnSimple[]
   */
  public s.Simple returnSimple_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple)
    // insert code here (returnSimple)
    s.Simple ret = new s.Simple();
    initSimple(ret);
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.returnSimple)

  }

  /**
   * Method:  passinSimple[]
   */
  public boolean passinSimple_Impl (
    /*in*/ s.Simple s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple)
    // insert code here (passinSimple)
    return checkSimple(s1);
    // DO-NOT-DELETE splicer.end(s.StructTest.passinSimple)

  }

  /**
   * Method:  passoutSimple[]
   */
  public boolean passoutSimple_Impl (
    /*out*/ s.Simple.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple)
    // insert code here (passoutSimple)
    if(s1 == null)
      return false;
    
    s.Simple ret = new s.Simple();
    initSimple(ret);
    s1.set(ret);
    return true;
    // DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple)

  }

  /**
   * Method:  passinoutSimple[]
   */
  public boolean passinoutSimple_Impl (
    /*inout*/ s.Simple.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple)
    // insert code here (passinoutSimple)
    if(s1 == null)
      return false;

    boolean ret = checkSimple(s1.get());
    invertSimple(s1.get());
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple)

  }

  /**
   * Method:  passeverywhereSimple[]
   */
  public s.Simple passeverywhereSimple_Impl (
    /*in*/ s.Simple s1,
    /*out*/ s.Simple.Holder s2,
    /*inout*/ s.Simple.Holder s3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple)
    // insert code here (passeverywhereSimple)
    if(s2 != null) {
      s.Simple _s2 = new s.Simple();
      initSimple(_s2);
      s2.set(_s2);
    }

    if(s3 != null && checkSimple(s1) && checkSimple(s3.get()))
      invertSimple(s3.get());
    
    s.Simple ret = new s.Simple();
    initSimple(ret);
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple)

  }

  /**
   * Method:  returnHard[]
   */
  public s.Hard returnHard_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.returnHard)
    // insert code here (returnHard)
    s.Hard ret = new s.Hard();
    initHard(ret);
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.returnHard)

  }

  /**
   * Method:  passinHard[]
   */
  public boolean passinHard_Impl (
    /*in*/ s.Hard s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinHard)
    // insert code here (passinHard)
    return checkHard(s1);
    // DO-NOT-DELETE splicer.end(s.StructTest.passinHard)

  }

  /**
   * Method:  passoutHard[]
   */
  public boolean passoutHard_Impl (
    /*out*/ s.Hard.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard)
    // insert code here (passoutHard)
    if(s1 == null)
      return false;
    
    s.Hard ret = new s.Hard();
    initHard(ret);
    s1.set(ret);
    return true;
    // DO-NOT-DELETE splicer.end(s.StructTest.passoutHard)

  }

  /**
   * Method:  passinoutHard[]
   */
  public boolean passinoutHard_Impl (
    /*inout*/ s.Hard.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard)
    // insert code here (passinoutHard)
    if(s1 == null)
      return false;
    boolean ret = checkHard(s1.get());
    invertHard(s1.get());
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard)

  }

  /**
   * Method:  passeverywhereHard[]
   */
  public s.Hard passeverywhereHard_Impl (
    /*in*/ s.Hard s1,
    /*out*/ s.Hard.Holder s2,
    /*inout*/ s.Hard.Holder s3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard)
    // insert code here (passeverywhereHard)
    if(s2 != null) {
      s.Hard _s2 = new s.Hard();
      initHard(_s2);
      s2.set(_s2);
    }

    if(s3 != null && checkHard(s1) && checkHard(s3.get()))
      invertHard(s3.get());
    
    s.Hard ret = new s.Hard();
    initHard(ret);
    return ret;

    // DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard)

  }

  /**
   * Method:  returnCombined[]
   */
  public s.Combined returnCombined_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined)
    // insert code here (returnCombined)
    s.Combined ret = new s.Combined();
    initCombined(ret);
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.returnCombined)

  }

  /**
   * Method:  passinCombined[]
   */
  public boolean passinCombined_Impl (
    /*in*/ s.Combined s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined)
    // insert code here (passinCombined)
    return checkCombined(s1);
    // DO-NOT-DELETE splicer.end(s.StructTest.passinCombined)

  }

  /**
   * Method:  passoutCombined[]
   */
  public boolean passoutCombined_Impl (
    /*out*/ s.Combined.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined)
    // insert code here (passoutCombined)
    if(s1 == null)
      return false;
    
    s.Combined ret = new s.Combined();
    initCombined(ret);
    s1.set(ret);
    return true;
    // DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined)

  }

  /**
   * Method:  passinoutCombined[]
   */
  public boolean passinoutCombined_Impl (
    /*inout*/ s.Combined.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined)
    // insert code here (passinoutCombined)
    if(s1 == null)
      return false;

    boolean ret = checkCombined(s1.get());
    invertCombined(s1.get());
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined)

  }

  /**
   * Method:  passeverywhereCombined[]
   */
  public s.Combined passeverywhereCombined_Impl (
    /*in*/ s.Combined s1,
    /*out*/ s.Combined.Holder s2,
    /*inout*/ s.Combined.Holder s3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined)
    // insert code here (passeverywhereCombined)
    if(s2 != null) {
      s.Combined _s2 = new s.Combined();
      initCombined(_s2);
      s2.set(_s2);
    }

    if(s3 != null && checkCombined(s1) && checkCombined(s3.get()))
      invertCombined(s3.get());
    
    s.Combined ret = new s.Combined();
    initCombined(ret);
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined)

  }

  /**
   * Method:  passinRarrays[]
   */
  public boolean passinRarrays_Impl (
    /*in*/ s.Rarrays s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays)
    return checkRarrays(s1);
    // DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays)

  }

  /**
   * Method:  passinoutRarrays[]
   */
  public boolean passinoutRarrays_Impl (
    /*inout*/ s.Rarrays.Holder s1 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays)
    if(s1 == null)
      return false;
    boolean ret = checkRarrays(s1.get());
    invertRarrays(s1.get());
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays)

  }

  /**
   * Method:  passeverywhereRarrays[]
   */
  public boolean passeverywhereRarrays_Impl (
    /*in*/ s.Rarrays s1,
    /*inout*/ s.Rarrays.Holder s2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays)
    if(s2 == null)
      return false;
    boolean ret = checkRarrays(s1) && checkRarrays(s2.get());
    if(ret) invertRarrays(s2.get());
    return ret;
    // DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays)

  }


  // DO-NOT-DELETE splicer.begin(s.StructTest._misc)
  // insert code here (miscellaneous)
  // DO-NOT-DELETE splicer.end(s.StructTest._misc)

} // end class StructTest

