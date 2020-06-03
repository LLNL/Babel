! 
! File:          Inherit_E_Impl.F03
! Symbol:        Inherit.E-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.E
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.E" (version 1.1)
! 


#include "Inherit_E_fAbbrev.h"
#include "Inherit_C_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_E_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_E_t) :: Inherit_E_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.E.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.E.private_data)
  end type Inherit_E_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Inherit_E_impl_t), target :: obj
    type(Inherit_E_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_E_wrap
    call Inherit_E_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.E._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E._ctor.use)
    implicit none
    !  in Inherit.E self
    type(Inherit_E_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.E._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.E._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.E._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E._dtor.use)
    implicit none
    !  in Inherit.E self
    type(Inherit_E_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.E._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.E._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.E._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.E._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.E._load)
  end subroutine load_impl


  ! 
  ! Method:  e[]
  ! 

  function e_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.E.e.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.E.e.use)
    implicit none
    !  in Inherit.E self
    type(Inherit_E_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.E.e)
    retval = 'E.e'
    ! DO-NOT-DELETE splicer.end(Inherit.E.e)
  end function e_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_E_Impl
