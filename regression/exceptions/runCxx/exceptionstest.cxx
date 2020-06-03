// excepttest.cc

#include <string>
#include <iostream>
using namespace std;
#include "Exceptions.hxx"
#include "synch.hxx"
#ifdef SIDL_USE_UCXX
using namespace ucxx::Exceptions;
using namespace ucxx;
#else
using namespace Exceptions;
#endif
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include <cstdio>

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

#define MYPASS( AAA ) \
  tracker.writeComment(AAA); \
  tracker.endPart(part_no, synch::ResultType_PASS);

#define MYFAIL( AAA ) \
  tracker.writeComment(AAA); \
  tracker.endPart(part_no, synch::ResultType_FAIL);

#define MYXFAIL( AAA )       \
  tracker.writeComment(AAA); \
  tracker.endPart(part_no, synch::ResultType_XFAIL);

//basic RMI support
static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static Fib
makeObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) 
    return Fib::_create(remoteURL);
#endif
  return Fib::_create();
}

static ::sidl::BaseClass
makeBaseClassObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) 
    return ::sidl::BaseClass::_create(remoteURL);
#endif
  return ::sidl::BaseClass::_create();
}

static void
leakCheckOne(synch::RegOut &tracker,
	     Fib           &fib,
	     int           &part_no)
{
  int32_t buffer1[4] = {0}, buffer2[4] = {0}, m=2, n=2;
  ::sidl::array<int> a1 = ::sidl::array<int>::create2dCol(3,3);
  ::sidl::array<int> a2 = ::sidl::array<int>::create2dCol(4,4);
  ::sidl::array<int> a3;
  ::sidl::array<int> c1 = ::sidl::array<int>::create1d(3);
  ::sidl::array<int> c2 = ::sidl::array<int>::create1d(4);
  ::sidl::array<int> c3, retval;
  ::sidl::BaseClass o1 = makeBaseClassObject();
  ::sidl::BaseClass o2 = makeBaseClassObject();
  ::sidl::BaseClass o3;
  ::std::string s1 = "foo";
  ::std::string s2 = "foo";
  ::std::string s3;

  int i,j;
  for (i=0; i<3; ++i)
    for (j=0; j<3; ++j)
      a1.set(i,j,42);

  for (i=0; i<4; ++i)
    for (j=0; j<4; ++j)
      a2.set(i,j,42);

  for (i=0; i<3; ++i)
    c1.set(i,42);

  for (i=0; i<4; ++i)
    c2.set(i,42);

  tracker.writeComment("leakCheckOne");
  try {
    tracker.startPart(++part_no);
    fib.noLeak(a1, a2, a3, buffer1, buffer2, m, n,
	       c1, c2, c3, s1, s2, s3, o1, o2, o3);
    MYFAIL("expected exception not thrown");
  }
  catch(::sidl::SIDLException &se){
    MYPASS("expected exception thrown");
  } catch (sidl::BaseException& e) {
    MYFAIL("unexpected exception thrown");
    std::cout << e.getTrace();
  } catch(...) {
    MYFAIL("unexpected exception thrown");
  }
}

static void
leakCheckTwo(synch::RegOut &tracker,
	     Fib           &fib,
	     bool           row,
	     int           &part_no)
{
  ::sidl::array<int> a1 = ::sidl::array<int>::create2dCol(3,3);
  ::sidl::array<int> a2 = ::sidl::array<int>::create2dCol(4,4);
  ::sidl::array<int> a3;
  ::sidl::array<int> r1 = 
      (row ? ::sidl::array<int>::create2dRow(3,3)
       : ::sidl::array<int>::create2dCol(3,3));
  ::sidl::array<int> r2 = 
      (row ? ::sidl::array<int>::create2dRow(3,3)
       : ::sidl::array<int>::create2dCol(3,3));
  ::sidl::array<int> c1 = ::sidl::array<int>::create1d(3);
  ::sidl::array<int> c2 = ::sidl::array<int>::create1d(4);
  ::sidl::array<int> c3, retval;
  ::sidl::BaseClass o1 = makeBaseClassObject();
  ::sidl::BaseClass o2 = makeBaseClassObject();
  ::sidl::BaseClass o3;
  ::std::string s1 = "foo";
  ::std::string s2 = "foo";
  ::std::string s3;

  int i,j;
  for (i=0; i<3; ++i)
    for (j=0; j<3; ++j) {
      a1.set(i,j,42);
      r1.set(i,j,42);
      r2.set(i,j,42);
    }

  for (i=0; i<4; ++i)
    for (j=0; j<4; ++j)
      a2.set(i,j,42);

  for (i=0; i<3; ++i)
    c1.set(i,42);

  for (i=0; i<4; ++i)
    c2.set(i,42);

  tracker.writeComment(::std::string("leakCheckTwo: row is ") +
		       ::std::string(row ? "TRUE" : "FALSE"));
  try {
    tracker.startPart(++part_no);
    fib.noLeak(a1, a2, a3, r1, r2,
	       c1, c2, c3, s1, s2, s3, o1, o2, o3);
    MYFAIL("expected exception not thrown");
  }
  catch(::sidl::SIDLException &se){
    MYPASS("expected exception thrown");
  }
  catch(...) {
    MYFAIL("unexpected exception thrown");
  }
}

int main(int argc, char **argv) { 
  int part_no = 0;
  synch::RegOut tracker = synch::RegOut::_create();
  
#ifdef WITH_RMI
  //Parse the command line  to see if we are running RMI tests
  {
    int _arg;
    for(_arg = 0; _arg < argc; ++_arg) {
      if(strncmp(argv[_arg], "--url=", 6) == 0) {
        std::string _tmp(argv[_arg] + 6);
        tracker.replaceMagicVars(_tmp, argv[0]);
        remoteURL = strdup(_tmp.c_str());
      }
    }
    if(withRMI()) {
      fprintf(stdout, "using remote URL %s\n", remoteURL);
    }
  }

  //Setup RMI if necessary
  if(withRMI()) {
    fprintf(stdout, "registering RMI protocol simhandle\n");
    if(!sidl::rmi::ProtocolFactory::addProtocol("simhandle","sidlx.rmi.SimHandle")) {
      printf("sidl.rmi.ProtocolFactor.addProtocol() failed\n");
      exit(2);
    }
  }
#endif

  Fib fib = makeObject();
  
  tracker.setExpectations(7);
  tracker.startPart(++part_no);
  try { 
    fib.getFib(10,25,200,0);

    MYPASS( "no exception thrown (none expected)" );
  } catch (...) { 
    MYFAIL("unexpected exception thrown");
  }

  tracker.startPart(++part_no);
  try { 
    fib.getFib(-1,10,10,0);    
    MYFAIL( "no exception thrown (NegativeValueException expected)" );
  } catch ( NegativeValueException ex ) { 
    MYPASS( "NegativeValueException thrown (as expected)" );
  } catch ( ... ) { 
    MYFAIL( "unexpected exception thrown" );
  }
 
  tracker.startPart(++part_no);
  try { 
    fib.getFib(10,1,1000,0);
    MYFAIL( "no exception thrown (TooDeepException expected)" );    
  } catch ( TooDeepException ex ) {
#ifdef SIDL_EXCEPTIONTEST_FIB_FASTCALL_ENABLED
    MYXFAIL( "TooDeepException thrown (and unexpectedly caught!)");
#else    
    MYFAIL( "TooDeepException thrown (and unexpectedly caught!)");
#endif
  } catch ( FibException ex ) { // catch declared types
    TooDeepException ex2 = sidl::babel_cast<TooDeepException>(ex);  // downcast
    if ( !ex2 ) { 
      MYFAIL( "FibException caught, but cannot cast to TooDeepException" );
    } else { 
      MYPASS( "FibException caught, and correctly cast to TooDeepException" );
    }
  } catch( ... ) { 
    MYFAIL( "unexpected exception thrown" );
  }

  tracker.startPart(++part_no);
  try { 
    fib.getFib(10,1000,1,0);
    MYFAIL( "no exception thrown (TooBigException expected)" );    
  } catch ( TooBigException ex ) { 
#ifdef SIDL_EXCEPTIONTEST_FIB_FASTCALL_ENABLED
    MYXFAIL( "TooBigException thrown (and unexpectedly caught!)");
#else
    MYFAIL( "TooBigException thrown (and unexpectedly caught!)");
#endif
  } catch ( FibException ex ) { // catch declared types
    TooBigException ex2 = sidl::babel_cast<TooBigException>(ex);  // downcast
    if ( !ex2 ) { 
      MYFAIL( "FibException caught, but cannot cast to TooBigException" );
    } else { 
      MYPASS( "FibException caught, and correctly cast to TooBigException" );
    }  
  } catch( ... ) { 
    MYFAIL( "unexpected exception thrown" );
  }
  leakCheckOne(tracker, fib, part_no);
  leakCheckTwo(tracker, fib, true, part_no);
  leakCheckTwo(tracker, fib, false, part_no);
  tracker.close();
  return 0;
}
