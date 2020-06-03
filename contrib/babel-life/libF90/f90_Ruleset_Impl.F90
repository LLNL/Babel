! 
! File:          f90_Ruleset_Impl.F90
! Symbol:        f90.Ruleset-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side implementation for f90.Ruleset
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "f90.Ruleset" (version 2.0)
! 


#include "sidl_NotImplementedException_fAbbrev.h"
#include "f90_Ruleset_fAbbrev.h"
#include "conway_Ruleset_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "conway_BoundsException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "conway_Environment_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)




! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine f90_Ruleset__ctor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Ruleset
  use f90_Ruleset_impl
  ! DO-NOT-DELETE splicer.begin(f90.Ruleset._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Ruleset._ctor.use)
  implicit none
  ! in
  type(f90_Ruleset_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Ruleset._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(f90.Ruleset._ctor)
end subroutine f90_Ruleset__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine f90_Ruleset__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Ruleset
  use f90_Ruleset_impl
  ! DO-NOT-DELETE splicer.begin(f90.Ruleset._ctor2.use)
  ! Insert-Code-Here {f90.Ruleset._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.Ruleset._ctor2.use)
  implicit none
  ! in
  type(f90_Ruleset_t) :: self
  type(f90_Ruleset_wrap) :: private_data
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Ruleset._ctor2)
! Insert-Code-Here {f90.Ruleset._ctor2} (_ctor2 method)
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
! DO-NOT-DELETE splicer.end(f90.Ruleset._ctor2)
end subroutine f90_Ruleset__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine f90_Ruleset__dtor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Ruleset
  use f90_Ruleset_impl
  ! DO-NOT-DELETE splicer.begin(f90.Ruleset._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.Ruleset._dtor.use)
  implicit none
  ! in
  type(f90_Ruleset_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Ruleset._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(f90.Ruleset._dtor)
end subroutine f90_Ruleset__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine f90_Ruleset__load_mi(exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Ruleset
  use f90_Ruleset_impl
  ! DO-NOT-DELETE splicer.begin(f90.Ruleset._load.use)
  ! Insert-Code-Here {f90.Ruleset._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.Ruleset._load.use)
  implicit none
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Ruleset._load)
! Insert-Code-Here {f90.Ruleset._load} (_load method)
! DO-NOT-DELETE splicer.end(f90.Ruleset._load)
end subroutine f90_Ruleset__load_mi


! 
! Method:  setAlive[]
! Birth: an empty cell has 3 living neighbors
! Death: a living cell has 0 or 1 neighbors (loneliness)
! or a living cell has 4-8 neighbors (overcrowding)
! Life: a living cell has 2 or three neighbors
! 

recursive subroutine f90_Ruleset_setAlive_mi(self, x, y, env, retval,          &
  exception)
  use sidl
  use sidl_NotImplementedException
  use conway_BoundsException
  use conway_Environment
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_Ruleset
  use f90_Ruleset_impl
  ! DO-NOT-DELETE splicer.begin(f90.Ruleset.setAlive.use)
  use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(f90.Ruleset.setAlive.use)
  implicit none
  ! in
  type(f90_Ruleset_t) :: self
  ! in
  integer (kind=sidl_int) :: x
  ! in
  integer (kind=sidl_int) :: y
  ! in
  type(conway_Environment_t) :: env
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.Ruleset.setAlive)
  type(conway_BoundsException_t) :: bounds_exception
  type(sidl_BaseInterface_t) :: exception2
  integer (kind=sidl_int) :: n
  logical :: i
  character (len=*) myfilename
  parameter (myfilename='f90_Ruleset_Impl.F90')

  call nNeighbors(env, x, y, n, exception)
  if (not_null(exception)) then
    ! add to exception's stack and return
    call cast(exception, bounds_exception,exception2)
    call add(bounds_exception, myfilename, 109,&
             'f90_Ruleset_setAlive_impl',exception2)
    call deleteRef(bounds_exception,exception2)
    return
  endif
  if ( (n .eq. 0) .or. (n .eq. 1)) then
    retval = .false.
    return
  else if ( n .eq. 2 ) then 
    call isAlive(env, x, y, retval, exception) 
    if ( not_null(exception) ) then
      ! add to exception's stack and return
      call cast(exception, bounds_exception,exception2)
      call add(bounds_exception, myfilename, 122,&
               'f90_Ruleset_setAlive_impl',exception2)
      call deleteRef(bounds_exception,exception2)
      return
    endif
    return
  else if ( n .eq. 3) then
    retval = .true.
    return
  else
    retval = .false.
    return
  endif
! DO-NOT-DELETE splicer.end(f90.Ruleset.setAlive)
end subroutine f90_Ruleset_setAlive_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
