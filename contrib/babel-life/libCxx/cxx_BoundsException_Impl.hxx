// 
// File:          cxx_BoundsException_Impl.hxx
// Symbol:        cxx.BoundsException-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.BoundsException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_BoundsException_Impl_hxx
#define included_cxx_BoundsException_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_BoundsException_IOR_h
#include "cxx_BoundsException_IOR.h"
#endif
#ifndef included_conway_BoundsException_hxx
#include "conway_BoundsException.hxx"
#endif
#ifndef included_cxx_BoundsException_hxx
#include "cxx_BoundsException.hxx"
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


// DO-NOT-DELETE splicer.begin(cxx.BoundsException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.BoundsException._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.BoundsException" (version 2.0)
   */
  class BoundsException_impl : public virtual ::cxx::BoundsException 
  // DO-NOT-DELETE splicer.begin(cxx.BoundsException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.BoundsException._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.BoundsException._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(cxx.BoundsException._implementation)

  public:
    // default constructor, used for data wrapping(required)
    BoundsException_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      BoundsException_impl( struct cxx_BoundsException__object * ior ) : 
        StubBase(ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_io_serializable)),
      ::sidl::BaseException((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_baseexception)),
    ::conway::BoundsException((ior==NULL) ? NULL : &((
      *ior).d_conway_boundsexception)) , _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~BoundsException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class BoundsException_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.BoundsException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.BoundsException._hmisc)

#endif
