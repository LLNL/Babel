// 
// File:          cxx_WavePropagator_Impl.hxx
// Symbol:        cxx.WavePropagator-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.WavePropagator
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_cxx_WavePropagator_Impl_hxx
#define included_cxx_WavePropagator_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_cxx_WavePropagator_IOR_h
#include "cxx_WavePropagator_IOR.h"
#endif
#ifndef included_cxx_WavePropagator_hxx
#include "cxx_WavePropagator.hxx"
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
#ifndef included_wave2d_WavePropagator_hxx
#include "wave2d_WavePropagator.hxx"
#endif


// DO-NOT-DELETE splicer.begin(cxx.WavePropagator._hincludes)
// Insert-Code-Here {cxx.WavePropagator._hincludes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(cxx.WavePropagator._hincludes)

namespace cxx { 

  /**
   * Symbol "cxx.WavePropagator" (version 1.0)
   */
  class WavePropagator_impl : public virtual ::cxx::WavePropagator 
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator._inherits)
  // Insert-Code-Here {cxx.WavePropagator._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(cxx.WavePropagator._implementation)
    ::sidl::array<double> p_2;
    ::sidl::array<double> p_1;
    ::sidl::array<double> p_0;
    ::wave2d::ScalarField d_density;
    int32_t d_lower[2];
    int32_t d_upper[2];
    // DO-NOT-DELETE splicer.end(cxx.WavePropagator._implementation)

  public:
    // default constructor, used for data wrapping(required)
    WavePropagator_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      WavePropagator_impl( struct cxx_WavePropagator__object * ior ) : StubBase(
        ior,true), 
    ::wave2d::WavePropagator((ior==NULL) ? NULL : &((
      *ior).d_wave2d_wavepropagator)) , _wrapped(false) {_ctor();}


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~WavePropagator_impl() { _dtor(); }

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
      /* in */::wave2d::ScalarField& density,
      /* in array<double,2> */::sidl::array<double>& pressure
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    step_impl (
      /* in */int32_t n
    )
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<double>
    getPressure_impl() ;
  };  // end class WavePropagator_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.WavePropagator._hmisc)
// Insert-Code-Here {cxx.WavePropagator._hmisc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(cxx.WavePropagator._hmisc)

#endif
