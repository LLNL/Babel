! 
! File:          objarg_Basic_Impl.F03
! Symbol:        objarg.Basic-v0.5
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for objarg.Basic
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "objarg.Basic" (version 0.5)
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "objarg_Basic_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module objarg_Basic_Impl
  use sidl
  use sidl_BaseClass
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(objarg_Basic_t) :: objarg_Basic_impl_t
    ! DO-NOT-DELETE splicer.begin(objarg.Basic.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(objarg.Basic.private_data)
  end type objarg_Basic_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(objarg_Basic_impl_t), target :: obj
    type(objarg_Basic_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external objarg_Basic_wrap
    call objarg_Basic_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic._ctor.use)
    ! Insert-Code-Here {objarg.Basic._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(objarg.Basic._ctor.use)
    implicit none
    !  in objarg.Basic self
    type(objarg_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Basic._ctor)
    ! DO-NOT-DELETE splicer.end(objarg.Basic._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic._dtor.use)
    ! Insert-Code-Here {objarg.Basic._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(objarg.Basic._dtor.use)
    implicit none
    !  in objarg.Basic self
    type(objarg_Basic_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Basic._dtor)
    ! DO-NOT-DELETE splicer.end(objarg.Basic._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic._load.use)
    ! Insert-Code-Here {objarg.Basic._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(objarg.Basic._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Basic._load)
    ! DO-NOT-DELETE splicer.end(objarg.Basic._load)
  end subroutine load_impl


  ! 
  ! Method:  passIn[]
  ! Return inNotNull == (o != NULL).
  ! 

  function passIn_impl(self, o, inNotNull, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic.passIn.use)
    ! DO-NOT-DELETE splicer.end(objarg.Basic.passIn.use)
    implicit none
    !  in objarg.Basic self
    type(objarg_Basic_impl_t) :: self
    !  in sidl.BaseClass o
    type(sidl_BaseClass_t), intent(in) :: o
    !  in bool inNotNull
    logical, intent(in) :: inNotNull
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Basic.passIn)
    call set_null(exception)
    retval = (inNotNull .and. not_null(o)) .or. &
         .not. ( inNotNull .or. not_null(o))
    ! DO-NOT-DELETE splicer.end(objarg.Basic.passIn)
  end function passIn_impl


  ! 
  ! Method:  passInOut[]
  ! Return inNotNull == (o != NULL).  If outNotNull, the outgoing
  ! value of o should not be NULL; otherwise, it will be NULL.
  ! If outNotNull is true, there are two cases, it retSame is true
  ! the incoming value of o will be returned; otherwise, a new
  ! object will be allocated and returned.
  ! 

  function passInOut_impl(self, o, inNotNull, outNotNull, retSame, exception)  &
    result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic.passInOut.use)
    ! DO-NOT-DELETE splicer.end(objarg.Basic.passInOut.use)
    implicit none
    !  in objarg.Basic self
    type(objarg_Basic_impl_t) :: self
    !  inout sidl.BaseClass o
    type(sidl_BaseClass_t), intent(inout) :: o
    !  in bool inNotNull
    logical, intent(in) :: inNotNull
    !  in bool outNotNull
    logical, intent(in) :: outNotNull
    !  in bool retSame
    logical, intent(in) :: retSame
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Basic.passInOut)
    call set_null(exception)
    retval = (inNotNull .and. not_null(o)) .or. &
         .not. (inNotNull .or. not_null(o))
    if (outNotNull) then
       if ((.not. retSame) .or. is_null(o)) then
          if (not_null(o)) then
             call deleteRef(o, exception)
             call set_null(o)
          endif
          if (is_null(exception)) then
             call new(o, exception)
          endif
       endif
    else
       if (not_null(o)) then
          call deleteRef(o, exception)
          call set_null(o)
       endif
    endif
    ! DO-NOT-DELETE splicer.end(objarg.Basic.passInOut)
  end function passInOut_impl


  ! 
  ! Method:  passOut[]
  ! If passOutNull is true, a NULL value of o will be returned; otherwise,
  ! a newly allocated object will be returned.
  ! 

  subroutine passOut_impl(self, o, passOutNull, exception)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic.passOut.use)
    ! DO-NOT-DELETE splicer.end(objarg.Basic.passOut.use)
    implicit none
    !  in objarg.Basic self
    type(objarg_Basic_impl_t) :: self
    !  out sidl.BaseClass o
    type(sidl_BaseClass_t), intent(out) :: o
    !  in bool passOutNull
    logical, intent(in) :: passOutNull
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(objarg.Basic.passOut)
    if (passOutNull) then
       call set_null(o)
    else
       call new(o, exception)
    endif
    ! DO-NOT-DELETE splicer.end(objarg.Basic.passOut)
  end subroutine passOut_impl


  ! 
  ! Method:  retObject[]
  ! Return a NULL or non-NULL object depending on the value of retNull.
  ! 

  function retObject_impl(self, retNull, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(objarg.Basic.retObject.use)
    ! DO-NOT-DELETE splicer.end(objarg.Basic.retObject.use)
    implicit none
    !  in objarg.Basic self
    type(objarg_Basic_impl_t) :: self
    !  in bool retNull
    logical, intent(in) :: retNull
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(sidl_BaseClass_t) :: retval

    ! DO-NOT-DELETE splicer.begin(objarg.Basic.retObject)
    if (retNull) then
       call set_null(retval)
    else
       call new(retval, exception)
    endif
    ! DO-NOT-DELETE splicer.end(objarg.Basic.retObject)
  end function retObject_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module objarg_Basic_Impl
