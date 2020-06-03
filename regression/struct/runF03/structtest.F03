
!
! File:       structtest.F03
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision: 6171 $
! Date:       $Date: 2007-10-08 17:39:28 -0600 (Mon, 08 Oct 2007) $
! Description:Exercise the FORTRAN interface
!
!

#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "s_StructTest_fAbbrev.h"
#include "s_Simple_fAbbrev.h"
#include "s_Hard_fAbbrev.h"
#include "s_Empty_fAbbrev.h"
#include "s_Rarrays_fAbbrev.h"
#include "s_Combined_fAbbrev.h"
#include "s_Color_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"
#include "s_Color_fAbbrev.h"
#include "sidl_double_fAbbrev.h"
#include "sidl_string_fAbbrev.h"

subroutine starttest(number)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: number
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  ! test the function interface
  tracker = getInstance(throwaway_exception)
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
  ! test the procedure interface, too
  tracker = getInstance(throwaway_exception)
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
  use s_Simple
  use sidl_BaseInterface
  use s_StructTest
  use s_Color
  implicit none

  type(s_Simple_t),intent(inout) :: s
  complex(kind=sidl_dcomplex) :: dc
  dc = cmplx(3.14_sidl_double,3.14_sidl_double)

  call set_d_bool(s, .true.)
  call set_d_char(s, '3')
  call set_d_dcomplex(s, dc)
  call set_d_double(s, 3.14_sidl_double)
  call set_d_fcomplex(s, cmplx(3.1_sidl_float,3.1_sidl_float))
  call set_d_float(s, 3.1_sidl_float)
  call set_d_int(s, 3)
  call set_d_long(s, 3_sidl_long)
  call set_d_opaque(s, sidl_opaque_null)
  call set_d_enum(s, blue)

end subroutine initSimpleClient

logical function checkSimpleClient(s)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use s_StructTest
  use s_Color
  implicit none

  type(s_Simple_t),intent(in) :: s
  real, parameter :: eps = 1E-6

  if ((get_d_bool(s) .eqv. .true.) .and. &
      (get_d_char(s) .eq. '3' ) .and. &
       (abs(real(get_d_dcomplex(s)) - 3.14_sidl_double) .lt. eps) .and. &
       (abs(aimag(get_d_dcomplex(s)) - 3.14_sidl_double) .lt. eps) .and. &
       (abs(get_d_double(s) - 3.14_sidl_double) .lt. eps ) .and. &
       (abs(get_d_float(s) - 3.1_sidl_float) .lt. eps ) .and. &
       (abs(real(get_d_fcomplex(s)) - 3.1_sidl_float) .lt. eps) .and. &
       (abs(aimag(get_d_fcomplex(s)) - 3.1_sidl_float) .lt. eps) .and. &
       (get_d_int(s) .eq. 3 ) .and. &
       (get_d_long(s) .eq. 3) .and. &
       (is_null(get_d_opaque(s))) .and. &
       (get_d_enum(s) .eq. blue)) then
    checkSimpleClient=.true.
  else
    checkSimpleClient=.false.
  endif

  return

end function checkSimpleClient

logical function checkSimpleInvClient(s)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use s_StructTest
  use s_Color
  implicit none

  type(s_Simple_t),intent(in) :: s
  real, parameter :: eps = 1E-6

  if ((get_d_bool(s) .eqv. .false.) .and. &
       (get_d_char(s) .eq. '3') .and. &
       (abs(real(get_d_dcomplex(s)) - 3.14_sidl_double) .lt. eps) .and. &
       (abs(aimag(get_d_dcomplex(s)) + 3.14_sidl_double) .lt. eps) .and. &
       (abs(get_d_double(s) + 3.14_sidl_double) .lt. eps) .and. &
       (abs(get_d_float(s) + 3.1_sidl_float) .lt. eps) .and. &
       (abs(real(get_d_fcomplex(s)) - 3.1_sidl_float) .lt. eps) .and. &
       (abs(aimag(get_d_fcomplex(s)) + 3.1_sidl_float) .lt. eps) .and. &
       (get_d_int(s) .eq. - 3) .and. &
       (get_d_long(s) .eq. - 3) .and. &
       (is_null(get_d_opaque(s))) .and. &
       (get_d_enum(s) .eq. red)) then
    checkSimpleInvClient=.true.
  else
    checkSimpleInvClient=.false.
  endif

  return

end function checkSimpleInvClient

logical function checkHardClient(h)
  use sidl
  use sidl_double_array
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass_array
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
  if(dimen(get_d_array(h)) .ne. 1 .or. length(get_d_array(h), 0) .ne. 3) then
     checkHardClient = .false.
     return
  endif

  do i = 0, 2
     call get(get_d_array(h), i, d)
     if(d .ne. (i + 1) * 1.0) then
        checkHardClient = .false.
        return
     endif
  enddo

  ! d_string
  if(dimen(get_d_string(h)) .ne. 1 .or. length(get_d_string(h), 0) .ne. 1) then
     checkHardClient = .false.
     return
  endif

  call get(get_d_string(h), 0, str)
  if(str .ne. 'Three') then
     checkHardClient = .false.
     return
  endif
  
  ! d_object and d_interface
  if(is_null(get_d_object(h)) .or. is_null(get_d_interface(h))) then
     checkHardClient = .false.
     return
  endif
  
  b = isSame(get_d_object(h), get_d_interface(h), ex)
  if(not_null(ex) .or. (b .eqv. .false.)) then
     checkHardClient = .false.
     return
  endif
  
  ! d_objectArray
  if(dimen(get_d_objectArray(h)) .ne. 1 .or. length(get_d_objectArray(h), 0) .ne. 3) then
     checkHardClient = .false.
     return
  endif

  do i = 0, 2
     call get(get_d_objectArray(h), i, bc)
     if(is_null(bc)) then
        checkHardClient = .false.
        return
     endif

     b = isType(bc, 'sidl.BaseClass', ex)
     if(not_null(ex) .or. (b .eqv. .false.)) then
        checkHardClient = .false.
        return
     endif
  enddo

  checkHardClient=.true.
  return
end function checkHardClient

logical function checkHardInvClient(h)
  use sidl
  use sidl_double_array
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass_array
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
  if(dimen(get_d_array(h)) .ne. 1 .or. length(get_d_array(h), 0) .ne. 3) then
     checkHardInvClient = .false.
     return
  endif

  do i = 0, 2
     call get(get_d_array(h), i, d)
     if(d .ne. 3.0 - (i * 1.0)) then
        checkHardInvClient = .false.
        return
     endif
  enddo

  ! d_string
  if(dimen(get_d_string(h)) .ne. 1 .or. length(get_d_string(h), 0) .ne. 1) then
     checkHardInvClient = .false.
     return
  endif

  call get(get_d_string(h), 0, str)
  if(str .ne. 'three') then
     checkHardInvClient = .false.
     return
  endif
  
  ! d_object and d_interface
  if(is_null(get_d_object(h)) .or. is_null(get_d_interface(h))) then
     checkHardInvClient = .false.
     return
  endif
  
  b = isSame(get_d_object(h), get_d_interface(h), ex)
  if(not_null(ex) .or. (b .eqv. .true.)) then
     checkHardInvClient = .false.
     return
  endif
  
  ! d_objectArray
  if(dimen(get_d_objectArray(h)) .ne. 1 .or. length(get_d_objectArray(h), 0) .ne. 3) then
     checkHardInvClient = .false.
     return
  endif

  do i = 0, 2
     call get(get_d_objectArray(h), i, bc)
     if(i .eq. 1) then
        if(not_null(bc)) then
           checkHardInvClient=.false.
           return
        endif
     else
        b = isType(bc, 'sidl.BaseClass', ex)
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
  use,intrinsic :: iso_c_binding
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest

  type(s_Rarrays_t),intent(inout) :: r
  integer,parameter :: N=3
  integer i 
  real(c_double), intent(inout) :: arr(N)

  call set_d_int(r, N)
  do i=1,N
     arr(i)=i
  enddo

  r%d_rarrayFix(1)=5 
  r%d_rarrayFix(2)=10 
  r%d_rarrayFix(3)=15 

  call set_d_rarrayRaw(r, arr)

end subroutine initRarraysClient

logical function checkRarraysClient(r)
  use,intrinsic :: iso_c_binding
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest

!  logical c_associated
  type(s_Rarrays_t),intent(in) :: r

  real(kind=sidl_double), dimension(:), pointer :: r_d_rarrayRaw_fptr

  ! FIXME:
  !
  ! We probably don't want the user to access those fields but for
  ! strange reasons a call to a function performing this actions
  ! results in a segfault
  ! c.f. StructModule.java, rarrays.F03
  call c_f_pointer(r%d_rarrayRaw, r_d_rarrayRaw_fptr, [3])
  !r_d_rarrayRaw_fptr = get_d_rarrayRaw(r)

  checkRarraysClient=.false.

  if (& !c_associated(r%d_rarrayRaw) .and.
    (get_d_int(r) .eq. 3) .and. &
    (r_d_rarrayRaw_fptr(1) .eq. 1.0) .and. &
    (r_d_rarrayRaw_fptr(2) .eq. 2.0) .and. &
    (r_d_rarrayRaw_fptr(3) .eq. 3.0) .and. &
    (r%d_rarrayFix(1) .eq. 5.0) .and. &
    (r%d_rarrayFix(2) .eq. 10.0) .and. &
    (r%d_rarrayFix(3) .eq. 15.0)) then
    checkRarraysClient=.true.
  endif

  return

end function checkRarraysClient

logical function checkRarraysInvClient(r)
  use,intrinsic :: iso_c_binding
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest

  type(s_Rarrays_t),intent(in) :: r

  real(kind=sidl_double), dimension(:), pointer :: r_d_rarrayRaw_fptr

  ! FIXME:
  !
  ! We probably don't want the user to access those fields but for
  ! strange reasons a call to a function performing this actions
  ! results in a segfault
  ! c.f. StructModule.java, rarrays.F03
  call c_f_pointer(r%d_rarrayRaw, r_d_rarrayRaw_fptr, [3])
  !r_d_rarrayRaw_fptr = get_d_rarrayRaw(r)

  checkRarraysInvClient=.false.

  if (& !c_associated(r%d_rarrayRaw) .and.
    (get_d_int(r) .eq. 3) .and. &
    (r_d_rarrayRaw_fptr(1) .eq. 3.0) .and. &
    (r_d_rarrayRaw_fptr(2) .eq. 2.0) .and. &
    (r_d_rarrayRaw_fptr(3) .eq. 1.0) .and. &
    (r%d_rarrayFix(1) .eq. 15.0) .and. &
    (r%d_rarrayFix(2) .eq. 10.0) .and. &
    (r%d_rarrayFix(3) .eq. 5.0)) then
    checkRarraysInvClient=.true.
  endif

  return

end function checkRarraysInvClient

subroutine deleteRarrays(r)
  use,intrinsic :: iso_c_binding
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest

  type(s_Rarrays_t),intent(inout) :: r
  integer,parameter :: N=3
  integer i 

  !
  ! Not really deleting anything since the deallocates happens in main, this
  ! just resets stuff.
  !

  call set_d_int(r, -99)

  r%d_rarrayFix(1)=-99
  r%d_rarrayFix(2)=-99 
  r%d_rarrayFix(3)=-99 
  
  r%d_rarrayRaw = c_null_ptr

end subroutine deleteRarrays


logical function checkCombinedClient(s)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use s_StructTest
  use s_Color
  implicit none
  type(s_Combined_t), intent(in) :: s
  logical checkSimpleClient
  logical checkHardClient
  logical checkRarraysClient

  checkCombinedClient=.false.
  if (checkSimpleClient(get_d_simple(s)) .and. &
       checkHardClient(get_d_hard(s))) then
     checkCombinedClient=.true.
  endif

  return
end function checkCombinedClient

logical function checkCombinedInvClient(s)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use s_StructTest
  use s_Color
  implicit none
  type(s_Combined_t), intent(in) :: s
  logical checkSimpleInvClient
  logical checkHardInvClient
  logical checkRarraysInvClient

  checkCombinedInvClient=.false.
  if (checkSimpleInvClient(get_d_simple(s)) .and. &
       checkHardInvClient(get_d_hard(s))) then
    checkCombinedInvClient=.true.
  endif

  return
end function checkCombinedInvClient

subroutine checkEmptyStructs(test,tracker,remoteURL)
  use sidl
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
  e1 = returnEmpty(obj, throwaway_exception) 
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, 'passinEmpty(obj,e1,throwaway_exception)', throwaway_exception)
  retval = passinEmpty(obj,e1,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutEmpty(obj,e2,throwaway_exception)', throwaway_exception)
  retval = passoutEmpty(obj,e2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutEmpty(obj,e2,throwaway_exception)', throwaway_exception)
  retval = passinoutEmpty(obj,e2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutEmpty(obj,e3,throwaway_exception)', throwaway_exception)
  retval = passoutEmpty(obj,e3,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereEmpty(obj,e1,e2,e3,throwaway_exception)', throwaway_exception)
  e4 = passeverywhereEmpty(obj,e1,e2,e3,throwaway_exception) 
  call reporttest(is_null(throwaway_exception), test)

end subroutine checkEmptyStructs

subroutine checkSimpleStructs(test,tracker,remoteURL)
  use sidl
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
  call writeComment(tracker, 'returnSimple(obj,throwaway_exception)', throwaway_exception)
  s1 = returnSimple(obj,throwaway_exception) 
  retval=checkSimpleClient(s1)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinSimple(obj,s1,throwaway_exception)', throwaway_exception)
  retval = passinSimple(obj,s1,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutSimple(obj,s2,throwaway_exception)', throwaway_exception)
  retval = passoutSimple(obj,s2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutSimple(obj,s2,throwaway_exception)', throwaway_exception)
  retval = passinoutSimple(obj,s2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkSimpleInvClient(s2)', throwaway_exception)
  retval = checkSimpleInvClient(s2) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutSimple(obj,s3,throwaway_exception)', throwaway_exception)
  retval = passoutSimple(obj,s3,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereSimple(obj,s1,s2,s3,throwaway_exception)', throwaway_exception)
  s4 = passeverywhereSimple(obj,s1,s2,s3,throwaway_exception) 
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
  call writeComment(tracker, 'returnHard(obj,throwaway_exception)', throwaway_exception)
  h1 = returnHard(obj,throwaway_exception) 
  retval=checkHardClient(h1)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinHard(obj,h1,throwaway_exception)', throwaway_exception)
  retval = passinHard(obj,h1,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutHard(obj,h2,throwaway_exception)', throwaway_exception)
  retval = passoutHard(obj,h2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutHard(obj,h2,throwaway_exception)', throwaway_exception)
  retval = passinoutHard(obj,h2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkHardInvClient(h2)', throwaway_exception)
  retval = checkHardInvClient(h2) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutHard(obj,h3,throwaway_exception)', throwaway_exception)
  retval = passoutHard(obj,h3,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereHard(obj,h1,h2,h3,throwaway_exception)', throwaway_exception)
  h4 = passeverywhereHard(obj,h1,h2,h3,throwaway_exception) 
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, &
       '(checkHardClient(h4) .and. checkHardClient(h2) .and. checkHardInv(h3))', &
       throwaway_exception)
  retval=(checkHardClient(h4) .and. checkHardClient(h2) .and. checkHardInvClient(h3))
  call reporttest(retval, test)

end subroutine checkHardStructs

subroutine checkCombinedStructs(test,tracker,remoteURL)
  use sidl
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
  call writeComment(tracker, 'returnCombined(obj,throwaway_exception)', throwaway_exception)
  c1 = returnCombined(obj,throwaway_exception) 
  retval=checkCombinedClient(c1)
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinCombined(obj,c1,throwaway_exception)', throwaway_exception)
  retval = passinCombined(obj,c1,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutCombined(obj,c2,throwaway_exception)', throwaway_exception)
  retval = passoutCombined(obj,c2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutCombined(obj,c2,throwaway_exception)', throwaway_exception)
  retval = passinoutCombined(obj,c2,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkCombinedInvClient(c2)', throwaway_exception)
  retval = checkCombinedInvClient(c2) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passoutCombined(obj,c3,throwaway_exception)', throwaway_exception)
  retval = passoutCombined(obj,c3,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereCombined(obj,c1,c2,c3,throwaway_exception)', throwaway_exception)
  c4 = passeverywhereCombined(obj,c1,c2,c3,throwaway_exception) 
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, &
       '(checkCombinedClient(c4) .and. checkCombinedClient(c2) & .and. checkCombinedInvClient(c3))', &
       throwaway_exception)
  retval=(checkCombinedClient(c4) .and. checkCombinedClient(c2) .and. checkCombinedInvClient(c3))
  call reporttest(retval, test)

end subroutine checkCombinedStructs

subroutine checkRarraysStructs(test,tracker)
  use sidl
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
  logical checkRarraysClient,checkRarraysInvClient
  type(s_Rarrays_t) :: r1,r2
  real(c_double),dimension(:),target,allocatable :: tempArr1,tempArr2
  integer,parameter :: N=3
  
  call new(obj,throwaway_exception)

  allocate(tempArr1(N))

  call initRarraysClient(r1,tempArr1)

  call starttest(test)
  call writeComment(tracker, 'passinRarrays(obj,r1,throwaway_exception)', throwaway_exception)
  retval = passinRarrays(obj,r1,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'passinoutRarrays(obj,r1,throwaway_exception)', throwaway_exception)
  retval = passinoutRarrays(obj,r1,throwaway_exception) 
  call reporttest(retval, test)

  call starttest(test)
  call writeComment(tracker, 'checkRarraysInvClient(r1)', throwaway_exception)
  retval = checkRarraysInvClient(r1) 
  call reporttest(retval, test)

  deallocate(tempArr1)
  call deleteRarrays(r1)
  
  allocate(tempArr1(N))
  allocate(tempArr2(N))
  call initRarraysClient(r1,tempArr1)
  call initRarraysClient(r2,tempArr2)

  call starttest(test)
  call writeComment(tracker, 'passeverywhereRarrays(obj,r1,r2,&
       &throwaway_exception)', throwaway_exception)
  retval = passeverywhereRarrays(obj,r1,r2,throwaway_exception)
  call reporttest(is_null(throwaway_exception), test)

  call starttest(test)
  call writeComment(tracker, '(checkRarraysClient(r1) .and. checkRarraysInvClient(r2))' &
       , throwaway_exception)
  retval=(checkRarraysClient(r1) .and. checkRarraysInvClient(r2))
  call reporttest(retval, test)

  deallocate(tempArr1)
  deallocate(tempArr2)
end subroutine checkRarraysStructs

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

  tracker = getInstance(throwaway_exception)

  !Parse the command line  to see if we are running RMI tests
  remoteURL = ''
  num_tests = 36_sidl_int
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
     retval = addProtocol('simhandle', 'sidlx.rmi.SimHandle', throwaway_exception)
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

     call writeComment(tracker, 'Rarrays Struct Tests', throwaway_exception)
     call checkRarraysStructs(test,tracker)

     call writeComment(tracker, 'Combined Struct Tests', throwaway_exception)
     call checkCombinedStructs(test,tracker,remoteURL)
  endif

  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program structtest
