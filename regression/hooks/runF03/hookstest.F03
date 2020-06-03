!
! File:       argstest.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision: 5121 $
! Date:       $Date: 2005-12-09 14:41:13 -0800 (Fri, 09 Dec 2005) $
! Description:Exercise the FORTRAN interface
!
!
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
#include "hooks_Basics_fAbbrev.h"
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
  use hooks_Basics
  type(hooks_Basics_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeObject

subroutine testhooks(test, remoteURL)
  use sidl
  use hooks_Basics
  implicit none
  type(hooks_Basics_t) :: obj
  integer (kind=sidl_int)  :: test, ret, b, c
  logical                  :: out, inout, retval
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=256) :: remoteURL

  inout = .true.
  call makeObject(obj, remoteURL)
  !
  ! Until the overloading issue can be addressed, the following approach
  ! must be used to enable hooks execution.
  !
  call hooks_Basics__set_hooks_static(inout, throwaway_exception)
  inout = .true.
  call set_hooks(obj, inout, throwaway_exception)

  test = 1
  b = -1
  c = -1
  ret = 0

  call starttest(test)
  ret = aStaticMeth(test,b,c, throwaway_exception)
  call reporttest(b .eq. 1 .and. c .eq. 0, test, .false.)

  call starttest(test)
  ret = aStaticMeth(test,b,c, throwaway_exception)
  call reporttest(b .eq. 2 .and. c .eq. 1, test, .false.)

  b = -1
  c = -1
  ret = 0

  call starttest(test)
  ret = aNonStaticMeth(obj, test,b,c, throwaway_exception)
  call reporttest(b .eq. 1 .and. c .eq. 0, test, .false.)

  call starttest(test)
  ret = aNonStaticMeth(obj, test,b,c, throwaway_exception)
  call reporttest(b .eq. 2 .and. c .eq. 1, test, .false.)
      
  call deleteRef(obj, throwaway_exception)
end subroutine testhooks

program hookstest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use sidl_rmi_ProtocolFactory
  integer (kind=sidl_int) :: test
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

  test = 1
  call setExpectations(tracker, 4_sidl_int, throwaway_exception)
  call writeComment(tracker, 'hooks tests', throwaway_exception)
  call testhooks(test, remoteURL)
  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program hookstest
