// 
// File:          decaf_ConnectionID_Impl.cxx
// Symbol:        decaf.ConnectionID-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ConnectionID
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_ConnectionID_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentID_hxx
#include "gov_cca_ComponentID.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
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
// DO-NOT-DELETE splicer.begin(decaf.ConnectionID._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ConnectionID._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::ConnectionID_impl::ConnectionID_impl() : StubBase(reinterpret_cast< 
  void*>(::decaf::ConnectionID::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID._ctor2)
  // Insert-Code-Here {decaf.ConnectionID._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID._ctor2)
}

// user defined constructor
void decaf::ConnectionID_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID._ctor)
}

// user defined destructor
void decaf::ConnectionID_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID._dtor)
}

// static class initializer
void decaf::ConnectionID_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  initialize[]
 */
void
decaf::ConnectionID_impl::initialize_impl (
  /* in */::gov::cca::ComponentID& user,
  /* in */const ::std::string& userPortName,
  /* in */::gov::cca::ComponentID& provider,
  /* in */const ::std::string& providerPortName,
  /* in */::gov::cca::TypeMap& properties ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.initialize)
  d_provider = provider;
  d_providerPortName = providerPortName;
  d_user = user;
  d_userPortName = userPortName; 
  d_properties = properties;
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.initialize)
}

/**
 * Method:  setProperties[]
 */
void
decaf::ConnectionID_impl::setProperties_impl (
  /* in */::gov::cca::TypeMap& properties ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.setProperties)
  d_properties = properties;
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.setProperties)
}

/**
 * Method:  getProperties[]
 */
::gov::cca::TypeMap
decaf::ConnectionID_impl::getProperties_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.getProperties)
  return d_properties.cloneTypeMap();
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.getProperties)
}

/**
 *  
 * Get the providing component (callee) ID.
 * @return ComponentID of the component that has 
 * provided the Port for this connection. 
 * @throws CCAException if the underlying connection 
 * is no longer valid.
 */
::gov::cca::ComponentID
decaf::ConnectionID_impl::getProvider_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.getProvider)
  return d_provider;
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.getProvider)
}

/**
 *  
 * Get the using component (caller) ID.
 * @return ComponentID of the component that is using the provided Port.
 * @throws CCAException if the underlying connection is no longer valid.
 */
::gov::cca::ComponentID
decaf::ConnectionID_impl::getUser_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.getUser)
  return d_user;
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.getUser)
}

/**
 *  
 * Get the port name in the providing component of this connection.
 * @return the instance name of the provided Port.
 * @throws CCAException if the underlying connection is no longer valid.
 */
::std::string
decaf::ConnectionID_impl::getProviderPortName_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.getProviderPortName)
  return d_providerPortName;
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.getProviderPortName)
}

/**
 *  
 * Get the port name in the using component of this connection.
 * Return the instance name of the Port registered for use in 
 * this connection.
 * @throws CCAException if the underlying connection is no longer valid.
 */
::std::string
decaf::ConnectionID_impl::getUserPortName_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID.getUserPortName)
  return d_userPortName;
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID.getUserPortName)
}


// DO-NOT-DELETE splicer.begin(decaf.ConnectionID._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ConnectionID._misc)

