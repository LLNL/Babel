! 
! File:          sorting_Quicksort_Impl.F90
! Symbol:        sorting.Quicksort-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.Quicksort
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.Quicksort" (version 0.1)
! 
! Quick sort
! 


#include "sorting_Counter_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sorting_SortingAlgorithm_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Comparator_fAbbrev.h"
#include "sorting_Quicksort_fAbbrev.h"
#include "sorting_Container_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
#include "sorting_Mergesort_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"


logical function notEmpty(self)
  use sidl
  use sorting_Quicksort
  use sorting_Quicksort_impl
  implicit none
  type(sorting_Quicksort_t) :: self
  type(sorting_Quicksort_wrap) :: dp
  call sorting_Quicksort__get_data_m(self, dp)
  notEmpty = (dp%d_private_data%depth .gt. 0_sidl_int)
end function notEmpty

subroutine push(self, low, up)
  use sidl
  use sorting_Quicksort
  use sorting_Quicksort_impl
  use synch_RegOut
  use sidl_BaseInterface
  implicit none
  type(sorting_Quicksort_t) :: self
  type(sorting_Quicksort_wrap) :: dp
  type(sidl_BaseInterface_t) :: throwaway_exception
  integer(kind=sidl_int) low, up
  type(synch_RegOut_t) :: tracker
  call sorting_Quicksort__get_data_m(self, dp)
  if (dp%d_private_data%depth .lt. 32_sidl_int) then
     call set(dp%d_private_data%lower, dp%d_private_data%depth, low)
     call set(dp%d_private_data%upper, dp%d_private_data%depth, up)
     dp%d_private_data%depth = dp%d_private_data%depth + 1_sidl_int
  else
     call getInstance(tracker, throwaway_exception)
     call writeComment(tracker, &
          'stack overflow in Quicksort', throwaway_exception)
     call forceFailure(tracker, throwaway_exception)
     stop
  endif
end subroutine push

subroutine pop(self, low, up)
  use sidl
  use sorting_Quicksort
  use sorting_Quicksort_impl
  use synch_RegOut
  use sidl_BaseInterface
  implicit none
  type(sorting_Quicksort_t) :: self
  integer(kind=sidl_int) low, up, stackdepth
  type(sorting_Quicksort_wrap) :: dp
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  call sorting_Quicksort__get_data_m(self, dp)
  if (dp%d_private_data%depth .gt. 0_sidl_int) then
     dp%d_private_data%depth = dp%d_private_data%depth - 1_sidl_int
     stackdepth = dp%d_private_data%depth
     call get(dp%d_private_data%lower, stackdepth, low)
     call get(dp%d_private_data%upper, stackdepth, up)
  else
     call getInstance(tracker, throwaway_exception)
     call writeComment(tracker, &
          'stack underflow in Quicksort', throwaway_exception)
     call forceFailure(tracker, throwaway_exception)
     stop
  endif
end subroutine pop

!
! Choose the middle of the first, middle and last element of the
! list.  For small lists, return the middle without checking.
!
integer(kind=selected_int_kind(9)) function choosePivot(elems, comp, cmp, start, end)
  use sidl
  use sorting_Container
  use sorting_Comparator
  use sorting_Counter
  use sidl_BaseInterface
  implicit none
  type(sorting_Container_t) :: elems
  type(sorting_Comparator_t) :: comp
  type(sorting_Counter_t) cmp
  type(sidl_BaseInterface_t) :: throwaway_exception
  integer(kind=sidl_int) start, end, pivot, mid, cmpres, counter
  pivot = (start + end) / 2_sidl_int
  if ((end - start) .gt. 4_sidl_int) then
     mid = pivot
     call inc(cmp, counter, throwaway_exception)
     call compare(elems, start, mid, comp, cmpres, throwaway_exception)
     if (cmpres .le. 0_sidl_int) then
        call inc(cmp, counter, throwaway_exception)
        call compare(elems, mid, end -1_sidl_int, comp,  &
            cmpres, throwaway_exception)
        if (cmpres .gt. 0_sidl_int) then
           call inc(cmp, counter, throwaway_exception)
           call compare(elems, start, end - 1_sidl_int, comp, &
                cmpres, throwaway_exception)
           if (cmpres .lt. 0_sidl_int) then
              pivot = end - 1_sidl_int
           else
              pivot = start
           endif
        endif
     else
        call inc(cmp, counter, throwaway_exception)
        call compare(elems, mid, end - 1_sidl_int, comp, &
             cmpres, throwaway_exception)
        if (cmpres .lt. 0_sidl_int) then
           call inc(cmp, counter, throwaway_exception)
           call compare(elems, start, end - 1_sidl_int, comp, &
                cmpres, throwaway_exception)
           if (cmpres .gt. 0_sidl_int) then
              pivot = end - 1_sidl_int
           else
              pivot = start
           endif
        endif
     endif
  endif
  choosePivot = pivot
end function choosePivot

subroutine quickSort(self, elems, comp, cmp, swp)
  use sidl
  use sorting_Quicksort
  use sorting_Container
  use sorting_Comparator
  use sorting_Counter
  use sidl_BaseInterface
  implicit none
  type(sorting_Quicksort_t) :: self
  type(sorting_Container_t) :: elems
  type(sorting_Comparator_t) :: comp
  type(sorting_Counter_t) :: cmp, swp
  type(sidl_BaseInterface_t) :: throwaway_exception
  integer(kind=sidl_int) start, end, pivot, choosePivot
  integer(kind=sidl_int) i, j, cmpres, counter
  logical notEmpty
  call getLength(elems, end, throwaway_exception)
  start = 0_sidl_int
  call push(self, start, end)
  do while (notEmpty(self))
     call pop(self, start, end)
     if ((end - start) .gt. 1_sidl_int) then
        pivot = choosePivot(elems, comp, cmp, start, end)
        i = start
        j = end
        if (pivot .ne. start) then
           call inc(swp, counter, throwaway_exception)
           call swap(elems, start, pivot, throwaway_exception)
        endif
100     j = j - 1_sidl_int
        call inc(cmp, counter, throwaway_exception)
        call compare(elems, start, j, comp, cmpres, throwaway_exception)
        if (cmpres .lt. 0_sidl_int) goto 100
        i = i + 1_sidl_int
        do while (i .lt. j)
           call inc(cmp, counter, throwaway_exception)
           call compare(elems, start, i, comp, cmpres, throwaway_exception)
           if (cmpres .lt. 0_sidl_int) goto 200
           i = i + 1_sidl_int
        enddo
200     if (i .ge. j) goto 300
        call inc(swp, counter, throwaway_exception)
        call swap(elems, i, j, throwaway_exception)
        goto 100
300     if (j .ne. start) then
           call inc(swp, counter, throwaway_exception)
           call swap(elems, start, j, throwaway_exception)
        endif
        call push(self, start, j)
        call push(self, j + 1_sidl_int, end)
     endif
  enddo
end subroutine quickSort

! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine sorting_Quicksort__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Quicksort
  use sorting_Quicksort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor.use)
  implicit none
  type(sorting_Quicksort_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor)
  type(sorting_Quicksort_wrap) :: dp
  allocate(dp%d_private_data)
  call create1d(32_sidl_int, dp%d_private_data%lower)
  call create1d(32_sidl_int, dp%d_private_data%upper)
  dp%d_private_data%depth = 0_sidl_int
  call sorting_Quicksort__set_data_m(self, dp)
! DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor)
end subroutine sorting_Quicksort__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine sorting_Quicksort__ctor2_mi(self, private_data,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Quicksort
  use sorting_Quicksort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor2.use)
  ! Insert-Code-Here {sorting.Quicksort._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor2.use)
  implicit none
  type(sorting_Quicksort_t) :: self
  ! in
  type(sorting_Quicksort_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Quicksort._ctor2)
! Insert-Code-Here {sorting.Quicksort._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(sorting.Quicksort._ctor2)
end subroutine sorting_Quicksort__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine sorting_Quicksort__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Quicksort
  use sorting_Quicksort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort._dtor.use)
  implicit none
  type(sorting_Quicksort_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Quicksort._dtor)
  type(sorting_Quicksort_wrap) :: dp
  call sorting_Quicksort__get_data_m(self, dp)
  call deleteRef(dp%d_private_data%lower)
  call deleteRef(dp%d_private_data%upper)
  deallocate(dp%d_private_data)
! DO-NOT-DELETE splicer.end(sorting.Quicksort._dtor)
end subroutine sorting_Quicksort__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine sorting_Quicksort__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Quicksort
  use sorting_Quicksort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Quicksort._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(sorting.Quicksort._load)
end subroutine sorting_Quicksort__load_mi


! 
! Method:  sort[]
! Sort elements using Quick Sort.
! 

recursive subroutine sorting_Quicksort_sort_mi(self, elems, comp, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Comparator
  use sorting_Container
  use sorting_Quicksort
  use sorting_Quicksort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort.sort.use)
  use sorting_Counter
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort.sort.use)
  implicit none
  type(sorting_Quicksort_t) :: self
  ! in
  type(sorting_Container_t) :: elems
  ! in
  type(sorting_Comparator_t) :: comp
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Quicksort.sort)
  type (sorting_Counter_t) :: swp, cmp
  call getCompareCounter(self, cmp, exception)
  call getSwapCounter(self, swp, exception)
  call quickSort(self, elems, comp, cmp, swp)
  call deleteRef(cmp, exception)
  call deleteRef(swp, exception)
! DO-NOT-DELETE splicer.end(sorting.Quicksort.sort)
end subroutine sorting_Quicksort_sort_mi


! 
! Method:  getName[]
! Return quick sorting.
! 

recursive subroutine sorting_Quicksort_getName_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Quicksort
  use sorting_Quicksort_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort.getName.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort.getName.use)
  implicit none
  type(sorting_Quicksort_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Quicksort.getName)
  retval = 'Quick sort'
! DO-NOT-DELETE splicer.end(sorting.Quicksort.getName)
end subroutine sorting_Quicksort_getName_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
