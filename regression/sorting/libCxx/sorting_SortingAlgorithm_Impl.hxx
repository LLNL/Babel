// 
// File:          sorting_SortingAlgorithm_Impl.hxx
// Symbol:        sorting.SortingAlgorithm-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.SortingAlgorithm
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_SortingAlgorithm_Impl_hxx
#define included_sorting_SortingAlgorithm_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_SortingAlgorithm_IOR_h
#include "sorting_SortingAlgorithm_IOR.h"
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
#ifndef included_sorting_Comparator_hxx
#include "sorting_Comparator.hxx"
#endif
#ifndef included_sorting_Container_hxx
#include "sorting_Container.hxx"
#endif
#ifndef included_sorting_Counter_hxx
#include "sorting_Counter.hxx"
#endif
#ifndef included_sorting_SortingAlgorithm_hxx
#include "sorting_SortingAlgorithm.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._hincludes)
// Put additional includes or other arbitrary code here...
#ifndef included_sorting_Counter_hh
#include "sorting_Counter.hxx"
#endif
// DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.SortingAlgorithm" (version 0.1)
   * 
   * An abstract sorting algorithm.
   */
  class SortingAlgorithm_impl : public virtual ::sorting::SortingAlgorithm 
  // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._implementation)
    ::sorting::Counter d_cmp;
    ::sorting::Counter d_swp;
    // DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._implementation)

  private:
    // default constructor, do not use! 
    SortingAlgorithm_impl();
    public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    SortingAlgorithm_impl( struct sorting_SortingAlgorithm__object * ior ) : 
      StubBase(ior,true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~SortingAlgorithm_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Return the comparison counter.
     */
    ::sorting::Counter
    getCompareCounter_impl() ;

    /**
     * Return the swap counter.
     */
    ::sorting::Counter
    getSwapCounter_impl() ;

    /**
     * Reset the comparison and swap counter.
     */
    void
    reset_impl() ;
  };  // end class SortingAlgorithm_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._hmisc)

#endif
