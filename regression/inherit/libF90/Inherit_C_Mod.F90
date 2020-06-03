! 
! File:          Inherit_C_Mod.F90
! Symbol:        Inherit.C-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.C
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_C_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_C_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.C.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.C.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Inherit_C_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.C.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.C.private_data)
  end type Inherit_C_priv

  type Inherit_C_wrap
    sequence
    type(Inherit_C_priv), pointer :: d_private_data
  end type Inherit_C_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_C
    use sidl_BaseInterface
    implicit none
    ! out Inherit_C retval
    type(Inherit_C_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_C_wrap private_data
    type(Inherit_C_wrap), intent(in) :: private_data
    external Inherit_C_wrapObj_m
    call Inherit_C_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Inherit_C_impl
