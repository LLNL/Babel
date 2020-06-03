! 
! File:          f90_WavePropagator_Impl.F90
! Symbol:        f90.WavePropagator-v1.0
! Symbol Type:   class
! Babel Version: 1.2.0
! Description:   Server-side implementation for f90.WavePropagator
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "f90.WavePropagator" (version 1.0)
! 


#include "sidl_NotImplementedException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "f90_WavePropagator_fAbbrev.h"
#include "wave2d_ScalarField_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "wave2d_WavePropagator_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_double_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
recursive subroutine print_array( name, a )
  use sidl_double_array

  implicit none
  character(len=4) :: name
  type(sidl_double_2d) :: a
  integer(kind=sidl_int) :: min_i, max_i, min_j, max_j, i, j
 100   format (F7.3)
  min_i = lower(a,0)
  min_j = lower(a,1)
  max_i = upper(a,0)
  max_j = upper(a,1)
  print *, name, '  (', min_i, ',', min_j,') to (',max_i, ',', max_j,')'
  do i=min_i, max_i
    do j=min_j, max_j
      write (unit=*,fmt='(f7.3)',advance='no') a%d_data(i,j)
    end do
    print *,''
  end do 
  print *,''
end subroutine print_array
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)




! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine f90_WavePropagator__ctor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_WavePropagator
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator._ctor.use)
  ! Insert-Code-Here {f90.WavePropagator._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator._ctor.use)
  implicit none
  type(f90_WavePropagator_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator._ctor)
  type(f90_WavePropagator_wrap) :: pd
  allocate(pd%d_private_data)
  call set_null( pd%d_private_data%p1 )
  call set_null( pd%d_private_data%p2 )
  call set_null( pd%d_private_data%p3 )
  call f90_WavePropagator__set_data_m(self,pd)
! DO-NOT-DELETE splicer.end(f90.WavePropagator._ctor)
end subroutine f90_WavePropagator__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine f90_WavePropagator__ctor2_mi(self, private_data,          &
  exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_WavePropagator
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator._ctor2.use)
  ! Insert-Code-Here {f90.WavePropagator._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator._ctor2.use)
  implicit none
  type(f90_WavePropagator_t) :: self
  ! in
  type(f90_WavePropagator_wrap) :: private_data
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator._ctor2)
! Insert-Code-Here {f90.WavePropagator._ctor2} (_ctor2 method)
! 
! This method has not been implemented
! 

  ! DO-DELETE-WHEN-IMPLEMENTING exception.begin(f90.WavePropagator._ctor2)
  type(sidl_BaseInterface_t) :: throwaway
  type(sidl_NotImplementedException_t) :: notImpl
  call new(notImpl, exception)
  call setNote(notImpl, 'Not Implemented', exception)
  call cast(notImpl, exception,throwaway)
  call deleteRef(notImpl,throwaway)
  return
  ! DO-DELETE-WHEN-IMPLEMENTING exception.end(f90.WavePropagator._ctor2)
! DO-NOT-DELETE splicer.end(f90.WavePropagator._ctor2)
end subroutine f90_WavePropagator__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine f90_WavePropagator__dtor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_WavePropagator
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator._dtor.use)
  ! Insert-Code-Here {f90.WavePropagator._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator._dtor.use)
  implicit none
  type(f90_WavePropagator_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator._dtor)
  type(f90_WavePropagator_wrap) :: pd
  call f90_WavePropagator__get_data_m(self,pd)
  if ( .not. is_null( pd%d_private_data%p1 ) ) then
    call deleteRef( pd%d_private_data%p1 )
  endif 
  if ( .not. is_null( pd%d_private_data%p2 ) ) then
    call deleteRef( pd%d_private_data%p2 )
  endif 
  if ( .not. is_null( pd%d_private_data%p3 ) ) then
    call deleteRef( pd%d_private_data%p3 )
  endif 
  deallocate(pd%d_private_data)
! DO-NOT-DELETE splicer.end(f90.WavePropagator._dtor)
end subroutine f90_WavePropagator__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine f90_WavePropagator__load_mi(exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_WavePropagator
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator._load.use)
  ! Insert-Code-Here {f90.WavePropagator._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator._load)
! DO-NOT-DELETE splicer.end(f90.WavePropagator._load)
end subroutine f90_WavePropagator__load_mi


! 
! Method:  init[]
! 

recursive subroutine f90_WavePropagator_init_mi(self, density, pressure,       &
  exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use wave2d_ScalarField
  use f90_WavePropagator
  use sidl_double_array
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator.init.use)
  ! Insert-Code-Here {f90.WavePropagator.init.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator.init.use)
  implicit none
  type(f90_WavePropagator_t) :: self
  ! in
  type(wave2d_ScalarField_t) :: density
  ! in
  type(sidl_double_2d) :: pressure
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator.init)
  type(f90_WavePropagator_wrap) :: pd
  type(sidl_double_2d) :: d

  call f90_WavePropagator__get_data_m(self,pd)
  call getData( density, d, exception )

  if (.not. ( is_null(d) .or. is_null( pressure ) ) ) then
    if ( ( dimen(d) .eq. dimen(pressure) ) .and. &
         ( length(d,0) .eq. length(pressure,0) ) .and. &
         ( length(d,1) .eq. length(pressure,1) ) ) then 
      pd%d_private_data%density = density
      pd%d_private_data%lower(1) = lower(d,0)-1
      pd%d_private_data%lower(2) = lower(d,1)-1
      pd%d_private_data%upper(1) = upper(d,0)+1
      pd%d_private_data%upper(2) = upper(d,1)+1
      call createRow(pd%d_private_data%lower, &
                     pd%d_private_data%upper, &
                     pd%d_private_data%p1)
      call createRow(pd%d_private_data%lower, &
                     pd%d_private_data%upper, &
                     pd%d_private_data%p2)
      call createRow(pd%d_private_data%lower, &
                     pd%d_private_data%upper, &
                     pd%d_private_data%p3)
      pd%d_private_data%p1%d_data = 0.0
      pd%d_private_data%p2%d_data = 0.0
      pd%d_private_data%p3%d_data = 0.0
      call copy( pressure, pd%d_private_data%p2 ) 
      ! call print_array( 'p1_0', pd%d_private_data%p1)
      ! call print_array( 'p2_0', pd%d_private_data%p2)
      ! call print_array( 'p3_0', pd%d_private_data%p3)
    endif
  endif 
! DO-NOT-DELETE splicer.end(f90.WavePropagator.init)
end subroutine f90_WavePropagator_init_mi


! 
! Method:  step[]
! 

recursive subroutine f90_WavePropagator_step_mi(self, n, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_WavePropagator
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator.step.use)
  ! Insert-Code-Here {f90.WavePropagator.step.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator.step.use)
  implicit none
  type(f90_WavePropagator_t) :: self
  ! in
  integer (kind=sidl_int) :: n
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator.step)
  type(f90_WavePropagator_wrap) :: pd
  type(sidl_double_2d) :: d 
  type(sidl_double_2d) :: swap 
  integer (kind=sidl_int) :: t, i, j
  integer (kind=sidl_int) :: min_i, max_i, min_j, max_j

  call f90_WavePropagator__get_data_m(self,pd)

  call getData( pd%d_private_data%density, d, exception )
  min_i = lower(pd%d_private_data%p2,0)+1
  min_j = lower(pd%d_private_data%p2,1)+1
  max_i = upper(pd%d_private_data%p2,0)-1
  max_j = upper(pd%d_private_data%p2,1)-1
  
  do t=1,n
    pd%d_private_data%p3%d_data(min_i:max_i,min_j:max_j) & 
      = (2-4*d%d_data(min_i:max_i,min_j:max_j)) & 
      * pd%d_private_data%p2%d_data(min_i:max_i,min_j:max_j)& 
      + d%d_data(min_i:max_i,min_j:max_j) &
      * ( pd%d_private_data%p2%d_data(min_i-1:max_i-1,min_j:max_j) &
          + pd%d_private_data%p2%d_data(min_i+1:max_i+1,min_j:max_j) &
          + pd%d_private_data%p2%d_data(min_i:max_i,min_j-1:max_j-1) &
          + pd%d_private_data%p2%d_data(min_i:max_i,min_j+1:max_j+1) ) & 
      - pd%d_private_data%p1%d_data(min_i:max_i,min_j:max_j) 

    !! Element-wise could do this way.
    ! do i=min_i, max_i
    !   do j=min_j, max_j
    !     pd%d_private_data%p3%d_data(i,j) & 
    !       = (2-4*d%d_data(i,j) ) & 
    !       * pd%d_private_data%p2%d_data(i,j) & 
    !       + d%d_data(i,j) &
    !       * ( pd%d_private_data%p2%d_data(i-1,j) & 
    !           + pd%d_private_data%p2%d_data(i+1,j) &
    !           + pd%d_private_data%p2%d_data(i,j-1) &
    !           + pd%d_private_data%p2%d_data(i,j+1) ) & 
    !       - pd%d_private_data%p1%d_data(i,j) 
    !   
    !   end do 
    ! end do  

    !! Uncomment for help debugging
    ! print *, 'Time = ', t
    ! call print_array( 'p1_0', pd%d_private_data%p1)
    ! call print_array( 'p2_0', pd%d_private_data%p2)
    ! call print_array( 'p3_0', pd%d_private_data%p3)

    swap = pd%d_private_data%p1
    pd%d_private_data%p1 = pd%d_private_data%p2
    pd%d_private_data%p2 = pd%d_private_data%p3
    pd%d_private_data%p3 = swap
  end do 
! DO-NOT-DELETE splicer.end(f90.WavePropagator.step)
end subroutine f90_WavePropagator_step_mi


! 
! Method:  getPressure[]
! 

recursive subroutine WavePr_getPressure5lcx7btctg_mi(self, retval, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_WavePropagator
  use sidl_double_array
  use f90_WavePropagator_impl
  ! DO-NOT-DELETE splicer.begin(f90.WavePropagator.getPressure.use)
  ! Insert-Code-Here {f90.WavePropagator.getPressure.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.WavePropagator.getPressure.use)
  implicit none
  type(f90_WavePropagator_t) :: self
  ! in
  type(sidl_double_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(f90.WavePropagator.getPressure)
  type(f90_WavePropagator_wrap) :: pd
  integer(kind=sidl_int) :: nElem(2), start(2), stride_(2), lower_(2)
  call f90_WavePropagator__get_data_m(self,pd)
  nElem(1) = length( pd%d_private_data%p1, 0)-2
  nElem(2) = length( pd%d_private_data%p1, 1)-2
  start(1) = lower( pd%d_private_data%p1, 0)+1
  start(2) = lower( pd%d_private_data%p1, 1)+1
  stride_(1) = 1
  stride_(2) = 1
  lower_(1) = 0
  lower_(2) = 0
  call slice( pd%d_private_data%p2, nElem, start, stride_, & 
      lower_, retval)
! DO-NOT-DELETE splicer.end(f90.WavePropagator.getPressure)
end subroutine WavePr_getPressure5lcx7btctg_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
