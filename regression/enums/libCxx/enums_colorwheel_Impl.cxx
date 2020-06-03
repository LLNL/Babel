// 
// File:          enums_colorwheel_Impl.cxx
// Symbol:        enums.colorwheel-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for enums.colorwheel
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "enums_colorwheel_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_enums_color_hxx
#include "enums_color.hxx"
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
// DO-NOT-DELETE splicer.begin(enums.colorwheel._includes)
using namespace UCXX ::enums;
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(enums.colorwheel._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
enums::colorwheel_impl::colorwheel_impl() : StubBase(reinterpret_cast< void*>(
  ::enums::colorwheel::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor2)
  // Insert-Code-Here {enums.colorwheel._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(enums.colorwheel._ctor2)
}

// user defined constructor
void enums::colorwheel_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(enums.colorwheel._ctor)
}

// user defined destructor
void enums::colorwheel_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(enums.colorwheel._dtor)
}

// static class initializer
void enums::colorwheel_impl::_load() {
  // DO-NOT-DELETE splicer.begin(enums.colorwheel._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(enums.colorwheel._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  returnback[]
 */
::enums::color
enums::colorwheel_impl::returnback_impl () 

{
  // DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback)
  return color_violet;
  // DO-NOT-DELETE splicer.end(enums.colorwheel.returnback)
}

/**
 * Method:  passin[]
 */
bool
enums::colorwheel_impl::passin_impl (
  /* in */::enums::color c ) 
{
  // DO-NOT-DELETE splicer.begin(enums.colorwheel.passin)
  return ( c == color_blue );
  // DO-NOT-DELETE splicer.end(enums.colorwheel.passin)
}

/**
 * Method:  passout[]
 */
bool
enums::colorwheel_impl::passout_impl (
  /* out */::enums::color& c ) 
{
  // DO-NOT-DELETE splicer.begin(enums.colorwheel.passout)
  c = color_violet;
  return true;
  // DO-NOT-DELETE splicer.end(enums.colorwheel.passout)
}

/**
 * Method:  passinout[]
 */
bool
enums::colorwheel_impl::passinout_impl (
  /* inout */::enums::color& c ) 
{
  // DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout)
  switch ( c ) { 
  case color_red:
    c = color_green; 
    break;
  case color_orange:
    c = color_blue;
    break;
  case color_yellow:
    c = color_violet;
    break;
  case color_green:
    c = color_red;
    break;
  case color_blue:
    c = color_orange;
    break;
  case color_violet:
    c = color_yellow;
    break;
  default:
    return false;
  }
  return true;
  // DO-NOT-DELETE splicer.end(enums.colorwheel.passinout)
}

/**
 * Method:  passeverywhere[]
 */
::enums::color
enums::colorwheel_impl::passeverywhere_impl (
  /* in */::enums::color c1,
  /* out */::enums::color& c2,
  /* inout */::enums::color& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere)
  c2 = color_violet;
  switch ( c3 ) { 
  case color_red:
    c3 = color_green; 
    break;
  case color_orange:
    c3 = color_blue;
    break;
  case color_yellow:
    c3 = color_violet;
    break;
  case color_green:
    c3 = color_red;
    break;
  case color_blue:
    c3 = color_orange;
    break;
  case color_violet:
    c3 = color_yellow;
    break;
  default:
    return (color) false;
  }
  return ( c1 == color_blue ) ? color_violet : (color) false;
  // DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere)
}


// DO-NOT-DELETE splicer.begin(enums.colorwheel._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(enums.colorwheel._misc)

