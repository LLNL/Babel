/*
 * File:          vect_vNegValExcept_Impl.h
 * Symbol:        vect.vNegValExcept-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for vect.vNegValExcept
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_vect_vNegValExcept_Impl_h
#define included_vect_vNegValExcept_Impl_h

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
#ifndef included_vect_vNegValExcept_h
#include "vect_vNegValExcept.h"
#endif
/* DO-NOT-DELETE splicer.begin(vect.vNegValExcept._hincludes) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(vect.vNegValExcept._hincludes) */

/*
 * Private data for class vect.vNegValExcept
 */

struct vect_vNegValExcept__data {
  /* DO-NOT-DELETE splicer.begin(vect.vNegValExcept._data) */
  /* Nothing needed here. */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(vect.vNegValExcept._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct vect_vNegValExcept__data*
vect_vNegValExcept__get_data(
  vect_vNegValExcept);

extern void
vect_vNegValExcept__set_data(
  vect_vNegValExcept,
  struct vect_vNegValExcept__data*);

extern
void
impl_vect_vNegValExcept__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vNegValExcept__ctor(
  /* in */ vect_vNegValExcept self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vNegValExcept__ctor2(
  /* in */ vect_vNegValExcept self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_vect_vNegValExcept__dtor(
  /* in */ vect_vNegValExcept self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_vect_vNegValExcept_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_vect_vNegValExcept_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_vect_vNegValExcept_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_vect_vNegValExcept_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_vect_vNegValExcept_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_vect_vNegValExcept_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
