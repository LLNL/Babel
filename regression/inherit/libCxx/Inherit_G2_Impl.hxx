// 
// File:          Inherit_G2_Impl.hxx
// Symbol:        Inherit.G2-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Inherit.G2
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Inherit_G2_Impl_hxx
#define included_Inherit_G2_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Inherit_G2_IOR_h
#include "Inherit_G2_IOR.h"
#endif
#ifndef included_Inherit_D_hxx
#include "Inherit_D.hxx"
#endif
#ifndef included_Inherit_G2_hxx
#include "Inherit_G2.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Inherit.G2._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Inherit.G2._hincludes)

namespace Inherit { 

  /**
   * Symbol "Inherit.G2" (version 1.1)
   */
  class G2_impl : public virtual ::Inherit::G2 
  // DO-NOT-DELETE splicer.begin(Inherit.G2._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Inherit.G2._inherits)

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
      struct Inherit_D__epv* superEPV;
      struct Inherit_G2__object* superSelf;


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

      Super(struct Inherit_G2__object* loc_self, const struct 
        Inherit_G2__external* loc_ext) {
        superEPV = loc_ext->getSuperEPV();
        superSelf = loc_self;
      }
      /**
       * user defined static method
       */
      ::std::string
      a() ;

      /**
       * user defined static method
       */
      ::std::string
      d() ;

  };

  private:
  // Use this to dispatch to super functions.
  Super super;


  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Inherit.G2._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Inherit.G2._implementation)

  public:
    // default constructor, used for data wrapping(required)
    G2_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      G2_impl( struct Inherit_G2__object * ior ) : StubBase(ior,true), 
    ::Inherit::A((ior==NULL) ? NULL : &((*ior).d_inherit_d.d_inherit_a)), super(
      ior, G2::_get_ext()) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~G2_impl() { _dtor(); }

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
    a_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    d_impl() ;
    /**
     * user defined non-static method.
     */
    ::std::string
    g_impl() ;
  };  // end class G2_impl

} // end namespace Inherit

// DO-NOT-DELETE splicer.begin(Inherit.G2._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Inherit.G2._hmisc)

#endif
