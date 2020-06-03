! 
! File:          sorting_Mergesort_Impl.F90
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
     call inc(cmp, counter, throwaway_exception)
     call compare(elems, s, m, comp, compres, throwaway_exception)
     if (compres .gt. 0_sidl_int) then
        do j = m, s + 1_sidl_int, -1_sidl_int
           call inc(swp, counter, throwaway_exception)
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

recursive subroutine sorting_Mergesort__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Mergesort
  use sorting_Mergesort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor.use)
  implicit none
  type(sorting_Mergesort_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor)
! DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor)
end subroutine sorting_Mergesort__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine sorting_Mergesort__ctor2_mi(self, private_data,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Mergesort
  use sorting_Mergesort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor2.use)
  ! Insert-Code-Here {sorting.Mergesort._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor2.use)
  implicit none
  type(sorting_Mergesort_t) :: self
  ! in
  type(sorting_Mergesort_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor2)
! Insert-Code-Here {sorting.Mergesort._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor2)
end subroutine sorting_Mergesort__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine sorting_Mergesort__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Mergesort
  use sorting_Mergesort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor.use)
  implicit none
  type(sorting_Mergesort_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor)
! DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor)
end subroutine sorting_Mergesort__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine sorting_Mergesort__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Mergesort
  use sorting_Mergesort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Mergesort._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(sorting.Mergesort._load)
end subroutine sorting_Mergesort__load_mi


! 
! Method:  sort[]
! Sort elements using Merge Sort.
! 

recursive subroutine sorting_Mergesort_sort_mi(self, elems, comp, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Comparator
  use sorting_Container
  use sorting_Mergesort
  use sorting_Mergesort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort.use)
  use sorting_Counter
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort.sort.use)
  implicit none
  type(sorting_Mergesort_t) :: self
  ! in
  type(sorting_Container_t) :: elems
  ! in
  type(sorting_Comparator_t) :: comp
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort)
  type(sorting_Counter_t) :: cmp, swp
  integer(kind=sidl_int) stride, i, mid, last, len
  mid = 1_sidl_int
  stride = 2_sidl_int
  call getLength(elems, len, exception)
  call getCompareCounter(self, cmp, exception)
  call getSwapCounter(self, swp, exception)
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
end subroutine sorting_Mergesort_sort_mi


! 
! Method:  getName[]
! Return merge sorting.
! 

recursive subroutine sorting_Mergesort_getName_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Mergesort
  use sorting_Mergesort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort.getName.use)
  implicit none
  type(sorting_Mergesort_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName)
  retval = 'Merge sort (non-recursive)'
! DO-NOT-DELETE splicer.end(sorting.Mergesort.getName)
end subroutine sorting_Mergesort_getName_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
