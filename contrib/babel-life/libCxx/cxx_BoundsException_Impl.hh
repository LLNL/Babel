// 
// File:          cxx_BoundsException_Impl.hh
// Symbol:        cxx.BoundsException-v2.0
// Symbol Type:   class
// Babel Version: 0.9.6
// sidl Created:  20041004 21:44:21 PDT
// Generated:     20041004 21:44:25 PDT
// Description:   Server-side implementation for cxx.BoundsException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.9.6
// source-line   = 4
// source-url    = file:/home/kumfert/scdemo/hands-on/task_0/babel-life/libCxx/cxxlife.sidl
// 

#ifndef included_cxx_BoundsException_Impl_hh
#define included_cxx_BoundsException_Impl_hh

#ifndef included_sidl_cxx_hh
#include "sidl_cxx.hh"
#endif
#ifndef included_cxx_BoundsException_IOR_h
#include "cxx_BoundsException_IOR.h"
#endif
// 
// Includes for all method dependencies.
// 
#ifndef included_cxx_BoundsException_hh
#include "cxx_BoundsException.hh"
#endif
#ifndef included_sidl_BaseInterface_hh
#include "sidl_BaseInterface.hh"
#endif
#ifndef included_sidl_ClassInfo_hh
#include "sidl_ClassInfo.hh"
#endif


// DO-NOT-DELETE splicer.begin(cxx.BoundsException._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.BoundsException._includes)

namespace cxx { 

  /**
   * Symbol "cxx.BoundsException" (version 2.0)
   */
  class BoundsException_impl
  // DO-NOT-DELETE splicer.begin(cxx.BoundsException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cxx.BoundsException._inherits)
  {

  private:
    // Pointer back to IOR.
    // Use this to dispatch back through IOR vtable.
    BoundsException self;

    // DO-NOT-DELETE splicer.begin(cxx.BoundsException._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(cxx.BoundsException._implementation)

  private:
    // private default constructor (required)
    BoundsException_impl() {} 

  public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    BoundsException_impl( struct cxx_BoundsException__object * s ) : self(s,
      true) { _ctor(); }

    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~BoundsException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

  public:

  };  // end class BoundsException_impl

} // end namespace cxx

// DO-NOT-DELETE splicer.begin(cxx.BoundsException._misc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cxx.BoundsException._misc)

#endif
