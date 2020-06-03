/*
 * File:          sidlx_rmi_SimHandle_Impl.c
 * Symbol:        sidlx.rmi.SimHandle-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.SimHandle
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.SimHandle" (version 0.1)
 * 
 * implementation of InstanceHandle using the Simhandle Protocol (written by Jim)
 */

#include "sidlx_rmi_SimHandle_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle._includes) */
#include "sidl_String.h"
#include <stdio.h>
#include <string.h>
#include <stddef.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "sidlx_common.h"
#include "sidl_rmi_MalformedURLException.h"
#include "sidlx_rmi_UnrecoverableException.h"
#include "sidlx_rmi_Common.h"
#include "sidl_MemAllocException.h"
#include "sidlOps.h"
/* This class, SimHandle, implements InstanceHandle, the starting
   point for Babel RMI Protocols.  It should be pointed out to anyone
   planning to make a Babel protocol, that most of this is
   implementation dependent.  The InstanceHandle's purpose is pretty
   much just to return serializers and deserializers for the Babel
   client side.  These are where the real work is done with
   communicating over the network.  In Simple Protocol's case, we open
   a connection to the server, serialize everything into a buffer,then
   push the buffer over the wire, then we wait to receive the
   response, which is copied into a buffer for deserialization.  After
   we get the response we close the connection.  The user deserializes
   the buffer at his leisure. It would probably be more efficent to
   use streams or something.  Protocol writers can do this, the Babel
   interface is flexible enough to handle just about anything.  So, in
   looking at this protocol for protocol writing guidence, don't worry
   too much about the implementation here, just the basics of what the
   functions do, not how.
*/


static int s_cookieLen = 0;
static char* s_cookie = NULL;

/* If this protocol has been used to make RMI calls, s_callsMade is set to TRUE;*/
static sidl_bool s_callsMade = FALSE;

/* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimHandle__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle._load) */
  /* insert implementation here: sidlx.rmi.SimHandle._load (static class initializer method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimHandle__ctor(
  /* in */ sidlx_rmi_SimHandle self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle._ctor) */
  struct sidlx_rmi_SimHandle__data *dptr = 
    malloc(sizeof(struct sidlx_rmi_SimHandle__data));
  if(NULL == dptr) {
    sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
    sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
    sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.SimHandle._ctor", _ex);
    *_ex = (sidl_BaseInterface)ex;
    goto EXIT;
  }
  
  sidlx_rmi_SimHandle__set_data(self, dptr);
  /* initialize data */
  dptr->d_prefix=NULL;
  dptr->d_server=NULL;
  dptr->d_port=-1;
  dptr->d_objectID=NULL;
  dptr->d_typeName=NULL;

  EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimHandle__ctor2(
  /* in */ sidlx_rmi_SimHandle self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle._ctor2) */
  /* Insert-Code-Here {sidlx.rmi.SimHandle._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimHandle__dtor(
  /* in */ sidlx_rmi_SimHandle self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle._dtor) */
  struct sidlx_rmi_SimHandle__data *dptr = NULL;
  sidl_BaseInterface _throwaway_exception = NULL;

  sidlx_rmi_SimHandle_close(self,_ex); SIDL_CHECK(*_ex);
  if(*_ex) {
    /* No exceptions should be thrown from a destructor*/
    sidl_BaseInterface_deleteRef(*_ex, &_throwaway_exception);
    *_ex = NULL;
  }
  dptr = sidlx_rmi_SimHandle__get_data(self);

  if(dptr) {
    if (dptr->d_prefix) sidl_String_free(dptr->d_prefix);
    if (dptr->d_server) sidl_String_free(dptr->d_server);
    if (dptr->d_objectID) sidl_String_free(dptr->d_objectID);
    if (dptr->d_typeName) sidl_String_free(dptr->d_typeName);
    free(dptr);
    sidlx_rmi_SimHandle__set_data(self,NULL);
  }
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle._dtor) */
  }
}

/*
 * Set a cookie.  All RMI's made by this protocol will use this cookie
 * after it is set.  This should be called BEFORE any calls are made
 * by this protocol.  If it is called WHILE calls are being made on
 * another thread, the results are undefined.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_setCookie"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimHandle_setCookie(
  /* in rarray[len] */ char* cookie,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.setCookie) */

    if(s_callsMade || s_cookieLen > 0) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, 
                 "ERROR: setCookie cannot be called twice or after RMIs have been made.\n");   
    }

    /* I don't lock here because s_cookie is read-only after this call.*/
    s_cookie = (char*) malloc(sizeof(char)*len);
    if ( s_cookie ) { 
      s_cookieLen = len;
      memmove(s_cookie, cookie, len);
      sidl_atexit(free, (void*)s_cookie);
    } else { 
      sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
      sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
      sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.SimpleOrb.setCookie", _ex);
      *_ex = (sidl_BaseInterface)ex;
      goto EXIT;
    }
  EXIT:
    return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.setCookie) */
  }
}

/*
 *  initialize a connection (intended for use by the
 * ProtocolFactory, (see above).  This should parse the url and
 * do everything necessary to create the remote object.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_initCreate"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimHandle_initCreate(
  /* in */ sidlx_rmi_SimHandle self,
  /* in */ const char* url,
  /* in */ const char* typeName,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.initCreate) */
  /* This function creates a remote object on the server named in the url.*/
  sidl_BaseInterface _throwaway_exception = NULL;
  sidl_BaseException _be = NULL;

  struct sidlx_rmi_SimHandle__data *dptr = NULL;
  sidlx_rmi_Simvocation obj = NULL;
  sidlx_rmi_ClientSocket connSock = NULL;
  sidlx_rmi_Socket locSock = NULL;
  sidl_rmi_Response resp = NULL;
  sidlx_rmi_Simsponse simSponse = NULL;
  int32_t port;
  int32_t IP;
  struct sidl_char__array * carray= NULL;
  char* prefix = NULL;
  char* server = NULL;
  char* objectID = NULL;

  /* Just a bool so we can tell if RMI's have been made.  Not thread-safe. */
  s_callsMade = TRUE;

  dptr=sidlx_rmi_SimHandle__get_data(self);
  if (!dptr) {
        SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "ERROR: simhandle was not ctor'd\n");   

  }
  sidlx_parseURL(url, &prefix, &server, &port, NULL, &objectID, _ex);SIDL_CHECK(*_ex);

  if(!prefix || !server || !port || objectID) {
    SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "ERROR: malformed URL\n");
  }
  
  /*
   * Here where we make a connection to the ORB
   */
  obj = sidlx_rmi_Simvocation__create(_ex); SIDL_CHECK(*_ex);
  connSock = sidlx_rmi_ClientSocket__create(_ex); SIDL_CHECK(*_ex);
  IP = sidlx_rmi_Common_getHostIP(server, _ex); SIDL_CHECK(*_ex);
  sidlx_rmi_ClientSocket_init(connSock, IP, port,_ex);SIDL_CHECK(*_ex);
  locSock = sidlx_rmi_Socket__cast(connSock,_ex); SIDL_CHECK(*_ex);
  
  /* Call CREATE */    
  sidlx_rmi_Simvocation_initCreate(obj, typeName, locSock, s_cookie, 
                                   s_cookieLen, _ex); SIDL_CHECK(*_ex);
  resp = sidlx_rmi_Simvocation_invokeMethod(obj, _ex); SIDL_CHECK(*_ex);
  _be = sidl_rmi_Response_getExceptionThrown(resp, _ex);SIDL_CHECK(*_ex);

  simSponse = sidlx_rmi_Simsponse__cast(resp, _ex); SIDL_CHECK(*_ex);  
  
  /* Don't set this stuff until we're sure the object actually was created*/
  dptr->d_prefix = prefix;
  dptr->d_server = server;
  dptr->d_IP = IP;
  dptr->d_port = port;
  dptr->d_typeName = sidl_String_strdup(typeName);
  dptr->d_objectID = sidlx_rmi_Simsponse_getObjectID(simSponse, _ex); SIDL_CHECK(*_ex);

  sidl_char__array_deleteRef(carray);
  sidl_rmi_Response_deleteRef(resp, &_throwaway_exception);
  sidlx_rmi_Simvocation_deleteRef(obj,&_throwaway_exception);
  sidlx_rmi_Simsponse_deleteRef(simSponse,&_throwaway_exception); 
  sidlx_rmi_ClientSocket_deleteRef(connSock,&_throwaway_exception); 
  sidlx_rmi_Socket_deleteRef(locSock,&_throwaway_exception); 
  return 1; /*true*/
 EXIT:
  if(resp) {sidl_rmi_Response_deleteRef(resp, &_throwaway_exception); resp=NULL;}
  if(obj) { sidlx_rmi_Simvocation_deleteRef(obj,&_throwaway_exception); obj=NULL;}
  if(simSponse) { sidlx_rmi_Simsponse_deleteRef(simSponse,&_throwaway_exception); simSponse=NULL;}
  if(carray) { sidl_char__array_deleteRef(carray); }
  if(locSock) { sidlx_rmi_Socket_deleteRef(locSock,&_throwaway_exception);}
  if(connSock){ sidlx_rmi_ClientSocket_deleteRef(connSock, &_throwaway_exception);}
  return 0; /*false*/
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.initCreate) */
  }
}

/*
 * initialize a connection (intended for use by the ProtocolFactory) 
 * This should parse the url and do everything necessary to connect 
 * to a remote object.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_initConnect"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimHandle_initConnect(
  /* in */ sidlx_rmi_SimHandle self,
  /* in */ const char* url,
  /* in */ const char* typeName,
  /* in */ sidl_bool ar,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.initConnect) */
  /* Alerts the ORB to the additional remote connection to this object.
   * In Simple Protocol, this really just means that it calls remote addRef
   */
  sidl_BaseInterface _throwaway_exception = NULL;
  sidl_BaseException netex = NULL;
  sidlx_rmi_ClientSocket connSock = NULL;
  sidlx_rmi_Socket locSock = NULL;
  /*sidlx_rmi_Simsponse resp = NULL;*/
  sidl_rmi_Response resp = NULL;
  sidlx_rmi_Simvocation simvocation = NULL; 
  char* prefix = NULL;
  char* server = NULL;
  int32_t port = 0;
  /*char* typename = NULL;*/
  char* objectID = NULL;
  sidl_bool success = FALSE;

  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);  

  /* Just a bool so we can tell if RMI's have been made.  Not thread-safe. */
  s_callsMade = TRUE;

  sidlx_parseURL(url, &prefix, &server, &port, NULL, &objectID, _ex);SIDL_CHECK(*_ex);

  if(!prefix || !server || !port || !objectID) {
    SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "ERROR: malformed URL\n");
  }

  dptr->d_IP = sidlx_rmi_Common_getHostIP(server, _ex); SIDL_CHECK(*_ex);
  dptr->d_prefix = prefix;
  dptr->d_server = server;
  dptr->d_port = port;
  dptr->d_objectID = objectID;
  dptr->d_typeName = NULL; /*sidl_String_strdup(typename);*/

  /* In implicit connection cases, we don't want to addRef the remote object.
   * if ar is false, do not addRef.  If it's true, addRef
   */
  if(ar) {
    void* castResult = NULL;

    simvocation = sidlx_rmi_Simvocation__create(_ex); SIDL_CHECK(*_ex);
    /* Open a connection*/
    connSock = sidlx_rmi_ClientSocket__create(_ex); SIDL_CHECK(*_ex);
    sidlx_rmi_ClientSocket_init(connSock, dptr->d_IP, dptr->d_port,_ex);SIDL_CHECK(*_ex);
    locSock = sidlx_rmi_Socket__cast(connSock, _ex); SIDL_CHECK(*_ex);
    
    /* Call addRef */    
    sidlx_rmi_Simvocation_initInvocation(simvocation, "_cast", dptr->d_objectID, 
			       locSock, s_cookie, s_cookieLen, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Simvocation_packString(simvocation, "name", typeName, _ex); SIDL_CHECK(*_ex);
    resp = sidlx_rmi_Simvocation_invokeMethod(simvocation, _ex); SIDL_CHECK(*_ex);

    netex = sidl_rmi_Response_getExceptionThrown(resp, _ex); SIDL_CHECK(*_ex);
    if(netex != NULL) {
      *_ex = (sidl_BaseInterface) netex;
      goto EXIT;
    }

    sidl_rmi_Response_unpackOpaque(resp, "_retval", &castResult, _ex); SIDL_CHECK(*_ex);

    if(castResult == NULL) {
      success = FALSE;
    } else {
      success = TRUE;
    }

  } else {

    simvocation = sidlx_rmi_Simvocation__create(_ex); SIDL_CHECK(*_ex);
    /* Open a connection*/
    connSock = sidlx_rmi_ClientSocket__create(_ex); SIDL_CHECK(*_ex);
    sidlx_rmi_ClientSocket_init(connSock, dptr->d_IP, dptr->d_port,_ex);SIDL_CHECK(*_ex);
    locSock = sidlx_rmi_Socket__cast(connSock, _ex); SIDL_CHECK(*_ex);
    
    /* Call addRef */    
    sidlx_rmi_Simvocation_initInvocation(simvocation, "isType", dptr->d_objectID, 
			       locSock, s_cookie, s_cookieLen, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Simvocation_packString(simvocation, "name", typeName, _ex); SIDL_CHECK(*_ex);
    resp = sidlx_rmi_Simvocation_invokeMethod(simvocation, _ex); SIDL_CHECK(*_ex);
    
    netex = sidl_rmi_Response_getExceptionThrown(resp, _ex); SIDL_CHECK(*_ex);
    if(netex != NULL) {
      *_ex = (sidl_BaseInterface) netex;
      goto EXIT;
    }

    sidl_rmi_Response_unpackBool(resp, "_retval", &success, _ex); SIDL_CHECK(*_ex);
  }

 EXIT:
  if(locSock) { sidlx_rmi_Socket_deleteRef(locSock,&_throwaway_exception); }
  if(connSock) { sidlx_rmi_ClientSocket_deleteRef(connSock,&_throwaway_exception); }
  if(resp) { sidl_rmi_Response_deleteRef(resp,&_throwaway_exception); }
  if(simvocation) { sidlx_rmi_Simvocation_deleteRef(simvocation,&_throwaway_exception);}
  return success; 
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.initConnect) */
  }
}

/*
 *  Get a connection specifically for the purpose for requesting a 
 * serialization of a remote object (intended for use by the
 * ProtocolFactory, (see above).  This should parse the url and
 * request the object.  It should return a deserializer..
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_initUnserialize"

#ifdef __cplusplus
extern "C"
#endif
sidl_io_Serializable
impl_sidlx_rmi_SimHandle_initUnserialize(
  /* in */ sidlx_rmi_SimHandle self,
  /* in */ const char* url,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.initUnserialize) */
  sidl_BaseInterface _throwaway_exception = NULL;
  sidl_BaseException _be = NULL;

  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);

  sidlx_rmi_Simvocation obj = NULL;
  sidlx_rmi_ClientSocket connSock = NULL;
  sidlx_rmi_Socket locSock = NULL;
  sidl_rmi_Response resp = NULL;
  int32_t port;
  struct sidl_char__array * carray= NULL;
  char* prefix = NULL;
  char* server = NULL;
  char* objectID = NULL;
  sidl_io_Serializable ser = NULL;

  /* Just a bool so we can tell if RMI's have been made.  Not thread-safe. */
  s_callsMade = TRUE;

  if (!dptr) {
    SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "ERROR: simhandle was not ctor'd\n");   
  }
  sidlx_parseURL(url, &prefix, &server, &port, NULL, &objectID, _ex);SIDL_CHECK(*_ex);

  if(!prefix || !server || !port || !objectID) {
    SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "ERROR: malformed URL\n");
  }

  dptr->d_IP = sidlx_rmi_Common_getHostIP(server, _ex); SIDL_CHECK(*_ex);
  dptr->d_prefix = prefix;
  dptr->d_server = server;
  dptr->d_port = port;
  dptr->d_typeName = NULL;
  dptr->d_objectID = objectID;
  /*dptr->d_sock = NULL;*/

  /*
   * Here where we make a connection to the ORB
   */
  connSock = sidlx_rmi_ClientSocket__create(_ex); SIDL_CHECK(*_ex);
  sidlx_rmi_ClientSocket_init(connSock, dptr->d_IP, port,_ex);SIDL_CHECK(*_ex);
  locSock = sidlx_rmi_Socket__cast(connSock,_ex); SIDL_CHECK(*_ex);
  obj = sidlx_rmi_Simvocation__create(_ex); SIDL_CHECK(*_ex);

  /*
   * Call unserialize
   */
  sidlx_rmi_Simvocation_initUnserialize(obj, objectID, locSock, s_cookie, 
                                        s_cookieLen, _ex); SIDL_CHECK(*_ex);
  resp = sidlx_rmi_Simvocation_invokeMethod(obj, _ex); SIDL_CHECK(*_ex);
  _be = sidl_rmi_Response_getExceptionThrown(resp, _ex);SIDL_CHECK(*_ex);

  sidl_rmi_Response_unpackSerializable(resp, NULL, &ser, _ex); SIDL_CHECK(*_ex);

  sidl_char__array_deleteRef(carray);
  sidlx_rmi_Simvocation_deleteRef(obj,_ex); SIDL_CHECK(*_ex);
  sidl_rmi_Response_deleteRef(resp,_ex); SIDL_CHECK(*_ex);
  sidlx_rmi_ClientSocket_deleteRef(connSock,_ex); SIDL_CHECK(*_ex);
  sidlx_rmi_Socket_deleteRef(locSock,_ex); SIDL_CHECK(*_ex);
  return ser; /*true*/
 EXIT:
  if(carray) { sidl_char__array_deleteRef(carray); }
  if(resp) { sidl_rmi_Response_deleteRef(resp,&_throwaway_exception);}
  if(obj) { sidlx_rmi_Simvocation_deleteRef(obj,&_throwaway_exception);}
  if(locSock) { sidlx_rmi_Socket_deleteRef(locSock,&_throwaway_exception);}
  if(connSock){ sidlx_rmi_ClientSocket_deleteRef(connSock, &_throwaway_exception);}
  if(ser) {sidl_io_Serializable_deleteRef(ser, &_throwaway_exception);}
  return 0; /*false*/

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.initUnserialize) */
  }
}

/*
 *  return the short name of the protocol 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_getProtocol"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimHandle_getProtocol(
  /* in */ sidlx_rmi_SimHandle self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.getProtocol) */
  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);

  if (dptr) {
    return sidl_String_strdup(dptr->d_prefix);;
  }
  SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Simhandle has not been initialized");
 EXIT:
  return NULL;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.getProtocol) */
  }
}

/*
 *  return the object ID for the remote object
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_getObjectID"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimHandle_getObjectID(
  /* in */ sidlx_rmi_SimHandle self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.getObjectID) */
  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);

  if (dptr) {
    return sidl_String_strdup(dptr->d_objectID);
  }
  SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Simhandle has not been initialized");
 EXIT:
  return NULL;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.getObjectID) */
  }
}

/*
 *  
 * return the full URL for this object, takes the form: 
 * protocol://serviceID/objectID (where serviceID would = server:port 
 * on TCP/IP)
 * So usually, like this: protocol://server:port/objectID
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_getObjectURL"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimHandle_getObjectURL(
  /* in */ sidlx_rmi_SimHandle self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.getObjectURL) */
  int len = 0;
  char * url = NULL;
  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);

  if (dptr) {
    if(dptr->d_port > 65536) {
      SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, 
		 "Simhandle.getURL: port number is too large!");
    }
    len = sidl_String_strlen(dptr->d_prefix) + 3 + 
      sidl_String_strlen(dptr->d_server) + 1 + 6 /*maximum port length*/  
      + 1 + sidl_String_strlen(dptr->d_objectID) + 1; 
    /* FORMAT: prefix://server:port/typename/objectID*/
    
    url = sidl_String_alloc(len);
    sprintf(url, "%s://%s:%d/%s",dptr->d_prefix, 
	    dptr->d_server, dptr->d_port, dptr->d_objectID);
    
    return url;
  }
  SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Simhandle has not been initialized");
 EXIT:
  return NULL;
  
  
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.getObjectURL) */
  }
}

/*
 *  create a serializer handle to invoke the named method 
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_createInvocation"

#ifdef __cplusplus
extern "C"
#endif
sidl_rmi_Invocation
impl_sidlx_rmi_SimHandle_createInvocation(
  /* in */ sidlx_rmi_SimHandle self,
  /* in */ const char* methodName,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.createInvocation) */
  /* This function prepares one to make function calls over the network*/
  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);

  if (dptr) {
    sidl_rmi_Invocation ret = NULL;
    sidlx_rmi_ClientSocket connSock = NULL;
    sidlx_rmi_Socket locSock= NULL;
    sidlx_rmi_Simvocation obj = sidlx_rmi_Simvocation__create(_ex); SIDL_CHECK(*_ex);

    /* Just a bool so we can tell if RMI's have been made.  Not thread-safe. */
    s_callsMade = TRUE;

    /*
     * TODO: THIS IS A BAD PLACE TO MAKE THE CONNECTION, IF SERIALIZATION FAILS, IT'S
     * DIFFICULT TO TELL THE SERVER THAT!  (WE need some way to tell the server 
     * serialization failed I guess.)
     *
     * Here where we make a connection to the ORB
     */
    connSock = sidlx_rmi_ClientSocket__create(_ex); SIDL_CHECK(*_ex);
    sidlx_rmi_ClientSocket_init(connSock, dptr->d_IP, dptr->d_port,_ex);SIDL_CHECK(*_ex);
    locSock = sidlx_rmi_Socket__cast(connSock, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Simvocation_initInvocation(obj, methodName, dptr->d_objectID, 
			       locSock, s_cookie, s_cookieLen, _ex); SIDL_CHECK(*_ex);
    ret = sidl_rmi_Invocation__cast(obj, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Simvocation_deleteRef(obj, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Socket_deleteRef(locSock, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_ClientSocket_deleteRef(connSock, _ex); SIDL_CHECK(*_ex);
    return ret;
  }
  SIDL_THROW(*_ex, sidlx_rmi_UnrecoverableException, "Simhandle has not been initialized");
 EXIT:
  return NULL;

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.createInvocation) */
  }
}

/*
 *  
 * closes the connection (called by the destructor, if not done
 * explicitly) returns true if successful, false otherwise
 * (including subsequent calls)
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimHandle_close"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimHandle_close(
  /* in */ sidlx_rmi_SimHandle self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimHandle.close) */
  sidl_BaseInterface _throwaway_exception = NULL;
  struct sidlx_rmi_SimHandle__data *dptr =
    sidlx_rmi_SimHandle__get_data(self);


  /* Make sure that dptr exists, and the object has been init'd*/
  if(dptr && dptr->d_server) {
    /* Remote deleteRef the object we were connected to */
    sidlx_rmi_Simvocation obj = NULL; 
    sidlx_rmi_Socket locSock = NULL;
    sidlx_rmi_ClientSocket connSock = NULL;
    sidl_rmi_Response resp = NULL;

    obj = sidlx_rmi_Simvocation__create(_ex); SIDL_CHECK(*_ex);
    connSock = sidlx_rmi_ClientSocket__create(_ex); SIDL_CHECK(*_ex);
    sidlx_rmi_ClientSocket_init(connSock, dptr->d_IP, dptr->d_port,_ex);SIDL_CHECK(*_ex);
    locSock = sidlx_rmi_Socket__cast(connSock, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Simvocation_initInvocation(obj, "deleteRef", dptr->d_objectID, 
			       locSock, s_cookie, s_cookieLen, _ex); SIDL_CHECK(*_ex);
    resp = sidlx_rmi_Simvocation_invokeMethod(obj, _ex); SIDL_CHECK(*_ex);
    
    sidl_rmi_Response_deleteRef(resp, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Simvocation_deleteRef(obj, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_Socket_deleteRef(locSock, _ex); SIDL_CHECK(*_ex);
    sidlx_rmi_ClientSocket_deleteRef(connSock, _ex); SIDL_CHECK(*_ex);
 
    return 1; /*true*/

 EXIT:
    if(resp) { sidl_rmi_Response_deleteRef(resp, &_throwaway_exception);}
    if(obj) { sidlx_rmi_Simvocation_deleteRef(obj, &_throwaway_exception);}
    if(locSock) { sidlx_rmi_Socket_deleteRef(locSock, &_throwaway_exception);}
    if(connSock) { sidlx_rmi_ClientSocket_deleteRef(connSock, &_throwaway_exception); }
    return 0; /*false*/
    
  }  
  return 0; /*false*/

    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimHandle.close) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

