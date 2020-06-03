// 
// File:          enums_cartest_Impl.cxx
// Symbol:        enums.cartest-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for enums.cartest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "enums_cartest_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_enums_car_hxx
#include "enums_car.hxx"
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
// DO-NOT-DELETE splicer.begin(enums.cartest._includes)
using namespace UCXX ::enums;
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(enums.cartest._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
enums::cartest_impl::cartest_impl() : StubBase(reinterpret_cast< void*>(
  ::enums::cartest::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(enums.cartest._ctor2)
  // Insert-Code-Here {enums.cartest._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(enums.cartest._ctor2)
}

// user defined constructor
void enums::cartest_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(enums.cartest._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(enums.cartest._ctor)
}

// user defined destructor
void enums::cartest_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(enums.cartest._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(enums.cartest._dtor)
}

// static class initializer
void enums::cartest_impl::_load() {
  // DO-NOT-DELETE splicer.begin(enums.cartest._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(enums.cartest._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  returnback[]
 */
::enums::car
enums::cartest_impl::returnback_impl () 

{
  // DO-NOT-DELETE splicer.begin(enums.cartest.returnback)
  return car_porsche;
  // DO-NOT-DELETE splicer.end(enums.cartest.returnback)
}

/**
 * Method:  passin[]
 */
bool
enums::cartest_impl::passin_impl (
  /* in */::enums::car c ) 
{
  // DO-NOT-DELETE splicer.begin(enums.cartest.passin)
  return c == car_mercedes;
  // DO-NOT-DELETE splicer.end(enums.cartest.passin)
}

/**
 * Method:  passout[]
 */
bool
enums::cartest_impl::passout_impl (
  /* out */::enums::car& c ) 
{
  // DO-NOT-DELETE splicer.begin(enums.cartest.passout)
  c = car_ford;
  return true;
  // DO-NOT-DELETE splicer.end(enums.cartest.passout)
}

/**
 * Method:  passinout[]
 */
bool
enums::cartest_impl::passinout_impl (
  /* inout */::enums::car& c ) 
{
  // DO-NOT-DELETE splicer.begin(enums.cartest.passinout)
  switch( c ) { 
  case car_ford:
    c=car_porsche; 
    break;
  case car_porsche:
    c=car_mercedes;
    break;
  case car_mercedes:
    break;
  default:
    return false;
  }
  return true;
  // DO-NOT-DELETE splicer.end(enums.cartest.passinout)
}

/**
 * Method:  passeverywhere[]
 */
::enums::car
enums::cartest_impl::passeverywhere_impl (
  /* in */::enums::car c1,
  /* out */::enums::car& c2,
  /* inout */::enums::car& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere)
  c2 = car_ford;
  switch(c3){
  case car_ford:
    c3=car_porsche;
    break;
  case car_porsche:
    c3=car_mercedes;
    break;
  case car_mercedes:
    break;
  default:
    return (car )false;
  }
  return ( c1 == car_mercedes ) ? car_porsche : (car )false;
  // DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere)
}

/**
 * All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
 * in that order.
 */
::sidl::array< ::enums::car>
enums::cartest_impl::passarray_impl (
  /* in array<enums.car> */::sidl::array< ::enums::car>& c1,
  /* out array<enums.car> */::sidl::array< ::enums::car>& c2,
  /* inout array<enums.car> */::sidl::array< ::enums::car>& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(enums.cartest.passarray)
  ::enums::car vals[] = {
    car_ford,
    car_mercedes,
    car_porsche
  };
  ::sidl::array< ::enums::car> retval;
  int32_t i;
  bool failed = false;
  if (c1._not_nil() && c3._not_nil() && (c1.length(0) == 3) &&
      (c3.length(0) == 3)) {
    c2 = ::sidl::array< ::enums::car>::create1d(3);
    retval = ::sidl::array< ::enums::car>::create1d(3);
    for(i = 0; i < 3; ++i) {
      c2.set(i, vals[i]);
      retval.set(i, vals[i]);
      failed = (failed ||
		(c1[i + c1.lower(0)] != vals[i]) ||
		(c3.get(i + c3.lower(0)) != vals[i]));
    }
    if (failed) {
      retval.deleteRef();
      c2.deleteRef();
    }
  }
  return retval;
  // DO-NOT-DELETE splicer.end(enums.cartest.passarray)
}


// DO-NOT-DELETE splicer.begin(enums.cartest._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(enums.cartest._misc)

