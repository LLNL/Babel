// 
// File:          enums_numbertest_Impl.cxx
// Symbol:        enums.numbertest-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for enums.numbertest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "enums_numbertest_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_enums_number_hxx
#include "enums_number.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(enums.numbertest._includes)
using namespace UCXX ::enums;
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(enums.numbertest._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
enums::numbertest_impl::numbertest_impl() : StubBase(reinterpret_cast< void*>(
  ::enums::numbertest::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(enums.numbertest._ctor2)
  // Insert-Code-Here {enums.numbertest._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(enums.numbertest._ctor2)
}

// user defined constructor
void enums::numbertest_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(enums.numbertest._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(enums.numbertest._ctor)
}

// user defined destructor
void enums::numbertest_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(enums.numbertest._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(enums.numbertest._dtor)
}

// static class initializer
void enums::numbertest_impl::_load() {
  // DO-NOT-DELETE splicer.begin(enums.numbertest._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(enums.numbertest._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  returnback[]
 */
::enums::number
enums::numbertest_impl::returnback_impl () 

{
  // DO-NOT-DELETE splicer.begin(enums.numbertest.returnback)
  return number_notOne;
  // DO-NOT-DELETE splicer.end(enums.numbertest.returnback)
}

/**
 * Method:  passin[]
 */
bool
enums::numbertest_impl::passin_impl (
  /* in */::enums::number n ) 
{
  // DO-NOT-DELETE splicer.begin(enums.numbertest.passin)
  return ( n == number_notZero );
  // DO-NOT-DELETE splicer.end(enums.numbertest.passin)
}

/**
 * Method:  passout[]
 */
bool
enums::numbertest_impl::passout_impl (
  /* out */::enums::number& n ) 
{
  // DO-NOT-DELETE splicer.begin(enums.numbertest.passout)
  n = number_negOne;
  return true;
  // DO-NOT-DELETE splicer.end(enums.numbertest.passout)
}

/**
 * Method:  passinout[]
 */
bool
enums::numbertest_impl::passinout_impl (
  /* inout */::enums::number& n ) 
{
  // DO-NOT-DELETE splicer.begin(enums.numbertest.passinout)
  switch ( n ) { 
  case number_zero:
    n = number_notZero;
    break;
  case number_one:
    n = number_notOne;
    break;
  case number_negOne:
    n = number_notNeg;
    break;
  case number_notZero:
    n = number_zero;
    break;
  case number_notOne:
    n = number_one;
    break;
  case number_notNeg:
    n = number_negOne;
    break;
  default:
    return false;
  }
  return true;
  // DO-NOT-DELETE splicer.end(enums.numbertest.passinout)
}

/**
 * Method:  passeverywhere[]
 */
::enums::number
enums::numbertest_impl::passeverywhere_impl (
  /* in */::enums::number n1,
  /* out */::enums::number& n2,
  /* inout */::enums::number& n3 ) 
{
  // DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere)
  n2 = number_negOne;
  switch ( n3 ) { 
  case number_zero:
    n3 = number_notZero;
    break;
  case number_one:
    n3 = number_notOne;
    break;
  case number_negOne:
    n3 = number_notNeg;
    break;
  case number_notZero:
    n3 = number_zero;
    break;
  case number_notOne:
    n3 = number_one;
    break;
  case number_notNeg:
    n3 = number_negOne;
    break;
  default:
    return (number) false;
  }
  return ( n1 == number_notZero ) ? number_notOne : (number) false ;
  // DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere)
}


// DO-NOT-DELETE splicer.begin(enums.numbertest._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(enums.numbertest._misc)

