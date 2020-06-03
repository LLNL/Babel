// 
// File:          cxx_internal_Rotation_Impl.hxx
// Symbol:        cxx.internal.Rotation-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Rotation
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_internal_Rotation_Impl_hxx
#define included_cxx_internal_Rotation_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_internal_Rotation_IOR_h
#include "cxx_internal_Rotation_IOR.h"
#endif
#ifndef included_cxx_internal_Rotation_hxx
#include "cxx_internal_Rotation.hxx"
#endif
#ifndef included_cxx_internal_Shape_hxx
#include "cxx_internal_Shape.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_wave2d_Shape_hxx
#include "wave2d_Shape.hxx"
#endif


// DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._hincludes)
// Insert-Code-Here {cxx.internal.Rotation._hincludes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(cxx.internal.Rotation._hincludes)

namespace cxx { 
  namespace internal { 

    /**
     * Symbol "cxx.internal.Rotation" (version 1.0)
     */
    class Rotation_impl : public virtual ::cxx::internal::Rotation 
    // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._inherits)
    // Insert-Code-Here {cxx.internal.Rotation._inherits} (optional inheritance here)
    // DO-NOT-DELETE splicer.end(cxx.internal.Rotation._inherits)

    {

    // All data marked protected will be accessable by 
    // descendant Impl classes
    protected:

      bool _wrapped;

      // DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._implementation)
      ::wave2d::Shape d_source;
      double d_angle;
      double d_cos;
      double d_sin;
      // DO-NOT-DELETE splicer.end(cxx.internal.Rotation._implementation)

    public:
      // default constructor, used for data wrapping(required)
      Rotation_impl();
      // sidl constructor (required)
      // Note: alternate Skel constructor doesn't call addref()
      // (fixes bug #275)
        Rotation_impl( struct cxx_internal_Rotation__object * ior ) : StubBase(
          ior,true), 
      ::wave2d::Shape((ior==NULL) ? NULL : &((
        *ior).d_cxx_internal_shape.d_wave2d_shape)) , _wrapped(false) {_ctor();}


      // user defined construction
      void _ctor();

      // virtual destructor (required)
      virtual ~Rotation_impl() { _dtor(); }

      // user defined destruction
      void _dtor();

      // true if this object was created by a user newing the impl
      inline bool _isWrapped() {return _wrapped;}

      // static class initializer
      static void _load();

    public:

      /**
       * user defined non-static method.
       */
      void
      init_impl (
        /* in */::wave2d::Shape& source,
        /* in */double angle
      )
      ;

      /**
       * user defined non-static method.
       */
      bool
      inLocus_impl (
        /* in */double x,
        /* in */double y
      )
      ;

    };  // end class Rotation_impl

  } // end namespace internal
} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.internal.Rotation._hmisc)
// Insert-Code-Here {cxx.internal.Rotation._hmisc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(cxx.internal.Rotation._hmisc)

#endif
