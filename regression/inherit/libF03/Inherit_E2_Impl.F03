! 
! File:          Inherit_E2_Impl.F03
! Symbol:        Inherit.E2-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.E2
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.E2" (version 1.1)
! 


#include "Inherit_C_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_E2_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_E2_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E2
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_E2_t) :: Inherit_E2_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.E2.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.E2.private_data)
  end type Inherit_E2_impl_t

contains

  type(Inherit_C_t) function super(self)
    use Inherit_C_type
    type(Inherit_E2_impl_t), intent(in) :: self
    external Inherit_E2_super
    call Inherit_E2_super(self, super)
  end function super

  subroutine wrap(obj, self, exception)
    type(Inherit_E2_impl_t), target :: obj
    type(Inherit_E2_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_E2_wrap
    call Inherit_E2_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.E2._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2._ctor.use)
    implicit none
    !  in Inherit.E2 self
    type(Inherit_E2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.E2._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.E2._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2._dtor.use)
    implicit none
    !  in Inherit.E2 self
    type(Inherit_E2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.E2._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.E2._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.E2._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2._load)
  end subroutine load_impl


  ! 
  ! Method:  c[]
  ! 

  function c_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.E2.c.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2.c.use)
    implicit none
    !  in Inherit.E2 self
    type(Inherit_E2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.E2.c)
    retval = 'E2.c'
    ! DO-NOT-DELETE splicer.end(Inherit.E2.c)
  end function c_impl


  ! 
  ! Method:  e[]
  ! 

  function e_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.E2.e.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E2.e.use)
    implicit none
    !  in Inherit.E2 self
    type(Inherit_E2_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.E2.e)
    retval = 'E2.e'
    ! DO-NOT-DELETE splicer.end(Inherit.E2.e)
  end function e_impl


  ! 
  ! Method:  m[]
  ! 

  function m_impl(exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.E2.m.use)
    ! DO-NOT-DELETE splicer.end(Inherit.E2.m.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.E2.m)
    retval = 'E2.m'
    ! DO-NOT-DELETE splicer.end(Inherit.E2.m)
  end function m_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_E2_Impl
