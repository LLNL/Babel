
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include "sidlx_common.h"
#include "sidl_String.h"

#include "sidl_DLL.h"
#include "sidl_Resolve.h"
#include "sidl_Scope.h"
#include "sidl_Loader.h"

#include "sidl_rmi_NetworkException.h"
#include "sidl_rmi_MalformedURLException.h"
#include "sidl_rmi_ObjectDoesNotExistException.h"
#include "sidl_io_IOException.h"

#include "sidlx_rmi_Settings.h"
#include "sidlx_rmi_OutOfAddressesException.h"
#include "sidlx_rmi_NotEnoughMemoryException.h"
#include "sidlx_rmi_TooManyOpenFilesException.h"
#include "sidlx_rmi_RetryException.h"
#include "sidlx_rmi_TimeoutException.h"

#include "sidlx_rmi_ConnectionRefusedException.h"
#include "sidlx_rmi_BadFileDescriptorException.h"
#include "sidlx_rmi_NetworkUnreachableException.h"
#include "sidlx_rmi_ConnectionResetException.h"
#include "sidlx_rmi_UnexpectedCloseException.h"
#include "sidlx_rmi_UnrecognizedNetworkException.h"
#include "sidlx_rmi_RecoverableException.h"

#include <limits.h>

static struct sidlx_stats my_stats = {0,0,0,0,0,0,0,0,0,0};

struct sidlx_stats * get_sidlx_stats_struct(void) { 
  return &my_stats;
}


void sidlx_throwException(const int errval, sidl_BaseInterface* _ex) {
  const char * const errmsg = strerror(errval);
  char *note = NULL;
  sidl_BaseInterface _throwaway_exception=NULL;    

  switch(errval) 
    {
    case ENOMEM:
    case ENOBUFS:
      {
	sidlx_rmi_NotEnoughMemoryException tmpEx = 
	  sidlx_rmi_NotEnoughMemoryException__create(&_throwaway_exception);
	sidlx_rmi_NotEnoughMemoryException_setNote(tmpEx, "Cannot allocate enough memory in the Kernel to complete the operation", &_throwaway_exception);
	sidlx_rmi_NotEnoughMemoryException_setErrno(tmpEx,errval, &_throwaway_exception);
	sidlx_rmi_NotEnoughMemoryException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case EADDRNOTAVAIL:
      {
	sidlx_rmi_OutOfAddressesException tmpEx = 
	  sidlx_rmi_OutOfAddressesException__create(&_throwaway_exception);
        note = sidl_String_concat2
          ("We have run out of addresses to assign to new connections: ",
           errmsg);
	sidlx_rmi_OutOfAddressesException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_OutOfAddressesException_setErrno(tmpEx,errval, &_throwaway_exception);
	sidlx_rmi_OutOfAddressesException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }
      
    case ENFILE:
    case EMFILE:
      {
	sidlx_rmi_TooManyOpenFilesException tmpEx = 
	  sidlx_rmi_TooManyOpenFilesException__create(&_throwaway_exception);
        note = sidl_String_concat2
          ("There are too many files open to complete the operation: ",
           errmsg);
	sidlx_rmi_TooManyOpenFilesException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_TooManyOpenFilesException_setErrno(tmpEx,errval, &_throwaway_exception);
	sidlx_rmi_TooManyOpenFilesException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case ECONNABORTED:
    case EAGAIN:
    case EINTR:
      {
	sidlx_rmi_RetryException tmpEx = 
	  sidlx_rmi_RetryException__create(&_throwaway_exception);
        note = sidl_String_concat2("Call was interrupted, try again: ", 
                                   errmsg);
	sidlx_rmi_RetryException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_RetryException_setErrno(tmpEx,errval, &_throwaway_exception);
	sidlx_rmi_RetryException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case ETIMEDOUT:
#ifdef ETIME
    case ETIME:
#endif
      {
	sidlx_rmi_TimeoutException tmpEx = 
	  sidlx_rmi_TimeoutException__create(&_throwaway_exception);
        note = sidl_String_concat2("Communication Timed out, restart and try again: ", errmsg);
	sidlx_rmi_TimeoutException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_TimeoutException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_TimeoutException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case ECONNREFUSED:
      {
	sidlx_rmi_ConnectionRefusedException tmpEx = 
	  sidlx_rmi_ConnectionRefusedException__create(&_throwaway_exception);
        note = sidl_String_concat2("The connection was refused, host is not listening: ", errmsg);
	sidlx_rmi_ConnectionRefusedException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_ConnectionRefusedException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_ConnectionRefusedException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case ENETUNREACH:
    case EHOSTUNREACH:
    case EHOSTDOWN:
      {
	sidlx_rmi_NetworkUnreachableException tmpEx = 
	  sidlx_rmi_NetworkUnreachableException__create(&_throwaway_exception);
        note = sidl_String_concat2("Fatal Error, network or host unreachable: ", errmsg);
	sidlx_rmi_NetworkUnreachableException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_NetworkUnreachableException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_NetworkUnreachableException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case ENOTCONN:
    case ENOLINK:
    case EPIPE:
      {
	sidlx_rmi_UnexpectedCloseException tmpEx = 
	  sidlx_rmi_UnexpectedCloseException__create(&_throwaway_exception);
        note = sidl_String_concat2("The connection was unexpectedly aborted: ",
                                   errmsg);
	sidlx_rmi_UnexpectedCloseException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_UnexpectedCloseException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_UnexpectedCloseException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case ENETRESET:
    case ECONNRESET:
      {
	sidlx_rmi_ConnectionResetException tmpEx = 
	  sidlx_rmi_ConnectionResetException__create(&_throwaway_exception);
        note = sidl_String_concat2
          ("The connection was reset by software or peer: ", errmsg);
	sidlx_rmi_ConnectionResetException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_ConnectionResetException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_ConnectionResetException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    case EINVAL:
#ifdef EBADFD
    case EBADFD:
#endif
      {
	sidlx_rmi_BadFileDescriptorException tmpEx = 
	  sidlx_rmi_BadFileDescriptorException__create(&_throwaway_exception);
        note = sidl_String_concat2("Fatal Error, bad file descriptor: ",
                                   errmsg);
	sidlx_rmi_BadFileDescriptorException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_BadFileDescriptorException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_BadFileDescriptorException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }

    default:
      {
	sidlx_rmi_UnrecognizedNetworkException tmpEx = 
	  sidlx_rmi_UnrecognizedNetworkException__create(&_throwaway_exception);        note = sidl_String_concat2
                                                                                          ("Fatal Error, unexpected and unrecognized error: ", errmsg);
	sidlx_rmi_UnrecognizedNetworkException_setNote(tmpEx, note, &_throwaway_exception);
	sidlx_rmi_UnrecognizedNetworkException_setErrno(tmpEx, errval, &_throwaway_exception);
	sidlx_rmi_UnrecognizedNetworkException_add(tmpEx, __FILE__, __LINE__, __FUNC__, &_throwaway_exception);
	*_ex = (sidl_BaseInterface) tmpEx;
	break;
      }
    }
  if (note) sidl_String_free(note);
  return;
}


sidl_BaseClass sidlx_createClass(const char* objName, sidl_BaseInterface* _ex) {
  sidl_BaseInterface _throwaway_exception = NULL; /*(TODO: a way to not throw these away? */
  sidl_BaseClass baseClass = NULL;

  void* (*_locCreateObject)(void*,sidl_BaseInterface*);
  sidl_DLL dll = sidl_DLL__create(_ex); 

  /* HMMM, would it be faster or better to do this directly rather than using sidl_DLL_createClass?*/

  /* Holds the name for the object creation call. objName+__createObject */
  /*  int objNameLength = strlen(objName);
  char* createObjName = malloc( objNameLength + 24);
  char* createObjName_ptr = createObjName;
  int ii;

  for(ii = 0; ii < objNameLength; ++ii) {
    if(objName[ii] == '.') {
      createObjName[ii] = '_';
    } else {
      createObjName[ii] = objName[ii];
    }
  }
  memcpy(createObjName+ii, "__createObject", 15);
  */
  /* check global namespace for symbol first */
  if (dll && sidl_DLL_loadLibrary(dll, "main:", TRUE, FALSE,&_throwaway_exception)) {
    baseClass = sidl_DLL_createClass(dll, objName,_ex); SIDL_CHECK(*_ex); 

      /*_locCreateObject =
        (  void*(*)(void)) sidl_DLL_lookupSymbol(
        dll, createObjName,_ex); SIDL_CHECK(*_ex);*/
        /*_locCreateObject = (dll_f ? (*dll_f)() : NULL);*/
  }
  if (dll) sidl_DLL_deleteRef(dll,_ex); SIDL_CHECK(*_ex);
  if (!baseClass) {
    dll = sidl_Loader_findLibrary(objName,
				  "ior/impl", sidl_Scope_SCLSCOPE,
				  sidl_Resolve_SCLRESOLVE,_ex); SIDL_CHECK(*_ex);
    if (dll) {
      baseClass = sidl_DLL_createClass(dll, objName,_ex); SIDL_CHECK(*_ex); 
      /*_locCreateObject =
        (  void*(*)(void)) sidl_DLL_lookupSymbol( 
        dll, createObjName,_ex); SIDL_CHECK(*_ex);*/
      /*      _locCreateObject = (dll_f ? (*dll_f)() : NULL);*/
      sidl_DLL_deleteRef(dll,_ex); SIDL_CHECK(*_ex);
    }
  }
  if (!baseClass) {
    char ex_msg[1024];      
    sprintf(ex_msg, 
            "sidlx_createObject: Unable to load DLL for class %s. check SIDL_DLL_PATH.", 
            objName);
    SIDL_THROW(*_ex, sidl_rmi_ObjectDoesNotExistException, 
               ex_msg);
  }
  
  /*create class and return sidl_BaseClass */
  /*  baseClass = (*_locCreateObject)(NULL,_ex); SIDL_CHECK(*_ex);*/
  return baseClass;
  
 EXIT:
  return NULL;
  
}


int s_socket( int family, int type, int protocol, sidl_BaseInterface *_ex) { 
  int n;
  
  /* socket() returns nonnegative descriptor if ok, -1 on error */
  if ((n=socket(family,type,protocol))< 0) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  }
 EXIT:
  return(n);
}

inline int s_bind( int sockfd, const struct sockaddr * myaddr, socklen_t addrlen) { 

  /* bind() returns 0 if ok, -1 on error */
  return bind(sockfd, myaddr, addrlen);
}


inline int s_listen( int sockfd, int backlog, sidl_BaseInterface *_ex) { 
  
  /* listen() returns 0 if ok, -1 on error */
  return listen(sockfd,backlog);
}

int s_accept( int sockfd, struct sockaddr *cliaddr, socklen_t *addrlen, 
	      sidl_BaseInterface *_ex ) { 
  
  int accept_retries;
  int64_t sidlSleep;
  unsigned long sleeptime;  
  int i;
  int n = -1;                   /* default to reporting failure */
  sidl_BaseInterface throwaway_exception;
  sidlx_rmi_RecoverableException recoverableEx= NULL;

  ++my_stats.totalAcceptsRequested;
   
  accept_retries = sidlx_rmi_Settings_getMaxAcceptRetries(_ex); SIDL_CHECK(*_ex);
  if (accept_retries < 0) accept_retries = 0;
  sidlSleep = sidlx_rmi_Settings_getAcceptRetryInitialSleep(_ex); SIDL_CHECK(*_ex);
  if (sidlSleep > 0) { 
#if SIZEOF_LONG >= 8
    sleeptime = (unsigned long)sidlSleep;
#else
    if ((sidlSleep >> 32) != 0) {
      sleeptime = ULONG_MAX;
    }
    else {
      sleeptime = (unsigned long)sidlSleep;
    }
#endif
  }
  else {
    sleeptime = 0;
  }

  /* 
   * NOTE: the <= in the for loop is intentional!
   * We want to go through this loop at least once!
   */
  for( i=0; i<=accept_retries; ++i ) {
    if ((n = accept(sockfd, cliaddr, addrlen)) > 0 ) { 
      /* success! connection created */
      if ( i == 0 ) { 
	++my_stats.totalAcceptsFirstTry;
      } else if ( i > my_stats.maxAcceptRetries ) { 
	my_stats.maxAcceptRetries = i;
      }
      ++my_stats.totalAcceptsGranted;
      return n;
    } else { 
      /* uh oh, is it worth trying again? */
      sidlx_throwException(errno, _ex );
      recoverableEx = sidlx_rmi_RecoverableException__cast(*_ex, &throwaway_exception);
      if ( recoverableEx == NULL ) {         
	/* nope, the exception is not recoverable */
	SIDL_CHECK(*_ex); /* Note: this should goto EXIT: */
      } else if ( i<accept_retries ) {
	/* yes, exception is recoverable and we can try again */
        /* We leak RecoverableException here */
	SIDL_CLEAR(*_ex);
	if ( sleeptime > ULONG_MAX/2 ) { break; } /* about to overflow unsigned long */
	++my_stats.totalAcceptRetries;
	sleeptime <<= 1 ; /* double amount of time */
	usleep(sleeptime);
      } 
    } 
  }

  /* if we got here, then accept failed */
  SIDL_CHECK(*_ex);
  if ( accept_retries <= 0 ) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  } else { 
    char buf[512];
    snprintf(buf, 512, "accept() error, even after %d retries", i );
    SIDL_THROW( *_ex, sidl_io_IOException, buf );
  }

 EXIT:
  return n;
}


int s_connect( int sockfd, const struct sockaddr *servaddr, socklen_t addrlen,
	       sidl_BaseInterface *_ex ) { 

  int connect_retries;
  int64_t sidlSleep;
  unsigned long sleeptime;  
  int i;
  int n = -1;                   /* default to reporting failure */
  sidl_BaseInterface throwaway_exception;
  sidlx_rmi_RecoverableException recoverableEx= NULL;

  ++my_stats.totalConnectsRequested;
   
  connect_retries = sidlx_rmi_Settings_getMaxConnectRetries(_ex); SIDL_CHECK(*_ex);
  if (connect_retries < 0) connect_retries = 0;
  sidlSleep = sidlx_rmi_Settings_getConnectRetryInitialSleep(_ex); SIDL_CHECK(*_ex);
  if (sidlSleep > 0) { 
#if SIZEOF_LONG >= 8
    sleeptime = (unsigned long)sidlSleep;
#else
    if ((sidlSleep >> 32) != 0) {
      sleeptime = ULONG_MAX;
    }
    else {
      sleeptime = (unsigned long)sidlSleep;
    }
#endif
  }
  else {
    sleeptime = 0;
  }

  /* 
   * NOTE: the <= in the for loop is intentional!
   * We want to go through this loop at least once!
   */
  for( i=0; i<=connect_retries; ++i ) {
    if ((n = connect( sockfd, servaddr, addrlen )) == 0 ) { 
      /* success! connection created */
      if ( i == 0 ) { 
	++my_stats.totalConnectsFirstTry;
      } else if ( i > my_stats.maxConnectRetries ) { 
	my_stats.maxConnectRetries = i;
      }
      ++my_stats.totalConnectsGranted;
      return 0;
    } else { 
      /* uh oh, is it worth trying again? */
      sidlx_throwException(errno, _ex );
      recoverableEx = sidlx_rmi_RecoverableException__cast(*_ex, &throwaway_exception);
      if ( recoverableEx == NULL ) {         
	/* nope, the exception is not recoverable */
	SIDL_CHECK(*_ex); /* Note: this should goto EXIT: */
      } else if ( i<connect_retries ) {
	/* yes, exception is recoverable and we can try again */
        /* We leak RecoverableException here */
	SIDL_CLEAR(*_ex);
	if ( sleeptime > ULONG_MAX/2 ) { break; } /* about to overflow unsigned long */
	++my_stats.totalConnectRetries;
	sleeptime <<= 1 ; /* double amount of time */
	usleep(sleeptime);
      } 
    } 
  }

  /* if we got here, then connect failed */
  SIDL_CHECK(*_ex);
  if ( connect_retries <= 0 ) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  } else { 
    char buf[512];
    snprintf(buf, 512, "connect() error, even after %d retries", i );
    SIDL_THROW( *_ex, sidl_io_IOException, buf );
  }

 EXIT:
  return n;
}


pid_t s_fork( sidl_BaseInterface *_ex ) { 
  
  pid_t pid;
  
  /* returns 0 in child, PID in parent, -1 on error */
  if ((pid=fork()) < 0) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  }
 EXIT:
  return pid;
}


int s_close( int sockfd, sidl_BaseInterface *_ex) { 
  int n;
  
  /* returns 0 if okay, -1 on error */
  if ((n=close(sockfd)) < 0 ) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  }
 EXIT:
  return n;
}


/* same as getsockname(), but using sidl exceptions */
int s_getsockname( int sockfd, struct sockaddr *localaddr, socklen_t *addrlen, 
		   sidl_BaseInterface *_ex) { 
  int n;
  
  /* returns 0 if OK, -1 on error */
  if ((n=getsockname(sockfd, localaddr, addrlen))<0) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  }
 EXIT:
  return n;
}

/* same as getpeername(), but using sidl exceptions */
int s_getpeername( int sockfd, struct sockaddr *peeraddr, socklen_t *addrlen, 
		   sidl_BaseInterface *_ex) { 
  int n;
  
  /* returns 0 if OK, -1 on error */
  if ((n=getpeername(sockfd, peeraddr, addrlen))<0) { 
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
  }
 EXIT:
  return n;
}


/* this is a utility function that makes sure a char array is
   1-D, packed, and has a minimum length. */
void ensure1DPackedCharArray( const int32_t minlen, 
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

/* read nbytes into a character array */
int32_t s_readn( int filedes, int32_t nbytes, struct sidl_char__array** data,
		 sidl_BaseInterface *_ex) {
  char* ptr;
  int32_t n_read;

  ensure1DPackedCharArray( nbytes, data );

  ptr = sidl_char__array_first( *data ); /* get the first one */
					   
  n_read = s_readn2(filedes, nbytes, &ptr, _ex ); SIDL_CHECK(*_ex);
 EXIT:
  return n_read;
}

/* read a line up to nbytes long into character array (newline preserved)*/
int32_t s_readline( int filedes, int32_t nbytes, 
		    struct sidl_char__array** data, sidl_BaseInterface *_ex ) {
  char* ptr;
  int32_t n_read;

  ensure1DPackedCharArray( nbytes, data );

  ptr = sidl_char__array_first( *data ); /* get the first one */
  
  n_read = s_readline2(filedes, nbytes, &ptr, _ex); SIDL_CHECK(*_ex);

  return n_read;
 EXIT:
  return -1;
}

/* write nbytes of a character array (-1 implies whole array) */
int32_t s_writen( int filedes, int32_t nbytes, 
		  struct sidl_char__array* data, sidl_BaseInterface *_ex) {
  char * ptr= sidl_char__array_first( data );
  int32_t n = sidl_char__array_length(data,0);
  int32_t n_written;
  if (nbytes != -1 ) { 
    /* unless nbytes is -1 (meaning "all"), take the min(nbytes, length()) */
    n = (n<nbytes)? n : nbytes;
  }
  n_written = s_writen2( filedes, n, ptr, _ex); SIDL_CHECK(*_ex);
  return n_written;
 EXIT:
  return -1;
}

/* read a null terminated string from a FILE */
int32_t s_fgets( FILE * fp, const int32_t maxlen, struct sidl_char__array ** data, sidl_BaseInterface *_ex ) { 
  char * p;
  char * ptr;

  ensure1DPackedCharArray( maxlen, data );
  
  ptr= sidl_char__array_first(* data );
  p = fgets( ptr, maxlen+1, fp );

  if ( p==NULL ) { 
    return 0;
  } else { 
    return strlen(p);
  }
}

/* write a null terminated string to a FILE */
int32_t s_fputs( FILE *fp, const int32_t nbytes, 
		 const struct sidl_char__array * data, 
		 sidl_BaseInterface *_ex ) { 
  char * ptr; 
  int n; 

  if (data == NULL || (sidl_char__array_dimen(data)!=1) || (sidl_char__array_stride(data,0)!=1) ) { 
    return -1;
  }

  ptr= sidl_char__array_first( data );
  n = sidl_char__array_length(data,0)-1;
  ptr[n]='\0'; /* just to be safe */
  if(nbytes != -1) {
    if(nbytes < n)
      ptr[nbytes-1]='\0';
  }

  return fputs( ptr, fp );
}

/* read an int32_t from the network */
int32_t s_readInt(int filedes, int32_t* data, sidl_BaseInterface *_ex) {
  int32_t ret = 0;
  char ** cast_data = (char**) &data;
  
  ret = s_readn2(filedes, 4, cast_data, _ex); SIDL_CHECK(*_ex);
  *data = ntohl(*data);
  return ret;
 EXIT:
  return 0;
}

/* read nbytes into a character string */
int32_t s_readn2( int filedes, int32_t nbytes, char ** data,
		  sidl_BaseInterface *_ex) {
  size_t nleft;
  ssize_t nread;
  char* ptr = *data;

  if ( *data == NULL ) { 
    *data = sidl_String_alloc((size_t)nbytes);
  }

  nleft = (size_t)nbytes;
  
  while ( nleft > 0 ) {
    if ( ( nread = read( filedes, ptr, nleft)) < 0 ) { 
      if ( errno == EINTR ) { 
	nread = 0; /* and call read() again */
      } else { 
	nleft = (size_t)nbytes+1;

	sidlx_throwException(errno, _ex);
	SIDL_CHECK(*_ex);
        errno = 0;
      } 
    } else if ( nread == 0 ) { 
      break; /* EOF */
    }
    nleft -= nread;
    ptr += nread;
  }
 EXIT:
  return (nbytes-nleft);
}

/* read a line up to nbytes long into character string (newline preserved)*/
int32_t s_readline2( int filedes, int32_t nbytes, 
		     char ** data, sidl_BaseInterface *_ex ) {

  /* TODO:  This is intentionally a quickly implemented and threadsafe 
     implementation with the downside of being a bit slow and 
     unsophisticated.  Upgrade to something faster when time allows*/
  ssize_t n, rc;
  char c;
  char *ptr;

  if ( *data == NULL ) { 
    *data = sidl_String_alloc(nbytes);
  }
  ptr = *data;

  for( n=1; n<nbytes;n++) { 
    if (( rc=read(filedes,&c,1))==1) { 
      /* add a character */
      *ptr++=c;
      if (c=='\n') { 
	/* newline is not removed */
	break;       
      }
    } else if (rc == 0 ) { 
      if (n==1) { 
	/* EOF, no data read */
	return 0;  
      } else { 
	/* EOF, some data read */
	break;       
      }
    } else { /* rc = something other than 1 or zero */ 
      if (errno==EINTR) { 
	n--; continue;
      } else { 
	sidlx_throwException(errno, _ex);
	SIDL_CHECK(*_ex);
      }
    }
  }
  return n;
 EXIT:
  return -1;
}

/*write an int32_t to the network*/
void s_writeInt(int filedes, int32_t data, sidl_BaseInterface *_ex) {
  
  char * cast_data = (char*) &data;
  data = htonl(data);
  s_writen2(filedes, 4, cast_data, _ex);

}

/* write the character string */
int32_t s_writen2( int filedes, const int32_t nbytes, const char * data, 
		   sidl_BaseInterface *_ex) {
  size_t nleft;
  ssize_t nwritten;
  const char * ptr = data;
  /* int n = sidl_String_strlen( data ); */
  int n = nbytes;
  nleft = n;
  while( nleft > 0 ) { 
    if ( ( nwritten=write(filedes,ptr,nleft))<=0) { 
      if (errno==EINTR) { 
	nwritten=0; /* and call write() again */
      } else { 
	sidlx_throwException(errno, _ex);
	SIDL_CHECK(*_ex);
      }
    }
    nleft -= nwritten;
    ptr += nwritten;
  }
  return (n);
 EXIT:
  return -1;
}

/* write nbytes of this character array as a string.  (an length integer 
   followed by the byte stream) -1 means write the whole string*/ 
int32_t s_write_string(int filedes, const int32_t nbytes, 
		       struct sidl_char__array * data, 
		       sidl_BaseInterface *_ex) {

  char * ptr= sidl_char__array_first( data );
  int32_t n = sidl_char__array_length(data,0);
  int32_t n_written;
  if (nbytes != -1 ) { 
    /* unless nbytes is -1 (meaning "all"), take the min(nbytes, length()) */
    n = (n<nbytes)? n : nbytes;
  }
  s_writeInt(filedes, n, _ex); SIDL_CHECK(*_ex);
  n_written = s_writen2( filedes, n, ptr, _ex); SIDL_CHECK(*_ex);
  return n_written;
 EXIT:
  return -1;

}
/*
  I don't really like this.  I think we should have some what communicating back when
  the array was not big enough to handle all the data, so we can handle it later.
  I recommen using s_read_string_alloc intead.
  read a string up to min(nbytes,length) long into character array (newline preserved)
*/
int32_t s_read_string( int filedes, const int32_t nbytes, 
		       struct sidl_char__array* data, sidl_BaseInterface *_ex ) {
  char* ptr;
  int32_t bytesToRead = 0;
  int32_t len = sidl_char__array_length(data,0);
  int32_t n_read, inLen, left;

  if(nbytes == -1)
    bytesToRead = len;
  else
    left = bytesToRead = (nbytes<len) ? nbytes: len;

  ensure1DPackedCharArray( bytesToRead, &data );

  ptr = sidl_char__array_first( data ); /* get the first one */
  n_read = s_readInt(filedes, &inLen, _ex); SIDL_CHECK(*_ex);					   
  if(n_read == 0)
    goto EXIT;
  
  bytesToRead = (bytesToRead<inLen)? bytesToRead:inLen; 
  /* hopefully bytesToRead is bigger than inLen */
  n_read = s_readn2(filedes, bytesToRead, &ptr, _ex ); SIDL_CHECK(*_ex);
  
 EXIT:
  return n_read;

}

/* read a string up into character array (newline preserved)
   frees the current sidl_char__array and allocates a new one if length < the incoming string
   if(*data == null) a string as long as nessecary will be allocated */
int32_t s_read_string_alloc( int filedes,
			     struct sidl_char__array** data, sidl_BaseInterface *_ex ) {

  int32_t inLen = 0;
  int32_t curLen = 0; 
  int32_t n;
  int32_t lower[1], upper[1];
  if(data ==NULL) {
    SIDL_THROW( *_ex, sidl_io_IOException, "read() error: data is NULL!");
    return 0;
  }
  if(*data != NULL)
    curLen = sidl_char__array_length(*data, 0); /* Now that we know data isn't null */
  else
    curLen = 0;

  n = s_readInt(filedes, &inLen, _ex); SIDL_CHECK(*_ex);
  if(inLen <= 0) {
    sidlx_throwException(errno, _ex);
    SIDL_CHECK(*_ex);
    return 0;
  }

  if(curLen < inLen) {
    if(*data != NULL)
      sidl_char__array_deleteRef(*data);
    lower[0] = 0;
    upper[0] = inLen-1;
    *data = sidl_char__array_createCol(1,lower,upper);
  }

  n = s_readn(filedes, inLen, data, _ex); SIDL_CHECK(*_ex);
  return n;

 EXIT:
  return 0;

}

/* This function parses a url into the pointers provided (they are all out parameters)
   url, protocol, and server are required, and the method will throw an if they are
   null.  port, className, and objectID are optional, and may be passed in as NULL
   Support Port Ranges.  If a range is not detected, start_port has the real port
*/ 
void sidlx_parseURL(const char* url, char** protocol, char** server, 
		    int* start_port, int* end_port, 
		    char** objectID, sidl_BaseInterface *_ex) {

  int i = 0;
  int start=0;
  int length = 0;

  if(url == NULL || protocol == NULL || server == NULL) {
    SIDL_THROW(*_ex, sidl_rmi_NetworkException, 
	       "sidl_rmi_ProtocolFactory.praseURL: Required arg is NULL\n");
  }
  
  length = sidl_String_strlen(url);
  
  /* extract the protocol name */
  while ((i<length) && (url[i]!=':')) { 
    i++;
  }
  if ( (i==start) || (i==length) ) { 
    SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "could not extract prefix from URL\n");
  }
  
  if(protocol != NULL) {
    *protocol=sidl_String_strndup(url,i); /* copies (i-1) chars plus a '\0'*/
  }

  /* skip colons & slashes (should be "://") */
  if ( ((i+3)>=length) || (url[i]!=':') || (url[i+1]!='/') || (url[i+2]!='/')) { 
    SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "expected :// next in URL\n");
  } else { 
    i+=3;
  }
  /* extract server name */
  start=i;
  while ( (i<length) && url[i]!=':'&& url[i]!='/') { 
    i++;
  }

  if (i==start) { 
    SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "could not extract host from URL");
  }
  if(server != NULL) {
    *server = sidl_String_strndup(url + start, i-start);
  }

  /* extract port number (if it exists ) */
  if ( (i<length) && (url[i]==':')) {
    ++i;
    start=i;
    while ((i<length) && (url[i] != '/') && (url[i] != '-')) { 
      if ( (url[i]<'0') || url[i]>'9') { 
	SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "could not extract port number from URL");
      }
      i++;
    }
    if(start_port!=NULL) {
      char buffer[256];
      strncpy( buffer, url+start, i-start );
      buffer[i-start] = '\0';
      *start_port = atoi( buffer );
    }
  }

  /* extract end_port number (if a range exists exists ) */
  if ( (i<length) && (url[i]=='-')) {
    ++i;
    start=i;
    while ((i<length) && (url[i] != '/')) { 
      if ( (url[i]<'0') || url[i]>'9') { 
	SIDL_THROW(*_ex, sidl_rmi_MalformedURLException, "could not extract max port from URL\n");
      }
      i++;
    }
    if(end_port!=NULL) {
      char buffer[256];
      strncpy ( buffer, url+start, i-start);
      *end_port = atoi( buffer );
    }
  } else {  /*If an end_port was requested, but isn't in the URL, end_port =
              0; */
    if(end_port!=NULL) {
      *end_port = 0;
    }
  }

  /* Continue onward to extract the objectid, if it exists*/
  if ( (i<length) && (url[i]=='/')) {
    ++i;
    start=i;
    while ((i<length) && (url[i] != '/')) { 
      i++;
    }
    if(objectID!=NULL) {
      *objectID = sidl_String_strndup( url+start,i-start );
    }
  } else {
    return;
  }

 EXIT:
  return;

}


/* This function converts an int to a dot format IP address.
   params[in] int32_t   : iAddress (host format)
   params[out] char*    : sAddress, but point to a buffer of at least 16 char.
*/

void int2ip(int32_t iAddress, char* sAddress) {

  int32_t nAddress = htonl(iAddress);
  sprintf(sAddress, "%u.%u.%u.%u",
          (0x000000FF & nAddress),
          0x000000FF & (nAddress >> 8),
          0x000000FF & (nAddress >> 16),
          0x000000FF & (nAddress >> 24));
  
}
   


