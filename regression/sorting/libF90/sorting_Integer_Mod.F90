! 
! File:          sorting_Integer_Mod.F90
! Symbol:        sorting.Integer-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.Integer
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_Integer_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_Integer_impl

  ! DO-NOT-DELETE splicer.begin(sorting.Integer.use)
use sidl
  ! DO-NOT-DELETE splicer.end(sorting.Integer.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type sorting_Integer_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.Integer.private_data)
   integer(kind=sidl_int) :: d_value
    ! DO-NOT-DELETE splicer.end(sorting.Integer.private_data)
  end type sorting_Integer_priv

  type sorting_Integer_wrap
    sequence
    type(sorting_Integer_priv), pointer :: d_private_data
  end type sorting_Integer_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use sorting_Integer
    use sidl_BaseInterface
    implicit none
    ! out sorting_Integer retval
    type(sorting_Integer_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in sorting_Integer_wrap private_data
    type(sorting_Integer_wrap), intent(in) :: private_data
    external sorting_Integer_wrapObj_m
    call sorting_Integer_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module sorting_Integer_impl
