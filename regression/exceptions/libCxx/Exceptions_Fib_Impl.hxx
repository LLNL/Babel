// 
// File:          Exceptions_Fib_Impl.hxx
// Symbol:        Exceptions.Fib-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Exceptions.Fib
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Exceptions_Fib_Impl_hxx
#define included_Exceptions_Fib_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Exceptions_Fib_IOR_h
#include "Exceptions_Fib_IOR.h"
#endif
#ifndef included_Exceptions_Fib_hxx
#include "Exceptions_Fib.hxx"
#endif
#ifndef included_Exceptions_FibException_hxx
#include "Exceptions_FibException.hxx"
#endif
#ifndef included_Exceptions_NegativeValueException_hxx
#include "Exceptions_NegativeValueException.hxx"
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
#ifndef included_sidl_MemAllocException_hxx
#include "sidl_MemAllocException.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif
#ifndef included_sidl_SIDLException_hxx
#include "sidl_SIDLException.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Exceptions.Fib._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Exceptions.Fib._hincludes)

namespace Exceptions { 

  /**
   * Symbol "Exceptions.Fib" (version 1.0)
   * 
   * This class holds the method <code>getFib</code> that generates the
   * requested Fibonacci numbers.
   */
  class Fib_impl : public virtual ::Exceptions::Fib 
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Exceptions.Fib._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Exceptions.Fib._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Fib_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Fib_impl( struct Exceptions_Fib__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Fib_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * <p>
     * Generate the requested Fibonacci number or generate Exceptions if
     * the input Fibonacci number is invalid or if any of the maximum depth
     * or maximum value parameters are exceeded.  The last argument of the
     * method should be zero.
     * </p>
     * <p>
     * The algorithm should be similar to the <code>Java</code> code below.
     * </p>
     * <pre>
     * public int getFib(int n, int max_depth, int max_value, int depth)
     * throws NegativeValueException, FibException {
     * 
     * if (n < 0) {
     * throw new NegativeValueException("n negative");
     * 
     * } else if (depth > max_depth) {
     * throw new TooDeepException("too deep");
     * 
     * } else if (n == 0) {
     * return 1;
     * 
     * } else if (n == 1) {
     * return 1;
     * 
     * } else {
     * int a = getFib(n-1, max_depth, max_value, depth+1);
     * int b = getFib(n-2, max_depth, max_value, depth+1);
     * if (a + b > max_value) {
     * throw new TooBigException("too big");
     * }
     * return a + b;
     * }
     * } 
     * </pre>
     */
    int32_t
    getFib_impl (
      /* in */int32_t n,
      /* in */int32_t max_depth,
      /* in */int32_t max_value,
      /* in */int32_t depth
    )
    // throws:
    //    ::Exceptions::FibException
    //    ::Exceptions::NegativeValueException
    //    ::sidl::MemAllocException
    //    ::sidl::RuntimeException
    ;


    /**
     * Check for memory/reference leaks in the presence of an exception.
     * The impl will throw an exception and assign random values to
     * out parameters to prove that out values are ignored.
     * The intent is that row-major arrays should be passed to parameters
     * a1, a2, a3.
     */
    ::sidl::array<int32_t>
    noLeak_impl (
      /* in array<int,2,column-major> */::sidl::array<int32_t>& a1,
      /* inout array<int,2,column-major> */::sidl::array<int32_t>& a2,
      /* out array<int,2,column-major> */::sidl::array<int32_t>& a3,
      /* in rarray[m,n] */int32_t* r1,
      /* inout rarray[m,n] */int32_t* r2,
      /* in */int32_t m,
      /* in */int32_t n,
      /* in array<int> */::sidl::array<int32_t>& c1,
      /* inout array<int> */::sidl::array<int32_t>& c2,
      /* out array<int> */::sidl::array<int32_t>& c3,
      /* in */const ::std::string& s1,
      /* inout */::std::string& s2,
      /* out */::std::string& s3,
      /* in */::sidl::BaseClass& o1,
      /* inout */::sidl::BaseClass& o2,
      /* out */::sidl::BaseClass& o3
    )
    // throws:
    //    ::sidl::RuntimeException
    //    ::sidl::SIDLException
    ;

  };  // end class Fib_impl

} // end namespace Exceptions

// DO-NOT-DELETE splicer.begin(Exceptions.Fib._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Exceptions.Fib._hmisc)

#endif
