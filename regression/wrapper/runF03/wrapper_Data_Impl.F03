! 
! File:          wrapper_Data_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module wrapper_Data_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wrapper_Data
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(wrapper_Data_t) :: wrapper_Data_impl_t
    ! DO-NOT-DELETE splicer.begin(wrapper.Data.private_data)
    ! insert code here (private data members)
    character(len=256)     :: d_ctortest
    character(len=256)     :: d_string
    integer(kind=sidl_int) :: d_int
    ! DO-NOT-DELETE splicer.end(wrapper.Data.private_data)
  end type wrapper_Data_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(wrapper_Data_impl_t), target :: obj
    type(wrapper_Data_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external wrapper_Data_wrap
    call wrapper_Data_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.Data._ctor.use)
    ! Insert-Code-Here {wrapper.Data._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.Data._ctor.use)
    implicit none
    !  in wrapper.Data self
    type(wrapper_Data_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.Data._ctor)
    ! Insert-Code-Here {wrapper.Data._ctor} (_ctor method)
    self%d_ctortest = 'ctor was run'
    ! DO-NOT-DELETE splicer.end(wrapper.Data._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.Data._dtor.use)
    ! Insert-Code-Here {wrapper.Data._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.Data._dtor.use)
    implicit none
    !  in wrapper.Data self
    type(wrapper_Data_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.Data._dtor)
    ! Insert-Code-Here {wrapper.Data._dtor} (_dtor method)
    ! 
    ! DO-NOT-DELETE splicer.end(wrapper.Data._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.Data._load.use)
    ! Insert-Code-Here {wrapper.Data._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.Data._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.Data._load)
    ! Insert-Code-Here {wrapper.Data._load} (_load method)
    ! DO-NOT-DELETE splicer.end(wrapper.Data._load)
  end subroutine load_impl


  ! 
  ! Method:  setString[]
  ! 

  subroutine setString_impl(self, s, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.Data.setString.use)
    ! Insert-Code-Here {wrapper.Data.setString.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.Data.setString.use)
    implicit none
    !  in wrapper.Data self
    type(wrapper_Data_impl_t) :: self
    !  in string s
    character (len=*), intent(in) :: s
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.Data.setString)
    self%d_string = s
    ! DO-NOT-DELETE splicer.end(wrapper.Data.setString)
  end subroutine setString_impl


  ! 
  ! Method:  setInt[]
  ! 

  subroutine setInt_impl(self, i, exception)
    ! DO-NOT-DELETE splicer.begin(wrapper.Data.setInt.use)
    ! Insert-Code-Here {wrapper.Data.setInt.use} (use statements)
    ! DO-NOT-DELETE splicer.end(wrapper.Data.setInt.use)
    implicit none
    !  in wrapper.Data self
    type(wrapper_Data_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(wrapper.Data.setInt)
    self%d_int = i
    ! DO-NOT-DELETE splicer.end(wrapper.Data.setInt)
  end subroutine setInt_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module wrapper_Data_Impl
