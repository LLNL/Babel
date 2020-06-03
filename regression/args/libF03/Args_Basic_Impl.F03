! 
! File:          Args_Basic_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Args_Basic_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Args_Basic
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Args_Basic_t) :: Args_Basic_impl_t
    ! DO-NOT-DELETE splicer.begin(Args.Basic.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Args.Basic.private_data)
  end type Args_Basic_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Args_Basic_impl_t), target :: obj
    type(Args_Basic_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Args_Basic_wrap
    call Args_Basic_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Args.Basic._ctor.use)
    ! Insert-Code-Here {Args.Basic._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic._ctor.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Args.Basic._ctor)
    ! DO-NOT-DELETE splicer.end(Args.Basic._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Args.Basic._dtor.use)
    ! Insert-Code-Here {Args.Basic._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic._dtor.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Args.Basic._dtor)
    ! DO-NOT-DELETE splicer.end(Args.Basic._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Args.Basic._load.use)
    ! Insert-Code-Here {Args.Basic._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Args.Basic._load)
    ! DO-NOT-DELETE splicer.end(Args.Basic._load)
  end subroutine load_impl


  ! 
  ! Method:  returnbackbool[]
  ! 

  function returnbackbool_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool.use)
    ! Insert-Code-Here {Args.Basic.returnbackbool.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool)
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool)
  end function returnbackbool_impl


  ! 
  ! Method:  passinbool[]
  ! 

  function passinbool_impl(self, b, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinbool.use)
    ! Insert-Code-Here {Args.Basic.passinbool.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinbool.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in bool b
    logical, intent(in) :: b
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinbool)
    if (b) then
       retval = .true.
    else
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinbool)
  end function passinbool_impl


  ! 
  ! Method:  passoutbool[]
  ! 

  function passoutbool_impl(self, b, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool.use)
    ! Insert-Code-Here {Args.Basic.passoutbool.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutbool.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out bool b
    logical, intent(out) :: b
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool)
    b = .true.
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutbool)
  end function passoutbool_impl


  ! 
  ! Method:  passinoutbool[]
  ! 

  function passinoutbool_impl(self, b, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool.use)
    ! Insert-Code-Here {Args.Basic.passinoutbool.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout bool b
    logical, intent(inout) :: b
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool)
    b = .not. b
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool)
  end function passinoutbool_impl


  ! 
  ! Method:  passeverywherebool[]
  ! 

  function passeverywherebool_impl(self, b1, b2, b3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool.use)
    ! Insert-Code-Here {Args.Basic.passeverywherebool.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in bool b1
    logical, intent(in) :: b1
    !  out bool b2
    logical, intent(out) :: b2
    !  inout bool b3
    logical, intent(inout) :: b3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool)
    b2 = .true.
    b3 = .not. b3
    if (b1) then
       retval = .true.
    else
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool)
  end function passeverywherebool_impl


  ! 
  ! Method:  returnbackchar[]
  ! 

  function returnbackchar_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar.use)
    ! Insert-Code-Here {Args.Basic.returnbackchar.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=1) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar)
    retval = '3'
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar)
  end function returnbackchar_impl


  ! 
  ! Method:  passinchar[]
  ! 

  function passinchar_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinchar.use)
    ! Insert-Code-Here {Args.Basic.passinchar.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinchar.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in char c
    character (len=1), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinchar)
    retval = (c .eq. '3')
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinchar)
  end function passinchar_impl


  ! 
  ! Method:  passoutchar[]
  ! 

  function passoutchar_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar.use)
    ! Insert-Code-Here {Args.Basic.passoutchar.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutchar.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out char c
    character (len=1), intent(out) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar)
    c = '3'
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutchar)
  end function passoutchar_impl


  ! 
  ! Method:  passinoutchar[]
  ! 

  function passinoutchar_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar.use)
    ! Insert-Code-Here {Args.Basic.passinoutchar.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout char c
    character (len=1), intent(inout) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

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
  end function passinoutchar_impl


  ! 
  ! Method:  passeverywherechar[]
  ! 

  function passeverywherechar_impl(self, c1, c2, c3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar.use)
    ! Insert-Code-Here {Args.Basic.passeverywherechar.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in char c1
    character (len=1), intent(in) :: c1
    !  out char c2
    character (len=1), intent(out) :: c2
    !  inout char c3
    character (len=1), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=1) :: retval

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
  end function passeverywherechar_impl


  ! 
  ! Method:  returnbackint[]
  ! 

  function returnbackint_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint.use)
    ! Insert-Code-Here {Args.Basic.returnbackint.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackint.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint)
    retval = 3
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackint)
  end function returnbackint_impl


  ! 
  ! Method:  passinint[]
  ! 

  function passinint_impl(self, i, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinint.use)
    ! Insert-Code-Here {Args.Basic.passinint.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinint.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinint)
    retval = (i .eq. 3)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinint)
  end function passinint_impl


  ! 
  ! Method:  passoutint[]
  ! 

  function passoutint_impl(self, i, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutint.use)
    ! Insert-Code-Here {Args.Basic.passoutint.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutint.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out int i
    integer (kind=sidl_int), intent(out) :: i
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutint)
    i = 3
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutint)
  end function passoutint_impl


  ! 
  ! Method:  passinoutint[]
  ! 

  function passinoutint_impl(self, i, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint.use)
    ! Insert-Code-Here {Args.Basic.passinoutint.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutint.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout int i
    integer (kind=sidl_int), intent(inout) :: i
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint)
    i = -i
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutint)
  end function passinoutint_impl


  ! 
  ! Method:  passeverywhereint[]
  ! 

  function passeverywhereint_impl(self, i1, i2, i3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint.use)
    ! Insert-Code-Here {Args.Basic.passeverywhereint.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in int i1
    integer (kind=sidl_int), intent(in) :: i1
    !  out int i2
    integer (kind=sidl_int), intent(out) :: i2
    !  inout int i3
    integer (kind=sidl_int), intent(inout) :: i3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint)
    i2 = 3
    i3 = -i3
    if (i1 .eq. 3) then
       retval = 3
    else
       retval = 0
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint)
  end function passeverywhereint_impl


  ! 
  ! Method:  returnbacklong[]
  ! 

  function returnbacklong_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong.use)
    ! Insert-Code-Here {Args.Basic.returnbacklong.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_long) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong)
    retval = 3
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong)
  end function returnbacklong_impl


  ! 
  ! Method:  passinlong[]
  ! 

  function passinlong_impl(self, l, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinlong.use)
    ! Insert-Code-Here {Args.Basic.passinlong.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinlong.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in long l
    integer (kind=sidl_long), intent(in) :: l
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinlong)
    retval = (l .eq. 3)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinlong)
  end function passinlong_impl


  ! 
  ! Method:  passoutlong[]
  ! 

  function passoutlong_impl(self, l, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong.use)
    ! Insert-Code-Here {Args.Basic.passoutlong.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutlong.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out long l
    integer (kind=sidl_long), intent(out) :: l
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong)
    l = 3
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutlong)
  end function passoutlong_impl


  ! 
  ! Method:  passinoutlong[]
  ! 

  function passinoutlong_impl(self, l, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong.use)
    ! Insert-Code-Here {Args.Basic.passinoutlong.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout long l
    integer (kind=sidl_long), intent(inout) :: l
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong)
    l = -l
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong)
  end function passinoutlong_impl


  ! 
  ! Method:  passeverywherelong[]
  ! 

  function passeverywherelong_impl(self, l1, l2, l3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong.use)
    ! Insert-Code-Here {Args.Basic.passeverywherelong.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in long l1
    integer (kind=sidl_long), intent(in) :: l1
    !  out long l2
    integer (kind=sidl_long), intent(out) :: l2
    !  inout long l3
    integer (kind=sidl_long), intent(inout) :: l3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_long) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong)
    l2 = 3
    l3 = -l3
    if (l1 .eq. 3) then
       retval = 3
    else
       retval = 0
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong)
  end function passeverywherelong_impl


  ! 
  ! Method:  returnbackfloat[]
  ! 

  function returnbackfloat_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat.use)
    ! Insert-Code-Here {Args.Basic.returnbackfloat.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_float) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat)
    retval = 3.1_sidl_float
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat)
  end function returnbackfloat_impl


  ! 
  ! Method:  passinfloat[]
  ! 

  function passinfloat_impl(self, f, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat.use)
    ! Insert-Code-Here {Args.Basic.passinfloat.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinfloat.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in float f
    real (kind=sidl_float), intent(in) :: f
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat)
    retval = (f .eq. 3.1_sidl_float)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinfloat)
  end function passinfloat_impl


  ! 
  ! Method:  passoutfloat[]
  ! 

  function passoutfloat_impl(self, f, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat.use)
    ! Insert-Code-Here {Args.Basic.passoutfloat.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out float f
    real (kind=sidl_float), intent(out) :: f
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat)
    f = 3.1_sidl_float
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat)
  end function passoutfloat_impl


  ! 
  ! Method:  passinoutfloat[]
  ! 

  function passinoutfloat_impl(self, f, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat.use)
    ! Insert-Code-Here {Args.Basic.passinoutfloat.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout float f
    real (kind=sidl_float), intent(inout) :: f
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat)
    f = -f
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat)
  end function passinoutfloat_impl


  ! 
  ! Method:  passeverywherefloat[]
  ! 

  function passeverywherefloat_impl(self, f1, f2, f3, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat.use)
    ! Insert-Code-Here {Args.Basic.passeverywherefloat.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in float f1
    real (kind=sidl_float), intent(in) :: f1
    !  out float f2
    real (kind=sidl_float), intent(out) :: f2
    !  inout float f3
    real (kind=sidl_float), intent(inout) :: f3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_float) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat)
    f2 = 3.1_sidl_float
    f3 = -f3
    if (f1 .eq. 3.1_sidl_float) then
       retval = 3.1_sidl_float
    else
       retval = 0.0_sidl_float
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat)
  end function passeverywherefloat_impl


  ! 
  ! Method:  returnbackdouble[]
  ! 

  function returnbackdouble_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble.use)
    ! Insert-Code-Here {Args.Basic.returnbackdouble.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble)
    retval = 3.14_sidl_double
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble)
  end function returnbackdouble_impl


  ! 
  ! Method:  passindouble[]
  ! 

  function passindouble_impl(self, d, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passindouble.use)
    ! Insert-Code-Here {Args.Basic.passindouble.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passindouble.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in double d
    real (kind=sidl_double), intent(in) :: d
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passindouble)
    retval = (d .eq. 3.14_sidl_double)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passindouble)
  end function passindouble_impl


  ! 
  ! Method:  passoutdouble[]
  ! 

  function passoutdouble_impl(self, d, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble.use)
    ! Insert-Code-Here {Args.Basic.passoutdouble.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out double d
    real (kind=sidl_double), intent(out) :: d
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble)
    d = 3.14_sidl_double
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble)
  end function passoutdouble_impl


  ! 
  ! Method:  passinoutdouble[]
  ! 

  function passinoutdouble_impl(self, d, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble.use)
    ! Insert-Code-Here {Args.Basic.passinoutdouble.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout double d
    real (kind=sidl_double), intent(inout) :: d
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble)
    d = -d
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble)
  end function passinoutdouble_impl


  ! 
  ! Method:  passeverywheredouble[]
  ! 

  function passeverywheredouble_impl(self, d1, d2, d3, exception) result(      &
    retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble.use)
    ! Insert-Code-Here {Args.Basic.passeverywheredouble.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in double d1
    real (kind=sidl_double), intent(in) :: d1
    !  out double d2
    real (kind=sidl_double), intent(out) :: d2
    !  inout double d3
    real (kind=sidl_double), intent(inout) :: d3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble)
    d2 = 3.14_sidl_double
    d3 = -d3
    if (d1 .eq. 3.14_sidl_double) then
       retval = 3.14_sidl_double
    else
       retval = 0.0_sidl_double
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble)
  end function passeverywheredouble_impl


  ! 
  ! Method:  returnbackfcomplex[]
  ! 

  function returnbackfcomplex_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex.use)
    ! Insert-Code-Here {Args.Basic.returnbackfcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    complex (kind=sidl_fcomplex) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex)
    retval = (3.1_sidl_float, 3.1_sidl_float)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex)
  end function returnbackfcomplex_impl


  ! 
  ! Method:  passinfcomplex[]
  ! 

  function passinfcomplex_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex.use)
    ! Insert-Code-Here {Args.Basic.passinfcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in fcomplex c
    complex (kind=sidl_fcomplex), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex)
    if (real(c) .eq. 3.1_sidl_float .and. aimag(c) .eq. 3.1_sidl_float) then
       retval = .true.
    else
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex)
  end function passinfcomplex_impl


  ! 
  ! Method:  passoutfcomplex[]
  ! 

  function passoutfcomplex_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex.use)
    ! Insert-Code-Here {Args.Basic.passoutfcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out fcomplex c
    complex (kind=sidl_fcomplex), intent(out) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex)
    c = (3.1_sidl_float, 3.1_sidl_float)
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex)
  end function passoutfcomplex_impl


  ! 
  ! Method:  passinoutfcomplex[]
  ! 

  function passinoutfcomplex_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex.use)
    ! Insert-Code-Here {Args.Basic.passinoutfcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout fcomplex c
    complex (kind=sidl_fcomplex), intent(inout) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex)
    c = conjg(c)
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex)
  end function passinoutfcomplex_impl


  ! 
  ! Method:  passeverywherefcomplex[]
  ! 

  function passeverywherefcomplex_impl(self, c1, c2, c3, exception) result(    &
    retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex.use)
    ! Insert-Code-Here {Args.Basic.passeverywherefcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in fcomplex c1
    complex (kind=sidl_fcomplex), intent(in) :: c1
    !  out fcomplex c2
    complex (kind=sidl_fcomplex), intent(out) :: c2
    !  inout fcomplex c3
    complex (kind=sidl_fcomplex), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    complex (kind=sidl_fcomplex) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex)
    c2 = (3.1_sidl_float, 3.1_sidl_float)
    c3 = conjg(c3)
    if (real(c1) .eq. 3.1_sidl_float .and. aimag(c1) .eq. 3.1_sidl_float) then
       retval = c1
    else
       retval = (0.0_sidl_float, 0.0_sidl_float)
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex)
  end function passeverywherefcomplex_impl


  ! 
  ! Method:  returnbackdcomplex[]
  ! 

  function returnbackdcomplex_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex.use)
    ! Insert-Code-Here {Args.Basic.returnbackdcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    complex (kind=sidl_dcomplex) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex)
    retval = (3.14_sidl_double, 3.14_sidl_double)
    ! DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex)
  end function returnbackdcomplex_impl


  ! 
  ! Method:  passindcomplex[]
  ! 

  function passindcomplex_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex.use)
    ! Insert-Code-Here {Args.Basic.passindcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in dcomplex c
    complex (kind=sidl_dcomplex), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex)
    if (3.14_sidl_double .eq. dreal(c) .and.  3.14_sidl_double .eq. dimag(c)) then
       retval = .true.
    else
       retval = .false.
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex)
  end function passindcomplex_impl


  ! 
  ! Method:  passoutdcomplex[]
  ! 

  function passoutdcomplex_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex.use)
    ! Insert-Code-Here {Args.Basic.passoutdcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  out dcomplex c
    complex (kind=sidl_dcomplex), intent(out) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex)
    c = (3.14_sidl_double, 3.14_sidl_double)
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex)
  end function passoutdcomplex_impl


  ! 
  ! Method:  passinoutdcomplex[]
  ! 

  function passinoutdcomplex_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex.use)
    ! Insert-Code-Here {Args.Basic.passinoutdcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  inout dcomplex c
    complex (kind=sidl_dcomplex), intent(inout) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex)
    c = conjg(c)
    retval = .true.
    ! DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex)
  end function passinoutdcomplex_impl


  ! 
  ! Method:  passeverywheredcomplex[]
  ! 

  function passeverywheredcomplex_impl(self, c1, c2, c3, exception) result(    &
    retval)
    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex.use)
    ! Insert-Code-Here {Args.Basic.passeverywheredcomplex.use} (use statements)
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex.use)
    implicit none
    !  in Args.Basic self
    type(Args_Basic_impl_t) :: self
    !  in dcomplex c1
    complex (kind=sidl_dcomplex), intent(in) :: c1
    !  out dcomplex c2
    complex (kind=sidl_dcomplex), intent(out) :: c2
    !  inout dcomplex c3
    complex (kind=sidl_dcomplex), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    complex (kind=sidl_dcomplex) :: retval

    ! DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex)
    c2 = (3.14_sidl_double, 3.14_sidl_double)
    c3 = conjg(c3)
    if (3.14_sidl_double .eq. dreal(c1) .and.  3.14_sidl_double .eq. dimag(c1)) then
       retval = c2
    else
       retval = (0.0_sidl_double, 0.0_sidl_double)
    endif
    ! DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex)
  end function passeverywheredcomplex_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Args_Basic_Impl
