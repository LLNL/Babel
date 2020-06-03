// 
// File:          decaf_TypeMismatchException_Impl.cxx
// Symbol:        decaf.TypeMismatchException-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.TypeMismatchException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_TypeMismatchException_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_CCAExceptionType_hxx
#include "gov_cca_CCAExceptionType.hxx"
#endif
#ifndef included_gov_cca_Type_hxx
#include "gov_cca_Type.hxx"
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
// DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::TypeMismatchException_impl::TypeMismatchException_impl() : StubBase(
  reinterpret_cast< void*>(::decaf::TypeMismatchException::_wrapObj(
  reinterpret_cast< void*>(this))),false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._ctor2)
  // Insert-Code-Here {decaf.TypeMismatchException._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._ctor2)
}

// user defined constructor
void decaf::TypeMismatchException_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._ctor)
  d_exceptionType = gov::cca::CCAExceptionType_Unexpected;
  d_actualType = gov::cca::Type_NoType;
  d_requestedType = gov::cca::Type_NoType;
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._ctor)
}

// user defined destructor
void decaf::TypeMismatchException_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._dtor)
}

// static class initializer
void decaf::TypeMismatchException_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getCCAExceptionType[]
 */
::gov::cca::CCAExceptionType
decaf::TypeMismatchException_impl::getCCAExceptionType_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException.getCCAExceptionType)
  return d_exceptionType;
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException.getCCAExceptionType)
}

/**
 * Method:  getRequestedType[]
 */
::gov::cca::Type
decaf::TypeMismatchException_impl::getRequestedType_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException.getRequestedType)
  return d_requestedType;
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException.getRequestedType)
}

/**
 * Method:  getActualType[]
 */
::gov::cca::Type
decaf::TypeMismatchException_impl::getActualType_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException.getActualType)
  return d_actualType;
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException.getActualType)
}

/**
 * Method:  initialize[]
 */
void
decaf::TypeMismatchException_impl::initialize_impl (
  /* in */::gov::cca::Type requestedType,
  /* in */::gov::cca::Type actualType ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException.initialize)
  d_requestedType = requestedType;
  d_actualType = actualType;
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException.initialize)
}


// DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._misc)

