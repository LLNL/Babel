! 
! File:          wrapper_User_Impl.F90
! Symbol:        wrapper.User-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for wrapper.User
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "wrapper.User" (version 1.0)
! 


#include "wrapper_User_fAbbrev.h"
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

recursive subroutine wrapper_User__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_User
  use wrapper_User_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.User._ctor.use)
  ! Insert-Code-Here {wrapper.User._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.User._ctor.use)
  implicit none
  type(wrapper_User_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.User._ctor)
! Insert-Code-Here {wrapper.User._ctor} (_ctor method)
! 
! ! boilerplate contructor
! type(wrapper_User_wrap) :: dp
! allocate(dp%d_private_data)
! ! initialize contents of dp%d_private_data here
! call wrapper_User__set_data_m(self, dp)
! 

! DO-NOT-DELETE splicer.end(wrapper.User._ctor)
end subroutine wrapper_User__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine wrapper_User__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_User
  use wrapper_User_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.User._ctor2.use)
  ! Insert-Code-Here {wrapper.User._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.User._ctor2.use)
  implicit none
  type(wrapper_User_t) :: self
  ! in
  type(wrapper_User_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.User._ctor2)
! Insert-Code-Here {wrapper.User._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(wrapper.User._ctor2)
end subroutine wrapper_User__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine wrapper_User__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_User
  use wrapper_User_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.User._dtor.use)
  ! Insert-Code-Here {wrapper.User._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.User._dtor.use)
  implicit none
  type(wrapper_User_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.User._dtor)
! Insert-Code-Here {wrapper.User._dtor} (_dtor method)
! 
! ! boilerplate contructor
! type(wrapper_User_wrap) :: dp
! call wrapper_User__get_data_m(self, dp)
! ! release resources in dp%d_private_data here
! deallocate(dp%d_private_data)
! 

! DO-NOT-DELETE splicer.end(wrapper.User._dtor)
end subroutine wrapper_User__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine wrapper_User__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_User
  use wrapper_User_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.User._load.use)
  ! Insert-Code-Here {wrapper.User._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.User._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.User._load)
! Insert-Code-Here {wrapper.User._load} (_load method)
! DO-NOT-DELETE splicer.end(wrapper.User._load)
end subroutine wrapper_User__load_mi


! 
! Method:  accept[]
! 

recursive subroutine wrapper_User_accept_mi(self, data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_User
  use wrapper_User_impl
  ! DO-NOT-DELETE splicer.begin(wrapper.User.accept.use)
  ! Insert-Code-Here {wrapper.User.accept.use} (use statements)
  ! DO-NOT-DELETE splicer.end(wrapper.User.accept.use)
  implicit none
  type(wrapper_User_t) :: self
  ! in
  type(wrapper_Data_t) :: data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(wrapper.User.accept)
  call setString(data, 'Hello World!', exception)
  call setInt(data, 3_sidl_int, exception)

! DO-NOT-DELETE splicer.end(wrapper.User.accept)
end subroutine wrapper_User_accept_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
