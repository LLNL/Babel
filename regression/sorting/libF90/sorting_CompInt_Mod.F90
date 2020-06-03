! 
! File:          sorting_CompInt_Mod.F90
! Symbol:        sorting.CompInt-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.CompInt
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_CompInt_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_CompInt_impl

  ! DO-NOT-DELETE splicer.begin(sorting.CompInt.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(sorting.CompInt.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_CompInt_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.CompInt.private_data)
   logical :: d_increasing
    ! DO-NOT-DELETE splicer.end(sorting.CompInt.private_data)
  end type sorting_CompInt_priv

  type sorting_CompInt_wrap
    sequence
    type(sorting_CompInt_priv), pointer :: d_private_data
  end type sorting_CompInt_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_CompInt
    use sidl_BaseInterface
    implicit none
    ! out sorting_CompInt retval
    type(sorting_CompInt_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_CompInt_wrap private_data
    type(sorting_CompInt_wrap), intent(in) :: private_data
    external sorting_CompInt_wrapObj_m
    call sorting_CompInt_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_CompInt_impl
