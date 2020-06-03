c
c     File:       overloadtest.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 6813 $
c     Date:       $Date: 2009-12-02 14:25:29 -0800 (Wed, 02 Dec 2009) $
c     Description:Simple F77 overload test client
c
c

      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker
      integer*8 exception
      call synch_RegOut_getInstance_f(tracker,exception)
      call synch_RegOut_startPart_f(tracker, number,exception)
      call synch_RegOut_deleteRef_f(tracker,exception)
      end

      subroutine reporttest(test, number)
      implicit none
      integer*4 number
      integer*8 tracker
      logical test
      integer*8 exception
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker,exception)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        exception)
      else
         call synch_RegOut_endPart_f(tracker, number, FAIL,
     $        exception)
      endif
      call synch_RegOut_deleteRef_f(tracker, exception)
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

      subroutine makeOverloadTest(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Overload_Test__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Overload_Test__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeException(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Overload_AnException__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Overload_AnException__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeAClass(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Overload_AClass__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Overload_AClass__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeBClass(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Overload_BClass__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Overload_BClass__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end
      
      subroutine testnone(t, test, remoteURL)
      implicit none
      integer*4 test
      integer*8 t
      integer*4 retval
      integer*8 exception
      character*256 remoteURL

      retval = 0

      call starttest(test)
      call Overload_Test_getValue_f (t, retval,exception)
      call reporttest(retval .eq. 1, test)
      end

      subroutine testone(t, test, remoteURL)
      implicit none
      character*80 s1, sretval
      integer*4 test
      integer*8 t, ae, ac, bc
      integer*4 i1, iretval
      double precision d1, dretval
      real f1, fretval
      logical b1, bretval
      complex fc, fcretval
      double complex dc, dcretval
      integer*8 exception
      character*256 remoteURL

      b1 = .true.
      d1 = 1.0d0
      f1 = 1.0
      i1 = 1
      fc = (1.1, 1.1)
      dc = (2.2d0, 2.2d0)
      s1 = 'AnException'

      call starttest(test)
      call Overload_Test_getValueBool_f (t, b1, bretval, exception)
      call reporttest(bretval .eqv. b1, test)
      call starttest(test)
      call Overload_Test_getValueDouble_f (t, d1, dretval, exception)
      call reporttest(dretval .eq. d1, test)
      call starttest(test)
      call Overload_Test_getValueDcomplex_f (t, dc, dcretval, exception)
      call reporttest(dcretval .eq. dc, test)
      call starttest(test)
      call Overload_Test_getValueFloat_f (t, f1, fretval, exception)
      call reporttest(fretval .eq. f1, test)
      call starttest(test)
      call Overload_Test_getValueFcomplex_f (t, fc, fcretval, exception)
      call reporttest(fcretval .eq. fc, test)
      call starttest(test)
      call Overload_Test_getValueInt_f (t, i1, iretval, exception)
      call reporttest(iretval .eq. i1, test)
      call starttest(test)
      call Overload_Test_getValueString_f (t, s1, sretval, exception)
      call reporttest(sretval .eq. s1, test)

      call makeException(ae, remoteURL)
      call starttest(test)
      call Overload_Test_getValueException_f (t, ae, sretval, exception)
      call reporttest(sretval .eq. s1, test)
      call Overload_AnException_deleteRef_f(ae, exception)
      call makeAClass(ac, remoteURL)
      call starttest(test)
      call Overload_Test_getValueAClass_f (t, ac, iretval, exception)
      call reporttest(iretval .eq. 2, test)
      call Overload_AClass_deleteRef_f(ac, exception)
      call makeBClass(bc, remoteURL)
      call starttest(test)
      call Overload_Test_getValueBClass_f (t, bc, iretval, exception)
      call reporttest(iretval .eq. 2, test)
      call Overload_BClass_deleteRef_f(bc, exception)
      end

      subroutine testtwo(t, test, remoteURL)
      implicit none
      integer*4 test
      integer*8 t
      integer*4 i1, iretval
      double precision d1, dretval, did
      real f1
      integer*8 exception
      character*256 remoteURL

      d1 = 1.0d0
      i1 = 1
      did =2.0d0

      call starttest(test)
      call Overload_Test_getValueDoubleInt_f (t, d1, i1, dretval, 
     & exception)
      call reporttest(dretval .eq. did, test)
      call starttest(test)
      call Overload_Test_getValueIntDouble_f (t, i1, d1, dretval, 
     & exception)
      call reporttest(dretval .eq. did, test)
      end

      subroutine testthree(t, test, remoteURL)
      implicit none
      integer*8 t
      integer*4 i1
      integer*4 test
      double precision d1, difd, dretval
      real f1
      integer*8 exception
      character*256 remoteURL

      d1 = 1.0d0
      f1 = 1.0
      i1 = 1
      difd = 3.0d0

      call starttest(test)
      call Overload_Test_getValueDoubleIntFloat_f (t, d1, i1, f1, 
     $                                             dretval, exception)
      call reporttest(dretval .eq. difd, test)
      call starttest(test)
      call Overload_Test_getValueIntDoubleFloat_f (t, i1, d1, f1, 
     $                                             dretval, exception)
      call reporttest(dretval .eq. difd, test)
      call starttest(test)
      call Overload_Test_getValueDoubleFloatInt_f (t, d1, f1, i1, 
     $                                             dretval, exception)
      call reporttest(dretval .eq. difd, test)
      call starttest(test)
      call Overload_Test_getValueIntFloatDouble_f (t, i1, f1, d1,
     $                                             dretval, exception)
      call reporttest(dretval .eq. difd, test)
      call starttest(test)
      call Overload_Test_getValueFloatDoubleInt_f (t, f1, d1, i1,
     $                                             dretval, exception)
      call reporttest(dretval .eq. difd, test)
      call starttest(test)
      call Overload_Test_getValueFloatIntDouble_f (t, f1, i1, d1,
     $                                             dretval, exception)
      call reporttest(dretval .eq. difd, test)
      end


      program overloadtest
      implicit none
      integer*4 test, i,IArgc
      integer*8 t, tracker
      integer*8 exception, throwaway
      character*256 remoteURL, arg
      logical retval

      call synch_RegOut_getInstance_f(tracker, exception)

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
     $        'sidlx.rmi.SimHandle', retval, throwaway)
         if (retval .neqv. .TRUE. ) then
            write(*,'(A)') 'sidl.rmi.ProtocolFactor.addProtocol()
     $       failed'
         endif
      endif
      
      call synch_RegOut_setExpectations_f(tracker, 19,exception)
      call makeOverloadTest(t, remoteURL)

      test = 1

      call synch_RegOut_writeComment_f(tracker,
     $     'No Argument test             ', exception)
      call testnone(t, test, remoteURL)
      call synch_RegOut_writeComment_f(tracker,
     $     'Single Argument tests        ', exception)
      call testone(t, test, remoteURL)
      call synch_RegOut_writeComment_f(tracker,
     $     'Double Argument tests        ', exception)
      call testtwo(t, test, remoteURL)
      call synch_RegOut_writeComment_f(tracker,
     $     'Triple Argument tests        ', exception)
      call testthree(t, test, remoteURL)

      call Overload_Test_deleteRef_f (t, exception)

      call synch_RegOut_close_f(tracker, exception)
      call synch_RegOut_deleteRef_f(tracker, exception)
      end
