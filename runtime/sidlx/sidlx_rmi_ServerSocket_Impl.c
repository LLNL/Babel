/*
 * File:          sidlx_rmi_ServerSocket_Impl.c
 * Symbol:        sidlx.rmi.ServerSocket-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.ServerSocket
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.ServerSocket" (version 0.1)
 * 
 * Automatically sets up a port for listening for new connections
 */

#include "sidlx_rmi_ServerSocket_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._includes) */
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include "sidl_MemAllocException.h"
#include "sidlx_rmi_ChildSocket.h"
#include "sidlx_common.h"
#define LISTENQ 1024
#define MAXLINE 1023
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/poll.h>

/* I'd rather use strerror_r (threadsafe version) but it
   appears to be broken on ingot -- gkk */
#define SIDL_THROW_PERROR( ex, type, msg ) { \
   char stp_buf[1024]; \
   int my_err = errno; \
   const size_t msg_len = strlen(msg); \
   stp_buf[sizeof(stp_buf)-1] = '\0'; \
   if (msg_len >= sizeof(stp_buf)) { \
     memcpy(stp_buf, msg, sizeof(stp_buf) - 1); \
   } \
   else { \
     const char *_err = strerror(my_err); \
     strcpy(stp_buf, msg); \
     if ((msg_len + strlen(_err)) < sizeof(stp_buf)) { \
       strcpy(stp_buf+msg_len, _err); \
     } \
     else { \
       memcpy(stp_buf+msg_len, _err, sizeof(stp_buf) - msg_len - 1); \
     } \
   }\
   SIDL_THROW(ex, type, stp_buf); \
 }

/* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ServerSocket__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._load) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ServerSocket__ctor(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._ctor) */
  struct sidlx_rmi_ServerSocket__data *dptr = 
    (struct sidlx_rmi_ServerSocket__data *)
    malloc(sizeof(struct sidlx_rmi_ServerSocket__data));
  if(NULL == dptr) {
    sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
    sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
    sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.ServerSocket._ctor", _ex);
    *_ex = (sidl_BaseInterface)ex;
    goto EXIT;
  }

  sidlx_rmi_ServerSocket__set_data(self,dptr);
  /* initialize entire struct to zeros */
  memset(dptr, 0, sizeof(struct sidlx_rmi_ServerSocket__data));
  dptr->d_fd_listen = -1;
  dptr->d_fd_signal[0] = -1;
  dptr->d_fd_signal[1] = -1;
  if (pipe(dptr->d_fd_signal) == -1) {
    free((void *)dptr);
    SIDL_THROW_PERROR(*_ex, sidl_rmi_NetworkException,
		      "pipe() call failed: ");
  }

  EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ServerSocket__ctor2(
  /* in */ sidlx_rmi_ServerSocket self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._ctor2) */
  /* Insert-Code-Here {sidlx.rmi.ServerSocket._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_ServerSocket__dtor(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket._dtor) */
  struct sidlx_rmi_ServerSocket__data *dptr = 
    sidlx_rmi_ServerSocket__get_data(self);
  if (dptr != NULL) {
    if (dptr->d_fd_listen != -1) {
      sidlx_rmi_ServerSocket_close(self,_ex); SIDL_CLEAR(*_ex);
    }
    if (dptr->d_fd_signal[0] != -1) {
      close(dptr->d_fd_signal[0]);
    }
    if (dptr->d_fd_signal[1] != -1) {
      close(dptr->d_fd_signal[1]);
    }
  }
  sidlx_rmi_ServerSocket__set_data(self,NULL);
  free((void*) dptr);
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket._dtor) */
  }
}

/*
 *  If successful, returns 0 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket_init"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_ServerSocket_init(
  /* in */ sidlx_rmi_ServerSocket self,
  /* in */ int32_t port,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket.init) */
    return impl_sidlx_rmi_ServerSocket_initLocal(self, port, FALSE, _ex);
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket.init) */
  }
}

/*
 *  If successful, returns 0. If parameter local is true, the
 * socket accepts local connections only.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket_initLocal"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_ServerSocket_initLocal(
  /* in */ sidlx_rmi_ServerSocket self,
  /* in */ int32_t port,
  /* in */ sidl_bool loopback,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket.initLocal) */
    int n = -1;
    int fd;
    struct sockaddr_in addr;
    struct sidlx_rmi_ServerSocket__data *dptr =  
      sidlx_rmi_ServerSocket__get_data(self);

    /* test if the port is assigned */
    if (dptr->d_port != 0) { 
      SIDL_THROW_PERROR( *_ex, sidl_rmi_NetworkException,
                         "cannot init() an active sidlx.rmi.ServerSocket: ");
    } 
    dptr->d_port = (short)port;

    /* Fill in the address structure. */
    memset(&addr, 0, sizeof(struct sockaddr_in));
    addr.sin_family = AF_INET;
    if(loopback)
      addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
    else
      addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons(dptr->d_port);
  
    fd = s_socket(AF_INET, SOCK_STREAM, 0, _ex); SIDL_CHECK(*_ex);

    n = s_bind(fd, (struct sockaddr*) &(addr), 
               sizeof(struct sockaddr_in)); 
    if (n < 0)
      goto EXIT2;

    n = s_listen(fd, LISTENQ, _ex); 
    if (n < 0)
      goto EXIT2;

    dptr->d_fd_listen = fd;

    return n;
  EXIT2:
    close(fd);
  EXIT:
    /* reset port number on failure */
    dptr->d_port = 0;

    return n;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket.initLocal) */
  }
}

/*
 * Method:  accept[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket_accept"

#ifdef __cplusplus
extern "C"
#endif
sidlx_rmi_Socket
impl_sidlx_rmi_ServerSocket_accept(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket.accept) */
  sidlx_rmi_ChildSocket cSock = NULL;
  sidlx_rmi_Socket retSock = NULL;
  struct sockaddr_in cliaddr;
  socklen_t clilen = sizeof(struct sockaddr_in);
  int fd = -1;
  int rc;
  struct pollfd pfds[2];
  struct sidlx_rmi_ServerSocket__data *dptr =  
    sidlx_rmi_ServerSocket__get_data(self);

  if (dptr != NULL && dptr->d_fd_listen != -1) {
    pfds[0].fd = dptr->d_fd_signal[0];
    pfds[0].events = POLLIN;
    pfds[1].fd = dptr->d_fd_listen;
    pfds[1].events = POLLIN;

    while (1) {
      pfds[0].revents = 0;
      pfds[1].revents = 0;

      rc = poll(pfds, 2, -1); /* wait indefinitely */

      if (rc == 0) {
	/* Poll timed out.  Will never happen with a time of -1. */
      } else if (rc < 0) {
	/* Poll returned an error. */
	switch (errno) {
	case EAGAIN:
	case EINTR:
	  continue;
	default:
	  SIDL_THROW_PERROR(*_ex, sidl_rmi_NetworkException,
			    "poll() error: ");
	  break;
	}
      } else {
	/* Poll event occurred.  First check for errors. */
	if (pfds[0].revents & (POLLERR|POLLHUP|POLLNVAL|POLLIN)) {
	  /* If the other end of the pipe has been closed, it tells us
	   * that someone wants to shut down the listening socket.
	   * We should no longer poll the listening socket.
	   */
	  goto EXIT;
	}
	if (pfds[1].revents & (POLLERR|POLLHUP|POLLNVAL)) {
	  SIDL_THROW_PERROR(*_ex, sidl_rmi_NetworkException,
			    "error polling listening socket: ");
	}
	
	/* One of the file descriptors must be ready. */
	if (pfds[0].revents & POLLIN) {
	  /* Data on this pipe tells us that someone wants to shutdown this
	   * listening socket, so we should no longer poll the listening
	   * socket.
	   */
	  goto EXIT;
	}
	if (pfds[1].revents & POLLIN) {
	  /* Accept a new incoming RMI connection. */
	  fd = s_accept(dptr->d_fd_listen, (struct sockaddr*)&cliaddr,
			&clilen, _ex);
	  SIDL_CHECK(*_ex);
	  cSock = sidlx_rmi_ChildSocket__create(_ex); SIDL_CHECK(*_ex);
	  sidlx_rmi_ChildSocket_init(cSock, fd, _ex); SIDL_CHECK(*_ex);
	  retSock = sidlx_rmi_Socket__cast(cSock,_ex); SIDL_CHECK(*_ex);
	  sidlx_rmi_ChildSocket_deleteRef(cSock,_ex); SIDL_CHECK(*_ex);
	  return retSock;
	}
      }
    }
  } else {
    SIDL_THROW(*_ex, sidl_rmi_NetworkException,
	       "Server Socket has not been initialized!");
  }

 EXIT:
  if (fd != -1) {
    close(fd);
  }
  return NULL;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket.accept) */
  }
}

/*
 *  If successful, returns 0 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_ServerSocket_close"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_ServerSocket_close(
  /* in */ sidlx_rmi_ServerSocket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.ServerSocket.close) */
    int rc = -1;
    struct sidlx_rmi_ServerSocket__data *dptr = 
	    sidlx_rmi_ServerSocket__get_data(self);

    if (dptr->d_fd_listen == -1) {
      SIDL_THROW_PERROR(*_ex, sidl_rmi_NetworkException,
			"cannot close() an uninitialized sidlx.rmi.ServerSocket: ");
    } else {
      if (dptr->d_fd_signal[1] != -1) {
	close(dptr->d_fd_signal[1]);
	dptr->d_fd_signal[1] = -1;
      }
      close(dptr->d_fd_listen);
    }

  EXIT:
    return (int32_t)rc;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.ServerSocket.close) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

