// overloadtest.cc

#include <string>
#include <string.h>
using namespace std;
#include "Overload.hxx"
#include "synch.hxx"
#include <cstdio>

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif
using namespace Overload;

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

#define MYASSERT( AAA ) \
  tracker.startPart(++part_no); \
  tracker.writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL; \
  tracker.endPart(part_no, result);

//basic RMI support
static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static Overload::Test
makeOverloadTest( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return Overload::Test::_create(remoteURL);
#endif
  return Overload::Test::_create();
}

static Overload::AnException
makeAnException( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return Overload::AnException::_create(remoteURL);
#endif
  return Overload::AnException::_create();
}

static Overload::AClass
makeAClass( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return Overload::AClass::_create(remoteURL);
#endif
  return Overload::AClass::_create();
}

static Overload::BClass
makeBClass( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return Overload::BClass::_create(remoteURL);
#endif
  return Overload::BClass::_create();
}

int main(int argc, char * argv[]) {
  int part_no      = 0;
  synch::ResultType result       = synch::ResultType_PASS;
  synch::RegOut tracker = synch::RegOut::_create();

#ifdef WITH_RMI
  //Parse the command line  to see if we are running RMI tests
  {
    unsigned _arg;
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
  
  bool   b1        = true;
  double d1        = 1.0;
  float  f1        = 1.0F;
  int    i1        = 1;
  double did       = 2.0;
  double difd      = 3.0;
  string s1        = "aString";

  complex<double> cd1 (1.1, 1.1);
  complex<double> cdret;
  complex<float>  cf1 (1.1F, 1.1F);
  complex<float>  cfret;

  Overload::Test        t  = makeOverloadTest();
  Overload::AnException ae = makeAnException();
  Overload::AClass      ac = makeAClass();
  Overload::BClass      bc = makeBClass();

  tracker.setExpectations(19);
  MYASSERT( t.getValue() == 1 );

  MYASSERT( t.getValue(b1) == b1 );
  MYASSERT( t.getValue(d1) == d1 );
  cdret = t.getValue(cd1);
  MYASSERT( cdret.real() == cd1.real() && cdret.imag() == cd1.imag() );
  MYASSERT( t.getValue(f1) == f1 );
  cfret = t.getValue(cf1);
  MYASSERT( cfret.real() == cf1.real() && cfret.imag() == cf1.imag() );
  MYASSERT( t.getValue(i1) == i1 );
  MYASSERT( t.getValue(s1) == s1 );

  MYASSERT( t.getValue(d1, i1) == did );
  MYASSERT( t.getValue(i1, d1) == did );

  MYASSERT( t.getValue(d1, i1, f1) == difd );
  MYASSERT( t.getValue(i1, d1, f1) == difd );

  MYASSERT( t.getValue(d1, f1, i1) == difd );
  MYASSERT( t.getValue(i1, f1, d1) == difd );

  MYASSERT( t.getValue(f1, d1, i1) == difd );
  MYASSERT( t.getValue(f1, i1, d1) == difd );

  MYASSERT( t.getValue(ae) == "AnException" );
  MYASSERT( t.getValue(ac) == 2 );
  MYASSERT( t.getValue(bc) == 2 );

  tracker.close();
  return 0;
}
