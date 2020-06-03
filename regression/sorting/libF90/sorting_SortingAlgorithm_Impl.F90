! 
! File:          sorting_SortingAlgorithm_Impl.F90
! Symbol:        sorting.SortingAlgorithm-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.SortingAlgorithm
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.SortingAlgorithm" (version 0.1)
! 
! An abstract sorting algorithm.
! 


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
#include "sorting_SimpleCounter_fAbbrev.h"
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine SortingAlgor__ctory6bw2agi33_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor.use)
  use sorting_Counter
  use sorting_SimpleCounter
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor.use)
  implicit none
  type(sorting_SortingAlgorithm_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor)
  type(sorting_Counter_t) :: count
  type(sorting_SimpleCounter_t) :: scount
  type(sorting_SortingAlgorithm_wrap) :: dp
  allocate(dp%d_private_data)
  call new(scount, exception)
  call cast(scount, dp%d_private_data%d_compare, exception)
  call deleteRef(scount, exception)
  call new(scount, exception)
  call cast(scount, dp%d_private_data%d_swap, exception)
  call deleteRef(scount, exception)
  call sorting_SortingAlgorithm__set_data_m(self, dp)
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor)
end subroutine SortingAlgor__ctory6bw2agi33_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine SortingAlgo__ctor2bi5ww7jdt__mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor2.use)
  ! Insert-Code-Here {sorting.SortingAlgorithm._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor2.use)
  implicit none
  type(sorting_SortingAlgorithm_t) :: self
  ! in
  type(sorting_SortingAlgorithm_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor2)
! Insert-Code-Here {sorting.SortingAlgorithm._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor2)
end subroutine SortingAlgo__ctor2bi5ww7jdt__mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine SortingAlgor__dtora7sfquy71i_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._dtor.use)
  use sorting_Counter
  use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._dtor.use)
  implicit none
  type(sorting_SortingAlgorithm_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._dtor)
  type(sorting_SortingAlgorithm_wrap) :: dp
  type(sidl_BaseInterface_t) :: throwaway_exception
  call sorting_SortingAlgorithm__get_data_m(self, dp)
  call deleteRef(dp%d_private_data%d_swap, throwaway_exception)
  call deleteRef(dp%d_private_data%d_compare, throwaway_exception)
  deallocate(dp%d_private_data)
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._dtor)
end subroutine SortingAlgor__dtora7sfquy71i_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine SortingAlgor__loaddof170c1_4_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._load)
end subroutine SortingAlgor__loaddof170c1_4_mi


! 
! Method:  getCompareCounter[]
! Return the comparison counter.
! 

recursive subroutine getCompareCounter1olep06876k_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Counter
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getCompareCounter.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getCompareCounter.use)
  implicit none
  type(sorting_SortingAlgorithm_t) :: self
  ! in
  type(sorting_Counter_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getCompareCounter)
  type(sorting_SortingAlgorithm_wrap) :: dp
  call sorting_SortingAlgorithm__get_data_m(self, dp)
  retval = dp%d_private_data%d_compare
  call addRef(retval, exception)
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getCompareCounter)
end subroutine getCompareCounter1olep06876k_mi


! 
! Method:  getSwapCounter[]
! Return the swap counter.
! 

recursive subroutine Sor_getSwapCounterl3f6odh_t0_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Counter
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getSwapCounter.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getSwapCounter.use)
  implicit none
  type(sorting_SortingAlgorithm_t) :: self
  ! in
  type(sorting_Counter_t) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getSwapCounter)
  type(sorting_SortingAlgorithm_wrap) :: dp
  call sorting_SortingAlgorithm__get_data_m(self, dp)
  retval = dp%d_private_data%d_swap
  call addRef(retval, exception)
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getSwapCounter)
end subroutine Sor_getSwapCounterl3f6odh_t0_mi


! 
! Method:  reset[]
! Reset the comparison and swap counter.
! 

recursive subroutine SortingAlgor_resetz0ewoa4oia_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SortingAlgorithm
  use sorting_SortingAlgorithm_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.reset.use)
  use sorting_Counter
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.reset.use)
  implicit none
  type(sorting_SortingAlgorithm_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.reset)
  type(sorting_SortingAlgorithm_wrap) :: dp
  call sorting_SortingAlgorithm__get_data_m(self, dp)
  call reset(dp%d_private_data%d_compare, exception)
  call reset(dp%d_private_data%d_swap, exception)
! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.reset)
end subroutine SortingAlgor_resetz0ewoa4oia_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
