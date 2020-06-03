// 
// File:          Overload_Test_Impl.cxx
// Symbol:        Overload.Test-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.Test
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Overload_Test_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_Overload_AClass_hxx
#include "Overload_AClass.hxx"
#endif
#ifndef included_Overload_AnException_hxx
#include "Overload_AnException.hxx"
#endif
#ifndef included_Overload_BClass_hxx
#include "Overload_BClass.hxx"
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
// DO-NOT-DELETE splicer.begin(Overload.Test._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.Test._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Overload::Test_impl::Test_impl() : StubBase(reinterpret_cast< void*>(
  ::Overload::Test::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Overload.Test._ctor2)
  // Insert-Code-Here {Overload.Test._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Overload.Test._ctor2)
}

// user defined constructor
void Overload::Test_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Overload.Test._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Overload.Test._ctor)
}

// user defined destructor
void Overload::Test_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Overload.Test._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Overload.Test._dtor)
}

// static class initializer
void Overload::Test_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Overload.Test._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Overload.Test._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getValue[IntDouble]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */int32_t a,
  /* in */double b ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble)
  return ((double)a + b);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble)
}

/**
 * Method:  getValue[DoubleInt]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */double a,
  /* in */int32_t b ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt)
  return (a + (double)b);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt)
}

/**
 * Method:  getValue[IntDoubleFloat]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */int32_t a,
  /* in */double b,
  /* in */float c ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat)
  return ((double)a + b + (double)c);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat)
}

/**
 * Method:  getValue[DoubleIntFloat]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */double a,
  /* in */int32_t b,
  /* in */float c ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat)
  return (a + (double)b + (double)c);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat)
}

/**
 * Method:  getValue[IntFloatDouble]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */int32_t a,
  /* in */float b,
  /* in */double c ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble)
  return ((double)a + (double)b + c);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble)
}

/**
 * Method:  getValue[DoubleFloatInt]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */double a,
  /* in */float b,
  /* in */int32_t c ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt)
  return (a + (double)b + (double)c);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt)
}

/**
 * Method:  getValue[FloatIntDouble]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */float a,
  /* in */int32_t b,
  /* in */double c ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble)
  return ((double)a + (double)b + c);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble)
}

/**
 * Method:  getValue[FloatDoubleInt]
 */
double
Overload::Test_impl::getValue_impl (
  /* in */float a,
  /* in */double b,
  /* in */int32_t c ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt)
  return ((double)a + b + (double)c);
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt)
}

/**
 * Method:  getValue[Exception]
 */
::std::string
Overload::Test_impl::getValue_impl (
  /* in */::Overload::AnException& v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueException)
  return v.getNote();
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueException)
}

/**
 * Method:  getValue[AClass]
 */
int32_t
Overload::Test_impl::getValue_impl (
  /* in */::Overload::AClass& v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass)
  return v.getValue();
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass)
}

/**
 * Method:  getValue[BClass]
 */
int32_t
Overload::Test_impl::getValue_impl (
  /* in */::Overload::BClass& v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass)
  return v.getValue();
  // DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass)
}


// DO-NOT-DELETE splicer.begin(Overload.Test._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Overload.Test._misc)

