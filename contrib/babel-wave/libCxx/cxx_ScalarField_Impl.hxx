// 
// File:          cxx_ScalarField_Impl.hxx
// Symbol:        cxx.ScalarField-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.ScalarField
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_ScalarField_Impl_hxx
#define included_cxx_ScalarField_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_ScalarField_IOR_h
#include "cxx_ScalarField_IOR.h"
#endif
#ifndef included_cxx_ScalarField_hxx
#include "cxx_ScalarField.hxx"
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
#ifndef included_wave2d_ScalarField_hxx
#include "wave2d_ScalarField.hxx"
#endif
#ifndef included_wave2d_Shape_hxx
#include "wave2d_Shape.hxx"
#endif


// DO-NOT-DELETE splicer.begin(cxx.ScalarField._hincludes)
// Insert-Code-Here {cxx.ScalarField._hincludes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(cxx.ScalarField._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.ScalarField" (version 1.0)
   */
  class ScalarField_impl : public virtual ::cxx::ScalarField 
  // DO-NOT-DELETE splicer.begin(cxx.ScalarField._inherits)
  // Insert-Code-Here {cxx.ScalarField._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(cxx.ScalarField._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.ScalarField._implementation)
    double d_minX;
    double d_maxX;
    double d_minY;
    double d_maxY;
    double d_spacing;

    sidl::array<double> d_density;
    // DO-NOT-DELETE splicer.end(cxx.ScalarField._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ScalarField_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      ScalarField_impl( struct cxx_ScalarField__object * ior ) : StubBase(ior,
        true), 
    ::wave2d::ScalarField((ior==NULL) ? NULL : &((*ior).d_wave2d_scalarfield)) ,
      _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ScalarField_impl() { _dtor(); }

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
      /* in */double minX,
      /* in */double minY,
      /* in */double maxX,
      /* in */double maxY,
      /* in */double spacing,
      /* in */double value
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    getBounds_impl (
      /* out */double& minX,
      /* out */double& minY,
      /* out */double& maxX,
      /* out */double& maxY,
      /* out */double& spacing
    )
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<double>
    getData_impl() ;
    /**
     * user defined non-static method.
     */
    void
    render_impl (
      /* in */::wave2d::Shape& shape,
      /* in */double value
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    setData_impl (
      /* in array<double,2> */::sidl::array<double>& data
    )
    ;

  };  // end class ScalarField_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.ScalarField._hmisc)
// Insert-Code-Here {cxx.ScalarField._hmisc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(cxx.ScalarField._hmisc)

#endif
