/*
 * File:          Overload_ParentTest_Impl.c
 * Symbol:        Overload.ParentTest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.ParentTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Overload.ParentTest" (version 1.0)
 * 
 * This class is used as the work-horse, returning the value passed
 * in.
 */

#include "Overload_ParentTest_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Overload.ParentTest._includes) */
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Overload.ParentTest._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_ParentTest__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_ParentTest__ctor(
  /* in */ Overload_ParentTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_ParentTest__ctor2(
  /* in */ Overload_ParentTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor2) */
  /* Insert-Code-Here {Overload.ParentTest._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_ParentTest__dtor(
  /* in */ Overload_ParentTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor) */
  }
}

/*
 * Method:  getValue[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValue"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Overload_ParentTest_getValue(
  /* in */ Overload_ParentTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue) */
  return 1;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue) */
  }
}

/*
 * Method:  getValue[Int]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueInt"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Overload_ParentTest_getValueInt(
  /* in */ Overload_ParentTest self,
  /* in */ int32_t v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt) */
  return v;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt) */
  }
}

/*
 * Method:  getValue[Bool]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueBool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Overload_ParentTest_getValueBool(
  /* in */ Overload_ParentTest self,
  /* in */ sidl_bool v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool) */
  return v;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool) */
  }
}

/*
 * Method:  getValue[Double]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueDouble"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_ParentTest_getValueDouble(
  /* in */ Overload_ParentTest self,
  /* in */ double v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble) */
  return v;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble) */
  }
}

/*
 * Method:  getValue[Dcomplex]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueDcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_dcomplex
impl_Overload_ParentTest_getValueDcomplex(
  /* in */ Overload_ParentTest self,
  /* in */ struct sidl_dcomplex v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex) */
  return v;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex) */
  }
}

/*
 * Method:  getValue[Float]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueFloat"

#ifdef __cplusplus
extern "C"
#endif
float
impl_Overload_ParentTest_getValueFloat(
  /* in */ Overload_ParentTest self,
  /* in */ float v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat) */
  return v;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat) */
  }
}

/*
 * Method:  getValue[Fcomplex]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueFcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_fcomplex
impl_Overload_ParentTest_getValueFcomplex(
  /* in */ Overload_ParentTest self,
  /* in */ struct sidl_fcomplex v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex) */
  return v;
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex) */
  }
}

/*
 * Method:  getValue[String]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_ParentTest_getValueString"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Overload_ParentTest_getValueString(
  /* in */ Overload_ParentTest self,
  /* in */ const char* v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString) */
  return sidl_String_strdup(v);
    /* DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

