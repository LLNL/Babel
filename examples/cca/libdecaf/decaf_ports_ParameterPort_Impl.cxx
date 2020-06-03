// 
// File:          decaf_ports_ParameterPort_Impl.cxx
// Symbol:        decaf.ports.ParameterPort-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.ports.ParameterPort
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_ports_ParameterPort_Impl.hxx"

// 
// Includes for all method dependencies.
// 
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
#ifndef included_gov_cca_ports_ParameterSetListener_hxx
#include "gov_cca_ports_ParameterSetListener.hxx"
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
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._includes)
#include <algorithm>
#include "decaf_Type.hxx"
#include "decaf_aux.hxx"
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::ports::ParameterPort_impl::ParameterPort_impl() : StubBase(
  reinterpret_cast< void*>(::decaf::ports::ParameterPort::_wrapObj(
  reinterpret_cast< void*>(this))),false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._ctor2)
  // Insert-Code-Here {decaf.ports.ParameterPort._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._ctor2)
}

// user defined constructor
void decaf::ports::ParameterPort_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._ctor)
}

// user defined destructor
void decaf::ports::ParameterPort_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._dtor)
}

// static class initializer
void decaf::ports::ParameterPort_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 *  Return a TypeMap of runtime configuration parameters. 
 * It is recommended that the map returned be a clone/copy of the
 * a privately held map, not a shared object reference.
 */
::gov::cca::TypeMap
decaf::ports::ParameterPort_impl::readConfigurationMap_impl () 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readConfigurationMap)
  return d_data;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readConfigurationMap)
}

/**
 *  Copy the parameter values given in map into the
 * internal map, for those parameters which
 * are already defined by the internal map.
 * The outsider does not get to cause arbitrary
 * keys to be copied into the internal map.
 * @throws gov.cca.CCAException if TypeMap operations fail.
 */
void
decaf::ports::ParameterPort_impl::writeConfigurationMap_impl (
  /* in */::gov::cca::TypeMap& map ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.writeConfigurationMap)
  sidl::array< std::string> keys = d_data.getAllKeys( gov::cca::Type_NoType );
  for (sidl::array<std::string>::iterator i=keys.begin();
       i!=keys.end(); ++i) { 
    if ( map.hasKey(*i) && ( map.typeOf(*i) == d_data.typeOf(*i) ) ) { 
      switch ( d_data.typeOf(*i) ) { 
      case gov::cca::Type_NoType:
	break;
      case gov::cca::Type_Int:
	d_data.putInt( *i, map.getInt( *i, 0 ) );
	break;
      case gov::cca::Type_Long:
	d_data.putLong( *i, map.getLong( *i, 0 ) );
	break;
      case gov::cca::Type_Float:
	d_data.putFloat( *i, map.getFloat( *i, 0.0 ) );
	break;
      case gov::cca::Type_Double:
	d_data.putDouble( *i, map.getDouble( *i, 0.0 ) );
	break;	
      case gov::cca::Type_Fcomplex:
	d_data.putFcomplex( *i, map.getFcomplex( *i, std::complex<float>(0,0) ) );
	break;
      case gov::cca::Type_Dcomplex:
	d_data.putDcomplex( *i, map.getDcomplex( *i, std::complex<double>(0,0) ) );
	break;
      case gov::cca::Type_String:
	d_data.putString( *i, map.getString( *i, "" ) );
	break;
      case gov::cca::Type_Bool:
	d_data.putBool( *i, map.getBool( *i, 0 ) );
	break;
      case gov::cca::Type_IntArray:
	d_data.putIntArray( *i, map.getIntArray( *i, 0 ) );
	break;
      case gov::cca::Type_LongArray:
	d_data.putLongArray( *i, map.getLongArray( *i, 0 ) );
	break;
      case gov::cca::Type_FloatArray:
	d_data.putFloatArray( *i, map.getFloatArray( *i, 0 ) );
	break;
      case gov::cca::Type_DoubleArray:
	d_data.putDoubleArray( *i, map.getDoubleArray( *i, 0 ) );
	break;	
      case gov::cca::Type_FcomplexArray:
	d_data.putFcomplexArray( *i, map.getFcomplexArray( *i, 0 ) );
	break;
      case gov::cca::Type_DcomplexArray:
	d_data.putDcomplexArray( *i, map.getDcomplexArray( *i, 0 ) );
	break;
      case gov::cca::Type_StringArray:
	d_data.putStringArray( *i, map.getStringArray( *i, 0 ) );
	break;
      case gov::cca::Type_BoolArray:
	d_data.putBoolArray( *i, map.getBoolArray( *i, 0 ) );
	break;
      }
    } // end if
  } // end for loop
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.writeConfigurationMap)
}

/**
 *  Fetch the list of keys in the TypeMap that are
 * for public configuration purposes. Other values found in
 * the TypeMap must not be changed.
 */
::sidl::array< ::std::string>
decaf::ports::ParameterPort_impl::readConfigurationKeys_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readConfigurationKeys)
  return  d_data.getAllKeys( gov::cca::Type_NoType );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readConfigurationKeys)
}

/**
 *  Fetch configuration title. 
 * @return Description of data set.
 */
::std::string
decaf::ports::ParameterPort_impl::readTitle_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readTitle)
  return d_title;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readTitle)
}

/**
 *  Fetch the names of groups. Each page in a tabbed dialog
 * goes with a group, for example. Group names should be
 * a simple word without whitespace.
 */
::sidl::array< ::std::string>
decaf::ports::ParameterPort_impl::readGroupNames_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readGroupNames)
  return  d_data.getAllKeys( (gov::cca::Type) decaf::Type_TypeMapT );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readGroupNames)
}

/**
 *  Fetch UI-appropriate name for group.
 * Group name and group title may be the same, if simple.
 * Group title can be complex if desired.
 * @throws gov.cca.CCAException if groupName is unknown.
 */
::std::string
decaf::ports::ParameterPort_impl::readGroupTitle_impl (
  /* in */const ::std::string& groupName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readGroupTitle)
  CCA_ASSERT( d_data.hasKey( groupName ) && d_data.typeOf(groupName) == (gov::cca::Type) decaf::Type_TypeMapT );
  return  d_prompt.count(groupName) ? d_prompt[groupName] : groupName; 
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readGroupTitle)
}

/**
 *  Fetch data item keys associated with groupName.
 * Array order will be the same as the order in which
 * the additions were made in the ParameterPortFactory interface.
 * @throws gov.cca.CCAException if groupName is unknown.
 */
::sidl::array< ::std::string>
decaf::ports::ParameterPort_impl::readGroupKeys_impl (
  /* in */const ::std::string& groupName ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readGroupKeys)
  ::sidl::array< ::std::string> retval;
  decaf::TypeMap group = d_data.getTypeMap( groupName,0 );
  CCA_ASSERT( group._not_nil() );
  return group.getAllKeys( gov::cca::Type_NoType );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readGroupKeys)
}

/**
 *  Fetch the help string for key.
 * @throws gov.cca.CCAException if key is unknown.
 */
::std::string
decaf::ports::ParameterPort_impl::readHelp_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readHelp)
  CCA_ASSERT( d_help.find(key) != d_help.end() );
  return d_help[ key ];
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readHelp)
}

/**
 *  Fetch the prompt string for key.
 * @throws gov.cca.CCAException if key is unknown.
 */
::std::string
decaf::ports::ParameterPort_impl::readPrompt_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readPrompt)
  CCA_ASSERT( d_prompt.find( key ) != d_prompt.end() );
  return d_prompt[ key ];
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readPrompt)
}

/**
 *  By default, bounds are defined except for complex numbers
 * booleans, and strings. On array types, bounds apply
 * element-wise. i.e.  low <= arr[i] <= high for all i.
 * @return true if simple bounds are defined for a key.
 * @throws gov.cca.CCAException if key is unknown.
 */
bool
decaf::ports::ParameterPort_impl::hasBounds_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.hasBounds)
  CCA_ASSERT( d_data.hasKey(key) );
  return d_upper.hasKey(key) && d_lower.hasKey(key);
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.hasBounds)
}

/**
 *  By default, lists of choices are not defined for scalars or arrays.
 * Choices are applied element-wise for array types.
 * I.e. (arr[i] IN choice-set) == true  for all i.
 * @return true if choice list is defined for a key.
 * @throws gov.cca.CCAException if key is unknown.
 */
bool
decaf::ports::ParameterPort_impl::hasChoices_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.hasChoices)
  CCA_ASSERT( d_data.hasKey(key) );
  gov::cca::Type t = d_data.typeOf(key);
  CCA_ASSERT( t == gov::cca::Type_Int || t == gov::cca::Type_Long || t == gov::cca::Type_Float ||
	      t == gov::cca::Type_Double || t == gov::cca::Type_Fcomplex || t == gov::cca::Type_Dcomplex );
  switch (t) { 
  case gov::cca::Type_Int:
    return d_stringChoice.count(key);
  case gov::cca::Type_Long:
    return d_intChoice.count(key);
  case gov::cca::Type_Float:
    return d_floatChoice.count(key);
  case gov::cca::Type_Double:
    return d_doubleChoice.count(key);
  case gov::cca::Type_Fcomplex:
    return d_fcomplexChoice.count(key);
  case gov::cca::Type_Dcomplex:
    return d_dcomplexChoice.count(key);
  case gov::cca::Type_NoType: case gov::cca::Type_String: case gov::cca::Type_Bool:
  case gov::cca::Type_IntArray: case gov::cca::Type_LongArray: case gov::cca::Type_DoubleArray:
  case gov::cca::Type_FcomplexArray: case gov::cca::Type_DcomplexArray: case gov::cca::Type_StringArray:
  case gov::cca::Type_BoolArray:
    CCA_ASSERT( 1 ); // should never get here
    break;
  default:
    CCA_ASSERT( 1 ); // should never get here
    break;    
  }
  return 0;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.hasChoices)
}

/**
 *  Fetch the default for boolean-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
bool
decaf::ports::ParameterPort_impl::readDefaultBoolean_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultBoolean)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Bool );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Bool );
  return d_default.getBool( key, false );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultBoolean)
}

/**
 *  Fetch the default for string-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::std::string
decaf::ports::ParameterPort_impl::readDefaultString_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultString)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_String );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_String );
  return d_default.getString( key, "" );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultString)
}

/**
 *  Fetch the default for integer-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
int32_t
decaf::ports::ParameterPort_impl::readDefaultInt_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultInt)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Int );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Int );
  return d_default.getInt( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultInt)
}

/**
 *  Fetch the default for long-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
int64_t
decaf::ports::ParameterPort_impl::readDefaultLong_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultLong)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Long );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Long );
  return d_default.getLong( key, 0L );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultLong)
}

/**
 *  Fetch the default for float-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
float
decaf::ports::ParameterPort_impl::readDefaultFloat_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultFloat)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Float );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Float );
  return d_default.getFloat( key, 0.0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultFloat)
}

/**
 *  Fetch the default for double-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
double
decaf::ports::ParameterPort_impl::readDefaultDouble_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultDouble)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Double );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Double );
  return d_default.getDouble( key, 0.0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultDouble)
}

/**
 *  Fetch the default for fcomplex-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::std::complex<float>
decaf::ports::ParameterPort_impl::readDefaultFcomplex_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultFcomplex)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Fcomplex );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Fcomplex );
  return d_default.getFcomplex( key, std::complex<float>(0.0,0.0) );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultFcomplex)
}

/**
 *  Fetch the default for dcomplex-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::std::complex<double>
decaf::ports::ParameterPort_impl::readDefaultDcomplex_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultDcomplex)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Dcomplex );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_Dcomplex );
  return d_default.getDcomplex( key, std::complex<double>(0.0,0.0) );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultDcomplex)
}

/**
 *  Fetch the default for string-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array< ::std::string>
decaf::ports::ParameterPort_impl::readDefaultStringArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultStringArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_StringArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_StringArray );
  return d_default.getStringArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultStringArray)
}

/**
 *  Fetch the default for boolean-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<bool>
decaf::ports::ParameterPort_impl::readDefaultBooleanArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultBooleanArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_BoolArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_BoolArray );
  return d_default.getBoolArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultBooleanArray)
}

/**
 *  Fetch the default for integer-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<int32_t>
decaf::ports::ParameterPort_impl::readDefaultIntArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultIntArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_IntArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_IntArray );
  return d_default.getIntArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultIntArray)
}

/**
 *  Fetch the default for long-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<int64_t>
decaf::ports::ParameterPort_impl::readDefaultLongArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultLongArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_LongArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_LongArray );
  return d_default.getLongArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultLongArray)
}

/**
 *  Fetch the default for float-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<float>
decaf::ports::ParameterPort_impl::readDefaultFloatArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultFloatArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_FloatArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_FloatArray );
  return d_default.getFloatArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultFloatArray)
}

/**
 *  Fetch the default for double-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<double>
decaf::ports::ParameterPort_impl::readDefaultDoubleArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultDoubleArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_DoubleArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_DoubleArray );
  return d_default.getDoubleArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultDoubleArray)
}

/**
 *  Fetch the default for fcomplex-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array< ::sidl::fcomplex>
decaf::ports::ParameterPort_impl::readDefaultFcomplexArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultFcomplexArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_FcomplexArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_FcomplexArray );
  return d_default.getFcomplexArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultFcomplexArray)
}

/**
 *  Fetch the default for dcomplex-array-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array< ::sidl::dcomplex>
decaf::ports::ParameterPort_impl::readDefaultDcomplexArray_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readDefaultDcomplexArray)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_DcomplexArray );
  CCA_ASSERT( d_default.hasKey(key) );
  CCA_ASSERT( d_default.typeOf(key)==gov::cca::Type_DcomplexArray );
  return d_default.getDcomplexArray( key, 0 );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readDefaultDcomplexArray)
}

/**
 *  Fetch the bounds for integer-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::readBoundsInt_impl (
  /* in */const ::std::string& key,
  /* out */int32_t& low,
  /* out */int32_t& high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readBoundsInt)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Int );
  CCA_ASSERT( d_upper.hasKey(key) );
  CCA_ASSERT( d_upper.typeOf(key)==gov::cca::Type_Int );
  CCA_ASSERT( d_lower.hasKey(key) );
  CCA_ASSERT( d_lower.typeOf(key)==gov::cca::Type_Int );
  low  = d_lower.getInt(key,0);
  high = d_upper.getInt(key,0);
  return;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readBoundsInt)
}

/**
 *  Fetch the bounds for long-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::readBoundsLong_impl (
  /* in */const ::std::string& key,
  /* out */int64_t& low,
  /* out */int64_t& high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readBoundsLong)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Long );
  CCA_ASSERT( d_upper.hasKey(key) );
  CCA_ASSERT( d_upper.typeOf(key)==gov::cca::Type_Long );
  CCA_ASSERT( d_lower.hasKey(key) );
  CCA_ASSERT( d_lower.typeOf(key)==gov::cca::Type_Long );
  low  = d_lower.getLong(key,0);
  high = d_upper.getLong(key,0);
  return;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readBoundsLong)
}

/**
 *  Fetch the bounds for float-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::readBoundsFloat_impl (
  /* in */const ::std::string& key,
  /* out */float& low,
  /* out */float& high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readBoundsFloat)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Float );
  CCA_ASSERT( d_upper.hasKey(key) );
  CCA_ASSERT( d_upper.typeOf(key)==gov::cca::Type_Float );
  CCA_ASSERT( d_lower.hasKey(key) );
  CCA_ASSERT( d_lower.typeOf(key)==gov::cca::Type_Float );
  low  = d_lower.getFloat(key,0);
  high = d_upper.getFloat(key,0);
  return;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readBoundsFloat)
}

/**
 *  Fetch the bounds for double-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::readBoundsDouble_impl (
  /* in */const ::std::string& key,
  /* out */double& low,
  /* out */double& high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readBoundsDouble)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Double );
  CCA_ASSERT( d_upper.hasKey(key) );
  CCA_ASSERT( d_upper.typeOf(key)==gov::cca::Type_Double );
  CCA_ASSERT( d_lower.hasKey(key) );
  CCA_ASSERT( d_lower.typeOf(key)==gov::cca::Type_Double );
  low  = d_lower.getDouble(key,0);
  high = d_upper.getDouble(key,0);
  return;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readBoundsDouble)
}

/**
 *  Fetch the choices for string-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array< ::std::string>
decaf::ports::ParameterPort_impl::readChoicesString_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesString)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_String );
  CCA_ASSERT( d_stringChoice.count( key ) );

  std::vector< std::string > tmp;
  copy( d_stringChoice[key].begin(), d_stringChoice[key].end(), back_inserter(tmp) );
  sidl::array< std::string > choices = sidl::array< std::string>::create1d( tmp.size() ) ;
  for (unsigned int i=0; i<tmp.size(); ++i ) { 
    choices.set(i,tmp[i]);
  }
  return choices;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesString)
}

/**
 *  Fetch the choices for integer-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<int32_t>
decaf::ports::ParameterPort_impl::readChoicesInt_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesInt)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Int );
  CCA_ASSERT( d_intChoice.count(key) );

  std::vector< int32_t > tmp;
  copy( d_intChoice[key].begin(), d_intChoice[key].end(), back_inserter(tmp) );
  sidl::array< int32_t > choices = sidl::array< int32_t >::create1d( tmp.size());
  for ( unsigned int i=0; i<tmp.size(); ++i ) { 
    choices.set(i,tmp[i]);
  }
  return choices;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesInt)
}

/**
 *  Fetch the choices for long-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<int64_t>
decaf::ports::ParameterPort_impl::readChoicesLong_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesLong)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Long );
  CCA_ASSERT( d_longChoice.count(key) );

  std::vector< int64_t > tmp;
  copy( d_longChoice[key].begin(), d_longChoice[key].end(), back_inserter(tmp) );
  sidl::array< int64_t > choices = sidl::array< int64_t >::create1d( tmp.size());
  for ( unsigned int i=0; i<tmp.size(); ++i ) { 
    choices.set(i,tmp[i]);
  }
  return choices;

  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesLong)
}

/**
 *  Fetch the choices for float-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<float>
decaf::ports::ParameterPort_impl::readChoicesFloat_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesFloat)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Float );
  CCA_ASSERT( d_floatChoice.count(key) );

  std::vector< float > tmp;
  copy( d_floatChoice[key].begin(), d_floatChoice[key].end(), back_inserter(tmp) );
  sidl::array< float > choices = sidl::array< float >::create1d( tmp.size() );
  for ( unsigned int i=0; i<tmp.size(); ++i ) { 
    choices.set(i,tmp[i]);
  }
  return choices;

  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesFloat)
}

/**
 *  Fetch the default for double-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array<double>
decaf::ports::ParameterPort_impl::readChoicesDouble_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesDouble)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Double );
  CCA_ASSERT( d_doubleChoice.count(key) );

  std::vector< double > tmp;
  copy( d_doubleChoice[key].begin(), d_doubleChoice[key].end(), back_inserter(tmp) );
  sidl::array< double > choices = sidl::array< double >::create1d( tmp.size());
  for ( unsigned int i=0; i<tmp.size(); ++i ) { 
    choices.set(i,tmp[i]);
  }
  return choices;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesDouble)
}

/**
 *  Fetch the choices for fcomplex-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array< ::sidl::fcomplex>
decaf::ports::ParameterPort_impl::readChoicesFcomplex_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesFcomplex)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Fcomplex );
  CCA_ASSERT( d_fcomplexChoice.count(key) );

  std::vector< sidl::fcomplex > tmp;
  copy( d_fcomplexChoice[key].begin(), d_fcomplexChoice[key].end(), back_inserter(tmp) );
  sidl::array< sidl::fcomplex > choices = sidl::array< sidl::fcomplex >::create1d( tmp.size() );
  for ( unsigned int i=0; i<tmp.size(); ++i ) { 
    choices.set(i,tmp[i]);
  }
  return choices;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesFcomplex)
}

/**
 *  Fetch the choices for dcomplex-typed key. 
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
::sidl::array< ::sidl::dcomplex>
decaf::ports::ParameterPort_impl::readChoicesDcomplex_impl (
  /* in */const ::std::string& key ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.readChoicesDcomplex)
  CCA_ASSERT( d_data.hasKey(key) );
  CCA_ASSERT( d_data.typeOf(key)==gov::cca::Type_Dcomplex );
  CCA_ASSERT( d_dcomplexChoice.count(key));

  std::vector< sidl::dcomplex > tmp;
  copy( d_dcomplexChoice[key].begin(), d_dcomplexChoice[key].end(), back_inserter(tmp) );
  sidl::array< sidl::dcomplex > choices = sidl::array< sidl::dcomplex >::create1d( tmp.size() );
  for( unsigned int i=0; i<tmp.size(); ++i ) {
    choices.set(i,tmp[i]);
  }
  return choices;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.readChoicesDcomplex)
}

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
decaf::ports::ParameterPort_impl::initParameterData_impl (
  /* inout */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& portName ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.initParameterData)
  if ( portData._is_nil() ) { 
    portData = decaf::TypeMap::_create();
  }
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.initParameterData)
}

/**
 *  Define the window title for the parameter UI dialog.
 */
void
decaf::ports::ParameterPort_impl::setBatchTitle_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& title ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.setBatchTitle)
  d_title = title;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.setBatchTitle)
}

/**
 *  Define the next tab/group title to use. All
 * addRequest subsequent calls will add to this group.
 * Multiple dialog tabs/groups can be defined in this way.
 * @param newGroupName a one-word name for the group.
 * @param portData the hash-table which we're subsetting
 * with a tab notation.
 */
void
decaf::ports::ParameterPort_impl::setGroupName_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& newGroupName ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.setGroupName)

  CCA_ASSERT( (!d_data.hasKey( newGroupName )) || d_data.typeOf(newGroupName)==(gov::cca::Type)decaf::Type_TypeMapT)
  if ( std::find( d_entries.begin(), d_entries.end(), newGroupName ) == d_entries.end() ) { 
    d_entries.push_back( newGroupName );
  }
  d_data.putTypeMap(newGroupName, decaf::TypeMap::_create() );

  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.setGroupName)
}

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
decaf::ports::ParameterPort_impl::setGroupName_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& newGroupName,
  /* in */const ::std::string& groupTitle ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.setGroupNameAndTitle)

  CCA_ASSERT( (!d_data.hasKey( newGroupName )) || d_data.typeOf(newGroupName)==(gov::cca::Type)decaf::Type_TypeMapT)
  if ( std::find( d_entries.begin(), d_entries.end(), newGroupName ) == d_entries.end() ) { 
    d_entries.push_back( newGroupName );
  }
  d_data.putTypeMap(newGroupName, decaf::TypeMap::_create() );
  d_prompt[newGroupName]=groupTitle;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.setGroupNameAndTitle)
}

/**
 *  Define a boolean parameter and its default state.
 * The configured value is always available by
 * portData.getBool(name, ...);
 * @throws gov.cca.CCAException if key is known and mistyped.
 */
void
decaf::ports::ParameterPort_impl::addRequestBoolean_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in */const ::std::string& help,
  /* in */const ::std::string& prompt,
  /* in */bool bdefault ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestBoolean)
  CCA_ASSERT( !d_data.hasKey( name ));
  d_data.putBool( name, bdefault );
  d_default.putBool( name, bdefault );
  d_help[name]=help;
  d_prompt[name]=prompt;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestBoolean)
}

/**
 *  Define a int parameter and its default state.
 * The configured value is always available by
 * portData.getInt(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is known and mistyped.
 */
void
decaf::ports::ParameterPort_impl::addRequestInt_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in */const ::std::string& help,
  /* in */const ::std::string& prompt,
  /* in */int32_t idefault,
  /* in */int32_t low,
  /* in */int32_t high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestInt)
  CCA_ASSERT( !d_data.hasKey( name ));
  CCA_ASSERT( idefault >= low );
  CCA_ASSERT( idefault <= high );
  d_data.putInt( name, idefault );
  d_default.putInt( name, idefault );
  d_lower.putInt(name,low);
  d_upper.putInt(name,high);
  d_help[name]=help;
  d_prompt[name]=prompt;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestInt)
}

/**
 *  Define a long parameter and its default state.
 * The configured value is always available by
 * portData.getLong(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::addRequestLong_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in */const ::std::string& help,
  /* in */const ::std::string& prompt,
  /* in */int64_t ldefault,
  /* in */int64_t low,
  /* in */int64_t high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestLong)
  CCA_ASSERT( !d_data.hasKey( name ));
  CCA_ASSERT( ldefault >= low );
  CCA_ASSERT( ldefault <= high );
  d_data.putLong( name, ldefault );
  d_default.putLong( name, ldefault );
  d_lower.putLong(name,low);
  d_upper.putLong(name,high);
  d_help[name]=help;
  d_prompt[name]=prompt;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestLong)
}

/**
 *  Define a float parameter and its default state.
 * The configured value is always available by
 * portData.getFloat(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::addRequestFloat_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in */const ::std::string& help,
  /* in */const ::std::string& prompt,
  /* in */float fdefault,
  /* in */float low,
  /* in */float high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestFloat)
  CCA_ASSERT( !d_data.hasKey( name ));
  CCA_ASSERT( fdefault >= low );
  CCA_ASSERT( fdefault <= high );
  d_data.putFloat( name, fdefault );
  d_default.putFloat( name, fdefault );
  d_lower.putFloat(name,low);
  d_upper.putFloat(name,high);
  d_help[name]=help;
  d_prompt[name]=prompt;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestFloat)
}

/**
 *  Define a double parameter and its default state.
 * The configured value is always available by
 * portData.getDouble(name, ...) and it will be
 * in the range [low, high]. The initially given bounds
 * can be replaced with a choice list later.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::addRequestDouble_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in */const ::std::string& help,
  /* in */const ::std::string& prompt,
  /* in */double ddefault,
  /* in */double low,
  /* in */double high ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestDouble)
  CCA_ASSERT( !d_data.hasKey( name ));
  CCA_ASSERT( ddefault >= low );
  CCA_ASSERT( ddefault <= high );
  d_data.putDouble( name, ddefault );
  d_default.putDouble( name, ddefault );
  d_lower.putDouble(name,low);
  d_upper.putDouble(name,high);
  d_help[name]=help;
  d_prompt[name]=prompt;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestDouble)
}

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
decaf::ports::ParameterPort_impl::addRequestString_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in */const ::std::string& help,
  /* in */const ::std::string& prompt,
  /* in */const ::std::string& sdefault ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestString)
  CCA_ASSERT( !d_data.hasKey( name ));
  d_data.putString( name, sdefault );
  d_default.putString( name, sdefault );
  d_help[name]=help;
  d_prompt[name]=prompt;
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestString)
}

/**
 *  Define a new choice for a string parameter.
 * If no calls to this function are made for a given
 * name, then any form of string will be acceptable input.
 * @throws gov.cca.CCAException if key is unknown or mistyped.
 */
void
decaf::ports::ParameterPort_impl::addRequestStringChoice_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& key,
  /* in */const ::std::string& choice ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addRequestStringChoice)
  CCA_ASSERT( d_data.hasKey( key ));
  CCA_ASSERT( d_data.typeOf( key ) == gov::cca::Type_String );
  d_stringChoice[key].insert(choice);
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addRequestStringChoice)
}

/**
 *  As addRequestStringChoice, but in bulk. 
 */
void
decaf::ports::ParameterPort_impl::addStringChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<string> */::sidl::array< ::std::string>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addStringChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_String );
  d_stringChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addStringChoices)
}

/**
 *  Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPort_impl::addIntChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<int> */::sidl::array<int32_t>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addIntChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_Int );
  if ( d_upper.hasKey(name) ) { 
    d_upper.remove(name);
  } 
  if ( d_lower.hasKey(name) ) { 
    d_lower.remove(name);
  }
  d_intChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addIntChoices)
}

/**
 *  Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPort_impl::addLongChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<long> */::sidl::array<int64_t>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addLongChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_Long );
  if ( d_upper.hasKey(name) ) { 
    d_upper.remove(name);
  } 
  if ( d_lower.hasKey(name) ) { 
    d_lower.remove(name);
  }
  d_longChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addLongChoices)
}

/**
 *  Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPort_impl::addFloatChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<float> */::sidl::array<float>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addFloatChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_Float );
  if ( d_upper.hasKey(name) ) { 
    d_upper.remove(name);
  } 
  if ( d_lower.hasKey(name) ) { 
    d_lower.remove(name);
  }
  d_floatChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addFloatChoices)
}

/**
 *  Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPort_impl::addDoubleChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<double> */::sidl::array<double>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addDoubleChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_Double );
  if ( d_upper.hasKey(name) ) { 
    d_upper.remove(name);
  } 
  if ( d_lower.hasKey(name) ) { 
    d_lower.remove(name);
  }
  d_doubleChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addDoubleChoices)
}

/**
 *  Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPort_impl::addFcomplexChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addFcomplexChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_Fcomplex );
  if ( d_upper.hasKey(name) ) { 
    d_upper.remove(name);
  } 
  if ( d_lower.hasKey(name) ) { 
    d_lower.remove(name);
  }
  d_fcomplexChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addFcomplexChoices)
}

/**
 *  Replace the bounds on the named parameter by a list. 
 */
void
decaf::ports::ParameterPort_impl::addDcomplexChoices_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */const ::std::string& name,
  /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& choices ) 
// throws:
//    ::gov::cca::CCAException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addDcomplexChoices)
  CCA_ASSERT( d_data.hasKey( name ));
  CCA_ASSERT( d_data.typeOf( name ) == gov::cca::Type_Dcomplex );
  if ( d_upper.hasKey(name) ) { 
    d_upper.remove(name);
  } 
  if ( d_lower.hasKey(name) ) { 
    d_lower.remove(name);
  }
  d_dcomplexChoice[name].insert( choices.begin(), choices.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addDcomplexChoices)
}

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
decaf::ports::ParameterPort_impl::clearRequests_impl (
  /* in */::gov::cca::TypeMap& portData ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.clearRequests)
  d_title="";
  d_data = decaf::TypeMap::_create();
  d_default = decaf::TypeMap::_create();
  d_upper = decaf::TypeMap::_create();
  d_lower = decaf::TypeMap::_create();
  d_help.erase( d_help.begin(), d_help.end());
  d_prompt.erase( d_prompt.begin(), d_prompt.end() );
  d_stringChoice.erase( d_stringChoice.begin(), d_stringChoice.end() );
  d_intChoice.erase( d_intChoice.begin(), d_intChoice.end() );
  d_longChoice.erase( d_longChoice.begin(), d_longChoice.end() );
  d_floatChoice.erase( d_floatChoice.begin(), d_floatChoice.end() );
  d_doubleChoice.erase( d_doubleChoice.begin(), d_doubleChoice.end() );
  d_fcomplexChoice.erase( d_fcomplexChoice.begin(), d_fcomplexChoice.end() );
  d_dcomplexChoice.erase( d_dcomplexChoice.begin(), d_dcomplexChoice.end() );
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.clearRequests)
}

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
decaf::ports::ParameterPort_impl::registerUpdater_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */::gov::cca::ports::ParameterGetListener& powner ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.registerUpdater)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.registerUpdater)
}

/**
 *  Register listener (the component) if it wishes to be
 * informed when an parameter is changed via writeConfigurationMap.
 * Listeners are called after values are set.
 */
void
decaf::ports::ParameterPort_impl::registerUpdatedListener_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */::gov::cca::ports::ParameterSetListener& powner ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.registerUpdatedListener)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.registerUpdatedListener)
}

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
decaf::ports::ParameterPort_impl::addParameterPort_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */::gov::cca::Services& services ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.addParameterPort)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.addParameterPort)
}

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
decaf::ports::ParameterPort_impl::removeParameterPort_impl (
  /* in */::gov::cca::TypeMap& portData,
  /* in */::gov::cca::Services& services ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort.removeParameterPort)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort.removeParameterPort)
}


// DO-NOT-DELETE splicer.begin(decaf.ports.ParameterPort._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(decaf.ports.ParameterPort._misc)

