// 
// File:          objarg_Basic_Impl.hxx
// Symbol:        objarg.Basic-v0.5
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for objarg.Basic
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_objarg_Basic_Impl_hxx
#define included_objarg_Basic_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_objarg_Basic_IOR_h
#include "objarg_Basic_IOR.h"
#endif
#ifndef included_objarg_Basic_hxx
#include "objarg_Basic.hxx"
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


// DO-NOT-DELETE splicer.begin(objarg.Basic._hincludes)
// Insert-Code-Here {objarg.Basic._includes} (includes or arbitrary code)
// DO-NOT-DELETE splicer.end(objarg.Basic._hincludes)

namespace objarg { 

  /**
   * Symbol "objarg.Basic" (version 0.5)
   */
  class Basic_impl : public virtual ::objarg::Basic 
  // DO-NOT-DELETE splicer.begin(objarg.Basic._inherits)
  // Insert-Code-Here {objarg.Basic._inherits} (optional inheritance here)
  // DO-NOT-DELETE splicer.end(objarg.Basic._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(objarg.Basic._implementation)
    // Insert-Code-Here {objarg.Basic._implementation} (additional details)
    // DO-NOT-DELETE splicer.end(objarg.Basic._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Basic_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Basic_impl( struct objarg_Basic__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Basic_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Return inNotNull == (o != NULL).
     */
    bool
    passIn_impl (
      /* in */::sidl::BaseClass& o,
      /* in */bool inNotNull
    )
    ;


    /**
     * Return inNotNull == (o != NULL).  If outNotNull, the outgoing
     * value of o should not be NULL; otherwise, it will be NULL.
     * If outNotNull is true, there are two cases, it retSame is true
     * the incoming value of o will be returned; otherwise, a new
     * object will be allocated and returned.
     */
    bool
    passInOut_impl (
      /* inout */::sidl::BaseClass& o,
      /* in */bool inNotNull,
      /* in */bool outNotNull,
      /* in */bool retSame
    )
    ;


    /**
     * If passOutNull is true, a NULL value of o will be returned; otherwise,
     * a newly allocated object will be returned.
     */
    void
    passOut_impl (
      /* out */::sidl::BaseClass& o,
      /* in */bool passOutNull
    )
    ;


    /**
     * Return a NULL or non-NULL object depending on the value of retNull.
     */
    ::sidl::BaseClass
    retObject_impl (
      /* in */bool retNull
    )
    ;

  };  // end class Basic_impl

} // end namespace objarg

// DO-NOT-DELETE splicer.begin(objarg.Basic._hmisc)
// Insert-Code-Here {objarg.Basic._misc} (miscellaneous things)
// DO-NOT-DELETE splicer.end(objarg.Basic._hmisc)

#endif
