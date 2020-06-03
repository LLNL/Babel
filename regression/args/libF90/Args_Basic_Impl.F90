! 
! File:          Args_Basic_Impl.F90
! Symbol:        Args.Basic-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Args.Basic
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Args.Basic" (version 1.0)
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "Args_Basic_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Args_Basic__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic._ctor.use)
  ! Insert-Code-Here {Args.Basic._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic._ctor.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic._ctor)
! DO-NOT-DELETE splicer.end(Args.Basic._ctor)
end subroutine Args_Basic__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Args_Basic__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic._ctor2.use)
  ! Insert-Code-Here {Args.Basic._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic._ctor2.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  type(Args_Basic_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic._ctor2)
! DO-NOT-DELETE splicer.end(Args.Basic._ctor2)
end subroutine Args_Basic__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Args_Basic__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic._dtor.use)
  ! Insert-Code-Here {Args.Basic._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic._dtor.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic._dtor)
! DO-NOT-DELETE splicer.end(Args.Basic._dtor)
end subroutine Args_Basic__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Args_Basic__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic._load.use)
  ! Insert-Code-Here {Args.Basic._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic._load)
! DO-NOT-DELETE splicer.end(Args.Basic._load)
end subroutine Args_Basic__load_mi


! 
! Method:  returnbackbool[]
! 

recursive subroutine Args_Basic_returnbackbool_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool.use)
  ! Insert-Code-Here {Args.Basic.returnbackbool.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool)
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool)
end subroutine Args_Basic_returnbackbool_mi


! 
! Method:  passinbool[]
! 

recursive subroutine Args_Basic_passinbool_mi(self, b, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinbool.use)
  ! Insert-Code-Here {Args.Basic.passinbool.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinbool.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  logical :: b
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinbool)
  if (b) then
     retval = .true.
  else
     retval = .false.
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passinbool)
end subroutine Args_Basic_passinbool_mi


! 
! Method:  passoutbool[]
! 

recursive subroutine Args_Basic_passoutbool_mi(self, b, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool.use)
  ! Insert-Code-Here {Args.Basic.passoutbool.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutbool.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  logical :: b
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool)
  b = .true.
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutbool)
end subroutine Args_Basic_passoutbool_mi


! 
! Method:  passinoutbool[]
! 

recursive subroutine Args_Basic_passinoutbool_mi(self, b, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool.use)
  ! Insert-Code-Here {Args.Basic.passinoutbool.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  logical :: b
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool)
  b = .not. b
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool)
end subroutine Args_Basic_passinoutbool_mi


! 
! Method:  passeverywherebool[]
! 

recursive subroutine passeverywherebool7mqm8266ce_mi(self, b1, b2, b3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool.use)
  ! Insert-Code-Here {Args.Basic.passeverywherebool.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  logical :: b1
  ! in
  logical :: b2
  ! out
  logical :: b3
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool)
  b2 = .true.
  b3 = .not. b3
  if (b1) then
     retval = .true.
  else
     retval = .false.
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool)
end subroutine passeverywherebool7mqm8266ce_mi


! 
! Method:  returnbackchar[]
! 

recursive subroutine Args_Basic_returnbackchar_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar.use)
  ! Insert-Code-Here {Args.Basic.returnbackchar.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  character (len=1) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar)
  retval = '3'
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar)
end subroutine Args_Basic_returnbackchar_mi


! 
! Method:  passinchar[]
! 

recursive subroutine Args_Basic_passinchar_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinchar.use)
  ! Insert-Code-Here {Args.Basic.passinchar.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinchar.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  character (len=1) :: c
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinchar)
  retval = (c .eq. '3')
! DO-NOT-DELETE splicer.end(Args.Basic.passinchar)
end subroutine Args_Basic_passinchar_mi


! 
! Method:  passoutchar[]
! 

recursive subroutine Args_Basic_passoutchar_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar.use)
  ! Insert-Code-Here {Args.Basic.passoutchar.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutchar.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  character (len=1) :: c
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar)
  c = '3'
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutchar)
end subroutine Args_Basic_passoutchar_mi


! 
! Method:  passinoutchar[]
! 

recursive subroutine Args_Basic_passinoutchar_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar.use)
  ! Insert-Code-Here {Args.Basic.passinoutchar.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  character (len=1) :: c
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar)
  retval = .false.
  if (c .ge. 'A' .and. c .le. 'Z') then
     c = char(ichar(c) + 32)
     retval = .true.
  else
     if (c .ge. 'a' .and. c .le. 'z') then
        c = char(ichar(c) - 32)
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar)
end subroutine Args_Basic_passinoutchar_mi


! 
! Method:  passeverywherechar[]
! 

recursive subroutine passeverywherecharoiciivaf0r_mi(self, c1, c2, c3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar.use)
  ! Insert-Code-Here {Args.Basic.passeverywherechar.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  character (len=1) :: c1
  ! in
  character (len=1) :: c2
  ! out
  character (len=1) :: c3
  ! inout
  character (len=1) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar)
  retval = ' '
  c2 = '3'
  if (c3 .ge. 'A' .and. c3 .le. 'Z') then
     c3 = char(ichar(c3) + 32)
  else
     if (c3 .ge. 'a' .and. c3 .le. 'z') then
        c3 = char(ichar(c3) - 32)
     endif
  endif
  if (c1 .eq. '3') then
     retval = '3'
  else
     retval = ' '
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar)
end subroutine passeverywherecharoiciivaf0r_mi


! 
! Method:  returnbackint[]
! 

recursive subroutine Args_Basic_returnbackint_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint.use)
  ! Insert-Code-Here {Args.Basic.returnbackint.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackint.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint)
  retval = 3
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackint)
end subroutine Args_Basic_returnbackint_mi


! 
! Method:  passinint[]
! 

recursive subroutine Args_Basic_passinint_mi(self, i, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinint.use)
  ! Insert-Code-Here {Args.Basic.passinint.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinint.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinint)
  retval = (i .eq. 3)
! DO-NOT-DELETE splicer.end(Args.Basic.passinint)
end subroutine Args_Basic_passinint_mi


! 
! Method:  passoutint[]
! 

recursive subroutine Args_Basic_passoutint_mi(self, i, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutint.use)
  ! Insert-Code-Here {Args.Basic.passoutint.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutint.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutint)
  i = 3
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutint)
end subroutine Args_Basic_passoutint_mi


! 
! Method:  passinoutint[]
! 

recursive subroutine Args_Basic_passinoutint_mi(self, i, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint.use)
  ! Insert-Code-Here {Args.Basic.passinoutint.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutint.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint)
  i = -i
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutint)
end subroutine Args_Basic_passinoutint_mi


! 
! Method:  passeverywhereint[]
! 

recursive subroutine Args_Basic_passeverywhereint_mi(self, i1, i2, i3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint.use)
  ! Insert-Code-Here {Args.Basic.passeverywhereint.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_int) :: i1
  ! in
  integer (kind=sidl_int) :: i2
  ! out
  integer (kind=sidl_int) :: i3
  ! inout
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint)
  i2 = 3
  i3 = -i3
  if (i1 .eq. 3) then
     retval = 3
  else
     retval = 0
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint)
end subroutine Args_Basic_passeverywhereint_mi


! 
! Method:  returnbacklong[]
! 

recursive subroutine Args_Basic_returnbacklong_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong.use)
  ! Insert-Code-Here {Args.Basic.returnbacklong.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_long) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong)
  retval = 3
! DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong)
end subroutine Args_Basic_returnbacklong_mi


! 
! Method:  passinlong[]
! 

recursive subroutine Args_Basic_passinlong_mi(self, l, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinlong.use)
  ! Insert-Code-Here {Args.Basic.passinlong.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinlong.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_long) :: l
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinlong)
  retval = (l .eq. 3)
! DO-NOT-DELETE splicer.end(Args.Basic.passinlong)
end subroutine Args_Basic_passinlong_mi


! 
! Method:  passoutlong[]
! 

recursive subroutine Args_Basic_passoutlong_mi(self, l, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong.use)
  ! Insert-Code-Here {Args.Basic.passoutlong.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutlong.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_long) :: l
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong)
  l = 3
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutlong)
end subroutine Args_Basic_passoutlong_mi


! 
! Method:  passinoutlong[]
! 

recursive subroutine Args_Basic_passinoutlong_mi(self, l, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong.use)
  ! Insert-Code-Here {Args.Basic.passinoutlong.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_long) :: l
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong)
  l = -l
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong)
end subroutine Args_Basic_passinoutlong_mi


! 
! Method:  passeverywherelong[]
! 

recursive subroutine passeverywherelongu1lhnastoa_mi(self, l1, l2, l3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong.use)
  ! Insert-Code-Here {Args.Basic.passeverywherelong.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  integer (kind=sidl_long) :: l1
  ! in
  integer (kind=sidl_long) :: l2
  ! out
  integer (kind=sidl_long) :: l3
  ! inout
  integer (kind=sidl_long) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong)
  l2 = 3
  l3 = -l3
  if (l1 .eq. 3) then
     retval = 3
  else
     retval = 0
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong)
end subroutine passeverywherelongu1lhnastoa_mi


! 
! Method:  returnbackfloat[]
! 

recursive subroutine Args_Basic_returnbackfloat_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat.use)
  ! Insert-Code-Here {Args.Basic.returnbackfloat.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_float) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat)
  retval = 3.1_sidl_float
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat)
end subroutine Args_Basic_returnbackfloat_mi


! 
! Method:  passinfloat[]
! 

recursive subroutine Args_Basic_passinfloat_mi(self, f, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat.use)
  ! Insert-Code-Here {Args.Basic.passinfloat.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinfloat.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_float) :: f
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat)
  retval = (f .eq. 3.1_sidl_float)
! DO-NOT-DELETE splicer.end(Args.Basic.passinfloat)
end subroutine Args_Basic_passinfloat_mi


! 
! Method:  passoutfloat[]
! 

recursive subroutine Args_Basic_passoutfloat_mi(self, f, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat.use)
  ! Insert-Code-Here {Args.Basic.passoutfloat.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_float) :: f
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat)
  f = 3.1_sidl_float
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat)
end subroutine Args_Basic_passoutfloat_mi


! 
! Method:  passinoutfloat[]
! 

recursive subroutine Args_Basic_passinoutfloat_mi(self, f, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat.use)
  ! Insert-Code-Here {Args.Basic.passinoutfloat.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_float) :: f
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat)
  f = -f
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat)
end subroutine Args_Basic_passinoutfloat_mi


! 
! Method:  passeverywherefloat[]
! 

recursive subroutine passeverywherefloamg_birwzqi_mi(self, f1, f2, f3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat.use)
  ! Insert-Code-Here {Args.Basic.passeverywherefloat.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_float) :: f1
  ! in
  real (kind=sidl_float) :: f2
  ! out
  real (kind=sidl_float) :: f3
  ! inout
  real (kind=sidl_float) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat)
  f2 = 3.1_sidl_float
  f3 = -f3
  if (f1 .eq. 3.1_sidl_float) then
     retval = 3.1_sidl_float
  else
     retval = 0.0_sidl_float
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat)
end subroutine passeverywherefloamg_birwzqi_mi


! 
! Method:  returnbackdouble[]
! 

recursive subroutine Args_Basic_returnbackdouble_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble.use)
  ! Insert-Code-Here {Args.Basic.returnbackdouble.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble)
  retval = 3.14_sidl_double
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble)
end subroutine Args_Basic_returnbackdouble_mi


! 
! Method:  passindouble[]
! 

recursive subroutine Args_Basic_passindouble_mi(self, d, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passindouble.use)
  ! Insert-Code-Here {Args.Basic.passindouble.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passindouble.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_double) :: d
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passindouble)
  retval = (d .eq. 3.14_sidl_double)
! DO-NOT-DELETE splicer.end(Args.Basic.passindouble)
end subroutine Args_Basic_passindouble_mi


! 
! Method:  passoutdouble[]
! 

recursive subroutine Args_Basic_passoutdouble_mi(self, d, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble.use)
  ! Insert-Code-Here {Args.Basic.passoutdouble.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_double) :: d
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble)
  d = 3.14_sidl_double
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble)
end subroutine Args_Basic_passoutdouble_mi


! 
! Method:  passinoutdouble[]
! 

recursive subroutine Args_Basic_passinoutdouble_mi(self, d, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble.use)
  ! Insert-Code-Here {Args.Basic.passinoutdouble.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_double) :: d
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble)
  d = -d
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble)
end subroutine Args_Basic_passinoutdouble_mi


! 
! Method:  passeverywheredouble[]
! 

recursive subroutine passeverywheredoubiie743pwx__mi(self, d1, d2, d3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble.use)
  ! Insert-Code-Here {Args.Basic.passeverywheredouble.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  real (kind=sidl_double) :: d1
  ! in
  real (kind=sidl_double) :: d2
  ! out
  real (kind=sidl_double) :: d3
  ! inout
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble)
  d2 = 3.14_sidl_double
  d3 = -d3
  if (d1 .eq. 3.14_sidl_double) then
     retval = 3.14_sidl_double
  else
     retval = 0.0_sidl_double
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble)
end subroutine passeverywheredoubiie743pwx__mi


! 
! Method:  returnbackfcomplex[]
! 

recursive subroutine returnbackfcomplex5hu6v_9fkg_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex.use)
  ! Insert-Code-Here {Args.Basic.returnbackfcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_fcomplex) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex)
  retval = (3.1_sidl_float, 3.1_sidl_float)
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex)
end subroutine returnbackfcomplex5hu6v_9fkg_mi


! 
! Method:  passinfcomplex[]
! 

recursive subroutine Args_Basic_passinfcomplex_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex.use)
  ! Insert-Code-Here {Args.Basic.passinfcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_fcomplex) :: c
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex)
  if (real(c) .eq. 3.1_sidl_float .and. aimag(c) .eq. 3.1_sidl_float) then
     retval = .true.
  else
     retval = .false.
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex)
end subroutine Args_Basic_passinfcomplex_mi


! 
! Method:  passoutfcomplex[]
! 

recursive subroutine Args_Basic_passoutfcomplex_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex.use)
  ! Insert-Code-Here {Args.Basic.passoutfcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_fcomplex) :: c
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex)
  c = (3.1_sidl_float, 3.1_sidl_float)
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex)
end subroutine Args_Basic_passoutfcomplex_mi


! 
! Method:  passinoutfcomplex[]
! 

recursive subroutine Args_Basic_passinoutfcomplex_mi(self, c, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex.use)
  ! Insert-Code-Here {Args.Basic.passinoutfcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_fcomplex) :: c
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex)
  c = conjg(c)
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex)
end subroutine Args_Basic_passinoutfcomplex_mi


! 
! Method:  passeverywherefcomplex[]
! 

recursive subroutine passeverywherefcomv1mfs_pco5_mi(self, c1, c2, c3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex.use)
  ! Insert-Code-Here {Args.Basic.passeverywherefcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_fcomplex) :: c1
  ! in
  complex (kind=sidl_fcomplex) :: c2
  ! out
  complex (kind=sidl_fcomplex) :: c3
  ! inout
  complex (kind=sidl_fcomplex) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex)
  c2 = (3.1_sidl_float, 3.1_sidl_float)
  c3 = conjg(c3)
  if (real(c1) .eq. 3.1_sidl_float .and. aimag(c1) .eq. 3.1_sidl_float) then
     retval = c1
  else
     retval = (0.0_sidl_float, 0.0_sidl_float)
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex)
end subroutine passeverywherefcomv1mfs_pco5_mi


! 
! Method:  returnbackdcomplex[]
! 

recursive subroutine returnbackdcomplexmlrumh2spz_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex.use)
  ! Insert-Code-Here {Args.Basic.returnbackdcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_dcomplex) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex)
  retval = (3.14_sidl_double, 3.14_sidl_double)
! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex)
end subroutine returnbackdcomplexmlrumh2spz_mi


! 
! Method:  passindcomplex[]
! 

recursive subroutine Args_Basic_passindcomplex_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex.use)
  ! Insert-Code-Here {Args.Basic.passindcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_dcomplex) :: c
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex)
  if (3.14_sidl_double .eq. dreal(c) .and.  3.14_sidl_double .eq. dimag(c)) then
     retval = .true.
  else
     retval = .false.
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex)
end subroutine Args_Basic_passindcomplex_mi


! 
! Method:  passoutdcomplex[]
! 

recursive subroutine Args_Basic_passoutdcomplex_mi(self, c, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex.use)
  ! Insert-Code-Here {Args.Basic.passoutdcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_dcomplex) :: c
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex)
  c = (3.14_sidl_double, 3.14_sidl_double)
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex)
end subroutine Args_Basic_passoutdcomplex_mi


! 
! Method:  passinoutdcomplex[]
! 

recursive subroutine Args_Basic_passinoutdcomplex_mi(self, c, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex.use)
  ! Insert-Code-Here {Args.Basic.passinoutdcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_dcomplex) :: c
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex)
  c = conjg(c)
  retval = .true.
! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex)
end subroutine Args_Basic_passinoutdcomplex_mi


! 
! Method:  passeverywheredcomplex[]
! 

recursive subroutine passeverywheredcomq037lt3x42_mi(self, c1, c2, c3, retval, &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  use Args_Basic_impl
  ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex.use)
  ! Insert-Code-Here {Args.Basic.passeverywheredcomplex.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex.use)
  implicit none
  type(Args_Basic_t) :: self
  ! in
  complex (kind=sidl_dcomplex) :: c1
  ! in
  complex (kind=sidl_dcomplex) :: c2
  ! out
  complex (kind=sidl_dcomplex) :: c3
  ! inout
  complex (kind=sidl_dcomplex) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex)
  c2 = (3.14_sidl_double, 3.14_sidl_double)
  c3 = conjg(c3)
  if (3.14_sidl_double .eq. dreal(c1) .and.  3.14_sidl_double .eq. dimag(c1)) then
     retval = c2
  else
     retval = (0.0_sidl_double, 0.0_sidl_double)
  endif
! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex)
end subroutine passeverywheredcomq037lt3x42_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
