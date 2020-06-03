#ifndef included_enums_hxx
#include "enums.hxx"
#endif
#include "synch.hxx"
#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif

#include <cstdio>

#include <string.h>

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

#define MYASSERT( AAA ) \
  tracker.startPart(++part_no); \
  tracker.writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart( part_no, result);

static
::sidl::array< ::enums::car > 
createArray(void)
{
  ::sidl::array< ::enums::car > retval;
  retval = ::sidl::array< ::enums::car >::create1d(3);
  retval.set(0, enums::car_ford);
  retval.set(1, enums::car_mercedes);
  retval.set(2, enums::car_porsche);
  return retval;
}

static
bool
checkArray(const ::sidl::array< ::enums::car > src)
{
  const ::enums::car vals[] = { 
    ::enums::car_ford, ::enums::car_mercedes, ::enums::car_porsche 
  };
  bool result = src._not_nil() && (src.dimen() == 1) && (src.length() == 3);
  for(int i = 0; i < 3; ++i) {
    result = result && (src.get(src.lower(0) + i) == vals[i]);
  }
  return result;
}

//basic RMI support
static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static enums::colorwheel
make_colorwheel( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return enums::colorwheel::_create(remoteURL);
#endif
  return enums::colorwheel::_create();
}

static enums::cartest
make_cartest( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return enums::cartest::_create(remoteURL);
#endif
  return enums::cartest::_create();
}

static enums::numbertest
make_numbertest( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return enums::numbertest::_create(remoteURL);
#endif
  return enums::numbertest::_create();
}

int main(int argc, char **argv) { 
  synch::ResultType result = synch::ResultType_PASS; 
  int part_no = 0;
  synch::RegOut tracker = synch::RegOut::_create();
  tracker.setExpectations(26);

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
  
  { // undefined integer values 
    enums::color out = (enums::color) -5;
    enums::color inout = enums::color_green;
    enums::colorwheel obj = make_colorwheel();
 
    MYASSERT( obj.returnback( ) == enums::color_violet );
    MYASSERT( obj.passin( enums::color_blue ) == true );
    MYASSERT( obj.passout( out ) == true && out == enums::color_violet );
    MYASSERT( obj.passinout( inout ) == true && inout == enums::color_red );
    MYASSERT( obj.passeverywhere( enums::color_blue, out, inout ) == enums::color_violet &&
	      out == enums::color_violet && inout == enums::color_green );    
  } 

  { // fully defined integer values 
    enums::car out = (enums::car) -5;
    enums::car inout = enums::car_ford;
    enums::cartest obj = make_cartest();
    ::sidl::array< ::enums::car > tin, tout, tinout, tret;
 
    MYASSERT( obj.returnback( ) == enums::car_porsche );
    MYASSERT( obj.passin( enums::car_mercedes ) == true );
    MYASSERT( obj.passout( out ) == true && out == enums::car_ford );
    MYASSERT( obj.passinout( inout ) == true && inout == enums::car_porsche );
    MYASSERT( obj.passeverywhere( enums::car_mercedes, out, inout ) == enums::car_porsche &&
	      out == enums::car_ford && inout == enums::car_mercedes );
    tin = createArray();
    tinout = createArray();
    tracker.writeComment("Calling enums.cartest.passarray");
    tret = obj.passarray(tin, tout, tinout);
    MYASSERT(checkArray(tin) && checkArray(tout) && checkArray(tinout) &&
	     checkArray(tret));
  }


  { // partially defined integer values
    enums::number out = (enums::number) -5;
    enums::number inout = enums::number_zero;
    enums::numbertest obj = make_numbertest();
 
    MYASSERT( obj.returnback( ) == enums::number_notOne );
    MYASSERT( obj.passin( enums::number_notZero ) == true );
    MYASSERT( obj.passout( out ) == true && out == enums::number_negOne );
    MYASSERT( obj.passinout( inout ) == true && inout == enums::number_notZero );
    MYASSERT( obj.passeverywhere( enums::number_notZero, out, inout ) == enums::number_notOne &&
	      out == enums::number_negOne && inout == enums::number_zero );
  }

  {
    const int32_t numElem[] = { 2 };
    const int32_t stride[] = { 2 };
    sidl::array<enums::car> enumArray = sidl::array<enums::car>::create1d(4);
    sidl::array<enums::car> tmpArray;
    MYASSERT(enumArray);
    enumArray.set(0, enums::car_porsche);
    enumArray.set(1, enums::car_ford);
    enumArray.set(2, enums::car_mercedes);
    enumArray.set(3, enums::car_porsche);
    MYASSERT(enums::car_porsche == enumArray.get(0));
    MYASSERT(enums::car_porsche == enumArray.get(3));
    MYASSERT(enums::car_ford == enumArray.get(1));
    tmpArray = enumArray;
    MYASSERT(tmpArray);
    tmpArray = tmpArray.slice(1, numElem, 0, stride);
    MYASSERT(enums::car_porsche == tmpArray.get(0));
    MYASSERT(enums::car_mercedes == tmpArray.get(1));
    tmpArray.smartCopy();
    MYASSERT(tmpArray);
    MYASSERT(enums::car_porsche == tmpArray.get(0));
    MYASSERT(enums::car_mercedes == tmpArray.get(1));
  }

  tracker.close();
  return 0;
}
