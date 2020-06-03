! 
! File:          Exceptions_Fib_Mod.F90
! Symbol:        Exceptions.Fib-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Exceptions.Fib
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_Fib_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Exceptions_Fib_impl

  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Exceptions_Fib_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Exceptions.Fib.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Exceptions.Fib.private_data)
  end type Exceptions_Fib_priv

  type Exceptions_Fib_wrap
    sequence
    type(Exceptions_Fib_priv), pointer :: d_private_data
  end type Exceptions_Fib_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Exceptions_Fib
    use sidl_BaseInterface
    implicit none
    ! out Exceptions_Fib retval
    type(Exceptions_Fib_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Exceptions_Fib_wrap private_data
    type(Exceptions_Fib_wrap), intent(in) :: private_data
    external Exceptions_Fib_wrapObj_m
    call Exceptions_Fib_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Exceptions_Fib_impl
