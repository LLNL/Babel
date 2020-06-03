! 
! File:          sorting_SimpleCounter_Mod.F90
! Symbol:        sorting.SimpleCounter-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.SimpleCounter
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_SimpleCounter_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_SimpleCounter_impl

  ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.use)
use sidl
  ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_SimpleCounter_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.private_data)
   integer(kind=sidl_int) :: count
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.private_data)
  end type sorting_SimpleCounter_priv

  type sorting_SimpleCounter_wrap
    sequence
    type(sorting_SimpleCounter_priv), pointer :: d_private_data
  end type sorting_SimpleCounter_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_SimpleCounter
    use sidl_BaseInterface
    implicit none
    ! out sorting_SimpleCounter retval
    type(sorting_SimpleCounter_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_SimpleCounter_wrap private_data
    type(sorting_SimpleCounter_wrap), intent(in) :: private_data
    external sorting_SimpleCounter_wrapObj_m
    call sorting_SimpleCounter_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_SimpleCounter_impl
