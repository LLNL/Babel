!
! File:       argstest.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision$
! Date:       $Date$
! Description:Exercise the FORTRAN interface
!
!
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
#include "Args_Basic_fAbbrev.h"
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

subroutine reporttest(test, number, python)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use synch_ResultType
  implicit none
  integer (kind=sidl_int) :: number
  logical                             :: test, python
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  tracker = getInstance(throwaway_exception)
  if (test) then
     call endPart(tracker, number, PASS, throwaway_exception)
  else
     if (python) then
        call endPart(tracker, number, XFAIL, throwaway_exception)
     else
        call endPart(tracker, number, FAIL, throwaway_exception)
     endif
  endif
  call deleteRef(tracker, throwaway_exception)
  number = number + 1
end subroutine reporttest

subroutine makeObject(obj, remoteURL)
  use Args_Basic
  type(Args_Basic_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeObject

subroutine testbool(test, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)  :: test
  logical :: out, inout, retval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = .true.
  call makeObject(obj, remoteURL)
  call starttest(test)
  retval = returnbackbool(obj, throwaway_exception)
  call reporttest(retval, test,  .false.)
  call starttest(test)
  retval = passinbool(obj, .true., throwaway_exception)
  call reporttest(retval, test,  .false.)
  call starttest(test)
  retval = passoutbool(obj, out, throwaway_exception)
  call reporttest(retval .and. out, test, .false.)
  call starttest(test)
  retval = passinoutbool(obj, inout, throwaway_exception)
  call reporttest(retval .and. .not. inout, test,  .false.)
  call starttest(test)
  retval = passeverywherebool(obj, .true., out, inout, throwaway_exception)
  call reporttest(retval .and. out .and. inout, test, .false.)
  call deleteRef(obj, throwaway_exception)
end subroutine testbool

subroutine testint(test, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)  :: test 
  logical                         :: bretval
  integer (kind=sidl_int)  :: iretval, out, inout
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = 3_sidl_int
  call makeObject(obj, remoteURL)
  call starttest(test)
  iretval = returnbackint(obj, throwaway_exception)
  call reporttest(iretval .eq. 3_sidl_int, test,  .false.)
  call starttest(test)
  bretval = passinint(obj, 3_sidl_int, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  bretval = passoutint(obj, out, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3_sidl_int), test,  .false.)
  call starttest(test)
  bretval = passinoutint(obj, inout, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3_sidl_int), test,  &
                  .false.)
  call starttest(test)
  iretval = passeverywhereint(obj, 3_sidl_int, out, inout, throwaway_exception)
  call reporttest((iretval .eq. 3_sidl_int) .and.  &
                  (out .eq. 3_sidl_int) .and. &
                  (inout .eq. 3_sidl_int), test,  .false.)
  call deleteRef(obj, throwaway_exception)
end subroutine testint

subroutine testchar(test, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type (Args_Basic_t) :: obj
  integer (kind=sidl_int)  :: test
  logical                         :: bretval
  character (len=1)               :: cretval, out, inout
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = 'A'
  call makeObject(obj, remoteURL)
  call starttest(test)
  cretval = returnbackchar(obj, throwaway_exception)
  call reporttest(cretval .eq. '3', test,  .false.)
  call starttest(test)
  bretval = passinchar(obj, '3', throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  bretval = passoutchar(obj, out, throwaway_exception)
  call reporttest(bretval .and. (out .eq. '3'), test,  &
                  .false.)
  call starttest(test)
  bretval = passinoutchar(obj, inout, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. 'a'), test,  &
                  .false.)
  call starttest(test)
  cretval = passeverywherechar(obj, '3', out, inout, throwaway_exception)
  call reporttest((cretval .eq. '3') .and.  (out .eq. '3') .and. &
                  (inout .eq. 'A'), test,  .false.)
  call deleteRef(obj, throwaway_exception)
end subroutine testchar

subroutine testlong(test, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)  :: test
  logical                         :: bretval
  integer (kind=sidl_long) :: out, inout, iretval, inval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = 3_sidl_long
  call makeObject(obj, remoteURL)
  call starttest(test)
  iretval = returnbacklong(obj, throwaway_exception)
  call reporttest(iretval .eq. 3_sidl_long, test,  .false.)
  call starttest(test)
  inval = 3_sidl_long
  bretval = passinlong(obj, inval, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  bretval = passoutlong(obj, out, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3_sidl_long), test,  .false.)
  call starttest(test)
  bretval = passinoutlong(obj, inout, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3_sidl_long), test,  &
                  .false.)
  call starttest(test)
  inval = 3_sidl_long
  iretval = passeverywherelong(obj, inval, out, inout, throwaway_exception)
  call reporttest((iretval .eq. 3_sidl_long) .and.  &
                  (out .eq. 3_sidl_long) .and. &
                  (inout .eq. 3_sidl_long), test,  .false.)
  call deleteRef(obj, throwaway_exception)
end subroutine testlong

subroutine testfloat(test,  python, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)  :: test
  logical                         :: bretval, python
  real (kind=sidl_float) :: out, inout, fretval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = 3.1_sidl_float
  call makeObject(obj, remoteURL)
  call starttest(test)
  fretval = returnbackfloat(obj, throwaway_exception)
  call reporttest(fretval .eq. 3.1_sidl_float, test,  .false.)
  call starttest(test)
  bretval = passinfloat(obj, 3.1_sidl_float, &
     throwaway_exception)
  call reporttest(bretval, test,  python)
  call starttest(test)
  bretval = passoutfloat(obj, out, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3.1_sidl_float), test,  &
                  .false.)
  call starttest(test)
  bretval = passinoutfloat(obj, inout, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3.1_sidl_float), test,  &
                  .false.)
  call starttest(test)
  fretval = passeverywherefloat(obj, 3.1_sidl_float, out, inout, throwaway_exception)
  call reporttest((fretval .eq. 3.1_sidl_float) .and.  &
                  (out .eq. 3.1_sidl_float) .and. &
                  (inout .eq. 3.1_sidl_float), test,  python)
  call deleteRef(obj, throwaway_exception)
end subroutine testfloat

subroutine testdouble(test, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)    :: test
  logical                           :: bretval
  real (kind=sidl_double) :: out, inout, dretval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = 3.14_sidl_double
  call makeObject(obj, remoteURL)
  call starttest(test)
  dretval =  returnbackdouble(obj, throwaway_exception)
  call reporttest(dretval .eq. 3.14_sidl_double, test,  .false.)
  call starttest(test)
  bretval = passindouble(obj, 3.14_sidl_double, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  bretval = passoutdouble(obj, out, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3.14_sidl_double), test,  &
                  .false.)
  call starttest(test)
  bretval = passinoutdouble(obj, inout, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3.14_sidl_double), test,  &
                  .false.)
  call starttest(test)
  dretval = passeverywheredouble(obj, 3.14_sidl_double, out, inout, throwaway_exception)
  call reporttest((dretval .eq. 3.14_sidl_double) .and.  &
       (out .eq. 3.14_sidl_double) .and. &
       (inout .eq. 3.14_sidl_double), test,  .false.)
  call deleteRef(obj, throwaway_exception)
end subroutine testdouble

subroutine testfcomplex(test,  python, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)     :: test
  logical                            :: bretval, python
  complex (kind=sidl_fcomplex) :: in, out, inout, cretval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  in = (3.1_sidl_float,3.1_sidl_float)
  inout = (3.1_sidl_float, 3.1_sidl_float)
  call makeObject(obj, remoteURL)
  call starttest(test)
  cretval = returnbackfcomplex(obj, throwaway_exception)
  call reporttest(cretval .eq. (3.1_sidl_float,3.1_sidl_float), test, &
        .false.)
  call starttest(test)
  bretval = passinfcomplex(obj, in, throwaway_exception)
  call reporttest(bretval, test,  python)
  call starttest(test)
  bretval = passoutfcomplex(obj, out, throwaway_exception)
  call reporttest(bretval .and. (out .eq. (3.1_sidl_float,3.1_sidl_float)), &
       test,  .false.)
  call starttest(test)
  bretval = passinoutfcomplex(obj, inout, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. (3.1_sidl_float,-3.1_sidl_float)),&
      test, .false.)
  call starttest(test)
  cretval = passeverywherefcomplex(obj, in, out, inout, throwaway_exception)
  call reporttest((cretval .eq. (3.1_sidl_float,3.1_sidl_float)) .and.  (out .eq. (3.1_sidl_float,3.1_sidl_float)) .and. &
                  (inout .eq. (3.1_sidl_float,3.1_sidl_float)), test,  python)
  call deleteRef(obj, throwaway_exception)
end subroutine testfcomplex

subroutine testdcomplex(test, remoteURL)
  use sidl
  use Args_Basic
  use sidl_BaseInterface
  implicit none
  type(Args_Basic_t) :: obj
  integer (kind=sidl_int)       :: test
  logical                              :: bretval
  complex (kind=sidl_dcomplex) :: in, out, inout, cretval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  in = (3.14_sidl_double,3.14_sidl_double)
  inout = (3.14_sidl_double, 3.14_sidl_double)
  call makeObject(obj, remoteURL)
  call starttest(test)
  cretval = returnbackdcomplex(obj, throwaway_exception)
  call reporttest(cretval .eq. (3.14_sidl_double,3.14_sidl_double), test,  &
                  .false.)
  call starttest(test)
  bretval = passindcomplex(obj, in, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  bretval = passoutdcomplex(obj, out, throwaway_exception)
  call reporttest(bretval .and. &
           (out .eq. (3.14_sidl_double,3.14_sidl_double)), test, .false.)
  call starttest(test)
  bretval = passinoutdcomplex(obj, inout, throwaway_exception)
  call reporttest(bretval .and. &
           (inout .eq. (3.14_sidl_double,-3.14_sidl_double)), test, .false.)
  call starttest(test)
  cretval = passeverywheredcomplex(obj, in, out, inout, throwaway_exception)
  call reporttest((cretval .eq. (3.14_sidl_double,3.14_sidl_double)) .and.  &
                  (out .eq. (3.14_sidl_double,3.14_sidl_double)) .and. &
                  (inout .eq. (3.14_sidl_double,3.14_sidl_double)), test,   &
                  .false.)
  call deleteRef(obj, throwaway_exception)
end subroutine testdcomplex

program argstest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use sidl_rmi_ProtocolFactory
  integer (kind=sidl_int) :: test
  integer (kind=sidl_int), parameter :: numparts = 40_sidl_int
  character (len=80)             :: language
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
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

  ! Setup RMI if necessary
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
  if (IArgc() .eq. 1) then
     call GetArg(1, language)
  endif
  test = 1
  call setExpectations(tracker, 40_sidl_int, throwaway_exception)
  call writeComment(tracker, 'Boolean tests', throwaway_exception)
  call testbool(test, remoteURL)
  call writeComment(tracker, 'Character tests', throwaway_exception)
  call testchar(test, remoteURL)
  call writeComment(tracker, 'Integer tests', throwaway_exception)
  call testint(test, remoteURL)
  call writeComment(tracker, 'Long tests', throwaway_exception)
  call testlong(test, remoteURL)
  call writeComment(tracker, 'Float tests', throwaway_exception)
  call testfloat(test,  language .eq. 'Python', remoteURL)
  call writeComment(tracker, 'Double tests', throwaway_exception)
  call testdouble(test, remoteURL)
  call writeComment(tracker, 'Fcomplex tests', throwaway_exception)
  call testfcomplex(test,  language .eq. 'Python', remoteURL)
  call writeComment(tracker, 'Dcomplex tests', throwaway_exception)
  call testDcomplex(test, remoteURL)
  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program argstest
