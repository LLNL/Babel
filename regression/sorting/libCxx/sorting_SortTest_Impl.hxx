// 
// File:          sorting_SortTest_Impl.hxx
// Symbol:        sorting.SortTest-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.SortTest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_SortTest_Impl_hxx
#define included_sorting_SortTest_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_SortTest_IOR_h
#include "sorting_SortTest_IOR.h"
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
#ifndef included_sorting_SortTest_hxx
#include "sorting_SortTest.hxx"
#endif
#ifndef included_sorting_SortingAlgorithm_hxx
#include "sorting_SortingAlgorithm.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.SortTest._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.SortTest._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.SortTest" (version 0.1)
   * 
   * Run a bunch of sorts through a stress test.
   */
  class SortTest_impl : public virtual ::sorting::SortTest 
  // DO-NOT-DELETE splicer.begin(sorting.SortTest._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.SortTest._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.SortTest._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(sorting.SortTest._implementation)

  public:
    // default constructor, used for data wrapping(required)
    SortTest_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    SortTest_impl( struct sorting_SortTest__object * ior ) : StubBase(ior,true) 
      , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~SortTest_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * Perform the array stress test.
     * 
     * Return true if all the algorithms work okay.
     */
    static bool
    stressTest_impl (
      /* in array<sorting.SortingAlgorithm> */::sidl::array< 
        ::sorting::SortingAlgorithm>& algs
    )
    ;


  };  // end class SortTest_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.SortTest._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.SortTest._hmisc)

#endif
