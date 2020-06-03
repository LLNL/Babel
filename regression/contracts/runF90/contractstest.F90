!
! File:        vectortest.F90
! Copyright:   (c) 2009 Lawrence Livermore National Security, LLC
! Revision:    @(#) $Revision: 6183 $
! Date:        $Date: 2009-06-12 11:34:51 -0700 (Fri, 12 Jun 2009) $
! Description: Contract Regression Test Fortran 90 client
!
!

#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "vect_BadLevel_fAbbrev.h"
#include "vect_ExpectExcept_fAbbrev.h"
#include "vect_Utils_fAbbrev.h"
#include "vect_vDivByZeroExcept_fAbbrev.h"
#include "vect_vExcept_fAbbrev.h"
#include "vect_vNegValExcept_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"


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


subroutine createDouble(l, arr)
  use sidl
  use sidl_double_array
  implicit none
  integer (kind=sidl_int) :: l
  type (sidl_double_1d), intent(out) :: arr

  integer (kind=sidl_int) :: lw(1), up(1)

  call set_null(arr)
  if (l .gt. 0_sidl_int) then
    lw(1) = 0_sidl_int
    up(1) = l - 1_sidl_int
    call createRow(lw, up, arr)
  endif
end subroutine createDouble


subroutine create2Double(l, arr)
  use sidl
  use sidl_double_array
  implicit none
  integer (kind=sidl_int) :: l
  type (sidl_double_2d), intent(out) :: arr

  integer (kind=sidl_int) :: lw(2), up(2)

  call set_null(arr)
  if (l .gt. 0_sidl_int) then
    lw(1) = 0_sidl_int
    lw(2) = 0_sidl_int
    up(1) = l - 1_sidl_int
    up(2) = l - 1_sidl_int
    call createRow(lw, up, arr)
  endif
end subroutine create2Double


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
  use vect_ExpectExcept
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

      call isType(exc, 'sidl.PreViolation', isOne, throwaway)
      if (isOne .eqv. .true.) then
         exceptType = PreExp
      else
         call isType(exc, 'sidl.PostViolation', isOne, throwaway)
         if (isOne .eqv. .true.) then
            exceptType = PostExp
         endif
         if (isOne .eqv. .false.) then
            call isType(exc, 'vect.vDivByZeroExcept', isOne, throwaway)
            if (isOne .eqv. .true.) then
              exceptType = DBZExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call isType(exc, 'vect.vNegValExcept', isOne, throwaway)
            if (isOne .eqv. .true.) then
               exceptType = NVEExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call isType(exc, 'vect.vExcept', isOne, throwaway)
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


subroutine evalResB(tracker, partNum, res, expected)
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
end subroutine evalResB


subroutine evalResA(tracker, partNum, res, expected, tol, okay)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use synch_ResultType
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: res, expected
  real (kind=sidl_double) :: tol
  logical :: okay

  logical :: ok
  type (sidl_BaseInterface_t) :: exc, throwaway

  if (.not. is_null(res)) then
    if (.not. is_null(expected)) then
      call vect_Utils_vuAreEqual_m(res, expected, tol, ok, exc)
      if (.not. is_null(exc)) then
        call catch(tracker, exc)
        ok = .false.
      endif 
    else
      ok = .false.
    endif
  else
    if (.not. is_null(expected)) then
      ok = .false.
    else
      ok = .true.
    endif
  endif
  call evalResB(tracker, partNum, ok, okay)
end subroutine evalResA


subroutine evalResD(tracker, partNum, res, expected, tol)
  use sidl
  use sidl_BaseInterface
  use synch_RegOut
  use synch_ResultType
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  real (kind=sidl_double) :: res, expected, tol

  type (sidl_BaseInterface_t) :: throwaway

  if (abs(res - expected) .le. abs(tol)) then
     call endPart(tracker, partNum, PASS, throwaway)
  else
     call endPart(tracker, partNum, FAIL, throwaway)
  endif
end subroutine evalResD

subroutine failNoExcept(tracker, partNum, expectExc)
  use sidl
  use sidl_BaseInterface
  use synch_RegOut
  use synch_ResultType
  use vect_ExpectExcept
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
subroutine runIsZero(tracker, partNum, v, tol, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use sidl_SIDLException
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: v
  real (kind=sidl_double) :: tol
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  logical :: x
  type (sidl_BaseInterface_t) :: exc
  type(sidl_SIDLException_t)  :: sExcept

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuIsZero_m(v, tol, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResB(tracker, partNum, x, res)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runIsZero


subroutine runIsUnit(tracker, partNum, v, tol, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: v
  real (kind=sidl_double) :: tol
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  type (sidl_BaseInterface_t) :: exc
  logical :: x

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuIsUnit_m(v, tol, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResB(tracker, partNum, x, res)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runIsUnit


subroutine runAreEqual(tracker, partNum, u, v, tol, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v
  real (kind=sidl_double) :: tol
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  type (sidl_BaseInterface_t) :: exc
  logical :: x

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuAreEqual_m(u, v, tol, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResB(tracker, partNum, x, res)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runAreEqual


subroutine runAreOrth(tracker, partNum, u, v, tol, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v
  real (kind=sidl_double) :: tol
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  type (sidl_BaseInterface_t) :: exc
  logical :: x

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuAreOrth_m(u, v, tol, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResB(tracker, partNum, x, res)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runAreOrth


subroutine runSchwarzHolds(tracker, partNum, u, v, tol, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v
  real (kind=sidl_double) :: tol
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  logical :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuSchwarzHolds_m(u, v, tol, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResB(tracker, partNum, x, res)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runSchwarzHolds


subroutine runTriIneqHolds(tracker, partNum, u, v, tol, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v
  real (kind=sidl_double) :: tol
  logical :: res
  integer (kind=sidl_enum) :: expectExc
  character (len=62) :: desc

  type (sidl_BaseInterface_t) :: exc
  logical :: x

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuTriIneqHolds_m(u, v, tol, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResB(tracker, partNum, x, res)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runTriIneqHolds


subroutine runNorm(tracker, partNum, u, tol, badLvl, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u
  real (kind=sidl_double) :: tol, res
  integer (kind=sidl_enum) :: badLvl, expectExc
  character (len=62) :: desc

  real (kind=sidl_double) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuNorm_m(u, tol, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResD(tracker, partNum, x, res, tol)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runNorm


subroutine runDot(tracker, partNum, u, v, tol, badLvl, res, expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v
  real (kind=sidl_double) :: tol, res
  integer (kind=sidl_enum) :: badLvl, expectExc
  character (len=62) :: desc

  real (kind=sidl_double) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuDot_m(u, v, tol, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResD(tracker, partNum, x, res, tol)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runDot


!  NOTE: Tolerance is only needed to check the result of the call.
subroutine runProduct(tracker, partNum, a, u, tol, badLvl, res, okay, &
                      expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  real (kind=sidl_double) :: a, tol
  type (sidl_double_1d) :: u, res
  integer (kind=sidl_enum) :: badLvl, expectExc
  logical :: okay
  character (len=62) :: desc

  type (sidl_double_1d) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuProduct_m(a, u, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResA(tracker, partNum, x, res, tol, okay)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runProduct


!  NOTE: Tolerance is only needed to check the result of the call.
subroutine runNegate(tracker, partNum, u, tol, badLvl, res, okay, expectExc, &
                     desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, res
  real (kind=sidl_double) :: tol
  integer (kind=sidl_enum) :: badLvl, expectExc
  logical :: okay
  character (len=62) :: desc

  type (sidl_double_1d) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuNegate_m(u, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResA(tracker, partNum, x, res, tol, okay)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runNegate


subroutine runNormalize(tracker, partNum, u, tol, badLvl, res, okay, &
                        expectExc, desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, res
  real (kind=sidl_double) :: tol
  integer (kind=sidl_enum) :: badLvl, expectExc
  logical :: okay
  character (len=62) :: desc

  type (sidl_double_1d) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuNormalize_m(u, tol, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResA(tracker, partNum, x, res, tol, okay)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runNormalize


!  NOTE: Tolerance is only needed to check the result of the call.
subroutine runSum(tracker, partNum, u, v, tol, badLvl, res, okay, expectExc, &
                  desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v, res
  real (kind=sidl_double) :: tol
  integer (kind=sidl_enum) :: badLvl, expectExc
  logical :: okay
  character (len=62) :: desc

  type (sidl_double_1d) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuSum_m(u, v, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResA(tracker, partNum, x, res, tol, okay)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runSum


!  NOTE: Tolerance is only needed to check the result of the call.
subroutine runDiff(tracker, partNum, u, v, tol, badLvl, res, okay, expectExc, &
                   desc)
  use sidl
  use sidl_double_array
  use sidl_BaseInterface
  use synch_RegOut
  use vect_ExpectExcept
  use vect_Utils
  implicit none
  type (synch_RegOut_t) :: tracker
  integer (kind=sidl_int) :: partNum
  type (sidl_double_1d) :: u, v, res
  real (kind=sidl_double) :: tol
  integer (kind=sidl_enum) :: badLvl, expectExc
  logical :: okay
  character (len=62) :: desc

  type (sidl_double_1d) :: x
  type (sidl_BaseInterface_t) :: exc

  call describeTest(tracker, partNum, desc)
  call vect_Utils_vuDiff_m(u, v, badLvl, x, exc)
  if (is_null(exc)) then
    if (expectExc .eq. NoneExp) then
      call evalResA(tracker, partNum, x, res, tol, okay)
    else 
      call failNoExcept(tracker, partNum, expectExc)
    endif
  else
    call evalExcept(tracker, partNum, exc, expectExc) 
  endif
end subroutine runDiff


!
!     ***************************************************************
!     ***************************************************************
!     **  MAIN:  vectortest
!     ***************************************************************
!     ***************************************************************
!
program vectortest
  use sidl
  use sidl_ContractClass
  use sidl_double_array
  use sidl_BaseInterface
  use sidl_EnfPolicy
  use synch_RegOut
  use vect_BadLevel
  use vect_ExpectExcept
  use vect_Utils
  implicit none

  character (len=25), parameter :: STATS_FILE = 'VUtils.stats'
!     Total tests minus those attempting to pass a null input array 
!     argument (that have not been replaced with alternate argument,
!     where applicable).
  integer (kind=sidl_int), parameter :: PARTS = 128_sidl_int - 21_sidl_int
  integer (kind=sidl_int), parameter :: MAX_SIZE = 6_sidl_int

  real (kind=sidl_double), parameter :: MAX_SIZED = 6.0_sidl_double

  real (kind=sidl_double), parameter :: TOL = 1.0_sidl_double / &
                  (1000.0_sidl_double*1000.0_sidl_double*1000.0_sidl_double)
  real (kind=sidl_double), parameter :: NTOL = -1.0_sidl_double * TOL

  real (kind=sidl_double), parameter :: FIVE = 5.0_sidl_double
  real (kind=sidl_double), parameter :: NEG_FIVE = -5.0_sidl_double
  real (kind=sidl_double), parameter :: ONE = 1.0_sidl_double
  real (kind=sidl_double), parameter :: TWO = 2.0_sidl_double
  real (kind=sidl_double), parameter :: ZERO = 0.0_sidl_double

  integer (kind=sidl_int) :: i, j, partNum
  real (kind=sidl_double) :: VAL, NVAL, SQRT_SIZE
  character (len=60) heading
  type (synch_RegOut_t) :: tracker
  type (sidl_BaseInterface_t) :: exception, throwaway
  type (sidl_double_1d) :: u, u1, nu, z, n
  type (sidl_double_2d) :: t

  logical :: areEnf
  
  SQRT_SIZE = sqrt(MAX_SIZED)
  VAL = ONE / SQRT_SIZE
  NVAL = -1.0_sidl_double * VAL
  partNum = 0_sidl_int

  call getInstance(tracker, throwaway)
  call setExpectations(tracker, PARTS, throwaway)

!
!     Initialize test vectors
!
  call createDouble(MAX_SIZE, u)
  call createDouble(MAX_SIZE + 1_sidl_int, u1)
  call createDouble(MAX_SIZE, nu)
  call createDouble(MAX_SIZE, z)
  call create2Double(MAX_SIZE, t)
  call set_null(n)

  do i = 0_sidl_int, MAX_SIZE - 1_sidl_int
    u%d_data(i) = VAL
    u1%d_data(i) = VAL
    nu%d_data(i) = NVAL
    z%d_data(i) = ZERO
    do j = 0_sidl_int, MAX_SIZE - 1_sidl_int
      t%d_data(i,j) = VAL
    enddo
  enddo
  u1%d_data(MAX_SIZE) = VAL

!
!     Establish initial enforcement options
!
  heading = '*** ENABLE FULL CONTRACT CHECKING ***'
  call comment(tracker, heading)
  call sidl_EnfPolicy_setEnforceAll_m(ALLCLASSES, .true., exception)
  call catch(tracker, exception)

!
!     vuIsZero() set
!
  call runIsZero(tracker, partNum, z, TOL, .true., NoneExp, &
   'ensuring the zero vector is the zero vector                   ')
  call runIsZero(tracker, partNum, u, TOL, .false., NoneExp, &
   'ensuring the unit vector is not the zero vector               ')
  call runIsZero(tracker, partNum, n, TOL, .false., PreExp, &
   'passing vuIsZero() a null array                               ')
  call runIsZero(tracker, partNum, t, TOL, .false., PreExp, &
   'passing vuIsZero() a 2D array                                 ')
  call runIsZero(tracker, partNum, z, NTOL, .false., PreExp, &
   'passing vuIsZero() a negative tolerance                       ')

!
!     vuIsUnit() set
!

  call runIsUnit(tracker, partNum, u, TOL, .true., NoneExp, &
   'ensuring the unit vector is the unit vector                   ')
  call runIsUnit(tracker, partNum, z, TOL, .false., NoneExp, &
   'ensuring the zero vector is not the unit vector               ')
  call runIsUnit(tracker, partNum, n, TOL, .false., PreExp, & 
   'passing vuIsUnit() a null array                               ')
!  call runIsUnit(tracker, partNum, t, TOL, .false., PreExp, &
!   'passing vuIsUnit() a 2D array                                 ')
  call runIsUnit(tracker, partNum, u, NTOL, .false., PreExp, &
   'passing vuIsUnit() a negative tolerance                       ')

!
!     vuAreEqual() set
!

  call runAreEqual(tracker, partNum, u, z, TOL, .false., NoneExp, &
   'ensuring the unit and zero vectors are not equal              ')
  call runAreEqual(tracker, partNum, u, u, TOL, .true., NoneExp, &
   'ensuring the unit vector is equal to itself                   ')
  call runAreEqual(tracker, partNum, n, u, TOL, .false., PreExp, &
   'passing vuAreEqual() a null 1st array                         ')
!  call runAreEqual(tracker, partNum, t, u, TOL, .false., PreExp, &
!   'passing vuAreEqual() a 2D 1st array                           ')
  call runAreEqual(tracker, partNum, u, n, TOL, .false., PreExp, &
   'passing vuAreEqual() a null 2nd array                         ')
!  call runAreEqual(tracker, partNum, u, t, TOL, .false., PreExp, &
!   'passing vuAreEqual() a 2D 2nd array                           ')
  call runAreEqual(tracker, partNum, u, u1, TOL, .false., PreExp, &
   'passing vuAreEqual() different sized arrays                   ')
  call runAreEqual(tracker, partNum, u, u, NTOL, .false., PreExp, &
   'passing vuAreEqual() a negative tolerance                     ')

!
!     vuAreOrth() set
!
  call runAreOrth(tracker, partNum, u, z, TOL, .true., NoneExp, &
   'ensuring the unit and zero vectors are orthogonal             ')
  call runAreOrth(tracker, partNum, u, nu, TOL, .false., NoneExp, &
   'ensuring unit and negative unit vectors are not orthogonal    ')
  call runAreOrth(tracker, partNum, n, u, TOL, .false., PreExp, &
   'passing vuAreOrth() a null 1st array                          ')
!  call runAreOrth(tracker, partNum, t, u, TOL, .false., PreExp, &
!   'passing vuAreOrth() a 2D 1st array                            ')
  call runAreOrth(tracker, partNum, u, n, TOL, .false., PreExp, &
   'passing vuAreOrth() a null 2nd array                          ')
!  call runAreOrth(tracker, partNum, u, t, TOL, .false., PreExp, &
!   'passing vuAreOrth() a 2D 2nd array                            ')
  call runAreOrth(tracker, partNum, u, u1, TOL, .false., PreExp, &
   'passing vuAreOrth() different sized unit arrays               ')
  call runAreOrth(tracker, partNum, u, u, NTOL, .false., PreExp, &
   'passing vuAreOrth() a negative tolerance                      ')
!  call runAreOrth(tracker, partNum, t, t, TOL, .false., PreExp, &
!   'passing vuAreOrth() 2D arrays in both arguments               ')

!
!     vuSchwarzHolds() set
!
  call runSchwarzHolds(tracker, partNum, u, z, TOL, .true., NoneExp, &
   'ensuring schwarz holds for the unit and zero vectors          ')
  call runSchwarzHolds(tracker, partNum, u, z, NTOL, .false., PreExp, &
   'passing vuSchwarzHolds() a null 1st array                     ')
!  call runSchwarzHolds(tracker, partNum, t, z, TOL, .false., PreExp, &
!   'passing vuSchwarzHolds() a 2D 1st array                       ')
  call runSchwarzHolds(tracker, partNum, z, n, TOL, .false., PreExp, &
   'passing vuSchwarzHolds() a null 2nd array                     ')
!  call runSchwarzHolds(tracker, partNum, u, t, TOL, .false., PreExp, &
!   'passing vuSchwarzHolds() a 2D 2nd array                       ')
  call runSchwarzHolds(tracker, partNum, u, u1, TOL, .false., PreExp, &
   'passing vuSchwarzHolds() different sized unit arrays          ')
  call runSchwarzHolds(tracker, partNum, u, z, NTOL, .false., PreExp, &
   'passing vuSchwarzHolds() a negative tolerance                 ')

!
!     vuTriIneqHolds() set
!
  call runTriIneqHolds(tracker, partNum, u, z, TOL, .true., NoneExp, &
   'ensuring triangle ineq. holds for unit and zero vectors       ')
  call runTriIneqHolds(tracker, partNum, n, u, TOL, .false., PreExp, &
   'passing vuTriIneqHolds() a null 1st array                     ')
!  call runTriIneqHolds(tracker, partNum, t, u, TOL, .false., PreExp, &
!   'passing vuTriIneqHolds() a 2D 1st array                       ')
  call runTriIneqHolds(tracker, partNum, u, n, TOL, .false., PreExp, &
   'passing vuTriIneqHolds() a null 2nd array                     ')
!  call runTriIneqHolds(tracker, partNum, u, t, TOL, .false., PreExp, &
!   'passing vuTriIneqHolds() a 2D 2nd array                       ')
  call runTriIneqHolds(tracker, partNum, u, u1, TOL, .false., PreExp, &
   'passing vuTriIneqHolds() different sized unit vectors         ')
  call runTriIneqHolds(tracker, partNum, u, u, NTOL, .false., PreExp, &
   'passing vuTriIneqHolds() a negative tolerance                 ')

!
!     vuNorm() set
!
  call runNorm(tracker, partNum, u, TOL, NoVio, ONE, NoneExp,&
   'ensuring the unit vector norm is 1.0                          ')
  call runNorm(tracker, partNum, n, TOL, NoVio, ZERO, PreExp,&
   'passing vuNorm() a null vector                                ')
!  call runNorm(tracker, partNum, t, TOL, NoVio, ZERO, PreExp,&
!   'passing vuNorm() a 2D array                                   ')
  call runNorm(tracker, partNum, u, NTOL, NoVio, ZERO, PreExp,&
   'passing vuNorm() a negative tolerance                         ')
  call runNorm(tracker, partNum, u, TOL, NegRes, NEG_FIVE, PostExp,&
   'passing vuNorm() badness level for negative result            ')
  call runNorm(tracker, partNum, z, TOL, PosRes, FIVE, PostExp, &
   'passing vuNorm() bad level for positive result with zero vec. ')
  call runNorm(tracker, partNum, u, TOL, ZeroRes, ZERO, PostExp, &
   'passing vuNorm() bad level for zero result with non-zero vec. ')

!
!     vuDot() set
!
  call runDot(tracker, partNum, u, z, TOL, NoVio, ZERO, NoneExp, &
   'ensuring dot product of the unit and zero vectors is 0.0      ')
  call runDot(tracker, partNum, n, u, TOL, NoVio, ZERO, PreExp, &
   'passing vuDot() a null 1st array                              ')
!  call runDot(tracker, partNum, t, u, TOL, NoVio, ZERO, PreExp, &
!  'passing vuDot() a 2D 1st array                                 ')
  call runDot(tracker, partNum, u, n, TOL, NoVio, ZERO, PreExp, &
   'passing vuDot() a null 2nd array                              ')
!  call runDot(tracker, partNum, u, t, TOL, NoVio, ZERO, PreExp, &
!   'passing vuDot() a 2D 2nd array                                ')
  call runDot(tracker, partNum, u, u1, TOL, NoVio, ZERO, PreExp, &
  'passing vuDot() different sized unit vectors                   ')
  call runDot(tracker, partNum, u, u, NTOL, NoVio, ZERO, PreExp, &
   'passing vuDot() a negative tolerance                          ')
!  call runDot(tracker, partNum, t, t, TOL, NoVio, ZERO, PreExp, &
!   'passing vuDot() a 2D arrays in both arguments                 ')
  call runDot(tracker, partNum, u, u, TOL, NegRes, NEG_FIVE, PostExp, &
   'passing vuDot() bad level for negative result with u=v        ')
  call runDot(tracker, partNum, z, z, TOL, PosRes, FIVE, PostExp, &
   'passing vuDot() bad level for positive result with u=v=0      ')

!
!     vuProduct() set
!
  call runProduct(tracker, partNum, ONE, u, TOL, NoVio, u, .true., NoneExp, &
   'ensuring product of 1 and unit vector is the unit vector      ')
  call runProduct(tracker, partNum, TWO, u, TOL, NoVio, u, .false., NoneExp, &
   'ensuring product of 2 and unit vector is not unit vector      ')
  call runProduct(tracker, partNum, ZERO, n, TOL, NoVio, n, .true., PreExp, &
   'passing vuProduct() a null array                              ')
!  call runProduct(tracker, partNum, ONE, t, TOL, NoVio, u, .false., PreExp, &
!   'passing vuProduct() a 2D array                                ')
  call runProduct(tracker, partNum, ONE, u, TOL, NullRes, u, .true., PostExp, &
   'passing vuProduct() badness level for null result             ')
  call runProduct(tracker, partNum, ONE, u, TOL, TwoDRes, u, .true., PostExp, &
   'passing vuProduct() badness level for 2D result               ')
  call runProduct(tracker, partNum, ONE, u, TOL, WrongSizeRes, u, .true., &
    PostExp, &
   'passing vuProduct() bad level for wrong size result           ')

!
!     vuNegate() set
!
  call runNegate(tracker, partNum, u, TOL, NoVio, nu, .true., NoneExp, &
   'ensuring negation of the the unit vector is its negative      ')
  call runNegate(tracker, partNum, u, TOL, NoVio, u, .false., NoneExp, &
   'ensuring negation of unit vector is not the unit vector       ')
  call runNegate(tracker, partNum, n, TOL, NoVio, nu, .true., PreExp, &
   'passing vuNegate() a null array                               ')
!  call runNegate(tracker, partNum, t, TOL, NoVio, nu, .false., PreExp, &
!   'passing vuNegate() a 2D array                                 ')
  call runNegate(tracker, partNum, u, TOL, NullRes, nu, .true., PostExp, &
   'passing vuNegate() badness level for null result              ')
  call runNegate(tracker, partNum, u, TOL, TwoDRes, nu, .true., PostExp, &
   'passing vuNegate() badness level for 2D result                ')
  call runNegate(tracker, partNum, u, TOL, WrongSizeRes, nu, .true., PostExp,&
   'passing vuNegate() bad level for wrong size result            ')

!
!     vuNormalize() set
!
  call runNormalize(tracker, partNum, u, TOL, NoVio, u, .true., NoneExp, &
   'ensuring normalize of the unit vector is itself               ')
  call runNormalize(tracker, partNum, u, TOL, NoVio, nu, .false., NoneExp, &
   'ensuring normalize of the unit vector is not its negative     ')
  call runNormalize(tracker, partNum, z, TOL, NoVio, z, .true., DBZExp, &
   'ensuring normalize of zero vector raises a DBZ exception      ')
  call runNormalize(tracker, partNum, n, TOL, NoVio, n, .true., PreExp, &
   'passing vuNormalize() a null array                            ')
!  call runNormalize(tracker, partNum, t, TOL, NoVio, u, .false., PreExp, &
!   'passing vuNormalize() a 2D array                              ')
  call runNormalize(tracker, partNum, u, NTOL, NoVio, u, .true., PreExp, &
   'passing vuNormalize() a neg tolerance using unit vector       ')
  call runNormalize(tracker, partNum, u, TOL, NullRes, u, .true., PostExp, &
   'passing vuNormalize() badness level for null result           ')
  call runNormalize(tracker, partNum, u, TOL, TwoDRes, u, .true., PostExp, &
   'passing vuNormalize() badness level for 2D result             ')
  call runNormalize(tracker, partNum, u, TOL, WrongSizeRes, u, .true., &
   PostExp, &
   'passing vuNormalize() bad level for wrong size result         ')

!
!     vuSum() set  (NOTE:  Tolerance not relevant to vuSum() API.)
!
  call runSum(tracker, partNum, u, z, TOL, NoVio, u, .true., NoneExp, &
   'ensuring sum of unit and zero vectors is unit vector          ')
  call runSum(tracker, partNum, u, z, TOL, NoVio, nu, .false., NoneExp, &
   'ensuring sum of unit and zero vectors is not neg of unit      ')
  call runSum(tracker, partNum, n, z, TOL, NoVio, n, .true., PreExp, &
   'passing vuSum() a null 1st array                              ')
!  call runSum(tracker, partNum, t, n, TOL, NoVio, n, .false., PreExp, &
!   'passing vuSum() a 2D 1st array                                ')
  call runSum(tracker, partNum, u, n, TOL, NoVio, n, .true., PreExp, &
   'passing vuSum() a null 2nd array                              ')
!  call runSum(tracker, partNum, u, t, TOL, NoVio, n, .false., PreExp, &
!   'passing vuSum() a 2D as second                                ')
  call runSum(tracker, partNum, u, u1, TOL, NoVio, z, .true., PreExp, &
   'passing vuSum() different sized unit vectors                  ')
  call runSum(tracker, partNum, u, z, TOL, NullRes, u, .true., PostExp, &
   'passing vuSum() badness level for null result                 ')
  call runSum(tracker, partNum, u, z, TOL, TwoDRes, u, .true., PostExp, &
   'passing vuSum() badness level for 2D result                   ')
  call runSum(tracker, partNum, u, z, TOL, WrongSizeRes, u, .true., PostExp,&
   'passing vuSum() bad level for wrong size result               ')

!
!     vuDiff() set  (NOTE:  Tolerance not relevant to vuSum() API.)
!
  call runDiff(tracker, partNum, z, u, TOL, NoVio, nu, .true., NoneExp, &
   'ensuring diff of zero and unit vectors is neg unit vector     ')
  call runDiff(tracker, partNum, u, z, TOL, NoVio, u, .true., NoneExp, &
   'ensuring diff of unit and zero vectors is unit vector         ')
  call runDiff(tracker, partNum, u, z, TOL, NoVio, nu, .false., NoneExp, &
   'ensuring diff of unit and zero is not neg unit vector         ')
  call runDiff(tracker, partNum, n, u, TOL, NoVio, n, .true., PreExp, &
   'passing vuDiff() a null 1st array                             ')
!  call runDiff(tracker, partNum, t, u, TOL, NoVio, u, .false., PreExp, &
!   'passing vuDiff() a 2D 1st array                               ')
  call runDiff(tracker, partNum, u, n, TOL, NoVio, n, .true., PreExp, &
   'passing vuDiff() a null 2nd array                             ')
!  call runDiff(tracker, partNum, u, t, TOL, NoVio, u, .false., PreExp, &
!   'passing vuDiff() a 2D 2nd array                               ')
  call runDiff(tracker, partNum, u, u1, TOL, NegRes, u, .true., PreExp, &
   'passing vuDiff() different sized vectors                      ')
  call runDiff(tracker, partNum, z, u, TOL, NullRes, nu, .true., PostExp, &
   'passing vuDiff() badness level for null result                ')
  call runDiff(tracker, partNum, z, u, TOL, TwoDRes, nu, .true., PostExp, &
   'passing vuDiff() badness level for 2D result                  ')
  call runDiff(tracker, partNum, z, u, TOL, WrongSizeRes, nu, .true., PostExp,&
   'passing vuDiff() bad level for wrong size result              ')

  call vect_Utils_dump_stats_static_m(STATS_FILE,&
   'After full checking                                           ',&
   throwaway)


!     ****************************************************************
!     * Now check preconditions only.  Only need three checks:
!     *   1) successful execution;
!     *   2) precondition violation that is not caught but is
!     *      okay anyway; and
!     *   3) postcondition violation that is caught.
!     ****************************************************************
  heading = '*** ENABLE PRECONDITION ENFORCEMENT ONLY ***'
  call comment(tracker, heading)
  call sidl_EnfPolicy_setEnforceAll_m(PRECONDS, .false., exception)
  call catch(tracker, exception)

  call runDot(tracker, partNum, u, z, TOL, NoVio, ZERO, NoneExp, &
   'ensuring dot product of the unit and zero vectors is 0.0      ')
  call runDot(tracker, partNum, u, u, NTOL, NoVio, ONE, PreExp, &
   'passing vuDot() a negative tolerance                          ')
  call runDot(tracker, partNum, u, u, TOL, NegRes, NEG_FIVE, NoneExp, &
   'passing vuDot() bad level for negative result with u=v        ')

  call vect_Utils_dump_stats_static_m(STATS_FILE, &
   'After Precondition checking                                   ', &
   throwaway)


!     ****************************************************************
!     * Now check postconditions only.  Only need three checks:
!     *   1) successful execution;
!     *   2) precondition violation that gets caught; and
!     *   3) postcondition violation that is not caught.
!     ****************************************************************
  heading = '*** ENABLE POSTCONDITION ENFORCEMENT ONLY ***'
  call comment(tracker, heading)
  call sidl_EnfPolicy_setEnforceAll_m(POSTCONDS, .false., exception)
  call catch(tracker, exception)

  call runDot(tracker, partNum, u, z, TOL, NoVio, ZERO, NoneExp, &
   'ensuring dot product of the unit and zero vectors is 0.0      ')
  call runDot(tracker, partNum, u, u, NTOL, NoVio, ONE, NoneExp, &
   'passing vuDot() a negative tolerance                          ')
  call runDot(tracker, partNum, u, u, TOL, NegRes, FIVE, PostExp, &
   'passing vuDot() bad level for negative result with u=v        ')

  call vect_Utils_dump_stats_static_m(STATS_FILE, &
   'After Postcondition checking                                  ', &
   throwaway)


!     ***************************************************************
!     * Now make sure contract violations are not caught when 
!     * contract enforcement turned off.  Do this for each type of 
!     * violation for every method.
!     ***************************************************************
  heading = 'COMMENT: *** DISABLE ALL CONTRACT ENFORCEMENT ***'
  call comment(tracker, heading)
  call sidl_EnfPolicy_setEnforceNone_m(.false., exception)
  call catch(tracker, exception)

!
!     Since issue with null arrays in F77, try negative tolerance
!
  call runIsZero(tracker, partNum, n, TOL, .false., NoneExp, & 
   'passing vuIsZero() a null array - no precondition vio         ')
!  call runIsZero(tracker, partNum, n, NTOL, .false., NoneExp, &
!   'passing vuIsZero() a negative tolerance - no precondition vio ')

  call runIsUnit(tracker, partNum, n, TOL, .false., NoneExp, &
   'passing vuIsUnit() a null array - no precondition vio         ')
!  call runIsUnit(tracker, partNum, n, NTOL, .false., NoneExp, &
!   'passing vuIsUnit() a negative tolerance - no precondition vio ')

  call runAreEqual(tracker, partNum, n, u, TOL, .false., NoneExp, &
   'passing vuAreEqual() a null 1st array - no pre vio            ')
!  call runAreEqual(tracker, partNum, n, u, NTOL, .false., NoneExp, &
!   'passing vuAreEqual() a negative tolerance - no pre vio        ')

  call runAreOrth(tracker, partNum, n, u, TOL, .false., NoneExp, &
   'passing vuAreOrth() a null 1st array - no precondition vio    ')
!  call runAreOrth(tracker, partNum, n, u, NTOL, .false., NoneExp, &
!   'passing vuAreOrth() a negative tolerance - no pre vio         ')

  call runSchwarzHolds(tracker, partNum, n, z, TOL, .false., NoneExp, &
   'passing vuSchwarzHolds() a null 1st array - no pre vio        ')
!  call runSchwarzHolds(tracker, partNum, n, z, NTOL, .false., NoneExp, &
!   'passing vuSchwarzHolds() a negative tolerance - no pre vio    ')

  call runTriIneqHolds(tracker, partNum, n, u, TOL, .false., NoneExp, &
   'passing vuTriIneqHolds() a null 1st array - no pre vio        ')
!  call runTriIneqHolds(tracker, partNum, n, u, NTOL, .false., NoneExp, &
!   'passing vuTriIneqHolds() a negative tolerance - no pre vio    ')

  call runNorm(tracker, partNum, n, TOL, NoVio, ZERO, NoneExp, &
   'passing vuNorm() a null vector - no precondition vio          ')
!  call runNorm(tracker, partNum, n, NTOL, NoVio, ZERO, NoneExp, &
!   'passing vuNorm() a negative tolerance - no pre vio            ')

  call runNorm(tracker, partNum, u, TOL, NegRes, NEG_FIVE, NoneExp, &
   'passing vuNorm() badness level for negative result            ')

  call runDot(tracker, partNum, n, u, TOL, NoVio, ZERO, NoneExp, &
   'passing vuDot() a null 1st array - no precondition vio        ')
!  call runDot(tracker, partNum, n, u, TOL, NoVio, ZERO, NoneExp, &
!   'passing vuDot() a negative tolerance - no precondition vio    ')

  call runDot(tracker, partNum, u, u, TOL, NegRes, NEG_FIVE, NoneExp, &
   'passing vuDot() badness level for negative result with u=v    ')

  call runProduct(tracker, partNum, ZERO, n, TOL, NoVio, n, .true., NoneExp,&
   'passing vuProduct() a null array - no precondition vio        ')
!  call runProduct(tracker, partNum, ZERO, n, NTOL, NoVio, n, .true., NoneExp,&
!   'passing vuProduct() a negative tolerance - no precondition vio')

  call runProduct(tracker, partNum, ONE, u, TOL, NullRes, u, .false., NoneExp,&
   'passing vuProduct() badness level for null result             ')

  call runNegate(tracker, partNum, n, TOL, NoVio, n, .true., NoneExp, &
   'passing vuNegate() a null array - no precondition vio         ')
!  call runNegate(tracker, partNum, n, NTOL, NoVio, n, .true., NoneExp, &
!   'passing vuNegate() a negative tolerance - no precondition vio ')

  call runNegate(tracker, partNum, u, TOL, NullRes, nu, .false., NoneExp, &
   'passing vuNegate() badness level for null result              ')

  call runNormalize(tracker, partNum, n, TOL, NoVio, n, .true., NoneExp, &
   'passing vuNormalize() a null array - no precondition vio      ')
!  call runNormalize(tracker, partNum, n, NTOL, NoVio, n, .true., NoneExp, &
!   'passing vuNormalize() a negative tolerance - no pre vio       ')

  call runNormalize(tracker, partNum, u, TOL, NullRes, u, .false., NoneExp, &
   'passing vuNormalize() a badness level for null result - no post')

  call runSum(tracker, partNum, n, z, TOL, NoVio, n, .true., NoneExp, &
   'passing vuSum() a null 1st array - no postcondition vio       ')
!  call runSum(tracker, partNum, n, z, NTOL, NoVio, n, .true., NoneExp, &
!   'passing vuSum() a negative tolerance - no postcondition vio   ')

  call runSum(tracker, partNum, u, z, TOL, NullRes, u, .false., NoneExp, &
   'passing vuSum() a bad level for null result - no post          ')

  call runDiff(tracker, partNum, n, u, TOL, NoVio, n, .true., NoneExp, &
   'passing vuDiff() a null 1st array - no precondition vio       ')
!  call runDiff(tracker, partNum, n, u, NTOL, NoVio, n, .true., NoneExp, &
!   'passing vuDiff() a negative tolerance - no precondition vio   ')

  call runDiff(tracker, partNum, z, u, TOL, NullRes, nu, .false., NoneExp, &
   'passing vuDiff() bad level for null result - no post          ')

  call vect_Utils_dump_stats_static_m(STATS_FILE,&
   'After no checking                                             ',&
   throwaway)

  call deleteRef(t)
  call deleteRef(u)
  call deleteRef(nu)
  call deleteRef(u1)
  call deleteRef(z)

  call close(tracker, throwaway)
  call deleteRef(tracker, throwaway)

  stop
end program vectortest
