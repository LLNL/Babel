// 
// File:          decaf_TypeMismatchException_Impl.hxx
// Symbol:        decaf.TypeMismatchException-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.TypeMismatchException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_TypeMismatchException_Impl_hxx
#define included_decaf_TypeMismatchException_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_TypeMismatchException_IOR_h
#include "decaf_TypeMismatchException_IOR.h"
#endif
#ifndef included_decaf_TypeMismatchException_hxx
#include "decaf_TypeMismatchException.hxx"
#endif
#ifndef included_gov_cca_CCAExceptionType_hxx
#include "gov_cca_CCAExceptionType.hxx"
#endif
#ifndef included_gov_cca_Type_hxx
#include "gov_cca_Type.hxx"
#endif
#ifndef included_gov_cca_TypeMismatchException_hxx
#include "gov_cca_TypeMismatchException.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.TypeMismatchException" (version 0.8.2)
   */
  class TypeMismatchException_impl : public virtual 
    ::decaf::TypeMismatchException 
  // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._implementation)
    gov::cca::CCAExceptionType d_exceptionType;
    gov::cca::Type d_actualType;
    gov::cca::Type d_requestedType;
    // DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._implementation)

  public:
    // default constructor, used for data wrapping(required)
    TypeMismatchException_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      TypeMismatchException_impl( struct decaf_TypeMismatchException__object * 
        ior ) : StubBase(ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_io_serializable)),
      ::sidl::BaseException((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_baseexception)),
      ::gov::cca::CCAException((ior==NULL) ? NULL : &((
        *ior).d_gov_cca_ccaexception)),
    ::gov::cca::TypeMismatchException((ior==NULL) ? NULL : &((
      *ior).d_gov_cca_typemismatchexception)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~TypeMismatchException_impl() { _dtor(); }

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
    ::gov::cca::Type
    getRequestedType_impl() ;
    /**
     * user defined non-static method.
     */
    ::gov::cca::Type
    getActualType_impl() ;
    /**
     * user defined non-static method.
     */
    void
    initialize_impl (
      /* in */::gov::cca::Type requestedType,
      /* in */::gov::cca::Type actualType
    )
    ;

  };  // end class TypeMismatchException_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.TypeMismatchException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.TypeMismatchException._hmisc)

#endif
