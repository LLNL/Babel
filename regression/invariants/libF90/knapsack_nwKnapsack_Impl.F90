! 
! File:          knapsack_nwKnapsack_Impl.F90
! Symbol:        knapsack.nwKnapsack-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 0)
! Description:   Server-side implementation for knapsack.nwKnapsack
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "knapsack.nwKnapsack" (version 1.0)
! 
! nwKnapsack:  An implementation of knapsack that drops about half 
! of the input weights.
! 


#include "knapsack_kExcept_fAbbrev.h"
#include "sidl_InvViolation_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "knapsack_kSizeExcept_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "knapsack_nwKnapsack_fAbbrev.h"
#include "knapsack_iKnapsack_fAbbrev.h"
#include "sidl_PostViolation_fAbbrev.h"
#include "knapsack_kBadWeightExcept_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_PreViolation_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
#include "knapsack_gKnapsack_fAbbrev.h"

! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine knapsack_nwKnapsack__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor.use)
  use knapsack_gKnapsack_Impl
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor)
   type (knapsack_nwKnapsack_wrap) :: dpd
   integer (kind=sidl_int)        :: i

   allocate(dpd%d_private_data)
   dpd%d_private_data%d_length = 0_sidl_int
   do i = 0_sidl_int, MAX_WEIGHTS
      dpd%d_private_data%d_weights(i) = 0_sidl_int
   enddo
   call knapsack_nwKnapsack__set_data_m(self, dpd)

   return
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor)
end subroutine knapsack_nwKnapsack__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine knapsack_nwKnapsack__ctor2_mi(self, private_data,         &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor2.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor2.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  type(knapsack_nwKnapsack_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor2)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor2)
end subroutine knapsack_nwKnapsack__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine knapsack_nwKnapsack__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._dtor.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._dtor.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._dtor)
   type (knapsack_nwKnapsack_wrap) :: dpd

   call knapsack_nwKnapsack__get_data_m(self, dpd)
   deallocate(dpd%d_private_data)

   return
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._dtor)
end subroutine knapsack_nwKnapsack__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine knapsack_nwKnapsack__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._load.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._load)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._load)
end subroutine knapsack_nwKnapsack__load_mi


! 
! Method:  initialize[]
! Initialize the knapsack with the specified weights, w.
! 

recursive subroutine nwKnaps_initializezkjqpfw5zw_mi(self, w, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PostViolation
  use sidl_PreViolation
  use knapsack_kBadWeightExcept
  use knapsack_kSizeExcept
  use sidl_int_array
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.initialize.use)
  use knapsack_gKnapsack_Impl
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.initialize.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  type(sidl_int_1d) :: w
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.initialize)
   type (knapsack_nwKnapsack_wrap)    :: dpd
   type (knapsack_kBadWeightExcept_t) :: bwe
   type (knapsack_kSizeExcept_t)      :: bse
   type (knapsack_kExcept_t)          :: ke
   type (sidl_BaseInterface_t)        :: tae
   integer (kind=sidl_int)            :: i, num, val
   logical                            :: onlyPosSIDL

   call knapsack_nwKnapsack__get_data_m(self, dpd)
   if (not_null(w)) then
      num = length(w, 0)
      if (num .le. MAX_WEIGHTS) then
         if (onlyPosSIDL(w)) then
            do i = 0_sidl_int, num
               if (mod(i, 2) .ne. 0_sidl_int) then
                  call get(w, i, val)
                  dpd%d_private_data%d_weights(i) = val
               endif
            enddo
            dpd%d_private_data%d_length = num
            call knapsack_nwKnapsack__set_data_m(self, dpd)
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
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.initialize)
end subroutine nwKnaps_initializezkjqpfw5zw_mi


! 
! Method:  onlyPosWeights[]
! Return TRUE if all weights in the knapsack are positive;
! otherwise, return FALSE.
! 

recursive subroutine nwK_onlyPosWeightsfag_xcnnfs_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.onlyPosWeights.use)
  ! Nothing to do here 
  use knapsack_gKnapsack_Impl
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.onlyPosWeights.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.onlyPosWeights)
   type (knapsack_nwKnapsack_wrap) :: dpd
   type (knapsack_kExcept_t)       :: ke
   type (sidl_BaseInterface_t)     :: tae
   logical                         :: onlyPos

   call knapsack_nwKnapsack__get_data_m(self, dpd)
   retval = onlyPos(dpd%d_private_data%d_weights, &
                    dpd%d_private_data%d_length)

   return
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.onlyPosWeights)
end subroutine nwK_onlyPosWeightsfag_xcnnfs_mi


! 
! Method:  hasWeights[]
! Return TRUE if all of the specified weights, w, are in the knapsack
! or there are no specified weights; otherwise, return FALSE.
! 

recursive subroutine nwKnaps_hasWeightsgiqqdcvyp1_mi(self, w, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PreViolation
  use sidl_int_array
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasWeights.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasWeights.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  type(sidl_int_1d) :: w
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasWeights)
   type (knapsack_nwKnapsack_wrap) :: dpd
   type (knapsack_kExcept_t)       :: ke
   type (sidl_BaseInterface_t)     :: tae
   logical                         :: sameWeights

   call knapsack_nwKnapsack__get_data_m(self, dpd)
   retval = sameWeights(dpd%d_private_data%d_length, &
                        dpd%d_private_data%d_weights, w)

   return
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasWeights)
end subroutine nwKnaps_hasWeightsgiqqdcvyp1_mi


! 
! Method:  hasSolution[]
! Return TRUE if there is a solution for the specified target
! weight; otherwise, return FALSE.  Recall a solution is a
! subset of weights that total exactly to the specified target
! weight.
! 

recursive subroutine nwKnap_hasSolution3f4cqu8jrl_mi(self, t, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_nwKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PreViolation
  use knapsack_nwKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasSolution.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasSolution.use)
  implicit none
  type(knapsack_nwKnapsack_t) :: self
  ! in
  integer (kind=sidl_int) :: t
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasSolution)
   type (knapsack_nwKnapsack_wrap) :: dpd
   type (knapsack_kExcept_t)       :: ke
   type (sidl_BaseInterface_t)     :: tae
   logical                         :: solve

   call knapsack_nwKnapsack__get_data_m(self, dpd)
   retval = solve(dpd%d_private_data%d_weights, t, 0_sidl_int, &
                  dpd%d_private_data%d_length)

   return
! DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasSolution)
end subroutine nwKnap_hasSolution3f4cqu8jrl_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Nothing to do here
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
