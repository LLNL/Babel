// 
// File:          decaf_TypeMap_Impl.hxx
// Symbol:        decaf.TypeMap-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.TypeMap
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_TypeMap_Impl_hxx
#define included_decaf_TypeMap_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_TypeMap_IOR_h
#include "decaf_TypeMap_IOR.h"
#endif
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


// DO-NOT-DELETE splicer.begin(decaf.TypeMap._hincludes)
#include <map>
#include <utility> // need pair<T,U>
// DO-NOT-DELETE splicer.end(decaf.TypeMap._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.TypeMap" (version 0.8.2)
   */
  class TypeMap_impl : public virtual ::decaf::TypeMap 
  // DO-NOT-DELETE splicer.begin(decaf.TypeMap._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.TypeMap._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.TypeMap._implementation)
    // first define the types
  public:
    typedef std::map<std::string, gov::cca::Type> key2type_t ;
    typedef std::map<std::string, bool > key2bool_t ;
    typedef std::map<std::string, int32_t > key2int_t ;
    typedef std::map<std::string, int64_t > key2long_t ;
    typedef std::map<std::string, float > key2float_t ;
    typedef std::map<std::string, double > key2double_t ;
    typedef std::map<std::string, std::complex<float> > key2fcomplex_t ;
    typedef std::map<std::string, std::complex<double> > key2dcomplex_t ;
    typedef std::map<std::string, std::string> key2string_t ;
    typedef std::map<std::string, sidl::opaque> key2opaque_t;
    typedef std::map<std::string, decaf::TypeMap> key2TypeMap_t ;

    typedef std::map<std::string, sidl::array<bool> > key2boolArray_t ;
    typedef std::map<std::string, sidl::array<int32_t> > key2intArray_t ;
    typedef std::map<std::string, sidl::array<int64_t> > key2longArray_t ;
    typedef std::map<std::string, sidl::array<float> > key2floatArray_t ;
    typedef std::map<std::string, sidl::array<double> > key2doubleArray_t ;
    typedef std::map<std::string, sidl::array<sidl::fcomplex> > key2fcomplexArray_t ;
    typedef std::map<std::string, sidl::array<sidl::dcomplex> > key2dcomplexArray_t ;
    typedef std::map<std::string, sidl::array<sidl::string > > key2stringArray_t ;    
    typedef std::map<std::string, sidl::array<sidl::opaque> > key2opaqueArray_t;
    typedef std::map<std::string, sidl::array<decaf::TypeMap> > key2TypeMapArray_t ;    

  private:
    // now declare the data members
    key2type_t           d_key2type;
    key2bool_t           d_key2bool;
    key2int_t            d_key2int;
    key2long_t           d_key2long;
    key2float_t          d_key2float;
    key2double_t         d_key2double;
    key2fcomplex_t       d_key2fcomplex;
    key2dcomplex_t       d_key2dcomplex;
    key2string_t         d_key2string;
    key2opaque_t         d_key2opaque;
    key2TypeMap_t        d_key2TypeMap;
                        
    key2boolArray_t      d_key2boolArray;
    key2intArray_t       d_key2intArray;
    key2longArray_t      d_key2longArray;
    key2floatArray_t     d_key2floatArray;
    key2doubleArray_t    d_key2doubleArray;
    key2fcomplexArray_t  d_key2fcomplexArray;
    key2dcomplexArray_t  d_key2dcomplexArray;
    key2stringArray_t    d_key2stringArray;    
    key2opaqueArray_t    d_key2opaqueArray;    
    key2TypeMapArray_t   d_key2TypeMapArray;    

    template< class MapType, class DefaultType >
    DefaultType getType( MapType& mymap, const std::string& key, 
			 gov::cca::Type gov_cca_t, DefaultType dflt ) 
      throw (gov::cca::TypeMismatchException); 

    // DO-NOT-DELETE splicer.end(decaf.TypeMap._implementation)

  public:
    // default constructor, used for data wrapping(required)
    TypeMap_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      TypeMap_impl( struct decaf_TypeMap__object * ior ) : StubBase(ior,true), 
    ::gov::cca::TypeMap((ior==NULL) ? NULL : &((*ior).d_gov_cca_typemap)) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~TypeMap_impl() { _dtor(); }

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
    void*
    getOpaque_impl (
      /* in */const ::std::string& key,
      /* in */void* dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<void*>
    getOpaqueArray_impl (
      /* in */const ::std::string& key,
      /* in array<opaque> */::sidl::array<void*>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putOpaque_impl (
      /* in */const ::std::string& key,
      /* in */void* value
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    putOpaqueArray_impl (
      /* in */const ::std::string& key,
      /* in array<opaque> */::sidl::array<void*>& value
    )
    ;

    /**
     * user defined non-static method.
     */
    ::decaf::TypeMap
    getTypeMap_impl (
      /* in */const ::std::string& key,
      /* in */::decaf::TypeMap& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array< ::decaf::TypeMap>
    getTypeMapArray_impl (
      /* in */const ::std::string& key,
      /* in array<decaf.TypeMap> */::sidl::array< ::decaf::TypeMap>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putTypeMap_impl (
      /* in */const ::std::string& key,
      /* in */::decaf::TypeMap& value
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    putTypeMapArray_impl (
      /* in */const ::std::string& key,
      /* in array<decaf.TypeMap> */::sidl::array< ::decaf::TypeMap>& value
    )
    ;


    /**
     *  Create an exact copy of this Map 
     */
    ::gov::cca::TypeMap
    cloneTypeMap_impl() ;

    /**
     *  Create a new Map with no key/value associations. 
     */
    ::gov::cca::TypeMap
    cloneEmpty_impl() ;
    /**
     * user defined non-static method.
     */
    int32_t
    getInt_impl (
      /* in */const ::std::string& key,
      /* in */int32_t dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    int64_t
    getLong_impl (
      /* in */const ::std::string& key,
      /* in */int64_t dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    float
    getFloat_impl (
      /* in */const ::std::string& key,
      /* in */float dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    double
    getDouble_impl (
      /* in */const ::std::string& key,
      /* in */double dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<float>
    getFcomplex_impl (
      /* in */const ::std::string& key,
      /* in */const ::std::complex<float>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::std::complex<double>
    getDcomplex_impl (
      /* in */const ::std::string& key,
      /* in */const ::std::complex<double>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::std::string
    getString_impl (
      /* in */const ::std::string& key,
      /* in */const ::std::string& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    bool
    getBool_impl (
      /* in */const ::std::string& key,
      /* in */bool dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<int32_t>
    getIntArray_impl (
      /* in */const ::std::string& key,
      /* in array<int> */::sidl::array<int32_t>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<int64_t>
    getLongArray_impl (
      /* in */const ::std::string& key,
      /* in array<long> */::sidl::array<int64_t>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<float>
    getFloatArray_impl (
      /* in */const ::std::string& key,
      /* in array<float> */::sidl::array<float>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<double>
    getDoubleArray_impl (
      /* in */const ::std::string& key,
      /* in array<double> */::sidl::array<double>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array< ::sidl::fcomplex>
    getFcomplexArray_impl (
      /* in */const ::std::string& key,
      /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array< ::sidl::dcomplex>
    getDcomplexArray_impl (
      /* in */const ::std::string& key,
      /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array< ::std::string>
    getStringArray_impl (
      /* in */const ::std::string& key,
      /* in array<string> */::sidl::array< ::std::string>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    ::sidl::array<bool>
    getBoolArray_impl (
      /* in */const ::std::string& key,
      /* in array<bool> */::sidl::array<bool>& dflt
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Assign a key and value. Any value previously assigned
     * to the same key will be overwritten so long as it
     * is of the same type. If types conflict, an exception occurs.
     */
    void
    putInt_impl (
      /* in */const ::std::string& key,
      /* in */int32_t value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putLong_impl (
      /* in */const ::std::string& key,
      /* in */int64_t value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putFloat_impl (
      /* in */const ::std::string& key,
      /* in */float value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putDouble_impl (
      /* in */const ::std::string& key,
      /* in */double value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putFcomplex_impl (
      /* in */const ::std::string& key,
      /* in */const ::std::complex<float>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putDcomplex_impl (
      /* in */const ::std::string& key,
      /* in */const ::std::complex<double>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putString_impl (
      /* in */const ::std::string& key,
      /* in */const ::std::string& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putBool_impl (
      /* in */const ::std::string& key,
      /* in */bool value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putIntArray_impl (
      /* in */const ::std::string& key,
      /* in array<int> */::sidl::array<int32_t>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putLongArray_impl (
      /* in */const ::std::string& key,
      /* in array<long> */::sidl::array<int64_t>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putFloatArray_impl (
      /* in */const ::std::string& key,
      /* in array<float> */::sidl::array<float>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putDoubleArray_impl (
      /* in */const ::std::string& key,
      /* in array<double> */::sidl::array<double>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putFcomplexArray_impl (
      /* in */const ::std::string& key,
      /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putDcomplexArray_impl (
      /* in */const ::std::string& key,
      /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putStringArray_impl (
      /* in */const ::std::string& key,
      /* in array<string> */::sidl::array< ::std::string>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;

    /**
     * user defined non-static method.
     */
    void
    putBoolArray_impl (
      /* in */const ::std::string& key,
      /* in array<bool> */::sidl::array<bool>& value
    )
    // throws:
    //    ::gov::cca::TypeMismatchException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Make the key and associated value disappear from the object. 
     */
    void
    remove_impl (
      /* in */const ::std::string& key
    )
    ;


    /**
     *  
     * Get all the names associated with a particular type
     * without exposing the data implementation details.  The keys
     * will be returned in an arbitrary order. If type specified is
     * NoType (no specification) all keys of all types are returned.
     */
    ::sidl::array< ::std::string>
    getAllKeys_impl (
      /* in */::gov::cca::Type t
    )
    ;


    /**
     *  Return true if the key exists in this map 
     */
    bool
    hasKey_impl (
      /* in */const ::std::string& key
    )
    ;


    /**
     *  Return the type of the value associated with this key 
     */
    ::gov::cca::Type
    typeOf_impl (
      /* in */const ::std::string& key
    )
    ;

  };  // end class TypeMap_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.TypeMap._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(decaf.TypeMap._hmisc)

#endif
