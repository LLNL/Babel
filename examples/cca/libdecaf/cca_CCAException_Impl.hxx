// 
// File:          cca_CCAException_Impl.hh
// Symbol:        cca.CCAException-v0.6
// Symbol Type:   class
// Babel Version: 0.7.4
// Description:   Server-side implementation for cca.CCAException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.7.4
// 

#ifndef included_cca_CCAException_Impl_hh
#define included_cca_CCAException_Impl_hh

#ifndef included_sidl_cxx_hh
#include "sidl_cxx.hh"
#endif
#ifndef included_cca_CCAException_IOR_h
#include "cca_CCAException_IOR.h"
#endif
// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hh
#include "sidl_BaseInterface.hh"
#endif
#ifndef included_cca_CCAException_hh
#include "cca_CCAException.hh"
#endif
#ifndef included_cca_CCAExceptionType_hh
#include "cca_CCAExceptionType.hh"
#endif


// DO-NOT-DELETE splicer.begin(cca.CCAException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cca.CCAException._hincludes)

namespace cca { 

  /**
   * Symbol "cca.CCAException" (version 0.6)
   * 
   * Most CCA interfaces throw this exception 
   */
  class CCAException_impl
  // DO-NOT-DELETE splicer.begin(cca.CCAException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(cca.CCAException._inherits)
  {

  private:
    // Pointer back to IOR.
    // Use this to dispatch back through IOR vtable.
    CCAException self;

    // DO-NOT-DELETE splicer.begin(cca.CCAException._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(cca.CCAException._implementation)

  private:
    // private default constructor (required)
    CCAException_impl() {} 

  public:
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    CCAException_impl( struct cca_CCAException__object * s ) : self(s,
      true) { _ctor(); }

    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~CCAException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

  public:

  };  // end class CCAException_impl

} // end namespace cca

// DO-NOT-DELETE splicer.begin(cca.CCAException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(cca.CCAException._hmisc)

#endif
