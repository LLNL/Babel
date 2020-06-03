#include <iostream>
#include "wave2d.hxx"
#include "cxx_ScalarField.hxx"
#include "cxx_WavePropagator.hxx"
#include "sidlx_rmi_SimpleOrb.hxx"
#include "sidl_rmi_ServerRegistry.hxx"
#include "sidl_rmi_ProtocolFactory.hxx"
#include "sidl_RuntimeException.hxx"

using namespace std;

int main(int argc, char* argv[]) { 
  if ( argc!=2 ) { 
    cerr << "usage: " << argv[0] 
	 << "  simhandle://[some host]:[port#]/[instanceID] " << endl;
    return 1;
  }
  sidl::rmi::ProtocolFactory::addProtocol("simhandle","sidlx.rmi.SimHandle");

  sidlx::rmi::SimpleOrb orb = sidlx::rmi::SimpleOrb::_create();
  if (! orb.requestPort(8000,9000) ) { 
    cerr << "No port available in range" << endl;
    return 1;
  }

  sidl::rmi::ServerRegistry::registerServer(orb);
  int32_t thread_id = orb.run();
  try { 

    cxx::ScalarField d = cxx::ScalarField::_connect(argv[1]);
    d.init( 0.0, 0.0, 1.0, 1.0, 0.5, 0.2);

    cxx::WavePropagator wp = cxx::WavePropagator::_create();
    sidl::array<double> p = sidl::array<double>::create2dRow(2,2);
    p.set(0,0,1.0);
    p.set(0,1,1.0);
    p.set(1,0,1.0);
    p.set(1,1,1.0);
    wp.init( d, p );
    
    sidl::array<double> p2 = wp.getPressure();  
    cout << "step 0" << endl;
    cout << p2.get(0,0) << "  " << p2.get(0,1) << endl;
    cout << p2.get(1,0) << "  " << p2.get(1,1) << endl << endl;
    
    for( int i=0; i<10; ++i ) { 
      wp.step(1);    
      p2 = wp.getPressure();
      cout << "step " << i << endl;
      cout << p2.get(0,0) << "  " << p2.get(0,1) << endl;
      cout << p2.get(1,0) << "  " << p2.get(1,1) << endl << endl;
    }
    cout << "ScalarField " << (d._isLocal() ? "isLocal" : "isRemote" ) << endl;
    cout << "WavePropagator " << ( wp._isLocal() ? " isLocal" : "isRemote") << endl;
  } catch ( sidl::RuntimeException e ) { 
    cerr <<  "error: caught exception with msg=\"" << e.getNote() << "\"" << endl;
    cerr << e.getTrace() << endl;
  }

  // cleanup
  orb.shutdown();
  if (thread_id) { 
    pthread_join(thread_id,NULL);
  }
  return 0;
}
