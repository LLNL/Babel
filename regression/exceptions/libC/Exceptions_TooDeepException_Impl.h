/*
 * File:          Exceptions_TooDeepException_Impl.h
 * Symbol:        Exceptions.TooDeepException-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Exceptions.TooDeepException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_Exceptions_TooDeepException_Impl_h
#define included_Exceptions_TooDeepException_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_Exceptions_FibException_h
#include "Exceptions_FibException.h"
#endif
#ifndef included_Exceptions_TooDeepException_h
#include "Exceptions_TooDeepException.h"
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
/* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._hincludes) */

/*
 * Private data for class Exceptions.TooDeepException
 */

struct Exceptions_TooDeepException__data {
  /* DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._data) */
  /* Put private data members here... */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct Exceptions_TooDeepException__data*
Exceptions_TooDeepException__get_data(
  Exceptions_TooDeepException);

extern void
Exceptions_TooDeepException__set_data(
  Exceptions_TooDeepException,
  struct Exceptions_TooDeepException__data*);

extern
void
impl_Exceptions_TooDeepException__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Exceptions_TooDeepException__ctor(
  /* in */ Exceptions_TooDeepException self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Exceptions_TooDeepException__ctor2(
  /* in */ Exceptions_TooDeepException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_Exceptions_TooDeepException__dtor(
  /* in */ Exceptions_TooDeepException self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_Exceptions_TooDeepException_fconnect_sidl_io_Deserializer(const char* 
  url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_Exceptions_TooDeepException_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_Exceptions_TooDeepException_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_Exceptions_TooDeepException_fconnect_sidl_io_Deserializer(const char* 
  url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_Exceptions_TooDeepException_fconnect_sidl_io_Serializer(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_Exceptions_TooDeepException_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
