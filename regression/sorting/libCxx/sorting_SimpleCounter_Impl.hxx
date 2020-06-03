// 
// File:          sorting_SimpleCounter_Impl.hxx
// Symbol:        sorting.SimpleCounter-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.SimpleCounter
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_sorting_SimpleCounter_Impl_hxx
#define included_sorting_SimpleCounter_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_sorting_SimpleCounter_IOR_h
#include "sorting_SimpleCounter_IOR.h"
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
#ifndef included_sorting_Counter_hxx
#include "sorting_Counter.hxx"
#endif
#ifndef included_sorting_SimpleCounter_hxx
#include "sorting_SimpleCounter.hxx"
#endif


// DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(sorting.SimpleCounter._hincludes)

namespace sorting { 

  /**
   * Symbol "sorting.SimpleCounter" (version 0.1)
   * 
   * Simple counter
   */
  class SimpleCounter_impl : public virtual ::sorting::SimpleCounter 
  // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._implementation)
    int32_t d_counter;
    // DO-NOT-DELETE splicer.end(sorting.SimpleCounter._implementation)

  public:
    // default constructor, used for data wrapping(required)
    SimpleCounter_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      SimpleCounter_impl( struct sorting_SimpleCounter__object * ior ) : 
        StubBase(ior,true), 
    ::sorting::Counter((ior==NULL) ? NULL : &((*ior).d_sorting_counter)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~SimpleCounter_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Set the count to zero.
     */
    void
    reset_impl() ;

    /**
     * Return the current count.
     */
    int32_t
    getCount_impl() ;

    /**
     * Increment the count (i.e. add one).
     */
    int32_t
    inc_impl() ;
  };  // end class SimpleCounter_impl

} // end namespace sorting

// DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(sorting.SimpleCounter._hmisc)

#endif
