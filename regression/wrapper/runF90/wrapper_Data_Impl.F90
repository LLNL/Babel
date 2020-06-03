! 
! File:          wrapper_Data_Impl.F90
! Symbol:        wrapper.Data-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for wrapper.Data
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "wrapper.Data" (version 1.0)
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "wrapper_Data_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine wrapper_Data__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_Data_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.Data._ctor.use)
  ! Insert-Code-Here {wrapper.Data._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.Data._ctor.use)
  implicit none
  type(wrapper_Data_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.Data._ctor)
! Insert-Code-Here {wrapper.Data._ctor} (_ctor method)
! 
! ! boilerplate contructor
! type(wrapper_Data_wrap) :: dp
! allocate(dp%d_private_data)
! ! initialize contents of dp%d_private_data here
! call wrapper_Data__set_data_m(self, dp)
! 

! DO-NOT-DELETE splicer.end(wrapper.Data._ctor)
end subroutine wrapper_Data__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine wrapper_Data__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_Data_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.Data._ctor2.use)
  ! Insert-Code-Here {wrapper.Data._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.Data._ctor2.use)
  implicit none
  type(wrapper_Data_t) :: self
  ! in
  type(wrapper_Data_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.Data._ctor2)
  private_data%d_private_data%d_ctortest = 'ctor was run'

! DO-NOT-DELETE splicer.end(wrapper.Data._ctor2)
end subroutine wrapper_Data__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine wrapper_Data__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_Data_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.Data._dtor.use)
  ! Insert-Code-Here {wrapper.Data._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.Data._dtor.use)
  implicit none
  type(wrapper_Data_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.Data._dtor)
! Insert-Code-Here {wrapper.Data._dtor} (_dtor method)
! 
! ! boilerplate contructor
  type(wrapper_Data_wrap) :: dp
  call wrapper_Data__get_data_m(self, dp)
  deallocate(dp%d_private_data)

  
! DO-NOT-DELETE splicer.end(wrapper.Data._dtor)
end subroutine wrapper_Data__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine wrapper_Data__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_Data_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.Data._load.use)
  ! Insert-Code-Here {wrapper.Data._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.Data._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.Data._load)
! Insert-Code-Here {wrapper.Data._load} (_load method)
! DO-NOT-DELETE splicer.end(wrapper.Data._load)
end subroutine wrapper_Data__load_mi


! 
! Method:  setString[]
! 

recursive subroutine wrapper_Data_setString_mi(self, s, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_Data_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.Data.setString.use)
  ! Insert-Code-Here {wrapper.Data.setString.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.Data.setString.use)
  implicit none
  type(wrapper_Data_t) :: self
  ! in
  character (len=*) :: s
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.Data.setString)
  type(wrapper_Data_wrap) :: dp
  call wrapper_Data__get_data_m(self, dp)
  dp%d_private_data%d_string = s
! DO-NOT-DELETE splicer.end(wrapper.Data.setString)
end subroutine wrapper_Data_setString_mi


! 
! Method:  setInt[]
! 

recursive subroutine wrapper_Data_setInt_mi(self, i, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_Data_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.Data.setInt.use)
  ! Insert-Code-Here {wrapper.Data.setInt.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.Data.setInt.use)
  implicit none
  type(wrapper_Data_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.Data.setInt)
  type(wrapper_Data_wrap) :: dp
  call wrapper_Data__get_data_m(self, dp)
  dp%d_private_data%d_int = i

! DO-NOT-DELETE splicer.end(wrapper.Data.setInt)
end subroutine wrapper_Data_setInt_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
