// 
// File:          knapsack_kExcept_Impl.hxx
// Symbol:        knapsack.kExcept-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7133M trunk)
// Description:   Server-side implementation for knapsack.kExcept
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_knapsack_kExcept_Impl_hxx
#define included_knapsack_kExcept_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_knapsack_kExcept_IOR_h
#include "knapsack_kExcept_IOR.h"
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
#ifndef included_sidl_SIDLException_hxx
#include "sidl_SIDLException.hxx"
#endif
#ifndef included_sidl_io_Deserializer_hxx
#include "sidl_io_Deserializer.hxx"
#endif
#ifndef included_sidl_io_Serializer_hxx
#include "sidl_io_Serializer.hxx"
#endif


// DO-NOT-DELETE splicer.begin(knapsack.kExcept._hincludes)
   // Nothing needed here
// DO-NOT-DELETE splicer.end(knapsack.kExcept._hincludes)

namespace knapsack { 

  /**
   * Symbol "knapsack.kExcept" (version 1.0)
   */
  class kExcept_impl : public virtual ::knapsack::kExcept 
  // DO-NOT-DELETE splicer.begin(knapsack.kExcept._inherits)
     // Nothing needed here
  // DO-NOT-DELETE splicer.end(knapsack.kExcept._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(knapsack.kExcept._implementation)
       // Nothing needed here
    // DO-NOT-DELETE splicer.end(knapsack.kExcept._implementation)

  public:
    // default constructor, used for data wrapping(required)
    kExcept_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      kExcept_impl( struct knapsack_kExcept__object * ior ) : StubBase(ior,
        true), 
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
    virtual ~kExcept_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class kExcept_impl

} // end namespace knapsack

// DO-NOT-DELETE splicer.begin(knapsack.kExcept._hmisc)
   // Nothing needed here
// DO-NOT-DELETE splicer.end(knapsack.kExcept._hmisc)

#endif
