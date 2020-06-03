// 
// File:          Hello_World_Impl.hxx
// Symbol:        Hello.World-v1.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for Hello.World
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Hello_World_Impl_hxx
#define included_Hello_World_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Hello_World_IOR_h
#include "Hello_World_IOR.h"
#endif
#ifndef included_Hello_World_hxx
#include "Hello_World.hxx"
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


// DO-NOT-DELETE splicer.begin(Hello.World._hincludes)
// nothing needed
// DO-NOT-DELETE splicer.end(Hello.World._hincludes)

namespace Hello { 

  /**
   * Symbol "Hello.World" (version 1.2)
   */
  class World_impl : public virtual ::Hello::World 
  // DO-NOT-DELETE splicer.begin(Hello.World._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Hello.World._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Hello.World._implementation)
    // nothing needed
    // DO-NOT-DELETE splicer.end(Hello.World._implementation)

  public:
    // default constructor, used for data wrapping(required)
    World_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    World_impl( struct Hello_World__object * ior ) : StubBase(ior,true) , 
      _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~World_impl() { _dtor(); }

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
    ::std::string
    getMsg_impl() ;
    /**
     * user defined non-static method.
     */
    int32_t
    foo_impl (
      /* in */int32_t i,
      /* out */int32_t& o,
      /* inout */int32_t& io
    )
    ;

  };  // end class World_impl

} // end namespace Hello

// DO-NOT-DELETE splicer.begin(Hello.World._hmisc)
// nothing needed
// DO-NOT-DELETE splicer.end(Hello.World._hmisc)

#endif
