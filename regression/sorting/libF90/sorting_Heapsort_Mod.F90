! 
! File:          sorting_Heapsort_Mod.F90
! Symbol:        sorting.Heapsort-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.Heapsort
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Heapsort_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_Heapsort_impl

  ! DO-NOT-DELETE splicer.begin(sorting.Heapsort.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.Heapsort.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_Heapsort_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.Heapsort.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(sorting.Heapsort.private_data)
  end type sorting_Heapsort_priv

  type sorting_Heapsort_wrap
    sequence
    type(sorting_Heapsort_priv), pointer :: d_private_data
  end type sorting_Heapsort_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_Heapsort
    use sidl_BaseInterface
    implicit none
    ! out sorting_Heapsort retval
    type(sorting_Heapsort_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_Heapsort_wrap private_data
    type(sorting_Heapsort_wrap), intent(in) :: private_data
    external sorting_Heapsort_wrapObj_m
    call sorting_Heapsort_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_Heapsort_impl
