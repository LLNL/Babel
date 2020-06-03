!
! File:       structtest.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision: 6171 $
! Date:       $Date: 2007-10-08 17:39:28 -0600 (Mon, 08 Oct 2007) $
! Description:Exercise the FORTRAN interface
!
!
#include "s_StructTest_fAbbrev.h"
#include "s_Simple_fAbbrev.h"
#include "s_Hard_fAbbrev.h"
#include "s_Empty_fAbbrev.h"
#include "s_Combined_fAbbrev.h"
#include "s_Color_fAbbrev.h"
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

subroutine reporttest(test, number)
  use sidl
  use synch_RegOut
  use synch_ResultType
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: number
  logical                             :: test
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  call getInstance(tracker, throwaway_exception)
  if (test) then
    call endPart(tracker, number, PASS, throwaway_exception)
  else
    call endPart(tracker, number, FAIL, throwaway_exception)
  endif
  call deleteRef(tracker, throwaway_exception)
  number = number + 1
end subroutine reporttest

subroutine my_force_float(f)
  use sidl
  real(kind=sidl_float) :: f
  return
end subroutine my_force_float

subroutine my_force_fcomplex(f)
  use sidl
  complex(kind=sidl_fcomplex) :: f
  return
end subroutine my_force_fcomplex

subroutine makeObject(obj, remoteURL)
  use s_StructTest
  type(s_StructTest_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeObject

subroutine initSimpleClient(s)
  use sidl
  use sidl_NotImplementedException
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  implicit none

  type(s_Simple_t),intent(inout) :: s

  s%d_bool = .true.
  s%d_char = '3'
  s%d_dcomplex = cmplx(3.14_sidl_double,3.14_sidl_double)
  s%d_double = 3.14_sidl_double
  s%d_fcomplex =  cmplx(3.1_sidl_float,3.1_sidl_float)
  s%d_float = 3.1_sidl_float
  s%d_int = 3
  s%d_long = 3
  s%d_opaque = 0
  s%d_enum = blue

end subroutine initSimpleClient

logical function checkSimpleClient(s)
  use sidl
  use sidl_NotImplementedException
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  implicit none

  type(s_Simple_t),intent(in) :: s
  real, parameter :: eps = 1E-6

  if ((s%d_bool .eqv. .true.) .and. &
      (s%d_char .eq. '3' ) .and. &
       (abs(real(s%d_dcomplex) - 3.14_sidl_double) .lt. eps) .and. &
       (abs(aimag(s%d_dcomplex) - 3.14_sidl_double) .lt. eps) .and. &
       (abs(s%d_double - 3.14_sidl_double) .lt. eps ) .and. &
       (abs(s%d_float - 3.1_sidl_float) .lt. eps ) .and. &
       (abs(real(s%d_fcomplex) - 3.1_sidl_float) .lt. eps) .and. &
       (abs(aimag(s%d_fcomplex) - 3.1_sidl_float) .lt. eps) .and. &
       (s%d_int .eq. 3 ) .and. &
       (s%d_long .eq. 3) .and. &
       (s%d_opaque .eq. 0) .and. &
       (s%d_enum .eq. blue )) then
    checkSimpleClient=.true.
  else
    checkSimpleClient=.false.
  endif

  return

end

logical function checkSimpleInvClient(s)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  implicit none

  type(s_Simple_t),intent(in) :: s
  real, parameter :: eps = 1E-6

  if ((s%d_bool .eqv. .false.) .and. &
       (s%d_char .eq. '3') .and. &
       (abs(real(s%d_dcomplex) - 3.14_sidl_double) .lt. eps) .and. &
       (abs(aimag(s%d_dcomplex) + 3.14_sidl_double) .lt. eps) .and. &
       (abs(s%d_double + 3.14_sidl_double) .lt. eps) .and. &
       (abs(s%d_float + 3.1_sidl_float) .lt. eps) .and. &
       (abs(real(s%d_fcomplex) - 3.1_sidl_float) .lt. eps) .and. &
       (abs(aimag(s%d_fcomplex) + 3.1_sidl_float) .lt. eps) .and. &
       (s%d_int .eq. - 3) .and. &
       (s%d_long .eq. - 3) .and. &
       (s%d_opaque .eq. 0) .and. &
       (s%d_enum .eq. red)) then
    checkSimpleInvClient=.true.
  else
    checkSimpleInvClient=.false.
  endif

  return
end

logical function checkHardClient(h)
  use sidl
  use sidl_double_array
  use sidl_NotImplementedException
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass
  use sidl_BaseClass_array
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  use sidl_string_array
  implicit none

  type(s_Hard_t), intent(in) :: h
  integer :: i
  real(kind=sidl_double) d
  character(len=6) :: str
  type(sidl_BaseInterface_t) :: throwaway, ex
  logical :: b
  type(sidl_BaseClass_t) :: bc


  call set_null(ex)
  call set_null(throwaway)

  ! d_array
  if(dimen(h%d_array) .ne. 1 .or. length(h%d_array, 0) .ne. 3) then
     checkHardClient = .false.
     return
  endif

  do i = 0, 2
     call get(h%d_array, i, d)
     if(d .ne. (i + 1) * 1.0) then
        checkHardClient = .false.
        return
     endif
  enddo

  ! d_string
  if(dimen(h%d_string) .ne. 1 .or. length(h%d_string, 0) .ne. 1) then
     checkHardClient = .false.
     return
  endif

  call get(h%d_string, 0, str)
  if(str .ne. 'Three') then
     checkHardClient = .false.
     return
  endif

  ! d_object and d_interface
  if(is_null(h%d_object) .or. is_null(h%d_interface)) then
     checkHardClient = .false.
     return
  endif

  call isSame(h%d_object, h%d_interface, b, ex)
  if(not_null(ex) .or. (b .eqv. .false.)) then
     checkHardClient = .false.
     return
  endif

  ! d_objectArray
  if(dimen(h%d_objectArray) .ne. 1 .or. length(h%d_objectArray, 0) .ne. 3) then
     checkHardClient = .false.
     return
  endif

  do i = 0, 2
     call get(h%d_objectArray, i, bc)
     if(is_null(bc)) then
        checkHardClient = .false.
        return
     endif

     call isType(bc, 'sidl.BaseClass', b, ex)
     if(not_null(ex) .or. (b .eqv. .false.)) then
        checkHardClient = .false.
        return
     endif
  enddo

  checkHardClient=.true.
  return
end

logical function checkHardInvClient(h)
  use sidl
  use sidl_double_array
  use sidl_NotImplementedException
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass
  use sidl_BaseClass_array
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  use sidl_string_array
  implicit none

  type(s_Hard_t), intent(in) :: h
  integer :: i
  real(kind=sidl_double) d
  character(len=6) :: str
  type(sidl_BaseInterface_t) :: throwaway, ex
  logical :: b
  type(sidl_BaseClass_t) :: bc


  call set_null(ex)
  call set_null(throwaway)

  ! d_array
  if(dimen(h%d_array) .ne. 1 .or. length(h%d_array, 0) .ne. 3) then
     checkHardInvClient = .false.
     return
  endif

  do i = 0, 2
     call get(h%d_array, i, d)
     if(d .ne. 3.0 - (i * 1.0)) then
        checkHardInvClient = .false.
        return
     endif
  enddo

  ! d_string
  if(dimen(h%d_string) .ne. 1 .or. length(h%d_string, 0) .ne. 1) then
     checkHardInvClient = .false.
     return
  endif

  call get(h%d_string, 0, str)
  if(str .ne. 'three') then
     checkHardInvClient = .false.
     return
  endif

  ! d_object and d_interface
  if(is_null(h%d_object) .or. is_null(h%d_interface)) then
     checkHardInvClient = .false.
     return
  endif

  call isSame(h%d_object, h%d_interface, b, ex)
  if(not_null(ex) .or. (b .eqv. .true.)) then
     checkHardInvClient = .false.
     return
  endif

  ! d_objectArray
  if(dimen(h%d_objectArray) .ne. 1 .or. length(h%d_objectArray, 0) .ne. 3) then
     checkHardInvClient = .false.
     return
  endif

  do i = 0, 2
     call get(h%d_objectArray, i, bc)
     if(i .eq. 1) then
        if(not_null(bc)) then
           checkHardInvClient=.false.
           return
        endif
     else
        call isType(bc, 'sidl.BaseClass', b, ex)
        if(not_null(ex) .or. (b .eqv. .false.)) then
           checkHardInvClient = .false.
           return
        endif
     endif
  enddo

  checkHardInvClient=.true.
  return
end function checkHardInvClient

subroutine initRarraysClient(r,arr)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest

  type(s_Rarrays_t),intent(inout) :: r
  integer,parameter :: N=3
  integer i
  real(c_double), intent(inout),target :: arr(N)


  r%d_int = N
  do i=1,N
     arr(i)=i
  enddo

  r%d_rarrayFix(1)=5
  r%d_rarrayFix(2)=10
  r%d_rarrayFix(3)=15

  r%d_rarrayRaw = arr

end subroutine initRarraysClient


logical function checkRarraysClient(s)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  implicit none

  type(s_Rarrays_t), intent(in) :: s

  checkRarraysClient=.false.

  if (& !c_associated(s%d_rarrayRaw) .and. &
       (s%d_int .eq. 3) .and. &
       (s%d_rarrayRaw(1) .eq. 1.0) .and. &
       (s%d_rarrayRaw(2) .eq. 2.0) .and. &
       (s%d_rarrayRaw(3) .eq. 3.0) .and. &
       (s%d_rarrayFix(1) .eq. 5.0) .and. &
       (s%d_rarrayFix(2) .eq. 10.0) .and. &
       (s%d_rarrayFix(3) .eq. 15.0)) then
     checkRarraysClient=.true.
  endif

  return

end function checkRarraysClient

logical function checkRarraysInvClient(r)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest

  type(s_Rarrays_t),intent(in) :: r

  checkRarraysInvClient=.false.

  if (& !c_associated(r%d_rarrayRaw) .and.
    (r%d_int .eq. 3) .and. &
    (r%d_rarrayRaw(1) .eq. 3.0) .and. &
    (r%d_rarrayRaw(2) .eq. 2.0) .and. &
    (r%d_rarrayRaw(3) .eq. 1.0) .and. &
    (r%d_rarrayFix(1) .eq. 15.0) .and. &
    (r%d_rarrayFix(2) .eq. 10.0) .and. &
    (r%d_rarrayFix(3) .eq. 5.0)) then
    checkRarraysInvClient=.true.
  endif

  return

end function checkRarraysInvClient


subroutine invertRarraysClient(s)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  implicit none

  type(s_Rarrays_t), intent(inout) :: s
  real(kind=sidl_double) :: temp

 if (associated(s%d_rarrayRaw) .and. &
      associated(s%d_rarrayFix)) then
     temp = s%d_rarrayRaw(1)
     s%d_rarrayRaw(1) = s%d_rarrayRaw(3)
     s%d_rarrayRaw(3) = temp

     temp = s%d_rarrayRaw(1)
     s%d_rarrayRaw(1) = s%d_rarrayRaw(3)
     s%d_rarrayRaw(3) = temp
 endif
end subroutine invertRarraysClient

logical function checkCombinedClient(s)
  use sidl
  use sidl_NotImplementedException
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  implicit none
  type(s_Combined_t), intent(in) :: s
  logical checkSimpleClient
  logical checkHardClient

  checkCombinedClient=.false.
  if (checkSimpleClient(s%d_simple) .and. checkHardClient(s%d_hard)) then
    checkCombinedClient=.true.
  endif

  return

end function checkCombinedClient

logical function checkCombinedInvClient(s)
  use sidl
  use sidl_NotImplementedException
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_Color
  type(s_Combined_t), intent(in) :: s
  logical checkSimpleInvClient
  logical checkHardInvClient

  checkCombinedInvClient=.false.
  if (checkSimpleInvClient(s%d_simple) .and. checkHardInvClient(s%d_hard)) then
    checkCombinedInvClient=.true.
  endif

  return

end function checkCombinedInvClient

subroutine checkEmptyStructs(test,tracker,remoteURL)
  use sidl
  use sidl_bool_array
  use sidl_BaseInterface
  use synch_RegOut
  use s_StructTest
  use s_Empty
  implicit none
  type(synch_RegOut_t),intent(inout) :: tracker
  type(s_StructTest_t) :: obj
  logical ::  retval
  type(sidl_BaseInterface_t) :: throwaway_exception
  integer (kind=sidl_int) :: test
  character (len=256) :: remoteURL

  type(s_Empty_t) :: e1,e2,e3,e4;

  call makeObject(obj,remoteURL)

  call starttest(test)
  call writeComment(tracker, 'not_null(obj)', throwaway_exception)
  call reporttest(not_null(obj), test)

  call starttest(test)
  call writeComment(tracker, 'returnEmpty(obj,e1,throwaway_exception)', throwaway_exception)
  call returnEmpty(obj,e1,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, 'passinEmpty(obj,e1,retval,throwaway_exception)', throwaway_exception)
  call passinEmpty(obj,e1,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutEmpty(obj,e2,retval,throwaway_exception)', throwaway_exception)
  call passoutEmpty(obj,e2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutEmpty(obj,e2,retval,throwaway_exception)', throwaway_exception)
  call passinoutEmpty(obj,e2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutEmpty(obj,e3,retval,throwaway_exception)', throwaway_exception)
  call passoutEmpty(obj,e3,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereEmpty(obj,e1,e2,e3,e4,throwaway_exception)', throwaway_exception)
  call passeverywhereEmpty(obj,e1,e2,e3,e4,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

end subroutine checkEmptyStructs

subroutine checkSimpleStructs(test,tracker,remoteURL)
  use sidl
  use sidl_bool_array
  use sidl_BaseInterface
  use synch_RegOut
  use s_StructTest
  use s_Simple
  implicit none
  integer(kind=sidl_int), intent(inout) :: test
  type(synch_RegOut_t),intent(inout) :: tracker
  type(s_StructTest_t) :: obj
  logical ::  retval
  type(sidl_BaseInterface_t) :: throwaway_exception

  logical checkSimpleClient, checkSimpleInvClient

  type(s_Simple_t) :: s1,s2,s3,s4;
  character (len=256) :: remoteURL

  call makeObject(obj,remoteURL)

  call starttest(test)
  call writeComment(tracker, 'returnSimple(obj,s1,throwaway_exception)', throwaway_exception)
  call returnSimple(obj,s1,throwaway_exception)
  retval=checkSimpleClient(s1)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinSimple(obj,s1,retval,throwaway_exception)', throwaway_exception)
  call passinSimple(obj,s1,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutSimple(obj,s2,retval,throwaway_exception)', throwaway_exception)
  call passoutSimple(obj,s2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutSimple(obj,s2,retval,throwaway_exception)', throwaway_exception)
  call passinoutSimple(obj,s2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkSimpleInvClient(s2)', throwaway_exception)
  retval = checkSimpleInvClient(s2)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutSimple(obj,s3,retval,throwaway_exception)', throwaway_exception)
  call passoutSimple(obj,s3,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereSimple(obj,s1,s2,s3,s4,throwaway_exception)', throwaway_exception)
  call passeverywhereSimple(obj,s1,s2,s3,s4,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, &
       '(checkSimpleClient(s4) .and. checkSimpleClient(s2) .and. checkSimpleInvClient(s3))', &
       throwaway_exception)
  retval=(checkSimpleClient(s4) .and. checkSimpleClient(s2) .and. checkSimpleInvClient(s3))
  call reporttest(retval, test)

end subroutine checkSimpleStructs

subroutine checkHardStructs(test,tracker,remoteURL)
  use sidl
  use sidl_bool_array
  use sidl_BaseInterface
  use synch_RegOut
  use s_StructTest
  use s_Hard
  implicit none
  integer(kind=sidl_int), intent(inout) :: test
  type(synch_RegOut_t),intent(inout) :: tracker
  type(s_StructTest_t) :: obj
  logical ::  retval
  type(sidl_BaseInterface_t) :: throwaway_exception

  logical checkHardClient, checkHardInvClient

  type(s_Hard_t) :: h1,h2,h3,h4;
  character (len=256) :: remoteURL

  call makeObject(obj,remoteURL)

  call starttest(test)
  call writeComment(tracker, 'returnHard(obj,h1,throwaway_exception)', throwaway_exception)
  call returnHard(obj,h1,throwaway_exception)
  retval=checkHardClient(h1)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinHard(obj,h1,retval,throwaway_exception)', throwaway_exception)
  call passinHard(obj,h1,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutHard(obj,h2,retval,throwaway_exception)', throwaway_exception)
  call passoutHard(obj,h2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutHard(obj,h2,retval,throwaway_exception)', throwaway_exception)
  call passinoutHard(obj,h2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkHardInvClient(h2)', throwaway_exception)
  retval = checkHardInvClient(h2)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutHard(obj,h3,retval,throwaway_exception)', throwaway_exception)
  call passoutHard(obj,h3,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereHard(obj,h1,h2,h3,h4,throwaway_exception)', throwaway_exception)
  call passeverywhereHard(obj,h1,h2,h3,h4,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, &
       '(checkHardClient(h4) .and. checkHardClient(h2) .and. checkHardInv(h3))', &
       throwaway_exception)
  retval=(checkHardClient(h4) .and. checkHardClient(h2) .and. checkHardInvClient(h3))
  call reporttest(retval, test)

end subroutine checkHardStructs

subroutine checkRarraysStructs(test,tracker,remoteURL)
  use sidl
  use sidl_bool_array
  use sidl_BaseInterface
  use synch_RegOut
  use s_StructTest
  use s_Rarrays
  implicit none
  integer(kind=sidl_int), intent(inout) :: test
  type(synch_RegOut_t),intent(inout) :: tracker
  type(s_StructTest_t) :: obj
  logical ::  retval
  type(sidl_BaseInterface_t) :: throwaway_exception

  logical checkRarraysClient, checkRarraysInvClient

  type(s_Rarrays_t) :: r1, r2
  real(c_double),dimension(:),target,allocatable :: tempArr1,tempArr2
  integer,parameter :: N=3
  character (len=256) :: remoteURL

  interface initRarraysClient            !  pathf90 insists on this
     subroutine initRarraysClient(r,arr) !  because of the array parameter
       use s_Rarrays
       type(s_Rarrays_t),intent(inout) :: r
       integer,parameter :: N=3
       real(c_double), intent(inout),target :: arr(N)
     end subroutine initRarraysClient
  end interface initRarraysClient

  call makeObject(obj,remoteURL)

  allocate(tempArr1(N))

  call initRarraysClient(r1,tempArr1)
  call starttest(test)
  call writeComment(tracker, 'passinRarrays(obj,r1,retval,throwaway_exception)', throwaway_exception)
  call passinRarrays(obj,r1,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutRarrays(obj,r1,retval,throwaway_exception)', throwaway_exception)
  call passinoutRarrays(obj,r1,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkRarraysInvClient(r1)', throwaway_exception)
  retval = checkRarraysInvClient(r1)
  call reporttest(retval, test)

  deallocate(tempArr1)
  !call deleteRarrays(r1,tempArr1)

  allocate(tempArr1(N))
  allocate(tempArr2(N))

  call initRarraysClient(r1,tempArr1)
  call initRarraysClient(r2,tempArr2)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereRarrays(obj,r1,r2,throwaway_exception)', throwaway_exception)
  call passeverywhereRarrays(obj,r1,r2,retval,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, '(checkRarraysClient(r1) .and. checkRarraysClient(r2))', &
       throwaway_exception)
  retval=(checkRarraysClient(r1) .and. checkRarraysClient(r2))
  call reporttest(retval, test)

  deallocate(tempArr1)
  deallocate(tempArr2)
end subroutine checkRarraysStructs


subroutine checkCombinedStructs(test,tracker,remoteURL)
  use sidl
  use sidl_bool_array
  use sidl_BaseInterface
  use synch_RegOut
  use s_StructTest
  use s_Combined
  implicit none
  integer(kind=sidl_int), intent(inout) :: test
  type(synch_RegOut_t),intent(inout) :: tracker
  type(s_StructTest_t) :: obj
  logical ::  retval
  type(sidl_BaseInterface_t) :: throwaway_exception

  logical checkCombinedClient, checkCombinedInvClient

  type(s_Combined_t) :: c1,c2,c3,c4;
  character (len=256) :: remoteURL

  call makeObject(obj,remoteURL)

  call starttest(test)
  call writeComment(tracker, 'returnCombined(obj,c1,throwaway_exception)', throwaway_exception)
  call returnCombined(obj,c1,throwaway_exception)
  retval=checkCombinedClient(c1)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinCombined(obj,c1,retval,throwaway_exception)', throwaway_exception)
  call passinCombined(obj,c1,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutCombined(obj,c2,retval,throwaway_exception)', throwaway_exception)
  call passoutCombined(obj,c2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutCombined(obj,c2,retval,throwaway_exception)', throwaway_exception)
  call passinoutCombined(obj,c2,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkCombinedInvClient(c2)', throwaway_exception)
  retval = checkCombinedInvClient(c2)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutCombined(obj,c3,retval,throwaway_exception)', throwaway_exception)
  call passoutCombined(obj,c3,retval,throwaway_exception)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereCombined(obj,c1,c2,c3,c4,throwaway_exception)', throwaway_exception)
  call passeverywhereCombined(obj,c1,c2,c3,c4,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, &
       '(checkCombinedClient(c4) .and. checkCombinedClient(c2) & .and. checkCombinedInvClient(c3))', &
       throwaway_exception)
  retval=(checkCombinedClient(c4) .and. checkCombinedClient(c2) .and. checkCombinedInvClient(c3))
  call reporttest(retval, test)

end subroutine checkCombinedStructs

program structtest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  use s_StructTest
  use sidl_rmi_ProtocolFactory
  implicit none
  integer :: IArgc
  integer(kind=sidl_int) :: test, num_tests, i
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(synch_RegOut_t) :: tracker
  character (len=256) :: remoteURL, arg
  logical :: retval

  call getInstance(tracker, throwaway_exception)

  !Parse the command line  to see if we are running RMI tests
  remoteURL = ''
  num_tests = 31_sidl_int
  do i = 1, IArgc()
     call GetArg(i, arg)
     if ( arg(1:6) .eq. '--url=' ) then
        remoteURL = arg(7:)
        num_tests = 15_sidl_int
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

  test=1
  call setExpectations(tracker,num_tests, throwaway_exception)
  call writeComment(tracker, 'Empty Struct Tests', throwaway_exception)
  call checkEmptyStructs(test,tracker,remoteURL)

  call writeComment(tracker, 'Simple Struct Tests', throwaway_exception)
  call checkSimpleStructs(test,tracker,remoteURL)

  !some elements in s.Hard can't be passed as they are not
  !serializable, so skip these tests for RMI
  if ( remoteURL .eq. '' ) then
     call writeComment(tracker, 'Hard Struct Tests', throwaway_exception)
     call checkHardStructs(test,tracker,remoteURL)

     call writeComment(tracker, 'Combined Struct Tests', throwaway_exception)
     call checkCombinedStructs(test,tracker,remoteURL)
  endif

  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program structtest
