!
! File:       argstest.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision: 6796 $
! Date:       $Date: 2009-11-13 17:30:04 -0800 (Fri, 13 Nov 2009) $
! Description:Exercise the FORTRAN interface
!
!
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
  call getInstance(tracker, throwaway_exception)
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
  call getInstance(tracker, throwaway_exception)
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
  logical                         :: out, inout, retval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = .true.
  call makeObject(obj, remoteURL)
  call starttest(test)
  call returnbackbool(obj, retval, throwaway_exception)
  call reporttest(retval, test,  .false.)
  call starttest(test)
  call passinbool(obj, .true., retval, throwaway_exception)
  call reporttest(retval, test,  .false.)
  call starttest(test)
  call passoutbool(obj, out, retval, throwaway_exception)
  call reporttest(retval .and. out, test,  .false.)
  call starttest(test)
  call passinoutbool(obj, inout, retval, throwaway_exception)
  call reporttest(retval .and. .not. inout, test,  .false.)
  call starttest(test)
  call passeverywherebool(obj, .true., out, inout, retval, throwaway_exception)
  call reporttest(retval .and. out .and. inout, test,  &
                  .false.)
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
  call returnbackint(obj, iretval, throwaway_exception)
  call reporttest(iretval .eq. 3_sidl_int, test,  .false.)
  call starttest(test)
  call passinint(obj, 3_sidl_int, bretval, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  call passoutint(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3_sidl_int), test,  .false.)
  call starttest(test)
  call passinoutint(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3_sidl_int), test,  &
                  .false.)
  call starttest(test)
  call passeverywhereint(obj, 3_sidl_int, out, inout, iretval, &
    throwaway_exception)
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
  call returnbackchar(obj, cretval, throwaway_exception)
  call reporttest(cretval .eq. '3', test,  .false.)
  call starttest(test)
  call passinchar(obj, '3', bretval, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  call passoutchar(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. (out .eq. '3'), test,  &
                  .false.)
  call starttest(test)
  call passinoutchar(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. 'a'), test,  &
                  .false.)
  call starttest(test)
  call passeverywherechar(obj, '3', out, inout, cretval, throwaway_exception)
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
  call returnbacklong(obj, iretval, throwaway_exception)
  call reporttest(iretval .eq. 3_sidl_long, test,  .false.)
  call starttest(test)
  inval = 3_sidl_long
  call passinlong(obj, inval, bretval, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  call passoutlong(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3_sidl_long), test,  .false.)
  call starttest(test)
  call passinoutlong(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3_sidl_long), test,  &
                  .false.)
  call starttest(test)
  inval = 3_sidl_long
  call passeverywherelong(obj, inval, out, inout, iretval, throwaway_exception)
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
  call returnbackfloat(obj, fretval, throwaway_exception)
  call reporttest(fretval .eq. 3.1_sidl_float, test,  .false.)
  call starttest(test)
  call passinfloat(obj, 3.1_sidl_float, bretval, &
     throwaway_exception)
  call reporttest(bretval, test,  python)
  call starttest(test)
  call passoutfloat(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3.1_sidl_float), test,  &
                  .false.)
  call starttest(test)
  call passinoutfloat(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3.1_sidl_float), test,  &
                  .false.)
  call starttest(test)
  call passeverywherefloat(obj, 3.1_sidl_float, out, inout, fretval, &
      throwaway_exception)
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
  call returnbackdouble(obj, dretval, throwaway_exception)
  call reporttest(dretval .eq. 3.14_sidl_double, test,  .false.)
  call starttest(test)
  call passindouble(obj, 3.14_sidl_double, bretval, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  call passoutdouble(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. (out .eq. 3.14_sidl_double), test,  &
                  .false.)
  call starttest(test)
  call passinoutdouble(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. -3.14_sidl_double), test,  &
                  .false.)
  call starttest(test)
  call passeverywheredouble(obj, 3.14_sidl_double, out, inout, dretval, &
      throwaway_exception)
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
  call returnbackfcomplex(obj, cretval, throwaway_exception)
  call reporttest(cretval .eq. (3.1_sidl_float,3.1_sidl_float), test, &
        .false.)
  call starttest(test)
  call passinfcomplex(obj, in, bretval, throwaway_exception)
  call reporttest(bretval, test,  python)
  call starttest(test)
  call passoutfcomplex(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. (out .eq. (3.1_sidl_float,3.1_sidl_float)), &
      test,  .false.)
  call starttest(test)
  call passinoutfcomplex(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. (inout .eq. (3.1_sidl_float,-3.1_sidl_float)),&
      test, .false.)
  call starttest(test)
  call passeverywherefcomplex(obj, in, out, inout, cretval, &
       throwaway_exception)
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
  call returnbackdcomplex(obj, cretval, throwaway_exception)
  call reporttest(cretval .eq. (3.14_sidl_double,3.14_sidl_double), test,  &
                  .false.)
  call starttest(test)
  call passindcomplex(obj, in, bretval, throwaway_exception)
  call reporttest(bretval, test,  .false.)
  call starttest(test)
  call passoutdcomplex(obj, out, bretval, throwaway_exception)
  call reporttest(bretval .and. &
           (out .eq. (3.14_sidl_double,3.14_sidl_double)), test, .false.)
  call starttest(test)
  call passinoutdcomplex(obj, inout, bretval, throwaway_exception)
  call reporttest(bretval .and. &
           (inout .eq. (3.14_sidl_double,-3.14_sidl_double)), test, .false.)
  call starttest(test)
  call passeverywheredcomplex(obj, in, out, inout, cretval, &
           throwaway_exception)
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

  call getInstance(tracker, throwaway_exception)

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
     call addProtocol('simhandle', 'sidlx.rmi.SimHandle', retval, throwaway_exception)
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
