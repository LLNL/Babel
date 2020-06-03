/*
 * File:          cons_C_Impl.c
 * Symbol:        cons.C-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.C
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "cons.C" (version 1.0)
 */

#include "cons_C_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(cons.C._includes) */
#include "synch_RegOut.h"
/* DO-NOT-DELETE splicer.end(cons.C._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct cons_B__epv* superEPV = NULL;

void cons_C__superEPV(
struct cons_B__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Method:  init[]
 */

static void
super_init(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_init)((struct cons_B__object*)
    self,
    _ex);
}

/*
 * Method:  destruct[]
 */

static void
super_destruct(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_destruct)((struct cons_B__object*)
    self,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_C__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_C__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.C._load) */
    /* DO-NOT-DELETE splicer.end(cons.C._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_C__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_C__ctor(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.C._ctor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 7, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 8, _ex); SIDL_REPORT(*_ex);
    cons_C_init(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 9, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 10, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.C._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_C__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_C__ctor2(
  /* in */ cons_C self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.C._ctor2) */
    /* DO-NOT-DELETE splicer.end(cons.C._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_C__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_C__dtor(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.C._dtor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 17, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 18, _ex); SIDL_REPORT(*_ex);
    cons_C_destruct(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 19, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 20, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.C._dtor) */
  }
}

/*
 * Method:  init[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_C_init"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_C_init(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.C.init) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 8, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 9, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.C.init) */
  }
}

/*
 * Method:  destruct[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_C_destruct"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_C_destruct(
  /* in */ cons_C self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.C.destruct) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 18, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 19, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.C.destruct) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

