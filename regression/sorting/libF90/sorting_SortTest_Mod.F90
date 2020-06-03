! 
! File:          sorting_SortTest_Mod.F90
! Symbol:        sorting.SortTest-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.SortTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_SortTest_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_SortTest_impl

  ! DO-NOT-DELETE splicer.begin(sorting.SortTest.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.SortTest.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_SortTest_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.SortTest.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(sorting.SortTest.private_data)
  end type sorting_SortTest_priv

  type sorting_SortTest_wrap
    sequence
    type(sorting_SortTest_priv), pointer :: d_private_data
  end type sorting_SortTest_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_SortTest
    use sidl_BaseInterface
    implicit none
    ! out sorting_SortTest retval
    type(sorting_SortTest_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_SortTest_wrap private_data
    type(sorting_SortTest_wrap), intent(in) :: private_data
    external sorting_SortTest_wrapObj_m
    call sorting_SortTest_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_SortTest_impl
