! 
! File:          sorting_IntegerContainer_Mod.F90
! Symbol:        sorting.IntegerContainer-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.IntegerContainer
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_IntegerContainer_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_IntegerContainer_impl

  ! DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.use)
  use sorting_Integer_type
  ! DO-NOT-DELETE splicer.end(sorting.IntegerContainer.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_IntegerContainer_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.private_data)
  type(sorting_Integer_1d) :: d_array
    ! DO-NOT-DELETE splicer.end(sorting.IntegerContainer.private_data)
  end type sorting_IntegerContainer_priv

  type sorting_IntegerContainer_wrap
    sequence
    type(sorting_IntegerContainer_priv), pointer :: d_private_data
  end type sorting_IntegerContainer_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_IntegerContainer
    use sidl_BaseInterface
    implicit none
    ! out sorting_IntegerContainer retval
    type(sorting_IntegerContainer_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_IntegerContainer_wrap private_data
    type(sorting_IntegerContainer_wrap), intent(in) :: private_data
    external sorting_IntegerContainer_wrapObj_m
    call sorting_IntegerContainer_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_IntegerContainer_impl
