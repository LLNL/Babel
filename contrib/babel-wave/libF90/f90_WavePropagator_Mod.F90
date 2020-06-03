! 
! File:          f90_WavePropagator_Mod.F90
! Symbol:        f90.WavePropagator-v1.0
! Symbol Type:   class
! Babel Version: 1.2.0
! Description:   Server-side private data module for f90.WavePropagator
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "f90_WavePropagator_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module f90_WavePropagator_impl

  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator.use)
  use sidl_double_array
  use wave2d_ScalarField
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type f90_WavePropagator_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(f90.WavePropagator.private_data)
    ! Insert-Code-Here {f90.WavePropagator.private_data} (private data members)
    type(sidl_double_2d) :: p1, p2, p3
    type(wave2d_ScalarField_t ) :: density
    integer(kind=sidl_int) :: lower(2), upper(2)
    ! DO-NOT-DELETE splicer.end(f90.WavePropagator.private_data)
  end type f90_WavePropagator_priv

  type f90_WavePropagator_wrap
    sequence
    type(f90_WavePropagator_priv), pointer :: d_private_data
  end type f90_WavePropagator_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use f90_WavePropagator
    use sidl_BaseInterface
    implicit none
    ! out f90_WavePropagator retval
    type(f90_WavePropagator_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in f90_WavePropagator_wrap private_data
    type(f90_WavePropagator_wrap), intent(in) :: private_data
    external f90_WavePropagator_wrapObj_m
    call f90_WavePropagator_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module f90_WavePropagator_impl
