// 
// File:          decaf_CCAException_Impl.hxx
// Symbol:        decaf.CCAException-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.CCAException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_CCAException_Impl_hxx
#define included_decaf_CCAException_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_CCAException_IOR_h
#include "decaf_CCAException_IOR.h"
#endif
#ifndef included_decaf_CCAException_hxx
#include "decaf_CCAException.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_CCAExceptionType_hxx
#include "gov_cca_CCAExceptionType.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_SIDLException_hxx
#include "sidl_SIDLException.hxx"
#endif
#ifndef included_sidl_io_Deserializer_hxx
#include "sidl_io_Deserializer.hxx"
#endif
#ifndef included_sidl_io_Serializer_hxx
#include "sidl_io_Serializer.hxx"
#endif


// DO-NOT-DELETE splicer.begin(decaf.CCAException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.CCAException._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.CCAException" (version 0.8.2)
   */
  class CCAException_impl : public virtual ::decaf::CCAException 
  // DO-NOT-DELETE splicer.begin(decaf.CCAException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.CCAException._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.CCAException._implementation)
    gov::cca::CCAExceptionType d_exceptionType;
    // DO-NOT-DELETE splicer.end(decaf.CCAException._implementation)

  public:
    // default constructor, used for data wrapping(required)
    CCAException_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      CCAException_impl( struct decaf_CCAException__object * ior ) : StubBase(
        ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_io_serializable)),
      ::sidl::BaseException((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_baseexception)),
    ::gov::cca::CCAException((ior==NULL) ? NULL : &((
      *ior).d_gov_cca_ccaexception)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~CCAException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * user defined non-static method.
     */
    ::gov::cca::CCAExceptionType
    getCCAExceptionType_impl() ;
    /**
     * user defined non-static method.
     */
    void
    setCCAExceptionType_impl (
      /* in */::gov::cca::CCAExceptionType et
    )
    ;

  };  // end class CCAException_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.CCAException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.CCAException._hmisc)

#endif
