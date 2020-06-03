/*
 * File:          enums_colorwheel_Impl.c
 * Symbol:        enums.colorwheel-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for enums.colorwheel
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "enums.colorwheel" (version 1.0)
 */

#include "enums_colorwheel_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(enums.colorwheel._includes) */
/* Put additional includes or other arbitrary code here... */
/* DO-NOT-DELETE splicer.end(enums.colorwheel._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_colorwheel__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(enums.colorwheel._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_colorwheel__ctor(
  /* in */ enums_colorwheel self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(enums.colorwheel._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_colorwheel__ctor2(
  /* in */ enums_colorwheel self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor2) */
  /* Insert-Code-Here {enums.colorwheel._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(enums.colorwheel._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_enums_colorwheel__dtor(
  /* in */ enums_colorwheel self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(enums.colorwheel._dtor) */
  }
}

/*
 * Method:  returnback[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel_returnback"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_enums_colorwheel_returnback(
  /* in */ enums_colorwheel self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback) */
  return enums_color_violet;
    /* DO-NOT-DELETE splicer.end(enums.colorwheel.returnback) */
  }
}

/*
 * Method:  passin[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel_passin"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_colorwheel_passin(
  /* in */ enums_colorwheel self,
  /* in */ enum enums_color__enum c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel.passin) */
  return ( c == enums_color_blue );
    /* DO-NOT-DELETE splicer.end(enums.colorwheel.passin) */
  }
}

/*
 * Method:  passout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel_passout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_colorwheel_passout(
  /* in */ enums_colorwheel self,
  /* out */ enum enums_color__enum* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel.passout) */
  *c = enums_color_violet;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(enums.colorwheel.passout) */
  }
}

/*
 * Method:  passinout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel_passinout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_enums_colorwheel_passinout(
  /* in */ enums_colorwheel self,
  /* inout */ enum enums_color__enum* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout) */
  switch ( *c ) { 
  case enums_color_red:
    *c = enums_color_green; 
    break;
  case enums_color_orange:
    *c = enums_color_blue;
    break;
  case enums_color_yellow:
    *c = enums_color_violet;
    break;
  case enums_color_green:
    *c = enums_color_red;
    break;
  case enums_color_blue:
    *c = enums_color_orange;
    break;
  case enums_color_violet:
    *c = enums_color_yellow;
    break;
  default:
    return FALSE;
  }
  return TRUE;
    /* DO-NOT-DELETE splicer.end(enums.colorwheel.passinout) */
  }
}

/*
 * Method:  passeverywhere[]
 */

#undef __FUNC__
#define __FUNC__ "impl_enums_colorwheel_passeverywhere"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_enums_colorwheel_passeverywhere(
  /* in */ enums_colorwheel self,
  /* in */ enum enums_color__enum c1,
  /* out */ enum enums_color__enum* c2,
  /* inout */ enum enums_color__enum* c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere) */
  *c2 = enums_color_violet;
  switch ( *c3 ) { 
  case enums_color_red:
    *c3 = enums_color_green; 
    break;
  case enums_color_orange:
    *c3 = enums_color_blue;
    break;
  case enums_color_yellow:
    *c3 = enums_color_violet;
    break;
  case enums_color_green:
    *c3 = enums_color_red;
    break;
  case enums_color_blue:
    *c3 = enums_color_orange;
    break;
  case enums_color_violet:
    *c3 = enums_color_yellow;
    break;
  default:
    return FALSE;
  }
  return ( c1 == enums_color_blue ) ? enums_color_violet : FALSE;
    /* DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

