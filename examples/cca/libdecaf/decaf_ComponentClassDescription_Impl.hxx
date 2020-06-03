// 
// File:          decaf_ComponentClassDescription_Impl.hxx
// Symbol:        decaf.ComponentClassDescription-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ComponentClassDescription
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_ComponentClassDescription_Impl_hxx
#define included_decaf_ComponentClassDescription_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_ComponentClassDescription_IOR_h
#include "decaf_ComponentClassDescription_IOR.h"
#endif
#ifndef included_decaf_ComponentClassDescription_hxx
#include "decaf_ComponentClassDescription.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentClassDescription_hxx
#include "gov_cca_ComponentClassDescription.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.ComponentClassDescription" (version 0.8.2)
   */
  class ComponentClassDescription_impl : public virtual 
    ::decaf::ComponentClassDescription 
  // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ComponentClassDescription_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      ComponentClassDescription_impl( struct 
        decaf_ComponentClassDescription__object * ior ) : StubBase(ior,true), 
    ::gov::cca::ComponentClassDescription((ior==NULL) ? NULL : &((
      *ior).d_gov_cca_componentclassdescription)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ComponentClassDescription_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     *  
     * Returns the class name provided in 
     * <code>BuilderService.createInstance()</code>
     * or in
     * <code>AbstractFramework.getServices()</code>.
     * <p>
     * Throws <code>CCAException</code> if <code>ComponentClassDescription</code> is invalid.
     */
    ::std::string
    getComponentClassName_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;
  };  // end class ComponentClassDescription_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ComponentClassDescription._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ComponentClassDescription._hmisc)

#endif
