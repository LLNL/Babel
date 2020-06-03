! 
! File:          wrapper_User_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module wrapper_User_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  use wrapper_User
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(wrapper_User_t) :: wrapper_User_impl_t
    ! DO-NOT-DELETE splicer.begin(wrapper.User.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(wrapper.User.private_data)
  end type wrapper_User_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(wrapper_User_impl_t), target :: obj
    type(wrapper_User_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external wrapper_User_wrap
    call wrapper_User_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.User._ctor.use)
    ! Insert-Code-Here {wrapper.User._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.User._ctor.use)
    implicit none
    !  in wrapper.User self
    type(wrapper_User_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

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
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.User._dtor.use)
    ! Insert-Code-Here {wrapper.User._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.User._dtor.use)
    implicit none
    !  in wrapper.User self
    type(wrapper_User_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

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
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.User._load.use)
    ! Insert-Code-Here {wrapper.User._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.User._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.User._load)
    ! Insert-Code-Here {wrapper.User._load} (_load method)
    ! DO-NOT-DELETE splicer.end(wrapper.User._load)
  end subroutine load_impl


  ! 
  ! Method:  accept[]
  ! 

  subroutine accept_impl(self, data, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.User.accept.use)
    ! Insert-Code-Here {wrapper.User.accept.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.User.accept.use)
    implicit none
    !  in wrapper.User self
    type(wrapper_User_impl_t) :: self
    !  in wrapper.Data data
    type(wrapper_Data_t), intent(in) :: data
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.User.accept)
    call setString(data, 'Hello World!', exception)
    call setInt(data, 3_sidl_int, exception)

    ! DO-NOT-DELETE splicer.end(wrapper.User.accept)
  end subroutine accept_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module wrapper_User_Impl
