! 
! File:          objarg_Employee_Mod.F90
! Symbol:        objarg.Employee-v0.5
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for objarg.Employee
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "objarg_Employee_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module objarg_Employee_impl

  ! DO-NOT-DELETE splicer.begin(objarg.Employee.use)
use sidl
  ! DO-NOT-DELETE splicer.end(objarg.Employee.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type objarg_Employee_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.private_data)
   character(len=256)     :: d_name
   integer(kind=sidl_int) :: d_age
   real(kind=sidl_float)  :: d_salary
   character              :: d_status
    ! DO-NOT-DELETE splicer.end(objarg.Employee.private_data)
  end type objarg_Employee_priv

  type objarg_Employee_wrap
    sequence
    type(objarg_Employee_priv), pointer :: d_private_data
  end type objarg_Employee_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use objarg_Employee
    use sidl_BaseInterface
    implicit none
    ! out objarg_Employee retval
    type(objarg_Employee_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in objarg_Employee_wrap private_data
    type(objarg_Employee_wrap), intent(in) :: private_data
    external objarg_Employee_wrapObj_m
    call objarg_Employee_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module objarg_Employee_impl
