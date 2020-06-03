#include <string>
#include <string.h>
using namespace std;
#include "Strings.hxx"
#include "synch.hxx"
#include <cstdio>

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif


#define MYASSERT( AAA ) \
  tracker.startPart(++part_no);\
  tracker.writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart(part_no, result);

//basic RMI support
static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static Strings::Cstring
makeObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return Strings::Cstring::_create(remoteURL);
#endif
  return Strings::Cstring::_create();
}

int main(int argc, char * argv[]) { 
  synch::ResultType result = synch::ResultType_PASS;
  synch::RegOut tracker = synch::RegOut::_create();
  
  int part_no = 0;

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
  
  Strings::Cstring obj = makeObject();
  tracker.setExpectations(-1);
  
  { 
    string in = "Three";
    string out;
    string inout = "Three";
 
    MYASSERT( obj.returnback(true) == "Three" );
    MYASSERT( obj.returnback(false) == "" );
    MYASSERT( obj.passin( in ) == true );
    MYASSERT( obj.passout(true, out ) == true && out == "Three" );
    MYASSERT( obj.passout(false, out ) == false && out == "" );
    MYASSERT( obj.passinout( inout ) == true && inout == "threes" );
    MYASSERT( obj.passeverywhere( in, out, inout ) == "Three" &&
	      out == "Three" && inout == "Three" );
    MYASSERT( obj.mixedarguments( "Test", 'z', "Test", 'z') );
    MYASSERT( !obj.mixedarguments( "Not", 'A', "Equal", 'a') );
    
  }

  tracker.close();
  return 0;
}
