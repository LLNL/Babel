/*
 * File:        knapsacktest.c
 * Copyright:   (c) 2011 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 6171 $
 * Date:        $Date: 2011-01-05 16:39:28 -0700 (Wed, 05 Jan 2011) $
 * Description: C invariants regression tests
 *
 */
#include <stdio.h>
#include <math.h>
#include <string.h>
#include "sidl_EnfPolicy.h"
#include "sidl_String.h"
#include "synch.h"
#include "knapsack_gKnapsack.h"
#include "knapsack_iKnapsack.h"
#include "knapsack_npKnapsack.h"
#include "knapsack_nwKnapsack.h"
#include "knapsack_ExpectExcept.h"
#include "knapsack_kExcept.h"
#include "sidl_Exception.h"

/* 
 * The following is a "hack" to get around not being able to include
 * non-generated header and source files in the regression build process.
 */
/*
 * Maximum number of weights.
 */
#define MAX_WEIGHTS 10


/* Ensure the following is set to 0 BEFORE commiting to production */
#define TEST_DEBUG 1

#ifndef FALSE
#define FALSE 0
#endif
#ifndef TRUE
#define TRUE 1
#endif

#if TEST_DEBUG
#define START_DEBUG(RES, EXC) {\
  char sdMsg[128]; \
  sidl_BaseInterface sdExc; \
  sprintf(sdMsg, "** DEBUG:  resExp=%d, expectExc=%d", RES, EXC); \
  synch_RegOut_writeComment(tracker, sdMsg, &sdExc); \
}
#define START_DEBUGD(RES, TOL, EXC) {\
  char sddMsg[128]; \
  sidl_BaseInterface sddExc; \
  sprintf(sddMsg, "** DEBUG:  res=%12.9f, tol=%12.9f, expectExc=%d", RES, TOL, EXC); \
  synch_RegOut_writeComment(tracker, sddMsg, &sddExc); \
}
#define DEBUG_MSG(MSG) {\
  char sddMsg[256]; \
  sidl_BaseInterface sddExc; \
  sprintf(sddMsg, "** DEBUG:  %s", MSG); \
  synch_RegOut_writeComment(tracker, sddMsg, &sddExc); \
}
#define RESULT_DEBUG(MSG, X) {\
  char rdMsg[128]; \
  sidl_BaseInterface rdExc; \
  sprintf(rdMsg, "** DEBUG:  %s%d", MSG, X); \
  synch_RegOut_writeComment(tracker, rdMsg, &rdExc); \
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
#define DEBUG_MSG(MSG)
#define RESULT_DEBUG(MSG, X)
#define END_DEBUG(OK)
#endif /* TEST_DEBUG */


#define CHECK(FUNC,COMMENT) {\
  declare_part(tracker, &part_no); \
  synch_RegOut_writeComment(tracker, COMMENT, &exception); \
  result = (FUNC) ? synch_ResultType_PASS : synch_ResultType_FAIL; \
  end_part(tracker, part_no, result); \
}

#define EXCEPTION_CHECKS(EXCV, EXC_EXPECT, RET) {\
  if ((EXCV) != NULL) {\
    if (SIDL_CATCH((EXCV), "knapsack.kBadWeightExcept")) {\
      RET = (EXC_EXPECT == knapsack_ExpectExcept_BWExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "knapsack.kSizeExcept")) {\
      RET = (EXC_EXPECT == knapsack_ExpectExcept_SizeExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "knapsack.kExcept")) {\
      RET = (EXC_EXPECT == knapsack_ExpectExcept_ExcExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "sidl.InvViolation")) {\
      RET = (EXC_EXPECT == knapsack_ExpectExcept_InvExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "sidl.PreViolation")) {\
      RET = (EXC_EXPECT == knapsack_ExpectExcept_PreExp); \
      CLEANUP(EXCV, RET)\
    } else if (SIDL_CATCH((EXCV), "sidl.PostViolation")) {\
      RET = (EXC_EXPECT == knapsack_ExpectExcept_PostExp); \
      CLEANUP(EXCV, RET)\
    } else {\
      goto EXIT;\
    }\
  }\
}

#define RESULT_CHECKS(EXCV, EXC_EXPECT, RES, RES_EXPECT) {\
  RESULT_DEBUG("x=", RES)\
  int _ret = FALSE;\
  EXCEPTION_CHECKS(EXCV, EXC_EXPECT, _ret) \
  if ( ((EXCV) == NULL) && (EXC_EXPECT == knapsack_ExpectExcept_NoneExp) ) {\
    _ret = (RES == RES_EXPECT);\
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


enum knapsackTypeEnum {
  knapsackTypeEnum_GOOD,
  knapsackTypeEnum_NON_POS,
  knapsackTypeEnum_NO_WT
};

void 
declare_part(synch_RegOut tracker,  int * part_no ) {
  sidl_BaseInterface exception = NULL;
  synch_RegOut_startPart(tracker, ++(*part_no), &exception);
  if (exception) {
    sidl_BaseInterface tae;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &tae);
    }
    sidl_BaseInterface_deleteRef(exception, &tae);
  }
}

void 
end_part( synch_RegOut tracker, int part_no, enum synch_ResultType__enum result)
{
  sidl_BaseInterface exception = NULL;
  synch_RegOut_endPart(tracker, part_no, result, &exception);
  if (exception) {
    sidl_BaseInterface tae;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &tae);
    }
    sidl_BaseInterface_deleteRef(exception, &tae);
  }
}

static void 
printExceptionNote(sidl_SIDLException _ex)
{
  if (_ex != NULL) {
    sidl_BaseInterface tae;
    char* msg = NULL;

    msg = sidl_SIDLException_getNote(_ex, &tae);
    printf("  %s\n", msg);
    sidl_String_free(msg);
  }
}

int 
runBaseCase(
  enum knapsackTypeEnum const      tp, 
  struct sidl_int__array          *wArr,
  int                              tgt,
  int                              resExp,
  enum knapsack_ExpectExcept__enum excExp, 
  synch_RegOut                     tracker)
{
  sidl_BaseInterface _ex;
  knapsack_iKnapsack k;
  int                x;

  START_DEBUG(resExp, excExp)
  switch (tp) {
    case knapsackTypeEnum_GOOD:
      DEBUG_MSG("Instantiating gKnapsack");
      k = (knapsack_iKnapsack)knapsack_gKnapsack__create(&_ex); SIDL_CHECK(_ex);
      DEBUG_MSG("Initializing gKnapsack");
      knapsack_gKnapsack_initialize((knapsack_gKnapsack)k, wArr, 
                                    (sidl_BaseInterface*)(&_ex)); 
      DEBUG_MSG("Checking for gKnapsack solution");
      x = knapsack_gKnapsack_hasSolution((knapsack_gKnapsack)k, tgt, 
                                         (sidl_BaseInterface*)(&_ex)); 
      DEBUG_MSG("Done with gKnapsack");
      break;
    case knapsackTypeEnum_NON_POS:
      k = (knapsack_iKnapsack)knapsack_npKnapsack__create(&_ex);SIDL_CHECK(_ex);
      knapsack_npKnapsack_initialize((knapsack_npKnapsack)k, wArr, 
                                     (sidl_BaseInterface*)(&_ex)); 
      x = knapsack_npKnapsack_hasSolution((knapsack_npKnapsack)k, tgt, 
                                          (sidl_BaseInterface*)(&_ex)); 
      break;
    case knapsackTypeEnum_NO_WT:
      k = (knapsack_iKnapsack)knapsack_nwKnapsack__create(&_ex);SIDL_CHECK(_ex);
      knapsack_nwKnapsack_initialize((knapsack_nwKnapsack)k, wArr, 
                                     (sidl_BaseInterface*)(&_ex)); 
      x = knapsack_nwKnapsack_hasSolution((knapsack_nwKnapsack)k, tgt, 
                                          (sidl_BaseInterface*)(&_ex)); 
      break;
    default:
      RESULT_DEBUG("runBaseCase: Invalid class option: ", tp)
      x = 0;
      break;
  }
  RESULT_CHECKS(_ex, excExp, x, resExp)
  {
    sidl_BaseInterface tae = NULL;
    if (k) {
      knapsack_iKnapsack_deleteRef(k, &tae);
    }
  }

  EXIT:;
    CLEANUP(_ex, FALSE)
    {
      sidl_BaseInterface tae = NULL;
      if (k) {
        knapsack_iKnapsack_deleteRef(k, &tae);
      }
    }
}  /* runBaseCase */



/*************************** main ***************************/
int 
main(int argc, char**argv)
{ 
  sidl_BaseInterface exception = NULL;

  enum synch_ResultType__enum result  = synch_ResultType_PASS; 
  int                         part_no = 0;


  int  i;

  struct sidl_int__array *good  = NULL;
  struct sidl_int__array *gLong  = NULL;
  struct sidl_int__array *nArr  = NULL;  /* The null array! */
  struct sidl_int__array *negEnd = NULL;
  struct sidl_int__array *zStart = NULL;
  struct sidl_int__array *zMid = NULL;
  struct sidl_int__array *zEnd = NULL;

  synch_RegOut tracker = synch_RegOut__create(
                                     &exception); SIDL_CHECK(exception);

  int *v = (int *)malloc(MAX_WEIGHTS*sizeof(int));
  int *vp1 = (int *)malloc((MAX_WEIGHTS+1)*sizeof(int));
  int total = 0;
  int val = 0;
  for (i=0; i < MAX_WEIGHTS; i++) {
    val = val + 2;
    v[i] = val;
    vp1[i] = val;
    total += val;
  }
  vp1[i] = val + 2;

  good = sidl_int__array_create1d(MAX_WEIGHTS);
  memcpy(sidl_int__array_first(good), v, (size_t)(MAX_WEIGHTS*sizeof(int)));

  gLong = sidl_int__array_create1d(MAX_WEIGHTS);
  memcpy(sidl_int__array_first(good),vp1,(size_t)((MAX_WEIGHTS+1)*sizeof(int)));

  negEnd = sidl_int__array_create1d(MAX_WEIGHTS);
  memcpy(sidl_int__array_first(negEnd), v, (size_t)(MAX_WEIGHTS*sizeof(int)));
  sidl_int__array_set1(negEnd, MAX_WEIGHTS-1, -1);

  zEnd = sidl_int__array_create1d(MAX_WEIGHTS);
  memcpy(sidl_int__array_first(zEnd), v, (size_t)(MAX_WEIGHTS*sizeof(int)));
  sidl_int__array_set1(zEnd, MAX_WEIGHTS-1, 0);

  zMid = sidl_int__array_create1d(MAX_WEIGHTS);
  memcpy(sidl_int__array_first(zMid), v, (size_t)(MAX_WEIGHTS*sizeof(int)));
  sidl_int__array_set1(zMid, (int)(MAX_WEIGHTS/2), 0);

  zStart = sidl_int__array_create1d(MAX_WEIGHTS);
  memcpy(sidl_int__array_first(zStart), v, (size_t)((MAX_WEIGHTS)*sizeof(int)));
  sidl_int__array_set1(zStart, 0, 0);


  (void) argc;
  (void) argv;
  synch_RegOut_setExpectations(tracker, 81, &exception); SIDL_CHECK(exception);

  /*
   ***********************************************************************
   * FULL CONTRACT ENFORCEMENT
   ***********************************************************************
   */
  printf("\nCOMMENT: *** ENABLE CONTRACT ENFORCEMENT ***\n\n");
  sidl_EnfPolicy_setEnforceAll(sidl_ContractClass_ALLCLASSES, TRUE, 
                               &exception); SIDL_CHECK(exception);


  /* gKnapsack.BaseCase.Full set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Full: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total-1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Full: good: do not expect solution for total-1")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: good: precondition vio (neg. target)")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.Full: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, zStart, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, zMid, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, zEnd, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected")

/*
 * TODO/TBD:  This will either be an invariant or postcondition violation,
 * depending on ordering in generated code
 */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "gKnapsack.BaseCase.Full: nArr: invariants vio/no solution expected")


  /* npKnapsack.BaseCase.Full set */
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Full: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, total-1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Full: good: do not expect solution for total-1")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, -1, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: good: precondition vio (neg. target)")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.Full: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zStart, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zMid, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zEnd, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, negEnd, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected")

/*
 * TODO/TBD:  This will either be an invariant or postcondition violation,
 * depending on ordering in generated code
 */
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, nArr, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Full: nArr: invariants/no solution expected")


  /* nwKnapsack.BaseCase.Full set */
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Full: good: do not expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, total-1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Full: good: do not expect solution for total-1")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, -1, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: good: precondition vio (neg. target)")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.Full: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, zStart, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, zMid, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, zEnd, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, negEnd, total, FALSE,
                    knapsack_ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected")

/*
 * TODO/TBD:  This will either be an invariant or postcondition violation,
 * depending on ordering in generated code
 */
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, nArr, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "nwKnapsack.BaseCase.Full: nArr: invariant vio/no solution expected")


  /*
   ***********************************************************************
   * POSTCONDITION-ONLY ENFORCEMENT
   ***********************************************************************
   */
  printf("\nCOMMENT: *** POSTCONDITION-ONLY ENFORCEMENT ***\n\n");
  sidl_EnfPolicy_setEnforceAll(sidl_ContractClass_POSTCONDS, FALSE, 
                               &exception); SIDL_CHECK(exception);

  /* gKnapsack.BaseCase.Post set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Post: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Post: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.Post: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, FALSE,
                    knapsack_ExpectExcept_BWExp, tracker), 
   "gKnapsack.BaseCase.Post: negEnd: bad weight exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, FALSE,
                    knapsack_ExpectExcept_PostExp, tracker), 
   "gKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected")


  /* npKnapsack.BaseCase.Post set */
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.Post: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zStart, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: zStart: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zMid, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: zMid: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zEnd, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: zEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, negEnd, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: negEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, nArr, total, FALSE,
                    knapsack_ExpectExcept_PostExp, tracker), 
   "npKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected")


  /* nwKnapsack.BaseCase.Post set */
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, total, FALSE,
                    knapsack_ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: good: postcondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, -1, FALSE,
                    knapsack_ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: good: postcondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.Post: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, negEnd, total, FALSE,
                    knapsack_ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: negEnd: postcondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, nArr, total, FALSE,
                    knapsack_ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected")


  /*
   ***********************************************************************
   * INVARIANTS-ONLY ENFORCEMENT
   ***********************************************************************
   */
  printf("\nCOMMENT: *** INVARIANTS-ONLY ENFORCEMENT ***\n\n");
  sidl_EnfPolicy_setEnforceAll(sidl_ContractClass_INVARIANTS, FALSE, 
                               &exception); SIDL_CHECK(exception);

  /* gKnapsack.BaseCase.Inv set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Inv: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.Inv: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, FALSE,
                    knapsack_ExpectExcept_BWExp, tracker), 
   "gKnapsack.BaseCase.Inv: negEnd: bad weight exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "gKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected")


  /* npKnapsack.BaseCase.Inv set */
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Inv: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.Inv: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zStart, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: zStart: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zMid, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: zMid: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zEnd, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: zEnd: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, negEnd, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: negEnd: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, nArr, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected")


  /* nwKnapsack.BaseCase.Inv set */
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.Inv: gLong: size exception expected")

  /*
   * WARNING:  The following assumes the non-positive weight does get added to
   * the knapsack.
   */
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, negEnd, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "nwKnapsack.BaseCase.Inv: negEnd: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, nArr, total, FALSE,
                    knapsack_ExpectExcept_InvExp, tracker), 
   "nwKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected")


  /*
   ***********************************************************************
   * NO CONTRACT ENFORCEMENT
   ***********************************************************************
   */
  printf("\nCOMMENT: *** DISABLE CONTRACT ENFORCEMENT ***\n\n");
  sidl_EnfPolicy_setEnforceNone(FALSE, &exception); SIDL_CHECK(exception);


  /* gKnapsack.BaseCase.None set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.None: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.None: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, FALSE,
                    knapsack_ExpectExcept_BWExp, tracker), 
   "gKnapsack.BaseCase.None: negEnd: bad weight exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.None: nArr: invariants vio/no solution expected")


  /* npKnapsack.BaseCase.None set */
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, total, TRUE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.None: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zStart, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: zStart: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zMid, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: zMid: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, zEnd, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: zEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, negEnd, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: negEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NON_POS, nArr, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: nArr: no solution expected")


  /* nwKnapsack.BaseCase.None set */
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, good, -1, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, gLong, total, FALSE,
                    knapsack_ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.None: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, negEnd, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: negEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NO_WT, nArr, total, FALSE,
                    knapsack_ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: nArr: no solution expected")

  /*
   * TODO/TBD:  Other possible cases:
   * 1) create, initialize, and explicitly call hasWeights
      x = knapsack_gKnapsack_hasWeights(k, w, (sidl_BaseInterface*)(&_ex)); 

   * 2) create, initialize, and explicitly call onlyPosWeights
      x = knapsack_gKnapsack_onlyPosWeights(k, (sidl_BaseInterface*)(&_ex)); 
   */

  /*
   * Clean up
   */
  sidl__array_deleteRef((struct sidl__array*)good);
  sidl__array_deleteRef((struct sidl__array*)gLong);
  sidl__array_deleteRef((struct sidl__array*)negEnd);
  sidl__array_deleteRef((struct sidl__array*)zEnd);
  sidl__array_deleteRef((struct sidl__array*)zMid);
  sidl__array_deleteRef((struct sidl__array*)zStart);

  synch_RegOut_close(tracker, &exception); SIDL_CHECK(exception);
  synch_RegOut_deleteRef(tracker, &exception); SIDL_CHECK(exception);

  return 0;
 EXIT:
  {
    sidl_BaseInterface tae = NULL;
    if (tracker) {
      sidl_BaseInterface exception2 = NULL;
      synch_RegOut_forceFailure(tracker, &exception2);
      if (exception2) {
        puts("TEST_RESULT FAIL\n");
        sidl_BaseInterface_deleteRef(exception2, &tae);
      }
      synch_RegOut_deleteRef(tracker, &tae);
    }
    sidl_BaseInterface_deleteRef(exception, &tae);
    return -1;
  }
}
