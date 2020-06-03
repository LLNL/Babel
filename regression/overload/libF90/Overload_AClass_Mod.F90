! 
! File:          Overload_AClass_Mod.F90
! Symbol:        Overload.AClass-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Overload.AClass
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Overload_AClass_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Overload_AClass_impl

  ! DO-NOT-DELETE splicer.begin(Overload.AClass.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AClass.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Overload_AClass_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Overload.AClass.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Overload.AClass.private_data)
  end type Overload_AClass_priv

  type Overload_AClass_wrap
    sequence
    type(Overload_AClass_priv), pointer :: d_private_data
  end type Overload_AClass_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Overload_AClass
    use sidl_BaseInterface
    implicit none
    ! out Overload_AClass retval
    type(Overload_AClass_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Overload_AClass_wrap private_data
    type(Overload_AClass_wrap), intent(in) :: private_data
    external Overload_AClass_wrapObj_m
    call Overload_AClass_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Overload_AClass_impl
