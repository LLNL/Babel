! 
! File:          s_StructTest_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
!
! Add additional include statements here
!
#include "s_Color_fAbbrev.h"
#include "sidl_double_fAbbrev.h"
#include "sidl_string_fAbbrev.h"
! DO-NOT-DELETE splicer.end(module.include)

module s_StructTest_Impl
  use sidl
  use s_Combined
  use s_Empty
  use s_Hard
  use s_Rarrays
  use s_Simple
  use sidl_BaseInterface
  use sidl_RuntimeException
  use s_StructTest
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(s_StructTest_t) :: s_StructTest_impl_t
    ! DO-NOT-DELETE splicer.begin(s.StructTest.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(s.StructTest.private_data)
  end type s_StructTest_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(s_StructTest_impl_t), target :: obj
    type(s_StructTest_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external s_StructTest_wrap
    call s_StructTest_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)

  subroutine initSimple(s)
    use s_Color
    implicit none
   
    type(s_Simple_t),intent(inout) :: s
    complex(kind=sidl_dcomplex) :: dc
    dc = cmplx(3.14_sidl_double,3.14_sidl_double)
   
    call set_d_bool(s, .true.)
    call set_d_char(s, '3')
    s%d_dcomplex = dc
    s%d_double = 3.14_sidl_double
    s%d_fcomplex = cmplx(3.1_sidl_float,3.1_sidl_float)
    s%d_float = 3.1_sidl_float
    s%d_int = 3
    s%d_long = 3_sidl_long
    call set_d_opaque(s, sidl_opaque_null)
    s%d_enum = blue
    s%d_string = c_null_ptr     ! initialize the c_ptr beneath
    call set_d_string(s, "string")
   
  end subroutine initSimple
   
  logical function checkSimple(s)
    use sidl
    use s_Simple
    use sidl_BaseInterface
    use sidl_RuntimeException
    use s_StructTest
    use s_Color
    implicit none
   
    type(s_Simple_t),intent(in) :: s
    real, parameter :: eps = 1E-6
   
    if ((get_d_bool(s) .eqv. .true.) .and. &
        (get_d_char(s) .eq. '3' ) .and. &
         (abs(real(s%d_dcomplex) - 3.14_sidl_double) .lt. eps) .and. &
         (abs(aimag(s%d_dcomplex) - 3.14_sidl_double) .lt. eps) .and. &
         (abs(s%d_double - 3.14_sidl_double) .lt. eps ) .and. &
         (abs(s%d_float - 3.1_sidl_float) .lt. eps ) .and. &
         (abs(real(s%d_fcomplex) - 3.1_sidl_float) .lt. eps) .and. &
         (abs(aimag(s%d_fcomplex) - 3.1_sidl_float) .lt. eps) .and. &
         (s%d_int .eq. 3 ) .and. &
         (s%d_long .eq. 3_sidl_long) .and. &
         (is_null(get_d_opaque(s))) .and. &
         (s%d_enum .eq. blue )) then
      checkSimple=.true.
    else
      checkSimple=.false.
    endif
   
    return 
   
  end function checkSimple
   
  subroutine invertSimple(s)
    use sidl
    use s_Simple
    use sidl_BaseInterface
    use sidl_RuntimeException
    use s_StructTest
    use s_Color;
    implicit none
   
    type(s_Simple_t), intent(inout) :: s
    complex(kind=sidl_dcomplex) :: dc
    dc = cmplx(3.14_sidl_double,-3.14_sidl_double)
   
    ! ignore inversion of d_char since 3 is neither upper or lower case.  Also 
    ! ignore inversion of NULL
   
    if (get_d_bool(s) .eqv. .false.) then
      call set_d_bool(s, .true.)
    else
      call set_d_bool(s,.false.)
    endif
    s%d_dcomplex = dc
    s%d_double = -s%d_double
    s%d_fcomplex = cmplx(3.1_sidl_float,-3.1_sidl_float)
    s%d_float = -s%d_float
    s%d_int = -s%d_int
    s%d_long = -s%d_long
    s%d_enum = red
   
  end subroutine invertSimple
   
  subroutine initHard(h, ex)
    use sidl
    use s_Hard
    use sidl_BaseInterface
    use sidl_BaseClass_array
    use sidl_RuntimeException
    use s_StructTest
    use sidl_double_array
    use sidl_string_array
    implicit none
   
    type(s_Hard_t), intent(inout) :: h
    type(sidl_BaseInterface_t), intent(out) :: ex
    type(sidl_BaseInterface_t) :: int
    integer :: i
    type(sidl_BaseClass_t) :: bc, obj
    type(sidl_string_1d) :: sarr
    type(sidl_double_1d) :: darr
    type(sidl_BaseClass_1d) :: oarr
    
    !string array
    call create1d(1, sarr)
    call set_d_string(h, sarr)
    call set(get_d_string(h), 0, 'Three')
   
    !regular object
    call new(obj, ex)
    call set_d_object(h, obj)
    if(not_null(ex)) return
   
    !regular interface (cast)
    call cast(obj, int, ex)
    if(not_null(ex)) return
    call set_d_interface(h, int)
    
    !double array
    call create1d(3, darr)
    call set(darr, 0, 1.0_sidl_double)
    call set(darr, 1, 2.0_sidl_double)
    call set(darr, 2, 3.0_sidl_double)
    call set_d_array(h, darr)
   
    !object array
    call create1d(3, oarr)
    do i = 0,2
       call new(bc, ex)
       if(not_null(ex)) return
       call set(oarr, i, bc)
       call deleteRef(bc, ex)
    enddo
    call set_d_objectArray(h, oarr)    
  end subroutine initHard
   
  logical function checkHard(h)
    use sidl
    use sidl_double_array
    use s_Hard
    use sidl_BaseInterface
    use sidl_BaseClass
    use sidl_BaseClass_array
    use sidl_RuntimeException
    use s_StructTest
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
       checkHard = .false.
       return
    endif
   
    do i = 0, 2
       call get(get_d_array(h), i, d)
       if(d .ne. (i + 1) * 1.0) then
          checkHard = .false.
          return
       endif
    enddo
   
    ! d_string
    if(dimen(get_d_string(h)) .ne. 1 .or. length(get_d_string(h), 0) .ne. 1) then
       checkHard = .false.
       return
    endif
   
    call get(get_d_string(h), 0, str)
    if(str .ne. 'Three') then
       checkHard = .false.
       return
    endif
    
    ! d_object and d_interface
    if(is_null(get_d_object(h)) .or. is_null(get_d_interface(h))) then
       checkHard = .false.
       return
    endif
    
    b = isSame(get_d_object(h), get_d_interface(h), ex)
    if(not_null(ex) .or. (b .eqv. .false.)) then
       checkHard = .false.
       return
    endif
    
    ! d_objectArray
    if(dimen(get_d_objectArray(h)) .ne. 1 .or. length(get_d_objectArray(h), 0) .ne. 3) then
       checkHard = .false.
       return
    endif
   
    do i = 0, 2
       call get(get_d_objectArray(h), i, bc)
       if(is_null(bc)) then
          checkHard = .false.
          return
       endif
   
       b = isType(bc, 'sidl.BaseClass', ex)
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
    use s_Hard
    use sidl_BaseInterface
    use sidl_BaseClass_array
    use sidl_RuntimeException
    use s_StructTest
    use sidl_double_array
    use sidl_string_array
    implicit none
   
    type(s_Hard_t),intent(inout) :: s
    real(kind=sidl_double) :: temp,temp2
    integer (kind=sidl_int) :: lw(1),up(1)
    character(len=6) :: tString
    type(sidl_BaseInterface_t) :: ex,throwaway, int
    logical test
    type(sidl_BaseClass_t) :: bc, obj
    type(sidl_BaseClass_1d) :: oarr
   
    if ((dimen(get_d_array(s)) .eq. 1) .and. &
        (length(get_d_array(s),0) .eq. 3 )) then
           call get(get_d_array(s),0,temp)
           call get(get_d_array(s),2,temp2)
           call set(get_d_array(s),0,temp2)
           call set(get_d_array(s),2,temp)
    endif 
   
    call get(get_d_string(s),0,tString)
    call set(get_d_string(s), 0, 'three')
   
    call set_null(ex)
    call set_null(throwaway)

    int = get_d_interface(s)   
    obj = get_d_object(s)
    if (not_null(obj) .and. not_null(int)) then

      test = isSame(obj,int,ex)
      if (not_null(ex)) then
        call deleteRef(ex,throwaway)
        call deleteRef(int,throwaway)
        call set_null(int)
      else
        call deleteRef(int,throwaway)
        call set_null(int)
        if (test) then
          call new(bc,ex)  
          call cast(bc,int,ex)
          call deleteRef(bc,throwaway)
          call set_d_interface(s, int)
        else
          call cast(obj, int, ex)
        endif
      endif
    endif 
   
    ! invert object array
    oarr = get_d_ObjectArray(s)
    if ((dimen(oarr) .eq. 1) .and. &
        (length(oarr,0) .eq. 3 )) then
        call get(oarr,1,bc)
        if (not_null(bc)) then
          call set_null(bc)
            if (not_null(bc)) then
            endif
          call set(oarr, 1, bc)
        else
          call new(bc, ex)
          call set(oarr, 1, bc)
        endif
    endif
   
  end subroutine invertHard
   
  subroutine initCombined(s,ex)
    use sidl
    use s_Simple
    use s_Hard
    use s_Combined
    use sidl_BaseInterface
    use sidl_RuntimeException
    use s_StructTest
    implicit none
   
    type(s_Combined_t),intent(inout) :: s
    type(sidl_BaseInterface_t),intent(out) :: ex
    real(kind=sidl_double) :: temp
    type(s_Simple_t),target :: tSimple
    type(s_Hard_t),target :: tHard
   
    call initSimple(tSimple)
    call initHard(tHard,ex)
    call set_d_simple(s, tSimple)
    call set_d_hard(s, tHard)
  end subroutine initCombined
   
  logical function checkCombined(s)
    use sidl
    use s_Combined
    use sidl_BaseInterface
    use sidl_RuntimeException
    use s_StructTest
   
    type(s_Combined_t),intent(in) :: s
   
    ! logical checkSimple
    ! logical checkHard
   
    checkCombined=.false.
   
    if (checkSimple(get_d_simple(s)) .and. checkHard(get_d_hard(s))) then
      checkCombined=.true.
    endif
   
    return
   
  end function checkCombined
   
  subroutine invertCombined(s)
    use sidl
    use s_Combined
    use sidl_BaseInterface
    use sidl_RuntimeException
    use s_StructTest
    implicit none
   
    type(s_Combined_t),intent(inout) :: s
    type(s_Simple_t),target :: tSimple

    ! test both interfaces
    tSimple = get_d_simple(s)
    call invertSimple(tSimple)
    call set_d_simple(s, tSimple)
    call invertHard(s%d_hard)
  end subroutine invertCombined

  logical function checkRarrays(s)
    use sidl
    use s_Rarrays
    use sidl_BaseInterface
    use sidl_RuntimeException
    use s_StructTest
    implicit none

    !  logical c_associated
    type(s_Rarrays_t), intent(in) :: s

    real(kind=sidl_double), dimension(:), pointer :: s_d_rarrayRaw_fptr

    ! FIXME:
    !
    ! We probably don't want the user to access those fields but for
    ! strange reasons a call to a function performing this actions
    ! results in a segfault
    ! c.f. StructModule.java, rarrays.F03
    call c_f_pointer(s%d_rarrayRaw, s_d_rarrayRaw_fptr, [s%d_int])

    checkRarrays=.false.

    if (& !c_associated(s%d_rarrayRaw) .and. &
         (s%d_int .eq. 3) .and. &
         (s_d_rarrayRaw_fptr(1) .eq. 1.0) .and. &
         (s_d_rarrayRaw_fptr(2) .eq. 2.0) .and. &
         (s_d_rarrayRaw_fptr(3) .eq. 3.0) .and. &
         (s%d_rarrayFix(1) .eq. 5.0) .and. &
         (s%d_rarrayFix(2) .eq. 10.0) .and. &
         (s%d_rarrayFix(3) .eq. 15.0)) then
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

    type(s_Rarrays_t),intent(inout) :: s
    real(kind=sidl_double) :: temp
    !  logical c_associated

    real(kind=sidl_double), dimension(:), pointer :: s_d_rarrayRaw_fptr
    !real(kind=sidl_double), dimension(:), pointer :: s_d_rarrayFix_fptr
    real(kind=sidl_double), dimension(:), allocatable, target, save :: newRarray_fptr

    !allocate(newRarray_fptr(3))

    ! FIXME: see comment in checkRarrays
    call c_f_pointer(s%d_rarrayRaw, s_d_rarrayRaw_fptr, [s%d_int])
    !s_d_rarrayRaw_fptr = get_d_rarrayRaw(s)
    !s_d_rarrayFix_fptr = get_d_rarrayFix(s)
    
    if (associated(s_d_rarrayRaw_fptr)) then
       temp = s_d_rarrayRaw_fptr(1)   
       s_d_rarrayRaw_fptr(1) = s_d_rarrayRaw_fptr(3)     
       s_d_rarrayRaw_fptr(3) = temp
  
       temp = s%d_rarrayFix(1)
       s%d_rarrayFix(1) = s%d_rarrayFix(3)
       s%d_rarrayFix(3) = temp
    endif
  end subroutine invertRarrays
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(s.StructTest._ctor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest._ctor.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(s.StructTest._ctor)
    ! insert code here (_ctor method)
    !
    ! This method has not been implemented
    !
    ! DO-NOT-DELETE splicer.end(s.StructTest._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(s.StructTest._dtor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest._dtor.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(s.StructTest._dtor)
    ! insert code here (_dtor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(s.StructTest._load.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(s.StructTest._load)
    ! insert code here (_load method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest._load)
  end subroutine load_impl


  ! 
  ! Method:  returnEmpty[]
  ! 

  function returnEmpty_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Empty_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty)
    ! insert code here (returnEmpty method)
    ! 
    ! Do Nothing. all we have to do is return retval
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty)
  end function returnEmpty_impl


  ! 
  ! Method:  passinEmpty[]
  ! 

  function passinEmpty_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Empty s1
    type(s_Empty_t), intent(in) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty)
    ! insert code here (passinEmpty method)
    ! 
    retval =.true.
    

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty)
  end function passinEmpty_impl


  ! 
  ! Method:  passoutEmpty[]
  ! 

  function passoutEmpty_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out s.Empty s1
    type(s_Empty_t), intent(out) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty)
    ! insert code here (passoutEmpty method)
    ! 
    retval =.true.
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty)
  end function passoutEmpty_impl


  ! 
  ! Method:  passinoutEmpty[]
  ! 

  function passinoutEmpty_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  inout s.Empty s1
    type(s_Empty_t), intent(inout) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty)
    ! insert code here (passinoutEmpty method)
    ! 
    retval =.true.
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty)
  end function passinoutEmpty_impl


  ! 
  ! Method:  passeverywhereEmpty[]
  ! 

  function passeverywhereEmpty_impl(self, s1, s2, s3, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Empty s1
    type(s_Empty_t), intent(in) :: s1
    !  out s.Empty s2
    type(s_Empty_t), intent(out) :: s2
    !  inout s.Empty s3
    type(s_Empty_t), intent(inout) :: s3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Empty_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty)
    call set_null(exception)
    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty)
  end function passeverywhereEmpty_impl


  ! 
  ! Method:  returnSimple[]
  ! 

  function returnSimple_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnSimple.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Simple_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple)
    ! insert code here (returnSimple method)
    !
    call initSimple(retval)

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnSimple)
  end function returnSimple_impl


  ! 
  ! Method:  passinSimple[]
  ! 

  function passinSimple_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinSimple.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Simple s1
    type(s_Simple_t), intent(in) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple)
    ! insert code here (passinSimple method)
    ! logical checkSimple

    retval=checkSimple(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinSimple)
  end function passinSimple_impl


  ! 
  ! Method:  passoutSimple[]
  ! 

  function passoutSimple_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out s.Simple s1
    type(s_Simple_t), intent(out) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple)
    ! insert code here (passoutSimple method)
    ! 
    ! This method has not been implemented
    call initSimple(s1)
    retval = .true.

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple)
  end function passoutSimple_impl


  ! 
  ! Method:  passinoutSimple[]
  ! 

  function passinoutSimple_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  inout s.Simple s1
    type(s_Simple_t), intent(inout) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple)
    ! insert code here (passinoutSimple method)
    ! logical checkSimple
    retval=checkSimple(s1)
    call invertSimple(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple)
  end function passinoutSimple_impl


  ! 
  ! Method:  passeverywhereSimple[]
  ! 

  function passeverywhereSimple_impl(self, s1, s2, s3, exception) result(      &
    retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Simple s1
    type(s_Simple_t), intent(in) :: s1
    !  out s.Simple s2
    type(s_Simple_t), intent(out) :: s2
    !  inout s.Simple s3
    type(s_Simple_t), intent(inout) :: s3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Simple_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple)
    ! insert code here (passeverywhereSimple method)
    ! logical checkSimple

    call initSimple(s2)
    call initSimple(retval)
    if (checkSimple(s1) .and. checkSimple(s2)) then
       call invertSimple(s3)
    endif

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple)
  end function passeverywhereSimple_impl


  ! 
  ! Method:  returnHard[]
  ! 

  function returnHard_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnHard.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnHard.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Hard_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnHard)
    ! insert code here (returnHard method)
    call initHard(retval,exception)

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnHard)
  end function returnHard_impl


  ! 
  ! Method:  passinHard[]
  ! 

  function passinHard_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinHard.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinHard.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Hard s1
    type(s_Hard_t), intent(in) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinHard)
    ! insert code here (passinHard method)
    ! logical checkHard
    retval = checkHard(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinHard)
  end function passinHard_impl


  ! 
  ! Method:  passoutHard[]
  ! 

  function passoutHard_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutHard.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out s.Hard s1
    type(s_Hard_t), intent(out) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard)
    ! insert code here (passoutHard method)
    retval = .true.
    call initHard(s1, exception)
    if (not_null(exception)) then
       retval = .false.
    endif
    
    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutHard)
  end function passoutHard_impl


  ! 
  ! Method:  passinoutHard[]
  ! 

  function passinoutHard_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  inout s.Hard s1
    type(s_Hard_t), intent(inout) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard)
    ! insert code here (passinoutHard method)
    ! logical checkHard
    retval = checkHard(s1)
    call invertHard(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard)
  end function passinoutHard_impl


  ! 
  ! Method:  passeverywhereHard[]
  ! 

  function passeverywhereHard_impl(self, s1, s2, s3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Hard s1
    type(s_Hard_t), intent(in) :: s1
    !  out s.Hard s2
    type(s_Hard_t), intent(out) :: s2
    !  inout s.Hard s3
    type(s_Hard_t), intent(inout) :: s3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Hard_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard)
    ! insert code here (passeverywhereHard method)
    ! logical checkHard
 
    call initHard(retval,exception)
    call initHard(s2,exception)
    if (checkHard(s1).and.checkHard(s3)) then
       call invertHard(s3)
    endif

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard)
  end function passeverywhereHard_impl


  ! 
  ! Method:  returnCombined[]
  ! 

  function returnCombined_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnCombined.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Combined_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined)
    ! insert code here (returnCombined method)
    call initCombined(retval,exception)

    ! DO-NOT-DELETE splicer.end(s.StructTest.returnCombined)
  end function returnCombined_impl


  ! 
  ! Method:  passinCombined[]
  ! 

  function passinCombined_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinCombined.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Combined s1
    type(s_Combined_t), intent(in) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined)
    ! insert code here (passinCombined method)
    ! logical checkCombined
    retval=.false.
    retval=checkCombined(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinCombined)
  end function passinCombined_impl


  ! 
  ! Method:  passoutCombined[]
  ! 

  function passoutCombined_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  out s.Combined s1
    type(s_Combined_t), intent(out) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined)
    ! insert code here (passoutCombined method)
    retval = .true.
    call initCombined(s1, exception)
    if (not_null(exception)) then
       retval = .false.
    endif

    ! DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined)
  end function passoutCombined_impl


  ! 
  ! Method:  passinoutCombined[]
  ! 

  function passinoutCombined_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  inout s.Combined s1
    type(s_Combined_t), intent(inout) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined)
    ! insert code here (passinoutCombined method)
    ! logical checkCombined
    retval = checkCombined(s1)
    call invertCombined(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined)
  end function passinoutCombined_impl


  ! 
  ! Method:  passeverywhereCombined[]
  ! 

  function passeverywhereCombined_impl(self, s1, s2, s3, exception) result(    &
    retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Combined s1
    type(s_Combined_t), intent(in) :: s1
    !  out s.Combined s2
    type(s_Combined_t), intent(out) :: s2
    !  inout s.Combined s3
    type(s_Combined_t), intent(inout) :: s3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(s_Combined_t) :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined)
    ! insert code here (passeverywhereCombined method)
    ! 
    ! logical checkCombined

    call initCombined(retval,exception)
    call initCombined(s2,exception)
    if (checkCombined(s1).and.checkCombined(s3)) then
       call invertCombined(s3)
    endif

    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined)
  end function passeverywhereCombined_impl


  ! 
  ! Method:  passinRarrays[]
  ! 

  function passinRarrays_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays.use)
    ! insert code here (use statements)
    ! DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Rarrays s1
    type(s_Rarrays_t), intent(in) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays)
    ! 
    retval = checkRarrays(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays)
  end function passinRarrays_impl


  ! 
  ! Method:  passinoutRarrays[]
  ! 

  function passinoutRarrays_impl(self, s1, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays.use)
    ! insert code here (use statements)
    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  inout s.Rarrays s1
    type(s_Rarrays_t), intent(inout) :: s1
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays)
    ! insert code here (passinoutRarrays method)
    ! 
    retval = checkRarrays(s1)
    call invertRarrays(s1)

    ! DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays)
  end function passinoutRarrays_impl


  ! 
  ! Method:  passeverywhereRarrays[]
  ! 

  function passeverywhereRarrays_impl(self, s1, s2, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays.use)
    ! insert code here (use statements)
    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays.use)
    implicit none
    !  in s.StructTest self
    type(s_StructTest_impl_t) :: self
    !  in s.Rarrays s1
    type(s_Rarrays_t), intent(in) :: s1
    !  inout s.Rarrays s2
    type(s_Rarrays_t), intent(inout) :: s2
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays)
    ! 
    if (checkRarrays(s1) .and. checkRarrays(s2)) then
       call invertRarrays(s2)
       retval=.true.
    endif
    
    ! DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays)
  end function passeverywhereRarrays_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module s_StructTest_Impl
