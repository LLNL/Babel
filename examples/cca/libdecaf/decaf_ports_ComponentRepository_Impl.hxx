// 
// File:          decaf_ports_ComponentRepository_Impl.hxx
// Symbol:        decaf.ports.ComponentRepository-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ports.ComponentRepository
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_ports_ComponentRepository_Impl_hxx
#define included_decaf_ports_ComponentRepository_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_ports_ComponentRepository_IOR_h
#include "decaf_ports_ComponentRepository_IOR.h"
#endif
#ifndef included_decaf_ports_ComponentRepository_hxx
#include "decaf_ports_ComponentRepository.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentClassDescription_hxx
#include "gov_cca_ComponentClassDescription.hxx"
#endif
#ifndef included_gov_cca_ports_ComponentRepository_hxx
#include "gov_cca_ports_ComponentRepository.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._hincludes)

namespace decaf { 
  namespace ports { 

    /**
     * Symbol "decaf.ports.ComponentRepository" (version 0.8.2)
     */
    class ComponentRepository_impl : public virtual 
      ::decaf::ports::ComponentRepository 
    // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._inherits)
    // Put additional inheritance here...
    // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._inherits)

    {

    // All data marked protected will be accessable by 
    // descendant Impl classes
    protected:

      bool _wrapped;

      // DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._implementation)
      // Put additional implementation details here...
      // DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._implementation)

    public:
      // default constructor, used for data wrapping(required)
      ComponentRepository_impl();
      // sidl constructor (required)
      // Note: alternate Skel constructor doesn't call addref()
      // (fixes bug #275)
        ComponentRepository_impl( struct 
          decaf_ports_ComponentRepository__object * ior ) : StubBase(ior,true), 
        ::gov::cca::Port((ior==NULL) ? NULL : &((*ior).d_gov_cca_port)),
      ::gov::cca::ports::ComponentRepository((ior==NULL) ? NULL : &((
        *ior).d_gov_cca_ports_componentrepository)) , _wrapped(false) {
        ior->d_data = this;
        _ctor();
      }


      // user defined construction
      void _ctor();

      // virtual destructor (required)
      virtual ~ComponentRepository_impl() { _dtor(); }

      // user defined destruction
      void _dtor();

      // true if this object was created by a user newing the impl
      inline bool _isWrapped() {return _wrapped;}

      // static class initializer
      static void _load();

    public:


      /**
       *  
       * Collect the currently obtainable class name strings from
       * factories known to the builder and the from the
       * already instantiated components.
       * @return The list of class description, which may be empty, that are
       * known a priori to contain valid values for the className
       * argument of createInstance. 
       * @throws CCAException in the event of error.
       */
      ::sidl::array< ::gov::cca::ComponentClassDescription>
      getAvailableComponentClasses_impl() // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;
    };  // end class ComponentRepository_impl

  } // end namespace ports
} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ports.ComponentRepository._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ports.ComponentRepository._hmisc)

#endif
