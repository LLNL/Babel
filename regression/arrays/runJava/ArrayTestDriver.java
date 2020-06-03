// 
// File:        ArrayTestDriver.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7003 $
// Date:        $Date: 2010-11-18 17:01:04 -0800 (Thu, 18 Nov 2010) $
// Description: array regression test case for Java calling other languages
// 

/**
 * The following class runs the array regression test cases for Java.
 */
public class ArrayTestDriver {
  private final static int FAIL        = 0;
  private final static int XFAIL       = 1;
  private final static int PASS        = 2;
  private final static int XPASS       = 3;
  private final static int UNSUPPORTED = 4;

  private final static String[] s_results = {
    "FAIL",
    "XFAIL",
    "PASS",
    "XPASS",
    "UNSUPPORTED"
  };

  private final static int ONE_D_SIZE = 345;
  private final static int TEST_DIM1 = 13;
  private final static int TEST_DIM2 = 16;

  private static int s_part = 0;
  private static long s_result = synch.ResultType.PASS;

  private static synch.RegOut tracker; 
  /**
   * Check the results of the test case.
   */
  //Comments should really be passed in here.
  private static void startTest(String test) {
    tracker.startPart(++s_part);
    if(test != null)
      tracker.writeComment(test);
  }
  
  private static void check(long expected, boolean pass, String test ) {
    if (test != null)
      tracker.writeComment(test);
    
    if(expected == synch.ResultType.PASS)
      if(pass)
        tracker.endPart(s_part, synch.ResultType.PASS);
      else
        tracker.endPart(s_part, synch.ResultType.FAIL);
    else if (expected == synch.ResultType.XFAIL)
      if(pass)
        tracker.endPart(s_part, synch.ResultType.XPASS);
      else
        tracker.endPart(s_part, synch.ResultType.XFAIL);
    else
      tracker.endPart(s_part, synch.ResultType.FAIL);
  }

  private static void tests(synch.RegOut tracker)
    throws InterruptedException
  {
      /*
       * Boolean array tests
       */

      /*if (true) {
        sidl.Integer.Array1 array = ArrayTest.ArrayOps.createInt(ONE_D_SIZE);

	int i = array._get(1);
	System.out.println(i);
	array._destroy();
	}*/
      if (true) {
	sidl.Boolean.Array1 array = ArrayTest.ArrayOps.createBool(ONE_D_SIZE);
        sidl.Boolean.Array1.Holder hold = new sidl.Boolean.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createBool: (array != null)");

        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkBool(array) == true),
              "createBool: (ArrayTest.ArrayOps.checkBool(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseBool(hold, true) == true),
              "createBool: " + 
              "(ArrayTest.ArrayOps.reverseBool(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array._length(0) == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.Boolean.Array1.Holder();
        ArrayTest.ArrayOps.makeBool(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkBool(array) == true),
              "makeBool218: (ArrayTest.ArrayOps.checkBool(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseBool(hold, false) == true),
              "makeBool218: " + 
              "(ArrayTest.ArrayOps.reverseBool(array, false) == true)");
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkBool(array) == false),
              "makeBool218: (ArrayTest.ArrayOps.checkBool(array) == false)");

        hold = new sidl.Boolean.Array1.Holder();
        ArrayTest.ArrayOps.makeBool(9, hold);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseBool(hold, false) == true),
              "makeBool9: " +
              "(ArrayTest.ArrayOps.reverseBool(array, false) == true)");
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkBool(array) == true),
              "makeBool9: (ArrayTest.ArrayOps.checkBool(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Character array tests
       */

      if (true) {
        sidl.Character.Array1 array = ArrayTest.ArrayOps.createChar(ONE_D_SIZE);
        sidl.Character.Array1.Holder hold = new sidl.Character.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createChar: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkChar(array) == true),
              "createChar: (ArrayTest.ArrayOps.checkChar(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseChar(hold, true) == true),
              "createChar: " +
              "(ArrayTest.ArrayOps.reverseChar(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.Character.Array1.Holder();
        ArrayTest.ArrayOps.makeChar(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkChar(array) == true),
              "makeChar: (ArrayTest.ArrayOps.checkChar(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseChar(hold, false) == true),
              "makeChar: " +
              "(ArrayTest.ArrayOps.reverseChar(array, false) == true)");
        array = hold.get(); 
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkChar(array) == false),
              "makeChar: (ArrayTest.ArrayOps.checkChar(array) == false)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Integer array tests
       */

      if (true) {
        sidl.Integer.Array1 array = ArrayTest.ArrayOps.createInt(ONE_D_SIZE);
        sidl.Integer.Array1.Holder hold = new sidl.Integer.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createInt: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkInt(array) == true),
              "createInt: (ArrayTest.ArrayOps.checkInt(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseInt(hold, true) == true),
              "createInt: (ArrayTest.ArrayOps.reverseInt(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.Integer.Array1.Holder();
        ArrayTest.ArrayOps.makeInt(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkInt(array) == true),
              "makeInt: (ArrayTest.ArrayOps.checkInt(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseInt(hold, false) == true),
              "makeInt: (ArrayTest.ArrayOps.reverseInt(array, false) == true)");
        array = hold.get(); 
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkInt(array) == false),
              "makeInt: (ArrayTest.ArrayOps.checkInt(array) == false)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

            
      /*
       * Long array tests
       */

      if (true) {
        sidl.Long.Array1 array = ArrayTest.ArrayOps.createLong(ONE_D_SIZE);
        sidl.Long.Array1.Holder hold = new sidl.Long.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createLong: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkLong(array) == true),
              "createLong: (ArrayTest.ArrayOps.checkLong(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseLong(hold, true) == true),
              "createLong: " +
              "(ArrayTest.ArrayOps.reverseLong(hold, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.Long.Array1.Holder();
        ArrayTest.ArrayOps.makeLong(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkLong(array) == true),
              "makeLong: (ArrayTest.ArrayOps.checkLong(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseLong(hold, false) == true),
              "makeLong: " +
              "(ArrayTest.ArrayOps.reverseLong(array, false) == true)");
        array = hold.get(); 
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkLong(array) == false),
              "makeLong: (ArrayTest.ArrayOps.checkLong(array) == false)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * String array tests
       */

      if (true) {
        sidl.String.Array1 array = ArrayTest.ArrayOps.createString(ONE_D_SIZE);
        sidl.String.Array1.Holder hold = new sidl.String.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createString: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkString(array) == true),
              "createString: (ArrayTest.ArrayOps.checkString(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseString(hold, true) == true),
              "createString: " + 
              "(ArrayTest.ArrayOps.reverseString(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.String.Array1.Holder();
        ArrayTest.ArrayOps.makeString(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkString(array) == true),
              "makeString: (ArrayTest.ArrayOps.checkString(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseString(hold, false) == true),
              "makeString: " +
              "(ArrayTest.ArrayOps.reverseString(array, false) == true)");
        array = hold.get(); 
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkString(array) == false),
              "makeString: (ArrayTest.ArrayOps.checkString(array) == false)");
     
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Double array tests
       */

      if (true) {
        sidl.Double.Array1 array = ArrayTest.ArrayOps.createDouble(ONE_D_SIZE);
        sidl.Double.Array1.Holder hold = new sidl.Double.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createDouble: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDouble(array) == true),
              "createDouble: (ArrayTest.ArrayOps.checkDouble(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseDouble(hold, true) == true),
              "createDouble: " +
              "(ArrayTest.ArrayOps.reverseDouble(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.Double.Array1.Holder();
        ArrayTest.ArrayOps.makeDouble(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDouble(array) == true),
              "makeDouble: (ArrayTest.ArrayOps.checkDouble(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseDouble(hold, false) == true),
              "makeDouble: " + 
              "(ArrayTest.ArrayOps.reverseDouble(array, false) == true)");
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDouble(array) == false),
              "makeDouble: (ArrayTest.ArrayOps.checkDouble(array) == false)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Float array tests
       */

      if (true) {
        sidl.Float.Array1 array = ArrayTest.ArrayOps.createFloat(ONE_D_SIZE);
        sidl.Float.Array1.Holder hold = new sidl.Float.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createFloat: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFloat(array) == true),
              "createFloat: (ArrayTest.ArrayOps.checkFloat(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseFloat(hold, true) == true),
              "createFloat: " +
              "(ArrayTest.ArrayOps.reverseFloat(array, true) == true)");

        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.Float.Array1.Holder();
        ArrayTest.ArrayOps.makeFloat(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFloat(array) == true),
              "makeFloat: (ArrayTest.ArrayOps.checkFloat(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseFloat(hold, false) == true),
              "makeFloat: " +
              "(ArrayTest.ArrayOps.reverseFloat(array, false) == true)");
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFloat(array) == false),
              "makeFloat: (ArrayTest.ArrayOps.checkFloat(array) == false)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * FloatComplex array tests
       */

      if (true) {
        sidl.FloatComplex.Array1 array =
          ArrayTest.ArrayOps.createFcomplex(ONE_D_SIZE);
        sidl.FloatComplex.Array1.Holder hold = 
          new sidl.FloatComplex.Array1.Holder();
       
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createFcomplex: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFcomplex(array) == true),
              "createFcomplex: " +
              "(ArrayTest.ArrayOps.checkFcomplex(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseFcomplex(hold, true) == true),
              "createFcomplex: " +
              "(ArrayTest.ArrayOps.reverseFcomplex(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.FloatComplex.Array1.Holder();
        ArrayTest.ArrayOps.makeFcomplex(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFcomplex(array) == true),
              "makeFcomplex: (ArrayTest.ArrayOps.checkFcomplex(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseFcomplex(hold, false) == true),
              "makeFcomplex: " +
              "(ArrayTest.ArrayOps.reverseFcomplex(array, false) == true)");
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFcomplex(array) == false),
              "makeFcomplex: " +
              "(ArrayTest.ArrayOps.checkFcomplex(array) == false)");
   
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * DoubleComplex array tests
       */

      if (true) {
        sidl.DoubleComplex.Array1 array =
          ArrayTest.ArrayOps.createDcomplex(ONE_D_SIZE);
        sidl.DoubleComplex.Array1.Holder hold = 
          new sidl.DoubleComplex.Array1.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createDcomplex: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDcomplex(array) == true),
              "createDcomplex: " +
              "(ArrayTest.ArrayOps.checkDcomplex(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseDcomplex(hold, true) == true),
              "createDcomplex: " +
              "(ArrayTest.ArrayOps.reverseDcomplex(array, true) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == ONE_D_SIZE), "ARRAY LENGTH CORRECTLY RETRIEVED");

        hold = new sidl.DoubleComplex.Array1.Holder();
        ArrayTest.ArrayOps.makeDcomplex(218, hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDcomplex(array) == true),
              "makeDcomplex: (ArrayTest.ArrayOps.checkDcomplex(array) == true)");
        hold.set(array);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.reverseDcomplex(hold, false) == true),
              "makeDcomplex: " +
              "(ArrayTest.ArrayOps.reverseDcomplex(array, false) == true)");
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDcomplex(array) == false),
              "makeDcomplex: " +
              "(ArrayTest.ArrayOps.checkDcomplex(array) == false)");
     
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Object array tests
       */

      if (true) {
        ArrayTest.ArrayOps.Array1 array = (ArrayTest.ArrayOps.Array1)ArrayTest.ArrayOps.createObject(10);
        startTest(null);
        check(synch.ResultType.PASS, (array != null), "The Array of Objects is not NULL");
        startTest(null);
        check(synch.ResultType.PASS, (ArrayTest.ArrayOps.checkObject(array) == 10), "Check an array of objects");
        startTest(null);
        check(synch.ResultType.PASS, (array.length() == 10), "Correctly retrived length");
        array.set(5, new ArrayTest.ArrayOps());
        ArrayTest.ArrayOps received = null;
        received = array.get(5);
        ArrayTest.ArrayOps foo = array.get(4);
        startTest(null);
        check(synch.ResultType.PASS, (received != null), "Successfully get an object from an Array");
       

        /*
         *  Test Object sliced and copied arrays
         */

        int[] numElem = {10};
        int[] stride = {1};
        ArrayTest.ArrayOps.Array sliced = array._slice(1, numElem, (int[]) null,
                                                       (int[]) stride, (int[]) null);
        ArrayTest.ArrayOps.Array1 sliced1 = (ArrayTest.ArrayOps.Array1) sliced._dcast();
        startTest(null);
        check(synch.ResultType.PASS, (sliced != null), "The Sliced Array is not NULL");
        startTest(null);
        check(synch.ResultType.PASS, (sliced.get_ior_pointer() == sliced1.get_ior_pointer()), "The cast array is equal to the non cast array"); 
	  
        startTest(null);
        check(synch.ResultType.PASS, (sliced1._get(0) != null), "Sliced array element 0 is not null");
        sidl.BaseClass testBase = (sidl.BaseClass) sidl.BaseClass._cast(received);
        ArrayTest.ArrayOps testA = (ArrayTest.ArrayOps) ArrayTest.ArrayOps._cast(testBase);
        startTest(null);
        check(synch.ResultType.PASS, (testBase != null), "Element cast to baseClass is not null");
        startTest(null);
        check(synch.ResultType.PASS, (testBase._get_ior() == testA._get_ior()), "Pre cast and post cast elements are equal");  

        array = null; sliced = null; sliced1 = null; 
        received = null; testA = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check two dimensional array for integer
       */

      if (true) {
        sidl.Integer.Array2 array = ArrayTest.ArrayOps.create2Int(TEST_DIM1,
                                                                  TEST_DIM2);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Int(array) == true),
              "create2Int: " + 
              "(ArrayTest.ArrayOps.check2Int(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length(0) == TEST_DIM1), "1st Dimension has the correct length");
        startTest(null);
        check(synch.ResultType.PASS, (array.length(1) == TEST_DIM2), "2nd Dimension has the correct length");
        
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check two dimensional array for double
       */

      if (true) {
        sidl.Double.Array2 array = ArrayTest.ArrayOps.create2Double(TEST_DIM1,
                                                                    TEST_DIM2);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Double(array) == true),
              "create2Double: " + 
              "(ArrayTest.ArrayOps.check2Double(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS, (array.length(0) == TEST_DIM1), "1st Dimension has the correct length");
        startTest(null);
        check(synch.ResultType.PASS, (array.length(1) == TEST_DIM2), "2nd Dimension has the correct length");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check two dimensional array for float
       */

      if (true) {
        sidl.Float.Array2 array = ArrayTest.ArrayOps.create2Float(TEST_DIM1,
                                                                  TEST_DIM2);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Float(array) == true),
              "create2Float: (ArrayTest.ArrayOps.check2Float(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check two dimensional array for double complex
       */

      if (true) {
        sidl.DoubleComplex.Array2 array = ArrayTest.ArrayOps.create2Dcomplex(
                                                                             TEST_DIM1, TEST_DIM2);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Dcomplex(array) == true),
              "create2Dcomplex:" + 
              "(ArrayTest.ArrayOps.check2Dcomplex(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check two dimensional array for float complex
       */

      if (true) {
        sidl.FloatComplex.Array2 array = ArrayTest.ArrayOps.create2Fcomplex(
                                                                            TEST_DIM1, TEST_DIM2);
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Fcomplex(array) == true),
              "create2Fcomplex: " +
              "(ArrayTest.ArrayOps.check2Fcomplex(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check three dimensional array for integer
       */

      if (true) {
        sidl.Integer.Array3 array = ArrayTest.ArrayOps.create3Int();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check3Int(array) == true),
              "create3Int: " +
              "(ArrayTest.ArrayOps.check3Int(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check four dimensional array for integer
       */

      if (true) {
        sidl.Integer.Array4 array = ArrayTest.ArrayOps.create4Int();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check4Int(array) == true),
              "create4Int: " +
              "(ArrayTest.ArrayOps.check4Int(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check five dimensional array for integer
       */

      if (true) {
        sidl.Integer.Array5 array = ArrayTest.ArrayOps.create5Int();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check5Int(array) == true),
              "create5Int: " +
              "(ArrayTest.ArrayOps.check5Int(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check six dimensional array for integer
       */

      if (true) {
        sidl.Integer.Array6 array = ArrayTest.ArrayOps.create6Int();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check6Int(array) == true),
              "create6Int: " +
              "(ArrayTest.ArrayOps.check6Int(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * Create and check seven dimensional array for integer
       */

      if (true) {
        sidl.Integer.Array7 array = ArrayTest.ArrayOps.create7Int();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check7Int(array) == true),
              "create7Int: " +
              "(ArrayTest.ArrayOps.check7Int(array) == true)");
        array = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }



      /*
       * inout null Boolean array test
       */
      
      if (true) {
        /*  check(XFAIL, false, "makeInOutBool using null array");
         */
        sidl.Boolean.Array1 array = new sidl.Boolean.Array1();
        sidl.Boolean.Array1.Holder hold = new sidl.Boolean.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutBool(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS, 
              (ArrayTest.ArrayOps.checkBool(array) == true),
              "makeInOutBool: (ArrayTest.ArrayOps.checkBool(array) == true)"); 
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null Character array test
       */
      
      if (true) {	
        //  check(XFAIL, false, "makeInOutChar using null array");
	  
        sidl.Character.Array1 array = new sidl.Character.Array1();
        sidl.Character.Array1.Holder hold = new sidl.Character.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutChar(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkChar(array) == true),
              "makeInOutChar: (ArrayTest.ArrayOps.checkChar(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null Integer array test
       */

      if (true) {	
        //        check(XFAIL, false, "makeInOutInt using null array");

        sidl.Integer.Array1 array = new sidl.Integer.Array1();
        sidl.Integer.Array1.Holder hold = new sidl.Integer.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutInt(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkInt(array) == true),
              "makeInOutInt: (ArrayTest.ArrayOps.checkInt(array) == true)");
	
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null Long array test
       */

      if (true) {
        //        check(XFAIL, false, "makeInOutLong using null array");
      
        sidl.Long.Array1 array = new sidl.Long.Array1();
        sidl.Long.Array1.Holder hold = new sidl.Long.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutLong(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkLong(array) == true),
              "makeInOutLong: (ArrayTest.ArrayOps.checkLong(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null String array test
       */

      if (true) {	
        //     check(XFAIL, false, "makeInOutString using null array");

        sidl.String.Array1 array = new sidl.String.Array1();
        sidl.String.Array1.Holder hold = new sidl.String.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutString(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkString(array) == true),
              "makeInOutString: " + 
              "(ArrayTest.ArrayOps.checkString(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null Double array test
       */

      if (true) {
        //       check(XFAIL, false, "makeInOutDouble using null array");

        sidl.Double.Array1 array = new sidl.Double.Array1();
        sidl.Double.Array1.Holder hold = new sidl.Double.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutDouble(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDouble(array) == true),
              "makeInOutDouble: " +
              "(ArrayTest.ArrayOps.checkDouble(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null Float array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOutFloat using null array");

        sidl.Float.Array1 array = new sidl.Float.Array1();
        sidl.Float.Array1.Holder hold = new sidl.Float.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutFloat(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFloat(array) == true),
              "makeInOutFloat: (ArrayTest.ArrayOps.checkFloat(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null FloatComplex array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOutFloatComplex using null array");

        sidl.FloatComplex.Array1 array = new sidl.FloatComplex.Array1();
        sidl.FloatComplex.Array1.Holder hold = new sidl.FloatComplex.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutFcomplex(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkFcomplex(array) == true),
              "makeInOutFcomplex: " +
              "(ArrayTest.ArrayOps.checkFcomplex(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null DoubleComplex array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOutDoubleComplex using null array");

        sidl.DoubleComplex.Array1 array = new sidl.DoubleComplex.Array1();
        sidl.DoubleComplex.Array1.Holder hold = new sidl.DoubleComplex.Array1.Holder();
        ArrayTest.ArrayOps.makeInOutDcomplex(hold, 218);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDcomplex(array) == true),
              "makeInOutDcomplex: " +
              "(ArrayTest.ArrayOps.checkDcomplex(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 2D integer array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut2Int using null array");

        sidl.Integer.Array2 array = new sidl.Integer.Array2();
        sidl.Integer.Array2.Holder hold = new sidl.Integer.Array2.Holder();
        ArrayTest.ArrayOps.makeInOut2Int(hold, TEST_DIM1, TEST_DIM2);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Int(array) == true),
              "makeInOut2Int: " +
              "(ArrayTest.ArrayOps.check2Int(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 2D double array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut2Double using null array");

        sidl.Double.Array2 array = new sidl.Double.Array2();
        sidl.Double.Array2.Holder hold = new sidl.Double.Array2.Holder();
        ArrayTest.ArrayOps.makeInOut2Double(hold, TEST_DIM1, TEST_DIM2);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Double(array) == true),
              "makeInOut2Double: " +
              "(ArrayTest.ArrayOps.check2Double(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 2D float array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut2Float using null array");

        sidl.Float.Array2 array = new sidl.Float.Array2();
        sidl.Float.Array2.Holder hold = new sidl.Float.Array2.Holder();
        ArrayTest.ArrayOps.makeInOut2Float(hold, TEST_DIM1, TEST_DIM2);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Float(array) == true),
              "makeInOut2Float: " +
              "(ArrayTest.ArrayOps.check2Float(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 2D double complex array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut2Dcomplex using null array");

        sidl.DoubleComplex.Array2 array = new sidl.DoubleComplex.Array2();
        sidl.DoubleComplex.Array2.Holder hold = new sidl.DoubleComplex.Array2.Holder();
        ArrayTest.ArrayOps.makeInOut2Dcomplex(hold, TEST_DIM1, TEST_DIM2);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Dcomplex(array) == true),
              "makeInOut2Dcomplex: " +
              "(ArrayTest.ArrayOps.check2Dcomplex(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 2D float complex array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut2Fcomplex using null array");

        sidl.FloatComplex.Array2 array = new sidl.FloatComplex.Array2();
        sidl.FloatComplex.Array2.Holder hold = new sidl.FloatComplex.Array2.Holder();
        ArrayTest.ArrayOps.makeInOut2Fcomplex(hold, TEST_DIM1, TEST_DIM2);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2Fcomplex(array) == true),
              "makeInOut2Fcomplex: " +
              "(ArrayTest.ArrayOps.check2Fcomplex(array) == true)");

        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 3D integer array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut3Int using null array");

        sidl.Integer.Array3 array = new sidl.Integer.Array3();
        sidl.Integer.Array3.Holder hold = new sidl.Integer.Array3.Holder();
        ArrayTest.ArrayOps.makeInOut3Int(hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check3Int(array) == true),
              "makeInOut3Int: " +
              "(ArrayTest.ArrayOps.check3Int(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 4D integer array test
       */

      if (true) {
        //check(XFAIL, false, "makeInOut4Int using null array");

        sidl.Integer.Array4 array = new sidl.Integer.Array4();
        sidl.Integer.Array4.Holder hold = new sidl.Integer.Array4.Holder();
        ArrayTest.ArrayOps.makeInOut4Int(hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check4Int(array) == true),
              "makeInOut4Int: " +
              "(ArrayTest.ArrayOps.check4Int(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 5D integer array test
       */

      if (true) {

        sidl.Integer.Array5 array = new sidl.Integer.Array5();
        sidl.Integer.Array5.Holder hold = new sidl.Integer.Array5.Holder();
        ArrayTest.ArrayOps.makeInOut5Int(hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check5Int(array) == true),
              "makeInOut5Int: " +
              "(ArrayTest.ArrayOps.check5Int(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }
      /*
       * inout null 6D integer array test
       */

      if (true) {

        sidl.Integer.Array6 array = new sidl.Integer.Array6();
        sidl.Integer.Array6.Holder hold = new sidl.Integer.Array6.Holder();
        ArrayTest.ArrayOps.makeInOut6Int(hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check6Int(array) == true),
              "makeInOut6Int: " +
              "(ArrayTest.ArrayOps.check6Int(array) == true)");
	  
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      /*
       * inout null 7D integer array test
       */

      if (true) {

        sidl.Integer.Array7 array = new sidl.Integer.Array7();
        sidl.Integer.Array7.Holder hold = new sidl.Integer.Array7.Holder();
        ArrayTest.ArrayOps.makeInOut7Int(hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check7Int(array) == true),
              "makeInOut7Int: " +
              "(ArrayTest.ArrayOps.check7Int(array) == true)");
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      if (true) {
        int dimen = 0;
        int type = 0;
        int dimen2 = 0;
        int type2 = 0;
        sidl.Integer.Holder holdDim = new sidl.Integer.Holder();
        sidl.Integer.Holder holdTp = new sidl.Integer.Holder();
        gov.llnl.sidl.BaseArray garray = null;
        gov.llnl.sidl.BaseArray garrayret = null;
        gov.llnl.sidl.BaseArray garrayout = null;
        gov.llnl.sidl.BaseArray garrayinout = null;

        gov.llnl.sidl.BaseArray.Holder holdOut = new gov.llnl.sidl.BaseArray.Holder();
        gov.llnl.sidl.BaseArray.Holder holdInout = new gov.llnl.sidl.BaseArray.Holder();
        
        ArrayTest.ArrayOps.checkGeneric(garray, holdDim, holdTp);
        startTest(null);
        check(synch.ResultType.PASS, garray == null, "Generic array is still Null" );
        startTest(null);
        check(synch.ResultType.PASS, (holdDim.get() == 0), "NULL Generic array has no dimension");
        startTest(null);
        check(synch.ResultType.PASS, (holdTp.get() == 0), "NULL Generic array has no type");

        dimen = 1;
        type = 7;
        garray = ArrayTest.ArrayOps.createGeneric(dimen, type);
        startTest(null);
        check(synch.ResultType.PASS, garray != null, "Created Generic array is not Null" );
        startTest(null);
        check(synch.ResultType.PASS, (garray._dim() == dimen), "Generic array has correct dimension");
        startTest(null);
        check(synch.ResultType.PASS, (garray._type() == type), "Generic array has correct type");

        
        ArrayTest.ArrayOps.checkGeneric(garray, holdDim, holdTp);
        startTest(null);
        check(synch.ResultType.PASS, (holdDim.get() == dimen), "checkGeneric array has correct dimension");
        startTest(null);
        check(synch.ResultType.PASS, (holdTp.get() == type), "checkGeneric array has correct type");

        
        garrayret = ArrayTest.ArrayOps.passGeneric(garray, holdInout,
                                                   holdOut);
        
        garrayinout = holdInout.get();
        garrayout = holdOut.get();
        startTest(null);
        check(synch.ResultType.PASS, garrayret != null, "Returned Generic array is not Null" );
        startTest(null);
        check(synch.ResultType.PASS, (garrayret._dim() == dimen), "Returned  array has correct dimension");
        startTest(null);
        check(synch.ResultType.PASS, (garrayret._type() == type), "Returned array has correct type");

        startTest(null);
        check(synch.ResultType.PASS, garrayout != null, "Out Generic array is not Null" );
        startTest(null);
        check(synch.ResultType.PASS, (garrayout._dim() == dimen), "Out Generic array has correct dimension");
        startTest(null);
        check(synch.ResultType.PASS, (garrayout._type() == type), "Out Generic array has correct type");

        sidl.Integer.Array iarray = (sidl.Integer.Array) garrayinout;
        sidl.Integer.Array2 iarray2 = (sidl.Integer.Array2) iarray._dcast();
        startTest(null);
        check(synch.ResultType.PASS, ArrayTest.ArrayOps.check2Int(iarray2) == true, 
              "Generic inout is correct" );
        holdDim = null; holdTp = null; holdOut = null; holdInout = null;
        garray = null; garrayret = null; garrayout = null; 
        garrayinout = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      
      /*
       * Integer array tests
       */
      
      if (true) {
        sidl.Integer.Array1 array = ArrayTest.ArrayOps.createInt(ONE_D_SIZE);
        sidl.Integer.Array1.Holder hold = new sidl.Integer.Array1.Holder();
        int[] lower = {0};
        int[] upper = {99};
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "rarrayInt: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkInt(array) == true),
              "rarrayInt: (ArrayTest.ArrayOps.checkInt(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray1Int(array) == true),
              "rarrayInt: (ArrayTest.ArrayOps.checkRarray1Int(array) == true)");

        array = null;

        array = new sidl.Integer.Array1(99, false);

        hold.set(array);

        ArrayTest.ArrayOps.initRarray1Int(hold);

        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray1Int(array) == true),
              "rarrayInt: (ArrayTest.ArrayOps.checkRarray1Int(array) == true)");
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      if (true) {
        sidl.Integer.Array3 array = ArrayTest.ArrayOps.create3Int();
        sidl.Integer.Array3.Holder hold = new sidl.Integer.Array3.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "rarray3Int: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check3Int(array) == true),
              "rarray3Int: (ArrayTest.ArrayOps.check3Int(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray3Int(array) == true),
              "rarray3Int: (ArrayTest.ArrayOps.checkRarray3Int(array) == true)");
        array = null;
        
        array = (sidl.Integer.Array3) new sidl.Integer.Array3(9,8,7, false);
        hold.set(array);
        startTest(null);
        ArrayTest.ArrayOps.initRarray3Int(hold);
        array = hold.get();
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray3Int(array) == true),
              "rarray3Int: (ArrayTest.ArrayOps.checkRarray3Int(array) == true)");
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      if (true) {
        sidl.Integer.Array7 array = ArrayTest.ArrayOps.create7Int();
        sidl.Integer.Array7.Holder hold = new sidl.Integer.Array7.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "rarray7Int: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check7Int(array) == true),
              "rarray7Int: (ArrayTest.ArrayOps.check7Int(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray7Int(array) == true),
              "rarray7Int: (ArrayTest.ArrayOps.checkRarray7Int(array) == true)");
        array = null;
        startTest(null);
        array = (sidl.Integer.Array7) new sidl.Integer.Array7(3,3,3,3,2,2,2, false);
        hold.set(array);
        ArrayTest.ArrayOps.initRarray7Int(hold);
        array = hold.get();
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray7Int(array) == true),
              "rarray7Int: (ArrayTest.ArrayOps.checkRarray7Int(array) == true)");
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      if (true) {
        sidl.Double.Array1 array = ArrayTest.ArrayOps.createDouble(ONE_D_SIZE);
        sidl.Double.Array1.Holder hold = new sidl.Double.Array1.Holder();

        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createDouble: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDouble(array) == true),
              "createDouble: (ArrayTest.ArrayOps.checkDouble(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray1Double(array) == true),
              "rarray1Double: (ArrayTest.ArrayOps.checkRarray1Double(array) == true)");
        array = null;
        
        array = (sidl.Double.Array1) new sidl.Double.Array1(99, false);
        hold.set(array);
        startTest(null);
        ArrayTest.ArrayOps.initRarray1Double(hold);
        array = hold.get();
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray1Double(array) == true),
              "rarray1Double: (ArrayTest.ArrayOps.checkRarray1Double(array) == true)");
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }

      if (true) {
        sidl.DoubleComplex.Array1 array = ArrayTest.ArrayOps.createDcomplex(ONE_D_SIZE);
        sidl.DoubleComplex.Array1.Holder hold = new sidl.DoubleComplex.Array1.Holder();

        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "createDcomplex: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkDcomplex(array) == true),
              "createDcomplex: (ArrayTest.ArrayOps.checkDcomplex(array) == true)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray1Dcomplex(array) == true),
              "rarray1Dcomplex: (ArrayTest.ArrayOps.checkRarray1Dcomplex(array) == true)");
        array = null;
        
        array = (sidl.DoubleComplex.Array1) new sidl.DoubleComplex.Array1(99, false);
        hold.set(array);
        ArrayTest.ArrayOps.initRarray1Dcomplex(hold);
        array = hold.get();
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkRarray1Dcomplex(array) == true),
              "rarray1Dcomplex: (ArrayTest.ArrayOps.checkRarray1Dcomplex(array) == true)");
        array = null; hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }
      
      if (true) {
        sidl.Integer.Array2 a = (sidl.Integer.Array2) new sidl.Integer.Array2(3, 3, false);
        sidl.Integer.Array2 b = (sidl.Integer.Array2) new sidl.Integer.Array2(3, 2, false);
        sidl.Integer.Array2 x = (sidl.Integer.Array2) new sidl.Integer.Array2(3, 2, false);
        sidl.Integer.Array2.Holder h = new sidl.Integer.Array2.Holder();
        h.set(x);
        for(int i = 0; i < 3; ++i) {
          for(int j = 0; j < 3; ++j) {
            a.set(i,j,i+j);
          }
        }
        for(int i = 0; i < 3; ++i) {
          for(int j = 0; j < 2; ++j) {
            b.set(i,j,i+j);
          }
        }
        
        ArrayTest.ArrayOps.matrixMultiply(a,b,h);
        x = h.get();
        startTest(null);

        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.checkMatrixMultiply(a,b,x) == true),
              "checkMatrixMultiply: (ArrayTest.ArrayOps.checkMatrixMultiply(a,b,x) == true)");

        a = null; b = null; x = null; h = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }
      

      /*
       * 2D String array tests
       */

      if (true) {
        sidl.String.Array2 array = ArrayTest.ArrayOps.create2String(12,15);
        sidl.String.Array2.Holder hold = new sidl.String.Array2.Holder();
        startTest(null);
        check(synch.ResultType.PASS,
              (array != null),
              "create2String: (array != null)");
        startTest(null);
        check(synch.ResultType.PASS,
              (ArrayTest.ArrayOps.check2String(array) == true),
              "create2String: (ArrayTest.ArrayOps.checkString(array) == true)");
        array = null;
        hold = null;
        System.gc(); Thread.sleep(5); // try to force garbage collector to run
      }
  }


  /**
   * The main test driver takes no command-line arguments and runs the
   * regression tests.
   */
  public static void main(String args[]) {
    try {

      /*
       * Begin the test
       */
      tracker = new synch.RegOut();
      tracker.setExpectations(145);
      
      s_part   = 0;
      s_result = synch.ResultType.PASS;

      tests(tracker);
      System.gc(); Thread.sleep(5); // try to force garbage collector to run

      // Output final test results
      tracker.close();
      tracker = null;

      // try to force garbage collector to run
      System.gc(); Thread.sleep(50);
      System.gc(); Thread.sleep(50);
      System.gc(); Thread.sleep(50);

      Runtime.getRuntime().exit(0); // workaround for Linux JVM 1.3.1 bug

    } catch (Throwable ex) {
      // Catch any unexpected exceptions and return a test failure
      if (tracker != null) {
        tracker.forceFailure();
        tracker.close();
      }	else {
        System.out.println("TEST_RESULT FAIL");
      }
      ex.printStackTrace();
    }
  }
}
