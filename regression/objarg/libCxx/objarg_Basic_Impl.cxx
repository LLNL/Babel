// 
// File:          objarg_Basic_Impl.cxx
// Symbol:        objarg.Basic-v0.5
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for objarg.Basic
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "objarg_Basic_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseClass_hxx
#include "sidl_BaseClass.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(objarg.Basic._includes)
// Insert-Code-Here {objarg.Basic._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(objarg.Basic._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
objarg::Basic_impl::Basic_impl() : StubBase(reinterpret_cast< void*>(
  ::objarg::Basic::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(objarg.Basic._ctor2)
  // Insert-Code-Here {objarg.Basic._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(objarg.Basic._ctor2)
}

// user defined constructor
void objarg::Basic_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(objarg.Basic._ctor)
  // Insert-Code-Here {objarg.Basic._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(objarg.Basic._ctor)
}

// user defined destructor
void objarg::Basic_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(objarg.Basic._dtor)
  // Insert-Code-Here {objarg.Basic._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(objarg.Basic._dtor)
}

// static class initializer
void objarg::Basic_impl::_load() {
  // DO-NOT-DELETE splicer.begin(objarg.Basic._load)
  // Insert-Code-Here {objarg.Basic._load} (class initialization)
  // DO-NOT-DELETE splicer.end(objarg.Basic._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Return inNotNull == (o != NULL).
 */
bool
objarg::Basic_impl::passIn_impl (
  /* in */::sidl::BaseClass& o,
  /* in */bool inNotNull ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Basic.passIn)
  return (inNotNull && o._not_nil()) ||
    !(inNotNull || o._not_nil());
  // DO-NOT-DELETE splicer.end(objarg.Basic.passIn)
}

/**
 * Return inNotNull == (o != NULL).  If outNotNull, the outgoing
 * value of o should not be NULL; otherwise, it will be NULL.
 * If outNotNull is true, there are two cases, it retSame is true
 * the incoming value of o will be returned; otherwise, a new
 * object will be allocated and returned.
 */
bool
objarg::Basic_impl::passInOut_impl (
  /* inout */::sidl::BaseClass& o,
  /* in */bool inNotNull,
  /* in */bool outNotNull,
  /* in */bool retSame ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Basic.passInOut)
  bool retval = (inNotNull && o._not_nil()) ||
    !(inNotNull || o._not_nil());
  if (outNotNull) {
    if (!retSame || o._is_nil()) {
      o = ::sidl::BaseClass::_create();
    }
  }
  else {
    o = ::sidl::BaseClass();
  }
  return retval;
  // DO-NOT-DELETE splicer.end(objarg.Basic.passInOut)
}

/**
 * If passOutNull is true, a NULL value of o will be returned; otherwise,
 * a newly allocated object will be returned.
 */
void
objarg::Basic_impl::passOut_impl (
  /* out */::sidl::BaseClass& o,
  /* in */bool passOutNull ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Basic.passOut)
  if (!passOutNull) {
    o = ::sidl::BaseClass::_create();
  }
  else {
    o = ::sidl::BaseClass();
  }
  // DO-NOT-DELETE splicer.end(objarg.Basic.passOut)
}

/**
 * Return a NULL or non-NULL object depending on the value of retNull.
 */
::sidl::BaseClass
objarg::Basic_impl::retObject_impl (
  /* in */bool retNull ) 
{
  // DO-NOT-DELETE splicer.begin(objarg.Basic.retObject)
  ::sidl::BaseClass retval;
  if (!retNull) {
    retval = ::sidl::BaseClass::_create();
  }
  return retval;
  // DO-NOT-DELETE splicer.end(objarg.Basic.retObject)
}


// DO-NOT-DELETE splicer.begin(objarg.Basic._misc)
// Insert-Code-Here {objarg.Basic._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(objarg.Basic._misc)

