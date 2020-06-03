// 
// File:          decaf_ports_ComponentRepository_Impl.cxx
// Symbol:        decaf.ports.ComponentRepository-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ports.ComponentRepository
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_ports_ComponentRepository_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentClassDescription_hxx
#include "gov_cca_ComponentClassDescription.hxx"
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
// DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::ports::ComponentRepository_impl::ComponentRepository_impl() : StubBase(
  reinterpret_cast< void*>(::decaf::ports::ComponentRepository::_wrapObj(
  reinterpret_cast< void*>(this))),false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._ctor2)
  // Insert-Code-Here {decaf.ports.ComponentRepository._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._ctor2)
}

// user defined constructor
void decaf::ports::ComponentRepository_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._ctor)
}

// user defined destructor
void decaf::ports::ComponentRepository_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._dtor)
}

// static class initializer
void decaf::ports::ComponentRepository_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 *  
 * Collect the currently obtainable class name strings from
 * factories known to the builder and the from the
 * already instantiated components.
 * @return The list of class description, which may be empty, that are
 * known a priori to contain valid values for the className
 * argument of createInstance. 
 * @throws CCAException in the event of error.
 */
::sidl::array< ::gov::cca::ComponentClassDescription>
decaf::ports::ComponentRepository_impl::getAvailableComponentClasses_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository.getAvailableComponentClasses)
  return 0;
  // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository.getAvailableComponentClasses)
}


// DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._misc)

