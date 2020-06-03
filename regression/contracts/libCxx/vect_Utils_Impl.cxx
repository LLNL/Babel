// 
// File:          vect_Utils_Impl.cxx
// Symbol:        vect.Utils-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for vect.Utils
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "vect_Utils_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_PostViolation_hxx
#include "sidl_PostViolation.hxx"
#endif
#ifndef included_sidl_PreViolation_hxx
#include "sidl_PreViolation.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif
#ifndef included_vect_BadLevel_hxx
#include "vect_BadLevel.hxx"
#endif
#ifndef included_vect_vDivByZeroExcept_hxx
#include "vect_vDivByZeroExcept.hxx"
#endif
#ifndef included_vect_vNegValExcept_hxx
#include "vect_vNegValExcept.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(vect.Utils._includes)
#include <math.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include "sidlArray.h"
#include "vect_vExcept.hxx"
#include "vect_vDivByZeroExcept.hxx"
#include "vect_vNegValExcept.hxx"

#ifndef NULL
#define NULL 0
#endif

/* Comment out DEBUG for production */
/* #define DEBUG 1 */
// DO-NOT-DELETE splicer.end(vect.Utils._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
vect::Utils_impl::Utils_impl() : StubBase(reinterpret_cast< void*>(
  ::vect::Utils::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(vect.Utils._ctor2)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._ctor2)
}

// user defined constructor
void vect::Utils_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(vect.Utils._ctor)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._ctor)
}

// user defined destructor
void vect::Utils_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(vect.Utils._dtor)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._dtor)
}

// static class initializer
void vect::Utils_impl::_load() {
  // DO-NOT-DELETE splicer.begin(vect.Utils._load)
  // Nothing needed here.
  // DO-NOT-DELETE splicer.end(vect.Utils._load)
}

// user defined static methods:
/**
 * boolean result operations 
 * Return TRUE if the specified vector is the zero vector, within the
 * given tolerance level; otherwise, return FALSE.
 */
bool
vect::Utils_impl::vuIsZero_impl (
  /* in array<> */::sidl::basearray& u,
  /* in */double tol ) 
// throws:
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuIsZero)
  bool   is = true;
  int    i, maxI;
  double absVal;
  ::sidl::array<double> _u = u;

  if (u._not_nil())
  {
    maxI = u.upper(0);
    for (i=u.lower(0); (i <= maxI) && is; i++)
    {
      absVal = fabs(_u.get(i));
      if ( absVal > fabs(tol) ) {
         is = false;
      }
    }
  } else {
    is = false;
  }
                                                                              
  return is;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuIsZero)
}

/**
 * Return TRUE if the specified vector is the unit vector, within the
 * given tolerance level; otherwise, return FALSE.
 */
bool
vect::Utils_impl::vuIsUnit_impl (
  /* in array<> */::sidl::basearray& u,
  /* in */double tol ) 
// throws:
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::vect::vNegValExcept
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuIsUnit)
  bool is = false;
                                                                              
  double vNorm = vect::Utils_impl::vuNorm_impl(u, tol, vect::BadLevel_NoVio);
  if ( fabs(vNorm - 1.0) <= fabs(tol) ) {
    is = true;
  } else {
    is = false;
  }

  return is;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuIsUnit)
}

/**
 * Return TRUE if the specified vectors are equal, within the given
 * tolerance level; otherwise, return FALSE.
 */
bool
vect::Utils_impl::vuAreEqual_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */double tol ) 
// throws:
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuAreEqual)
  bool   are = false;
  int    i, lenU, lenV;
  double absDiff;
  ::sidl::array<double> _u = u;
  ::sidl::array<double> _v = v;

  if ( (u._not_nil()) && (v._not_nil()) )
  {
    lenU = u.length();  // sidlLength(u._get_ior(), 0);
    lenV = v.length();  // sidlLength(v._get_ior(), 0);
    if ( (lenU == lenV) && (u.dimen() == 1) && (v.dimen() == 1) )
    {
      are = true;
      for (i=0; (i < lenU) && are; i++)
      {
        absDiff = fabs(_u.get(i) - _v.get(i));
        if ( absDiff > fabs(tol) ) {
          are = false;
        }
      }
    }
  }
                                                                              
  return are;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuAreEqual)
}

/**
 * Return TRUE if the specified vectors are orthogonal, within the given
 * tolerance; otherwise, return FALSE.
 */
bool
vect::Utils_impl::vuAreOrth_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */double tol ) 
// throws:
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuAreOrth)
  bool   are = false;
  double val;
                                                                              
  if ( (u._not_nil()) && (v._not_nil()) )
  {
    val = vect::Utils_impl::vuDot_impl(u, v, tol, vect::BadLevel_NoVio);
    if ( fabs(val) <= fabs(tol) ) {
      are = true;
    } else {
      are = false;
    }
  }
                                                                              
  return are;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuAreOrth)
}

/**
 * Return TRUE if the Schwarz (or Cauchy-Schwarz) inequality holds, within
 * the given tolerance; otherwise, return FALSE.
 */
bool
vect::Utils_impl::vuSchwarzHolds_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */double tol ) 
// throws:
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::vect::vNegValExcept
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuSchwarzHolds)
  bool   holds = false;
  double absNorms, vDot;
                                                                              
  if ( (u._not_nil()) && (v._not_nil()) )
  {
    vDot = vect::Utils_impl::vuDot_impl(u, v, tol, vect::BadLevel_NoVio);
    absNorms = fabs(vect::Utils_impl::vuNorm_impl(u, tol, vect::BadLevel_NoVio)
                  * vect::Utils_impl::vuNorm_impl(v, tol, vect::BadLevel_NoVio)); 
    if ( fabs(vDot) <= (absNorms + fabs(tol)) ) {
      holds = true;
    } else {
      holds = false;
    }
  }
                                                                              
  return holds;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuSchwarzHolds)
}

/**
 * Return TRUE if the Minkowski (or triangle) inequality holds, within the
 * given tolerance; otherwise, return FALSE.
 */
bool
vect::Utils_impl::vuTriIneqHolds_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */double tol ) 
// throws:
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::vect::vNegValExcept
//    ::sidl::PostViolation
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuTriIneqHolds)
  bool                  holds = false;
  ::sidl::array<double> sum   = NULL;
  double                absNormSum, normU, normV, absSumNorms;
                                                                                
  if ( (u._not_nil()) && (v._not_nil()) )
  {
    if ( (u.dimen() == 1) && (v.dimen() == 1) ) {
      sum        = vect::Utils_impl::vuSum_impl(u, v, vect::BadLevel_NoVio);
      absNormSum = fabs(vect::Utils_impl::vuNorm_impl(sum, tol, 
                         vect::BadLevel_NoVio));
      normU      = vect::Utils_impl::vuNorm_impl(u, tol, vect::BadLevel_NoVio);
      normV      = vect::Utils_impl::vuNorm_impl(v, tol, vect::BadLevel_NoVio);
      absSumNorms = fabs(normU + normV);
      if ( absNormSum <= absSumNorms + fabs(tol)) {
        holds = true;
      } else {
        holds = false;
      }
    }
  }

  return holds;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuTriIneqHolds)
}

/**
 * double result operations 
 * Return the norm (or length) of the specified vector.
 * 
 * Note that the size exception is given here simply because the
 * implementation is leveraging the vuDot() method.  Also the tolerance
 * is included to enable the caller to specify the tolerance used in
 * contract checking.
 */
double
vect::Utils_impl::vuNorm_impl (
  /* in array<> */::sidl::basearray& u,
  /* in */double tol,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::vect::vNegValExcept
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuNorm)
  double        dot;
  double        res = 0.0;
  ::std::string msg = "vuNorm: vNegValExcept: Cannot sqrt() a negative value.";
                                                                                
  if (badLevel == vect::BadLevel_NoVio)
  {
    if (u._not_nil())
    {
      dot = vect::Utils_impl::vuDot_impl(u, u, tol, vect::BadLevel_NoVio); 
      if (dot > 0.0) {
        res = (double) sqrt(dot);
      } else if (dot < 0.0) {
        /* Note that this should NEVER happen! */
        res = -5.0;
        vect::vNegValExcept ex = 
            vect::vNegValExcept::_create();
        ex.setNote(msg);
        ex.add(__FILE__, __LINE__, "vect::vNegValExcept");
        throw ex;
      }
    }
  } else if (badLevel == vect::BadLevel_NegRes) {
    res = -5.0;
  } else if (badLevel == vect::BadLevel_PosRes) {
    res = 5.0;
  } else if (badLevel == vect::BadLevel_ZeroRes) {
    res = 0.0;
  } else {
    res = -5.0;
  }
                                                                                
    return res;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuNorm)
}

/**
 * Return the dot (, inner, or scalar) product of the specified vectors.
 */
double
vect::Utils_impl::vuDot_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */double tol,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuDot)
  double dot = 0.0;
  int    i, lenU, lenV;
  ::sidl::array<double> _u = u;
  ::sidl::array<double> _v = v;                                                                                
  if (u._not_nil()) { lenU = u.length(); } else { lenU = 0; }
  if (badLevel == vect::BadLevel_NoVio) 
  {
    if ( (u._not_nil()) && (v._not_nil()) )
    {
      lenV = v.length();  // sidlLength(v._get_ior(), 0);
      if ( (lenU == lenV) && (u.dimen() == 1) && (v.dimen() == 1) )
      {
        for (i=0; i < lenU; ++i) {
          dot += (sidlArrayElem1(_u._get_ior(), i) 
                * sidlArrayElem1(_v._get_ior(), i));
        }
      }
    }
  } else if (badLevel == vect::BadLevel_NegRes) {
    dot = -5.0;
  } else if (badLevel == vect::BadLevel_PosRes) {
    dot = 5.0;
  } else {
    dot = -1.0;
  }
                                                                                
  return dot;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuDot)
}

/**
 * vector result operations 
 * Return the (scalar) product of the specified vector.
 */
::sidl::basearray
vect::Utils_impl::vuProduct_impl (
  /* in */double a,
  /* in array<> */::sidl::basearray& u,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuProduct)
  ::sidl::array<double> prod = NULL;
  int                   i, lenU;
  ::sidl::array<double> _u = u;
                                                                                
  if (u._not_nil()) { lenU = u.length(); } else { lenU = 0; }
  if (badLevel == vect::BadLevel_NoVio) 
  {
    if (u._not_nil()) {
      prod = ::sidl::array<double>::create1d(lenU);
      for (i=0; i < lenU; i++) {
        prod.set(i, a * _u.get(i));
      }
    }
  } else if (badLevel == vect::BadLevel_NullRes) {
    // prod = NULL;
  } else if (badLevel == vect::BadLevel_TwoDRes) {
    prod = ::sidl::array<double>::create2dCol(lenU, lenU);
  } else if (badLevel == vect::BadLevel_WrongSizeRes) {
    prod = ::sidl::array<double>::create1d(lenU+5);
  } else {
    // prod = NULL;
  }
                                                                                
   return prod;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuProduct)
}

/**
 * Return the negation of the specified vector.
 */
::sidl::basearray
vect::Utils_impl::vuNegate_impl (
  /* in array<> */::sidl::basearray& u,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuNegate)
  ::sidl::array<double> negU = NULL;
  int                   lenU;

  if (u._not_nil()) { lenU = u.length(); } else { lenU = 0; }
  if (badLevel == vect::BadLevel_NoVio) {
    if (u._not_nil()) {
      negU = vect::Utils_impl::vuProduct_impl(-1.0, u, vect::BadLevel_NoVio);
    }
  } else if (badLevel == vect::BadLevel_NullRes) {
    // negU = NULL;
  } else if (badLevel == vect::BadLevel_TwoDRes) {
    negU = ::sidl::array<double>::create2dCol(lenU, lenU);
  } else if (badLevel == vect::BadLevel_WrongSizeRes) {
    negU = ::sidl::array<double>::create1d(lenU+5);
  } else {
    // negU = NULL;
  }
                                                                                
  return negU;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuNegate)
}

/**
 * Return the normalizaton of the specified vector.
 * 
 * Note the tolerance is included because the implementation invokes 
 * vuDot().
 */
::sidl::basearray
vect::Utils_impl::vuNormalize_impl (
  /* in array<> */::sidl::basearray& u,
  /* in */double tol,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
//    ::vect::vDivByZeroExcept
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuNormalize)
  ::sidl::array<double> prod = NULL;
  int                   lenU;
  double                val;
                                                                                
  if (u._not_nil()) { lenU = u.length(); } else { lenU = 0; }
  if (badLevel == vect::BadLevel_NoVio) 
  {
    if (u._not_nil())
    {
      val = vect::Utils_impl::vuNorm_impl(u, tol, vect::BadLevel_NoVio);
#ifdef DEBUG
      fprintf(stderr, "vect.Utils.vuNormalize: val=%f\n", val);
#endif /* DEBUG */
      if (val != 0.0) {
        prod = vect::Utils_impl::vuProduct_impl(1.0/val, u, vect::BadLevel_NoVio);
      } else {
        // prod = NULL;
#ifdef DEBUG
        fprintf(stderr, "vect.Utils.vuNormalize: throwing DBZ exception\n");
#endif /* DEBUG */
        vect::vDivByZeroExcept ex = 
            vect::vDivByZeroExcept::_create();
        ex.setNote(
          "vuNormalize: vDivByZeroExcept: Cannot divide by zero.");
        ex.add(__FILE__, __LINE__, "vect::vDivByZeroExcept");
        throw ex;
      }
    }
  } else if (badLevel == vect::BadLevel_NullRes) {
    // prod = NULL;
  } else if (badLevel == vect::BadLevel_TwoDRes) {
    prod = ::sidl::array<double>::create2dCol(lenU, lenU);
  } else if (badLevel == vect::BadLevel_WrongSizeRes) {
    prod = ::sidl::array<double>::create1d(lenU+5);
  } else {
    // prod = NULL;
  }
                                                                                
  return prod;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuNormalize)
}

/**
 * Return the sum of the specified vectors.
 */
::sidl::basearray
vect::Utils_impl::vuSum_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuSum)
  ::sidl::array<double> sum = NULL;
  int                   i, lenU, lenV;
  ::sidl::array<double> _u = u;
  ::sidl::array<double> _v = v;                                                                                                                
  if (u._not_nil()) { lenU = u.length(); } else { lenU = 0; }
  if (badLevel == vect::BadLevel_NoVio) 
  {
    if ( (u._not_nil()) && (v._not_nil()) )
    {
      lenV = v.length();  // sidlLength(v._get_ior(), 0);
      if ( (lenU == lenV) && (u.dimen() == 1) && (v.dimen() == 1) )
      {
        sum = ::sidl::array<double>::create1d(lenU);
        for (i=0; i < lenU; i++) {
          sum.set(i, _u.get(i) + _v.get(i));
        }
      }
    }
  } else if (badLevel == vect::BadLevel_NullRes) {
    // sum = NULL;
  } else if (badLevel == vect::BadLevel_TwoDRes) {
    sum = ::sidl::array<double>::create2dCol(lenU, lenU);
  } else if (badLevel == vect::BadLevel_WrongSizeRes) {
    sum = ::sidl::array<double>::create1d(lenU+5);
  } else {
    // sum = NULL;
  }
                                                                                
  return sum;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuSum)
}

/**
 * Return the difference of the specified vectors.
 */
::sidl::basearray
vect::Utils_impl::vuDiff_impl (
  /* in array<> */::sidl::basearray& u,
  /* in array<> */::sidl::basearray& v,
  /* in */::vect::BadLevel badLevel ) 
// throws:
//    ::sidl::PostViolation
//    ::sidl::PreViolation
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(vect.Utils.vuDiff)
  ::sidl::array<double> diff = NULL;
  int                   i, lenU, lenV;
  ::sidl::array<double> _u = u;
  ::sidl::array<double> _v = v;                                                                                                                
  if (u._not_nil()) { lenU = u.length(); } else { lenU = 0; }
  if (badLevel == vect::BadLevel_NoVio)
  {
    if ( (u._not_nil()) && (v._not_nil()) )
    {
      lenV = v.length();  // sidlLength(v._get_ior(), 0);
      if ( (lenU == lenV) && (u.dimen() == 1) && (v.dimen() == 1) )
      {
        diff = ::sidl::array<double>::create1d(lenU);
        for (i=0; i < lenU; i++) {
          diff.set(i, _u.get(i) - _v.get(i));
        }
      }
    }
  } else if (badLevel == vect::BadLevel_NullRes) {
    // diff = NULL;
  } else if (badLevel == vect::BadLevel_TwoDRes) {
    diff = ::sidl::array<double>::create2dCol(lenU, lenU);
  } else if (badLevel == vect::BadLevel_WrongSizeRes) {
    diff = ::sidl::array<double>::create1d(lenU+5);
  } else {
    // diff = NULL;
  }
                                                                                
  return diff;
  // DO-NOT-DELETE splicer.end(vect.Utils.vuDiff)
}


// user defined non-static methods: (none)

// DO-NOT-DELETE splicer.begin(vect.Utils._misc)
// Nothing needed here.
// DO-NOT-DELETE splicer.end(vect.Utils._misc)

