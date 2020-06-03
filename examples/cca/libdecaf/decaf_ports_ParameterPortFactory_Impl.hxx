// 
// File:          decaf_ports_ParameterPortFactory_Impl.hh
// Symbol:        decaf.ports.ParameterPortFactory-v0.6.3
// Symbol Type:   class
// Babel Version: 0.8.6
// Description:   Server-side implementation for decaf.ports.ParameterPortFactory
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.8.6
// 

#ifndef included_decaf_ports_ParameterPortFactory_Impl_hh
#define included_decaf_ports_ParameterPortFactory_Impl_hh

#ifndef included_sidl_cxx_hh
#include "sidl_cxx.hh"
#endif
#ifndef included_decaf_ports_ParameterPortFactory_IOR_h
#include "decaf_ports_ParameterPortFactory_IOR.h"
#endif
// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hh
#include "sidl_BaseInterface.hh"
#endif
#ifndef included_sidl_ClassInfo_hh
#include "sidl_ClassInfo.hh"
#endif
#ifndef included_decaf_ports_ParameterPortFactory_hh
#include "decaf_ports_ParameterPortFactory.hh"
#endif
#ifndef included_gov_cca_Services_hh
#include "gov_cca_Services.hh"
#endif
#ifndef included_gov_cca_TypeMap_hh
#include "gov_cca_TypeMap.hh"
#endif
#ifndef included_gov_cca_ports_ParameterGetListener_hh
#include "gov_cca_ports_ParameterGetListener.hh"
#endif
#ifndef included_gov_cca_ports_ParameterSetListener_hh
#include "gov_cca_ports_ParameterSetListener.hh"
#endif


// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._hincludes)

namespace decaf { 
  namespace ports { 

    /**
     * Symbol "decaf.ports.ParameterPortFactory" (version 0.6.3)
     */
    class ParameterPortFactory_impl
    // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._inherits)
    // Put additional inheritance here...
    // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._inherits)
    {

    private:
      // Pointer back to IOR.
      // Use this to dispatch back through IOR vtable.
      ParameterPortFactory self;

      // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._implementation)
      // Put additional implementation details here...
      // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._implementation)

    private:
      // private default constructor (required)
      ParameterPortFactory_impl() {} 

    public:
      // sidl constructor (required)
      // Note: alternate Skel constructor doesn't call addref()
      // (fixes bug #275)
      ParameterPortFactory_impl( struct 
        decaf_ports_ParameterPortFactory__object * s ) : self(s,
        true) { _ctor(); }

      // user defined construction
      void _ctor();

      // virtual destructor (required)
      virtual ~ParameterPortFactory_impl() { _dtor(); }

      // user defined destruction
      void _dtor();

    public:


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
      initParameterData (
        /*inout*/ ::gov::cca::TypeMap& portData,
        /*in*/ const ::std::string& portName
      )
      throw () 
      ;


      /**
       * Define the window title for the parameter UI dialog.
       */
      void
      setBatchTitle (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& title
      )
      throw () 
      ;


      /**
       * Define the next tab/group title to use. All
       * addRequest subsequent calls will add to this group.
       * Multiple dialog tabs/groups can be defined in this way.
       */
      void
      setGroupName (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& newGroupName
      )
      throw () 
      ;


      /**
       * Define a boolean parameter and its default state.
       * The configured value is always available by
       * portData->getBool(name, ...);
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestBoolean (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ const ::std::string& help,
        /*in*/ const ::std::string& prompt,
        /*in*/ bool bdefault
      )
      throw () 
      ;


      /**
       * Define a int parameter and its default state.
       * The configured value is always available by
       * portData->getInt(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestInt (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ const ::std::string& help,
        /*in*/ const ::std::string& prompt,
        /*in*/ int32_t idefault,
        /*in*/ int32_t low,
        /*in*/ int32_t high
      )
      throw () 
      ;


      /**
       * Define a long parameter and its default state.
       * The configured value is always available by
       * portData->getLong(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestLong (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ const ::std::string& help,
        /*in*/ const ::std::string& prompt,
        /*in*/ int64_t ldefault,
        /*in*/ int64_t low,
        /*in*/ int64_t high
      )
      throw () 
      ;


      /**
       * Define a float parameter and its default state.
       * The configured value is always available by
       * portData->getFloat(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestFloat (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ const ::std::string& help,
        /*in*/ const ::std::string& prompt,
        /*in*/ float fdefault,
        /*in*/ float low,
        /*in*/ float high
      )
      throw () 
      ;


      /**
       * Define a double parameter and its default state.
       * The configured value is always available by
       * portData->getDouble(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestDouble (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ const ::std::string& help,
        /*in*/ const ::std::string& prompt,
        /*in*/ double ddefault,
        /*in*/ double low,
        /*in*/ double high
      )
      throw () 
      ;


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
      addRequestString (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ const ::std::string& help,
        /*in*/ const ::std::string& prompt,
        /*in*/ const ::std::string& sdefault
      )
      throw () 
      ;


      /**
       * Define a new choice for a string parameter.
       *  If no calls to this function are made for a given
       *  name, then any form of string will be acceptable input.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestStringChoice (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& key,
        /*in*/ const ::std::string& choice
      )
      throw () 
      ;


      /**
       * As addRequestStringChoice, but in bulk. 
       */
      void
      addStringChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& key,
        /*in*/ ::sidl::array< ::std::string> choices
      )
      throw () 
      ;


      /**
       * Replace the bounds on the named parameter by a list. 
       */
      void
      addIntChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ ::sidl::array<int32_t> choices
      )
      throw () 
      ;


      /**
       * Replace the bounds on the named parameter by a list. 
       */
      void
      addLongChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ ::sidl::array<int64_t> choices
      )
      throw () 
      ;


      /**
       * Replace the bounds on the named parameter by a list. 
       */
      void
      addFloatChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ ::sidl::array<float> choices
      )
      throw () 
      ;


      /**
       * Replace the bounds on the named parameter by a list. 
       */
      void
      addDoubleChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ ::sidl::array<double> choices
      )
      throw () 
      ;


      /**
       * Replace the bounds on the named parameter by a list. 
       */
      void
      addFcomplexChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ ::sidl::array< ::sidl::fcomplex> choices
      )
      throw () 
      ;


      /**
       * Replace the bounds on the named parameter by a list. 
       */
      void
      addDcomplexChoices (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ const ::std::string& name,
        /*in*/ ::sidl::array< ::sidl::dcomplex> choices
      )
      throw () 
      ;


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
      clearRequests (
        /*in*/ ::gov::cca::TypeMap portData
      )
      throw () 
      ;


      /**
       * Register listener (the component) that wishes to have
       * a chance to change the contents of its ParameterPort
       * just before the parameters TypeMap is read.
       * @param powner a pointer to the listener that will be
       * forgotten when it is no longer needed. 
       */
      void
      registerUpdater (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ ::gov::cca::ports::ParameterGetListener powner
      )
      throw () 
      ;


      /**
       * Register listener (the component) if it wishes to be
       * informed when an parameter is changed via writeConfigurationMap.
       * Listeners are called after values are set.
       */
      void
      registerUpdatedListener (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ ::gov::cca::ports::ParameterSetListener powner
      )
      throw () 
      ;


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
      addParameterPort (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ ::gov::cca::Services svc
      )
      throw () 
      ;


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
      removeParameterPort (
        /*in*/ ::gov::cca::TypeMap portData,
        /*in*/ ::gov::cca::Services svc
      )
      throw () 
      ;

    };  // end class ParameterPortFactory_impl

  } // end namespace ports
} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPortFactory._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPortFactory._hmisc)

#endif
