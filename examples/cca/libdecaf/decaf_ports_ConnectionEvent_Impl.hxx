// 
// File:          decaf_ports_ConnectionEvent_Impl.hxx
// Symbol:        decaf.ports.ConnectionEvent-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ports.ConnectionEvent
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_ports_ConnectionEvent_Impl_hxx
#define included_decaf_ports_ConnectionEvent_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_ports_ConnectionEvent_IOR_h
#include "decaf_ports_ConnectionEvent_IOR.h"
#endif
#ifndef included_decaf_ports_ConnectionEvent_hxx
#include "decaf_ports_ConnectionEvent.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_gov_cca_ports_ConnectionEvent_hxx
#include "gov_cca_ports_ConnectionEvent.hxx"
#endif
#ifndef included_gov_cca_ports_EventType_hxx
#include "gov_cca_ports_EventType.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._hincludes)

namespace decaf { 
  namespace ports { 

    /**
     * Symbol "decaf.ports.ConnectionEvent" (version 0.8.2)
     */
    class ConnectionEvent_impl : public virtual ::decaf::ports::ConnectionEvent 
    // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._inherits)
    // Put additional inheritance here...
    // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._inherits)

    {

    // All data marked protected will be accessable by 
    // descendant Impl classes
    protected:

      bool _wrapped;

      // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._implementation)
      gov::cca::ports::EventType d_event;
      gov::cca::TypeMap d_portProperties;
      // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._implementation)

    public:
      // default constructor, used for data wrapping(required)
      ConnectionEvent_impl();
      // sidl constructor (required)
      // Note: alternate Skel constructor doesn't call addref()
      // (fixes bug #275)
        ConnectionEvent_impl( struct decaf_ports_ConnectionEvent__object * ior 
          ) : StubBase(ior,true), 
      ::gov::cca::ports::ConnectionEvent((ior==NULL) ? NULL : &((
        *ior).d_gov_cca_ports_connectionevent)) , _wrapped(false) {
        ior->d_data = this;
        _ctor();
      }


      // user defined construction
      void _ctor();

      // virtual destructor (required)
      virtual ~ConnectionEvent_impl() { _dtor(); }

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
        /* in */::gov::cca::ports::EventType eventType,
        /* in */::gov::cca::TypeMap& portProperties
      )
      ;


      /**
       *  
       * <p>Returns the integer from those enumerated that describes the event.</p>
       * 
       * <p>
       * The semantics are noted before
       * each member of the enum/static constant. We can add in different
       * types of connect/disconnect as multiports and
       * explicit local/global/sync/async semantics are agreed to in the future.
       * At present we assume that:
       * <ul>
       * <li> All instances in a component cohort (often thought of as a single
       * "parallel component") receive all the events
       * and in the same order, but not necessarily globally synchronously.
       * 
       * <li> For disconnections, within a process the events are delivered first
       * to the using component then (if necessary) to the providing
       * component.
       * 
       * <li> For connections, within a process the events are delivered first
       * to the providing component then (if necessary) to the using
       * component.
       * </ul>
       * </p>
       * 
       * <p>
       * Clearly some of the assumptions above may not suit a component
       * instance in which multiple execution threads act on a
       * single instance of the <code>cca.Services</code> object (SMP). The Services
       * specification is ambiguous as to whether such a component is even
       * allowed.
       * </p>
       * <p>
       * When this is clarified, additional members of the enum may arise,
       * in which case the assumptions here apply only to
       * <code>ConnectPending</code>, <code>Connected</code>, <code>DisconnectPending</code>, 
       * <code>Disconnected</code> types.
       */
      ::gov::cca::ports::EventType
      getEventType_impl() ;

      /**
       *  
       * Get Properties of the affected Port.
       * Among the standard properties are the name and type info.
       * The keys are cca.portName, cca.portType.
       */
      ::gov::cca::TypeMap
      getPortInfo_impl() ;
    };  // end class ConnectionEvent_impl

  } // end namespace ports
} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._hmisc)

#endif
