! 
! File:          enums_numbertest_Mod.F90
! Symbol:        enums.numbertest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for enums.numbertest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "enums_numbertest_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module enums_numbertest_impl

  ! DO-NOT-DELETE splicer.begin(enums.numbertest.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type enums_numbertest_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(enums.numbertest.private_data)
  end type enums_numbertest_priv

  type enums_numbertest_wrap
    sequence
    type(enums_numbertest_priv), pointer :: d_private_data
  end type enums_numbertest_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use enums_numbertest
    use sidl_BaseInterface
    implicit none
    ! out enums_numbertest retval
    type(enums_numbertest_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in enums_numbertest_wrap private_data
    type(enums_numbertest_wrap), intent(in) :: private_data
    external enums_numbertest_wrapObj_m
    call enums_numbertest_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module enums_numbertest_impl
