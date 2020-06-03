! 
! File:          sorting_Quicksort_Mod.F90
! Symbol:        sorting.Quicksort-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.Quicksort
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Quicksort_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_Quicksort_impl

  ! DO-NOT-DELETE splicer.begin(sorting.Quicksort.use)
use sidl
use sidl_int_array
  ! DO-NOT-DELETE splicer.end(sorting.Quicksort.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_Quicksort_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.Quicksort.private_data)
   type(sidl_int_1d) :: lower
   type(sidl_int_1d) :: upper
   integer(kind=sidl_int)  :: depth
    ! DO-NOT-DELETE splicer.end(sorting.Quicksort.private_data)
  end type sorting_Quicksort_priv

  type sorting_Quicksort_wrap
    sequence
    type(sorting_Quicksort_priv), pointer :: d_private_data
  end type sorting_Quicksort_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_Quicksort
    use sidl_BaseInterface
    implicit none
    ! out sorting_Quicksort retval
    type(sorting_Quicksort_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_Quicksort_wrap private_data
    type(sorting_Quicksort_wrap), intent(in) :: private_data
    external sorting_Quicksort_wrapObj_m
    call sorting_Quicksort_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_Quicksort_impl
