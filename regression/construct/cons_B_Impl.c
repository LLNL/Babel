/*
 * File:          cons_B_Impl.c
 * Symbol:        cons.B-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.B
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "cons.B" (version 1.0)
 */

#include "cons_B_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(cons.B._includes) */
#include "synch_RegOut.h"
/* DO-NOT-DELETE splicer.end(cons.B._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct cons_A__epv* superEPV = NULL;

void cons_B__superEPV(
struct cons_A__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Method:  init[]
 */

static void
super_init(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_init)((struct cons_A__object*)
    self,
    _ex);
}

/*
 * Method:  destruct[]
 */

static void
super_destruct(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_destruct)((struct cons_A__object*)
    self,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_B__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_B__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.B._load) */
    /* DO-NOT-DELETE splicer.end(cons.B._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_B__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_B__ctor(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.B._ctor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 4, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 5, _ex); SIDL_REPORT(*_ex);
    cons_B_init(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 6, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 7, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.B._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_B__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_B__ctor2(
  /* in */ cons_B self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.B._ctor2) */
    /* DO-NOT-DELETE splicer.end(cons.B._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_B__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_B__dtor(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.B._dtor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 20, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 21, _ex); SIDL_REPORT(*_ex);
    cons_B_destruct(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 22, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 23, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.B._dtor) */
  }
}

/*
 * Method:  init[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_B_init"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_B_init(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.B.init) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 5, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 6, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.B.init) */
  }
}

/*
 * Method:  destruct[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_B_destruct"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_B_destruct(
  /* in */ cons_B self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.B.destruct) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 21, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 22, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.B.destruct) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

