c
c     File:       exceptionstest.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 7451 $
c     Date:       $Date: 2012-04-19 09:33:07 -0700 (Thu, 19 Apr 2012) $
c     Description:Simple F77 exception test client
c
c

      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker
      integer*8 throwaway
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_startPart_f(tracker, number, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      end

      subroutine reporttest(test, number)
      implicit none
      integer*4 number
      integer*8 tracker
      logical test
      integer*8 throwaway
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker, throwaway)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        throwaway)
      else
         call synch_RegOut_endPart_f(tracker, number, FAIL,
     $        throwaway)
      endif
      call catch(throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      call catch(throwaway)
      number = number + 1
      end

      subroutine catch(exc)
      implicit none
      integer*8 exc, classinfo, tracker, throwaway
      character*(1024) buffer, name
      if (exc .ne. 0) then
         call synch_RegOut_getInstance_f(tracker, throwaway)
         call synch_RegOut_writeComment_f(tracker,
     $        'Unexpected exception thrown', throwaway)
         call sidl_BaseInterface_getClassInfo_f(exc, classinfo,
     $        throwaway)
         if (classinfo .ne. 0) then
            call sidl_ClassInfo_getName_f(classinfo, name, throwaway)
            buffer = 'Exception name: ' // name
            call synch_RegOut_writeComment_f(tracker, buffer,
     $           throwaway)
         end if
         call synch_RegOut_forceFailure_f(tracker, throwaway)
         call synch_RegOut_deleteRef_f(tracker, throwaway)
      end if
      exc = 1000
      end   

      subroutine reportexc(exc)
      implicit none
      integer*8 exc, sidlex
      character*(100) msg
      character*(1024) trace
      integer*8 throwaway
      call sidl_SIDLException__cast_f(exc, sidlex, throwaway)
      call catch(throwaway)
      call sidl_SIDLException_getNote_f(sidlex, msg, throwaway)
      call catch(throwaway)
      write (6, 100) msg
      call sidl_SIDLException_getTrace_f(sidlex, trace, throwaway)
      call catch(throwaway)
      write (6, 110) trace
      call sidl_SIDLException_deleteRef_f(sidlex, throwaway)
      call catch(throwaway)
 100  format (1x, a100)
 110  format (1x, a1024)
      end

      subroutine testnone(fib, test)
      implicit none
      integer*8 fib
      integer*4 test
      integer*8 retval
      integer*8 exc
      integer*8 throwaway
      retval = 0
      call starttest(test)
      call Exceptions_Fib_getFib_f (fib, 10, 25, 200, 0, retval, 
     $                                 exc)
      if (exc .eq. 0) then
        call reporttest(.true., test)
        write (6, 100) retval
      else
        call reporttest(.false., test)
        call reportexc(exc)
        call sidl_BaseInterface_deleteRef_f (exc, throwaway)
        call catch(throwaway)
      endif
 100  format ('fib= ', I4)
      end

      subroutine testneg(fib, test)
      implicit none
      integer*8 fib
      integer*4 test
      integer*8 retval
      integer*8 exc
      logical   isone
      integer*8 throwaway
      call starttest(test)
      call Exceptions_Fib_getFib_f (fib, -1, 10, 10, 0, retval, 
     $                                 exc)
      if (exc .eq. 0) then
        call reporttest(.false., test)
        write (6, 100) retval
      else
        call sidl_BaseInterface_isType_f (exc, 
     $          'Exceptions.NegativeValueException', isone, 
     $        throwaway)
        call catch(throwaway)
        if (isone .eqv. .true.) then
          call reporttest(.true., test)
        else
          call reporttest(.false., test)
        endif
        call reportexc(exc)
        call sidl_BaseInterface_deleteRef_f (exc, throwaway)
        call catch(throwaway)
      endif
 100  format ('fib= ', I4)
      end

      subroutine testdeep(fib, test)
      implicit none
      integer*8 fib
      integer*4 test
      integer*8 retval
      integer*8 exc
      logical   isone
      integer*8 throwaway
      call starttest(test)
      call Exceptions_Fib_getFib_f (fib, 10, 1, 100, 0, retval, 
     $                                 exc)
      if (exc .eq. 0) then
        call reporttest(.false., test)
        write (6, 100) retval
      else
        call sidl_BaseInterface_isType_f (exc, 
     $          'Exceptions.TooDeepException', isone, throwaway)
        call catch(throwaway)
        if (isone .eqv. .true.) then
          call reporttest(.true., test)
        else
          call reporttest(.false., test)
        endif
        call reportexc(exc)
        call sidl_BaseInterface_deleteRef_f (exc, throwaway)
        call catch(throwaway)
      endif
 100  format ('fib= ', I4)
      end

      subroutine testbig(fib, test)
      implicit none
      integer*8 fib
      integer*4 test
      integer*8 retval
      integer*8 exc
      logical   isone
      integer*8 throwaway
      call starttest(test)
      call Exceptions_Fib_getFib_f (fib, 10, 100, 1, 0, retval, 
     $                                 exc)
      if (exc .eq. 0) then
        call reporttest(.false., test)
        write (6, 100) retval
      else
        call sidl_BaseInterface_isType_f (exc, 
     $          'Exceptions.TooBigException', isone, throwaway)
        call catch(throwaway)
        if (isone .eqv. .true.) then
          call reporttest(.true., test)
        else
          call reporttest(.false., test)
        endif
        call reportexc(exc)
        call sidl_BaseInterface_deleteRef_f (exc, throwaway)
        call catch(throwaway)
      endif
 100  format ('fib= ', I4)
      end

      subroutine testleak(fib, test, userow)
      integer*8 fib
      integer*4 test
      logical userow, righttype
      integer*8 a1, a2, a3, c1, c2, c3, o1, o2, o3, retval
      integer*8 exception, throwaway
      character*(80) s1, s2, s3
      integer*4 buffer1(4), buffer(4)
      integer*4 m, n
      s1 = 'foo'
      s2 = 'foo'
      m = 2
      n = 2
      if (userow) then
         call sidl_int__array_create2dRow_f(3, 3, a1)
         call sidl_int__array_create2dRow_f(4, 4, a2)
      else
         call sidl_int__array_create2dCol_f(3, 3, a1)
         call sidl_int__array_create2dCol_f(4, 4, a2)
      endif
      a3 = 1
      call sidl_int__array_create1d_f(3, c1)
      call sidl_int__array_create1d_f(4, c2)
      c3 = 1
      call sidl_BaseClass__create_f(o1, exception)
      call catch(exception)
      call sidl_BaseClass__create_f(o2, exception)
      call catch(exception)
      o3 = 1
      call starttest(test)
      call Exceptions_Fib_noLeak_f(fib, a1, a2, a3,
     $     buffer1, buffer2, m, n, c1, c2, c3, s1, s2, s3,
     $     o1, o2, o3, retval, exception)
      if (exception .eq. 0) then
         call reporttest(.false., test)
      else
         call sidl_BaseInterface_isType_f(exception,
     $        'sidl.SIDLException', righttype, throwaway)
         call catch(throwaway)
         call reporttest(righttype, test)
         if (o1 .ne. 0) then
            call sidl_BaseClass_deleteRef_f(o1, throwaway)
            call catch(throwaway)
         endif
         if (c1 .ne. 0) then
            call sidl_int__array_deleteRef_f(c1)
         endif
         if (a1 .ne. 0) then
            call sidl_int__array_deleteRef_f(a1)
         endif
         call sidl_BaseInterface_deleteRef_f(exception, throwaway)
         call catch(throwaway)
      endif
      end
      

      subroutine makeObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway

      if ( remoteURL .ne. '' ) then
         call Exceptions_Fib__createRemote_f (obj,
     $        remoteURL,throwaway)
      else
         call Exceptions_Fib__create_f(obj,throwaway)
         
      endif
      call catch(throwaway)
      end


      program exceptionstest
      implicit none
      integer*4 test, i ,IArgc
      integer*8 fib
      integer*8 retval, tracker
      integer*8 throwaway
      character*256 remoteURL, arg
      logical result
      
      call synch_RegOut_getInstance_f(tracker, throwaway)

C     Parse the command line  to see if we are running RMI tests
      remoteURL = ''
      do 10 i = 1, IArgc()
         call GetArg(i, arg)
         if ( arg(1:6) .eq. '--url=' ) then
            remoteURL = arg(7:)
         endif
 10   continue

C     Setup RMI if necessary
      if ( remoteURL .ne. '' ) then
         call GetArg(0, arg)
         call synch_RegOut_replaceMagicVars_f(tracker, remoteURL, arg,
     $        throwaway)
         write(*,'(A,A)') 'using remote URL ', remoteURL
         write(*,'(A)') 'registering RMI protocol simhandle'
         call sidl_rmi_ProtocolFactory_addProtocol_f('simhandle',
     $        'sidlx.rmi.SimHandle', result, throwaway)
         if (result .neqv. .TRUE. ) then
            write(*,'(A)') 'sidl.rmi.ProtocolFactor.addProtocol()
     $       failed'
         endif
      endif

      throwaway = 0
      call synch_RegOut_setExpectations_f(tracker, 6, throwaway)
      call catch(throwaway)
      call makeObject(fib, remoteURL)

      test = 1

      call synch_RegOut_writeComment_f(tracker,
     $      'No Exception test', throwaway)
      call catch(throwaway)
      call testnone(fib, test)

      call synch_RegOut_writeComment_f(tracker,
     $     'Negative Value Exception test', throwaway)
      call catch(throwaway)
      call testneg(fib, test)
      call synch_RegOut_writeComment_f(tracker,
     $     'Too Deep Exception test', throwaway)
      call catch(throwaway)
      call testdeep(fib, test)
      call synch_RegOut_writeComment_f(tracker,
     $     'Too Big Exception test', throwaway)
      call catch(throwaway)
      call testbig(fib, test)

      call synch_RegOut_writeComment_f(tracker,
     $     'Check memory leaks and ignored return values', throwaway)
      call testleak(fib, test, .true.)
      call testleak(fib, test, .false.)
      

      call Exceptions_Fib_deleteRef_f (fib, throwaway)
      call catch(throwaway)
      call synch_RegOut_close_f(tracker, throwaway)
      call catch(throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      call catch(throwaway)
      end
