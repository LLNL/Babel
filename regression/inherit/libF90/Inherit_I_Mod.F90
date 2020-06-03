! 
! File:          Inherit_I_Mod.F90
! Symbol:        Inherit.I-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.I
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_I_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_I_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.I.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.I.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Inherit_I_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.I.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.I.private_data)
  end type Inherit_I_priv

  type Inherit_I_wrap
    sequence
    type(Inherit_I_priv), pointer :: d_private_data
  end type Inherit_I_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_I
    use sidl_BaseInterface
    implicit none
    ! out Inherit_I retval
    type(Inherit_I_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_I_wrap private_data
    type(Inherit_I_wrap), intent(in) :: private_data
    external Inherit_I_wrapObj_m
    call Inherit_I_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Inherit_I_impl
