#include <string>
#include <sstream>
#include <iostream>
#include <cstdio>
#include <string.h>
using namespace std;
#include "hooks.hxx"
#include "synch.hxx"
#include "sidl_RuntimeException.hxx"

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif /* UCXX */

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

#define MYASSERT( AAA ) \
  tracker.startPart(++part_no); \
  tracker.writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart(part_no, result);

//basic RMI support
static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static hooks::Basics
makeObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return hooks::Basics::_create(remoteURL);
#endif
  return hooks::Basics::_create();
}

int main(int argc, char **argv) { 
  synch::ResultType result = synch::ResultType_PASS; 
  synch::RegOut tracker = synch::RegOut::getInstance();

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
  
  int part_no = 0;
  tracker.setExpectations(4);
  { 
    int32_t b, c, ret = 0;
    int32_t test = 1;
    try {
      hooks::Basics::_set_hooks_static(TRUE);

      hooks::Basics obj = makeObject();
      obj._set_hooks(TRUE);

      b = c = -1;
      ret = hooks::Basics::aStaticMeth(test, b, c);
      MYASSERT( b == 1 && c == 0 );
      ret = hooks::Basics::aStaticMeth(test, b, c);
      MYASSERT( b == 2 && c == 1 );

      b = c = -1;
      ret = obj.aNonStaticMeth(test, b, c);
      MYASSERT( b == 1 && c == 0 );
      ret = obj.aNonStaticMeth(test, b, c);
      MYASSERT( b == 2 && c == 1 );
    } catch (::sidl::RuntimeException e) {
      cout << e.getNote() << endl;
    }
 
  }

  tracker.close();
  return 0;
}

