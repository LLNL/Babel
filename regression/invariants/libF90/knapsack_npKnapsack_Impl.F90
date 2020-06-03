! 
! File:          knapsack_npKnapsack_Impl.F90
! Symbol:        knapsack.npKnapsack-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 0)
! Description:   Server-side implementation for knapsack.npKnapsack
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "knapsack.npKnapsack" (version 1.0)
! 
! npKnapsack:  An implementation of knapsack that allows non-positive
! weights.
! 


#include "knapsack_kExcept_fAbbrev.h"
#include "sidl_InvViolation_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "knapsack_kSizeExcept_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "knapsack_npKnapsack_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
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

recursive subroutine knapsack_npKnapsack__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor.use)
  use knapsack_gKnapsack_Impl
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor)
   type (knapsack_npKnapsack_wrap) :: dpd
   integer (kind=sidl_int)        :: i

   allocate(dpd%d_private_data)
   dpd%d_private_data%d_length = 0_sidl_int
   do i = 0_sidl_int, MAX_WEIGHTS
      dpd%d_private_data%d_weights(i) = 0_sidl_int
   enddo
   call knapsack_npKnapsack__set_data_m(self, dpd)

   return
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor)
end subroutine knapsack_npKnapsack__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine knapsack_npKnapsack__ctor2_mi(self, private_data,         &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor2.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor2.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  type(knapsack_npKnapsack_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor2)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor2)
end subroutine knapsack_npKnapsack__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine knapsack_npKnapsack__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._dtor.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._dtor.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._dtor)
   type (knapsack_npKnapsack_wrap) :: dpd

   call knapsack_npKnapsack__get_data_m(self, dpd)
   deallocate(dpd%d_private_data)

   return
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._dtor)
end subroutine knapsack_npKnapsack__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine knapsack_npKnapsack__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._load.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._load)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack._load)
end subroutine knapsack_npKnapsack__load_mi


! 
! Method:  initialize[]
! Initialize the knapsack with the specified weights, w.
! 

recursive subroutine npKnaps_initialize04ae41l377_mi(self, w, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PostViolation
  use sidl_PreViolation
  use knapsack_kBadWeightExcept
  use knapsack_kSizeExcept
  use sidl_int_array
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.initialize.use)
  use knapsack_gKnapsack_Impl
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.initialize.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  type(sidl_int_1d) :: w
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.initialize)
   type (knapsack_npKnapsack_wrap)    :: dpd
   type (knapsack_kSizeExcept_t)      :: bse
   type (knapsack_kExcept_t)          :: ke
   type (sidl_BaseInterface_t)        :: tae
   integer (kind=sidl_int)            :: i, num, val

   call knapsack_npKnapsack__get_data_m(self, dpd)

   if (not_null(w)) then
      num = length(w, 0)
      if (num .le. MAX_WEIGHTS) then
         do i = 0_sidl_int, num
            call get(w, i, val)
            dpd%d_private_data%d_weights(i) = val
         enddo
         dpd%d_private_data%d_length = num
         call knapsack_npKnapsack__set_data_m(self, dpd)
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
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.initialize)
end subroutine npKnaps_initialize04ae41l377_mi


! 
! Method:  onlyPosWeights[]
! Return TRUE if all weights in the knapsack are positive;
! otherwise, return FALSE.
! 

recursive subroutine npK_onlyPosWeights290svg52fl_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.onlyPosWeights.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.onlyPosWeights.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.onlyPosWeights)
   type (knapsack_npKnapsack_wrap) :: dpd
   type (sidl_BaseInterface_t)     :: tae
   logical                         :: onlyPos

   call knapsack_npKnapsack__get_data_m(self, dpd)
   retval = onlyPos(dpd%d_private_data%d_weights, &
                    dpd%d_private_data%d_length)

   return
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.onlyPosWeights)
end subroutine npK_onlyPosWeights290svg52fl_mi


! 
! Method:  hasWeights[]
! Return TRUE if all of the specified weights, w, are in the knapsack
! or there are no specified weights; otherwise, return FALSE.
! 

recursive subroutine npKnaps_hasWeights2a6m3nltbw_mi(self, w, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PreViolation
  use sidl_int_array
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasWeights.use)
  ! Nothing to do here 
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasWeights.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  type(sidl_int_1d) :: w
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasWeights)
   type (knapsack_npKnapsack_wrap) :: dpd
   type (sidl_BaseInterface_t)     :: tae
   logical                         :: sameWeights

   call knapsack_npKnapsack__get_data_m(self, dpd)
   retval = sameWeights(dpd%d_private_data%d_length, &
                        dpd%d_private_data%d_weights, w)

   return
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasWeights)
end subroutine npKnaps_hasWeights2a6m3nltbw_mi


! 
! Method:  hasSolution[]
! Return TRUE if there is a solution for the specified target
! weight; otherwise, return FALSE.  Recall a solution is a
! subset of weights that total exactly to the specified target
! weight.
! 

recursive subroutine npKnap_hasSolution5pljnirbr5_mi(self, t, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_npKnapsack
  use knapsack_kExcept
  use sidl_InvViolation
  use sidl_PreViolation
  use knapsack_npKnapsack_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasSolution.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasSolution.use)
  implicit none
  type(knapsack_npKnapsack_t) :: self
  ! in
  integer (kind=sidl_int) :: t
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasSolution)
   type (knapsack_npKnapsack_wrap) :: dpd
   type (sidl_BaseInterface_t)     :: tae
   logical                         :: solve

   call knapsack_npKnapsack__get_data_m(self, dpd)
   retval = solve(dpd%d_private_data%d_weights, t, 0_sidl_int, &
                  dpd%d_private_data%d_length)

   return
! DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasSolution)
end subroutine npKnap_hasSolution5pljnirbr5_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Nothing to do here
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
