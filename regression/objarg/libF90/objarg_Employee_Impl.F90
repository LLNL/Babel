! 
! File:          objarg_Employee_Impl.F90
! Symbol:        objarg.Employee-v0.5
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for objarg.Employee
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "objarg.Employee" (version 0.5)
! 
! This object type holds the basic information about an employee:
! name, age, salary and marital status.  This object exists purely
! to serve as a test case for sidl.  It is not intended for serious
! use.
! 


#include "sidl_RuntimeException_fAbbrev.h"
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

recursive subroutine objarg_Employee__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee._ctor.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee._ctor)
  type(objarg_Employee_wrap) :: pd
  allocate(pd%d_private_data)
  pd%d_private_data%d_salary = 0
  pd%d_private_data%d_age = 0
  pd%d_private_data%d_name = ''
  pd%d_private_data%d_status = achar(0)
  call objarg_Employee__set_data_m(self, pd)
! DO-NOT-DELETE splicer.end(objarg.Employee._ctor)
end subroutine objarg_Employee__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine objarg_Employee__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee._ctor2.use)
  ! Insert-Code-Here {objarg.Employee._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.Employee._ctor2.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  type(objarg_Employee_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee._ctor2)
! Insert-Code-Here {objarg.Employee._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(objarg.Employee._ctor2)
end subroutine objarg_Employee__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine objarg_Employee__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee._dtor.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee._dtor)
  type(objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self,pd)
  deallocate(pd%d_private_data)
! DO-NOT-DELETE splicer.end(objarg.Employee._dtor)
end subroutine objarg_Employee__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine objarg_Employee__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(objarg.Employee._load)
end subroutine objarg_Employee__load_mi


! 
! Method:  init[]
! Provide the data for the employee object to hold.
! <code>false</code> is returned when the data was unacceptable.
! <code>true</code> means the employee object was successfully
! initialized.
! 

recursive subroutine objarg_Employee_init_mi(self, name, age, salary, status,  &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.init.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.init.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  character (len=*) :: name
  ! in
  integer (kind=sidl_int) :: age
  ! in
  real (kind=sidl_float) :: salary
  ! in
  character (len=1) :: status
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.init)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  pd%d_private_data%d_name = name
  pd%d_private_data%d_age = age
  pd%d_private_data%d_salary = salary
  pd%d_private_data%d_status = status
  retval = .true.
! DO-NOT-DELETE splicer.end(objarg.Employee.init)
end subroutine objarg_Employee_init_mi


! 
! Method:  setName[]
! Change the name of an employee.
! 

recursive subroutine objarg_Employee_setName_mi(self, name, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.setName.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.setName.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  character (len=*) :: name
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.setName)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  pd%d_private_data%d_name = name
! DO-NOT-DELETE splicer.end(objarg.Employee.setName)
end subroutine objarg_Employee_setName_mi


! 
! Method:  getName[]
! Return the name of an employee.
! 

recursive subroutine objarg_Employee_getName_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.getName.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.getName.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.getName)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  retval = pd%d_private_data%d_name
! DO-NOT-DELETE splicer.end(objarg.Employee.getName)
end subroutine objarg_Employee_getName_mi


! 
! Method:  setAge[]
! Change the age of an employee.
! 

recursive subroutine objarg_Employee_setAge_mi(self, age, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.setAge.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.setAge.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  integer (kind=sidl_int) :: age
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.setAge)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  pd%d_private_data%d_age = age
! DO-NOT-DELETE splicer.end(objarg.Employee.setAge)
end subroutine objarg_Employee_setAge_mi


! 
! Method:  getAge[]
! Return the age of an employee.
! 

recursive subroutine objarg_Employee_getAge_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.getAge.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.getAge.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.getAge)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  retval = pd%d_private_data%d_age
! DO-NOT-DELETE splicer.end(objarg.Employee.getAge)
end subroutine objarg_Employee_getAge_mi


! 
! Method:  setSalary[]
! Set an employee's salary.
! 

recursive subroutine objarg_Employee_setSalary_mi(self, salary, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.setSalary.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  real (kind=sidl_float) :: salary
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  pd%d_private_data%d_salary = salary
! DO-NOT-DELETE splicer.end(objarg.Employee.setSalary)
end subroutine objarg_Employee_setSalary_mi


! 
! Method:  getSalary[]
! Return an employee's salary.
! 

recursive subroutine objarg_Employee_getSalary_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.getSalary.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  real (kind=sidl_float) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  retval = pd%d_private_data%d_salary
! DO-NOT-DELETE splicer.end(objarg.Employee.getSalary)
end subroutine objarg_Employee_getSalary_mi


! 
! Method:  setStatus[]
! Set an employee's marital status.
! 

recursive subroutine objarg_Employee_setStatus_mi(self, status, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.setStatus.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  character (len=1) :: status
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  pd%d_private_data%d_status = status
! DO-NOT-DELETE splicer.end(objarg.Employee.setStatus)
end subroutine objarg_Employee_setStatus_mi


! 
! Method:  getStatus[]
! Return an employee's marital status.
! 

recursive subroutine objarg_Employee_getStatus_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  use objarg_Employee_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(objarg.Employee.getStatus.use)
  implicit none
  type(objarg_Employee_t) :: self
  ! in
  character (len=1) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus)
  type (objarg_Employee_wrap) :: pd
  call objarg_Employee__get_data_m(self, pd)
  retval = pd%d_private_data%d_status
! DO-NOT-DELETE splicer.end(objarg.Employee.getStatus)
end subroutine objarg_Employee_getStatus_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
