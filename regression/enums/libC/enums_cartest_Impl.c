/*
 * File:          enums_cartest_Impl.c
 * Symbol:        enums.cartest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.cartest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "enums.cartest" (version 1.0)
 */

#include "enums_cartest_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(enums.cartest._includes) */
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(enums.cartest._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_cartest__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(enums.cartest._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_cartest__ctor(
  /* in */ enums_cartest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(enums.cartest._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_cartest__ctor2(
  /* in */ enums_cartest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest._ctor2) */
  /* Insert-Code-Here {enums.cartest._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(enums.cartest._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_cartest__dtor(
  /* in */ enums_cartest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(enums.cartest._dtor) */
  }
}

/*
 * Method:  returnback[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest_returnback"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_enums_cartest_returnback(
  /* in */ enums_cartest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest.returnback) */
  return enums_car_porsche;
    /* DO-NOT-DELETE splicer.end(enums.cartest.returnback) */
  }
}

/*
 * Method:  passin[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest_passin"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_cartest_passin(
  /* in */ enums_cartest self,
  /* in */ enum enums_car__enum c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest.passin) */
  return  c == enums_car_mercedes;    
    /* DO-NOT-DELETE splicer.end(enums.cartest.passin) */
  }
}

/*
 * Method:  passout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest_passout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_cartest_passout(
  /* in */ enums_cartest self,
  /* out */ enum enums_car__enum* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest.passout) */
  *c = enums_car_ford;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(enums.cartest.passout) */
  }
}

/*
 * Method:  passinout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest_passinout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_cartest_passinout(
  /* in */ enums_cartest self,
  /* inout */ enum enums_car__enum* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest.passinout) */
  switch( *c ) { 
  case enums_car_ford:
    *c=enums_car_porsche; return TRUE;
  case enums_car_porsche:
    *c=enums_car_mercedes; return TRUE;
  case enums_car_mercedes:
    return TRUE;
  default:
    return FALSE;
  }
    /* DO-NOT-DELETE splicer.end(enums.cartest.passinout) */
  }
}

/*
 * Method:  passeverywhere[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest_passeverywhere"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_enums_cartest_passeverywhere(
  /* in */ enums_cartest self,
  /* in */ enum enums_car__enum c1,
  /* out */ enum enums_car__enum* c2,
  /* inout */ enum enums_car__enum* c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere) */
  *c2 = enums_car_ford;
  switch( *c3 ) { 
  case enums_car_ford:
    *c3=enums_car_porsche; 
    break;
  case enums_car_porsche:
    *c3=enums_car_mercedes; 
    break;
  case enums_car_mercedes:
    break;
  default:
    return FALSE;
  }
  return ( c1 == enums_car_mercedes ) ? enums_car_porsche : FALSE ;
    /* DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere) */
  }
}

/*
 * All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
 * in that order.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_cartest_passarray"

#ifdef __cplusplus
extern "C"
#endif
struct enums_car__array*
impl_enums_cartest_passarray(
  /* in */ enums_cartest self,
  /* in array<enums.car> */ struct enums_car__array* c1,
  /* out array<enums.car> */ struct enums_car__array** c2,
  /* inout array<enums.car> */ struct enums_car__array** c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.cartest.passarray) */
    enum enums_car__enum vals[] = { 
      enums_car_ford,
      enums_car_mercedes,
      enums_car_porsche
    };
    int32_t i;
    sidl_bool failed = 0;
    struct enums_car__array *retval = NULL;
    if (c1 && c3 && *c3 &&
        (enums_car__array_length(c1,0) == 3) &&
        (enums_car__array_length(*c3,0) == 3)){
      *c2 = enums_car__array_create1d(3);
      retval = enums_car__array_create1d(3);
      for(i = 0; i < 3; ++i) {
        enums_car__array_set1(*c2, i, vals[i]);
        enums_car__array_set1(retval, i, vals[i]);
        failed = (failed ||
                  (enums_car__array_get1(c1, 
                                         i + enums_car__array_lower(c1, 0))
                   != vals[i]) ||
                  (enums_car__array_get1(*c3, 
                                         i + enums_car__array_lower(*c3, 0))
                   != vals[i]));
      }
      if (failed) {
        enums_car__array_deleteRef(*c2);
        *c2 = NULL;
        enums_car__array_deleteRef(retval);
        retval = NULL;
      }
    }
    else {
      *c2 = NULL;
    }
    return retval;
    /* DO-NOT-DELETE splicer.end(enums.cartest.passarray) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

