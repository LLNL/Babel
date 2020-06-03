/*
 * File:          sidlx_rmi_SimCall_Impl.c
 * Symbol:        sidlx.rmi.SimCall-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.SimCall
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.SimCall" (version 0.1)
 * 
 * This type is created on the server side to get inargs off the network and 
 * pass them into exec.	
 */

#include "sidlx_rmi_SimCall_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall._includes) */
#include "sidl_rmi_NetworkException.h"
#include "sidlType.h"
#include "sidl_Exception.h"
#include "sidl_String.h"
#include "sidl_DLL.h"
#include "sidl_Resolve.h"
#include "sidl_Scope.h"
#include "sidl_Loader.h"
#include "sidl_io_Serializable.h"
#include "sidl_rmi_ProtocolFactory.h"
#include "sidl_rmi_ServerRegistry.h"
#include "sidlx_rmi_UnrecoverableException.h"
#include "sidl_rmi_ObjectDoesNotExistException.h"
#include "sidlx_rmi_UnauthorizedCallException.h"
#include "sidlx_rmi_IPv4Socket.h"
#include "sidlx_common.h"

#include "sidl_MemAllocException.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <unistd.h>
#include <errno.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <time.h>
#include <sys/time.h>
/* The SimCall class implements sidl.io.deserializer.  It's what
   happens to your in and inout args on the other side of the wire.
   Protocol writers: You don't really have to do anything like a I did
   here.  You probably should reuse the deserializer interface to make
   it easier to deserialize objects, but that's about where the
   requirements end.  Protocol writers can do pretty much anything
   they want in the ORB.
*/

static void check_cookie(sidlx_rmi_SimCall self,  
			 /*in*/ const char * cookie, /*in*/ int32_t len, /*out*/ sidl_BaseInterface* _ex ) { 
  *_ex=NULL;
  if ( len == 0 && cookie == NULL ) { return; } /* no cookie to check */
  else { 
    struct sidlx_rmi_SimCall__data *dptr =
      sidlx_rmi_SimCall__get_data(self);
    if(dptr){
      /* int counter = dptr->d_current; */
      int upper = sidl_char__array_upper(dptr->d_carray,0);
      char* d_buf = sidl_char__array_first(dptr->d_carray);
      char* begin = d_buf+dptr->d_current;
      int i;


      if ( (upper - dptr->d_current) < len ) { goto ERROR; }
      for( i=0; i<len; ++i ) {
	if ( begin[i] != cookie[i] ) { 
	  goto ERROR;
	} 
      }
      dptr->d_current += len;
      return;
    ERROR:
      /* This is commented out because I'm trusting SimpleOrb not to send anything back 
       * to the caller is it sees and UnauthorizedCallException.  If I close
       * this now, SimpleOrb might throw a spurious exception when it attempts to close
       * the socket. */
      {
        sidlx_rmi_IPv4Socket ipv4Sock = sidlx_rmi_IPv4Socket__cast(dptr->d_sock, _ex);
        char* badCookie = malloc(len+1);

        if(ipv4Sock && badCookie) {
          char msg[1024];
          char ip[16];
          char* selfUrl;
          int32_t address = 0;
          int32_t port = 0;
          struct timeval t1;
          struct tm t2;
          char date[20];
          gettimeofday(&t1, 0);
          /* Converting raw seconds to date and time. */
          gmtime_r(&(t1.tv_sec), &t2);
          /* Format the date and time into a string. */
          strftime(date, 20, "%Y-%m-%d %H:%M:%S", &t2);
          
          sidlx_rmi_IPv4Socket_getpeername(ipv4Sock, &address, &port, _ex); 
          selfUrl = sidl_rmi_ServerRegistry_getServerURL("", _ex);
          sidlx_rmi_IPv4Socket_deleteRef(ipv4Sock, _ex);
          int2ip(address, ip);
          
          if ( ((upper - dptr->d_current) < len) || (len > 512)) { 
            badCookie[0] = '\0'; 
          } else {
            memcpy(badCookie, begin, len);
            badCookie[len-1] = '\0';
          }
          
          sprintf(msg, "SimCall.check_cookie(): failed: closed socket without response.\n"
                  "Time: %s\n"
                  "Reciever URL: %s\n"
                  "Sender address: %s:%d\n"
                  "Sender Cookie: %s\n", date, selfUrl, ip, port, badCookie);
          fprintf(stderr, "%s\n", msg);
          SIDL_THROW(*_ex, sidlx_rmi_UnauthorizedCallException, msg);
        } else {
          fprintf(stderr, "SimCall.check_cookie(): failed: closed socket without response.");
          SIDL_THROW(*_ex, sidlx_rmi_UnauthorizedCallException, "SimCall.check_cookie(): failed: closed socket without response.");
        }
      }
    EXIT:
      return;
    }
  }
}

/** Parses string into tokens, replaces token seperator with '\0' and
 *  returns the pointer to the beginning of this token.  Should only be used
 *  when you know you're dealing with an alpha-numeric string.
 */

static char* get_next_token(sidlx_rmi_SimCall self,/*out*/ sidl_BaseInterface* _ex) {
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr){
    /* int counter = dptr->d_current; */
    int upper = sidl_char__array_upper(dptr->d_carray,0);
    char* d_buf = sidl_char__array_first(dptr->d_carray);
    char* begin = d_buf+dptr->d_current;
    char* s_ptr = begin;

    while(*s_ptr != ':') {
      ++s_ptr;
      ++(dptr->d_current);
      if(*s_ptr == '\0' || dptr->d_current > upper) {
	SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.get_next_token:Improperly formed response!");  
      }
    }
    *s_ptr = '\0';
    ++(dptr->d_current); /* Advance the the beginning of the next token */
    return begin;
  EXIT:
    return NULL;
  }
  return NULL;
}

/* Copy n bytes from the buffer to data*/
static void unserialize(sidlx_rmi_SimCall self, char* data, int n, int obj_size,
                        sidl_BaseInterface* _ex) {
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  int bytes = n * obj_size;
  char* d_buf = sidl_char__array_first(dptr->d_carray);
  int d_capacity = sidl_char__array_length(dptr->d_carray, 0);
  int rem = d_capacity - dptr->d_current; /*space remaining*/
  /*Offset required to align to data type size*/
  int offset = (obj_size - ((dptr->d_current) % obj_size)) % obj_size; 
  char* s_ptr = (d_buf)+(dptr->d_current) + offset;
  if(offset+bytes>rem) {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.unserialize: Not enough data left!");  
  }
  memcpy(data, s_ptr, bytes);
  (dptr->d_current) += bytes + offset;
 EXIT:
  return;
}

/* Moves some pointers around to pass back the chunk of space that should be
   holding array data. total_len is the expected length of the array data
   IN BYTES*/
static void* buffer_array(sidlx_rmi_SimCall self, int64_t total_elements, 
                          int32_t obj_size, int32_t obj_per_elem, sidl_BaseInterface* _ex) {

  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  char* d_buf = sidl_char__array_first(dptr->d_carray);
  int d_capacity = sidl_char__array_length(dptr->d_carray, 0);
  /*Offset required to align to data type size*/
  int offset = (obj_size - ((dptr->d_current) % obj_size)) % obj_size;
  int64_t total_len = (total_elements * obj_size * obj_per_elem) + offset;
  int rem = d_capacity - (dptr->d_current); /*space remaining*/
  char* s_ptr = (d_buf)+(dptr->d_current) + offset;
  if(total_len > rem) {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.unserialize: Not enough data left!");  
  }
  (dptr->d_current) += total_len;
  
  return s_ptr;
 EXIT:
  return NULL;
}

/* Checks dimension and array boundries to make sure they match*/
static sidl_bool check_bounds(struct sidl__array* a, int32_t dimen, int32_t* lower, int32_t* upper) {
  int32_t i;
  if(a && sidlArrayDim(a) == dimen) {
    for(i = 0; i < dimen; ++i) {
      if(sidlLower(a,i) != lower[i] || sidlUpper(a,i) != upper[i]) {
	return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
}
/* I'm removing the flipping because this protocol is already broken 
   if the byte order changes.
static void flip64(int64_t* in) {
  int64_t x = *in;
  *in =  ((((x) & 0xff00000000000000ull) >> 56)				
	  | (((x) & 0x00ff000000000000ull) >> 40)			
	  | (((x) & 0x0000ff0000000000ull) >> 24)			
	  | (((x) & 0x000000ff00000000ull) >> 8)			
	  | (((x) & 0x00000000ff000000ull) << 8)			
	  | (((x) & 0x0000000000ff0000ull) << 24)			
	  | (((x) & 0x000000000000ff00ull) << 40)			
	  | (((x) & 0x00000000000000ffull) << 56));
}

static void flip32(int32_t* in) {
  int32_t x = *in;
  *in = ((((x) & 0xff000000) >> 24) | (((x) & 0x00ff0000) >>  8) |	
	 (((x) & 0x0000ff00) <<  8) | (((x) & 0x000000ff) << 24));
}
*/

/* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall._load) */
  /* insert implementation here: sidlx.rmi.SimCall._load (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall__ctor(
  /* in */ sidlx_rmi_SimCall self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall._ctor) */
  /* insert implementation here: sidlx.rmi.SimCall._ctor (constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall__ctor2(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall._ctor2) */
  /* Insert-Code-Here {sidlx.rmi.SimCall._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall__dtor(
  /* in */ sidlx_rmi_SimCall self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall._dtor) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);

  if(dptr) {

    sidlx_rmi_Socket sock = NULL;
    sidl_char__array_deleteRef(dptr->d_carray);
    sidl_String_free(dptr->d_methodName);
    /*sidl_String_free(dptr->d_clsid);*/
    sidl_String_free(dptr->d_objid);
    sock = dptr->d_sock;
    free((void*)dptr);
    sidlx_rmi_SimCall__set_data(self, NULL);

    if(sock) { 
      /* Clean up everything before there's a chance of exception. */
      sidlx_rmi_Socket_deleteRef(sock, _ex); /*SIDL_CHECK(*_ex);*/
    }
  }
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall._dtor) */
  }
}

/*
 * Method:  init[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_init"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_init(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ sidlx_rmi_Socket sock,
  /* in rarray[len] */ char* cookie,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.init) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  char* token = NULL;
  if (dptr) {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "This Call has already been init'd!");
  } else {
    dptr = malloc(sizeof(struct sidlx_rmi_SimCall__data));
    if(!dptr) {
      sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.SimCall.init", _ex);
      *_ex = (sidl_BaseInterface)ex;
      goto EXIT;
    }
  }
  sidlx_rmi_Socket_addRef(sock, _ex); SIDL_CHECK(*_ex);
  dptr->d_methodName = NULL;
  /*dptr->d_clsid = NULL;*/
  dptr->d_objid = NULL;
  dptr->d_sock = sock;
  dptr->d_carray = NULL;
  dptr->d_current = 0;
  sidlx_rmi_SimCall__set_data(self, dptr);

  /* Allocate a buffer and copy the method call into it. */
  sidlx_rmi_Socket_readstring_alloc(sock,&(dptr->d_carray),_ex);SIDL_CHECK(*_ex);

  /* Check cookie (if it exists) */
  check_cookie(self, cookie, len, _ex); SIDL_CHECK(*_ex);

  /* The call could either be a create request or a function call.
     It's our job to figure out which.  The format is explained in
     sidlx.rmi.SimHandle */
  token = get_next_token(self, _ex); SIDL_CHECK(*_ex);
  if(sidl_String_equals(token, "CREATE")) {
    dptr->d_calltype = sidlx_rmi_CallType_CREATE;
    dptr->d_objid = NULL;
    dptr->d_methodName = sidl_String_strdup("CREATE");

  } else if(sidl_String_equals(token, "EXEC")) {
    dptr->d_calltype = sidlx_rmi_CallType_EXEC;

    token = get_next_token(self, _ex); SIDL_CHECK(*_ex);
    if(!sidl_String_equals(token, "objid")) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.init:Improperly formed call!");  
    }
    
    token = get_next_token(self, _ex); SIDL_CHECK(*_ex);
    dptr->d_objid = sidl_String_strdup(token); /*This could be eliminated to save time*/

    token = get_next_token(self, _ex);SIDL_CHECK(*_ex);
    if(!sidl_String_equals(token, "method")) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.init:Improperly formed call!");
    }

    token = get_next_token(self, _ex); SIDL_CHECK(*_ex);
    dptr->d_methodName = sidl_String_strdup(token); /*This could be eliminated to save time*/

    token = get_next_token(self, _ex);SIDL_CHECK(*_ex);
    if(!sidl_String_equals(token, "args")) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.init:Improperly formed call!");
    }
    /* Now return and the arguments will be unserialized by the ORB*/
  } else if(sidl_String_equals(token, "SERIAL")) {
    dptr->d_calltype = sidlx_rmi_CallType_SERIAL;
    dptr->d_objid = NULL;
    dptr->d_methodName = sidl_String_strdup("SERIAL");

  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.init:Improperly formed call!");

  }

  return;
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.init) */
  }
}

/*
 * Method:  getMethodName[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_getMethodName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimCall_getMethodName(
  /* in */ sidlx_rmi_SimCall self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.getMethodName) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if (dptr) {
    return sidl_String_strdup(dptr->d_methodName);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This call has not been initialized yet.!");
  }
 EXIT:
  return NULL;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.getMethodName) */
  }
}

/*
 * Method:  getObjectID[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_getObjectID"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimCall_getObjectID(
  /* in */ sidlx_rmi_SimCall self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.getObjectID) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if (dptr) {
    return sidl_String_strdup(dptr->d_objid);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This call has not been initialized yet.!");
  }
 EXIT:
  return NULL;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.getObjectID) */
  }
}

/*
 * Method:  getCallType[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_getCallType"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_SimCall_getCallType(
  /* in */ sidlx_rmi_SimCall self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.getCallType) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if (dptr) {
    return dptr->d_calltype;
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This call has not been initialized yet.!");
  }
 EXIT:
  return sidlx_rmi_CallType_ERROR;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.getCallType) */
  }
}

/*
 * Method:  unpackBool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackBool"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackBool(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ sidl_bool* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackBool) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    char temp;
    unserialize(self, &temp, 1, 1, _ex); SIDL_CHECK(*_ex);
    if(temp == 0) {
      *value = 0;  /*false*/
    }else {
      *value = 1;  /*true*/
    }
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackBool) */
  }
}

/*
 * Method:  unpackChar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackChar"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackChar(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ char* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackChar) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    unserialize(self, value, 1, 1, _ex); SIDL_CHECK(*_ex);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackChar) */
  }
}

/*
 * Method:  unpackInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackInt"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackInt(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ int32_t* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackInt) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    unserialize(self, (char*)value, 1, 4, _ex); SIDL_CHECK(*_ex);
    /*int32_t temp;
     *value = ntohl(temp);*/
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackInt) */
  }
}

/*
 * Method:  unpackLong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackLong"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackLong(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ int64_t* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackLong) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  /*  short host = 1;
      short net = ntohs(host);*/
  if(dptr) {
    unserialize(self, (char*)value, 1, 8, _ex); SIDL_CHECK(*_ex);
    /*    int64_t temp;
          if(host == net) {  This computer uses network byte ordering
          *value = temp;
          } else {           This computer does not use network byte ordering
          *value = temp;
          flip64(value);
          }*/
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackLong) */
  }
}

/*
 * Method:  unpackOpaque[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackOpaque"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackOpaque(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ void** value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackOpaque) */
    struct sidlx_rmi_SimCall__data *dptr =
      sidlx_rmi_SimCall__get_data(self);
    if(dptr) {
      int64_t temp;
      unserialize(self, (char*)&temp, 1, 8, _ex); SIDL_CHECK(*_ex);
      *value = (void*) (ptrdiff_t) temp;
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackOpaque) */
  }
}

/*
 * Method:  unpackFloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackFloat"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackFloat(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ float* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackFloat) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    unserialize(self, (char*)value, 1, 4, _ex); SIDL_CHECK(*_ex);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackFloat) */
  }
}

/*
 * Method:  unpackDouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackDouble"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackDouble(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ double* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackDouble) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    unserialize(self, (char*)value, 1, 8, _ex); SIDL_CHECK(*_ex);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackDouble) */
  }
}

/*
 * Method:  unpackFcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackFcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackFcomplex(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ struct sidl_fcomplex* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackFcomplex) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    unserialize(self, (char*)(&(value->real)), 1, 4, _ex); SIDL_CHECK(*_ex);
    unserialize(self, (char*)(&(value->imaginary)), 1, 4, _ex); SIDL_CHECK(*_ex);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackFcomplex) */
  }
}

/*
 * Method:  unpackDcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackDcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackDcomplex(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ struct sidl_dcomplex* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackDcomplex) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    unserialize(self, (char*)(&(value->real)), 1, 8, _ex); SIDL_CHECK(*_ex);
    unserialize(self, (char*)(&(value->imaginary)), 1, 8, _ex); SIDL_CHECK(*_ex);
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackDcomplex) */
  }
}

/*
 * Method:  unpackString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackString"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackString(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ char** value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackString) */
  struct sidlx_rmi_SimCall__data *dptr =
    sidlx_rmi_SimCall__get_data(self);
  if(dptr) {
    int32_t len = 0;
    unserialize(self, (char*)&len, 1, 4, _ex); SIDL_CHECK(*_ex);
    /*len = ntohl(temp);*/
    if(len <= 0) {
      *value = NULL;
      return;
    }
    *value = sidl_String_alloc(len);
    unserialize(self, *value, len, 1, _ex); SIDL_CHECK(*_ex);
    (*value)[len] = '\0';
  } else {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "SimCall.getMethodName: This SimCall not initilized!");  
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackString) */
  }
}

/*
 * Method:  unpackSerializable[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackSerializable"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackSerializable(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout */ sidl_io_Serializable* value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackSerializable) */
  char* className = NULL;
  sidl_DLL dll = NULL;
  sidl_BaseClass h = NULL;
  sidl_io_Deserializer ds = NULL;
  int is_remote = 0;
  char* obj_url = NULL;
  sidl_BaseInterface _throwaway_exception = NULL;

  sidlx_rmi_SimCall_unpackBool(self, NULL, &is_remote, _ex); SIDL_CHECK(*_ex);
  if(is_remote) {
    sidlx_rmi_SimCall_unpackString(self, NULL, &obj_url, _ex); SIDL_CHECK(*_ex); 
    if(obj_url == NULL) { *value = NULL; goto EXIT; }
    *value = sidl_rmi_ProtocolFactory_unserializeInstance(obj_url, _ex); SIDL_CHECK(*_ex);
    
  } else {
    ds = sidl_io_Deserializer__cast(self, _ex); SIDL_CHECK(*_ex);
    sidl_io_Deserializer_unpackString(ds, NULL, &className, _ex); SIDL_CHECK(*_ex); 
    
    h = sidlx_createClass(className, _ex); SIDL_CHECK(*_ex);
    *value = sidl_io_Serializable__cast((void*)h, _ex); SIDL_CHECK(*_ex);
    sidl_io_Serializable_unpackObj(*value, ds, _ex); SIDL_CHECK(*_ex);
  }

 EXIT:
  if (ds) { sidl_io_Deserializer_deleteRef(ds, &_throwaway_exception); }
  if (dll){ sidl_DLL_deleteRef(dll, &_throwaway_exception); }
  if (h) {sidl_BaseClass_deleteRef(h, &_throwaway_exception); }
  sidl_String_free(obj_url);
  sidl_String_free(className);

  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackSerializable) */
  }
}

/*
 *  unpack arrays of values 
 * It is possible to ensure an array is
 * in a certain order by passing in ordering and dimension
 * requirements.  ordering should represent a value in the
 * sidl_array_ordering enumeration in sidlArray.h If either
 * argument is 0, it means there is no restriction on that
 * aspect.  The rarray flag should be set if the array being
 * passed in is actually an rarray.  The semantics are slightly
 * different for rarrays.  The passed in array MUST be reused,
 * even if the array has changed bounds.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackBoolArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackBoolArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<bool> */ struct sidl_bool__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackBoolArray) */
  sidl_bool isRow;
  char* srcFirst = NULL;
  sidl_bool* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  int i;
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }  

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}
  
  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_bool__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_bool__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len, 1, 1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_bool__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    /* Boolean is a special case, we can't memcpy*/
    do {
      if(*srcFirst == 0) {
	*destFirst = FALSE;
      } else {
	*destFirst = TRUE;
      }
      /* the whole point of this for-loop is to move forward one element */
      for(i = l_dimen - 1; i >= 0; --i) {
	++(current[i]);
	if (current[i] >= lengths[i]) {
	  /* this dimension has been enumerated already reset to beginning */
	  current[i] = 0;
	  /* prepare to next iteration of for-loop for i-1 */
	  destFirst -= ((lengths[i]-1) * dest_stride[i]);
	  srcFirst -= ((lengths[i]-1) * src_stride[i]);
	}
	else {
	  /* move forward one element in dimension i */
	  destFirst += dest_stride[i];
	  srcFirst += src_stride[i];
	  break; /* exit for loop */
	}
      }
    } while (i >= 0);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackBoolArray) */
  }
}

/*
 * Method:  unpackCharArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackCharArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackCharArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<char> */ struct sidl_char__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackCharArray) */
  sidl_bool isRow;
  char* srcFirst = NULL;
  char* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_char__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_char__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,1,1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_char__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 1 * 1);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackCharArray) */
  }
}

/*
 * Method:  unpackIntArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackIntArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackIntArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<int> */ struct sidl_int__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackIntArray) */
  sidl_bool isRow;
  int32_t* srcFirst = NULL;
  int32_t* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_int__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_int__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,4,1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_int__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 4 * 1);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackIntArray) */
  }
}

/*
 * Method:  unpackLongArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackLongArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackLongArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<long> */ struct sidl_long__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackLongArray) */
  sidl_bool isRow;
  int64_t* srcFirst = NULL;
  int64_t* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_long__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_long__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,8,1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_long__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 8 * 1);
  }
 EXIT:
  return;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackLongArray) */
  }
}

/*
 * Method:  unpackOpaqueArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackOpaqueArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackOpaqueArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<opaque> */ struct sidl_opaque__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackOpaqueArray) */
  sidl_bool isRow;
  int64_t* srcFirst = NULL;
  void** destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  int i;
  sidl_bool reuse = FALSE;
  int64_t temp = 0;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_opaque__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_opaque__array_createCol(l_dimen,lower,upper);
      }
    }
  }
  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,8,1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_opaque__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    do {
      temp = *srcFirst;
      *destFirst = (void*) (ptrdiff_t)temp;
      /* the whole point of this for-loop is to move forward one element */
      for(i = l_dimen - 1; i >= 0; --i) {
	++(current[i]);
	if (current[i] >= lengths[i]) {
	  /* this dimension has been enumerated already reset to beginning */
	  current[i] = 0;
	  /* prepare to next iteration of for-loop for i-1 */
	  destFirst -= ((lengths[i]-1) * dest_stride[i]);
	  srcFirst -= ((lengths[i]-1) * src_stride[i]);
	}
	else {
	  /* move forward one element in dimension i */
	  destFirst += dest_stride[i];
	  srcFirst += src_stride[i];
	  break; /* exit for loop */
	}
      }
    } while (i >= 0);
  }
 EXIT:
  return;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackOpaqueArray) */
  }
}

/*
 * Method:  unpackFloatArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackFloatArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackFloatArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<float> */ struct sidl_float__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackFloatArray) */
  sidl_bool isRow;
  float* srcFirst = NULL;
  float* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_float__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_float__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,4,1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_float__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 4 * 1);
  }
 EXIT:
  return;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackFloatArray) */
  }
}

/*
 * Method:  unpackDoubleArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackDoubleArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackDoubleArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<double> */ struct sidl_double__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackDoubleArray) */
  sidl_bool isRow;
  double* srcFirst = NULL;
  double* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }


  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_double__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_double__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = (double*)buffer_array(self, t_len,8,1, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_double__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 8 * 1);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackDoubleArray) */
  }
}

/*
 * Method:  unpackFcomplexArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackFcomplexArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackFcomplexArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<fcomplex> */ struct sidl_fcomplex__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackFcomplexArray) */
  sidl_bool isRow;
  struct sidl_fcomplex* srcFirst = NULL;
  struct sidl_fcomplex* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = 0;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_fcomplex__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_fcomplex__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,4,2, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_fcomplex__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 4 * 2);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackFcomplexArray) */
  }
}

/*
 * Method:  unpackDcomplexArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackDcomplexArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackDcomplexArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<dcomplex> */ struct sidl_dcomplex__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackDcomplexArray) */
  sidl_bool isRow;
  struct sidl_dcomplex* srcFirst = NULL;
  struct sidl_dcomplex* destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t *src_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  sidl_bool reuse = FALSE;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }


  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_dcomplex__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_dcomplex__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginnings of both arrays*/
  srcFirst = buffer_array(self, t_len,8,2, _ex);SIDL_CHECK(*_ex);
  destFirst = sidl_dcomplex__array_first(*value);

  dest_stride = (*value)->d_metadata.d_stride;
  src_stride = dest_stride; /*SHOULD be the same, figured out remotely*/
  if(t_len > 0) {
    memcpy(destFirst, srcFirst, t_len * 8 * 2);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackDcomplexArray) */
  }
}

/*
 * Method:  unpackStringArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackStringArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackStringArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<string> */ struct sidl_string__array** value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackStringArray) */
  sidl_bool isRow;
  char** destFirst = NULL;
  int32_t l_dimen = 0;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];

  int i;
  sidl_bool reuse = 0;

  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  /* If we want to reuse the array, and the array is reuseable, we don't have to
   * do anything here.  Otherwise, we need to either create a new array, or throw 
   * an exception.  (It shouldn't be possible that an rarray could not 
   * be reuseable
   * (ON SERVER SIDE WE SHOULD NEVER REUSE)
   */
  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value))) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_string__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_string__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginning the dest array*/
  /*HACK*/
  destFirst = (char**)sidl_int__array_first((struct sidl_int__array*) *value);

  dest_stride = ((struct sidl__array*)(*value))->d_stride;
  if(t_len > 0) {
    do {
      int len;
      unserialize(self, (char*)&len, 1, 4, _ex); SIDL_CHECK(*_ex);
      if(len <= 0) {
	*destFirst = NULL;
      } else {
	*destFirst = sidl_String_alloc(len);
	unserialize(self, *destFirst, len, 1, _ex); SIDL_CHECK(*_ex);
	(*destFirst)[len] = '\0';
      }
      /* the whole point of this for-loop is to move forward one element */
      for(i = l_dimen - 1; i >= 0; --i) {
	++(current[i]);
	if (current[i] >= lengths[i]) {
	  /* this dimension has been enumerated already reset to beginning */
	  current[i] = 0;
	  /* prepare to next iteration of for-loop for i-1 */
	  destFirst -= ((lengths[i]-1) * dest_stride[i]);
	}
	else {
	  /* move forward one element in dimension i */
	  destFirst += dest_stride[i];
	  break; /* exit for loop */
	}
      }
    } while (i >= 0);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackStringArray) */
  }
}

/*
 * Method:  unpackGenericArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackGenericArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackGenericArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<> */ struct sidl__array** value,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackGenericArray) */

  int32_t type = 0;
  /*unserialize type */
  sidlx_rmi_SimCall_unpackInt(self, NULL, &type, _ex); SIDL_CHECK(*_ex);
  if(type == 0) {
    /* If array is null (type == 0) return a null array*/
    *value = NULL;
    return;
  }
  switch(type) {
  case sidl_bool_array:
    sidlx_rmi_SimCall_unpackBoolArray(self,key,(struct sidl_bool__array**) value, 
				      0,0,0,_ex);
    break;
  case sidl_char_array:
    sidlx_rmi_SimCall_unpackCharArray(self,key,(struct sidl_char__array**) value, 
				      0,0,0,_ex);
    break;
  case sidl_dcomplex_array:
    sidlx_rmi_SimCall_unpackDcomplexArray(self,key,(struct sidl_dcomplex__array**) value, 
					  0,0,0,_ex);
    break;
  case sidl_double_array:
    sidlx_rmi_SimCall_unpackDoubleArray(self,key,(struct sidl_double__array**) value, 
					0,0,0,_ex);
    break;
  case sidl_fcomplex_array:
    sidlx_rmi_SimCall_unpackFcomplexArray(self,key,(struct sidl_fcomplex__array**) value, 
					  0,0,0,_ex);
    break;
  case sidl_float_array:
    sidlx_rmi_SimCall_unpackFloatArray(self,key,(struct sidl_float__array**) value, 
				       0,0,0,_ex);
    break;
  case sidl_int_array:
    sidlx_rmi_SimCall_unpackIntArray(self,key,(struct sidl_int__array**) value, 
				     0,0,0,_ex);
    break;
  case sidl_long_array:
    sidlx_rmi_SimCall_unpackLongArray(self,key,(struct sidl_long__array**) value, 
				      0,0,0,_ex);
    break;
  case sidl_opaque_array:
    sidlx_rmi_SimCall_unpackOpaqueArray(self,key,(struct sidl_opaque__array**) value, 
					0,0,0,_ex);
    break;
  case sidl_string_array:
    sidlx_rmi_SimCall_unpackStringArray(self,key,(struct sidl_string__array**) value, 
					0,0,0,_ex);
    break;
  case sidl_interface_array:
    sidlx_rmi_SimCall_unpackSerializableArray(self,key,(struct sidl_io_Serializable__array**) value, 
					      0,0,0,_ex);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackGenericArray) */
  }
}

/*
 * Method:  unpackSerializableArray[]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimCall_unpackSerializableArray"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimCall_unpackSerializableArray(
  /* in */ sidlx_rmi_SimCall self,
  /* in */ const char* key,
  /* inout array<sidl.io.Serializable> */ struct sidl_io_Serializable__array** 
    value,
  /* in */ int32_t ordering,
  /* in */ int32_t dimen,
  /* in */ sidl_bool isRarray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimCall.unpackSerializableArray) */
  sidl_bool isRow;
  sidl_io_Serializable* destFirst = NULL;
  int64_t t_len = 1; /*Total length (of the array, in elements)*/ 
  int32_t l_dimen = 0;
  int32_t count = 0;
  int32_t *dest_stride = NULL;
  int32_t lengths[7];
  int32_t current[7];
  int32_t lower[7];
  int32_t upper[7];
  int i;
  sidl_bool reuse = FALSE;
  
  /*Unserialize isRow and dimension*/
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &reuse, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackBool(self, NULL, &isRow, _ex);  SIDL_CHECK(*_ex);
  impl_sidlx_rmi_SimCall_unpackInt(self, NULL, &l_dimen, _ex); SIDL_CHECK(*_ex);
  if(l_dimen == 0) {
    *value = NULL;  /*A zero dimension means a null array*/
    return;
  }

  /* If dimen is 1, isRow is insignificant. */
  if(l_dimen == 1) {isRow = TRUE;}

  /*Unserialize arrays of upper and lower bounds*/
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, lower+count, _ex);
    SIDL_CHECK(*_ex);
  }
  for(count = 0; count < l_dimen; ++count) {
    impl_sidlx_rmi_SimCall_unpackInt(self, NULL, upper+count, _ex);
    SIDL_CHECK(*_ex);
  }

  if(!(reuse && check_bounds((struct sidl__array*)*value, l_dimen, lower, upper)
       && isRow == sidl__array_isRowOrder((struct sidl__array*)*value) )) {
    if(isRarray && reuse) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Rarray has illeagally changed bounds remotely");
    } else {
      if(reuse && *value) {
	sidl__array_deleteRef((struct sidl__array*)*value);
      }
      /* Create the destination array*/
      if(isRow) {
	*value = sidl_io_Serializable__array_createRow(l_dimen,lower,upper);
      } else {
	*value = sidl_io_Serializable__array_createCol(l_dimen,lower,upper);
      }
    }
  }

  /* Figure out the lengths of each dimension, and total length*/
  for(count=0; count<l_dimen; ++count) {
    int32_t len = sidlLength(*value, count);
    t_len *= len;
    lengths[count] = len;
    current[count] = 0;
  }

  /*Get the pointers to the beginning the dest array*/
  /*HACK*/
  destFirst = (sidl_io_Serializable*)sidl_int__array_first((struct sidl_int__array*) *value);

  dest_stride = ((struct sidl__array*)(*value))->d_stride;
  if(t_len > 0) {
    do {
      sidlx_rmi_SimCall_unpackSerializable(self, NULL, destFirst, _ex);
      /* the whole point of this for-loop is to move forward one element */
      for(i = l_dimen - 1; i >= 0; --i) {
	++(current[i]);
	if (current[i] >= lengths[i]) {
	  /* this dimension has been enumerated already reset to beginning */
	  current[i] = 0;
	  /* prepare to next iteration of for-loop for i-1 */
	  destFirst -= ((lengths[i]-1) * dest_stride[i]);
	}
	else {
	  /* move forward one element in dimension i */
	  destFirst += dest_stride[i];
	  break; /* exit for loop */
	}
      }
    } while (i >= 0);
  }
 EXIT:
  return;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimCall.unpackSerializableArray) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

