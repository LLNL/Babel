! 
! File:          Inherit_E2_Mod.F90
! Symbol:        Inherit.E2-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.E2
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_E2_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_E2_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.E2.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.E2.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  private :: Inherit_E2_super_c_mi

  interface super_c
  module procedure Inherit_E2_super_c_mi
  end interface

  type Inherit_E2_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.E2.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.E2.private_data)
  end type Inherit_E2_priv

  type Inherit_E2_wrap
    sequence
    type(Inherit_E2_priv), pointer :: d_private_data
  end type Inherit_E2_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_E2
    use sidl_BaseInterface
    implicit none
    ! out Inherit_E2 retval
    type(Inherit_E2_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_E2_wrap private_data
    type(Inherit_E2_wrap), intent(in) :: private_data
    external Inherit_E2_wrapObj_m
    call Inherit_E2_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
  recursive subroutine Inherit_E2_super_c_mi(self, retval, exception)
    use sidl
    use sidl_BaseInterface
    use sidl_RuntimeException
    use Inherit_E2
    implicit none
    !  in Inherit.E2 self
    type(Inherit_E2_t) , intent(in) :: self
    !  out string retval
    character (len=*) , intent(out) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception

    external Inherit_E2_super_c_m
    call Inherit_E2_super_c_m(self, retval, exception)

  end subroutine Inherit_E2_super_c_mi

end module Inherit_E2_impl
