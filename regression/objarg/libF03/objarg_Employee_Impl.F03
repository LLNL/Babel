! 
! File:          objarg_Employee_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module objarg_Employee_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Employee
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(objarg_Employee_t) :: objarg_Employee_impl_t
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.private_data)
    ! insert code here (private data members)
    character(len=256)     :: d_name
    integer(kind=sidl_int) :: d_age
    real(kind=sidl_float)  :: d_salary
    character              :: d_status
    ! DO-NOT-DELETE splicer.end(objarg.Employee.private_data)
  end type objarg_Employee_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(objarg_Employee_impl_t), target :: obj
    type(objarg_Employee_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external objarg_Employee_wrap
    call objarg_Employee_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee._ctor.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee._ctor)
    self%d_salary = 0
    self%d_age = 0
    self%d_name = ''
    self%d_status = achar(0)
    ! DO-NOT-DELETE splicer.end(objarg.Employee._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee._dtor.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee._dtor)
    ! DO-NOT-DELETE splicer.end(objarg.Employee._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee._load)
  end subroutine load_impl


  ! 
  ! Method:  init[]
  ! Provide the data for the employee object to hold.
  ! <code>false</code> is returned when the data was unacceptable.
  ! <code>true</code> means the employee object was successfully
  ! initialized.
  ! 

  function init_impl(self, name, age, salary, status, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.init.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.init.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  in string name
    character (len=*), intent(in) :: name
    !  in int age
    integer (kind=sidl_int), intent(in) :: age
    !  in float salary
    real (kind=sidl_float), intent(in) :: salary
    !  in char status
    character (len=1), intent(in) :: status
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.init)
    self%d_name = name
    self%d_age = age
    self%d_salary = salary
    self%d_status = status
    retval = .true.
    ! DO-NOT-DELETE splicer.end(objarg.Employee.init)
  end function init_impl


  ! 
  ! Method:  setName[]
  ! Change the name of an employee.
  ! 

  subroutine setName_impl(self, name, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setName.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setName.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  in string name
    character (len=*), intent(in) :: name
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setName)
    self%d_name = name
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setName)
  end subroutine setName_impl


  ! 
  ! Method:  getName[]
  ! Return the name of an employee.
  ! 

  function getName_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getName.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getName.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getName)
    retval = self%d_name
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getName)
  end function getName_impl


  ! 
  ! Method:  setAge[]
  ! Change the age of an employee.
  ! 

  subroutine setAge_impl(self, age, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setAge.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setAge.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  in int age
    integer (kind=sidl_int), intent(in) :: age
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setAge)
    self%d_age = age
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setAge)
  end subroutine setAge_impl


  ! 
  ! Method:  getAge[]
  ! Return the age of an employee.
  ! 

  function getAge_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getAge.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getAge.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getAge)
    retval = self%d_age
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getAge)
  end function getAge_impl


  ! 
  ! Method:  setSalary[]
  ! Set an employee's salary.
  ! 

  subroutine setSalary_impl(self, salary, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setSalary.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  in float salary
    real (kind=sidl_float), intent(in) :: salary
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setSalary)
    self%d_salary = salary
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setSalary)
  end subroutine setSalary_impl


  ! 
  ! Method:  getSalary[]
  ! Return an employee's salary.
  ! 

  function getSalary_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getSalary.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_float) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getSalary)
    retval = self%d_salary
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getSalary)
  end function getSalary_impl


  ! 
  ! Method:  setStatus[]
  ! Set an employee's marital status.
  ! 

  subroutine setStatus_impl(self, status, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setStatus.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  in char status
    character (len=1), intent(in) :: status
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.setStatus)
    self%d_status = status
    ! DO-NOT-DELETE splicer.end(objarg.Employee.setStatus)
  end subroutine setStatus_impl


  ! 
  ! Method:  getStatus[]
  ! Return an employee's marital status.
  ! 

  function getStatus_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getStatus.use)
    implicit none
    !  in objarg.Employee self
    type(objarg_Employee_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=1) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Employee.getStatus)
    retval = self%d_status
    ! DO-NOT-DELETE splicer.end(objarg.Employee.getStatus)
  end function getStatus_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module objarg_Employee_Impl
