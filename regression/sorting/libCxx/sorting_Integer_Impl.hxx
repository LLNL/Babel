// 
// File:          sorting_Integer_Impl.hxx
// Symbol:        sorting.Integer-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Integer
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_Integer_Impl_hxx
#define included_sorting_Integer_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_Integer_IOR_h
#include "sorting_Integer_IOR.h"
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
#ifndef included_sorting_Integer_hxx
#include "sorting_Integer.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.Integer._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.Integer._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.Integer" (version 0.1)
   * 
   * An object to hold a simple integer.
   */
  class Integer_impl : public virtual ::sorting::Integer 
  // DO-NOT-DELETE splicer.begin(sorting.Integer._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.Integer._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.Integer._implementation)
    int32_t d_num;
    // DO-NOT-DELETE splicer.end(sorting.Integer._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Integer_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Integer_impl( struct sorting_Integer__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Integer_impl() { _dtor(); }

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
    int32_t
    getValue_impl() ;
    /**
     * user defined non-static method.
     */
    void
    setValue_impl (
      /* in */int32_t value
    )
    ;

  };  // end class Integer_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.Integer._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.Integer._hmisc)

#endif
