! 
! File:          sorting_CompInt_Impl.F90
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
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
#include "sorting_Integer_fAbbrev.h"
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine sorting_CompInt__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  use sorting_CompInt_impl
  ! DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.CompInt._ctor.use)
  implicit none
  type(sorting_CompInt_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor)
  type(sorting_CompInt_wrap) :: privatedata
  allocate(privatedata%d_private_data)
  privatedata%d_private_data%d_increasing = .true.
  call sorting_CompInt__set_data_m(self, privatedata)
! DO-NOT-DELETE splicer.end(sorting.CompInt._ctor)
end subroutine sorting_CompInt__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine sorting_CompInt__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  use sorting_CompInt_impl
  ! DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor2.use)
  ! Insert-Code-Here {sorting.CompInt._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(sorting.CompInt._ctor2.use)
  implicit none
  type(sorting_CompInt_t) :: self
  ! in
  type(sorting_CompInt_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.CompInt._ctor2)
! Insert-Code-Here {sorting.CompInt._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(sorting.CompInt._ctor2)
end subroutine sorting_CompInt__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine sorting_CompInt__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  use sorting_CompInt_impl
  ! DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.CompInt._dtor.use)
  implicit none
  type(sorting_CompInt_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.CompInt._dtor)
  type(sorting_CompInt_wrap) :: privatedata
  call sorting_CompInt__get_data_m(self, privatedata)
  deallocate(privatedata%d_private_data)
! DO-NOT-DELETE splicer.end(sorting.CompInt._dtor)
end subroutine sorting_CompInt__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine sorting_CompInt__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  use sorting_CompInt_impl
  ! DO-NOT-DELETE splicer.begin(sorting.CompInt._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.CompInt._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.CompInt._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(sorting.CompInt._load)
end subroutine sorting_CompInt__load_mi


! 
! Method:  setSortIncreasing[]
! If increasing is true, this will cause the comparator to
! report a normal definition of less than; otherwise, it will
! reverse the normal ordering.
! 

recursive subroutine setSortIncreasingu9fj8n0p555_mi(self, increasing,         &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  use sorting_CompInt_impl
  ! DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing.use)
  implicit none
  type(sorting_CompInt_t) :: self
  ! in
  logical :: increasing
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.CompInt.setSortIncreasing)
  type(sorting_CompInt_wrap) :: privatedata
  call sorting_CompInt__get_data_m(self, privatedata)
  privatedata%d_private_data%d_increasing = increasing
! DO-NOT-DELETE splicer.end(sorting.CompInt.setSortIncreasing)
end subroutine setSortIncreasingu9fj8n0p555_mi


! 
! Method:  compare[]
! This method is used to define an ordering of objects.  This method
! will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
! 

recursive subroutine sorting_CompInt_compare_mi(self, i1, i2, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_CompInt
  use sorting_CompInt_impl
  ! DO-NOT-DELETE splicer.begin(sorting.CompInt.compare.use)
  use sorting_Integer
  use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(sorting.CompInt.compare.use)
  implicit none
  type(sorting_CompInt_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: i1
  ! in
  type(sidl_BaseInterface_t) :: i2
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.CompInt.compare)
  type(sorting_Integer_t) int1, int2
  type(sorting_CompInt_wrap) :: privatedata
  type(sidl_BaseInterface_t) :: throwaway_exception
  integer(kind=sidl_int) val1, val2
  logical increasing
  retval = 0
  call sorting_CompInt__get_data_m(self, privatedata)
  increasing = privatedata%d_private_data%d_increasing
  call cast(i1, int1, throwaway_exception)
  call cast(i2, int2, throwaway_exception)
  if (not_null(int1) .and. not_null(int2)) then
     call getValue(int1, val1, throwaway_exception)
     call getValue(int2, val2, throwaway_exception)
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
end subroutine sorting_CompInt_compare_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
