// 
// File:          Hello_World_Impl.cxx
// Symbol:        Hello.World-v1.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for Hello.World
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Hello_World_Impl.hxx"

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
// DO-NOT-DELETE splicer.begin(Hello.World._includes)
// nothing needed
// DO-NOT-DELETE splicer.end(Hello.World._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Hello::World_impl::World_impl() : StubBase(reinterpret_cast< void*>(
  ::Hello::World::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(Hello.World._ctor2)
  // Insert-Code-Here {Hello.World._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Hello.World._ctor2)
}

// user defined constructor
void Hello::World_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Hello.World._ctor)
  // nothing needed
  // DO-NOT-DELETE splicer.end(Hello.World._ctor)
}

// user defined destructor
void Hello::World_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Hello.World._dtor)
  // nothing needed
  // DO-NOT-DELETE splicer.end(Hello.World._dtor)
}

// static class initializer
void Hello::World_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Hello.World._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Hello.World._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getMsg[]
 */
::std::string
Hello::World_impl::getMsg_impl () 

{
  // DO-NOT-DELETE splicer.begin(Hello.World.getMsg)
  return std::string ("Hello world2!");
  // DO-NOT-DELETE splicer.end(Hello.World.getMsg)
}

/**
 * Method:  foo[]
 */
int32_t
Hello::World_impl::foo_impl (
  /* in */int32_t i,
  /* out */int32_t& o,
  /* inout */int32_t& io ) 
{
  // DO-NOT-DELETE splicer.begin(Hello.World.foo)
  // Insert-Code-Here {Hello.World.foo} (foo method)
  // DO-NOT-DELETE splicer.end(Hello.World.foo)
}


// DO-NOT-DELETE splicer.begin(Hello.World._misc)
// nothing needed
// DO-NOT-DELETE splicer.end(Hello.World._misc)

