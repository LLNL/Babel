! 
! File:          objarg_Basic_Mod.F90
! Symbol:        objarg.Basic-v0.5
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for objarg.Basic
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "objarg_Basic_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module objarg_Basic_impl

  ! DO-NOT-DELETE splicer.begin(objarg.Basic.use)
  ! Insert-Code-Here {objarg.Basic.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.Basic.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type objarg_Basic_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(objarg.Basic.private_data)
    ! Insert-Code-Here {objarg.Basic.private_data} (private data members)
    integer :: place_holder ! replace with your private data
    ! DO-NOT-DELETE splicer.end(objarg.Basic.private_data)
  end type objarg_Basic_priv

  type objarg_Basic_wrap
    sequence
    type(objarg_Basic_priv), pointer :: d_private_data
  end type objarg_Basic_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use objarg_Basic
    use sidl_BaseInterface
    implicit none
    ! out objarg_Basic retval
    type(objarg_Basic_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in objarg_Basic_wrap private_data
    type(objarg_Basic_wrap), intent(in) :: private_data
    external objarg_Basic_wrapObj_m
    call objarg_Basic_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module objarg_Basic_impl
