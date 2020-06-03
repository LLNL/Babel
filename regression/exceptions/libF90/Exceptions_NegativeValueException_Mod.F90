! 
! File:          Exceptions_NegativeValueException_Mod.F90
! Symbol:        Exceptions.NegativeValueException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Exceptions.NegativeValueException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_NegativeValueException_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Exceptions_NegativeValueException_impl

  ! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Exceptions_NegativeValueException_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException.private_data)
  end type Exceptions_NegativeValueException_priv

  type Exceptions_NegativeValueException_wrap
    sequence
    type(Exceptions_NegativeValueException_priv), pointer :: d_private_data
  end type Exceptions_NegativeValueException_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Exceptions_NegativeValueException
    use sidl_BaseInterface
    implicit none
    ! out Exceptions_NegativeValueException retval
    type(Exceptions_NegativeValueException_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Exceptions_NegativeValueException_wrap private_data
    type(Exceptions_NegativeValueException_wrap), intent(in) :: private_data
    external Exceptions_NegativeValueException_wrapObj_m
    call Exceptions_NegativeValueException_wrapObj_m(private_data, retval,     &
      exception)
   end subroutine wrapObj_s
end module Exceptions_NegativeValueException_impl
