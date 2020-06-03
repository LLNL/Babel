/*
 * File:          c_BoundsException_Impl.h
 * Symbol:        c.BoundsException-v2.0
 * Symbol Type:   class
 * Babel Version: 1.1.1
 * Description:   Server-side implementation for c.BoundsException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_c_BoundsException_Impl_h
#define included_c_BoundsException_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_c_BoundsException_h
#include "c_BoundsException.h"
#endif
#ifndef included_conway_BoundsException_h
#include "conway_BoundsException.h"
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

/* DO-NOT-DELETE splicer.begin(c.BoundsException._hincludes) */
/* Insert-Code-Here {c.BoundsException._hincludes} (include files) */
/* DO-NOT-DELETE splicer.end(c.BoundsException._hincludes) */

/*
 * Private data for class c.BoundsException
 */

struct c_BoundsException__data {
  /* DO-NOT-DELETE splicer.begin(c.BoundsException._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(c.BoundsException._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct c_BoundsException__data*
c_BoundsException__get_data(
  c_BoundsException);

extern void
c_BoundsException__set_data(
  c_BoundsException,
  struct c_BoundsException__data*);

extern
void
impl_c_BoundsException__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_c_BoundsException__ctor(
  /* in */ c_BoundsException self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_c_BoundsException__ctor2(
  /* in */ c_BoundsException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_c_BoundsException__dtor(
  /* in */ c_BoundsException self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_c_BoundsException_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_c_BoundsException_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_c_BoundsException_fconnect_sidl_io_Serializer(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_c_BoundsException_fconnect_sidl_io_Deserializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_c_BoundsException_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_c_BoundsException_fconnect_sidl_io_Serializer(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
