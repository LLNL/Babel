! 
! File:          Inherit_G2_Impl.F03
! Symbol:        Inherit.G2-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.G2
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.G2" (version 1.1)
! 


#include "Inherit_D_fAbbrev.h"
#include "Inherit_G2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_G2_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_G2
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_G2_t) :: Inherit_G2_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.G2.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.G2.private_data)
  end type Inherit_G2_impl_t

contains

  type(Inherit_D_t) function super(self)
    use Inherit_D_type
    type(Inherit_G2_impl_t), intent(in) :: self
    external Inherit_G2_super
    call Inherit_G2_super(self, super)
  end function super

  subroutine wrap(obj, self, exception)
    type(Inherit_G2_impl_t), target :: obj
    type(Inherit_G2_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_G2_wrap
    call Inherit_G2_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.G2._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2._ctor.use)
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.G2._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.G2._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2._dtor.use)
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.G2._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.G2._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.G2._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2._load)
  end subroutine load_impl


  ! 
  ! Method:  a[]
  ! 

  function a_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.G2.a.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2.a.use)
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.G2.a)
    retval = 'G2.a'
    ! DO-NOT-DELETE splicer.end(Inherit.G2.a)
  end function a_impl


  ! 
  ! Method:  d[]
  ! 

  function d_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.G2.d.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2.d.use)
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.G2.d)
    retval = 'G2.d'
    ! DO-NOT-DELETE splicer.end(Inherit.G2.d)
  end function d_impl


  ! 
  ! Method:  g[]
  ! 

  function g_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.G2.g.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.G2.g.use)
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.G2.g)
    retval = 'G2.g'
    ! DO-NOT-DELETE splicer.end(Inherit.G2.g)
  end function g_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_G2_Impl
