/*
 * File:          enums_numbertest_Impl.c
 * Symbol:        enums.numbertest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.numbertest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "enums.numbertest" (version 1.0)
 */

#include "enums_numbertest_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(enums.numbertest._includes) */
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(enums.numbertest._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_numbertest__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(enums.numbertest._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_numbertest__ctor(
  /* in */ enums_numbertest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(enums.numbertest._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_numbertest__ctor2(
  /* in */ enums_numbertest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest._ctor2) */
  /* Insert-Code-Here {enums.numbertest._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(enums.numbertest._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_numbertest__dtor(
  /* in */ enums_numbertest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(enums.numbertest._dtor) */
  }
}

/*
 * Method:  returnback[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest_returnback"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_enums_numbertest_returnback(
  /* in */ enums_numbertest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest.returnback) */
  return enums_number_notOne;
    /* DO-NOT-DELETE splicer.end(enums.numbertest.returnback) */
  }
}

/*
 * Method:  passin[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest_passin"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_numbertest_passin(
  /* in */ enums_numbertest self,
  /* in */ enum enums_number__enum n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest.passin) */
  return ( n == enums_number_notZero );
    /* DO-NOT-DELETE splicer.end(enums.numbertest.passin) */
  }
}

/*
 * Method:  passout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest_passout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_numbertest_passout(
  /* in */ enums_numbertest self,
  /* out */ enum enums_number__enum* n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest.passout) */
  *n = enums_number_negOne;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(enums.numbertest.passout) */
  }
}

/*
 * Method:  passinout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest_passinout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_numbertest_passinout(
  /* in */ enums_numbertest self,
  /* inout */ enum enums_number__enum* n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest.passinout) */
  switch ( *n ) { 
  case enums_number_zero:
    *n = enums_number_notZero;
    break;
  case enums_number_one:
    *n = enums_number_notOne;
    break;
  case enums_number_negOne:
    *n = enums_number_notNeg;
    break;
  case enums_number_notZero:
    *n = enums_number_zero;
    break;
  case enums_number_notOne:
    *n = enums_number_one;
    break;
  case enums_number_notNeg:
    *n = enums_number_negOne;
    break;
  default:
    return FALSE;
  }
  return TRUE;
    /* DO-NOT-DELETE splicer.end(enums.numbertest.passinout) */
  }
}

/*
 * Method:  passeverywhere[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_numbertest_passeverywhere"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_enums_numbertest_passeverywhere(
  /* in */ enums_numbertest self,
  /* in */ enum enums_number__enum n1,
  /* out */ enum enums_number__enum* n2,
  /* inout */ enum enums_number__enum* n3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere) */
  *n2 = enums_number_negOne;
  switch ( *n3 ) { 
  case enums_number_zero:
    *n3 = enums_number_notZero;
    break;
  case enums_number_one:
    *n3 = enums_number_notOne;
    break;
  case enums_number_negOne:
    *n3 = enums_number_notNeg;
    break;
  case enums_number_notZero:
    *n3 = enums_number_zero;
    break;
  case enums_number_notOne:
    *n3 = enums_number_one;
    break;
  case enums_number_notNeg:
    *n3 = enums_number_negOne;
    break;
  default:
    return FALSE;
  }
  return ( n1 == enums_number_notZero ) ? enums_number_notOne : FALSE ;
    /* DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

