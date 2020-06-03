// 
// File:          vect_vDivByZeroExcept_Impl.hxx
// Symbol:        vect.vDivByZeroExcept-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for vect.vDivByZeroExcept
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_vect_vDivByZeroExcept_Impl_hxx
#define included_vect_vDivByZeroExcept_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_vect_vDivByZeroExcept_IOR_h
#include "vect_vDivByZeroExcept_IOR.h"
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
#ifndef included_vect_vDivByZeroExcept_hxx
#include "vect_vDivByZeroExcept.hxx"
#endif
#ifndef included_vect_vExcept_hxx
#include "vect_vExcept.hxx"
#endif


// DO-NOT-DELETE splicer.begin(vect.vDivByZeroExcept._hincludes)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.vDivByZeroExcept._hincludes)

namespace vect { 

  /**
   * Symbol "vect.vDivByZeroExcept" (version 1.0)
   */
  class vDivByZeroExcept_impl : public virtual ::vect::vDivByZeroExcept 
  // DO-NOT-DELETE splicer.begin(vect.vDivByZeroExcept._inherits)
  // Nothing to do here.
  // DO-NOT-DELETE splicer.end(vect.vDivByZeroExcept._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(vect.vDivByZeroExcept._implementation)
    // Nothing to do here.
    // DO-NOT-DELETE splicer.end(vect.vDivByZeroExcept._implementation)

  public:
    // default constructor, used for data wrapping(required)
    vDivByZeroExcept_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      vDivByZeroExcept_impl( struct vect_vDivByZeroExcept__object * ior ) : 
        StubBase(ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_vect_vexcept.d_sidl_sidlexception.d_sidl_io_serializable)),
    ::sidl::BaseException((ior==NULL) ? NULL : &((
      *ior).d_vect_vexcept.d_sidl_sidlexception.d_sidl_baseexception)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~vDivByZeroExcept_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class vDivByZeroExcept_impl

} // end namespace vect

// DO-NOT-DELETE splicer.begin(vect.vDivByZeroExcept._hmisc)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.vDivByZeroExcept._hmisc)

#endif
