/*
 * File:          Args_Basic_Impl.h
 * Symbol:        Args.Basic-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Args.Basic
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Args_Basic_Impl_h
#define included_Args_Basic_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Args_Basic_h
#include "Args_Basic.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseInterface_h
#include "sidl_BaseInterface.h"
#endif
#ifndef included_sidl_ClassInfo_h
#include "sidl_ClassInfo.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
/* DO-NOT-DELETE splicer.begin(Args.Basic._hincludes) */
/* Insert-Code-Here {Args.Basic._includes} (include files) */
/* DO-NOT-DELETE splicer.end(Args.Basic._hincludes) */

/*
 * Private data for class Args.Basic
 */

struct Args_Basic__data {
  /* DO-NOT-DELETE splicer.begin(Args.Basic._data) */
  /* Insert-Code-Here {Args.Basic._data} (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Args.Basic._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Args_Basic__data*
Args_Basic__get_data(
  Args_Basic);

extern void
Args_Basic__set_data(
  Args_Basic,
  struct Args_Basic__data*);

extern
void
impl_Args_Basic__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Args_Basic__ctor(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Args_Basic__ctor2(
  /* in */ Args_Basic self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Args_Basic__dtor(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Args_Basic_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
sidl_bool
impl_Args_Basic_returnbackbool(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinbool(
  /* in */ Args_Basic self,
  /* in */ sidl_bool b,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutbool(
  /* in */ Args_Basic self,
  /* out */ sidl_bool* b,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutbool(
  /* in */ Args_Basic self,
  /* inout */ sidl_bool* b,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passeverywherebool(
  /* in */ Args_Basic self,
  /* in */ sidl_bool b1,
  /* out */ sidl_bool* b2,
  /* inout */ sidl_bool* b3,
  /* out */ sidl_BaseInterface *_ex);

extern
char
impl_Args_Basic_returnbackchar(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinchar(
  /* in */ Args_Basic self,
  /* in */ char c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutchar(
  /* in */ Args_Basic self,
  /* out */ char* c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutchar(
  /* in */ Args_Basic self,
  /* inout */ char* c,
  /* out */ sidl_BaseInterface *_ex);

extern
char
impl_Args_Basic_passeverywherechar(
  /* in */ Args_Basic self,
  /* in */ char c1,
  /* out */ char* c2,
  /* inout */ char* c3,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_Args_Basic_returnbackint(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinint(
  /* in */ Args_Basic self,
  /* in */ int32_t i,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutint(
  /* in */ Args_Basic self,
  /* out */ int32_t* i,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutint(
  /* in */ Args_Basic self,
  /* inout */ int32_t* i,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_Args_Basic_passeverywhereint(
  /* in */ Args_Basic self,
  /* in */ int32_t i1,
  /* out */ int32_t* i2,
  /* inout */ int32_t* i3,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_Args_Basic_returnbacklong(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinlong(
  /* in */ Args_Basic self,
  /* in */ int64_t l,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutlong(
  /* in */ Args_Basic self,
  /* out */ int64_t* l,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutlong(
  /* in */ Args_Basic self,
  /* inout */ int64_t* l,
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_Args_Basic_passeverywherelong(
  /* in */ Args_Basic self,
  /* in */ int64_t l1,
  /* out */ int64_t* l2,
  /* inout */ int64_t* l3,
  /* out */ sidl_BaseInterface *_ex);

extern
float
impl_Args_Basic_returnbackfloat(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinfloat(
  /* in */ Args_Basic self,
  /* in */ float f,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutfloat(
  /* in */ Args_Basic self,
  /* out */ float* f,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutfloat(
  /* in */ Args_Basic self,
  /* inout */ float* f,
  /* out */ sidl_BaseInterface *_ex);

extern
float
impl_Args_Basic_passeverywherefloat(
  /* in */ Args_Basic self,
  /* in */ float f1,
  /* out */ float* f2,
  /* inout */ float* f3,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Args_Basic_returnbackdouble(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passindouble(
  /* in */ Args_Basic self,
  /* in */ double d,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutdouble(
  /* in */ Args_Basic self,
  /* out */ double* d,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutdouble(
  /* in */ Args_Basic self,
  /* inout */ double* d,
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_Args_Basic_passeverywheredouble(
  /* in */ Args_Basic self,
  /* in */ double d1,
  /* out */ double* d2,
  /* inout */ double* d3,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_fcomplex
impl_Args_Basic_returnbackfcomplex(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinfcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_fcomplex c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutfcomplex(
  /* in */ Args_Basic self,
  /* out */ struct sidl_fcomplex* c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutfcomplex(
  /* in */ Args_Basic self,
  /* inout */ struct sidl_fcomplex* c,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_fcomplex
impl_Args_Basic_passeverywherefcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_fcomplex c1,
  /* out */ struct sidl_fcomplex* c2,
  /* inout */ struct sidl_fcomplex* c3,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_dcomplex
impl_Args_Basic_returnbackdcomplex(
  /* in */ Args_Basic self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passindcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_dcomplex c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passoutdcomplex(
  /* in */ Args_Basic self,
  /* out */ struct sidl_dcomplex* c,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_Args_Basic_passinoutdcomplex(
  /* in */ Args_Basic self,
  /* inout */ struct sidl_dcomplex* c,
  /* out */ sidl_BaseInterface *_ex);

extern
struct sidl_dcomplex
impl_Args_Basic_passeverywheredcomplex(
  /* in */ Args_Basic self,
  /* in */ struct sidl_dcomplex c1,
  /* out */ struct sidl_dcomplex* c2,
  /* inout */ struct sidl_dcomplex* c3,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_Args_Basic_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
