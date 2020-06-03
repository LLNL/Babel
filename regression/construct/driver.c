/*
 * File:        driver.c
 * Copyright:   (c) 2006 Lawrence Livermore National Security, LLC
 * Release:     $Name$
 * Revision:    @(#) $Revision$
 * Date:        $Date$
 * Description: Test details of the construction and destruction of objects
 *
 * This test checks the intricate ordering of the constructor and destructor
 * processing. It check that each ctor/dtor fires in the correct order, and
 * it also checks that the EPV is correctly unwound.
 */

#include "synch_RegOut.h"
#include "cons_D.h"
#include "sidl_BaseInterface.h"
#include "sidl_Exception.h"
#include <stdio.h>

int
main(int argc, char **argv)
{
  sidl_BaseInterface exception = NULL;
  cons_D object = NULL;
  synch_RegOut tracker = synch_RegOut_getInstance(&exception); SIDL_REPORT(exception);
  synch_RegOut_setExpectations(tracker, 26, &exception);   SIDL_REPORT(exception);
  synch_RegOut_startPart(tracker, 1, &exception);    SIDL_REPORT(exception);
  object = cons_D__create(&exception); SIDL_REPORT(exception);
  synch_RegOut_endPart(tracker, 13, synch_ResultType_PASS, &exception); SIDL_REPORT(exception);

  synch_RegOut_startPart(tracker, 14, &exception); SIDL_REPORT(exception);
  cons_D_deleteRef(object, &exception); SIDL_REPORT(exception);
  synch_RegOut_endPart(tracker, 26, synch_ResultType_PASS, &exception); SIDL_REPORT(exception);
  synch_RegOut_close(tracker, &exception); SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception); SIDL_REPORT(exception);
  return 0;
 EXIT:
  {
    sidl_BaseInterface throwaway_exception = NULL;
    if (tracker) {
      sidl_BaseInterface exception2 = NULL;
      synch_RegOut_forceFailure(tracker, &exception2);
      if (exception2) {
        puts("TEST_RESULT FAIL\n");
        sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
      }
      synch_RegOut_deleteRef(tracker, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
    return -1;
  }
}
