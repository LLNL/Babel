! 
! File:          f90_BoundsException_Mod.F90
! Symbol:        f90.BoundsException-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side private data module for f90.BoundsException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "f90_BoundsException_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module f90_BoundsException_impl

  ! DO-NOT-DELETE splicer.begin(f90.BoundsException.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.BoundsException.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type f90_BoundsException_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(f90.BoundsException.private_data)
   integer :: place_holder ! replace with your private data
    ! DO-NOT-DELETE splicer.end(f90.BoundsException.private_data)
  end type f90_BoundsException_priv

  type f90_BoundsException_wrap
    sequence
    type(f90_BoundsException_priv), pointer :: d_private_data
  end type f90_BoundsException_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use f90_BoundsException
    use sidl_BaseInterface
    implicit none
    ! out f90_BoundsException retval
    type(f90_BoundsException_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in f90_BoundsException_wrap private_data
    type(f90_BoundsException_wrap), intent(in) :: private_data
    external f90_BoundsException_wrapObj_m
    call f90_BoundsException_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module f90_BoundsException_impl
