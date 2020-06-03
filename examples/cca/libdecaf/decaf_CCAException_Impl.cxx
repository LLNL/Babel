// 
// File:          decaf_CCAException_Impl.cxx
// Symbol:        decaf.CCAException-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.CCAException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_CCAException_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_CCAExceptionType_hxx
#include "gov_cca_CCAExceptionType.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_io_Deserializer_hxx
#include "sidl_io_Deserializer.hxx"
#endif
#ifndef included_sidl_io_Serializer_hxx
#include "sidl_io_Serializer.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(decaf.CCAException._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.CCAException._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::CCAException_impl::CCAException_impl() : StubBase(reinterpret_cast< 
  void*>(::decaf::CCAException::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.CCAException._ctor2)
  // Insert-Code-Here {decaf.CCAException._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.CCAException._ctor2)
}

// user defined constructor
void decaf::CCAException_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.CCAException._ctor)
  d_exceptionType = gov::cca::CCAExceptionType_Unexpected;
  // DO-NOT-DELETE splicer.end(decaf.CCAException._ctor)
}

// user defined destructor
void decaf::CCAException_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.CCAException._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.CCAException._dtor)
}

// static class initializer
void decaf::CCAException_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.CCAException._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.CCAException._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getCCAExceptionType[]
 */
::gov::cca::CCAExceptionType
decaf::CCAException_impl::getCCAExceptionType_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.CCAException.getCCAExceptionType)
  return d_exceptionType;
  // DO-NOT-DELETE splicer.end(decaf.CCAException.getCCAExceptionType)
}

/**
 * Method:  setCCAExceptionType[]
 */
void
decaf::CCAException_impl::setCCAExceptionType_impl (
  /* in */::gov::cca::CCAExceptionType et ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.CCAException.setCCAExceptionType)
  d_exceptionType = et;
  // DO-NOT-DELETE splicer.end(decaf.CCAException.setCCAExceptionType)
}


// DO-NOT-DELETE splicer.begin(decaf.CCAException._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.CCAException._misc)

