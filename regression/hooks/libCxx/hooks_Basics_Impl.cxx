// 
// File:          hooks_Basics_Impl.cxx
// Symbol:        hooks.Basics-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for hooks.Basics
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "hooks_Basics_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(hooks.Basics._includes)
#include "synch.hxx"

int hooks::Basics_impl::num_prehooks_static = 0;
int hooks::Basics_impl::num_posthooks_static = 0;

// DO-NOT-DELETE splicer.end(hooks.Basics._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
hooks::Basics_impl::Basics_impl() : StubBase(reinterpret_cast< void*>(
  ::hooks::Basics::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(hooks.Basics._ctor2)
  // Insert-Code-Here {hooks.Basics._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(hooks.Basics._ctor2)
}

// user defined constructor
void hooks::Basics_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(hooks.Basics._ctor)
  num_prehooks = 0;
  num_posthooks = 0;
  // DO-NOT-DELETE splicer.end(hooks.Basics._ctor)
}

// user defined destructor
void hooks::Basics_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(hooks.Basics._dtor)
  // Insert-Code-Here {hooks.Basics._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(hooks.Basics._dtor)
}

// static class initializer
void hooks::Basics_impl::_load() {
  // DO-NOT-DELETE splicer.begin(hooks.Basics._load)
  // Insert-Code-Here {hooks.Basics._load} (class initialization)
  // DO-NOT-DELETE splicer.end(hooks.Basics._load)
}

// user defined static methods:
/**
 * Basic illustration of hooks for static methods.
 */
void
hooks::Basics_impl::aStaticMeth_pre_impl (
  /* in */int32_t i,
  /* in */int32_t io ) 
{
  // DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_pre)
  // Insert-Code-Here {hooks.Basics.aStaticMeth_pre} (aStaticMeth_pre method)
  num_prehooks_static++;
  // DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_pre)
}

/**
 * Basic illustration of hooks for static methods.
 */
int32_t
hooks::Basics_impl::aStaticMeth_impl (
  /* in */int32_t i,
  /* out */int32_t& o,
  /* inout */int32_t& io ) 
{
  // DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth)
  // Insert-Code-Here {hooks.Basics.aStaticMeth} (aStaticMeth method)
  o = num_prehooks_static;
  io = num_posthooks_static;
  return 1;
  // DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth)
}

/**
 * Basic illustration of hooks for static methods.
 */
void
hooks::Basics_impl::aStaticMeth_post_impl (
  /* in */int32_t i,
  /* in */int32_t o,
  /* in */int32_t io,
  /* in */int32_t _retval ) 
{
  // DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_post)
  // Insert-Code-Here {hooks.Basics.aStaticMeth_post} (aStaticMeth_post method)
  num_posthooks_static++;
  // DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_post)
}


// user defined non-static methods:
/**
 * Basic illustration of hooks for static methods.
 */
void
hooks::Basics_impl::aNonStaticMeth_pre_impl (
  /* in */int32_t i,
  /* in */int32_t io ) 
{
  // DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_pre)
  // Insert-Code-Here {hooks.Basics.aNonStaticMeth_pre} (aNonStaticMeth_pre method)
  num_prehooks++;
  // DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_pre)
}

/**
 * Basic illustration of hooks for static methods.
 */
int32_t
hooks::Basics_impl::aNonStaticMeth_impl (
  /* in */int32_t i,
  /* out */int32_t& o,
  /* inout */int32_t& io ) 
{
  // DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth)
  // Insert-Code-Here {hooks.Basics.aNonStaticMeth} (aNonStaticMeth method)
  o = num_prehooks;
  io = num_posthooks;
  return 1;
  // DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth)
}

/**
 * Basic illustration of hooks for static methods.
 */
void
hooks::Basics_impl::aNonStaticMeth_post_impl (
  /* in */int32_t i,
  /* in */int32_t o,
  /* in */int32_t io,
  /* in */int32_t _retval ) 
{
  // DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_post)
  // Insert-Code-Here {hooks.Basics.aNonStaticMeth_post} (aNonStaticMeth_post method)
  num_posthooks++;
  // DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_post)
}


// DO-NOT-DELETE splicer.begin(hooks.Basics._misc)
// Insert-Code-Here {hooks.Basics._misc} (miscellaneous code)
// DO-NOT-DELETE splicer.end(hooks.Basics._misc)

