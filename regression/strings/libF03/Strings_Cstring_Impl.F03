! 
! File:          Strings_Cstring_Impl.F03
! Symbol:        Strings.Cstring-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Strings.Cstring
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Strings.Cstring" (version 1.1)
! 
! Class to allow testing of string passing using every possible mode.
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Strings_Cstring_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Strings_Cstring_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Strings_Cstring_t) :: Strings_Cstring_impl_t
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.private_data)
  end type Strings_Cstring_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Strings_Cstring_impl_t), target :: obj
    type(Strings_Cstring_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Strings_Cstring_wrap
    call Strings_Cstring_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring._ctor.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring._dtor.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring._load)
  end subroutine load_impl


  ! 
  ! Method:  returnback[]
  ! If <code>nonNull</code> is <code>true</code>, this will
  ! return "Three"; otherwise, it will return a NULL or empty string.
  ! 

  function returnback_impl(self, nonNull, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.returnback.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  in bool nonNull
    logical, intent(in) :: nonNull
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback)
    if (nonNull) then
       retval = 'Three'
    else
       retval = ''
    endif
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.returnback)
  end function returnback_impl


  ! 
  ! Method:  passin[]
  ! This will return <code>true</code> iff <code>c</code> equals "Three".
  ! 

  function passin_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passin.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passin.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  in string c
    character (len=*), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passin)
    retval = (c .eq. 'Three')
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passin)
  end function passin_impl


  ! 
  ! Method:  passout[]
  ! If <code>nonNull</code> is <code>true</code>, this will return
  ! "Three" in <code>c</code>; otherwise, it will return a null or
  ! empty string. The return value is <code>false</code> iff 
  ! the outgoing value of <code>c</code> is <code>null</code>.
  ! 

  function passout_impl(self, nonNull, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passout.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  in bool nonNull
    logical, intent(in) :: nonNull
    !  out string c
    character (len=*), intent(out) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passout)
    if (nonNull) then
       c = 'Three'
       retval = .true.
    else
       c = ''
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passout)
  end function passout_impl


  ! 
  ! Method:  passinout[]
  ! 

  function passinout_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passinout.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  inout string c
    character (len=*), intent(inout) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout)
    integer i
    retval = .false.
    if (len(c) .ge. 1) then
       do i = len(c), 1, -1
          if (c(i:i) .ne. ' ') then
             if (i .lt. len(c)) then
                c(i+1:i+1) = 's'
                retval = .true.
             endif
             goto 100
          endif
       end do
100    if (c(1:1) .ge. 'a' .and. c(1:1) .le. 'z') then
          c(1:1) = char(ichar(c(1:1)) - 32)
       else
          if (c(1:1) .ge. 'A' .and. c(1:1) .le. 'Z') then
             c(1:1) = char(ichar(c(1:1)) + 32)
          endif
       endif
    else
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passinout)
  end function passinout_impl


  ! 
  ! Method:  passeverywhere[]
  ! 

  function passeverywhere_impl(self, c1, c2, c3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  in string c1
    character (len=*), intent(in) :: c1
    !  out string c2
    character (len=*), intent(out) :: c2
    !  inout string c3
    character (len=*), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere)
    integer i
    c2 = 'Three'
    if (c1 .eq. 'Three') then
       retval = 'Three'
    else
       retval = ''
    endif
    if (len(c3) .ge. 1) then
       do i = len(c3), 1, -1
          if (c3(i:i) .ne. ' ') then
             c3(i:i) = ' '
             goto 100
          endif
       end do
100    if (c3(1:1) .ge. 'a' .and. c3(1:1) .le. 'z') then
          c3(1:1) = char(ichar(c3(1:1)) - 32)
       else
          if (c3(1:1) .ge. 'A' .and. c3(1:1) .le. 'Z') then
             c3(1:1) = char(ichar(c3(1:1)) + 32)
          endif
       endif
    endif
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere)
  end function passeverywhere_impl


  ! 
  ! Method:  mixedarguments[]
  !  return true iff s1 == s2 and c1 == c2 
  ! 

  function mixedarguments_impl(self, s1, c1, s2, c2, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments.use)
    implicit none
    !  in Strings.Cstring self
    type(Strings_Cstring_impl_t) :: self
    !  in string s1
    character (len=*), intent(in) :: s1
    !  in char c1
    character (len=1), intent(in) :: c1
    !  in string s2
    character (len=*), intent(in) :: s2
    !  in char c2
    character (len=1), intent(in) :: c2
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments)
    retval = ((c1 .eq. c2) .and. (s1 .eq. s2))
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments)
  end function mixedarguments_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Strings_Cstring_Impl
