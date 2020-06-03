!
! File:       overloadtest.F03
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision$
! Date:       $Date$
! Description:Simple F03 overload test client
!
!

#include "Overload_AClass_fAbbrev.h"
#include "Overload_AnException_fAbbrev.h"
#include "Overload_BClass_fAbbrev.h"
#include "Overload_ParentTest_fAbbrev.h"
#include "Overload_Test_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"

subroutine starttest(number)
  use sidl
  use synch_RegOut
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

subroutine makeOverloadTest(obj, remoteURL)
  use Overload_Test
  type(Overload_Test_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeOverloadTest

subroutine makeException(obj, remoteURL)
  use Overload_AnException
  type(Overload_AnException_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeException

subroutine makeAClass(obj, remoteURL)
  use Overload_AClass
  type(Overload_AClass_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeAClass

subroutine makeBClass(obj, remoteURL)
  use Overload_BClass
  type(Overload_BClass_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeBClass


subroutine testnone(t, test, remoteURL)
  use sidl
  use Overload_Test
  implicit none
  integer (kind=sidl_int)  :: test
  type(Overload_Test_t) :: t
  integer (kind=sidl_int)  :: retval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character*256 remoteURL

  call starttest(test)
  retval = getValue(t, throwaway_exception)
  call reporttest(retval .eq. 1, test)
end subroutine testnone

subroutine testone(t, test, remoteURL)
  use sidl
  use Overload_Test
  use Overload_AnException
  use Overload_AClass
  use Overload_BClass
  implicit none
  character*80 s1, sretval
  integer (kind=sidl_int)       :: test
  type(Overload_Test_t)                :: t
  type(Overload_AnException_t)         :: ae
  type(Overload_AClass_t)              :: ac
  type(Overload_BClass_t)              :: bc
  integer (kind=sidl_int)       :: i1, iretval
  real (kind=sidl_double)    :: d1, dretval
  real (kind=sidl_float)      :: f1, fretval
  logical                              :: b1, bretval
  complex (kind=sidl_fcomplex)   :: fc, fcretval
  complex (kind=sidl_dcomplex) :: dc, dcretval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character*256 remoteURL

  b1 = .true.
  d1 = 1.0d0
  f1 = 1.0
  i1 = 1
  fc = (1.1, 1.1)
  dc = (2.2d0, 2.2d0)
  s1 = 'AnException'

  call starttest(test)
  bretval = getValue(t, b1, throwaway_exception)
  call reporttest(bretval .eqv. b1, test)
  call starttest(test)
  dretval = getValue(t, d1, throwaway_exception)
  call reporttest(dretval .eq. d1, test)
  call starttest(test)
  dcretval = getValue(t, dc, throwaway_exception)
  call reporttest(dcretval .eq. dc, test)
  call starttest(test)
  fretval = getValue(t, f1, throwaway_exception)
  call reporttest(fretval .eq. f1, test)
  call starttest(test)
  fcretval = getValue(t, fc, throwaway_exception)
  call reporttest(fcretval .eq. fc, test)
  call starttest(test)
  ! this syntax is necessary to work around a bug in ifort
  iretval = getValue(t, i1, throwaway_exception)
  call reporttest(iretval .eq. i1, test)
  call starttest(test)
  sretval = getValue(t, s1, throwaway_exception)
  call reporttest(sretval .eq. s1, test)

  ! FIXME ae = makeException(remoteURL)
  call makeException(ae, remoteURL)
  s1 = 'XXX'
  s1 = getNote(ae, throwaway_exception)
  call starttest(test)
  sretval = getValue(t, ae, throwaway_exception)
  call reporttest(sretval .eq. s1, test)
  call deleteRef(ae, throwaway_exception)
  call makeAClass(ac, remoteURL)
  call starttest(test)
  iretval = getValue(t, ac, throwaway_exception)
  call reporttest(iretval .eq. 2, test)
  call deleteRef(ac, throwaway_exception)
  call makeBClass(bc, remoteURL)
  call starttest(test)
  iretval = getValue(t, bc, throwaway_exception)
  call reporttest(iretval .eq. 2, test)
  call deleteRef(bc, throwaway_exception)
end subroutine testone

subroutine testtwo(t, test, remoteURL)
  use sidl
  use Overload_Test
  implicit none
  integer (kind=sidl_int)    :: test
  type(Overload_Test_t)             :: t
  integer (kind=sidl_int)    :: i1, iretval
  real (kind=sidl_double) :: d1, dretval, did
  real (kind=sidl_float)   :: f1
  type(sidl_BaseInterface_t) :: throwaway_exception
  character*256 remoteURL

  d1 = 1.0d0
  i1 = 1
  did =2.0d0

  call starttest(test)
  dretval = getValue(t, d1, i1, throwaway_exception)
  call reporttest(dretval .eq. did, test)
  call starttest(test)
  dretval = getValue(t, i1, d1, throwaway_exception)
  call reporttest(dretval .eq. did, test)
end subroutine testtwo

subroutine testthree(t, test, remoteURL)
  use sidl
  use Overload_Test
  implicit none
  type(Overload_Test_t)             :: t
  integer (kind=sidl_int)    :: i1
  integer (kind=sidl_int)    :: test
  real (kind=sidl_double) :: d1, difd, dretval
  real (kind=sidl_float)   :: f1
  type(sidl_BaseInterface_t) :: throwaway_exception
  character*256 remoteURL

  d1 = 1.0d0
  f1 = 1.0
  i1 = 1
  difd = 3.0d0

  call starttest(test)
  dretval = getValue(t, d1, i1, f1, throwaway_exception)
  call reporttest(dretval .eq. difd, test)
  call starttest(test)
  dretval = getValue(t, i1, d1, f1, throwaway_exception)
  call reporttest(dretval .eq. difd, test)
  call starttest(test)
  dretval = getValue(t, d1, f1, i1, throwaway_exception)
  call reporttest(dretval .eq.  difd, test)
  call starttest(test)
  dretval = getValue(t, i1, f1, d1, throwaway_exception)
  call reporttest(dretval .eq. difd, test)
  call starttest(test)
  dretval = getValue(t, f1, d1, i1, throwaway_exception)
  call reporttest(dretval .eq. difd, test)
  call starttest(test)
  dretval = getValue(t, f1, i1, d1, throwaway_exception)
  call reporttest(dretval .eq. difd, test)
end subroutine testthree


program overloadtest
  use sidl
  use Overload_Test
  use synch_RegOut
  use sidl_rmi_ProtocolFactory
  implicit none
  integer (kind=sidl_int)  :: test
  type(Overload_Test_t) :: t
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL, arg
  integer :: i,IArgc
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

  call setExpectations(tracker, 19_sidl_int, throwaway_exception)
  call makeOverloadTest(t, remoteURL)

  test = 1

  call writeComment(tracker, 'No Argument test             ', throwaway_exception)
  call testnone(t, test, remoteURL)
  call writeComment(tracker, 'Single Argument tests        ', throwaway_exception)
  call testone(t, test, remoteURL)
  call writeComment(tracker, 'Double Argument tests        ', throwaway_exception)
  call testtwo(t, test, remoteURL)
  call writeComment(tracker, 'Triple Argument tests        ', throwaway_exception)
  call testthree(t, test, remoteURL)

  call deleteRef(t, throwaway_exception)

  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program overloadtest
