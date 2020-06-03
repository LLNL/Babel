! 
! File:          enums_cartest_Impl.F90
! Symbol:        enums.cartest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for enums.cartest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "enums.cartest" (version 1.0)
! 


#include "enums_car_fAbbrev.h"
#include "enums_cartest_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine enums_cartest__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest._ctor.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.cartest._ctor)
end subroutine enums_cartest__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine enums_cartest__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest._ctor2.use)
  ! Insert-Code-Here {enums.cartest._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(enums.cartest._ctor2.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  type(enums_cartest_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest._ctor2)
! Insert-Code-Here {enums.cartest._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(enums.cartest._ctor2)
end subroutine enums_cartest__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine enums_cartest__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest._dtor.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.cartest._dtor)
end subroutine enums_cartest__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine enums_cartest__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.cartest._load)
end subroutine enums_cartest__load_mi


! 
! Method:  returnback[]
! 

recursive subroutine enums_cartest_returnback_mi(self, retval, exception)
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest.returnback.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest.returnback.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  integer (kind=sidl_enum) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest.returnback)
  retval = porsche
! DO-NOT-DELETE splicer.end(enums.cartest.returnback)
end subroutine enums_cartest_returnback_mi


! 
! Method:  passin[]
! 

recursive subroutine enums_cartest_passin_mi(self, c, retval, exception)
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest.passin.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest.passin.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  integer (kind=sidl_enum) :: c
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest.passin)
  retval = (c .eq. mercedes)
! DO-NOT-DELETE splicer.end(enums.cartest.passin)
end subroutine enums_cartest_passin_mi


! 
! Method:  passout[]
! 

recursive subroutine enums_cartest_passout_mi(self, c, retval, exception)
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest.passout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest.passout.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  integer (kind=sidl_enum) :: c
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest.passout)
  c = ford
  retval = .true.
! DO-NOT-DELETE splicer.end(enums.cartest.passout)
end subroutine enums_cartest_passout_mi


! 
! Method:  passinout[]
! 

recursive subroutine enums_cartest_passinout_mi(self, c, retval, exception)
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest.passinout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest.passinout.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  integer (kind=sidl_enum) :: c
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest.passinout)
  if (c .eq. ford) then
     c = porsche
     retval = .true.
  else
     if (c .eq. porsche) then
        c = mercedes
        retval = .true.
     else
        if (c .eq. mercedes) then
           retval = .true.
        else
           retval = .false.
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(enums.cartest.passinout)
end subroutine enums_cartest_passinout_mi


! 
! Method:  passeverywhere[]
! 

recursive subroutine enums_cartest_passeverywhere_mi(self, c1, c2, c3, retval, &
  exception)
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  integer (kind=sidl_enum) :: c1
  ! in
  integer (kind=sidl_enum) :: c2
  ! out
  integer (kind=sidl_enum) :: c3
  ! inout
  integer (kind=sidl_enum) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere)
  c2 = ford
  if (c3 .eq. ford) then
     c3 = porsche
  else 
     if (c3 .eq. porsche) then
        c3 = mercedes
     else 
        if (c3 .ne. mercedes) then
           retval = mercedes
           return
        endif
     endif
  endif
  if (c1 .eq. mercedes) then
     retval = porsche
  else
     retval = mercedes
  endif
! DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere)
end subroutine enums_cartest_passeverywhere_mi


! 
! Method:  passarray[]
! All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
! in that order.
! 

recursive subroutine enums_cartest_passarray_mi(self, c1, c2, c3, retval,      &
  exception)
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_car_array
  use enums_cartest_impl
  ! DO-NOT-DELETE splicer.begin(enums.cartest.passarray.use)
  ! DO-NOT-DELETE splicer.end(enums.cartest.passarray.use)
  implicit none
  type(enums_cartest_t) :: self
  ! in
  type(enums_car_1d) :: c1
  ! in
  type(enums_car_1d) :: c2
  ! out
  type(enums_car_1d) :: c3
  ! inout
  type(enums_car_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.cartest.passarray)
  logical failed
  integer(kind=sidl_int) :: len, i, dim
  integer(kind=sidl_enum) :: value
  integer(kind=sidl_enum), dimension(3) :: vals = &
       (/ford, mercedes, porsche/)
  failed = .false.
  call set_null(exception)
  call set_null(retval)
  call set_null(c2)
  len = 3
  dim = 0
  if (not_null(c1) .and. not_null(c3) .and. (length(c1, dim) .eq. 3) &
       .and. (length(c3, dim) .eq. 3)) then
     call create1d(len, c2)
     call create1d(len, retval)
     do i = 0, 2
        call set(c2, i, vals(i+1))
        call set(retval, i, vals(i+1))
        call get(c1, lower(c1, dim)+i, value)
        failed = failed .or. (value .ne. vals(i+1))
        call get(c3, lower(c3, dim)+i, value)
        failed = failed .or. (value .ne. vals(i+1))
     end do
     if (failed) then
        call deleteRef(c2)
        call set_null(c2)
        call deleteRef(retval)
        call set_null(retval)
     endif
  endif
! DO-NOT-DELETE splicer.end(enums.cartest.passarray)
end subroutine enums_cartest_passarray_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
