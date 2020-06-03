! 
! File:          sorting_CompInt_Impl.F03
! Symbol:        sorting.CompInt-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.CompInt
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.CompInt" (version 0.1)
! 
! Compare two Integer's.  By default, this will sort in increasing order.
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Comparator_fAbbrev.h"
#include "sorting_CompInt_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module sorting_CompInt_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(sorting_CompInt_t) :: sorting_CompInt_impl_t
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt.private_data)
    ! insert code here (private data members)
    logical :: d_increasing
    ! DO-NOT-DELETE splicer.end(sorting.CompInt.private_data)
  end type sorting_CompInt_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(sorting_CompInt_impl_t), target :: obj
    type(sorting_CompInt_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external sorting_CompInt_wrap
    call sorting_CompInt_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
#include "sorting_Integer_fAbbrev.h"
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.CompInt._ctor.use)
    implicit none
    !  in sorting.CompInt self
    type(sorting_CompInt_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor)
    self%d_increasing = .true.
    ! DO-NOT-DELETE splicer.end(sorting.CompInt._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.CompInt._dtor.use)
    implicit none
    !  in sorting.CompInt self
    type(sorting_CompInt_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor)
    ! DO-NOT-DELETE splicer.end(sorting.CompInt._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.CompInt._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.CompInt._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(sorting.CompInt._load)
  end subroutine load_impl


  ! 
  ! Method:  setSortIncreasing[]
  ! If increasing is true, this will cause the comparator to
  ! report a normal definition of less than; otherwise, it will
  ! reverse the normal ordering.
  ! 

  subroutine setSortIncreasing_impl(self, increasing, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing.use)
    implicit none
    !  in sorting.CompInt self
    type(sorting_CompInt_impl_t) :: self
    !  in bool increasing
    logical, intent(in) :: increasing
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing)
    self%d_increasing = increasing
    ! DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing)
  end subroutine setSortIncreasing_impl


  ! 
  ! Method:  compare[]
  ! This method is used to define an ordering of objects.  This method
  ! will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
  ! 

  function compare_impl(self, i1, i2, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt.compare.use)
    use sorting_Integer
    use sidl_BaseInterface
    ! DO-NOT-DELETE splicer.end(sorting.CompInt.compare.use)
    implicit none
    !  in sorting.CompInt self
    type(sorting_CompInt_impl_t) :: self
    !  in sidl.BaseInterface i1
    type(sidl_BaseInterface_t), intent(in) :: i1
    !  in sidl.BaseInterface i2
    type(sidl_BaseInterface_t), intent(in) :: i2
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(sorting.CompInt.compare)
    type(sorting_Integer_t) int1, int2
    type(sidl_BaseInterface_t) :: throwaway_exception
    integer(kind=sidl_int) val1, val2
    logical increasing
    retval = 0
    increasing = self%d_increasing
    call cast(i1, int1, throwaway_exception)
    call cast(i2, int2, throwaway_exception)
    if (not_null(int1) .and. not_null(int2)) then
       val1 = getValue(int1, throwaway_exception)
       val2 = getValue(int2, throwaway_exception)
       if (val1 .lt. val2)  then
          retval = -1
       endif
       if (val1 .gt. val2) then
          retval = 1
       endif
       if (.not. increasing) then
          retval = -retval
       endif
    endif
    if (not_null(int1)) then
       call deleteRef(int1, throwaway_exception)
    endif
    if (not_null(int2)) then
       call deleteRef(int2, throwaway_exception)
    endif
    ! DO-NOT-DELETE splicer.end(sorting.CompInt.compare)
  end function compare_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module sorting_CompInt_Impl
