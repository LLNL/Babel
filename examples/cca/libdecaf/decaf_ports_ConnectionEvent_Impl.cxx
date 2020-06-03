// 
// File:          decaf_ports_ConnectionEvent_Impl.cxx
// Symbol:        decaf.ports.ConnectionEvent-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ports.ConnectionEvent
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_ports_ConnectionEvent_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_gov_cca_ports_EventType_hxx
#include "gov_cca_ports_EventType.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::ports::ConnectionEvent_impl::ConnectionEvent_impl() : StubBase(
  reinterpret_cast< void*>(::decaf::ports::ConnectionEvent::_wrapObj(
  reinterpret_cast< void*>(this))),false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._ctor2)
  // Insert-Code-Here {decaf.ports.ConnectionEvent._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._ctor2)
}

// user defined constructor
void decaf::ports::ConnectionEvent_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._ctor)
}

// user defined destructor
void decaf::ports::ConnectionEvent_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._dtor)
}

// static class initializer
void decaf::ports::ConnectionEvent_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  initialize[]
 */
void
decaf::ports::ConnectionEvent_impl::initialize_impl (
  /* in */::gov::cca::ports::EventType eventType,
  /* in */::gov::cca::TypeMap& portProperties ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent.initialize)
  d_event = eventType; 
  d_portProperties = portProperties; 
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent.initialize)
}

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
decaf::ports::ConnectionEvent_impl::getEventType_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent.getEventType)
  return d_event;
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent.getEventType)
}

/**
 *  
 * Get Properties of the affected Port.
 * Among the standard properties are the name and type info.
 * The keys are cca.portName, cca.portType.
 */
::gov::cca::TypeMap
decaf::ports::ConnectionEvent_impl::getPortInfo_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent.getPortInfo)
  return d_portProperties;
  // DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent.getPortInfo)
}


// DO-NOT-DELETE splicer.begin(decaf.ports.ConnectionEvent._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ports.ConnectionEvent._misc)

