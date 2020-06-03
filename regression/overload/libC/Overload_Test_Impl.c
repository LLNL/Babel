/*
 * File:          Overload_Test_Impl.c
 * Symbol:        Overload.Test-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Overload.Test
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Overload.Test" (version 1.0)
 * 
 * This class is used as the work-horse, returning the value passed
 * in.
 */

#include "Overload_Test_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Overload.Test._includes) */
#include "sidl_String.h"
#include "sidl_Exception.h"
/* DO-NOT-DELETE splicer.end(Overload.Test._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_Test__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test._load) */
    /* DO-NOT-DELETE splicer.end(Overload.Test._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_Test__ctor(
  /* in */ Overload_Test self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.Test._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_Test__ctor2(
  /* in */ Overload_Test self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test._ctor2) */
  /* Insert-Code-Here {Overload.Test._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Overload.Test._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Overload_Test__dtor(
  /* in */ Overload_Test self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(Overload.Test._dtor) */
  }
}

/*
 * Method:  getValue[IntDouble]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueIntDouble"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueIntDouble(
  /* in */ Overload_Test self,
  /* in */ int32_t a,
  /* in */ double b,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble) */
  return ((double)a + b);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble) */
  }
}

/*
 * Method:  getValue[DoubleInt]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueDoubleInt"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueDoubleInt(
  /* in */ Overload_Test self,
  /* in */ double a,
  /* in */ int32_t b,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt) */
  return (a + (double)b);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt) */
  }
}

/*
 * Method:  getValue[IntDoubleFloat]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueIntDoubleFloat"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueIntDoubleFloat(
  /* in */ Overload_Test self,
  /* in */ int32_t a,
  /* in */ double b,
  /* in */ float c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat) */
  return ((double)a + b + (double)c);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat) */
  }
}

/*
 * Method:  getValue[DoubleIntFloat]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueDoubleIntFloat"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueDoubleIntFloat(
  /* in */ Overload_Test self,
  /* in */ double a,
  /* in */ int32_t b,
  /* in */ float c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat) */
  return (a + (double)b + (double)c);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat) */
  }
}

/*
 * Method:  getValue[IntFloatDouble]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueIntFloatDouble"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueIntFloatDouble(
  /* in */ Overload_Test self,
  /* in */ int32_t a,
  /* in */ float b,
  /* in */ double c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble) */
  return ((double)a + (double)b + c);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble) */
  }
}

/*
 * Method:  getValue[DoubleFloatInt]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueDoubleFloatInt"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueDoubleFloatInt(
  /* in */ Overload_Test self,
  /* in */ double a,
  /* in */ float b,
  /* in */ int32_t c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt) */
  return (a + (double)b + (double)c);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt) */
  }
}

/*
 * Method:  getValue[FloatIntDouble]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueFloatIntDouble"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueFloatIntDouble(
  /* in */ Overload_Test self,
  /* in */ float a,
  /* in */ int32_t b,
  /* in */ double c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble) */
  return ((double)a + (double)b + c);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble) */
  }
}

/*
 * Method:  getValue[FloatDoubleInt]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueFloatDoubleInt"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Overload_Test_getValueFloatDoubleInt(
  /* in */ Overload_Test self,
  /* in */ float a,
  /* in */ double b,
  /* in */ int32_t c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt) */
  return ((double)a + b + (double)c);
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt) */
  }
}

/*
 * Method:  getValue[Exception]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueException"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Overload_Test_getValueException(
  /* in */ Overload_Test self,
  /* in */ Overload_AnException v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueException) */
  char * retval = NULL;
  retval = Overload_AnException_getNote(v,_ex); SIDL_REPORT(*_ex);
  return retval;
 EXIT:
  return NULL;
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueException) */
  }
}

/*
 * Method:  getValue[AClass]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueAClass"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Overload_Test_getValueAClass(
  /* in */ Overload_Test self,
  /* in */ Overload_AClass v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass) */
  int32_t retval = Overload_AClass_getValue(v,_ex); SIDL_REPORT(*_ex);
  return retval;
 EXIT:
  return 0;
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass) */
  }
}

/*
 * Method:  getValue[BClass]
 */

#undef __FUNC__
#define __FUNC__ "impl_Overload_Test_getValueBClass"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Overload_Test_getValueBClass(
  /* in */ Overload_Test self,
  /* in */ Overload_BClass v,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass) */
  int32_t retval = Overload_BClass_getValue(v,_ex); SIDL_REPORT(*_ex);
 EXIT:
  return retval;
    /* DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

