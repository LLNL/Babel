c
c     File:       hookstest.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 5006 $
c     Date:       $Date: 2005-11-01 15:20:38 -0800 (Tue, 01 Nov 2005) $
c     Description:Exercise the FORTRAN interface
c
c
      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker, throwaway
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_startPart_f(tracker, number, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      end

      subroutine reporttest(test, number,  python)
      implicit none
      integer*4 number
      integer*8 tracker, throwaway
      logical test, python
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker, throwaway)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        throwaway)
      else
         if (python) then
            call synch_RegOut_endPart_f(tracker, number, XFAIL,
     $           throwaway)
         else
            call synch_RegOut_endPart_f(tracker, number, FAIL,
     $           throwaway)
         endif
      endif
      call synch_RegOut_deleteRef_f(tracker, throwaway)
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
      
      subroutine makeObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway

      if ( remoteURL .ne. '' ) then
         call hooks_Basics__createRemote_f(obj,remoteURL,throwaway)
      else
         call hooks_Basics__create_f(obj,throwaway)
      endif
      
      call catch(throwaway)
      end
      
      subroutine testhooks(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test, a, b, c, ret
      logical   boolval
      character*256 remoteURL
      
      boolval = .true.
      call hooks_Basics__set_hooks_static_f(boolval,throwaway)

      call makeObject(obj, remoteURL)
      call hooks_Basics__set_hooks_f(obj,boolval,throwaway)

      test = 1
      b = -1
      c = -1
      ret = 0

      call starttest(test)
      call hooks_Basics_aStaticMeth_f(test,b,c,ret,throwaway)
      call reporttest(b .eq. 1 .and. c .eq. 0, test, .false.)

      call starttest(test)
      call hooks_Basics_aStaticMeth_f(test,b,c,ret,throwaway)
      call reporttest(b .eq. 2 .and. c .eq. 1, test, .false.)

      b = -1
      c = -1
      ret = 0

      call starttest(test)
      call hooks_Basics_aNonStaticMeth_f(obj, test,b,c,ret,throwaway)
      call reporttest(b .eq. 1 .and. c .eq. 0, test, .false.)

      call starttest(test)
      call hooks_Basics_aNonStaticMeth_f(obj, test,b,c,ret,throwaway)
      call reporttest(b .eq. 2 .and. c .eq. 1, test, .false.)

      call hooks_Basics_deleteRef_f(obj,throwaway)
      end


      program hookstest
      integer*4 test, i
      integer*8 tracker, throwaway
      character*80 language
      character*256 remoteURL, arg
      logical result

      throwaway = 0
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

      language = ' '
      call synch_RegOut_setExpectations_f(tracker, 4, throwaway)
      call synch_RegOut_writeComment_f(tracker,
     $     'Make sure hooks work', throwaway)
      call testhooks(test, remoteURL)
      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker,throwaway)
      end
