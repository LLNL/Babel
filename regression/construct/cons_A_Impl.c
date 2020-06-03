/*
 * File:          cons_A_Impl.c
 * Symbol:        cons.A-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for cons.A
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "cons.A" (version 1.0)
 */

#include "cons_A_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(cons.A._includes) */
#include "synch_RegOut.h"
/* DO-NOT-DELETE splicer.end(cons.A._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_A__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_A__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.A._load) */
    /* DO-NOT-DELETE splicer.end(cons.A._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_A__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_A__ctor(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.A._ctor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 1, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 2, _ex); SIDL_REPORT(*_ex);
    cons_A_init(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 3, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 4, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.A._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_A__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_A__ctor2(
  /* in */ cons_A self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.A._ctor2) */
    /* DO-NOT-DELETE splicer.end(cons.A._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_A__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_A__dtor(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.A._dtor) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 23, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 24, _ex); SIDL_REPORT(*_ex);
    cons_A_destruct(self, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 25, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 26, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;
    
  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.A._dtor) */
  }
}

/*
 * Method:  init[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_A_init"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_A_init(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.A.init) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 2, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 3, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.A.init) */
  }
}

/*
 * Method:  destruct[]
 */

#undef __FUNC__
#define __FUNC__ "impl_cons_A_destruct"

#ifdef __cplusplus
extern "C"
#endif
void
impl_cons_A_destruct(
  /* in */ cons_A self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(cons.A.destruct) */
  {
    synch_RegOut tracker = synch_RegOut_getInstance(_ex);  SIDL_REPORT(*_ex);
    synch_RegOut_endPart(tracker, 24, synch_ResultType_PASS, _ex);  SIDL_REPORT(*_ex);
    synch_RegOut_startPart(tracker, 25, _ex); SIDL_REPORT(*_ex);
    synch_RegOut_deleteRef(tracker, _ex);
    return;

  EXIT:;
    if (tracker) {
      sidl_BaseInterface throwaway;
      synch_RegOut_forceFailure(tracker, &throwaway);
      synch_RegOut_deleteRef(tracker, &throwaway);
    }
  }
    /* DO-NOT-DELETE splicer.end(cons.A.destruct) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

