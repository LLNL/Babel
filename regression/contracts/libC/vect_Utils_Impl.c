/*
 * File:          vect_Utils_Impl.c
 * Symbol:        vect.Utils-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for vect.Utils
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "vect.Utils" (version 1.0)
 */

#include "vect_Utils_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(vect.Utils._includes) */
/************************** Includes **************************/
#include <math.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include "sidlArray.h"
#include "sidl_Exception.h"
#include "vect_vExcept.h"
#include "vect_vDivByZeroExcept.h"
#include "vect_vNegValExcept.h"

/* DO-NOT-DELETE splicer.end(vect.Utils._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_vect_Utils__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils._load) */
    /* Nothing needed here. */
    /* DO-NOT-DELETE splicer.end(vect.Utils._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_vect_Utils__ctor(
  /* in */ vect_Utils self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils._ctor) */
    /* Nothing needed here. */
    /* DO-NOT-DELETE splicer.end(vect.Utils._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_vect_Utils__ctor2(
  /* in */ vect_Utils self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils._ctor2) */
    /* Nothing needed here. */
    /* DO-NOT-DELETE splicer.end(vect.Utils._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_vect_Utils__dtor(
  /* in */ vect_Utils self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils._dtor) */
    /* Nothing needed here. */
    /* DO-NOT-DELETE splicer.end(vect.Utils._dtor) */
  }
}

/*
 * boolean result operations 
 * Return TRUE if the specified vector is the zero vector, within the
 * given tolerance level; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuIsZero"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_vect_Utils_vuIsZero(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuIsZero) */
    sidl_bool is = TRUE;
    int       i, maxI;
    double    absVal;
    struct sidl_double__array* _u = NULL;

    if (sidl__array_type(u) == sidl_double_array)
      _u = sidl_double__array_cast(u);

    if (_u != NULL) 
    {
      maxI = sidl_double__array_upper(_u, 0);
      for (i=sidl_double__array_lower(_u, 0); (i <= maxI) && is; i++) 
      {
        absVal = fabs(sidl_double__array_get1(_u, i));
        if ( absVal > fabs(tol) ) {
           is = FALSE;
        } 
      }
    } else {
      is = FALSE;
    }

    return is;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuIsZero) */
  }
}

/*
 * Return TRUE if the specified vector is the unit vector, within the
 * given tolerance level; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuIsUnit"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_vect_Utils_vuIsUnit(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuIsUnit) */
    sidl_bool is   = FALSE;

    double vNorm = impl_vect_Utils_vuNorm(u, tol, vect_BadLevel_NoVio, 
                         _ex); SIDL_CHECK(*_ex);
    if ( fabs(vNorm - 1.0) <= fabs(tol) ) {
      is = TRUE;
    } else {
      is = FALSE;
    }
  
    EXIT:;
    return is;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuIsUnit) */
  }
}

/*
 * Return TRUE if the specified vectors are equal, within the given
 * tolerance level; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuAreEqual"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_vect_Utils_vuAreEqual(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuAreEqual) */
    sidl_bool are = FALSE;
    int       i, lenU, lenV;
    double    absDiff;
    struct sidl_double__array* _u = NULL;
    struct sidl_double__array* _v = NULL;

    if (sidl__array_type(u) == sidl_double_array)
      _u = sidl_double__array_cast(u);

    if (sidl__array_type(v) == sidl_double_array)
      _v = sidl_double__array_cast(v);
  
    if ( (_u != NULL) && (_v != NULL) ) 
    {
      lenU = sidlLength(u, 0);
      lenV = sidlLength(v, 0);
      if (  (lenU == lenV)
         && (sidlArrayDim(u) == 1) 
         && (sidlArrayDim(v) == 1) ) 
      {
        are = TRUE;
        for (i=0; (i < lenU) && are; i++) 
        {
          absDiff = fabs(sidl_double__array_get1(_u, i) 
                    - sidl_double__array_get1(_v, i));
          if ( absDiff > fabs(tol) ) {
            are = FALSE;
          } 
        } 
      }
    }
  
    return are;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuAreEqual) */
  }
}

/*
 * Return TRUE if the specified vectors are orthogonal, within the given
 * tolerance; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuAreOrth"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_vect_Utils_vuAreOrth(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuAreOrth) */
    sidl_bool are    = FALSE;
    double    absVal;
  
    if ( (u != NULL) && (v != NULL) ) 
    {
      absVal = fabs(impl_vect_Utils_vuDot(u, v, tol, vect_BadLevel_NoVio, 
                                            _ex)); SIDL_CHECK(*_ex);
      if ( absVal <= fabs(tol) ) {
        are = TRUE;
      } else {
        are = FALSE;
      }
    }
  
    EXIT:;
    return are;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuAreOrth) */
  }
}

/*
 * Return TRUE if the Schwarz (or Cauchy-Schwarz) inequality holds, within
 * the given tolerance; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuSchwarzHolds"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_vect_Utils_vuSchwarzHolds(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuSchwarzHolds) */
    sidl_bool holds = FALSE;
    double    absDot, absNorms;
  
    if ( (u != NULL) && (v != NULL) ) 
    {
      absDot   = fabs(impl_vect_Utils_vuDot(u, v, tol, vect_BadLevel_NoVio, 
                                            _ex)); SIDL_CHECK(*_ex);
      absNorms = fabs(impl_vect_Utils_vuNorm(u, tol, vect_BadLevel_NoVio, _ex) 
                 * impl_vect_Utils_vuNorm(v, tol, vect_BadLevel_NoVio, 
                                          _ex)); SIDL_CHECK(*_ex);
      if ( absDot <= (absNorms + fabs(tol)) ) {
        holds = TRUE;
      } else {
        holds = FALSE;
      }
    }
  
    EXIT:;
    return holds;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuSchwarzHolds) */
  }
}

/*
 * Return TRUE if the Minkowski (or triangle) inequality holds, within the
 * given tolerance; otherwise, return FALSE.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuTriIneqHolds"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_vect_Utils_vuTriIneqHolds(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuTriIneqHolds) */
    sidl_bool                  holds = FALSE;
    struct sidl__array* sum   = NULL;
    double absNormSum, normU, normV, absSumNorms;

    if ( (u != NULL) && (v != NULL) ) 
    {
      if (  (sidlArrayDim(u) == 1) && (sidlArrayDim(v) == 1) ) 
      {
        sum = impl_vect_Utils_vuSum(u, v, vect_BadLevel_NoVio, 
                                    _ex); SIDL_CHECK(*_ex);
        absNormSum = fabs(impl_vect_Utils_vuNorm(sum, tol, vect_BadLevel_NoVio, 
                          _ex)); SIDL_CHECK(*_ex);
        normU = impl_vect_Utils_vuNorm(u, tol, vect_BadLevel_NoVio, 
                                       _ex); SIDL_CHECK(*_ex);
        normV = impl_vect_Utils_vuNorm(v, tol, vect_BadLevel_NoVio, 
                                       _ex); SIDL_CHECK(*_ex);
        absSumNorms = fabs(normU + normV);
        if ( absNormSum <= (absSumNorms + fabs(tol)) ) {
          holds = TRUE;
        } else {
          holds = FALSE;
        }
      }
    }
  
    EXIT:;
    if (sum != NULL) sidl__array_deleteRef(sum);
    return holds;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuTriIneqHolds) */
  }
}

/*
 * double result operations 
 * Return the norm (or length) of the specified vector.
 * 
 * Note that the size exception is given here simply because the
 * implementation is leveraging the vuDot() method.  Also the tolerance
 * is included to enable the caller to specify the tolerance used in
 * contract checking.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuNorm"

#ifdef __cplusplus
extern "C"
#endif
double
impl_vect_Utils_vuNorm(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuNorm) */
    double dot;
    double res = 0.0;
    char*  msg = "vuNorm: vNegValExcept: Cannot sqrt() a negative value.";
  
    if (badLevel == vect_BadLevel_NoVio)
    {
      if (u != NULL) 
      {
        dot = impl_vect_Utils_vuDot(u, u, tol, vect_BadLevel_NoVio, 
                                    _ex); SIDL_CHECK(*_ex);
        if (dot > 0.0) {
          res = (double) sqrt(dot);
        } else if (dot < 0.0) {
          /* Note that this should NEVER happen! */
          res = -5.0;
          SIDL_THROW(*_ex, vect_vNegValExcept, msg);
        }
      } 
    } else if (badLevel == vect_BadLevel_NegRes) {
      res = -5.0;
    } else if (badLevel == vect_BadLevel_PosRes) {
      res = 5.0;
    } else if (badLevel == vect_BadLevel_ZeroRes) {
      res = 0.0;
    } else {
      res = -5.0;
    }
  
    EXIT:;
    return res;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuNorm) */
  }
}

/*
 * Return the dot (, inner, or scalar) product of the specified vectors.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuDot"

#ifdef __cplusplus
extern "C"
#endif
double
impl_vect_Utils_vuDot(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ double tol,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuDot) */
    double dot = 0.0;
    int    i, lenU, lenV;
    struct sidl_double__array* _u = NULL;
    struct sidl_double__array* _v = NULL;

    if (sidl__array_type(u) == sidl_double_array)
      _u = sidl_double__array_cast(u);

    if (sidl__array_type(v) == sidl_double_array)
      _v = sidl_double__array_cast(v);
  
    if (badLevel == vect_BadLevel_NoVio) {
      if ( (u != NULL) && (v != NULL) ) 
      {
        lenU = sidlLength(u, 0);
        lenV = sidlLength(v, 0);
        if (  (lenU == lenV)
           && (sidlArrayDim(u) == 1) 
           && (sidlArrayDim(v) == 1) ) 
        {
          for (i=0; i < lenU; ++i) {
            dot += (sidlArrayElem1(_u, i) * sidlArrayElem1(_v, i));
          }
        }
      } 
    } else if (badLevel == vect_BadLevel_NegRes) {
      dot = -5.0;
    } else if (badLevel == vect_BadLevel_PosRes) {
      dot = 5.0;
    } else {
      dot = -1.0;
    }

    return dot;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuDot) */
  }
}

/*
 * vector result operations 
 * Return the (scalar) product of the specified vector.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuProduct"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_vect_Utils_vuProduct(
  /* in */ double a,
  /* in array<> */ struct sidl__array* u,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuProduct) */
    struct sidl_double__array* prod = NULL;
    int    i, lenU;
    struct sidl_double__array* _u = NULL;

    if (sidl__array_type(u) == sidl_double_array)
      _u = sidl_double__array_cast(u);

    if (u != NULL) { lenU = sidlLength(u, 0); } else { lenU = 0; }
    if (badLevel == vect_BadLevel_NoVio) 
    {
      if (u != NULL) 
      {
        prod = sidl_double__array_create1d(lenU);
        memset(sidl_double__array_first(prod), 0, 
               (size_t)(lenU * sizeof(double)));
        for (i=0; i < lenU; i++) {
          sidl_double__array_set1(prod, i, a * sidl_double__array_get1(_u,i));
        }
      } 
    } else if (badLevel == vect_BadLevel_NullRes) {
      prod = NULL;
    } else if (badLevel == vect_BadLevel_TwoDRes) {
      prod = sidl_double__array_create2dCol(lenU, lenU);
    } else if (badLevel == vect_BadLevel_WrongSizeRes) {
      prod = sidl_double__array_create1d(lenU+5);
    } else {
      prod = NULL;
    }

    return sidl_array_cast(prod);
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuProduct) */
  }
}

/*
 * Return the negation of the specified vector.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuNegate"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_vect_Utils_vuNegate(
  /* in array<> */ struct sidl__array* u,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuNegate) */
    struct sidl__array* negU  = NULL;
    int                        lenU;
  
    if (u != NULL) { lenU = sidlLength(u, 0); } else { lenU = 0; }
    if (badLevel == vect_BadLevel_NoVio) {
      if (u != NULL) {
        negU = impl_vect_Utils_vuProduct(-1.0, u, vect_BadLevel_NoVio, 
                                         _ex);  SIDL_CHECK(*_ex);
      } 
    } else if (badLevel == vect_BadLevel_NullRes) {
      negU = NULL;
    } else if (badLevel == vect_BadLevel_TwoDRes) {
      negU = sidl_array_cast(sidl_double__array_create2dCol(lenU, lenU));
    } else if (badLevel == vect_BadLevel_WrongSizeRes) {
      negU = sidl_array_cast(sidl_double__array_create1d(lenU+5));
    } else {
      negU = NULL;
    }

    EXIT:;
    return negU;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuNegate) */
  }
}

/*
 * Return the normalizaton of the specified vector.
 * 
 * Note the tolerance is included because the implementation invokes 
 * vuDot().
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuNormalize"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_vect_Utils_vuNormalize(
  /* in array<> */ struct sidl__array* u,
  /* in */ double tol,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuNormalize) */
    struct sidl__array* prod  = NULL;
    int                        lenU;
    double                     val;
  
    if (u != NULL) { lenU = sidlLength(u, 0); } else { lenU = 0; }
    if (badLevel == vect_BadLevel_NoVio) {
      if (u != NULL)
      {
        val = impl_vect_Utils_vuNorm(u, tol, vect_BadLevel_NoVio, 
                                     _ex); SIDL_CHECK(*_ex);
        if (val != 0.0) {
          prod = impl_vect_Utils_vuProduct(1.0/val, u, vect_BadLevel_NoVio, 
                                             _ex); SIDL_CHECK(*_ex);
        } else {
          SIDL_THROW(*_ex, vect_vDivByZeroExcept, 
            "vuNormalize: vDivByZeroExcept: Cannot divide by zero."); 
        }
      }
    } else if (badLevel == vect_BadLevel_NullRes) {
      prod = NULL;
    } else if (badLevel == vect_BadLevel_TwoDRes) {
      prod = sidl_array_cast(sidl_double__array_create2dCol(lenU, lenU));
    } else if (badLevel == vect_BadLevel_WrongSizeRes) {
      prod = sidl_array_cast(sidl_double__array_create1d(lenU+5));
    } else {
      prod = NULL;
    }
  
    EXIT:;
    return prod;
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuNormalize) */
  }
}

/*
 * Return the sum of the specified vectors.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuSum"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_vect_Utils_vuSum(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuSum) */
    struct sidl_double__array* sum = NULL;
    int                        i, lenU, lenV;
    struct sidl_double__array* _u = NULL;
    struct sidl_double__array* _v = NULL;

    if (sidl__array_type(u) == sidl_double_array)
      _u = sidl_double__array_cast(u);

    if (sidl__array_type(v) == sidl_double_array)
      _v = sidl_double__array_cast(v);
    
    if (_u != NULL) { lenU = sidlLength(u, 0); } else { lenU = 0; }
    if (badLevel == vect_BadLevel_NoVio) 
    {
      if ( (_u != NULL) && (_v != NULL) ) 
      {
      lenV = sidlLength(v, 0);
        if (  (lenU == lenV)
           && (sidlArrayDim(u) == 1) 
           && (sidlArrayDim(v) == 1) ) 
        {
          sum = sidl_double__array_create1d(lenU);
          memset(sidl_double__array_first(sum), 0,
                 (size_t)(lenU * sizeof(double)));
          for (i=0; i < lenU; i++) {
            sidl_double__array_set1(sum, i, 
              sidl_double__array_get1(_u,i) + sidl_double__array_get1(_v,i));
          }
        }
      }
    } else if (badLevel == vect_BadLevel_NullRes) {
      sum = NULL;
    } else if (badLevel == vect_BadLevel_TwoDRes) {
      sum = sidl_double__array_create2dCol(lenU, lenU);
    } else if (badLevel == vect_BadLevel_WrongSizeRes) {
      sum = sidl_double__array_create1d(lenU+5);
    } else {
      sum = NULL;
    }

    return sidl_array_cast(sum);
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuSum) */
  }
}

/*
 * Return the difference of the specified vectors.
 */

#undef __FUNC__
#define __FUNC__ "impl_vect_Utils_vuDiff"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_vect_Utils_vuDiff(
  /* in array<> */ struct sidl__array* u,
  /* in array<> */ struct sidl__array* v,
  /* in */ enum vect_BadLevel__enum badLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(vect.Utils.vuDiff) */
    struct sidl_double__array* diff  = NULL;
    int                        i, lenU, lenV;
    struct sidl_double__array* _u = NULL;
    struct sidl_double__array* _v = NULL;

    if (sidl__array_type(u) == sidl_double_array)
      _u = sidl_double__array_cast(u);

    if (sidl__array_type(v) == sidl_double_array)
      _v = sidl_double__array_cast(v);
    
  
    if (u != NULL) { lenU = sidlLength(u, 0); } else { lenU = 0; }
    if (badLevel == vect_BadLevel_NoVio) 
    {
      if ( (u != NULL) && (v != NULL) ) 
      {
      lenV = sidlLength(v, 0);
        if (  (lenU == lenV)
           && (sidlArrayDim(u) == 1) 
           && (sidlArrayDim(v) == 1) ) 
        {
          diff = sidl_double__array_create1d(lenU);
          memset(sidl_double__array_first(diff), 0,
                 (size_t)(lenU * sizeof(double)));
          for (i=0; i < lenU; i++) {
            sidl_double__array_set1(diff, i, 
              sidl_double__array_get1(_u,i) - sidl_double__array_get1(_v,i));
          }
        }
      }
    } else if (badLevel == vect_BadLevel_NullRes) {
      diff = NULL;
    } else if (badLevel == vect_BadLevel_TwoDRes) {
      diff = sidl_double__array_create2dCol(lenU, lenU);
    } else if (badLevel == vect_BadLevel_WrongSizeRes) {
      diff = sidl_double__array_create1d(lenU+5);
    } else {
      diff = NULL;
    }

    return sidl_array_cast(diff);
    /* DO-NOT-DELETE splicer.end(vect.Utils.vuDiff) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Nothing needed here. */
/* DO-NOT-DELETE splicer.end(_misc) */

