// 
// File:          Exceptions_Fib_Impl.cxx
// Symbol:        Exceptions.Fib-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Exceptions.Fib
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Exceptions_Fib_Impl.hxx"

// 
// Includes for all method dependencies.
// 
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
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(Exceptions.Fib._includes)
#include "Exceptions_NegativeValueException.hxx"
#include "Exceptions_TooDeepException.hxx"
#include "Exceptions_TooBigException.hxx"
//using namespace ucxx::Exceptions;
// DO-NOT-DELETE splicer.end(Exceptions.Fib._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Exceptions::Fib_impl::Fib_impl() : StubBase(reinterpret_cast< void*>(
  ::Exceptions::Fib::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor2)
  // Insert-Code-Here {Exceptions.Fib._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor2)
}

// user defined constructor
void Exceptions::Fib_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor)
}

// user defined destructor
void Exceptions::Fib_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._dtor)
}

// static class initializer
void Exceptions::Fib_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Exceptions.Fib._load)
}

// user defined static methods: (none)

// user defined non-static methods:
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
Exceptions::Fib_impl::getFib_impl (
  /* in */int32_t n,
  /* in */int32_t max_depth,
  /* in */int32_t max_value,
  /* in */int32_t depth ) 
// throws:
//    ::Exceptions::FibException
//    ::Exceptions::NegativeValueException
//    ::sidl::MemAllocException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib.getFib)
  if (n < 0) {
    UCXX ::Exceptions::NegativeValueException ex = UCXX ::Exceptions::NegativeValueException::_create();
    ex.setNote("n negative");
    ex.add(__FILE__, __LINE__, "Exceptions::Fib_impl::getFib");
    throw ex;

  } else if (depth > max_depth) {
    UCXX ::Exceptions::TooDeepException ex = UCXX ::Exceptions::TooDeepException::_create();
    ex.setNote("too deep");
    ex.add(__FILE__, __LINE__, "Exceptions::Fib_impl::getFib");
    throw ex;

  } else if (n == 0) {
    return 1;

  } else if (n == 1) {
    return 1;

  } else {
    int32_t a = getFib(n-1, max_depth, max_value, depth+1);
    int32_t b = getFib(n-2, max_depth, max_value, depth+1);
    if (a + b > max_value) {
      UCXX ::Exceptions::TooBigException ex = UCXX ::Exceptions::TooBigException::_create();
      ex.setNote("too big");
      ex.add(__FILE__, __LINE__, "Exceptions::Fib_impl::getFib");
      throw ex;
    }
    return a + b;
  }
  // DO-NOT-DELETE splicer.end(Exceptions.Fib.getFib)
}

/**
 * Check for memory/reference leaks in the presence of an exception.
 * The impl will throw an exception and assign random values to
 * out parameters to prove that out values are ignored.
 * The intent is that row-major arrays should be passed to parameters
 * a1, a2, a3.
 */
::sidl::array<int32_t>
Exceptions::Fib_impl::noLeak_impl (
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
  /* out */::sidl::BaseClass& o3 ) 
// throws:
//    ::sidl::RuntimeException
//    ::sidl::SIDLException
{
  // DO-NOT-DELETE splicer.begin(Exceptions.Fib.noLeak)
  // No Action required. C++ should handle the cleanup.
  ::sidl::SIDLException ex = ::sidl::SIDLException::_create();
  ex.setNote("This method is supposed to throw an exception.");
  ex.add(__FILE__, __LINE__, "noLeak");
  throw ex;
  // DO-NOT-DELETE splicer.end(Exceptions.Fib.noLeak)
}


// DO-NOT-DELETE splicer.begin(Exceptions.Fib._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Exceptions.Fib._misc)

