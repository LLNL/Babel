/*
 * File:          sidlx_rmi_ServerSocket_Impl.h
 * Symbol:        sidlx.rmi.ServerSocket-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.ServerSocket
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sidlx_rmi_ServerSocket_Impl_h
#define included_sidlx_rmi_ServerSocket_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
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
#ifndef included_sidlx_rmi_ServerSocket_h
#include "sidlx_rmi_ServerSocket.h"
#endif
#ifndef included_sidlx_rmi_Socket_h
#include "sidlx_rmi_Socket.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._hincludes) */
#include <pthread.h>
#include <stdio.h>
#include <stddef.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "sidl_rmi_NetworkException.h"
#include "sidlx_rmi_Socket.h"
#include "sidl_String.h"
#include "sidl_Exception.h"
/* insert implementation here: sidlx.rmi.ServerSocket._hincludes (include files) */
/* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._hincludes) */

/*
 * Private data for class sidlx.rmi.ServerSocket
 */

struct sidlx_rmi_ServerSocket__data {
  /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._data) */
  short d_port;
  int d_fd_listen;
  int d_fd_signal[2];
  /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidlx_rmi_ServerSocket__data*
sidlx_rmi_ServerSocket__get_data(
  sidlx_rmi_ServerSocket);

extern void
sidlx_rmi_ServerSocket__set_data(
  sidlx_rmi_ServerSocket,
  struct sidlx_rmi_ServerSocket__data*);

extern
void
impl_sidlx_rmi_ServerSocket__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_ServerSocket__ctor(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_ServerSocket__ctor2(
  /* in */ sidlx_rmi_ServerSocket self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidlx_rmi_ServerSocket__dtor(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_ServerSocket_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
int32_t
impl_sidlx_rmi_ServerSocket_init(
  /* in */ sidlx_rmi_ServerSocket self,
  /* in */ int32_t port,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidlx_rmi_ServerSocket_initLocal(
  /* in */ sidlx_rmi_ServerSocket self,
  /* in */ int32_t port,
  /* in */ sidl_bool loopback,
  /* out */ sidl_BaseInterface *_ex);

extern
sidlx_rmi_Socket
impl_sidlx_rmi_ServerSocket_accept(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidlx_rmi_ServerSocket_close(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidlx_rmi_ServerSocket_fconnect_sidl_BaseInterface(const char* url, 
  sidl_bool ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
