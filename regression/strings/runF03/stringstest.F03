!
! File:       stringstest.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision$
! Date:       $Date$
! Description:Exercise the FORTRAN interface
!

#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
#include "Strings_Cstring_fAbbrev.h"
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

subroutine makeObject(obj, remoteURL)
  use Strings_Cstring
  type(Strings_Cstring_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeObject

subroutine teststring(test, remoteURL)
  use sidl
  use Strings_Cstring
  use sidl_BaseInterface
  implicit none
  type(Strings_Cstring_t)         :: obj
  type(sidl_BaseInterface_t) :: throwaway_exception
  integer (kind=sidl_int)  :: test
  logical                         :: retval
  character (len=80)              :: in, inout, out, sreturn
  character (len=1)               :: ch1, ch2
  character (len=256) :: remoteURL

  call makeObject(obj, remoteURL)
  sreturn = 'Not three'
  call starttest(test)
  sreturn = returnback(obj, .true., throwaway_exception)
  call reporttest(sreturn .eq. 'Three', test)
  retval = .false.
  call starttest(test)
  retval = passin(obj, 'Three', throwaway_exception)
  call reporttest(retval, test)
  in = 'Three'
  call starttest(test)
  retval = passin(obj, in, throwaway_exception)
  call reporttest(retval, test)
  call starttest(test)
  retval =  passin(obj, 'Four', throwaway_exception)
  call reporttest(.not. retval, test)
  out = 'Not three'
  call starttest(test)
  retval = passout(obj, .true., out, throwaway_exception)
  call reporttest(retval .and. out .eq. 'Three', test)
  inout = 'Three'
  call starttest(test)
  retval = passinout(obj, inout, throwaway_exception)
  call reporttest(retval .and. inout .eq. 'threes', test)
  call starttest(test)
  sreturn = passeverywhere(obj, 'Three', out, inout, throwaway_exception)
  call reporttest(sreturn .eq. 'Three' .and. out .eq. 'Three' .and. &
       inout .eq. 'Three', test)
  call starttest(test)
  retval = mixedarguments(obj, 'Test', 'z', 'Test', 'z', throwaway_exception)
  call reporttest(retval, test)
  call starttest(test)
  retval = mixedarguments(obj, 'Not', 'A', 'Equal', 'a', throwaway_exception)
  call reporttest(.not. retval, test)
  ch1 = 'z'
  ch2 = 'z'
  call starttest(test)
  retval = mixedarguments(obj, 'Test', ch1, 'Test', ch1, throwaway_exception)
  call reporttest(retval, test)
  call starttest(test)
  retval = mixedarguments(obj, 'Test', ch1, 'Test', ch2, throwaway_exception)
  call reporttest(retval, test)
  ch2 = 'A'
  call starttest(test)
  retval = mixedarguments(obj, 'Not', ch1, 'Equal', ch2, throwaway_exception)
  call reporttest(.not. retval, test)
  call deleteRef(obj, throwaway_exception)

end subroutine teststring

program stringstest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use sidl_rmi_ProtocolFactory
  integer (kind=sidl_int) :: test
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(synch_RegOut_t) :: tracker
  character (len=256) :: remoteURL, arg
  integer :: i
  logical :: retval

  test = 1
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

  call setExpectations(tracker, 12_sidl_int, throwaway_exception)
  call writeComment(tracker, 'String tests', throwaway_exception)
  call teststring(test, remoteURL)
  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program stringstest
