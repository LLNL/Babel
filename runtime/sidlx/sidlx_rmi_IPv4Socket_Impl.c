/*
 * File:          sidlx_rmi_IPv4Socket_Impl.c
 * Symbol:        sidlx.rmi.IPv4Socket-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.IPv4Socket
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.IPv4Socket" (version 0.1)
 * 
 * Basic functionality for an IPv4 Socket.  Implements most of the functions in Socket
 */

#include "sidlx_rmi_IPv4Socket_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket._includes) */
#include <stdio.h>
#include <stddef.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#ifdef HAVE_SYS_TYPES_H
#include <sys/types.h>
#endif
#ifdef HAVE_SYS_SOCKET_H
#include <sys/socket.h>
#endif
#ifdef HAVE_SYS_SELECT_H
#include <sys/select.h>
#endif

#include <sys/poll.h>

#ifdef HAVE_UNISTD_H
#include <unistd.h>
#endif
#ifdef HAVE_SYS_TIME_H
#include <sys/time.h>
#endif
#include <netinet/in.h>
#include <arpa/inet.h>
#include "sidl_rmi_NetworkException.h"
#include "sidl_String.h"
#include "sidl_Exception.h"
#include "sidl_io_IOException.h"
#include "sidlx_common.h"
#include "sidlx_rmi_UnrecoverableException.h"
#include "sidl_MemAllocException.h"
/* this is a utility function that makes sure a char array is
   1-D, packed, and has a minimum length. */
void ensure1DPackedChar( const int32_t minlen, 
			      struct sidl_char__array ** data ) { 
  int realloc = 1;
  int len;

  if (*data != NULL) { 
    if ( (sidl_char__array_dimen(*data)==1) &&  /* if 1-D */
	 (sidl_char__array_stride(*data,0)==1) ) {  /* and packed */
      len = sidl_char__array_length(*data,0); /* get length */
      if ( len >= minlen ) {                  /* if long enough */
	realloc = 0; /* no realloc */
      }
    }
    if (realloc) {  /* if realloc, then free current array */
      sidl_char__array_deleteRef(*data);
      *data=NULL;
    }
  }
  if (*data==NULL) { 
    /* at this point, whether it was always NULL or recently realloced 
       doesn't matter */
    *data = sidl_char__array_create1d(minlen+1);
  }
  return;
}

/* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_IPv4Socket__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket._load) */
  /* insert implementation here: sidlx.rmi.IPv4Socket._load (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_IPv4Socket__ctor(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket._ctor) */
  /*Actually, I don't want to do anything here.  I want data to be null.*/
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_IPv4Socket__ctor2(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket._ctor2) */
  /* Insert-Code-Here {sidlx.rmi.IPv4Socket._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_IPv4Socket__dtor(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket._dtor) */
  struct sidlx_rmi_IPv4Socket__data * data = sidlx_rmi_IPv4Socket__get_data( self );
  if (data) { 
    /*If the socket isn't closed, close it. Close frees data*/
    sidlx_rmi_IPv4Socket_close(self, _ex); 
  }
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket._dtor) */
  }
}

/*
 * Method:  getsockname[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_getsockname"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_getsockname(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* inout */ int32_t* address,
  /* inout */ int32_t* port,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.getsockname) */
  struct sockaddr_in localaddr;
  socklen_t len = sizeof(struct sockaddr_in);
  int32_t n = 0;

  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    localaddr.sin_family = AF_INET;
    if ((n=getsockname(dptr->fd, (struct sockaddr*) &localaddr, &len))<0) { 
      sidlx_throwException(errno, _ex);
      SIDL_CHECK(*_ex);
    }
    *port = ntohs(localaddr.sin_port);
    *address = ntohl(localaddr.sin_addr.s_addr);
    return n;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
 EXIT:
  return -1;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.getsockname) */
  }
}

/*
 * Method:  getpeername[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_getpeername"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_getpeername(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* inout */ int32_t* address,
  /* inout */ int32_t* port,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.getpeername) */
  struct sockaddr_in localaddr;
  socklen_t len = sizeof(struct sockaddr_in);
  int32_t n = 0;
  
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    localaddr.sin_family = AF_INET;
    if ((n=getpeername(dptr->fd, (struct sockaddr*) &localaddr, &len))<0) { 
      sidlx_throwException(errno, _ex);
      SIDL_CHECK(*_ex);
    }
    *port = ntohs(localaddr.sin_port);
    *address = ntohl(localaddr.sin_addr.s_addr);
    return n;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
 EXIT:
  return -1;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.getpeername) */
  }
}

/*
 * Method:  close[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_close"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_close(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.close) */
  int32_t n = 0;
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {

    /* Clean up the memory before we could possibly throw an exception. */
    int fd = dptr->fd;
    free((void *)dptr);
    sidlx_rmi_IPv4Socket__set_data(self, NULL);

    /* Attempt to shutdown, but don't worry about it if it fails. */
    shutdown(fd, SHUT_RDWR);

    /* Worry about the close if it fails though */
    if ((n=close(fd)) < 0 ) { 
      sidlx_throwException(errno, _ex);
      SIDL_CHECK(*_ex);
    }
    return n;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
  EXIT:
  return -1;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.close) */
  }
}

/*
 * Method:  readn[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_readn"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_readn(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t nbytes,
  /* inout array<char> */ struct sidl_char__array** data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.readn) */
  char* ptr;
  int32_t n_read = 0;
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);

  if (dptr) {
    ensure1DPackedChar( nbytes, data );
    
    ptr = sidl_char__array_first( *data ); /* get the first one */
    
    n_read = s_readn2(dptr->fd, nbytes, &ptr, _ex ); SIDL_CHECK(*_ex);
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
  return -1;
 EXIT:
  return n_read;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.readn) */
  }
}

/*
 * Method:  readline[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_readline"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_readline(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t nbytes,
  /* inout array<char> */ struct sidl_char__array** data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.readline) */
  char* ptr;
  int32_t n_read = -1;
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);

  if (dptr) {
    ensure1DPackedChar( nbytes, data );
    
    ptr = sidl_char__array_first( *data ); /* get the first one */
    
    n_read = s_readline2(dptr->fd, nbytes, &ptr, _ex); SIDL_CHECK(*_ex);
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
  return -1;
 EXIT:
  return n_read;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.readline) */
  }
}

/*
 * Method:  readstring[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_readstring"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_readstring(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t nbytes,
  /* inout array<char> */ struct sidl_char__array** data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.readstring) */
  /*I don't really like this.  I think we should have some what communicating back when
    the array was not big enough to handle all the data, so we can handle it later.
    I recommen using s_read_string_alloc intead.*/
  
  char* ptr;
  int32_t bytesToRead = 0;
  int32_t len = sidl_char__array_length(*data,0);
  int32_t n_read = -1;
  int32_t inLen, left;

  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    
    if(nbytes == -1)
      bytesToRead = len;
    else
      left = bytesToRead = (nbytes<len) ? nbytes: len;
    
    ensure1DPackedChar( bytesToRead, data );
    
    ptr = sidl_char__array_first( *data ); /* get the first one */
    n_read = s_readInt(dptr->fd, &inLen, _ex); SIDL_CHECK(*_ex);					   
    if(n_read <= 0 || inLen <= 0) {
      goto EXIT;
    }    

    bytesToRead = (bytesToRead<inLen)? bytesToRead:inLen; 
    /*hopefully bytesToRead is bigger than inLen */
    n_read = s_readn2(dptr->fd, bytesToRead, &ptr, _ex ); SIDL_CHECK(*_ex);
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
  return -1;
 EXIT:
  return n_read;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.readstring) */
  }
}

/*
 * Method:  readstring_alloc[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_readstring_alloc"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_readstring_alloc(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* inout array<char> */ struct sidl_char__array** data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.readstring_alloc) */
  /* read a string up into character array (newline preserved)
   frees the current sidl_char__array and allocates a new one if length < the incoming string
   if(*data == null) a string as long as nessecary will be allocated */

  int32_t inLen = 0;
  int32_t curLen = 0; 
  int32_t n = 0;
  int32_t lower[1], upper[1];
  
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    if(data ==NULL) {
      SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "read() error: data is NULL!");
      goto EXIT;
    }
    if(*data != NULL)
      curLen = sidl_char__array_length(*data, 0); /*Now that we know data isn't null*/
    else
      curLen = 0;

    n = s_readInt(dptr->fd, &inLen, _ex); if(*_ex) {goto EXIT;}
    if(inLen <= 0) {
      char errMsg[128];
      sprintf(errMsg, "Recieved invalid string length from server (%d), aborting. ", inLen);
      SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, errMsg);
      SIDL_CHECK(*_ex);
      goto EXIT;
    }
    
    if(curLen < inLen) {
      if(*data != NULL)
	sidl_char__array_deleteRef(*data);
      lower[0] = 0;
      upper[0] = inLen-1;
      *data = sidl_char__array_createCol(1,lower,upper);
    }
    
    n = s_readn(dptr->fd, inLen, data, _ex); SIDL_CHECK(*_ex);
    return n;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
  EXIT:
  return -1;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.readstring_alloc) */
  }
}

/*
 * Method:  readint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_readint"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_readint(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* inout */ int32_t* data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.readint) */
  int32_t ret = 0;
  char ** cast_data = (char**) &data;
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    ret = s_readn2(dptr->fd, 4, cast_data, _ex); SIDL_CHECK(*_ex);
    *data = ntohl(*data);
    return ret;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
 EXIT:
  return 0;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.readint) */
  }
}

/*
 * Method:  writen[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_writen"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_writen(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t nbytes,
  /* in array<char> */ struct sidl_char__array* data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.writen) */
  char * ptr= sidl_char__array_first( data );
  int32_t n = sidl_char__array_length(data,0);
  int32_t n_written;
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    if (nbytes != -1 ) { 
      /* unless nbytes is -1 (meaning "all"), take the min(nbytes, length()) */
      n = (n<nbytes)? n : nbytes;
    }
    n_written = s_writen2( dptr->fd, n, ptr, _ex); SIDL_CHECK(*_ex);
    return n_written;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
 EXIT:
  return -1;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.writen) */
  }
}

/*
 * Method:  writestring[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_writestring"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_writestring(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t nbytes,
  /* in array<char> */ struct sidl_char__array* data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.writestring) */
  char * ptr= sidl_char__array_first( data );
  int32_t n = sidl_char__array_length(data,0);
  int32_t n_written;
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    if (nbytes != -1 ) { 
      /* unless nbytes is -1 (meaning "all"), take the min(nbytes, length()) */
      n = (n<nbytes)? n : nbytes;
    }
    s_writeInt(dptr->fd, n, _ex); SIDL_CHECK(*_ex);
    n_written = s_writen2( dptr->fd, n, ptr, _ex); SIDL_CHECK(*_ex);
    return n_written;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
 EXIT:
  return -1;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.writestring) */
  }
}

/*
 * Method:  writeint[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_writeint"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_writeint(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.writeint) */
  printf("impl_sidlx_rmi_IPv4Socket_writeint NO WORKIE!");
  return 0;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.writeint) */
  }
}

/*
 * Method:  setFileDescriptor[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_setFileDescriptor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_IPv4Socket_setFileDescriptor(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t fd,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.setFileDescriptor) */
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    dptr->fd = fd;
  } else {
    dptr = malloc(sizeof(struct sidlx_rmi_IPv4Socket__data));
    if(NULL == dptr) {
      sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.IPv4Socket.setFileDescriptor", _ex);
      *_ex = (sidl_BaseInterface)ex;
      goto EXIT;
    }
    dptr->fd = fd;
  }
  sidlx_rmi_IPv4Socket__set_data(self, dptr);
  EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.setFileDescriptor) */
  }
}

/*
 * Method:  getFileDescriptor[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_getFileDescriptor"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_IPv4Socket_getFileDescriptor(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.getFileDescriptor) */
  struct sidlx_rmi_IPv4Socket__data *dptr =
    sidlx_rmi_IPv4Socket__get_data(self);
  if (dptr) {
    return dptr->fd;
  }
  SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");
 EXIT:
  return -1;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.getFileDescriptor) */
  }
}

/*
 * Method:  test[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_IPv4Socket_test"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_IPv4Socket_test(
  /* in */ sidlx_rmi_IPv4Socket self,
  /* in */ int32_t secs,
  /* in */ int32_t usecs,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.IPv4Socket.test) */
    /* WE ASSUME HERE THAT WE CANNOT HAVE MORE THAN 32768 FILE DESCRIPTORS! */
    /*    long fdsarray[MAXFILEDESC/sizeof(long)];
          fd_set* rfds = (fd_set*) fdsarray;
          struct timeval tv; 
    */
    struct pollfd fdarray[1];
    int retval;
    struct sidlx_rmi_IPv4Socket__data *dptr =
      sidlx_rmi_IPv4Socket__get_data(self);
    int timeout;

    if (!dptr) {
      SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, "This Socket isn't initialized!");  
    }

    /*    if(dptr->fd >= MAXFILEDESC) {
          char errormsg[1024];
          sprintf(errormsg,"This Socket code cannot support file descriptors > %d. %d is too big.\n",
          MAXFILEDESC, dptr->fd);
          SIDL_THROW( *_ex, sidlx_rmi_UnrecoverableException, errormsg);
          }
    */
    /* watch dptr->fd to see when it has input. */
    /*for(ii = 0; ii < sizeof(fdsarray); ++ii) {
       fdsarray[ii]=0;
      }
    FD_SET(dptr->fd, rfds);*/

    fdarray->fd = dptr->fd;
    fdarray->events = POLLIN;
    
    
    if ( secs >= 0 && usecs >= 0 ) { 
      timeout = 1000*secs + usecs / 1000;  /* We lose resolution here with poll.*/
    } else {
      timeout = -1;
    }
    retval = poll(fdarray, 1, timeout);
      /*     tv.tv_sec=secs;
             tv.tv_usec=usecs;
             retval = select( (dptr->fd)+1, rfds, NULL, NULL, &tv);
             } else { 
             retval = select( (dptr->fd)+1, rfds, NULL, NULL, NULL);
             }
      */
    
    if ( retval == -1 ) { 
      sidlx_throwException(errno, _ex);
      SIDL_CHECK(*_ex);
    } else if (retval) { 
      return TRUE;
    } else {
      return FALSE;
    }
  EXIT:
    return FALSE;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.IPv4Socket.test) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

