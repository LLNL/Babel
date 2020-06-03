! 
! File:          Inherit_E_Mod.F90
! Symbol:        Inherit.E-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.E
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_E_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_E_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.E.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.E.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Inherit_E_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.E.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.E.private_data)
  end type Inherit_E_priv

  type Inherit_E_wrap
    sequence
    type(Inherit_E_priv), pointer :: d_private_data
  end type Inherit_E_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_E
    use sidl_BaseInterface
    implicit none
    ! out Inherit_E retval
    type(Inherit_E_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_E_wrap private_data
    type(Inherit_E_wrap), intent(in) :: private_data
    external Inherit_E_wrapObj_m
    call Inherit_E_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Inherit_E_impl
