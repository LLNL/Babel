! 
! File:          Inherit_F_Mod.F90
! Symbol:        Inherit.F-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.F
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_F_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_F_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.F.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Inherit_F_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.F.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.F.private_data)
  end type Inherit_F_priv

  type Inherit_F_wrap
    sequence
    type(Inherit_F_priv), pointer :: d_private_data
  end type Inherit_F_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_F
    use sidl_BaseInterface
    implicit none
    ! out Inherit_F retval
    type(Inherit_F_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_F_wrap private_data
    type(Inherit_F_wrap), intent(in) :: private_data
    external Inherit_F_wrapObj_m
    call Inherit_F_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Inherit_F_impl
