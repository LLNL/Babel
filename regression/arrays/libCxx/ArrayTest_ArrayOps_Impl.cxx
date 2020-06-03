// 
// File:          ArrayTest_ArrayOps_Impl.cxx
// Symbol:        ArrayTest.ArrayOps-v1.3
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for ArrayTest.ArrayOps
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "ArrayTest_ArrayOps_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_ArrayTest_ArrayOps_hxx
#include "ArrayTest_ArrayOps.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._includes)
#include "sidlArray.h"
#include <cstring>
using namespace UCXX ::ArrayTest;

double powTwo(int32_t i) {
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

float fpowTwo(int32_t i) {
  register float result = 1.0F;
  static volatile float forcestore; /* force memory storage on x86 architectures */
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
  for(i = 3; i*i <= num; ++i) {
    if (!(num % i)) return 0;
  }
  return 1;
}

int64_t
nextPrime(int64_t prev) {
  if (prev <= 1L) {
    return 2L;
  }
  else if (prev == 2L) {
    return 3L;
  }
  else {
    do {
      prev += 2;
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

const char *nextChar(const char *str) {
  if (!*(++str)) {
    str = s_TestText;
  }
  return str;
}

const char * const* nextWord(const char *const*word) {
  if (!*(++word)){
    word = s_TestWords;
  }
  return word;
}

static bool
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

static bool
hasElements(const int dimen, const int32_t lower[], const int32_t upper[])
{
  int i;
  for (i = 0; i < dimen; ++i){
    if (lower[i] > upper[i]) return false;
  }
  return true;
}

static int32_t intFunc(const int dimen, const int32_t ind[])
{
  int32_t result = 1;
  int i;
  for(i = 0;i < dimen; ++i){
    result *= (ind[i] + (i + 1));
  }
  return result;
}

static bool
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

static UCXX ::sidl::array<int32_t>
makeIntTestMatrix(int dimen)
{
  struct UCXX ::sidl::array<int32_t> result;
  static const int32_t lower[] = {0, 0, 0, 0, 0, 0, 0};
  static const int32_t upper[] = {3, 3, 2, 2, 2, 2, 2};
  int32_t ind[] = {0, 0, 0, 0, 0, 0, 0};
  int32_t value;
  result  = UCXX ::sidl::array<int32_t>::createCol(dimen, lower, upper);
  do {
    value = intFunc(dimen, ind);
    switch(rand() % 2) {
    case 0:
      switch(dimen) {
      case 3:
        result.set(ind[0], ind[1], ind[2], value);
	break;
      case 4:
        result.set(ind[0], ind[1], ind[2], ind[3], value);
	break;
      case 5:
	result.set(ind[0], ind[1], ind[2], ind[3], ind[4], value);
	break;
      case 6:
	result.set(ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], value);
	break;
      case 7:
	result.set(ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], ind[6], value);
	break;
      }
    case 1:
      result.set(ind, value);
      break;
    }
  } while (next(dimen, ind, lower, upper));
  return result;
}

static UCXX ::sidl::basearray
createArrayByType(const int32_t type,
                  const int32_t dimen,
                  const int32_t lower[],
                  const int32_t upper[])
{
  switch(type){
  case sidl_bool_array:
    return
      UCXX ::sidl::array< bool >::createRow(dimen, lower, upper);
  case sidl_char_array:
    return
      UCXX ::sidl::array< char >::createRow(dimen, lower, upper);
  case sidl_dcomplex_array:
    return
      UCXX ::sidl::array< UCXX ::sidl::dcomplex >::createRow(dimen, lower, upper);
  case sidl_double_array:
    return
      UCXX ::sidl::array< double >::createRow(dimen, lower, upper);
  case sidl_fcomplex_array:
    return
      UCXX ::sidl::array< UCXX ::sidl::fcomplex >::createRow(dimen, lower, upper);
  case sidl_float_array:
    return
      UCXX ::sidl::array< float >::createRow(dimen, lower, upper);
  case sidl_int_array:
    return
      UCXX ::sidl::array< int32_t >::createRow(dimen, lower, upper);
  case sidl_long_array:
    return
      UCXX ::sidl::array< int64_t >::createRow(dimen, lower, upper);
  case sidl_opaque_array:
    return
      UCXX ::sidl::array< UCXX ::sidl::opaque >::createRow(dimen, lower, upper);
  case sidl_string_array:
    return
      UCXX ::sidl::array< UCXX ::sidl::string >::createRow(dimen, lower, upper);
  case sidl_interface_array:
    return
      UCXX ::sidl::array< UCXX ::sidl::BaseInterface >::createRow(dimen, lower, upper);
  default:
    return UCXX ::sidl::basearray();
  }
}

static void
copyArrayByType(const UCXX ::sidl::basearray &src,
                UCXX ::sidl::basearray &dest)
{
  switch(src.arrayType()){
  case sidl_bool_array:
    static_cast< UCXX ::sidl::array< bool > &>(dest).copy
      (static_cast< const UCXX ::sidl::array< bool > &>(src));
    break;
  case sidl_char_array:
    static_cast< UCXX ::sidl::array< char > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< char > &>(src));
    break;
  case sidl_dcomplex_array:
    static_cast< UCXX ::sidl::array< UCXX ::sidl::dcomplex > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< UCXX ::sidl::dcomplex > &>(src));
    break;
  case sidl_double_array:
    static_cast< UCXX ::sidl::array< double > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< double > &>(src));
    break;
  case sidl_fcomplex_array:
    static_cast< UCXX ::sidl::array< UCXX ::sidl::fcomplex > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< UCXX ::sidl::fcomplex > &>(src));
    break;
  case sidl_float_array:
    static_cast< UCXX ::sidl::array< float > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< float > &>(src));
    break;
  case sidl_int_array:
    static_cast< UCXX ::sidl::array< int32_t > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< int32_t > &>(src));
    break;
  case sidl_long_array:
    static_cast< UCXX ::sidl::array< int64_t > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< int64_t > &>(src));
    break;
  case sidl_opaque_array:
    static_cast< UCXX ::sidl::array< UCXX ::sidl::opaque > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< UCXX ::sidl::opaque > &>(src));
    break;
  case sidl_string_array:
    static_cast< UCXX ::sidl::array< UCXX ::sidl::string > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< UCXX ::sidl::string > &>(src));
    break;
  case sidl_interface_array:
    static_cast< UCXX ::sidl::array< UCXX ::sidl::BaseInterface > &>(dest).copy
      (static_cast<  const UCXX ::sidl::array< UCXX ::sidl::BaseInterface > &>(src));
    break;
  }
}


void locMatrixMultiply(
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

// DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
ArrayTest::ArrayOps_impl::ArrayOps_impl() : StubBase(reinterpret_cast< void*>(
  ::ArrayTest::ArrayOps::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor2)
  // Insert-Code-Here {ArrayTest.ArrayOps._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor2)
}

// user defined constructor
void ArrayTest::ArrayOps_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor)
}

// user defined destructor
void ArrayTest::ArrayOps_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._dtor)
}

// static class initializer
void ArrayTest::ArrayOps_impl::_load() {
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._load)
}

// user defined static methods:
/**
 * Return <code>true</code> iff the even elements are true and
 * the odd elements are false.
 */
bool
ArrayTest::ArrayOps_impl::checkBool_impl (
  /* in array<bool> */::sidl::array<bool>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkBool)
  if ( a._is_nil() ||
       ( a.dimen() != 1) ||
       (sidlArrayDim(a._get_ior()) != 1) ||
       (a.lower(0) != sidlLower(a._get_ior(),0)) ||
       (a.upper(0) != sidlUpper(a._get_ior(),0)) ) {
    return false;
  }
  for( int32_t i=a.lower(0); i<a.upper(0); ++i) {
    if ( ( a.get(i) != sidl_bool__array_get1(a._get_ior(), i ) ) ||
	 ( a.get(i) != ((i & 0x1) ? FALSE : TRUE) ) ) { 
      return false;
    }    
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkBool)
}

/**
 * Method:  checkChar[]
 */
bool
ArrayTest::ArrayOps_impl::checkChar_impl (
  /* in array<char> */::sidl::array<char>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkChar)
  if ( a._is_nil() ||
       ( a.dimen() != 1) ||
       (sidlArrayDim(a._get_ior()) != 1) ||
       (a.lower(0) != sidlLower(a._get_ior(),0)) ||
       (a.upper(0) != sidlUpper(a._get_ior(),0)) ) {
    return false;
  }
  const char * testStr = s_TestText;
  for( int32_t i=a.lower(0); i<a.upper(0); ++i, testStr = nextChar(testStr) ) {
    if ( ( a.get(i) != sidl_char__array_get1(a._get_ior(), i ) ) ||
	 ( a.get(i) != *testStr ) ) { 
      return false;
    }    
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkChar)
}

/**
 * Method:  checkInt[]
 */
bool
ArrayTest::ArrayOps_impl::checkInt_impl (
  /* in array<int> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkInt)
  if( a._is_nil() ||
      ( a.dimen() != 1 ) ||
      ( sidlArrayDim(a._get_ior()) != 1 ) ||
      ( a.lower(0) != sidlLower(a._get_ior(),0) ) ||
      ( a.upper(0) != sidlUpper(a._get_ior(),0) ) ) { 
    return false;
  }
  int32_t prime = nextPrime(0);
  for( int32_t i=a.lower(0); i<=a.upper(0); ++i, prime = nextPrime(prime) ) { 
    if ( ( a.get(i) != sidlArrayElem1(a._get_ior(), i) ) ||
	   ( a.get(i) != prime ) ) { 
	return false;
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkInt)
}

/**
 * Method:  checkLong[]
 */
bool
ArrayTest::ArrayOps_impl::checkLong_impl (
  /* in array<long> */::sidl::array<int64_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkLong)
  if( a._is_nil() ||
      ( a.dimen() != 1 ) ||
      ( sidlArrayDim(a._get_ior()) != 1 ) ||
      ( a.lower(0) != sidlLower(a._get_ior(),0) ) ||
      ( a.upper(0) != sidlUpper(a._get_ior(),0) ) ) { 
    return false;
  }
  int64_t prime = nextPrime(0);
  for( int32_t i=a.lower(0); i<=a.upper(0); ++i, prime = nextPrime(prime) ) { 
    if ( ( a.get(i) != sidlArrayElem1(a._get_ior(), i) ) ||
	 ( a.get(i) != prime ) ) { 
      return false;
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkLong)
}

/**
 * Method:  checkString[]
 */
bool
ArrayTest::ArrayOps_impl::checkString_impl (
  /* in array<string> */::sidl::array< ::std::string>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkString)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const char * const * testWord = s_TestWords;
  for(int32_t i=a.lower(0); i<=a.upper(0); 
      ++i, testWord = nextWord(testWord) ) { 
    char * s1 = sidl_string__array_get1(a._get_ior(), i);
    std::string s2 = a.get(i);
    //const char * s2c_str = s2.c_str();
    //int size = s2.size();
    //int cmp1 = strcmp(s1,s2.c_str());
    //int cmp2 = strcmp(s2.c_str(),*testWord);
    if ( !s1 || 
	 (s2.size() == 0) ||
	 strcmp(s1,s2.c_str()) ||
	 strcmp(s2.c_str(),*testWord) ) { 
      free(s1);
      return false;
    }
    free(s1);
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkString)
}

/**
 * Method:  checkDouble[]
 */
bool
ArrayTest::ArrayOps_impl::checkDouble_impl (
  /* in array<double> */::sidl::array<double>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDouble)
  if( a._is_nil() ||
      ( a.dimen() != 1 ) ||
      ( sidlArrayDim(a._get_ior()) != 1 ) ||
      ( a.lower(0) != sidlLower(a._get_ior(),0) ) ||
      ( a.upper(0) != sidlUpper(a._get_ior(),0) ) ) { 
    return false;
  }
  for( int32_t i=a.lower(0); i<=a.upper(0); ++i ) { 
    if ( ( a.get(i) != sidlArrayElem1(a._get_ior(), i) ) ||
	 ( a.get(i) != powTwo(-i) ) ) { 
	return false;
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDouble)
}

/**
 * Method:  checkFloat[]
 */
bool
ArrayTest::ArrayOps_impl::checkFloat_impl (
  /* in array<float> */::sidl::array<float>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFloat)
  if( a._is_nil() ||
      ( a.dimen() != 1 ) ||
      ( sidlArrayDim(a._get_ior()) != 1 ) ||
      ( a.lower(0) != sidlLower(a._get_ior(),0) ) ||
      ( a.upper(0) != sidlUpper(a._get_ior(),0) ) ) { 
    return false;
  }
  for( int32_t i=a.lower(0); i<=a.upper(0); ++i ) { 
    if ( ( a.get(i) != sidlArrayElem1(a._get_ior(), i) ) ||
	   ( a.get(i) != fpowTwo(-i) ) ) { 
	return false;
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFloat)
}

/**
 * Method:  checkFcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::checkFcomplex_impl (
  /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFcomplex)
  if ( a._is_nil() ||
       (a.lower(0) != sidlLower(a._get_ior(),0) ) ||
       (a.upper(0) != sidlUpper(a._get_ior(),0) ) ) { 
    return false;
  }
  for( int32_t i=a.lower(0); i<= a.upper(0); ++i ) { 
    std::complex<double> r1 = a.get(i);
    if ( (r1.real() != sidlArrayElem1(a._get_ior(), i).real ) ||
	 (r1.imag() != sidlArrayElem1(a._get_ior(), i).imaginary ) ||
	 (r1.real() != fpowTwo(i)) ||
	 (r1.imag() != fpowTwo(-i))) { 
      return false;
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFcomplex)
}

/**
 * Method:  checkDcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::checkDcomplex_impl (
  /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDcomplex)
  if ( a._is_nil() ||
       (a.lower(0) != sidlLower(a._get_ior(),0) ) ||
       (a.upper(0) != sidlUpper(a._get_ior(),0) ) ) { 
    return false;
  }
  for( int32_t i=a.lower(0); i<= a.upper(0); ++i ) { 
    std::complex<double> r1 = a.get(i);
    if ( (r1.real() != sidlArrayElem1(a._get_ior(), i).real ) ||
	 (r1.imag() != sidlArrayElem1(a._get_ior(), i).imaginary ) ||
	 (r1.real() != powTwo(i)) ||
	 (r1.imag() != powTwo(-i))) { 
      return false;
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDcomplex)
}

/**
 * Method:  check2Int[]
 */
bool
ArrayTest::ArrayOps_impl::check2Int_impl (
  /* in array<int,2> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Int)
  if ( a._is_nil() ||
       ( a.dimen() != 2) ||
       ( sidlArrayDim(a._get_ior())) != 2 ) { 
    return false;
  }
  for( int i=0; i<2; ++i ) { 
    if ( ( a.lower(i) != sidlLower(a._get_ior(),i) ) ||
	 ( a.upper(i) != sidlUpper(a._get_ior(),i) ) ) { 
      return false;
    }
  }
  for( int32_t i=a.lower(0); i<a.upper(0); ++i ) { 
    for( int32_t j=a.lower(1); j<a.upper(1); ++j ) { 
      if ( ( a.get(i,j) != sidlArrayElem2(a._get_ior(),i,j) ) ||
	   ( a.get(i,j) != (int)powTwo(abs(i-j)) ) ) { 
	return false;
      }
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Int)
}

/**
 * Method:  check2Double[]
 */
bool
ArrayTest::ArrayOps_impl::check2Double_impl (
  /* in array<double,2> */::sidl::array<double>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Double)
  if ( a._is_nil() ||
       ( a.dimen() != 2) ||
       ( sidlArrayDim(a._get_ior())) != 2 ) { 
    return false;
  }
  for( int i=0; i<2; ++i ) { 
    if ( ( a.lower(i) != sidlLower(a._get_ior(),i) ) ||
	 ( a.upper(i) != sidlUpper(a._get_ior(),i) ) ) { 
      return false;
    }
  }
  for( int32_t i=a.lower(0); i<a.upper(0); ++i ) { 
    for( int32_t j=a.lower(1); j<a.upper(1); ++j ) { 
      if ( ( a.get(i,j) != sidlArrayElem2(a._get_ior(),i,j) ) ||
	   ( a.get(i,j) != powTwo(i-j) ) ) { 
	return false;
      }
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Double)
}

/**
 * Method:  check2Float[]
 */
bool
ArrayTest::ArrayOps_impl::check2Float_impl (
  /* in array<float,2> */::sidl::array<float>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Float)
  if ( a._is_nil() ||
       ( a.dimen() != 2 ) || 
       ( sidlArrayDim(a._get_ior()) != 2 ) ) { 
    return false;
  }
  for( int i=0; i<2; ++i ) { 
    if ( ( a.lower(i) != sidlLower(a._get_ior(),i) ) ||
	 ( a.upper(i) != sidlUpper(a._get_ior(),i) ) ) { 
      return false;
    }
  }
  for( int32_t i=a.lower(0); i<a.upper(0); ++i ) { 
    for( int32_t j=a.lower(1); j<a.upper(1); ++j ) { 
      if ( ( a.get(i,j) != sidlArrayElem2(a._get_ior(),i,j) ) ||
	   ( a.get(i,j) != (float)powTwo(i-j) ) ) { 
	
	return false;
      }
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Float)
}

/**
 * Method:  check2Fcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::check2Fcomplex_impl (
  /* in array<fcomplex,2> */::sidl::array< ::sidl::fcomplex>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Fcomplex)
  if ( a._is_nil() ||
       (a.dimen() != 2) || 
       (sidlArrayDim(a._get_ior()) != 2) ) { 
    return false;
  }
  for(int i = 0; i < 2; ++i){
    if ( ( a.lower(i) != sidlLower(a._get_ior(),i) ) ||
	 ( a.upper(i) != sidlUpper(a._get_ior(),i) ) ) { 
      return false;
    }
  } 
  for( int32_t i = a.lower(0); i<= a.upper(0); ++i ) { 
    for( int32_t j = a.lower(1); j<= a.upper(1); ++j ) { 
      std::complex<float> r1 = a.get(i,j);
      if ( (r1.real() != sidlArrayElem2(a._get_ior(), i, j).real ) ||
	   (r1.imag() != sidlArrayElem2(a._get_ior(), i, j).imaginary ) ||
	   (r1.real() != (float)powTwo(i)) ||
	   (r1.imag() != (float)powTwo(-j))) { 
	return false;
      }
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Fcomplex)
}

/**
 * Method:  check2Dcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::check2Dcomplex_impl (
  /* in array<dcomplex,2> */::sidl::array< ::sidl::dcomplex>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Dcomplex)
  if ( a._is_nil() ||
       ( a.dimen() != 2) ||
       ( sidlArrayDim(a._get_ior()) != 2)) { 
    return false;
  }
  for(int i = 0; i < 2; ++i){
    if ( ( a.lower(i) != sidlLower(a._get_ior(),i) ) ||
	 ( a.upper(i) != sidlUpper(a._get_ior(),i) ) ) { 
      return false;
    }
  } 
  for( int32_t i = a.lower(0); i<= a.upper(0); ++i ) { 
    for( int32_t j = a.lower(1); j<= a.upper(1); ++j ) { 
      std::complex<double> r1 = a.get(i,j);
      if ( (r1.real() != sidlArrayElem2(a._get_ior(), i, j).real ) ||
	   (r1.imag() != sidlArrayElem2(a._get_ior(), i, j).imaginary ) ||
	   (r1.real() != powTwo(i)) ||
	   (r1.imag() != powTwo(-j))) { 
	return false;
      }
    }
  }
  return true;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Dcomplex)
}

/**
 * Method:  check3Int[]
 */
bool
ArrayTest::ArrayOps_impl::check3Int_impl (
  /* in array<int,3> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check3Int)
  int i;
  int32_t ind[3], lower[3], upper[3], value;
  bool result = false;
  if (a.dimen() == 3) {
    for(i = 0; i < 3; ++i) {
      ind[i] = lower[i] = a.lower(i);
      upper[i] = a.upper(i);
    }
    result = true;
    if (hasElements(3, lower, upper)) {
      do {
	value = arrayValue(3, ind);
	switch(rand() % 2) {
	case 0:
	  if (a.get(ind[0], ind[1], ind[2]) != value)
	    result = false;
	  break;
	case 1:
	  if (a.get(ind) != value) 
	    result = false;
	  break;
	}
      } while (result && nextElem(3, ind, lower, upper));
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check3Int)
}

/**
 * Method:  check4Int[]
 */
bool
ArrayTest::ArrayOps_impl::check4Int_impl (
  /* in array<int,4> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check4Int)
  int i;
  int32_t ind[4], lower[4], upper[4], value;
  bool result = false;
  if (a.dimen() == 4) {
    for(i = 0; i < 4; ++i) {
      ind[i] = lower[i] = a.lower(i);
      upper[i] = a.upper(i);
    }
    result = true;
    if (hasElements(4, lower, upper)) {
      do {
	value = arrayValue(4, ind);
	switch(rand() % 2) {
	case 0:
	  if (a.get(ind[0], ind[1], ind[2], ind[3]) != value)
	    result = false;
	  break;
	case 1:
	  if (a.get(ind) != value) 
	    result = false;
	  break;
	}
      } while (result && nextElem(4, ind, lower, upper));
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check4Int)
}

/**
 * Method:  check5Int[]
 */
bool
ArrayTest::ArrayOps_impl::check5Int_impl (
  /* in array<int,5> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check5Int)
  int i;
  int32_t ind[5], lower[5], upper[5], value;
  bool result = false;
  if (a.dimen() == 5) {
    for(i = 0; i < 5; ++i) {
      ind[i] = lower[i] = a.lower(i);
      upper[i] = a.upper(i);
    }
    result = true;
    if (hasElements(5, lower, upper)) {
      do {
	value = arrayValue(5, ind);
	switch(rand() % 2) {
	case 0:
	  if (a.get(ind[0], ind[1], ind[2], ind[3], ind[4]) != value)
	    result = false;
	  break;
	case 1:
	  if (a.get(ind) != value) 
	    result = false;
	  break;
	}
      } while (result && nextElem(5, ind, lower, upper));
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check5Int)
}

/**
 * Method:  check6Int[]
 */
bool
ArrayTest::ArrayOps_impl::check6Int_impl (
  /* in array<int,6> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check6Int)
  int i;
  int32_t ind[6], lower[6], upper[6], value;
  bool result = false;
  if (a.dimen() == 6) {
    for(i = 0; i < 6; ++i) {
      ind[i] = lower[i] = a.lower(i);
      upper[i] = a.upper(i);
    }
    result = true;
    if (hasElements(6, lower, upper)) {
      do {
	value = arrayValue(6, ind);
	switch(rand() % 2) {
	case 0:
	  if (a.get(ind[0], ind[1], ind[2], ind[3], ind[4], ind[5]) != value)
	    result = false;
	  break;
	case 1:
	  if (a.get(ind) != value) 
	    result = false;
	  break;
	}
      } while (result && nextElem(6, ind, lower, upper));
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check6Int)
}

/**
 * Method:  check7Int[]
 */
bool
ArrayTest::ArrayOps_impl::check7Int_impl (
  /* in array<int,7> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check7Int)
  int i;
  int32_t ind[7], lower[7], upper[7], value;
  bool result = false;
  if (a.dimen() == 7) {
    for(i = 0; i < 7; ++i) {
      ind[i] = lower[i] = a.lower(i);
      upper[i] = a.upper(i);
    }
    result = true;
    if (hasElements(7, lower, upper)) {
      do {
	value = arrayValue(7, ind);
	switch(rand() % 2) {
	case 0:
	  if (a.get(ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], ind[6]) != value)
	    result = false;
	  break;
	case 1:
	  if (a.get(ind) != value) 
	    result = false;
	  break;
	}
      } while (result && nextElem(7, ind, lower, upper));
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check7Int)
}

/**
 * Method:  check2String[]
 */
bool
ArrayTest::ArrayOps_impl::check2String_impl (
  /* in array<string,2> */::sidl::array< ::std::string>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2String)
  if (a._is_nil()) return false;
  const char* const * testWord = s_TestWords;
  char* tmp = NULL;
  for( int32_t i=0; i< a.upper(0); ++i) { 
    for(int32_t j = 0; j < a.upper(1); ++j, testWord = nextWord(testWord)) {
      tmp = sidl_string__array_get2( a._get_ior(), i, j);
      if(strcmp(tmp, *testWord)) {
	free(tmp);
	return false;
      }
      free(tmp);
    }
  }
  return true;

  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2String)
}

/**
 * Method:  checkObject[]
 */
int32_t
ArrayTest::ArrayOps_impl::checkObject_impl (
  /* in array<ArrayTest.ArrayOps> */::sidl::array< ::ArrayTest::ArrayOps>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkObject)
  if ( a._is_nil() ||
       (a.dimen() != 1 ) ) { 
    return 0;
  }
  int32_t count=0;
  for( int32_t i=a.lower(0); i<= a.upper(0); ++i ) { 
    ArrayOps ops = a.get(i);
    if ( ops._is_nil() ) { 
      ;
    } else { 
      ++count;
    }
  }
  return count;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkObject)
}

/**
 * Method:  reverseBool[]
 */
bool
ArrayTest::ArrayOps_impl::reverseBool_impl (
  /* inout array<bool> */::sidl::array<bool>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseBool)
  if ( !a ||
       a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkBool(a);
  
  if (newArray) { // build a new array that is the reverse of the old one
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<bool> copy = UCXX ::sidl::array<bool>::createRow(1,&lower,&upper);
    for(int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i, a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1) >> 1;
    for ( int32_t i=0; i<len; ++i ) { 
      bool temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }      
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseBool)
}

/**
 * Method:  reverseChar[]
 */
bool
ArrayTest::ArrayOps_impl::reverseChar_impl (
  /* inout array<char> */::sidl::array<char>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseChar)
  if ( !a ||
       a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkChar(a);
  
  if (newArray) { // build a new array that is the reverse of the old one
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<char> copy = UCXX ::sidl::array<char>::createRow(1,&lower,&upper);
    for(int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i,a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1) >> 1;
    for ( int32_t i=0; i<len; ++i ) { 
      char temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }      
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseChar)
}

/**
 * Method:  reverseInt[]
 */
bool
ArrayTest::ArrayOps_impl::reverseInt_impl (
  /* inout array<int> */::sidl::array<int32_t>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseInt)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkInt(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<int32_t> copy = UCXX ::sidl::array<int32_t>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i,a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      int32_t temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseInt)
}

/**
 * Method:  reverseLong[]
 */
bool
ArrayTest::ArrayOps_impl::reverseLong_impl (
  /* inout array<long> */::sidl::array<int64_t>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseLong)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkLong(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<int64_t> copy = UCXX ::sidl::array<int64_t>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i,a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      int64_t temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseLong)
}

/**
 * Method:  reverseString[]
 */
bool
ArrayTest::ArrayOps_impl::reverseString_impl (
  /* inout array<string> */::sidl::array< ::std::string>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseString)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkString(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<std::string> copy = UCXX ::sidl::array<std::string>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i, a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      std::string temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseString)
}

/**
 * Method:  reverseDouble[]
 */
bool
ArrayTest::ArrayOps_impl::reverseDouble_impl (
  /* inout array<double> */::sidl::array<double>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDouble)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkDouble(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<double> copy = UCXX ::sidl::array<double>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i,a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      double temp = a.get(lower+i);
      a.set(lower+i,a.get(upper-i));
      a.set(upper-i,temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDouble)
}

/**
 * Method:  reverseFloat[]
 */
bool
ArrayTest::ArrayOps_impl::reverseFloat_impl (
  /* inout array<float> */::sidl::array<float>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFloat)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkFloat(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array<float> copy = UCXX ::sidl::array<float>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i,a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      float temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFloat)
}

/**
 * Method:  reverseFcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::reverseFcomplex_impl (
  /* inout array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFcomplex)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkFcomplex(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array< UCXX ::sidl::fcomplex> copy = UCXX ::sidl::array< UCXX ::sidl::fcomplex>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i, a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      std::complex<float> temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFcomplex)
}

/**
 * Method:  reverseDcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::reverseDcomplex_impl (
  /* inout array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a,
  /* in */bool newArray ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDcomplex)
  if ( !a || a.dimen() != 1 ) { 
    return false;
  }
  const int32_t upper = a.upper(0);
  const int32_t lower = a.lower(0);
  bool result = ArrayOps::checkDcomplex(a);
  if ( newArray ) { // create a separate copy
    const int32_t len = upper - lower + 1;
    UCXX ::sidl::array< UCXX ::sidl::dcomplex> copy = UCXX ::sidl::array< UCXX ::sidl::dcomplex>::createRow(1,&lower,&upper);
    for( int32_t i=0; i<len; ++i ) { 
      copy.set(upper-i, a.get(lower+i));
    }
    a.deleteRef();
    a=copy;
  } else { // reverse in place
    const int32_t len = (upper - lower + 1 ) >> 1;
    for( int32_t i=0; i<len; ++i ) { 
      std::complex<double> temp = a.get(lower+i);
      a.set(lower+i, a.get(upper-i));
      a.set(upper-i, temp);
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDcomplex)
}

/**
 * Method:  createBool[]
 */
::sidl::array<bool>
ArrayTest::ArrayOps_impl::createBool_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createBool)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array<bool> result = UCXX ::sidl::array<bool>::createRow(1,lower,upper);
  
  for( int32_t i=0; i<len; ++i ) { 
    switch( rand() % 3) { 
    case 0:
      sidlArrayElem1(result._get_ior(),i) = ((i & 0x1) ? FALSE : TRUE);
      break;
    case 1:
      sidl_bool__array_set1(result._get_ior(), i, ((i & 0x1) ? FALSE : TRUE));
      break;
    case 2:
      result.set(i, ((i & 0x1) ? FALSE : TRUE));
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createBool)
}

/**
 * Method:  createChar[]
 */
::sidl::array<char>
ArrayTest::ArrayOps_impl::createChar_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createChar)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array<char> result = UCXX ::sidl::array<char>::createRow(1,lower,upper);
  
  const char* testStr = s_TestText;
  for( int32_t i=0; i<len; ++i, testStr=nextChar(testStr) ) { 
    switch( rand() % 3) { 
    case 0:
      sidlArrayElem1(result._get_ior(),i) = *testStr;
      break;
    case 1:
      sidl_char__array_set1(result._get_ior(), i, *testStr);
      break;
    case 2:
      result.set(i, *testStr);
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createChar)
}

/**
 * Method:  createInt[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::createInt_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createInt)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  int32_t prime = nextPrime(0);
  UCXX ::sidl::array<int32_t> result = UCXX ::sidl::array<int32_t>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i, prime = nextPrime(prime) ) { 
    switch( rand()%3 ) { 
    case 0:
      sidlArrayElem1(result._get_ior(), i ) = prime;
      break;
    case 1:
      sidl_int__array_set1(result._get_ior(), i, prime );
      break;
    case 2:
      result.set(i, prime);
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createInt)
}

/**
 * Method:  createLong[]
 */
::sidl::array<int64_t>
ArrayTest::ArrayOps_impl::createLong_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createLong)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  int64_t prime = nextPrime(0);
  UCXX ::sidl::array<int64_t> result = UCXX ::sidl::array<int64_t>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i, prime = nextPrime(prime) ) { 
    switch( rand()%3 ) { 
    case 0:
      sidlArrayElem1(result._get_ior(), i ) = prime;
      break;
    case 1:
      sidl_long__array_set1(result._get_ior(), i, prime );
      break;
    case 2:
      result.set(i, prime);
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createLong)
}

/**
 * Method:  createString[]
 */
::sidl::array< ::std::string>
ArrayTest::ArrayOps_impl::createString_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createString)
  if (len < 0) return NULL;
  const char* const * testWord = s_TestWords;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array<std::string> result = UCXX ::sidl::array<std::string>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i, testWord = nextWord(testWord)) { 
    switch(rand()%2) { 
    case 0:
      sidl_string__array_set1( result._get_ior(), i, *testWord );
      break;
    case 1:
      result.set(i, *testWord);
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createString)
}

/**
 * Method:  createDouble[]
 */
::sidl::array<double>
ArrayTest::ArrayOps_impl::createDouble_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDouble)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array<double> result = UCXX ::sidl::array<double>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i ) { 
    switch(rand()%3) { 
    case 0:
      sidlArrayElem1(result._get_ior(), i) = powTwo(-i);
      break;
    case 1:
      sidl_double__array_set1(result._get_ior(), i, powTwo(-i) );
      break;
    case 2:
      result.set(i, powTwo(-i));
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDouble)
}

/**
 * Method:  createFloat[]
 */
::sidl::array<float>
ArrayTest::ArrayOps_impl::createFloat_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFloat)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array<float> result = UCXX ::sidl::array<float>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i ) { 
    switch(rand()%3) { 
    case 0:
      sidlArrayElem1(result._get_ior(), i) = powTwo(-i);
      break;
    case 1:
      sidl_float__array_set1(result._get_ior(), i, powTwo(-i) );
      break;
    case 2:
      result.set(i, powTwo(-i) );
      break;
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFloat)
}

/**
 * Method:  createFcomplex[]
 */
::sidl::array< ::sidl::fcomplex>
ArrayTest::ArrayOps_impl::createFcomplex_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFcomplex)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array< UCXX ::sidl::fcomplex> result = UCXX ::sidl::array< UCXX ::sidl::fcomplex>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i ) { 
    switch(rand() % 3 ) { 
    case 0:
      sidlArrayElem1(result._get_ior(), i).real = fpowTwo(i);
      sidlArrayElem1(result._get_ior(), i).imaginary = fpowTwo(-i);
      break;
    case 1:
      { 
	struct sidl_fcomplex tmp;
        tmp.real = fpowTwo(i);
        tmp.imaginary = fpowTwo(-i);
	sidl_fcomplex__array_set1(result._get_ior(), i, tmp);
      }
      break;
    case 2:
      {
	std::complex<float> f( fpowTwo(i), fpowTwo(-i) );
	result.set(i,f);
      }
      break;
      
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFcomplex)
}

/**
 * Method:  createDcomplex[]
 */
::sidl::array< ::sidl::dcomplex>
ArrayTest::ArrayOps_impl::createDcomplex_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDcomplex)
  if (len < 0) return NULL;
  int32_t lower[1] = {0};
  int32_t upper[1] = {len-1};
  UCXX ::sidl::array< UCXX ::sidl::dcomplex> result= UCXX ::sidl::array< UCXX ::sidl::dcomplex>::createRow(1,lower,upper);
  for( int32_t i=0; i<len; ++i ) { 
    switch(rand() % 3 ) { 
    case 0:
      sidlArrayElem1(result._get_ior(), i).real = powTwo(i);
      sidlArrayElem1(result._get_ior(), i).imaginary = powTwo(-i);
      break;
    case 1:
      { 
	struct sidl_dcomplex tmp;
        tmp.real = powTwo(i);
        tmp.imaginary = powTwo(-i);
	sidl_dcomplex__array_set1(result._get_ior(), i, tmp);
      }
      break;
    case 2:
      {
	std::complex<double> d( powTwo(i), powTwo(-i) );
	result.set(i,d);
      }
      break;
      
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDcomplex)
}

/**
 * Method:  createObject[]
 */
::sidl::array< ::ArrayTest::ArrayOps>
ArrayTest::ArrayOps_impl::createObject_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createObject)
  UCXX ::sidl::array<ArrayOps> a;
  if ( len >= 0 ) { 
    int32_t lower = 0;
    int32_t upper = len-1;
    a = UCXX ::sidl::array<ArrayOps>::createRow(1,&lower,&upper);
    while( len--) { 
      ArrayOps obj = ArrayOps::_create();
      a.set(len,obj);
    }
  }
  return a;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createObject)
}

/**
 * Method:  create2Int[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::create2Int_impl (
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Int)
  if ((d1 < 0) || (d2 < 0)) return NULL;
  int32_t lower[2] = {0, 0};
  int32_t upper[2] = {d1-1, d2-1};
  UCXX ::sidl::array<int32_t> result = UCXX ::sidl::array<int32_t>::createRow(2,lower,upper);
  for( int32_t i=0; i<d1; ++i ) {
    for( int32_t j=0;  j<d2; ++j ) {
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result._get_ior(),i,j) = (int)powTwo(abs(i-j));
        break;
      case 1:
        sidl_int__array_set2(result._get_ior(), i, j, (int)powTwo(abs(i-j)));
        break;
      case 2:
        result.set(i, j, (int)powTwo(abs(i-j)));
        break;
      }
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Int)
}

/**
 * Method:  create2Double[]
 */
::sidl::array<double>
ArrayTest::ArrayOps_impl::create2Double_impl (
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Double)
  if ((d1 < 0) || (d2 < 0)) return NULL;
  int32_t lower[2] = {0, 0};
  int32_t upper[2] = {d1-1, d2-1};
  UCXX ::sidl::array<double> result = UCXX ::sidl::array<double>::createRow(2,lower,upper);
  for( int32_t i=0; i<d1; ++i ) {
    for( int32_t j=0;  j<d2; ++j ) {
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result._get_ior(),i,j) = powTwo(i-j);
        break;
      case 1:
        sidl_double__array_set2(result._get_ior(), i, j, powTwo(i-j));
        break;
      case 2:
        result.set(i, j, powTwo(i-j));
        break;
      }
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Double)
}

/**
 * Method:  create2Float[]
 */
::sidl::array<float>
ArrayTest::ArrayOps_impl::create2Float_impl (
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Float)
  if ((d1 < 0) || (d2 < 0)) return NULL;
  int32_t lower[2] = {0, 0};
  int32_t upper[2] = { d1-1, d2-1};
  UCXX ::sidl::array<float> result = UCXX ::sidl::array<float>::createRow(2, lower, upper);
  for( int32_t i=0; i<d1; ++i) {
    for( int32_t j=0; j<d2; ++j) {
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result._get_ior(),i,j) = powTwo(i-j);
        break;
      case 1:
        sidl_float__array_set2(result._get_ior(), i, j, powTwo(i-j));
        break;
      case 2:
        result.set(i, j, powTwo(i-j));
        break;
      }
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Float)
}

/**
 * Method:  create2Dcomplex[]
 */
::sidl::array< ::sidl::dcomplex>
ArrayTest::ArrayOps_impl::create2Dcomplex_impl (
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Dcomplex)
  if ((d1 < 0) || (d2 < 0)) return NULL;
  int32_t lower[2] = {0, 0};
  int32_t upper[2] = {d1-1, d2-1};
  UCXX ::sidl::array< UCXX ::sidl::dcomplex> result = UCXX ::sidl::array< UCXX ::sidl::dcomplex>::createRow(2, lower, upper);
  for(int32_t i=0; i<d1; ++i) {
    for(int32_t j=0; j<d2; ++j) {
      std::complex<double> tmp( powTwo(i), powTwo(-j) );
      struct sidl_dcomplex tmp2;
      tmp2.real = tmp.real();
      tmp2.imaginary = tmp.imag();
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result._get_ior(),i,j) = tmp2;
        break;
      case 1:
        sidl_dcomplex__array_set2(result._get_ior(), i, j, tmp2);
        break;
      case 2:
        result.set(i, j, tmp);
        break;
      }
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Dcomplex)
}

/**
 * Method:  create2Fcomplex[]
 */
::sidl::array< ::sidl::fcomplex>
ArrayTest::ArrayOps_impl::create2Fcomplex_impl (
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Fcomplex)
  if ((d1 < 0) || (d2 < 0)) return NULL;
  int32_t lower[2] = {0,0};
  int32_t upper[2] = { d1-1, d2-1};
  UCXX ::sidl::array< UCXX ::sidl::fcomplex> result = UCXX ::sidl::array< UCXX ::sidl::fcomplex>::createRow(2, lower, upper);
  for(int32_t i=0; i<d1; ++i) {
    for(int32_t j=0; j<d2; ++j) {
      std::complex<float> tmp2( fpowTwo(i), fpowTwo(-j) );
      struct sidl_fcomplex tmp;
      tmp.real = fpowTwo(i);
      tmp.imaginary = fpowTwo(-j);
      switch(rand() % 3) {
      case 0:
        sidlArrayElem2(result._get_ior(),i,j) = tmp;
        break;
      case 1:
        sidl_fcomplex__array_set2(result._get_ior(), i, j, tmp);
        break;
      case 2:
        result.set(i, j, tmp2);
        break;
      }
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Fcomplex)
}

/**
 * Method:  create2String[]
 */
::sidl::array< ::std::string>
ArrayTest::ArrayOps_impl::create2String_impl (
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2String)
  if (d1 < 0 || d2 < 0) return NULL;
  const char* const * testWord = s_TestWords;
  int32_t lower[2] = {0,0};
  int32_t upper[2] = {d1-1,d2-1};
  UCXX ::sidl::array<std::string> result = UCXX ::sidl::array<std::string>::createRow(2,lower,upper);
  for( int32_t i=0; i<d1-1; ++i) { 
    for(int32_t j = 0; j < d2-1; ++j, testWord = nextWord(testWord)) {
      sidl_string__array_set2( result._get_ior(), i, j, *testWord );
      
    }
  }
  return result;

  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2String)
}

/**
 * Method:  create3Int[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::create3Int_impl () 

{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create3Int)
  return makeIntTestMatrix(3);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create3Int)
}

/**
 * Method:  create4Int[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::create4Int_impl () 

{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create4Int)
  return makeIntTestMatrix(4);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create4Int)
}

/**
 * Method:  create5Int[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::create5Int_impl () 

{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create5Int)
  return makeIntTestMatrix(5);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create5Int)
}

/**
 * Method:  create6Int[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::create6Int_impl () 

{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create6Int)
  return makeIntTestMatrix(6);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create6Int)
}

/**
 * Method:  create7Int[]
 */
::sidl::array<int32_t>
ArrayTest::ArrayOps_impl::create7Int_impl () 

{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create7Int)
  return makeIntTestMatrix(7);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create7Int)
}

/**
 * Method:  makeBool[]
 */
void
ArrayTest::ArrayOps_impl::makeBool_impl (
  /* in */int32_t len,
  /* out array<bool> */::sidl::array<bool>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeBool)
  a = ArrayOps::createBool(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeBool)
}

/**
 * Method:  makeChar[]
 */
void
ArrayTest::ArrayOps_impl::makeChar_impl (
  /* in */int32_t len,
  /* out array<char> */::sidl::array<char>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeChar)
  a = ArrayOps::createChar(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeChar)
}

/**
 * Method:  makeInt[]
 */
void
ArrayTest::ArrayOps_impl::makeInt_impl (
  /* in */int32_t len,
  /* out array<int> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInt)
  a = ArrayOps::createInt(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInt)
}

/**
 * Method:  makeLong[]
 */
void
ArrayTest::ArrayOps_impl::makeLong_impl (
  /* in */int32_t len,
  /* out array<long> */::sidl::array<int64_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeLong)
  a = ArrayOps::createLong(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeLong)
}

/**
 * Method:  makeString[]
 */
void
ArrayTest::ArrayOps_impl::makeString_impl (
  /* in */int32_t len,
  /* out array<string> */::sidl::array< ::std::string>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeString)
  a = ArrayOps::createString(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeString)
}

/**
 * Method:  makeDouble[]
 */
void
ArrayTest::ArrayOps_impl::makeDouble_impl (
  /* in */int32_t len,
  /* out array<double> */::sidl::array<double>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDouble)
  a = ArrayOps::createDouble(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDouble)
}

/**
 * Method:  makeFloat[]
 */
void
ArrayTest::ArrayOps_impl::makeFloat_impl (
  /* in */int32_t len,
  /* out array<float> */::sidl::array<float>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFloat)
  a = ArrayOps::createFloat(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFloat)
}

/**
 * Method:  makeFcomplex[]
 */
void
ArrayTest::ArrayOps_impl::makeFcomplex_impl (
  /* in */int32_t len,
  /* out array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFcomplex)
  a = ArrayOps::createFcomplex(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFcomplex)
}

/**
 * Method:  makeDcomplex[]
 */
void
ArrayTest::ArrayOps_impl::makeDcomplex_impl (
  /* in */int32_t len,
  /* out array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDcomplex)
  a = ArrayOps::createDcomplex(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDcomplex)
}

/**
 * Method:  makeInOutBool[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutBool_impl (
  /* inout array<bool> */::sidl::array<bool>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutBool)
  a = ArrayOps::createBool(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutBool)
}

/**
 * Method:  makeInOutChar[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutChar_impl (
  /* inout array<char> */::sidl::array<char>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutChar)
  a = ArrayOps::createChar(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutChar)
}

/**
 * Method:  makeInOutInt[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutInt_impl (
  /* inout array<int> */::sidl::array<int32_t>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutInt)
  a = ArrayOps::createInt(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutInt)
}

/**
 * Method:  makeInOutLong[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutLong_impl (
  /* inout array<long> */::sidl::array<int64_t>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutLong)
  a = ArrayOps::createLong(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutLong)
}

/**
 * Method:  makeInOutString[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutString_impl (
  /* inout array<string> */::sidl::array< ::std::string>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutString)
  a = ArrayOps::createString(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutString)
}

/**
 * Method:  makeInOutDouble[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutDouble_impl (
  /* inout array<double> */::sidl::array<double>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDouble)
  a = ArrayOps::createDouble(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDouble)
}

/**
 * Method:  makeInOutFloat[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutFloat_impl (
  /* inout array<float> */::sidl::array<float>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFloat)
  a = ArrayOps::createFloat(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFloat)
}

/**
 * Method:  makeInOutDcomplex[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutDcomplex_impl (
  /* inout array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDcomplex)
  a = ArrayOps::createDcomplex(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDcomplex)
}

/**
 * Method:  makeInOutFcomplex[]
 */
void
ArrayTest::ArrayOps_impl::makeInOutFcomplex_impl (
  /* inout array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a,
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFcomplex)
  a = ArrayOps::createFcomplex(len);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFcomplex)
}

/**
 * Method:  makeInOut2Int[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut2Int_impl (
  /* inout array<int,2> */::sidl::array<int32_t>& a,
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Int)
  a = ArrayOps::create2Int(d1, d2);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Int)
}

/**
 * Method:  makeInOut2Double[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut2Double_impl (
  /* inout array<double,2> */::sidl::array<double>& a,
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Double)
  a = ArrayOps::create2Double(d1, d2);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Double)
}

/**
 * Method:  makeInOut2Float[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut2Float_impl (
  /* inout array<float,2> */::sidl::array<float>& a,
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Float)
  a = ArrayOps::create2Float(d1, d2);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Float)
}

/**
 * Method:  makeInOut2Dcomplex[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut2Dcomplex_impl (
  /* inout array<dcomplex,2> */::sidl::array< ::sidl::dcomplex>& a,
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Dcomplex)
  a = ArrayOps::create2Dcomplex(d1, d2);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Dcomplex)
}

/**
 * Method:  makeInOut2Fcomplex[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut2Fcomplex_impl (
  /* inout array<fcomplex,2> */::sidl::array< ::sidl::fcomplex>& a,
  /* in */int32_t d1,
  /* in */int32_t d2 ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Fcomplex)
  a = ArrayOps::create2Fcomplex(d1, d2);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Fcomplex)
}

/**
 * Method:  makeInOut3Int[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut3Int_impl (
  /* inout array<int,3> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut3Int)
  a = ArrayOps::create3Int();
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut3Int)
}

/**
 * Method:  makeInOut4Int[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut4Int_impl (
  /* inout array<int,4> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut4Int)
  a = ArrayOps::create4Int();
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut4Int)
}

/**
 * Method:  makeInOut5Int[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut5Int_impl (
  /* inout array<int,5> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut5Int)
  a = ArrayOps::create5Int();
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut5Int)
}

/**
 * Method:  makeInOut6Int[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut6Int_impl (
  /* inout array<int,6> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut6Int)
  a = ArrayOps::create6Int();
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut6Int)
}

/**
 * Method:  makeInOut7Int[]
 */
void
ArrayTest::ArrayOps_impl::makeInOut7Int_impl (
  /* inout array<int,7> */::sidl::array<int32_t>& a ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut7Int)
  a = ArrayOps::create7Int();
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut7Int)
}

/**
 * Return as out parameters the type and dimension of the 
 * array passed in. If a is NULL, dimen == type == 0 on exit.
 * The contents of the array have the default values for a 
 * newly created array.
 */
void
ArrayTest::ArrayOps_impl::checkGeneric_impl (
  /* in array<> */::sidl::basearray& a,
  /* out */int32_t& dmn,
  /* out */int32_t& tp ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkGeneric)
  if (a) {
    dmn = a.dimen();
    tp = a.arrayType();
  }
  else {
    dmn = 0;
    tp = 0;
  }
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkGeneric)
}

/**
 * Create an array of the type and dimension specified and
 * return it. A type of 0 causes a NULL array to be returned.
 */
::sidl::basearray
ArrayTest::ArrayOps_impl::createGeneric_impl (
  /* in */int32_t dmn,
  /* in */int32_t tp ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createGeneric)
  static const int32_t lower[] = {0, 0, 0, 0, 0, 0, 0};
  static const int32_t upper[] = {2, 2, 2, 2, 2, 2, 2};
  return createArrayByType(tp, dmn, lower, upper);
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createGeneric)
}

/**
 * Testing passing generic arrays using every possible mode.
 * The returned array is a copy of inArg, so if inArg != NULL,
 * the return value should != NULL. outArg is also a copy of
 * inArg.
 * If inOutArg is NULL on entry, a 2-D array of int that should
 * pass check2Int is returned.
 * If inOutArg is not NULL on entry and its dimension is even,
 * it is returned unchanged; otherwise, NULL is returned.
 */
::sidl::basearray
ArrayTest::ArrayOps_impl::passGeneric_impl (
  /* in array<> */::sidl::basearray& inArg,
  /* inout array<> */::sidl::basearray& inOutArg,
  /* out array<> */::sidl::basearray& outArg ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.passGeneric)
  int32_t lower[7], upper[7], i;
  UCXX ::sidl::basearray result = NULL;
  if (inArg) {
    const int32_t dimen = inArg.dimen();
    for(i = 0; i < dimen; ++i) {
      lower[i] = inArg.lower(i);
      upper[i] = inArg.upper(i);
    }
    result = createArrayByType(inArg.arrayType(), dimen, lower, upper);
    outArg = createArrayByType(inArg.arrayType(), dimen, lower, upper);
    copyArrayByType(inArg, result);
    copyArrayByType(inArg, outArg);
  }
  if (inOutArg) {
    if (inOutArg.dimen() & 1) {
      inOutArg = NULL;
    }
  }
  else {
    inOutArg = UCXX ::ArrayTest::ArrayOps::create2Int(3, 3);
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.passGeneric)
}

/**
 * Method:  initRarray1Int[]
 */
void
ArrayTest::ArrayOps_impl::initRarray1Int_impl (
  /* inout rarray[n] */int32_t* a,
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Int)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Int)
}

/**
 * Method:  initRarray3Int[]
 */
void
ArrayTest::ArrayOps_impl::initRarray3Int_impl (
  /* inout rarray[n,m,o] */int32_t* a,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray3Int)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray3Int)
}

/**
 * Method:  initRarray7Int[]
 */
void
ArrayTest::ArrayOps_impl::initRarray7Int_impl (
  /* inout rarray[n,m,o,p,q,r,s] */int32_t* a,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o,
  /* in */int32_t p,
  /* in */int32_t q,
  /* in */int32_t r,
  /* in */int32_t s ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray7Int)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray7Int)
}

/**
 * Method:  initRarray1Double[]
 */
void
ArrayTest::ArrayOps_impl::initRarray1Double_impl (
  /* inout rarray[n] */double* a,
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Double)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Double)
}

/**
 * Method:  initRarray1Dcomplex[]
 */
void
ArrayTest::ArrayOps_impl::initRarray1Dcomplex_impl (
  /* inout rarray[n] */struct sidl_dcomplex* a,
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Dcomplex)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Dcomplex)
}

/**
 * Method:  checkRarray1Int[]
 */
bool
ArrayTest::ArrayOps_impl::checkRarray1Int_impl (
  /* in rarray[n] */int32_t* a,
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Int)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Int)
}

/**
 * Method:  checkRarray3Int[]
 */
bool
ArrayTest::ArrayOps_impl::checkRarray3Int_impl (
  /* in rarray[n,m,o] */int32_t* a,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray3Int)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray3Int)
}

/**
 * Method:  checkRarray7Int[]
 */
bool
ArrayTest::ArrayOps_impl::checkRarray7Int_impl (
  /* in rarray[n,m,o,p,q,r,s] */int32_t* a,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o,
  /* in */int32_t p,
  /* in */int32_t q,
  /* in */int32_t r,
  /* in */int32_t s ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray7Int)
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
    result = TRUE;
    int32_t ind[7] = {0, 0, 0, 0, 0, 0, 0};
    int32_t value = 0;
    do {
      value = arrayValue(7, ind);
      if (RarrayElem7(a, ind[0], ind[1], ind[2], ind[3], ind[4], ind[5], ind[6],
		      n, m, o, p, q, r) != value) {
	result = FALSE;
      }
    } while (result && nextElem(7, ind, lower, upper));
  }
  return result;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray7Int)
}

/**
 * Method:  checkRarray1Double[]
 */
bool
ArrayTest::ArrayOps_impl::checkRarray1Double_impl (
  /* in rarray[n] */double* a,
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Double)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Double)
}

/**
 * Method:  checkRarray1Dcomplex[]
 */
bool
ArrayTest::ArrayOps_impl::checkRarray1Dcomplex_impl (
  /* in rarray[n] */struct sidl_dcomplex* a,
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Dcomplex)
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
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Dcomplex)
}

/**
 * Method:  matrixMultiply[]
 */
void
ArrayTest::ArrayOps_impl::matrixMultiply_impl (
  /* in rarray[n,m] */int32_t* a,
  /* in rarray[m,o] */int32_t* b,
  /* inout rarray[n,o] */int32_t* res,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.matrixMultiply)
  if(a && b && res) {
    locMatrixMultiply(a,b,res,n,m,o);
  }
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.matrixMultiply)
}

/**
 * Method:  checkMatrixMultiply[]
 */
bool
ArrayTest::ArrayOps_impl::checkMatrixMultiply_impl (
  /* in rarray[n,m] */int32_t* a,
  /* in rarray[m,o] */int32_t* b,
  /* in rarray[n,o] */int32_t* res,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkMatrixMultiply)
  int32_t *test;
  int32_t i,j;
  if(a && b && res) {
    test = (int32_t *)malloc(sizeof(int32_t)*n*o);
    locMatrixMultiply(a,b,test,n,m,o);
    for(i=0;i<n;++i) {
      for(j=0;j<o;++j) {
	if(RarrayElem2(test,i,j,n) != RarrayElem2(res,i,j,n)) {
	  free((void*)test);
	  return FALSE;
	}
      }
    }
    free((void*)test);
    return TRUE;
  }
  return FALSE;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkMatrixMultiply)
}


// user defined non-static methods:
/**
 * Method:  mm[]
 */
void
ArrayTest::ArrayOps_impl::mm_impl (
  /* in rarray[n,m] */int32_t* a,
  /* in rarray[m,o] */int32_t* b,
  /* inout rarray[n,o] */int32_t* res,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.mm)
  if(a && b && res) {
    locMatrixMultiply(a,b,res,n,m,o);
  }
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.mm)
}

/**
 * Method:  checkmm[]
 */
bool
ArrayTest::ArrayOps_impl::checkmm_impl (
  /* in rarray[n,m] */int32_t* a,
  /* in rarray[m,o] */int32_t* b,
  /* in rarray[n,o] */int32_t* res,
  /* in */int32_t n,
  /* in */int32_t m,
  /* in */int32_t o ) 
{
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkmm)
  int32_t *test;
  int32_t i,j;
  if(a && b && res) {
    test = (int32_t *)malloc(sizeof(int32_t)*n*o);
    locMatrixMultiply(a,b,test,n,m,o);
    for(i=0;i<n;++i) {
      for(j=0;j<o;++j) {
	if(RarrayElem2(test,i,j,n) != RarrayElem2(res,i,j,n)) {
	  free((void*)test);
	  return FALSE;
	}
      }
    }
    free((void*)test);
    return TRUE;
  }
  return FALSE;
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkmm)
}


// DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._misc)

