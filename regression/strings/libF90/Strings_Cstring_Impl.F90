! 
! File:          Strings_Cstring_Impl.F90
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
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Strings_Cstring__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring._ctor.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Strings.Cstring._ctor)
end subroutine Strings_Cstring__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Strings_Cstring__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor2.use)
  ! Insert-Code-Here {Strings.Cstring._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Strings.Cstring._ctor2.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  type(Strings_Cstring_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor2)
! Insert-Code-Here {Strings.Cstring._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Strings.Cstring._ctor2)
end subroutine Strings_Cstring__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Strings_Cstring__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring._dtor.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Strings.Cstring._dtor)
end subroutine Strings_Cstring__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Strings_Cstring__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Strings.Cstring._load)
end subroutine Strings_Cstring__load_mi


! 
! Method:  returnback[]
! If <code>nonNull</code> is <code>true</code>, this will
! return "Three"; otherwise, it will return a NULL or empty string.
! 

recursive subroutine Strings_Cstring_returnback_mi(self, nonNull, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.returnback.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  logical :: nonNull
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback)
  if (nonNull) then
     retval = 'Three'
  else
     retval = ''
  endif
! DO-NOT-DELETE splicer.end(Strings.Cstring.returnback)
end subroutine Strings_Cstring_returnback_mi


! 
! Method:  passin[]
! This will return <code>true</code> iff <code>c</code> equals "Three".
! 

recursive subroutine Strings_Cstring_passin_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passin.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.passin.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  character (len=*) :: c
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring.passin)
  retval = (c .eq. 'Three')
! DO-NOT-DELETE splicer.end(Strings.Cstring.passin)
end subroutine Strings_Cstring_passin_mi


! 
! Method:  passout[]
! If <code>nonNull</code> is <code>true</code>, this will return
! "Three" in <code>c</code>; otherwise, it will return a null or
! empty string. The return value is <code>false</code> iff 
! the outgoing value of <code>c</code> is <code>null</code>.
! 

recursive subroutine Strings_Cstring_passout_mi(self, nonNull, c, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.passout.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  logical :: nonNull
  ! in
  character (len=*) :: c
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring.passout)
  if (nonNull) then
     c = 'Three'
     retval = .true.
  else
     c = ''
     retval = .false.
  endif
! DO-NOT-DELETE splicer.end(Strings.Cstring.passout)
end subroutine Strings_Cstring_passout_mi


! 
! Method:  passinout[]
! 

recursive subroutine Strings_Cstring_passinout_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.passinout.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  character (len=*) :: c
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

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
100  if (c(1:1) .ge. 'a' .and. c(1:1) .le. 'z') then
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
end subroutine Strings_Cstring_passinout_mi


! 
! Method:  passeverywhere[]
! 

recursive subroutine Cst_passeverywhere1q5fsozb7a_mi(self, c1, c2, c3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  character (len=*) :: c1
  ! in
  character (len=*) :: c2
  ! out
  character (len=*) :: c3
  ! inout
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

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
100  if (c3(1:1) .ge. 'a' .and. c3(1:1) .le. 'z') then
        c3(1:1) = char(ichar(c3(1:1)) - 32)
     else
        if (c3(1:1) .ge. 'A' .and. c3(1:1) .le. 'Z') then
           c3(1:1) = char(ichar(c3(1:1)) + 32)
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere)
end subroutine Cst_passeverywhere1q5fsozb7a_mi


! 
! Method:  mixedarguments[]
!  return true iff s1 == s2 and c1 == c2 
! 

recursive subroutine Cst_mixedargumentsgdf1o24zbg_mi(self, s1, c1, s2, c2,     &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Strings_Cstring
  use Strings_Cstring_impl
  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments.use)
  implicit none
  type(Strings_Cstring_t) :: self
  ! in
  character (len=*) :: s1
  ! in
  character (len=1) :: c1
  ! in
  character (len=*) :: s2
  ! in
  character (len=1) :: c2
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments)
  retval = ((c1 .eq. c2) .and. (s1 .eq. s2))
! DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments)
end subroutine Cst_mixedargumentsgdf1o24zbg_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
