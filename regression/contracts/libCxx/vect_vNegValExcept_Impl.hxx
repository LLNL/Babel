// 
// File:          vect_vNegValExcept_Impl.hxx
// Symbol:        vect.vNegValExcept-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for vect.vNegValExcept
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_vect_vNegValExcept_Impl_hxx
#define included_vect_vNegValExcept_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_vect_vNegValExcept_IOR_h
#include "vect_vNegValExcept_IOR.h"
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
#ifndef included_vect_vExcept_hxx
#include "vect_vExcept.hxx"
#endif
#ifndef included_vect_vNegValExcept_hxx
#include "vect_vNegValExcept.hxx"
#endif


// DO-NOT-DELETE splicer.begin(vect.vNegValExcept._hincludes)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.vNegValExcept._hincludes)

namespace vect { 

  /**
   * Symbol "vect.vNegValExcept" (version 1.0)
   */
  class vNegValExcept_impl : public virtual ::vect::vNegValExcept 
  // DO-NOT-DELETE splicer.begin(vect.vNegValExcept._inherits)
  // Nothing to do here.
  // DO-NOT-DELETE splicer.end(vect.vNegValExcept._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(vect.vNegValExcept._implementation)
    // Nothing to do here.
    // DO-NOT-DELETE splicer.end(vect.vNegValExcept._implementation)

  public:
    // default constructor, used for data wrapping(required)
    vNegValExcept_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      vNegValExcept_impl( struct vect_vNegValExcept__object * ior ) : StubBase(
        ior,true), 
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
    virtual ~vNegValExcept_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class vNegValExcept_impl

} // end namespace vect

// DO-NOT-DELETE splicer.begin(vect.vNegValExcept._hmisc)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.vNegValExcept._hmisc)

#endif
