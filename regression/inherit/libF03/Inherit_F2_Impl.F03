! 
! File:          Inherit_F2_Impl.F03
! Symbol:        Inherit.F2-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.F2
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.F2" (version 1.1)
! 


#include "Inherit_C_fAbbrev.h"
#include "Inherit_F2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "Inherit_B_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_F2_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_F2_t) :: Inherit_F2_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.F2.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.F2.private_data)
  end type Inherit_F2_impl_t

contains

  type(Inherit_C_t) function super(self)
    use Inherit_C_type
    type(Inherit_F2_impl_t), intent(in) :: self
    external Inherit_F2_super
    call Inherit_F2_super(self, super)
  end function super

  subroutine wrap(obj, self, exception)
    type(Inherit_F2_impl_t), target :: obj
    type(Inherit_F2_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_F2_wrap
    call Inherit_F2_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2._ctor.use)
    implicit none
    !  in Inherit.F2 self
    type(Inherit_F2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.F2._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2._dtor.use)
    implicit none
    !  in Inherit.F2 self
    type(Inherit_F2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.F2._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.F2._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2._load)
  end subroutine load_impl


  ! 
  ! Method:  c[]
  ! 

  function c_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2.c.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2.c.use)
    implicit none
    !  in Inherit.F2 self
    type(Inherit_F2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.F2.c)
    retval = 'F2.c'
    ! DO-NOT-DELETE splicer.end(Inherit.F2.c)
  end function c_impl


  ! 
  ! Method:  a[]
  ! 

  function a_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2.a.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2.a.use)
    implicit none
    !  in Inherit.F2 self
    type(Inherit_F2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.F2.a)
    retval = 'F2.a'
    ! DO-NOT-DELETE splicer.end(Inherit.F2.a)
  end function a_impl


  ! 
  ! Method:  b[]
  ! 

  function b_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2.b.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2.b.use)
    implicit none
    !  in Inherit.F2 self
    type(Inherit_F2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.F2.b)
    retval = 'F2.b'
    ! DO-NOT-DELETE splicer.end(Inherit.F2.b)
  end function b_impl


  ! 
  ! Method:  f[]
  ! 

  function f_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.F2.f.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.F2.f.use)
    implicit none
    !  in Inherit.F2 self
    type(Inherit_F2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.F2.f)
    retval = 'F2.f'
    ! DO-NOT-DELETE splicer.end(Inherit.F2.f)
  end function f_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_F2_Impl
