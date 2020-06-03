! 
! File:          f90_TimeStepper_Mod.F90
! Symbol:        f90.TimeStepper-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side private data module for f90.TimeStepper
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "f90_TimeStepper_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module f90_TimeStepper_impl

  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper.use)
use sidl
use conway_Environment
use conway_Ruleset 
use sidl_int_array
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type f90_TimeStepper_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(f90.TimeStepper.private_data)
  type(conway_Environment_t) :: d_env
  type(conway_Ruleset_t) :: d_rules
  type(sidl_int_2d) :: d_next
  integer(kind=sidl_int) :: d_step
    ! DO-NOT-DELETE splicer.end(f90.TimeStepper.private_data)
  end type f90_TimeStepper_priv

  type f90_TimeStepper_wrap
    sequence
    type(f90_TimeStepper_priv), pointer :: d_private_data
  end type f90_TimeStepper_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use f90_TimeStepper
    use sidl_BaseInterface
    implicit none
    ! out f90_TimeStepper retval
    type(f90_TimeStepper_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in f90_TimeStepper_wrap private_data
    type(f90_TimeStepper_wrap), intent(in) :: private_data
    external f90_TimeStepper_wrapObj_m
    call f90_TimeStepper_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module f90_TimeStepper_impl
