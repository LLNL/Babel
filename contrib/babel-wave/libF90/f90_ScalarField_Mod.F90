! 
! File:          f90_ScalarField_Mod.F90
! Symbol:        f90.ScalarField-v1.0
! Symbol Type:   class
! Babel Version: 1.2.0
! Description:   Server-side private data module for f90.ScalarField
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "f90_ScalarField_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module f90_ScalarField_impl

  ! DO-NOT-DELETE splicer.begin(f90.ScalarField.use)
  use sidl_double_array
  ! DO-NOT-DELETE splicer.end(f90.ScalarField.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type f90_ScalarField_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(f90.ScalarField.private_data)
    real(kind=sidl_double) :: d_minx
    real(kind=sidl_double) :: d_miny
    real(kind=sidl_double) :: d_maxx
    real(kind=sidl_double) :: d_maxy

    real(kind=sidl_double) :: d_spacing
    type(sidl_double_2d) :: d_density
    ! DO-NOT-DELETE splicer.end(f90.ScalarField.private_data)
  end type f90_ScalarField_priv

  type f90_ScalarField_wrap
    sequence
    type(f90_ScalarField_priv), pointer :: d_private_data
  end type f90_ScalarField_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use f90_ScalarField
    use sidl_BaseInterface
    implicit none
    ! out f90_ScalarField retval
    type(f90_ScalarField_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in f90_ScalarField_wrap private_data
    type(f90_ScalarField_wrap), intent(in) :: private_data
    external f90_ScalarField_wrapObj_m
    call f90_ScalarField_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module f90_ScalarField_impl
