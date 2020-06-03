! 
! File:          Ordering_IntOrderTest_Mod.F90
! Symbol:        Ordering.IntOrderTest-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Ordering.IntOrderTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Ordering_IntOrderTest_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Ordering_IntOrderTest_impl

  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Ordering_IntOrderTest_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.private_data)
  end type Ordering_IntOrderTest_priv

  type Ordering_IntOrderTest_wrap
    sequence
    type(Ordering_IntOrderTest_priv), pointer :: d_private_data
  end type Ordering_IntOrderTest_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Ordering_IntOrderTest
    use sidl_BaseInterface
    implicit none
    ! out Ordering_IntOrderTest retval
    type(Ordering_IntOrderTest_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Ordering_IntOrderTest_wrap private_data
    type(Ordering_IntOrderTest_wrap), intent(in) :: private_data
    external Ordering_IntOrderTest_wrapObj_m
    call Ordering_IntOrderTest_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Ordering_IntOrderTest_impl
