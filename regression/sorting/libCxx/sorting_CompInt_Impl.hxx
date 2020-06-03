// 
// File:          sorting_CompInt_Impl.hxx
// Symbol:        sorting.CompInt-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.CompInt
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_CompInt_Impl_hxx
#define included_sorting_CompInt_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_CompInt_IOR_h
#include "sorting_CompInt_IOR.h"
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
#ifndef included_sorting_CompInt_hxx
#include "sorting_CompInt.hxx"
#endif
#ifndef included_sorting_Comparator_hxx
#include "sorting_Comparator.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.CompInt._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.CompInt._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.CompInt" (version 0.1)
   * 
   * Compare two Integer's.  By default, this will sort in increasing order.
   */
  class CompInt_impl : public virtual ::sorting::CompInt 
  // DO-NOT-DELETE splicer.begin(sorting.CompInt._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.CompInt._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.CompInt._implementation)
    bool d_increasing;
    // DO-NOT-DELETE splicer.end(sorting.CompInt._implementation)

  public:
    // default constructor, used for data wrapping(required)
    CompInt_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      CompInt_impl( struct sorting_CompInt__object * ior ) : StubBase(ior,true),
        
    ::sorting::Comparator((ior==NULL) ? NULL : &((*ior).d_sorting_comparator)) ,
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~CompInt_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * If increasing is true, this will cause the comparator to
     * report a normal definition of less than; otherwise, it will
     * reverse the normal ordering.
     */
    void
    setSortIncreasing_impl (
      /* in */bool increasing
    )
    ;


    /**
     * This method is used to define an ordering of objects.  This method
     * will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
     */
    int32_t
    compare_impl (
      /* in */::sidl::BaseInterface& i1,
      /* in */::sidl::BaseInterface& i2
    )
    ;

  };  // end class CompInt_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.CompInt._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.CompInt._hmisc)

#endif
