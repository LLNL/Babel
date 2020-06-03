// 
// File:        StructTest.java
// Copyright:   (c) 2009 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 6707 $
// Date:        $Date: 2009-09-26 20:53:56 -0700 (Wed, 09 Aug 2009) $
// Description: argument regression test case for Java calling other languages
// 

/**
 * The following class runs the struct regression test cases for Java.
 */
public class StructTest {
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

  private static int s_part = 0;
  private static long s_result =  synch.ResultType.PASS;
  private static synch.RegOut tracker = null; 
  private static final double eps = 1.E-6;

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

  //create a return a local or remote object, depending
  //on command-line arguments
  private static String remoteURL = null;
  
  private static s.StructTest makeObject() {
    s.StructTest ret = null;
    try {
      if(!withRMI())
        ret = new s.StructTest();
      else
        ret = new s.StructTest(remoteURL);
      
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return ret;
  }

  private static boolean withRMI() {
     return remoteURL != null && remoteURL.length() > 0;
  }

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
    initSimple(c.d_simple);
    initHard(c.d_hard);
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
      result &= h.d_objectArray.get(2) != null &&
        h.d_objectArray.get(2).isType("sidl.BaseClass");
    }
    return result;
  }

  private static boolean checkCombined(s.Combined c) {
    return checkSimple(c.d_simple) 
      && checkHard(c.d_hard);
  }

  private static boolean checkCombinedInv(s.Combined c) {
    return checkSimpleInv(c.d_simple)
      && checkHardInv(c.d_hard);
  }

  private static void initRarrays(s.Rarrays r) {
    r.d_int = 3;
    r.d_rarrayRaw = new sidl.Double.Array1(r.d_int, false);
    r.d_rarrayFix = new sidl.Double.Array1(r.d_int, false);
    for(int i=0; i < r.d_int; ++i) {
      r.d_rarrayRaw.set(i, (double)(i+1));
      r.d_rarrayFix.set(i, (double)((i+1)*5));
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
  
  private static void tests(synch.RegOut tracker)
  {
    /*
     * "Empty" Tests
     */
    if (true) {
      s.StructTest test = makeObject();

      s.Empty e1 = new s.Empty();
      s.Empty e2 = new s.Empty();
      s.Empty e3 = new s.Empty();
      s.Empty e4 = new s.Empty();
      
      s.Empty.Holder _e1 = new s.Empty.Holder(e1);
      s.Empty.Holder _e2 = new s.Empty.Holder(e2);
      s.Empty.Holder _e3 = new s.Empty.Holder(e3);

      startTest(null);
      check(synch.ResultType.PASS, (e1 = test.returnEmpty()) != null, "test.returnEmpty()");
      startTest(null);
      check(synch.ResultType.PASS, test.passinEmpty(e1), "test.passinEmpty(e1)");
      startTest(null);
      check(synch.ResultType.PASS, test.passoutEmpty(_e1), "test.passoutEmpty(e1)");
      startTest(null);
      check(synch.ResultType.PASS, test.passoutEmpty(_e2), "test.passoutEmpty(e2)");
      startTest(null);
      check(synch.ResultType.PASS, test.passinoutEmpty(_e2), "test.passinoutEmpty(e2)");
      startTest(null);
      check(synch.ResultType.PASS, test.passoutEmpty(_e3), "test.passoutEmpty(e3)");
      e4 = test.passeverywhereEmpty(e1, _e2, _e3);
      
      test = null;
      System.gc(); // try to force garbage collector to run
    }
  
    /*
     * "Simple" Tests
     */
    if (true) {
      s.StructTest test = makeObject();

      s.Simple s1 = new s.Simple();
      s.Simple s2 = new s.Simple();
      s.Simple s3 = new s.Simple();
      s.Simple s4 = new s.Simple();

      s.Simple.Holder _s1 = new s.Simple.Holder(s1);
      s.Simple.Holder _s2 = new s.Simple.Holder(s2);
      s.Simple.Holder _s3 = new s.Simple.Holder(s3);

      startTest(null);
      s1 = test.returnSimple();
      check(synch.ResultType.PASS, checkSimple(s1), "checkSimple(s1)");
      startTest(null);
      check(synch.ResultType.PASS, test.passinSimple(s1), "test.passinSimple(s1)");
      startTest(null);
      check(synch.ResultType.PASS, test.passoutSimple(_s1), "test.passoutSimple(s1)");
      startTest(null);
      check(synch.ResultType.PASS, test.passoutSimple(_s2), "test.passoutSimple(s2)");
      startTest(null);
      check(synch.ResultType.PASS, test.passinoutSimple(_s2), "test.passinoutSimple(s2)");
      startTest(null);
      check(synch.ResultType.PASS, checkSimpleInv(_s2.get()), "checkSimpleInv(s2)");      
      startTest(null);
      check(synch.ResultType.PASS, test.passoutSimple(_s3), "test.passoutSimple(s3)");      
      startTest(null);
      s4 = test.passeverywhereSimple(s1, _s2, _s3);
      check(synch.ResultType.PASS, checkSimple(s4), "checkSimple(s4)");
      
      test = null;
      System.gc(); // try to force garbage collector to run
    }

    if(!withRMI()) {
    
      /*
       * "Hard" Tests
       */
      if (true) {
        s.StructTest test = makeObject();

        s.Hard h1 = new s.Hard();
        s.Hard h2 = new s.Hard();
        s.Hard h3 = new s.Hard();
        s.Hard h4 = new s.Hard();

        s.Hard.Holder _h1 = new s.Hard.Holder(h1);
        s.Hard.Holder _h2 = new s.Hard.Holder(h2);
        s.Hard.Holder _h3 = new s.Hard.Holder(h3);

        startTest(null);
        h1 = test.returnHard();
        check(synch.ResultType.PASS, checkHard(h1), "checkHard(h1)");
        startTest(null);
        check(synch.ResultType.PASS, test.passinHard(h1), "test.passinHard(h1)");

      
        startTest(null);
        check(synch.ResultType.PASS, test.passoutHard(_h1), "test.passoutHard(h1)");
        startTest(null);
        check(synch.ResultType.PASS, test.passoutHard(_h2), "test.passoutHard(h2)");
        startTest(null);
        check(synch.ResultType.PASS, test.passinoutHard(_h2), "test.passinoutHard(h2)");
        startTest(null);
        check(synch.ResultType.PASS, checkHardInv(_h2.get()), "checkHardInv(h2)");      
        startTest(null);
        check(synch.ResultType.PASS, test.passoutHard(_h3), "test.passoutHard(h3)");      
        startTest(null);
        h4 = test.passeverywhereHard(h1, _h2, _h3);
        check(synch.ResultType.PASS, checkHard(h4), "checkHard(h4)");

        test = null;
        System.gc(); // try to force garbage collector to run
      }

      /*
       * "Combined" Tests
       */
      if (true) {
        s.StructTest test = makeObject();

        s.Combined c1 = new s.Combined();
        s.Combined c2 = new s.Combined();
        s.Combined c3 = new s.Combined();
        s.Combined c4 = new s.Combined();

        s.Combined.Holder _c1 = new s.Combined.Holder(c1);
        s.Combined.Holder _c2 = new s.Combined.Holder(c2);
        s.Combined.Holder _c3 = new s.Combined.Holder(c3);

        startTest(null);
        c1 = test.returnCombined();
        check(synch.ResultType.PASS, checkCombined(c1), "checkCombined(c1)");
        startTest(null);
        check(synch.ResultType.PASS, test.passinCombined(c1), "test.passinCombined(c1)");
        startTest(null);
        check(synch.ResultType.PASS, test.passoutCombined(_c1), "test.passoutCombined(c1)");
        startTest(null);
        check(synch.ResultType.PASS, test.passoutCombined(_c2), "test.passoutCombined(c2)");
        startTest(null);
        check(synch.ResultType.PASS, test.passinoutCombined(_c2), "test.passinoutCombined(c2)");
        startTest(null);
        check(synch.ResultType.PASS, checkCombinedInv(_c2.get()), "checkCombinedInv(c2)");      
        startTest(null);
        check(synch.ResultType.PASS, test.passoutCombined(_c3), "test.passoutCombined(c3)");      
        startTest(null);
        c4 = test.passeverywhereCombined(c1, _c2, _c3);
        check(synch.ResultType.PASS, checkCombined(c4), "checkCombined(c4)");
      
        test = null;
        System.gc(); // try to force garbage collector to run
      }

      /*
       * "Rarrays" Tests
       */
      if (true) {
        s.StructTest test = new s.StructTest();

        s.Rarrays r1 = new s.Rarrays();
        s.Rarrays r2 = new s.Rarrays();
        s.Rarrays r3 = new s.Rarrays();
        
        s.Rarrays.Holder _r1 = new s.Rarrays.Holder(r1);
        s.Rarrays.Holder _r2 = new s.Rarrays.Holder(r2);
        s.Rarrays.Holder _r3 = new s.Rarrays.Holder(r3);

        startTest(null);
        initRarrays(r1);
        check(synch.ResultType.PASS, test.passinRarrays(r1), "test.passinRarrays(r1)");
        startTest(null);
        check(synch.ResultType.PASS, test.passinoutRarrays(_r1), "test.passinoutRarrays(_r1)");
        startTest(null);
        check(synch.ResultType.PASS, checkRarraysInv(_r1.get()), "checkRarraysInv(_r1)");
        startTest(null);
        initRarrays(r2);
        initRarrays(r3);
        test.passeverywhereRarrays(r2, _r3);
        check(synch.ResultType.PASS, checkRarrays(r2) && checkRarraysInv(_r3.get()),
              "checkRarrays(r2) && checkRarraysInv(_r3.get)");
        test = null;
        System.gc(); // try to force garbage collector to run
      }
    } // !withRMI
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
      tracker.setExpectations(-1);
      s_part   = 0;
      s_result = synch.ResultType.PASS;

      /*
       * Parse the command line  to see if we are running RMI tests
       */
      for(int _arg = 0; _arg < args.length; ++_arg) {
        if(args[_arg].startsWith("--url=")) {
          sidl.String.Holder _tmp = new sidl.String.Holder(args[_arg].substring(6));
          tracker.replaceMagicVars(_tmp, ""); /* we always look at $SIDL_DLL_PATH for Java*/
          remoteURL = _tmp.get();
        }
      }
      
      if(withRMI()) {
        System.out.println("using remote URL " + remoteURL);
      }
      
      /*
       * Setup RMI if necessary
       */
      if(withRMI()) {
        try {
          System.out.println("registering RMI protocol simhandle");
          sidl.rmi.ProtocolFactory.addProtocol("simhandle","sidlx.rmi.SimHandle");
        } catch (Throwable ex) {
          ex.printStackTrace();
          System.exit(1);
        }
      }
      
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
