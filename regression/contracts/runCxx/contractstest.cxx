/*
 * File:        vectortest.cxx
 * Copyright:   (c) 2008-2009 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 6171 $
 * Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
 * Description: Contract Test C++ client
 *
 */

#include <string>
#include <cstdio>
//#include <sstream>
#include <stdlib.h>
#include <math.h>
#include "vect_Utils.hxx"
#include "vect_BadLevel.hxx"
#include "vect_ExpectExcept.hxx"
#include "vect_vExcept.hxx"
#include "vect_vDivByZeroExcept.hxx"
#include "vect_vNegValExcept.hxx"
#include "sidl_EnfPolicy.hxx"
#include "sidl_Exception.h"
#include "sidl_PreViolation.hxx"
#include "sidl_PostViolation.hxx"
#include "synch.hxx"

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif /* SIDL_USE_UCXX */
//#include <stddef.h>

/* 
 * Enable the following (i.e., set to 1) for tests that leak memory
 * for this client (but not the C++ client).
 */
#define ALLOW_MEMORY_LEAK_CHECKS 0

/* Ensure the following is set to 0 BEFORE commiting to production */
#define TEST_DEBUG 0

static const int NO_EXC_EXPECTED = 0;
static const int EXC_EXPECTED    = 1;
static const int DBZ_EXPECTED    = 2;
static const int NVE_EXPECTED    = 3;
static const int PRE_EXPECTED    = 4;
static const int POST_EXPECTED   = 5;

static const int PROPER_BEHAVIOR = 0;
static const int BAD_LEVEL_1     = 1;
static const int BAD_LEVEL_2     = 2;
static const int BAD_LEVEL_3     = 3;
static const int BAD_LEVEL_4     = 4;

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

#define PERFORMB(FUNC, RES, T, EXC_EXPECT) {\
  bool               _x  = false;\
  bool               _ok = false;\
  vect::ExpectExcept _exc = vect::ExpectExcept_NoneExp;\
  START_DEBUG(RES, EXC_EXPECT)\
  try {\
    _x = (FUNC);\
    _ok = (_x == RES);\
  } catch ( ::sidl::PreViolation preExc ) {\
    T.writeComment(preExc.getNote());\
    _exc = vect::ExpectExcept_PreExp;\
  } catch ( ::sidl::PostViolation postExc ) {\
    T.writeComment(postExc.getNote());\
    _exc = vect::ExpectExcept_PostExp;\
  } catch ( vect::vDivByZeroExcept dbzExc ) {\
    T.writeComment(dbzExc.getNote());\
    _exc = vect::ExpectExcept_DBZExp;\
  } catch ( vect::vNegValExcept nveExc ) {\
    T.writeComment(nveExc.getNote());\
    _exc = vect::ExpectExcept_NVEExp;\
  } catch ( vect::vExcept vExc ) {\
    T.writeComment(vExc.getNote());\
    _ok = false;\
  } catch (...) {\
    (T).writeComment("Caught unexpected exception");\
    _ok = false;\
  }\
  END_DEBUG(_x, _ok) \
  return ( _ok || (_exc == EXC_EXPECT) );\
}

#define PERFORMD(FUNC, RES, TOL, T, EXC_EXPECT) {\
  double             _x  = 0.0;\
  bool               _ok = false;\
  vect::ExpectExcept _exc = vect::ExpectExcept_NoneExp;\
  START_DEBUGD(RES, TOL, EXC_EXPECT)\
  try {\
    _x = (FUNC);\
    _ok = ( (fabs(RES) - fabs(_x)) <= fabs(TOL) );\
  } catch ( ::sidl::PreViolation preExc ) {\
    T.writeComment(preExc.getNote());\
    _exc = vect::ExpectExcept_PreExp;\
  } catch ( ::sidl::PostViolation postExc ) {\
    T.writeComment(postExc.getNote());\
    _exc = vect::ExpectExcept_PostExp;\
  } catch ( vect::vDivByZeroExcept dbzExc ) {\
    T.writeComment(dbzExc.getNote());\
    _exc = vect::ExpectExcept_DBZExp;\
  } catch ( vect::vNegValExcept nveExc) {\
    T.writeComment(nveExc.getNote());\
    _exc = vect::ExpectExcept_NVEExp;\
  } catch ( vect::vExcept vExc ) {\
    T.writeComment(vExc.getNote());\
  } catch (...) {\
    T.writeComment("Caught unexpected exception");\
  }\
  END_DEBUGD(_x, RES, _ok)\
  return (_ok || (_exc == EXC_EXPECT) );\
}

#define PERFORMA(FUNC, RES, TOL, T, OKAY, EXC_EXPECT) {\
  ::sidl::array<double> _x;\
  bool                  _ok = false;\
  vect::ExpectExcept    _exc = vect::ExpectExcept_NoneExp;\
  START_DEBUGP(OKAY, EXC_EXPECT)\
  try {\
    _x = (FUNC);\
    if (_x) {\
      _ok = vect::Utils::vuAreEqual(_x, RES, TOL);\
    } else { /* _x == NULL */ \
      _ok = (RES) ? false : true;\
    }\
  } catch ( ::sidl::PreViolation preExc ) {\
    T.writeComment(preExc.getNote());\
    _exc = vect::ExpectExcept_PreExp;\
  } catch ( ::sidl::PostViolation postExc ) {\
    T.writeComment(postExc.getNote());\
    _exc = vect::ExpectExcept_PostExp;\
  } catch ( vect::vDivByZeroExcept dbzExc ) {\
    T.writeComment(dbzExc.getNote());\
    _exc = vect::ExpectExcept_DBZExp;\
  } catch ( vect::vNegValExcept nveExc) {\
    T.writeComment(nveExc.getNote());\
    _exc = vect::ExpectExcept_NVEExp;\
  } catch ( vect::vExcept vExc ) {\
    T.writeComment(vExc.getNote());\
    _ok = false;\
  } catch (...) {\
    T.writeComment("Caught unexpected exception");\
    _ok = false;\
  }\
  END_DEBUGP(_x, _ok)\
  if (_x) { _x.deleteRef(); }\
  return ( (_ok == OKAY) || (_exc == EXC_EXPECT) );\
}

#if TEST_DEBUG
#define START_DEBUG(RES, EXC) {\
  char sdMsg[128]; \
  sprintf(sdMsg, "** DEBUG:  res=%d, expectExc=%d", RES, EXC); \
  tracker.writeComment(sdMsg); \
}
#define START_DEBUGD(RES, TOL, EXC) {\
  char sddMsg[128]; \
  sprintf(sddMsg, "** DEBUG:  res=%f, tol=%f, expectExc=%d", RES, TOL, EXC); \
  tracker.writeComment(sddMsg); \
}
#define START_DEBUGP(OK, EXC) {\
  char sdoMsg[128]; \
  sprintf(sdoMsg, "** DEBUG:  okay=%d, expectExc=%d", OK, EXC); \
  tracker.writeComment(sdoMsg); \
}
#define END_DEBUG(X, OK) {\
  char edMsg[128]; \
  sprintf(edMsg, "** DEBUG:  x=%d == ok=%d?", X, OK); \
  tracker.writeComment(edMsg); \
}
#define END_DEBUGD(X, RES, OK) {\
  char eddMsg[128]; \
  sprintf(eddMsg, "** DEBUG:  x=%f == res=%f? ok=%d", X, RES, OK); \
  tracker.writeComment(eddMsg); \
}
#define END_DEBUGP(X, OK) {\
  char edpMsg[128]; \
  sprintf(edpMsg, "** DEBUG:  x==NULL? %d, ok=%d", (!X), OK); \
  tracker.writeComment(edpMsg); \
}
#else
#define START_DEBUG(RES, EXC) 
#define START_DEBUGD(RES, TOL, EXC) 
#define START_DEBUGP(OK, EXC) 
#define END_DEBUG(X, OK) 
#define END_DEBUGD(X, RES, OK) 
#define END_DEBUGP(X, OK)
#endif /* TEST_DEBUG */


bool 
runIsZero(::sidl::array<double> v, double tol, bool res, 
          vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMB(vect::Utils::vuIsZero(v, tol), res, tracker, expectExc) 
}


bool 
runIsUnit(::sidl::array<double> v, double tol, bool res, 
          vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMB(vect::Utils::vuIsUnit(v, tol), res, tracker, expectExc) 
}


bool 
runAreEqual(::sidl::array<double> u, ::sidl::array<double> v, double tol, 
            bool res, vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMB(vect::Utils::vuAreEqual(u, v, tol), res, tracker, expectExc) 
}


bool 
runAreOrthogonal(::sidl::array<double> u, ::sidl::array<double> v, double tol, 
                 bool res, vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMB(vect::Utils::vuAreOrth(u, v, tol), res, tracker, expectExc) 
}


bool 
runSchwarzHolds(::sidl::array<double> u, ::sidl::array<double> v, double tol, 
                bool res, vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMB(vect::Utils::vuSchwarzHolds(u, v, tol), res, tracker, expectExc) 
}


bool 
runTriangleInequalityHolds(::sidl::array<double> u, ::sidl::array<double> v, 
                           double tol, bool res, vect::ExpectExcept expectExc, 
                           synch::RegOut tracker)
{
  PERFORMB(vect::Utils::vuTriIneqHolds(u, v, tol), res, tracker, expectExc) 
}


bool 
runNorm(::sidl::array<double> u, double tol, vect::BadLevel badLvl, double res, 
        vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMD(vect::Utils::vuNorm(u, tol, badLvl), res, tol, tracker, expectExc) 
}


bool 
runDot(::sidl::array<double> u, ::sidl::array<double> v, double tol, 
       vect::BadLevel badLvl, double res, vect::ExpectExcept expectExc, 
       synch::RegOut tracker)
{
  PERFORMD(vect::Utils::vuDot(u, v, tol, badLvl), res, tol, tracker, 
           expectExc) 
}


/* NOTE: Tolerance is only needed to check the result of the call. */
bool 
runProduct(double a, ::sidl::array<double> u, double tol, vect::BadLevel badLvl,
           ::sidl::array<double> res, bool okay, vect::ExpectExcept expectExc, 
           synch::RegOut tracker)
{
  PERFORMA(vect::Utils::vuProduct(a, u, badLvl), res, tol, tracker, okay, 
           expectExc) 
}


/* NOTE: Tolerance is only needed to check the result of the call. */
bool 
runNegate(::sidl::array<double> u, double tol, vect::BadLevel badLvl, 
          ::sidl::array<double> res, bool okay, vect::ExpectExcept expectExc, 
          synch::RegOut tracker)
{
  PERFORMA(vect::Utils::vuNegate(u, badLvl), res, tol, tracker, okay, 
           expectExc) 
}


bool 
runNormalize(::sidl::array<double> u, double tol, vect::BadLevel badLvl, 
             ::sidl::array<double> res, bool okay, vect::ExpectExcept expectExc,
             synch::RegOut tracker)
{
  PERFORMA(vect::Utils::vuNormalize(u, tol, badLvl), res, tol, tracker, okay, 
           expectExc) 
}


bool 
runSum(::sidl::array<double> u, ::sidl::array<double> v, double tol, 
       vect::BadLevel badLvl, ::sidl::array<double> res, bool okay, 
       vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMA(vect::Utils::vuSum(u, v, badLvl), res, tol, tracker, okay, expectExc) 
}


bool 
runDiff(::sidl::array<double> u, ::sidl::array<double> v, double tol, 
        vect::BadLevel badLvl, ::sidl::array<double> res, bool okay, 
        vect::ExpectExcept expectExc, synch::RegOut tracker)
{
  PERFORMA(vect::Utils::vuDiff(u, v, badLvl), res, tol, tracker, okay,
           expectExc) 
}


/*************************** main ***************************/

int 
main(int argc, char**argv)
{ 
  const char*       statsFile = "VUtils.stats";

  synch::ResultType result    = synch::ResultType_PASS; 
  synch::RegOut     tracker   = synch::RegOut::_create();
  int               part_no   = 0;

  int32_t i, j;
  int     max_size  = 6;
  double  sqrt_size = sqrt(1.0*max_size);
  double  tol       = 1.0e-9;
  double  ntol      = -1.0e-9;
  double  val       = 1.0/sqrt_size;
  double  nval      = -1.0/sqrt_size;

  ::sidl::array<double> t  = ::sidl::array<double>::create2dCol(max_size, 
                                                                max_size); 
  ::sidl::array<double> u  = ::sidl::array<double>::create1d(max_size);
  ::sidl::array<double> u1 = ::sidl::array<double>::create1d(max_size+1);
  ::sidl::array<double> nu = ::sidl::array<double>::create1d(max_size);
  ::sidl::array<double> z  = ::sidl::array<double>::create1d(max_size);
  ::sidl::array<double> n; // Apparently it always defaults to null (per GK)


  for (i=0; i < max_size; i++) {
    u.set(i, val);
    u1.set(i, val);
    nu.set(i, nval);
    z.set(i, 0);

    for (j=0; j < max_size; j++) {
       t.set(i, j, val); 
    }
  }
  u1.set(max_size, val);


  (void) argc;
  (void) argv;

#if ALLOW_MEMORY_LEAK_CHECKS
/*
 * The actual number of tests differs depending on whether the calls
 * that pass the regression tests but leak memory should be enabled
 * or not.  The ten tests leaking memory occur whenever the implementation
 * attempts to return a different sized output array (or matrix).  
 *
 * The solution _may_ be to change the corresponding check routines
 * to free the memory (on the postcondition violation) and return a
 * null pointer.  However, that will not protect C++ clients from
 * leaking memory from an errant implementation that does not have
 * such a contract check.
 */
  tracker.setExpectations(128);
#else
  tracker.setExpectations(118);
#endif /* ALLOW_MEMORY_LEAK_CHECKS */

  tracker.writeComment("*** ENABLE FULL CONTRACT CHECKING ***");
  ::sidl::EnfPolicy::setEnforceAll(::sidl::ContractClass_ALLCLASSES, true);

  /* vuIsZero() set */
  CHECK(runIsZero(z, tol, true, vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the zero vector is the zero vector")
  CHECK(runIsZero(u, tol, false, vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector is not the zero vector")
  CHECK(runIsZero(n, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuIsZero() a null array")
  CHECK(runIsZero(t, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuIsZero() a 2D array")
  CHECK(runIsZero(z, ntol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuIsZero() a negative tolerance")

  /* vuIsUnit() set */
  CHECK(runIsUnit(u, tol, true, vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector is the unit vector")
  CHECK(runIsUnit(z, tol, false, vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the zero vector is not the unit vector")
  CHECK(runIsUnit(n, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuIsUnit() a null array")
  CHECK(runIsUnit(t, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuIsUnit() a 2D array")
  CHECK(runIsUnit(u, ntol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuIsUnit() a negative tolerance")

  /* vuAreEqual() set */
  CHECK(runAreEqual(u, z, tol, false, vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the unit and zero vectors are not equal")
  CHECK(runAreEqual(u, u, tol, true, vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector is equal to itself")
  CHECK(runAreEqual(n, u, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a null 1st array")
  CHECK(runAreEqual(t, u, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a 2D 1st array")
  CHECK(runAreEqual(u, n, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a null 2nd array")
  CHECK(runAreEqual(u, t, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a 2D 2nd array")
  CHECK(runAreEqual(u, u1, tol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() different sized arrays")
  CHECK(runAreEqual(u, u, ntol, false, vect::ExpectExcept_PreExp, tracker), 
   "passing vuAreEqual() a negative tolerance")

  /* vuAreOrth() set */
  CHECK(runAreOrthogonal(u, z, tol, true, vect::ExpectExcept_NoneExp, 
                         tracker), 
   "ensuring the unit and zero vectors are orthogonal")
  CHECK(runAreOrthogonal(u, nu, tol, false, vect::ExpectExcept_NoneExp, 
                         tracker), 
   "ensuring the unit and negative unit vectors are not orthogonal")
  CHECK(runAreOrthogonal(n, u, tol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() a null 1st array")
  CHECK(runAreOrthogonal(t, u, tol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() a 2D 1st array")
  CHECK(runAreOrthogonal(u, n, tol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() a null 2nd array")
  CHECK(runAreOrthogonal(u, t, tol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() a 2D 2nd array")
  CHECK(runAreOrthogonal(u, u1, tol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() different sized unit arrays")
  CHECK(runAreOrthogonal(u, u, ntol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() a negative tolerance")
  CHECK(runAreOrthogonal(t, t, tol, false, vect::ExpectExcept_PreExp, 
                         tracker), 
   "passing vuAreOrth() 2D arrays in both arguments")

  /* vuSchwarzHolds() set */
  CHECK(runSchwarzHolds(u, z, tol, true, vect::ExpectExcept_NoneExp, tracker),
   "ensuring schwarz holds for the unit and zero vectors")
  CHECK(runSchwarzHolds(n, z, tol, false, vect::ExpectExcept_PreExp, tracker),
   "passing vuSchwarzHolds() a null 1st array")
  CHECK(runSchwarzHolds(t, z, tol, false, vect::ExpectExcept_PreExp, tracker),
   "passing vuSchwarzHolds() a 2D 1st array")
  CHECK(runSchwarzHolds(z, n, tol, false, vect::ExpectExcept_PreExp, tracker),
   "passing vuSchwarzHolds() a null 2nd array")
  CHECK(runSchwarzHolds(u, t, tol, false, vect::ExpectExcept_PreExp, tracker),
   "passing vuSchwarzHolds() a 2D 2nd array")
  CHECK(runSchwarzHolds(u, u1, tol, false, vect::ExpectExcept_PreExp, 
                        tracker), 
   "passing vuSchwarzHolds() different sized unit arrays")
  CHECK(runSchwarzHolds(u, z, ntol, false, vect::ExpectExcept_PreExp, 
                        tracker), 
   "passing vuSchwarzHolds() a negative tolerance")

  /* vuTriIneqHolds() set */
  CHECK(runTriangleInequalityHolds(u, z, tol, true, 
                                   vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the triangle inequality holds for the unit and zero vectors")
  CHECK(runTriangleInequalityHolds(n, u, tol, false, 
                                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuTriIneqHolds() a null 1st array")
  CHECK(runTriangleInequalityHolds(t, u, tol, false, 
                                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuTriIneqHolds() a 2D 1st array")
  CHECK(runTriangleInequalityHolds(u, n, tol, false, 
                                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuTriIneqHolds() a null 2nd array")
  CHECK(runTriangleInequalityHolds(u, t, tol, false, 
                                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuTriIneqHolds() a 2D 2nd array")
  CHECK(runTriangleInequalityHolds(u, u1, tol, false, 
                                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuTriIneqHolds() different sized unit vectors")
  CHECK(runTriangleInequalityHolds(u, u, ntol, false, 
                                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuTriIneqHolds() a negative tolerance")

  /* vuNorm() set */
  CHECK(runNorm(u, tol, vect::BadLevel_NoVio, 1.0, 
                vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the unit vector norm is 1.0")
  CHECK(runNorm(n, tol, vect::BadLevel_NoVio, 0.0, 
                vect::ExpectExcept_PreExp, tracker), 
   "passing vuNorm() a null vector")
  CHECK(runNorm(t, tol, vect::BadLevel_NoVio, 0.0, 
                vect::ExpectExcept_PreExp, tracker), 
   "passing vuNorm() a 2D array")
  CHECK(runNorm(u, ntol, vect::BadLevel_NoVio, 0.0, 
                vect::ExpectExcept_PreExp, tracker), 
   "passing vuNorm() a negative tolerance")
  CHECK(runNorm(u, tol, vect::BadLevel_NegRes, -5.0, 
                vect::ExpectExcept_PostExp, tracker), 
   "passing vuNorm() badness level for negative result")
  CHECK(runNorm(z, tol, vect::BadLevel_PosRes, 5.0, 
                vect::ExpectExcept_PostExp, tracker), 
   "passing vuNorm() badness level for positive result with zero vector")
  CHECK(runNorm(u, tol, vect::BadLevel_ZeroRes, 0.0, 
                vect::ExpectExcept_PostExp, tracker), 
   "passing vuNorm() badness level for zero result with non-zero vector")

  /* vuDot() set */
  CHECK(runDot(u, z, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the dot of the unit and zero vectors is 0.0")
  CHECK(runDot(n, u, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() a null 1st array")
  CHECK(runDot(t, u, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() a 2D 1st array")
  CHECK(runDot(u, n, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() a null 2nd array")
  CHECK(runDot(u, t, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() a 2D 2nd array")
  CHECK(runDot(u, u1, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() different sized unit vectors")
  CHECK(runDot(u, u, ntol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() a negative tolerance")
  CHECK(runDot(t, t, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDot() a 2D arrays in both arguments")
  CHECK(runDot(u, u, tol, vect::BadLevel_NegRes, -5.0, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuDot() badness level for negative result with u=v")
  CHECK(runDot(z, z, tol, vect::BadLevel_PosRes, 5.0, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuDot() badness level for positive result with u=v=0")

  /* vuProduct() set */
  CHECK(runProduct(1.0, u, tol, vect::BadLevel_NoVio, u, true, 
                   vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the product of 1 and the unit vector is the unit vector")
  CHECK(runProduct(2.0, u, tol, vect::BadLevel_NoVio, u, false, 
                   vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the product of 2 and the unit vector is not the unit vector")
  CHECK(runProduct(0.0, n, tol, vect::BadLevel_NoVio, n, true, 
                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuProduct() a null array")
  CHECK(runProduct(1.0, t, tol, vect::BadLevel_NoVio, u, false, 
                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuProduct() a 2D array")
  CHECK(runProduct(1.0, u, tol, vect::BadLevel_NullRes, u, true, 
                   vect::ExpectExcept_PostExp, tracker), 
   "passing vuProduct() badness level for null result")
#if ALLOW_MEMORY_LEAK_CHECKS
  CHECK(runProduct(1.0, u, tol, vect::BadLevel_TwoDRes, u, true, 
                   vect::ExpectExcept_PostExp, tracker), 
   "passing vuProduct() badness level for 2D result)")
  CHECK(runProduct(1.0, u, tol, vect::BadLevel_WrongSizeRes, u, true, 
                   vect::ExpectExcept_PostExp, tracker), 
   "passing vuProduct() badness level for wrong size result")
#endif /* ALLOW_MEMORY_LEAK_CHECKS */

  /* vuNegate() set */
  CHECK(runNegate(u, tol, vect::BadLevel_NoVio, nu, true, 
                   vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the negation of the unit vector is its negative")
  CHECK(runNegate(u, tol, vect::BadLevel_NoVio, u, false, 
                   vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the negation of the unit vector is not the unit vector")
  CHECK(runNegate(n, tol, vect::BadLevel_NoVio, nu, true, 
                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuNegate() a null array")
  CHECK(runNegate(t, tol, vect::BadLevel_NoVio, nu, false, 
                   vect::ExpectExcept_PreExp, tracker), 
   "passing vuNegate() a 2D array")
  CHECK(runNegate(u, tol, vect::BadLevel_NullRes, nu, true, 
                   vect::ExpectExcept_PostExp, tracker), 
   "passing vuNegate() badness level for null result")
#if ALLOW_MEMORY_LEAK_CHECKS
  CHECK(runNegate(u, tol, vect::BadLevel_TwoDRes, nu, true, 
                   vect::ExpectExcept_PostExp, tracker), 
   "passing vuNegate() badness level for 2D result)")
  CHECK(runNegate(u, tol, vect::BadLevel_WrongSizeRes, nu, true, 
                   vect::ExpectExcept_PostExp, tracker), 
   "passing vuNegate() badness level for wrong size result")
#endif /* ALLOW_MEMORY_LEAK_CHECKS */

  /* vuNormalize() set */
  CHECK(runNormalize(u, tol, vect::BadLevel_NoVio, u, true, 
                     vect::ExpectExcept_NoneExp, tracker), 
   "ensuring normalize of the unit vector is itself")
  CHECK(runNormalize(u, tol, vect::BadLevel_NoVio, nu, false, 
                     vect::ExpectExcept_NoneExp, tracker), 
   "ensuring normalize of the unit vector is not its negative")
  CHECK(runNormalize(z, tol, vect::BadLevel_NoVio, z, true, 
                     vect::ExpectExcept_DBZExp, tracker), 
   "ensuring normalize of the zero vector raises a DBZ exception")
  CHECK(runNormalize(n, tol, vect::BadLevel_NoVio, n, true, 
                     vect::ExpectExcept_PreExp, tracker), 
   "passing vuNormalize() a null array")
  CHECK(runNormalize(t, tol, vect::BadLevel_NoVio, u, false, 
                     vect::ExpectExcept_PreExp, tracker), 
   "passing vuNormalize() a 2D array")
  CHECK(runNormalize(u, ntol, vect::BadLevel_NoVio, u, true, 
                     vect::ExpectExcept_PreExp, tracker), 
   "passing vuNormalize() a negative tolerance using the unit vector")
  CHECK(runNormalize(u, tol, vect::BadLevel_NullRes, u, true, 
                     vect::ExpectExcept_PostExp, tracker), 
   "passing vuNormalize() a badness level for null result")
#if ALLOW_MEMORY_LEAK_CHECKS
  CHECK(runNormalize(u, tol, vect::BadLevel_TwoDRes, u, true, 
                     vect::ExpectExcept_PostExp, tracker), 
   "passing vuNormalize() a badness level for 2D result")
  CHECK(runNormalize(u, tol, vect::BadLevel_WrongSizeRes, u, true, 
                     vect::ExpectExcept_PostExp, tracker), 
   "passing vuNormalize() a badness level for wrong size result")
#endif /* ALLOW_MEMORY_LEAK_CHECKS */

  /* vuSum() set (NOTE: tolerance not relevant to vuSum() API.) */
  CHECK(runSum(u, z, tol, vect::BadLevel_NoVio, u, true, 
               vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the sum of the unit and zero vectors is the unit vector")
  CHECK(runSum(u, z, tol, vect::BadLevel_NoVio, nu, false, 
               vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the sum of unit and zero vectors is not the negative of the unit")
  CHECK(runSum(n, z, tol, vect::BadLevel_NoVio, n, true, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuSum() a null 1st array")
  CHECK(runSum(t, n, tol, vect::BadLevel_NoVio, n, false, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuSum() a 2D 1st array")
  CHECK(runSum(u, n, tol, vect::BadLevel_NoVio, n, true, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuSum() a null 2nd array")
  CHECK(runSum(u, t, tol, vect::BadLevel_NoVio, n, false, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuSum() a 2D as second")
  CHECK(runSum(u, u1, tol, vect::BadLevel_NoVio, z, true, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuSum() different sized unit vectors")
  CHECK(runSum(u, z, tol, vect::BadLevel_NullRes, u, true, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuSum() badness level for null result")
#if ALLOW_MEMORY_LEAK_CHECKS
  CHECK(runSum(u, z, tol, vect::BadLevel_TwoDRes, u, true, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuSum() badness level for 2D result")
  CHECK(runSum(u, z, tol, vect::BadLevel_WrongSizeRes, u, true, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuSum() badness level for wrong size result")
#endif /* ALLOW_MEMORY_LEAK_CHECKS */

  /* vuDiff() set (NOTE: tolerance not relevant to vuDiff() API.) */
  CHECK(runDiff(z, u, tol, vect::BadLevel_NoVio, nu, true, 
               vect::ExpectExcept_NoneExp, tracker),
   "ensuring the diff of the zero and unit vectors is the negative unit vector")
  CHECK(runDiff(u, z, tol, vect::BadLevel_NoVio, u, true, 
               vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the diff of the unit and zero vectors is the unit vector")
  CHECK(runDiff(u, z, tol, vect::BadLevel_NoVio, nu, false, 
               vect::ExpectExcept_NoneExp, tracker), 
   "ensuring the diff of the unit and zero vectors is not the neg unit vector")
  CHECK(runDiff(n, u, tol, vect::BadLevel_NoVio, n, true, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a null 1st array")
  CHECK(runDiff(t, u, tol, vect::BadLevel_NoVio, u, false, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a 2D 1st array")
  CHECK(runDiff(u, n, tol, vect::BadLevel_NoVio, n, true, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a null 2nd array")
  CHECK(runDiff(u, t, tol, vect::BadLevel_NoVio, u, false, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDiff() a 2D 2nd array")
  CHECK(runDiff(u, u1, tol, vect::BadLevel_NoVio, u, true, 
               vect::ExpectExcept_PreExp, tracker), 
   "passing vuDiff() different sized vectors")
  CHECK(runDiff(z, u, tol, vect::BadLevel_NullRes, nu, true, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuDiff() badness level for null result")
#if ALLOW_MEMORY_LEAK_CHECKS
  CHECK(runDiff(z, u, tol, vect::BadLevel_TwoDRes, nu, true, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuDiff() badness level for 2D result")
  CHECK(runDiff(z, u, tol, vect::BadLevel_WrongSizeRes, nu, true, 
               vect::ExpectExcept_PostExp, tracker), 
   "passing vuDiff() badness level for wrong size result")
#endif /* ALLOW_MEMORY_LEAK_CHECKS */

  vect::Utils::_dump_stats_static(statsFile, "After full checking");

  /****************************************************************
   * Now check preconditions only.  Only need three checks:
   *   1) successful execution;
   *   2) precondition violation that is not caught but is
   *      okay anyway; and
   *   3) postcondition violation that is caught.
   ****************************************************************/
  tracker.writeComment("*** ENABLE PRECONDITION ENFORCEMENT ONLY ***");
  ::sidl::EnfPolicy::setEnforceAll(::sidl::ContractClass_PRECONDS, false);

  CHECK(runDot(u, z, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_NoneExp, tracker),
   "ensuring the dot product of the unit and zero vectors is 0.0")
  CHECK(runDot(u, u, ntol, vect::BadLevel_NoVio, 1.0, 
               vect::ExpectExcept_PreExp, tracker),
   "passing vuDot() a negative tolerance")
  CHECK(runDot(u, u, tol, vect::BadLevel_NegRes, -5.0, 
               vect::ExpectExcept_NoneExp, tracker),
   "passing vuDot() badness level for negative result with u=v")

  vect::Utils::_dump_stats_static(statsFile, "After precondition checking");

  /****************************************************************
   * Now check postconditions only.  Only need three checks:
   *   1) successful execution;
   *   2) precondition violation that gets caught; and
   *   3) postcondition violation that is not caught.
   ****************************************************************/
  tracker.writeComment("*** ENABLE POSTCONDITION ENFORCEMENT ONLY ***");
  ::sidl::EnfPolicy::setEnforceAll(::sidl::ContractClass_POSTCONDS, false);

  CHECK(runDot(u, z, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_NoneExp, tracker),
   "ensuring the dot product of the unit and zero vectors is 0.0")
  CHECK(runDot(u, u, ntol, vect::BadLevel_NoVio, 1.0, 
               vect::ExpectExcept_NoneExp, tracker),
   "passing vuDot() a negative tolerance")
  CHECK(runDot(u, u, tol, vect::BadLevel_NegRes, -5.0, 
               vect::ExpectExcept_PostExp, tracker),
   "passing vuDot() badness level for negative result with u=v")

  vect::Utils::_dump_stats_static(statsFile, "After Postcondition checking");

  /****************************************************************
   * Now make sure contract violations are not caught when contract
   * enforcement turned off.  Do this for each type of violation
   * for every method.
   ****************************************************************/
  tracker.writeComment("*** DISABLE ALL CONTRACT ENFORCEMENT ***");
  ::sidl::EnfPolicy::setEnforceNone(false);

  CHECK(runIsZero(n, tol, false, vect::ExpectExcept_NoneExp, tracker),
   "passing vuIsZero() a null array - no precondition violation")
  CHECK(runIsUnit(n, tol, false, vect::ExpectExcept_NoneExp, tracker),
   "passing vuIsUnit() a null array - no precondition violation")
  CHECK(runAreEqual(n, u, tol, false, vect::ExpectExcept_NoneExp, tracker),
   "passing vuAreEqual() a null 1st array - no precondition violation")
  CHECK(runAreOrthogonal(n, u, tol, false, vect::ExpectExcept_NoneExp, 
                         tracker),
   "passing vuAreOrth() a null 1st array - no precondition violation")
  CHECK(runSchwarzHolds(n, z, tol, false, vect::ExpectExcept_NoneExp, 
                        tracker),
   "passing vuSchwarzHolds() a null 1st array - no precondition violation")
  CHECK(runTriangleInequalityHolds(n, u, tol, false, 
                                   vect::ExpectExcept_NoneExp, tracker),
   "passing() vuTriIneqHolds() a null 1st array - no precondition violation")
  CHECK(runNorm(n, tol, vect::BadLevel_NoVio, 0.0, 
                vect::ExpectExcept_NoneExp, tracker),
   "passing() vuNorm() a null vector - no precondition violation")
  CHECK(runNorm(u, tol, vect::BadLevel_NegRes, -5.0, 
                vect::ExpectExcept_NoneExp, tracker),
   "passing vuNorm() badness level for negative result - no post violation")
  CHECK(runDot(n, u, tol, vect::BadLevel_NoVio, 0.0, 
               vect::ExpectExcept_NoneExp, tracker),
   "passing vuDot() a null 1st array - no precondition violation")
  CHECK(runDot(u, u, tol, vect::BadLevel_NegRes, -5.0, 
               vect::ExpectExcept_NoneExp, tracker),
   "passing vuDot() badness level for negative result with u=v - no post vio.")
  CHECK(runProduct(0.0, n, tol, vect::BadLevel_NoVio, n, true, 
                   vect::ExpectExcept_NoneExp, tracker),
   "passing vuProduct() a null array - no precondition violation")
  CHECK(runProduct(1.0, u, tol, vect::BadLevel_NullRes, u, false, 
                   vect::ExpectExcept_NoneExp, tracker),
   "passing vuProduct() badness level for null result - no post violation")
  CHECK(runNegate(n, tol, vect::BadLevel_NoVio, n, true, 
                  vect::ExpectExcept_NoneExp, tracker),
   "passing vuNegate() a null array - no precondition violation")
  CHECK(runNegate(u, tol, vect::BadLevel_NullRes, nu, false, 
                  vect::ExpectExcept_NoneExp, tracker),
   "passing vuNegate() badness level for null result - no post violation")
  CHECK(runNormalize(n, tol, vect::BadLevel_NoVio, n, true, 
                     vect::ExpectExcept_NoneExp, tracker),
   "passing vuNormalize() a null array - no precondition violation")
  CHECK(runNormalize(u, tol, vect::BadLevel_NullRes, u, false, 
                     vect::ExpectExcept_NoneExp, tracker),
   "passing vuNormalize() a badness level for null result - no post violation")
  CHECK(runSum(n, z, tol, vect::BadLevel_NoVio, n, TRUE, 
               vect::ExpectExcept_NoneExp, tracker),
   "passing vuSum() a null 1st array - no precondition violation")
  CHECK(runSum(u, z, tol, vect::BadLevel_NullRes, u, false, 
               vect::ExpectExcept_NoneExp, tracker),
   "passing vuSum() a badness level for null result - no post violation")
  CHECK(runDiff(n, u, tol, vect::BadLevel_NoVio, n, true, 
                vect::ExpectExcept_NoneExp, tracker),
   "passing vuDiff() a null 1st array - no precondition violation")
  CHECK(runDiff(z, u, tol, vect::BadLevel_NullRes, nu, false, 
                vect::ExpectExcept_NoneExp, tracker),
   "passing vuDiff() badness level for null result - no post violation")

  vect::Utils::_dump_stats_static(statsFile, "After no checking");

  t.deleteRef(); 
  u.deleteRef();
  u1.deleteRef();
  nu.deleteRef();
  z.deleteRef();

  tracker.close();
  tracker.deleteRef();

  return 0;
}
