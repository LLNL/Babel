// 
// File:          Overload_AnException_Impl.hxx
// Symbol:        Overload.AnException-v1.0
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Overload.AnException
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_Overload_AnException_Impl_hxx
#define included_Overload_AnException_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_Overload_AnException_IOR_h
#include "Overload_AnException_IOR.h"
#endif
#ifndef included_Overload_AnException_hxx
#include "Overload_AnException.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_SIDLException_hxx
#include "sidl_SIDLException.hxx"
#endif
#ifndef included_sidl_io_Deserializer_hxx
#include "sidl_io_Deserializer.hxx"
#endif
#ifndef included_sidl_io_Serializer_hxx
#include "sidl_io_Serializer.hxx"
#endif


// DO-NOT-DELETE splicer.begin(Overload.AnException._hincludes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(Overload.AnException._hincludes)

namespace Overload { 

  /**
   * Symbol "Overload.AnException" (version 1.0)
   * 
   * This exception is passed into the overloaded method as an example
   * of passing classes.
   */
  class AnException_impl : public virtual ::Overload::AnException 
  // DO-NOT-DELETE splicer.begin(Overload.AnException._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(Overload.AnException._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(Overload.AnException._implementation)
    // Put additional implementation details here...
    // DO-NOT-DELETE splicer.end(Overload.AnException._implementation)

  public:
    // default constructor, used for data wrapping(required)
    AnException_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      AnException_impl( struct Overload_AnException__object * ior ) : StubBase(
        ior,true), 
      ::sidl::io::Serializable((ior==NULL) ? NULL : &((
        *ior).d_sidl_sidlexception.d_sidl_io_serializable)),
    ::sidl::BaseException((ior==NULL) ? NULL : &((
      *ior).d_sidl_sidlexception.d_sidl_baseexception)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~AnException_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:

  };  // end class AnException_impl

} // end namespace Overload

// DO-NOT-DELETE splicer.begin(Overload.AnException._hmisc)
// Put miscellaneous things here...
// DO-NOT-DELETE splicer.end(Overload.AnException._hmisc)

#endif
