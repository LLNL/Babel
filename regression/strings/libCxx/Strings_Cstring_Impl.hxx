// 
// File:          Strings_Cstring_Impl.hxx
// Symbol:        Strings.Cstring-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Strings.Cstring
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Strings_Cstring_Impl_hxx
#define included_Strings_Cstring_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Strings_Cstring_IOR_h
#include "Strings_Cstring_IOR.h"
#endif
#ifndef included_Strings_Cstring_hxx
#include "Strings_Cstring.hxx"
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


// DO-NOT-DELETE splicer.begin(Strings.Cstring._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Strings.Cstring._hincludes)

namespace Strings { 

  /**
   * Symbol "Strings.Cstring" (version 1.1)
   * 
   * Class to allow testing of string passing using every possible mode.
   */
  class Cstring_impl : public virtual ::Strings::Cstring 
  // DO-NOT-DELETE splicer.begin(Strings.Cstring._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Strings.Cstring._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Strings.Cstring._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Strings.Cstring._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Cstring_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    Cstring_impl( struct Strings_Cstring__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Cstring_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * If <code>nonNull</code> is <code>true</code>, this will
     * return "Three"; otherwise, it will return a NULL or empty string.
     */
    ::std::string
    returnback_impl (
      /* in */bool nonNull
    )
    ;


    /**
     * This will return <code>true</code> iff <code>c</code> equals "Three".
     */
    bool
    passin_impl (
      /* in */const ::std::string& c
    )
    ;


    /**
     * If <code>nonNull</code> is <code>true</code>, this will return
     * "Three" in <code>c</code>; otherwise, it will return a null or
     * empty string. The return value is <code>false</code> iff 
     * the outgoing value of <code>c</code> is <code>null</code>.
     */
    bool
    passout_impl (
      /* in */bool nonNull,
      /* out */::std::string& c
    )
    ;

    /**
     * user defined non-static method.
     */
    bool
    passinout_impl (
      /* inout */::std::string& c
    )
    ;

    /**
     * user defined non-static method.
     */
    ::std::string
    passeverywhere_impl (
      /* in */const ::std::string& c1,
      /* out */::std::string& c2,
      /* inout */::std::string& c3
    )
    ;


    /**
     *  return true iff s1 == s2 and c1 == c2 
     */
    bool
    mixedarguments_impl (
      /* in */const ::std::string& s1,
      /* in */char c1,
      /* in */const ::std::string& s2,
      /* in */char c2
    )
    ;

  };  // end class Cstring_impl

} // end namespace Strings

// DO-NOT-DELETE splicer.begin(Strings.Cstring._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Strings.Cstring._hmisc)

#endif
