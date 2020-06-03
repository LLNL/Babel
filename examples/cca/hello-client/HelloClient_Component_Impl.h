/*
 * File:          HelloClient_Component_Impl.h
 * Symbol:        HelloClient.Component-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7399M trunk)
 * Description:   Server-side implementation for HelloClient.Component
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_HelloClient_Component_Impl_h
#define included_HelloClient_Component_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_HelloClient_Component_h
#include "HelloClient_Component.h"
#endif
#ifndef included_gov_cca_CCAException_h
#include "gov_cca_CCAException.h"
#endif
#ifndef included_gov_cca_Component_h
#include "gov_cca_Component.h"
#endif
#ifndef included_gov_cca_Port_h
#include "gov_cca_Port.h"
#endif
#ifndef included_gov_cca_Services_h
#include "gov_cca_Services.h"
#endif
#ifndef included_gov_cca_ports_GoPort_h
#include "gov_cca_ports_GoPort.h"
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
/* DO-NOT-DELETE splicer.begin(HelloClient.Component._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(HelloClient.Component._hincludes) */

/*
 * Private data for class HelloClient.Component
 */

struct HelloClient_Component__data {
  /* DO-NOT-DELETE splicer.begin(HelloClient.Component._data) */
  gov_cca_Services services;
  /* DO-NOT-DELETE splicer.end(HelloClient.Component._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct HelloClient_Component__data*
HelloClient_Component__get_data(
  HelloClient_Component);

extern void
HelloClient_Component__set_data(
  HelloClient_Component,
  struct HelloClient_Component__data*);

extern
void
impl_HelloClient_Component__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_HelloClient_Component__ctor(
  /* in */ HelloClient_Component self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_HelloClient_Component__ctor2(
  /* in */ HelloClient_Component self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_HelloClient_Component__dtor(
  /* in */ HelloClient_Component self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct gov_cca_Services__object* 
  impl_HelloClient_Component_fconnect_gov_cca_Services(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_HelloClient_Component_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int32_t
impl_HelloClient_Component_go(
  /* in */ HelloClient_Component self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_HelloClient_Component_setServices(
  /* in */ HelloClient_Component self,
  /* in */ gov_cca_Services services,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct gov_cca_Services__object* 
  impl_HelloClient_Component_fconnect_gov_cca_Services(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
extern struct sidl_BaseInterface__object* 
  impl_HelloClient_Component_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
