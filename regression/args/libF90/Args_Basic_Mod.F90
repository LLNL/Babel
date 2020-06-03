! 
! File:          Args_Basic_Mod.F90
! Symbol:        Args.Basic-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Args.Basic
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Args_Basic_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Args_Basic_impl

  ! DO-NOT-DELETE splicer.begin(Args.Basic.use)
  ! Insert-Code-Here {Args.Basic.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Args_Basic_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Args.Basic.private_data)
    ! Insert-Code-Here {Args.Basic.private_data} (private data members)
    integer :: place_holder ! replace with your private data
    ! DO-NOT-DELETE splicer.end(Args.Basic.private_data)
  end type Args_Basic_priv

  type Args_Basic_wrap
    sequence
    type(Args_Basic_priv), pointer :: d_private_data
  end type Args_Basic_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Args_Basic
    use sidl_BaseInterface
    implicit none
    ! out Args_Basic retval
    type(Args_Basic_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Args_Basic_wrap private_data
    type(Args_Basic_wrap), intent(in) :: private_data
    external Args_Basic_wrapObj_m
    call Args_Basic_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Args_Basic_impl
