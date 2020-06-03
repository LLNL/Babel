! 
! File:          vect_vExcept_Mod.F90
! Symbol:        vect.vExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for vect.vExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "vect_vExcept_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Nothing to add here
! DO-NOT-DELETE splicer.end(_hincludes)

module vect_vExcept_impl

  ! DO-NOT-DELETE splicer.begin(vect.vExcept.use)
  ! Nothing to add here
  ! DO-NOT-DELETE splicer.end(vect.vExcept.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type vect_vExcept_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(vect.vExcept.private_data)
    ! Nothing to add here
    integer :: place_holder ! replace with your private data
    ! DO-NOT-DELETE splicer.end(vect.vExcept.private_data)
  end type vect_vExcept_priv

  type vect_vExcept_wrap
    sequence
    type(vect_vExcept_priv), pointer :: d_private_data
  end type vect_vExcept_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use vect_vExcept
    use sidl_BaseInterface
    implicit none
    ! out vect_vExcept retval
    type(vect_vExcept_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in vect_vExcept_wrap private_data
    type(vect_vExcept_wrap), intent(in) :: private_data
    external vect_vExcept_wrapObj_m
    call vect_vExcept_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module vect_vExcept_impl
