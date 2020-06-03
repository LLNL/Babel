/*
 * File:          Strings_Cstring_Impl.c
 * Symbol:        Strings.Cstring-v1.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Strings.Cstring
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Strings.Cstring" (version 1.1)
 * 
 * Class to allow testing of string passing using every possible mode.
 */

#include "Strings_Cstring_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Strings.Cstring._includes) */
#include <string.h>
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(Strings.Cstring._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Strings_Cstring__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(Strings.Cstring._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Strings_Cstring__ctor(
  /* in */ Strings_Cstring self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Strings.Cstring._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Strings_Cstring__ctor2(
  /* in */ Strings_Cstring self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor2) */
  /* Insert-Code-Here {Strings.Cstring._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(Strings.Cstring._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Strings_Cstring__dtor(
  /* in */ Strings_Cstring self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor) */
  /* Insert the implementation of the constructor method here... */
    /* DO-NOT-DELETE splicer.end(Strings.Cstring._dtor) */
  }
}

/*
 * If <code>nonNull</code> is <code>true</code>, this will
 * return "Three"; otherwise, it will return a NULL or empty string.
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring_returnback"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Strings_Cstring_returnback(
  /* in */ Strings_Cstring self,
  /* in */ sidl_bool nonNull,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback) */
  return nonNull ? sidl_String_strdup("Three") : NULL;
    /* DO-NOT-DELETE splicer.end(Strings.Cstring.returnback) */
  }
}

/*
 * This will return <code>true</code> iff <code>c</code> equals "Three".
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring_passin"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Strings_Cstring_passin(
  /* in */ Strings_Cstring self,
  /* in */ const char* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring.passin) */
  return (c && !strcmp(c,"Three")) ? TRUE : FALSE;
    /* DO-NOT-DELETE splicer.end(Strings.Cstring.passin) */
  }
}

/*
 * If <code>nonNull</code> is <code>true</code>, this will return
 * "Three" in <code>c</code>; otherwise, it will return a null or
 * empty string. The return value is <code>false</code> iff 
 * the outgoing value of <code>c</code> is <code>null</code>.
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring_passout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Strings_Cstring_passout(
  /* in */ Strings_Cstring self,
  /* in */ sidl_bool nonNull,
  /* out */ char** c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring.passout) */
  *c = nonNull ? sidl_String_strdup("Three") : NULL;
  return (*c == NULL) ? FALSE : TRUE;
    /* DO-NOT-DELETE splicer.end(Strings.Cstring.passout) */
  }
}

/*
 * Method:  passinout[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring_passinout"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Strings_Cstring_passinout(
  /* in */ Strings_Cstring self,
  /* inout */ char** c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout) */
  char * s = "s";
  char * temp;
  if ( *c == NULL ) { 
    return FALSE;
  }
  if ((temp=(char*)malloc( (strlen(*c) + 2) * sizeof(char) )) == NULL ) { 
    return FALSE;
  }
  strcpy( temp, *c );
  strcat( temp, s );
  free(*c);
  *c = NULL;
  if ( temp[0] >= 'a' && temp[0] <= 'z' ) { 
    temp[0] += 'A' - 'a';
  } else if ( temp[0] >= 'A' && temp[0] <= 'Z' ) { 
    temp[0] += 'a' - 'A';
  }
  *c = temp;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Strings.Cstring.passinout) */
  }
}

/*
 * Method:  passeverywhere[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring_passeverywhere"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_Strings_Cstring_passeverywhere(
  /* in */ Strings_Cstring self,
  /* in */ const char* c1,
  /* out */ char** c2,
  /* inout */ char** c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere) */
  char * temp;  
  *c2 = NULL;
  if ( *c3 == NULL ) {
    return NULL;
  }
  temp = *c3;
  temp[ strlen( temp ) - 1 ] = '\0'; /* remove last character */
  if ( temp[0] >= 'a' && temp[0] <= 'z' ) { 
    temp[0] += 'A' - 'a';
  } else if ( temp[0] >= 'A' && temp[0] <= 'Z' ) { 
    temp[0] += 'a' - 'A';
  }
  *c3 = temp;
  *c2 = sidl_String_strdup("Three");
  return (c1 && !(strcmp(c1,"Three")) ) ? sidl_String_strdup("Three") : NULL;
    /* DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere) */
  }
}

/*
 *  return true iff s1 == s2 and c1 == c2 
 */

#undef __FUNC__
#define __FUNC__ "impl_Strings_Cstring_mixedarguments"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Strings_Cstring_mixedarguments(
  /* in */ Strings_Cstring self,
  /* in */ const char* s1,
  /* in */ char c1,
  /* in */ const char* s2,
  /* in */ char c2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments) */
  return (s1 && s2 && (c1 == c2) && !strcmp(s1, s2)) ? TRUE : FALSE;
    /* DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

