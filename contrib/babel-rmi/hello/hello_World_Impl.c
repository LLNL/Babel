/*
 * File:          hello_World_Impl.c
 * Symbol:        hello.World-v1.0
 * Symbol Type:   class
 * Babel Version: 1.5.0 (Revision: 6727M trunk)
 * Description:   Server-side implementation for hello.World
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "hello.World" (version 1.0)
 */

#include "hello_World_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(hello.World._includes) */
#include <string.h>
#include "sidl_String.h"
#include <stdio.h>
/* DO-NOT-DELETE splicer.end(hello.World._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_hello_World__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hello_World__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hello.World._load) */
    /* insert code here (static class initializer) */
    /* DO-NOT-DELETE splicer.end(hello.World._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_hello_World__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hello_World__ctor(
  /* in */ hello_World self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hello.World._ctor) */
    struct hello_World__data *dptr = (struct hello_World__data*)malloc(sizeof(struct hello_World__data));
    if (dptr) {
      memset(dptr, 0, sizeof(struct hello_World__data));
    hello_World__set_data(self, dptr);
    } else {
      sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
      SIDL_CHECK(*_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.", _ex); SIDL_CHECK(*_ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__, "hello.World._ctor", _ex);
      SIDL_CHECK(*_ex);
      *_ex = (sidl_BaseInterface)ex;
    }
    EXIT:;
    /* DO-NOT-DELETE splicer.end(hello.World._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_hello_World__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hello_World__ctor2(
  /* in */ hello_World self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hello.World._ctor2) */
    /* insert code here (special constructor) */
    /* DO-NOT-DELETE splicer.end(hello.World._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_hello_World__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hello_World__dtor(
  /* in */ hello_World self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hello.World._dtor) */
    struct hello_World__data *dptr = hello_World__get_data(self);
    if (dptr) {
      free(dptr);
      hello_World__set_data(self, NULL);
    }
    /* DO-NOT-DELETE splicer.end(hello.World._dtor) */
  }
}

/*
 * Method:  setName[]
 */

#undef __FUNC__
#define __FUNC__ "impl_hello_World_setName"

#ifdef __cplusplus
extern "C"
#endif
void
impl_hello_World_setName(
  /* in */ hello_World self,
  /* in */ const char* name,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hello.World.setName) */
    struct hello_World__data *dptr = 
      hello_World__get_data(self);
    if (dptr) { 
      if ( dptr->d_name ) { 
        sidl_String_free( dptr->d_name );
      }
      dptr->d_name = sidl_String_strdup( name );
    }
    /* DO-NOT-DELETE splicer.end(hello.World.setName) */
  }
}

/*
 * Method:  getMsg[]
 */

#undef __FUNC__
#define __FUNC__ "impl_hello_World_getMsg"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_hello_World_getMsg(
  /* in */ hello_World self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(hello.World.getMsg) */
#define BUFSIZE 1024
    char buffer[BUFSIZE];
    int nbytesleft = BUFSIZE;
    struct hello_World__data *dptr = 
      hello_World__get_data(self);
    if (dptr) { 
      if ( dptr->d_name ) { 
        strncpy( buffer, "Hello ", 8 );
        nbytesleft -= 9;
        strncat( buffer, dptr->d_name, nbytesleft );
        nbytesleft -= strlen(dptr->d_name);
        nbytesleft = ( nbytesleft < 0 ) ? 0 : nbytesleft;
        strncat( buffer, "!", nbytesleft );
        return sidl_String_strdup( buffer );
      }
    }
    return sidl_String_strdup( "Hello World!" );
    /* DO-NOT-DELETE splicer.end(hello.World.getMsg) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* insert code here (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

