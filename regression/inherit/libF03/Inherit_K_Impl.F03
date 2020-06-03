! 
! File:          Inherit_K_Impl.F03
! Symbol:        Inherit.K-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.K
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.K" (version 1.1)
! 


#include "Inherit_H_fAbbrev.h"
#include "Inherit_A2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_K_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_K_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_K_t) :: Inherit_K_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.K.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.K.private_data)
  end type Inherit_K_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Inherit_K_impl_t), target :: obj
    type(Inherit_K_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_K_wrap
    call Inherit_K_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.K._ctor.use)
    ! Insert-Code-Here {Inherit.K._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K._ctor.use)
    implicit none
    !  in Inherit.K self
    type(Inherit_K_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.K._ctor)
    ! Insert-Code-Here {Inherit.K._ctor} (_ctor method)
    ! DO-NOT-DELETE splicer.end(Inherit.K._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.K._dtor.use)
    ! Insert-Code-Here {Inherit.K._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K._dtor.use)
    implicit none
    !  in Inherit.K self
    type(Inherit_K_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.K._dtor)
    ! Insert-Code-Here {Inherit.K._dtor} (_dtor method)
    ! DO-NOT-DELETE splicer.end(Inherit.K._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.K._load.use)
    ! Insert-Code-Here {Inherit.K._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.K._load)
    ! Insert-Code-Here {Inherit.K._load} (_load method)
    ! DO-NOT-DELETE splicer.end(Inherit.K._load)
  end subroutine load_impl


  ! 
  ! Method:  a[2]
  ! 

  function a2_impl(self, i, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.K.a2.use)
    ! Insert-Code-Here {Inherit.K.a2.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K.a2.use)
    implicit none
    !  in Inherit.K self
    type(Inherit_K_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.K.a2)
    retval = 'K.a2'
    ! DO-NOT-DELETE splicer.end(Inherit.K.a2)
  end function a2_impl


  ! 
  ! Method:  a[]
  ! 

  function a_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.K.a.use)
    ! Insert-Code-Here {Inherit.K.a.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K.a.use)
    implicit none
    !  in Inherit.K self
    type(Inherit_K_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.K.a)
    retval = 'K.a'
    ! DO-NOT-DELETE splicer.end(Inherit.K.a)
  end function a_impl


  ! 
  ! Method:  h[]
  ! 

  function h_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.K.h.use)
    ! Insert-Code-Here {Inherit.K.h.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K.h.use)
    implicit none
    !  in Inherit.K self
    type(Inherit_K_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.K.h)
    retval = 'K.h'
    ! DO-NOT-DELETE splicer.end(Inherit.K.h)
  end function h_impl


  ! 
  ! Method:  k[]
  ! 

  function k_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.K.k.use)
    ! Insert-Code-Here {Inherit.K.k.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.K.k.use)
    implicit none
    !  in Inherit.K self
    type(Inherit_K_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.K.k)
    retval = 'K.k'
    ! DO-NOT-DELETE splicer.end(Inherit.K.k)
  end function k_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_K_Impl
