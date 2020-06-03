! 
! File:          enums_cartest_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module enums_cartest_Impl
  use sidl
  use enums_car
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_cartest
  use enums_car_array
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(enums_cartest_t) :: enums_cartest_impl_t
    ! DO-NOT-DELETE splicer.begin(enums.cartest.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(enums.cartest.private_data)
  end type enums_cartest_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(enums_cartest_impl_t), target :: obj
    type(enums_cartest_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external enums_cartest_wrap
    call enums_cartest_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(enums.cartest._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest._ctor.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.cartest._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.cartest._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(enums.cartest._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest._dtor.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.cartest._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.cartest._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(enums.cartest._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.cartest._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.cartest._load)
  end subroutine load_impl


  ! 
  ! Method:  returnback[]
  ! 

  function returnback_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.cartest.returnback.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest.returnback.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_enum) :: retval

    ! DO-NOT-DELETE splicer.begin(enums.cartest.returnback)
    retval = porsche
    ! DO-NOT-DELETE splicer.end(enums.cartest.returnback)
  end function returnback_impl


  ! 
  ! Method:  passin[]
  ! 

  function passin_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.cartest.passin.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest.passin.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  in enums.car c
    integer (kind=sidl_enum), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.cartest.passin)
    retval = (c .eq. mercedes)
    ! DO-NOT-DELETE splicer.end(enums.cartest.passin)
  end function passin_impl


  ! 
  ! Method:  passout[]
  ! 

  function passout_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.cartest.passout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest.passout.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  out enums.car c
    integer (kind=sidl_enum), intent(out) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.cartest.passout)
    c = ford
    retval = .true.
    ! DO-NOT-DELETE splicer.end(enums.cartest.passout)
  end function passout_impl


  ! 
  ! Method:  passinout[]
  ! 

  function passinout_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.cartest.passinout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest.passinout.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  inout enums.car c
    integer (kind=sidl_enum), intent(inout) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

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
  end function passinout_impl


  ! 
  ! Method:  passeverywhere[]
  ! 

  function passeverywhere_impl(self, c1, c2, c3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  in enums.car c1
    integer (kind=sidl_enum), intent(in) :: c1
    !  out enums.car c2
    integer (kind=sidl_enum), intent(out) :: c2
    !  inout enums.car c3
    integer (kind=sidl_enum), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_enum) :: retval

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
  end function passeverywhere_impl


  ! 
  ! Method:  passarray[]
  ! All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
  ! in that order.
  ! 

  function passarray_impl(self, c1, c2, c3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.cartest.passarray.use)
    ! DO-NOT-DELETE splicer.end(enums.cartest.passarray.use)
    implicit none
    !  in enums.cartest self
    type(enums_cartest_impl_t) :: self
    !  in array<enums.car> c1
    type(enums_car_1d), intent(in) :: c1
    !  out array<enums.car> c2
    type(enums_car_1d), intent(out) :: c2
    !  inout array<enums.car> c3
    type(enums_car_1d), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(enums_car_1d) :: retval

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
  end function passarray_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module enums_cartest_Impl
