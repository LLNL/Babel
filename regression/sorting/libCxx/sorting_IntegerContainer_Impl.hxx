// 
// File:          sorting_IntegerContainer_Impl.hxx
// Symbol:        sorting.IntegerContainer-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.IntegerContainer
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_IntegerContainer_Impl_hxx
#define included_sorting_IntegerContainer_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_IntegerContainer_IOR_h
#include "sorting_IntegerContainer_IOR.h"
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
#ifndef included_sorting_IntegerContainer_hxx
#include "sorting_IntegerContainer.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._hincludes)
#include "sorting_Integer.hxx"
// DO-NOT-DELETE splicer.end(sorting.IntegerContainer._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.IntegerContainer" (version 0.1)
   * 
   * Integer container.
   */
  class IntegerContainer_impl : public virtual ::sorting::IntegerContainer 
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._implementation)
    ::sidl::array< ::sorting::Integer> d_elements;
    // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._implementation)

  public:
    // default constructor, used for data wrapping(required)
    IntegerContainer_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      IntegerContainer_impl( struct sorting_IntegerContainer__object * ior ) : 
        StubBase(ior,true), 
    ::sorting::Container((ior==NULL) ? NULL : &((*ior).d_sorting_container)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~IntegerContainer_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * This sets the container length and pseudo-randomly orders the
     * Integer elements contained.
     */
    void
    setLength_impl (
      /* in */int32_t len
    )
    ;


    /**
     * Return the number of elements in the container.
     */
    int32_t
    getLength_impl() ;

    /**
     * Return -1 if element i is less than element j, 0 if element i
     * is equal to element j, or otherwise 1.
     */
    int32_t
    compare_impl (
      /* in */int32_t i,
      /* in */int32_t j,
      /* in */::sorting::Comparator& comp
    )
    ;


    /**
     * Swap elements i and j.
     */
    void
    swap_impl (
      /* in */int32_t i,
      /* in */int32_t j
    )
    ;


    /**
     * Print elements s through e-1
     */
    void
    output_impl (
      /* in */int32_t s,
      /* in */int32_t e
    )
    ;

  };  // end class IntegerContainer_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.IntegerContainer._hmisc)

#endif
