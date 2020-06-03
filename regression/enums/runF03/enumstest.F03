!
! File:       enumstests.F90
! Copyright:  (c) 2010 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision$
! Date:       $Date$
! Description:Exercise the FORTRAN interface
!
!

#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
#include "sidl_long_fAbbrev.h"
#include "enums_car_fAbbrev.h"
#include "enums_cartest_fAbbrev.h"
#include "enums_color_fAbbrev.h"
#include "enums_colorwheel_fAbbrev.h"
#include "enums_number_fAbbrev.h"
#include "enums_numbertest_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"

subroutine starttest(number)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  integer (kind=sidl_int) :: number
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  tracker = getInstance(throwaway_exception)
  call startPart(tracker, number, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end subroutine starttest

subroutine reporttest(test, number, python, tracker)
  use sidl
  use synch_RegOut
  use synch_ResultType
  use sidl_BaseInterface
  integer (kind=sidl_int) :: number
  logical                             :: test, python
! implicit none
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  if (test) then
     call endPart(tracker, number, PASS, throwaway_exception)
  else
     if (python) then
        call endPart(tracker, number, XFAIL, throwaway_exception)
     else
        call endPart(tracker, number, FAIL, throwaway_exception)
     endif
  endif
  number = number + 1
end subroutine reporttest

subroutine checkColors(test, python, tracker, remoteURL)
  use sidl
  use sidl_BaseInterface
  use enums_color
  use enums_colorwheel
  use synch_RegOut
  integer(kind=sidl_int) :: test
  logical python
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway
  character (len=256) :: remoteURL
  integer(kind=sidl_enum) :: out, inout, retval
  logical bRetval
  type(enums_colorwheel_t) :: obj

  out = -5
  inout = green
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif


  call starttest(test)
  retval = returnback(obj, throwaway)
  call reporttest(retval .eq. violet, test, python, tracker)

  call starttest(test)
  bRetval = passin(obj, blue, throwaway)
  call reporttest(bRetval, test, python, tracker)

  call starttest(test)
  bRetval = passout(obj, out, throwaway)
  call reporttest(bRetval .and. (out .eq. violet), test, python, tracker)

  call starttest(test)
  bRetval = passinout(obj, inout, throwaway)
  call reporttest(bRetval .and. (inout .eq. red), test, python, tracker)

  call starttest(test)
  retval = passeverywhere(obj, blue, out, inout, throwaway)
  call reporttest((retval .eq. violet) &
            .and. (out .eq. violet)    &
            .and. (inout .eq. green),  &
            test, python, tracker)
end subroutine checkColors

subroutine checkCars(test, python, tracker, remoteURL)
  use sidl
  use sidl_BaseInterface
  use enums_car
  use enums_cartest
  use enums_car_array
  use synch_RegOut
  implicit none

  integer(kind=sidl_int) test
  logical python
  type(synch_RegOut_t) :: tracker
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) :: throwaway
  integer(kind=sidl_enum) :: out, inout, retval
  type(enums_cartest_t) :: obj
  logical bRetval
  integer*8 result
  type(enums_car_1d) :: array_in, array_out, array_inout, array_retval
  integer(kind=sidl_enum) :: c1, c2, c3
  integer(kind=sidl_enum), dimension(3) :: vals = (/ford, mercedes, porsche/)
  integer(kind=sidl_int) i

  out = -5
  inout = ford

  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif

  call starttest(test)
  retval = returnback(obj, throwaway)
  call reporttest(retval .eq. porsche, test, python, tracker)

  call starttest(test)
  bRetval = passin(obj, mercedes, throwaway)
  call reporttest(bRetval, test, python, tracker)

  call starttest(test)
  bRetval = passout(obj, out, throwaway)
  call reporttest(bRetval .and. (out .eq. ford), test, python, tracker)

  call starttest(test)
  bRetval = passinout(obj, inout, throwaway)
  call reporttest(bRetval .and. (inout .eq. porsche), test, python, tracker)

  call starttest(test)
  retval = passeverywhere(obj, mercedes, out, inout, throwaway)
  call reporttest((retval .eq. porsche)  &
            .and. (out .eq. ford)        &
            .and. (inout .eq. mercedes), &
            test, python, tracker)

  call starttest(test)
  call create1d(3_sidl_int, array_in)
  call create1d(3_sidl_int, array_inout)

  do i = 0, 2
     call set(array_in, i, vals(i+1))
     call set(array_inout, i, vals(i+1))
  end do

  call copy(array_in, array_inout)

  array_retval = passarray(obj, array_in, array_out, array_inout, throwaway)
  result = 1
  do i = 0, 2
     call get(array_retval, i, c1) 
     call get(array_out, i, c2)
     call get(array_inout, i, c3)
     if ((c1 .ne. vals(i+1)) .or. &
         (c2 .ne. vals(i+1)) .or. &
         (c3 .ne. vals(i+1))) then
        result = 0
     end if
  end do

  call deleteRef(array_in)
  call deleteRef(array_inout)
  call deleteRef(array_out)
  call deleteRef(array_retval)

  call reporttest(result .ne. 0, test, python, tracker)

  ! TODO: write generic enumarray testcases
end subroutine checkCars


subroutine checkNumbers(test, python, tracker, remoteURL)
  use sidl
  use sidl_BaseInterface
  use enums_number
  use enums_numbertest
  use synch_RegOut
  integer(kind=sidl_int) test
  logical python
  type(synch_RegOut_t) :: tracker
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) :: throwaway
  integer(kind=sidl_enum) out, inout, retval
  type(enums_numbertest_t) :: obj
  logical bRetval

  out = -5
  inout = zero
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif

  call starttest(test)
  retval = returnback(obj, throwaway)
  call reporttest(retval .eq. notOne, test, python, tracker)

  call starttest(test)
  bRetval = passin(obj, notZero, throwaway)
  call reporttest(bRetval, test, python, tracker)

  call starttest(test)
  bRetval = passout(obj, out, throwaway)
  call reporttest(bRetval .and. (out .eq. negOne), test, python, tracker)

  call starttest(test)
  bRetval = passinout(obj, inout, throwaway)
  call reporttest(bRetval .and. (inout .eq. notZero), test, python, tracker)

  call starttest(test)
  retval = passeverywhere(obj, notZero, out, inout, throwaway)

  call reporttest((retval .eq. notOne) &
            .and. (out .eq. negOne)    &
            .and. (inout .eq. zero),   &
            test, python, tracker)

end subroutine checkNumbers


program enumstest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sidl_rmi_ProtocolFactory

  implicit none

  integer :: IArgc
  integer (kind=sidl_int) :: test
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(synch_RegOut_t) :: tracker
  character (len=80)             :: language
  logical                        :: ispython
  character (len=256) :: remoteURL, arg
  integer :: i
  logical :: retval

  tracker = getInstance(throwaway_exception)

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
     call replaceMagicVars(tracker, remoteURL, arg, throwaway_exception)
     print *,'using remote URL ', remoteURL
     print *,'registering RMI protocol simhandle'
     retval = addProtocol('simhandle', 'sidlx.rmi.SimHandle', throwaway_exception)
     if (retval .neqv. .true.) then
        print *,'sidl.rmi.ProtocolFactor.addProtocol() failed'
     endif
  endif

  language = ' '
  if (IArgc() .ge. 1) then
     call GetArg(1, language)
     write (*,*) language
  endif
  ispython = language .eq. 'Python'
  test = 1
  call setExpectations(tracker,16_sidl_int, throwaway_exception)

  call writeComment(tracker, 'undefined integer values', throwaway_exception)
  call CheckColors(test, ispython, tracker, remoteURL)

  call writeComment(tracker, 'partially defined integer values', &
       throwaway_exception)
  call CheckCars(test, ispython, tracker, remoteURL)

  call writeComment(tracker, 'fully defined integer values', throwaway_exception)
  call CheckNumbers(test, ispython, tracker, remoteURL)

  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)

end program enumstest
