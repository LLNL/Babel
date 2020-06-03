/*
 * File:          Args_Basic_Impl.c
 * Symbol:        Args.Basic-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Args.Basic
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "Args.Basic" (version 1.0)
 */

#include "Args_Basic_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(Args.Basic._includes) */
#include <ctype.h>
/* DO-NOT-DELETE splicer.end(Args.Basic._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Args_Basic__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic._load) */
    /* DO-NOT-DELETE splicer.end(Args.Basic._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Args_Basic__ctor(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic._ctor) */
    /* Insert-Code-Here {Args.Basic._ctor} (constructor method) */
    /*
     * // boilerplate constructor
     * struct Args_Basic__data *dptr = (struct Args_Basic__data*)malloc(sizeof(struct Args_Basic__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct Args_Basic__data));
     *   // initialize elements of dptr here
     * }
     * Args_Basic__set_data(self, dptr);
     */

    /* DO-NOT-DELETE splicer.end(Args.Basic._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Args_Basic__ctor2(
  /* in */ Args_Basic self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic._ctor2) */
    /* DO-NOT-DELETE splicer.end(Args.Basic._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_Args_Basic__dtor(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic._dtor) */
    /* Insert-Code-Here {Args.Basic._dtor} (destructor method) */
    /*
     * // boilerplate destructor
     * struct Args_Basic__data *dptr = Args_Basic__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   Args_Basic__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(Args.Basic._dtor) */
  }
}

/*
 * Method:  returnbackbool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackbool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_returnbackbool(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool) */
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool) */
  }
}

/*
 * Method:  passinbool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinbool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinbool(
  /* in */ Args_Basic self,
  /* in */ sidl_bool b,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinbool) */
  return (b == TRUE) ? TRUE : FALSE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinbool) */
  }
}

/*
 * Method:  passoutbool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutbool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutbool(
  /* in */ Args_Basic self,
  /* out */ sidl_bool* b,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool) */
  *b = TRUE;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutbool) */
  }
}

/*
 * Method:  passinoutbool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutbool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutbool(
  /* in */ Args_Basic self,
  /* inout */ sidl_bool* b,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool) */
  *b = !(*b);
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool) */
  }
}

/*
 * Method:  passeverywherebool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywherebool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passeverywherebool(
  /* in */ Args_Basic self,
  /* in */ sidl_bool b1,
  /* out */ sidl_bool* b2,
  /* inout */ sidl_bool* b3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool) */
  *b2 = TRUE;
  *b3 = !(*b3);
  return (b1 == TRUE) ? TRUE: FALSE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool) */
  }
}

/*
 * Method:  returnbackchar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackchar"

#ifdef __cplusplus
extern "C"
#endif
char
impl_Args_Basic_returnbackchar(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar) */
  return '3';
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar) */
  }
}

/*
 * Method:  passinchar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinchar"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinchar(
  /* in */ Args_Basic self,
  /* in */ char c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinchar) */
  return (c == '3');
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinchar) */
  }
}

/*
 * Method:  passoutchar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutchar"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutchar(
  /* in */ Args_Basic self,
  /* out */ char* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar) */
  *c = '3';
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutchar) */
  }
}

/*
 * Method:  passinoutchar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutchar"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutchar(
  /* in */ Args_Basic self,
  /* inout */ char* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar) */
  if (islower((int) *c)) {
    *c = (char) toupper(*c);
  }
  else if (isupper((int) *c)) {
    *c = (char) tolower(*c);
  }
  else {
    return FALSE;
  }
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar) */
  }
}

/*
 * Method:  passeverywherechar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywherechar"

#ifdef __cplusplus
extern "C"
#endif
char
impl_Args_Basic_passeverywherechar(
  /* in */ Args_Basic self,
  /* in */ char c1,
  /* out */ char* c2,
  /* inout */ char* c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar) */
  *c2 = '3';
  if (islower((int) *c3)) {
    *c3 = (char) toupper(*c3);
  }
  else if (isupper((int) *c3)) {
    *c3 = (char) tolower(*c3);
  }
  else {
    return FALSE;
  }
  return (c1 == '3') ? '3' : FALSE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar) */
  }
}

/*
 * Method:  returnbackint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackint"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Args_Basic_returnbackint(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint) */
  return 3;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackint) */
  }
}

/*
 * Method:  passinint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinint"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinint(
  /* in */ Args_Basic self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinint) */
  return (i == 3);
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinint) */
  }
}

/*
 * Method:  passoutint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutint"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutint(
  /* in */ Args_Basic self,
  /* out */ int32_t* i,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutint) */
  *i = 3;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutint) */
  }
}

/*
 * Method:  passinoutint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutint"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutint(
  /* in */ Args_Basic self,
  /* inout */ int32_t* i,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint) */
  *i = -(*i);
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutint) */
  }
}

/*
 * Method:  passeverywhereint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywhereint"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_Args_Basic_passeverywhereint(
  /* in */ Args_Basic self,
  /* in */ int32_t i1,
  /* out */ int32_t* i2,
  /* inout */ int32_t* i3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint) */
  *i2 = 3;
  *i3 = -(*i3);
  return (i1 == 3) ? 3 : 0;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint) */
  }
}

/*
 * Method:  returnbacklong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbacklong"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_Args_Basic_returnbacklong(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong) */
  return 3L;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong) */
  }
}

/*
 * Method:  passinlong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinlong"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinlong(
  /* in */ Args_Basic self,
  /* in */ int64_t l,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinlong) */
  return ( l == 3L );
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinlong) */
  }
}

/*
 * Method:  passoutlong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutlong"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutlong(
  /* in */ Args_Basic self,
  /* out */ int64_t* l,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong) */
  *l = 3L;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutlong) */
  }
}

/*
 * Method:  passinoutlong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutlong"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutlong(
  /* in */ Args_Basic self,
  /* inout */ int64_t* l,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong) */
  *l = -(*l);
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong) */
  }
}

/*
 * Method:  passeverywherelong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywherelong"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_Args_Basic_passeverywherelong(
  /* in */ Args_Basic self,
  /* in */ int64_t l1,
  /* out */ int64_t* l2,
  /* inout */ int64_t* l3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong) */
  *l2 = 3L;
  *l3 = -(*l3);
  return (l1 == 3L) ? 3L : 0L;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong) */
  }
}

/*
 * Method:  returnbackfloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackfloat"

#ifdef __cplusplus
extern "C"
#endif
float
impl_Args_Basic_returnbackfloat(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat) */
  return 3.1F;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat) */
  }
}

/*
 * Method:  passinfloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinfloat"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinfloat(
  /* in */ Args_Basic self,
  /* in */ float f,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat) */
  return (3.1F == f);
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinfloat) */
  }
}

/*
 * Method:  passoutfloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutfloat"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutfloat(
  /* in */ Args_Basic self,
  /* out */ float* f,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat) */
  *f = 3.1F;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat) */
  }
}

/*
 * Method:  passinoutfloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutfloat"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutfloat(
  /* in */ Args_Basic self,
  /* inout */ float* f,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat) */
  *f = - (*f);
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat) */
  }
}

/*
 * Method:  passeverywherefloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywherefloat"

#ifdef __cplusplus
extern "C"
#endif
float
impl_Args_Basic_passeverywherefloat(
  /* in */ Args_Basic self,
  /* in */ float f1,
  /* out */ float* f2,
  /* inout */ float* f3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat) */
  *f2 = 3.1F;
  *f3 = -(*f3);
  return (3.1F == f1) ? 3.1F : 0.0F;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat) */
  }
}

/*
 * Method:  returnbackdouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackdouble"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Args_Basic_returnbackdouble(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble) */
  return 3.14;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble) */
  }
}

/*
 * Method:  passindouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passindouble"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passindouble(
  /* in */ Args_Basic self,
  /* in */ double d,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passindouble) */
  return (d == 3.14);
    /* DO-NOT-DELETE splicer.end(Args.Basic.passindouble) */
  }
}

/*
 * Method:  passoutdouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutdouble"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutdouble(
  /* in */ Args_Basic self,
  /* out */ double* d,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble) */
  *d = 3.14;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble) */
  }
}

/*
 * Method:  passinoutdouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutdouble"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutdouble(
  /* in */ Args_Basic self,
  /* inout */ double* d,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble) */
  *d = - (*d);
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble) */
  }
}

/*
 * Method:  passeverywheredouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywheredouble"

#ifdef __cplusplus
extern "C"
#endif
double
impl_Args_Basic_passeverywheredouble(
  /* in */ Args_Basic self,
  /* in */ double d1,
  /* out */ double* d2,
  /* inout */ double* d3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble) */
  *d2 = 3.14;
  *d3 = - (*d3);
  return (d1 == 3.14) ? 3.14 : 0.0;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble) */
  }
}

/*
 * Method:  returnbackfcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackfcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_fcomplex
impl_Args_Basic_returnbackfcomplex(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex) */
  struct sidl_fcomplex c = {3.1F, 3.1F};
  return c;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex) */
  }
}

/*
 * Method:  passinfcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinfcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinfcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_fcomplex c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex) */
  return (3.1F == c.real && 3.1F == c.imaginary);
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex) */
  }
}

/*
 * Method:  passoutfcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutfcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutfcomplex(
  /* in */ Args_Basic self,
  /* out */ struct sidl_fcomplex* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex) */
  c->real = c->imaginary = 3.1F;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex) */
  }
}

/*
 * Method:  passinoutfcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutfcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutfcomplex(
  /* in */ Args_Basic self,
  /* inout */ struct sidl_fcomplex* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex) */
  c->imaginary = - c->imaginary;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex) */
  }
}

/*
 * Method:  passeverywherefcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywherefcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_fcomplex
impl_Args_Basic_passeverywherefcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_fcomplex c1,
  /* out */ struct sidl_fcomplex* c2,
  /* inout */ struct sidl_fcomplex* c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex) */
  struct sidl_fcomplex c4 = {3.1F, 3.1F};
  struct sidl_fcomplex c5 = {0.0F, 0.0F};
  c2->imaginary = c2->real = 3.1F;
  c3->imaginary = - c3->imaginary;
  if (3.1F == c1.real && 3.1F == c1.imaginary) {
    return c4;
  }
  else {
    return c5;
  }
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex) */
  }
}

/*
 * Method:  returnbackdcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_returnbackdcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_dcomplex
impl_Args_Basic_returnbackdcomplex(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex) */
  struct sidl_dcomplex c = {3.14, 3.14};
  return c;
    /* DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex) */
  }
}

/*
 * Method:  passindcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passindcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passindcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_dcomplex c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex) */
  if (3.14 == c.real && 3.14 == c.imaginary) {
    return TRUE;
  }
  else {
    return FALSE;
  }
    /* DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex) */
  }
}

/*
 * Method:  passoutdcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passoutdcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passoutdcomplex(
  /* in */ Args_Basic self,
  /* out */ struct sidl_dcomplex* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex) */
  c->imaginary = c->real = 3.14;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex) */
  }
}

/*
 * Method:  passinoutdcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passinoutdcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_Args_Basic_passinoutdcomplex(
  /* in */ Args_Basic self,
  /* inout */ struct sidl_dcomplex* c,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex) */
  c->imaginary = - c->imaginary;
  return TRUE;
    /* DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex) */
  }
}

/*
 * Method:  passeverywheredcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_Args_Basic_passeverywheredcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_dcomplex
impl_Args_Basic_passeverywheredcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_dcomplex c1,
  /* out */ struct sidl_dcomplex* c2,
  /* inout */ struct sidl_dcomplex* c3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex) */
  struct sidl_dcomplex c4 = {3.14, 3.14};
  struct sidl_dcomplex c5 = {0.0, 0.0};
  c2->real = 3.14;
  c2->imaginary = 3.14;
  c3->imaginary = -c3->imaginary;
  if (3.14 == c1.real && 3.14 == c1.imaginary) {
    return c4;
  }
  else {
    return c5;
  }
    /* DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

