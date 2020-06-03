// 
// File:          decaf_Services_Impl.cxx
// Symbol:        decaf.Services-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.Services
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_Services_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_AbstractFramework_hxx
#include "gov_cca_AbstractFramework.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentID_hxx
#include "gov_cca_ComponentID.hxx"
#endif
#ifndef included_gov_cca_ComponentRelease_hxx
#include "gov_cca_ComponentRelease.hxx"
#endif
#ifndef included_gov_cca_Port_hxx
#include "gov_cca_Port.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_gov_cca_ports_ConnectionEventListener_hxx
#include "gov_cca_ports_ConnectionEventListener.hxx"
#endif
#ifndef included_gov_cca_ports_EventType_hxx
#include "gov_cca_ports_EventType.hxx"
#endif
#ifndef included_gov_cca_ports_ServiceProvider_hxx
#include "gov_cca_ports_ServiceProvider.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(decaf.Services._includes)
#include <cstdio>
#include <iostream>
#include "decaf_TypeMap.hxx"
#include "decaf_Framework.hxx"
#include "decaf_CCAException.hxx"
#include "decaf_ports_ConnectionEvent.hxx"

class equality_predicate { 
  gov::cca::ports::ConnectionEventListener d_cel;
public:
  explicit equality_predicate(gov::cca::ports::ConnectionEventListener& cel ) : d_cel(cel) { }

  bool operator()(const gov::cca::ports::ConnectionEventListener& cel ) { 
    return d_cel._get_ior() == cel._get_ior();
  }
};

// DO-NOT-DELETE splicer.end(decaf.Services._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::Services_impl::Services_impl() : StubBase(reinterpret_cast< void*>(
  ::decaf::Services::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.Services._ctor2)
  // Insert-Code-Here {decaf.Services._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.Services._ctor2)
}

// user defined constructor
void decaf::Services_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.Services._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.Services._ctor)
}

// user defined destructor
void decaf::Services_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.Services._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.Services._dtor)
}

// static class initializer
void decaf::Services_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.Services._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.Services._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  bindPort[]
 */
void
decaf::Services_impl::bindPort_impl (
  /* in */const ::std::string& portName,
  /* in */::gov::cca::Port& port ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.bindPort)
  if ( port._not_nil() ) { 
    portMap_t::iterator it = d_usesPort.find( portName );
    if ( it != d_usesPort.end() ) {
      (*it).second.first = port;
    }
  } else { 
    std::cout << "binding to nul port" << std::endl;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.bindPort)
}

/**
 * Method:  getProvidesPort[]
 */
::gov::cca::Port
decaf::Services_impl::getProvidesPort_impl (
  /* in */const ::std::string& name ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getProvidesPort)
  portMap_t::iterator it = d_providesPort.find(name);
  if ( it != d_providesPort.end() ) { 
    portMap_t::value_type vt =(*it);
    return (*it).second.first;
  } else { 
    return 0;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.getProvidesPort)
}

/**
 * Method:  initialize[]
 */
void
decaf::Services_impl::initialize_impl (
  /* in */::gov::cca::AbstractFramework& fwk,
  /* in */::gov::cca::ComponentID& componentID,
  /* in */::gov::cca::TypeMap& properties,
  /* in */bool is_alias ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.initialize)
  d_fwk = fwk;
  d_componentID = componentID;
  d_instanceProperties = properties;
  d_is_alias = is_alias;
  // DO-NOT-DELETE splicer.end(decaf.Services.initialize)
}

/**
 * Method:  getInstanceProperties[]
 */
::gov::cca::TypeMap
decaf::Services_impl::getInstanceProperties_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getInstanceProperties)
  return d_instanceProperties;
  // DO-NOT-DELETE splicer.end(decaf.Services.getInstanceProperties)
}

/**
 * Method:  setInstanceProperties[]
 */
void
decaf::Services_impl::setInstanceProperties_impl (
  /* in */::gov::cca::TypeMap& properties ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.setInstanceProperties)
  d_instanceProperties = properties;
  // DO-NOT-DELETE splicer.end(decaf.Services.setInstanceProperties)
}

/**
 * Method:  setPortProperties[]
 */
void
decaf::Services_impl::setPortProperties_impl (
  /* in */const ::std::string& portName,
  /* in */::gov::cca::TypeMap& properties ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.setPortProperties)
  if ( d_providesPort.find( portName ) != d_providesPort.end() ) { 
    d_providesPort[ portName ].second = properties;
  } else if ( d_usesPort.find( portName ) != d_usesPort.end() ) {
    d_usesPort[ portName ].second = properties;
  } else { 
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_BadPortName );
    gov::cca::CCAException ex = dex; 
    ex.setNote( "No portname registered to associate properties with" );
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::setPortProperties(...)" );
    throw ex;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.setPortProperties)
}

/**
 * Method:  getProvidedPortNames[]
 */
::sidl::array< ::std::string>
decaf::Services_impl::getProvidedPortNames_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getProvidedPortNames)
  int len = d_providesPort.size();
  sidl::array<std::string> retval = sidl::array<std::string>::create1d( len );  
  int i =0;
  for ( portMap_t::iterator it = d_providesPort.begin(); it != d_providesPort.end(); ++it,++i ){ 
    retval.set( i, (*it).first );
  }
  return retval;
  // DO-NOT-DELETE splicer.end(decaf.Services.getProvidedPortNames)
}

/**
 * Method:  getUsedPortNames[]
 */
::sidl::array< ::std::string>
decaf::Services_impl::getUsedPortNames_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getUsedPortNames)
  int len = d_usesPort.size();
  sidl::array<std::string> retval = sidl::array<std::string>::create1d( len );  
  int i =0;
  for ( portMap_t::iterator it = d_usesPort.begin(); it != d_usesPort.end(); ++it,++i ) { 
    retval.set( i, (*it).first );
  }
  return retval;
  // DO-NOT-DELETE splicer.end(decaf.Services.getUsedPortNames)
}

/**
 * Method:  notifyConnectionEvent[]
 */
void
decaf::Services_impl::notifyConnectionEvent_impl (
  /* in */const ::std::string& portName,
  /* in */::gov::cca::ports::EventType event ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.notifyConnectionEvent)
  listenerList_t &listeners(d_listeners[ event ]);
  listenerList_t::iterator i = listeners.begin();
  decaf::ports::ConnectionEvent ce = decaf::ports::ConnectionEvent::_create();
  gov::cca::TypeMap tm = createTypeMap();
  tm.putString("cca.portName", portName);
  tm.putString("cca.portType", d_portType[portName]);
  ce.initialize(event, tm);
  while (i != listeners.end()) {
    i->connectionActivity(ce);
    ++i;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.notifyConnectionEvent)
}

/**
 *  
 * Fetch a previously registered Port (defined by either 
 * addProvidePort or (more typically) registerUsesPort).  
 * @return Will return the Port (possibly waiting forever while
 * attempting to acquire it) or throw an exception. Does not return
 * NULL, even in the case where no connection has been made. 
 * If a Port is returned,
 * there is then a contract that the port will remain valid for use
 * by the caller until the port is released via releasePort(), or a 
 * Disconnect Event is successfully dispatched to the caller,
 * or a runtime exception (such as network failure) occurs during 
 * invocation of some function in the Port. 
 * <p>
 * Subtle interpretation: If the Component is not listening for
 * Disconnect events, then the framework has no clean way to
 * break the connection until after the component calls releasePort.
 * </p>
 * <p>The framework may go through some machinations to obtain
 * the port, possibly involving an interactive user or network 
 * queries, before giving up and throwing an exception.
 * </p>
 * 
 * @param portName The previously registered or provide port which
 * the component now wants to use.
 * @exception CCAException with the following types: NotConnected, PortNotDefined, 
 * NetworkError, OutOfMemory.
 */
::gov::cca::Port
decaf::Services_impl::getPort_impl (
  /* in */const ::std::string& portName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getPort)
  portMap_t::iterator it = d_usesPort.find(portName);
  if ( it != d_usesPort.end() ) { 
    gov::cca::Port requested_port = (*it).second.first;
    return requested_port;
  } else { 
    return 0;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.getPort)
}

/**
 *  
 * Get a previously registered Port (defined by
 * either addProvide or registerUses) and return that
 * Port if it is available immediately (already connected
 * without further connection machinations).
 * There is an contract that the
 * port will remain valid per the description of getPort.
 * @return The named port, if it exists and is connected or self-provided,
 * or NULL if it is registered and is not yet connected. Does not
 * return if the Port is neither registered nor provided, but rather
 * throws an exception.
 * @param portName registered or provided port that
 * the component now wants to use.
 * @exception CCAException with the following types: PortNotDefined, OutOfMemory.
 */
::gov::cca::Port
decaf::Services_impl::getPortNonblocking_impl (
  /* in */const ::std::string& portName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getPortNonblocking)
  portMap_t::iterator it = d_usesPort.find(portName);
  if ( it != d_usesPort.end() ) { 
    gov::cca::Port requested_port = (*it).second.first;
    return requested_port;
  } else { 
    return 0;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.getPortNonblocking)
}

/**
 *  
 * Notifies the framework that this component is finished 
 * using the previously fetched Port that is named.     
 * The releasePort() method calls should be paired with 
 * getPort() method calls; however, an extra call to releasePort()
 * for the same name may (is not required to) generate an exception.
 * Calls to release ports which are not defined or have never be fetched
 * with one of the getPort functions generate exceptions.
 * @param portName The name of a port.
 * @exception CCAException with the following types: PortNotDefined, PortNotInUse.
 */
void
decaf::Services_impl::releasePort_impl (
  /* in */const ::std::string& portName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.releasePort)
  portMap_t::iterator it = d_usesPort.find( portName );
  if ( it != d_usesPort.end() ) { 
    gov::cca::Port empty;
    (*it).second.first = empty;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.releasePort)
}

/**
 * Creates a TypeMap, potentially to be used in subsequent
 * calls to describe a Port.  Initially, this map is empty.
 */
::gov::cca::TypeMap
decaf::Services_impl::createTypeMap_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.Services.createTypeMap)
  return decaf::TypeMap::_create();
  // DO-NOT-DELETE splicer.end(decaf.Services.createTypeMap)
}

/**
 *  
 * Register a request for a Port that will be retrieved subsequently 
 * with a call to getPort().
 * @param portName A string uniquely describing this port.  This string
 * must be unique for this component, over both uses and provides ports.
 * @param type A string desribing the type of this port.
 * @param properties A TypeMap describing optional properties
 * associated with this port. This can be a null pointer, which
 * indicates an empty list of properties.  Properties may be
 * obtained from createTypeMap or any other source.  The properties
 * be copied into the framework, and subsequent changes to the
 * properties object will have no effect on the properties
 * associated with this port.
 * In these properties, all frameworks recognize at least the
 * following keys and values in implementing registerUsesPort:
 * <pre>
 * key:              standard values (in string form)     default
 * "MAX_CONNECTIONS" any nonnegative integer, "unlimited".   1
 * "MIN_CONNECTIONS" any integer > 0.                        0
 * "ABLE_TO_PROXY"   "true", "false"                      "false"
 * </pre>
 * The component is not expected to work if the framework
 * has not satisfied the connection requirements.
 * The framework is allowed to return an error if it
 * is incapable of meeting the connection requirements,
 * e.g. it does not implement multiple uses ports.
 * The caller of registerUsesPort is not obligated to define
 * these properties. If left undefined, the default listed above is
 * assumed.
 * @exception CCAException with the following types: PortAlreadyDefined, OutOfMemory.
 */
void
decaf::Services_impl::registerUsesPort_impl (
  /* in */const ::std::string& portName,
  /* in */const ::std::string& type,
  /* in */::gov::cca::TypeMap& properties ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.registerUsesPort)
  if ( d_providesPort.find( portName ) != d_providesPort.end() ) { 
    // uh-oh.  this portname is not unique
    ::std::string errorMsg = ::std::string("Specified usesPort name \"") + portName +
	"\" not unique to this instance.\n"
	"A providesPort already registered under that name";
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_BadPortName );
    gov::cca::CCAException ex = dex; 
    ex.setNote ( errorMsg );
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::registerUsesPort(...)");
    throw ex;
  } else if (d_usesPort.find( portName ) != d_usesPort.end()) { 
    // uh-oh.  this portname is not unique
    ::std::string errorMsg = ::std::string("Specified usesPort name \"") + portName +
	"\" not unique to this instance.\n" 
	"Another usesPort already registered under that name";
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_BadPortName );
    gov::cca::CCAException ex = dex; 
    ex.setNote ( errorMsg );
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::registerUsesPort(...)");
    throw ex;
  }
  gov::cca::Port empty;
  d_usesPort[ portName ] = std::make_pair(empty, properties);
  d_portType[ portName ] = type;
  // downcast to decaf functionality
  decaf::Framework fwk = sidl::babel_cast< decaf::Framework >(d_fwk); 
  if ( fwk._not_nil() ) { 
    if (fwk.isProvidedService(type)) {
      fwk.provideRequestedServices( d_componentID, portName, type );
    }
  } else { 
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
    gov::cca::CCAException ex = dex; 
    ex.setNote( "downcast from gov::cca::AbstractFramework to decaf::Framework failed!?");
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::registerUsesPort()" );
    throw ex;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.registerUsesPort)
}

/**
 *  
 * Notify the framework that a Port, previously registered by this
 * component but currently not in use, is no longer desired. 
 * Unregistering a port that is currently 
 * in use (i.e. an unreleased getPort() being outstanding) 
 * is an error.
 * @param portName The name of a registered Port.
 * @exception CCAException with the following types: UsesPortNotReleased, PortNotDefined.
 */
void
decaf::Services_impl::unregisterUsesPort_impl (
  /* in */const ::std::string& portName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.unregisterUsesPort)
  d_usesPort.erase(portName);
  d_portType.erase(portName);
  // DO-NOT-DELETE splicer.end(decaf.Services.unregisterUsesPort)
}

/**
 *  
 * Exposes a Port from this component to the framework.  
 * This Port is now available for the framework to connect 
 * to other components. 
 * @param inPort An abstract interface (tagged with CCA-ness
 * by inheriting from gov.cca.Port) the framework will
 * make available to other components.
 * 
 * @param portName string uniquely describing this port.  This string
 * must be unique for this component, over both uses and provides ports.
 * 
 * @param type string describing the type (class) of this port.
 * 
 * @param properties A TypeMap describing optional properties
 * associated with this port. This can be a null pointer, which
 * indicates an empty list of properties.  Properties may be
 * obtained from createTypeMap or any other source.  The properties
 * be copied into the framework, and subsequent changes to the
 * properties object will have no effect on the properties
 * associated with this port.
 * In these properties, all frameworks recognize at least the
 * following keys and values in implementing registerUsesPort:
 * <pre>
 * key:              standard values (in string form)     default
 * "MAX_CONNECTIONS" any nonnegative integer, "unlimited".   1
 * "MIN_CONNECTIONS" any integer > 0.                        0
 * "ABLE_TO_PROXY"   "true", "false"                      "false"
 * </pre>
 * The component is not expected to work if the framework
 * has not satisfied the connection requirements.
 * The framework is allowed to return an error if it
 * is incapable of meeting the connection requirements,
 * e.g. it does not implement multiple uses ports.
 * The caller of addProvidesPort is not obligated to define
 * these properties. If left undefined, the default listed above is
 * assumed.
 * @exception CCAException with the following types: PortAlreadyDefined, OutOfMemory.
 */
void
decaf::Services_impl::addProvidesPort_impl (
  /* in */::gov::cca::Port& inPort,
  /* in */const ::std::string& portName,
  /* in */const ::std::string& type,
  /* in */::gov::cca::TypeMap& properties ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.addProvidesPort)
  if ( d_providesPort.find( portName ) != d_providesPort.end() ) { 
    // uh-oh.  this portname is not unique
    ::std::string errorMsg = ::std::string("Specified providesPort name \"") + portName +
	"\" not unique to this instance.\n"
	"Another providesPort already registered under that name";
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_BadPortName );
    gov::cca::CCAException ex = dex; 
    ex.setNote ( errorMsg );
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::addProvidesPort(...)");
    throw ex;
  } else if (d_usesPort.find( portName ) != d_usesPort.end()) { 
    // uh-oh.  this portname is not unique
    ::std::string errorMsg = ::std::string("Specified providesPort name \"") + portName +
	"\" not unique to this instance.\n"
	"A usesPort already registered under that name";
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_BadPortName );
    gov::cca::CCAException ex = dex; 
    ex.setNote ( errorMsg );
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::addProvidesPort(...)");
    throw ex;
  } else if ( (!d_is_alias) && (!inPort.isType( type )) ) { // check only if svcs is not an alias for framework.
    // D'oh, cannot cast port to the declared type.
    ::std::string errorMsg = 
	::std::string("Port instance is not an instance of specified type \"") +
	type + "\"";
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_BadPortName );
    gov::cca::CCAException ex = dex; 
    ex.setNote ( errorMsg );
    ex.add( __FILE__, __LINE__, "decaf::Services_impl::addProvidesPort(...)");
    throw ex;
  }
  d_providesPort[ portName ] = std::make_pair( inPort, properties );
  d_portType[ portName ] = type;
  // DO-NOT-DELETE splicer.end(decaf.Services.addProvidesPort)
}

/**
 *  Returns the complete list of the properties for a Port.  This
 * includes the properties defined when the port was registered
 * (these properties can be modified by the framework), two special
 * properties "cca.portName" and "cca.portType", and any other
 * properties that the framework wishes to disclose to the component.
 * The framework may also choose to provide only the subset of input
 * properties (i.e. from addProvidesPort/registerUsesPort) that it
 * will honor.      
 */
::gov::cca::TypeMap
decaf::Services_impl::getPortProperties_impl (
  /* in */const ::std::string& name ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getPortProperties)
  if ( d_usesPort.find(name) != d_usesPort.end() ) { 
    return d_usesPort[name].second;
  } else if ( d_providesPort.find( name ) != d_providesPort.end() ) { 
    return d_providesPort[ name ].second;
  } else { 
    return 0;
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.getPortProperties)
}

/**
 *  Notifies the framework that a previously exposed Port is no longer 
 * available for use. The Port being removed must exist
 * until this call returns, or a CCAException may occur.
 * @param portName The name of a provided Port.
 * @exception PortNotDefined. In general, the framework will not dictate 
 * when the component chooses to stop offering services.
 */
void
decaf::Services_impl::removeProvidesPort_impl (
  /* in */const ::std::string& portName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.removeProvidesPort)
  d_providesPort.erase( portName );
  d_portType.erase( portName );
  // DO-NOT-DELETE splicer.end(decaf.Services.removeProvidesPort)
}

/**
 *  
 * Get a reference to the component to which this 
 * Services object belongs. 
 */
::gov::cca::ComponentID
decaf::Services_impl::getComponentID_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.Services.getComponentID)
  return d_componentID;
  // DO-NOT-DELETE splicer.end(decaf.Services.getComponentID)
}

/**
 *  Obtain a callback for component destruction.
 * @param callBack an object that implements the ComponentRelease
 * interface that will be called when the component is to be destroyed.
 * 
 * Register a callback to be executed when the component is going
 * to be destroyed.  During this callback, the Services object passed
 * through setServices will still be valid, but after all such
 * callbacks are made for a specific component, subsequent usage
 * of the Services object is not allowed/is undefined.
 */
void
decaf::Services_impl::registerForRelease_impl (
  /* in */::gov::cca::ComponentRelease& callBack ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.registerForRelease)
  decaf::Framework fwk = sidl::babel_cast< decaf::Framework >(d_fwk); //downcast for special access
  fwk.setInstanceRelease( d_componentID, callBack);
  // DO-NOT-DELETE splicer.end(decaf.Services.registerForRelease)
}

/**
 *  
 * Sign up to be told about connection activity.
 * connectionEventType must be one of the integer 
 * values defined iN ConnectionEvent. 
 */
void
decaf::Services_impl::addConnectionEventListener_impl (
  /* in */::gov::cca::ports::EventType et,
  /* in */::gov::cca::ports::ConnectionEventListener& cel ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.addConnectionEventListener)
  if ( et == gov::cca::ports::EventType_Error ) { 
    return;
  } else if ( et == gov::cca::ports::EventType_ALL ) { 
    addConnectionEventListener( gov::cca::ports::EventType_ConnectPending,    cel ) ;
    addConnectionEventListener( gov::cca::ports::EventType_Connected,         cel ) ;
    addConnectionEventListener( gov::cca::ports::EventType_DisconnectPending, cel ) ;
    addConnectionEventListener( gov::cca::ports::EventType_Disconnected,      cel ) ;
  } else { 
    d_listeners[ et ].remove_if( equality_predicate( cel ) );
    d_listeners[ et ].push_back( cel );
  }    
  // DO-NOT-DELETE splicer.end(decaf.Services.addConnectionEventListener)
}

/**
 *  
 * Ignore future ConnectionEvents of the given type.
 * connectionEventType must be one of the integer values 
 * defined in ConnectionEvent. 
 */
void
decaf::Services_impl::removeConnectionEventListener_impl (
  /* in */::gov::cca::ports::EventType et,
  /* in */::gov::cca::ports::ConnectionEventListener& cel ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.removeConnectionEventListener)
  if ( et == gov::cca::ports::EventType_Error ) { 
    return;
  } else if ( et == gov::cca::ports::EventType_ALL ) { 
    for ( listeners_t::iterator it = d_listeners.begin(); it != d_listeners.end(); ++it ) { 
      removeConnectionEventListener( (*it).first, cel );
    }
  } else { 
    d_listeners[ et ].remove_if( equality_predicate( cel ) );
  }
  // DO-NOT-DELETE splicer.end(decaf.Services.removeConnectionEventListener)
}

/**
 * Add a ServiceProvider that can be asked to produce service Port's
 * for other components to use subsequently.
 * True means success. False means that for some reason, the
 * provider isn't going to function. Possibly another server is doing
 * the job.
 */
bool
decaf::Services_impl::addService_impl (
  /* in */const ::std::string& serviceType,
  /* in */::gov::cca::ports::ServiceProvider& portProvider ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.addService)
  decaf::Framework fwk = sidl::babel_cast< decaf::Framework >(d_fwk); //downcast for special access
  fwk.addServiceProvider(serviceType, this->getComponentID(), 
			 portProvider);
  return true;
  // DO-NOT-DELETE splicer.end(decaf.Services.addService)
}

/**
 *  Add a "reusable" service gov.cca.Port for other components to use 
 * subsequently.
 */
bool
decaf::Services_impl::addSingletonService_impl (
  /* in */const ::std::string& serviceType,
  /* in */::gov::cca::Port& server ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.addSingletonService)
  decaf::Framework fwk = sidl::babel_cast< decaf::Framework >(d_fwk); //downcast for special access
  fwk.addServicePort(serviceType, server);
  return true;
  // DO-NOT-DELETE splicer.end(decaf.Services.addSingletonService)
}

/**
 *  Inform the framework that this service Port is no longer to
 * be used, subsequent to this call. 
 */
void
decaf::Services_impl::removeService_impl (
  /* in */const ::std::string& serviceType ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Services.removeService)
  decaf::Framework fwk = sidl::babel_cast< decaf::Framework >(d_fwk); //downcast for special access
  fwk.removeFromRegistry(serviceType);
  // DO-NOT-DELETE splicer.end(decaf.Services.removeService)
}


// DO-NOT-DELETE splicer.begin(decaf.Services._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.Services._misc)

