! 
! File:          knapsack_nwKnapsack_Mod.F90
! Symbol:        knapsack.nwKnapsack-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7340M trunk)
! Description:   Server-side private data module for knapsack.nwKnapsack
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_nwKnapsack_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
#include "knapsack_gKnapsack_fAbbrev.h"
! DO-NOT-DELETE splicer.end(_hincludes)

module knapsack_nwKnapsack_impl

  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.use)
  use sidl
  use knapsack_gKnapsack_impl

  implicit none
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type knapsack_nwKnapsack_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.private_data)
    integer (kind=sidl_int) :: d_weights(MAX_WEIGHTS)
    integer (kind=sidl_int) :: d_length
    ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.private_data)
  end type knapsack_nwKnapsack_priv

  type knapsack_nwKnapsack_wrap
    sequence
    type(knapsack_nwKnapsack_priv), pointer :: d_private_data
  end type knapsack_nwKnapsack_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use knapsack_nwKnapsack
    use sidl_BaseInterface
    implicit none
    ! out knapsack_nwKnapsack retval
    type(knapsack_nwKnapsack_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in knapsack_nwKnapsack_wrap private_data
    type(knapsack_nwKnapsack_wrap), intent(in) :: private_data
    external knapsack_nwKnapsack_wrapObj_m
    call knapsack_nwKnapsack_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module knapsack_nwKnapsack_impl
