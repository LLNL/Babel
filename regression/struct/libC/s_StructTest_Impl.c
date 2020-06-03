/*
 * File:          s_StructTest_Impl.c
 * Symbol:        s.StructTest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for s.StructTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "s.StructTest" (version 1.0)
 */

#include "s_StructTest_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif
#ifndef included_s_Empty_h
#include "s_Empty.h"
#endif
#ifndef included_s_Simple_h
#include "s_Simple.h"
#endif
#ifndef included_s_Hard_h
#include "s_Hard.h"
#endif
#ifndef included_s_Combined_h
#include "s_Combined.h"
#endif
#ifndef included_s_Rarrays_h
#include "s_Rarrays.h"
#endif

/* DO-NOT-DELETE splicer.begin(s.StructTest._includes) */
#include "s_Color.h"
#include "sidl_String.h"
#include <ctype.h>
#include <string.h>
#include <math.h>

static void
initSimple(struct s_Simple__data *s)
{
  s_Simple__init(s);
  s->d_bool = TRUE;
  s->d_char = '3';
  s->d_dcomplex.real = 3.14;
  s->d_dcomplex.imaginary = 3.14;
  s->d_double = 3.14;
  s->d_float = 3.1F;
  s->d_fcomplex.real =  3.1F;
  s->d_fcomplex.imaginary = 3.1F;
  s->d_opaque = NULL;
  s->d_int = 3;
  s->d_long = 3;
  s->d_enum = s_Color_blue;
}

static sidl_bool
checkSimple(const struct s_Simple__data *s)
{

  const double eps = 1.E-5;

  return((s->d_bool &&
           (s->d_char == '3') &&
           (fabs(s->d_dcomplex.real - 3.14) < eps) &&
           (fabs(s->d_dcomplex.imaginary - 3.14) < eps) &&
           (fabs(s->d_double - 3.14) < eps) &&
           (fabs(s->d_fcomplex.real - 3.1F) < eps) &&
           (fabs(s->d_fcomplex.imaginary - 3.1F) < eps) &&
           (fabs(s->d_float - 3.1F) < eps) &&
           (s->d_int == 3) &&
           (s->d_long == 3) &&
           (s->d_opaque == NULL) &&
           (s->d_enum == s_Color_blue)) ? TRUE : FALSE);

}

void
invertSimple(struct s_Simple__data *s) {
    s->d_bool = (s->d_bool ? FALSE : TRUE);
    s->d_char = (islower((int)s->d_char) ? toupper(s->d_char) :
                 tolower(s->d_char));
    s->d_dcomplex.imaginary = - s->d_dcomplex.imaginary;
    s->d_double = - s->d_double;
    s->d_fcomplex.imaginary = - s->d_fcomplex.imaginary;
    s->d_float = - s->d_float;
    s->d_int = - s->d_int;
    s->d_long = - s->d_long;
    s->d_enum = s_Color_red;
}

static void 
initHard(struct s_Hard__data *h, sidl_BaseInterface *_ex) {
  int32_t i;
  s_Hard__init(h);
  h->d_string = sidl_string__array_create1d(1);
  sidl_string__array_set1(h->d_string,0,"Three");
  h->d_object = sidl_BaseClass__create(_ex);
  if (*_ex) return;
  h->d_interface = sidl_BaseInterface__cast(h->d_object, _ex);
  if (*_ex) return;
  h->d_array = sidl_double__array_create1d(3);
  sidlArrayElem1(h->d_array, 0) = 1.0;
  sidlArrayElem1(h->d_array, 1) = 2.0;
  sidlArrayElem1(h->d_array, 2) = 3.0;
  h->d_objectArray = sidl_BaseClass__array_create1d(3);
  for(i = 0 ; i < 3 ; ++i) {
    sidl_BaseClass bc = sidl_BaseClass__create(_ex);
    if (*_ex) return;
    sidl_BaseClass__array_set1(h->d_objectArray, i, bc);
    sidl_BaseClass_deleteRef(bc, _ex);
    if (*_ex) return;
  }
}

static sidl_bool
checkHard(const struct s_Hard__data *h) {
  sidl_bool result=(h->d_string != NULL) ? TRUE : FALSE;
  if (result) {
   result = result && (sidlArrayDim(h->d_string) == 1) && 
      (sidlLength(h->d_string, 0) == 1);
    if (result){
       char* sString = sidl_string__array_get1(h->d_string, 0);
       if (strcmp("Three",sString)) result=FALSE;
    } 
  }

  result = result && (h->d_object != NULL);
  result = result && (h->d_interface != NULL);
  if (result) {
    sidl_bool test;
    sidl_BaseInterface ex;
    test = sidl_BaseClass_isSame(h->d_object, h->d_interface, &ex);
    if (ex) {
      sidl_BaseInterface throwaway;
      result = FALSE;
      sidl_BaseInterface_deleteRef(ex, &throwaway);
    }
    else {
      result = result && test;
    }
  }
  result = result && (h->d_array != NULL) && (h->d_objectArray != NULL);
  if (result) {
    result = result && (sidlArrayDim(h->d_array) == 1) && 
      (sidlLength(h->d_array, 0) == 3);
    result = result && (sidlArrayDim(h->d_objectArray) == 1) && 
      (sidlLength(h->d_objectArray, 0) == 3);
    if (result) {
      int32_t i;
      result = result && (sidlArrayElem1(h->d_array, 0) == 1.0);
      result = result && (sidlArrayElem1(h->d_array, 1) == 2.0);
      result = result && (sidlArrayElem1(h->d_array, 2) == 3.0);
      for(i = 0; i < 3; ++i) {
        sidl_BaseInterface be;
        sidl_BaseClass bc = sidl_BaseClass__array_get1(h->d_objectArray, i);
        result = result && (bc != NULL);
        if (result) {
          result = result && sidl_BaseClass_isType(bc, "sidl.BaseClass", &be);
          if (be) {
            sidl_BaseInterface throwaway;
            result = FALSE;
            sidl_BaseInterface_deleteRef(be, &throwaway);
          }
        }
        if (bc) {
          sidl_BaseClass_deleteRef(bc, &be);
          if (be) {
            sidl_BaseInterface throwaway;
            result = FALSE;
            sidl_BaseInterface_deleteRef(be, &throwaway);
          }
        }
      }
    }
  }
  
  return result;
}

static void
invertHard(struct s_Hard__data *h) {
  sidl_bool result=(h->d_string != NULL) ? TRUE : FALSE;
  if (result) {
   result = result && (sidlArrayDim(h->d_string) == 1) && 
      (sidlLength(h->d_string, 0) == 1);
    if (result){
       char* sString = sidl_string__array_get1(h->d_string, 0);
       if(*sString) {
        *sString = (islower(*sString) ? toupper(*sString) : tolower(*sString));
        sidl_string__array_set1(h->d_string,0,sString);
       }
    } 
  }
  if (h->d_object && h->d_interface) {
    sidl_bool test;
    sidl_BaseInterface ex, throwaway;
    test = sidl_BaseClass_isSame(h->d_object, h->d_interface, &ex);
    if (ex) {
      sidl_BaseInterface_deleteRef(ex, &throwaway);
      sidl_BaseInterface_deleteRef(h->d_interface, &throwaway);
      h->d_interface = NULL;
    }
    else {
      sidl_BaseInterface_deleteRef(h->d_interface, &throwaway);
      if (test) {
        sidl_BaseClass bc = sidl_BaseClass__create(&throwaway);
        h->d_interface = sidl_BaseInterface__cast(bc, &throwaway);
        sidl_BaseClass_deleteRef(bc, &throwaway);
      }
      else {
        h->d_interface = sidl_BaseInterface__cast(h->d_object, &throwaway);
      }
    }
  }
  if ((h->d_array) && (sidlArrayDim(h->d_array) == 1) &&
      (sidlLength(h->d_array, 0) == 3)) {
    double tmp = sidlArrayElem1(h->d_array, 0);
    sidlArrayElem1(h->d_array, 0) = sidlArrayElem1(h->d_array, 2);
    sidlArrayElem1(h->d_array, 2) = tmp;
  }
  if ((h->d_objectArray) && (sidlArrayDim(h->d_objectArray) == 1) &&
      (sidlLength(h->d_objectArray, 0) == 3)) {
    sidl_BaseClass bc = sidl_BaseClass__array_get1(h->d_objectArray, 1);
    sidl_BaseInterface throwaway;
    if (bc) {
      sidl_BaseClass__array_set1(h->d_objectArray, 1, NULL);
      sidl_BaseClass_deleteRef(bc, &throwaway);
    }
    else {
      sidl_BaseClass__array_set1(h->d_objectArray, 1, 
                                 sidl_BaseClass__create(&throwaway));
    }
  }
}

static sidl_bool
checkRarrays(const struct s_Rarrays__data *r) {

  sidl_bool result = (r->d_rarrayRaw != NULL) ;
    if (result) {
      result = result && (r->d_int == 3);
      result = result && (r->d_rarrayRaw[0] == 1.0);
      result = result && (r->d_rarrayRaw[1] == 2.0);
      result = result && (r->d_rarrayRaw[2] == 3.0);
      result = result && (r->d_rarrayFix[0] == 5.0);
      result = result && (r->d_rarrayFix[1] == 10.0);
      result = result && (r->d_rarrayFix[2] == 15.0);
    }
  return result;
}

static void
invertRarrays(struct s_Rarrays__data *r) {
      int temp;
      temp = r->d_rarrayRaw[0];
      r->d_rarrayRaw[0] = r->d_rarrayRaw[2] ;
      r->d_rarrayRaw[2] = temp ;

      temp = r->d_rarrayFix[0];
      r->d_rarrayFix[0] = r->d_rarrayFix[2] ;
      r->d_rarrayFix[2] = temp ;
}


static void
initCombined(struct s_Combined__data *c, sidl_BaseInterface *_ex)
{
  initSimple(&(c->d_simple));
  initHard(&(c->d_hard), _ex);
}

static sidl_bool
checkCombined(const struct s_Combined__data *c)
{
  return checkSimple(&(c->d_simple)) && checkHard(&(c->d_hard));
}

static void
invertCombined(struct s_Combined__data *c)
{
  invertSimple(&(c->d_simple));
  invertHard(&(c->d_hard));
}

/* DO-NOT-DELETE splicer.end(s.StructTest._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_s_StructTest__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest._load) */
    /* DO-NOT-DELETE splicer.end(s.StructTest._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_s_StructTest__ctor(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest._ctor) */
    /* DO-NOT-DELETE splicer.end(s.StructTest._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_s_StructTest__ctor2(
  /* in */ s_StructTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest._ctor2) */
    /* DO-NOT-DELETE splicer.end(s.StructTest._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_s_StructTest__dtor(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest._dtor) */
    /* DO-NOT-DELETE splicer.end(s.StructTest._dtor) */
  }
}

/*
 * Method:  returnEmpty[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_returnEmpty"

#ifdef __cplusplus
extern "C"
#endif
struct s_Empty__data
impl_s_StructTest_returnEmpty(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty) */
    struct s_Empty__data result = { 0 };
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty) */
  }
}

/*
 * Method:  passinEmpty[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinEmpty"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinEmpty(
  /* in */ s_StructTest self,
  /* in */ const struct s_Empty__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty) */
    return TRUE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty) */
  }
}

/*
 * Method:  passoutEmpty[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passoutEmpty"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passoutEmpty(
  /* in */ s_StructTest self,
  /* out */ struct s_Empty__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty) */
    return TRUE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty) */
  }
}

/*
 * Method:  passinoutEmpty[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinoutEmpty"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinoutEmpty(
  /* in */ s_StructTest self,
  /* inout */ struct s_Empty__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty) */
    return TRUE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty) */
  }
}

/*
 * Method:  passeverywhereEmpty[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passeverywhereEmpty"

#ifdef __cplusplus
extern "C"
#endif
struct s_Empty__data
impl_s_StructTest_passeverywhereEmpty(
  /* in */ s_StructTest self,
  /* in */ const struct s_Empty__data* s1,
  /* out */ struct s_Empty__data* s2,
  /* inout */ struct s_Empty__data* s3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty) */
    struct s_Empty__data result = { 0 };
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty) */
  }
}

/*
 * Method:  returnSimple[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_returnSimple"

#ifdef __cplusplus
extern "C"
#endif
struct s_Simple__data
impl_s_StructTest_returnSimple(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple) */
    struct s_Simple__data result;
    initSimple(&result);
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.returnSimple) */
  }
}

/*
 * Method:  passinSimple[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinSimple"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinSimple(
  /* in */ s_StructTest self,
  /* in */ const struct s_Simple__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple) */
    return checkSimple(s1);
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinSimple) */
  }
}

/*
 * Method:  passoutSimple[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passoutSimple"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passoutSimple(
  /* in */ s_StructTest self,
  /* out */ struct s_Simple__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple) */
    initSimple(s1);
    return TRUE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple) */
  }
}

/*
 * Method:  passinoutSimple[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinoutSimple"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinoutSimple(
  /* in */ s_StructTest self,
  /* inout */ struct s_Simple__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple) */
    sidl_bool result = checkSimple(s1);
    invertSimple(s1);
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple) */
  }
}

/*
 * Method:  passeverywhereSimple[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passeverywhereSimple"

#ifdef __cplusplus
extern "C"
#endif
struct s_Simple__data
impl_s_StructTest_passeverywhereSimple(
  /* in */ s_StructTest self,
  /* in */ const struct s_Simple__data* s1,
  /* out */ struct s_Simple__data* s2,
  /* inout */ struct s_Simple__data* s3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple) */
    struct s_Simple__data result;
    initSimple(s2);
    initSimple(&result);
    if (checkSimple(s1) && checkSimple(s3)) {
      invertSimple(s3);
    }
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple) */
  }
}

/*
 * Method:  returnHard[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_returnHard"

#ifdef __cplusplus
extern "C"
#endif
struct s_Hard__data
impl_s_StructTest_returnHard(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.returnHard) */
    struct s_Hard__data h;
    initHard(&h, _ex);
    return h;
    /* DO-NOT-DELETE splicer.end(s.StructTest.returnHard) */
  }
}

/*
 * Method:  passinHard[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinHard"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinHard(
  /* in */ s_StructTest self,
  /* in */ const struct s_Hard__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinHard) */
    return checkHard(s1);
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinHard) */
  }
}

/*
 * Method:  passoutHard[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passoutHard"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passoutHard(
  /* in */ s_StructTest self,
  /* out */ struct s_Hard__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard) */
    initHard(s1, _ex);
    return (*_ex == NULL) ? TRUE : FALSE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passoutHard) */
  }
}

/*
 * Method:  passinoutHard[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinoutHard"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinoutHard(
  /* in */ s_StructTest self,
  /* inout */ struct s_Hard__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard) */
    sidl_bool result = checkHard(s1);
    invertHard(s1);
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard) */
  }
}

/*
 * Method:  passeverywhereHard[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passeverywhereHard"

#ifdef __cplusplus
extern "C"
#endif
struct s_Hard__data
impl_s_StructTest_passeverywhereHard(
  /* in */ s_StructTest self,
  /* in */ const struct s_Hard__data* s1,
  /* out */ struct s_Hard__data* s2,
  /* inout */ struct s_Hard__data* s3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard) */
    struct s_Hard__data result;
    initHard(&result, _ex); SIDL_CHECK(*_ex);
    initHard(s2, _ex); SIDL_CHECK(*_ex);
    if (checkHard(s1) && checkHard(s3)) {
      invertHard(s3);
    }
  EXIT:;
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard) */
  }
}

/*
 * Method:  returnCombined[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_returnCombined"

#ifdef __cplusplus
extern "C"
#endif
struct s_Combined__data
impl_s_StructTest_returnCombined(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined) */
    struct s_Combined__data result;
    initCombined(&result, _ex);
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.returnCombined) */
  }
}

/*
 * Method:  passinCombined[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinCombined"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinCombined(
  /* in */ s_StructTest self,
  /* in */ const struct s_Combined__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined) */
    return checkCombined(s1);
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinCombined) */
  }
}

/*
 * Method:  passoutCombined[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passoutCombined"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passoutCombined(
  /* in */ s_StructTest self,
  /* out */ struct s_Combined__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined) */
    initCombined(s1, _ex);
    return (*_ex == NULL) ? TRUE : FALSE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined) */
  }
}

/*
 * Method:  passinoutCombined[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinoutCombined"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinoutCombined(
  /* in */ s_StructTest self,
  /* inout */ struct s_Combined__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined) */
    sidl_bool result = checkCombined(s1);
    invertCombined(s1);
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined) */
  }
}

/*
 * Method:  passeverywhereCombined[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passeverywhereCombined"

#ifdef __cplusplus
extern "C"
#endif
struct s_Combined__data
impl_s_StructTest_passeverywhereCombined(
  /* in */ s_StructTest self,
  /* in */ const struct s_Combined__data* s1,
  /* out */ struct s_Combined__data* s2,
  /* inout */ struct s_Combined__data* s3,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined) */
    struct s_Combined__data result;
    initCombined(&result, _ex); SIDL_CHECK(*_ex);
    initCombined(s2, _ex); SIDL_CHECK(*_ex);
    if (checkCombined(s1) && checkCombined(s3)) {
      invertCombined(s3);
    }
  EXIT:;
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined) */
  }
}

/*
 * Method:  passinRarrays[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinRarrays"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinRarrays(
  /* in */ s_StructTest self,
  /* in */ const struct s_Rarrays__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays) */
    return checkRarrays(s1);
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays) */
  }
}

/*
 * Method:  passinoutRarrays[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passinoutRarrays"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passinoutRarrays(
  /* in */ s_StructTest self,
  /* inout */ struct s_Rarrays__data* s1,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays) */
    sidl_bool result = checkRarrays(s1);
    invertRarrays(s1);
    return result;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays) */
  }
}

/*
 * Method:  passeverywhereRarrays[]
 */

#undef __FUNC__
#define __FUNC__ "impl_s_StructTest_passeverywhereRarrays"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_s_StructTest_passeverywhereRarrays(
  /* in */ s_StructTest self,
  /* in */ const struct s_Rarrays__data* s1,
  /* inout */ struct s_Rarrays__data* s2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays) */
    if (checkRarrays(s1) && checkRarrays(s2)) {
      invertRarrays(s2);
      return TRUE; 
    }
    return FALSE;
    /* DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* DO-NOT-DELETE splicer.end(_misc) */

