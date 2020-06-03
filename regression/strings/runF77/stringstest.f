c
c     File:       argstest.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 6879 $
c     Date:       $Date: 2010-08-18 11:12:53 -0700 (Wed, 18 Aug 2010) $
c     Description:Exercise the FORTRAN interface
c
c
      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker, tae
      call synch_RegOut_getInstance_f(tracker, tae)
      call synch_RegOut_startPart_f(tracker, number, tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      end

      subroutine reporttest(test, number)
      implicit none
      integer*4 number
      integer*8 tracker, tae
      logical test
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker, tae)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        tae)
      else
         call synch_RegOut_endPart_f(tracker, number, FAIL,
     $        tae)
      endif
      call synch_RegOut_deleteRef_f(tracker, tae)
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
         call Strings_Cstring__createRemote_f(obj,remoteURL,throwaway)
      else
         call Strings_Cstring__create_f(obj,throwaway)
      endif
      call catch(throwaway)
      end
      
      subroutine teststring(test, remoteURL)
      implicit none
      integer*8 obj, tae
      integer*4 test
      logical retval
      character*80 in, inout, out, sreturn
      character ch1, ch2
      character*256 remoteURL

      call makeObject(obj, remoteURL)
      sreturn = 'Not three'
      call starttest(test)
      call Strings_Cstring_returnback_f(obj, .true., sreturn, tae)
      call reporttest(sreturn .eq. 'Three', test)
      retval = .false.
      call starttest(test)
      call Strings_Cstring_passin_f(obj, 'Three', retval, tae)
      call reporttest(retval, test)
      in = 'Three'
      call starttest(test)
      call Strings_Cstring_passin_f(obj, in, retval, tae)
      call reporttest(retval, test)
      call starttest(test)
      call Strings_Cstring_passin_f(obj, 'Four', retval, tae)
      call reporttest(.not. retval, test)
      out = 'Not three'
      call starttest(test)
      call Strings_Cstring_passout_f(obj, .true., out, retval, tae)
      call reporttest(retval .and. out .eq. 'Three', test)
      inout = 'Three'
      call starttest(test)
      call Strings_Cstring_passinout_f(obj, inout, retval, tae)
      call reporttest(retval .and. inout .eq. 'threes', test)
      call starttest(test)
      call Strings_Cstring_passeverywhere_f(obj,
     $     'Three', out, inout, sreturn, tae)
      call reporttest(sreturn .eq. 'Three' .and.
     $     out .eq. 'Three' .and. inout .eq. 'Three',
     $     test)
      call starttest(test)
      call Strings_Cstring_mixedarguments_f(obj, 'Test', 'z',
     $     'Test', 'z', retval, tae)
      call reporttest(retval, test)
      call starttest(test)
      call Strings_Cstring_mixedarguments_f(obj, 'Not', 'A',
     $     'Equal', 'a', retval, tae)
      call reporttest(.not. retval, test)
      ch1 = 'z'
      ch2 = 'z'
      call starttest(test)
      call Strings_Cstring_mixedarguments_f(obj, 'Test', ch1,
     $     'Test', ch1, retval, tae)
      call reporttest(retval, test)
      call starttest(test)
      call Strings_Cstring_mixedarguments_f(obj, 'Test', ch1,
     $     'Test', ch2, retval, tae)
      call reporttest(retval, test)
      ch2 = 'A'
      call starttest(test)
      call Strings_Cstring_mixedarguments_f(obj, 'Not', ch1,
     $     'Equal', ch2, retval, tae)
      call reporttest(.not. retval, test)
      call Strings_Cstring_deleteRef_f(obj, tae)

      end

      
      program stringstest
      integer*4 test, i
      integer*8 tracker, tae, throwaway
      character*256 remoteURL, arg
      logical retval
      test = 1
      call synch_RegOut_getInstance_f(tracker, tae)

C     Parse the command line to see if we are running RMI tests
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
      
      call synch_RegOut_setExpectations_f(tracker, 12, tae)
      call synch_RegOut_writeComment_f(tracker,
     $     'String tests', tae)
      call teststring(test, remoteURL)
      call synch_RegOut_close_f(tracker, tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      end
