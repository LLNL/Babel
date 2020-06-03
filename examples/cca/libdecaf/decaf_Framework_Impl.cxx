// 
// File:          decaf_Framework_Impl.cxx
// Symbol:        decaf.Framework-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.Framework
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_Framework_Impl.hxx"

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
#ifndef included_gov_cca_ConnectionID_hxx
#include "gov_cca_ConnectionID.hxx"
#endif
#ifndef included_gov_cca_Port_hxx
#include "gov_cca_Port.hxx"
#endif
#ifndef included_gov_cca_Services_hxx
#include "gov_cca_Services.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
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
// DO-NOT-DELETE splicer.begin(decaf.Framework._includes)
#ifdef HAVE_NUMERIC_LIMITS
#include <limits>
#define LARGEST_INT std::numeric_limits<int32_t>::max()
#else 
#include <limits.h>
#define LARGEST_INT INT_MAX
#endif
#include <sstream>
#include "sidl_BaseClass.hxx"
#include "sidl_Loader.hxx"
#include "decaf_ComponentID.hxx"
#include "decaf_Services.hxx"
#include "decaf_TypeMap.hxx"
#include "decaf_CCAException.hxx"
#include "gov_cca_ports_EventType.hxx"
#include "gov_cca_ports_ConnectionEventService.hxx"
#include "sidl_DLL.hxx"
#include "sidl_Loader.hxx"
#include "sidl_Resolve.hxx"
#include "sidl_Scope.hxx"
// DO-NOT-DELETE splicer.end(decaf.Framework._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::Framework_impl::Framework_impl() : StubBase(reinterpret_cast< void*>(
  ::decaf::Framework::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.Framework._ctor2)
  // Insert-Code-Here {decaf.Framework._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.Framework._ctor2)
}

// user defined constructor
void decaf::Framework_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.Framework._ctor)
  addServicePort("gov.cca.ports.BuilderService", *this);
  // DO-NOT-DELETE splicer.end(decaf.Framework._ctor)
}

// user defined destructor
void decaf::Framework_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.Framework._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.Framework._dtor)
}

// static class initializer
void decaf::Framework_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.Framework._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.Framework._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Return the named port from the specified component id.
 */
::gov::cca::Port
decaf::Framework_impl::lookupPort_impl (
  /* in */::gov::cca::ComponentID& componentID,
  /* in */const ::std::string& portName ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.lookupPort)
  std::string instanceName = componentID.getInstanceName();
  gov::cca::Port port;
  if (d_instance.find(instanceName) != d_instance.end()) {
    decaf::Services services = sidl::babel_cast< decaf::Services > (d_instance[instanceName].svcs);
    port = services.getProvidesPort(portName);
  }
  return port;
  // DO-NOT-DELETE splicer.end(decaf.Framework.lookupPort)
}

/**
 * Check if this type is a framework supported service.
 */
bool
decaf::Framework_impl::isProvidedService_impl (
  /* in */const ::std::string& portType ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.isProvidedService)
  return ((portType == "gov.cca.ports.ConnectionEventService") ||
	  (portType == "gov.cca.ports.ServiceRegistry") ||
	  (d_serviceProviders.find(portType) != d_serviceProviders.end()) ||
	  (d_servicePorts.find(portType) != d_servicePorts.end()));
  // DO-NOT-DELETE splicer.end(decaf.Framework.isProvidedService)
}

/**
 * Framework creates providing component, registers provides port and connects to
 * using port for special cases (e.g. BuilderService)
 * @param type the string name of the port type 
 * (currently accepts only "gov.cca.ports.BuilderServices" and 
 * gov.cca.ports.ConnectionEventServices)
 * @param componentID the ID of the user component
 * @param portName the userPortName on the user component
 */
void
decaf::Framework_impl::provideRequestedServices_impl (
  /* in */::gov::cca::ComponentID& componentID,
  /* in */const ::std::string& portName,
  /* in */const ::std::string& type ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.provideRequestedServices)
  if ( type == "gov.cca.ports.ConnectionEventService" ) { 
    decaf::Services userSvcs = sidl::babel_cast< decaf::Services > (d_instance[componentID.getInstanceName()].svcs);
    // surprize! the services object itself provides the event services
    std::string uniqueName = p_getUniqueName( "connectionEventer" ); 
    gov::cca::Services svcs = getServices( uniqueName, type, 0 ); 
    svcs.addProvidesPort( userSvcs, "ConnectionEventService", type, 0 );
    this->connect( componentID, portName, svcs.getComponentID(), "ConnectionEventService" );
  }  
  else if (type == "gov.cca.ports.ServiceRegistry") {
    decaf::Services userSvcs = sidl::babel_cast< decaf::Services > (d_instance[componentID.getInstanceName()].svcs);
    // surprize! the services object itself provides the RegistryService
    std::string uniqueName = p_getUniqueName( "registryService" ); 
    gov::cca::Services svcs = getServices( uniqueName, type, 0 ); 
    svcs.addProvidesPort( userSvcs, "RegistryService", type, 0 );
    this->connect( componentID, portName, svcs.getComponentID(), "RegistryService" );
  }
  else if (d_servicePorts.find(type) != d_servicePorts.end()) {
    gov::cca::Port port = d_servicePorts[type];
    std::string uniqueName = p_getUniqueName("singletonPort"); // unique name
    gov::cca::Services svcs = getServices( uniqueName, type, 0);
    svcs.addProvidesPort(port, "AvailService", type, 0);
    this->connect( componentID, portName, svcs.getComponentID(), "AvailService" );
  }
  else if (d_serviceProviders.find(type) != d_serviceProviders.end()) {
    ProviderEntry pe = d_serviceProviders[type];
    gov::cca::ports::ServiceProvider sp = pe.d_provider;
    std::string portName = sp.createService(type);
    this->connect( componentID, portName, pe.d_id, portName);
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.provideRequestedServices)
}

/**
 * Method:  addServiceProvider[]
 */
void
decaf::Framework_impl::addServiceProvider_impl (
  /* in */const ::std::string& portType,
  /* in */::gov::cca::ComponentID& componentID,
  /* in */::gov::cca::ports::ServiceProvider& provider ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.addServiceProvider)
  ProviderEntry pe;
  pe.d_id = componentID;
  pe.d_provider = provider;
  d_serviceProviders.insert(make_pair(portType, pe));
  // DO-NOT-DELETE splicer.end(decaf.Framework.addServiceProvider)
}

/**
 * Method:  addServicePort[]
 */
void
decaf::Framework_impl::addServicePort_impl (
  /* in */const ::std::string& portType,
  /* in */::gov::cca::Port& port ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.addServicePort)
  d_servicePorts.insert(make_pair(portType, port));
  // DO-NOT-DELETE splicer.end(decaf.Framework.addServicePort)
}

/**
 * Method:  removeFromRegistry[]
 */
void
decaf::Framework_impl::removeFromRegistry_impl (
  /* in */const ::std::string& portType ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.removeFromRegistry)
  if (portType != "gov.cca.ports.BuilderService") {
    d_servicePorts.erase(portType);
    d_serviceProviders.erase(portType);
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.removeFromRegistry)
}

/**
 * Method:  setInstanceRelease[]
 */
void
decaf::Framework_impl::setInstanceRelease_impl (
  /* in */::gov::cca::ComponentID& cid,
  /* in */::gov::cca::ComponentRelease& callback ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.setInstanceRelease)
  std::string instanceName = cid.getInstanceName();
  d_instance[instanceName].release = callback;
  // DO-NOT-DELETE splicer.end(decaf.Framework.setInstanceRelease)
}

/**
 *  
 * Create an empty TypeMap. Presumably this would be used in 
 * an ensuing call to <code>getServices()</code>. The "normal" method of
 * creating typemaps is found in the <code>Services</code> interface. It
 * is duplicated here to break the "chicken and egg" problem.
 */
::gov::cca::TypeMap
decaf::Framework_impl::createTypeMap_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.createTypeMap)
  return decaf::TypeMap::_create();
  // DO-NOT-DELETE splicer.end(decaf.Framework.createTypeMap)
}

/**
 *  
 * Retrieve a Services handle to the underlying framework. 
 * This interface effectively causes the calling program to 
 * appear as the image of a component inside the framework.
 * This method may be called any number of times
 * with different arguments, creating a new component image 
 * each time. 
 * The only proper method to destroy a Services obtained 
 * from this interface is to pass it to releaseServices.
 * 
 * @param selfInstanceName the Component instance name,
 * as it will appear in the framework.
 * 
 * @param selfClassName the Component type of the 
 * calling program, as it will appear in the framework. 
 * 
 * @param selfProperties (which can be null) the properties 
 * of the component image to appear. 
 * 
 * @throws CCAException in the event that selfInstanceName 
 * is already in use by another component.
 * 
 * @return  A Services object that pertains to the
 * image of the this component. This is identical
 * to the object passed into Component.setServices() 
 * when a component is created.
 */
::gov::cca::Services
decaf::Framework_impl::getServices_impl (
  /* in */const ::std::string& selfInstanceName,
  /* in */const ::std::string& selfClassName,
  /* in */::gov::cca::TypeMap& selfProperties ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getServices)
  gov::cca::Component nil;
  decaf::ComponentID cid = decaf::ComponentID::_create();
  std::string uniqueName = p_getUniqueName( selfInstanceName );
  cid.initialize( uniqueName );
  decaf::Services svcs = decaf::Services::_create();
  svcs.initialize( *this, cid, selfProperties, true);
  d_instance[ uniqueName ].component = nil;
  d_instance[ uniqueName ].svcs = svcs; 
  d_aliases[ uniqueName ] = selfClassName ;
  return svcs;
  // DO-NOT-DELETE splicer.end(decaf.Framework.getServices)
}

/**
 *  
 * Inform framework that the <code>Services</code> handle is no longer needed by the 
 * caller and that the reference to its component image is to be
 * deleted from the context of the underlying framework. This invalidates
 * any <code>ComponentID</code>'s or <code>ConnectionID</code>'s associated 
 * with the given <code>Services</code>' component image. 
 * 
 * @param services The result of getServices earlier obtained.
 * 
 * @throws CCAException if the <code>Services</code>
 * handle has already been released or is otherwise rendered invalid 
 * or was not obtained from <code>getServices()</code>.
 */
void
decaf::Framework_impl::releaseServices_impl (
  /* in */::gov::cca::Services& services ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.releaseServices)
  ::std::cerr << "before release services" << ::std::endl;
  ::std::cerr << *this;
  
  if ( services._not_nil() ) { 
    gov::cca::ComponentID cid = services.getComponentID();
    std::string instanceName = cid.getInstanceName();
    ::std::cerr << "Releasing Component " << instanceName << ::std::endl;
    if ( d_aliases.find( cid.getInstanceName() ) != d_aliases.end() ) {
      int n_removed_instances = p_removeInstance( instanceName );
      int n_removed_aliases = d_aliases.erase( instanceName );
      if ( n_removed_instances != 1 || n_removed_aliases != 1 ) { 
	::std::cerr << "Error: #removed instances != #removed aliases != 1" << ::std::endl;
      }

    } else { 
      // should throw an exception, services not associated with
      // a known alias to the underslying framework
      // (i.e. not created by getServices)
      ::std::cerr << "Error: releaseServices() called on services object not created by getServices()" << ::std::endl;
    }
  } else { 
    // should throw an exception for services._is_nil();
    ::std::cerr << "Error: releaseServices() called on nil object" << ::std::endl;
  }
  ::std::cerr << "after  release services" << ::std::endl;
  ::std::cerr << *this;
  
  // DO-NOT-DELETE splicer.end(decaf.Framework.releaseServices)
}

/**
 *  
 * Tell the framework it is no longer needed and to clean up after itself. 
 * @throws CCAException if the framework has already been shutdown.
 */
void
decaf::Framework_impl::shutdownFramework_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.shutdownFramework)
  std::set<std::string> names;
  for (instancemap_t::iterator it = d_instance.begin(), end=d_instance.end();
       it != end; ++it ) { 
    names.insert( (*it).first );
  }
  for ( std::set<std::string>::iterator it = names.begin();
	it != names.end(); ++it ) { 
    p_removeInstance( *it );
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.shutdownFramework)
}

/**
 *  
 * Creates a new framework instance based on the same underlying 
 * framework implementation. This does not copy the existing 
 * framework, nor are any of the user-instantiated components in
 * the original framework available in the newly created 
 * <code>AbstractFramework</code>. 
 * 
 * @throws CCAException when one of the following conditions occur:
 * 
 * (1)the AbstractFramework previously had shutdownFramework() called on it, or 
 * (2)the underlying framework implementation does not permit creation 
 * of another instance.	 
 */
::gov::cca::AbstractFramework
decaf::Framework_impl::createEmptyFramework_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.createEmptyFramework)
  return decaf::Framework::_create();
  // DO-NOT-DELETE splicer.end(decaf.Framework.createEmptyFramework)
}

/**
 * Creates an instance of a CCA component of the type defined by the 
 * string className.  The string classname uniquely defines the
 * "type" of the component, e.g.
 * doe.cca.Library.GaussianElmination. 
 * It has an instance name given by the string instanceName.
 * The instanceName may be empty (zero length) in which case
 * the instanceName will be assigned to the component automatically.
 * @throws CCAException If the Component className is unknown, or if the
 * instanceName has already been used, a CCAException is thrown.
 * @return A ComponentID corresponding to the created component. Destroying
 * the returned ID does not destroy the component; 
 * see destroyInstance instead.
 */
::gov::cca::ComponentID
decaf::Framework_impl::createInstance_impl (
  /* in */const ::std::string& instanceName,
  /* in */const ::std::string& className,
  /* in */::gov::cca::TypeMap& properties ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.createInstance)
  decaf::ComponentID cid = decaf::ComponentID::_create();
  ::sidl::DLL dll = ::sidl::Loader::findLibrary
      ( className, "ior/impl", ::sidl::Scope_SCLSCOPE, ::sidl::Resolve_SCLRESOLVE);
  if (dll._not_nil()) {
    sidl::BaseClass sidl_class = dll.createClass(className);
    gov::cca::Component component = sidl::babel_cast< gov::cca::Component > (sidl_class); // downcast
    if ( component._not_nil() ) { 
      std::string uniqueName = p_getUniqueName( instanceName );  
      cid.initialize( uniqueName );
      decaf::Services svc = decaf::Services::_create();
      svc.initialize( *this, cid, properties, false );
      d_instance[ uniqueName ].component = component;
      d_instance[ uniqueName ].svcs = svc;
      component.setServices( svc );
    }
    else {
      // should throw an exception to indicate ctor failure
      ::std::cerr << "Error: createInstance dll doesn't have constructor" << ::std::endl;
      
    }
  }
  else {
    // should throw exception to indicate failed dll search
    ::std::cerr << "Error: createInstance library not found" << ::std::endl;
  }

  return cid;
  // DO-NOT-DELETE splicer.end(decaf.Framework.createInstance)
}

/**
 *  
 * Get component list.
 * @return a ComponentID for each component currently created.
 */
::sidl::array< ::gov::cca::ComponentID>
decaf::Framework_impl::getComponentIDs_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getComponentIDs)
  int len = d_instance.size();
  sidl::array<gov::cca::ComponentID> retval = 
    sidl::array<gov::cca::ComponentID>::create1d( len );
  int i=0;
  for ( instancemap_t::iterator it=d_instance.begin(); 
	it!= d_instance.end(); 
	++it, ++i ) {
    retval.set( i, (*it).second.svcs.getComponentID() );
  }
  return retval;
  // DO-NOT-DELETE splicer.end(decaf.Framework.getComponentIDs)
}

/**
 *  
 * Get property map for component.
 * @return the public properties associated with the component referred to by
 * ComponentID. 
 * @throws a CCAException if the ComponentID is invalid.
 */
::gov::cca::TypeMap
decaf::Framework_impl::getComponentProperties_impl (
  /* in */::gov::cca::ComponentID& cid ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getComponentProperties)
  std::string instanceName = cid.getInstanceName();
  if ( d_instance.find( instanceName ) != d_instance.end() ) { 
    decaf::Services svcs = sidl::babel_cast< decaf::Services > (d_instance[ instanceName ].svcs);
    if ( svcs._not_nil() ) { 
      return svcs.getInstanceProperties();
    }
  }

  /*  
   * To Do...This is a "quickie fix" to get beyond the complaint of no
   *          return here but should give more thought as to a better
   *          solution (e.g., null pointer?).
   */
  decaf::CCAException dex = decaf::CCAException::_create();
  dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
  gov::cca::CCAException ex = dex;
  ex.setNote("Unable to get properties");
  ex.add( __FILE__, __LINE__, 
                "decaf::Framework_impl::getComponentProperties()");
  throw ex;
  // DO-NOT-DELETE splicer.end(decaf.Framework.getComponentProperties)
}

/**
 * Causes the framework implementation to associate the given properties 
 * with the component designated by cid. 
 * @throws CCAException if cid is invalid or if there is an attempted
 * change to a property locked by the framework implementation.
 */
void
decaf::Framework_impl::setComponentProperties_impl (
  /* in */::gov::cca::ComponentID& cid,
  /* in */::gov::cca::TypeMap& map ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.setComponentProperties)
  std::string instanceName = cid.getInstanceName();
  if ( d_instance.find( instanceName ) != d_instance.end() ) {
    // downcast to access decaf-specific routines
    decaf::Services svcs =  sidl::babel_cast< decaf::Services > (d_instance[ instanceName ].svcs);
    if ( svcs._not_nil() ) { 
      svcs.setInstanceProperties( map );
    }
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.setComponentProperties)
}

/**
 *  Get component id from stringified reference.
 * @return a ComponentID from the string produced by 
 * ComponentID.getSerialization(). 
 * @throws CCAException if the string does not represent the appropriate 
 * serialization of a ComponentID for the underlying framework.
 */
::gov::cca::ComponentID
decaf::Framework_impl::getDeserialization_impl (
  /* in */const ::std::string& s ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getDeserialization)
  decaf::CCAException dex = decaf::CCAException::_create();
  dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
  gov::cca::CCAException ex = dex;
  ex.setNote("Sorry: Decaf does not implement component serialization/deserialization");
  ex.add( __FILE__, __LINE__, "decaf::Framework_impl::getProvidedPortNames()");
  throw ex;
  // DO-NOT-DELETE splicer.end(decaf.Framework.getDeserialization)
}

/**
 *  Get id from name by which it was created.
 * @return a ComponentID from the instance name of the component
 * produced by ComponentID.getInstanceName().
 * @throws CCAException if there is no component matching the 
 * given componentInstanceName.
 */
::gov::cca::ComponentID
decaf::Framework_impl::getComponentID_impl (
  /* in */const ::std::string& componentInstanceName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getComponentID)
  if ( d_instance.find( componentInstanceName ) != d_instance.end() ) { 
    return d_instance[ componentInstanceName ].svcs.getComponentID();
  } else { 
    return 0;
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.getComponentID)
}

/**
 *  
 * Eliminate the Component instance, from the scope of the framework.
 * @param toDie the component to be removed.
 * @param timeout the allowable wait; 0 means up to the framework.
 * @throws CCAException if toDie refers to an invalid component, or
 * if the operation takes longer than timeout seconds.
 */
void
decaf::Framework_impl::destroyInstance_impl (
  /* in */::gov::cca::ComponentID& toDie,
  /* in */float timeout ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.destroyInstance)
  p_removeInstance( toDie.getInstanceName() );
  // DO-NOT-DELETE splicer.end(decaf.Framework.destroyInstance)
}

/**
 *  
 * Get the names of Port instances provided by the identified component.
 * @param cid the component.
 * @throws CCAException if cid refers to an invalid component.
 */
::sidl::array< ::std::string>
decaf::Framework_impl::getProvidedPortNames_impl (
  /* in */::gov::cca::ComponentID& cid ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getProvidedPortNames)
  std::string instanceName = cid.getInstanceName();
  if ( d_instance.find( instanceName ) != d_instance.end() ) { 
    decaf::Services svcs = sidl::babel_cast< decaf::Services > (d_instance[ instanceName ].svcs);
    if ( svcs._not_nil() ) { 
      return svcs.getProvidedPortNames();
    } else { 
      decaf::CCAException dex = decaf::CCAException::_create();
      dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
      gov::cca::CCAException ex = dex;
      ex.setNote("downcast from gov::cca::Services to decaf::Services failed");
      ex.add( __FILE__, __LINE__, "decaf::Framework_impl::getProvidedPortNames()");
      throw ex;
    } 
  } else { 
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
    gov::cca::CCAException ex = dex;
    ::std::string errorMsg = ::std::string("componentID with instanceName=\"") +
	instanceName + "\" not found.";
    ex.setNote(errorMsg);
    ex.add( __FILE__, __LINE__, "decaf::Framework_impl::getProvidedPortNames()");
    throw ex;
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.getProvidedPortNames)
}

/**
 *  
 * Get the names of Port instances used by the identified component.
 * @param cid the component.
 * @throws CCAException if cid refers to an invalid component. 
 */
::sidl::array< ::std::string>
decaf::Framework_impl::getUsedPortNames_impl (
  /* in */::gov::cca::ComponentID& cid ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getUsedPortNames)
  std::string instanceName = cid.getInstanceName();
  if ( d_instance.find( instanceName ) != d_instance.end() ) { 
    decaf::Services svcs = sidl::babel_cast< decaf::Services > (d_instance[ instanceName ].svcs);
    if ( svcs._not_nil() ) { 
      return svcs.getUsedPortNames();
    } else { 
      decaf::CCAException dex =  decaf::CCAException::_create();
      dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
      gov::cca::CCAException ex = dex;
      ex.setNote("downcast from gov::cca::Services to decaf::Services failed");
      ex.add( __FILE__, __LINE__, "decaf::Framework_impl::getUsedPortNames()");
      throw ex;
    } 
  } else { 
    decaf::CCAException dex = decaf::CCAException::_create();
    dex.setCCAExceptionType( gov::cca::CCAExceptionType_Nonstandard );
    gov::cca::CCAException ex = dex;
    ::std::string errorMsg = ::std::string("componentID with instanceName=\"") +
	instanceName + "\" not found.";
    ex.setNote(errorMsg);
    ex.add( __FILE__, __LINE__, "decaf::Framework_impl::getUsedPortNames()");
    throw ex;
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.getUsedPortNames)
}

/**
 *  
 * Fetch map of Port properties exposed by the framework.
 * @return the public properties pertaining to the Port instance 
 * portname on the component referred to by cid. 
 * @throws CCAException when any one of the following conditions occur:<ul>
 * <li>portname is not a registered Port on the component indicated by cid,
 * <li>cid refers to an invalid component. </ul>
 */
::gov::cca::TypeMap
decaf::Framework_impl::getPortProperties_impl (
  /* in */::gov::cca::ComponentID& cid,
  /* in */const ::std::string& portName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getPortProperties)
  std::string instanceName = cid.getInstanceName();
  if ( d_instance.find( instanceName ) != d_instance.end() ) { 
    decaf::Services svcs = sidl::babel_cast< decaf::Services > (d_instance[ instanceName ].svcs);
    if ( svcs._not_nil() ) { 
      return svcs.getPortProperties( portName );
    }
  }
  return 0;
  // DO-NOT-DELETE splicer.end(decaf.Framework.getPortProperties)
}

/**
 *  
 * Associates the properties given in map with the Port indicated by 
 * portname. The component must have a Port known by portname.
 * @throws CCAException if either cid or portname are
 * invalid, or if this a changed property is locked by 
 * the underlying framework or component.
 */
void
decaf::Framework_impl::setPortProperties_impl (
  /* in */::gov::cca::ComponentID& cid,
  /* in */const ::std::string& portName,
  /* in */::gov::cca::TypeMap& map ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.setPortProperties)
  std::string instanceName = cid.getInstanceName();
  if ( d_instance.find( instanceName ) != d_instance.end() ) { 
    decaf::Services svcs = sidl::babel_cast< decaf::Services > (d_instance[ instanceName].svcs);
    if ( svcs._not_nil() ) { 
      svcs.setPortProperties( portName, map );
    }
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.setPortProperties)
}

/**
 * Creates a connection between ports on component user and 
 * component provider. Destroying the ConnectionID does not
 * cause a disconnection; for that, see disconnect().
 * @throws CCAException when any one of the following conditions occur:<ul>
 * <li>If either user or provider refer to an invalid component,
 * <li>If either usingPortName or providingPortName refer to a 
 * nonexistent Port on their respective component,
 * <li>If other-- In reality there are a lot of things that can go wrong 
 * with this operation, especially if the underlying connections 
 * involve networking.</ul>
 */
::gov::cca::ConnectionID
decaf::Framework_impl::connect_impl (
  /* in */::gov::cca::ComponentID& user,
  /* in */const ::std::string& usingPortName,
  /* in */::gov::cca::ComponentID& provider,
  /* in */const ::std::string& providingPortName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.connect)
  decaf::ConnectionID connectID;
  std::string userName = user.getInstanceName();
  std::string provName = provider.getInstanceName();
  if ( ( d_instance.find( userName ) != d_instance.end() ) &&
       ( d_instance.find( provName ) != d_instance.end() ) ) { // iff both ports exist.
	decaf::Services userSvc = sidl::babel_cast< decaf::Services > (d_instance[ userName ].svcs);
	decaf::Services provSvc = sidl::babel_cast< decaf::Services > (d_instance[ provName ].svcs);
	// Yikes!!!! should check if types match.
	// okay, fire a connect pending.  Connections fired at providers first, users 2nd
	provSvc.notifyConnectionEvent( providingPortName, gov::cca::ports::EventType_ConnectPending );
	userSvc.notifyConnectionEvent( usingPortName, gov::cca::ports::EventType_ConnectPending );
	gov::cca::Port port = provSvc.getProvidesPort( providingPortName );
	if ( port._not_nil() ) {
	  userSvc.bindPort( usingPortName, port );
	  connectID = decaf::ConnectionID::_create();
	  connectID.initialize( user, usingPortName, provider, providingPortName, 0);
	  d_instance[ userName ].usesConnection[ usingPortName ] = connectID;
	  d_instance[ provName ].providesConnection[ providingPortName ].insert( connectID );
	  provSvc.notifyConnectionEvent( providingPortName, gov::cca::ports::EventType_Connected );
	  userSvc.notifyConnectionEvent( usingPortName, gov::cca::ports::EventType_Connected );
	}
  }
  return connectID;
  // DO-NOT-DELETE splicer.end(decaf.Framework.connect)
}

/**
 *  Returns a list of connections as an array of 
 * handles. This will return all connections involving components 
 * in the given componentList of ComponentIDs. This
 * means that ConnectionID's will be returned even if only one 
 * of the participants in the connection appears in componentList.
 * 
 * @throws CCAException if any component in componentList is invalid.
 */
::sidl::array< ::gov::cca::ConnectionID>
decaf::Framework_impl::getConnectionIDs_impl (
  /* in array<gov.cca.ComponentID> */::sidl::array< ::gov::cca::ComponentID>& 
    componentList ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getConnectionIDs)
  cidset_t cache; //sets don't allow duplication

  // for all components in list
  for (int i=componentList.lower(0); i<componentList.upper(0); ++i ) { 
    std::string instanceName = componentList.get(i).getInstanceName();

    // for all uses connections (1 per portName)
    for ( cidmap_t::iterator it = d_instance[instanceName].usesConnection.begin(),
	    end = d_instance[instanceName].usesConnection.end();
	  it != end; ++it ) { 
      cache.insert( (*it).second );
    }
    
    // for all provides connections (possibly many per port name)
    for ( cidmapset_t::iterator it2 = d_instance[instanceName].providesConnection.begin(),
	    end2 = d_instance[instanceName].providesConnection.end();
	  it2 != end2; ++it2 ) { 
      for ( cidset_t::iterator it3 = (*it2).second.begin(), end3 = (*it2).second.end();
	    it3 != end3; ++it3 ) { 
	cache.insert( *it3 );
      }      
    }
  }
  
  // now copy cache into a sidl array
  int len = cache.size();
  ::sidl::array< ::gov::cca::ConnectionID> retval = 
      ::sidl::array< ::gov::cca::ConnectionID>::create1d( len );

  cidset_t::iterator cur = cache.begin();
  for ( int i=0; i < len; ++i ) { 
    retval.set( i, *cur );
    ++cur;
  }

  return retval;
  // DO-NOT-DELETE splicer.end(decaf.Framework.getConnectionIDs)
}

/**
 * Fetch property map of a connection.
 * @returns the properties for the given connection.
 * @throws CCAException if connID is invalid.
 */
::gov::cca::TypeMap
decaf::Framework_impl::getConnectionProperties_impl (
  /* in */::gov::cca::ConnectionID& connID ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.getConnectionProperties)
  decaf::ConnectionID cid = sidl::babel_cast< decaf::ConnectionID > (connID);
  if ( cid._not_nil() ) { 
    return cid.getProperties();
  } else { 
    return 0;
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.getConnectionProperties)
}

/**
 *  Associates the properties with the connection.
 * @param map the source of the properties.
 * @param connID connection to receive property values.
 * @throws CCAException if connID is invalid, or if this changes 
 * a property locked by the underlying framework.
 */
void
decaf::Framework_impl::setConnectionProperties_impl (
  /* in */::gov::cca::ConnectionID& connID,
  /* in */::gov::cca::TypeMap& map ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.setConnectionProperties)
  std::string userName = connID.getUser().getInstanceName();
  if ( d_instance.find( userName ) != d_instance.end() ) { 
    std::string userPortName = connID.getUserPortName();
    cidmap_t &lev2 = d_instance[ userName ].usesConnection;
    if ( lev2.find( userPortName ) != lev2.end() ) { 
      // downcast to access decaf-specifics
      decaf::ConnectionID connID = sidl::babel_cast< decaf::ConnectionID >(lev2[ userPortName ]);
      if ( connID._not_nil() ) { 
	connID.setProperties( map );
      }
    }
  }
  // DO-NOT-DELETE splicer.end(decaf.Framework.setConnectionProperties)
}

/**
 *  Disconnect the connection indicated by connID before the indicated
 * timeout in secs. Upon successful completion, connID and the connection
 * it represents become invalid. 
 * @param timeout the time in seconds to wait for a connection to close; 0
 * means to use the framework implementation default.
 * @param connID the connection to be broken.
 * @throws CCAException when any one of the following conditions occur: <ul>
 * <li>id refers to an invalid ConnectionID,
 * <li>timeout is exceeded, after which, if id was valid before 
 * disconnect() was invoked, it remains valid
 * </ul>
 */
void
decaf::Framework_impl::disconnect_impl (
  /* in */::gov::cca::ConnectionID& connID,
  /* in */float timeout ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.disconnect)
  std::string userName = connID.getUser().getInstanceName();
  std::string userPortName = connID.getUserPortName();
  std::string provName = connID.getProvider().getInstanceName();
  std::string provPortName = connID.getProviderPortName();
  decaf::Services userSvcs;
  decaf::Services provSvcs;

  ::std::cerr << "Disconnecting " << userName << "." << userPortName << "-->"
	      << provName << "." << provPortName << ::std::flush;

  int n_removed_user=0;
  int n_removed_provider=0;
  if (d_instance.find( userName ) != d_instance.end() ) { 
    userSvcs = sidl::babel_cast< decaf::Services >(d_instance[userName].svcs);
  } else { 
    ::std::cerr << "Error: void decaf::Framework_impl::disconnect(): " 
		<< __LINE__ << "  no instance named \"" << userName 
		<< "\" found to remove using \"" << userPortName 
		<< "\" connection." << ::std::endl;
    ::std::cerr << *this;
  }
  if (d_instance.find( provName ) != d_instance.end() ) { 
    provSvcs = sidl::babel_cast< decaf::Services >(d_instance[provName].svcs);
  } else { 
    ::std::cerr << "Error: void decaf::Framework_impl::disconnect(): " 
		<< __LINE__ << "  no instance named \"" << provName 
		<< "\" found to remove  providing\"" << provPortName 
		<< "\" connection" << ::std::endl;

    ::std::cerr << *this;

  }
  if ( provSvcs._not_nil() && userSvcs._not_nil() ) { 
    userSvcs.notifyConnectionEvent( userPortName, gov::cca::ports::EventType_DisconnectPending );
    provSvcs.notifyConnectionEvent( provPortName, gov::cca::ports::EventType_DisconnectPending );
    if ( d_instance.find( userName ) != d_instance.end() ) { 
      n_removed_user = d_instance[userName].usesConnection.erase( userPortName );
      if ( d_instance.find( provName ) != d_instance.end() &&
	   ( d_instance[provName].providesConnection.find(provPortName) != 
	     d_instance[provName].providesConnection.end() ) ) { 
	n_removed_provider = d_instance[provName].providesConnection[provPortName].erase( connID );
	if ( d_instance[provName].providesConnection[provPortName].size() == 0 ) { 
	  d_instance[provName].providesConnection.erase( provPortName );
	}
      }
      userSvcs.notifyConnectionEvent( userPortName, gov::cca::ports::EventType_Disconnected );
      provSvcs.notifyConnectionEvent( provPortName, gov::cca::ports::EventType_Disconnected );
    }
  }
  ::std::cerr << "  (done) " << ::std::endl;
  // assert ( n_removed_user == n_removed_provider == 1 )
  // DO-NOT-DELETE splicer.end(decaf.Framework.disconnect)
}

/**
 *  Remove all connections between components id1 and id2 within 
 * the period of timeout secs. If id2 is null, then all connections 
 * to id1 are removed (within the period of timeout secs).
 * @throws CCAException when any one of the following conditions occur:<ul>
 * <li>id1 or id2 refer to an invalid ComponentID (other than id2 == null),
 * <li>The timeout period is exceeded before the disconnections can be made. 
 * </ul>
 */
void
decaf::Framework_impl::disconnectAll_impl (
  /* in */::gov::cca::ComponentID& id1,
  /* in */::gov::cca::ComponentID& id2,
  /* in */float timeout ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.Framework.disconnectAll)
  std::string userName = id1.getInstanceName();
  std::string provName = id2.getInstanceName();
  cidset_t cache;
  if ( d_instance.find( userName ) != d_instance.end() ) { 
    for ( cidmap_t::iterator it = d_instance[userName].usesConnection.begin(),
	    end = d_instance[userName].usesConnection.end();
	  it != end; ++it ) { 
      if ((*it).second.getProvider().getInstanceName() == provName ) { 
	cache.insert( (*it).second );
      }
    }
  }
  for ( cidset_t::iterator it = cache.begin();
	it != cache.end();
	++it ) { 
    disconnect( *it, timeout );
  }

  // DO-NOT-DELETE splicer.end(decaf.Framework.disconnectAll)
}


// DO-NOT-DELETE splicer.begin(decaf.Framework._misc)
std::string
decaf::Framework_impl::p_getUniqueName (
      /*in*/ const std::string& requestedName ) {
  std::string name = requestedName;
  if (d_instance.find( name ) != d_instance.end() ) { 
    for (int i=0; i<LARGEST_INT; ++i) { 
      ::std::ostringstream concat;
      concat << requestedName << i;
      name = concat.str();
      if (d_instance.find( name ) == d_instance.end() ) { 
	break;
      }
    }
  }
  return name;
}

int 
decaf::Framework_impl::p_removeInstance( const std::string& instanceName ) { 
  // removes the named instance, and all of its connections
  // returns 0 if instance is not found, 1 if it is

  // 1. cache all connectionID's
  cidset_t cache;
  for ( cidmap_t::iterator it = d_instance[instanceName].usesConnection.begin(),
	  end = d_instance[instanceName].usesConnection.end();
	it != end; ++it ) { 
    cache.insert((*it).second);
  }

  // 2. break all connections
  for ( cidset_t::iterator it = cache.begin();
	it != cache.end(); ++it ) { 
    disconnect( *it, 0.0 );
  }
  
  // 3. remove the instance itself  
  // (pre 0.6.3 standard did "d_instance[instanceName].component.setServices(0);")
  instance_t& instance = d_instance[instanceName];
  
  if ( instance.release._not_nil() ) { 
    instance.release.releaseServices( instance.svcs );
  }
  return d_instance.erase( instanceName );
}

::std::ostream&
decaf::operator<<( ::std::ostream& os, decaf::Framework_impl& fwk ) { 
  
  os << "framework = {" << ::std::endl;

  // for each component instance
  for ( decaf::Framework_impl::instancemap_t::iterator it = fwk.d_instance.begin();
	it != fwk.d_instance.end(); ++it ) { 
    os << "    Component \"" << (*it).first <<"\" { " << ::std::endl;
    if ( (*it).second.component._not_nil() ) { 
      os << "        Type=" << (*it).second.component.getClassInfo().getName() << ";" << ::std::endl;
    } else { 
      os << "        Type=(proxy);" << ::std::endl;
    }
    os << "        needsRelease=" << ( (*it).second.release._not_nil()  ? "yes;" : "no;" )<< ::std::endl;
    os << "        isAlias=" << ( fwk.d_aliases.find( (*it).first ) != fwk.d_aliases.end()  ? "yes;" : "no;" ) << ::std::endl;

    decaf::Framework_impl::cidmap_t& uses = (*it).second.usesConnection;
    if ( uses.size() == 0 ) { 
      os << "        UsesConnections={}" << ::std::endl;
    } else { 
      os << "        UsesConnections={" << ::std::endl;
      for ( decaf::Framework_impl::cidmap_t::iterator j=uses.begin(); j != uses.end(); ++j ) { 
	os << "            \"" << (*j).first << "\" --> \"" << (*j).second.getProvider().getInstanceName() 
	   << "." << (*j).second.getProviderPortName() << "\"" << ::std::endl;
      }
      os << "        } // end UsesConnections" << ::std::endl;
    }

    decaf::Framework_impl::cidmapset_t& provs = (*it).second.providesConnection;
    if ( provs.size() == 0 ) { 
      os << "        ProvidesConnections={}" << ::std::endl;
    } else { 
      os << "        ProvidesConnections={" << ::std::endl;
      // for each provides port
      for ( decaf::Framework_impl::cidmapset_t::iterator j=provs.begin(); j != provs.end(); ++j ) { 
	decaf::Framework_impl::cidset_t& links = (*j).second;
	os << "            { " << ::std::endl;
	// for each link to a provides port
	for ( decaf::Framework_impl::cidset_t::iterator k=links.begin(); k!=links.end(); ++k ) { 
	  gov::cca::ConnectionID& nonconst = const_cast< gov::cca::ConnectionID&>( *k );
	  os << "                \"" << nonconst.getUser().getInstanceName() << "." << nonconst.getUserPortName() << "\" " << ::std::endl;
	} // end for each link to a provides port
	os << "            } --> \"" << (*j).first << "\"" << ::std::endl;
      } // end for each provides port
      os << "        } // end ProvidesConnections" << ::std::endl;
    }


    os << "    }" << ::std::endl;
  } // end for each component instance
  os << "} // end framework" << ::std::endl;
  return os;
}
// DO-NOT-DELETE splicer.end(decaf.Framework._misc)

