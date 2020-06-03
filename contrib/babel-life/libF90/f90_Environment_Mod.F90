! 
! File:          f90_Environment_Mod.F90
! Symbol:        f90.Environment-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side private data module for f90.Environment
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "f90_Environment_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module f90_Environment_impl

  ! DO-NOT-DELETE splicer.begin(f90.Environment.use)
use sidl_int_array
  ! DO-NOT-DELETE splicer.end(f90.Environment.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type f90_Environment_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(f90.Environment.private_data)
  type(sidl_int_2d) :: d_grid
    ! DO-NOT-DELETE splicer.end(f90.Environment.private_data)
  end type f90_Environment_priv

  type f90_Environment_wrap
    sequence
    type(f90_Environment_priv), pointer :: d_private_data
  end type f90_Environment_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use f90_Environment
    use sidl_BaseInterface
    implicit none
    ! out f90_Environment retval
    type(f90_Environment_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in f90_Environment_wrap private_data
    type(f90_Environment_wrap), intent(in) :: private_data
    external f90_Environment_wrapObj_m
    call f90_Environment_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module f90_Environment_impl
