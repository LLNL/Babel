// 
// File:          cxx_internal_Shape_Impl.hxx
// Symbol:        cxx.internal.Shape-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.internal.Shape
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_internal_Shape_Impl_hxx
#define included_cxx_internal_Shape_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_internal_Shape_IOR_h
#include "cxx_internal_Shape_IOR.h"
#endif
#ifndef included_cxx_internal_Shape_hxx
#include "cxx_internal_Shape.hxx"
#endif
#ifndef included_sidl_BaseClass_hxx
#include "sidl_BaseClass.hxx"
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


// DO-NOT-DELETE splicer.begin(cxx.internal.Shape._hincludes)
// Insert-Code-Here {cxx.internal.Shape._hincludes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(cxx.internal.Shape._hincludes)

namespace cxx { 
  namespace internal { 

    /**
     * Symbol "cxx.internal.Shape" (version 1.0)
     */
    class Shape_impl : public virtual ::cxx::internal::Shape 
    // DO-NOT-DELETE splicer.begin(cxx.internal.Shape._inherits)
    // Insert-Code-Here {cxx.internal.Shape._inherits} (optional inheritance here)
    // DO-NOT-DELETE splicer.end(cxx.internal.Shape._inherits)

    {

    // All data marked protected will be accessable by 
    // descendant Impl classes
    protected:

      bool _wrapped;

      // DO-NOT-DELETE splicer.begin(cxx.internal.Shape._implementation)
      // Insert-Code-Here {cxx.internal.Shape._implementation} (additional details)
      // DO-NOT-DELETE splicer.end(cxx.internal.Shape._implementation)

    private:
      // default constructor, do not use! 
      Shape_impl();
      public:
      // sidl constructor (required)
      // Note: alternate Skel constructor doesn't call addref()
      // (fixes bug #275)
        Shape_impl( struct cxx_internal_Shape__object * ior ) : StubBase(ior,
          true), 
      ::wave2d::Shape((ior==NULL) ? NULL : &((*ior).d_wave2d_shape)) , _wrapped(
        false) {_ctor();}


      // user defined construction
      void _ctor();

      // virtual destructor (required)
      virtual ~Shape_impl() { _dtor(); }

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
      ::wave2d::Shape
      translate_impl (
        /* in */double delta_x,
        /* in */double delta_y
      )
      ;

      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      rotate_impl (
        /* in */double radians
      )
      ;

      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      unify_impl (
        /* in */::wave2d::Shape& other
      )
      ;

      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      intersect_impl (
        /* in */::wave2d::Shape& other
      )
      ;

      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      subtract_impl (
        /* in */::wave2d::Shape& other
      )
      ;

      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      invert_impl() ;
      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      scale_impl (
        /* in */double scale_x,
        /* in */double scale_y
      )
      ;

      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      reflectX_impl() ;
      /**
       * user defined non-static method.
       */
      ::wave2d::Shape
      reflectY_impl() ;
      /**
       * user defined non-static method.
       */
      void
      render_impl (
        /* inout array<double,2> */::sidl::array<double>& field,
        /* in */double value
      )
      ;

    };  // end class Shape_impl

  } // end namespace internal
} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.internal.Shape._hmisc)
// Insert-Code-Here {cxx.internal.Shape._hmisc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(cxx.internal.Shape._hmisc)

#endif
