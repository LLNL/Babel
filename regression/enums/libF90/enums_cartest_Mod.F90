! 
! File:          enums_cartest_Mod.F90
! Symbol:        enums.cartest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for enums.cartest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "enums_cartest_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module enums_cartest_impl

  ! DO-NOT-DELETE splicer.begin(enums.cartest.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.cartest.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type enums_cartest_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(enums.cartest.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(enums.cartest.private_data)
  end type enums_cartest_priv

  type enums_cartest_wrap
    sequence
    type(enums_cartest_priv), pointer :: d_private_data
  end type enums_cartest_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use enums_cartest
    use sidl_BaseInterface
    implicit none
    ! out enums_cartest retval
    type(enums_cartest_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in enums_cartest_wrap private_data
    type(enums_cartest_wrap), intent(in) :: private_data
    external enums_cartest_wrapObj_m
    call enums_cartest_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module enums_cartest_impl
