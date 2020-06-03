/*
 * File:          sidlx_rmi_UnauthorizedCallException_Impl.h
 * Symbol:        sidlx.rmi.UnauthorizedCallException-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.UnauthorizedCallException
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sidlx_rmi_UnauthorizedCallException_Impl_h
#define included_sidlx_rmi_UnauthorizedCallException_Impl_h

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
#ifndef included_sidlx_rmi_UnauthorizedCallException_h
#include "sidlx_rmi_UnauthorizedCallException.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._hincludes) */
/* insert code here (include files) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._hincludes) */

/*
 * Private data for class sidlx.rmi.UnauthorizedCallException
 */

struct sidlx_rmi_UnauthorizedCallException__data {
  /* DO-NOT-DELETE splicer.begin(sidlx.rmi.UnauthorizedCallException._data) */
  /* insert code here (private data members) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sidlx.rmi.UnauthorizedCallException._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidlx_rmi_UnauthorizedCallException__data*
sidlx_rmi_UnauthorizedCallException__get_data(
  sidlx_rmi_UnauthorizedCallException);

extern void
sidlx_rmi_UnauthorizedCallException__set_data(
  sidlx_rmi_UnauthorizedCallException,
  struct sidlx_rmi_UnauthorizedCallException__data*);

extern
void
impl_sidlx_rmi_UnauthorizedCallException__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_UnauthorizedCallException__ctor(
  /* in */ sidlx_rmi_UnauthorizedCallException self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_UnauthorizedCallException__ctor2(
  /* in */ sidlx_rmi_UnauthorizedCallException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_UnauthorizedCallException__dtor(
  /* in */ sidlx_rmi_UnauthorizedCallException self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_sidlx_rmi_UnauthorizedCallException_fconnect_sidl_io_Deserializer(const 
  char* url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_sidlx_rmi_UnauthorizedCallException_fconnect_sidl_io_Serializer(const 
  char* url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_UnauthorizedCallException_fconnect_sidl_BaseInterface(const 
  char* url, sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_io_Deserializer__object* 
  impl_sidlx_rmi_UnauthorizedCallException_fconnect_sidl_io_Deserializer(const 
  char* url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_io_Serializer__object* 
  impl_sidlx_rmi_UnauthorizedCallException_fconnect_sidl_io_Serializer(const 
  char* url, sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_UnauthorizedCallException_fconnect_sidl_BaseInterface(const 
  char* url, sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* insert code here (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
