! 
! File:          Exceptions_TooBigException_Mod.F90
! Symbol:        Exceptions.TooBigException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Exceptions.TooBigException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_TooBigException_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Exceptions_TooBigException_impl

  ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Exceptions_TooBigException_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException.private_data)
  end type Exceptions_TooBigException_priv

  type Exceptions_TooBigException_wrap
    sequence
    type(Exceptions_TooBigException_priv), pointer :: d_private_data
  end type Exceptions_TooBigException_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Exceptions_TooBigException
    use sidl_BaseInterface
    implicit none
    ! out Exceptions_TooBigException retval
    type(Exceptions_TooBigException_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Exceptions_TooBigException_wrap private_data
    type(Exceptions_TooBigException_wrap), intent(in) :: private_data
    external Exceptions_TooBigException_wrapObj_m
    call Exceptions_TooBigException_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Exceptions_TooBigException_impl
