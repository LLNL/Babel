! 
! File:          Overload_AnException_Mod.F90
! Symbol:        Overload.AnException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Overload.AnException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Overload_AnException_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Overload_AnException_impl

  ! DO-NOT-DELETE splicer.begin(Overload.AnException.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AnException.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Overload_AnException_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Overload.AnException.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Overload.AnException.private_data)
  end type Overload_AnException_priv

  type Overload_AnException_wrap
    sequence
    type(Overload_AnException_priv), pointer :: d_private_data
  end type Overload_AnException_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Overload_AnException
    use sidl_BaseInterface
    implicit none
    ! out Overload_AnException retval
    type(Overload_AnException_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Overload_AnException_wrap private_data
    type(Overload_AnException_wrap), intent(in) :: private_data
    external Overload_AnException_wrapObj_m
    call Overload_AnException_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Overload_AnException_impl
