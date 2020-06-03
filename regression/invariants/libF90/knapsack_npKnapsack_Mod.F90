! 
! File:          knapsack_npKnapsack_Mod.F90
! Symbol:        knapsack.npKnapsack-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7340M trunk)
! Description:   Server-side private data module for knapsack.npKnapsack
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_npKnapsack_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
#include "knapsack_gKnapsack_fAbbrev.h"
! DO-NOT-DELETE splicer.end(_hincludes)

module knapsack_npKnapsack_impl

  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.use)
  use sidl
  use knapsack_gKnapsack_impl

  implicit none
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type knapsack_npKnapsack_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.private_data)
    integer (kind=sidl_int) :: d_weights(MAX_WEIGHTS)
    integer (kind=sidl_int) :: d_length
    ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.private_data)
  end type knapsack_npKnapsack_priv

  type knapsack_npKnapsack_wrap
    sequence
    type(knapsack_npKnapsack_priv), pointer :: d_private_data
  end type knapsack_npKnapsack_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use knapsack_npKnapsack
    use sidl_BaseInterface
    implicit none
    ! out knapsack_npKnapsack retval
    type(knapsack_npKnapsack_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in knapsack_npKnapsack_wrap private_data
    type(knapsack_npKnapsack_wrap), intent(in) :: private_data
    external knapsack_npKnapsack_wrapObj_m
    call knapsack_npKnapsack_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module knapsack_npKnapsack_impl
