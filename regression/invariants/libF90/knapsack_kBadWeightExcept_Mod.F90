! 
! File:          knapsack_kBadWeightExcept_Mod.F90
! Symbol:        knapsack.kBadWeightExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7340M trunk)
! Description:   Server-side private data module for knapsack.kBadWeightExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_kBadWeightExcept_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Nothing to add here
! DO-NOT-DELETE splicer.end(_hincludes)

module knapsack_kBadWeightExcept_impl

  ! DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept.use)
  ! Nothing to add here
  ! DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type knapsack_kBadWeightExcept_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(knapsack.kBadWeightExcept.private_data)
    ! Nothing to add here
    integer :: place_holder
    ! DO-NOT-DELETE splicer.end(knapsack.kBadWeightExcept.private_data)
  end type knapsack_kBadWeightExcept_priv

  type knapsack_kBadWeightExcept_wrap
    sequence
    type(knapsack_kBadWeightExcept_priv), pointer :: d_private_data
  end type knapsack_kBadWeightExcept_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use knapsack_kBadWeightExcept
    use sidl_BaseInterface
    implicit none
    ! out knapsack_kBadWeightExcept retval
    type(knapsack_kBadWeightExcept_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in knapsack_kBadWeightExcept_wrap private_data
    type(knapsack_kBadWeightExcept_wrap), intent(in) :: private_data
    external knapsack_kBadWeightExcept_wrapObj_m
    call knapsack_kBadWeightExcept_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module knapsack_kBadWeightExcept_impl
