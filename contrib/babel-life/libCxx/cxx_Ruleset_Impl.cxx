// 
// File:          cxx_Ruleset_Impl.cxx
// Symbol:        cxx.Ruleset-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.Ruleset
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_Ruleset_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_conway_BoundsException_hxx
#include "conway_BoundsException.hxx"
#endif
#ifndef included_conway_Environment_hxx
#include "conway_Environment.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(cxx.Ruleset._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.Ruleset._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::Ruleset_impl::Ruleset_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::Ruleset::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset._ctor2)
  // Insert-Code-Here {cxx.Ruleset._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.Ruleset._ctor2)
}

// user defined constructor
void cxx::Ruleset_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(cxx.Ruleset._ctor)
}

// user defined destructor
void cxx::Ruleset_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(cxx.Ruleset._dtor)
}

// static class initializer
void cxx::Ruleset_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset._load)
  // Insert-Code-Here {cxx.Ruleset._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.Ruleset._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Birth: an empty cell has 3 living neighbors
 * Death: a living cell has 0 or 1 neighbors (loneliness)
 * or a living cell has 4-8 neighbors (overcrowding)
 * Life: a living cell has 2 or three neighbors
 */
bool
cxx::Ruleset_impl::setAlive_impl (
  /* in */int32_t x,
  /* in */int32_t y,
  /* in */::conway::Environment& env ) 
// throws:
//     ::conway::BoundsException
//     ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(cxx.Ruleset.setAlive)
  int32_t n = env.nNeighbors(x,y); // throws exception here if out of bounds
  switch (n) { 
  case 0: case 1:
    return false;  // if was alive, dies of loneliness
  case 2: 
    return env.isAlive(x,y); //is alive only if it was last turn
  case 3: 
    return true;  // if was alive, it continues, if not, new one is born
  default: // case 4 and above
    return false;  // if was alive, dies of overcrowding

  }
  // DO-NOT-DELETE splicer.end(cxx.Ruleset.setAlive)
}


// DO-NOT-DELETE splicer.begin(cxx.Ruleset._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(cxx.Ruleset._misc)

