/*
 * File:          objarg_Basic_Impl.c
 * Symbol:        objarg.Basic-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for objarg.Basic
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "objarg.Basic" (version 0.5)
 */

#include "objarg_Basic_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(objarg.Basic._includes) */
/* Insert-Code-Here {objarg.Basic._includes} (includes and arbitrary code) */
/* DO-NOT-DELETE splicer.end(objarg.Basic._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Basic__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic._load) */
    /* DO-NOT-DELETE splicer.end(objarg.Basic._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Basic__ctor(
  /* in */ objarg_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic._ctor) */
    /* Insert-Code-Here {objarg.Basic._ctor} (constructor method) */
    /*
     * // boilerplate constructor
     * struct objarg_Basic__data *dptr = (struct objarg_Basic__data*)malloc(sizeof(struct objarg_Basic__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct objarg_Basic__data));
     *   // initialize elements of dptr here
     * }
     * objarg_Basic__set_data(self, dptr);
     */

    /* DO-NOT-DELETE splicer.end(objarg.Basic._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Basic__ctor2(
  /* in */ objarg_Basic self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic._ctor2) */
    /* DO-NOT-DELETE splicer.end(objarg.Basic._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Basic__dtor(
  /* in */ objarg_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic._dtor) */
    /* Insert-Code-Here {objarg.Basic._dtor} (destructor method) */
    /*
     * // boilerplate destructor
     * struct objarg_Basic__data *dptr = objarg_Basic__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   objarg_Basic__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(objarg.Basic._dtor) */
  }
}

/*
 * Return inNotNull == (o != NULL).
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic_passIn"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_objarg_Basic_passIn(
  /* in */ objarg_Basic self,
  /* in */ sidl_BaseClass o,
  /* in */ sidl_bool inNotNull,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic.passIn) */
    return (inNotNull && (o != NULL)) ||
      !(inNotNull || (o != NULL));
    /* DO-NOT-DELETE splicer.end(objarg.Basic.passIn) */
  }
}

/*
 * Return inNotNull == (o != NULL).  If outNotNull, the outgoing
 * value of o should not be NULL; otherwise, it will be NULL.
 * If outNotNull is true, there are two cases, it retSame is true
 * the incoming value of o will be returned; otherwise, a new
 * object will be allocated and returned.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic_passInOut"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_objarg_Basic_passInOut(
  /* in */ objarg_Basic self,
  /* inout */ sidl_BaseClass* o,
  /* in */ sidl_bool inNotNull,
  /* in */ sidl_bool outNotNull,
  /* in */ sidl_bool retSame,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic.passInOut) */
    sidl_bool retval = (inNotNull && (*o != NULL)) ||
      !(inNotNull || (*o != NULL));
    if (outNotNull) {
      if (!retSame || !(*o)) {
        if (*o) {
          sidl_BaseClass_deleteRef(*o, _ex); 
          *o = NULL;
          SIDL_REPORT(*_ex);
        }
        *o = sidl_BaseClass__create(_ex); 
        if (*_ex) {
          *o = NULL;
          SIDL_REPORT(*_ex);
        }
      }
    }
    else {
      if (*o) {
        sidl_BaseClass_deleteRef(*o, _ex);
        *o = NULL;
        SIDL_REPORT(*_ex);
      }
    }
  EXIT:
    return retval;
    /* DO-NOT-DELETE splicer.end(objarg.Basic.passInOut) */
  }
}

/*
 * If passOutNull is true, a NULL value of o will be returned; otherwise,
 * a newly allocated object will be returned.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic_passOut"

#ifdef __cplusplus
extern "C"
#endif
void
impl_objarg_Basic_passOut(
  /* in */ objarg_Basic self,
  /* out */ sidl_BaseClass* o,
  /* in */ sidl_bool passOutNull,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic.passOut) */
    if (passOutNull) {
      *o = NULL;
    }
    else {
      *o = sidl_BaseClass__create(_ex); 
      if (*_ex) {
        *o = NULL;
        SIDL_REPORT(*_ex);
      }
    }
  EXIT:;
    /* DO-NOT-DELETE splicer.end(objarg.Basic.passOut) */
  }
}

/*
 * Return a NULL or non-NULL object depending on the value of retNull.
 */

#undef __FUNC__
#define __FUNC__ "impl_objarg_Basic_retObject"

#ifdef __cplusplus
extern "C"
#endif
sidl_BaseClass
impl_objarg_Basic_retObject(
  /* in */ objarg_Basic self,
  /* in */ sidl_bool retNull,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(objarg.Basic.retObject) */
    sidl_BaseClass retval = NULL;
    if (!retNull) {
      retval = sidl_BaseClass__create(_ex);
      if (*_ex) {
        retval = NULL;
        SIDL_REPORT(*_ex);
      }
    }
  EXIT:
    return retval;
    /* DO-NOT-DELETE splicer.end(objarg.Basic.retObject) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

