! 
! File:          f90_Environment_Impl.F90
! Symbol:        f90.Environment-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side implementation for f90.Environment
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "f90.Environment" (version 2.0)
! 


#include "sidl_NotImplementedException_fAbbrev.h"
#include "f90_Environment_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "conway_BoundsException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "conway_Environment_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)




! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine f90_Environment__ctor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Environment._ctor.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment._ctor)
  type(f90_Environment_wrap) :: pd
  allocate(pd%d_private_data)
  call set_null(pd%d_private_data%d_grid)
  call f90_Environment__set_data_m(self, pd)
! DO-NOT-DELETE splicer.end(f90.Environment._ctor)
end subroutine f90_Environment__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine f90_Environment__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment._ctor2.use)
  ! Insert-Code-Here {f90.Environment._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.Environment._ctor2.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  type(f90_Environment_wrap) :: private_data
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment._ctor2)
! Insert-Code-Here {f90.Environment._ctor2} (_ctor2 method)
! 
! This method has not been implemented
! 

  type(sidl_BaseInterface_t) :: throwaway
  type(sidl_NotImplementedException_t) :: notImpl
  call new(notImpl, exception)
  call setNote(notImpl, 'Not Implemented', exception)
  call cast(notImpl, exception,throwaway)
  call deleteRef(notImpl,throwaway)
  return
! DO-NOT-DELETE splicer.end(f90.Environment._ctor2)
end subroutine f90_Environment__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine f90_Environment__dtor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Environment._dtor.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment._dtor)
  type(f90_Environment_wrap) :: pd
  call f90_Environment__get_data_m(self, pd)
  if (not_null(pd%d_private_data%d_grid)) then
     call deleteRef(pd%d_private_data%d_grid)
     call set_null(pd%d_private_data%d_grid)
  endif
  deallocate(pd%d_private_data)
! DO-NOT-DELETE splicer.end(f90.Environment._dtor)
end subroutine f90_Environment__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine f90_Environment__load_mi(exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment._load.use)
  ! Insert-Code-Here {f90.Environment._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.Environment._load.use)
  implicit none
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment._load)
! Insert-Code-Here {f90.Environment._load} (_load method)
! DO-NOT-DELETE splicer.end(f90.Environment._load)
end subroutine f90_Environment__load_mi


! 
! Method:  init[]
! Create a grid of a certain height and width
! 

recursive subroutine f90_Environment_init_mi(self, height, width, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment.init.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Environment.init.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! in
  integer (kind=sidl_int) :: height
  ! in
  integer (kind=sidl_int) :: width
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment.init)
  integer (kind=sidl_int) :: low(2), high(2), x, y
  type(f90_Environment_wrap) :: pd
  call f90_Environment__get_data_m(self, pd)

  ! remove previous grid (if any)
  if (not_null(pd%d_private_data%d_grid)) then
     call deleteRef(pd%d_private_data%d_grid)
     call set_null(pd%d_private_data%d_grid)
  endif
  low(1) = 0
  low(2) = 0
  high(1) = height-1
  high(2) = width-1
  call createCol(low,high,pd%d_private_data%d_grid)
  do x= low(1), high(1)
    do y=low(2), high(2)
      !call set( self%grid, high(1)+low(1)-x, high(2)+low(2)-y, 0)
      pd%d_private_data%d_grid%d_data(x,y)=0
    enddo
  enddo
! DO-NOT-DELETE splicer.end(f90.Environment.init)
end subroutine f90_Environment_init_mi


! 
! Method:  isAlive[]
! Returns true iff that cell is alive
! 

recursive subroutine f90_Environment_isAlive_mi(self, x, y, retval, exception)
  use sidl
  use sidl_NotImplementedException
  use conway_BoundsException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment.isAlive.use)
  use f90_BoundsException
  ! DO-NOT-DELETE splicer.end(f90.Environment.isAlive.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! in
  integer (kind=sidl_int) :: x
  ! in
  integer (kind=sidl_int) :: y
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment.isAlive)
  integer (kind=sidl_int) :: low(2), high(2)
  type(f90_Environment_wrap) :: pd
  type(f90_BoundsException_t) :: bounds_exception
  character (len=*) myfilename 
  parameter(myfilename='f90_Environment_Impl.F90')

  call f90_Environment__get_data_m(self, pd)

  ! check for grid existence 
  if (is_null(pd%d_private_data%d_grid)) then
    print *, 'no grid to check if alive'
    call new(bounds_exception, exception)
    if (not_null(bounds_exception)) then
      call setNote(bounds_exception, 'no grid to check if alive', exception)
      call add(bounds_exception, myfilename,161,&
               'f90_Environment_isAlive_impl', exception)
      call cast(bounds_exception,exception,exception)
      call deleteRef(bounds_exception,exception)
      return
    endif
  endif

  ! check bounds
  low(1)=lower(pd%d_private_data%d_grid,0)
  low(2)=lower(pd%d_private_data%d_grid,1)
  high(1)=upper(pd%d_private_data%d_grid,0)
  high(2)=upper(pd%d_private_data%d_grid,1)
  if ( (low(1) .le. x) .and. (high(1) .ge. x) .and. & 
       (low(2) .le. y) .and. (high(2) .ge. y) ) then
    retval = ( pd%d_private_data%d_grid%d_data(x,y) .ne. 0 )
    return
  else
    ! throw Bounds exception
    call new(bounds_exception,exception)
    if (not_null(bounds_exception)) then
      call setNote(bounds_exception, 'selected x,y index out of range',exception)
      call add(bounds_exception, myfilename,161,&
               'f90_Environment_isAlive_impl',exception)
      call cast(bounds_exception,exception,exception)
      call deleteRef(bounds_exception,exception)
      print *, 'bounds problem at ', x, ',', y
      print *, 'bounds are:'
      print *, '    low(1)=', low(1)
      print *, '    low(2)=', low(2)
      print *, '    high(1)=', high(1)
      print *, '    high(2)=', high(2)
      
      return
    endif
  endif

! DO-NOT-DELETE splicer.end(f90.Environment.isAlive)
end subroutine f90_Environment_isAlive_mi


! 
! Method:  nNeighbors[]
! Return the number of living adjacent cells
! 

recursive subroutine f90_Environment_nNeighbors_mi(self, x, y, retval,         &
  exception)
  use sidl
  use sidl_NotImplementedException
  use conway_BoundsException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment.nNeighbors.use)
  use f90_BoundsException
  ! DO-NOT-DELETE splicer.end(f90.Environment.nNeighbors.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! in
  integer (kind=sidl_int) :: x
  ! in
  integer (kind=sidl_int) :: y
  ! out
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment.nNeighbors)
  integer (kind=sidl_int) :: low(2), high(2)
  type(f90_Environment_wrap) :: pd
  type(f90_BoundsException_t) :: bounds_exception
  character (len=*) myfilename 
  parameter(myfilename='f90_Environment_Impl.F90')

  call f90_Environment__get_data_m(self, pd)

  ! check for grid existence 
  if (is_null(pd%d_private_data%d_grid)) then
    call new(bounds_exception,exception)
    if (not_null(bounds_exception)) then
      call setNote(bounds_exception, 'no grid to check if alive',exception)
      call add(bounds_exception, myfilename,161,&
               'f90_Environment_isAlive_impl',exception)
      call cast(bounds_exception,exception,exception)
      call deleteRef(bounds_exception,exception)
      return
    endif
  endif

  ! check bounds
  low(1)=lower(pd%d_private_data%d_grid,0)
  low(2)=lower(pd%d_private_data%d_grid,1)
  high(1)=upper(pd%d_private_data%d_grid,0)
  high(2)=upper(pd%d_private_data%d_grid,1)
  if ( (low(1) .le. x) .and. (high(1) .ge. x) .and. & 
       (low(2) .le. y) .and. (high(2) .ge. y) ) then
    retval = 0
    if ( (low(1) .le. x-1) .and. (high(1) .ge. x-1) .and. & 
         (low(2) .le. y-1) .and. (high(2) .ge. y-1) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x-1,y-1)
    endif
    if ( (low(1) .le. x-1) .and. (high(1) .ge. x-1) .and. & 
         (low(2) .le. y  ) .and. (high(2) .ge. y  ) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x-1,y)
    endif
    if ( (low(1) .le. x-1) .and. (high(1) .ge. x-1) .and. & 
         (low(2) .le. y+1) .and. (high(2) .ge. y+1) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x-1,y+1)
    endif
    if ( (low(1) .le. x  ) .and. (high(1) .ge. x  ) .and. & 
         (low(2) .le. y-1) .and. (high(2) .ge. y-1) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x,y-1)
    endif
    if ( (low(1) .le. x  ) .and. (high(1) .ge. x  ) .and. & 
         (low(2) .le. y+1) .and. (high(2) .ge. y+1) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x,y+1)
    endif
    if ( (low(1) .le. x+1) .and. (high(1) .ge. x+1) .and. & 
         (low(2) .le. y-1) .and. (high(2) .ge. y-1) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x+1,y-1)
    endif
    if ( (low(1) .le. x+1) .and. (high(1) .ge. x+1) .and. & 
         (low(2) .le. y  ) .and. (high(2) .ge. y  ) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x+1,y)
    endif
    if ( (low(1) .le. x+1) .and. (high(1) .ge. x+1) .and. & 
         (low(2) .le. y+1) .and. (high(2) .ge. y+1) ) then
      retval = retval+pd%d_private_data%d_grid%d_data(x+1,y+1)
    endif
    return
  else
    ! throw Bounds exception
    call new(bounds_exception,exception)
    if (not_null(bounds_exception)) then
      call setNote(bounds_exception, 'selected x,y index out of range',exception)
      call add(bounds_exception, myfilename,161,&
               'f90_Environment_isAlive_impl',exception)
      call cast(bounds_exception,exception,exception)
      call deleteRef(bounds_exception,exception)
      return
    endif
  endif
! DO-NOT-DELETE splicer.end(f90.Environment.nNeighbors)
end subroutine f90_Environment_nNeighbors_mi


! 
! Method:  getGrid[]
! Return the entire grid of data
! 

recursive subroutine f90_Environment_getGrid_mi(self, retval, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use sidl_int_array
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment.getGrid.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Environment.getGrid.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! out
  type(sidl_int_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment.getGrid)
  type(f90_Environment_wrap) :: pd
  call f90_Environment__get_data_m(self, pd)
  retval = pd%d_private_data%d_grid
  call addRef(retval)
  return
! DO-NOT-DELETE splicer.end(f90.Environment.getGrid)
end subroutine f90_Environment_getGrid_mi


! 
! Method:  setGrid[]
! Set an entire grid of data (may change height and width)
! 

recursive subroutine f90_Environment_setGrid_mi(self, grid, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Environment
  use sidl_int_array
  use f90_Environment_impl
  ! DO-NOT-DELETE splicer.begin(f90.Environment.setGrid.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Environment.setGrid.use)
  implicit none
  ! in
  type(f90_Environment_t) :: self
  ! in
  type(sidl_int_2d) :: grid
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Environment.setGrid)
  type(f90_Environment_wrap) :: pd
  call f90_Environment__get_data_m(self, pd)
  call copy(grid, pd%d_private_data%d_grid)
! DO-NOT-DELETE splicer.end(f90.Environment.setGrid)
end subroutine f90_Environment_setGrid_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
