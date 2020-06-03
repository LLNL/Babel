! 
! File:          sorting_SimpleCounter_Impl.F90
! Symbol:        sorting.SimpleCounter-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.SimpleCounter
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.SimpleCounter" (version 0.1)
! 
! Simple counter
! 


#include "sorting_Counter_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sorting_SimpleCounter_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine sorting_SimpleCounter__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor.use)
  implicit none
  type(sorting_SimpleCounter_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor)
  type(sorting_SimpleCounter_wrap) :: dp
  allocate(dp%d_private_data)
  dp%d_private_data%count = 0
  call sorting_SimpleCounter__set_data_m(self, dp)
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor)
end subroutine sorting_SimpleCounter__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine sorting_SimpleCounter__ctor2_mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor2.use)
  ! Insert-Code-Here {sorting.SimpleCounter._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor2.use)
  implicit none
  type(sorting_SimpleCounter_t) :: self
  ! in
  type(sorting_SimpleCounter_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor2)
! Insert-Code-Here {sorting.SimpleCounter._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor2)
end subroutine sorting_SimpleCounter__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine sorting_SimpleCounter__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._dtor.use)
  implicit none
  type(sorting_SimpleCounter_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._dtor)
  type(sorting_SimpleCounter_wrap) :: dp
  call sorting_SimpleCounter__get_data_m(self, dp)
  deallocate(dp%d_private_data)
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._dtor)
end subroutine sorting_SimpleCounter__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine sorting_SimpleCounter__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._load)
end subroutine sorting_SimpleCounter__load_mi


! 
! Method:  reset[]
! Set the count to zero.
! 

recursive subroutine sorting_SimpleCounter_reset_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.reset.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.reset.use)
  implicit none
  type(sorting_SimpleCounter_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.reset)
  type(sorting_SimpleCounter_wrap) :: dp
  call sorting_SimpleCounter__get_data_m(self, dp)
  dp%d_private_data%count = 0
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.reset)
end subroutine sorting_SimpleCounter_reset_mi


! 
! Method:  getCount[]
! Return the current count.
! 

recursive subroutine SimpleCou_getCount0_llemn2xb_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.getCount.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.getCount.use)
  implicit none
  type(sorting_SimpleCounter_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.getCount)
  type(sorting_SimpleCounter_wrap) :: dp
  call sorting_SimpleCounter__get_data_m(self, dp)
  retval = dp%d_private_data%count
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.getCount)
end subroutine SimpleCou_getCount0_llemn2xb_mi


! 
! Method:  inc[]
! Increment the count (i.e. add one).
! 

recursive subroutine sorting_SimpleCounter_inc_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  use sorting_SimpleCounter_impl
  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.inc.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.inc.use)
  implicit none
  type(sorting_SimpleCounter_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.inc)
  type(sorting_SimpleCounter_wrap) :: dp
  call sorting_SimpleCounter__get_data_m(self, dp)
  dp%d_private_data%count = dp%d_private_data%count + 1
  retval = dp%d_private_data%count
! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.inc)
end subroutine sorting_SimpleCounter_inc_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
