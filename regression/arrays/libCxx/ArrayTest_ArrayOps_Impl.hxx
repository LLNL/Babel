// 
// File:          ArrayTest_ArrayOps_Impl.hxx
// Symbol:        ArrayTest.ArrayOps-v1.3
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for ArrayTest.ArrayOps
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_ArrayTest_ArrayOps_Impl_hxx
#define included_ArrayTest_ArrayOps_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_ArrayTest_ArrayOps_IOR_h
#include "ArrayTest_ArrayOps_IOR.h"
#endif
#ifndef included_ArrayTest_ArrayOps_hxx
#include "ArrayTest_ArrayOps.hxx"
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


// DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._hincludes)

namespace ArrayTest { 

  /**
   * Symbol "ArrayTest.ArrayOps" (version 1.3)
   */
  class ArrayOps_impl : public virtual ::ArrayTest::ArrayOps 
  // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._implementation)

  public:
    // default constructor, used for data wrapping(required)
    ArrayOps_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    ArrayOps_impl( struct ArrayTest_ArrayOps__object * ior ) : StubBase(ior,
      true) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~ArrayOps_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

    /**
     * Return <code>true</code> iff the even elements are true and
     * the odd elements are false.
     */
    static bool
    checkBool_impl (
      /* in array<bool> */::sidl::array<bool>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkChar_impl (
      /* in array<char> */::sidl::array<char>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkInt_impl (
      /* in array<int> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkLong_impl (
      /* in array<long> */::sidl::array<int64_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkString_impl (
      /* in array<string> */::sidl::array< ::std::string>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkDouble_impl (
      /* in array<double> */::sidl::array<double>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkFloat_impl (
      /* in array<float> */::sidl::array<float>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkFcomplex_impl (
      /* in array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkDcomplex_impl (
      /* in array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check2Int_impl (
      /* in array<int,2> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check2Double_impl (
      /* in array<double,2> */::sidl::array<double>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check2Float_impl (
      /* in array<float,2> */::sidl::array<float>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check2Fcomplex_impl (
      /* in array<fcomplex,2> */::sidl::array< ::sidl::fcomplex>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check2Dcomplex_impl (
      /* in array<dcomplex,2> */::sidl::array< ::sidl::dcomplex>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check3Int_impl (
      /* in array<int,3> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check4Int_impl (
      /* in array<int,4> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check5Int_impl (
      /* in array<int,5> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check6Int_impl (
      /* in array<int,6> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check7Int_impl (
      /* in array<int,7> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    check2String_impl (
      /* in array<string,2> */::sidl::array< ::std::string>& a
    )
    ;

    /**
     * user defined static method
     */
    static int32_t
    checkObject_impl (
      /* in array<ArrayTest.ArrayOps> */::sidl::array< ::ArrayTest::ArrayOps>& a
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseBool_impl (
      /* inout array<bool> */::sidl::array<bool>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseChar_impl (
      /* inout array<char> */::sidl::array<char>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseInt_impl (
      /* inout array<int> */::sidl::array<int32_t>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseLong_impl (
      /* inout array<long> */::sidl::array<int64_t>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseString_impl (
      /* inout array<string> */::sidl::array< ::std::string>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseDouble_impl (
      /* inout array<double> */::sidl::array<double>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseFloat_impl (
      /* inout array<float> */::sidl::array<float>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseFcomplex_impl (
      /* inout array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static bool
    reverseDcomplex_impl (
      /* inout array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a,
      /* in */bool newArray
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<bool>
    createBool_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<char>
    createChar_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    createInt_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<int64_t>
    createLong_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::std::string>
    createString_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<double>
    createDouble_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<float>
    createFloat_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::sidl::fcomplex>
    createFcomplex_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::sidl::dcomplex>
    createDcomplex_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::ArrayTest::ArrayOps>
    createObject_impl (
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    create2Int_impl (
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<double>
    create2Double_impl (
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<float>
    create2Float_impl (
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::sidl::dcomplex>
    create2Dcomplex_impl (
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::sidl::fcomplex>
    create2Fcomplex_impl (
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array< ::std::string>
    create2String_impl (
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    create3Int_impl() ;
    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    create4Int_impl() ;
    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    create5Int_impl() ;
    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    create6Int_impl() ;
    /**
     * user defined static method
     */
    static ::sidl::array<int32_t>
    create7Int_impl() ;
    /**
     * user defined static method
     */
    static void
    makeBool_impl (
      /* in */int32_t len,
      /* out array<bool> */::sidl::array<bool>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeChar_impl (
      /* in */int32_t len,
      /* out array<char> */::sidl::array<char>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInt_impl (
      /* in */int32_t len,
      /* out array<int> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeLong_impl (
      /* in */int32_t len,
      /* out array<long> */::sidl::array<int64_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeString_impl (
      /* in */int32_t len,
      /* out array<string> */::sidl::array< ::std::string>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeDouble_impl (
      /* in */int32_t len,
      /* out array<double> */::sidl::array<double>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeFloat_impl (
      /* in */int32_t len,
      /* out array<float> */::sidl::array<float>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeFcomplex_impl (
      /* in */int32_t len,
      /* out array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeDcomplex_impl (
      /* in */int32_t len,
      /* out array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutBool_impl (
      /* inout array<bool> */::sidl::array<bool>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutChar_impl (
      /* inout array<char> */::sidl::array<char>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutInt_impl (
      /* inout array<int> */::sidl::array<int32_t>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutLong_impl (
      /* inout array<long> */::sidl::array<int64_t>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutString_impl (
      /* inout array<string> */::sidl::array< ::std::string>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutDouble_impl (
      /* inout array<double> */::sidl::array<double>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutFloat_impl (
      /* inout array<float> */::sidl::array<float>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutDcomplex_impl (
      /* inout array<dcomplex> */::sidl::array< ::sidl::dcomplex>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOutFcomplex_impl (
      /* inout array<fcomplex> */::sidl::array< ::sidl::fcomplex>& a,
      /* in */int32_t len
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut2Int_impl (
      /* inout array<int,2> */::sidl::array<int32_t>& a,
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut2Double_impl (
      /* inout array<double,2> */::sidl::array<double>& a,
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut2Float_impl (
      /* inout array<float,2> */::sidl::array<float>& a,
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut2Dcomplex_impl (
      /* inout array<dcomplex,2> */::sidl::array< ::sidl::dcomplex>& a,
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut2Fcomplex_impl (
      /* inout array<fcomplex,2> */::sidl::array< ::sidl::fcomplex>& a,
      /* in */int32_t d1,
      /* in */int32_t d2
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut3Int_impl (
      /* inout array<int,3> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut4Int_impl (
      /* inout array<int,4> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut5Int_impl (
      /* inout array<int,5> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut6Int_impl (
      /* inout array<int,6> */::sidl::array<int32_t>& a
    )
    ;

    /**
     * user defined static method
     */
    static void
    makeInOut7Int_impl (
      /* inout array<int,7> */::sidl::array<int32_t>& a
    )
    ;


    /**
     * Return as out parameters the type and dimension of the 
     * array passed in. If a is NULL, dimen == type == 0 on exit.
     * The contents of the array have the default values for a 
     * newly created array.
     */
    static void
    checkGeneric_impl (
      /* in array<> */::sidl::basearray& a,
      /* out */int32_t& dmn,
      /* out */int32_t& tp
    )
    ;


    /**
     * Create an array of the type and dimension specified and
     * return it. A type of 0 causes a NULL array to be returned.
     */
    static ::sidl::basearray
    createGeneric_impl (
      /* in */int32_t dmn,
      /* in */int32_t tp
    )
    ;


    /**
     * Testing passing generic arrays using every possible mode.
     * The returned array is a copy of inArg, so if inArg != NULL,
     * the return value should != NULL. outArg is also a copy of
     * inArg.
     * If inOutArg is NULL on entry, a 2-D array of int that should
     * pass check2Int is returned.
     * If inOutArg is not NULL on entry and its dimension is even,
     * it is returned unchanged; otherwise, NULL is returned.
     */
    static ::sidl::basearray
    passGeneric_impl (
      /* in array<> */::sidl::basearray& inArg,
      /* inout array<> */::sidl::basearray& inOutArg,
      /* out array<> */::sidl::basearray& outArg
    )
    ;

    /**
     * user defined static method
     */
    static void
    initRarray1Int_impl (
      /* inout rarray[n] */int32_t* a,
      /* in */int32_t n
    )
    ;

    /**
     * user defined static method
     */
    static void
    initRarray3Int_impl (
      /* inout rarray[n,m,o] */int32_t* a,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o
    )
    ;

    /**
     * user defined static method
     */
    static void
    initRarray7Int_impl (
      /* inout rarray[n,m,o,p,q,r,s] */int32_t* a,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o,
      /* in */int32_t p,
      /* in */int32_t q,
      /* in */int32_t r,
      /* in */int32_t s
    )
    ;

    /**
     * user defined static method
     */
    static void
    initRarray1Double_impl (
      /* inout rarray[n] */double* a,
      /* in */int32_t n
    )
    ;

    /**
     * user defined static method
     */
    static void
    initRarray1Dcomplex_impl (
      /* inout rarray[n] */struct sidl_dcomplex* a,
      /* in */int32_t n
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkRarray1Int_impl (
      /* in rarray[n] */int32_t* a,
      /* in */int32_t n
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkRarray3Int_impl (
      /* in rarray[n,m,o] */int32_t* a,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkRarray7Int_impl (
      /* in rarray[n,m,o,p,q,r,s] */int32_t* a,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o,
      /* in */int32_t p,
      /* in */int32_t q,
      /* in */int32_t r,
      /* in */int32_t s
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkRarray1Double_impl (
      /* in rarray[n] */double* a,
      /* in */int32_t n
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkRarray1Dcomplex_impl (
      /* in rarray[n] */struct sidl_dcomplex* a,
      /* in */int32_t n
    )
    ;

    /**
     * user defined static method
     */
    static void
    matrixMultiply_impl (
      /* in rarray[n,m] */int32_t* a,
      /* in rarray[m,o] */int32_t* b,
      /* inout rarray[n,o] */int32_t* res,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o
    )
    ;

    /**
     * user defined static method
     */
    static bool
    checkMatrixMultiply_impl (
      /* in rarray[n,m] */int32_t* a,
      /* in rarray[m,o] */int32_t* b,
      /* in rarray[n,o] */int32_t* res,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o
    )
    ;


    /**
     * user defined non-static method.
     */
    void
    mm_impl (
      /* in rarray[n,m] */int32_t* a,
      /* in rarray[m,o] */int32_t* b,
      /* inout rarray[n,o] */int32_t* res,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    checkmm_impl (
      /* in rarray[n,m] */int32_t* a,
      /* in rarray[m,o] */int32_t* b,
      /* in rarray[n,o] */int32_t* res,
      /* in */int32_t n,
      /* in */int32_t m,
      /* in */int32_t o
    )
    ;

  };  // end class ArrayOps_impl

} // end namespace ArrayTest

// DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._hmisc)

#endif
