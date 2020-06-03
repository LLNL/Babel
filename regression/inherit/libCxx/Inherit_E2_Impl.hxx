// 
// File:          Inherit_E2_Impl.hxx
// Symbol:        Inherit.E2-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.E2
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_E2_Impl_hxx
#define included_Inherit_E2_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_E2_IOR_h
#include "Inherit_E2_IOR.h"
#endif
#ifndef included_Inherit_C_hxx
#include "Inherit_C.hxx"
#endif
#ifndef included_Inherit_E2_hxx
#include "Inherit_E2.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.E2._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.E2._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.E2" (version 1.1)
   */
  class E2_impl : public virtual ::Inherit::E2 
  // DO-NOT-DELETE splicer.begin(Inherit.E2._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.E2._inherits)

  {
  public:
  // 
  // Hold pointer to IOR functions.
  // 
  class Super {

    private:
      // 
      // Hold pointer to Super EPV
      // 
      struct Inherit_C__epv* superEPV;
      struct Inherit_E2__object* superSelf;


      //////////////////////////////////////////////////
      // 
      // Special methods for throwing exceptions
      // 

    private:
      static 
      void
      throwException0(
        const char* methodName,
        struct sidl_BaseInterface__object *_exception
      )
        // throws:
      ;
    public:
      Super() : superEPV(NULL), superSelf(NULL) {}

      Super(struct Inherit_E2__object* loc_self, const struct 
        Inherit_E2__external* loc_ext) {
        superEPV = loc_ext->getSuperEPV();
        superSelf = loc_self;
      }
      /**
       * user defined static method
       */
      ::std::string
      c() ;

  };

  private:
  // Use this to dispatch to super functions.
  Super super;


  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.E2._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.E2._implementation)

  public:
    // default constructor, used for data wrapping(required)
    E2_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
    E2_impl( struct Inherit_E2__object * ior ) : StubBase(ior,true), super(ior, 
      E2::_get_ext()) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~E2_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:
    /**
     * user defined static method
     */
    static ::std::string
    m_impl() ;

    /**
     * user defined non-static method.
     */
    ::std::string
    c_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    e_impl() ;
  };  // end class E2_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.E2._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.E2._hmisc)

#endif
