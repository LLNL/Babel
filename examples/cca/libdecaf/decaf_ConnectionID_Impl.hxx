// 
// File:          decaf_ConnectionID_Impl.hxx
// Symbol:        decaf.ConnectionID-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ConnectionID
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_ConnectionID_Impl_hxx
#define included_decaf_ConnectionID_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_ConnectionID_IOR_h
#include "decaf_ConnectionID_IOR.h"
#endif
#ifndef included_decaf_ConnectionID_hxx
#include "decaf_ConnectionID.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentID_hxx
#include "gov_cca_ComponentID.hxx"
#endif
#ifndef included_gov_cca_ConnectionID_hxx
#include "gov_cca_ConnectionID.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_sidl_BaseClass_hxx
#include "sidl_BaseClass.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.ConnectionID._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ConnectionID._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.ConnectionID" (version 0.8.2)
   */
  class ConnectionID_impl : public virtual ::decaf::ConnectionID 
  // DO-NOT-DELETE splicer.begin(decaf.ConnectionID._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.ConnectionID._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.ConnectionID._implementation)
    gov::cca::ComponentID d_provider;
    gov::cca::ComponentID d_user;
    std::string d_providerPortName;
    std::string d_userPortName;
    gov::cca::TypeMap d_properties;
    // DO-NOT-DELETE splicer.end(decaf.ConnectionID._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ConnectionID_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      ConnectionID_impl( struct decaf_ConnectionID__object * ior ) : StubBase(
        ior,true), 
    ::gov::cca::ConnectionID((ior==NULL) ? NULL : &((
      *ior).d_gov_cca_connectionid)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ConnectionID_impl() { _dtor(); }

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
    void
    initialize_impl (
      /* in */::gov::cca::ComponentID& user,
      /* in */const ::std::string& userPortName,
      /* in */::gov::cca::ComponentID& provider,
      /* in */const ::std::string& providerPortName,
      /* in */::gov::cca::TypeMap& properties
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    setProperties_impl (
      /* in */::gov::cca::TypeMap& properties
    )
    ;

    /**
     * user defined non-static method.
     */
    ::gov::cca::TypeMap
    getProperties_impl() ;

    /**
     *  
     * Get the providing component (callee) ID.
     * @return ComponentID of the component that has 
     * provided the Port for this connection. 
     * @throws CCAException if the underlying connection 
     * is no longer valid.
     */
    ::gov::cca::ComponentID
    getProvider_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     *  
     * Get the using component (caller) ID.
     * @return ComponentID of the component that is using the provided Port.
     * @throws CCAException if the underlying connection is no longer valid.
     */
    ::gov::cca::ComponentID
    getUser_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     *  
     * Get the port name in the providing component of this connection.
     * @return the instance name of the provided Port.
     * @throws CCAException if the underlying connection is no longer valid.
     */
    ::std::string
    getProviderPortName_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     *  
     * Get the port name in the using component of this connection.
     * Return the instance name of the Port registered for use in 
     * this connection.
     * @throws CCAException if the underlying connection is no longer valid.
     */
    ::std::string
    getUserPortName_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;
  };  // end class ConnectionID_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ConnectionID._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ConnectionID._hmisc)

#endif
