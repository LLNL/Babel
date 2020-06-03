/*
 * File:          ArrayOps_Impl.java
 * Symbol:        ArrayTest.ArrayOps-v1.3
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for ArrayTest.ArrayOps
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package ArrayTest;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._imports)
// Put additional imports here...
import java.lang.Math;
// DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._imports)

/**
 * Symbol "ArrayTest.ArrayOps" (version 1.3)
 */
public class ArrayOps_Impl extends ArrayOps
{

  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._data)
  // Put additional private data here...
  static public int test = 0;
  final static public int sidl_bool_array = 1;
  final static public int sidl_char_array      = 2;
  final static public int sidl_dcomplex_array  = 3;
  final static public int sidl_double_array    = 4;
  final static public int sidl_fcomplex_array  = 5;
  final static public int sidl_float_array     = 6;
  final static public int sidl_int_array       = 7;
  final static public int sidl_long_array      = 8;
  final static public int sidl_opaque_array    = 9;
  final static public int sidl_string_array    = 10;
  final static public int sidl_interface_array = 11;;

  public static int intFunc(int dimen, int ind[]) {
    int result = 1;
    int i=0;
    for(i = 0;i < dimen; ++i){
      result *= (ind[i] + (i + 1));
    }
    return result;

  }

  /** The arrays are passed in by reference here, this function increased
   *  the indicies, until it goes over the end of the array, it then returns
   *   false.
   */

  public static int next(int dimen, int ind[],
                         int lower[], int upper[])
  {
    int i = 0;
    while ((i < dimen) && (++(ind[i]) > upper[i])) {
      ind[i] = lower[i];
      ++i;
    }
    if(i < dimen)
      return i;
    else
      return 0;
  }

  /** This function creates a test C array according to the specs.
   *  One odd thing is it tests all 3 ways of setting an arrays element  
   *  It selects amoung these choices randomly.  (C Macro, array indicies,
    *  and explicit indicies. ) 
   */
  public static sidl.Integer.Array
    makeIntTestMatrix(int dimen)
  {
    sidl.Integer.Array result = null;
    int lower[] = {0, 0, 0, 0, 0, 0, 0};
    int upper[] = {3, 3, 2, 2, 2, 2, 2};
    int ind[] = {0, 0, 0, 0, 0, 0, 0};
    int value;
    result  = new sidl.Integer.Array(dimen, lower, upper, false);
    do {  
      value = intFunc(dimen, ind);
      switch(dimen) {
      case 3:
        result._set(ind[0], ind[1], ind[2], 0,0,0,0, value);
        break;
      case 4:
        result._set(ind[0], ind[1], ind[2], ind[3], 0,0,0,value);
        break;
      case 5:
        result._set(ind[0], ind[1], ind[2], ind[3], 
                   ind[4], 0, 0, value);
        break;
      case 6:
        result._set(ind[0], ind[1], ind[2], ind[3], 
                   ind[4], ind[5], 0, value);
        break;
      case 7:
        result._set(ind[0], ind[1], ind[2], ind[3], 
                   ind[4], ind[5], ind[6], value);
        break;
      }
    
    } while (next(dimen, ind, lower, upper) != 0);
    return result;
  }

  /** This function take an array, and initializes it to spec.
   *  One odd thing is it tests all 3 ways of setting an arrays element  
   *  It selects amoung these choices randomly.  (C Macro, array indicies,
   *  and explicit indicies. ) 
   */
  public static void
    initIntTestMatrix(sidl.Integer.Array a)
  {
    int lower[] = {a._lower(0), a._lower(1),a._lower(2) , a._lower(3), 
                   a._lower(4),a._lower(5) , a._lower(6)};
    int upper[] = {a._upper(0), a._upper(1),a._upper(2) , a._upper(3), 
                   a._upper(4),a._upper(5) , a._upper(6) };
    int ind[] = {0, 0, 0, 0, 0, 0, 0};
    int value;
    int dimen = a._dim();
    do {  
      value = intFunc(dimen, ind);
      switch(dimen) {
      case 3:
        a._set(ind[0], ind[1], ind[2], 0,0,0,0, value);
        break;
      case 4:
        a._set(ind[0], ind[1], ind[2], ind[3], 0,0,0,value);
        break;
      case 5:
        a._set(ind[0], ind[1], ind[2], ind[3], 
                   ind[4], 0, 0, value);
        break;
      case 6:
        a._set(ind[0], ind[1], ind[2], ind[3], 
                   ind[4], ind[5], 0, value);
        break;
      case 7:
        a._set(ind[0], ind[1], ind[2], ind[3], 
                   ind[4], ind[5], ind[6], value);
        break;
      }
    
    } while (next(dimen, ind, lower, upper) != 0);
    return;
  }

  public static double powTwo(long i) {
    double result = 1;
    if (i >= 0) {
      while (i-- != 0) {
        result *= 2;
      }
    }
    else if (i < 0) {
      while (i++ != 0) {
        result *= 0.5;
      }
    }
    return result;
  }

  public static float fpowTwo(long i) {
    float result = 0.0F;
 
    result = 1.0F;
    if (i >= 0) {
      while (i-- != 0) {
        result *= 2.0F;
      }
    }
    else if (i < 0) {
      while (i++ != 0) {
        result *= 0.5F;
      }
    }
    return result;
  }


  public static int isPrime(int num) {
    int i;
    for(i = 3; i*i <= num; ++i) {
      if ((num % i) == 0) return 0;
    }
    return 1;
  }

  public static int nextPrime(int prev) {
    if (prev <= 1L) {
      return 2;
    }
    else if (prev == 2) {
      return 3;
    }
    else {
      do {
        prev += 2;
      } while (isPrime(prev) == 0);
      return prev;
    }
  }

  public static  String s_TestText = "I'd rather write programs to write programs than write programs.";

  public static String s_TestWords[] = {
    "I'd",
    "rather",
    "write",
    "programs",
    "to",
    "write",
    "programs",
    "than",
    "write",
    "programs.",
    null
  };
  /* i'M NOT SURE WHAT TO DO WITH THESE GUYS
     const char *nextChar(const char *str) {
     if (!*(++str)) {
     str = s_TestText;
     }
     return str;
     }

     const char * const* nextWord(const char *const*word) {
     if (!*(++word)){
     word = s_TestWords;
     }
     return word;
     }
  */
  static int
    nextElem(int dimen,
             int ind[],
             int lower[],
             int upper[])
  {
    int i = 0;
    while ((i < dimen) && (++(ind[i]) > upper[i])) {
      ind[i] = lower[i];
      ++i;
    }
    if(i < dimen)
      return i;
    else
      return 0;
  }

  static int
    arrayValue(int dimen, int ind[])
  {
    int result = 1;
    int i;
    for(i = 0; i < dimen; ++i){
      result *= (ind[i] + (i + 1));
    }
    return result;
  }

  static boolean
    hasElements(int dimen, int lower[], int upper[])
  {
    int i;
    for (i = 0; i < dimen; ++i){
      if (lower[i] > upper[i]) return false;
    }
    return true;
  }

  static gov.llnl.sidl.BaseArray
    createArrayByType(int type,
                      int dimen,
                      int lower[],
                      int upper[])
  {
    switch(type){
    case sidl_bool_array:
      return (gov.llnl.sidl.BaseArray) new sidl.Boolean.Array(dimen, lower, upper, true);
    case sidl_char_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.Character.Array(dimen, lower, upper, true);
    case sidl_dcomplex_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.DoubleComplex.Array(dimen, lower, upper, true);
    case sidl_double_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.Double.Array(dimen, lower, upper, true);
    case sidl_fcomplex_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.FloatComplex.Array(dimen, lower, upper, true);
    case sidl_float_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.Float.Array(dimen, lower, upper, true);
    case sidl_int_array:
      return (gov.llnl.sidl.BaseArray) new sidl.Integer.Array(dimen, lower, upper, true);
    case sidl_long_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.Long.Array(dimen, lower, upper, true);
    case sidl_opaque_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.Opaque.Array(dimen, lower, upper, true);
    case sidl_string_array:
      return (gov.llnl.sidl.BaseArray) new  sidl.String.Array(dimen, lower, upper, true);
    case sidl_interface_array:
      return (gov.llnl.sidl.BaseArray) new sidl.BaseInterface.Array(dimen, lower, upper, true);
    default:
      return null;
    }
  }
  
  static void
    copyArrayByType(gov.llnl.sidl.BaseArray src,
                    gov.llnl.sidl.BaseArray dest)
{
  switch(src._type()){
  case sidl_bool_array:
    ((sidl.Boolean.Array)src)._copy((sidl.Boolean.Array)dest);
    return;
  case sidl_char_array:
    ((sidl.Character.Array)src)._copy((sidl.Character.Array)dest);
    return;
  case sidl_dcomplex_array:
    ((sidl.DoubleComplex.Array)src)._copy((sidl.DoubleComplex.Array)dest);
    return;    
  case sidl_double_array:
    ((sidl.Double.Array)src)._copy((sidl.Double.Array)dest);
    return;
  case sidl_fcomplex_array:
       ((sidl.FloatComplex.Array)src)._copy((sidl.FloatComplex.Array)dest);
    return;
  case sidl_float_array:
      ((sidl.Float.Array)src)._copy((sidl.Float.Array)dest);
    return;
  case sidl_int_array:
      ((sidl.Integer.Array)src)._copy((sidl.Integer.Array)dest);
    return;
  case sidl_long_array:
      ((sidl.Long.Array)src)._copy((sidl.Long.Array)dest);
    return;
  case sidl_opaque_array:
     ((sidl.Opaque.Array)src)._copy((sidl.Opaque.Array)dest);
    return;
  case sidl_string_array:
      ((sidl.String.Array)src)._copy((sidl.String.Array)dest);
    return;
  case sidl_interface_array:
      ((sidl.BaseInterface.Array)src)._copy((sidl.BaseInterface.Array)dest);
    return;
  }
}

static void locMatrixMultiply(sidl.Integer.Array2 a, sidl.Integer.Array2 b, 
                       sidl.Integer.Array2 res)
{
  int i,j,k;
  if(a._length(0) == res._length(0) &&
     a._length(1) == b._length(0) &&
     b._length(1) == res._length(1)) {
    for(i=a._lower(0);i<=a._upper(0);++i){
      for(k=b._lower(1);k<=b._upper(1);++k){
        int temp = 0;
        for(j=a._lower(1);j<=a._upper(1);++j) {
          temp += (a.get(i,j) * b.get(j,k));
        }
        res.set(i,k,temp);
      }
    }  
  }
}

  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._data)


  static { 
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._load)
  // Put load function implementation here...
    ++test;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._load)

  }

  /**
   * User defined constructor
   */
  public ArrayOps_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.ArrayOps)
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.ArrayOps)

  }

  /**
   * Back door constructor
   */
  public ArrayOps_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._wrap)
    // Insert-Code-Here {ArrayTest.ArrayOps._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._dtor)
    // add destruction details here
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.finalize)
    // Insert-Code-Here {ArrayTest.ArrayOps.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.finalize)

  }

  // user defined static methods:
  /**
   * Return <code>true</code> iff the even elements are true and
   * the odd elements are false.
   */
  public static boolean checkBool_Impl (
    /*in*/ sidl.Boolean.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkBool)
    // insert implementation here
    if ((a != null) && (1 == a._dim())) {
      int i;
      for(i = a._lower(0); i <= a._upper(0);  ++i) {
        if (a.get(i) != ((i & 0x1) != 1)) {
          return false;
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkBool)

  }

  /**
   * Method:  checkChar[]
   */
  public static boolean checkChar_Impl (
    /*in*/ sidl.Character.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkChar)
    // insert implementation here
    if ((a != null) && (1 == a._dim())) {
      int i;
      int j = 0;
      for(i = a._lower(0); i <= a._upper(0); 
          ++i) {
        if (a.get(i) != s_TestText.charAt(j%(s_TestText.length()))){
          return false;
        }
        ++j;
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkChar)

  }

  /**
   * Method:  checkInt[]
   */
  public static boolean checkInt_Impl (
    /*in*/ sidl.Integer.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkInt)
  // insert implementation here
  if ((a != null) && (1 == a._dim())) {
    int i;
    int lprime = nextPrime(0);
    int prime;
    for(i = a._lower(0); i <= a._upper(0); 
        ++i, lprime = nextPrime(lprime)) {
      prime = (int)lprime;
      if (prime != a.get(i)) {
        return false;
      }

    }
    return true;
  }
  return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkInt)

  }

  /**
   * Method:  checkLong[]
   */
  public static boolean checkLong_Impl (
    /*in*/ sidl.Long.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkLong)
    if ((a != null) && (1 == a._dim())) {
      int i;
      int lprime = nextPrime(0);
      int prime;
      for(i = a._lower(0); i <= a._upper(0); 
          ++i, lprime = nextPrime(lprime)) {
        prime = (int)lprime;
        if (prime != (int)a.get(i)) {
          return false;
        }
      
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkLong)

  }

  /**
   * Method:  checkString[]
   */
  public static boolean checkString_Impl (
    /*in*/ sidl.String.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkString)
    if ((a != null) && (1 == a._dim())) {
      int i;
      String testStr = null;
      int j = 0;
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        if(a.get(i).compareTo(s_TestWords[(j%(s_TestWords.length-1))]) != 0) {
          return false;
        }
        ++j;
      }
      return true;
    }
    return false;

    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkString)

  }

  /**
   * Method:  checkDouble[]
   */
  public static boolean checkDouble_Impl (
    /*in*/ sidl.Double.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDouble)
    if ((a != null) && (1 == a._dim())) {
      int i;
      for(i = a._lower(0); i <= a._upper(0); 
          ++i) {
        if (powTwo(-i) != a.get(i)) {
          return false;
        }
        
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDouble)

  }

  /**
   * Method:  checkFloat[]
   */
  public static boolean checkFloat_Impl (
    /*in*/ sidl.Float.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFloat)
    if ((a != null) && (1 == a._dim())) {
      int i;
      for(i = a._lower(0); i <= a._upper(0); 
          ++i) {
        if (fpowTwo(-i) != a.get(i)) {
          return false;
        }
     
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFloat)

  }

  /**
   * Method:  checkFcomplex[]
   */
  public static boolean checkFcomplex_Impl (
    /*in*/ sidl.FloatComplex.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFcomplex)
    if ((a != null) && (1 == a._dim())) {
      int i;
      for(i = a._lower(0); i <= a._upper(0); 
          ++i) {
        if (fpowTwo(-i) != a.get(i).imag() ||
            fpowTwo(i) != a.get(i).real()) {
          return false;
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFcomplex)

  }

  /**
   * Method:  checkDcomplex[]
   */
  public static boolean checkDcomplex_Impl (
    /*in*/ sidl.DoubleComplex.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDcomplex)
    // insert implementation here
    if ((a != null) && (1 == a._dim())) {
      int i;
      for(i = a._lower(0); i <= a._upper(0); 
          ++i) {
        if (powTwo(-i) != a.get(i).imag() ||
            powTwo(i) != a.get(i).real()) {
          return false;
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDcomplex)

  }

  /**
   * Method:  check2Int[]
   */
  public static boolean check2Int_Impl (
    /*in*/ sidl.Integer.Array2 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Int)
    // insert implementation here
    if ((a != null) && (2 == a._dim())) {
      int i,j;
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        for(j = a._lower(1); j <= a._upper(1); ++j) {
          if ((int)powTwo(Math.abs(i-j)) != a.get(i,j)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Int)

  }

  /**
   * Method:  check2Double[]
   */
  public static boolean check2Double_Impl (
    /*in*/ sidl.Double.Array2 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Double)
    // insert implementation here
    if ((a != null) && (2 == a._dim())) {
      int i,j;
      
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        for(j = a._lower(1); j <= a._upper(1); ++j) {
          if (powTwo(i-j) != a.get(i,j)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Double)

  }

  /**
   * Method:  check2Float[]
   */
  public static boolean check2Float_Impl (
    /*in*/ sidl.Float.Array2 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Float)
    // insert implementation here
    if ((a != null) && (2 == a._dim())) {
      int i,j;
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        for(j = a._lower(1); j <= a._upper(1); ++j) {
          if (fpowTwo(i-j) != a.get(i,j)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Float)

  }

  /**
   * Method:  check2Fcomplex[]
   */
  public static boolean check2Fcomplex_Impl (
    /*in*/ sidl.FloatComplex.Array2 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Fcomplex)
    if ((a != null) && (2 == a._dim())) {
      int i,j;
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        for(j = a._lower(1); j <= a._upper(1); ++j) {
          if (fpowTwo(i) != a.get(i,j).real() ||
              fpowTwo(-j) != a.get(i,j).imag()) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Fcomplex)

  }

  /**
   * Method:  check2Dcomplex[]
   */
  public static boolean check2Dcomplex_Impl (
    /*in*/ sidl.DoubleComplex.Array2 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Dcomplex)
    // insert implementation here
        if ((a != null) && (2 == a._dim())) {
      int i,j;
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        for(j = a._lower(1); j <= a._upper(1); ++j) {
          if (powTwo(i) != a.get(i,j).real() ||
              powTwo(-j) != a.get(i,j).imag()) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Dcomplex)

  }

  /**
   * Method:  check3Int[]
   */
  public static boolean check3Int_Impl (
    /*in*/ sidl.Integer.Array3 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check3Int)
    boolean result = false;
    int[] low = {a._lower(0), a._lower(1), a._lower(2)};
    int[] upp = {a._upper(0), a._upper(1), a._upper(2)};
    if ((a != null) && a._dim() == 3) {
      result = true;
      if (hasElements(3, low,upp)) {
        int value;
        int[] ind = {a._lower(0), a._lower(1), a._lower(2)};
        do {
          value = arrayValue(3, ind);
          if (a.get(ind[0], ind[1], ind[2]) != value) {
            result = false;
          }
        } while (result && (nextElem(3, ind, low, upp) != 0));
      }
    }
    return result;    
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check3Int)

  }

  /**
   * Method:  check4Int[]
   */
  public static boolean check4Int_Impl (
    /*in*/ sidl.Integer.Array4 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check4Int)
    // insert implementation here
    boolean result = false;
    int[] low = {a._lower(0), a._lower(1), a._lower(2), a._lower(3)};
    int[] upp = {a._upper(0), a._upper(1), a._upper(2), a.upper(3)};
    if ((a != null) && a._dim() == 4) {
      result = true;
      if (hasElements(4, low,upp)) {
        int value;
        int[] ind = {a._lower(0), a._lower(1), a._lower(2), a._lower(3)};
        do {
          value = arrayValue(4, ind);
          if (a.get(ind[0], ind[1], ind[2], ind[3]) != value) {
            result = false;
          }
        } while (result && nextElem(4, ind, low, upp) != 0);
      }
    }
    return result;    
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check4Int)

  }

  /**
   * Method:  check5Int[]
   */
  public static boolean check5Int_Impl (
    /*in*/ sidl.Integer.Array5 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check5Int)
    // insert implementation here
    boolean result = false;
    int[] low = {a._lower(0), a._lower(1), a._lower(2), a._lower(3), 
                 a._lower(4)};
    int[] upp = {a._upper(0), a._upper(1), a._upper(2), a.upper(3),
                 a._upper(4)};
    if ((a != null) && a._dim() == 5) {
      result = true;
      if (hasElements(5, low,upp)) {
        int value;
        int[] ind = {a._lower(0), a._lower(1), a._lower(2), a._lower(3),
                     a._lower(4)};
        do {
          value = arrayValue(5, ind);
          if (a.get(ind[0], ind[1], ind[2], ind[3], ind[4]) != value) {
            result = false;
          }
        } while (result && nextElem(5, ind, low, upp) != 0);
      }
    }
    return result;    
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check5Int)

  }

  /**
   * Method:  check6Int[]
   */
  public static boolean check6Int_Impl (
    /*in*/ sidl.Integer.Array6 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check6Int)
    boolean result = false;
    int[] low = {a._lower(0), a._lower(1), a._lower(2), a._lower(3), 
                 a._lower(4), a._lower(5)};
    int[] upp = {a._upper(0), a._upper(1), a._upper(2), a.upper(3),
                 a._upper(4), a._upper(5)};
    if ((a != null) && a._dim() == 6) {
      result = true;
      if (hasElements(6, low,upp)) {
        int value;
        int[] ind = {a._lower(0), a._lower(1), a._lower(2), a._lower(3),
                     a._lower(4), a._lower(5)};
        do {
          value = arrayValue(6, ind);
          if (a.get(ind[0], ind[1], ind[2], ind[3], ind[4], ind[5]) != value) {
            result = false;
          }
        } while (result && nextElem(6, ind, low, upp) != 0);
      }
    }
    return result;    
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check6Int)

  }

  /**
   * Method:  check7Int[]
   */
  public static boolean check7Int_Impl (
    /*in*/ sidl.Integer.Array7 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check7Int)
    boolean result = false;
    int[] low = {a._lower(0), a._lower(1), a._lower(2), a._lower(3), 
                 a._lower(4), a._lower(5), a._lower(6)};
    int[] upp = {a._upper(0), a._upper(1), a._upper(2), a.upper(3),
                 a._upper(4), a._upper(5), a._lower(6)};
    if ((a != null) && a._dim() == 7) {
      result = true;
      if (hasElements(7, low,upp)) {
        int value;
        int[] ind = {a._lower(0), a._lower(1), a._lower(2), a._lower(3),
                     a._lower(4), a._lower(5), a._lower(6)};
        do {
          value = arrayValue(7, ind);
          if (a.get(ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], ind[6]) != value) {
            result = false;
          }
        } while (result && nextElem(7, ind, low, upp) != 0);
      }
    }
    return result;      
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check7Int)

  }

  /**
   * Method:  check2String[]
   */
  public static boolean check2String_Impl (
    /*in*/ sidl.String.Array2 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2String)
    // Insert-Code-Here {ArrayTest.ArrayOps.check2String} (check2String)
    if ((a != null) && (2 == a._dim())) {
      int i;
      String[] testWord = s_TestWords;
      String testStr = null;
      int j = 0;
      int c = 0;
      for(i = a._lower(0); i <= a._upper(0); ++i) {
        for(j = a._lower(1); j <= a._upper(1); ++j) {
          if(a.get(i,j).compareTo(s_TestWords[(c%(s_TestWords.length-1))]) != 0) {
            return false;
          }
          ++c;
        }
        
      }
      return true;
    }
    return false;

    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2String)

  }

  /**
   * Method:  checkObject[]
   */
  public static int checkObject_Impl (
    /*in*/ ArrayTest.ArrayOps.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkObject)
    if ((a != null) && a._dim() == 1) {
      int upper = a._upper(0);
      int i, count=0;
      for(i = a._lower(0); i <= upper; ++i) {
        ArrayTest.ArrayOps obj = a._get(i);
        if (obj != null && ArrayTest.ArrayOps._cast(obj) != null) {
          ++count;
        }
      }
      return count;
    }
    return 0;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkObject)

  }

  /**
   * Method:  reverseBool[]
   */
  public static boolean reverseBool_Impl (
    /*inout*/ sidl.Boolean.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseBool)
    // insert implementation here
    boolean result = false;
    sidl.Boolean.Array1 arry = a.get();
    
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkBool(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.Boolean.Array1
          copy = new sidl.Boolean.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy);
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          boolean tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseBool)

  }

  /**
   * Method:  reverseChar[]
   */
  public static boolean reverseChar_Impl (
    /*inout*/ sidl.Character.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseChar)
    boolean result = false;
    sidl.Character.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkChar(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.Character.Array1
          copy = new sidl.Character.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy);
       }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          char tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseChar)

  }

  /**
   * Method:  reverseInt[]
   */
  public static boolean reverseInt_Impl (
    /*inout*/ sidl.Integer.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseInt)
    boolean result = false;
    sidl.Integer.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkInt(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.Integer.Array1
          copy = new sidl.Integer.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }

        a.set(copy);
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          int tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseInt)

  }

  /**
   * Method:  reverseLong[]
   */
  public static boolean reverseLong_Impl (
    /*inout*/ sidl.Long.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseLong)
    boolean result = false;
    sidl.Long.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkLong(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.Long.Array1
          copy = new sidl.Long.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy);
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          long tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseLong)

  }

  /**
   * Method:  reverseString[]
   */
  public static boolean reverseString_Impl (
    /*inout*/ sidl.String.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseString)
    boolean result = false;
    sidl.String.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkString(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.String.Array1
          copy = new sidl.String.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy);
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          String tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseString)

  }

  /**
   * Method:  reverseDouble[]
   */
  public static boolean reverseDouble_Impl (
    /*inout*/ sidl.Double.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDouble)
    boolean result = false;
    sidl.Double.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkDouble(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.Double.Array1
          copy = new sidl.Double.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy);
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          double tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDouble)

  }

  /**
   * Method:  reverseFloat[]
   */
  public static boolean reverseFloat_Impl (
    /*inout*/ sidl.Float.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFloat)
    boolean result = false;
    sidl.Float.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkFloat(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.Float.Array1
          copy = new sidl.Float.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy);
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          float tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFloat)

  }

  /**
   * Method:  reverseFcomplex[]
   */
  public static boolean reverseFcomplex_Impl (
    /*inout*/ sidl.FloatComplex.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFcomplex)
    boolean result = false;
    sidl.FloatComplex.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkFcomplex(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.FloatComplex.Array1
          copy = new sidl.FloatComplex.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy); 
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          sidl.FloatComplex tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFcomplex)

  }

  /**
   * Method:  reverseDcomplex[]
   */
  public static boolean reverseDcomplex_Impl (
    /*inout*/ sidl.DoubleComplex.Array1.Holder a,
    /*in*/ boolean newArray ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDcomplex)
    boolean result = false;
    sidl.DoubleComplex.Array1 arry = a.get();
    if (arry != null && arry._dim() == 1) {
      int upper = arry._upper(0);
      int lower = arry._lower(0);
      result = ArrayTest.ArrayOps.checkDcomplex(arry);
      if (newArray) {
        int len = upper-lower + 1;
        int i;
        sidl.DoubleComplex.Array1
          copy = new sidl.DoubleComplex.Array1(lower,upper, true);
        for(i = 0; i < len; ++i){
          copy.set(upper - i, arry.get(lower + i));
        }
        arry.destroy();
        a.set(copy); 
      }
      else {
        /* reverse in place */
        int len = (upper - lower + 1) >> 1;
        int i;
        for(i = 0; i < len; ++i ) {
          sidl.DoubleComplex tmp = arry.get(lower + i);
          arry.set(lower + i, arry.get(upper - i));
          arry.set(upper - i, tmp);
        }
      }
    }
    return result; 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDcomplex)

  }

  /**
   * Method:  createBool[]
   */
  public static sidl.Boolean.Array1 createBool_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createBool)
    // insert implementation here
    if(len < 0) return null;    int lower = 0;
    int upper = len - 1;
    int i = 0;
    sidl.Boolean.Array1 ret = new sidl.Boolean.Array1(lower,upper, false);
    for(i = 0; i < len; ++i) {
      ret.set(i, ((i & 0x1) != 1));
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createBool)

  }

  /**
   * Method:  createChar[]
   */
  public static sidl.Character.Array1 createChar_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createChar)
    // insert implementation here
    if(len < 0) return null;
    int lower = 0;
    int upper = len - 1;
    int i = 0;
    int j = 0;
    sidl.Character.Array1 ret = new sidl.Character.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); 
        ++i) {
      ret.set(i, s_TestText.charAt(j%(s_TestText.length())));
        ++j;
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createChar)

  }

  /**
   * Method:  createInt[]
   */
  public static sidl.Integer.Array1 createInt_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createInt)
    // insert implementation here
    if(len < 0) return null;
    int lower = 0;
    int upper = len - 1;
    int i = 0;
    int lprime = nextPrime(0);
    sidl.Integer.Array1 ret = new sidl.Integer.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); 
        ++i, lprime = nextPrime(lprime)) {
      ret.set(i, lprime);
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createInt)

  }

  /**
   * Method:  createLong[]
   */
  public static sidl.Long.Array1 createLong_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createLong)
    if(len < 0) return null;
    int i;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    sidl.Long.Array1 ret = new sidl.Long.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); 
        ++i, lprime = nextPrime(lprime)) {
      prime = (int)lprime;
      ret.set(i,prime);
    }
    return ret;
  
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createLong)

  }

  /**
   * Method:  createString[]
   */
  public static sidl.String.Array1 createString_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createString)
    if(len < 0) return null;
    int i,j = 0;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    sidl.String.Array1 ret = new sidl.String.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, s_TestWords[(j%(s_TestWords.length-1))]);
      ++j;
      }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createString)

  }

  /**
   * Method:  createDouble[]
   */
  public static sidl.Double.Array1 createDouble_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDouble)
    if(len < 0) return null;
    int i,j;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    sidl.Double.Array1 ret = new sidl.Double.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, powTwo(-i));
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDouble)

  }

  /**
   * Method:  createFloat[]
   */
  public static sidl.Float.Array1 createFloat_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFloat)
    if(len < 0) return null;
    int i;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    sidl.Float.Array1 ret = new sidl.Float.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, fpowTwo(-i));
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFloat)

  }

  /**
   * Method:  createFcomplex[]
   */
  public static sidl.FloatComplex.Array1 createFcomplex_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFcomplex)
    if(len < 0) return null;
    int i;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    sidl.FloatComplex.Array1 ret = new sidl.FloatComplex.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, new sidl.FloatComplex(fpowTwo(i),fpowTwo(-i)));
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFcomplex)

  }

  /**
   * Method:  createDcomplex[]
   */
  public static sidl.DoubleComplex.Array1 createDcomplex_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDcomplex)
    if(len < 0) return null;
    int i;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    sidl.DoubleComplex.Array1 ret = new sidl.DoubleComplex.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, new sidl.DoubleComplex(powTwo(i),powTwo(-i)));
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDcomplex)

  }

  /**
   * Method:  createObject[]
   */
  public static ArrayTest.ArrayOps.Array1 createObject_Impl (
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createObject)
    if(len < 0) return null;
    int i;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = len - 1;  
    ArrayTest.ArrayOps.Array1 ret = new ArrayTest.ArrayOps.Array1(lower,upper, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ArrayTest.ArrayOps tmp = new ArrayTest.ArrayOps();
      ret._set(i, tmp);
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createObject)

  }

  /**
   * Method:  create2Int[]
   */
  public static sidl.Integer.Array2 create2Int_Impl (
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Int)
    if(d1 < 0 || d2 < 0) return null;
    int i,j;
    int[] lower = {0,0};
    int[] upper = {d1-1, d2-1};
    int prime;
    sidl.Integer.Array2 ret = (sidl.Integer.Array2)
      new sidl.Integer.Array(2, lower, upper, false)._dcast();
    for(i = 0; i < d1; ++i) {
      for(j = 0; j < d2; ++j) {
        ret.set(i,j,(int)powTwo(Math.abs(i-j)));
        
      }
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Int)

  }

  /**
   * Method:  create2Double[]
   */
  public static sidl.Double.Array2 create2Double_Impl (
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Double)
    if(d1 < 0 || d2 < 0) return null;
    int i,j;
    int[] lower = {0,0};
    int[] upper = {d1-1, d2-1};
    int prime;
    sidl.Double.Array2 ret = new sidl.Double.Array2(lower[0], lower[1],
                                                    upper[0], upper[1], false);
    for(i = 0; i < d1; ++i) {
      for(j = 0; j < d2; ++j) {
        ret.set(i,j,powTwo(i-j));
        
      }
    }
    
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Double)

  }

  /**
   * Method:  create2Float[]
   */
  public static sidl.Float.Array2 create2Float_Impl (
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Float)
    if(d1 < 0 || d2 < 0) return null;
    int i,j;
    int[] lower = {0,0};
    int[] upper = {d1-1, d2-1};
    int prime;
    sidl.Float.Array2 ret = new sidl.Float.Array2(lower[0], lower[1],
                                                    upper[0], upper[1], false);
    for(i = 0; i < d1; ++i) {
      for(j = 0; j < d2; ++j) {
        ret.set(i,j,fpowTwo(i-j));
        
      }
    }
    
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Float)

  }

  /**
   * Method:  create2Dcomplex[]
   */
  public static sidl.DoubleComplex.Array2 create2Dcomplex_Impl (
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Dcomplex)
    if(d1 < 0 || d2 < 0) return null;
    int i,j;
    int[] lower = {0,0};
    int[] upper = {d1-1, d2-1};
    int prime;
    sidl.DoubleComplex.Array2 ret = new sidl.DoubleComplex.Array2(lower[0], lower[1],
                                                                  upper[0], upper[1], false);
    for(i = 0; i < d1; ++i) {
      for(j = 0; j < d2; ++j) {
        ret.set(i,j, new sidl.DoubleComplex(powTwo(i), powTwo(-j)));
        
      }
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Dcomplex)

  }

  /**
   * Method:  create2Fcomplex[]
   */
  public static sidl.FloatComplex.Array2 create2Fcomplex_Impl (
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Fcomplex)
    if(d1 < 0 || d2 < 0) return null; 
    int i,j;
    int[] lower = {0,0};
    int[] upper = {d1-1, d2-1};
    int prime;
    sidl.FloatComplex.Array2 ret = new sidl.FloatComplex.Array2(lower[0], lower[1],
                                                                  upper[0], upper[1], false);
    for(i = 0; i < d1; ++i) {
      for(j = 0; j < d2; ++j) {
        ret.set(i,j, new sidl.FloatComplex(fpowTwo(i), fpowTwo(-j)));
        
      }
    }
    return ret;

    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Fcomplex)

  }

  /**
   * Method:  create2String[]
   */
  public static sidl.String.Array2 create2String_Impl (
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2String)
    // Insert-Code-Here {ArrayTest.ArrayOps.create2String} (create2String)
    if(d1 < 0 || d2 < 0) return null;
    int i,j,c = 0;
    int lprime = nextPrime(0);
    int prime;
    int lower = 0;
    int upper = d1 - 1;
    int upper2 = d2 - 1;
    sidl.String.Array2 ret = new sidl.String.Array2(lower,lower, upper, upper2, false);
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      for(j = ret._lower(1); j <= ret._upper(1); ++j) {
         ret.set(i, j, s_TestWords[(c%(s_TestWords.length-1))]);
        ++c;
      }
    }
    return ret;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2String)

  }

  /**
   * Method:  create3Int[]
   */
  public static sidl.Integer.Array3 create3Int_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create3Int)
    return (sidl.Integer.Array3) makeIntTestMatrix(3)._dcast();
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create3Int)

  }

  /**
   * Method:  create4Int[]
   */
  public static sidl.Integer.Array4 create4Int_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create4Int)
    return (sidl.Integer.Array4) makeIntTestMatrix(4)._dcast();

    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create4Int)

  }

  /**
   * Method:  create5Int[]
   */
  public static sidl.Integer.Array5 create5Int_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create5Int)
    return (sidl.Integer.Array5)makeIntTestMatrix(5)._dcast();
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create5Int)

  }

  /**
   * Method:  create6Int[]
   */
  public static sidl.Integer.Array6 create6Int_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create6Int)
    return (sidl.Integer.Array6)makeIntTestMatrix(6)._dcast();
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create6Int)

  }

  /**
   * Method:  create7Int[]
   */
  public static sidl.Integer.Array7 create7Int_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create7Int)
    return (sidl.Integer.Array7)makeIntTestMatrix(7)._dcast();
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create7Int)

  }

  /**
   * Method:  makeBool[]
   */
  public static void makeBool_Impl (
    /*in*/ int len,
    /*out*/ sidl.Boolean.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeBool)
    a.set(createBool(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeBool)

  }

  /**
   * Method:  makeChar[]
   */
  public static void makeChar_Impl (
    /*in*/ int len,
    /*out*/ sidl.Character.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeChar)
    a.set(createChar(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeChar)

  }

  /**
   * Method:  makeInt[]
   */
  public static void makeInt_Impl (
    /*in*/ int len,
    /*out*/ sidl.Integer.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInt)
    a.set(createInt(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInt)

  }

  /**
   * Method:  makeLong[]
   */
  public static void makeLong_Impl (
    /*in*/ int len,
    /*out*/ sidl.Long.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeLong)
    a.set(createLong(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeLong)

  }

  /**
   * Method:  makeString[]
   */
  public static void makeString_Impl (
    /*in*/ int len,
    /*out*/ sidl.String.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeString)
    a.set(createString(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeString)

  }

  /**
   * Method:  makeDouble[]
   */
  public static void makeDouble_Impl (
    /*in*/ int len,
    /*out*/ sidl.Double.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDouble)
    a.set(createDouble(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDouble)

  }

  /**
   * Method:  makeFloat[]
   */
  public static void makeFloat_Impl (
    /*in*/ int len,
    /*out*/ sidl.Float.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFloat)
    a.set(createFloat(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFloat)

  }

  /**
   * Method:  makeFcomplex[]
   */
  public static void makeFcomplex_Impl (
    /*in*/ int len,
    /*out*/ sidl.FloatComplex.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFcomplex)
    a.set(createFcomplex(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFcomplex)

  }

  /**
   * Method:  makeDcomplex[]
   */
  public static void makeDcomplex_Impl (
    /*in*/ int len,
    /*out*/ sidl.DoubleComplex.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDcomplex)
    a.set(createDcomplex(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDcomplex)

  }

  /**
   * Method:  makeInOutBool[]
   */
  public static void makeInOutBool_Impl (
    /*inout*/ sidl.Boolean.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutBool)
    if(a.get() != null)
      a.get().destroy();
    a.set(createBool(len));
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutBool)

  }

  /**
   * Method:  makeInOutChar[]
   */
  public static void makeInOutChar_Impl (
    /*inout*/ sidl.Character.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutChar)
    if(a.get() != null)
      a.get().destroy();
    a.set(createChar(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutChar)

  }

  /**
   * Method:  makeInOutInt[]
   */
  public static void makeInOutInt_Impl (
    /*inout*/ sidl.Integer.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutInt)
    if(a.get() != null)
      a.get().destroy();
    a.set(createInt(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutInt)

  }

  /**
   * Method:  makeInOutLong[]
   */
  public static void makeInOutLong_Impl (
    /*inout*/ sidl.Long.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutLong)
    if(a.get() != null)
      a.get().destroy();
    a.set(createLong(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutLong)

  }

  /**
   * Method:  makeInOutString[]
   */
  public static void makeInOutString_Impl (
    /*inout*/ sidl.String.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutString)
    if(a.get() != null)
      a.get().destroy();
    a.set(createString(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutString)

  }

  /**
   * Method:  makeInOutDouble[]
   */
  public static void makeInOutDouble_Impl (
    /*inout*/ sidl.Double.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDouble)
    if(a.get() != null)
      a.get().destroy();
    a.set(createDouble(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDouble)

  }

  /**
   * Method:  makeInOutFloat[]
   */
  public static void makeInOutFloat_Impl (
    /*inout*/ sidl.Float.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFloat)
    if(a.get() != null)
      a.get().destroy();
    a.set(createFloat(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFloat)

  }

  /**
   * Method:  makeInOutDcomplex[]
   */
  public static void makeInOutDcomplex_Impl (
    /*inout*/ sidl.DoubleComplex.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDcomplex)
    if(a.get() != null)
      a.get().destroy();
    a.set(createDcomplex(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDcomplex)

  }

  /**
   * Method:  makeInOutFcomplex[]
   */
  public static void makeInOutFcomplex_Impl (
    /*inout*/ sidl.FloatComplex.Array1.Holder a,
    /*in*/ int len ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFcomplex)
    if(a.get() != null)
      a.get().destroy();
    a.set(createFcomplex(len)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFcomplex)

  }

  /**
   * Method:  makeInOut2Int[]
   */
  public static void makeInOut2Int_Impl (
    /*inout*/ sidl.Integer.Array2.Holder a,
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Int)
    if(a.get() != null)
      a.get().destroy();
    a.set(create2Int(d1,d2)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Int)

  }

  /**
   * Method:  makeInOut2Double[]
   */
  public static void makeInOut2Double_Impl (
    /*inout*/ sidl.Double.Array2.Holder a,
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Double)
    if(a.get() != null)
      a.get().destroy();
    a.set(create2Double(d1,d2)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Double)

  }

  /**
   * Method:  makeInOut2Float[]
   */
  public static void makeInOut2Float_Impl (
    /*inout*/ sidl.Float.Array2.Holder a,
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Float)
    if(a.get() != null)
      a.get().destroy();
    a.set(create2Float(d1,d2)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Float)

  }

  /**
   * Method:  makeInOut2Dcomplex[]
   */
  public static void makeInOut2Dcomplex_Impl (
    /*inout*/ sidl.DoubleComplex.Array2.Holder a,
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Dcomplex)
    if(a.get() != null)
      a.get().destroy();
    a.set(create2Dcomplex(d1,d2)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Dcomplex)

  }

  /**
   * Method:  makeInOut2Fcomplex[]
   */
  public static void makeInOut2Fcomplex_Impl (
    /*inout*/ sidl.FloatComplex.Array2.Holder a,
    /*in*/ int d1,
    /*in*/ int d2 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Fcomplex)
    if(a.get() != null)
      a.get().destroy();
    a.set(create2Fcomplex(d1,d2)); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Fcomplex)

  }

  /**
   * Method:  makeInOut3Int[]
   */
  public static void makeInOut3Int_Impl (
    /*inout*/ sidl.Integer.Array3.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut3Int)
    if(a.get() != null)
      a.get().destroy();
    a.set(create3Int()); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut3Int)

  }

  /**
   * Method:  makeInOut4Int[]
   */
  public static void makeInOut4Int_Impl (
    /*inout*/ sidl.Integer.Array4.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut4Int)
    if(a.get() != null)
      a.get().destroy();
    a.set(create4Int()); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut4Int)

  }

  /**
   * Method:  makeInOut5Int[]
   */
  public static void makeInOut5Int_Impl (
    /*inout*/ sidl.Integer.Array5.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut5Int)
    if(a.get() != null)
      a.get().destroy();
    a.set(create5Int());  
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut5Int)

  }

  /**
   * Method:  makeInOut6Int[]
   */
  public static void makeInOut6Int_Impl (
    /*inout*/ sidl.Integer.Array6.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut6Int)
    if(a.get() != null)
      a.get().destroy();
    a.set(create6Int()); 
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut6Int)

  }

  /**
   * Method:  makeInOut7Int[]
   */
  public static void makeInOut7Int_Impl (
    /*inout*/ sidl.Integer.Array7.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut7Int)
    if(a.get() != null)
      a.get().destroy();
    a.set(create7Int()); 

    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut7Int)

  }

  /**
   * Return as out parameters the type and dimension of the 
   * array passed in. If a is NULL, dimen == type == 0 on exit.
   * The contents of the array have the default values for a 
   * newly created array.
   */
  public static void checkGeneric_Impl (
    /*in*/ gov.llnl.sidl.BaseArray a,
    /*out*/ sidl.Integer.Holder dmn,
    /*out*/ sidl.Integer.Holder tp ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkGeneric)
    // insert implementation here
    if (a != null) {
      dmn.set(a._dim());
      tp.set(a._type());
    }
    else {
      dmn.set(0);
      tp.set(0);
    }
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkGeneric)

  }

  /**
   * Create an array of the type and dimension specified and
   * return it. A type of 0 causes a NULL array to be returned.
   */
  public static gov.llnl.sidl.BaseArray createGeneric_Impl (
    /*in*/ int dmn,
    /*in*/ int tp ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createGeneric)
    // insert implementation here
    int lower[] = {0, 0, 0, 0, 0, 0, 0};
    int upper[] = {2, 2, 2, 2, 2, 2, 2};
    return createArrayByType(tp, dmn, lower, upper);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createGeneric)

  }

  /**
   * Testing passing generic arrays using every possible mode.
   * The returned array is a copy of inArg, so if inArg != NULL,
   * the return value should != NULL. outArg is also a copy of
   * inArg.
   * If inOutArg is NULL on entry, a 2-D array of int that should
   * pass check2Int is returned.
   * If inOutArg is not NULL on entry and its dimension is even,
   * it is returned unchanged; otherwise, NULL is returned.
   */
  public static gov.llnl.sidl.BaseArray passGeneric_Impl (
    /*in*/ gov.llnl.sidl.BaseArray inArg,
    /*inout*/ gov.llnl.sidl.BaseArray.Holder inOutArg,
    /*out*/ gov.llnl.sidl.BaseArray.Holder outArg ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.passGeneric)
    // insert implementation here
    int lower[] = new int[7];
    int upper[] = new int[7];
    int i;
    gov.llnl.sidl.BaseArray result = null;
    if (inArg != null) {
      int dimen = inArg._dim();
      for(i = 0; i < dimen; ++i) {
        lower[i] = inArg.lower(i);
        upper[i] = inArg.upper(i);
      }
      
      result = createArrayByType(inArg._type(), dimen, lower, upper);
      outArg.set(createArrayByType(inArg._type(), dimen, lower, upper));
      copyArrayByType(inArg, result);
      copyArrayByType(inArg, outArg.get());
    }
   
    if (inOutArg.get() != null) {
      if ((inOutArg.get()._dim() % 2) == 0) {
        inOutArg.set(null);
      }
    }
    else {
      inOutArg.set(ArrayTest.ArrayOps.create2Int(3, 3));
    }
   
    return result;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.passGeneric)

  }

  /**
   * Method:  initRarray1Int[]
   */
  public static void initRarray1Int_Impl (
    /*inout*/ sidl.Integer.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Int)
    // insert implementation here
    sidl.Integer.Array1 ret = a.get();
    int len = ret._upper(0);
    if(len < 0) return;
    int i = 0;
    int lprime = nextPrime(0);
    for(i = ret._lower(0); i <= ret._upper(0); 
        ++i, lprime = nextPrime(lprime)) {
      ret.set(i, lprime);
    }
    a.set(ret);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Int)

  }

  /**
   * Method:  initRarray3Int[]
   */
  public static void initRarray3Int_Impl (
    /*inout*/ sidl.Integer.Array3.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray3Int)
    // insert implementation here
    initIntTestMatrix(a.get());
    return;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray3Int)

  }

  /**
   * Method:  initRarray7Int[]
   */
  public static void initRarray7Int_Impl (
    /*inout*/ sidl.Integer.Array7.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray7Int)
    // insert implementation here
    initIntTestMatrix(a.get());
    return ;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray7Int)

  }

  /**
   * Method:  initRarray1Double[]
   */
  public static void initRarray1Double_Impl (
    /*inout*/ sidl.Double.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Double)
    // insert implementation here
    sidl.Double.Array1 ret = a.get();
    int len = ret._upper(0);
    if(len < 0) return;
    int i;
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, powTwo(-i));
    }
    a.set(ret);
    return ;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Double)

  }

  /**
   * Method:  initRarray1Dcomplex[]
   */
  public static void initRarray1Dcomplex_Impl (
    /*inout*/ sidl.DoubleComplex.Array1.Holder a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Dcomplex)
    // insert implementation here
    sidl.DoubleComplex.Array1 ret = a.get();
    if(ret._upper(0) < 0) return;
    int i;
    for(i = ret._lower(0); i <= ret._upper(0); ++i) {
      ret.set(i, new sidl.DoubleComplex(powTwo(i),powTwo(-i)));
    }
    a.set(ret);
    return ;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Dcomplex)

  }

  /**
   * Method:  checkRarray1Int[]
   */
  public static boolean checkRarray1Int_Impl (
    /*in*/ sidl.Integer.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Int)
    // insert implementation here

    return checkInt(a);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Int)

  }

  /**
   * Method:  checkRarray3Int[]
   */
  public static boolean checkRarray3Int_Impl (
    /*in*/ sidl.Integer.Array3 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray3Int)
    // insert implementation here
    return check3Int(a);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray3Int)

  }

  /**
   * Method:  checkRarray7Int[]
   */
  public static boolean checkRarray7Int_Impl (
    /*in*/ sidl.Integer.Array7 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray7Int)
    // insert implementation here
    return check7Int(a);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray7Int)

  }

  /**
   * Method:  checkRarray1Double[]
   */
  public static boolean checkRarray1Double_Impl (
    /*in*/ sidl.Double.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Double)
    // insert implementation here
    return checkDouble(a);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Double)

  }

  /**
   * Method:  checkRarray1Dcomplex[]
   */
  public static boolean checkRarray1Dcomplex_Impl (
    /*in*/ sidl.DoubleComplex.Array1 a ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Dcomplex)
    // insert implementation here
    return checkDcomplex(a);
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Dcomplex)

  }

  /**
   * Method:  matrixMultiply[]
   */
  public static void matrixMultiply_Impl (
    /*in*/ sidl.Integer.Array2 a,
    /*in*/ sidl.Integer.Array2 b,
    /*inout*/ sidl.Integer.Array2.Holder res ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.matrixMultiply)
    // insert implementation here
    locMatrixMultiply(a,b,res.get());
    return ;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.matrixMultiply)

  }

  /**
   * Method:  checkMatrixMultiply[]
   */
  public static boolean checkMatrixMultiply_Impl (
    /*in*/ sidl.Integer.Array2 a,
    /*in*/ sidl.Integer.Array2 b,
    /*in*/ sidl.Integer.Array2 res ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkMatrixMultiply)
    // insert implementation here

    if(a._length(0) == res._length(0) &&
       a._length(1) == b._length(0) &&
       b._length(1) == res._length(1)) {

      int[] lower = {res._lower(0),res._lower(1)};
      int[] upper = {res._upper(0),res._upper(1)};
      sidl.Integer.Array2 temp = (sidl.Integer.Array2)
        new sidl.Integer.Array(2, lower, upper, false)._dcast();
      locMatrixMultiply(a,b,temp);

      for(int i = res._lower(0); i <= res._upper(0); ++i) {
        for(int j = res._lower(1); j <= res._upper(1); ++j) {
          if(res.get(i,j) != temp.get(i,j))
            return false;
        }
      }
      return true;
    }
    return false;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkMatrixMultiply)

  }


  // user defined non-static methods:
  /**
   * Method:  mm[]
   */
  public void mm_Impl (
    /*in*/ sidl.Integer.Array2 a,
    /*in*/ sidl.Integer.Array2 b,
    /*inout*/ sidl.Integer.Array2.Holder res ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.mm)
    // Insert-Code-Here {ArrayTest.ArrayOps.mm} (mm)
    locMatrixMultiply(a,b,res.get());
    return ;
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.mm)

  }

  /**
   * Method:  checkmm[]
   */
  public boolean checkmm_Impl (
    /*in*/ sidl.Integer.Array2 a,
    /*in*/ sidl.Integer.Array2 b,
    /*in*/ sidl.Integer.Array2 res ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkmm)
    // Insert-Code-Here {ArrayTest.ArrayOps.checkmm} (checkmm)
    if(a._length(0) == res._length(0) &&
       a._length(1) == b._length(0) &&
       b._length(1) == res._length(1)) {

      int[] lower = {res._lower(0),res._lower(1)};
      int[] upper = {res._upper(0),res._upper(1)};
      sidl.Integer.Array2 temp = (sidl.Integer.Array2)
        new sidl.Integer.Array(2, lower, upper, false)._dcast();
      locMatrixMultiply(a,b,temp);

      for(int i = res._lower(0); i <= res._upper(0); ++i) {
        for(int j = res._lower(1); j <= res._upper(1); ++j) {
          if(res.get(i,j) != temp.get(i,j))
            return false;
        }
      }
      return true;
    }
    return false;

    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkmm)

  }


  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._misc)
  // Put miscellaneous code here
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._misc)

} // end class ArrayOps

