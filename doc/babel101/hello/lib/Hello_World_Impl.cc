// 
// File:          Hello_World_Impl.cc
// Symbol:        Hello.World-v1.0
// Symbol Type:   class
// Babel Version: 0.8.0
// SIDL Created:  20030121 09:05:50 PDT
// Generated:     20030121 09:05:53 PDT
// Description:   C++ Source for Implementation
// 
// babel-version = 0.8.0
// source-line   = 4
// source-url    = file:/home/babel/doc/babel101/hello/lib/../hello.sidl
// 
#include "Hello_World_Impl.hh"

// DO-NOT-DELETE splicer.begin(Hello.World._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Hello.World._includes)

// user defined constructor
void Hello::World_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Hello.World._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Hello.World._ctor)
}

// user defined destructor
void Hello::World_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Hello.World._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Hello.World._dtor)
}

// user defined static methods: (none)

// user defined non-static methods:
string
Hello::World_impl::getMsg () 
throw () 

{
  // DO-NOT-DELETE splicer.begin(Hello.World.getMsg)
  return string("Hello, World!");
  // DO-NOT-DELETE splicer.end(Hello.World.getMsg)
}


// DO-NOT-DELETE splicer.begin(Hello.World._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Hello.World._misc)

