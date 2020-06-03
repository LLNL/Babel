! 
! File:          f90_ScalarField_Impl.F90
! Symbol:        f90.ScalarField-v1.0
! Symbol Type:   class
! Babel Version: 1.2.0
! Description:   Server-side implementation for f90.ScalarField
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "f90.ScalarField" (version 1.0)
! 


#include "sidl_NotImplementedException_fAbbrev.h"
#include "f90_ScalarField_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "wave2d_ScalarField_fAbbrev.h"
#include "wave2d_Shape_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_double_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)




! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine f90_ScalarField__ctor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField._ctor.use)
  ! Insert-Code-Here {f90.ScalarField._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField._ctor.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField._ctor)
  type( f90_ScalarField_wrap ) :: pd
  allocate(pd%d_private_data)
  pd%d_private_data%d_minx = 0.0
  pd%d_private_data%d_miny = 0.0
  pd%d_private_data%d_maxx = 0.0
  pd%d_private_data%d_maxy = 0.0
  pd%d_private_data%d_spacing = 0.0

  call set_null( pd%d_private_data%d_density)
  call f90_ScalarField__set_data_m(self,pd)
! DO-NOT-DELETE splicer.end(f90.ScalarField._ctor)
end subroutine f90_ScalarField__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine f90_ScalarField__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField._ctor2.use)
  ! Insert-Code-Here {f90.ScalarField._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField._ctor2.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  type(f90_ScalarField_wrap) :: private_data
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField._ctor2)
! Insert-Code-Here {f90.ScalarField._ctor2} (_ctor2 method)
! 
! This method has not been implemented
! 

  ! DO-DELETE-WHEN-IMPLEMENTING exception.begin(f90.ScalarField._ctor2)
  type(sidl_BaseInterface_t) :: throwaway
  type(sidl_NotImplementedException_t) :: notImpl
  call new(notImpl, exception)
  call setNote(notImpl, 'Not Implemented', exception)
  call cast(notImpl, exception,throwaway)
  call deleteRef(notImpl,throwaway)
  return
  ! DO-DELETE-WHEN-IMPLEMENTING exception.end(f90.ScalarField._ctor2)
! DO-NOT-DELETE splicer.end(f90.ScalarField._ctor2)
end subroutine f90_ScalarField__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine f90_ScalarField__dtor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField._dtor.use)
  ! Insert-Code-Here {f90.ScalarField._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField._dtor.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField._dtor)
  type( f90_ScalarField_wrap ) :: pd
  call f90_ScalarField__get_data_m(self,pd)
  if ( .not. is_null( pd%d_private_data%d_density ) ) then 
    call deleteRef(pd%d_private_data%d_density)
  endif 
  deallocate(pd%d_private_data)
! DO-NOT-DELETE splicer.end(f90.ScalarField._dtor)
end subroutine f90_ScalarField__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine f90_ScalarField__load_mi(exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField._load.use)
  ! Insert-Code-Here {f90.ScalarField._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField._load)
! DO-NOT-DELETE splicer.end(f90.ScalarField._load)
end subroutine f90_ScalarField__load_mi


! 
! Method:  init[]
! 

recursive subroutine f90_ScalarField_init_mi(self, minX, minY, maxX, maxY,     &
  spacing, value, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField.init.use)
  use sidl_double_array
  ! DO-NOT-DELETE splicer.end(f90.ScalarField.init.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  real (kind=sidl_double) :: minX
  ! in
  real (kind=sidl_double) :: minY
  ! in
  real (kind=sidl_double) :: maxX
  ! in
  real (kind=sidl_double) :: maxY
  ! in
  real (kind=sidl_double) :: spacing
  ! in
  real (kind=sidl_double) :: value
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField.init)
  integer (kind=sidl_int) :: nrows, ncols
  ! integer (kind=sidl_int) :: low_x, up_x, low_y, up_y
  ! integer (kind=sidl_int) :: i, j 
  type( f90_ScalarField_wrap ) :: pd

  call f90_ScalarField__get_data_m(self,pd)
  if ( .not. is_null( pd%d_private_data%d_density ) ) then 
    call deleteRef(pd%d_private_data%d_density)
  endif 

  pd%d_private_data%d_minx = minX
  pd%d_private_data%d_miny = minY
  pd%d_private_data%d_maxx = maxX
  pd%d_private_data%d_maxy = maxY
  nrows = ceiling( (maxX-minX)/spacing )
  ncols = ceiling( (maxY-minY)/spacing )

  call create2dRow(nrows, ncols, pd%d_private_data%d_density)
  pd%d_private_data%d_density%d_data = value

  ! low_x = lower( pd%d_private_data%d_density, 0 )
  ! low_y = lower( pd%d_private_data%d_density, 1 )
  ! up_x = upper( pd%d_private_data%d_density, 0 )
  ! up_y = upper( pd%d_private_data%d_density, 1 )
  ! do i=low_x, up_x
  !   do j=low_y, up_y
  !     pd%d_private_data%d_density%d_data(i,j) = value
  !   enddo 
  ! enddo

! DO-NOT-DELETE splicer.end(f90.ScalarField.init)
end subroutine f90_ScalarField_init_mi


! 
! Method:  getBounds[]
! 

recursive subroutine f90_ScalarField_getBounds_mi(self, minX, minY, maxX,      &
  maxY, spacing, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField.getBounds.use)
  ! Insert-Code-Here {f90.ScalarField.getBounds.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField.getBounds.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  real (kind=sidl_double) :: minX
  ! out
  real (kind=sidl_double) :: minY
  ! out
  real (kind=sidl_double) :: maxX
  ! out
  real (kind=sidl_double) :: maxY
  ! out
  real (kind=sidl_double) :: spacing
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField.getBounds)
  type( f90_ScalarField_wrap ) :: pd
  call f90_ScalarField__get_data_m(self,pd)
  minX = pd%d_private_data%d_minx
  minY = pd%d_private_data%d_miny
  maxX = pd%d_private_data%d_maxx
  maxY = pd%d_private_data%d_maxy
  spacing = pd%d_private_data%d_spacing

! DO-NOT-DELETE splicer.end(f90.ScalarField.getBounds)
end subroutine f90_ScalarField_getBounds_mi


! 
! Method:  getData[]
! 

recursive subroutine f90_ScalarField_getData_mi(self, retval, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use sidl_double_array
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField.getData.use)
  ! Insert-Code-Here {f90.ScalarField.getData.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField.getData.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  type(sidl_double_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField.getData)
  type( f90_ScalarField_wrap ) :: pd
  call f90_ScalarField__get_data_m(self,pd)
  retval = pd%d_private_data%d_density
  call addRef( retval )
! DO-NOT-DELETE splicer.end(f90.ScalarField.getData)
end subroutine f90_ScalarField_getData_mi


! 
! Method:  render[]
! 

recursive subroutine f90_ScalarField_render_mi(self, shape, value, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wave2d_Shape
  use f90_ScalarField
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField.render.use)
  ! Insert-Code-Here {f90.ScalarField.render.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField.render.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  type(wave2d_Shape_t) :: shape
  ! in
  real (kind=sidl_double) :: value
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField.render)
  type( f90_ScalarField_wrap ) :: pd
  integer (kind=sidl_int) :: low_x, up_x, low_y, up_y
  integer (kind=sidl_int) :: i, j      
  real (kind=sidl_double) :: x, y
  logical :: inside

  low_x = lower( pd%d_private_data%d_density, 0 )
  low_y = lower( pd%d_private_data%d_density, 1 )
  up_x = upper( pd%d_private_data%d_density, 0 )
  up_y = upper( pd%d_private_data%d_density, 1 )
  x = pd%d_private_data%d_minx
  do i=low_x, up_x
    y = pd%d_private_data%d_miny
    do j=low_y, up_y
      call inLocus( shape, x, y, inside, exception )
      if ( inside .eqv. .true.) then
        pd%d_private_data%d_density%d_data(i,j) = value
      endif
    enddo 
  enddo


! DO-NOT-DELETE splicer.end(f90.ScalarField.render)
end subroutine f90_ScalarField_render_mi


! 
! Method:  setData[]
! 

recursive subroutine f90_ScalarField_setData_mi(self, data, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_ScalarField
  use sidl_double_array
  use f90_ScalarField_impl
  ! DO-NOT-DELETE splicer.begin(f90.ScalarField.setData.use)
  ! Insert-Code-Here {f90.ScalarField.setData.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.ScalarField.setData.use)
  implicit none
  type(f90_ScalarField_t) :: self
  ! in
  type(sidl_double_2d) :: data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.ScalarField.setData)
  type( f90_ScalarField_wrap ) :: pd
  call f90_ScalarField__get_data_m(self,pd)
  if ( .not. is_null( pd%d_private_data%d_density ) ) then 
    call deleteRef(pd%d_private_data%d_density)
  endif
  call smartCopy(data, pd%d_private_data%d_density)
! DO-NOT-DELETE splicer.end(f90.ScalarField.setData)
end subroutine f90_ScalarField_setData_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
