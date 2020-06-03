! 
! File:          Exceptions_TooDeepException_Mod.F90
! Symbol:        Exceptions.TooDeepException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Exceptions.TooDeepException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_TooDeepException_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Exceptions_TooDeepException_impl

  ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Exceptions_TooDeepException_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException.private_data)
  end type Exceptions_TooDeepException_priv

  type Exceptions_TooDeepException_wrap
    sequence
    type(Exceptions_TooDeepException_priv), pointer :: d_private_data
  end type Exceptions_TooDeepException_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Exceptions_TooDeepException
    use sidl_BaseInterface
    implicit none
    ! out Exceptions_TooDeepException retval
    type(Exceptions_TooDeepException_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Exceptions_TooDeepException_wrap private_data
    type(Exceptions_TooDeepException_wrap), intent(in) :: private_data
    external Exceptions_TooDeepException_wrapObj_m
    call Exceptions_TooDeepException_wrapObj_m(private_data, retval,           &
      exception)
   end subroutine wrapObj_s
end module Exceptions_TooDeepException_impl
