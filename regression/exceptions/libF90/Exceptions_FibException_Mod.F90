! 
! File:          Exceptions_FibException_Mod.F90
! Symbol:        Exceptions.FibException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Exceptions.FibException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Exceptions_FibException_impl

  ! DO-NOT-DELETE splicer.begin(Exceptions.FibException.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.FibException.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Exceptions_FibException_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException.private_data)
  end type Exceptions_FibException_priv

  type Exceptions_FibException_wrap
    sequence
    type(Exceptions_FibException_priv), pointer :: d_private_data
  end type Exceptions_FibException_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Exceptions_FibException
    use sidl_BaseInterface
    implicit none
    ! out Exceptions_FibException retval
    type(Exceptions_FibException_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Exceptions_FibException_wrap private_data
    type(Exceptions_FibException_wrap), intent(in) :: private_data
    external Exceptions_FibException_wrapObj_m
    call Exceptions_FibException_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Exceptions_FibException_impl
