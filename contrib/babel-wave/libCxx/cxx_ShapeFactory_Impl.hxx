// 
// File:          cxx_ShapeFactory_Impl.hxx
// Symbol:        cxx.ShapeFactory-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.ShapeFactory
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_ShapeFactory_Impl_hxx
#define included_cxx_ShapeFactory_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_ShapeFactory_IOR_h
#include "cxx_ShapeFactory_IOR.h"
#endif
#ifndef included_cxx_ShapeFactory_hxx
#include "cxx_ShapeFactory.hxx"
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


// DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._hincludes)
// Insert-Code-Here {cxx.ShapeFactory._hincludes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(cxx.ShapeFactory._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.ShapeFactory" (version 1.0)
   */
  class ShapeFactory_impl : public virtual ::cxx::ShapeFactory 
  // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._inherits)
  // Insert-Code-Here {cxx.ShapeFactory._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(cxx.ShapeFactory._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._implementation)
    // Insert-Code-Here {cxx.ShapeFactory._implementation} (additional details)
    // DO-NOT-DELETE splicer.end(cxx.ShapeFactory._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ShapeFactory_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    ShapeFactory_impl( struct cxx_ShapeFactory__object * ior ) : StubBase(ior,
      true) , _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ShapeFactory_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:
    /**
     * user defined static method
     */
    static ::wave2d::Shape
    createRectangle_impl (
      /* in */double x1,
      /* in */double y1,
      /* in */double x2,
      /* in */double y2
    )
    ;

    /**
     * user defined static method
     */
    static ::wave2d::Shape
    createEllipse_impl (
      /* in */double center_x,
      /* in */double center_y,
      /* in */double x_radius,
      /* in */double y_radius
    )
    ;

    /**
     * user defined static method
     */
    static ::wave2d::Shape
    createTriangle_impl (
      /* in */double x1,
      /* in */double y1,
      /* in */double x2,
      /* in */double y2,
      /* in */double x3,
      /* in */double y3
    )
    ;


  };  // end class ShapeFactory_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.ShapeFactory._hmisc)
// Insert-Code-Here {cxx.ShapeFactory._hmisc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(cxx.ShapeFactory._hmisc)

#endif
