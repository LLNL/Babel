// 
// File:          decaf_ports_ParameterPort_Impl.hxx
// Symbol:        decaf.ports.ParameterPort-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ports.ParameterPort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_ports_ParameterPort_Impl_hxx
#define included_decaf_ports_ParameterPort_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_ports_ParameterPort_IOR_h
#include "decaf_ports_ParameterPort_IOR.h"
#endif
#ifndef included_decaf_ports_ParameterPort_hxx
#include "decaf_ports_ParameterPort.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_Services_hxx
#include "gov_cca_Services.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_gov_cca_ports_ParameterGetListener_hxx
#include "gov_cca_ports_ParameterGetListener.hxx"
#endif
#ifndef included_gov_cca_ports_ParameterPort_hxx
#include "gov_cca_ports_ParameterPort.hxx"
#endif
#ifndef included_gov_cca_ports_ParameterPortFactory_hxx
#include "gov_cca_ports_ParameterPortFactory.hxx"
#endif
#ifndef included_gov_cca_ports_ParameterSetListener_hxx
#include "gov_cca_ports_ParameterSetListener.hxx"
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


// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._hincludes)
#include <list>
#include <map>
#include <set>
#ifndef included_decaf_TypeMap_hh
#include "decaf_TypeMap.hxx"
#endif
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._hincludes)

namespace decaf { 
  namespace ports { 

    /**
     * Symbol "decaf.ports.ParameterPort" (version 0.8.2)
     */
    class ParameterPort_impl : public virtual ::decaf::ports::ParameterPort 
    // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._inherits)
    // Put additional inheritance here...
    // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._inherits)

    {

    // All data marked protected will be accessable by 
    // descendant Impl classes
    protected:

      bool _wrapped;

      // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._implementation)
      typedef std::map< std::string, std::string> dict_t;
      std::list<std::string> d_entries;

      std::string d_title;
      decaf::TypeMap d_data;
      decaf::TypeMap d_default;
      decaf::TypeMap d_upper;
      decaf::TypeMap d_lower;
      dict_t d_help;
      dict_t d_prompt; 
      std::map< std::string, std::set< std::string > > d_stringChoice;
      std::map< std::string, std::set< int32_t > > d_intChoice;
      std::map< std::string, std::set< int64_t > > d_longChoice;
      std::map< std::string, std::set< float > > d_floatChoice;
      std::map< std::string, std::set< double > > d_doubleChoice;
      std::map< std::string, std::set< sidl::fcomplex, sidl::fcomplex_less > > d_fcomplexChoice;
      std::map< std::string, std::set< sidl::dcomplex, sidl::dcomplex_less > > d_dcomplexChoice;

      // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._implementation)

    public:
      // default constructor, used for data wrapping(required)
      ParameterPort_impl();
      // sidl constructor (required)
      // Note: alternate Skel constructor doesn't call addref()
      // (fixes bug #275)
        ParameterPort_impl( struct decaf_ports_ParameterPort__object * ior ) : 
          StubBase(ior,true), 
        ::gov::cca::Port((ior==NULL) ? NULL : &((*ior).d_gov_cca_port)),
        ::gov::cca::ports::BasicParameterPort((ior==NULL) ? NULL : &((
          *ior).d_gov_cca_ports_basicparameterport)),
        ::gov::cca::ports::ParameterPort((ior==NULL) ? NULL : &((
          *ior).d_gov_cca_ports_parameterport)),
      ::gov::cca::ports::ParameterPortFactory((ior==NULL) ? NULL : &((
        *ior).d_gov_cca_ports_parameterportfactory)) , _wrapped(false) {
        ior->d_data = this;
        _ctor();
      }


      // user defined construction
      void _ctor();

      // virtual destructor (required)
      virtual ~ParameterPort_impl() { _dtor(); }

      // user defined destruction
      void _dtor();

      // true if this object was created by a user newing the impl
      inline bool _isWrapped() {return _wrapped;}

      // static class initializer
      static void _load();

    public:


      /**
       *  Return a TypeMap of runtime configuration parameters. 
       * It is recommended that the map returned be a clone/copy of the
       * a privately held map, not a shared object reference.
       */
      ::gov::cca::TypeMap
      readConfigurationMap_impl() // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;

      /**
       *  Copy the parameter values given in map into the
       * internal map, for those parameters which
       * are already defined by the internal map.
       * The outsider does not get to cause arbitrary
       * keys to be copied into the internal map.
       * @throws gov.cca.CCAException if TypeMap operations fail.
       */
      void
      writeConfigurationMap_impl (
        /* in */::gov::cca::TypeMap& map
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the list of keys in the TypeMap that are
       * for public configuration purposes. Other values found in
       * the TypeMap must not be changed.
       */
      ::sidl::array< ::std::string>
      readConfigurationKeys_impl() ;

      /**
       *  Fetch configuration title. 
       * @return Description of data set.
       */
      ::std::string
      readTitle_impl() ;

      /**
       *  Fetch the names of groups. Each page in a tabbed dialog
       * goes with a group, for example. Group names should be
       * a simple word without whitespace.
       */
      ::sidl::array< ::std::string>
      readGroupNames_impl() ;

      /**
       *  Fetch UI-appropriate name for group.
       * Group name and group title may be the same, if simple.
       * Group title can be complex if desired.
       * @throws gov.cca.CCAException if groupName is unknown.
       */
      ::std::string
      readGroupTitle_impl (
        /* in */const ::std::string& groupName
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch data item keys associated with groupName.
       * Array order will be the same as the order in which
       * the additions were made in the ParameterPortFactory interface.
       * @throws gov.cca.CCAException if groupName is unknown.
       */
      ::sidl::array< ::std::string>
      readGroupKeys_impl (
        /* in */const ::std::string& groupName
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the help string for key.
       * @throws gov.cca.CCAException if key is unknown.
       */
      ::std::string
      readHelp_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the prompt string for key.
       * @throws gov.cca.CCAException if key is unknown.
       */
      ::std::string
      readPrompt_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  By default, bounds are defined except for complex numbers
       * booleans, and strings. On array types, bounds apply
       * element-wise. i.e.  low <= arr[i] <= high for all i.
       * @return true if simple bounds are defined for a key.
       * @throws gov.cca.CCAException if key is unknown.
       */
      bool
      hasBounds_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  By default, lists of choices are not defined for scalars or arrays.
       * Choices are applied element-wise for array types.
       * I.e. (arr[i] IN choice-set) == true  for all i.
       * @return true if choice list is defined for a key.
       * @throws gov.cca.CCAException if key is unknown.
       */
      bool
      hasChoices_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for boolean-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      bool
      readDefaultBoolean_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for string-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::std::string
      readDefaultString_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for integer-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      int32_t
      readDefaultInt_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for long-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      int64_t
      readDefaultLong_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for float-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      float
      readDefaultFloat_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for double-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      double
      readDefaultDouble_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for fcomplex-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::std::complex<float>
      readDefaultFcomplex_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for dcomplex-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::std::complex<double>
      readDefaultDcomplex_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for string-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array< ::std::string>
      readDefaultStringArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for boolean-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<bool>
      readDefaultBooleanArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for integer-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<int32_t>
      readDefaultIntArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for long-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<int64_t>
      readDefaultLongArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for float-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<float>
      readDefaultFloatArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for double-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<double>
      readDefaultDoubleArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for fcomplex-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array< ::sidl::fcomplex>
      readDefaultFcomplexArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for dcomplex-array-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array< ::sidl::dcomplex>
      readDefaultDcomplexArray_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the bounds for integer-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      readBoundsInt_impl (
        /* in */const ::std::string& key,
        /* out */int32_t& low,
        /* out */int32_t& high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the bounds for long-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      readBoundsLong_impl (
        /* in */const ::std::string& key,
        /* out */int64_t& low,
        /* out */int64_t& high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the bounds for float-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      readBoundsFloat_impl (
        /* in */const ::std::string& key,
        /* out */float& low,
        /* out */float& high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the bounds for double-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      readBoundsDouble_impl (
        /* in */const ::std::string& key,
        /* out */double& low,
        /* out */double& high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the choices for string-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array< ::std::string>
      readChoicesString_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the choices for integer-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<int32_t>
      readChoicesInt_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the choices for long-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<int64_t>
      readChoicesLong_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the choices for float-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<float>
      readChoicesFloat_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the default for double-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array<double>
      readChoicesDouble_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the choices for fcomplex-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array< ::sidl::fcomplex>
      readChoicesFcomplex_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Fetch the choices for dcomplex-typed key. 
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      ::sidl::array< ::sidl::dcomplex>
      readChoicesDcomplex_impl (
        /* in */const ::std::string& key
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Initialize the portData for use in a ParameterPort
       * with name portName.
       * More than one such port can be defined.
       * The given string portName will be cached in the TypeMap
       * as the result of this function and must not be changed
       * by the component henceforth.
       * 
       * @param portData the user-supplied TypeMap associated with the port;
       * It is somehow shared between the ParameterPortFactory
       * and the component. The ParameterPortFactory will
       * not read or change preexisting values in portData except those
       * requested via the addRequest functions and those
       * keys starting with the reserved prefix "gcpPPF.".
       * @param portName The name of the ParameterPort to appear in
       * the component. It must not conflict with other port
       * names in the same component. The port name "CONFIG"
       * is recommended if only one ParameterPort is being defined.
       */
      void
      initParameterData_impl (
        /* inout */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& portName
      )
      ;


      /**
       *  Define the window title for the parameter UI dialog.
       */
      void
      setBatchTitle_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& title
      )
      ;


      /**
       *  Define the next tab/group title to use. All
       * addRequest subsequent calls will add to this group.
       * Multiple dialog tabs/groups can be defined in this way.
       * @param newGroupName a one-word name for the group.
       * @param portData the hash-table which we're subsetting
       * with a tab notation.
       */
      void
      setGroupName_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& newGroupName
      )
      ;


      /**
       *  Define the next tab/group title to use. All
       * addRequest subsequent calls will add to this group.
       * Multiple dialog tabs/groups can be defined in this way.
       * @param portData the hash-table which we're subsetting
       * with a tab notation.
       * @param newGroupName a one-word name for the group.
       * @param groupTitle an optional title for the group.
       */
      void
      setGroupName_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& newGroupName,
        /* in */const ::std::string& groupTitle
      )
      ;


      /**
       *  Define a boolean parameter and its default state.
       * The configured value is always available by
       * portData.getBool(name, ...);
       * @throws gov.cca.CCAException if key is known and mistyped.
       */
      void
      addRequestBoolean_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in */const ::std::string& help,
        /* in */const ::std::string& prompt,
        /* in */bool bdefault
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Define a int parameter and its default state.
       * The configured value is always available by
       * portData.getInt(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is known and mistyped.
       */
      void
      addRequestInt_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in */const ::std::string& help,
        /* in */const ::std::string& prompt,
        /* in */int32_t idefault,
        /* in */int32_t low,
        /* in */int32_t high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Define a long parameter and its default state.
       * The configured value is always available by
       * portData.getLong(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestLong_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in */const ::std::string& help,
        /* in */const ::std::string& prompt,
        /* in */int64_t ldefault,
        /* in */int64_t low,
        /* in */int64_t high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Define a float parameter and its default state.
       * The configured value is always available by
       * portData.getFloat(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestFloat_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in */const ::std::string& help,
        /* in */const ::std::string& prompt,
        /* in */float fdefault,
        /* in */float low,
        /* in */float high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Define a double parameter and its default state.
       * The configured value is always available by
       * portData.getDouble(name, ...) and it will be
       * in the range [low, high]. The initially given bounds
       * can be replaced with a choice list later.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestDouble_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in */const ::std::string& help,
        /* in */const ::std::string& prompt,
        /* in */double ddefault,
        /* in */double low,
        /* in */double high
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Define a string parameter and its default state.
       * The configured value is always available by
       * portData.getString(name, ...).
       * If no addRequestStringChoice calls are made, the
       * user input may be any string. If addRequestStringChoice
       * is used, the value will be one among the choices.
       * If addRequestStringChoice is used, deflt must
       * be among the choices defined.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestString_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in */const ::std::string& help,
        /* in */const ::std::string& prompt,
        /* in */const ::std::string& sdefault
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Define a new choice for a string parameter.
       * If no calls to this function are made for a given
       * name, then any form of string will be acceptable input.
       * @throws gov.cca.CCAException if key is unknown or mistyped.
       */
      void
      addRequestStringChoice_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& key,
        /* in */const ::std::string& choice
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  As addRequestStringChoice, but in bulk. 
       */
      void
      addStringChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<string> */::sidl::array< ::std::string>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Replace the bounds on the named parameter by a list. 
       */
      void
      addIntChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<int> */::sidl::array<int32_t>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Replace the bounds on the named parameter by a list. 
       */
      void
      addLongChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<long> */::sidl::array<int64_t>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Replace the bounds on the named parameter by a list. 
       */
      void
      addFloatChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<float> */::sidl::array<float>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Replace the bounds on the named parameter by a list. 
       */
      void
      addDoubleChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<double> */::sidl::array<double>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Replace the bounds on the named parameter by a list. 
       */
      void
      addFcomplexChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Replace the bounds on the named parameter by a list. 
       */
      void
      addDcomplexChoices_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */const ::std::string& name,
        /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& choices
      )
      // throws:
      //    ::gov::cca::CCAException
      //    ::sidl::RuntimeException
      ;


      /**
       *  Clear all previously added requests, titles, groups. After
       * this call, it is as if the ParameterPort has
       * seen initParameterData but otherwise never configured.
       * The values of
       * previously defined parameters (but not bounds, etc)
       * remain in the TypeMap.
       * Typically, this is used only by someone implementing
       * the updateParameterPort function from
       * interface ParameterGetListener.
       */
      void
      clearRequests_impl (
        /* in */::gov::cca::TypeMap& portData
      )
      ;


      /**
       *  
       * Register listener (the component) that wishes to have
       * a chance to change the contents of its ParameterPort
       * just before the parameters TypeMap, portData, is read.
       * @param powner a pointer to the listener that will be
       * forgotten when it is no longer needed. 
       * @param portData the hash-table being accessed.
       */
      void
      registerUpdater_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */::gov::cca::ports::ParameterGetListener& powner
      )
      ;


      /**
       *  Register listener (the component) if it wishes to be
       * informed when an parameter is changed via writeConfigurationMap.
       * Listeners are called after values are set.
       */
      void
      registerUpdatedListener_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */::gov::cca::ports::ParameterSetListener& powner
      )
      ;


      /**
       *  Signal that the ParameterPort is fully defined and should
       * now pop out on the component.
       * The component is free to hold the portData map internally
       * without adding it until some desired time.
       * The Services passed here
       * must be the component's own Services handle.
       * The ParameterPortFactory takes care of addProvidesPort.
       */
      void
      addParameterPort_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */::gov::cca::Services& services
      )
      ;


      /**
       *  Cause a previously defined parameter port to go away.
       * This function should be called at component shutdown
       * (setService(0)) time for any parameter ports that have
       * been added but not yet removed.
       * The ParameterPortFactory takes care of removeProvidesPort.
       * This does not change the parameter values in the
       * TypeMap.
       */
      void
      removeParameterPort_impl (
        /* in */::gov::cca::TypeMap& portData,
        /* in */::gov::cca::Services& services
      )
      ;

    };  // end class ParameterPort_impl

  } // end namespace ports
} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._hmisc)

#endif
