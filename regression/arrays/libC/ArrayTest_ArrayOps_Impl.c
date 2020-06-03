/*
 * File:          ArrayTest_ArrayOps_Impl.c
 * Symbol:        ArrayTest.ArrayOps-v1.3
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for ArrayTest.ArrayOps
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "ArrayTest.ArrayOps" (version 1.3)
 */

#include "ArrayTest_ArrayOps_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._includes) */
#include "ArrayTest_ArrayOps_IOR.h"
#include "ArrayTest_ArrayOps.h"
#include "sidl_BaseInterface.h"
#include "sidlArray.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "sidl_Exception.h"

static int32_t intFunc(const int dimen, const int32_t ind[])
{
  int32_t result = 1;
  int i;
  for(i = 0;i < dimen; ++i){
    result *= (ind[i] + (i + 1));
  }
  return result;
}

/** The arrays are passed in by reference here, this function increased
 *  the indicies, until it goes over the end of the array, it then returns
 *   false.
 */

static int
next(const int dimen, int32_t ind[],
     const int32_t lower[], const int32_t upper[])
{
  int i = 0;
  while ((i < dimen) && (++(ind[i]) > upper[i])) {
    ind[i] = lower[i];
    ++i;
  }
  return i < dimen;
}
/** This function creates a test C array according to the specs.
 *  One odd thing is it tests all 3 ways of setting an arrays element  
 *  It selects amoung these choices randomly.  (C Macro, array indicies,
 *  and explicit indicies. ) 
 */
static struct sidl_int__array *
makeIntTestMatrix(int dimen)
{
  struct sidl_int__array *result = NULL;
  static const int32_t lower[] = {0, 0, 0, 0, 0, 0, 0};
  static const int32_t upper[] = {3, 3, 2, 2, 2, 2, 2};
  int32_t ind[] = {0, 0, 0, 0, 0, 0, 0};
  int32_t value;
  result  = sidl_int__array_createCol(dimen, lower, upper);
  do {  
    value = intFunc(dimen, ind);
    switch(rand() % 3) {
    case 0:
      switch(dimen) {
      case 3:
        sidlArrayElem3(result, ind[0], ind[1], ind[2]) = value;
        break;
      case 4:
        sidlArrayElem4(result, ind[0], ind[1], ind[2], ind[3]) = value;
        break;
      case 5:
        sidlArrayElem5(result, ind[0], ind[1], ind[2], ind[3], ind[4]) = value;
        break;
      case 6:
        sidlArrayElem6(result, ind[0], ind[1], ind[2], ind[3], ind[4],
                       ind[5]) = value;
        break;
      case 7:
        sidlArrayElem7(result, ind[0], ind[1], ind[2], ind[3], ind[4],
                       ind[5], ind[6]) = value;
        break;
      }
      break;
    case 1:
      sidl_int__array_set(result, ind, value);
      break;
    case 2:
      switch(dimen) {
      case 3:
        sidl_int__array_set3(result, ind[0], ind[1], ind[2], value);
        break;
      case 4:
        sidl_int__array_set4(result, ind[0], ind[1], ind[2], ind[3], value);
        break;
      case 5:
        sidl_int__array_set5(result, ind[0], ind[1], ind[2], ind[3], 
                             ind[4], value);
        break;
      case 6:
        sidl_int__array_set6(result, ind[0], ind[1], ind[2], ind[3], 
                             ind[4], ind[5], value);
        break;
      case 7:
        sidl_int__array_set7(result, ind[0], ind[1], ind[2], ind[3], 
                             ind[4], ind[5], ind[6], value);
        break;
      }
      break;
    }
  } while (next(dimen, ind, lower, upper));
  return result;
}

static double powTwo(int64_t i) {
  double result = 1;
  if (i >= 0) {
    while (i--) {
      result *= 2;
    }
  }
  else if (i < 0) {
    while (i++) {
      result *= 0.5;
    }
  }
  return result;
}

static float fpowTwo(int64_t i) {
  register float result = 0.0F;
  static volatile float forcestore;                               
 
  result = 1.0F;
  if (i >= 0) {
    while (i--) {
      result *= 2.0F;
    }
  }
  else if (i < 0) {
    while (i++) {
      result *= 0.5F;
    }
  }
  forcestore = result;
  return forcestore;
}

static int isPrime(const int64_t num) {
  register int64_t i;
  for(i = 3L; i*i <= num; ++i) {
    if (!(num % i)) return 0;
  }
  return 1;
}

static int64_t
nextPrime(int64_t prev) {
  if (prev <= 1L) {
    return 2L;
  }
  else if (prev == 2L) {
    return 3L;
  }
  else {
    do {
      prev += 2L;
    } while (!isPrime(prev));
    return prev;
  }
}

static const char s_TestText[] = "\
I'd rather write programs to write programs than write programs.";

static const char *s_TestWords[] = {
  "I'd",
  "rather",
  "write",
  "programs",
  "to",
  "write",
  "programs",
  "than",
  "write",
  "programs.",
  NULL
};

static const char *nextChar(const char *str) {
  if (!*(++str)) {
    str = s_TestText;
  }
  return str;
}

static const char * const* nextWord(const char *const*word) {
  if (!*(++word)){
    word = s_TestWords;
  }
  return word;
}

static int
nextElem(const int32_t dimen,
         int32_t ind[],
         const int32_t lower[],
         const int32_t upper[])
{
  int i = 0;
  while ((i < dimen) && (++(ind[i]) > upper[i])) {
    ind[i] = lower[i];
    ++i;
  }
  return i < dimen;
}

static int32_t
arrayValue(const int dimen, const int32_t ind[])
{
  int32_t result = 1;
  int i;
  for(i = 0; i < dimen; ++i){
    result *= (ind[i] + (i + 1));
  }
  return result;
}

static int
hasElements(const int dimen, const int32_t lower[], const int32_t upper[])
{
  int i;
  for (i = 0; i < dimen; ++i){
    if (lower[i] > upper[i]) return 0;
  }
  return 1;
}

static struct sidl__array *
createArrayByType(const int32_t type,
                  const int32_t dimen,
                  const int32_t lower[],
                  const int32_t upper[])
{
  switch(type){
  case sidl_bool_array:
    return (struct sidl__array *)
      sidl_bool__array_createRow(dimen, lower, upper);
  case sidl_char_array:
    return (struct sidl__array *)
      sidl_char__array_createRow(dimen, lower, upper);
  case sidl_dcomplex_array:
    return (struct sidl__array *)
      sidl_dcomplex__array_createRow(dimen, lower, upper);
  case sidl_double_array:
    return (struct sidl__array *)
      sidl_double__array_createRow(dimen, lower, upper);
  case sidl_fcomplex_array:
    return (struct sidl__array *)
      sidl_fcomplex__array_createRow(dimen, lower, upper);
  case sidl_float_array:
    return (struct sidl__array *)
      sidl_float__array_createRow(dimen, lower, upper);
  case sidl_int_array:
    return (struct sidl__array *)
      sidl_int__array_createRow(dimen, lower, upper);
  case sidl_long_array:
    return (struct sidl__array *)
      sidl_long__array_createRow(dimen, lower, upper);
  case sidl_opaque_array:
    return (struct sidl__array *)
      sidl_opaque__array_createRow(dimen, lower, upper);
  case sidl_string_array:
    return (struct sidl__array *)
      sidl_string__array_createRow(dimen, lower, upper);
  case sidl_interface_array:
    return (struct sidl__array *)
      sidl_BaseInterface__array_createRow(dimen, lower, upper);
  default:
    return NULL;
  }
}

static void
copyArrayByType(const struct sidl__array *src,
                struct sidl__array *dest)
{
  switch(sidl__array_type(src)){
  case sidl_bool_array:
    sidl_bool__array_copy((const struct sidl_bool__array *)src,
                          (struct sidl_bool__array *)dest);
    break;
  case sidl_char_array:
    sidl_char__array_copy((const struct sidl_char__array *)src,
                          (struct sidl_char__array *)dest);
    break;
  case sidl_dcomplex_array:
    sidl_dcomplex__array_copy((const struct sidl_dcomplex__array *)src, 
                              (struct sidl_dcomplex__array *)dest);
    break;
  case sidl_double_array:
    sidl_double__array_copy((const struct sidl_double__array *)src, 
                            (struct sidl_double__array *)dest);
    break;
  case sidl_fcomplex_array:
    sidl_fcomplex__array_copy((const struct sidl_fcomplex__array *)src,
                              (struct sidl_fcomplex__array *)dest);
    break;
  case sidl_float_array:
    sidl_float__array_copy((const struct sidl_float__array *)src, 
                           (struct sidl_float__array *)dest);
    break;
  case sidl_int_array:
    sidl_int__array_copy((const struct sidl_int__array *)src,
                         (struct sidl_int__array *)dest);
    break;
  case sidl_long_array:
    sidl_long__array_copy((const struct sidl_long__array *)src, 
                          (struct sidl_long__array *)dest);
    break;
  case sidl_opaque_array:
    sidl_opaque__array_copy((const struct sidl_opaque__array *)src,
                            (struct sidl_opaque__array *)dest);
    break;
  case sidl_string_array:
    sidl_string__array_copy((const struct sidl_string__array *)src,
                            (struct sidl_string__array *)dest);
    break;
  case sidl_interface_array:
    sidl_BaseInterface__array_copy
      ((const struct sidl_BaseInterface__array *)src,
       (struct sidl_BaseInterface__array *)dest);
    break;
  }
}

static void matrixMultiply(
  /*in*/ int32_t* a, /*in*/ int32_t* b, /*inout*/ int32_t* res,
    /*in*/ int32_t n, /*in*/ int32_t m, /*in*/ int32_t o)
{
  int32_t i,j,k;

  for(i=0;i<n;++i){
    for(k=0;k<o;++k){
      int32_t temp = 0;
      for(j=0;j<m;++j) {
        temp += (RarrayElem2(a,i,j,n) * RarrayElem2(b,j,k,m));
      }
      RarrayElem2(res,i,k,n) = temp;
    }
  }  
}



/* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps__ctor(
  /* in */ ArrayTest_ArrayOps self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor) */
  /* nothing needed */
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps__ctor2(
  /* in */ ArrayTest_ArrayOps self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor2) */
  /* Insert-Code-Here {ArrayTest.ArrayOps._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps__dtor(
  /* in */ ArrayTest_ArrayOps self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._dtor) */
  /* nothing needed */
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._dtor) */
  }
}

/*
 * Return <code>true</code> iff the even elements are true and
 * the odd elements are false.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkBool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkBool(
  /* in array<bool> */ struct sidl_bool__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkBool) */
  if (a && (1 == sidl_bool__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_bool__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_bool__array_upper(a,0))) {
    int32_t i, ind[1];
    for(i = sidlLower(a,0); i <= sidlUpper(a,0);  ++i) {
      ind[0] = i;
      if ((sidlArrayElem1(a,i) != sidl_bool__array_get1(a, i)) ||
          (sidlArrayElem1(a,i) != sidl_bool__array_get(a, ind)) ||
          (sidlArrayElem1(a,i) != ((i & 0x1) ? FALSE : TRUE))) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkBool) */
  }
}

/*
 * Method:  checkChar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkChar"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkChar(
  /* in array<char> */ struct sidl_char__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkChar) */
  if (a && (1 == sidl_char__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_char__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_char__array_upper(a,0))) {
    int32_t i, ind[1];
    const char *testStr = s_TestText;
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); 
        ++i, testStr = nextChar(testStr)) {
      ind[0] = i;
      if ((sidlArrayElem1(a,i) != sidl_char__array_get1(a, i)) ||
          (sidlArrayElem1(a,i) != sidl_char__array_get(a, ind)) ||
          (sidlArrayElem1(a,i) != *testStr)) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkChar) */
  }
}

/*
 * Method:  checkInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkInt"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkInt(
  /* in array<int> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkInt) */
  if (a && (1 == sidl_int__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_int__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_int__array_upper(a,0))) {
    int32_t i, ind[1];
    int64_t lprime = nextPrime(0L);
    int32_t prime;
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); 
        ++i, lprime = nextPrime(lprime)) {
      prime = (int32_t)lprime;
      ind[0] = i;
      if ((sidlArrayElem1(a,i) != sidl_int__array_get1(a, i)) ||
          (sidlArrayElem1(a,i) != sidl_int__array_get(a, ind)) ||
          (sidlArrayElem1(a,i) != prime)) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkInt) */
  }
}

/*
 * Method:  checkLong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkLong"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkLong(
  /* in array<long> */ struct sidl_long__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkLong) */
  if (a && (1 == sidl_long__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_long__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_long__array_upper(a,0))) {
    int32_t i, ind[1];
    int64_t prime = nextPrime(0L);
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); 
        ++i, prime = nextPrime(prime)) {
      ind[0] = i;
      if ((sidlArrayElem1(a,i) != sidl_long__array_get1(a, i)) ||
          (sidlArrayElem1(a,i) != sidl_long__array_get(a, ind)) ||
          (sidlArrayElem1(a,i) != prime)) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkLong) */
  }
}

/*
 * Method:  checkString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkString"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkString(
  /* in array<string> */ struct sidl_string__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkString) */
  if (a && (1 == sidl_string__array_dimen(a))) {
    int32_t i, ind[1];
    const char * const *testWord = s_TestWords;
    for(i = sidl_string__array_lower(a,0); 
        i <= sidl_string__array_upper(a,0); 
        ++i, testWord = nextWord(testWord)) {
      char *s1, *s2;
      ind[0] = i;
      s1 = sidl_string__array_get1(a, i);
      s2 = sidl_string__array_get(a, ind);
      if (!(s1 && s2 && !strcmp(s1, s2) && !strcmp(s1, *testWord))) {
        free(s1);
        free(s2);
        return FALSE;
      }
      free(s1);
      free(s2);
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkString) */
  }
}

/*
 * Method:  checkDouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkDouble"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkDouble(
  /* in array<double> */ struct sidl_double__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDouble) */
  if (a && (1 == sidl_double__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_double__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_double__array_upper(a,0))) {
    int32_t i, ind[1];
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i) {
      ind[0] = i;
      if ((sidlArrayElem1(a,i) != sidl_double__array_get1(a, i)) ||
          (sidlArrayElem1(a,i) != sidl_double__array_get(a, ind)) ||
          (sidlArrayElem1(a,i) != powTwo(-i))) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDouble) */
  }
}

/*
 * Method:  checkFloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkFloat"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkFloat(
  /* in array<float> */ struct sidl_float__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFloat) */
  if (a && (1 == sidl_float__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_float__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_float__array_upper(a,0))) {
    int32_t i, ind[1];
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i) {
      ind[0] = i;
      if ((sidlArrayElem1(a,i) != sidl_float__array_get1(a, i)) ||
          (sidlArrayElem1(a,i) != sidl_float__array_get(a, ind)) ||
          (sidlArrayElem1(a,i) != fpowTwo(-i))) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFloat) */
  }
}

/*
 * Method:  checkFcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkFcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkFcomplex(
  /* in array<fcomplex> */ struct sidl_fcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFcomplex) */
  if (a && (1 == sidl_fcomplex__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_fcomplex__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_fcomplex__array_upper(a,0))) {
    int32_t i, ind[1];
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i) {
      struct sidl_fcomplex r1 = sidl_fcomplex__array_get1(a, i);
      struct sidl_fcomplex r2;
      ind[0] = i;
      r2 = sidl_fcomplex__array_get(a, ind);
      if (memcmp(&r1, &sidlArrayElem1(a,i), 
                 sizeof(struct sidl_fcomplex)) ||
          memcmp(&r1, &r2, sizeof(struct sidl_fcomplex)) ||
          (sidlArrayElem1(a,i).real != fpowTwo(i)) ||
          (sidlArrayElem1(a,i).imaginary != fpowTwo(-i))) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFcomplex) */
  }
}

/*
 * Method:  checkDcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkDcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkDcomplex(
  /* in array<dcomplex> */ struct sidl_dcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDcomplex) */
  if (a && (1 == sidl_dcomplex__array_dimen(a)) && (1 == sidlArrayDim(a)) &&
      (sidlLower(a,0) == sidl_dcomplex__array_lower(a,0)) &&
      (sidlUpper(a,0) == sidl_dcomplex__array_upper(a,0))) {
    int32_t i, ind[1];
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i) {
      struct sidl_dcomplex r1 = sidl_dcomplex__array_get1(a, i);
      struct sidl_dcomplex r2;
      ind[0] = i;
      r2 = sidl_dcomplex__array_get(a, ind);
      if (memcmp(&r1, &sidlArrayElem1(a,i),
                 sizeof(struct sidl_dcomplex)) ||
          memcmp(&r1, &r2, sizeof(struct sidl_dcomplex)) ||
          (sidlArrayElem1(a,i).real != powTwo(i)) ||
          (sidlArrayElem1(a,i).imaginary != powTwo(-i))) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDcomplex) */
  }
}

/*
 * Method:  check2Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check2Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check2Int(
  /* in array<int,2> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Int) */
  if (a && (2 == sidl_int__array_dimen(a)) && (2 == sidlArrayDim(a))) {
    int32_t i, j, ind[2];
    for(i = 0; i < 2; ++i){
      if ((sidlLower(a,i) != sidl_int__array_lower(a,i)) ||
          (sidlUpper(a,i) != sidl_int__array_upper(a,i))) {
        return FALSE;
      }
    }
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i){
      ind[0] = i;
      for(j = sidlLower(a,1); j <= sidlUpper(a,1); ++j) {
        ind[1] = j;     
        if ((sidlArrayElem2(a, i, j) != 
             sidl_int__array_get2(a, i, j)) ||
            (sidlArrayElem2(a, i, j) !=
             sidl_int__array_get(a, ind)) ||
            (sidlArrayElem2(a, i, j) != (int)powTwo(abs(i-j)))) {
          return FALSE;
        }
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Int) */
  }
}

/*
 * Method:  check2Double[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check2Double"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check2Double(
  /* in array<double,2> */ struct sidl_double__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Double) */
  if (a && (2 == sidl_double__array_dimen(a)) && (2 == sidlArrayDim(a))) {
    int32_t i, j, ind[2];
    for(i = 0; i < 2; ++i){
      if ((sidlLower(a,i) != sidl_double__array_lower(a,i)) ||
          (sidlUpper(a,i) != sidl_double__array_upper(a,i))) {
        return FALSE;
      }
    }
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i){
      for(j = sidlLower(a,1); j <= sidlUpper(a,1); ++j) {
        ind[0] = i;
        ind[1] = j;
        if ((sidlArrayElem2(a, i, j) != 
             sidl_double__array_get2(a, i, j)) ||
            (sidlArrayElem2(a, i, j) !=
             sidl_double__array_get(a, ind)) ||
            (sidlArrayElem2(a, i, j) != powTwo(i-j))) {
          return FALSE;
        }
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Double) */
  }
}

/*
 * Method:  check2Float[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check2Float"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check2Float(
  /* in array<float,2> */ struct sidl_float__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Float) */
  if (a && (2 == sidl_float__array_dimen(a)) && (2 == sidlArrayDim(a))) {
    int32_t i, j, ind[2];
    for(i = 0; i < 2; ++i){
      if ((sidlLower(a,i) != sidl_float__array_lower(a,i)) ||
          (sidlUpper(a,i) != sidl_float__array_upper(a,i))) {
        return FALSE;
      }
    }
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i){
      for(j = sidlLower(a,1); j <= sidlUpper(a,1); ++j) {
        ind[0] = i;
        ind[1] = j;
        if ((sidlArrayElem2(a, i, j) != 
             sidl_float__array_get2(a, i, j)) ||
            (sidlArrayElem2(a, i, j) !=
             sidl_float__array_get(a, ind)) ||
            (sidlArrayElem2(a, i, j) != fpowTwo(i-j))) {
          return FALSE;
        }
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Float) */
  }
}

/*
 * Method:  check2Fcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check2Fcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check2Fcomplex(
  /* in array<fcomplex,2> */ struct sidl_fcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Fcomplex) */
  if (a && (2 == sidl_fcomplex__array_dimen(a)) && (2 == sidlArrayDim(a))) {
    int32_t i, j, ind[2];
    for(i = 0; i < 2; ++i){
      if ((sidlLower(a,i) != sidl_fcomplex__array_lower(a,i)) ||
          (sidlUpper(a,i) != sidl_fcomplex__array_upper(a,i))) {
        return FALSE;
      }
    }
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i){
      for(j = sidlLower(a,1); j <= sidlUpper(a,1); ++j) {
        struct sidl_fcomplex r1 = sidl_fcomplex__array_get2(a, i, j);
        struct sidl_fcomplex r2;
        ind[0] = i;
        ind[1] = j;
        r2 = sidl_fcomplex__array_get(a, ind);
        if (memcmp(&r1, &sidlArrayElem2(a, i, j),
                   sizeof(struct sidl_fcomplex)) ||
            memcmp(&r1, &r2, sizeof(struct sidl_fcomplex)) ||
            (sidlArrayElem2(a, i, j).real != fpowTwo(i)) ||
            (sidlArrayElem2(a, i, j).imaginary != fpowTwo(-j))) {
          return FALSE;
        }
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Fcomplex) */
  }
}

/*
 * Method:  check2Dcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check2Dcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check2Dcomplex(
  /* in array<dcomplex,2> */ struct sidl_dcomplex__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Dcomplex) */
  if (a && (2 == sidl_dcomplex__array_dimen(a)) && (2 == sidlArrayDim(a))) {
    int32_t i, j, ind[2];
    for(i = 0; i < 2; ++i){
      if ((sidlLower(a,i) != sidl_dcomplex__array_lower(a,i)) ||
          (sidlUpper(a,i) != sidl_dcomplex__array_upper(a,i))) {
        return FALSE;
      }
    }
    for(i = sidlLower(a,0); i <= sidlUpper(a,0); ++i){
      for(j = sidlLower(a,1); j <= sidlUpper(a,1); ++j) {
        struct sidl_dcomplex r1 = sidl_dcomplex__array_get2(a, i, j);
        struct sidl_dcomplex r2;
        ind[0] = i;
        ind[1] = j;
        r2 = sidl_dcomplex__array_get(a, ind);
        if (memcmp(&r1, &sidlArrayElem2(a, i, j),
                   sizeof(struct sidl_dcomplex)) ||
            memcmp(&r1, &r2, sizeof(struct sidl_dcomplex)) ||
            (sidlArrayElem2(a, i, j).real != powTwo(i)) ||
            (sidlArrayElem2(a, i, j).imaginary != powTwo(-j))) {
          return FALSE;
        }
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Dcomplex) */
  }
}

/*
 * Method:  check3Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check3Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check3Int(
  /* in array<int,3> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check3Int) */
  
  int result = FALSE;
  if (a && sidl_int__array_dimen(a) == 3) {
    result = TRUE;
    if (hasElements(3, a->d_metadata.d_lower, a->d_metadata.d_upper)) {
      int32_t ind[3], value;
      memcpy(ind, a->d_metadata.d_lower, sizeof(int32_t)*3);
      do {
        value = arrayValue(3, ind);
        switch(rand() % 3) {
        case 0:
          if (sidlArrayElem3(a, ind[0], ind[1], ind[2]) != value) {
            result = FALSE;
          }
          break;
        case 1:
          if (sidl_int__array_get3(a, ind[0], ind[1], ind[2]) != value) {
            result = FALSE;
          }
          break;
        case 2:
          if (sidl_int__array_get(a, ind) != value) {
            result = FALSE;
          }
          break;
        }
      } while (result && nextElem(3, ind, a->d_metadata.d_lower,
                                  a->d_metadata.d_upper));
    }
  }
  
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check3Int) */
  }
}

/*
 * Method:  check4Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check4Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check4Int(
  /* in array<int,4> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check4Int) */
  int result = FALSE;
  if (a && sidl_int__array_dimen(a) == 4) {
    result = TRUE;
    if (hasElements(4, a->d_metadata.d_lower, a->d_metadata.d_upper)) {
      int32_t ind[4], value;
      memcpy(ind, a->d_metadata.d_lower, sizeof(int32_t)*4);
      do {
        
        value = arrayValue(4, ind);
        switch(rand() % 3) {
        case 0:
          if (sidlArrayElem4(a, ind[0], ind[1], ind[2], ind[3]) != value)
            result = FALSE;
          break;
        case 1:
          if (sidl_int__array_get4(a, ind[0], ind[1], ind[2], ind[3])
              != value)
            result = FALSE;
          break;
        case 2:
          if (sidl_int__array_get(a, ind) != value) 
            result = FALSE;
          break;
        }
        
      } while (result && nextElem(4, ind, a->d_metadata.d_lower,
                                  a->d_metadata.d_upper));
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check4Int) */
  }
}

/*
 * Method:  check5Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check5Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check5Int(
  /* in array<int,5> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check5Int) */
  int result = FALSE;
  if (a && sidl_int__array_dimen(a) == 5) {
    result = TRUE;
    if (hasElements(5, a->d_metadata.d_lower, a->d_metadata.d_upper)) {
      int32_t ind[5], value;
      memcpy(ind, a->d_metadata.d_lower, sizeof(int32_t)*5);
      do {
        value = arrayValue(5, ind);
        switch(rand() % 3) {
        case 0:
          if (sidlArrayElem5(a, ind[0], ind[1], ind[2], ind[3], ind[4]) != value)
            result = FALSE;
          break;
        case 1:
          if (sidl_int__array_get5(a, ind[0], ind[1], ind[2], ind[3], ind[4])
              != value)
            result = FALSE;
          break;
        case 2:
          if (sidl_int__array_get(a, ind) != value) 
            result = FALSE;
          break;
        }
      } while (result && nextElem(5, ind, a->d_metadata.d_lower, 
                                  a->d_metadata.d_upper));
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check5Int) */
  }
}

/*
 * Method:  check6Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check6Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check6Int(
  /* in array<int,6> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check6Int) */
  int result = FALSE;
  if (a && sidl_int__array_dimen(a) == 6) {
    result = TRUE;
    if (hasElements(6, a->d_metadata.d_lower, a->d_metadata.d_upper)) {
      int32_t ind[6], value;
      memcpy(ind, a->d_metadata.d_lower, sizeof(int32_t)*6);
      do {
        value = arrayValue(6, ind);
        switch(rand() % 3) {
        case 0:
          if (sidlArrayElem6(a, ind[0], ind[1], ind[2], ind[3], ind[4], ind[5]) 
              != value)
            result = FALSE;
          break;
        case 1:
          if (sidl_int__array_get6(a, ind[0], ind[1], ind[2], ind[3], 
                                   ind[4], ind[5]) != value)
            result = FALSE;
          break;
        case 2:
          if (sidl_int__array_get(a, ind) != value) 
            result = FALSE;
          break;
        }
      } while (result && nextElem(6, ind, a->d_metadata.d_lower,
                                  a->d_metadata.d_upper));
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check6Int) */
  }
}

/*
 * Method:  check7Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check7Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check7Int(
  /* in array<int,7> */ struct sidl_int__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check7Int) */
  int result = FALSE;
  if (a && sidl_int__array_dimen(a) == 7) {
    result = TRUE;
    if (hasElements(7, a->d_metadata.d_lower, a->d_metadata.d_upper)) {
      int32_t ind[7], value;
      memcpy(ind, a->d_metadata.d_lower, sizeof(int32_t)*7);
      do {
        value = arrayValue(7, ind);
        switch(rand() % 3) {
        case 0:
          if (sidlArrayElem7(a, ind[0], ind[1], ind[2], ind[3], 
                             ind[4], ind[5], ind[6]) != value)
            result = FALSE;
          break;
        case 1:
          if (sidl_int__array_get7(a, ind[0], ind[1], ind[2], ind[3], 
                                   ind[4], ind[5], ind[6]) != value)
            result = FALSE;
          break;
        case 2:
          if (sidl_int__array_get(a, ind) != value) 
            result = FALSE;
          break;
        }
      } while (result && nextElem(7, ind, a->d_metadata.d_lower, 
                                  a->d_metadata.d_upper));
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check7Int) */
  }
}

/*
 * Method:  check2String[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_check2String"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_check2String(
  /* in array<string,2> */ struct sidl_string__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2String) */
  if (a && (2 == sidl_string__array_dimen(a))) {
    
    const char * const *testWord = s_TestWords;
    int32_t i,j;
    char* tmp = NULL;
    for(i = 0; i < sidl_string__array_upper(a,0); ++i) {
      for(j = 0; j < sidl_string__array_upper(a,1); ++j) {
	testWord = nextWord(testWord);
	tmp = sidl_string__array_get2(a, i, j);
	if(strcmp(tmp, *testWord)) {
	  free(tmp);
	  return FALSE;
	}
	free(tmp);
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2String) */
  }
}

/*
 * Method:  checkObject[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkObject"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_ArrayTest_ArrayOps_checkObject(
  /* in array<ArrayTest.ArrayOps> */ struct ArrayTest_ArrayOps__array* a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkObject) */
  if (a && ArrayTest_ArrayOps__array_dimen(a) == 1) {
    const int32_t upper = ArrayTest_ArrayOps__array_upper(a, 0);
    int32_t i, count=0;
    for(i = ArrayTest_ArrayOps__array_lower(a, 0); i <= upper; ++i) {
      ArrayTest_ArrayOps obj = ArrayTest_ArrayOps__array_get1(a, i);
      ArrayTest_ArrayOps typeCheck = ArrayTest_ArrayOps__cast(obj, _ex);
      if (typeCheck != NULL) {
        ++count;
        ArrayTest_ArrayOps_deleteRef(typeCheck, _ex); SIDL_REPORT(*_ex);
      }
      if (obj) ArrayTest_ArrayOps_deleteRef(obj,_ex); SIDL_REPORT(*_ex);
    }
    return count;
  }
  return 0;
 EXIT: 
  return 0;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkObject) */
  }
}

/*
 * Method:  reverseBool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseBool"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseBool(
  /* inout array<bool> */ struct sidl_bool__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseBool) */
  sidl_bool result = FALSE;
  if (*a && sidl_bool__array_dimen(*a) == 1) {
    const int32_t upper = sidl_bool__array_upper(*a,0);
    const int32_t lower = sidl_bool__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkBool(*a, _ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_bool__array
        *copy = sidl_bool__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_bool__array_set1(copy, upper - i,
                              sidl_bool__array_get1(*a,lower + i));
      }
      sidl_bool__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        sidl_bool tmp = sidl_bool__array_get1(*a, lower + i);
        sidl_bool__array_set1(*a, lower + i,
                              sidl_bool__array_get1(*a, upper - i));
        sidl_bool__array_set1(*a, upper - i, tmp);
      }
    }
  }
  return result;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseBool) */
  }
}

/*
 * Method:  reverseChar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseChar"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseChar(
  /* inout array<char> */ struct sidl_char__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseChar) */
  sidl_bool result = FALSE;
  if (*a && sidl_char__array_dimen(*a) == 1) {
    const int32_t upper = sidl_char__array_upper(*a,0);
    const int32_t lower = sidl_char__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkChar(*a,_ex); SIDL_REPORT(*_ex); 
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_char__array
        *copy = sidl_char__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_char__array_set1(copy, upper - i,
                              sidl_char__array_get1(*a,lower + i));
      }
      sidl_char__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        char tmp = sidl_char__array_get1(*a, lower + i);
        sidl_char__array_set1(*a, lower + i,
                              sidl_char__array_get1(*a, upper - i));
        sidl_char__array_set1(*a, upper - i, tmp);
      }
    }
  }
  return result;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseChar) */
  }
}

/*
 * Method:  reverseInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseInt"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseInt(
  /* inout array<int> */ struct sidl_int__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseInt) */
  sidl_bool result = FALSE;
  if (*a && sidl_int__array_dimen(*a) == 1) {
    const int32_t upper = sidl_int__array_upper(*a,0);
    const int32_t lower = sidl_int__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkInt(*a, _ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_int__array
        *copy = sidl_int__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_int__array_set1(copy, upper - i,
                             sidl_int__array_get1(*a,lower + i));
      }
      sidl_int__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        int32_t tmp = sidl_int__array_get1(*a, lower + i);
        sidl_int__array_set1(*a, lower + i,
                             sidl_int__array_get1(*a, upper - i));
        sidl_int__array_set1(*a, upper - i, tmp);
      }
    }
  }
 EXIT:
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseInt) */
  }
}

/*
 * Method:  reverseLong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseLong"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseLong(
  /* inout array<long> */ struct sidl_long__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseLong) */
  sidl_bool result = FALSE;
  if (*a && sidl_long__array_dimen(*a) == 1) {
    const int32_t upper = sidl_long__array_upper(*a,0);
    const int32_t lower = sidl_long__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkLong(*a, _ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_long__array
        *copy = sidl_long__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_long__array_set1(copy, upper - i,
                              sidl_long__array_get1(*a,lower + i));
      }
      sidl_long__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        int64_t tmp = sidl_long__array_get1(*a, lower + i);
        sidl_long__array_set1(*a, lower + i,
                              sidl_long__array_get1(*a, upper - i));
        sidl_long__array_set1(*a, upper - i, tmp);
      }
    }
  }
  return result;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseLong) */
  }
}

/*
 * Method:  reverseString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseString"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseString(
  /* inout array<string> */ struct sidl_string__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseString) */
  sidl_bool result = FALSE;
  if (*a && sidl_string__array_dimen(*a) == 1) {
    const int32_t upper = sidl_string__array_upper(*a,0);
    const int32_t lower = sidl_string__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkString(*a,_ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_string__array
        *copy = sidl_string__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        char *tmp = sidl_string__array_get1(*a,lower + i);
        sidl_string__array_set1(copy, upper - i, tmp);
        free(tmp);
      }
      sidl_string__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        char *tmp1 = sidl_string__array_get1(*a, lower + i);
        char *tmp2 = sidl_string__array_get1(*a, upper - i);
        sidl_string__array_set1(*a, lower + i, tmp2);
        sidl_string__array_set1(*a, upper - i, tmp1);
        free(tmp2);
        free(tmp1);
      }
    }
  }
  return result;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseString) */
  }
}

/*
 * Method:  reverseDouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseDouble"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseDouble(
  /* inout array<double> */ struct sidl_double__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDouble) */
  sidl_bool result = FALSE;
  if (*a && sidl_double__array_dimen(*a) == 1) {
    const int32_t upper = sidl_double__array_upper(*a,0);
    const int32_t lower = sidl_double__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkDouble(*a, _ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_double__array
        *copy = sidl_double__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_double__array_set1(copy, upper - i,
                                sidl_double__array_get1(*a,lower + i));
      }
      sidl_double__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        double tmp = sidl_double__array_get1(*a, lower + i);
        sidl_double__array_set1(*a, lower + i,
                                sidl_double__array_get1(*a, upper - i));
        sidl_double__array_set1(*a, upper - i, tmp);
      }
    }
  }
  return result;
 EXIT:
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDouble) */
  }
}

/*
 * Method:  reverseFloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseFloat"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseFloat(
  /* inout array<float> */ struct sidl_float__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFloat) */
  sidl_bool result = FALSE;
  if (*a && sidl_float__array_dimen(*a) == 1) {
    const int32_t upper = sidl_float__array_upper(*a,0);
    const int32_t lower = sidl_float__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkFloat(*a, _ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_float__array
        *copy = sidl_float__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_float__array_set1(copy, upper - i,
                               sidl_float__array_get1(*a,lower + i));
      }
      sidl_float__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        float tmp = sidl_float__array_get1(*a, lower + i);
        sidl_float__array_set1(*a, lower + i,
                               sidl_float__array_get1(*a, upper - i));
        sidl_float__array_set1(*a, upper - i, tmp);
      }
    }
  }
 EXIT:
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFloat) */
  }
}

/*
 * Method:  reverseFcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseFcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseFcomplex(
  /* inout array<fcomplex> */ struct sidl_fcomplex__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFcomplex) */
  sidl_bool result = FALSE;
  if (*a && sidl_fcomplex__array_dimen(*a) == 1) {
    const int32_t upper = sidl_fcomplex__array_upper(*a,0);
    const int32_t lower = sidl_fcomplex__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkFcomplex(*a, _ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_fcomplex__array
        *copy = sidl_fcomplex__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_fcomplex__array_set1(copy, upper - i,
                                  sidl_fcomplex__array_get1(*a,lower + i));
      }
      sidl_fcomplex__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        struct sidl_fcomplex 
          tmp = sidl_fcomplex__array_get1(*a, lower + i);
        sidl_fcomplex__array_set1(*a, lower + i,
                                  sidl_fcomplex__array_get1(*a, upper - i));
        sidl_fcomplex__array_set1(*a, upper - i, tmp);
      }
    }
  }
 EXIT:
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFcomplex) */
  }
}

/*
 * Method:  reverseDcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_reverseDcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_reverseDcomplex(
  /* inout array<dcomplex> */ struct sidl_dcomplex__array** a,
  /* in */ sidl_bool newArray,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDcomplex) */
  sidl_bool result = FALSE;
  if (*a && sidl_dcomplex__array_dimen(*a) == 1) {
    const int32_t upper = sidl_dcomplex__array_upper(*a,0);
    const int32_t lower = sidl_dcomplex__array_lower(*a,0);
    result = ArrayTest_ArrayOps_checkDcomplex(*a,_ex); SIDL_REPORT(*_ex);
    if (newArray) {
      const int32_t len = upper-lower + 1;
      int32_t i;
      struct sidl_dcomplex__array
        *copy = sidl_dcomplex__array_createRow(1,&lower,&upper);
      for(i = 0; i < len; ++i){
        sidl_dcomplex__array_set1(copy, upper - i,
                                  sidl_dcomplex__array_get1(*a,lower + i));
      }
      sidl_dcomplex__array_deleteRef(*a);
      *a = copy;
    }
    else {
      /* reverse in place */
      const int32_t len = (upper - lower + 1) >> 1;
      int32_t i;
      for(i = 0; i < len; ++i ) {
        struct sidl_dcomplex 
          tmp = sidl_dcomplex__array_get1(*a, lower + i);
        sidl_dcomplex__array_set1(*a, lower + i,
                                  sidl_dcomplex__array_get1(*a, upper - i));
        sidl_dcomplex__array_set1(*a, upper - i, tmp);
      }
    }
  }
 EXIT:
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDcomplex) */
  }
}

/*
 * Method:  createBool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createBool"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_bool__array*
impl_ArrayTest_ArrayOps_createBool(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createBool) */
  struct sidl_bool__array* result;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_bool__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i) = ((i & 0x1) ? FALSE : TRUE);
      break;
    case 1:
      sidl_bool__array_set1(result, i, ((i & 0x1) ? FALSE : TRUE));
      break;
    case 2:
      sidl_bool__array_set(result, lower, ((i & 0x1) ? FALSE : TRUE));
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createBool) */
  }
}

/*
 * Method:  createChar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createChar"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_char__array*
impl_ArrayTest_ArrayOps_createChar(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createChar) */
  struct sidl_char__array* result;
  const char *testStr = s_TestText;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_char__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i, testStr = nextChar(testStr)) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i) = *testStr;
      break;
    case 1:
      sidl_char__array_set1(result, i, *testStr);
      break;
    case 2:
      sidl_char__array_set(result, lower, *testStr);
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createChar) */
  }
}

/*
 * Method:  createInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createInt"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_createInt(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createInt) */
  struct sidl_int__array* result;
  int64_t lprime = nextPrime(0L);
  int32_t lower[1], upper[1], i, prime;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_int__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i, lprime = nextPrime(lprime)) {
    prime = (int32_t)lprime;
    lower[0] = i;
    switch(rand() % 3) {
    case 0:      sidlArrayElem1(result, i) = prime;
      break;
    case 1:
      sidl_int__array_set1(result, i, prime);
      break;
    case 2:
      sidl_int__array_set(result, lower, prime);
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createInt) */
  }
}

/*
 * Method:  createLong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createLong"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_long__array*
impl_ArrayTest_ArrayOps_createLong(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createLong) */
  struct sidl_long__array* result;
  int32_t lower[1], upper[1], i;
  int64_t prime = nextPrime(0L);
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_long__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i, prime = nextPrime(prime)) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i) = prime;
      break;
    case 1:
      sidl_long__array_set1(result, i, prime);
      break;
    case 2:
      sidl_long__array_set(result, lower, prime);
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createLong) */
  }
}

/*
 * Method:  createString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createString"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_string__array*
impl_ArrayTest_ArrayOps_createString(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createString) */
  struct sidl_string__array* result;
  const char * const *testWord = s_TestWords;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_string__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i, testWord = nextWord(testWord)) {
    lower[0] = i;
    switch(rand() % 2) {
    case 0:
      sidl_string__array_set1(result, i, *testWord);
      break;
    case 1:
      sidl_string__array_set(result, lower, *testWord);
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createString) */
  }
}

/*
 * Method:  createDouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createDouble"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_double__array*
impl_ArrayTest_ArrayOps_createDouble(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDouble) */
  struct sidl_double__array* result;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_double__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i) = powTwo(-i);
      break;
    case 1:
      sidl_double__array_set1(result, i, powTwo(-i));
      break;
    case 2:
      sidl_double__array_set(result, lower, powTwo(-i));
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDouble) */
  }
}

/*
 * Method:  createFloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createFloat"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_float__array*
impl_ArrayTest_ArrayOps_createFloat(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFloat) */
  struct sidl_float__array* result;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_float__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i) = fpowTwo(-i);
      break;
    case 1:
      sidl_float__array_set1(result, i, fpowTwo(-i));
      break;
    case 2:
      sidl_float__array_set(result, lower, fpowTwo(-i));
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFloat) */
  }
}

/*
 * Method:  createFcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createFcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_fcomplex__array*
impl_ArrayTest_ArrayOps_createFcomplex(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFcomplex) */
  struct sidl_fcomplex__array* result;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_fcomplex__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i).real = fpowTwo(i);
      sidlArrayElem1(result, i).imaginary = fpowTwo(-i);
      break;
    case 1:
      {
        struct sidl_fcomplex tmp;
        tmp.real = fpowTwo(i);
        tmp.imaginary = fpowTwo(-i);
        sidl_fcomplex__array_set1(result, i, tmp);
      }
      break;
    case 2:
      {
        struct sidl_fcomplex tmp;
        tmp.real = fpowTwo(i);
        tmp.imaginary = fpowTwo(-i);
        sidl_fcomplex__array_set(result, lower, tmp);
      }
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFcomplex) */
  }
}

/*
 * Method:  createDcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createDcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_dcomplex__array*
impl_ArrayTest_ArrayOps_createDcomplex(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDcomplex) */
  struct sidl_dcomplex__array* result;
  int32_t lower[1], upper[1], i;
  if (len < 0) return NULL;
  lower[0] = 0;
  upper[0] = len - 1;
  result = sidl_dcomplex__array_createRow(1, lower, upper);
  for(i = 0; i < len; ++i) {
    lower[0] = i;
    switch(rand() % 3) {
    case 0:
      sidlArrayElem1(result, i).real = powTwo(i);
      sidlArrayElem1(result, i).imaginary = powTwo(-i);
      break;
    case 1:
      {
        struct sidl_dcomplex tmp;
        tmp.real = powTwo(i);
        tmp.imaginary = powTwo(-i);
        sidl_dcomplex__array_set1(result, i, tmp);
      }
      break;
    case 2:
      {
        struct sidl_dcomplex tmp;
        tmp.real = powTwo(i);
        tmp.imaginary = powTwo(-i);
        sidl_dcomplex__array_set(result, lower, tmp);
      }
      break;
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDcomplex) */
  }
}

/*
 * Method:  createObject[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createObject"

#ifdef __cplusplus
extern "C"
#endif
struct ArrayTest_ArrayOps__array*
impl_ArrayTest_ArrayOps_createObject(
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createObject) */
  struct ArrayTest_ArrayOps__array *a = NULL;
  if (len >= 0) {
    int32_t lower = 0, upper = len - 1;
    a = ArrayTest_ArrayOps__array_createRow(1, &lower, &upper);
    while (len--) {
      ArrayTest_ArrayOps obj = ArrayTest_ArrayOps__create(_ex); SIDL_REPORT(*_ex);
      ArrayTest_ArrayOps__array_set1(a, len, obj);
      ArrayTest_ArrayOps_deleteRef(obj,_ex); SIDL_REPORT(*_ex);
    }
  }
 EXIT:
  return a;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createObject) */
  }
}

/*
 * Method:  create2Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create2Int"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create2Int(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Int) */
  struct sidl_int__array* result;
  int32_t lower[2], upper[2], i, j;
  if ((d1 < 0) || (d2 < 0)) return NULL;
  lower[0] = 0;
  upper[0] = d1 - 1;
  lower[1] = 0;
  upper[1] = d2 - 1;
  result = sidl_int__array_createRow(2, lower, upper);
  for(i = 0; i < d1; ++i) {
    lower[0] = i;
    for(j = 0; j < d2; ++j) {
      lower[1] = j;
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result, i, j) = powTwo(abs(i-j));
        break;
      case 1:
        sidl_int__array_set2(result, i, j, powTwo(abs(i-j)));
        break;
      case 2:
        sidl_int__array_set(result, lower, powTwo(abs(i-j)));
        break;
      }
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Int) */
  }
}

/*
 * Method:  create2Double[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create2Double"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_double__array*
impl_ArrayTest_ArrayOps_create2Double(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Double) */
  struct sidl_double__array* result;
  int32_t lower[2], upper[2], i, j;
  if ((d1 < 0) || (d2 < 0)) return NULL;
  lower[0] = 0;
  upper[0] = d1 - 1;
  lower[1] = 0;
  upper[1] = d2 - 1;
  result = sidl_double__array_createRow(2, lower, upper);
  for(i = 0; i < d1; ++i) {
    lower[0] = i;
    for(j = 0; j < d2; ++j) {
      lower[1] = j;
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result, i, j) = powTwo(i-j);
        break;
      case 1:
        sidl_double__array_set2(result, i, j, powTwo(i-j));
        break;
      case 2:
        sidl_double__array_set(result, lower, powTwo(i-j));
        break;
      }
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Double) */
  }
}

/*
 * Method:  create2Float[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create2Float"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_float__array*
impl_ArrayTest_ArrayOps_create2Float(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Float) */
  struct sidl_float__array* result;
  int32_t lower[2], upper[2], i, j;
  if ((d1 < 0) || (d2 < 0)) return NULL;
  lower[0] = 0;
  upper[0] = d1 - 1;
  lower[1] = 0;
  upper[1] = d2 - 1;
  result = sidl_float__array_createRow(2, lower, upper);
  for(i = 0; i < d1; ++i) {
    lower[0] = i;
    for(j = 0; j < d2; ++j) {
      lower[1] = j;
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result, i, j) = powTwo(i-j);
        break;
      case 1:
        sidl_float__array_set2(result, i, j, powTwo(i-j));
        break;
      case 2:
        sidl_float__array_set(result, lower, powTwo(i-j));
        break;
      }
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Float) */
  }
}

/*
 * Method:  create2Dcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create2Dcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_dcomplex__array*
impl_ArrayTest_ArrayOps_create2Dcomplex(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Dcomplex) */
  struct sidl_dcomplex__array* result;
  int32_t lower[2], upper[2], i, j;
  if ((d1 < 0) || (d2 < 0)) return NULL;
  lower[0] = 0;
  upper[0] = d1 - 1;
  lower[1] = 0;
  upper[1] = d2 - 1;
  result = sidl_dcomplex__array_createRow(2, lower, upper);
  for(i = 0; i < d1; ++i) {
    lower[0] = i;
    for(j = 0; j < d2; ++j) {
      struct sidl_dcomplex tmp;
      tmp.real = powTwo(i);
      tmp.imaginary = powTwo(-j);
      lower[1] = j;
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result, i, j) = tmp;
        break;
      case 1:
        sidl_dcomplex__array_set2(result, i, j, tmp);
        break;
      case 2:
        sidl_dcomplex__array_set(result, lower, tmp);
        break;
      }
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Dcomplex) */
  }
}

/*
 * Method:  create2Fcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create2Fcomplex"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_fcomplex__array*
impl_ArrayTest_ArrayOps_create2Fcomplex(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Fcomplex) */
  struct sidl_fcomplex__array* result;
  int32_t lower[2], upper[2], i, j;
  if ((d1 < 0) || (d2 < 0)) return NULL;
  lower[0] = 0;
  upper[0] = d1 - 1;
  lower[1] = 0;
  upper[1] = d2 - 1;
  result = sidl_fcomplex__array_createRow(2, lower, upper);
  for(i = 0; i < d1; ++i) {
    lower[0] = i;
    for(j = 0; j < d2; ++j) {
      struct sidl_fcomplex tmp;
      tmp.real = fpowTwo(i);
      tmp.imaginary = fpowTwo(-j);
      lower[1] = j;
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result, i, j) = tmp;
        break;
      case 1:
        sidl_fcomplex__array_set2(result, i, j, tmp);
        break;
      case 2:
        sidl_fcomplex__array_set(result, lower, tmp);
        break;
      }
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Fcomplex) */
  }
}

/*
 * Method:  create2String[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create2String"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_string__array*
impl_ArrayTest_ArrayOps_create2String(
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2String) */
  struct sidl_string__array* result;
  const char * const *testWord = s_TestWords;
  int32_t lower[2], upper[2], i,j;
  if (d1 < 0 || d2 < 0) return NULL;
  lower[0] = 0;
  upper[0] = d1 - 1;
  lower[1] = 0;
  upper[1] = d2 - 1;
  result = sidl_string__array_createRow(2, lower, upper);
  for(i = 0; i < d1-1; ++i) {
    for(j = 0; j < d2-1; ++j) {
      testWord = nextWord(testWord);
      sidl_string__array_set2(result, i, j, *testWord);
    }
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2String) */
  }
}

/*
 * Method:  create3Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create3Int"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create3Int(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create3Int) */
  return makeIntTestMatrix(3);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create3Int) */
  }
}

/*
 * Method:  create4Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create4Int"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create4Int(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create4Int) */
  return makeIntTestMatrix(4);;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create4Int) */
  }
}

/*
 * Method:  create5Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create5Int"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create5Int(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create5Int) */
  return makeIntTestMatrix(5);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create5Int) */
  }
}

/*
 * Method:  create6Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create6Int"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create6Int(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create6Int) */
  return makeIntTestMatrix(6);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create6Int) */
  }
}

/*
 * Method:  create7Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_create7Int"

#ifdef __cplusplus
extern "C"
#endif
struct sidl_int__array*
impl_ArrayTest_ArrayOps_create7Int(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create7Int) */
  return makeIntTestMatrix(7);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create7Int) */
  }
}

/*
 * Method:  makeBool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeBool"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeBool(
  /* in */ int32_t len,
  /* out array<bool> */ struct sidl_bool__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeBool) */
  *a =  impl_ArrayTest_ArrayOps_createBool(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeBool) */
  }
}

/*
 * Method:  makeChar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeChar"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeChar(
  /* in */ int32_t len,
  /* out array<char> */ struct sidl_char__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeChar) */
  *a = impl_ArrayTest_ArrayOps_createChar(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeChar) */
  }
}

/*
 * Method:  makeInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInt"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInt(
  /* in */ int32_t len,
  /* out array<int> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInt) */
  *a = impl_ArrayTest_ArrayOps_createInt(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInt) */
  }
}

/*
 * Method:  makeLong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeLong"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeLong(
  /* in */ int32_t len,
  /* out array<long> */ struct sidl_long__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeLong) */
  *a = impl_ArrayTest_ArrayOps_createLong(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeLong) */
  }
}

/*
 * Method:  makeString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeString"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeString(
  /* in */ int32_t len,
  /* out array<string> */ struct sidl_string__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeString) */
  *a = impl_ArrayTest_ArrayOps_createString(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeString) */
  }
}

/*
 * Method:  makeDouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeDouble"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeDouble(
  /* in */ int32_t len,
  /* out array<double> */ struct sidl_double__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDouble) */
  *a = impl_ArrayTest_ArrayOps_createDouble(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDouble) */
  }
}

/*
 * Method:  makeFloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeFloat"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeFloat(
  /* in */ int32_t len,
  /* out array<float> */ struct sidl_float__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFloat) */
  *a = impl_ArrayTest_ArrayOps_createFloat(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFloat) */
  }
}

/*
 * Method:  makeFcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeFcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeFcomplex(
  /* in */ int32_t len,
  /* out array<fcomplex> */ struct sidl_fcomplex__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFcomplex) */
  *a = impl_ArrayTest_ArrayOps_createFcomplex(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFcomplex) */
  }
}

/*
 * Method:  makeDcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeDcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeDcomplex(
  /* in */ int32_t len,
  /* out array<dcomplex> */ struct sidl_dcomplex__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDcomplex) */
  *a = impl_ArrayTest_ArrayOps_createDcomplex(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDcomplex) */
  }
}

/*
 * Method:  makeInOutBool[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutBool"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutBool(
  /* inout array<bool> */ struct sidl_bool__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutBool) */
  if (*a) sidl_bool__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createBool(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutBool) */
  }
}

/*
 * Method:  makeInOutChar[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutChar"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutChar(
  /* inout array<char> */ struct sidl_char__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutChar) */
  if (*a) sidl_char__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createChar(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutChar) */
  }
}

/*
 * Method:  makeInOutInt[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutInt"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutInt(
  /* inout array<int> */ struct sidl_int__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutInt) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createInt(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutInt) */
  }
}

/*
 * Method:  makeInOutLong[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutLong"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutLong(
  /* inout array<long> */ struct sidl_long__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutLong) */
  if (*a) sidl_long__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createLong(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutLong) */
  }
}

/*
 * Method:  makeInOutString[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutString"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutString(
  /* inout array<string> */ struct sidl_string__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutString) */
  if (*a) sidl_string__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createString(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutString) */
  }
}

/*
 * Method:  makeInOutDouble[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutDouble"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutDouble(
  /* inout array<double> */ struct sidl_double__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDouble) */
  if (*a) sidl_double__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createDouble(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDouble) */
  }
}

/*
 * Method:  makeInOutFloat[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutFloat"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutFloat(
  /* inout array<float> */ struct sidl_float__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFloat) */
  if (*a) sidl_float__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createFloat(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFloat) */
  }
}

/*
 * Method:  makeInOutDcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutDcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutDcomplex(
  /* inout array<dcomplex> */ struct sidl_dcomplex__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDcomplex) */
  if (*a) sidl_dcomplex__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createDcomplex(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDcomplex) */
  }
}

/*
 * Method:  makeInOutFcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOutFcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOutFcomplex(
  /* inout array<fcomplex> */ struct sidl_fcomplex__array** a,
  /* in */ int32_t len,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFcomplex) */
  if (*a) sidl_fcomplex__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_createFcomplex(len,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFcomplex) */
  }
}

/*
 * Method:  makeInOut2Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut2Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut2Int(
  /* inout array<int,2> */ struct sidl_int__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Int) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create2Int(d1, d2,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Int) */
  }
}

/*
 * Method:  makeInOut2Double[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut2Double"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut2Double(
  /* inout array<double,2> */ struct sidl_double__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Double) */
  if (*a) sidl_double__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create2Double(d1, d2,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Double) */
  }
}

/*
 * Method:  makeInOut2Float[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut2Float"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut2Float(
  /* inout array<float,2> */ struct sidl_float__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Float) */
  if (*a) sidl_float__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create2Float(d1, d2,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Float) */
  }
}

/*
 * Method:  makeInOut2Dcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut2Dcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut2Dcomplex(
  /* inout array<dcomplex,2> */ struct sidl_dcomplex__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Dcomplex) */
  if (*a) sidl_dcomplex__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create2Dcomplex(d1, d2,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Dcomplex) */
  }
}

/*
 * Method:  makeInOut2Fcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut2Fcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut2Fcomplex(
  /* inout array<fcomplex,2> */ struct sidl_fcomplex__array** a,
  /* in */ int32_t d1,
  /* in */ int32_t d2,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Fcomplex) */
  if (*a) sidl_fcomplex__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create2Fcomplex(d1, d2,_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Fcomplex) */
  }
}

/*
 * Method:  makeInOut3Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut3Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut3Int(
  /* inout array<int,3> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut3Int) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create3Int(_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut3Int) */
  }
}

/*
 * Method:  makeInOut4Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut4Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut4Int(
  /* inout array<int,4> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut4Int) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create4Int(_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut4Int) */
  }
}

/*
 * Method:  makeInOut5Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut5Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut5Int(
  /* inout array<int,5> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut5Int) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create5Int(_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut5Int) */
  }
}

/*
 * Method:  makeInOut6Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut6Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut6Int(
  /* inout array<int,6> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut6Int) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create6Int(_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut6Int) */
  }
}

/*
 * Method:  makeInOut7Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_makeInOut7Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_makeInOut7Int(
  /* inout array<int,7> */ struct sidl_int__array** a,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut7Int) */
  if (*a) sidl_int__array_deleteRef(*a);
  *a = impl_ArrayTest_ArrayOps_create7Int(_ex);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut7Int) */
  }
}

/*
 * Return as out parameters the type and dimension of the 
 * array passed in. If a is NULL, dimen == type == 0 on exit.
 * The contents of the array have the default values for a 
 * newly created array.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkGeneric"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_checkGeneric(
  /* in array<> */ struct sidl__array* a,
  /* out */ int32_t* dmn,
  /* out */ int32_t* tp,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkGeneric) */
  if (a) {
    *dmn = sidlArrayDim(a);
    *tp = sidl__array_type(a);
  }
  else {
    *dmn = 0;
    *tp = 0;
  }
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkGeneric) */
  }
}

/*
 * Create an array of the type and dimension specified and
 * return it. A type of 0 causes a NULL array to be returned.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_createGeneric"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_ArrayTest_ArrayOps_createGeneric(
  /* in */ int32_t dmn,
  /* in */ int32_t tp,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createGeneric) */
  static const int32_t lower[] = {0, 0, 0, 0, 0, 0, 0};
  static const int32_t upper[] = {2, 2, 2, 2, 2, 2, 2};
  return createArrayByType(tp, dmn, lower, upper);
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createGeneric) */
  }
}

/*
 * Testing passing generic arrays using every possible mode.
 * The returned array is a copy of inArg, so if inArg != NULL,
 * the return value should != NULL. outArg is also a copy of
 * inArg.
 * If inOutArg is NULL on entry, a 2-D array of int that should
 * pass check2Int is returned.
 * If inOutArg is not NULL on entry and its dimension is even,
 * it is returned unchanged; otherwise, NULL is returned.
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_passGeneric"

#ifdef __cplusplus
extern "C"
#endif
struct sidl__array*
impl_ArrayTest_ArrayOps_passGeneric(
  /* in array<> */ struct sidl__array* inArg,
  /* inout array<> */ struct sidl__array** inOutArg,
  /* out array<> */ struct sidl__array** outArg,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.passGeneric) */
  int32_t lower[7], upper[7], i;
  struct sidl__array *result = NULL;
  if (inArg) {
    const int32_t dimen = sidlArrayDim(inArg);
    for(i = 0; i <  dimen; ++i) { 
      lower[i] = sidlLower(inArg, i);
      upper[i] = sidlUpper(inArg, i);
    }
    result = createArrayByType(sidl__array_type(inArg), dimen, lower, upper);
    *outArg = createArrayByType(sidl__array_type(inArg), dimen, lower, upper);
    copyArrayByType(inArg, result);
    copyArrayByType(inArg, *outArg);
  }

  if (*inOutArg) {
    if (sidlArrayDim(*inOutArg) & 1) {
      sidl__array_deleteRef(*inOutArg);
      *inOutArg = NULL;
    }
  }
  else {
    *inOutArg = (struct sidl__array *)ArrayTest_ArrayOps_create2Int(3, 3,_ex);
  }

  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.passGeneric) */
  }
}

/*
 * Method:  initRarray1Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_initRarray1Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_initRarray1Int(
  /* inout rarray[n] */ int32_t* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Int) */
  int64_t lprime = nextPrime(0L);
  int32_t i, prime;
  if (n < 0) return;
  for(i = 0; i < n; ++i, lprime = nextPrime(lprime)) {
    prime = (int32_t)lprime;
    switch(rand() % 2) {
    case 0:      
      a[i] = prime;
      break;
    case 1:
      RarrayElem1(a,i) = prime;
      break;
    }
  }
  return;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Int) */
  }
}

/*
 * Method:  initRarray3Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_initRarray3Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_initRarray3Int(
  /* inout rarray[n,m,o] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray3Int) */
  int32_t lower[] = {0, 0, 0};
  int32_t upper[3];
  int32_t ind[] = {0, 0, 0};
  int32_t dimen = 3;
  upper[0] = n-1;
  upper[1] = m-1;
  upper[2] = o-1;
  do {  
    RarrayElem3(a,ind[0], ind[1], ind[2], n, m)  = intFunc(dimen, ind);
  } while (next(dimen, ind, lower, upper));
  return;


    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray3Int) */
  }
}

/*
 * Method:  initRarray7Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_initRarray7Int"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_initRarray7Int(
  /* inout rarray[n,m,o,p,q,r,s] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* in */ int32_t p,
  /* in */ int32_t q,
  /* in */ int32_t r,
  /* in */ int32_t s,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray7Int) */
  int32_t lower[] = {0, 0, 0, 0, 0, 0, 0};
  int32_t upper[7];
  int32_t ind[] = {0, 0, 0, 0, 0, 0, 0};
  int32_t dimen = 7;
  upper[0] = n-1;
  upper[1] = m-1;
  upper[2] = o-1;
  upper[3] = p-1;
  upper[4] = q-1;
  upper[5] = r-1;
  upper[6] = s-1;
  do {  
    RarrayElem7(a,ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], ind[6],
                n, m, o, p, q, r)  = intFunc(dimen, ind);
  } while (next(dimen, ind, lower, upper));
  return;

    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray7Int) */
  }
}

/*
 * Method:  initRarray1Double[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_initRarray1Double"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_initRarray1Double(
  /* inout rarray[n] */ double* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Double) */
  int32_t lower[1], upper[1], i;
  if (n < 0) return;
  lower[0] = 0;
  upper[0] = n - 1;
  for(i = 0; i < n; ++i) {
    switch(rand() % 2) {
    case 0:
      RarrayElem1(a, i) = powTwo(-i);
      break;
    case 1:
      a[i] = powTwo(-i);
      break;
    }
  }
  return;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Double) */
  }
}

/*
 * Method:  initRarray1Dcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_initRarray1Dcomplex"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_initRarray1Dcomplex(
  /* inout rarray[n] */ struct sidl_dcomplex* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Dcomplex) */
  int32_t lower[1], upper[1], i;
  if (n < 0) return;
  lower[0] = 0;
  upper[0] = n - 1;
  for(i = 0; i < n; ++i) {
    switch(rand() % 2) {
    case 0:
      RarrayElem1(a, i).real = powTwo(i);
      RarrayElem1(a, i).imaginary = powTwo(-i);
      break;
    case 1:
      {
        a[i].real = powTwo(i);
        a[i].imaginary = powTwo(-i);
      }
      break;

    }
  }
  return;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Dcomplex) */
  }
}

/*
 * Method:  checkRarray1Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkRarray1Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray1Int(
  /* in rarray[n] */ int32_t* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Int) */
  if (a) {
    int32_t i;
    int64_t lprime = nextPrime(0L);
    int32_t prime;
    for(i = 0; i < n; ++i, lprime = nextPrime(lprime)) {
      prime = (int32_t)lprime;
      if (a[i] != RarrayElem1(a,i) ||
          a[i] != prime) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Int) */
  }
}

/*
 * Method:  checkRarray3Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkRarray3Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray3Int(
  /* in rarray[n,m,o] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray3Int) */
  int result = FALSE;
  int32_t lower[3] = {0,0,0};
  int32_t upper[3];
  upper[0] = n-1;
  upper[1] = m-1;
  upper[2] = o-1;
  if (a) {

    int32_t ind[3] = {0, 0, 0};
    int32_t value = 0;
    result = TRUE;
    do {
      value = arrayValue(3, ind);
      if (RarrayElem3(a, ind[0], ind[1], ind[2], n, m) != value) {
        result = FALSE;
      }
    } while (result && nextElem(3, ind, lower, upper));
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray3Int) */
  }
}

/*
 * Method:  checkRarray7Int[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkRarray7Int"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray7Int(
  /* in rarray[n,m,o,p,q,r,s] */ int32_t* a,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* in */ int32_t p,
  /* in */ int32_t q,
  /* in */ int32_t r,
  /* in */ int32_t s,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray7Int) */
  int result = FALSE;
  int32_t lower[7] = {0,0,0,0,0,0,0};
  int32_t upper[7];
  upper[0] = n-1;
  upper[1] = m-1;
  upper[2] = o-1;
  upper[3] = p-1;
  upper[4] = q-1;
  upper[5] = r-1;
  upper[6] = s-1;
  if (a) {
    int32_t ind[7] = {0, 0, 0, 0, 0, 0, 0};
    int32_t value = 0;
    result = TRUE;
    do {
      value = arrayValue(7, ind);
      if (RarrayElem7(a, ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], ind[6],
                      n, m, o, p, q, r) != value) {
        result = FALSE;
      }
    } while (result && nextElem(7, ind, lower, upper));
  }
  return result;
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray7Int) */
  }
}

/*
 * Method:  checkRarray1Double[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkRarray1Double"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray1Double(
  /* in rarray[n] */ double* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Double) */
  if (a) {
    int32_t i, ind[1];
    for(i = 0; i < n; ++i) {
      ind[0] = i;
      if (RarrayElem1(a,i) != a[i] || a[i] != powTwo(-i)) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;

    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Double) */
  }
}

/*
 * Method:  checkRarray1Dcomplex[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkRarray1Dcomplex"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkRarray1Dcomplex(
  /* in rarray[n] */ struct sidl_dcomplex* a,
  /* in */ int32_t n,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Dcomplex) */
  if (a) {
    int32_t i;
    for(i = 0; i < n; ++i) {
      if ((RarrayElem1(a,i).real != powTwo(i)) ||
          (RarrayElem1(a,i).imaginary != powTwo(-i)) ||
          (a[i].real != powTwo(i)) ||
          (a[i].imaginary != powTwo(-i))) {
        return FALSE;
      }
    }
    return TRUE;
  }
  return FALSE;

    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Dcomplex) */
  }
}

/*
 * Method:  matrixMultiply[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_matrixMultiply"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_matrixMultiply(
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* inout rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.matrixMultiply) */
  if(a && b && res) {
    matrixMultiply(a,b,res,n,m,o);
  }
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.matrixMultiply) */
  }
}

/*
 * Method:  checkMatrixMultiply[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkMatrixMultiply"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkMatrixMultiply(
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* in rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkMatrixMultiply) */
  int32_t *test=(int32_t *)malloc(sizeof(int32_t)*
                                  (((n*o)>0) ? (n*o) : 1) );
  int32_t i,j;
  if(a && b && res && test) {
    matrixMultiply(a,b,test,n,m,o);
    for(i=0;i<n;++i) {
      for(j=0;j<o;++j) {
        if(RarrayElem2(test,i,j,n) != RarrayElem2(res,i,j,n)) {
          free(test);
          return FALSE;
        }
      }
    }
    free(test);
    return TRUE;
  }
  if (test) free(test);
  return FALSE;

    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkMatrixMultiply) */
  }
}

/*
 * Method:  mm[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_mm"

#ifdef __cplusplus
extern "C"
#endif
void
impl_ArrayTest_ArrayOps_mm(
  /* in */ ArrayTest_ArrayOps self,
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* inout rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.mm) */
  if(a && b && res) {
    matrixMultiply(a,b,res,n,m,o);
  }
    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.mm) */
  }
}

/*
 * Method:  checkmm[]
 */

#undef __FUNC__
#define __FUNC__ "impl_ArrayTest_ArrayOps_checkmm"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_ArrayTest_ArrayOps_checkmm(
  /* in */ ArrayTest_ArrayOps self,
  /* in rarray[n,m] */ int32_t* a,
  /* in rarray[m,o] */ int32_t* b,
  /* in rarray[n,o] */ int32_t* res,
  /* in */ int32_t n,
  /* in */ int32_t m,
  /* in */ int32_t o,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkmm) */
  int32_t *test=(int32_t *)malloc(sizeof(int32_t)*
                                  (((n*o)>0) ? (n*o) : 1) );
  int32_t i,j;
  if(a && b && res && test) {
    matrixMultiply(a,b,test,n,m,o);
    for(i=0;i<n;++i) {
      for(j=0;j<o;++j) {
        if(RarrayElem2(test,i,j,n) != RarrayElem2(res,i,j,n)) {
          free(test);
          return FALSE;
        }
      }
    }
    free(test);
    return TRUE;
  }
  if (test) free(test);
  return FALSE;

    /* DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkmm) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

