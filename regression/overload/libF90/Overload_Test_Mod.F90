! 
! File:          Overload_Test_Mod.F90
! Symbol:        Overload.Test-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Overload.Test
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Overload_Test_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Overload_Test_impl

  ! DO-NOT-DELETE splicer.begin(Overload.Test.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Overload_Test_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Overload.Test.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Overload.Test.private_data)
  end type Overload_Test_priv

  type Overload_Test_wrap
    sequence
    type(Overload_Test_priv), pointer :: d_private_data
  end type Overload_Test_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Overload_Test
    use sidl_BaseInterface
    implicit none
    ! out Overload_Test retval
    type(Overload_Test_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Overload_Test_wrap private_data
    type(Overload_Test_wrap), intent(in) :: private_data
    external Overload_Test_wrapObj_m
    call Overload_Test_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Overload_Test_impl
