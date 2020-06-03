! 
! File:          ArrayTest_ArrayOps_Mod.F90
! Symbol:        ArrayTest.ArrayOps-v1.3
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for ArrayTest.ArrayOps
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "ArrayTest_ArrayOps_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module ArrayTest_ArrayOps_impl

  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type ArrayTest_ArrayOps_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.private_data)
  end type ArrayTest_ArrayOps_priv

  type ArrayTest_ArrayOps_wrap
    sequence
    type(ArrayTest_ArrayOps_priv), pointer :: d_private_data
  end type ArrayTest_ArrayOps_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use ArrayTest_ArrayOps
    use sidl_BaseInterface
    implicit none
    ! out ArrayTest_ArrayOps retval
    type(ArrayTest_ArrayOps_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in ArrayTest_ArrayOps_wrap private_data
    type(ArrayTest_ArrayOps_wrap), intent(in) :: private_data
    external ArrayTest_ArrayOps_wrapObj_m
    call ArrayTest_ArrayOps_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module ArrayTest_ArrayOps_impl
