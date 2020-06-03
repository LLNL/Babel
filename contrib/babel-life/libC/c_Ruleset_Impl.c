/*
 * File:          c_Ruleset_Impl.c
 * Symbol:        c.Ruleset-v2.0
 * Symbol Type:   class
 * Babel Version: 1.1.1
 * Description:   Server-side implementation for c.Ruleset
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "c.Ruleset" (version 2.0)
 */

#include "c_Ruleset_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(c.Ruleset._includes) */
#include "c_BoundsException.h"
#include "sidl_Exception.h"
#include <stdio.h>
/* DO-NOT-DELETE splicer.end(c.Ruleset._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_c_Ruleset__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_Ruleset__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.Ruleset._load) */
  /* Insert-Code-Here {c.Ruleset._load} (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(c.Ruleset._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_c_Ruleset__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_Ruleset__ctor(
  /* in */ c_Ruleset self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.Ruleset._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(c.Ruleset._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_c_Ruleset__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_Ruleset__ctor2(
  /* in */ c_Ruleset self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.Ruleset._ctor2) */
  /* Insert-Code-Here {c.Ruleset._ctor2} (special constructor method) */
  /*
   * This method has not been implemented
   */

    SIDL_THROW(*_ex, sidl_NotImplementedException,     "This method has not been implemented");
  EXIT:;
    /* DO-NOT-DELETE splicer.end(c.Ruleset._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_c_Ruleset__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_c_Ruleset__dtor(
  /* in */ c_Ruleset self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.Ruleset._dtor) */
  /* Insert the implementation of the destructor method here... */
    /* DO-NOT-DELETE splicer.end(c.Ruleset._dtor) */
  }
}

/*
 * Birth: an empty cell has 3 living neighbors
 * Death: a living cell has 0 or 1 neighbors (loneliness)
 * or a living cell has 4-8 neighbors (overcrowding)
 * Life: a living cell has 2 or three neighbors
 */

#undef __FUNC__
#define __FUNC__ "impl_c_Ruleset_setAlive"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_c_Ruleset_setAlive(
  /* in */ c_Ruleset self,
  /* in */ int32_t x,
  /* in */ int32_t y,
  /* in */ conway_Environment env,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(c.Ruleset.setAlive) */
  int32_t n;
  sidl_bool b;
  n = conway_Environment_nNeighbors( env, x, y, _ex); SIDL_CHECK(*_ex);

  switch(n) { 
  case 0: case 1:
    return 0;  /*  if was alive, dies of loneliness */
  case 2: 
    b = conway_Environment_isAlive(env, x, y, _ex); SIDL_CHECK(*_ex); /* is alive only if it was last turn */
    return b;
  case 3: 
    return 1;  /* if was alive, it continues, if not, new one is born */
  default: /* case 4 and above */
    return 0;  /* if was alive, dies of overcrowding */

  }
 EXIT:
  return 0;
    /* DO-NOT-DELETE splicer.end(c.Ruleset.setAlive) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

