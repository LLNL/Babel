! 
! File:          Inherit_H_Impl.F03
! Symbol:        Inherit.H-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.H
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.H" (version 1.1)
! 


#include "Inherit_H_fAbbrev.h"
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

module Inherit_H_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_H
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_H_t) :: Inherit_H_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.H.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.H.private_data)
  end type Inherit_H_impl_t

contains

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.H._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.H._ctor.use)
    implicit none
    !  in Inherit.H self
    type(Inherit_H_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.H._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.H._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.H._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.H._dtor.use)
    implicit none
    !  in Inherit.H self
    type(Inherit_H_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.H._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.H._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.H._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.H._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.H._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.H._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_H_Impl
