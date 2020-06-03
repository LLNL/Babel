C
C File:        vectortest.f
C Copyright:   (c) 2009 Lawrence Livermore National Security, LLC
C Revision:    @(#) $Revision: 6183 $
C Date:        $Date: 2007-10-15 14:41:51 -0700 (Mon, 15 Oct 2007) $
C Description: Contract Regression Test FORTRAN client
C
C

      subroutine catch(tracker, exc)
      implicit none
      integer*8 exc, classinfo, tracker, throwaway
      character*(1024) buffer, name
      if (exc .ne. 0) then
         call synch_RegOut_writeComment_f(tracker,
     $        'Unexpected exception thrown', throwaway)
         call sidl_BaseInterface_getClassInfo_f(exc, classinfo,
     $        throwaway)
         if (classinfo .ne. 0) then
            call sidl_ClassInfo_getName_f(classinfo, name, throwaway)
            buffer = 'Exception name: ' // name
            call synch_RegOut_writeComment_f(tracker, buffer,
     $           throwaway)
         endif
         call synch_RegOut_forceFailure_f(tracker, throwaway)
      endif
      end


      subroutine comment(tracker, msg)
      implicit none
      integer*8 tracker, throwaway
      character*(60) msg
      call synch_RegOut_writeComment_f(tracker, msg, throwaway)
      end

      subroutine createDouble(len, arr)
      implicit none
      integer*4 len, lower(1), upper(1)
      integer*8 arr
      arr = 0
      if (len .ge. 0) then
        lower(1) = 0
        upper(1) = len - 1
        call sidl_double__array_createRow_f(1, lower, upper, arr)
      endif
      end

      subroutine create2Double(len, arr)
      implicit none
      integer*4 len, lower(2), upper(2)
      integer*8 arr
      arr = 0
      if (len .ge. 0) then
        lower(1) = 0
        lower(2) = 0
        upper(1) = len - 1
        upper(2) = len - 1
        call sidl_double__array_createRow_f(2, lower, upper, arr)
      endif
      end

      subroutine debug(msg)
      implicit none
      character*(62) msg
      write (6, 100) msg
 100  format ('DEBUG: ', 1x, a)
      end

      subroutine describeTest(tracker, partNum, desc)
      implicit none
      integer*8 tracker
      integer*4 partNum
      character*(62) desc
      integer*8 throwaway
      partNum = partNum + 1
      call synch_RegOut_startPart_f(tracker, partNum, throwaway)
      call synch_RegOut_writeComment_f(tracker, desc, throwaway)
      end

C      subroutine reportExc(tracker, exc)
C      implicit none
C      integer*8 tracker, exc, sidlex
C      character*(60) msg
C      character*(1024) trace
C      integer*8 throwaway
C      call sidl_SIDLException__cast_f(exc, sidlex, throwaway)
C      call catch(tracker, throwaway)
C      call sidl_SIDLException_getNote_f(sidlex, msg, throwaway)
C      call catch(tracker, throwaway)
C      write (6, 100) msg
C      call sidl_SIDLException_getTrace_f(sidlex, trace, throwaway)
C      call catch(tracker, throwaway)
C      write (6, 110) trace
C      call sidl_SIDLException_deleteRef_f(sidlex, throwaway)
C      call catch(tracker, throwaway)
C 100  format (1x, a)
C 110  format (1x, a)
C      end

C
C  Values of constants in upper case MUST match values in calling 
C  routine(s).
C
      subroutine evalExcept(tracker, partNum, exc, expected)

      implicit none
      integer*8 tracker, expected
      integer*4 partNum
      integer*8 exc, exceptType, classinfo, throwaway
      logical isType
      include 'vect_ExpectExcept.inc'
      include 'synch_ResultType.inc'

      exceptType = NoneExp
      isType = .false.
      if (exc .ne. 0) then
         call sidl_BaseInterface_isType_f(exc,
     $        'sidl.PreViolation', isType, throwaway)
         call catch(tracker, throwaway)
         if (isType .eqv. .true.) then
            exceptType = PreExp
         else
            call sidl_BaseInterface_isType_f(exc,
     $          'sidl.PostViolation', isType, throwaway)
            call catch(tracker, throwaway)
            if (isType .eqv. .true.) then
               exceptType = PostExp
            endif
            if (isType .eqv. .false.) then
               call sidl_BaseInterface_isType_f(exc,
     $            'vect.vDivByZeroExcept', isType, throwaway)
               call catch(tracker, throwaway)
               if (isType .eqv. .true.) then
                 exceptType = DBZExp
               endif
            endif
            if (isType .eqv. .false.) then
               call sidl_BaseInterface_isType_f(exc,
     $            'vect.vNegValExcept', isType, throwaway)
               call catch(tracker, throwaway)
               if (isType .eqv. .true.) then
                  exceptType = NVEExp
               endif
            endif
            if (isType .eqv. .false.) then
               call sidl_BaseInterface_isType_f(exc,
     $            'vect.vExcept', isType, throwaway)
               call catch(tracker, throwaway)
               if (isType .eqv. .true.) then
                  exceptType = ExcExp
               endif
            endif
         endif 
C          call reportExc(tracker, exc)
C         call sidl_SIDLException_deleteRef_f(exc, throwaway)
      else
         exceptType = NoneExp
      endif

      if (exceptType .eq. expected) then
        call synch_RegOut_endPart_f(tracker, partNum, 
     $                              PASS, throwaway)
        call catch(tracker, throwaway)
      else
        call synch_RegOut_endPart_f(tracker, partNum, 
     $                              FAIL, throwaway)
        call catch(tracker, throwaway)
      endif
      end

      subroutine evalResB(tracker, partNum, res, expected)
      implicit none
      integer*8 tracker
      integer*4 partNum
      logical res, expected
      integer*8 throwaway
      include 'synch_ResultType.inc'

      if (res .eqv. expected) then
         call synch_RegOut_endPart_f(tracker, partNum, PASS,
     $        throwaway)
      else
         call synch_RegOut_endPart_f(tracker, partNum, FAIL,
     $        throwaway)
      endif
      call catch(tracker, throwaway)
      end

      subroutine evalResA(tracker, partNum, res, expected, tol, okay)
      implicit none
      integer*8 tracker, res, expected
      integer*4 partNum
      logical okay, ok
      integer*8 exc, throwaway
      double precision tol
      include 'vect_ExpectExcept.inc'
      include 'synch_ResultType.inc'

      if (res .ne. 0) then
        if (expected .ne. 0) then
          call vect_Utils_vuAreEqual_f(res, expected, tol, ok, exc)
          if (exc .ne. 0) then
            call catch(tracker, exc)
            ok = .false.
          endif 
        else
          ok = .false.
        endif
      else
        if (expected .ne. 0) then
          ok = .false.
        else
          ok = .true.
        endif
      endif
      call evalResB(tracker, partNum, ok, okay)
      end

      subroutine evalResD(tracker, partNum, res, expected, tol)
      implicit none
      integer*8 tracker
      integer*4 partNum
      double precision res, expected, tol
      integer*8 throwaway
      include 'synch_ResultType.inc'

      if ((abs(res) - abs(expected)) .le. abs(tol)) then
         call synch_RegOut_endPart_f(tracker, partNum, PASS,
     $        throwaway)
         call catch(tracker, throwaway)
      else
         call synch_RegOut_endPart_f(tracker, partNum, FAIL,
     $        throwaway)
         call catch(tracker, throwaway)
      endif
      end

      subroutine failNoExcept(tracker, partNum, expectExc)
      implicit none
      integer*8 tracker, expectExc
      integer*4 partNum
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'
      include 'synch_ResultType.inc'

      if (expectExc .ne. NoneExp) then
        call synch_RegOut_endPart_f(tracker, partNum, FAIL,
     $       throwaway)
      endif
      call catch(tracker, throwaway)
      end

      subroutine reporttest(tracker, test, partNum)
      implicit none
      integer*4 partNum
      integer*8 tracker
      logical test
      integer*8 throwaway
      include 'synch_ResultType.inc'

      if (test) then
         call synch_RegOut_endPart_f(tracker, partNum, PASS,
     $        throwaway)
      else
         call synch_RegOut_endPart_f(tracker, partNum, FAIL,
     $        throwaway)
      endif
      call catch(tracker, throwaway)
      end

C
C ****************************************************************
C *                  Test Routines
C ****************************************************************
C
      subroutine runIsZero(tracker, partNum, v, tol, res, expectExc, 
     $                     desc)
      implicit none
      integer*8 tracker, v, expectExc
      integer*4 partNum
      double precision tol
      logical res
      character*(62) desc
      integer*8 exc
      logical x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuIsZero_f(v, tol, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResB(tracker, partNum, x, res)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runIsUnit(tracker, partNum, v, tol, res, expectExc, 
     $                     desc)
      implicit none
      integer*8 tracker, v, expectExc
      integer*4 partNum
      double precision tol
      logical res
      character*(62) desc
      integer*8 exc
      logical x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuIsUnit_f(v, tol, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResB(tracker, partNum, x, res)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runAreEqual(tracker, partNum, u, v, tol, res, 
     $                       expectExc, desc)
      implicit none
      integer*8 tracker, u, v, expectExc
      integer*4 partNum
      double precision tol
      logical res
      character*(62) desc
      integer*8 exc
      logical x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuAreEqual_f(u, v, tol, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResB(tracker, partNum, x, res)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runAreOrth(tracker, partNum, u, v, tol, res, 
     $                      expectExc, desc)
      implicit none
      integer*8 tracker, u, v, expectExc
      integer*4 partNum
      double precision tol
      logical res
      character*(62) desc
      integer*8 exc
      logical x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuAreOrth_f(u, v, tol, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResB(tracker, partNum, x, res)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runSchwarzHolds(tracker, partNum, u, v, tol, res, 
     $                           expectExc, desc)
      implicit none
      integer*8 tracker, u, v, expectExc
      integer*4 partNum
      double precision tol
      logical res
      character*(62) desc
      integer*8 exc
      logical x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuSchwarzHolds_f(u, v, tol, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResB(tracker, partNum, x, res)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runTriIneqHolds(tracker, partNum, u, v, tol, res, 
     $                           expectExc, desc)
      implicit none
      integer*8 tracker, u, v, expectExc
      integer*4 partNum
      double precision tol
      logical res
      character*(62) desc
      integer*8 exc
      logical x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuTriIneqHolds_f(u, v, tol, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResB(tracker, partNum, x, res)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runNorm(tracker, partNum, u, tol, badLvl, res, 
     $                   expectExc, desc)
      implicit none
      integer*8 tracker, u, expectExc
      integer*4 partNum
      double precision tol, res
      character*(62) desc
      integer*8 badLvl, exc
      double precision x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuNorm_f(u, tol, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResD(tracker, partNum, x, res, tol)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


      subroutine runDot(tracker, partNum, u, v, tol, badLvl, res, 
     $                  expectExc, desc)
      implicit none
      integer*8 tracker, u, v, expectExc
      integer*4 partNum
      double precision tol, res
      character*(62) desc
      integer*8 badLvl, exc
      double precision x
      integer*8 throwaway
      include 'vect_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuDot_f(u, v, tol, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResD(tracker, partNum, x, res, tol)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      end


C  NOTE: Tolerance is only needed to check the result of the call.
      subroutine runProduct(tracker, partNum, a, u, tol, badLvl, res, 
     $                  okay, expectExc, desc)
      implicit none
      integer*8 tracker, u, res, expectExc
      integer*4 partNum
      double precision a, tol
      character*(62) desc
      integer*8 badLvl, x, exc
      logical okay
      integer*8 throwaway
      include 'vect_BadLevel.inc'
      include 'vect_ExpectExcept.inc'

      x = 0
      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuProduct_f(a, u, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResA(tracker, partNum, x, res, tol, okay)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      if ((badLvl .gt. NegRes) .and. (x .ne. 0)) then
        call sidl_double__array_deleteRef_f(x, throwaway)
      endif
      end


C  NOTE: Tolerance is only needed to check the result of the call.
      subroutine runNegate(tracker, partNum, u, tol, badLvl, res, 
     $                     okay, expectExc, desc)
      implicit none
      integer*8 tracker, u, res, expectExc
      integer*4 partNum
      double precision tol
      character*(62) desc
      integer*8 badLvl, x, exc
      logical okay
      integer*8 throwaway
      include 'vect_BadLevel.inc'
      include 'vect_ExpectExcept.inc'

      x = 0
      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuNegate_f(u, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResA(tracker, partNum, x, res, tol, okay)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      if ((badLvl .gt. NegRes) .and. (x .ne. 0)) then
        call sidl_double__array_deleteRef_f(x, throwaway)
      endif
      end


      subroutine runNormalize(tracker, partNum, u, tol, badLvl, res, 
     $                        okay, expectExc, desc)
      implicit none
      integer*8 tracker, u, res, expectExc
      integer*4 partNum
      double precision tol
      character*(62) desc
      integer*8 badLvl, x, exc
      logical okay
      integer*8 throwaway
      include 'vect_BadLevel.inc'
      include 'vect_ExpectExcept.inc'

      x = 0
      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuNormalize_f(u, tol, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResA(tracker, partNum, x, res, tol, okay)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
C      if ((badLvl .gt. NegRes) .and. (x .ne. 0)) then
C        call sidl_double__array_deleteRef_f(x, throwaway)
C      endif
      end


C  NOTE: Tolerance is only needed to check the result of the call.
      subroutine runSum(tracker, partNum, u, v, tol, badLvl, res, 
     $                  okay, expectExc, desc)
      implicit none
      integer*8 tracker, u, v, res, expectExc
      integer*4 partNum
      double precision tol
      character*(62) desc
      integer*8 badLvl, x, exc
      logical okay
      integer*8 throwaway
      include 'vect_BadLevel.inc'
      include 'vect_ExpectExcept.inc'

      x = 0
      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuSum_f(u, v, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResA(tracker, partNum, x, res, tol, okay)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      if ((badLvl .gt. NegRes) .and. (x .ne. 0)) then
        call sidl_double__array_deleteRef_f(x, throwaway)
      endif
      end


C  NOTE: Tolerance is only needed to check the result of the call.
      subroutine runDiff(tracker, partNum, u, v, tol, badLvl, res, 
     $                   okay, expectExc, desc)
      implicit none
      integer*8 tracker, u, v, res, expectExc
      integer*4 partNum
      double precision tol
      character*(62) desc
      integer*8 badLvl, x, exc
      logical okay
      integer*8 throwaway
      include 'vect_BadLevel.inc'
      include 'vect_ExpectExcept.inc'

      x = 0
      call describeTest(tracker, partNum, desc)
      call vect_Utils_vuDiff_f(u, v, badLvl, x, exc)
      if (exc .eq. 0) then
        if (expectExc .eq. NoneExp) then
          call evalResA(tracker, partNum, x, res, tol, okay)
        else 
          call failNoExcept(tracker, partNum, expectExc)
        endif
      else
        call evalExcept(tracker, partNum, exc, expectExc) 
      endif
      if ((badLvl .gt. NegRes) .and. (x .ne. 0)) then
        call sidl_double__array_deleteRef_f(x, throwaway)
      endif
      end


C
C     ***************************************************************
C     ***************************************************************
C     **  MAIN:  vectortest
C     ***************************************************************
C     ***************************************************************
C
      program vectortest
      implicit none
      character*25 STATS_FILE
      integer*4 MAX_SIZE
      double precision TOL, NTOL, VAL, NVAL, SQRT_SIZE, MAX_SIZED
      integer*4 PARTS
      integer*4 partNum
      character*60 heading
      integer*8 tracker, exception, throwaway
      integer*8 t, u, u1, nu, z, n
      integer*4 i, j
      double precision ONE, FIVE, NEG_FIVE, NEG_ONE, TWO, ZERO
      include 'vect_BadLevel.inc'
      include 'vect_ExpectExcept.inc'
      include 'sidl_ContractClass.inc'

      STATS_FILE = 'VUtils.stats'

C     Total tests minus those attempting to pass a null input array 
C     argument (that have not been replaced with alternate argument,
C     where applicable).
      PARTS = 128 - 20
      MAX_SIZE = 6
      MAX_SIZED = 6.0
      SQRT_SIZE = sqrt(MAX_SIZED)

      TOL = 1.0e-9
      NTOL = -1.0e-9
      VAL = 1.0 / SQRT_SIZE
      NVAL = -1.0 / SQRT_SIZE

      FIVE = 5.0
      ONE = 1.0
      TWO = 2.0
      NEG_FIVE = -5.0
      NEG_ONE = -1.0
      ZERO = 0.0

      partNum = 0

      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_setExpectations_f(tracker, PARTS, throwaway)

C
C     Initialize test vectors
C
      call createDouble(MAX_SIZE, u)
      call createDouble(MAX_SIZE+1, u1)
      call createDouble(MAX_SIZE, nu)
      call createDouble(MAX_SIZE, z)
      call create2Double(MAX_SIZE, t)

      n = 0
      do i = 0, MAX_SIZE - 1
        call sidl_double__array_set1_f(u, i, VAL)
        call sidl_double__array_set1_f(u1, i, VAL)
        call sidl_double__array_set1_f(nu, i, NVAL)
        call sidl_double__array_set1_f(z, i, ZERO)
        do j = 0, MAX_SIZE - 1
          call sidl_double__array_set2_f(t, i, j, VAL)
        enddo
      enddo
      call sidl_double__array_set1_f(u1, MAX_SIZE, VAL)

C
C     Establish initial enforcement options
C
      heading = '*** ENABLE FULL CONTRACT CHECKING ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceAll_f(ALLCLASSES, .true., 
     $                                    exception)
      call catch(tracker, exception)

C
C     vuIsZero() set
C
      call runIsZero(tracker, partNum, z, tol, .true., NoneExp,
     $ 'ensuring the zero vector is the zero vector                   ')
      call runIsZero(tracker, partNum, u, tol, .false., 
     $ NoneExp,
     $ 'ensuring the unit vector is not the zero vector               ')
C      call runIsZero(tracker, partNum, n, tol, .false., PreExp,
C     $ 'passing vuIsZero() a null array                               ')
      call runIsZero(tracker, partNum, t, tol, .false., PreExp,
     $ 'passing vuIsZero() a 2D array                                 ')
      call runIsZero(tracker, partNum, z, ntol, .false., PreExp,
     $ 'passing vuIsZero() a negative tolerance                       ')

C
C     vuIsUnit() set
C

      call runIsUnit(tracker, partNum, u, tol, .true., NoneExp, 
     $ 'ensuring the unit vector is the unit vector                  ')
      call runIsUnit(tracker, partNum, z, tol, .false., NoneExp,
     $ 'ensuring the zero vector is not the unit vector              ')
C      call runIsUnit(tracker, partNum, n, tol, .false., PreExp, 
C     $ 'passing vuIsUnit() a null array                              ')
      call runIsUnit(tracker, partNum, t, tol, .false., PreExp, 
     $ 'passing vuIsUnit() a 2D array                                ')
      call runIsUnit(tracker, partNum, u, ntol, .false., PreExp, 
     $ 'passing vuIsUnit() a negative tolerance                      ')

C
C     vuAreEqual() set
C

      call runAreEqual(tracker, partNum, u, z, tol, .false., 
     $ NoneExp, 
     $ 'ensuring the unit and zero vectors are not equal              ')
      call runAreEqual(tracker, partNum, u, u, tol, .true., 
     $ NoneExp, 
     $ 'ensuring the unit vector is equal to itself                   ')
C      call runAreEqual(tracker, partNum, n, u, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuAreEqual() a null 1st array                         ')
      call runAreEqual(tracker, partNum, t, u, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreEqual() a 2D 1st array                           ')
C      call runAreEqual(tracker, partNum, u, n, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuAreEqual() a null 2nd array                         ')
      call runAreEqual(tracker, partNum, u, t, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreEqual() a 2D 2nd array                           ')
      call runAreEqual(tracker, partNum, u, u1, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreEqual() different sized arrays                   ')
      call runAreEqual(tracker, partNum, u, u, ntol, .false., 
     $ PreExp, 
     $ 'passing vuAreEqual() a negative tolerance                     ')

C
C     vuAreOrth() set
C
      call runAreOrth(tracker, partNum, u, z, tol, .true., 
     $ NoneExp, 
     $ 'ensuring the unit and zero vectors are orthogonal             ')
      call runAreOrth(tracker, partNum, u, nu, tol, .false., 
     $ NoneExp, 
     $ 'ensuring unit and negative unit vectors are not orthogonal    ')
C      call runAreOrth(tracker, partNum, n, u, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuAreOrth() a null 1st array                          ')
      call runAreOrth(tracker, partNum, t, u, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreOrth() a 2D 1st array                            ')
C      call runAreOrth(tracker, partNum, u, n, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuAreOrth() a null 2nd array                          ')
      call runAreOrth(tracker, partNum, u, t, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreOrth() a 2D 2nd array                            ')
      call runAreOrth(tracker, partNum, u, u1, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreOrth() different sized unit arrays               ')
      call runAreOrth(tracker, partNum, u, u, ntol, .false., 
     $ PreExp, 
     $ 'passing vuAreOrth() a negative tolerance                      ')
      call runAreOrth(tracker, partNum, t, t, tol, .false., 
     $ PreExp, 
     $ 'passing vuAreOrth() 2D arrays in both arguments               ')

C
C     vuSchwarzHolds() set
C
      call runSchwarzHolds(tracker, partNum, u, z, tol, .true., 
     $ NoneExp, 
     $ 'ensuring schwarz holds for the unit and zero vectors          ')
C      call runSchwarzHolds(tracker, partNum, u, z, ntol, .false., 
C     $ PreExp, 
C     $ 'passing vuSchwarzHolds() a null 1st array                     ')
      call runSchwarzHolds(tracker, partNum, t, z, tol, .false., 
     $ PreExp, 
     $ 'passing vuSchwarzHolds() a 2D 1st array                       ')
C      call runSchwarzHolds(tracker, partNum, z, n, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuSchwarzHolds() a null 2nd array                     ')
      call runSchwarzHolds(tracker, partNum, u, t, tol, .false., 
     $ PreExp, 
     $ 'passing vuSchwarzHolds() a 2D 2nd array                       ')
      call runSchwarzHolds(tracker, partNum, u, u1, tol, .false., 
     $ PreExp, 
     $ 'passing vuSchwarzHolds() different sized unit arrays          ')
      call runSchwarzHolds(tracker, partNum, u, z, ntol, .false., 
     $ PreExp, 
     $ 'passing vuSchwarzHolds() a negative tolerance                 ')

C
C     vuTriIneqHolds() set
C
      call runTriIneqHolds(tracker, partNum, u, z, tol, .true., 
     $ NoneExp, 
     $ 'ensuring triangle ineq. holds for unit and zero vectors       ')
C      call runTriIneqHolds(tracker, partNum, n, u, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuTriIneqHolds() a null 1st array                     ')
      call runTriIneqHolds(tracker, partNum, t, u, tol, .false., 
     $ PreExp, 
     $ 'passing vuTriIneqHolds() a 2D 1st array                       ')
C      call runTriIneqHolds(tracker, partNum, u, n, tol, .false., 
C     $ PreExp, 
C     $ 'passing vuTriIneqHolds() a null 2nd array                     ')
      call runTriIneqHolds(tracker, partNum, u, t, tol, .false., 
     $ PreExp, 
     $ 'passing vuTriIneqHolds() a 2D 2nd array                       ')
      call runTriIneqHolds(tracker, partNum, u, u1, tol, .false., 
     $ PreExp, 
     $ 'passing vuTriIneqHolds() different sized unit vectors         ')
      call runTriIneqHolds(tracker, partNum, u, u, ntol, .false., 
     $ PreExp, 
     $ 'passing vuTriIneqHolds() a negative tolerance                 ')

C
C     vuNorm() set
C
      call runNorm(tracker, partNum, u, tol, NoVio, ONE,
     $ NoneExp, 
     $ 'ensuring the unit vector norm is 1.0                          ')
C      call runNorm(tracker, partNum, n, tol, NoVio, ZERO,
C     $ PreExp, 
C     $ 'passing vuNorm() a null vector                                ')
      call runNorm(tracker, partNum, t, tol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuNorm() a 2D array                                   ')
      call runNorm(tracker, partNum, u, ntol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuNorm() a negative tolerance                         ')
      call runNorm(tracker, partNum, u, tol, NegRes, NEG_FIVE, 
     $ PostExp, 
     $ 'passing vuNorm() badness level for negative result            ')
      call runNorm(tracker, partNum, z, tol, PosRes, FIVE,
     $ PostExp, 
     $ 'passing vuNorm() bad level for pos result with zero vec       ')
      call runNorm(tracker, partNum, u, tol, ZeroRes, ZERO,
     $ PostExp, 
     $ 'passing vuNorm() bad level for zero result with non-zero      ')

C
C     vuDot() set
C
      call runDot(tracker, partNum, u, z, tol, NoVio, ZERO,
     $ NoneExp, 
     $ 'ensuring dot product of the unit and zero vectors is 0.0      ')
C      call runDot(tracker, partNum, n, u, tol, NoVio, ZERO,
C     $ PreExp, 
C     $ 'passing vuDot() a null 1st array                              ')
      call runDot(tracker, partNum, t, u, tol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuDot() a 2D 1st array                                ')
C      call runDot(tracker, partNum, u, n, tol, NoVio, ZERO,
C     $ PreExp, 
C     $ 'passing vuDot() a null 2nd array                              ')
      call runDot(tracker, partNum, u, t, tol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuDot() a 2D 2nd array                                ')
      call runDot(tracker, partNum, u, u1, tol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuDot() different sized unit vectors                  ')
      call runDot(tracker, partNum, u, u, ntol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuDot() a negative tolerance                          ')
      call runDot(tracker, partNum, t, t, tol, NoVio, ZERO,
     $ PreExp, 
     $ 'passing vuDot() a 2D arrays in both arguments                 ')
      call runDot(tracker, partNum, u, u, tol, NegRes, NEG_FIVE,
     $ PostExp, 
     $ 'passing vuDot() bad level for negative result with u=v        ')
      call runDot(tracker, partNum, z, z, tol, PosRes, FIVE,
     $ PostExp, 
     $ 'passing vuDot() bad level for positive result with u=v=0      ')

C
C     vuProduct() set
C
      call runProduct(tracker, partNum, ONE, u, tol, NoVio, 
     $ u, .true., NoneExp, 
     $ 'ensuring product of 1 and unit vector is the unit vector      ')
      call runProduct(tracker, partNum, TWO, u, tol, NoVio, 
     $ u, .false., NoneExp, 
     $ 'ensuring product of 2 and unit vector is not unit vector      ')
C      call runProduct(tracker, partNum, ZERO, n, tol, NoVio, 
C     $ n, .true., PreExp, 
C     $ 'passing vuProduct() a null array                              ')
      call runProduct(tracker, partNum, ONE, t, tol, NoVio, 
     $ u, .false., PreExp, 
     $ 'passing vuProduct() a 2D array                                ')
      call runProduct(tracker, partNum, ONE, u, tol, NullRes, 
     $ u, .true., PostExp, 
     $ 'passing vuProduct() badness level for null result             ')
      call runProduct(tracker, partNum, ONE, u, tol, TwoDRes, 
     $ u, .true., PostExp, 
     $ 'passing vuProduct() badness level for 2D result               ')
      call runProduct(tracker, partNum, ONE, u, tol, WrongSizeRes, 
     $ u, .true., PostExp, 
     $ 'passing vuProduct() bad level for wrong result size           ')

C
C     vuNegate() set
C
      call runNegate(tracker, partNum, u, tol, NoVio, 
     $ nu, .true., NoneExp, 
     $ 'ensuring negation of the the unit vector is its negative      ')
      call runNegate(tracker, partNum, u, tol, NoVio, 
     $ u, .false., NoneExp, 
     $ 'ensuring negation of unit vector is not the unit vector       ')
C      call runNegate(tracker, partNum, n, tol, NoVio, 
C     $ nu, .true., PreExp, 
C     $ 'passing vuNegate() a null array                               ')
      call runNegate(tracker, partNum, t, tol, NoVio, 
     $ nu, .false., PreExp, 
     $ 'passing vuNegate() a 2D array                                 ')
      call runNegate(tracker, partNum, u, tol, NullRes, 
     $ nu, .true., PostExp, 
     $ 'passing vuNegate() badness level for null result              ')
      call runNegate(tracker, partNum, u, tol, TwoDRes, 
     $ nu, .true., PostExp, 
     $ 'passing vuNegate() badness level for 2D result                ')
      call runNegate(tracker, partNum, u, tol, WrongSizeRes, 
     $ nu, .true., PostExp, 
     $ 'passing vuNegate() bad level for wrong result size            ')

C
C     vuNormalize() set
C
      call runNormalize(tracker, partNum, u, tol, NoVio, 
     $ u, .true., NoneExp, 
     $ 'ensuring normalize of the unit vector is itself               ')
      call runNormalize(tracker, partNum, u, tol, NoVio, 
     $ nu, .false., NoneExp, 
     $ 'ensuring normalize of the unit vector is not its negative     ')
      call runNormalize(tracker, partNum, z, tol, NoVio, 
     $ z, .true., DBZExp, 
     $ 'ensuring normalize of zero vector raises a DBZ exception      ')
C      call runNormalize(tracker, partNum, n, tol, NoVio, 
C     $ n, .true., PreExp, 
C     $ 'passing vuNormalize() a null array                            ')
      call runNormalize(tracker, partNum, t, tol, NoVio, 
     $ u, .false., PreExp, 
     $ 'passing vuNormalize() a 2D array                              ')
      call runNormalize(tracker, partNum, u, ntol, NoVio, 
     $ u, .true., PreExp, 
     $ 'passing vuNormalize() a neg tolerance using unit vector       ')
      call runNormalize(tracker, partNum, u, tol, NullRes, 
     $ u, .true., PostExp, 
     $ 'passing vuNormalize() badness level for null result           ')
      call runNormalize(tracker, partNum, u, tol, TwoDRes, 
     $ u, .true., PostExp, 
     $ 'passing vuNormalize() badness level for 2D result             ')
      call runNormalize(tracker, partNum, u, tol, WrongSizeRes, 
     $ u, .true., PostExp, 
     $ 'passing vuNormalize() bad level for wrong result size         ')

C
C     vuSum() set  (NOTE:  Tolerance not relevant to vuSum() API.)
C
      call runSum(tracker, partNum, u, z, tol, NoVio, 
     $ u, .true., NoneExp, 
     $ 'ensuring sum of unit and zero vectors is unit vector          ')
      call runSum(tracker, partNum, u, z, tol, NoVio, 
     $ nu, .false., NoneExp, 
     $ 'ensuring sum of unit and zero vectors is not neg of unit      ')
C      call runSum(tracker, partNum, n, z, tol, NoVio, 
C     $ n, .true., PreExp, 
C     $ 'passing vuSum() a null 1st array                              ')
      call runSum(tracker, partNum, t, n, tol, NoVio, 
     $ n, .false., PreExp, 
     $ 'passing vuSum() a 2D 1st array                                ')
C      call runSum(tracker, partNum, u, n, tol, NoVio, 
C     $ n, .true., PreExp, 
C     $ 'passing vuSum() a null 2nd array                              ')
      call runSum(tracker, partNum, u, t, tol, NoVio, 
     $ n, .false., PreExp, 
     $ 'passing vuSum() a 2D as second                                ')
      call runSum(tracker, partNum, u, u1, tol, NoVio, 
     $ z, .true., PreExp, 
     $ 'passing vuSum() different sized unit vectors                  ')
      call runSum(tracker, partNum, u, z, tol, NullRes, 
     $ u, .true., PostExp, 
     $ 'passing vuSum() badness level for null result                 ')
      call runSum(tracker, partNum, u, z, tol, TwoDRes, 
     $ u, .true., PostExp, 
     $ 'passing vuSum() badness level for 2D result                   ')
      call runSum(tracker, partNum, u, z, tol, WrongSizeRes, 
     $ u, .true., PostExp, 
     $ 'passing vuSum() bad level for wrong result size               ')

C
C     vuDiff() set  (NOTE:  Tolerance not relevant to vuSum() API.)
C
      call runDiff(tracker, partNum, z, u, tol, NoVio, 
     $ nu, .true., NoneExp, 
     $ 'ensuring diff of zero and unit vectors is neg unit vector     ')
      call runDiff(tracker, partNum, u, z, tol, NoVio, 
     $ u, .true., NoneExp, 
     $ 'ensuring diff of unit and zero vectors is unit vector         ')
      call runDiff(tracker, partNum, u, z, tol, NoVio, 
     $ nu, .false., NoneExp, 
     $ 'ensuring diff of unit and zero is not neg unit vector         ')
C      call runDiff(tracker, partNum, n, u, tol, NoVio, 
C     $ n, .true., PreExp, 
C     $ 'passing vuDiff() a null 1st array                             ')
      call runDiff(tracker, partNum, t, u, tol, NoVio, 
     $ u, .false., PreExp, 
     $ 'passing vuDiff() a 2D 1st array                               ')
C      call runDiff(tracker, partNum, u, n, tol, NoVio, 
C     $ n, .true., PreExp, 
C     $ 'passing vuDiff() a null 2nd array                             ')
      call runDiff(tracker, partNum, u, t, tol, NoVio, 
     $ u, .false., PreExp, 
     $ 'passing vuDiff() a 2D 2nd array                               ')
      call runDiff(tracker, partNum, u, u1, tol, NegRes, 
     $ u, .false., PreExp, 
     $ 'passing vuDiff() different sized vectors                      ')
      call runDiff(tracker, partNum, z, u, tol, NullRes, 
     $ nu, .true., PostExp, 
     $ 'passing vuDiff() badness level for null result                ')
      call runDiff(tracker, partNum, z, u, tol, TwoDRes, 
     $ nu, .true., PostExp, 
     $ 'passing vuDiff() badness level for 2D result                  ')
      call runDiff(tracker, partNum, z, u, tol, WrongSizeRes, 
     $ nu, .true., PostExp, 
     $ 'passing vuDiff() bad level for wrong result size              ')

      call vect_Utils_dump_stats_static_f(STATS_FILE,
     $ 'After full checking                                           ',
     $ throwaway)
      call catch(tracker, throwaway)


C     ****************************************************************
C     * Now check preconditions only.  Only need three checks:
C     *   1) successful execution;
C     *   2) precondition violation that is not caught but is
C     *      okay anyway; and
C     *   3) postcondition violation that is caught.
C     ****************************************************************
      heading = '*** ENABLE PRECONDITION ENFORCEMENT ONLY ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceAll_f(PRECONDS, .false., exception)
      call catch(tracker, exception)

      call runDot(tracker, partNum, u, z, tol, NoVio, 
     $ ZERO, NoneExp, 
     $ 'ensuring dot product of the unit and zero vectors is 0.0      ')
      call runDot(tracker, partNum, u, u, ntol, NoVio, 
     $ ONE, PreExp, 
     $ 'passing vuDot() a negative tolerance                          ')
      call runDot(tracker, partNum, u, u, tol, NegRes, 
     $ NEG_FIVE, NoneExp, 
     $ 'passing vuDot() bad level for negative result with u=v        ')

      call vect_Utils_dump_stats_static_f(STATS_FILE,
     $ 'After Precondition checking                                   ',
     $ throwaway)
      call catch(tracker, throwaway)


C     ****************************************************************
C     * Now check postconditions only.  Only need three checks:
C     *   1) successful execution;
C     *   2) precondition violation that gets caught; and
C     *   3) postcondition violation that is not caught.
C     ****************************************************************
      heading = '*** ENABLE POSTCONDITION ENFORCEMENT ONLY ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceAll_f(POSTCONDS, .false., 
     $                                    exception)
      call catch(tracker, exception)

      call runDot(tracker, partNum, u, z, tol, NoVio, ZERO,
     $ NoneExp, 
     $ 'ensuring dot product of the unit and zero vectors is 0.0      ')
      call runDot(tracker, partNum, u, u, ntol, NoVio, ONE,
     $ NoneExp, 
     $ 'passing vuDot() a negative tolerance                          ')
      call runDot(tracker, partNum, u, u, tol, NegRes, FIVE,
     $ PostExp, 
     $ 'passing vuDot() bad level for negative result with u=v        ')

      call vect_Utils_dump_stats_static_f(STATS_FILE,
     $ 'After Postcondition checking                                  ',
     $ throwaway)
      call catch(tracker, throwaway)


C     ***************************************************************
C     * Now make sure contract violations are not caught when 
C     * contract enforcement turned off.  Do this for each type of 
C     * violation for every method.
C     ***************************************************************
      heading = 'COMMENT: *** DISABLE ALL CONTRACT ENFORCEMENT ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceNone_f(.false., exception)
      call catch(tracker, exception)

C
C     Since issue with null arrays in F77, try negative tolerance
C
C      call runIsZero(tracker, partNum, n, tol, .false., NoneExp,
C     $ 'passing vuIsZero() a null array - no precondition vio         ')
      call runIsZero(tracker, partNum, n, ntol, .false., 
     $ NoneExp,
     $ 'passing vuIsZero() a negative tolerance - no precondition vio ')

C      call runIsUnit(tracker, partNum, n, tol, .false., NoneExp,
C     $ 'passing vuIsUnit() a null array - no precondition vio         ')
      call runIsUnit(tracker, partNum, n, ntol, .false., 
     $ NoneExp,
     $ 'passing vuIsUnit() a negative tolerance - no precondition vio ')

C      call runAreEqual(tracker, partNum, n, u, tol, .false.,
C     $ NoneExp, 
C     $ 'passing vuAreEqual() a null 1st array - no pre vio            ')
      call runAreEqual(tracker, partNum, n, u, ntol, .false.,
     $ NoneExp, 
     $ 'passing vuAreEqual() a negative tolerance - no pre vio        ')

C      call runAreOrth(tracker, partNum, n, u, tol, .false., 
C     $ NoneExp, 
C     $ 'passing vuAreOrth() a null 1st array - no precondition vio    ')
      call runAreOrth(tracker, partNum, n, u, ntol, .false., 
     $ NoneExp, 
     $ 'passing vuAreOrth() a negative tolerance - no pre vio         ')

C      call runSchwarzHolds(tracker, partNum, n, z, tol, .false., 
C     $ NoneExp, 
C     $ 'passing vuSchwarzHolds() a null 1st array - no pre vio        ')
      call runSchwarzHolds(tracker, partNum, n, z, ntol, .false., 
     $ NoneExp, 
     $ 'passing vuSchwarzHolds() a negative tolerance - no pre vio    ')

C      call runTriIneqHolds(tracker, partNum, n, u, tol, .false.,
C     $ NoneExp, 
C     $ 'passing vuTriIneqHolds() a null 1st array - no pre vio        ')
      call runTriIneqHolds(tracker, partNum, n, u, ntol, .false.,
     $ NoneExp, 
     $ 'passing vuTriIneqHolds() a negative tolerance - no pre vio    ')

C      call runNorm(tracker, partNum, n, tol, NoVio, ZERO,
C     $ NoneExp, 
C     $ 'passing vuNorm() a null vector - no precondition vio          ')
      call runNorm(tracker, partNum, n, ntol, NoVio, ZERO,
     $ NoneExp, 
     $ 'passing vuNorm() a negative tolerance - no pre vio            ')

      call runNorm(tracker, partNum, u, tol, NegRes, NEG_FIVE, 
     $ NoneExp, 
     $ 'passing vuNorm() badness level for negative result            ')

C      call runDot(tracker, partNum, n, u, tol, NoVio, ZERO, 
C     $ NoneExp, 
C     $ 'passing vuDot() a null 1st array - no precondition vio         ')
      call runDot(tracker, partNum, n, u, tol, NoVio, ZERO, 
     $ NoneExp, 
     $ 'passing vuDot() a negative tolerance - no precondition vio    ')

      call runDot(tracker, partNum, u, u, tol, NegRes, NEG_FIVE, 
     $ NoneExp, 
     $ 'passing vuDot() badness level for negative result with u=v    ')

C      call runProduct(tracker, partNum, ZERO, n, tol, NoVio,
C     $ n, .true., NoneExp, 
C     $ 'passing vuProduct() a null array - no precondition vio        ')
      call runProduct(tracker, partNum, ZERO, n, ntol, NoVio,
     $ n, .true., NoneExp, 
     $ 'passing vuProduct() a negative tolerance - no precondition vio')

      call runProduct(tracker, partNum, ONE, u, tol, NullRes, 
     $ u, .false., NoneExp, 
     $ 'passing vuProduct() badness level for null result             ')

C      call runNegate(tracker, partNum, n, tol, NoVio, 
C     $ n, .true., NoneExp, 
C     $ 'passing vuNegate() a null array - no precondition vio         ')
      call runNegate(tracker, partNum, n, ntol, NoVio, 
     $ n, .true., NoneExp, 
     $ 'passing vuNegate() a negative tolerance - no precondition vio ')

      call runNegate(tracker, partNum, u, tol, NullRes, nu, 
     $ .false., NoneExp, 
     $ 'passing vuNegate() badness level for null result              ')

C      call runNormalize(tracker, partNum, n, tol, NoVio, 
C     $ n, .true., NoneExp, 
C     $ 'passing vuNormalize() a null array - no precondition vio      ')
      call runNormalize(tracker, partNum, n, ntol, NoVio, 
     $ n, .true., NoneExp, 
     $ 'passing vuNormalize() a negative tolerance - no pre vio       ')

      call runNormalize(tracker, partNum, u, tol, NullRes, u, 
     $ .false., NoneExp, 
     $ 'passing vuNormalize() a badness level for null result         ')

C      call runSum(tracker, partNum, n, z, tol, NoVio, n, 
C     $ .true., NoneExp, 
C     $ 'passing vuSum() a null 1st array - no postcondition vio       ')
      call runSum(tracker, partNum, n, z, ntol, NoVio, n, 
     $ .true., NoneExp, 
     $ 'passing vuSum() a negative tolerance - no postcondition vio   ')

      call runSum(tracker, partNum, u, z, tol, NullRes, u, 
     $ .false., NoneExp, 
     $ 'passing vuSum() a bad level for null result - no post         ')

C      call runDiff(tracker, partNum, n, u, tol, NoVio, 
C     $ n, .true., NoneExp, 
C     $ 'passing vuDiff() a null 1st array - no precondition vio       ')
      call runDiff(tracker, partNum, n, u, ntol, NoVio, 
     $ n, .true., NoneExp, 
     $ 'passing vuDiff() a negative tolerance - no precondition vio   ')

      call runDiff(tracker, partNum, z, u, tol, NullRes, nu, 
     $ .false., NoneExp, 
     $ 'passing vuDiff() bad level for null result - no post          ')

      call vect_Utils_dump_stats_static_f(STATS_FILE,
     $ 'After no checking                                             ',
     $ throwaway)

      call sidl_double__array_deleteRef_f(t, throwaway)
      call sidl_double__array_deleteRef_f(u, throwaway)
      call sidl_double__array_deleteRef_f(nu, throwaway)
      call sidl_double__array_deleteRef_f(u1, throwaway)
      call sidl_double__array_deleteRef_f(z, throwaway)

      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)

      stop
      end 
