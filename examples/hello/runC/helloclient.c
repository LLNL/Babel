/*
 * File:        helloclient.c
 * Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 6471 $
 * Date:        $Date: 2008-08-13 14:53:28 -0700 (Wed, 13 Aug 2008) $
 * Description: Simple Hello World C client
 *
 */

#include "Hello_World.h"
#include "sidl_header.h"
#include "sidl_BaseException.h"
#include "sidl_Exception.h"
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char** argv) 
{
  sidl_BaseInterface ex;
  char* msg = NULL;
  Hello_World h = Hello_World__create(&ex);SIDL_CHECK(ex);
  msg = Hello_World_getMsg(h, &ex);SIDL_CHECK(ex);
  Hello_World_deleteRef(h,&ex);SIDL_CHECK(ex);

  printf ("%s\n", msg);
  free(msg);

  return 0;
 EXIT:
  {
    sidl_BaseInterface ignore;
    sidl_BaseException sbe = sidl_BaseException__cast(ex, &ignore);
    if (sbe) {
      char *msg = sidl_BaseException_getNote(sbe, &ignore);
      fprintf(stderr, "Exception: %s", msg ? msg : "");
      free(msg);
      msg = sidl_BaseException_getTrace(sbe, &ignore);
      fprintf(stderr, "Traceback: %s", msg ? msg : "");
      free(msg);
      sidl_BaseException_deleteRef(sbe, &ignore);
    }
    SIDL_CLEAR(ex);
    return -1;
  }
}
