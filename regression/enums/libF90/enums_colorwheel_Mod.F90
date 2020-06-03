! 
! File:          enums_colorwheel_Mod.F90
! Symbol:        enums.colorwheel-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for enums.colorwheel
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "enums_colorwheel_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module enums_colorwheel_impl

  ! DO-NOT-DELETE splicer.begin(enums.colorwheel.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type enums_colorwheel_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.private_data)
  end type enums_colorwheel_priv

  type enums_colorwheel_wrap
    sequence
    type(enums_colorwheel_priv), pointer :: d_private_data
  end type enums_colorwheel_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use enums_colorwheel
    use sidl_BaseInterface
    implicit none
    ! out enums_colorwheel retval
    type(enums_colorwheel_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in enums_colorwheel_wrap private_data
    type(enums_colorwheel_wrap), intent(in) :: private_data
    external enums_colorwheel_wrapObj_m
    call enums_colorwheel_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module enums_colorwheel_impl
