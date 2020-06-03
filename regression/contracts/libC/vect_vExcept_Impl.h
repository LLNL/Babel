/*
 * File:          vect_vExcept_Impl.h
 * Symbol:        vect.vExcept-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for vect.vExcept
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_vect_vExcept_Impl_h
#define included_vect_vExcept_Impl_h

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
#ifndef included_vect_vExcept_h
#include "vect_vExcept.h"
#endif
/* DO-NOT-DELETE splicer.begin(vect.vExcept._hincludes) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(vect.vExcept._hincludes) */

/*
 * Private data for class vect.vExcept
 */

struct vect_vExcept__data {
  /* DO-NOT-DELETE splicer.begin(vect.vExcept._data) */
  /* Nothing needed here. */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(vect.vExcept._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct vect_vExcept__data*
vect_vExcept__get_data(
  vect_vExcept);

extern void
vect_vExcept__set_data(
  vect_vExcept,
  struct vect_vExcept__data*);

extern
void
impl_vect_vExcept__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vExcept__ctor(
  /* in */ vect_vExcept self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vExcept__ctor2(
  /* in */ vect_vExcept self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vExcept__dtor(
  /* in */ vect_vExcept self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_vect_vExcept_fconnect_sidl_io_Deserializer(const char* url, sidl_bool ar,
  sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_vect_vExcept_fconnect_sidl_io_Serializer(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_vect_vExcept_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_vect_vExcept_fconnect_sidl_io_Deserializer(const char* url, sidl_bool ar,
  sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_vect_vExcept_fconnect_sidl_io_Serializer(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_vect_vExcept_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
