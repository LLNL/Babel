// 
// File:          vect_vExcept_Impl.hxx
// Symbol:        vect.vExcept-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for vect.vExcept
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_vect_vExcept_Impl_hxx
#define included_vect_vExcept_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_vect_vExcept_IOR_h
#include "vect_vExcept_IOR.h"
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
#ifndef included_vect_vExcept_hxx
#include "vect_vExcept.hxx"
#endif


// DO-NOT-DELETE splicer.begin(vect.vExcept._hincludes)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.vExcept._hincludes)

namespace vect { 

  /**
   * Symbol "vect.vExcept" (version 1.0)
   */
  class vExcept_impl : public virtual ::vect::vExcept 
  // DO-NOT-DELETE splicer.begin(vect.vExcept._inherits)
  // Nothing to do here.
  // DO-NOT-DELETE splicer.end(vect.vExcept._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(vect.vExcept._implementation)
    // Nothing to do here.
    // DO-NOT-DELETE splicer.end(vect.vExcept._implementation)

  public:
    // default constructor, used for data wrapping(required)
    vExcept_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      vExcept_impl( struct vect_vExcept__object * ior ) : StubBase(ior,true), 
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
    virtual ~vExcept_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class vExcept_impl

} // end namespace vect

// DO-NOT-DELETE splicer.begin(vect.vExcept._hmisc)
// Nothing to do here.
// DO-NOT-DELETE splicer.end(vect.vExcept._hmisc)

#endif
