/*
 * File:          vect_vDivByZeroExcept_Impl.h
 * Symbol:        vect.vDivByZeroExcept-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for vect.vDivByZeroExcept
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_vect_vDivByZeroExcept_Impl_h
#define included_vect_vDivByZeroExcept_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseException_h
#include "sidl_BaseException.h"
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
#ifndef included_sidl_SIDLException_h
#include "sidl_SIDLException.h"
#endif
#ifndef included_sidl_io_Deserializer_h
#include "sidl_io_Deserializer.h"
#endif
#ifndef included_sidl_io_Serializable_h
#include "sidl_io_Serializable.h"
#endif
#ifndef included_sidl_io_Serializer_h
#include "sidl_io_Serializer.h"
#endif
#ifndef included_vect_vDivByZeroExcept_h
#include "vect_vDivByZeroExcept.h"
#endif
#ifndef included_vect_vExcept_h
#include "vect_vExcept.h"
#endif
/* DO-NOT-DELETE splicer.begin(vect.vDivByZeroExcept._hincludes) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(vect.vDivByZeroExcept._hincludes) */

/*
 * Private data for class vect.vDivByZeroExcept
 */

struct vect_vDivByZeroExcept__data {
  /* DO-NOT-DELETE splicer.begin(vect.vDivByZeroExcept._data) */
  /* Nothing needed here. */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(vect.vDivByZeroExcept._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct vect_vDivByZeroExcept__data*
vect_vDivByZeroExcept__get_data(
  vect_vDivByZeroExcept);

extern void
vect_vDivByZeroExcept__set_data(
  vect_vDivByZeroExcept,
  struct vect_vDivByZeroExcept__data*);

extern
void
impl_vect_vDivByZeroExcept__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vDivByZeroExcept__ctor(
  /* in */ vect_vDivByZeroExcept self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vDivByZeroExcept__ctor2(
  /* in */ vect_vDivByZeroExcept self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vDivByZeroExcept__dtor(
  /* in */ vect_vDivByZeroExcept self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_vect_vDivByZeroExcept_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_vect_vDivByZeroExcept_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_vect_vDivByZeroExcept_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_vect_vDivByZeroExcept_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_vect_vDivByZeroExcept_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_vect_vDivByZeroExcept_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
