! 
! File:          wrapper_Data_Mod.F90
! Symbol:        wrapper.Data-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for wrapper.Data
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "wrapper_Data_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module wrapper_Data_impl

  ! DO-NOT-DELETE splicer.begin(wrapper.Data.use)
use sidl
  ! DO-NOT-DELETE splicer.end(wrapper.Data.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type wrapper_Data_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(wrapper.Data.private_data)
    ! Insert-Code-Here {wrapper.Data.private_data} (private data members)
     character(len=256)     :: d_ctortest
     character(len=256)     :: d_string
     integer(kind=sidl_int) :: d_int
    ! DO-NOT-DELETE splicer.end(wrapper.Data.private_data)
  end type wrapper_Data_priv

  type wrapper_Data_wrap
    sequence
    type(wrapper_Data_priv), pointer :: d_private_data
  end type wrapper_Data_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use wrapper_Data
    use sidl_BaseInterface
    implicit none
    ! out wrapper_Data retval
    type(wrapper_Data_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in wrapper_Data_wrap private_data
    type(wrapper_Data_wrap), intent(in) :: private_data
    external wrapper_Data_wrapObj_m
    call wrapper_Data_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module wrapper_Data_impl
