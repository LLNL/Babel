! 
! File:          objarg_EmployeeArray_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module objarg_EmployeeArray_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_EmployeeArray
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(objarg_EmployeeArray_t) :: objarg_EmployeeArray_impl_t
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.private_data)
    ! insert code here (private data members)
    type(objarg_Employee_t), dimension(:), pointer     :: d_employees
    integer(kind=sidl_int)                             :: d_length
    integer(kind=sidl_int)                             :: d_capacity
    logical                                            :: d_allocated
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.private_data)
  end type objarg_EmployeeArray_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(objarg_EmployeeArray_impl_t), target :: obj
    type(objarg_EmployeeArray_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external objarg_EmployeeArray_wrap
    call objarg_EmployeeArray_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor.use)
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._ctor)
    self%d_capacity = 0
    self%d_length = 0
    self%d_allocated = .false.
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor.use)
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._dtor)
    type(objarg_Employee_t)         :: employee
    integer(kind=sidl_int)   :: i
    do i = 1, self%d_length
       employee = self%d_employees(i)
       call set_null(self%d_employees(i))
       call deleteRef(employee, exception)
    end do
    if (self%d_allocated) then
       deallocate(self%d_employees)
       self%d_allocated = .false.
    endif
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray._load)
  end subroutine load_impl


  ! 
  ! Method:  getLength[]
  ! Return the number of employees in the employee array.
  ! 

  function getLength_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.getLength.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.getLength)
    retval = self%d_length
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.getLength)
  end function getLength_impl


  ! 
  ! Method:  at[]
  ! Return the employee in position <code>idx</code> where
  ! <code>idx</code> ranges from 1 to the length of the array.
  ! If <code>idx</code> is outside the range of the array (i.e.
  ! less than or equal to zero or greater than the current number
  ! of elements in the array), this method returns a NULL
  ! employee object.
  ! 

  function at_impl(self, idx, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  in int idx
    integer (kind=sidl_int), intent(in) :: idx
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(objarg_Employee_t) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.at)
    type(objarg_Employee_t) :: employee
    if ((idx .gt. 0) .and. &
         (idx .le. self%d_length)) then
       retval = self%d_employees(idx)
       call addRef(retval, exception)
    else
       call set_null(retval)
    end if
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.at)
  end function at_impl


  ! 
  ! Method:  appendEmployee[]
  ! Add an employee onto the end of the array.  It is perfectly
  ! legal to add the same employee multiple times.
  ! <code>true</code> is returned when the append was successful;
  ! otherwise, <code>false</code> is returned to indicate
  ! failure.  This method will not add a NULL employee.
  ! 

  function appendEmployee_impl(self, e, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.appendEmployee.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  in objarg.Employee e
    type(objarg_Employee_t), intent(in) :: e
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.appendEmployee)
    type (objarg_Employee_t), dimension(:), pointer :: newarray
    integer(kind=sidl_int) :: newcapacity, i
    if (not_null(e)) then
       if (self%d_length .ge. self%d_capacity) then
          newcapacity = self%d_capacity + 4
          if ((self%d_capacity / 10) .gt. 4) then
             newcapacity = self%d_capacity + &
                  self%d_capacity / 10
          endif
          allocate(newarray(newcapacity))
          do i = 1, self%d_length
             newarray(i) = self%d_employees(i)
          end do
          if (self%d_allocated) then
             deallocate(self%d_employees)
          endif
          self%d_employees => newarray
          self%d_allocated = .true.
          self%d_capacity = newcapacity
       end if
       call addRef(e, exception)
       self%d_length = self%d_length + 1
       self%d_employees(self%d_length) = e
       retval = .true.
    else
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.appendEmployee)
  end function appendEmployee_impl


  ! 
  ! Method:  findByName[]
  ! Find the first employee in the array that has a name matching
  ! <code>name</code>.  If a match exists, the index is returned,
  ! and the employee is returned in parameter <code>e</code>.
  ! 
  ! If no match exists, 0 is returned, and <code>e</code> is NULL.
  ! 

  function findByName_impl(self, name, e, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.findByName.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  in string name
    character (len=*), intent(in) :: name
    !  out objarg.Employee e
    type(objarg_Employee_t), intent(out) :: e
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.findByName)
    character(len=256) :: lname, tname
    integer(kind=sidl_int) :: i
    type(objarg_Employee_t) :: tmp
    lname = name
    retval = 0
    do i = 1, self%d_length
       tmp = self%d_employees(i)
       if (not_null(tmp)) then
          tname = getName(tmp, exception)
          if (lname .eq. tname) then
             retval = i
             e = tmp
             call addRef(tmp, exception)
             return
          endif
       endif
    end do
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.findByName)
  end function findByName_impl


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

  function promoteToMaxSalary_impl(self, e, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary.use)
    implicit none
    !  in objarg.EmployeeArray self
    type(objarg_EmployeeArray_impl_t) :: self
    !  inout objarg.Employee e
    type(objarg_Employee_t), intent(inout) :: e
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.EmployeeArray.promoteToMaxSalary)
    integer(kind=sidl_int) :: i
    type(objarg_Employee_t) :: tmp
    real(kind=sidl_float) :: maxsalary, salary
    retval = .false.
    if (not_null(e)) then
       maxsalary = -1.0e30
       do i = 1, self%d_length
          salary = getSalary(self%d_employees(i), exception)
          if (salary .gt. maxSalary) then
             maxsalary = salary
          endif
       end do
       salary = getSalary(e, exception)
       if (maxSalary .gt. salary) then
          call setSalary(e, maxSalary, exception)
          retval = .true.
       endif
    endif
    ! DO-NOT-DELETE splicer.end(objarg.EmployeeArray.promoteToMaxSalary)
  end function promoteToMaxSalary_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module objarg_EmployeeArray_Impl
