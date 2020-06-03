! 
! File:          sorting_Mergesort_Mod.F90
! Symbol:        sorting.Mergesort-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.Mergesort
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Mergesort_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_Mergesort_impl

  ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Mergesort.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_Mergesort_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.Mergesort.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(sorting.Mergesort.private_data)
  end type sorting_Mergesort_priv

  type sorting_Mergesort_wrap
    sequence
    type(sorting_Mergesort_priv), pointer :: d_private_data
  end type sorting_Mergesort_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_Mergesort
    use sidl_BaseInterface
    implicit none
    ! out sorting_Mergesort retval
    type(sorting_Mergesort_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_Mergesort_wrap private_data
    type(sorting_Mergesort_wrap), intent(in) :: private_data
    external sorting_Mergesort_wrapObj_m
    call sorting_Mergesort_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_Mergesort_impl
