! 
! File:          objarg_Basic_Impl.F90
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
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine objarg_Basic__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic._ctor.use)
  ! Insert-Code-Here {objarg.Basic._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.Basic._ctor.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic._ctor)
! DO-NOT-DELETE splicer.end(objarg.Basic._ctor)
end subroutine objarg_Basic__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine objarg_Basic__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic._ctor2.use)
  ! Insert-Code-Here {objarg.Basic._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.Basic._ctor2.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  type(objarg_Basic_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic._ctor2)
! DO-NOT-DELETE splicer.end(objarg.Basic._ctor2)
end subroutine objarg_Basic__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine objarg_Basic__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic._dtor.use)
  ! Insert-Code-Here {objarg.Basic._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.Basic._dtor.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic._dtor)
! DO-NOT-DELETE splicer.end(objarg.Basic._dtor)
end subroutine objarg_Basic__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine objarg_Basic__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic._load.use)
  ! Insert-Code-Here {objarg.Basic._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(objarg.Basic._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic._load)
! DO-NOT-DELETE splicer.end(objarg.Basic._load)
end subroutine objarg_Basic__load_mi


! 
! Method:  passIn[]
! Return inNotNull == (o != NULL).
! 

recursive subroutine objarg_Basic_passIn_mi(self, o, inNotNull, retval,        &
  exception)
  use sidl
  use sidl_BaseClass
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic.passIn.use)
  ! DO-NOT-DELETE splicer.end(objarg.Basic.passIn.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  type(sidl_BaseClass_t) :: o
  ! in
  logical :: inNotNull
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic.passIn)
  call set_null(exception)
  retval = (inNotNull .and. not_null(o)) .or. &
    .not. ( inNotNull .or. not_null(o))
! DO-NOT-DELETE splicer.end(objarg.Basic.passIn)
end subroutine objarg_Basic_passIn_mi


! 
! Method:  passInOut[]
! Return inNotNull == (o != NULL).  If outNotNull, the outgoing
! value of o should not be NULL; otherwise, it will be NULL.
! If outNotNull is true, there are two cases, it retSame is true
! the incoming value of o will be returned; otherwise, a new
! object will be allocated and returned.
! 

recursive subroutine objarg_Basic_passInOut_mi(self, o, inNotNull, outNotNull, &
  retSame, retval, exception)
  use sidl
  use sidl_BaseClass
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic.passInOut.use)
  ! DO-NOT-DELETE splicer.end(objarg.Basic.passInOut.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  type(sidl_BaseClass_t) :: o
  ! inout
  logical :: inNotNull
  ! in
  logical :: outNotNull
  ! in
  logical :: retSame
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

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
end subroutine objarg_Basic_passInOut_mi


! 
! Method:  passOut[]
! If passOutNull is true, a NULL value of o will be returned; otherwise,
! a newly allocated object will be returned.
! 

recursive subroutine objarg_Basic_passOut_mi(self, o, passOutNull, exception)
  use sidl
  use sidl_BaseClass
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic.passOut.use)
  ! DO-NOT-DELETE splicer.end(objarg.Basic.passOut.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  type(sidl_BaseClass_t) :: o
  ! out
  logical :: passOutNull
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic.passOut)
  if (passOutNull) then
     call set_null(o)
  else
     call new(o, exception)
  endif
! DO-NOT-DELETE splicer.end(objarg.Basic.passOut)
end subroutine objarg_Basic_passOut_mi


! 
! Method:  retObject[]
! Return a NULL or non-NULL object depending on the value of retNull.
! 

recursive subroutine objarg_Basic_retObject_mi(self, retNull, retval,          &
  exception)
  use sidl
  use sidl_BaseClass
  use sidl_BaseInterface
  use sidl_RuntimeException
  use objarg_Basic
  use objarg_Basic_impl
  ! DO-NOT-DELETE splicer.begin(objarg.Basic.retObject.use)
  ! DO-NOT-DELETE splicer.end(objarg.Basic.retObject.use)
  implicit none
  type(objarg_Basic_t) :: self
  ! in
  logical :: retNull
  ! in
  type(sidl_BaseClass_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(objarg.Basic.retObject)
  if (retNull) then
     call set_null(retval)
  else
     call new(retval, exception)
  endif
! DO-NOT-DELETE splicer.end(objarg.Basic.retObject)
end subroutine objarg_Basic_retObject_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
