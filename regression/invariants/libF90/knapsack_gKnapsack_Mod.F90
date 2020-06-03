! 
! File:          knapsack_gKnapsack_Mod.F90
! Symbol:        knapsack.gKnapsack-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7340M trunk)
! Description:   Server-side private data module for knapsack.gKnapsack
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_gKnapsack_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Nothing to add here
! DO-NOT-DELETE splicer.end(_hincludes)

module knapsack_gKnapsack_impl

  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.use)
  use sidl

  implicit none

  save
  integer (kind=sidl_int), parameter :: max_weights = 10_sidl_int
  character(36) l_dptr_missing
  character(40) l_max_weights
  character(39) l_pos_weights
  data l_dptr_missing /'Private data is erroneously missing.'/
  data l_max_weights /'Cannot exceed maximum number of weights.'/
  data l_pos_weights /'Non-positive weights are NOT supported.'/
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type knapsack_gKnapsack_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.private_data)
    integer (kind=sidl_int) :: d_weights(MAX_WEIGHTS)
    integer (kind=sidl_int) :: d_length
    ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.private_data)
  end type knapsack_gKnapsack_priv

  type knapsack_gKnapsack_wrap
    sequence
    type(knapsack_gKnapsack_priv), pointer :: d_private_data
  end type knapsack_gKnapsack_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use knapsack_gKnapsack
    use sidl_BaseInterface
    implicit none
    ! out knapsack_gKnapsack retval
    type(knapsack_gKnapsack_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in knapsack_gKnapsack_wrap private_data
    type(knapsack_gKnapsack_wrap), intent(in) :: private_data
    external knapsack_gKnapsack_wrapObj_m
    call knapsack_gKnapsack_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module knapsack_gKnapsack_impl
