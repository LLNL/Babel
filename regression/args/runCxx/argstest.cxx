#include <string>
#include <cstdio>
#include <sstream>
#include <string.h>
using namespace std;
#include "Args.hxx"
#include "synch.hxx"

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif /* UCXX */


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

static ::Args::Basic
makeObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return Args::Basic::_create(remoteURL);
#endif
  return Args::Basic::_create();
}

int main(int argc, char **argv) { 
  synch::ResultType result = synch::ResultType_PASS; 
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
  
  int part_no = 0;
  string language = "";
  tracker.setExpectations(-1);
  if (argc == 2) {
    language = argv[1];
  }
  
  { // bool 
    bool out;
    bool inout = true;
    Args::Basic obj = makeObject();
 
    MYASSERT( obj.returnbackbool( ) == true );
    MYASSERT( obj.passinbool( true ) == true );
    MYASSERT( obj.passoutbool( out ) == true && out == true );
    MYASSERT( obj.passinoutbool( inout ) == true && inout == false );
    MYASSERT( obj.passeverywherebool( true, out, inout ) == true &&
	      out == true && inout == true );    
  } 

  { // char 
    char out;
    char inout = 'A';
    Args::Basic obj = makeObject();
 
    MYASSERT( obj.returnbackchar( ) == '3' );
    MYASSERT( obj.passinchar( '3' ) == true );
    MYASSERT( obj.passoutchar( out ) == true && out == '3' );
    MYASSERT( obj.passinoutchar( inout ) == true && inout == 'a' );
    MYASSERT( obj.passeverywherechar( '3', out, inout ) == '3' &&
	      out == '3' && inout == 'A' );
  }


  { // int 
    int32_t out;
    int32_t inout = 3;
    Args::Basic obj = makeObject();
 
    MYASSERT( obj.returnbackint( ) == 3 );
    MYASSERT( obj.passinint( 3 ) == true );
    MYASSERT( obj.passoutint( out ) == true && out == 3 );
    MYASSERT( obj.passinoutint( inout ) == true && inout == -3 );
    MYASSERT( obj.passeverywhereint( 3, out, inout ) == 3 &&
	      out == 3 && inout == 3 );
  }


  { // long 
    int64_t out;
    int64_t inout = 3L;
    Args::Basic obj = makeObject();
 
    MYASSERT( obj.returnbacklong( ) == 3L );
    MYASSERT( obj.passinlong( 3L ) == true );
    MYASSERT( obj.passoutlong( out ) == true && out == 3L );
    MYASSERT( obj.passinoutlong( inout ) == true && inout == -3L );
    MYASSERT( obj.passeverywherelong( 3L, out, inout ) == 3L &&
	      out == 3L && inout == 3L );
  }


  { // float 
    ostringstream buf;
    float out;
    float inout = 3.1F;
    Args::Basic obj = makeObject();
    buf << "obj.returnbackfloat() == " << obj.returnbackfloat();
    tracker.writeComment(buf.str());
    MYASSERT( obj.returnbackfloat( ) == 3.1F );
    MYASSERT( obj.passinfloat( 3.1F ) == true );
    MYASSERT( obj.passoutfloat( out ) == true && out == 3.1F );
    MYASSERT( obj.passinoutfloat( inout ) == true && inout == -3.1F );
    MYASSERT( obj.passeverywherefloat( 3.1F, out, inout ) == 3.1F &&
	      out == 3.1F && inout == 3.1F );
  }


  { // double 
    double out;
    double inout = 3.14;
    Args::Basic obj = makeObject();
 
    MYASSERT( obj.returnbackdouble( ) == 3.14 );
    MYASSERT( obj.passindouble( 3.14 ) == true );
    MYASSERT( obj.passoutdouble( out ) == true && out == 3.14 );
    MYASSERT( obj.passinoutdouble( inout ) == true && inout == -3.14 );
    MYASSERT( obj.passeverywheredouble( 3.14, out, inout ) == 3.14 &&
	      out == 3.14 && inout == 3.14 );
  }


  { // fcomplex 
    ostringstream buf;
    complex<float> retval;
    complex<float> in( 3.1F, 3.1F );
    complex<float> out;
    complex<float> inout( 3.1F, 3.1F );
    Args::Basic obj = makeObject();
 
    buf << "retval = " << obj.returnbackfcomplex( );
    tracker.writeComment(buf.str());
    retval = obj.returnbackfcomplex( ); 
    MYASSERT( retval.real() == 3.1F && retval.imag() == 3.1F);
    MYASSERT( obj.passinfcomplex( in ) == true );

    MYASSERT( obj.passoutfcomplex( out ) == true && 
	      out.real() == 3.1F && out.imag() == 3.1F );
    MYASSERT( obj.passinoutfcomplex( inout ) == true && 
	      inout.real() == 3.1F && inout.imag() == -3.1F );
    tracker.writeComment("retval = obj.passeverywherefcomplex( in, out, inout );");
    retval = obj.passeverywherefcomplex( in, out, inout );
    MYASSERT( retval.real() == 3.1F && retval.imag() == 3.1F &&
	      out.real() == 3.1F && out.imag() == 3.1F && 
	      inout.real() == 3.1F && inout.imag() == 3.1F );
  }


  { // dcomplex 
    complex<double> retval;
    complex<double> in( 3.14, 3.14 );
    complex<double> out;
    complex<double> inout( 3.14, 3.14 );
    Args::Basic obj = makeObject();
 
    tracker.writeComment("retval = obj.returnback( );");
    retval = obj.returnbackdcomplex( );
    MYASSERT( retval.real() == 3.14 && retval.imag() == 3.14);
    MYASSERT( obj.passindcomplex( in ) == true );
    MYASSERT( obj.passoutdcomplex( out ) == true && 
	      out.real() == 3.14 && out.imag() == 3.14 );
    MYASSERT( obj.passinoutdcomplex( inout ) == true && 
	      inout.real() == 3.14 && inout.imag() == -3.14 );
    tracker.writeComment("retval = obj.passeverywheredcomplex( in, out, inout );");
    retval = obj.passeverywheredcomplex( in, out, inout );
    MYASSERT( retval.real() == 3.14 && retval.imag() == 3.14 &&
	      out.real() == 3.14 && out.imag() == 3.14 && 
	      inout.real() == 3.14 && inout.imag() == 3.14 );

  }

  tracker.close();
  return 0;
}

