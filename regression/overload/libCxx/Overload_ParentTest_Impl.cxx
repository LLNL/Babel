// 
// File:          Overload_ParentTest_Impl.cxx
// Symbol:        Overload.ParentTest-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.ParentTest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Overload_ParentTest_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Overload.ParentTest._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.ParentTest._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Overload::ParentTest_impl::ParentTest_impl() : StubBase(reinterpret_cast< 
  void*>(::Overload::ParentTest::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor2)
  // Insert-Code-Here {Overload.ParentTest._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor2)
}

// user defined constructor
void Overload::ParentTest_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor)
}

// user defined destructor
void Overload::ParentTest_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor)
}

// static class initializer
void Overload::ParentTest_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Overload.ParentTest._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getValue[]
 */
int32_t
Overload::ParentTest_impl::getValue_impl () 

{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue)
  return 1;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue)
}

/**
 * Method:  getValue[Int]
 */
int32_t
Overload::ParentTest_impl::getValue_impl (
  /* in */int32_t v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt)
}

/**
 * Method:  getValue[Bool]
 */
bool
Overload::ParentTest_impl::getValue_impl (
  /* in */bool v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool)
}

/**
 * Method:  getValue[Double]
 */
double
Overload::ParentTest_impl::getValue_impl (
  /* in */double v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble)
}

/**
 * Method:  getValue[Dcomplex]
 */
::std::complex<double>
Overload::ParentTest_impl::getValue_impl (
  /* in */const ::std::complex<double>& v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex)
}

/**
 * Method:  getValue[Float]
 */
float
Overload::ParentTest_impl::getValue_impl (
  /* in */float v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat)
}

/**
 * Method:  getValue[Fcomplex]
 */
::std::complex<float>
Overload::ParentTest_impl::getValue_impl (
  /* in */const ::std::complex<float>& v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex)
}

/**
 * Method:  getValue[String]
 */
::std::string
Overload::ParentTest_impl::getValue_impl (
  /* in */const ::std::string& v ) 
{
  // DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString)
  return v;
  // DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString)
}


// DO-NOT-DELETE splicer.begin(Overload.ParentTest._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Overload.ParentTest._misc)

