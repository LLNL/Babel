// 
// File:          decaf_ComponentID_Impl.hxx
// Symbol:        decaf.ComponentID-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ComponentID
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_ComponentID_Impl_hxx
#define included_decaf_ComponentID_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_ComponentID_IOR_h
#include "decaf_ComponentID_IOR.h"
#endif
#ifndef included_decaf_ComponentID_hxx
#include "decaf_ComponentID.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentID_hxx
#include "gov_cca_ComponentID.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.ComponentID._hincludes)
#include <string>
// DO-NOT-DELETE splicer.end(decaf.ComponentID._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.ComponentID" (version 0.8.2)
   */
  class ComponentID_impl : public virtual ::decaf::ComponentID 
  // DO-NOT-DELETE splicer.begin(decaf.ComponentID._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.ComponentID._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.ComponentID._implementation)
    std::string d_name;
    // DO-NOT-DELETE splicer.end(decaf.ComponentID._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ComponentID_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      ComponentID_impl( struct decaf_ComponentID__object * ior ) : StubBase(ior,
        true), 
    ::gov::cca::ComponentID((ior==NULL) ? NULL : &((
      *ior).d_gov_cca_componentid)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ComponentID_impl() { _dtor(); }

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
      /* in */const ::std::string& name
    )
    ;


    /**
     *  
     * Returns the instance name provided in 
     * <code>BuilderService.createInstance()</code>
     * or in 
     * <code>AbstractFramework.getServices()</code>.
     * @throws CCAException if <code>ComponentID</code> is invalid
     */
    ::std::string
    getInstanceName_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     * Returns a framework specific serialization of the ComponentID.
     * @throws CCAException if <code>ComponentID</code> is
     * invalid.
     */
    ::std::string
    getSerialization_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;
  };  // end class ComponentID_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ComponentID._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ComponentID._hmisc)

#endif
