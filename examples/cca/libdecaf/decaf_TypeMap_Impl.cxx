// 
// File:          decaf_TypeMap_Impl.cxx
// Symbol:        decaf.TypeMap-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.TypeMap
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "decaf_TypeMap_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_decaf_TypeMap_hxx
#include "decaf_TypeMap.hxx"
#endif
#ifndef included_gov_cca_Type_hxx
#include "gov_cca_Type.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_gov_cca_TypeMismatchException_hxx
#include "gov_cca_TypeMismatchException.hxx"
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
// DO-NOT-DELETE splicer.begin(decaf.TypeMap._includes)
#include <vector>
#include <algorithm>
#include <cstring>

#include "decaf_TypeMismatchException.hxx"

#include "decaf_Type.hxx"
#include "sidl_String.h"

template<class InputIterator, class OutputIterator>
OutputIterator copy_key( InputIterator first1, InputIterator last1,
			 OutputIterator first2);

// DO-NOT-DELETE splicer.end(decaf.TypeMap._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
decaf::TypeMap_impl::TypeMap_impl() : StubBase(reinterpret_cast< void*>(
  ::decaf::TypeMap::_wrapObj(reinterpret_cast< void*>(this))),false) , _wrapped(
  true){ 
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap._ctor2)
  // Insert-Code-Here {decaf.TypeMap._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(decaf.TypeMap._ctor2)
}

// user defined constructor
void decaf::TypeMap_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(decaf.TypeMap._ctor)
}

// user defined destructor
void decaf::TypeMap_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(decaf.TypeMap._dtor)
}

// static class initializer
void decaf::TypeMap_impl::_load() {
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(decaf.TypeMap._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  getOpaque[]
 */
void*
decaf::TypeMap_impl::getOpaque_impl (
  /* in */const ::std::string& key,
  /* in */void* dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getOpaque)
  return getType( d_key2opaque, key, gov::cca::Type_NoType, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getOpaque)
}

/**
 * Method:  getOpaqueArray[]
 */
::sidl::array<void*>
decaf::TypeMap_impl::getOpaqueArray_impl (
  /* in */const ::std::string& key,
  /* in array<opaque> */::sidl::array<void*>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getOpaqueArray)
  return getType( d_key2opaqueArray, key, gov::cca::Type_NoType, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getOpaqueArray)
}

/**
 * Method:  putOpaque[]
 */
void
decaf::TypeMap_impl::putOpaque_impl (
  /* in */const ::std::string& key,
  /* in */void* value ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putOpaque)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putOpaque)
}

/**
 * Method:  putOpaqueArray[]
 */
void
decaf::TypeMap_impl::putOpaqueArray_impl (
  /* in */const ::std::string& key,
  /* in array<opaque> */::sidl::array<void*>& value ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putOpaqueArray)
  // insert implementation here
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putOpaqueArray)
}

/**
 * Method:  getTypeMap[]
 */
::decaf::TypeMap
decaf::TypeMap_impl::getTypeMap_impl (
  /* in */const ::std::string& key,
  /* in */::decaf::TypeMap& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getTypeMap)
  return getType( d_key2TypeMap, key, gov::cca::Type_NoType, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getTypeMap)
}

/**
 * Method:  getTypeMapArray[]
 */
::sidl::array< ::decaf::TypeMap>
decaf::TypeMap_impl::getTypeMapArray_impl (
  /* in */const ::std::string& key,
  /* in array<decaf.TypeMap> */::sidl::array< ::decaf::TypeMap>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getTypeMapArray)
  return getType( d_key2TypeMapArray, key, gov::cca::Type_NoType, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getTypeMapArray)
}

/**
 * Method:  putTypeMap[]
 */
void
decaf::TypeMap_impl::putTypeMap_impl (
  /* in */const ::std::string& key,
  /* in */::decaf::TypeMap& value ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putTypeMap)
  remove( key );
  d_key2type[key] = gov::cca::Type_NoType;
  d_key2TypeMap[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putTypeMap)
}

/**
 * Method:  putTypeMapArray[]
 */
void
decaf::TypeMap_impl::putTypeMapArray_impl (
  /* in */const ::std::string& key,
  /* in array<decaf.TypeMap> */::sidl::array< ::decaf::TypeMap>& value ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putTypeMapArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_NoType;
  d_key2TypeMapArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putTypeMapArray)
}

/**
 *  Create an exact copy of this Map 
 */
::gov::cca::TypeMap
decaf::TypeMap_impl::cloneTypeMap_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.cloneTypeMap)
  // WARNING: there are some tricks in here that should not be 
  //          used in average Babel programming
  // 1. create a second TypeMap locally
  decaf::TypeMap m = decaf::TypeMap::_create();
  // 2. pry open the guts of the other typemap (don't do this unless you really, 
  //    are sure this is the right thing to do.
  decaf::TypeMap_impl* impl = 
    reinterpret_cast<decaf::TypeMap_impl*>( m._get_ior()->d_data );
  // 3. now copy the low-level details
  impl->d_key2type.insert( d_key2type.begin(), d_key2type.end() );
  impl->d_key2bool.insert( d_key2bool.begin(), d_key2bool.end() );
  impl->d_key2int.insert( d_key2int.begin(), d_key2int.end() );
  impl->d_key2long.insert(  d_key2long.begin(), d_key2long.end() );
  impl->d_key2float.insert( d_key2float.begin(), d_key2float.end() );
  impl->d_key2double.insert( d_key2double.begin(), d_key2double.end() );
  impl->d_key2fcomplex.insert( d_key2fcomplex.begin(), d_key2fcomplex.end() );
  impl->d_key2dcomplex.insert( d_key2dcomplex.begin(), d_key2dcomplex.end() );
  impl->d_key2string.insert( d_key2string.begin(), d_key2string.end() );
  impl->d_key2TypeMap.insert( d_key2TypeMap.begin(), d_key2TypeMap.end() );
  impl->d_key2boolArray.insert( d_key2boolArray.begin(), d_key2boolArray.end() );
  impl->d_key2intArray.insert( d_key2intArray.begin(), d_key2intArray.end() );
  impl->d_key2longArray.insert( d_key2longArray.begin(), d_key2longArray.end() );
  impl->d_key2floatArray.insert( d_key2floatArray.begin(), d_key2floatArray.end() );
  impl->d_key2doubleArray.insert( d_key2doubleArray.begin(), d_key2doubleArray.end() );
  impl->d_key2fcomplexArray.insert( d_key2fcomplexArray.begin(), d_key2fcomplexArray.end() );
  impl->d_key2dcomplexArray.insert( d_key2dcomplexArray.begin(), d_key2dcomplexArray.end() );
  impl->d_key2stringArray.insert( d_key2stringArray.begin(), d_key2stringArray.end() );
  impl->d_key2TypeMapArray.insert( d_key2TypeMapArray.begin(), d_key2TypeMapArray.end() );
  // 4. done now return the thing.
  return m;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.cloneTypeMap)
}

/**
 *  Create a new Map with no key/value associations. 
 */
::gov::cca::TypeMap
decaf::TypeMap_impl::cloneEmpty_impl () 

{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.cloneEmpty)
  return decaf::TypeMap();
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.cloneEmpty)
}

/**
 * Method:  getInt[]
 */
int32_t
decaf::TypeMap_impl::getInt_impl (
  /* in */const ::std::string& key,
  /* in */int32_t dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getInt)
  return getType( d_key2int, key, gov::cca::Type_Int, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getInt)
}

/**
 * Method:  getLong[]
 */
int64_t
decaf::TypeMap_impl::getLong_impl (
  /* in */const ::std::string& key,
  /* in */int64_t dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getLong)
  return getType( d_key2long, key, gov::cca::Type_Long, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getLong)
}

/**
 * Method:  getFloat[]
 */
float
decaf::TypeMap_impl::getFloat_impl (
  /* in */const ::std::string& key,
  /* in */float dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getFloat)
  return getType( d_key2float, key, gov::cca::Type_Float, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getFloat)
}

/**
 * Method:  getDouble[]
 */
double
decaf::TypeMap_impl::getDouble_impl (
  /* in */const ::std::string& key,
  /* in */double dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getDouble)
  return getType( d_key2double, key, gov::cca::Type_Double, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getDouble)
}

/**
 * Method:  getFcomplex[]
 */
::std::complex<float>
decaf::TypeMap_impl::getFcomplex_impl (
  /* in */const ::std::string& key,
  /* in */const ::std::complex<float>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getFcomplex)
  return getType( d_key2fcomplex, key, gov::cca::Type_Fcomplex, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getFcomplex)
}

/**
 * Method:  getDcomplex[]
 */
::std::complex<double>
decaf::TypeMap_impl::getDcomplex_impl (
  /* in */const ::std::string& key,
  /* in */const ::std::complex<double>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getDcomplex)
  return getType( d_key2dcomplex, key, gov::cca::Type_Dcomplex, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getDcomplex)
}

/**
 * Method:  getString[]
 */
::std::string
decaf::TypeMap_impl::getString_impl (
  /* in */const ::std::string& key,
  /* in */const ::std::string& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getString)
  return getType( d_key2string, key, gov::cca::Type_String, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getString)
}

/**
 * Method:  getBool[]
 */
bool
decaf::TypeMap_impl::getBool_impl (
  /* in */const ::std::string& key,
  /* in */bool dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getBool)
  return getType( d_key2bool, key, gov::cca::Type_Bool, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getBool)
}

/**
 * Method:  getIntArray[]
 */
::sidl::array<int32_t>
decaf::TypeMap_impl::getIntArray_impl (
  /* in */const ::std::string& key,
  /* in array<int> */::sidl::array<int32_t>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getIntArray)
  return getType( d_key2intArray, key, gov::cca::Type_IntArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getIntArray)
}

/**
 * Method:  getLongArray[]
 */
::sidl::array<int64_t>
decaf::TypeMap_impl::getLongArray_impl (
  /* in */const ::std::string& key,
  /* in array<long> */::sidl::array<int64_t>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getLongArray)
  return getType( d_key2longArray, key, gov::cca::Type_LongArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getLongArray)
}

/**
 * Method:  getFloatArray[]
 */
::sidl::array<float>
decaf::TypeMap_impl::getFloatArray_impl (
  /* in */const ::std::string& key,
  /* in array<float> */::sidl::array<float>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getFloatArray)
  return getType( d_key2floatArray, key, gov::cca::Type_FloatArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getFloatArray)
}

/**
 * Method:  getDoubleArray[]
 */
::sidl::array<double>
decaf::TypeMap_impl::getDoubleArray_impl (
  /* in */const ::std::string& key,
  /* in array<double> */::sidl::array<double>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getDoubleArray)
  return getType( d_key2doubleArray, key, gov::cca::Type_DoubleArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getDoubleArray)
}

/**
 * Method:  getFcomplexArray[]
 */
::sidl::array< ::sidl::fcomplex>
decaf::TypeMap_impl::getFcomplexArray_impl (
  /* in */const ::std::string& key,
  /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getFcomplexArray)
  return getType( d_key2fcomplexArray, key, gov::cca::Type_FcomplexArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getFcomplexArray)
}

/**
 * Method:  getDcomplexArray[]
 */
::sidl::array< ::sidl::dcomplex>
decaf::TypeMap_impl::getDcomplexArray_impl (
  /* in */const ::std::string& key,
  /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getDcomplexArray)
  return getType( d_key2dcomplexArray, key, gov::cca::Type_DcomplexArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getDcomplexArray)
}

/**
 * Method:  getStringArray[]
 */
::sidl::array< ::std::string>
decaf::TypeMap_impl::getStringArray_impl (
  /* in */const ::std::string& key,
  /* in array<string> */::sidl::array< ::std::string>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getStringArray)
  return getType( d_key2stringArray, key, gov::cca::Type_StringArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getStringArray)
}

/**
 * Method:  getBoolArray[]
 */
::sidl::array<bool>
decaf::TypeMap_impl::getBoolArray_impl (
  /* in */const ::std::string& key,
  /* in array<bool> */::sidl::array<bool>& dflt ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getBoolArray)
  return getType( d_key2boolArray, key, gov::cca::Type_BoolArray, dflt );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getBoolArray)
}

/**
 *  
 * Assign a key and value. Any value previously assigned
 * to the same key will be overwritten so long as it
 * is of the same type. If types conflict, an exception occurs.
 */
void
decaf::TypeMap_impl::putInt_impl (
  /* in */const ::std::string& key,
  /* in */int32_t value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putInt)
  remove( key );
  d_key2type[key] = gov::cca::Type_Int;
  d_key2int[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putInt)
}

/**
 * Method:  putLong[]
 */
void
decaf::TypeMap_impl::putLong_impl (
  /* in */const ::std::string& key,
  /* in */int64_t value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putLong)
  remove( key );
  d_key2type[key] = gov::cca::Type_Long;
  d_key2long[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putLong)
}

/**
 * Method:  putFloat[]
 */
void
decaf::TypeMap_impl::putFloat_impl (
  /* in */const ::std::string& key,
  /* in */float value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putFloat)
  remove( key );
  d_key2type[key] = gov::cca::Type_Float;
  d_key2float[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putFloat)
}

/**
 * Method:  putDouble[]
 */
void
decaf::TypeMap_impl::putDouble_impl (
  /* in */const ::std::string& key,
  /* in */double value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putDouble)
  remove( key );
  d_key2type[key] = gov::cca::Type_Double;
  d_key2double[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putDouble)
}

/**
 * Method:  putFcomplex[]
 */
void
decaf::TypeMap_impl::putFcomplex_impl (
  /* in */const ::std::string& key,
  /* in */const ::std::complex<float>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putFcomplex)
  remove( key );
  d_key2type[key] = gov::cca::Type_Fcomplex;
  d_key2fcomplex[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putFcomplex)
}

/**
 * Method:  putDcomplex[]
 */
void
decaf::TypeMap_impl::putDcomplex_impl (
  /* in */const ::std::string& key,
  /* in */const ::std::complex<double>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putDcomplex)
  remove( key );
  d_key2type[key] = gov::cca::Type_Dcomplex;
  d_key2dcomplex[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putDcomplex)
}

/**
 * Method:  putString[]
 */
void
decaf::TypeMap_impl::putString_impl (
  /* in */const ::std::string& key,
  /* in */const ::std::string& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putString)
  remove( key );
  d_key2type[key] = gov::cca::Type_String;
  d_key2string[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putString)
}

/**
 * Method:  putBool[]
 */
void
decaf::TypeMap_impl::putBool_impl (
  /* in */const ::std::string& key,
  /* in */bool value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putBool)
  remove( key );
  d_key2type[key] = gov::cca::Type_Bool;
  d_key2bool[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putBool)
}

/**
 * Method:  putIntArray[]
 */
void
decaf::TypeMap_impl::putIntArray_impl (
  /* in */const ::std::string& key,
  /* in array<int> */::sidl::array<int32_t>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putIntArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_IntArray;
  d_key2intArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putIntArray)
}

/**
 * Method:  putLongArray[]
 */
void
decaf::TypeMap_impl::putLongArray_impl (
  /* in */const ::std::string& key,
  /* in array<long> */::sidl::array<int64_t>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putLongArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_Long;
  d_key2longArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putLongArray)
}

/**
 * Method:  putFloatArray[]
 */
void
decaf::TypeMap_impl::putFloatArray_impl (
  /* in */const ::std::string& key,
  /* in array<float> */::sidl::array<float>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putFloatArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_FloatArray;
  d_key2floatArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putFloatArray)
}

/**
 * Method:  putDoubleArray[]
 */
void
decaf::TypeMap_impl::putDoubleArray_impl (
  /* in */const ::std::string& key,
  /* in array<double> */::sidl::array<double>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putDoubleArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_DoubleArray;
  d_key2doubleArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putDoubleArray)
}

/**
 * Method:  putFcomplexArray[]
 */
void
decaf::TypeMap_impl::putFcomplexArray_impl (
  /* in */const ::std::string& key,
  /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putFcomplexArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_FcomplexArray;
  d_key2fcomplexArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putFcomplexArray)
}

/**
 * Method:  putDcomplexArray[]
 */
void
decaf::TypeMap_impl::putDcomplexArray_impl (
  /* in */const ::std::string& key,
  /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putDcomplexArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_DcomplexArray;
  d_key2dcomplexArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putDcomplexArray)
}

/**
 * Method:  putStringArray[]
 */
void
decaf::TypeMap_impl::putStringArray_impl (
  /* in */const ::std::string& key,
  /* in array<string> */::sidl::array< ::std::string>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putStringArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_StringArray;
  d_key2stringArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putStringArray)
}

/**
 * Method:  putBoolArray[]
 */
void
decaf::TypeMap_impl::putBoolArray_impl (
  /* in */const ::std::string& key,
  /* in array<bool> */::sidl::array<bool>& value ) 
// throws:
//    ::gov::cca::TypeMismatchException
//    ::sidl::RuntimeException
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.putBoolArray)
  remove( key );
  d_key2type[key] = gov::cca::Type_BoolArray;
  d_key2boolArray[key] = value;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.putBoolArray)
}

/**
 *  Make the key and associated value disappear from the object. 
 */
void
decaf::TypeMap_impl::remove_impl (
  /* in */const ::std::string& key ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.remove)
  switch (typeOf(key)) { 
  case gov::cca::Type_NoType:
    break;
  case gov::cca::Type_Int:
    d_key2int.erase(key);
    break;
  case gov::cca::Type_Long:
    d_key2long.erase(key);
    break;
  case gov::cca::Type_Float:
    d_key2float.erase(key);
    break;
  case gov::cca::Type_Double:
    d_key2double.erase(key);
    break;
  case gov::cca::Type_Fcomplex:
    d_key2fcomplex.erase(key);
    break;
  case gov::cca::Type_Dcomplex:
    d_key2dcomplex.erase(key);
    break;
  case gov::cca::Type_String:
    d_key2string.erase(key);
    break;
  case gov::cca::Type_Bool:
    d_key2bool.erase(key);
    break;
  case gov::cca::Type_IntArray:
    d_key2intArray.erase(key);
    break;
  case gov::cca::Type_FloatArray:
    d_key2floatArray.erase(key);
    break;
  case gov::cca::Type_DoubleArray:
    d_key2doubleArray.erase(key);
    break;
  case gov::cca::Type_FcomplexArray:
    d_key2fcomplexArray.erase(key);
    break;
  case gov::cca::Type_DcomplexArray:
    d_key2dcomplexArray.erase(key);
    break;
  case gov::cca::Type_StringArray:
    d_key2stringArray.erase(key);
    break;
  case gov::cca::Type_BoolArray:
    d_key2boolArray.erase(key);
    break;
  default:
    break;
  }
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.remove)
}

/**
 *  
 * Get all the names associated with a particular type
 * without exposing the data implementation details.  The keys
 * will be returned in an arbitrary order. If type specified is
 * NoType (no specification) all keys of all types are returned.
 */
::sidl::array< ::std::string>
decaf::TypeMap_impl::getAllKeys_impl (
  /* in */::gov::cca::Type t ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.getAllKeys)
  int len;
  std::vector<char*> names;
  switch (t) {
  case gov::cca::Type_NoType:
    len = d_key2type.size();
    copy_key( d_key2type.begin(), d_key2type.end(), names.begin());
    break;
  case gov::cca::Type_Int:
    len = d_key2int.size();
    copy_key( d_key2int.begin(), d_key2int.end(), names.begin());
    break;
  case gov::cca::Type_Long:
    len = d_key2long.size();
    copy_key( d_key2long.begin(), d_key2long.end(), names.begin());
    break;
  case gov::cca::Type_Float:
    len = d_key2float.size();
    copy_key( d_key2float.begin(), d_key2float.end(), names.begin());
    break;
  case gov::cca::Type_Double:
    len = d_key2double.size();
    copy_key( d_key2double.begin(), d_key2double.end(), names.begin());
    break;
  case gov::cca::Type_Fcomplex:
    len = d_key2fcomplex.size();
    copy_key( d_key2fcomplex.begin(), d_key2fcomplex.end(), names.begin());
    break;
  case gov::cca::Type_Dcomplex:
    len = d_key2dcomplex.size();
    copy_key( d_key2dcomplex.begin(), d_key2dcomplex.end(), names.begin());
    break;
  case gov::cca::Type_String:
    len = d_key2string.size();
    copy_key( d_key2string.begin(), d_key2string.end(), names.begin());
    break;
  case gov::cca::Type_Bool:
    len = d_key2bool.size();
    copy_key( d_key2bool.begin(), d_key2bool.end(), names.begin());
    break;
  case gov::cca::Type_IntArray:
    len = d_key2intArray.size();
    copy_key( d_key2intArray.begin(), d_key2intArray.end(), names.begin());
    break;
  case gov::cca::Type_FloatArray:
    len = d_key2floatArray.size();
    copy_key( d_key2floatArray.begin(), d_key2floatArray.end(), names.begin());
    break;
  case gov::cca::Type_DoubleArray:
    len = d_key2doubleArray.size();
    copy_key( d_key2doubleArray.begin(), d_key2doubleArray.end(), names.begin());
    break;
  case gov::cca::Type_FcomplexArray:
    len = d_key2fcomplexArray.size();
    copy_key( d_key2fcomplexArray.begin(), d_key2fcomplexArray.end(), names.begin());
    break;
  case gov::cca::Type_DcomplexArray:
    len = d_key2dcomplexArray.size();
    copy_key( d_key2dcomplexArray.begin(), d_key2dcomplexArray.end(), names.begin());
    break;
  case gov::cca::Type_StringArray:
    len = d_key2stringArray.size();
    copy_key( d_key2stringArray.begin(), d_key2stringArray.end(), names.begin());
    break;
  case gov::cca::Type_BoolArray:
    len = d_key2boolArray.size();
    copy_key( d_key2boolArray.begin(), d_key2boolArray.end(), names.begin());
    break;
  default:
    break;
  }

  sidl::array<std::string> myarray = sidl::array<std::string>::create1d( names.size() );
  int i=0;
  for( std::vector<char*>::iterator it = names.begin(); it != names.end(); ++it, ++i ) { 
    myarray.set( i, (*it) );
  }
  return myarray;
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.getAllKeys)
}

/**
 *  Return true if the key exists in this map 
 */
bool
decaf::TypeMap_impl::hasKey_impl (
  /* in */const ::std::string& key ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.hasKey)
  return ( d_key2type.find(key) != d_key2type.end() );
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.hasKey)
}

/**
 *  Return the type of the value associated with this key 
 */
::gov::cca::Type
decaf::TypeMap_impl::typeOf_impl (
  /* in */const ::std::string& key ) 
{
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap.typeOf)
  if ( d_key2type.find( key ) != d_key2type.end() ) { 
    return d_key2type[key];
  } else { 
    return gov::cca::Type_NoType;
  }
  // DO-NOT-DELETE splicer.end(decaf.TypeMap.typeOf)
}


// DO-NOT-DELETE splicer.begin(decaf.TypeMap._misc)
template<class InputIterator, class OutputIterator>
OutputIterator copy_key( InputIterator first1, InputIterator last1,
			 OutputIterator first2) { 
  for( ; first1 != last1; ++first2, ++first1 ) { 
    *first2 = sidl_String_strdup((*first1).first.c_str());
  }
  return first2;
}

template< class MapType, class DefaultType >
DefaultType 
decaf::TypeMap_impl::getType( MapType& mymap, const std::string& key, 
			      gov::cca::Type gov_cca_t, DefaultType dflt )
  throw ( gov::cca::TypeMismatchException ) 
{ 
  if ( mymap.find( key ) != mymap.end() ) { 
    // found it, we're done
    return mymap[key];
  }
  if ( d_key2type.find(key) != d_key2type.end() ) { 
    // uh,oh!  we asked the wrong type.  Throw mismatch.
    decaf::TypeMismatchException ex;
    ex.initialize(gov_cca_t, d_key2type[key] );
    ex.setNote("actual data is of different type than requested/expected");
    ex.add( __FILE__, __LINE__, "template<...> getType(...)");
    throw ex;
  }
  // missed it, but the key doesn't exist elsewhere either, 
  // so just use the default.
  return dflt;
}

// DO-NOT-DELETE splicer.end(decaf.TypeMap._misc)

