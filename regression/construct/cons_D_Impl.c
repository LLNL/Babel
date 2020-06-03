/*
 * File:          cons_D_Impl.c
 * Symbol:        cons.D-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.D
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "cons.D" (version 1.0)
 */

#include "cons_D_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(cons.D._includes) */
#include "synch_RegOut.h"
/* DO-NOT-DELETE splicer.end(cons.D._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct cons_C__epv* superEPV = NULL;

void cons_D__superEPV(
struct cons_C__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Method:  init[]
 */

static void
super_init(
  /* in */ cons_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_init)((struct cons_C__object*)
    self,
    _ex);
}

/*
 * Method:  destruct[]
 */

static void
super_destruct(
  /* in */ cons_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_destruct)((struct cons_C__object*)
    self,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_D__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_D__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.D._load) */
    /* DO-NOT-DELETE splicer.end(cons.D._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_D__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_D__ctor(
  /* in */ cons_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.D._ctor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 10, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 11, _ex); SIDL_REPORT(*_ex);
    cons_D_init(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 12, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 13, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.D._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_D__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_D__ctor2(
  /* in */ cons_D self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.D._ctor2) */
    /* DO-NOT-DELETE splicer.end(cons.D._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_D__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_D__dtor(
  /* in */ cons_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.D._dtor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 14, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 15, _ex); SIDL_REPORT(*_ex);
    cons_D_destruct(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 16, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 17, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.D._dtor) */
  }
}

/*
 * Method:  init[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_D_init"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_D_init(
  /* in */ cons_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.D.init) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 11, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 12, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.D.init) */
  }
}

/*
 * Method:  destruct[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_D_destruct"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_D_destruct(
  /* in */ cons_D self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.D.destruct) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 15, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 16, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.D.destruct) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

