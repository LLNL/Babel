! 
! File:          s_StructTest_Impl.F90
! Symbol:        s.StructTest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for s.StructTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "s.StructTest" (version 1.0)
! 


#include "s_StructTest_fAbbrev.h"
#include "s_Simple_fAbbrev.h"
#include "s_Hard_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "s_Combined_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "s_Rarrays_fAbbrev.h"
#include "s_Empty_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! insert code here (extra code)

subroutine initSimple(s)
  use sidl
  use sidl_NotImplementedException
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
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

end subroutine initSimple

logical function checkSimple(s)
  use sidl
  use sidl_NotImplementedException
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
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
    checkSimple=.true.
  else
    checkSimple=.false.
  endif

  return

end

subroutine invertSimple(s)
  use sidl
  use sidl_NotImplementedException
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  use s_Color
  implicit none

  type(s_Simple_t), intent(inout) :: s

  ! ignore inversion of d_char since 3 is neither upper or lower case.  Also
  ! ignore inversion of NULL

  if (s%d_bool .eqv. .false.) then
    s%d_bool=.true.
  else
    s%d_bool=.false.
  endif
  s%d_dcomplex = cmplx(3.14_sidl_double,-3.14_sidl_double)
  s%d_double = -s%d_double
  s%d_fcomplex = cmplx(3.1_sidl_float,-3.1_sidl_float)
  s%d_float = -s%d_float
  s%d_int = -s%d_int
  s%d_long = -s%d_long
  s%d_enum = red

end subroutine invertSimple

subroutine initHard(h, ex)
  use sidl
  use sidl_NotImplementedException
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass_array
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  use sidl_array_array
  use sidl_double_array
  use sidl_string_array
  implicit none

  type(s_Hard_t), intent(inout) :: h
  type(sidl_BaseInterface_t), intent(out) :: ex

  integer :: i
  type(sidl_BaseClass_t) :: bc

  !string array
  call create1d(1, h%d_string)
  call set(h%d_string, 0, 'Three')

  !regular object
  call new(h%d_object, ex)
  if(not_null(ex)) return

  !regular interface (cast)
  call cast(h%d_object, h%d_interface, ex)
  if(not_null(ex)) return

  !double array
  call create1d(3, h%d_array)
  call set(h%d_array, 0, 1.0_sidl_double)
  call set(h%d_array, 1, 2.0_sidl_double)
  call set(h%d_array, 2, 3.0_sidl_double)

  !object array
  call create1d(3,h%d_objectArray)
  do i = 0,2
     call new(bc, ex)
     if(not_null(ex)) return
     call set(h%d_objectArray, i, bc)
     call deleteRef(bc, ex)
  enddo
end subroutine initHard

logical function checkHard(h)
  use sidl
  use sidl_double_array
  use sidl_NotImplementedException
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass
  use sidl_BaseClass_array
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
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
     checkHard = .false.
     return
  endif

  do i = 0, 2
     call get(h%d_array, i, d)
     if(d .ne. (i + 1) * 1.0) then
        checkHard = .false.
        return
     endif
  enddo

  ! d_string
  if(dimen(h%d_string) .ne. 1 .or. length(h%d_string, 0) .ne. 1) then
     checkHard = .false.
     return
  endif

  call get(h%d_string, 0, str)
  if(str .ne. 'Three') then
     checkHard = .false.
     return
  endif

  ! d_object and d_interface
  if(is_null(h%d_object) .or. is_null(h%d_interface)) then
     checkHard = .false.
     return
  endif

  call isSame(h%d_object, h%d_interface, b, ex)
  if(not_null(ex) .or. (b .eqv. .false.)) then
     checkHard = .false.
     return
  endif

  ! d_objectArray
  if(dimen(h%d_objectArray) .ne. 1 .or. length(h%d_objectArray, 0) .ne. 3) then
     checkHard = .false.
     return
  endif

  do i = 0, 2
     call get(h%d_objectArray, i, bc)
     if(is_null(bc)) then
        checkHard = .false.
        return
     endif

     call isType(bc, 'sidl.BaseClass', b, ex)
     if(not_null(ex) .or. (b .eqv. .false.)) then
        checkHard = .false.
        return
     endif
  enddo

  checkHard=.true.
  return
end function checkHard

subroutine invertHard(s)
  use sidl
  use sidl_NotImplementedException
  use s_Hard
  use sidl_BaseInterface
  use sidl_BaseClass_array
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  use sidl_array_array
  use sidl_double_array
  use sidl_string_array
  implicit none

  type(s_Hard_t),intent(inout) :: s
  real(kind=sidl_double) :: temp,temp2
  integer (kind=sidl_int) :: lw(1),up(1)
  character(len=6) :: tString
  type(sidl_BaseInterface_t) :: ex,throwaway
  logical test
  type(sidl_BaseClass_t) :: bc

  if ((dimen(s%d_array) .eq. 1) .and. &
      (length(s%d_array,0) .eq. 3 )) then
         call get(s%d_array,0,temp)
         call get(s%d_array,2,temp2)
         call set(s%d_array,0,temp2)
         call set(s%d_array,2,temp)
  endif

  call get(s%d_string,0,tString)
  call set(s%d_string, 0, 'three')

  call set_null(ex)
  call set_null(throwaway)

  if (not_null(s%d_object) .and. not_null(s%d_interface)) then
    call isSame(s%d_object,s%d_interface,test,ex)
    if (not_null(ex)) then
      call deleteRef(ex,throwaway)
      call deleteRef(s%d_interface,throwaway)
      call set_null(s%d_interface)
    else
      call deleteRef(s%d_interface,throwaway)
      call set_null(s%d_interface)
      if (test) then
        call new(bc,ex)
        call cast(bc,s%d_interface,ex)
        call deleteRef(bc,throwaway)
      else
        call cast(s%d_object,s%d_interface,ex)
      endif
    endif
  endif

  ! invert object array

  if ((dimen(s%d_ObjectArray) .eq. 1) .and. &
      (length(s%d_ObjectArray,0) .eq. 3 )) then
      call get(s%d_ObjectArray,1,bc)
      if (not_null(bc)) then
        call set_null(bc)
          if (not_null(bc)) then
          endif
        call set(s%d_ObjectArray, 1, bc)
      else
        call new(bc, ex)
        call set(s%d_ObjectArray, 1, bc)
      endif
  endif

end subroutine invertHard

logical function checkRarrays(s)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  implicit none

  type(s_Rarrays_t), intent(in) :: s

  checkRarrays=.false.

  if (associated(s%d_rarrayRaw) .and. &
       (s%d_int .eq. 3) .and. &
       (s%d_rarrayRaw(0) .eq. 1.0) .and. &
       (s%d_rarrayRaw(1) .eq. 2.0) .and. &
       (s%d_rarrayRaw(2) .eq. 3.0) .and. &
       (s%d_rarrayFix(0) .eq. 5.0) .and. &
       (s%d_rarrayFix(1) .eq. 10.0) .and. &
       (s%d_rarrayFix(2) .eq. 15.0)) then
     checkRarrays=.true.
  endif

  return

end function checkRarrays

subroutine invertRarrays(s)
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
     temp = s%d_rarrayRaw(0)
     s%d_rarrayRaw(0) = s%d_rarrayRaw(2)
     s%d_rarrayRaw(2) = temp

     temp = s%d_rarrayFix(0)
     s%d_rarrayFix(0) = s%d_rarrayFix(2)
     s%d_rarrayFix(2) = temp

  endif
end subroutine invertRarrays

subroutine initCombined(s,ex)
  use sidl
  use sidl_NotImplementedException
  use s_Simple
  use s_Hard
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  use s_Color
  implicit none

  type(s_Combined_t),intent(inout) :: s
  type(sidl_BaseInterface_t),intent(out) :: ex
  real(kind=sidl_double) :: temp
  type(s_Hard_t),target :: tHard

  call initSimple(s%d_simple)
  call initHard(s%d_hard,ex)
end subroutine initCombined

logical function checkCombined(s)
  use sidl
  use sidl_NotImplementedException
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  use s_Color

  type(s_Combined_t),intent(in) :: s

  logical checkSimple
  logical checkHard

  checkCombined=.false.

  if (checkSimple(s%d_simple) .and. checkHard(s%d_hard)) then
    checkCombined=.true.
  endif

  return

end function checkCombined

subroutine invertCombined(s)
  use sidl
  use sidl_NotImplementedException
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  use s_Color

  type(s_Combined_t),intent(inout) :: s

  call invertSimple(s%d_simple)
  call invertHard(s%d_hard)
end subroutine invertCombined

! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine s_StructTest__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest._ctor.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest._ctor.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest._ctor)
! insert code here (_ctor method)
!
! This method has not been implemented
!

! DO-NOT-DELETE splicer.end(s.StructTest._ctor)
end subroutine s_StructTest__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine s_StructTest__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest._ctor2.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest._ctor2.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_StructTest_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest._ctor2)
! insert code here (_ctor2 method)
!
! This method has not been implemented
!

! DO-NOT-DELETE splicer.end(s.StructTest._ctor2)
end subroutine s_StructTest__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine s_StructTest__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest._dtor.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest._dtor.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest._dtor)
! insert code here (_dtor method)
!
! This method has not been implemented
!

! DO-NOT-DELETE splicer.end(s.StructTest._dtor)
end subroutine s_StructTest__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine s_StructTest__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest._load.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest._load)
! insert code here (_load method)
!
! This method has not been implemented
!

! DO-NOT-DELETE splicer.end(s.StructTest._load)
end subroutine s_StructTest__load_mi


! 
! Method:  returnEmpty[]
! 

recursive subroutine s_StructTest_returnEmpty_mi(self, retval, exception)
  use sidl
  use s_Empty
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Empty_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty)
! insert code here (returnEmpty method)
!
! Do Nothing. all we have to do is return retval

! DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty)
end subroutine s_StructTest_returnEmpty_mi


! 
! Method:  passinEmpty[]
! 

recursive subroutine s_StructTest_passinEmpty_mi(self, s1, retval, exception)
  use sidl
  use s_Empty
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Empty_t) :: s1
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty)
! insert code here (passinEmpty method)
!
 retval =.true.

! DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty)
end subroutine s_StructTest_passinEmpty_mi


! 
! Method:  passoutEmpty[]
! 

recursive subroutine s_StructTest_passoutEmpty_mi(self, s1, retval, exception)
  use sidl
  use s_Empty
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Empty_t) :: s1
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty)
! insert code here (passoutEmpty method)
!
  retval=.true.

! DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty)
end subroutine s_StructTest_passoutEmpty_mi


! 
! Method:  passinoutEmpty[]
! 

recursive subroutine s_StructTest_passinoutEmpty_mi(self, s1, retval,          &
  exception)
  use sidl
  use s_Empty
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Empty_t) :: s1
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty)
! insert code here (passinoutEmpty method)
!
  retval=.true.

! DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty)
end subroutine s_StructTest_passinoutEmpty_mi


! 
! Method:  passeverywhereEmpty[]
! 

recursive subroutine passeverywhereEmpt05k3gk_f64_mi(self, s1, s2, s3, retval, &
  exception)
  use sidl
  use s_Empty
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Empty_t) :: s1
  ! in
  type(s_Empty_t) :: s2
  ! out
  type(s_Empty_t) :: s3
  ! inout
  type(s_Empty_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty)
! insert code here (passeverywhereEmpty method)
!
  call set_null(exception)


! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty)
end subroutine passeverywhereEmpt05k3gk_f64_mi


! 
! Method:  returnSimple[]
! 

recursive subroutine s_StructTest_returnSimple_mi(self, retval, exception)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.returnSimple.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Simple_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple)
! insert code here (returnSimple method)
!
  call initSimple(retval)

! DO-NOT-DELETE splicer.end(s.StructTest.returnSimple)
end subroutine s_StructTest_returnSimple_mi


! 
! Method:  passinSimple[]
! 

recursive subroutine s_StructTest_passinSimple_mi(self, s1, retval, exception)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinSimple.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Simple_t) :: s1
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple)
! insert code here (passinSimple method)
!
  logical checkSimple

  retval=checkSimple(s1)


! DO-NOT-DELETE splicer.end(s.StructTest.passinSimple)
end subroutine s_StructTest_passinSimple_mi


! 
! Method:  passoutSimple[]
! 

recursive subroutine s_StructTest_passoutSimple_mi(self, s1, retval,           &
  exception)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Simple_t) :: s1
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple)
! insert code here (passoutSimple method)
!
  call initSimple(s1)
  retval = .true.


! DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple)
end subroutine s_StructTest_passoutSimple_mi


! 
! Method:  passinoutSimple[]
! 

recursive subroutine s_StructTest_passinoutSimple_mi(self, s1, retval,         &
  exception)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Simple_t) :: s1
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple)
! insert code here (passinoutSimple method)
!
  logical checkSimple
  retval=checkSimple(s1)
  call invertSimple(s1)

! DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple)
end subroutine s_StructTest_passinoutSimple_mi


! 
! Method:  passeverywhereSimple[]
! 

recursive subroutine passeverywhereSimp1t_32r81n0_mi(self, s1, s2, s3, retval, &
  exception)
  use sidl
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Simple_t) :: s1
  ! in
  type(s_Simple_t) :: s2
  ! out
  type(s_Simple_t) :: s3
  ! inout
  type(s_Simple_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple)
! insert code here (passeverywhereSimple method)
!
  logical checkSimple

  call initSimple(s2)
  call initSimple(retval)
  if (checkSimple(s1) .and. checkSimple(s2)) then
    call invertSimple(s3)
  endif

! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple)
end subroutine passeverywhereSimp1t_32r81n0_mi


! 
! Method:  returnHard[]
! 

recursive subroutine s_StructTest_returnHard_mi(self, retval, exception)
  use sidl
  use s_Hard
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.returnHard.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.returnHard.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Hard_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.returnHard)
! insert code here (returnHard method)
!
  call initHard(retval,exception)

! DO-NOT-DELETE splicer.end(s.StructTest.returnHard)
end subroutine s_StructTest_returnHard_mi


! 
! Method:  passinHard[]
! 

recursive subroutine s_StructTest_passinHard_mi(self, s1, retval, exception)
  use sidl
  use s_Hard
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinHard.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinHard.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Hard_t) :: s1
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinHard)
! insert code here (passinHard method)
!
  logical checkHard
  retval = checkHard(s1)


! DO-NOT-DELETE splicer.end(s.StructTest.passinHard)
end subroutine s_StructTest_passinHard_mi


! 
! Method:  passoutHard[]
! 

recursive subroutine s_StructTest_passoutHard_mi(self, s1, retval, exception)
  use sidl
  use s_Hard
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passoutHard.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Hard_t) :: s1
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard)
! insert code here (passoutHard method)
!
  retval = .true.
  call initHard(s1, exception)
  if (not_null(exception)) then
    retval = .false.
  endif

! DO-NOT-DELETE splicer.end(s.StructTest.passoutHard)
end subroutine s_StructTest_passoutHard_mi


! 
! Method:  passinoutHard[]
! 

recursive subroutine s_StructTest_passinoutHard_mi(self, s1, retval,           &
  exception)
  use sidl
  use s_Hard
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Hard_t) :: s1
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard)
! insert code here (passinoutHard method)
!
  logical checkHard
  retval = checkHard(s1)
  call invertHard(s1)


! DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard)
end subroutine s_StructTest_passinoutHard_mi


! 
! Method:  passeverywhereHard[]
! 

recursive subroutine passeverywhereHardatsuyth6kv_mi(self, s1, s2, s3, retval, &
  exception)
  use sidl
  use s_Hard
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Hard_t) :: s1
  ! in
  type(s_Hard_t) :: s2
  ! out
  type(s_Hard_t) :: s3
  ! inout
  type(s_Hard_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard)
! insert code here (passeverywhereHard method)
!
   logical checkHard

   call initHard(retval,exception)
   call initHard(s2,exception)
   if (checkHard(s1).and.checkHard(s3)) then
     call invertHard(s3)
   endif

! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard)
end subroutine passeverywhereHardatsuyth6kv_mi


! 
! Method:  returnCombined[]
! 

recursive subroutine s_StructTest_returnCombined_mi(self, retval, exception)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.returnCombined.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Combined_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined)
! insert code here (returnCombined method)
!
  call initCombined(retval,exception)


! DO-NOT-DELETE splicer.end(s.StructTest.returnCombined)
end subroutine s_StructTest_returnCombined_mi


! 
! Method:  passinCombined[]
! 

recursive subroutine s_StructTest_passinCombined_mi(self, s1, retval,          &
  exception)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinCombined.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Combined_t) :: s1
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined)
! insert code here (passinCombined method)
!
  logical checkCombined
  retval=.false.
  retval=checkCombined(s1)


! DO-NOT-DELETE splicer.end(s.StructTest.passinCombined)
end subroutine s_StructTest_passinCombined_mi


! 
! Method:  passoutCombined[]
! 

recursive subroutine s_StructTest_passoutCombined_mi(self, s1, retval,         &
  exception)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Combined_t) :: s1
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined)
! insert code here (passoutCombined method)
!
  retval = .true.
  call initCombined(s1, exception)
  if (not_null(exception)) then
    retval = .false.
  endif


! DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined)
end subroutine s_StructTest_passoutCombined_mi


! 
! Method:  passinoutCombined[]
! 

recursive subroutine passinoutCombined0ko6zp9lxck_mi(self, s1, retval,         &
  exception)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Combined_t) :: s1
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined)
! insert code here (passinoutCombined method)
!
  logical checkCombined
  retval = checkCombined(s1)
  call invertCombined(s1)


! DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined)
end subroutine passinoutCombined0ko6zp9lxck_mi


! 
! Method:  passeverywhereCombined[]
! 

recursive subroutine passeverywhereComb7br08sdkjq_mi(self, s1, s2, s3, retval, &
  exception)
  use sidl
  use s_Combined
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Combined_t) :: s1
  ! in
  type(s_Combined_t) :: s2
  ! out
  type(s_Combined_t) :: s3
  ! inout
  type(s_Combined_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined)
! insert code here (passeverywhereCombined method)
!
  logical checkCombined

  call initCombined(retval,exception)
  call initCombined(s2,exception)
  if (checkCombined(s1).and.checkCombined(s3)) then
    call invertCombined(s3)
  endif


! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined)
end subroutine passeverywhereComb7br08sdkjq_mi


! 
! Method:  passinRarrays[]
! 

recursive subroutine s_StructTest_passinRarrays_mi(self, s1, retval,           &
  exception)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Rarrays_t) :: s1
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays)
! insert code here (passinRarrays method)
  logical checkRarrays
  retval = checkRarrays(s1)
! DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays)
end subroutine s_StructTest_passinRarrays_mi


! 
! Method:  passinoutRarrays[]
! 

recursive subroutine S_passinoutRarrays5r6_huey_6_mi(self, s1, retval,         &
  exception)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Rarrays_t) :: s1
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays)
! insert code here (passinoutRarrays method)
  call invertRarrays(s1)
  retval = .true.
! DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays)
end subroutine S_passinoutRarrays5r6_huey_6_mi


! 
! Method:  passeverywhereRarrays[]
! 

recursive subroutine passeverywhereRarrt506sngx5k_mi(self, s1, s2, retval,     &
  exception)
  use sidl
  use s_Rarrays
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  use s_StructTest_impl
  ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays.use)
  !
  ! Add use statements here
  !

  ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays.use)
  implicit none
  type(s_StructTest_t) :: self
  ! in
  type(s_Rarrays_t) :: s1
  ! in
  type(s_Rarrays_t) :: s2
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays)
! insert code here (passeverywhereRarrays method)
  logical checkRarrays

  retval = .false.
  if (checkRarrays(s1)) then
     call invertRarrays(s2)
     retval = .true.
  endif

! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays)
end subroutine passeverywhereRarrt506sngx5k_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! insert code here (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
