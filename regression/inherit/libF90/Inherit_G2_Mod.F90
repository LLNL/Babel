! 
! File:          Inherit_G2_Mod.F90
! Symbol:        Inherit.G2-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.G2
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_G2_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_G2_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.G2.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.G2.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  private :: Inherit_G2_super_a_mi

  interface super_a
  module procedure Inherit_G2_super_a_mi
  end interface

  private :: Inherit_G2_super_d_mi

  interface super_d
  module procedure Inherit_G2_super_d_mi
  end interface

  type Inherit_G2_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.G2.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.G2.private_data)
  end type Inherit_G2_priv

  type Inherit_G2_wrap
    sequence
    type(Inherit_G2_priv), pointer :: d_private_data
  end type Inherit_G2_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_G2
    use sidl_BaseInterface
    implicit none
    ! out Inherit_G2 retval
    type(Inherit_G2_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_G2_wrap private_data
    type(Inherit_G2_wrap), intent(in) :: private_data
    external Inherit_G2_wrapObj_m
    call Inherit_G2_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
  recursive subroutine Inherit_G2_super_a_mi(self, retval, exception)
    use sidl
    use sidl_BaseInterface
    use sidl_RuntimeException
    use Inherit_G2
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_t) , intent(in) :: self
    !  out string retval
    character (len=*) , intent(out) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception

    external Inherit_G2_super_a_m
    call Inherit_G2_super_a_m(self, retval, exception)

  end subroutine Inherit_G2_super_a_mi

  recursive subroutine Inherit_G2_super_d_mi(self, retval, exception)
    use sidl
    use sidl_BaseInterface
    use sidl_RuntimeException
    use Inherit_G2
    implicit none
    !  in Inherit.G2 self
    type(Inherit_G2_t) , intent(in) :: self
    !  out string retval
    character (len=*) , intent(out) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception

    external Inherit_G2_super_d_m
    call Inherit_G2_super_d_m(self, retval, exception)

  end subroutine Inherit_G2_super_d_mi

end module Inherit_G2_impl
