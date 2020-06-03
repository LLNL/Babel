!
! File:       argstest.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision: 5121 $
! Date:       $Date: 2005-12-09 14:41:13 -0800 (Fri, 09 Dec 2005) $
! Description:Exercise the FORTRAN interface
!
!
#include "wrapper_User_fAbbrev.h"
#include "wrapper_Data_fAbbrev.h"
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

program argstest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use wrapper_User
  use wrapper_Data
  use wrapper_Data_impl
  integer (kind=sidl_int) :: test
  character (len=80)             :: language
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(wrapper_Data_wrap) :: pd
  type(wrapper_Data_wrap) :: dp

  type(wrapper_Data_t) :: data
  type(wrapper_User_t) :: user
  language = ' '
  if (IArgc() .eq. 1) then
     call GetArg(1, language)
  endif
  call getInstance(tracker, throwaway_exception)
  test = 1
  call setExpectations(tracker, 5_sidl_int, throwaway_exception)

  allocate(pd%d_private_data)
  pd%d_private_data%d_int = 0
  pd%d_private_data%d_string = 'wrong!'
  pd%d_private_data%d_ctortest = 'certainly not!'

  call starttest(test)
  call new(user, throwaway_exception)
  call reporttest(not_null(user), test,  .false.)

  call starttest(test)
  call wrapObj(pd, data, throwaway_exception)
  call reporttest(not_null(data), test,  .false.)

  call starttest(test)
  call reporttest(pd%d_private_data%d_ctortest .eq. 'ctor was run', test,  .false.)

  call accept(user, data, throwaway_exception)

  call starttest(test)
  call reporttest(pd%d_private_data%d_string .eq. "Hello World!", test,  .false.)

  call starttest(test)
  call reporttest(pd%d_private_data%d_int .eq. 3, test,  .false.)

  call deleteRef(user, throwaway_exception)
  call deleteRef(data, throwaway_exception)
  ! Private data [should be] deallocated by the Impl dtor.

  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program argstest
