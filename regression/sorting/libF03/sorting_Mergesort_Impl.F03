! 
! File:          sorting_Mergesort_Impl.F03
! Symbol:        sorting.Mergesort-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.Mergesort
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.Mergesort" (version 0.1)
! 
! Merge sort
! 


#include "sorting_Mergesort_fAbbrev.h"
#include "sorting_Counter_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sorting_SortingAlgorithm_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Comparator_fAbbrev.h"
#include "sorting_Container_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module sorting_Mergesort_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Comparator
  use sorting_Container
  use sorting_Mergesort
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(sorting_Mergesort_t) :: sorting_Mergesort_impl_t
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort.private_data)
  end type sorting_Mergesort_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(sorting_Mergesort_impl_t), target :: obj
    type(sorting_Mergesort_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external sorting_Mergesort_wrap
    call sorting_Mergesort_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! This file contains an interative (non-recursive) version of mergesort.
  
  subroutine mergeLists(elems, comp, cmp, swp, start, mid, last)
    use sidl
    use sorting_Container
    use sorting_Comparator
    use sorting_Counter
    use sidl_BaseInterface
    type(sorting_Container_t) :: elems
    type(sorting_Comparator_t) :: comp
    type(sorting_Counter_t) :: cmp, swp
    type(sidl_BaseInterface_t) :: throwaway_exception
    integer(kind=sidl_int) start, mid, last, j
    integer(kind=sidl_int) compres, counter
    integer(kind=sidl_int) m, s
    m = mid
    s = start
    do while ((s .lt. m) .and. (m .lt. last))
       counter = inc(cmp, throwaway_exception)
       compres = compare(elems, s, m, comp, throwaway_exception)
       if (compres .gt. 0_sidl_int) then
          do j = m, s + 1_sidl_int, -1_sidl_int
             counter = inc(swp, throwaway_exception)
             call swap(elems, j, j - 1_sidl_int, throwaway_exception)
          enddo
          m = m + 1_sidl_int
       endif
       s = s + 1_sidl_int
    enddo
  end subroutine mergeLists
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor.use)
    implicit none
    !  in sorting.Mergesort self
    type(sorting_Mergesort_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor)
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor.use)
    implicit none
    !  in sorting.Mergesort self
    type(sorting_Mergesort_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor)
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort._load)
  end subroutine load_impl


  ! 
  ! Method:  sort[]
  ! Sort elements using Merge Sort.
  ! 

  subroutine sort_impl(self, elems, comp, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort.use)
    use sorting_Counter
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort.sort.use)
    implicit none
    !  in sorting.Mergesort self
    type(sorting_Mergesort_impl_t) :: self
    !  in sorting.Container elems
    type(sorting_Container_t), intent(in) :: elems
    !  in sorting.Comparator comp
    type(sorting_Comparator_t), intent(in) :: comp
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort)
    type(sorting_Counter_t) :: cmp, swp
    integer(kind=sidl_int) stride, i, mid, last, len
    mid = 1_sidl_int
    stride = 2_sidl_int
    len = getLength(elems, exception)
    cmp = getCompareCounter(self, exception)
    swp = getSwapCounter(self, exception)
    do while (mid .lt. len)
       do i = 0_sidl_int, len - mid, stride
          last = i + stride
          if (last .gt. len) then
             last = len
          endif
          call mergeLists(elems, comp, cmp, swp, i, i + mid, last)
       enddo
       mid = stride
       stride = stride + stride
    enddo
    call deleteRef(cmp, exception)
    call deleteRef(swp, exception)
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort.sort)
  end subroutine sort_impl


  ! 
  ! Method:  getName[]
  ! Return merge sorting.
  ! 

  function getName_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort.getName.use)
    implicit none
    !  in sorting.Mergesort self
    type(sorting_Mergesort_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName)
    retval = 'Merge sort (non-recursive)'
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort.getName)
  end function getName_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module sorting_Mergesort_Impl
