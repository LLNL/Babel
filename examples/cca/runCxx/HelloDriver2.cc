#include <iostream>
#include "decaf_Framework.hxx"
#include "gov_cca_AbstractFramework.hxx"
#include "gov_cca_ComponentID.hxx"
#include "gov_cca_CCAException.hxx"
#include "gov_cca_ports_GoPort.hxx"
#include "gov_cca_ports_BuilderService.hxx"
#include "gov_cca_ConnectionID.hxx"
#include "gov_cca_TypeMap.hxx"
#include "gov_cca_Services.hxx"
#include <string>
using namespace std;

void main_setServices( gov::cca::Services svcs );
void main_go( gov::cca::Services svcs );

int main() { 
  try { 
    gov::cca::AbstractFramework fwk = decaf::Framework::_create();
    gov::cca::TypeMap properties = fwk.createTypeMap();
    gov::cca::Services svcs = fwk.getServices( "me", "myOwnType", properties );
    
    // \begin{main() masquerading as a component}
    main_setServices( svcs );   
    main_go( svcs );
    // \end{main as a component}
    
    fwk.releaseServices( svcs );
    fwk.shutdownFramework();
  } catch (gov::cca::CCAException ex ) { 
    std::cout << "Caught Exception\n"
	      << ex.getNote() << '\n'
              << ex.getTrace() << std::endl;
  }

} 

void main_setServices( gov::cca::Services svcs ) {
  gov::cca::TypeMap properties = svcs.createTypeMap();
  svcs.registerUsesPort("builder", "gov.cca.ports.BuilderServices", properties );
  svcs.registerUsesPort("go", "gov.cca.ports.GoPort", properties );
}

void main_go( gov::cca::Services svcs ) { 
  // get my builder service
  gov::cca::ports::BuilderService bs = sidl::babel_cast< gov::cca::ports::BuilderService >(svcs.getPort( "builder" ));
  
  // create and connenct hello server and client
  gov::cca::ComponentID server = bs.createInstance( "HelloServerInstance", "HelloServer.Component", 0 );
  gov::cca::ComponentID client = bs.createInstance( "HelloClientInstance", "HelloClient.Component", 0 );
  bs.connect( client, "HelloServer", server, "HelloServer" );
    
  // now connect client's go point to mine
  bs.connect( svcs.getComponentID(), "go", client, "GoPort" );    
  gov::cca::ports::GoPort go = sidl::babel_cast< gov::cca::ports::GoPort >(svcs.getPort( "go" ));
  go.go();
  
  svcs.releasePort( "builder" );
  svcs.releasePort( "go" );
}
    
