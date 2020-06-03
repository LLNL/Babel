// 
// File:          sorting_Quicksort_Impl.hxx
// Symbol:        sorting.Quicksort-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Quicksort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_Quicksort_Impl_hxx
#define included_sorting_Quicksort_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_Quicksort_IOR_h
#include "sorting_Quicksort_IOR.h"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sorting_Comparator_hxx
#include "sorting_Comparator.hxx"
#endif
#ifndef included_sorting_Container_hxx
#include "sorting_Container.hxx"
#endif
#ifndef included_sorting_Counter_hxx
#include "sorting_Counter.hxx"
#endif
#ifndef included_sorting_Quicksort_hxx
#include "sorting_Quicksort.hxx"
#endif
#ifndef included_sorting_SortingAlgorithm_hxx
#include "sorting_SortingAlgorithm.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.Quicksort._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.Quicksort._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.Quicksort" (version 0.1)
   * 
   * Quick sort
   */
  class Quicksort_impl : public virtual ::sorting::Quicksort 
  // DO-NOT-DELETE splicer.begin(sorting.Quicksort._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.Quicksort._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.Quicksort._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(sorting.Quicksort._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Quicksort_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Quicksort_impl( struct sorting_Quicksort__object * ior ) : StubBase(ior,
      true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Quicksort_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Sort elements using Quick Sort.
     */
    void
    sort_impl (
      /* in */::sorting::Container& elems,
      /* in */::sorting::Comparator& comp
    )
    ;


    /**
     * Return quick sorting.
     */
    ::std::string
    getName_impl() ;
  };  // end class Quicksort_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.Quicksort._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.Quicksort._hmisc)

#endif
