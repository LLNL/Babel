! File:          knapsack_gKnapsack_Impl.F90
! Symbol:        knapsack.gKnapsack-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 0)
! Description:   Server-side implementation for knapsack.gKnapsack
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "knapsack.gKnapsack" (version 1.0)
! 
! gKnapsack:  A good implementation of the knapsack interface.
! 


#include "knapsack_kExcept_fAbbrev.h"
#include "sidl_InvViolation_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "knapsack_kSizeExcept_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "knapsack_iKnapsack_fAbbrev.h"
#include "sidl_PostViolation_fAbbrev.h"
#include "knapsack_kBadWeightExcept_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "knapsack_gKnapsack_fAbbrev.h"
#include "sidl_PreViolation_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)

! 
! Method:  onlyPos
! Determine if all len entries in the native array are positive, returning 
! true if they are or false if they are not.
!
logical function onlyPos(w, len)
   use sidl
   use knapsack_gKnapsack_impl
 
   implicit none
   integer (kind=sidl_int) :: w(MAX_WEIGHTS), len
   !  in 
 
   integer (kind=sidl_int) :: i
 
   onlyPos = .false.
   if (len .gt. 0_sidl_int) then
      onlyPos = .true.
      do i = 0_sidl_int, len - 1_sidl_int
         if (w(i) .le. 0_sidl_int) then
            onlyPos = .false.
         endif
     enddo
   endif

   return
end function onlyPos

! 
! Method:  onlyPosSIDL
! Determine if all entries in the SIDL array are positive, returning true
! if they are or false if they are not.
!
logical function onlyPosSIDL(w)
   use sidl
   use sidl_int_array
 
   implicit none
   type(sidl_int_1d) :: w
   !  in 
 
   integer (kind=sidl_int) :: num, i, wi
 
   onlyPosSIDL = .false.
   if (.not. is_null(w)) then
      num = length(w, 0_sidl_int)
      if (num .gt. 0_sidl_int) then
         onlyPosSIDL = .true.
         i = 0_sidl_int
         do while ( (i .lt. num) .and. (onlyPosSIDL .eqv. .true.) )
            call get(w, lower(w, 0) + i, wi)
            if (wi .le. 0_sidl_int) then
               onlyPosSIDL = .false.
            endif
            i = i + 1_sidl_int
         enddo
       endif
   endif

   return
end function onlyPosSIDL

! 
! Method:  sameWeights
! Check to see if the two lists (SIDL arrays) match where order does 
! not matter.
!
logical function sameWeights(num, nW, sW)
   use sidl
   use sidl_int_array
   use knapsack_gKnapsack_impl
 
   implicit none
   integer (kind=sidl_int) :: num
   ! in
   integer (kind=sidl_int) :: nW(MAX_WEIGHTS)
   ! in
   type(sidl_int_1d)       :: sW
   ! in
 
   integer (kind=sidl_int) :: p(MAX_WEIGHTS) 
   integer (kind=sidl_int) :: i, j, sDim, sLen, sLower, ws
   logical                 :: found, onlyPos
 
   p = 0_sidl_int
   sameWeights = .false.
   if (num .gt. 0_sidl_int) then
      if (not_null(sW)) then
         sDim = dimen(sW)
         if (sDim .eq. 1_sidl_int) then
            sLen = length(sW, 0_sidl_int)
            if (num .eq. sLen) then
               sLower = lower(sW, 0_sidl_int)
               do i = 0_sidl_int, num - 1_sidl_int
                  call get(sW, sLower+i, ws)
                  found = .false.
                  j = 0_sidl_int
                  do while ( (j .lt. num) .and. (.not. found) )
                     if ( (ws .eq. nW(j)) .and. (p(j) .eq. 0_sidl_int) ) then
                        p(j) = 1_sidl_int
                        found = .true.
                     endif
                     j = j + 1_sidl_int
                  enddo
               enddo
               sameWeights = onlyPos(p)
            endif
         endif
      endif
   endif

   return
end function sameWeights

!
! Recursive implementation of the simplified knapsack problem.
!
! Based on the algorithm defined in "Data Structures and Algorithms" 
! by Aho, Hopcroft, and Ullman (c) 1983.
!
recursive function solve(w, t, i, n) result(r)
   use sidl
   use knapsack_gKnapsack_impl

   implicit none
   integer (kind=sidl_int) :: w(MAX_WEIGHTS)
   ! in
   integer (kind=sidl_int) :: t, i, n
   ! in
   logical                 :: r
   ! out

   r = .false.
   if (t .eq. 0_sidl_int) then
      r = .true.
   else if ( (t .lt. 0_sidl_int) .or. (i .ge. n) ) then
      r = .false.
   else 
      r = solve(w, t-w(i), i+1_sidl_int, n)
      if (.not. r) then
        r = solve(w, t, i+1_sidl_int, n)
      endif
   endif

   return
end function solve

! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine knapsack_gKnapsack__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor)
   type (knapsack_gKnapsack_wrap) :: dpd
   integer (kind=sidl_int)        :: i

   allocate(dpd%d_private_data)
   dpd%d_private_data%d_length = 0_sidl_int
   do i = 0_sidl_int, MAX_WEIGHTS
      dpd%d_private_data%d_weights(i) = 0_sidl_int
   enddo
   call knapsack_gKnapsack__set_data_m(self, dpd)

   return
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor)
end subroutine knapsack_gKnapsack__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine knapsack_gKnapsack__ctor2_mi(self, private_data,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor2.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor2.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  type(knapsack_gKnapsack_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor2)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor2)
end subroutine knapsack_gKnapsack__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine knapsack_gKnapsack__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._dtor.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._dtor.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._dtor)
   type (knapsack_gKnapsack_wrap) :: dpd

   call knapsack_gKnapsack__get_data_m(self, dpd)
   deallocate(dpd%d_private_data)

   return
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._dtor)
end subroutine knapsack_gKnapsack__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine knapsack_gKnapsack__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._load.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._load)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack._load)
end subroutine knapsack_gKnapsack__load_mi


! 
! Method:  initialize[]
! Initialize the knapsack with the specified weights, w.
! 

recursive subroutine gKnapsa_initializeaxl2wtiyqr_mi(self, w, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PostViolation
  use sidl_PreViolation
  use knapsack_kBadWeightExcept
  use knapsack_kSizeExcept
  use sidl_int_array
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.initialize.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.initialize.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  type(sidl_int_1d) :: w
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.initialize)
   type (knapsack_gKnapsack_wrap)     :: dpd
   type (knapsack_kBadWeightExcept_t) :: bwe
   type (knapsack_kSizeExcept_t)      :: bse
   type (knapsack_kExcept_t)          :: ke
   type (sidl_BaseInterface_t)        :: tae
   integer (kind=sidl_int)            :: i, num, val
   logical                            :: onlyPosSIDL

   call knapsack_gKnapsack__get_data_m(self, dpd)
   if (not_null(w)) then 
      num = length(w, 0)
      if (num .le. MAX_WEIGHTS) then
         if (onlyPosSIDL(w)) then
            do i = 0_sidl_int, num
               call get(w, i, val)
               dpd%d_private_data%d_weights(i) = val
            enddo
            dpd%d_private_data%d_length = num
            call knapsack_gKnapsack__set_data_m(self, dpd)
         else
            call new(bwe, tae)
            if (not_null(bwe)) then
               call setNote(bwe, l_pos_weights, tae)
               call cast(bwe, exception, tae)
               call deleteRef(bwe, tae)
               return
            endif
         endif
      else
         call new(bse, tae)
         if (not_null(bse)) then
            call setNote(bse, l_max_weights, tae)
            call cast(bse, exception, tae)
            call deleteRef(bse, tae)
            return
         endif
      endif
   endif

  return
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.initialize)
end subroutine gKnapsa_initializeaxl2wtiyqr_mi


! 
! Method:  onlyPosWeights[]
! Return TRUE if all weights in the knapsack are positive;
! otherwise, return FALSE.
! 

recursive subroutine gKn_onlyPosWeightsa5mqvv2_0x_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.onlyPosWeights.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.onlyPosWeights.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.onlyPosWeights)
   type (knapsack_gKnapsack_wrap) :: dpd
   type (knapsack_kExcept_t)      :: ke
   type (sidl_BaseInterface_t)    :: tae
   logical                        :: onlyPos
 
   call knapsack_gKnapsack__get_data_m(self, dpd)
   retval = onlyPos(dpd%d_private_data%d_weights, &
                    dpd%d_private_data%d_length)

   return
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.onlyPosWeights)
end subroutine gKn_onlyPosWeightsa5mqvv2_0x_mi


! 
! Method:  hasWeights[]
! Return TRUE if all of the specified weights, w, are in the knapsack
! or there are no specified weights; otherwise, return FALSE.
! 

recursive subroutine gKnapsa_hasWeightsih063a6wt7_mi(self, w, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PreViolation
  use sidl_int_array
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasWeights.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasWeights.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  type(sidl_int_1d) :: w
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasWeights)
   type (knapsack_gKnapsack_wrap) :: dpd
   type (knapsack_kExcept_t)      :: ke
   type (sidl_BaseInterface_t)    :: tae
   logical                        :: sameWeights
 
   call knapsack_gKnapsack__get_data_m(self, dpd)
   retval = sameWeights(dpd%d_private_data%d_length, &
                        dpd%d_private_data%d_weights, w)

   return
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasWeights)
end subroutine gKnapsa_hasWeightsih063a6wt7_mi


! 
! Method:  hasSolution[]
! Return TRUE if there is a solution for the specified target
! weight; otherwise, return FALSE.  Recall a solution is a
! subset of weights that total exactly to the specified target
! weight.
! 

recursive subroutine gKnaps_hasSolutiontahr9dvoxi_mi(self, t, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_gKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PreViolation
  use knapsack_gKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasSolution.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasSolution.use)
  implicit none
  type(knapsack_gKnapsack_t) :: self
  ! in
  integer (kind=sidl_int) :: t
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasSolution)
   type (knapsack_gKnapsack_wrap) :: dpd
   type (knapsack_kExcept_t)      :: ke
   type (sidl_BaseInterface_t)    :: tae
   logical                        :: solve
 
   call knapsack_gKnapsack__get_data_m(self, dpd)
   retval = solve(dpd%d_private_data%d_weights, t, 0_sidl_int, &
                  dpd%d_private_data%d_length)

   return
! DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasSolution)
end subroutine gKnaps_hasSolutiontahr9dvoxi_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Nothing to do here
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
