// 
// File:          Exceptions_NegativeValueException_Impl.hxx
// Symbol:        Exceptions.NegativeValueException-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Exceptions.NegativeValueException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Exceptions_NegativeValueException_Impl_hxx
#define included_Exceptions_NegativeValueException_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Exceptions_NegativeValueException_IOR_h
#include "Exceptions_NegativeValueException_IOR.h"
#endif
#ifndef included_Exceptions_NegativeValueException_hxx
#include "Exceptions_NegativeValueException.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_SIDLException_hxx
#include "sidl_SIDLException.hxx"
#endif
#ifndef included_sidl_io_Deserializer_hxx
#include "sidl_io_Deserializer.hxx"
#endif
#ifndef included_sidl_io_Serializer_hxx
#include "sidl_io_Serializer.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._hincludes)

namespace Exceptions { 

  /**
   * Symbol "Exceptions.NegativeValueException" (version 1.0)
   * 
   * This exception is thrown if the value for which the Fibonacci number
   * is requested is negative.
   */
  class NegativeValueException_impl : public virtual 
    ::Exceptions::NegativeValueException 
  // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._implementation)

  public:
    // default constructor, used for data wrapping(required)
    NegativeValueException_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      NegativeValueException_impl( struct 
        Exceptions_NegativeValueException__object * ior ) : StubBase(ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_io_serializable)),
    ::sidl::BaseException((ior==NULL) ? NULL : &((
      *ior).d_sidl_sidlexception.d_sidl_baseexception)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~NegativeValueException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class NegativeValueException_impl

} // end namespace Exceptions

// DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._hmisc)

#endif
