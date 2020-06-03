/*
 * File:          sidl_MemAllocException_Impl.c
 * Symbol:        sidl.MemAllocException-v0.9.17
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Release:       $Name:  $
 * Revision:      @(#) $Id: $
 * Description:   Server-side implementation for sidl.MemAllocException
 * 
 * Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC.
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by the Components Team <components@llnl.gov>
 * All rights reserved.
 * 
 * This file is part of Babel. For more information, see
 * http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
 * for Our Notice and the LICENSE file for the GNU Lesser General Public
 * License.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License (as published by
 * the Free Software Foundation) version 2.1 dated February 1999.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
 * conditions of the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidl.MemAllocException" (version 0.9.17)
 * 
 * Exception thrown from Babel internals when memory allocation
 * fails.  This exception is special in that it avoids any memory
 * allocation.  For this reason, the trace or note may be truncated
 * to fit in the preallocated buffers.
 */

#include "sidl_MemAllocException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidl.MemAllocException._includes) */
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "sidl_Exception.h"
#include "sidlOps.h"
#include "sidl_MemAllocException.h"

/**--------------------------------------------------------------------
 * This exception is special, in order to avoid allocating memory when 
 * there is no memory to be allocated, this static exception is thrown.
 *--------------------------------------------------------------------*/
volatile static sidl_MemAllocException s_singletonEx;


/**--------------------------------------------------------------------
 * This is a boolean.  If it is true, the destrySingletonEx method has
 * been called, and it is ok to destroy the singletonEx.  Otherwise, it
 * should be false and an error message will be printed if the singletonEx
 * is destroyed.
 *--------------------------------------------------------------------*/
volatile static int s_destroyable;


/**---------------------------------------------------------------------------
 * This method is to be called by atexit().  It frees the singleton Exception.
 *----------------------------------------------------------------------------*/
static void destroySingletonEx(void* ignore) {
  sidl_BaseInterface _throwaway;
  if(s_singletonEx) {
    s_destroyable = TRUE;
    sidl_MemAllocException_deleteRef(s_singletonEx, &_throwaway);
    s_singletonEx = NULL;
  }
}

/* DO-NOT-DELETE splicer.end(sidl.MemAllocException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
static const struct sidl_SIDLException__epv* superEPV = NULL;

void sidl_MemAllocException__superEPV(
struct sidl_SIDLException__epv* parentEPV){
  superEPV = parentEPV;
}
/*
 * Return the message associated with the exception.
 */

static char*
super_getNote(
  /* in */ sidl_MemAllocException self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_getNote)((struct sidl_SIDLException__object*)
    self,
    _ex);
}

/*
 * Set the message associated with the exception.
 */

static void
super_setNote(
  /* in */ sidl_MemAllocException self,
  /* in */ const char* message,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_setNote)((struct sidl_SIDLException__object*)
    self,
    message,
    _ex);
}

/*
 * Returns formatted string containing the concatenation of all 
 * tracelines.
 */

static char*
super_getTrace(
  /* in */ sidl_MemAllocException self,
  /* out */ sidl_BaseInterface *_ex)
{
  return (*superEPV->f_getTrace)((struct sidl_SIDLException__object*)
    self,
    _ex);
}

/*
 * Adds a stringified entry/line to the stack trace.
 */

static void
super_addLine(
  /* in */ sidl_MemAllocException self,
  /* in */ const char* traceline,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_addLine)((struct sidl_SIDLException__object*)
    self,
    traceline,
    _ex);
}

/*
 * Formats and adds an entry to the stack trace based on the 
 * file name, line number, and method name.
 */

static void
super_add(
  /* in */ sidl_MemAllocException self,
  /* in */ const char* filename,
  /* in */ int32_t lineno,
  /* in */ const char* methodname,
  /* out */ sidl_BaseInterface *_ex)
{
  (*superEPV->f_add)((struct sidl_SIDLException__object*)
    self,
    filename,
    lineno,
    methodname,
    _ex);
}

/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException._load) */
    s_singletonEx = sidl_MemAllocException__create(_ex);
    if(*_ex) { return; }
    if(!s_singletonEx) {
      fprintf(stderr, "Unable to allocate sidl.MemoryException, s_singletonEx.  "
	      "Exiting due to serious memory issues.\n");
      exit(1);
    }

    s_destroyable = FALSE;
    sidl_atexit(destroySingletonEx, NULL);
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException__ctor(
  /* in */ sidl_MemAllocException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException._ctor) */
    struct sidl_MemAllocException__data *data = 
      (struct sidl_MemAllocException__data *)
      malloc(sizeof(struct sidl_MemAllocException__data));

    data->d_trace[0] = '\0';
    data->d_message[0] = '\0';
    data->d_trace_length = 0;
 
    sidl_MemAllocException__set_data(self, data);
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException__ctor2(
  /* in */ sidl_MemAllocException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException._ctor2) */
    /* Insert-Code-Here {sidl.MemAllocException._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException__dtor(
  /* in */ sidl_MemAllocException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException._dtor) */
    struct sidl_MemAllocException__data *data = 
      sidl_MemAllocException__get_data(self);

    free(data);

    /**-------------------------------------------------------
     * The user should never destroy the s_singletonEx, it would 
     * require 2 deleteRefs by the user, which is a bug.
     *--------------------------------------------------------*/
    if(self == s_singletonEx && !s_destroyable) {
      fprintf(stderr, "sidl.MemoryException s_singletonEx. Has been illegally "
	      "destroyed. This is a deleteRef bug. Attempting to re-create it.\n");
      fflush(stderr);
      s_singletonEx = sidl_MemAllocException__create(_ex);
      if(!s_singletonEx || *_ex) {
	fprintf(stderr, "sidl.MemoryException re-create failed. Attempting to "
		"continue execution anyway. Expect problems.\n");
	fflush(stderr);
      }
    }
    
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException._dtor) */
  }
}

/*
 * Returns the preallocated copy of this exception.  Any
 * failure of memory allocation should throw the exception returned
 * by this method to avoid further allocation failures.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException_getSingletonException"

#ifdef __cplusplus
extern "C"
#endif
sidl_MemAllocException
impl_sidl_MemAllocException_getSingletonException(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException.getSingletonException) */
    volatile static int no_infinite_recursion = 0;
    if(s_singletonEx) {
      struct sidl_MemAllocException__data *data = 
	sidl_MemAllocException__get_data(s_singletonEx);

      /* Clear out the old trace and message in s_singletonEx */

      data->d_trace[0] = '\0';
      data->d_message[0] = '\0';
      data->d_trace_length = 0;
      
    } else {
      if (no_infinite_recursion) {
	fprintf(stderr, "Attempt to re-create sidl.MemoryException s_singletonEx failed. Aborting.\n");
	exit(2);
      }
      fprintf(stderr, "sidl.MemoryException s_singletonEx. Does not exist. "
	      "Attempting to re-create it.\n");
      fflush(stderr);
      no_infinite_recursion = 1;
      s_singletonEx = sidl_MemAllocException__create(_ex);
      no_infinite_recursion = 0;
      if(!s_singletonEx || *_ex) {
	fprintf(stderr, "sidl.MemoryException re-create failed. Attempting to "
		"continue execution anyway. Returning NULL.\n");
	s_singletonEx = NULL;
      }
      fflush(stderr);
    }

    if(s_singletonEx) {
      /* addRef and return (refcount should be 2 after this addRef)*/
      sidl_MemAllocException_addRef(s_singletonEx, _ex);
    }
    return s_singletonEx;
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException.getSingletonException) */
  }
}

/*
 * Return the message associated with the exception.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException_getNote"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_MemAllocException_getNote(
  /* in */ sidl_MemAllocException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException.getNote) */
    struct sidl_MemAllocException__data *data = 
      sidl_MemAllocException__get_data(self);
    int len = strlen(data->d_message);
    
    char* ret_message = malloc(len+1);

    if(ret_message) {
      strncpy(ret_message, data->d_message, len);
      ret_message[len] = '\0';
      return ret_message;
    } else {
      fprintf(stderr, "sidl.MemoryException.getNote cannot allocate data. "
	      "Returning unfree-able string.\n");
      fflush(stderr);
      return data->d_message;
    }

    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException.getNote) */
  }
}

/*
 * Set the message associated with the exception.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException_setNote"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException_setNote(
  /* in */ sidl_MemAllocException self,
  /* in */ const char* message,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException.setNote) */
    struct sidl_MemAllocException__data *data = 
      sidl_MemAllocException__get_data(self);
    int len = strlen(message);
    int cpy_len = (len < MESSAGE_SIZE -1) ? len : MESSAGE_SIZE-1;

    strncpy(data->d_message, message, cpy_len);

    if(len > MESSAGE_SIZE) {
      data->d_message[MESSAGE_SIZE-1] = '\0';
    }

    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException.setNote) */
  }
}

/*
 * Returns formatted string containing the concatenation of all 
 * tracelines.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException_getTrace"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_MemAllocException_getTrace(
  /* in */ sidl_MemAllocException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException.getTrace) */
    struct sidl_MemAllocException__data *data = 
      sidl_MemAllocException__get_data(self);
    
    char* ret_trace = malloc(data->d_trace_length+1);

    if(ret_trace) {
      strncpy(ret_trace, data->d_trace, data->d_trace_length-1);
      ret_trace[data->d_trace_length] = '\0';
      return ret_trace;
    } else {
      fprintf(stderr, "sidl.MemoryException.getTrace cannot allocate data. "
	      "Returning unfree-able string.\n");
      fflush(stderr);
      return data->d_trace;
    }

    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException.getTrace) */
  }
}

/*
 * Adds a stringified entry/line to the stack trace.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException_addLine"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException_addLine(
  /* in */ sidl_MemAllocException self,
  /* in */ const char* traceline,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException.addLine) */
    struct sidl_MemAllocException__data *data = 
      sidl_MemAllocException__get_data(self);
    
    int traceline_len = strlen(traceline);
    int traceline_with_newline = traceline_len + 1;
    
    /* If there's room left in the trace too add the line, add as much 
     * as possible. */
    if(data->d_trace_length < TRACE_SIZE) {
      char* traceEnd = data->d_trace + data->d_trace_length;
      int spaceRemaining = TRACE_SIZE - data->d_trace_length;

      if(traceline_with_newline >= spaceRemaining) {
	strncpy(traceEnd, traceline, spaceRemaining);
	data->d_trace_length = TRACE_SIZE;
	data->d_trace[TRACE_SIZE-2] = '\n';
	data->d_trace[TRACE_SIZE-1] = '\0';
      } else {
	strncpy(traceEnd, traceline, traceline_len);
	/* +1 one character for the newline */
	data->d_trace_length = data->d_trace_length + traceline_with_newline;
	data->d_trace[data->d_trace_length-1] = '\n';
	data->d_trace[data->d_trace_length] = '\0';
      }
    }
    
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException.addLine) */
  }
}

/*
 * Formats and adds an entry to the stack trace based on the 
 * file name, line number, and method name.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_MemAllocException_add"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_MemAllocException_add(
  /* in */ sidl_MemAllocException self,
  /* in */ const char* filename,
  /* in */ int32_t lineno,
  /* in */ const char* methodname,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.MemAllocException.add) */
    /*
     *  The estimated length of the trace line is the sum of the lengths of
     *  the method name, file name, and hard-coded string contents plus a
     *  rough allowance for the line number.  Since we're using int for lineno,
     *  it is assumed the maximum int is 2^64, or 18446744073709551616 (i.e.,
     *  an allowance of 20 characters is made.)  Hence,
     *
     *    # bytes = filename + methodname + characters + lineno + 1
     *            = filename + methodname +      8     +   20   + 1
     *            = filename + methodname + 29
     *
     *  Of course, a more accurate approach would be to calculate the number 
     *  of digits in lineno prior to the malloc but, at first blush, it was
     *  assumed it wasn't worth the extra cycles given the purpose of the 
     *  stack trace.
     */
    const char* tmpfn;
    size_t filelen;
    const char*  tmpmn;
    size_t methlen;
    
    if (filename) {
      tmpfn = filename;
    } else {
      tmpfn = "UnspecifiedFile";
    } 
    if (methodname) {
      tmpmn = methodname;
    } else {
      tmpmn = "UnspecifiedMethod";
    } 
    
    filelen = strlen(tmpfn);
    methlen = strlen(tmpmn);
    
    { /* New scope just to decalre tmpline */
      char *tmpline = malloc(29 + filelen + methlen);
      
      if (tmpline) {
	sprintf(tmpline, "in %s at %s:%d", tmpmn, tmpfn, lineno); 
	sidl_MemAllocException_addLine(self,tmpline, _ex);
	free(tmpline);
      }
    }
    
    return;
    /* DO-NOT-DELETE splicer.end(sidl.MemAllocException.add) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
  /* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

