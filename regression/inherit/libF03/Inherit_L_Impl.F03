! 
! File:          Inherit_L_Impl.F03
! Symbol:        Inherit.L-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.L
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.L" (version 1.1)
! 


#include "Inherit_A2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Inherit_L_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_L_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_L_t) :: Inherit_L_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.L.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.L.private_data)
  end type Inherit_L_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Inherit_L_impl_t), target :: obj
    type(Inherit_L_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_L_wrap
    call Inherit_L_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.L._ctor.use)
    ! Insert-Code-Here {Inherit.L._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.L._ctor.use)
    implicit none
    !  in Inherit.L self
    type(Inherit_L_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.L._ctor)
    ! Insert-Code-Here {Inherit.L._ctor} (_ctor method)
    ! DO-NOT-DELETE splicer.end(Inherit.L._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.L._dtor.use)
    ! Insert-Code-Here {Inherit.L._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.L._dtor.use)
    implicit none
    !  in Inherit.L self
    type(Inherit_L_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.L._dtor)
    ! Insert-Code-Here {Inherit.L._dtor} (_dtor method)
    ! DO-NOT-DELETE splicer.end(Inherit.L._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.L._load.use)
    ! Insert-Code-Here {Inherit.L._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.L._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.L._load)
    ! Insert-Code-Here {Inherit.L._load} (_load method)
    ! DO-NOT-DELETE splicer.end(Inherit.L._load)
  end subroutine load_impl


  ! 
  ! Method:  a[a]
  ! 

  function aa_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.L.aa.use)
    ! Insert-Code-Here {Inherit.L.aa.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.L.aa.use)
    implicit none
    !  in Inherit.L self
    type(Inherit_L_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.L.aa)
    retval = 'L.a'
    ! DO-NOT-DELETE splicer.end(Inherit.L.aa)
  end function aa_impl


  ! 
  ! Method:  a[2]
  ! 

  function a2_impl(self, i, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.L.a2.use)
    ! Insert-Code-Here {Inherit.L.a2.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.L.a2.use)
    implicit none
    !  in Inherit.L self
    type(Inherit_L_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.L.a2)
    retval = 'L.a2'
    ! DO-NOT-DELETE splicer.end(Inherit.L.a2)
  end function a2_impl


  ! 
  ! Method:  l[]
  ! 

  function l_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.L.l.use)
    ! Insert-Code-Here {Inherit.L.l.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Inherit.L.l.use)
    implicit none
    !  in Inherit.L self
    type(Inherit_L_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.L.l)
    retval = 'L.l'
    ! DO-NOT-DELETE splicer.end(Inherit.L.l)
  end function l_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_L_Impl
