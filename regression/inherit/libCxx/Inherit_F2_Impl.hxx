// 
// File:          Inherit_F2_Impl.hxx
// Symbol:        Inherit.F2-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.F2
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_F2_Impl_hxx
#define included_Inherit_F2_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_F2_IOR_h
#include "Inherit_F2_IOR.h"
#endif
#ifndef included_Inherit_A_hxx
#include "Inherit_A.hxx"
#endif
#ifndef included_Inherit_B_hxx
#include "Inherit_B.hxx"
#endif
#ifndef included_Inherit_C_hxx
#include "Inherit_C.hxx"
#endif
#ifndef included_Inherit_F2_hxx
#include "Inherit_F2.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.F2._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.F2._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.F2" (version 1.1)
   */
  class F2_impl : public virtual ::Inherit::F2 
  // DO-NOT-DELETE splicer.begin(Inherit.F2._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.F2._inherits)

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
      struct Inherit_F2__object* superSelf;


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

      Super(struct Inherit_F2__object* loc_self, const struct 
        Inherit_F2__external* loc_ext) {
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

    // DO-NOT-DELETE splicer.begin(Inherit.F2._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.F2._implementation)

  public:
    // default constructor, used for data wrapping(required)
    F2_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      F2_impl( struct Inherit_F2__object * ior ) : StubBase(ior,true), 
      ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_a)),
    ::Inherit::B((ior==NULL) ? NULL : &((*ior).d_inherit_b)), super(ior, 
      F2::_get_ext()) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~F2_impl() { _dtor(); }

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
    /**
     * user defined non-static method.
     */
    ::std::string
    f_impl() ;
  };  // end class F2_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.F2._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.F2._hmisc)

#endif
