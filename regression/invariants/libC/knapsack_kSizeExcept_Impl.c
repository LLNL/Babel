/*
 * File:          knapsack_kSizeExcept_Impl.c
 * Symbol:        knapsack.kSizeExcept-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7133M trunk)
 * Description:   Server-side implementation for knapsack.kSizeExcept
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "knapsack.kSizeExcept" (version 1.0)
 */

#include "knapsack_kSizeExcept_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept._includes) */
/* insert code here (includes and arbitrary code) */
/* DO-NOT-DELETE splicer.end(knapsack.kSizeExcept._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_kSizeExcept__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_kSizeExcept__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept._load) */
    /* insert code here (static class initializer) */
    /* DO-NOT-DELETE splicer.end(knapsack.kSizeExcept._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_kSizeExcept__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_kSizeExcept__ctor(
  /* in */ knapsack_kSizeExcept self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept._ctor) */
    /*
     * // boilerplate constructor
     * struct knapsack_kSizeExcept__data *dptr = (struct knapsack_kSizeExcept__data*)malloc(sizeof(struct knapsack_kSizeExcept__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct knapsack_kSizeExcept__data));
     *   // initialize elements of dptr here
     * knapsack_kSizeExcept__set_data(self, dptr);
     * } else {
     *   sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
     *   SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_setNote(ex, "Out of memory.", _ex); SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_add(ex, __FILE__, __LINE__, "knapsack.kSizeExcept._ctor", _ex);
     *   SIDL_CHECK(*_ex);
     *   *_ex = (sidl_BaseInterface)ex;
     * }
     * EXIT:;
     */

    /* DO-NOT-DELETE splicer.end(knapsack.kSizeExcept._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_kSizeExcept__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_kSizeExcept__ctor2(
  /* in */ knapsack_kSizeExcept self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept._ctor2) */
    /* insert code here (special constructor) */
    /* DO-NOT-DELETE splicer.end(knapsack.kSizeExcept._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_knapsack_kSizeExcept__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_knapsack_kSizeExcept__dtor(
  /* in */ knapsack_kSizeExcept self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept._dtor) */
    /*
     * // boilerplate destructor
     * struct knapsack_kSizeExcept__data *dptr = knapsack_kSizeExcept__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   knapsack_kSizeExcept__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(knapsack.kSizeExcept._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

