! 
! File:          sorting_Integer_Impl.F90
! Symbol:        sorting.Integer-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.Integer
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.Integer" (version 0.1)
! 
! An object to hold a simple integer.
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sorting_Integer_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine sorting_Integer__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  use sorting_Integer_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Integer._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Integer._ctor.use)
  implicit none
  type(sorting_Integer_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Integer._ctor)
  type(sorting_Integer_wrap) :: dp
  allocate(dp%d_private_data)
  dp%d_private_data%d_value = 0
  call sorting_Integer__set_data_m(self, dp)
! DO-NOT-DELETE splicer.end(sorting.Integer._ctor)
end subroutine sorting_Integer__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine sorting_Integer__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  use sorting_Integer_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Integer._ctor2.use)
  ! Insert-Code-Here {sorting.Integer._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(sorting.Integer._ctor2.use)
  implicit none
  type(sorting_Integer_t) :: self
  ! in
  type(sorting_Integer_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Integer._ctor2)
! Insert-Code-Here {sorting.Integer._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(sorting.Integer._ctor2)
end subroutine sorting_Integer__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine sorting_Integer__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  use sorting_Integer_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Integer._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Integer._dtor.use)
  implicit none
  type(sorting_Integer_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Integer._dtor)
  type(sorting_Integer_wrap) :: dp
  call sorting_Integer__get_data_m(self, dp)
  deallocate(dp%d_private_data)
! DO-NOT-DELETE splicer.end(sorting.Integer._dtor)
end subroutine sorting_Integer__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine sorting_Integer__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  use sorting_Integer_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Integer._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Integer._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Integer._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(sorting.Integer._load)
end subroutine sorting_Integer__load_mi


! 
! Method:  getValue[]
! 

recursive subroutine sorting_Integer_getValue_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  use sorting_Integer_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Integer.getValue.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Integer.getValue.use)
  implicit none
  type(sorting_Integer_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Integer.getValue)
  type(sorting_Integer_wrap) :: dp
  call sorting_Integer__get_data_m(self, dp)
  retval = dp%d_private_data%d_value
! DO-NOT-DELETE splicer.end(sorting.Integer.getValue)
end subroutine sorting_Integer_getValue_mi


! 
! Method:  setValue[]
! 

recursive subroutine sorting_Integer_setValue_mi(self, value, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  use sorting_Integer_impl
  ! DO-NOT-DELETE splicer.begin(sorting.Integer.setValue.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Integer.setValue.use)
  implicit none
  type(sorting_Integer_t) :: self
  ! in
  integer (kind=sidl_int) :: value
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(sorting.Integer.setValue)
  type(sorting_Integer_wrap) :: dp
  call sorting_Integer__get_data_m(self, dp)
  dp%d_private_data%d_value = value
! DO-NOT-DELETE splicer.end(sorting.Integer.setValue)
end subroutine sorting_Integer_setValue_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
