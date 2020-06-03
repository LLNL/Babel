C
C File:        knapsacktest.f
C Copyright:   (c) 2011 Lawrence Livermore National Security, LLC
C Revision:    @(#) $Revision: 6183 $
C Date:        $Date: 2011-01-05 14:41:51 -0700 (Wed, 05 Jan 2011) $
C Description: FORTRAN 77 invariants regression tests
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

      subroutine createArray(len, arr)
      implicit none
      integer*4 len, lower(1), upper(1)
      integer*8 arr
      arr = 0
      if (len .ge. 0) then
        lower(1) = 0
        upper(1) = len - 1
        call sidl_int__array_createRow_f(1, lower, upper, arr)
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
      logical isOne
      include 'knapsack_ExpectExcept.inc'
      include 'synch_ResultType.inc'

      exceptType = NoneExp
      isOne = .false.
      if (exc .ne. 0) then
         call sidl_BaseInterface_isType_f(exc,
     $        'knapsack.kBadWeightExcept', isOne, throwaway)
         call catch(tracker, throwaway)
         if (isOne .eqv. .true.) then
            exceptType = BWExp
         endif
         if (isOne .eqv. .false.) then
            call sidl_BaseInterface_isType_f(exc,
     $          'knapsack.kSizeExcept', isOne, throwaway)
            call catch(tracker, throwaway)
            if (isOne .eqv. .true.) then
               exceptType = InvExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call sidl_BaseInterface_isType_f(exc,
     $          'sidl.InvViolation', isOne, throwaway)
            call catch(tracker, throwaway)
            if (isOne .eqv. .true.) then
               exceptType = InvExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call sidl_BaseInterface_isType_f(exc,
     $          'sidl.PreViolation', isOne, throwaway)
            call catch(tracker, throwaway)
            if (isOne .eqv. .true.) then
               exceptType = PreExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call sidl_BaseInterface_isType_f(exc,
     $         'sidl.PostViolation', isOne, throwaway)
            call catch(tracker, throwaway)
            if (isOne .eqv. .true.) then
              exceptType = PostExp
            endif
         endif
         if (isOne .eqv. .false.) then
            call sidl_BaseInterface_isType_f(exc,
     $         'knapsack.kExcept', isOne, throwaway)
            call catch(tracker, throwaway)
            if (isOne .eqv. .true.) then
               exceptType = ExcExp
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

      subroutine evalRes(tracker, partNum, res, expected)
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

      subroutine failNoExcept(tracker, partNum, expectExc)
      implicit none
      integer*8 tracker, expectExc
      integer*4 partNum
      integer*8 throwaway
      include 'knapsack_ExpectExcept.inc'
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
      subroutine runBaseGCase(tracker, partNum, w, t, res, expectExc, 
     $                        desc)
      implicit none
      integer*8 tracker, expectExc, w
      integer*4 partNum
      logical res, t
      character*(62) desc
    
      logical x
      integer*8 k
      integer*8 exc, throwaway, sExcept
      include 'knapsack_ExpectExcept.inc'
      
      call describeTest(tracker, partNum, desc)
      call knapsack_gKnapsack__create_f(k, throwaway)
      call knapsack_gKnapsack_initialize_f(k, w, exc)
        if (exc .ne. 0) then
        call knapsack_gKnapsack_hasSolution_f(k, t, x, exc)
          if (exc .ne. 0) then
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
      end
      
      
      subroutine runBaseNPCase(tracker, partNum, w, t, res, expectExc, 
     $                         desc)
      implicit none
      integer*8 tracker, expectExc, w
      integer*4 partNum
      logical t, res
      character*(62) desc
    
      logical x
      integer*8 k
      integer*8 exc, throwaway, sExcept
      include 'knapsack_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call knapsack_npKnapsack__create_f(k, throwaway)
      call knapsack_npKnapsack_initialize_f(k, w, exc)
        if (exc .ne. 0) then
        call knapsack_npKnapsack_hasSolution_f(k, t, x, exc)
          if (exc .ne. 0) then
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
      end
      
      subroutine runBaseNWCase(tracker, partNum, w, t, res, expectExc, 
     $                         desc)
      implicit none
      integer*8 tracker, expectExc, w
      integer*4 partNum
      logical t, res
      character*(62) desc
    
      logical x
      integer*8 k
      integer*8 exc, throwaway, sExcept
      include 'knapsack_ExpectExcept.inc'

      call describeTest(tracker, partNum, desc)
      call knapsack_nwKnapsack__create_f(k, throwaway)
      call knapsack_nwKnapsack_initialize_f(k, w, exc)
        if (exc .ne. 0) then
        call knapsack_nwKnapsack_hasSolution_f(k, t, x, exc)
          if (exc .ne. 0) then
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
      end


C
C     ***************************************************************
C     ***************************************************************
C     **  MAIN:  knapsacktest
C     ***************************************************************
C     ***************************************************************
C
      program knapsacktest
      implicit none
      
      integer*4 PARTS
      integer*4 MAX_SIZE
      
      integer*4 ONE
      integer*4 NEG_ONE
      
      integer*4 i, j, partNum, total, val
      character*60 heading
      integer*8 tracker
      integer*8 exception, throwaway
      integer*8 good, gLong, nArr, negEnd, zStart, zMid, zEnd
      
      logical areEnf
      include 'knapsack_ExpectExcept.inc'
      include 'sidl_ContractClass.inc'
        
C     Total tests minus those attempting to pass a null input array 
C     argument (that have not been replaced with alternate argument,
C     where applicable).

C TODO/TLD:  Need to count number of tests trying to use null array
      PARTS = 81 - 21
      MAX_SIZE = 6
      ONE = 1
      NEG_ONE = -1

      partNum = 0
      
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_setExpectations_f(tracker, PARTS, throwaway)
      
C
C     Initialize test knapsacks
C
      call createArray(MAX_SIZE, good)
      call createArray(MAX_SIZE + 1, gLong)
      call createArray(MAX_SIZE, negEnd)
      call createArray(MAX_SIZE, zStart)
      call createArray(MAX_SIZE, zMid)
      call createArray(MAX_SIZE, zEnd)
      nArr = 0
      
      total = 0
      val = 0
      do i = 0, MAX_SIZE - 1
        val = val + 2
        call sidl_int__array_set1_f(good, i, val)
        call sidl_int__array_set1_f(gLong, i, val)
        call sidl_int__array_set1_f(negEnd, i, val)
        call sidl_int__array_set1_f(zStart, i, val)
        call sidl_int__array_set1_f(zMid, i, val)
        call sidl_int__array_set1_f(zEnd, i, val)
        total = total + val
      enddo
      call sidl_int__array_set1_f(gLong, i, val+2)
      
C
C     Establish initial enforcement options
C
      heading = '*** ENABLE CONTRACT ENFORCEMENT ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceAll_f(ALLCLASSES, .true., exception)
      call catch(tracker, exception)
        
        
C
C     gKnapsack.BaseCase.Full set
C
      call runBaseGCase(tracker, partNum, good, total, .true., NoneExp,
     $ 'gKnapsack.BaseCase.Full: good: expect solution for total      ')
        
      call runBaseGCase(tracker, partNum, good, total-1, .false., 
     $ NoneExp,
     $ 'gKnapsack.BaseCase.Full: good: do not expect solution for t-1 ')
        
      call runBaseGCase(tracker, partNum, good, -1, .false., PreExp,
     $ 'gKnapsack.BaseCase.Full: good: precondition vio (neg. target) ')
        
      call runBaseGCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'gKnapsack.BaseCase.Full: gLong: size exception expected       ')
        
      call runBaseGCase(tracker, partNum, zStart, total, .false., 
     $ PreExp,
     $ 'gKnapsack.BaseCase.Full: zStart: pre vio/no solution expected ')
        
      call runBaseGCase(tracker, partNum, zMid, total, .false., PreExp,
     $ 'gKnapsack.BaseCase.Full: zMid: pre vio/no solution expected   ')
        
      call runBaseGCase(tracker, partNum, zEnd, total, .false., PreExp,
     $ 'gKnapsack.BaseCase.Full: zEnd: pre vio/no solution expected   ')
        
      call runBaseGCase(tracker, partNum, negEnd, total, .false., 
     $ PreExp,
     $ 'gKnapsack.BaseCase.Full: negEnd: pre vio/no solution expected ')
        
C
C TODO/TBD:  This will either be an invariant or postcondition violation,
C depending on ordering in generated code
C
      call runBaseGCase(tracker, partNum, nArr, total, .false., InvExp,
     $ 'gKnapsack.BaseCase.Full: nArr: inv vio/no solution expected   ')
        
        
C
C     npKnapsack.BaseCase.Full set
C
      call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,
     $ 'npKnapsack.BaseCase.Full: good: expect solution for total     ')
        
      call runBaseNPCase(tracker, partNum, good, total-1, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.Full: good: do not expect solution for t-1')
        
      call runBaseNPCase(tracker, partNum, good, -1, .false., PreExp,
     $ 'npKnapsack.BaseCase.Full: good: precondition vio (neg. target)')
        
      call runBaseNPCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'npKnapsack.BaseCase.Full: gLong: size exception expected      ')
        
      call runBaseNPCase(tracker, partNum, zStart, total, .false., 
     $ PreExp,
     $ 'npKnapsack.BaseCase.Full: zStart: pre vio/no solution expected')
        
      call runBaseNPCase(tracker, partNum, zMid, total, .false., PreExp,
     $ 'npKnapsack.BaseCase.Full: zMid: pre vio/no solution expected  ')
        
      call runBaseNPCase(tracker, partNum, zEnd, total, .false., PreExp,
     $ 'npKnapsack.BaseCase.Full: zEnd: pre vio/no solution expected  ')
        
      call runBaseNPCase(tracker, partNum, negEnd, total, .false., 
     $ PreExp,
     $ 'npKnapsack.BaseCase.Full: negEnd: pre vio/no solution expected')
        
C
C TODO/TBD:  This will either be an invariant or postcondition violation,
C depending on ordering in generated code
C
      call runBaseNPCase(tracker, partNum, nArr, total, .false., InvExp,
     $ 'npKnapsack.BaseCase.Full: nArr: inv vio/no solution expected  ')
        
        
C
C     nwKnapsack.BaseCase.Full set
C
      call runBaseNWCase(tracker, partNum, good, total, .false., 
     $ NoneExp,
     $ 'nwKnapsack.BaseCase.Full: good: do not expect solution for t  ')
        
      call runBaseNWCase(tracker, partNum, good, total-1, .false., 
     $ NoneExp,
     $ 'nwKnapsack.BaseCase.Full: good: do not expect solution for t-1')
        
      call runBaseNWCase(tracker, partNum, good, -1, .false., PreExp,
     $ 'nwKnapsack.BaseCase.Full: good: precondition vio (neg. target)')
        
      call runBaseNWCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'nwKnapsack.BaseCase.Full: gLong: size exception expected      ')
        
      call runBaseNWCase(tracker, partNum, zStart, total, .false., 
     $ PreExp,
     $ 'nwKnapsack.BaseCase.Full: zStart: pre vio/no solution expected')
        
      call runBaseNWCase(tracker, partNum, zMid, total, .false., PreExp,
     $ 'nwKnapsack.BaseCase.Full: zMid: pre vio/no solution expected  ')
        
      call runBaseNWCase(tracker, partNum, zEnd, total, .false., PreExp,
     $ 'nwKnapsack.BaseCase.Full: zEnd: pre vio/no solution expected  ')
        
      call runBaseNWCase(tracker, partNum, negEnd, total, .false., 
     $ PreExp,
     $ 'nwKnapsack.BaseCase.Full: negEnd: pre vio/no solution expected')
        
C
C TODO/TBD:  This will either be an invariant or postcondition violation,
C depending on ordering in generated code
C
      call runBaseNWCase(tracker, partNum, nArr, total, .false., InvExp,
     $ 'nwKnapsack.BaseCase.Full: nArr: inv vio/no solution expected  ')
        
        
C
C ***********************************************************************
C * POSTCONDITION-ONLY ENFORCEMENT
C ***********************************************************************
C
      heading = '*** POSTCONDITION-ONLY ENFORCEMENT ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceAll_f(POSTCONDS, .false., exception)
      call catch(tracker, exception)
                                     
        
C
C     gKnapsack.BaseCase.Post set
C
      call runBaseGCase(good, total, .true., NoneExp,
     $ 'gKnapsack.BaseCase.Post: good: expect solution for total      ')
        
      call runBaseGCase(good, -1, .false., NoneExp,
     $ 'gKnapsack.BaseCase.Post: good: no solution expected           ')
        
      call runBaseGCase(gLong, total, .false., SizeExp,
     $ 'gKnapsack.BaseCase.Post: gLong: size exception expected       ')
        
      call runBaseGCase(negEnd, total, .false., BWExp,
     $ 'gKnapsack.BaseCase.Post: negEnd: bad weight exception expected')
        
      call runBaseGCase(nArr, total, .false., PostExp,
     $ 'gKnapsack.BaseCase.Post: nArr: post vio/no solution expected  ')
        
        
C
C     npKnapsack.BaseCase.Post set
C
      call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,
     $ 'npKnapsack.BaseCase.Post: good: expect solution for total     ')
        
      call runBaseNPCase(tracker, partNum, good, -1, .false., NoneExp,
     $ 'npKnapsack.BaseCase.Post: good: no solution expected          ')
        
      call runBaseNPCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'npKnapsack.BaseCase.Post: gLong: size exception expected      ')
        
      call runBaseNPCase(tracker, partNum, zStart, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.Post: zStart: no solution expected        ')
        
      call runBaseNPCase(tracker, partNum, zMid, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.Post: zMid: no solution expected          ')
        
      call runBaseNPCase(tracker, partNum, zEnd, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.Post: zEnd: no solution expected          ')
        
      call runBaseNPCase(tracker, partNum, negEnd, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.Post: negEnd: no solution expected        ')
        
      call runBaseNPCase(tracker, partNum, nArr, total, .false., 
     $ PostExp,
     $ 'npKnapsack.BaseCase.Post: nArr: post vio/no solution expected ')
        
        
C
C     nwKnapsack.BaseCase.Post set
C
      call runBaseNWCase(tracker, partNum, good, total, .false., 
     $ PostExp,
     $ 'nwKnapsack.BaseCase.Post: good: post vio/no solution expected ')
        
      call runBaseNWCase(tracker, partNum, good, -1, .false., PostExp,
     $ 'nwKnapsack.BaseCase.Post: good: post vio/no solution expected ')
        
      call runBaseNWCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'nwKnapsack.BaseCase.Post: gLong: size exception expected      ')
        
      call runBaseNWCase(tracker, partNum, negEnd, total, .false., 
     $ PostExp,
     $ 'nwKnapsack.BaseCase.Post: negEnd: post vio/no solution exp.   ')
        
      call runBaseNWCase(tracker, partNum, nArr, total, .false., 
     $ PostExp,
     $ 'nwKnapsack.BaseCase.Post: nArr: post vio/no solution expected ')
        
        
C
C ***********************************************************************
C * INVARIANTS-ONLY ENFORCEMENT
C ***********************************************************************
C
      heading = '*** INVARIANTS-ONLY ENFORCEMENT ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceAll_f(INVARIANTS, .false., 
     $ exception)
      call catch(tracker, exception)
        
C
C     gKnapsack.BaseCase.Inv set
C
      call runBaseGCase(good, total, .true., NoneExp,
     $ 'gKnapsack.BaseCase.Inv: good: expect solution for total       ')
        
      call runBaseGCase(good, -1, .false., NoneExp,
     $ 'gKnapsack.BaseCase.Inv: good: no solution expected            ')
        
      call runBaseGCase(gLong, total, .false., SizeExp,
     $ 'gKnapsack.BaseCase.Inv: gLong: size exception expected        ')
        
      call runBaseGCase(negEnd, total, .false., BWExp,
     $ 'gKnapsack.BaseCase.Inv: negEnd: bad weight exception expected ')
        
      call runBaseGCase(nArr, total, .false., InvExp,
     $ 'gKnapsack.BaseCase.Inv: nArr: inv vio/no solution expected    ')
        
        
C
C     npKnapsack.BaseCase.Inv set
C
      call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,
     $ 'npKnapsack.BaseCase.Inv: good: expect solution for total      ')
        
      call runBaseNPCase(tracker, partNum, good, -1, .false., NoneExp,
     $ 'npKnapsack.BaseCase.Inv: good: no solution expected           ')
        
      call runBaseNPCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'npKnapsack.BaseCase.Inv: gLong: size exception expected       ')
        
      call runBaseNPCase(tracker, partNum, zStart, total, .false., 
     $ InvExp,
     $ 'npKnapsack.BaseCase.Inv: zStart: inv vio/no solution expected ')
        
      call runBaseNPCase(tracker, partNum, zMid, total, .false., InvExp,
     $ 'npKnapsack.BaseCase.Inv: zMid: inv vio/no solution expected   ')
        
      call runBaseNPCase(tracker, partNum, zEnd, total, .false., InvExp,
     $ 'npKnapsack.BaseCase.Inv: zEnd: inv vio/no solution expected   ')
        
      call runBaseNPCase(tracker, partNum, negEnd, total, .false., 
     $ InvExp,
     $ 'npKnapsack.BaseCase.Inv: negEnd: inv vio/no solution expected ')
        
      call runBaseNPCase(tracker, partNum, nArr, total, .false., InvExp,
     $ 'npKnapsack.BaseCase.Inv: nArr: inv vio/no solution expected   ')
        
        
C
C     nwKnapsack.BaseCase.Inv set
C
      call runBaseNWCase(tracker, partNum, good, total, .false., 
     $ NoneExp,
     $ 'nwKnapsack.BaseCase.Inv: good: no solution expected           ')
        
      call runBaseNWCase(tracker, partNum, good, -1, .false., NoneExp,
     $ 'nwKnapsack.BaseCase.Inv: good: no solution expected           ')
        
      call runBaseNWCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'nwKnapsack.BaseCase.Inv: gLong: size exception expected       ')
        
C
C WARNING:  The following assumes the non-positive weight does get added 
C to the knapsack.
C
      call runBaseNWCase(tracker, partNum, negEnd, total, .false., 
     $ InvExp,
     $ 'nwKnapsack.BaseCase.Inv: negEnd: inv vio/no solution expected ')
        
      call runBaseNWCase(tracker, partNum, nArr, total, .false., InvExp,
     $ 'nwKnapsack.BaseCase.Inv: nArr: inv vio/no solution expected   ')
        
        
C
C ***********************************************************************
C * NO CONTRACT ENFORCEMENT
C ***********************************************************************
C 
      heading = '*** DISABLE CONTRACT ENFORCEMENT ***'
      call comment(tracker, heading)
      call sidl_EnfPolicy_setEnforceNone_f(.false., exception)
      call catch(tracker, exception)
        
C
C     gKnapsack.BaseCase.None set
C
      call runBaseGCase(good, total, .true., NoneExp,
     $ 'gKnapsack.BaseCase.None: good: expect solution for total      ')
        
      call runBaseGCase(good, -1, .false., NoneExp,
     $ 'gKnapsack.BaseCase.None: good: no solution expected           ')
        
      call runBaseGCase(gLong, total, .false., SizeExp,
     $ 'gKnapsack.BaseCase.None: gLong: size exception expected       ')
        
      call runBaseGCase(negEnd, total, .false., BWExp,
     $ 'gKnapsack.BaseCase.None: negEnd: bad weight exception expected')
        
      call runBaseGCase(nArr, total, .false., NoneExp,
     $ 'gKnapsack.BaseCase.None: nArr: inv vio/no solution expected   ')
        
        
C
C     npKnapsack.BaseCase.None set
C
      call runBaseNPCase(tracker, partNum, good, total, .true., NoneExp,
     $ 'npKnapsack.BaseCase.None: good: expect solution for total     ')
        
      call runBaseNPCase(tracker, partNum, good, -1, .false., NoneExp,
     $ 'npKnapsack.BaseCase.None: good: no solution expected          ')
        
      call runBaseNPCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'npKnapsack.BaseCase.None: gLong: size exception expected      ')
        
      call runBaseNPCase(tracker, partNum, zStart, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.None: zStart: no solution expected        ')
        
      call runBaseNPCase(tracker, partNum, zMid, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.None: zMid: no solution expected          ')
        
      call runBaseNPCase(tracker, partNum, zEnd, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.None: zEnd: no solution expected          ')
        
      call runBaseNPCase(tracker, partNum, negEnd, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.None: negEnd: no solution expected        ')
        
      call runBaseNPCase(tracker, partNum, nArr, total, .false., 
     $ NoneExp,
     $ 'npKnapsack.BaseCase.None: nArr: no solution expected          ')
        
        
C
C     nwKnapsack.BaseCase.None set
C
      call runBaseNWCase(tracker, partNum, good, total, .false., 
     $  NoneExp,
     $ 'nwKnapsack.BaseCase.None: good: no solution expected          ')
        
      call runBaseNWCase(tracker, partNum, good, -1, .false., NoneExp,
     $ 'nwKnapsack.BaseCase.None: good: no solution expected          ')
        
      call runBaseNWCase(tracker, partNum, gLong, total, .false., 
     $ SizeExp,
     $ 'nwKnapsack.BaseCase.None: gLong: size exception expected      ')
        
      call runBaseNWCase(tracker, partNum, negEnd, total, .false., 
     $ NoneExp,
     $ 'nwKnapsack.BaseCase.None: negEnd: no solution expected        ')
        
      call runBaseNWCase(tracker, partNum, nArr, total, .false., 
     $ NoneExp,
     $ 'nwKnapsack.BaseCase.None: nArr: no solution expected          ')
        
C
C   TODO/TBD:  Other possible cases:
C   1) create, initialize, and explicitly call hasWeights
C      x = k.hasWeights(k, w)
C  
C   2) create, initialize, and explicitly call onlyPosWeights
C      x = k.onlyPosWeights(k)
C

      call sidl_int__array_deleteRef_f(good, throwaway)
      call sidl_int__array_deleteRef_f(gLong, throwaway)
      call sidl_int__array_deleteRef_f(negEnd, throwaway)
      call sidl_int__array_deleteRef_f(zStart, throwaway)
      call sidl_int__array_deleteRef_f(zMid, throwaway)
      call sidl_int__array_deleteRef_f(zEnd, throwaway)

      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)

      stop
      end 
