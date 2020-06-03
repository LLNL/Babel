! 
! File:          objarg_EmployeeArray_Impl.F90
! Symbol:        objarg.EmployeeArray-v0.5
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for objarg.EmployeeArray
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "objarg.EmployeeArray" (version 0.5)
! 
! This class manages a collection of employees.
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "objarg_EmployeeArray_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "objarg_Employee_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine objarg_EmployeeArray__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor.use)
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor)
  type(objarg_EmployeeArray_wrap) :: pd
  allocate(pd%d_private_data)
  pd%d_private_data%d_capacity = 0
  pd%d_private_data%d_length = 0
  pd%d_private_data%d_allocated = .false.
  call objarg_EmployeeArray__set_data_m(self, pd)
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor)
end subroutine objarg_EmployeeArray__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine objarg_EmployeeArray__ctor2_mi(self, private_data,        &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor2.use)
  ! Insert-Code-Here {objarg.EmployeeArray._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor2.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  type(objarg_EmployeeArray_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor2)
! Insert-Code-Here {objarg.EmployeeArray._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor2)
end subroutine objarg_EmployeeArray__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine objarg_EmployeeArray__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor.use)
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor)
  type(objarg_EmployeeArray_wrap) :: pd
  type(objarg_Employee_t)         :: employee
  integer(kind=sidl_int)   :: i
  call objarg_EmployeeArray__get_data_m(self, pd)
  do i = 1, pd%d_private_data%d_length
     employee = pd%d_private_data%d_employees(i)
     call set_null(pd%d_private_data%d_employees(i))
     call deleteRef(employee, exception)
  end do
  if (pd%d_private_data%d_allocated) then
     deallocate(pd%d_private_data%d_employees)
     pd%d_private_data%d_allocated = .false.
  endif
  deallocate(pd%d_private_data)
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor)
end subroutine objarg_EmployeeArray__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine objarg_EmployeeArray__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load)
end subroutine objarg_EmployeeArray__load_mi


! 
! Method:  getLength[]
! Return the number of employees in the employee array.
! 

recursive subroutine Employee_getLengthf1kxbf743e_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.getLength.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength)
  type(objarg_EmployeeArray_wrap) :: pd
  call objarg_EmployeeArray__get_data_m(self, pd)
  retval = pd%d_private_data%d_length
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.getLength)
end subroutine Employee_getLengthf1kxbf743e_mi


! 
! Method:  at[]
! Return the employee in position <code>idx</code> where
! <code>idx</code> ranges from 1 to the length of the array.
! If <code>idx</code> is outside the range of the array (i.e.
! less than or equal to zero or greater than the current number
! of elements in the array), this method returns a NULL
! employee object.
! 

recursive subroutine objarg_EmployeeArray_at_mi(self, idx, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  integer (kind=sidl_int) :: idx
  ! in
  type(objarg_Employee_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at)
  type(objarg_EmployeeArray_wrap) :: pd
  type(objarg_Employee_t) :: employee
  call objarg_EmployeeArray__get_data_m(self, pd)
  if ((idx .gt. 0) .and. &
       (idx .le. pd%d_private_data%d_length)) then
     retval = pd%d_private_data%d_employees(idx)
     call addRef(retval, exception)
  else
     call set_null(retval)
  end if
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at)
end subroutine objarg_EmployeeArray_at_mi


! 
! Method:  appendEmployee[]
! Add an employee onto the end of the array.  It is perfectly
! legal to add the same employee multiple times.
! <code>true</code> is returned when the append was successful;
! otherwise, <code>false</code> is returned to indicate
! failure.  This method will not add a NULL employee.
! 

recursive subroutine Emp_appendEmployeey1p0il489c_mi(self, e, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.appendEmployee.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  type(objarg_Employee_t) :: e
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee)
  type (objarg_EmployeeArray_wrap) :: pd
  type (objarg_Employee_t), dimension(:), pointer :: newarray
  integer(kind=sidl_int) :: newcapacity, i
  if (not_null(e)) then
     call objarg_EmployeeArray__get_data_m(self, pd)
     if (pd%d_private_data%d_length .ge. pd%d_private_data%d_capacity) then
        newcapacity = pd%d_private_data%d_capacity + 4
        if ((pd%d_private_data%d_capacity / 10) .gt. 4) then
           newcapacity = pd%d_private_data%d_capacity + &
                pd%d_private_data%d_capacity / 10
        endif
        allocate(newarray(newcapacity))
        do i = 1, pd%d_private_data%d_length
           newarray(i) = pd%d_private_data%d_employees(i)
        end do
        if (pd%d_private_data%d_allocated) then
           deallocate(pd%d_private_data%d_employees)
        endif
        pd%d_private_data%d_employees => newarray
        pd%d_private_data%d_allocated = .true.
        pd%d_private_data%d_capacity = newcapacity
     end if
     call addRef(e, exception)
     pd%d_private_data%d_length = pd%d_private_data%d_length + 1
     pd%d_private_data%d_employees(pd%d_private_data%d_length) = e
     retval = .true.
  else
     retval = .false.
  endif
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.appendEmployee)
end subroutine Emp_appendEmployeey1p0il489c_mi


! 
! Method:  findByName[]
! Find the first employee in the array that has a name matching
! <code>name</code>.  If a match exists, the index is returned,
! and the employee is returned in parameter <code>e</code>.
! 
! If no match exists, 0 is returned, and <code>e</code> is NULL.
! 

recursive subroutine Employe_findByNameshr253wixu_mi(self, name, e, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.findByName.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  character (len=*) :: name
  ! in
  type(objarg_Employee_t) :: e
  ! out
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName)
  character(len=256) :: lname, tname
  integer(kind=sidl_int) :: i
  type(objarg_EmployeeArray_wrap) :: pd
  type(objarg_Employee_t) :: tmp
  call objarg_EmployeeArray__get_data_m(self, pd)
  lname = name
  retval = 0
  do i = 1, pd%d_private_data%d_length
     tmp = pd%d_private_data%d_employees(i)
     if (not_null(tmp)) then
        call getName(tmp, tname, exception)
        if (lname .eq. tname) then
           retval = i
           e = tmp
           call addRef(tmp, exception)
           return
        endif
     endif
  end do
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.findByName)
end subroutine Employe_findByNameshr253wixu_mi


! 
! Method:  promoteToMaxSalary[]
! Determine the maximum salary in the array. If the maximum
! salary in the array is greater than the current salary of
! <code>e</code>, the salary of <code>e</code> will be 
! increased to the maximum salary in the array.  If the
! array is empty, no change will be made to <code>e</code>.
! 
! This method returns <code>true</code> iff the salary of
! <code>e</code> is modified.
! 

recursive subroutine promoteToMaxSalaryvyx713pe07_mi(self, e, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_EmployeeArray
  use objarg_EmployeeArray_impl
  ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary.use)
  implicit none
  type(objarg_EmployeeArray_t) :: self
  ! in
  type(objarg_Employee_t) :: e
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary)
  integer(kind=sidl_int) :: i
  type(objarg_EmployeeArray_wrap) :: pd
  type(objarg_Employee_t) :: tmp
  real(kind=sidl_float) :: maxsalary, salary
  retval = .false.
  if (not_null(e)) then
     call objarg_EmployeeArray__get_data_m(self, pd)
     maxsalary = -1.0e30
     do i = 1, pd%d_private_data%d_length
        call getSalary(pd%d_private_data%d_employees(i), salary, exception)
        if (salary .gt. maxSalary) then
           maxsalary = salary
        endif
     end do
     call getSalary(e, salary, exception)
     if (maxSalary .gt. salary) then
        call setSalary(e, maxSalary, exception)
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary)
end subroutine promoteToMaxSalaryvyx713pe07_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
