// 
// File:          decaf_ComponentID_Impl.cxx
// Symbol:        decaf.ComponentID-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ComponentID
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_ComponentID_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
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
// DO-NOT-DELETE splicer.begin(decaf.ComponentID._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ComponentID._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::ComponentID_impl::ComponentID_impl() : StubBase(reinterpret_cast< void*>(
  ::decaf::ComponentID::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID._ctor2)
  // Insert-Code-Here {decaf.ComponentID._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.ComponentID._ctor2)
}

// user defined constructor
void decaf::ComponentID_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ComponentID._ctor)
}

// user defined destructor
void decaf::ComponentID_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ComponentID._dtor)
}

// static class initializer
void decaf::ComponentID_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.ComponentID._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  initialize[]
 */
void
decaf::ComponentID_impl::initialize_impl (
  /* in */const ::std::string& name ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID.initialize)
  d_name = name;
  // DO-NOT-DELETE splicer.end(decaf.ComponentID.initialize)
}

/**
 *  
 * Returns the instance name provided in 
 * <code>BuilderService.createInstance()</code>
 * or in 
 * <code>AbstractFramework.getServices()</code>.
 * @throws CCAException if <code>ComponentID</code> is invalid
 */
::std::string
decaf::ComponentID_impl::getInstanceName_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID.getInstanceName)
  return d_name;
  // DO-NOT-DELETE splicer.end(decaf.ComponentID.getInstanceName)
}

/**
 * Returns a framework specific serialization of the ComponentID.
 * @throws CCAException if <code>ComponentID</code> is
 * invalid.
 */
::std::string
decaf::ComponentID_impl::getSerialization_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID.getSerialization)
  return d_name;
  // DO-NOT-DELETE splicer.end(decaf.ComponentID.getSerialization)
}


// DO-NOT-DELETE splicer.begin(decaf.ComponentID._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ComponentID._misc)

