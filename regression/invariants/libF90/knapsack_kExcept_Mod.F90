! 
! File:          knapsack_kExcept_Mod.F90
! Symbol:        knapsack.kExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7340M trunk)
! Description:   Server-side private data module for knapsack.kExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_kExcept_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Nothing to add here
! DO-NOT-DELETE splicer.end(_hincludes)

module knapsack_kExcept_impl

  ! DO-NOT-DELETE splicer.begin(knapsack.kExcept.use)
  ! Nothing to add here
  ! DO-NOT-DELETE splicer.end(knapsack.kExcept.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type knapsack_kExcept_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(knapsack.kExcept.private_data)
    ! Nothing to add here
    integer :: place_holder
    ! DO-NOT-DELETE splicer.end(knapsack.kExcept.private_data)
  end type knapsack_kExcept_priv

  type knapsack_kExcept_wrap
    sequence
    type(knapsack_kExcept_priv), pointer :: d_private_data
  end type knapsack_kExcept_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use knapsack_kExcept
    use sidl_BaseInterface
    implicit none
    ! out knapsack_kExcept retval
    type(knapsack_kExcept_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in knapsack_kExcept_wrap private_data
    type(knapsack_kExcept_wrap), intent(in) :: private_data
    external knapsack_kExcept_wrapObj_m
    call knapsack_kExcept_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module knapsack_kExcept_impl
