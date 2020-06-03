! 
! File:          Overload_BClass_Mod.F90
! Symbol:        Overload.BClass-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Overload.BClass
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Overload_BClass_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Overload_BClass_impl

  ! DO-NOT-DELETE splicer.begin(Overload.BClass.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.BClass.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Overload_BClass_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Overload.BClass.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Overload.BClass.private_data)
  end type Overload_BClass_priv

  type Overload_BClass_wrap
    sequence
    type(Overload_BClass_priv), pointer :: d_private_data
  end type Overload_BClass_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Overload_BClass
    use sidl_BaseInterface
    implicit none
    ! out Overload_BClass retval
    type(Overload_BClass_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Overload_BClass_wrap private_data
    type(Overload_BClass_wrap), intent(in) :: private_data
    external Overload_BClass_wrapObj_m
    call Overload_BClass_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Overload_BClass_impl
