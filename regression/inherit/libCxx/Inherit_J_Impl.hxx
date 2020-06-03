// 
// File:          Inherit_J_Impl.hxx
// Symbol:        Inherit.J-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.J
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_J_Impl_hxx
#define included_Inherit_J_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_J_IOR_h
#include "Inherit_J_IOR.h"
#endif
#ifndef included_Inherit_A_hxx
#include "Inherit_A.hxx"
#endif
#ifndef included_Inherit_B_hxx
#include "Inherit_B.hxx"
#endif
#ifndef included_Inherit_E2_hxx
#include "Inherit_E2.hxx"
#endif
#ifndef included_Inherit_J_hxx
#include "Inherit_J.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.J._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.J._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.J" (version 1.1)
   */
  class J_impl : public virtual ::Inherit::J 
  // DO-NOT-DELETE splicer.begin(Inherit.J._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.J._inherits)

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
      struct Inherit_E2__epv* superEPV;
      struct Inherit_J__object* superSelf;


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

      Super(struct Inherit_J__object* loc_self, const struct 
        Inherit_J__external* loc_ext) {
        superEPV = loc_ext->getSuperEPV();
        superSelf = loc_self;
      }
      /**
       * user defined static method
       */
      ::std::string
      e() ;

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

    // DO-NOT-DELETE splicer.begin(Inherit.J._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.J._implementation)

  public:
    // default constructor, used for data wrapping(required)
    J_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      J_impl( struct Inherit_J__object * ior ) : StubBase(ior,true), 
      ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_a)),
    ::Inherit::B((ior==NULL) ? NULL : &((*ior).d_inherit_b)), super(ior, 
      J::_get_ext()) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~J_impl() { _dtor(); }

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
    j_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    e_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    c_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    a_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    b_impl() ;
  };  // end class J_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.J._hmisc)
  // Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.J._hmisc)

#endif
