!
! File:       objargtest.F03
! Copyright:  (c) 2010 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision$
! Date:       $Date$
! Description:Exercise the FORTRAN interface
!

#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
#include "objarg_Employee_fAbbrev.h"
#include "objarg_EmployeeArray_fAbbrev.h"
#include "objarg_Basic_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"

subroutine starttest(number)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: number
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  tracker = getInstance(throwaway_exception)
  call startPart(tracker, number, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end subroutine starttest

subroutine reporttest(test, number)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use synch_ResultType
  implicit none
  integer (kind=sidl_int) :: number
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  logical                        :: test
  tracker = getInstance(throwaway_exception)
  if (test) then
     call endPart(tracker, number, PASS, throwaway_exception)
  else
     call endPart(tracker, number, FAIL, throwaway_exception)
  endif
  call deleteRef(tracker, throwaway_exception)
  number = number + 1
end subroutine reporttest

subroutine makeBaseClassObject(obj, remoteURL)
  use sidl_BaseClass
  type(sidl_BaseClass_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeBaseClassObject

subroutine makeEmployeeObject(obj, remoteURL)
  use objarg_Employee
  type(objarg_Employee_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeEmployeeObject

subroutine makeEmployeeArrayObject(obj, remoteURL)
  use objarg_EmployeeArray
  type(objarg_EmployeeArray_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeEmployeeArrayObject

subroutine makeBasicObject(obj, remoteURL)
  use objarg_Basic
  type(objarg_Basic_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeBasicObject

program objargtest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use sidl_BaseClass
  use sidl_rmi_ProtocolFactory
  use objarg_Employee
  use objarg_EmployeeArray
  use objarg_Basic
  integer (kind=sidl_int) :: test, idx
  type(sidl_BaseInterface_t) :: ex, bi
  type(synch_RegOut_t) :: tracker
  character (len=256) :: remoteURL, arg
  integer :: i
  logical :: retval

  type tmp_data_t
     character(len=256)     :: name
     integer(kind=sidl_int) :: age
     real(kind=sidl_float)  :: salary
     character              :: status
  end type tmp_data_t

  type(tmp_data_t) :: tmp_data(7)

  type(objarg_EmployeeArray_t) :: a
  type(objarg_Employee_t) :: e, e2
  type(objarg_Basic_t) :: b
  type(sidl_BaseClass_t) :: o, inValue
  
  tmp_data(1) = tmp_data_t('John Smith', 35, 75700, 'c')
  tmp_data(2) = tmp_data_t('Jana Doe',   40, 85500, 'm')
  tmp_data(3) = tmp_data_t('Ella Vader', 64, 144200, 'r')
  tmp_data(4) = tmp_data_t('Marge Inovera', 32, 483200, 's')
  tmp_data(5) = tmp_data_t('Hughy Louis Dewey', 45, 182900, 'm')
  tmp_data(6) = tmp_data_t('Heywood Yubuzof', 12, 20800, 'x')
  tmp_data(7) = tmp_data_t('Picov Andropov', 90, 120600, 'r')

  test = 1
  tracker = getInstance(ex)

  !Parse the command line  to see if we are running RMI tests
  remoteURL = ''
  do i = 1, IArgc()
     call GetArg(i, arg)
     if ( arg(1:6) .eq. '--url=' ) then
        remoteURL = arg(7:)
     endif
  end do

  !Setup RMI if necessary
  if ( remoteURL .ne. '' ) then
     call GetArg(0, arg)
     call replaceMagicVars(tracker, remoteURL, arg, ex)
     print *,'using remote URL ', remoteURL
     print *,'registering RMI protocol simhandle'
     retval = addProtocol('simhandle', 'sidlx.rmi.SimHandle', ex)
     if (retval .neqv. .true.) then
        print *,'sidl.rmi.ProtocolFactor.addProtocol() failed'
     endif
  endif

  call setExpectations(tracker, 82_sidl_int, ex)
  call writeComment(tracker, 'Objarg tests', ex)

  call makeEmployeeArrayObject(a, remoteURL)

  do i = 1, 7
     call makeEmployeeObject(e, remoteURL)
     call starttest(test)
     retval = init(e, tmp_data(i)%name,  tmp_data(i)%age, tmp_data(i)%salary, &
          tmp_data(i)%status, ex)
     call reporttest(retval .eqv. .true., test)
     call starttest(test)
     retval = appendEmployee(a, e, ex)
     call reporttest(retval .eqv. .true., test)
     call starttest(test)
     call reporttest(getLength(a, ex) .eq. i, test)
     call starttest(test)
     e2 = at(a, i, ex)
     call cast(e2, bi, ex)
     retval = isSame(e, bi, ex)
     call deleteRef(e2, ex)
     call reporttest(retval .eqv. .true., test)
     call starttest(test)
     call reporttest(getAge(e, ex) .eq. tmp_data(i)%age, test)
     call starttest(test)
     call reporttest(getSalary(e, ex) .eq. tmp_data(i)%salary, test)
     call starttest(test)
     call reporttest(getStatus(e, ex) .eq. tmp_data(i)%status, test)
  end do


  do i = 1, 7
     call starttest(test)
     idx = findByName(a, tmp_data(i)%name, e, ex)
     call reporttest(idx .eq. i, test)
     if (idx .ne. 0) then
        call starttest(test)
        e2 = at(a, idx, ex)
        call cast(e2, bi, ex)
        retval = isSame(e, bi, ex)
        call deleteRef(e2, ex)
        call reporttest(retval .eqv. .true., test)
     end if
  end do

  call makeEmployeeObject(e2, remoteURL)
  retval = init(e2, 'Hire High', 21_sidl_int, 0._sidl_float, 's', ex)
  call starttest(test)
  retval = promoteToMaxSalary(a, e2, ex)
  call reporttest(retval .eqv. .true., test)
  call starttest(test)
  call reporttest(getSalary(e2, ex) .eq. 483200, test)
  call starttest(test)
  retval = appendEmployee(a, e2, ex)
  call reporttest(retval .eqv. .true., test)
  call starttest(test)
  call makeEmployeeObject(e2, remoteURL)
  retval = init(e2, 'Amadeo Avogadro, conte di Quaregna', 225_sidl_int, &
       6.022045e23_sidl_float, 'd', ex)
  retval = promoteToMaxSalary(a, e2, ex)
  call reporttest(retval .eqv. .false., test)


  call makeBasicObject(b, remoteURL)
  call starttest(test)
  call set_null(o)
  retval = passIn(b, o, .false., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call makeBaseClassObject(o, remoteURL)
  retval = passIn(b, o, .true., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call makeBaseClassObject(o, remoteURL)
  retval = passIn(b, o, .true., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call set_null(o)
  retval = passInOut(b, o, .false., .false., .true., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call reporttest(is_null(o) .eqv. .true., test)

  call starttest(test)
  call makeBaseClassObject(o, remoteURL)
  retval = passInOut(b, o, .true., .false., .false., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call reporttest(is_null(o) .eqv. .true., test)
  
  call starttest(test)
  call makeBaseClassObject(o, remoteURL)
  inValue = o 
  call addRef(inValue, ex)
  retval = passInOut(b, o, .true., .true., .true., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call cast(o, bi, ex)
  retval = isSame(inValue, bi, ex)
  call deleteRef(inValue, ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call makeBaseClassObject(o, remoteURL)
  inValue = o 
  call addRef(inValue, ex)
  retval = passInOut(b, o, .true., .true., .false., ex)
  call reporttest(retval .eqv. .true., test)

  call starttest(test)
  call cast(o, bi, ex)
  retval = isSame(inValue, bi, ex)
  call deleteRef(inValue, ex)
  call reporttest(retval .eqv. .false., test)

  call set_null(o)
  call starttest(test)
  call passOut(b, o, .false., ex)
  call reporttest(not_null(o) .eqv. .true., test)
  
  call starttest(test)
  call passOut(b, o, .true., ex)
  call reporttest(is_null(o) .eqv. .true., test)

  call starttest(test)
  o = retObject(b, .true., ex)
  call reporttest(is_null(o) .eqv. .true., test)

  call starttest(test)
  o = retObject(b, .false., ex)
  call reporttest(is_null(o) .eqv. .false., test)
  
  call close(tracker, ex)
  call deleteRef(tracker, ex)
end program objargtest
