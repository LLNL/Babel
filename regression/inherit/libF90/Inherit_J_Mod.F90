! 
! File:          Inherit_J_Mod.F90
! Symbol:        Inherit.J-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.J
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_J_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_J_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.J.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  private :: Inherit_J_super_e_mi

  interface super_e
  module procedure Inherit_J_super_e_mi
  end interface

  private :: Inherit_J_super_c_mi

  interface super_c
  module procedure Inherit_J_super_c_mi
  end interface

  type Inherit_J_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.J.private_data)
  integer :: place_holder ! replace with your private data
    ! DO-NOT-DELETE splicer.end(Inherit.J.private_data)
  end type Inherit_J_priv

  type Inherit_J_wrap
    sequence
    type(Inherit_J_priv), pointer :: d_private_data
  end type Inherit_J_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Inherit_J
    use sidl_BaseInterface
    implicit none
    ! out Inherit_J retval
    type(Inherit_J_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Inherit_J_wrap private_data
    type(Inherit_J_wrap), intent(in) :: private_data
    external Inherit_J_wrapObj_m
    call Inherit_J_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
  recursive subroutine Inherit_J_super_e_mi(self, retval, exception)
    use sidl
    use sidl_BaseInterface
    use sidl_RuntimeException
    use Inherit_J
    implicit none
    !  in Inherit.J self
    type(Inherit_J_t) , intent(in) :: self
    !  out string retval
    character (len=*) , intent(out) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception

    external Inherit_J_super_e_m
    call Inherit_J_super_e_m(self, retval, exception)

  end subroutine Inherit_J_super_e_mi

  recursive subroutine Inherit_J_super_c_mi(self, retval, exception)
    use sidl
    use sidl_BaseInterface
    use sidl_RuntimeException
    use Inherit_J
    implicit none
    !  in Inherit.J self
    type(Inherit_J_t) , intent(in) :: self
    !  out string retval
    character (len=*) , intent(out) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception

    external Inherit_J_super_c_m
    call Inherit_J_super_c_m(self, retval, exception)

  end subroutine Inherit_J_super_c_mi

end module Inherit_J_impl
