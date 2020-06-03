!
! File:        knapsacktest.F03
! Copyright:   (c) 2011 Lawrence Livermore National Security, LLC
! Revision:    @(#) $Revision: 6183 $
! Date:        $Date: 2011-09-30 14:34:51 -0700 (Fri, 30 Sep 2011) $
! Description: Invariants Regression Test Fortran 03 client
!
!

#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
!#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
!#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_ContractClass_fAbbrev.h"
#include "sidl_EnfPolicy_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "knapsack_ExpectExcept_fAbbrev.h"
#include "knapsack_gKnapsack_fAbbrev.h"
#include "knapsack_npKnapsack_fAbbrev.h"
#include "knapsack_nwKnapsack_fAbbrev.h"
#include "knapsack_kExcept_fAbbrev.h"
#include "knapsack_kBadWeightExcept_fAbbrev.h"
#include "knapsack_kSizeExcept_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"
#include "sidl_int_fAbbrev.h"


subroutine catch(tracker, exc)
  use sidl_BaseInterface
  use sidl_ClassInfo
  use synch_RegOut
  implicit none
  type (synch_RegOut_t) :: tracker
  type (sidl_BaseInterface_t) :: exc

  type (sidl_ClassInfo_t) :: classinfo
  type (sidl_BaseInterface_t) :: throwaway
  character (len=1024) :: buffer, nm

  if (.not. is_null(exc)) then
      call writeComment(tracker, 'Unexpected exception thrown', throwaway)
      call getClassInfo(exc, classinfo, throwaway)
      if (.not. is_null(classinfo)) then
          call getName(classinfo, nm, throwaway)
          buffer = 'Exception name: ' // nm
          call writeComment(tracker, buffer, throwaway)
      endif
      call deleteRef(exc, throwaway)
      call forceFailure(tracker, throwaway)
  endif
end subroutine catch


subroutine comment(tracker, msg)
  use sidl_BaseInterface
  use synch_RegOut
  implicit none
  type (synch_RegOut_t) :: tracker
  character (len=60) :: msg

  type (sidl_BaseInterface_t) :: throwaway

  call writeComment(tracker, msg, throwaway)
end subroutine comment


subroutine createArray(l, arr)
  use sidl
  use sidl_int_array
  implicit none
  integer (kind=sidl_int) :: l
  type (sidl_int_1d), intent(out) :: arr

  integer (kind=sidl_int) :: lw(1), up(1)

  call set_null(arr)
  if (l .gt. 0_sidl_int) then
    lw(1) = 0_sidl_int
    up(1) = l - 1_sidl_int
    call createRow(lw, up, arr)
  endif
end subroutine createArray


subroutine describeTest(tracker, partNum, desc)
  use sidl
  use sidl_BaseInterface
  use synch_RegOut
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  character (len=62) :: desc

  type (sidl_BaseInterface_t) :: throwaway

  partNum = partNum + 1_sidl_int
  call startPart(tracker, partNum, throwaway)
  call writeComment(tracker, desc, throwaway)
end subroutine describeTest


!
!  Values of constants in up case MUST match values in calling 
!  routine(s).
!
subroutine evalExcept(tracker, partNum, exc, expected)
  use sidl
  use sidl_BaseInterface
  use sidl_SIDLException
  use synch_RegOut
  use synch_ResultType
  use knapsack_ExpectExcept
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_BaseInterface_t) :: exc
  integer (kind=sidl_enum) :: expected

  type(sidl_SIDLException_t) :: sExcept
  type (sidl_BaseInterface_t) :: classinfo, throwaway
  integer (kind=sidl_enum) :: exceptType
  character (len=100) :: msg
  character (len=100) :: trace
  logical :: isOne
 

  exceptType = NoneExp
  isOne = .false.
  if (.not. is_null(exc)) then
!      call cast(exc, sExcept, throwaway)
!      call getNote(sExcept, msg, throwaway)
!      write (6, *) msg
!      call getTrace(sExcept, trace, throwaway)
!      write (6, *) trace
!      call deleteRef(sExcept, throwaway)

      call isType(exc, 'knapsack.kBadWeightExcept', isOne, throwaway)
      if (isOne .eqv. .true.) then
         exceptType = BWExp
      else
         call isType(exc, 'knapsack.kSizeExcept', isOne, throwaway)
         if (isOne .eqv. .true.) then
            exceptType = SizeExp
         endif
         call isType(exc, 'sidl.InvViolation', isOne, throwaway)
         if (isOne .eqv. .true.) then
            exceptType = InvExp
         endif
         call isType(exc, 'sidl.PreViolation', isOne, throwaway)
         if (isOne .eqv. .true.) then
            exceptType = PreExp
         endif
         if (isOne .eqv. .false.) then
            call isType(exc, 'sidl.PostViolation', isOne, throwaway)
            if (isOne .eqv. .true.) then
              exceptType = PostExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call isType(exc, 'knapsack.kExcept', isOne, throwaway)
            if (isOne .eqv. .true.) then
               exceptType = ExcExp
            endif
         endif
      endif 
     call deleteRef(exc, throwaway)
  else
      exceptType = NoneExp
  endif

  if (exceptType .eq. expected) then
     call endPart(tracker, partNum, PASS, throwaway)
  else
     call endPart(tracker, partNum, FAIL, throwaway)
  endif
end subroutine evalExcept


subroutine evalRes(tracker, partNum, res, expected)
  use sidl
  use sidl_BaseInterface
  use synch_RegOut
  use synch_ResultType
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  logical :: res, expected

  type (sidl_BaseInterface_t) :: throwaway

  if (res .eqv. expected) then
     call endPart(tracker, partNum, PASS, throwaway)
  else
     call endPart(tracker, partNum, FAIL, throwaway)
  endif
end subroutine evalRes


subroutine failNoExcept(tracker, partNum, expectExc)
  use sidl
  use sidl_BaseInterface
  use synch_RegOut
  use synch_ResultType
  use knapsack_ExpectExcept
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  integer (kind=sidl_enum) :: expectExc

  type (sidl_BaseInterface_t) :: throwaway

  if (expectExc .ne. NoneExp) then
    call endPart(tracker, partNum, FAIL, throwaway)
  endif
end subroutine failNoExcept


subroutine reporttest(tracker, test, partNum)
  use sidl
  use sidl_BaseInterface
  use synch_RegOut
  use synch_ResultType
  implicit none
  type (synch_RegOut_t) :: tracker
  logical :: test
  integer (kind=sidl_int) :: partNum

  type (sidl_BaseInterface_t) :: throwaway

  if (test) then
    call endPart(tracker, partNum, PASS, throwaway)
  else
    call endPart(tracker, partNum, FAIL, throwaway)
  endif
end subroutine reporttest


!
! ****************************************************************
! *                  Test Routines
! ****************************************************************
!
subroutine runBaseGCase(tracker, partNum, w, t, res, expectExc, desc)
  use sidl
  use sidl_int_array
  use sidl_BaseInterface
  use sidl_SIDLException
  use synch_RegOut
  use knapsack_ExpectExcept
  use knapsack_gKnapsack
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_int_1d) :: w
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  logical :: x
  type (knapsack_gKnapsack_t) :: k
  type (sidl_BaseInterface_t) :: exc, throwaway
  type(sidl_SIDLException_t)  :: sExcept

  call describeTest(tracker, partNum, desc)
  k = new(throwaway)
  call initialize(k, w, exc)
  if (is_null(exc)) then
    x = hasSolution(k, t, exc)
    if (is_null(exc)) then
      if (expectExc .eq. NoneExp) then
        call evalRes(tracker, partNum, x, res)
      else 
        call failNoExcept(tracker, partNum, expectExc)
      endif
    else
      call evalExcept(tracker, partNum, exc, expectExc) 
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runBaseGCase


subroutine runBaseNPCase(tracker, partNum, w, t, res, expectExc, desc)
  use sidl
  use sidl_int_array
  use sidl_BaseInterface
  use sidl_SIDLException
  use synch_RegOut
  use knapsack_ExpectExcept
  use knapsack_npKnapsack
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_int_1d) :: w
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  logical :: x
  type (knapsack_npKnapsack_t) :: k
  type (sidl_BaseInterface_t) :: exc, throwaway
  type(sidl_SIDLException_t)  :: sExcept

  call describeTest(tracker, partNum, desc)
  k = new(throwaway)
  call initialize(k, w, exc)
  if (is_null(exc)) then
    x = hasSolution(k, t, exc)
    if (is_null(exc)) then
      if (expectExc .eq. NoneExp) then
        call evalRes(tracker, partNum, x, res)
      else 
        call failNoExcept(tracker, partNum, expectExc)
      endif
    else
      call evalExcept(tracker, partNum, exc, expectExc) 
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runBaseNPCase


subroutine runBaseNWCase(tracker, partNum, w, t, res, expectExc, desc)
  use sidl
  use sidl_int_array
  use sidl_BaseInterface
  use sidl_SIDLException
  use synch_RegOut
  use knapsack_ExpectExcept
  use knapsack_nwKnapsack
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_int_1d) :: w
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  logical :: x
  type (knapsack_nwKnapsack_t) :: k
  type (sidl_BaseInterface_t) :: exc, throwaway
  type(sidl_SIDLException_t)  :: sExcept

  call describeTest(tracker, partNum, desc)
  k = new(k, throwaway)
  call initialize(k, w, exc)
  if (is_null(exc)) then
    x = hasSolution(k, t, exc)
    if (is_null(exc)) then
      if (expectExc .eq. NoneExp) then
        call evalRes(tracker, partNum, x, res)
      else 
        call failNoExcept(tracker, partNum, expectExc)
      endif
    else
      call evalExcept(tracker, partNum, exc, expectExc) 
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runBaseNWCase



!
!     ***************************************************************
!     ***************************************************************
!     **  MAIN:  knapsacktest
!     ***************************************************************
!     ***************************************************************
!
program knapsacktest
  use sidl
  use sidl_ContractClass
  use sidl_int_array
  use sidl_BaseInterface
  use sidl_EnfPolicy
  use synch_RegOut
  use knapsack_ExpectExcept
  implicit none

!     Total tests minus those attempting to pass a null input array 
!     argument (that have not been replaced with alternate argument,
!     where applicable).
! TODO/TLD:  Need to count number of tests trying to use null array
  integer (kind=sidl_int), parameter :: PARTS = 81_sidl_int - 21_sidl_int
  integer (kind=sidl_int), parameter :: MAX_SIZE = 6_sidl_int

  integer (kind=sidl_int), parameter :: ONE = 1_sidl_int
  integer (kind=sidl_int), parameter :: NEG_ONE = -1_sidl_int

  integer (kind=sidl_int) :: i, j, partNum, total
  character (len=60) :: heading
  type (synch_RegOut_t) :: tracker
  type (sidl_BaseInterface_t) :: exception, throwaway
  type (sidl_int_1d) :: good, gLong, nArr, negEnd, zStart, zMid, zEnd

  logical :: areEnf
  
  partNum = 0_sidl_int

  call getInstance(tracker, throwaway)
  call setExpectations(tracker, PARTS, throwaway)

!
!     Initialize test knapsacks
!
  call createArray(MAX_SIZE, good)
  call createArray(MAX_SIZE + 1_sidl_int, gLong)
  call createArray(MAX_SIZE, negEnd)
  call createArray(MAX_SIZE, zStart)
  call createArray(MAX_SIZE, zMid)
  call createArray(MAX_SIZE, zEnd)
  call set_null(nArr)

  total = 0_sidl_int
  val = 0_sidl_int
  do i = 0_sidl_int, MAX_SIZE - 1_sidl_int
    val = val + 2_sidl_int
    good%d_data(i) = val
    gLong%d_data(i) = val
    negEnd%d_data(i) = val
    zStart%d_data(i) = val
    zMid%d_data(i) = val
    zEnd%d_data(i) = val
    total = total + val
  enddo
  gLong%d_data(MAX_SIZE) = val + 2_sidl_int


!
!     Establish initial enforcement options
!
  heading = '*** ENABLE CONTRACT ENFORCEMENT ***'
  call comment(tracker, heading)
  call setEnforceAll(ALLCLASSES, .true., exception)
  call catch(tracker, exception)
  
  
!
!     gKnapsack.BaseCase.Full set
!
  call runBaseGCase(tracker, partNum, good, total, .true., NoneExp,&
   'gKnapsack.BaseCase.Full: good: expect solution for total      ')
  
  call runBaseGCase(tracker, partNum, good, total-1, .false., NoneExp,&
   'gKnapsack.BaseCase.Full: good: do not expect solution for t-1 ')
  
  call runBaseGCase(tracker, partNum, good, -1, .false., PreExp,&
   'gKnapsack.BaseCase.Full: good: precondition vio (neg. target) ')
  
  call runBaseGCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'gKnapsack.BaseCase.Full: gLong: size exception expected       ')
  
  call runBaseGCase(tracker, partNum, zStart, total, .false., PreExp,&
   'gKnapsack.BaseCase.Full: zStart: pre vio/no solution expected ')
  
  call runBaseGCase(tracker, partNum, zMid, total, .false., PreExp,&
   'gKnapsack.BaseCase.Full: zMid: pre vio/no solution expected   ')
  
  call runBaseGCase(tracker, partNum, zEnd, total, .false., PreExp,&
   'gKnapsack.BaseCase.Full: zEnd: pre vio/no solution expected   ')
  
  call runBaseGCase(tracker, partNum, negEnd, total, .false., PreExp,&
   'gKnapsack.BaseCase.Full: negEnd: pre vio/no solution expected ')
  
!
! TODO/TBD:  This will either be an invariant or postcondition violation,
! depending on ordering in generated code
!
  call runBaseGCase(tracker, partNum, nArr, total, .false., InvExp,&
   'gKnapsack.BaseCase.Full: nArr: inv vio/no solution expected   ')
  
  
!
!     npKnapsack.BaseCase.Full set
!
  call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,&
   'npKnapsack.BaseCase.Full: good: expect solution for total     ')
  
  call runBaseNPCase(tracker, partNum, good, total-1, .false., NoneExp,&
   'npKnapsack.BaseCase.Full: good: do not expect solution for t-1')
  
  call runBaseNPCase(tracker, partNum, good, -1, .false., PreExp,&
   'npKnapsack.BaseCase.Full: good: precondition vio (neg. target)')
  
  call runBaseNPCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'npKnapsack.BaseCase.Full: gLong: size exception expected      ')
  
  call runBaseNPCase(tracker, partNum, zStart, total, .false., PreExp,&
   'npKnapsack.BaseCase.Full: zStart: pre vio/no solution expected')
  
  call runBaseNPCase(tracker, partNum, zMid, total, .false., PreExp,&
   'npKnapsack.BaseCase.Full: zMid: pre vio/no solution expected  ')
  
  call runBaseNPCase(tracker, partNum, zEnd, total, .false., PreExp,&
   'npKnapsack.BaseCase.Full: zEnd: pre vio/no solution expected  ')
  
  call runBaseNPCase(tracker, partNum, negEnd, total, .false., PreExp,&
   'npKnapsack.BaseCase.Full: negEnd: pre vio/no solution expected')
  
!
! TODO/TBD:  This will either be an invariant or postcondition violation,
! depending on ordering in generated code
!
  call runBaseNPCase(tracker, partNum, nArr, total, .false., InvExp,&
   'npKnapsack.BaseCase.Full: nArr: inv vio/no solution expected  ')
  
  
!
!     nwKnapsack.BaseCase.Full set
!
  call runBaseNWCase(tracker, partNum, good, total, .false., NoneExp,&
   'nwKnapsack.BaseCase.Full: good: do not expect solution for t  ')
  
  call runBaseNWCase(tracker, partNum, good, total-1, .false., NoneExp,&
   'nwKnapsack.BaseCase.Full: good: do not expect solution for t-1')
  
  call runBaseNWCase(tracker, partNum, good, -1, .false., PreExp,&
   'nwKnapsack.BaseCase.Full: good: precondition vio (neg. target)')
  
  call runBaseNWCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'nwKnapsack.BaseCase.Full: gLong: size exception expected      ')
  
  call runBaseNWCase(tracker, partNum, zStart, total, .false., PreExp,&
   'nwKnapsack.BaseCase.Full: zStart: pre vio/no solution expected')
  
  call runBaseNWCase(tracker, partNum, zMid, total, .false., PreExp,&
   'nwKnapsack.BaseCase.Full: zMid: pre vio/no solution expected  ')
  
  call runBaseNWCase(tracker, partNum, zEnd, total, .false., PreExp,&
   'nwKnapsack.BaseCase.Full: zEnd: pre vio/no solution expected  ')
  
  call runBaseNWCase(tracker, partNum, negEnd, total, .false., PreExp,&
   'nwKnapsack.BaseCase.Full: negEnd: pre vio/no solution expected')
  
!
! TODO/TBD:  This will either be an invariant or postcondition violation, &
! depending on ordering in generated code
!
  call runBaseNWCase(tracker, partNum, nArr, total, .false., InvExp,&
   'nwKnapsack.BaseCase.Full: nArr: inv vio/no solution expected  ')
  
  
!
! ***********************************************************************
! * POSTCONDITION-ONLY ENFORCEMENT
! ***********************************************************************
!
  heading = '*** POSTCONDITION-ONLY ENFORCEMENT ***'
  call comment(tracker, heading)
  call setEnforceAll(POSTCONDS, .false., exception)
  call catch(tracker, exception)
                               
  
!
!     gKnapsack.BaseCase.Post set
!
  call runBaseGCase(good, total, .true., NoneExp,&
   'gKnapsack.BaseCase.Post: good: expect solution for total      ')
  
  call runBaseGCase(good, -1, .false., NoneExp,&
   'gKnapsack.BaseCase.Post: good: no solution expected           ')
  
  call runBaseGCase(gLong, total, .false., SizeExp,&
   'gKnapsack.BaseCase.Post: gLong: size exception expected       ')
  
  call runBaseGCase(negEnd, total, .false., BWExp,&
   'gKnapsack.BaseCase.Post: negEnd: bad weight exception expected')
  
  call runBaseGCase(nArr, total, .false., PostExp,&
   'gKnapsack.BaseCase.Post: nArr: post vio/no solution expected  ')
  
  
!
!     npKnapsack.BaseCase.Post set
!
  call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,&
   'npKnapsack.BaseCase.Post: good: expect solution for total     ')
  
  call runBaseNPCase(tracker, partNum, good, -1, .false., NoneExp,&
   'npKnapsack.BaseCase.Post: good: no solution expected          ')
  
  call runBaseNPCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'npKnapsack.BaseCase.Post: gLong: size exception expected      ')
  
  call runBaseNPCase(tracker, partNum, zStart, total, .false., NoneExp,&
   'npKnapsack.BaseCase.Post: zStart: no solution expected        ')
  
  call runBaseNPCase(tracker, partNum, zMid, total, .false., NoneExp,&
   'npKnapsack.BaseCase.Post: zMid: no solution expected          ')
  
  call runBaseNPCase(tracker, partNum, zEnd, total, .false., NoneExp,&
   'npKnapsack.BaseCase.Post: zEnd: no solution expected          ')
  
  call runBaseNPCase(tracker, partNum, negEnd, total, .false., NoneExp,&
   'npKnapsack.BaseCase.Post: negEnd: no solution expected        ')
  
  call runBaseNPCase(tracker, partNum, nArr, total, .false., PostExp,&
   'npKnapsack.BaseCase.Post: nArr: post vio/no solution expected ')
  
  
!
!     nwKnapsack.BaseCase.Post set
!
  call runBaseNWCase(tracker, partNum, good, total, .false., PostExp,&
   'nwKnapsack.BaseCase.Post: good: post vio/no solution expected ')
  
  call runBaseNWCase(tracker, partNum, good, -1, .false., PostExp,&
   'nwKnapsack.BaseCase.Post: good: post vio/no solution expected ')
  
  call runBaseNWCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'nwKnapsack.BaseCase.Post: gLong: size exception expected      ')
  
  call runBaseNWCase(tracker, partNum, negEnd, total, .false., PostExp,&
   'nwKnapsack.BaseCase.Post: negEnd: post vio/no solution exp.   ')
  
  call runBaseNWCase(tracker, partNum, nArr, total, .false., PostExp,&
   'nwKnapsack.BaseCase.Post: nArr: post vio/no solution expected ')
  
  
!
! ***********************************************************************
! * INVARIANTS-ONLY ENFORCEMENT
! ***********************************************************************
!
  heading = '*** INVARIANTS-ONLY ENFORCEMENT ***'
  call comment(tracker, heading)
  call setEnforceAll(INVARIANTS, .false., exception)
  call catch(tracker, exception)
  
!
!     gKnapsack.BaseCase.Inv set
!
  call runBaseGCase(good, total, .true., NoneExp,&
   'gKnapsack.BaseCase.Inv: good: expect solution for total       ')
  
  call runBaseGCase(good, -1, .false., NoneExp,&
   'gKnapsack.BaseCase.Inv: good: no solution expected            ')
  
  call runBaseGCase(gLong, total, .false., SizeExp,&
   'gKnapsack.BaseCase.Inv: gLong: size exception expected        ')
  
  call runBaseGCase(negEnd, total, .false., BWExp,&
   'gKnapsack.BaseCase.Inv: negEnd: bad weight exception expected ')
  
  call runBaseGCase(nArr, total, .false., InvExp,&
   'gKnapsack.BaseCase.Inv: nArr: inv vio/no solution expected    ')
  
  
!
!     npKnapsack.BaseCase.Inv set
!
  call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,&
   'npKnapsack.BaseCase.Inv: good: expect solution for total      ')
  
  call runBaseNPCase(tracker, partNum, good, -1, .false., NoneExp,&
   'npKnapsack.BaseCase.Inv: good: no solution expected           ')
  
  call runBaseNPCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'npKnapsack.BaseCase.Inv: gLong: size exception expected       ')
  
  call runBaseNPCase(tracker, partNum, zStart, total, .false., InvExp,&
   'npKnapsack.BaseCase.Inv: zStart: inv vio/no solution expected ')
  
  call runBaseNPCase(tracker, partNum, zMid, total, .false., InvExp,&
   'npKnapsack.BaseCase.Inv: zMid: inv vio/no solution expected   ')
  
  call runBaseNPCase(tracker, partNum, zEnd, total, .false., InvExp,&
   'npKnapsack.BaseCase.Inv: zEnd: inv vio/no solution expected   ')
  
  call runBaseNPCase(tracker, partNum, negEnd, total, .false., InvExp,&
   'npKnapsack.BaseCase.Inv: negEnd: inv vio/no solution expected ')
  
  call runBaseNPCase(tracker, partNum, nArr, total, .false., InvExp,&
   'npKnapsack.BaseCase.Inv: nArr: inv vio/no solution expected   ')
  
  
!
!     nwKnapsack.BaseCase.Inv set
!
  call runBaseNWCase(tracker, partNum, good, total, .false., NoneExp,&
   'nwKnapsack.BaseCase.Inv: good: no solution expected           ')
  
  call runBaseNWCase(tracker, partNum, good, -1, .false., NoneExp,&
   'nwKnapsack.BaseCase.Inv: good: no solution expected           ')
  
  call runBaseNWCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'nwKnapsack.BaseCase.Inv: gLong: size exception expected       ')
  
!
! WARNING:  The following assumes the non-positive weight does get added to
! the knapsack.
!
  call runBaseNWCase(tracker, partNum, negEnd, total, .false., InvExp,&
   'nwKnapsack.BaseCase.Inv: negEnd: inv vio/no solution expected ')
  
  call runBaseNWCase(tracker, partNum, nArr, total, .false., InvExp,&
   'nwKnapsack.BaseCase.Inv: nArr: inv vio/no solution expected   ')
  
  
!
! ***********************************************************************
! * NO CONTRACT ENFORCEMENT
! ***********************************************************************
! 
  heading = '*** DISABLE CONTRACT ENFORCEMENT ***'
  call comment(tracker, heading)
  call setEnforceNone(.false., exception)
  call catch(tracker, exception)
  
!
!     gKnapsack.BaseCase.None set
!
  call runBaseGCase(good, total, .true., NoneExp,&
   'gKnapsack.BaseCase.None: good: expect solution for total      ')
  
  call runBaseGCase(good, -1, .false., NoneExp,&
   'gKnapsack.BaseCase.None: good: no solution expected           ')
  
  call runBaseGCase(gLong, total, .false., SizeExp,&
   'gKnapsack.BaseCase.None: gLong: size exception expected       ')
  
  call runBaseGCase(negEnd, total, .false., BWExp,&
   'gKnapsack.BaseCase.None: negEnd: bad weight exception expected')
  
  call runBaseGCase(nArr, total, .false., NoneExp,&
   'gKnapsack.BaseCase.None: nArr: inv vio/no solution expected   ')
  
  
!
!     npKnapsack.BaseCase.None set
!
  call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,&
   'npKnapsack.BaseCase.None: good: expect solution for total     ')
  
  call runBaseNPCase(tracker, partNum, good, -1, .false., NoneExp,&
   'npKnapsack.BaseCase.None: good: no solution expected          ')
  
  call runBaseNPCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'npKnapsack.BaseCase.None: gLong: size exception expected      ')
  
  call runBaseNPCase(tracker, partNum, zStart, total, .false., NoneExp,&
   'npKnapsack.BaseCase.None: zStart: no solution expected        ')
  
  call runBaseNPCase(tracker, partNum, zMid, total, .false., NoneExp,&
   'npKnapsack.BaseCase.None: zMid: no solution expected          ')
  
  call runBaseNPCase(tracker, partNum, zEnd, total, .false., NoneExp,&
   'npKnapsack.BaseCase.None: zEnd: no solution expected          ')
  
  call runBaseNPCase(tracker, partNum, negEnd, total, .false., NoneExp,&
   'npKnapsack.BaseCase.None: negEnd: no solution expected        ')
  
  call runBaseNPCase(tracker, partNum, nArr, total, .false., NoneExp,&
   'npKnapsack.BaseCase.None: nArr: no solution expected          ')
  
  
!
!     nwKnapsack.BaseCase.None set
!
  call runBaseNWCase(tracker, partNum, good, total, .false., NoneExp,&
   'nwKnapsack.BaseCase.None: good: no solution expected          ')
  
  call runBaseNWCase(tracker, partNum, good, -1, .false., NoneExp,&
   'nwKnapsack.BaseCase.None: good: no solution expected          ')
  
  call runBaseNWCase(tracker, partNum, gLong, total, .false., SizeExp,&
   'nwKnapsack.BaseCase.None: gLong: size exception expected      ')
  
  call runBaseNWCase(tracker, partNum, negEnd, total, .false., NoneExp,&
   'nwKnapsack.BaseCase.None: negEnd: no solution expected        ')
  
  call runBaseNWCase(tracker, partNum, nArr, total, .false., NoneExp,&
   'nwKnapsack.BaseCase.None: nArr: no solution expected          ')
  
!
!   TODO/TBD:  Other possible cases:
!   1) create, initialize, and explicitly call hasWeights
!      x = k.hasWeights(k, w)
!  
!   2) create, initialize, and explicitly call onlyPosWeights
!      x = k.onlyPosWeights(k)
!

  call deleteRef(good)
  call deleteRef(gLong)
  call deleteRef(nArr)
  call deleteRef(negEnd)
  call deleteRef(zStart)
  call deleteRef(zMid)
  call deleteRef(zEnd)

  call close(tracker, throwaway)
  call deleteRef(tracker, throwaway)

  stop
end program knapsacktest
