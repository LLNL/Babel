/*
 * File:        sidl_Exception.c
 * Copyright:   (c) 2005 Lawrence Livermore National Security, LLC
 * Release:     $Name$
 * Revision:    @(#) $Revision$
 * Date:        $Date$
 * Description: 
 *
 */
#include "sidl_Exception.h"
#include "sidl_BaseInterface.h"
#include "sidl_BaseException.h"
#include "sidl_ClassInfo.h"
#include <stdio.h>
#include <stdlib.h>

int
SIDL_CATCH(struct sidl_BaseInterface__object *ex_var,
           const char *sidl_Name)
{
  struct sidl_BaseInterface__object *exception=NULL, *throwaway_exception;
  const int result = 
    (ex_var && sidl_BaseInterface_isType(ex_var, sidl_Name, &exception) &&
     !exception);
  if (exception) sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  return result;
}

void
sidl_update_exception(struct sidl_BaseInterface__object *ex,
                      const char *filename,
                      const int32_t line,
                      const char *funcname)
{
  sidl_BaseInterface exception = NULL; 
  sidl_BaseException _s_b_e = sidl_BaseException__cast(ex, &exception);
  sidl_BaseException_add(_s_b_e, filename, line, funcname, &exception);
  sidl_BaseException_deleteRef(_s_b_e, &exception); 
}

void sidl_report_exception(struct sidl_BaseInterface__object *ex)
{
  sidl_BaseInterface exception = NULL;
  sidl_BaseException _s_b_e = sidl_BaseException__cast(ex, &exception);
  if (!exception && _s_b_e) {
    char *note, *trace;
    sidl_ClassInfo info = sidl_BaseException_getClassInfo(_s_b_e, &exception);
    if (!exception && info) {
      char *name = sidl_ClassInfo_getName(info, &exception);
      if (!exception && name) {
        fprintf(stderr, "Babel exception: %s\n", name);
        free((void*)name);
      }
      else {
        SIDL_CLEAR(exception);
      }
    }
    else {
      SIDL_CLEAR(exception);
    }
    note = sidl_BaseException_getNote(_s_b_e, &exception);
    if (!exception && note) {
      fputs(note, stderr);
      putc('\n', stderr);
      free((void *)note);
    }
    else {
      SIDL_CLEAR(exception);
    }
    trace = sidl_BaseException_getTrace(_s_b_e, &exception);
    if (!exception && trace) {
      fputs(trace, stderr);
      putc('\n', stderr);
      free((void *)trace);
    }
    else {
      SIDL_CLEAR(exception);
    }
    sidl_BaseException_deleteRef(_s_b_e, &exception);
  }
  else {
    SIDL_CLEAR(exception);
  }
}
