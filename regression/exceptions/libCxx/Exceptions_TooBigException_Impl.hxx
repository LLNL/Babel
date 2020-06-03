// 
// File:          Exceptions_TooBigException_Impl.hxx
// Symbol:        Exceptions.TooBigException-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Exceptions.TooBigException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Exceptions_TooBigException_Impl_hxx
#define included_Exceptions_TooBigException_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Exceptions_TooBigException_IOR_h
#include "Exceptions_TooBigException_IOR.h"
#endif
#ifndef included_Exceptions_FibException_hxx
#include "Exceptions_FibException.hxx"
#endif
#ifndef included_Exceptions_TooBigException_hxx
#include "Exceptions_TooBigException.hxx"
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


// DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Exceptions.TooBigException._hincludes)

namespace Exceptions { 

  /**
   * Symbol "Exceptions.TooBigException" (version 1.0)
   * 
   * This exception is thrown if the Fibonacci number is too large.
   */
  class TooBigException_impl : public virtual ::Exceptions::TooBigException 
  // DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Exceptions.TooBigException._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Exceptions.TooBigException._implementation)

  public:
    // default constructor, used for data wrapping(required)
    TooBigException_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      TooBigException_impl( struct Exceptions_TooBigException__object * ior ) : 
        StubBase(ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_exceptions_fibexception.d_sidl_sidlexception.d_sidl_io_serializable))
        ,
    ::sidl::BaseException((ior==NULL) ? NULL : &((
      *ior).d_exceptions_fibexception.d_sidl_sidlexception.d_sidl_baseexception))
      , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~TooBigException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class TooBigException_impl

} // end namespace Exceptions

// DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Exceptions.TooBigException._hmisc)

#endif
