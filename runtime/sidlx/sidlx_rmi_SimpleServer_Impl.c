/*
 * File:          sidlx_rmi_SimpleServer_Impl.c
 * Symbol:        sidlx.rmi.SimpleServer-v0.1
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Description:   Server-side implementation for sidlx.rmi.SimpleServer
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidlx.rmi.SimpleServer" (version 0.1)
 * 
 * A multi-threaded base class for simple network servers.
 * 
 * This server takes the following flags:
 * 1: verbose output (to stdout)
 */

#include "sidlx_rmi_SimpleServer_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer._includes) */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <netdb.h>
#include <errno.h>
#include <string.h>
#include <sys/param.h>
#include "sidlx_common.h"
#include "sidlx_rmi_Common.h"
#include "sidl_rmi_NetworkException.h"
#include "sidl_MemAllocException.h"
#include "sidl_String.h"
#include "sidlx_rmi_Statistics.h"

#include <pthread.h>

#define MAXLINE 1023
#define MAX_THREADS 1024
#define DEBUG 0

/* NOTE:  
 *       
 * the "g_" prefix is used to indicate global 
 * context that must be locked first
 */

static pthread_mutex_t g_poolLock = PTHREAD_MUTEX_INITIALIZER;
static pthread_cond_t  g_poolCond = PTHREAD_COND_INITIALIZER;

static pthread_cond_t  g_serverWait = PTHREAD_COND_INITIALIZER;
static sidl_bool g_serverRunning = FALSE; /* True when the server is running */


static sidl_bool g_shutdownPool = FALSE; /* flag to trigger shutdown */
static sidl_bool g_haveWork = FALSE; /* flag to indicate additional work 
				        available on g_server and g_socket */
static int g_nPool = 0;  /* number of worker threads in pool */
static int g_nBusy = 0;  /* number of threads busy */
/*static int g_maxPool = 1024;  max number of threads in pool */
/* 
 * the following two are storage for serverFunc() to transfer 
 * information to threadFunc() 
 */
static sidlx_rmi_SimpleServer g_server = NULL;
static sidlx_rmi_Socket g_socket = NULL;

/**
 * This function is executed by child threads dealing with server connections.
 * It just runs the simpleServer serviceRequest, and cleans up after itself on exit.
 *
 * param:
 * in int arg A single integer that serves as an ad-hoc child thread ID.
 */
static void * threadFunc(void *arg) {
  sidlx_rmi_SimpleServer self = NULL;
  sidlx_rmi_Socket ac_sock = NULL;

  sidl_BaseInterface _ex = NULL;
  sidl_BaseInterface _ex2 = NULL;
  sidl_SIDLException e_x_p = NULL;
  int threadId = (int) arg;

  if (DEBUG) {
    fprintf(stdout, "Starting a child thread: %d\n", threadId);
    fflush(stdout);
  }

  while(1) {
    /* Try to grab some work */
    pthread_mutex_lock(&g_poolLock);

    while(! g_haveWork && !g_shutdownPool) {
      pthread_cond_wait(&g_poolCond,&g_poolLock);
    }
    if ( g_shutdownPool ) { 
      if (DEBUG) {
        printf("Shutting down a child thread\n");
        fflush(stdout);
      }
      g_nPool--;
      break;
    }

    g_nBusy++;                    /* Mark a busy thread */
    self = g_server;              /* grab work specified by serverFunc() */
    ac_sock = g_socket;           /* grab work specified by serverFunc() */
    g_socket = NULL;
    g_haveWork = FALSE;           /* Mark work taken */
    pthread_cond_broadcast(&g_poolCond);
    pthread_mutex_unlock(&g_poolLock);

    sidlx_rmi_SimpleServer_serviceRequest( self, ac_sock, &_ex );
    SIDL_CHECK(_ex);

    sidlx_rmi_Socket_deleteRef(ac_sock,&_ex); SIDL_CHECK(_ex);

    /* Mark as not busy anymore */
    pthread_mutex_lock(&g_poolLock);
    g_nBusy--;
    pthread_mutex_unlock(&g_poolLock);

  } /* end while(1) */
  pthread_cond_broadcast(&g_poolCond);
  pthread_mutex_unlock(&g_poolLock);

  return NULL;
 EXIT:
  /* Assume that only coming here when busy and lock is unlocked */
  /* because that is the only place we have SIDL_CHECK() */

  pthread_mutex_lock(&g_poolLock);
  g_nBusy--;
  g_nPool--;
  pthread_mutex_unlock(&g_poolLock);

  /* Don't do SIDL_CHECK HERE!!! */
  sidlx_rmi_Socket_deleteRef(ac_sock,&_ex2); 
  e_x_p = sidl_SIDLException__cast(_ex,&_ex2);
  printf( "Failure in threadFunc! %s\n", 
          sidl_SIDLException_getNote(e_x_p,&_ex2));
  printf( "%s\n*******\n",sidl_SIDLException_getTrace(e_x_p,&_ex2));
  SIDL_CLEAR(_ex);
  return NULL;
}

/**
 * This function is executed by the thread that acts as server, accepting
 * connections and spawning children to deal with them.  It does not 
 * detatch itself because the main thread may need to join to it to prevent
 * premature exit.
 * It is also designed to work in a nonthreaded enviornment, but I haven't
 * tested that.
 *
 */
static void * serverFunc(void *arg) {

  sidlx_rmi_ServerSocket serverSocket = NULL;
  sidlx_rmi_Socket ac_sock = NULL;
  sidl_BaseInterface _ex = NULL;
  sidl_BaseInterface _ex2 = NULL;
  pthread_t childThreads[MAX_THREADS];
  int ii;
  sidlx_rmi_SimpleServer self = (sidlx_rmi_SimpleServer)arg;

  struct sidlx_rmi_SimpleServer__data *dptr=sidlx_rmi_SimpleServer__get_data(self);
  if(!dptr || !dptr->s_sock) {
    SIDL_THROW(_ex, sidl_rmi_NetworkException,"Simple Server not initialized");
  }

  if(DEBUG) {
    fprintf(stdout, "Starting the server thread\n");
    fflush(stdout);
  }

  g_serverRunning = TRUE;

  /* Now that we know we'll be using this, addRef it */
  serverSocket = dptr->s_sock;
  sidlx_rmi_ServerSocket_addRef(serverSocket, &_ex);

  for(ii = 0; ii < MAX_THREADS; ++ii) {
    childThreads[ii] = 0;
  }

  while (1) { 
    pthread_t tid = 0;

    ac_sock = sidlx_rmi_ServerSocket_accept(serverSocket, &_ex); SIDL_CHECK(_ex);  

    pthread_mutex_lock(&g_poolLock);
    if (g_shutdownPool) {      
      pthread_cond_broadcast(&g_poolCond);
      pthread_mutex_unlock(&g_poolLock);
      if (DEBUG) {
        printf("Have a connection, but we shouldn't service it!\n");
        fflush(stdout);
      }
      goto EXIT;
    }
    if ( (g_nBusy == g_nPool) && (g_nPool < MAX_THREADS) ) {
      pthread_create(&tid,0,threadFunc,(void*)g_nPool);
      if(tid) {
	childThreads[g_nPool] = tid;
        ++g_nPool;
      }
    }
    
    /* Make sure we don't have pending work */
    while(g_haveWork) {
      pthread_cond_wait(&g_poolCond,&g_poolLock);
    }
    /* Assign new work */
    g_haveWork = TRUE;
    g_server = self;
    g_socket = ac_sock;
    ac_sock = NULL;
    pthread_cond_broadcast(&g_poolCond);
    pthread_mutex_unlock(&g_poolLock);
  }
  pthread_mutex_unlock(&g_poolLock);
  return NULL;  /* See if we have any bored workers */
 EXIT:
  if (ac_sock) {
    sidlx_rmi_Socket_close(ac_sock,&_ex2);
    ac_sock = 0;
    SIDL_CLEAR(_ex2);
  }
  if (serverSocket) {
    sidlx_rmi_ServerSocket_deleteRef(serverSocket,&_ex2);
    SIDL_CLEAR(_ex2);
  }
  if (self) {
    sidlx_rmi_SimpleServer_deleteRef(self,&_ex2);
    SIDL_CLEAR(_ex2);
  }
  if (g_shutdownPool) {
    if (DEBUG) {
      fprintf(stdout, "Shutting down server thread cleanly, waiting on %d children\n", g_nPool);
      fflush(stdout);
    }
   for(ii = 0; ii < g_nPool; ++ii) {
      if(childThreads[ii] != 0) {
	pthread_join(childThreads[ii], NULL);
      }
    }
   if (DEBUG) {
     fprintf(stdout, "Child threads all returned, shutting down.\n");
     fflush(stdout);
   }
 } else {
    int error_return;
    sidl_rmi_NetworkException netEx = sidl_rmi_NetworkException__cast(_ex, &_ex2);
    printf( "Server not shutting down cleanly, not waiting on children\n");
    if(netEx) {
      error_return = sidl_rmi_NetworkException_getErrno(netEx,&_ex2);
      printf( "Network Exception caught in serverFunc! %s\n", 
              sidl_rmi_NetworkException_getNote(netEx,&_ex2));
      printf( "Hop count: %d, errno: %d, errno string: %s",
              sidl_rmi_NetworkException_getHopCount(netEx,&_ex2),
              error_return, strerror(error_return));
      printf( "Stack Trace:\n%s\n**************************\n",
              sidl_rmi_NetworkException_getTrace(netEx,&_ex2));
    } else {
      sidl_BaseException baseEx = sidl_BaseException__cast(_ex, &_ex2);
      error_return = -1;
      if(baseEx) {
        printf( "Exception caught in serverFunc! %s\n", 
                sidl_BaseException_getNote(baseEx,&_ex2));
        printf( "%s\n*******\n",sidl_BaseException_getTrace(baseEx,&_ex2));
      } else {
        printf( 
                "Exception caught in serverFunc! Not a sidl.BaseException.\n");
      }
    }
    printf( "Statistics on server performance:\n");
    printf("Total Accept requests: %d\n", 
	   (int)sidlx_rmi_Statistics_getTotalAcceptRequests(&_ex2));
    printf("Total Acception successes: %d\n", 
	   (int)sidlx_rmi_Statistics_getTotalAcceptSucceded(&_ex2));
    printf("Total Acceptions that succeeded on the first try: %d\n", 
	   (int)sidlx_rmi_Statistics_getTotalAcceptsFirstTry(&_ex2));
    printf("Average Acception Retries: %d\n", 
	   (int)sidlx_rmi_Statistics_getAvgAcceptRetries(&_ex2));
    exit(error_return);
  }
  SIDL_CLEAR(_ex);

  /* Announce the death of the serverThread*/
  pthread_mutex_lock(&g_poolLock);
  g_serverRunning = FALSE;
  pthread_mutex_unlock(&g_poolLock);
  pthread_cond_broadcast(&g_serverWait);


  if (DEBUG) {
     fprintf(stdout, "Server thread has shutdown\n");
     fflush(stdout);
  }

  return NULL;  
}


/* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer__ctor(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer._ctor) */
  struct sidlx_rmi_SimpleServer__data *dptr;
  char name[MAXHOSTNAMELEN]; /*= sidl_String_alloc(MAXHOSTNAMELEN);*/

  dptr = malloc(sizeof(struct sidlx_rmi_SimpleServer__data));
  if(NULL == dptr) {
    sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
    sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
    sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidlx.rmi.SimpleServer._ctor", _ex);
    *_ex = (sidl_BaseInterface)ex;
    goto EXIT;
  }

  sidlx_rmi_SimpleServer__set_data(self, dptr);
  dptr->s_sock = sidlx_rmi_ServerSocket__create(_ex); SIDL_CHECK(*_ex);
  if(gethostname(name, MAXHOSTNAMELEN) == 0) { /*Returns 0 on success*/
    dptr->d_hostname =  sidlx_rmi_Common_getCanonicalName(name, _ex); SIDL_CHECK(*_ex);
  } else { 
    dptr->d_hostname = NULL;
  }
  dptr->d_port = -1;
  dptr->d_flags = 0;
 EXIT:
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer__ctor2(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer._ctor2) */
  /* Insert-Code-Here {sidlx.rmi.SimpleServer._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer__dtor(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer._dtor) */
  struct sidlx_rmi_SimpleServer__data * data = sidlx_rmi_SimpleServer__get_data( self );
  if (data) {
    if(data->s_sock) { sidlx_rmi_ServerSocket_deleteRef(data->s_sock, _ex); }
    if(data->d_hostname) { 
      sidl_String_free( data->d_hostname );
      data->d_hostname=NULL;
    }
  free((void*) data);
  }
  sidlx_rmi_SimpleServer__set_data( self, NULL );
  return;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer._dtor) */
  }
}

/*
 * Set the maximum size of the client thread pool.
 * (default = 1024)
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_setMaxThreadPool"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer_setMaxThreadPool(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ int32_t max,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.setMaxThreadPool) */
    /*  pthread_mutex_lock(&g_poolLock);
	g_maxPool = max;
	pthread_mutex_unlock(&g_poolLock);*/
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.setMaxThreadPool) */
  }
}

/*
 * request a specific port number
 * returns true iff request is satisfied
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_requestPort"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimpleServer_requestPort(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ int32_t port,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.requestPort) */
  struct sidlx_rmi_SimpleServer__data *data=sidlx_rmi_SimpleServer__get_data(self);
  if(data) {
    int32_t n = sidlx_rmi_ServerSocket_init(data->s_sock, port, _ex); SIDL_CHECK(*_ex);
    if(n < 0) {
      return FALSE;
    }
    data->d_port = port;
    return TRUE;
  }
  return FALSE;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.requestPort) */
  }
}

/*
 * Request the minimum available port in 
 * a range.  Returns true iff request is satisfied
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_requestPortInRange"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimpleServer_requestPortInRange(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ int32_t minport,
  /* in */ int32_t maxport,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.requestPortInRange) */
    sidl_bool found = FALSE;
    
    struct sidlx_rmi_SimpleServer__data *data=sidlx_rmi_SimpleServer__get_data(self);
    if(data) {
      int32_t port;
      for(port = minport; port<=maxport && !found; port++) {
        int result = impl_sidlx_rmi_SimpleServer_requestPort(self, port, _ex); SIDL_CHECK(*_ex);
        if(!result) {
          if(port < maxport) {
	    continue;
	  } else {
            return FALSE;
	  }
        }
        data->d_port = port;
        return TRUE;
      }
    }
  EXIT:
    return FALSE;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.requestPortInRange) */
  }
}

/*
 * Request a specific port number.
 * Accepts only local connections.
 * Returns true iff request is satisfied.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_requestLocalPort"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimpleServer_requestLocalPort(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ int32_t port,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.requestLocalPort) */
    struct sidlx_rmi_SimpleServer__data *data=sidlx_rmi_SimpleServer__get_data(self);
    if(data) {
      int32_t n = sidlx_rmi_ServerSocket_initLocal(data->s_sock, port, TRUE,  _ex); SIDL_CHECK(*_ex);
      if(n < 0) {
        return FALSE;
      }
      data->d_port = port;

      /* make sure to use "localhost" as the hostname so clients will connect
         via the loopback device
      */
      data->d_hostname = "localhost";
      return TRUE;
    }
    return FALSE;
  EXIT:
    return FALSE;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.requestLocalPort) */
  }
}

/*
 * Request the minimum available port in 
 * a range.  Returns true iff request is satisfied.
 * Accepts only local connections.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_requestLocalPortInRange"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimpleServer_requestLocalPortInRange(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ int32_t minport,
  /* in */ int32_t maxport,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.requestLocalPortInRange) */
    sidl_bool found = FALSE;
    
    struct sidlx_rmi_SimpleServer__data *data=sidlx_rmi_SimpleServer__get_data(self);
    if(data) {
      int32_t port;
      for(port = minport; port<=maxport && !found; port++) {
        int result = impl_sidlx_rmi_SimpleServer_requestLocalPort(self, port, _ex); SIDL_CHECK(*_ex);
        if(!result) {
          if(port < maxport) {
	    continue;
	  } else {
            return FALSE;
	  }
        }
        data->d_port = port;
        return TRUE;
      }
    }
  EXIT:
    return FALSE;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.requestLocalPortInRange) */
  }
}

/*
 * get the port that this Server is bound to
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_getPort"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidlx_rmi_SimpleServer_getPort(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.getPort) */
  struct sidlx_rmi_SimpleServer__data *dptr=sidlx_rmi_SimpleServer__get_data(self);
  
  if(dptr) {
    return dptr->d_port;
  }
  return 0;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.getPort) */
  }
}

/*
 * get the network name of this computer
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_getServerName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimpleServer_getServerName(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.getServerName) */
  struct sidlx_rmi_SimpleServer__data *dptr = sidlx_rmi_SimpleServer__get_data(self);
  if ( dptr && dptr->d_hostname ) { 
    return sidl_String_strdup( dptr->d_hostname );
  } 
  return NULL;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.getServerName) */
  }
}

/*
 * get the full URL for exporting objects
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_getServerURL"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidlx_rmi_SimpleServer_getServerURL(
  /* in */ sidlx_rmi_SimpleServer self,
  /* in */ const char* objID,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.getServerURL) */
  /* TODO: Is there anything we can do here?*/
  return NULL;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.getServerURL) */
  }
}

/*
 * run the server (must have port specified first), if a threaded server,
 * returns the tid of the server thread for joining.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_run"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidlx_rmi_SimpleServer_run(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.run) */
  pthread_t tid = 0;

  /* For threads, increment the reference count before passing it in.
     To avoid a race where the object might be accidentally colllected.*/
  sidlx_rmi_SimpleServer_addRef(self, _ex); SIDL_CHECK(*_ex);
  pthread_create(&tid, NULL, (void * (*)(void *))&serverFunc, (void*)self);
  return (int64_t)tid;
 EXIT:
  return 0;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.run) */
  }
}

/*
 * Cleanly shutdown the orb.  Method blocks until orb is fully shutdown.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_shutdown"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer_shutdown(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.shutdown) */
    struct sidlx_rmi_SimpleServer__data *dptr=sidlx_rmi_SimpleServer__get_data(self);
    
    if (DEBUG) {
      fprintf(stdout, "Shutdown: Starting shutdown.\n");
      fflush(stdout);
    }

    /* We close the socket so the server will not accept any more messages */
    pthread_mutex_lock(&g_poolLock);

    if(!g_serverRunning) {
      pthread_mutex_unlock(&g_poolLock);
      goto EXIT; /* Nothing to do */
    }
    g_shutdownPool = TRUE;
    
    if (dptr && dptr->s_sock) {
      sidlx_rmi_ServerSocket_close(dptr->s_sock,_ex);
      SIDL_CHECK(*_ex);
      sidlx_rmi_ServerSocket_deleteRef(dptr->s_sock, _ex);
      dptr->s_sock = 0;
    }
    pthread_mutex_unlock(&g_poolLock);
    pthread_cond_broadcast(&g_poolCond);
    
    /* Since we can't join on the server thread (it was passed back to the user
     *  Emulate joining by waiting on this condition variable.*/

    if (DEBUG) {
     fprintf(stdout, "Shutdown: Waiting for server thread to shutdown\n");
     fflush(stdout);
    }
    pthread_mutex_lock(&g_poolLock);
    while(g_serverRunning) {
      pthread_cond_wait(&g_serverWait, &g_poolLock);
    }
    pthread_mutex_unlock(&g_poolLock);
    if (DEBUG) {
     fprintf(stdout, "Shutdown: Server thread has shutdown\n");
     fflush(stdout);
    }
    
  EXIT:;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.shutdown) */
  }
}

/*
 * Starts the shutodwn process, but does not wait for shutdown to 
 * complete before returning.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_shutdownNoWait"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidlx_rmi_SimpleServer_shutdownNoWait(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.shutdownNoWait) */
    struct sidlx_rmi_SimpleServer__data *dptr=sidlx_rmi_SimpleServer__get_data(self);
    
    /* We close the socket so the server will not accept any more messages */
    pthread_mutex_lock(&g_poolLock);
    g_shutdownPool = TRUE;
    if(!g_serverRunning) {
      pthread_mutex_unlock(&g_poolLock);
      goto EXIT; /* Nothing to do */
    }
    
    if (dptr && dptr->s_sock) {
      sidlx_rmi_ServerSocket_close(dptr->s_sock,_ex);
      SIDL_CHECK(*_ex);
      sidlx_rmi_ServerSocket_deleteRef(dptr->s_sock, _ex);
      dptr->s_sock = 0;
    }
    pthread_mutex_unlock(&g_poolLock);
    pthread_cond_broadcast(&g_poolCond);

  EXIT:;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.shutdownNoWait) */
  }
}

/*
 * Check if the ORB is running.  NOT REALLY THREAD SAFE.  DON'T TRY TO USE
 * THIS WHEN THREAD SAFETY IS AN ISSUE.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidlx_rmi_SimpleServer_isRunning"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidlx_rmi_SimpleServer_isRunning(
  /* in */ sidlx_rmi_SimpleServer self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidlx.rmi.SimpleServer.isRunning) */
    sidl_bool retval = TRUE;
    pthread_mutex_lock(&g_poolLock);
    retval = g_serverRunning;
    pthread_mutex_unlock(&g_poolLock);
    return retval;
    /* DO-NOT-DELETE splicer.end(sidlx.rmi.SimpleServer.isRunning) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

