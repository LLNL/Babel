! 
! File:          objarg_EmployeeArray_Mod.F90
! Symbol:        objarg.EmployeeArray-v0.5
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for objarg.EmployeeArray
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "objarg_EmployeeArray_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module objarg_EmployeeArray_impl

  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.use)
  use sidl
  use objarg_Employee
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type objarg_EmployeeArray_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.private_data)
   type(objarg_Employee_t), dimension(:), pointer     :: d_employees
   integer(kind=sidl_int)                             :: d_length
   integer(kind=sidl_int)                             :: d_capacity
   logical                                            :: d_allocated
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.private_data)
  end type objarg_EmployeeArray_priv

  type objarg_EmployeeArray_wrap
    sequence
    type(objarg_EmployeeArray_priv), pointer :: d_private_data
  end type objarg_EmployeeArray_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use objarg_EmployeeArray
    use sidl_BaseInterface
    implicit none
    ! out objarg_EmployeeArray retval
    type(objarg_EmployeeArray_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in objarg_EmployeeArray_wrap private_data
    type(objarg_EmployeeArray_wrap), intent(in) :: private_data
    external objarg_EmployeeArray_wrapObj_m
    call objarg_EmployeeArray_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module objarg_EmployeeArray_impl
