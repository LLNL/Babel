! 
! File:          f90_TimeStepper_Impl.F90
! Symbol:        f90.TimeStepper-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side implementation for f90.TimeStepper
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "f90.TimeStepper" (version 2.0)
! 


#include "sidl_NotImplementedException_fAbbrev.h"
#include "f90_TimeStepper_fAbbrev.h"
#include "conway_Ruleset_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "conway_TimeStepper_fAbbrev.h"
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

recursive subroutine f90_TimeStepper__ctor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper._ctor.use)
  implicit none
  ! in
  type(f90_TimeStepper_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper._ctor)
  type(f90_TimeStepper_wrap) :: pd 
  allocate(pd%d_private_data)
  call set_null(pd%d_private_data%d_env)
  call set_null(pd%d_private_data%d_rules)
  call set_null(pd%d_private_data%d_next)
  pd%d_private_data%d_step = 0
  call f90_TimeStepper__set_data_m(self, pd)
! DO-NOT-DELETE splicer.end(f90.TimeStepper._ctor)
end subroutine f90_TimeStepper__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine f90_TimeStepper__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper._ctor2.use)
  ! Insert-Code-Here {f90.TimeStepper._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper._ctor2.use)
  implicit none
  ! in
  type(f90_TimeStepper_t) :: self
  type(f90_TimeStepper_wrap) :: private_data
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper._ctor2)
! Insert-Code-Here {f90.TimeStepper._ctor2} (_ctor2 method)
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
! DO-NOT-DELETE splicer.end(f90.TimeStepper._ctor2)
end subroutine f90_TimeStepper__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine f90_TimeStepper__dtor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper._dtor.use)
  implicit none
  ! in
  type(f90_TimeStepper_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper._dtor)
  type(f90_TimeStepper_wrap) :: pd
  call f90_TimeStepper__get_data_m(self,pd)
  if (not_null(pd%d_private_data%d_env)) then
    call deleteRef(pd%d_private_data%d_env,exception)
    call set_null(pd%d_private_data%d_env)
  endif
  if (not_null(pd%d_private_data%d_rules)) then
    call deleteRef(pd%d_private_data%d_rules,exception)
    call set_null(pd%d_private_data%d_rules)
  endif
  if (not_null(pd%d_private_data%d_next)) then
    call deleteRef(pd%d_private_data%d_next)
    call set_null(pd%d_private_data%d_rules)
  endif
  deallocate(pd%d_private_data)
! DO-NOT-DELETE splicer.end(f90.TimeStepper._dtor)
end subroutine f90_TimeStepper__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine f90_TimeStepper__load_mi(exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper._load.use)
  ! Insert-Code-Here {f90.TimeStepper._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper._load.use)
  implicit none
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper._load)
! Insert-Code-Here {f90.TimeStepper._load} (_load method)
! DO-NOT-DELETE splicer.end(f90.TimeStepper._load)
end subroutine f90_TimeStepper__load_mi


! 
! Method:  init[]
! 

recursive subroutine f90_TimeStepper_init_mi(self, env, rules, exception)
  use sidl
  use sidl_NotImplementedException
  use conway_Environment
  use conway_Ruleset
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper.init.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper.init.use)
  implicit none
  ! in
  type(f90_TimeStepper_t) :: self
  ! in
  type(conway_Environment_t) :: env
  ! in
  type(conway_Ruleset_t) :: rules
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper.init)
  type(f90_TimeStepper_wrap) :: pd
  call f90_TimeStepper__get_data_m(self,pd)
  if ( not_null(env) ) then
    pd%d_private_data%d_env = env
    call addRef( pd%d_private_data%d_env,exception)
  endif
  if ( not_null(rules)) then
    pd%d_private_data%d_rules = rules
    call addRef( pd%d_private_data%d_rules,exception)
  endif
  pd%d_private_data%d_step = 0
! DO-NOT-DELETE splicer.end(f90.TimeStepper.init)
end subroutine f90_TimeStepper_init_mi


! 
! Method:  step[]
!  
! advance one more timestep 
! returns population count at new timestep
! 

recursive subroutine f90_TimeStepper_step_mi(self, retval, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper.step.use)
  use sidl_BaseException
  use conway_Ruleset
  use conway_Environment
  use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper.step.use)
  implicit none
  ! in
  type(f90_TimeStepper_t) :: self
  ! out
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper.step)
  type(f90_TimeStepper_wrap) :: pd 
  type(conway_Environment_t) :: env
  type(conway_Ruleset_t) :: rules
  type(sidl_int_2d) :: grid, next
  logical :: alive
  integer( kind=sidl_int) :: x, y
 
  integer ( kind=sidl_int) :: low(2), high(2)
  call f90_TimeStepper__get_data_m(self,pd)
  ! dereference some memeber data for convenience
  env = pd%d_private_data%d_env
  rules = pd%d_private_data%d_rules
  next = pd%d_private_data%d_next

  ! make sure things are initialized
  if (is_null(env) .or. is_null(rules)) then
    retval = -1
    return
  endif
 
  call getGrid(env, grid,exception)
  low(1)=lower(grid,0)
  low(2)=lower(grid,1)
  high(1)=upper(grid,0)
  high(2)=upper(grid,1)
  
  if ( is_null( next ) ) then
    call createCol(low,high,pd%d_private_data%d_next)
    next=pd%d_private_data%d_next
  endif

  retval=0
  do x = low(1),high(1)
    do y = low(2),high(2)
      call setAlive(rules, x, y, env, alive, exception ) 
      if ( not_null( exception ) ) then
        print *, 'Caught an exception that should not have happened'
        retval = -1
      endif
      if (alive) then
        next%d_data(x,y) = 1
        retval = retval + 1 
      else
        next%d_data(x,y) = 0
      endif
    enddo
  enddo
  call setGrid( env, next, exception )
  pd%d_private_data%d_step = pd%d_private_data%d_step + 1
  return
! DO-NOT-DELETE splicer.end(f90.TimeStepper.step)
end subroutine f90_TimeStepper_step_mi


! 
! Method:  nStepsTaken[]
!  check the number of steps taken 
! 

recursive subroutine f90_TimeStepper_nStepsTaken_mi(self, retval, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_TimeStepper
  use f90_TimeStepper_impl
  ! DO-NOT-DELETE splicer.begin(f90.TimeStepper.nStepsTaken.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.TimeStepper.nStepsTaken.use)
  implicit none
  ! in
  type(f90_TimeStepper_t) :: self
  ! out
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.TimeStepper.nStepsTaken)
  type(f90_TimeStepper_wrap) :: pd
  call f90_TimeStepper__get_data_m(self,pd)
  retval = pd%d_private_data%d_step
  return
! DO-NOT-DELETE splicer.end(f90.TimeStepper.nStepsTaken)
end subroutine f90_TimeStepper_nStepsTaken_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
