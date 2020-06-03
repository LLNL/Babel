#include "decaf_Framework.hxx"
#include "gov_cca_ComponentID.hxx"
#include "gov_cca_ConnectionID.hxx"
#include "gov_cca_ports_GoPort.hxx"
#include "gov_cca_TypeMap.hxx"
#include <string>
using namespace std;

int main() { 
  decaf::Framework fwk = decaf::Framework::_create();
  gov::cca::ComponentID server = fwk.createInstance( "HelloServerInstance", "HelloServer.Component", 0 );
  gov::cca::ComponentID client = fwk.createInstance( "HelloClientInstance", "HelloClient.Component", 0 );

  fwk.connect( client, "HelloServer", server, "HelloServer" );
  gov::cca::Port p = fwk.lookupPort( client, "GoPort" );
  gov::cca::ports::GoPort go = sidl::babel_cast< gov::cca::ports::GoPort > (p);
  if (go._not_nil()) {
    go.go();
  }
} 
