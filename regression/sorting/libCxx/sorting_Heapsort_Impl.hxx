// 
// File:          sorting_Heapsort_Impl.hxx
// Symbol:        sorting.Heapsort-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.Heapsort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_Heapsort_Impl_hxx
#define included_sorting_Heapsort_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_Heapsort_IOR_h
#include "sorting_Heapsort_IOR.h"
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
#ifndef included_sorting_Heapsort_hxx
#include "sorting_Heapsort.hxx"
#endif
#ifndef included_sorting_SortingAlgorithm_hxx
#include "sorting_SortingAlgorithm.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.Heapsort._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.Heapsort._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.Heapsort" (version 0.1)
   * 
   * Heap sort
   */
  class Heapsort_impl : public virtual ::sorting::Heapsort 
  // DO-NOT-DELETE splicer.begin(sorting.Heapsort._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.Heapsort._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.Heapsort._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(sorting.Heapsort._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Heapsort_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Heapsort_impl( struct sorting_Heapsort__object * ior ) : StubBase(ior,true) 
      , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Heapsort_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Sort elements using Heap Sort.
     */
    void
    sort_impl (
      /* in */::sorting::Container& elems,
      /* in */::sorting::Comparator& comp
    )
    ;


    /**
     * Return heap sorting.
     */
    ::std::string
    getName_impl() ;
  };  // end class Heapsort_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.Heapsort._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.Heapsort._hmisc)

#endif
