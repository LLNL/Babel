/*
 * File:        knapsacktest.cxx
 * Copyright:   (c) 2011 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 6171 $
 * Date:        $Date: 2011-01-05 16:39:28 -0700 (Wed, 05 Jan 2011) $
 * Description: C++ invariants regression tests
 *
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "knapsack_gKnapsack.hxx"
#include "knapsack_iKnapsack.hxx"
#include "knapsack_npKnapsack.hxx"
#include "knapsack_nwKnapsack.hxx"
#include "knapsack_ExpectExcept.hxx"
#include "knapsack_kExcept.hxx"
#include "knapsack_kBadWeightExcept.hxx"
#include "knapsack_kSizeExcept.hxx"
#include "sidl_EnfPolicy.hxx"
#include "sidl_Exception.h"
#include "sidl_InvViolation.hxx"
#include "sidl_PreViolation.hxx"
#include "sidl_PostViolation.hxx"
#include "synch.hxx"

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif /* SIDL_USE_UCXX */
//#include <stddef.h>


/* 
 * The following is a "hack" to get around not being able to include
 * non-generated header and source files in the regression build process.
 */
/*
 * Maximum number of weights.
 */
#define MAX_WEIGHTS 10

/* Ensure the following is set to 0 BEFORE commiting to production */
#define TEST_DEBUG 0


//fast native function calls don't suppport contracts (yet?)
#ifdef SIDL_VECT_UTILS_FASTCALL_ENABLED
#define FAIL_RESULT synch::ResultType_XFAIL
#else
#define FAIL_RESULT synch::ResultType_FAIL
#endif

#define CHECK(FUNC, COMMENT) {\
  tracker.startPart(++part_no); \
  tracker.writeComment(COMMENT); \
  result = (FUNC) ? synch::ResultType_PASS : FAIL_RESULT; \
  tracker.endPart(part_no, result); \
}

#if TEST_DEBUG
#define START_DEBUG(EXP, EXC) {\
  char sdMsg[128]; \
  sprintf(sdMsg, "** DEBUG:  resExp=%d, expectExc=%d", EXP, EXC); \
  tracker.writeComment(sdMsg); \
}
#define END_DEBUG(RES, EXP, OK) {\
  char edMsg[128]; \
  sprintf(edMsg, "** DEBUG:  res=%d == resExp=%d? %d", RES, EXP, OK); \
  tracker.writeComment(edMsg); \
}
#define BAD_TYPE(MSG, TP) {\
  char rdMsg[128]; \
  sprintf(rdMsg, "** DEBUG: %s: tp=%d", MSG, TP); \
  tracker.writeComment(rdMsg); \
}
#else
#define START_DEBUG(RES, EXC) 
#define END_DEBUG(RES, EXP, OK)
#define BAD_TYPE(MSG, TP) 
#endif /* TEST_DEBUG */


enum knapsackTypeEnum {
  knapsackTypeEnum_GOOD,
  knapsackTypeEnum_NONPOS,
  knapsackTypeEnum_NOWT
};


bool 
runBaseCase(
  enum knapsackTypeEnum const  tp, 
  const ::sidl::array<int>     wArr,
  int                          tgt,
  bool                         resExp,
  enum knapsack::ExpectExcept  excExp, 
  synch::RegOut                tracker)
{
  knapsack::iKnapsack         k;
  bool                        ok = false;
  bool                        res = false;
  enum knapsack::ExpectExcept exc = knapsack::ExpectExcept_NoneExp;

  START_DEBUG(resExp, excExp)
  try {
    switch (tp) {
      case knapsackTypeEnum_GOOD:
        k = knapsack::gKnapsack::_create();
        k.initialize(wArr);
        res = k.hasSolution(tgt);
        break;
      case knapsackTypeEnum_NONPOS:
        k = knapsack::npKnapsack::_create();
        k.initialize(wArr);
        res = k.hasSolution(tgt);
        break;
      case knapsackTypeEnum_NOWT:
        k = knapsack::nwKnapsack::_create();
        k.initialize(wArr);
        res = k.hasSolution(tgt);
        break;
      default:
        BAD_TYPE("runBaseCase: Invalid class option", tp)
        res = false;
        break;
    }
    ok = (res == resExp);
  } catch ( ::sidl::PreViolation preExc ) {
    tracker.writeComment(preExc.getNote());
    exc = knapsack::ExpectExcept_PreExp;
  } catch ( ::sidl::PostViolation postExc ) {
    tracker.writeComment(postExc.getNote());
    exc = knapsack::ExpectExcept_PostExp;
  } catch ( ::sidl::InvViolation invExc ) {
    tracker.writeComment(invExc.getNote());
    exc = knapsack::ExpectExcept_InvExp;
  } catch ( knapsack::kSizeExcept szExc ) {
    tracker.writeComment(szExc.getNote());
    exc = knapsack::ExpectExcept_SizeExp;
  } catch ( knapsack::kBadWeightExcept bwExc ) {
    tracker.writeComment(bwExc.getNote());
    exc = knapsack::ExpectExcept_BWExp;
  } catch ( knapsack::kExcept kExc ) {
    tracker.writeComment(kExc.getNote());
    exc = knapsack::ExpectExcept_ExcExp;
  } catch (...) {\
    (tracker).writeComment("Caught unexpected exception");\
    exc = knapsack::ExpectExcept_NoneExp;
  } 
  END_DEBUG(res, resExp, ok)

  if (k._not_nil()) {
    k.deleteRef();
  }

  return ok || (exc == excExp);
}  /* runBaseCase */

/*************************** main ***************************/
int 
main(int argc, char**argv)
{ 
  synch::ResultType result  = synch::ResultType_PASS; 
  synch::RegOut     tracker = synch::RegOut::_create();
  int               part_no = 0;

  int32_t i;

  ::sidl::array<int> good ;
  ::sidl::array<int> gLong;
  ::sidl::array<int> nArr;  /* NULL/NIL array */
  ::sidl::array<int> negEnd;
  ::sidl::array<int> zStart;
  ::sidl::array<int> zMid;
  ::sidl::array<int> zEnd;


/* TODO/TLD:  Clean up array creation and initialization */
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

  good = ::sidl::array<int>::create1d(MAX_WEIGHTS);
  memcpy(good.first(), v, (size_t)(MAX_WEIGHTS*sizeof(int)));

  gLong = ::sidl::array<int>::create1d(MAX_WEIGHTS);
  memcpy(gLong.first(), vp1, (size_t)((MAX_WEIGHTS+1)*sizeof(int)));

  negEnd = ::sidl::array<int>::create1d(MAX_WEIGHTS);
  memcpy(negEnd.first(), v, (size_t)(MAX_WEIGHTS*sizeof(int)));
  negEnd.set(MAX_WEIGHTS-1, -1);

  zEnd = ::sidl::array<int>::create1d(MAX_WEIGHTS);
  memcpy(zEnd.first(), v, (size_t)(MAX_WEIGHTS*sizeof(int)));
  zEnd.set(MAX_WEIGHTS-1, 0);

  zMid = ::sidl::array<int>::create1d(MAX_WEIGHTS);
  memcpy(zMid.first(), v, (size_t)(MAX_WEIGHTS*sizeof(int)));
  zMid.set((int)(MAX_WEIGHTS/2), 0);

  zStart = ::sidl::array<int>::create1d(MAX_WEIGHTS);
  memcpy(zStart.first(), v, (size_t)((MAX_WEIGHTS)*sizeof(int)));
  zStart.set(0, 0);


  (void) argc;
  (void) argv;
  tracker.setExpectations(81);

  /*
   ***********************************************************************
   * FULL CONTRACT ENFORCEMENT
   ***********************************************************************
   */
  tracker.writeComment("*** ENABLE CONTRACT ENFORCEMENT ***");
  ::sidl::EnfPolicy::setEnforceAll(::sidl::ContractClass_ALLCLASSES, true);


  /* gKnapsack.BaseCase.Full set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Full: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total-1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Full: good: do not expect solution for total-1")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: good: precondition vio (neg. target)")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.Full: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, zStart, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, zMid, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, zEnd, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "gKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected")

// TODO/TBD:  This will either be an invariant or postcondition violation,
// depending on ordering in generated code
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "gKnapsack.BaseCase.Full: nArr: invariants vio/no solution expected")


  /* npKnapsack.BaseCase.Full set */
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Full: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, total-1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Full: good: do not expect solution for total-1")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, -1, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: good: precondition vio (neg. target)")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.Full: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zStart, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zMid, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zEnd, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, negEnd, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "npKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected")

// TODO/TBD:  This will either be an invariant or postcondition violation,
// depending on ordering in generated code
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, nArr, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Full: nArr: invariants/no solution expected")


  /* nwKnapsack.BaseCase.Full set */
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Full: good: do not expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, total-1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Full: good: do not expect solution for total-1")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, -1, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: good: precondition vio (neg. target)")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.Full: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, zStart, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: zStart: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, zMid, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: zMid: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, zEnd, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: zEnd: precondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, negEnd, total, false,
                    knapsack::ExpectExcept_PreExp, tracker), 
   "nwKnapsack.BaseCase.Full: negEnd: precondition vio/no solution expected")

// TODO/TBD:  This will either be an invariant or postcondition violation,
// depending on ordering in generated code
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, nArr, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "nwKnapsack.BaseCase.Full: nArr: invariant vio/no solution expected")


  /*
   ***********************************************************************
   * POSTCONDITION-ONLY ENFORCEMENT
   ***********************************************************************
   */
  tracker.writeComment("*** POSTCONDITION-ONLY ENFORCEMENT ***");
  ::sidl::EnfPolicy::setEnforceAll(::sidl::ContractClass_POSTCONDS, false);

  /* gKnapsack.BaseCase.Post set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Post: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Post: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.Post: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, false,
                    knapsack::ExpectExcept_BWExp, tracker), 
   "gKnapsack.BaseCase.Post: negEnd: bad weight exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, false,
                    knapsack::ExpectExcept_PostExp, tracker), 
   "gKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected")


  /* npKnapsack.BaseCase.Post set */
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.Post: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zStart, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: zStart: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zMid, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: zMid: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zEnd, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: zEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, negEnd, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Post: negEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, nArr, total, false,
                    knapsack::ExpectExcept_PostExp, tracker), 
   "npKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected")


  /* nwKnapsack.BaseCase.Post set */
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, total, false,
                    knapsack::ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: good: postcondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, -1, false,
                    knapsack::ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: good: postcondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.Post: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, negEnd, total, false,
                    knapsack::ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: negEnd: postcondition vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, nArr, total, false,
                    knapsack::ExpectExcept_PostExp, tracker), 
   "nwKnapsack.BaseCase.Post: nArr: postcondition vio/no solution expected")


  /*
   ***********************************************************************
   * INVARIANTS-ONLY ENFORCEMENT
   ***********************************************************************
   */
  tracker.writeComment("*** INVARIANTS-ONLY ENFORCEMENT ***");
  ::sidl::EnfPolicy::setEnforceAll(::sidl::ContractClass_INVARIANTS, false);

  /* gKnapsack.BaseCase.Inv set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Inv: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.Inv: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, false,
                    knapsack::ExpectExcept_BWExp, tracker), 
   "gKnapsack.BaseCase.Inv: negEnd: bad weight exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "gKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected")


  /* npKnapsack.BaseCase.Inv set */
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Inv: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.Inv: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zStart, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: zStart: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zMid, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: zMid: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zEnd, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: zEnd: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, negEnd, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: negEnd: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, nArr, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "npKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected")


  /* nwKnapsack.BaseCase.Inv set */
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.Inv: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.Inv: gLong: size exception expected")
  // WARNING:  The following assumes the non-positive weight does get added to
  // the knapsack.
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, negEnd, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "nwKnapsack.BaseCase.Inv: negEnd: invariants vio/no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, nArr, total, false,
                    knapsack::ExpectExcept_InvExp, tracker), 
   "nwKnapsack.BaseCase.Inv: nArr: invariants vio/no solution expected")


  /*
   ***********************************************************************
   * NO CONTRACT ENFORCEMENT
   ***********************************************************************
   */
  tracker.writeComment("*** DISABLE CONTRACT ENFORCEMENT ***");
  ::sidl::EnfPolicy::setEnforceNone(false);


  /* gKnapsack.BaseCase.None set */
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.None: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "gKnapsack.BaseCase.None: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, negEnd, total, false,
                    knapsack::ExpectExcept_BWExp, tracker), 
   "gKnapsack.BaseCase.None: negEnd: bad weight exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_GOOD, nArr, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "gKnapsack.BaseCase.None: nArr: invariants vio/no solution expected")


  /* npKnapsack.BaseCase.None set */
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, total, true,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: good: expect solution for total")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "npKnapsack.BaseCase.None: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zStart, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: zStart: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zMid, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: zMid: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, zEnd, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: zEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, negEnd, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: negEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NONPOS, nArr, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "npKnapsack.BaseCase.None: nArr: no solution expected")


  /* nwKnapsack.BaseCase.None set */
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, good, -1, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: good: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, gLong, total, false,
                    knapsack::ExpectExcept_SizeExp, tracker), 
   "nwKnapsack.BaseCase.None: gLong: size exception expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, negEnd, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: negEnd: no solution expected")
  CHECK(runBaseCase(knapsackTypeEnum_NOWT, nArr, total, false,
                    knapsack::ExpectExcept_NoneExp, tracker), 
   "nwKnapsack.BaseCase.None: nArr: no solution expected")


  /*
   * TODO/TBD:  Other possible cases:
   * 1) create, initialize, and explicitly call hasWeights
      x = k.hasWeights(w);

   * 2) create, initialize, and explicitly call onlyPosWeights
      x = k.onlyPosWeights();
   */

  /*
   * Clean up
   */
  good.deleteRef();
  gLong.deleteRef();
  negEnd.deleteRef();
  zEnd.deleteRef();
  zMid.deleteRef();
  zStart.deleteRef();

  tracker.close();
  tracker.deleteRef();

  return 0;
}
