! 
! File:          knapsack_kSizeExcept_Mod.F90
! Symbol:        knapsack.kSizeExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7340M trunk)
! Description:   Server-side private data module for knapsack.kSizeExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_kSizeExcept_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Nothing to add here
! DO-NOT-DELETE splicer.end(_hincludes)

module knapsack_kSizeExcept_impl

  ! DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept.use)
  ! Nothing to add here
  ! DO-NOT-DELETE splicer.end(knapsack.kSizeExcept.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type knapsack_kSizeExcept_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(knapsack.kSizeExcept.private_data)
    ! Nothing to add here
    integer :: place_holder
    ! DO-NOT-DELETE splicer.end(knapsack.kSizeExcept.private_data)
  end type knapsack_kSizeExcept_priv

  type knapsack_kSizeExcept_wrap
    sequence
    type(knapsack_kSizeExcept_priv), pointer :: d_private_data
  end type knapsack_kSizeExcept_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use knapsack_kSizeExcept
    use sidl_BaseInterface
    implicit none
    ! out knapsack_kSizeExcept retval
    type(knapsack_kSizeExcept_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in knapsack_kSizeExcept_wrap private_data
    type(knapsack_kSizeExcept_wrap), intent(in) :: private_data
    external knapsack_kSizeExcept_wrapObj_m
    call knapsack_kSizeExcept_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module knapsack_kSizeExcept_impl
