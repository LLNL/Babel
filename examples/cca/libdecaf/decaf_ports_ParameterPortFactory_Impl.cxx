// 
// File:          decaf_ports_ParameterPortFactory_Impl.cc
// Symbol:        decaf.ports.ParameterPortFactory-v0.6.3
// Symbol Type:   class
// Babel Version: 0.8.6
// Description:   Server-side implementation for decaf.ports.ParameterPortFactory
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.8.6
// 
#include "decaf_ports_ParameterPortFactory_Impl.hh"

// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._includes)

// user defined constructor
void decaf::ports::ParameterPortFactory_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._ctor)
}

// user defined destructor
void decaf::ports::ParameterPortFactory_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._dtor)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Initialize the portData for use in a ParameterPort
 *  with name portName.
 *  More than one such port can be defined.
 *  The given string portName will be cached in the TypeMap
 *  as the result of this function and must not be changed
 *  by the component henceforth.
 *  
 *  @param portData the user-supplied TypeMap associated with the port;
 *       It is somehow shared between the ParameterPortFactory
 *       and the component. The ParameterPortFactory will
 *       not read or change preexisting values in portData except those
 *       requested via the addRequest functions and those
 *       keys starting with the reserved prefix "gcpPPF.".
 *  @param portName The name of the ParameterPort to appear in
 *       the component. It must not conflict with other port
 *       names in the same component. The port name "CONFIG"
 *       is recommended if only one ParameterPort is being defined.
 *  
 * 
 */
void
decaf::ports::ParameterPortFactory_impl::initParameterData (
  /*inout*/ ::gov::cca::TypeMap& portData,
  /*in*/ const ::std::string& portName ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.initParameterData)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.initParameterData)
}

/**
 * Define the window title for the parameter UI dialog.
 */
void
decaf::ports::ParameterPortFactory_impl::setBatchTitle (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& title ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.setBatchTitle)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.setBatchTitle)
}

/**
 * Define the next tab/group title to use. All
 * addRequest subsequent calls will add to this group.
 * Multiple dialog tabs/groups can be defined in this way.
 */
void
decaf::ports::ParameterPortFactory_impl::setGroupName (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& newGroupName ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.setGroupName)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.setGroupName)
}

/**
 * Define a boolean parameter and its default state.
 * The configured value is always available by
 * portData->getBool(name, ...);
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestBoolean (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ const ::std::string& help,
  /*in*/ const ::std::string& prompt,
  /*in*/ bool bdefault ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestBoolean)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestBoolean)
}

/**
 * Define a int parameter and its default state.
 * The configured value is always available by
 * portData->getInt(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestInt (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ const ::std::string& help,
  /*in*/ const ::std::string& prompt,
  /*in*/ int32_t idefault,
  /*in*/ int32_t low,
  /*in*/ int32_t high ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestInt)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestInt)
}

/**
 * Define a long parameter and its default state.
 * The configured value is always available by
 * portData->getLong(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestLong (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ const ::std::string& help,
  /*in*/ const ::std::string& prompt,
  /*in*/ int64_t ldefault,
  /*in*/ int64_t low,
  /*in*/ int64_t high ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestLong)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestLong)
}

/**
 * Define a float parameter and its default state.
 * The configured value is always available by
 * portData->getFloat(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestFloat (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ const ::std::string& help,
  /*in*/ const ::std::string& prompt,
  /*in*/ float fdefault,
  /*in*/ float low,
  /*in*/ float high ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestFloat)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestFloat)
}

/**
 * Define a double parameter and its default state.
 * The configured value is always available by
 * portData->getDouble(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestDouble (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ const ::std::string& help,
  /*in*/ const ::std::string& prompt,
  /*in*/ double ddefault,
  /*in*/ double low,
  /*in*/ double high ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestDouble)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestDouble)
}

/**
 * Define a string parameter and its default state.
 * The configured value is always available by
 * portData->getString(name, ...).
 * If no addRequestStringChoice calls are made, the
 * user input may be any string. If addRequestStringChoice
 * is used, the value will be one among the choices.
 * If addRequestStringChoice is used, deflt must
 * be among the choices defined.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestString (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ const ::std::string& help,
  /*in*/ const ::std::string& prompt,
  /*in*/ const ::std::string& sdefault ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestString)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestString)
}

/**
 * Define a new choice for a string parameter.
 *  If no calls to this function are made for a given
 *  name, then any form of string will be acceptable input.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPortFactory_impl::addRequestStringChoice (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& key,
  /*in*/ const ::std::string& choice ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addRequestStringChoice)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addRequestStringChoice)
}

/**
 * As addRequestStringChoice, but in bulk. 
 */
void
decaf::ports::ParameterPortFactory_impl::addStringChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& key,
  /*in*/ ::sidl::array< ::std::string> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addStringChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addStringChoices)
}

/**
 * Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPortFactory_impl::addIntChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ ::sidl::array<int32_t> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addIntChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addIntChoices)
}

/**
 * Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPortFactory_impl::addLongChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ ::sidl::array<int64_t> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addLongChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addLongChoices)
}

/**
 * Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPortFactory_impl::addFloatChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ ::sidl::array<float> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addFloatChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addFloatChoices)
}

/**
 * Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPortFactory_impl::addDoubleChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ ::sidl::array<double> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addDoubleChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addDoubleChoices)
}

/**
 * Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPortFactory_impl::addFcomplexChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ ::sidl::array< ::sidl::fcomplex> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addFcomplexChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addFcomplexChoices)
}

/**
 * Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPortFactory_impl::addDcomplexChoices (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ const ::std::string& name,
  /*in*/ ::sidl::array< ::sidl::dcomplex> choices ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addDcomplexChoices)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addDcomplexChoices)
}

/**
 * Clear all previously added requests, titles, groups. After
 *  this call, it is as if the ParameterPort has
 *  seen initParameterData but otherwise never configured.
 *  The values of
 *  previously defined parameters (but not bounds, etc)
 *  remain in the TypeMap.
 *  Typically, this is used only by someone implementing
 *  the updateParameterPort function from
 *  interface ParameterGetListener.
 */
void
decaf::ports::ParameterPortFactory_impl::clearRequests (
  /*in*/ ::gov::cca::TypeMap portData ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.clearRequests)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.clearRequests)
}

/**
 * Register listener (the component) that wishes to have
 * a chance to change the contents of its ParameterPort
 * just before the parameters TypeMap is read.
 * @param powner a pointer to the listener that will be
 * forgotten when it is no longer needed. 
 */
void
decaf::ports::ParameterPortFactory_impl::registerUpdater (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ ::gov::cca::ports::ParameterGetListener powner ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.registerUpdater)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.registerUpdater)
}

/**
 * Register listener (the component) if it wishes to be
 * informed when an parameter is changed via writeConfigurationMap.
 * Listeners are called after values are set.
 */
void
decaf::ports::ParameterPortFactory_impl::registerUpdatedListener (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ ::gov::cca::ports::ParameterSetListener powner ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.registerUpdatedListener)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.registerUpdatedListener)
}

/**
 * Signal that the ParameterPort is fully defined and should
 * now pop out on the component.
 * The component is free to hold the portData map internally
 * without adding it until some desired time.
 * The Services passed here
 * must be the component's own Services handle.
 * The ParameterPortFactory takes care of addProvidesPort.
 */
void
decaf::ports::ParameterPortFactory_impl::addParameterPort (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ ::gov::cca::Services svc ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.addParameterPort)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.addParameterPort)
}

/**
 * Cause a previously defined parameter port to go away.
 * This function should be called at component shutdown
 * (setService(0)) time for any parameter ports that have
 * been added but not yet removed.
 * The ParameterPortFactory takes care of removeProvidesPort.
 * This does not change the parameter values in the
 * TypeMap.
 */
void
decaf::ports::ParameterPortFactory_impl::removeParameterPort (
  /*in*/ ::gov::cca::TypeMap portData,
  /*in*/ ::gov::cca::Services svc ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory.removeParameterPort)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory.removeParameterPort)
}


// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._misc)

