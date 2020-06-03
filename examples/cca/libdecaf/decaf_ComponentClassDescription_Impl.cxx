// 
// File:          decaf_ComponentClassDescription_Impl.cxx
// Symbol:        decaf.ComponentClassDescription-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ComponentClassDescription
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_ComponentClassDescription_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::ComponentClassDescription_impl::ComponentClassDescription_impl() : 
  StubBase(reinterpret_cast< void*>(
  ::decaf::ComponentClassDescription::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._ctor2)
  // Insert-Code-Here {decaf.ComponentClassDescription._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._ctor2)
}

// user defined constructor
void decaf::ComponentClassDescription_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._ctor)
}

// user defined destructor
void decaf::ComponentClassDescription_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._dtor)
}

// static class initializer
void decaf::ComponentClassDescription_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 *  
 * Returns the class name provided in 
 * <code>BuilderService.createInstance()</code>
 * or in
 * <code>AbstractFramework.getServices()</code>.
 * <p>
 * Throws <code>CCAException</code> if <code>ComponentClassDescription</code> is invalid.
 */
::std::string
decaf::ComponentClassDescription_impl::getComponentClassName_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription.getComponentClassName)
  return std::string("(unimplemented)");
  // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription.getComponentClassName)
}


// DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._misc)

