! 
! File:          Inherit_D_Mod.F90
! Symbol:        Inherit.D-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.D
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_D_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_D_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.D.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.D.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Inherit_D_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.D.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.D.private_data)
  end type Inherit_D_priv

  type Inherit_D_wrap
    sequence
    type(Inherit_D_priv), pointer :: d_private_data
  end type Inherit_D_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_D
    use sidl_BaseInterface
    implicit none
    ! out Inherit_D retval
    type(Inherit_D_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_D_wrap private_data
    type(Inherit_D_wrap), intent(in) :: private_data
    external Inherit_D_wrapObj_m
    call Inherit_D_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Inherit_D_impl
