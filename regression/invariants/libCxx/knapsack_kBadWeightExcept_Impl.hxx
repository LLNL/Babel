// 
// File:          knapsack_kBadWeightExcept_Impl.hxx
// Symbol:        knapsack.kBadWeightExcept-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.kBadWeightExcept
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_knapsack_kBadWeightExcept_Impl_hxx
#define included_knapsack_kBadWeightExcept_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_knapsack_kBadWeightExcept_IOR_h
#include "knapsack_kBadWeightExcept_IOR.h"
#endif
#ifndef included_knapsack_kBadWeightExcept_hxx
#include "knapsack_kBadWeightExcept.hxx"
#endif
#ifndef included_knapsack_kExcept_hxx
#include "knapsack_kExcept.hxx"
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


// DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept._hincludes)
   // Nothing needed here
// DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept._hincludes)

namespace knapsack { 

  /**
   * Symbol "knapsack.kBadWeightExcept" (version 1.0)
   */
  class kBadWeightExcept_impl : public virtual ::knapsack::kBadWeightExcept 
  // DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept._inherits)
    // Nothing needed here
  // DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept._implementation)
      // Nothing needed here
    // DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept._implementation)

  public:
    // default constructor, used for data wrapping(required)
    kBadWeightExcept_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      kBadWeightExcept_impl( struct knapsack_kBadWeightExcept__object * ior ) : 
        StubBase(ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_knapsack_kexcept.d_sidl_sidlexception.d_sidl_io_serializable)),
    ::sidl::BaseException((ior==NULL) ? NULL : &((
      *ior).d_knapsack_kexcept.d_sidl_sidlexception.d_sidl_baseexception)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~kBadWeightExcept_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class kBadWeightExcept_impl

} // end namespace knapsack

// DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept._hmisc)
  // Nothing needed here
// DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept._hmisc)

#endif
