/*
 * File:        vectortest.c
 * Copyright:   (c) 2004-2009 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 6171 $
 * Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
 * Description: Contract Test C client
 *
 */
#include <stdio.h>
#include <math.h>
#include <string.h>
#include "sidl_EnfPolicy.h"
#include "sidl_String.h"
#include "synch.h"
#include "vect_Utils.h"
#include "vect_BadLevel.h"
#include "vect_ExpectExcept.h"
#include "vect_vExcept.h"
#include "sidl_Exception.h"

/* Ensure the following is set to 0 BEFORE commiting to production */
#define TEST_DEBUG 0

#ifndef FALSE
#define FALSE 0
#endif
#ifndef TRUE
#define TRUE 1
#endif

void declare_part(synch_RegOut tracker,  int * part_no ) {
  sidl_BaseInterface exception = NULL;
  synch_RegOut_startPart(tracker, ++(*part_no), &exception);
  if (exception) {
    sidl_BaseInterface throwaway_exception;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
}

void end_part( synch_RegOut tracker, int part_no,
               enum synch_ResultType__enum result) 
{
  sidl_BaseInterface exception = NULL;
  synch_RegOut_endPart(tracker, part_no, result, &exception);
  if (exception) {
    sidl_BaseInterface throwaway_exception;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
}

static void printExceptionNote(sidl_SIDLException _ex)
{
  if (_ex != NULL) {
    sidl_BaseInterface throwaway_exception;
    char* msg = NULL;

    msg = sidl_SIDLException_getNote(_ex, &throwaway_exception);
    printf("  %s\n", msg);
    sidl_String_free(msg);
  }
}

#if TEST_DEBUG
#define START_DEBUG(RES, EXC) {\
  char sdMsg[128]; \
  sidl_BaseInterface sdExc; \
  sprintf(sdMsg, "** DEBUG:  res=%d, expectExc=%d", RES, EXC); \
  synch_RegOut_writeComment(tracker, sdMsg, &sdExc); \
}
#define START_DEBUGD(RES, TOL, EXC) {\
  char sddMsg[128]; \
  sidl_BaseInterface sddExc; \
  sprintf(sddMsg, "** DEBUG:  res=%12.9f, tol=%12.9f, expectExc=%d", RES, TOL, EXC); \
  synch_RegOut_writeComment(tracker, sddMsg, &sddExc); \
}
#define RESULT_DEBUG(X) {\
  char rdMsg[128]; \
  sidl_BaseInterface rdExc; \
  sprintf(rdMsg, "** DEBUG:  x=%d", X); \
  synch_RegOut_writeComment(tracker, rdMsg, &rdExc); \
}
#define RESULT_DEBUGD(X) {\
  char rddMsg[128]; \
  sidl_BaseInterface rddExc; \
  sprintf(rddMsg, "** DEBUG:  x=%12.9f", X); \
  synch_RegOut_writeComment(tracker, rddMsg, &rddExc); \
}
#define RESULT_DEBUGP(X) {\
  char rdpMsg[128]; \
  sidl_BaseInterface rdpExc; \
  sprintf(rdpMsg, "** DEBUG:  x==NULL? %d", (X == NULL)); \
  synch_RegOut_writeComment(tracker, rdpMsg, &rdpExc); \
}
#define END_DEBUG(OK) {\
  char edMsg[128]; \
  sidl_BaseInterface edExc; \
  sprintf(edMsg, "** DEBUG:  ok=%d", OK); \
  synch_RegOut_writeComment(tracker, edMsg, &edExc); \
}
#else
#define START_DEBUG(RES, EXC)
#define START_DEBUGD(RES, TOL, EXC)
#define RESULT_DEBUG(X)
#define RESULT_DEBUGD(X)
#define RESULT_DEBUGP(X)
#define END_DEBUG(OK)
#endif /* TEST_DEBUG */


#define CHECK(FUNC,COMMENT) {\
  declare_part(tracker, &part_no); \
  synch_RegOut_writeComment(tracker, COMMENT, &exception); \
  result = (FUNC) ? synch_ResultType_PASS : synch_ResultType_FAIL; \
  end_part(tracker, part_no, result); \
}

#define EXCEPTION_CHECKS(EXCV, EXC_EXPECT, RES, RES_EXPECT, RET) {\
  if ((EXCV) != NULL) {\
    if (SIDL_CATCH((EXCV), "vect.vNegValExcept")) {\
      RET = (EXC_EXPECT == vect_ExpectExcept_NVEExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "vect.vDivByZeroExcept")) {\
      RET = (EXC_EXPECT == vect_ExpectExcept_DBZExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "vect.vExcept")) {\
      RET = (EXC_EXPECT == vect_ExpectExcept_ExcExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "sidl.PreViolation")) {\
      RET = (EXC_EXPECT == vect_ExpectExcept_PreExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "sidl.PostViolation")) {\
      RET = (EXC_EXPECT == vect_ExpectExcept_PostExp); \
      CLEANUP(EXCV, RET)\
    } else {\
      goto EXIT;\
    }\
  }\
}

#define RESULT_CHECKS(EXCV, EXC_EXPECT, RES, RES_EXPECT) {\
  RESULT_DEBUG(RES)\
  int _ret = FALSE;\
  EXCEPTION_CHECKS(EXCV, EXC_EXPECT, RES, RES_EXPECT, _ret) \
  if ( ((EXCV) == NULL) && (EXC_EXPECT == vect_ExpectExcept_NoneExp) ) {\
    _ret = (RES == RES_EXPECT);\
    CLEANUP(EXCV, _ret)\
  }\
}

#define RESULT_CHECKSA(EXCV, EXC_EXPECT, RES, RES_EXPECT, TOL, OK, OK_EXPECT) {\
  RESULT_DEBUGP(RES)\
  if (EXCV != NULL) {\
    if (RES != NULL) {\
      sidl_double__array_deleteRef(RES);\
      RES = NULL;\
    }\
    RESULT_CHECKS(EXCV, EXC_EXPECT, OK_EXPECT, OK_EXPECT)\
  }\
  if (RES != NULL) {\
    OK = vect_Utils_vuAreEqual(sidl_array_cast(RES), \
                               sidl_array_cast(RES_EXPECT), TOL, \
                               (sidl_BaseInterface*)(&EXCV));\
    sidl_double__array_deleteRef(RES);\
    RESULT_CHECKS(EXCV, EXC_EXPECT, OK, OK_EXPECT)\
  } else { /* RES == NULL */ \
    OK = (RES_EXPECT == NULL) ? TRUE : FALSE;\
    CLEANUP(EXCV, (OK == OK_EXPECT))\
  }\
}

#define RESULT_CHECKSD(EXCV, EXC_EXPECT, RES, RES_EXPECT, TOL) {\
  RESULT_DEBUGD(RES)\
  int _ret = FALSE;\
  EXCEPTION_CHECKS(EXCV, EXC_EXPECT, RES, RES_EXPECT, _ret) \
  if ( ((EXCV) == NULL) && (EXC_EXPECT == vect_ExpectExcept_NoneExp) ) {\
    _ret = ((fabs((double)RES) - fabs((double)RES_EXPECT)) <= fabs(TOL)); \
    CLEANUP(EXCV, _ret)\
  }\
}

#define CLEAR(EX_VAR) { \
  if ((EX_VAR) != NULL) { \
    sidl_BaseInterface _tae; \
    sidl_BaseInterface_deleteRef((sidl_BaseInterface)(EX_VAR), &_tae); \
    (EX_VAR) = NULL; \
  }  \
}

#define CLEANUP(EXCV, RET) {\
  if ((EXCV) != NULL) {\
    sidl_BaseInterface _tae = NULL; \
    sidl_SIDLException _cue = sidl_SIDLException__cast(EXCV, &_tae); \
    printExceptionNote(_cue); \
    if (_cue) sidl_SIDLException_deleteRef(_cue, &_tae); \
    CLEAR(EXCV)\
  }\
  END_DEBUG(RET)\
  return RET;\
}


int runIsZero(struct sidl_double__array* v, double tol, int res, 
              enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUG(res, expectExc)
  x = vect_Utils_vuIsZero(sidl_array_cast(v), tol, (sidl_BaseInterface*)(&_ex));
  RESULT_CHECKS(_ex, expectExc, x, res)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runIsUnit(struct sidl_double__array* v, double tol, int res, 
              enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUG(res, expectExc)
  x = vect_Utils_vuIsUnit(sidl_array_cast(v), tol, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKS(_ex, expectExc, x, res)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runAreEqual(struct sidl_double__array* u, 
                struct sidl_double__array* v, double tol, int res, 
                enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUG(res, expectExc)
  x = vect_Utils_vuAreEqual(sidl_array_cast(u), sidl_array_cast(v), tol, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKS(_ex, expectExc, x, res)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runAreOrthogonal(struct sidl_double__array* u, 
                     struct sidl_double__array* v, double tol, int res, 
                     enum vect_ExpectExcept__enum expectExc, 
                     synch_RegOut tracker)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUG(res, expectExc)
  x = vect_Utils_vuAreOrth(sidl_array_cast(u), sidl_array_cast(v), tol, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKS(_ex, expectExc, x, res)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runSchwarzHolds(struct sidl_double__array* u, 
                    struct sidl_double__array* v, double tol, int res, 
                    enum vect_ExpectExcept__enum expectExc, 
                    synch_RegOut tracker)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUG(res, expectExc)
  x = vect_Utils_vuSchwarzHolds(sidl_array_cast(u), sidl_array_cast(v), 
				tol, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKS(_ex, expectExc, x, res)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runTriangleInequalityHolds(struct sidl_double__array* u, 
                               struct sidl_double__array* v, double tol, 
                               int res, enum vect_ExpectExcept__enum expectExc, 
                               synch_RegOut tracker)
{
  int x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUG(res, expectExc)
  x = vect_Utils_vuTriIneqHolds(sidl_array_cast(u), sidl_array_cast(v), 
				tol, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKS(_ex, expectExc, x, res)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runNorm(struct sidl_double__array* u, double tol, 
            enum vect_BadLevel__enum badLvl, double res, 
            enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  double             x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUGD(res, tol, expectExc)
  x = vect_Utils_vuNorm(sidl_array_cast(u), tol, badLvl, (sidl_BaseInterface*)(&_ex));
  RESULT_CHECKSD(_ex, expectExc, x, res, tol)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


int runDot(struct sidl_double__array* u, struct sidl_double__array* v, 
           double tol, enum vect_BadLevel__enum badLvl, double res, 
           enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  double x;
  sidl_BaseInterface _ex = NULL;

  START_DEBUGD(res, tol, expectExc)
  x = vect_Utils_vuDot(sidl_array_cast(u), sidl_array_cast(v), 
		       tol, badLvl, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKSD(_ex, expectExc, x, res, tol)

  EXIT:;
    CLEANUP(_ex, FALSE)
}


/* NOTE: Tolerance is only needed to check the result of the call. */
int runProduct(double a, struct sidl_double__array* u, double tol,
               enum vect_BadLevel__enum badLvl, 
               struct sidl_double__array* res, int okay, 
               enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  struct sidl_double__array* x   = NULL;
  sidl_BaseInterface         _ex = NULL;
  int                        ok  = FALSE;

  START_DEBUG(okay, expectExc)
  x  = (struct sidl_double__array*)
    vect_Utils_vuProduct(a, sidl_array_cast(u), badLvl, (sidl_BaseInterface*)(&_ex));
  RESULT_CHECKSA(_ex, expectExc, x, res, tol, ok, okay)

  EXIT:;
    if (x != NULL) sidl_double__array_deleteRef(x);
    CLEANUP(_ex, FALSE)
}


/* NOTE: Tolerance is only needed to check the result of the call. */
int runNegate(struct sidl_double__array* u, double tol, 
              enum vect_BadLevel__enum badLvl, struct sidl_double__array* res, 
              int okay, enum vect_ExpectExcept__enum expectExc, 
              synch_RegOut tracker)
{
  struct sidl_double__array* x   = NULL;
  sidl_BaseInterface         _ex = NULL;
  int                        ok  = FALSE;

  START_DEBUG(okay, expectExc)
  x  = (struct sidl_double__array*)
    vect_Utils_vuNegate(sidl_array_cast(u), badLvl, (sidl_BaseInterface*)(&_ex));
  RESULT_CHECKSA(_ex, expectExc, x, res, tol, ok, okay)

  EXIT:;
    if (x != NULL) sidl_double__array_deleteRef(x);
    CLEANUP(_ex, FALSE)
}


int runNormalize(struct sidl_double__array* u, double tol, 
                 enum vect_BadLevel__enum badLvl,
                 struct sidl_double__array* res, int okay, 
                 enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  struct sidl_double__array* x   = NULL;
  sidl_BaseInterface         _ex = NULL;
  int                        ok  = FALSE;

  START_DEBUG(okay, expectExc)
  x  = (struct sidl_double__array*)
    vect_Utils_vuNormalize(sidl_array_cast(u), tol, badLvl, (sidl_BaseInterface*)(&_ex));
  RESULT_CHECKSA(_ex, expectExc, x, res, tol, ok, okay)

  EXIT:;
    if (x != NULL) sidl_double__array_deleteRef(x);
    CLEANUP(_ex, FALSE)
}


int runSum(struct sidl_double__array* u, struct sidl_double__array* v, 
           double tol, enum vect_BadLevel__enum badLvl, 
           struct sidl_double__array* res, int okay, 
           enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  struct sidl_double__array* x   = NULL;
  sidl_BaseInterface         _ex = NULL;
  int                        ok  = FALSE;

  START_DEBUG(okay, expectExc)
    x = (struct sidl_double__array*)
      vect_Utils_vuSum(sidl_array_cast(u), sidl_array_cast(v), 
		       badLvl, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKSA(_ex, expectExc, x, res, tol, ok, okay)

  EXIT:;
    if (x != NULL) sidl_double__array_deleteRef(x);
    CLEANUP(_ex, FALSE)
}


int runDiff(struct sidl_double__array* u, struct sidl_double__array* v, 
            double tol, enum vect_BadLevel__enum badLvl, 
            struct sidl_double__array* res, int okay, 
            enum vect_ExpectExcept__enum expectExc, synch_RegOut tracker)
{
  struct sidl_double__array* x   = NULL;
  sidl_BaseInterface         _ex = NULL;
  int                        ok  = FALSE;

  START_DEBUG(okay, expectExc)
  x = (struct sidl_double__array*)
    vect_Utils_vuDiff(sidl_array_cast(u), sidl_array_cast(v), 
		      badLvl, (sidl_BaseInterface*)(&_ex)); 
  RESULT_CHECKSA(_ex, expectExc, x, res, tol, ok, okay)

  EXIT:;
    if (x != NULL) sidl_double__array_deleteRef(x);
    CLEANUP(_ex, FALSE)
}


/*************************** main ***************************/

int main(int argc, char**argv)
{ 
  static char*       statsFile = "VUtils.stats";
  sidl_BaseInterface exception = NULL;

  enum synch_ResultType__enum result  = synch_ResultType_PASS; 
  int                         part_no = 0;


  int i, j;
  int    max_size  = 6;
  double sqrt_size = sqrt(max_size);
  double tol       = 1.0e-9;
  double ntol      = -1.0e-9;
  double val       = 1.0/sqrt_size;
  double nval      = -1.0/sqrt_size;
  double *cva      = (double *)malloc(max_size*sizeof(double));
  double *cva1     = (double *)malloc((max_size+1)*sizeof(double));
  double *cna      = (double *)malloc(max_size*sizeof(double));

  struct sidl_double__array *t  = NULL;
  struct sidl_double__array *u  = NULL;
  struct sidl_double__array *u1 = NULL;
  struct sidl_double__array *nu = NULL;
  struct sidl_double__array *z  = NULL;
  struct sidl_double__array *n  = NULL;  /* The null array! */

  synch_RegOut tracker = synch_RegOut__create(
                                     &exception); SIDL_CHECK(exception);

  for (i=0; i < max_size; i++) {
    cva[i]  = val;
    cva1[i] = val;
    cna[i]  = nval;
  }
  cva1[max_size] = val;

  u = sidl_double__array_create1d(max_size);
  memcpy(sidl_double__array_first(u), cva, (size_t)(max_size*sizeof(double)));
  free(cva);

  u1 = sidl_double__array_create1d(max_size+1);
  memcpy(sidl_double__array_first(u1), cva1,
         (size_t)((max_size+1)*sizeof(double)));
  free(cva1);

  nu = sidl_double__array_create1d(max_size);
  memcpy(sidl_double__array_first(nu), cna, (size_t)(max_size*sizeof(double)));
  free(cna);

  z  = sidl_double__array_create1d(max_size);
  memset(sidl_double__array_first(z), 0, (size_t)(max_size*sizeof(double)));

  t  = sidl_double__array_create2dCol(max_size, max_size);
  for (i=0; i < max_size; i++) {
    for (j=0; j < max_size; j++) {
       sidl_double__array_set2(t, i, j, val); 
    }
  }
  sidl_double__array_set1(u1, max_size, val);

  (void) argc;
  (void) argv;
  synch_RegOut_setExpectations(tracker, 128, &exception); SIDL_CHECK(exception);

  printf("\nCOMMENT: *** ENABLE FULL CONTRACT CHECKING ***\n\n");
  sidl_EnfPolicy_setEnforceAll(sidl_ContractClass_ALLCLASSES, TRUE, 
                               &exception); SIDL_CHECK(exception);

  /* vuIsZero() set */
  CHECK(runIsZero(z, tol, TRUE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the zero vector is the zero vector")
  CHECK(runIsZero(u, tol, FALSE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector is not the zero vector")
  CHECK(runIsZero(n, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuIsZero() a null array")
  CHECK(runIsZero(t, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuIsZero() a 2D array")
  CHECK(runIsZero(z, ntol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuIsZero() a negative tolerance")

  /* vuIsUnit() set */
  CHECK(runIsUnit(u, tol, TRUE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector is the unit vector")
  CHECK(runIsUnit(z, tol, FALSE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the zero vector is not the unit vector")
  CHECK(runIsUnit(n, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuIsUnit() a null array")
  CHECK(runIsUnit(t, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuIsUnit() a 2D array")
  CHECK(runIsUnit(u, ntol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuIsUnit() a negative tolerance")

  /* vuAreEqual() set */
  CHECK(runAreEqual(u, z, tol, FALSE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the unit and zero vectors are not equal")
  CHECK(runAreEqual(u, u, tol, TRUE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector is equal to itself")
  CHECK(runAreEqual(n, u, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a null 1st array")
  CHECK(runAreEqual(t, u, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a 2D 1st array")
  CHECK(runAreEqual(u, n, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a null 2nd array")
  CHECK(runAreEqual(u, t, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a 2D 2nd array")
  CHECK(runAreEqual(u, u1, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() different sized arrays")
  CHECK(runAreEqual(u, u, ntol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a negative tolerance")

  /* vuAreOrth() set */
  CHECK(runAreOrthogonal(u, z, tol, TRUE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the unit and zero vectors are orthogonal")
  CHECK(runAreOrthogonal(u, nu, tol, FALSE, vect_ExpectExcept_NoneExp, tracker),
   "ensuring the unit and negative unit vectors are not orthogonal")
  CHECK(runAreOrthogonal(n, u, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() a null 1st array")
  CHECK(runAreOrthogonal(t, u, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() a 2D 1st array")
  CHECK(runAreOrthogonal(u, n, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() a null 2nd array")
  CHECK(runAreOrthogonal(u, t, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() a 2D 2nd array")
  CHECK(runAreOrthogonal(u, u1, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() different sized unit arrays")
  CHECK(runAreOrthogonal(u, u, ntol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() a negative tolerance")
  CHECK(runAreOrthogonal(t, t, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuAreOrth() 2D arrays in both arguments")

  /* vuSchwarzHolds() set */
  CHECK(runSchwarzHolds(u, z, tol, TRUE, vect_ExpectExcept_NoneExp, tracker), 
   "ensuring schwarz holds for the unit and zero vectors")
  CHECK(runSchwarzHolds(n, z, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuSchwarzHolds() a null 1st array")
  CHECK(runSchwarzHolds(t, z, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuSchwarzHolds() a 2D 1st array")
  CHECK(runSchwarzHolds(z, n, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuSchwarzHolds() a null 2nd array")
  CHECK(runSchwarzHolds(u, t, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuSchwarzHolds() a 2D 2nd array")
  CHECK(runSchwarzHolds(u, u1, tol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuSchwarzHolds() different sized unit arrays")
  CHECK(runSchwarzHolds(u, z, ntol, FALSE, vect_ExpectExcept_PreExp, tracker), 
   "passing vuSchwarzHolds() a negative tolerance")

  /* vuTriIneqHolds() set */
  CHECK(runTriangleInequalityHolds(u, z, tol, TRUE, vect_ExpectExcept_NoneExp, 
                                   tracker), 
   "ensuring the triangle inequality holds for the unit and zero vectors")
  CHECK(runTriangleInequalityHolds(n, u, tol, FALSE, vect_ExpectExcept_PreExp, 
                                   tracker), 
   "passing vuTriIneqHolds() a null 1st array")
  CHECK(runTriangleInequalityHolds(t, u, tol, FALSE, vect_ExpectExcept_PreExp, 
                                   tracker), 
   "passing vuTriIneqHolds() a 2D 1st array")
  CHECK(runTriangleInequalityHolds(u, n, tol, FALSE, vect_ExpectExcept_PreExp, 
                                   tracker), 
   "passing vuTriIneqHolds() a null 2nd array")
  CHECK(runTriangleInequalityHolds(u, t, tol, FALSE, vect_ExpectExcept_PreExp, 
                                   tracker), 
   "passing vuTriIneqHolds() a 2D 2nd array")
  CHECK(runTriangleInequalityHolds(u, u1, tol, FALSE, vect_ExpectExcept_PreExp, 
                                   tracker), 
   "passing vuTriIneqHolds() different sized unit vectors")
  CHECK(runTriangleInequalityHolds(u, u, ntol, FALSE, vect_ExpectExcept_PreExp, 
                                   tracker), 
   "passing vuTriIneqHolds() a negative tolerance")

  /* vuNorm() set */
  CHECK(runNorm(u, tol, vect_BadLevel_NoVio, 1.0, vect_ExpectExcept_NoneExp, 
                tracker), 
   "ensuring the unit vector norm is 1.0")
  CHECK(runNorm(n, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
                tracker), 
   "passing vuNorm() a null vector")
  CHECK(runNorm(t, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
                tracker), 
   "passing vuNorm() a 2D array")
  CHECK(runNorm(u, ntol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
                tracker), 
   "passing vuNorm() a negative tolerance")
  CHECK(runNorm(u, tol, vect_BadLevel_NegRes, -5.0, vect_ExpectExcept_PostExp, 
                tracker), 
   "passing vuNorm() badness level for negative result")
  CHECK(runNorm(z, tol, vect_BadLevel_PosRes, 5.0, vect_ExpectExcept_PostExp, 
                tracker), 
   "passing vuNorm() badness level for positive result with zero vector")
  CHECK(runNorm(u, tol, vect_BadLevel_ZeroRes, 0.0, vect_ExpectExcept_PostExp, 
                tracker), 
   "passing vuNorm() badness level for zero result with non-zero vector")

  /* vuDot() set */
  CHECK(runDot(u, z, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_NoneExp, 
               tracker),
   "ensuring the dot product of the unit and zero vectors is 0.0")
  CHECK(runDot(n, u, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() a null 1st array")
  CHECK(runDot(t, u, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() a 2D 1st array")
  CHECK(runDot(u, n, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() a null 2nd array")
  CHECK(runDot(u, t, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() a 2D 2nd array")
  CHECK(runDot(u, u1, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() different sized unit vectors")
  CHECK(runDot(u, u, ntol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() a negative tolerance")
  CHECK(runDot(t, t, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_PreExp, 
               tracker), 
   "passing vuDot() a 2D arrays in both arguments")
  CHECK(runDot(u, u, tol, vect_BadLevel_NegRes, -5.0, vect_ExpectExcept_PostExp,
               tracker), 
   "passing vuDot() badness level for negative result with u=v")
  CHECK(runDot(z, z, tol, vect_BadLevel_PosRes, 5.0, vect_ExpectExcept_PostExp, 
               tracker), 
   "passing vuDot() badness level for positive result with u=v=0")

  /* vuProduct() set */
  CHECK(runProduct(1.0, u, tol, vect_BadLevel_NoVio, u, TRUE, 
                   vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the product of 1 and the unit vector is the unit vector")
  CHECK(runProduct(2.0, u, tol, vect_BadLevel_NoVio, u, FALSE, 
                   vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the product of 2 and the unit vector is not the unit vector")
  CHECK(runProduct(0.0, n, tol, vect_BadLevel_NoVio, n, TRUE, 
                   vect_ExpectExcept_PreExp, tracker), 
   "passing vuProduct() a null array")
  CHECK(runProduct(1.0, t, tol, vect_BadLevel_NoVio, u, FALSE, 
                   vect_ExpectExcept_PreExp, tracker), 
   "passing vuProduct() a 2D array")
  CHECK(runProduct(1.0, u, tol, vect_BadLevel_NullRes, u, TRUE, 
                   vect_ExpectExcept_PostExp, tracker), 
   "passing vuProduct() badness level for null result")
  CHECK(runProduct(1.0, u, tol, vect_BadLevel_TwoDRes, u, TRUE, 
                   vect_ExpectExcept_PostExp, tracker), 
   "passing vuProduct() badness level for 2D result")
  CHECK(runProduct(1.0, u, tol, vect_BadLevel_WrongSizeRes, u, TRUE, 
                   vect_ExpectExcept_PostExp, tracker), 
   "passing vuProduct() badness level for wrong result size")

  /* vuNegate() set */
  CHECK(runNegate(u, tol, vect_BadLevel_NoVio, nu, TRUE, 
                  vect_ExpectExcept_NoneExp, tracker),
   "ensuring the negation of the the unit vector is its negative")
  CHECK(runNegate(u, tol, vect_BadLevel_NoVio, u, FALSE, 
                  vect_ExpectExcept_NoneExp, tracker),
   "ensuring the negation of the unit vector is not the unit vector")
  CHECK(runNegate(n, tol, vect_BadLevel_NoVio, nu, TRUE, 
                  vect_ExpectExcept_PreExp, tracker), 
   "passing vuNegate() a null array")
  CHECK(runNegate(t, tol, vect_BadLevel_NoVio, nu, FALSE, 
                  vect_ExpectExcept_PreExp, tracker), 
   "passing vuNegate() a 2D array")
  CHECK(runNegate(u, tol, vect_BadLevel_NullRes, nu, TRUE, 
                  vect_ExpectExcept_PostExp, tracker), 
   "passing vuNegate() badness level for null result")
  CHECK(runNegate(u, tol, vect_BadLevel_TwoDRes, nu, TRUE, 
                  vect_ExpectExcept_PostExp, tracker), 
   "passing vuNegate() badness level for 2D result")
  CHECK(runNegate(u, tol, vect_BadLevel_WrongSizeRes, nu, TRUE, 
                  vect_ExpectExcept_PostExp, tracker), 
   "passing vuNegate() badness level for wrong size result")

  /* vuNormalize() set */
  CHECK(runNormalize(u, tol, vect_BadLevel_NoVio, u, TRUE, 
                     vect_ExpectExcept_NoneExp, tracker), 
   "ensuring normalize of the unit vector is itself")
  CHECK(runNormalize(u, tol, vect_BadLevel_NoVio, nu, FALSE, 
                     vect_ExpectExcept_NoneExp, tracker), 
   "ensuring normalize of the unit vector is not its negative")
  CHECK(runNormalize(z, tol, vect_BadLevel_NoVio, z, TRUE, 
                     vect_ExpectExcept_DBZExp, tracker), 
   "ensuring normalize of the zero vector raises a DBZ exception")
  CHECK(runNormalize(n, tol, vect_BadLevel_NoVio, n, TRUE, 
                     vect_ExpectExcept_PreExp, tracker), 
   "passing vuNormalize() a null array")
  CHECK(runNormalize(t, tol, vect_BadLevel_NoVio, u, FALSE, 
                     vect_ExpectExcept_PreExp, tracker), 
   "passing vuNormalize() a 2D array")
  CHECK(runNormalize(u, ntol, vect_BadLevel_NoVio, u, TRUE, 
                     vect_ExpectExcept_PreExp, tracker), 
   "passing vuNormalize() a negative tolerance while using the unit vector")
  CHECK(runNormalize(u, tol, vect_BadLevel_NullRes, u, TRUE, 
                     vect_ExpectExcept_PostExp, tracker), 
   "passing vuNormalize() badness level for null result")
  CHECK(runNormalize(u, tol, vect_BadLevel_TwoDRes, u, TRUE, 
                     vect_ExpectExcept_PostExp, tracker), 
   "passing vuNormalize() badness level for 2D result")
  CHECK(runNormalize(u, tol, vect_BadLevel_WrongSizeRes, u, TRUE, 
                     vect_ExpectExcept_PostExp, tracker), 
   "passing vuNormalize() badness level for wrong size result")

  /* vuSum() set (NOTE: tolerance not relevant to vuSum() API.) */
  CHECK(runSum(u, z, tol, vect_BadLevel_NoVio, u, TRUE, 
               vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the sum of unit and zero vectors is the unit vector")
  CHECK(runSum(u, z, tol, vect_BadLevel_NoVio, nu, FALSE, 
               vect_ExpectExcept_NoneExp, tracker),
   "ensuring the sum of unit and zero vectors is not the negative of the unit")
  CHECK(runSum(n, z, tol, vect_BadLevel_NoVio, n, TRUE, 
               vect_ExpectExcept_PreExp, tracker), 
   "passing vuSum() a null 1st array")
  CHECK(runSum(t, n, tol, vect_BadLevel_NoVio, n, FALSE, 
               vect_ExpectExcept_PreExp, tracker), 
   "passing vuSum() a 2D 1st array")
  CHECK(runSum(u, n, tol, vect_BadLevel_NoVio, n, TRUE, 
               vect_ExpectExcept_PreExp, tracker), 
   "passing vuSum() a null 2nd array")
  CHECK(runSum(u, t, tol, vect_BadLevel_NoVio, n, FALSE, 
               vect_ExpectExcept_PreExp, tracker), 
   "passing vuSum() a 2D as second")
  CHECK(runSum(u, u1, tol, vect_BadLevel_NoVio, z, TRUE, 
               vect_ExpectExcept_PreExp, tracker), 
   "passing vuSum() different sized unit vectors")
  CHECK(runSum(u, z, tol, vect_BadLevel_NullRes, u, TRUE, 
               vect_ExpectExcept_PostExp, tracker), 
   "passing vuSum() badness level for null result)")
  CHECK(runSum(u, z, tol, vect_BadLevel_TwoDRes, u, TRUE, 
               vect_ExpectExcept_PostExp, tracker), 
   "passing vuSum() badness level for 2D result")
  CHECK(runSum(u, z, tol, vect_BadLevel_WrongSizeRes, u, TRUE, 
               vect_ExpectExcept_PostExp, tracker), 
   "passing vuSum() badness level for wrong size result")

  /* vuDiff() set (NOTE: tolerance not relevant to vuDiff() API.) */
  CHECK(runDiff(z, u, tol, vect_BadLevel_NoVio, nu, TRUE, 
                vect_ExpectExcept_NoneExp, tracker),
   "ensuring the diff of the zero and unit vectors is the negative unit vector")
  CHECK(runDiff(u, z, tol, vect_BadLevel_NoVio, u, TRUE, 
                vect_ExpectExcept_NoneExp, tracker),
   "ensuring the diff of the unit and zero vectors is the unit vector")
  CHECK(runDiff(u, z, tol, vect_BadLevel_NoVio, nu, FALSE, 
                vect_ExpectExcept_NoneExp, tracker), 
   "ensuring the diff of the unit and zero vectors is not the neg unit vector")
  CHECK(runDiff(n, u, tol, vect_BadLevel_NoVio, n, TRUE, 
                vect_ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a null 1st array")
  CHECK(runDiff(t, u, tol, vect_BadLevel_NoVio, u, FALSE, 
                vect_ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a 2D 1st array")
  CHECK(runDiff(u, n, tol, vect_BadLevel_NoVio, n, TRUE, 
                vect_ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a null 2nd array")
  CHECK(runDiff(u, t, tol, vect_BadLevel_NoVio, u, FALSE, 
                vect_ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a 2D 2nd array")
  CHECK(runDiff(u, u1, tol, vect_BadLevel_NoVio, u, FALSE, 
                vect_ExpectExcept_PreExp, tracker), 
   "passing vuDiff() different sized vectors")
  CHECK(runDiff(z, u, tol, vect_BadLevel_NullRes, nu, TRUE, 
                vect_ExpectExcept_PostExp, tracker), 
   "passing vuDiff() badness level for null result")
  CHECK(runDiff(z, u, tol, vect_BadLevel_TwoDRes, nu, TRUE, 
                vect_ExpectExcept_PostExp, tracker), 
   "passing vuDiff() badness level for 2D result")
  CHECK(runDiff(z, u, tol, vect_BadLevel_WrongSizeRes, nu, TRUE, 
                vect_ExpectExcept_PostExp, tracker), 
   "passing vuDiff() badness level for wrong size result")

  vect_Utils__dump_stats_static(statsFile, "After full checking", 
                                  &exception); SIDL_CHECK(exception);

  /****************************************************************
   * Now check preconditions only.  Only need three checks:
   *   1) successful execution;
   *   2) precondition violation that is not caught but is
   *      okay anyway; and
   *   3) postcondition violation that is caught.
   ****************************************************************/
  printf("\nCOMMENT: *** ENABLE PRECONDITION ENFORCEMENT ONLY ***\n\n");
  sidl_EnfPolicy_setEnforceAll(sidl_ContractClass_PRECONDS, FALSE,
                               &exception); SIDL_CHECK(exception);

  CHECK(runDot(u, z, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_NoneExp, 
               tracker),
   "ensuring the dot product of the unit and zero vectors is 0.0")
  CHECK(runDot(u, u, ntol, vect_BadLevel_NoVio, 1.0, vect_ExpectExcept_PreExp, 
               tracker),
   "passing vuDot() a negative tolerance")
  CHECK(runDot(u, u, tol, vect_BadLevel_NegRes, -5.0, vect_ExpectExcept_NoneExp,
               tracker),
   "passing vuDot() badness level for negative result with u=v")

  vect_Utils__dump_stats_static(statsFile, "After precondition checking", 
                                  &exception); SIDL_CHECK(exception);

  /****************************************************************
   * Now check postconditions only.  Only need three checks:
   *   1) successful execution;
   *   2) precondition violation that gets caught; and
   *   3) postcondition violation that is not caught.
   ****************************************************************/
  printf("\nCOMMENT: *** ENABLE POSTCONDITION ENFORCEMENT ONLY ***\n\n");
  sidl_EnfPolicy_setEnforceAll(sidl_ContractClass_POSTCONDS, FALSE, 
                               &exception); SIDL_CHECK(exception);

  CHECK(runDot(u, z, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_NoneExp, 
               tracker),
   "ensuring the dot product of the unit and zero vectors is 0.0")
  CHECK(runDot(u, u, ntol, vect_BadLevel_NoVio, 1.0, vect_ExpectExcept_NoneExp, 
               tracker),
   "passing vuDot() a negative tolerance")
  CHECK(runDot(u, u, tol, vect_BadLevel_NegRes, -5.0, vect_ExpectExcept_PostExp,
               tracker),
   "passing vuDot() badness level for negative result with u=v")

  vect_Utils__dump_stats_static(statsFile, "After Postcondition checking", 
                                  &exception); SIDL_CHECK(exception);

  /****************************************************************
   * Now make sure contract violations are not caught when contract
   * enforcement turned off.  Do this for each type of violation
   * for every method.
   ****************************************************************/
  printf("\nCOMMENT: *** DISABLE ALL CONTRACT ENFORCEMENT ***\n\n");
  sidl_EnfPolicy_setEnforceNone(FALSE, &exception); SIDL_CHECK(exception);

  CHECK(runIsZero(n, tol, FALSE, vect_ExpectExcept_NoneExp, tracker),
   "passing vuIsZero() a null array - no precondition violation")
  CHECK(runIsUnit(n, tol, FALSE, vect_ExpectExcept_NoneExp, tracker),
   "passing vuIsUnit() a null array - no precondition violation")
  CHECK(runAreEqual(n, u, tol, FALSE, vect_ExpectExcept_NoneExp, tracker),
   "passing vuAreEqual() a null 1st array - no precondition violation")
  CHECK(runAreOrthogonal(n, u, tol, FALSE, vect_ExpectExcept_NoneExp, tracker),
   "passing vuAreOrth() a null 1st array - no precondition violation")
  CHECK(runSchwarzHolds(n, z, tol, FALSE, vect_ExpectExcept_NoneExp, tracker),
   "passing vuSchwarzHolds() a null 1st array - no precondition violation")
  CHECK(runTriangleInequalityHolds(n, u, tol, FALSE, vect_ExpectExcept_NoneExp, 
                                   tracker),
   "passing vuTriIneqHolds() a null 1st array - no precondition violation")
  CHECK(runNorm(n, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_NoneExp, 
                tracker),
   "passing vuNorm() a null vector - no precondition violation")
  CHECK(runNorm(u, tol, vect_BadLevel_NegRes, -5.0, vect_ExpectExcept_NoneExp, 
               tracker),
   "passing vuNorm() badness level for negative result - no post vioation")
  CHECK(runDot(n, u, tol, vect_BadLevel_NoVio, 0.0, vect_ExpectExcept_NoneExp, 
               tracker),
   "passing vuDot() a null 1st array - no precondition violation")
  CHECK(runDot(u, u, tol, vect_BadLevel_NegRes, -5.0, vect_ExpectExcept_NoneExp,
               tracker),
  "passing vuDot() badness level for negative result with u=v - no post vio.")
  CHECK(runProduct(0.0, n, tol, vect_BadLevel_NoVio, n, TRUE, 
                   vect_ExpectExcept_NoneExp, tracker),
   "passing vuProduct() a null array - no precondition violation")
  CHECK(runProduct(1.0, u, tol, vect_BadLevel_NullRes, u, FALSE, 
                   vect_ExpectExcept_NoneExp, tracker),
   "passing vuProduct() badness level for null result - no post violation")
  CHECK(runNegate(n, tol, vect_BadLevel_NoVio, n, TRUE, 
                  vect_ExpectExcept_NoneExp, tracker),
   "passing vuNegate() a null array - no precondition violation")
  CHECK(runNegate(u, tol, vect_BadLevel_NegRes, nu, FALSE, 
                  vect_ExpectExcept_NoneExp, tracker),
   "passing vuNegate() badness level for null result - no post violation")
  CHECK(runNormalize(n, tol, vect_BadLevel_NoVio, n, TRUE, 
                     vect_ExpectExcept_NoneExp, tracker),
   "passing vuNormalize() a null array - no precondition violation")
  CHECK(runNormalize(u, tol, vect_BadLevel_NullRes, u, FALSE, 
               vect_ExpectExcept_NoneExp, tracker),
  "passing vuNormalize() a badness level for null result - no post violation")
  CHECK(runSum(n, z, tol, vect_BadLevel_NoVio, n, TRUE, 
               vect_ExpectExcept_NoneExp, tracker),
   "passing vuSum() a null 1st array - no precondition violation")
  CHECK(runSum(u, z, tol, vect_BadLevel_NullRes, u, FALSE, 
               vect_ExpectExcept_NoneExp, tracker),
   "passing vuSum() a badness level for null result - no post violation")
  CHECK(runDiff(n, u, tol, vect_BadLevel_NoVio, n, TRUE, 
                vect_ExpectExcept_NoneExp, tracker),
   "passing vuDiff() a null 1st array - no precondition violation")
  CHECK(runDiff(z, u, tol, vect_BadLevel_NullRes, nu, FALSE, 
                vect_ExpectExcept_NoneExp, tracker),
   "passing vuDiff() badness level for null result - no post violation")

  vect_Utils__dump_stats_static(statsFile, "After no checking", 
                                  &exception); SIDL_CHECK(exception);

  sidl__array_deleteRef((struct sidl__array*)t);
  sidl__array_deleteRef((struct sidl__array*)u);
  sidl__array_deleteRef((struct sidl__array*)nu);
  sidl__array_deleteRef((struct sidl__array*)u1);
  sidl__array_deleteRef((struct sidl__array*)z);
  
  
  synch_RegOut_close(tracker, &exception); SIDL_CHECK(exception);
  synch_RegOut_deleteRef(tracker, &exception); SIDL_CHECK(exception);

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
