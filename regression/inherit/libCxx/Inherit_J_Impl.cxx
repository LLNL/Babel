// 
// File:          Inherit_J_Impl.cxx
// Symbol:        Inherit.J-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.J
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Inherit_J_Impl.hxx"

// 
// Includes for all method dependencies.
// 
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
#ifndef included_sidl_LangSpecificException_hxx
#include "sidl_LangSpecificException.hxx"
#endif
#ifndef included_sidl_String_h
#include "sidl_String.h"
#endif
// DO-NOT-DELETE splicer.begin(Inherit.J._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.J._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Inherit::J_impl::J_impl() : StubBase(reinterpret_cast< void*>(
  ::Inherit::J::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Inherit.J._ctor2)
  // Insert-Code-Here {Inherit.J._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Inherit.J._ctor2)
}

// user defined constructor
void Inherit::J_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Inherit.J._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Inherit.J._ctor)
}

// user defined destructor
void Inherit::J_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Inherit.J._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Inherit.J._dtor)
}

// static class initializer
void Inherit::J_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Inherit.J._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Inherit.J._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  j[]
 */
::std::string
Inherit::J_impl::j_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.J.j)
  return "J.j";
  // DO-NOT-DELETE splicer.end(Inherit.J.j)
}

/**
 * Method:  e[]
 */
::std::string
Inherit::J_impl::e_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.J.e)
  ::std::string ret = "J.";
  return ret + super.e();
  // DO-NOT-DELETE splicer.end(Inherit.J.e)
}

/**
 * Method:  c[]
 */
::std::string
Inherit::J_impl::c_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.J.c)
  ::std::string ret = "J.";
  return ret + super.c();

  // DO-NOT-DELETE splicer.end(Inherit.J.c)
}

/**
 * Method:  a[]
 */
::std::string
Inherit::J_impl::a_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.J.a)
  return "J.a";
  // DO-NOT-DELETE splicer.end(Inherit.J.a)
}

/**
 * Method:  b[]
 */
::std::string
Inherit::J_impl::b_impl () 

{
  // DO-NOT-DELETE splicer.begin(Inherit.J.b)
  return "J.b";
  // DO-NOT-DELETE splicer.end(Inherit.J.b)
}


// DO-NOT-DELETE splicer.begin(Inherit.J._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Inherit.J._misc)


//////////////////////////////////////////////////
// 
// Special methods for throwing exceptions
// 

void
Inherit::J_impl::Super::throwException0(
  const char* methodName,
  struct sidl_BaseInterface__object *_exception
)
  // throws:
{
  void * _p = 0;
  struct sidl_BaseInterface__object *throwaway_exception;

  if ( (_p=(*(_exception->d_epv->f__cast))(_exception->d_object, 
    "sidl.RuntimeException", &throwaway_exception)) != 0 ) {
    struct sidl_RuntimeException__object * _realtype = reinterpret_cast< struct 
      sidl_RuntimeException__object*>(_p);
    (*_exception->d_epv->f_deleteRef)(_exception->d_object, 
      &throwaway_exception);
    // Note: alternate constructor does not increment refcount.
    ::sidl::RuntimeException _resolved_exception = ::sidl::RuntimeException( 
      _realtype, false );
    (_resolved_exception._get_ior()->d_epv->f_add) (
      _resolved_exception._get_ior()->d_object , __FILE__, __LINE__, methodName,
      &throwaway_exception);throw _resolved_exception;
  }
  // Any unresolved exception is treated as LangSpecificException
  ::sidl::LangSpecificException _unexpected = 
    ::sidl::LangSpecificException::_create();
  _unexpected.add(__FILE__,__LINE__, "Unknown method");
  _unexpected.setNote("Unexpected exception received by C++ stub.");
  throw _unexpected;
}
/**
 * super method
 */
::std::string
Inherit::J_impl::Super::e(  )

{
  ::std::string _result;
  char * _local_result;
  sidl_BaseInterface__object * _exception;
  /*pack args to dispatch to ior*/
  _local_result = (*(superEPV->f_e))(reinterpret_cast< struct 
    Inherit_E2__object*>(superSelf), &_exception );
  /*dispatch to ior*/
  if (_exception != NULL ) {

    throwException0("Super::e", _exception);
  }
  if (_local_result) {
    _result = _local_result;
    ::sidl_String_free( _local_result );
  }
  /*unpack results and cleanup*/
  return _result;
}


/**
 * super method
 */
::std::string
Inherit::J_impl::Super::c(  )

{
  ::std::string _result;
  char * _local_result;
  sidl_BaseInterface__object * _exception;
  /*pack args to dispatch to ior*/
  _local_result = (*(superEPV->f_c))(reinterpret_cast< struct 
    Inherit_E2__object*>(superSelf), &_exception );
  /*dispatch to ior*/
  if (_exception != NULL ) {

    throwException0("Super::c", _exception);
  }
  if (_local_result) {
    _result = _local_result;
    ::sidl_String_free( _local_result );
  }
  /*unpack results and cleanup*/
  return _result;
}


