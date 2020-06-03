// 
// File:          Ordering_IntOrderTest_Impl.hxx
// Symbol:        Ordering.IntOrderTest-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Ordering.IntOrderTest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Ordering_IntOrderTest_Impl_hxx
#define included_Ordering_IntOrderTest_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Ordering_IntOrderTest_IOR_h
#include "Ordering_IntOrderTest_IOR.h"
#endif
#ifndef included_Ordering_IntOrderTest_hxx
#include "Ordering_IntOrderTest.hxx"
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


// DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._hincludes)

namespace Ordering { 

  /**
   * Symbol "Ordering.IntOrderTest" (version 0.1)
   * 
   * This class provides methods to verify that the array ordering
   * capabilities work for arrays of int.
   */
  class IntOrderTest_impl : public virtual ::Ordering::IntOrderTest 
  // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._implementation)

  public:
    // default constructor, used for data wrapping(required)
    IntOrderTest_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    IntOrderTest_impl( struct Ordering_IntOrderTest__object * ior ) : StubBase(
      ior,true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~IntOrderTest_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * Create a column-major matrix satisfying condition I.
     */
    static ::sidl::array<int32_t>
    makeColumnIMatrix_impl (
      /* in */int32_t size,
      /* in */bool useCreateCol
    )
    ;


    /**
     * Create a row-major matrix satisfying condition I.
     */
    static ::sidl::array<int32_t>
    makeRowIMatrix_impl (
      /* in */int32_t size,
      /* in */bool useCreateRow
    )
    ;


    /**
     * Create a 4-D matrix satisfying condition I.  Each dimension has
     * size elements numbers 0 through size-1.
     */
    static ::sidl::array<int32_t>
    makeIMatrix_impl (
      /* in */int32_t size,
      /* in */bool useCreateColumn
    )
    ;


    /**
     * Create a column-major matrix satisfying condition I.
     */
    static void
    createColumnIMatrix_impl (
      /* in */int32_t size,
      /* in */bool useCreateCol,
      /* out array<int,2,column-major> */::sidl::array<int32_t>& res
    )
    ;


    /**
     * Create a row-major matrix satisfying condition I.
     */
    static void
    createRowIMatrix_impl (
      /* in */int32_t size,
      /* in */bool useCreateRow,
      /* out array<int,2,row-major> */::sidl::array<int32_t>& res
    )
    ;


    /**
     * Make sure an array is column-major.  No changes to the dimension or
     * values in a are made.
     */
    static void
    ensureColumn_impl (
      /* inout array<int,2,column-major> */::sidl::array<int32_t>& a
    )
    ;


    /**
     * Make sure an array is row-major.  No changes to the dimension or
     * values in a are made.
     */
    static void
    ensureRow_impl (
      /* inout array<int,2,row-major> */::sidl::array<int32_t>& a
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming array satisfying condition I.
     */
    static bool
    isIMatrixOne_impl (
      /* in array<int> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming column-major array satisfying condition I.
     */
    static bool
    isColumnIMatrixOne_impl (
      /* in array<int,column-major> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming row-major array satisfying condition I.
     */
    static bool
    isRowIMatrixOne_impl (
      /* in array<int,row-major> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming array satisfying condition I.
     */
    static bool
    isIMatrixTwo_impl (
      /* in array<int,2> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming column-major array satisfying condition I.
     */
    static bool
    isColumnIMatrixTwo_impl (
      /* in array<int,2,column-major> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming row-major array satisfying condition I.
     */
    static bool
    isRowIMatrixTwo_impl (
      /* in array<int,2,row-major> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming array satisfying condition I.
     */
    static bool
    isIMatrixFour_impl (
      /* in array<int,4> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming column-major array satisfying condition I.
     */
    static bool
    isColumnIMatrixFour_impl (
      /* in array<int,4,column-major> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation sees
     * an incoming row-major array satisfying condition I.
     */
    static bool
    isRowIMatrixFour_impl (
      /* in array<int,4,row-major> */::sidl::array<int32_t>& A
    )
    ;


    /**
     * Return <code>true</code> iff the implementation of slice
     * and smart copy is correct.
     */
    static bool
    isSliceWorking_impl (
      /* in */bool useCreateCol
    )
    ;


  };  // end class IntOrderTest_impl

} // end namespace Ordering

// DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._hmisc)

#endif
