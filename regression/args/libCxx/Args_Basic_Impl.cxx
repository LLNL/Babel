// 
// File:          Args_Basic_Impl.cxx
// Symbol:        Args.Basic-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Args.Basic
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Args_Basic_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(Args.Basic._includes)
// Insert-Code-Here {Args.Basic._includes} (additional includes or code)
// DO-NOT-DELETE splicer.end(Args.Basic._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Args::Basic_impl::Basic_impl() : StubBase(reinterpret_cast< void*>(
  ::Args::Basic::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Args.Basic._ctor2)
  // Insert-Code-Here {Args.Basic._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Args.Basic._ctor2)
}

// user defined constructor
void Args::Basic_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Args.Basic._ctor)
  // Insert-Code-Here {Args.Basic._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(Args.Basic._ctor)
}

// user defined destructor
void Args::Basic_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Args.Basic._dtor)
  // Insert-Code-Here {Args.Basic._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(Args.Basic._dtor)
}

// static class initializer
void Args::Basic_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Args.Basic._load)
  // Insert-Code-Here {Args.Basic._load} (class initialization)
  // DO-NOT-DELETE splicer.end(Args.Basic._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  returnbackbool[]
 */
bool
Args::Basic_impl::returnbackbool_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool)
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool)
}

/**
 * Method:  passinbool[]
 */
bool
Args::Basic_impl::passinbool_impl (
  /* in */bool b ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinbool)
  return b;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinbool)
}

/**
 * Method:  passoutbool[]
 */
bool
Args::Basic_impl::passoutbool_impl (
  /* out */bool& b ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool)
  b = true;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutbool)
}

/**
 * Method:  passinoutbool[]
 */
bool
Args::Basic_impl::passinoutbool_impl (
  /* inout */bool& b ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool)
  b = !b;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool)
}

/**
 * Method:  passeverywherebool[]
 */
bool
Args::Basic_impl::passeverywherebool_impl (
  /* in */bool b1,
  /* out */bool& b2,
  /* inout */bool& b3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool)
  b2 = true;
  b3 = !b3;
  return b1;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool)
}

/**
 * Method:  returnbackchar[]
 */
char
Args::Basic_impl::returnbackchar_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar)
  return '3';
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar)
}

/**
 * Method:  passinchar[]
 */
bool
Args::Basic_impl::passinchar_impl (
  /* in */char c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinchar)
    return ( c == '3');
  // DO-NOT-DELETE splicer.end(Args.Basic.passinchar)
}

/**
 * Method:  passoutchar[]
 */
bool
Args::Basic_impl::passoutchar_impl (
  /* out */char& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar)
  c = '3';
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutchar)
}

/**
 * Method:  passinoutchar[]
 */
bool
Args::Basic_impl::passinoutchar_impl (
  /* inout */char& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar)
  if ( c >= 'a' && c <= 'z' ) { 
    c += 'A' - 'a';
  } else if ( c >= 'A' && c <= 'Z' ) { 
    c += 'a' - 'A';
  }
  return true;  
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar)
}

/**
 * Method:  passeverywherechar[]
 */
char
Args::Basic_impl::passeverywherechar_impl (
  /* in */char c1,
  /* out */char& c2,
  /* inout */char& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar)
  c2 = '3';
  if ( c3 >= 'a' && c3 <= 'z' ) { 
    c3 += 'A' - 'a';
  } else if ( c3 >= 'A' && c3 <= 'Z' ) { 
    c3 += 'a' - 'A';
  }
  return ( c1 == '3') ? '3' : '\0' ;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar)
}

/**
 * Method:  returnbackint[]
 */
int32_t
Args::Basic_impl::returnbackint_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint)
  return 3;
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackint)
}

/**
 * Method:  passinint[]
 */
bool
Args::Basic_impl::passinint_impl (
  /* in */int32_t i ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinint)
  return ( i == 3 );
  // DO-NOT-DELETE splicer.end(Args.Basic.passinint)
}

/**
 * Method:  passoutint[]
 */
bool
Args::Basic_impl::passoutint_impl (
  /* out */int32_t& i ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutint)
  i = 3;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutint)
}

/**
 * Method:  passinoutint[]
 */
bool
Args::Basic_impl::passinoutint_impl (
  /* inout */int32_t& i ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint)
  i = -i;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutint)
}

/**
 * Method:  passeverywhereint[]
 */
int32_t
Args::Basic_impl::passeverywhereint_impl (
  /* in */int32_t i1,
  /* out */int32_t& i2,
  /* inout */int32_t& i3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint)
  i2 = 3;
  i3 = -i3;
  return ( i1 == 3 ) ? 3 : 0 ;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint)
}

/**
 * Method:  returnbacklong[]
 */
int64_t
Args::Basic_impl::returnbacklong_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong)
  return 3L;
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong)
}

/**
 * Method:  passinlong[]
 */
bool
Args::Basic_impl::passinlong_impl (
  /* in */int64_t l ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinlong)
  return ( l == 3L );
  // DO-NOT-DELETE splicer.end(Args.Basic.passinlong)
}

/**
 * Method:  passoutlong[]
 */
bool
Args::Basic_impl::passoutlong_impl (
  /* out */int64_t& l ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong)
  l = 3L;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutlong)
}

/**
 * Method:  passinoutlong[]
 */
bool
Args::Basic_impl::passinoutlong_impl (
  /* inout */int64_t& l ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong)
  l = -l;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong)
}

/**
 * Method:  passeverywherelong[]
 */
int64_t
Args::Basic_impl::passeverywherelong_impl (
  /* in */int64_t l1,
  /* out */int64_t& l2,
  /* inout */int64_t& l3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong)
  l2 = 3L;
  l3 = -l3;
  return ( l1 == 3L ) ? 3L : 0L;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong)
}

/**
 * Method:  returnbackfloat[]
 */
float
Args::Basic_impl::returnbackfloat_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat)
  return 3.1F;
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat)
}

/**
 * Method:  passinfloat[]
 */
bool
Args::Basic_impl::passinfloat_impl (
  /* in */float f ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat)
   return ( f == 3.1F );
  // DO-NOT-DELETE splicer.end(Args.Basic.passinfloat)
}

/**
 * Method:  passoutfloat[]
 */
bool
Args::Basic_impl::passoutfloat_impl (
  /* out */float& f ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat)
  f = 3.1F;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat)
}

/**
 * Method:  passinoutfloat[]
 */
bool
Args::Basic_impl::passinoutfloat_impl (
  /* inout */float& f ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat)
  f = -f;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat)
}

/**
 * Method:  passeverywherefloat[]
 */
float
Args::Basic_impl::passeverywherefloat_impl (
  /* in */float f1,
  /* out */float& f2,
  /* inout */float& f3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat)
  f2 = 3.1F;
  f3 = -f3;
  return ( f1 == 3.1F ) ? 3.1F : 0.0;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat)
}

/**
 * Method:  returnbackdouble[]
 */
double
Args::Basic_impl::returnbackdouble_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble)
  return 3.14;
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble)
}

/**
 * Method:  passindouble[]
 */
bool
Args::Basic_impl::passindouble_impl (
  /* in */double d ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passindouble)
  return ( d == 3.14 );
  // DO-NOT-DELETE splicer.end(Args.Basic.passindouble)
}

/**
 * Method:  passoutdouble[]
 */
bool
Args::Basic_impl::passoutdouble_impl (
  /* out */double& d ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble)
  d = 3.14;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble)
}

/**
 * Method:  passinoutdouble[]
 */
bool
Args::Basic_impl::passinoutdouble_impl (
  /* inout */double& d ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble)
  d = -d;
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble)
}

/**
 * Method:  passeverywheredouble[]
 */
double
Args::Basic_impl::passeverywheredouble_impl (
  /* in */double d1,
  /* out */double& d2,
  /* inout */double& d3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble)
  d2 = 3.14;
  d3 = -d3;
  return ( d1 == 3.14 ) ? 3.14 : 0.0 ;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble)
}

/**
 * Method:  returnbackfcomplex[]
 */
::std::complex<float>
Args::Basic_impl::returnbackfcomplex_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex)
  return std::complex<float>(3.1F, 3.1F);
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex)
}

/**
 * Method:  passinfcomplex[]
 */
bool
Args::Basic_impl::passinfcomplex_impl (
  /* in */const ::std::complex<float>& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex)
  return ( c.real() == 3.1F && c.imag() == 3.1F );
  // DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex)
}

/**
 * Method:  passoutfcomplex[]
 */
bool
Args::Basic_impl::passoutfcomplex_impl (
  /* out */::std::complex<float>& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex)
  c = std::complex<float>(3.1F,3.1F);
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex)
}

/**
 * Method:  passinoutfcomplex[]
 */
bool
Args::Basic_impl::passinoutfcomplex_impl (
  /* inout */::std::complex<float>& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex)
  c = std::complex<float>(c.real(),-c.imag());
  // was 'c=conj(c)', but I've had too many compilers complain... :[
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex)
}

/**
 * Method:  passeverywherefcomplex[]
 */
::std::complex<float>
Args::Basic_impl::passeverywherefcomplex_impl (
  /* in */const ::std::complex<float>& c1,
  /* out */::std::complex<float>& c2,
  /* inout */::std::complex<float>& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex)
  std::complex<float> temp1(3.1F,3.1F);
  std::complex<float> error(0.0, 0.0);
  c2 = temp1;
  c3 = std::complex<float>(c3.real(),-c3.imag());
  // was 'c3=conj(c3)', but I've had too many compilers complain... :[
  return ( c1 == temp1 ) ? temp1 : error ;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex)
}

/**
 * Method:  returnbackdcomplex[]
 */
::std::complex<double>
Args::Basic_impl::returnbackdcomplex_impl () 

{
  // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex)
  return std::complex<double>(3.14, 3.14);
  // DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex)
}

/**
 * Method:  passindcomplex[]
 */
bool
Args::Basic_impl::passindcomplex_impl (
  /* in */const ::std::complex<double>& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex)
  return ( c.real() == 3.14 && c.imag() == 3.14 );
  // DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex)
}

/**
 * Method:  passoutdcomplex[]
 */
bool
Args::Basic_impl::passoutdcomplex_impl (
  /* out */::std::complex<double>& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex)
  c = std::complex<double>(3.14,3.14);
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex)
}

/**
 * Method:  passinoutdcomplex[]
 */
bool
Args::Basic_impl::passinoutdcomplex_impl (
  /* inout */::std::complex<double>& c ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex)
  c = std::complex<double>(c.real(), -c.imag() );
  // was 'c=conj(c);' but I've had too many compilers complain for no reason.  :[
  return true;
  // DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex)
}

/**
 * Method:  passeverywheredcomplex[]
 */
::std::complex<double>
Args::Basic_impl::passeverywheredcomplex_impl (
  /* in */const ::std::complex<double>& c1,
  /* out */::std::complex<double>& c2,
  /* inout */::std::complex<double>& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex)
  std::complex<double> temp( 3.14, 3.14 );
  std::complex<double> error(0.0,0.0);
  c2 = temp;
  c3 = std::complex<double>(c3.real(),-c3.imag()); 
  // was 'c3=conj(c3);' but I've had too many compilers complain for no reason.  :[
  return ( c1 == temp ) ? temp : error;
  // DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex)
}


// DO-NOT-DELETE splicer.begin(Args.Basic._misc)
// Insert-Code-Here {Args.Basic._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(Args.Basic._misc)

