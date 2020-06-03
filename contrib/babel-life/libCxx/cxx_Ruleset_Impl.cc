// 
// File:          cxx_Ruleset_Impl.cc
// Symbol:        cxx.Ruleset-v2.0
// Symbol Type:   class
// Babel Version: 0.9.6
// sidl Created:  20041004 21:44:22 PDT
// Generated:     20041004 21:44:24 PDT
// Description:   Server-side implementation for cxx.Ruleset
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.9.6
// source-line   = 6
// source-url    = file:/home/kumfert/scdemo/hands-on/task_0/babel-life/libCxx/cxxlife.sidl
// 
#include "cxx_Ruleset_Impl.hh"

// DO-NOT-DELETE splicer.begin(cxx.Ruleset._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.Ruleset._includes)

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

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Birth: an empty cell has 3 living neighbors
 * Death: a living cell has 0 or 1 neighbors (loneliness)
 *        or a living cell has 4-8 neighbors (overcrowding)
 * Life: a living cell has 2 or three neighbors
 */
bool
cxx::Ruleset_impl::setAlive (
  /*in*/ int32_t x,
  /*in*/ int32_t y,
  /*in*/ ::conway::Environment env ) 
throw ( 
  ::conway::BoundsException
){
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

