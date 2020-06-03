! 
! File:          Inherit_D_Impl.F03
! Symbol:        Inherit.D-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.D
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.D" (version 1.1)
! 


#include "Inherit_D_fAbbrev.h"
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

module Inherit_D_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_D
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_D_t) :: Inherit_D_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.D.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.D.private_data)
  end type Inherit_D_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Inherit_D_impl_t), target :: obj
    type(Inherit_D_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_D_wrap
    call Inherit_D_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.D._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.D._ctor.use)
    implicit none
    !  in Inherit.D self
    type(Inherit_D_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.D._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.D._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.D._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.D._dtor.use)
    implicit none
    !  in Inherit.D self
    type(Inherit_D_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.D._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.D._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.D._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.D._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.D._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.D._load)
  end subroutine load_impl


  ! 
  ! Method:  a[]
  ! 

  function a_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.D.a.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.D.a.use)
    implicit none
    !  in Inherit.D self
    type(Inherit_D_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.D.a)
    retval = 'D.a'
    ! DO-NOT-DELETE splicer.end(Inherit.D.a)
  end function a_impl


  ! 
  ! Method:  d[]
  ! 

  function d_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.D.d.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.D.d.use)
    implicit none
    !  in Inherit.D self
    type(Inherit_D_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.D.d)
    retval = 'D.d'
    ! DO-NOT-DELETE splicer.end(Inherit.D.d)
  end function d_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_D_Impl
