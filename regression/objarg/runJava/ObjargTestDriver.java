// 
// File:        ObjargTestDriver.java
// Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7003 $
// Date:        $Date: 2010-11-18 17:01:04 -0800 (Thu, 18 Nov 2010) $
// Description: Objects as arguments regression test cases for Java 
// calling other languages
// 

/**
 * The following class runs the array regression test cases for Java.
 */
public class ObjargTestDriver {

  private final static String[] s_results = {
    "FAIL",
    "XFAIL",
    "synch.ResultType.PASS",
    "Xsynch.ResultType.PASS",
    "UNSUPPORTED"
  };

  private final static int ONE_D_SIZE = 345;
  private final static int TEST_DIM1 = 13;
  private final static int TEST_DIM2 = 16;

  private static int s_part = 0;
  private static long s_result = synch.ResultType.PASS;
  private static synch.RegOut tracker; 

  //basic RMI support
  private static String remoteURL = null;

  private static boolean withRMI() {
    return remoteURL != null && remoteURL.length() > 0;
  }
  
  private static sidl.BaseClass makeBaseClassObject() {
    try {
      if(withRMI())
        return new sidl.BaseClass(remoteURL);
      return new sidl.BaseClass();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static objarg.Employee makeEmployeeObject() {
    try {
      if(withRMI())
        return new objarg.Employee(remoteURL);
      return new objarg.Employee();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  private static objarg.EmployeeArray makeEmployeeArrayObject() {
    try {
      if(withRMI())
        return new objarg.EmployeeArray(remoteURL);
      return new objarg.EmployeeArray();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
  private static objarg.Basic makeBasicObject() {
    try {
      if(withRMI())
        return new objarg.Basic(remoteURL);
      return new objarg.Basic();
    } catch (Throwable ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    return null;
  }
  
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
  {
    //Prepare For The Test

    final int numEmp = 7;
    TmpData[] tdata = null; 
    tdata =new TmpData[numEmp];
    
    tdata[0] = new TmpData("John Smith", 35, 75.7e3F, 'c');
    tdata[1] = new TmpData("Jane Doe", 40, 85.5e3F, 'm');
    tdata[2] = new TmpData("Ella Vader", 64, 144.2e3F, 'r');
    tdata[3] = new TmpData("Marge Inovera", 32, 483.2e3F, 's');
    tdata[4] = new TmpData("Hughy Louis Dewey", 45, 182.9e3F, 'm');
    tdata[5] = new TmpData("Heywood Yubuzof", 12, 20.8e3F, 'x');
    tdata[6] = new TmpData("Picov Andropov", 90, 120.6e3F, 'r');
	    
    //Load the Employee array
    //And begin the initial tests

    objarg.EmployeeArray a = makeEmployeeArrayObject();
    
    for(int i = 0; i < numEmp; ++i) {
      objarg.Employee e = makeEmployeeObject();
      startTest(null);
      check(synch.ResultType.PASS, (e != null), "Create Employee " + tdata[i].name);
      startTest(null);
      check(synch.ResultType.PASS, 
            e.init(tdata[i].name, tdata[i].age, tdata[i].salary, 
                   tdata[i].status), 
            "Initialize Employee");
      
      startTest(null);
      check(synch.ResultType.PASS, a.appendEmployee(e), "Append Employee");
      startTest(null);
      check(synch.ResultType.PASS, (a.getLength() == (i+1)), "Correct Array Length");
      startTest(null);
      check(synch.ResultType.PASS, e.isSame(a.at(i+1)), "Check Name in Array");
      startTest(null);
      check(synch.ResultType.PASS, (e.getAge() == tdata[i].age), "Check Age in Array");
      startTest(null);
      check(synch.ResultType.PASS, (e.getSalary() == tdata[i].salary), "Check Salary in Array");
      startTest(null);
      check(synch.ResultType.PASS, (e.getStatus() == tdata[i].status), "Check Status in Array");
      
    }	    
    
    for(int i = 0; i < numEmp; ++i) {
      objarg.Employee.Holder e_hold = new objarg.Employee.Holder();
      int empInd = a.findByName(tdata[i].name, e_hold);
      startTest(null);
      check(synch.ResultType.PASS, (empInd == (i+1)), "Check find by Name Ind Result");
      if (empInd != 0) {
        startTest(null);
        check(synch.ResultType.PASS, e_hold.get().isSame(a.at(empInd)), "Double Check Name");
      }
      
    }
    
    
    objarg.Employee f = makeEmployeeObject();
    f.init("Hire High", 21, 0, 's');
    objarg.Employee.Holder f_hold = new objarg.Employee.Holder(f);
    startTest(null);
    check(synch.ResultType.PASS, a.promoteToMaxSalary(f_hold), "Promote to Max Salary");
    startTest(null);
    check(synch.ResultType.PASS, (f.getSalary() == (float)483.2e3F),
          "Hire has been upgraded to the correct salary");
    startTest(null);
    check(synch.ResultType.PASS, a.appendEmployee(f), "Append Hire");
    
    f = new objarg.Employee();
    f.init("Amadeo Avogadro, conte di Quaregna", 225, 6.022045e23F, 'd');
    startTest(null);
    check(synch.ResultType.PASS, !a.promoteToMaxSalary(f_hold), "Amadeo can't get a higher salary");
    
    /**
     *  Feasability of java object arrays
     */
    
    objarg.Employee.Array1 orray = new objarg.Employee.Array1(5,true);
    
    for(int i = 0; i < 5; ++i) {
      orray.set(i, a.at(i+1));
    }
    
    for(int i = 0; i < 5; ++i) {
      
      startTest(null);
      check(synch.ResultType.PASS, (orray.get(i).getName().compareTo(a.at(i+1).getName()) == 0), 
            "Object " + i + " correctly copied to Sidl Array");
    }
    
    objarg.Employee[] gotArray = orray.toArray();
    for(int i = 0; i < 5; ++i) {
      startTest(null);
      check(synch.ResultType.PASS, (orray.get(i).getName().compareTo(gotArray[i].getName()) == 0), 
            "Object " + i + " correctly copied from Sidl Array");
    }
    
    objarg.Employee.Array1 orray2 = new objarg.Employee.Array1(gotArray, true);
    for(int i = 0; i < 5; ++i) {
      startTest(null);
      check(synch.ResultType.PASS, (orray.get(i).getName().compareTo(orray2.get(i).getName()) == 0), 
            "Object " + i + " correctly copied from Java Array");
    }
    
  }


  /**
   * The main test driver takes no command-line arguments and runs the
   * regression tests.
   */
  public static void main(String args[]) {
    try {

      tracker = new synch.RegOut();
      
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

      tracker.setExpectations(-1);
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

/** A little class to hold the Data used for the test*/
class TmpData {

    String name;
    int age;
    float salary;
    char status;
    
    public TmpData(String s, int a, float sal, char stat) {
	
	name = s;
	age = a;
	salary = sal;
	status = stat;
    }
}
