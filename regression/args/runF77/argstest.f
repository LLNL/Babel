c
c     File:       argstest.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 6793 $
c     Date:       $Date: 2009-11-13 11:35:54 -0800 (Fri, 13 Nov 2009) $
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
         call Args_Basic__createRemote_f(obj,remoteURL,throwaway)
      else
         call Args_Basic__create_f(obj,throwaway)
      endif
      call catch(throwaway)
      end
      
      subroutine testbool(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical out, inout, retval
      character*256 remoteURL
      
      inout = .true.
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackbool_f(obj, retval,throwaway)
      call reporttest(retval, test, .false.)
      call starttest(test)
      call Args_Basic_passinbool_f(obj, .true., retval,throwaway)
      call reporttest(retval, test, .false.)
      call starttest(test)
      call Args_Basic_passoutbool_f(obj, out, retval,throwaway)
      call reporttest(retval .and. out, test,
     $     .false.)
      call starttest(test)
      call Args_Basic_passinoutbool_f(obj, inout, retval,throwaway)
      call reporttest(retval .and. .not. inout, test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passeverywherebool_f(obj, .true.,
     $     out, inout, retval,throwaway)
      call reporttest(retval .and. out .and. inout, test,
     $      .false.)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testint(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval
      integer*4 iretval, out, inout
      character*256 remoteURL
      
      inout = 3
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackint_f(obj, iretval,throwaway)
      call reporttest(iretval .eq. 3, test,
     $     .false.)
      call starttest(test)
      call Args_Basic_passinint_f(obj, 3, bretval,throwaway)
      call reporttest(bretval, test,
     $    .false.)
      call starttest(test)
      call Args_Basic_passoutint_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. 3), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passinoutint_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. -3), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passeverywhereint_f(obj, 3,
     $     out, inout, iretval,throwaway)
      call reporttest((iretval .eq. 3) .and.
     $     (out .eq. 3) .and.
     $     (inout .eq. 3), test, .false.)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testchar(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval
      character cretval, out, inout
      character*256 remoteURL
      
      inout = 'A'
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackchar_f(obj, cretval,throwaway)
      call reporttest(cretval .eq. '3', test, 
     $      .false.)
      call starttest(test)
      call Args_Basic_passinchar_f(obj, '3', bretval,throwaway)
      call reporttest(bretval, test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passoutchar_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. '3'), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passinoutchar_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. 'a'), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passeverywherechar_f(obj, '3',
     $     out, inout, cretval,throwaway)
      call reporttest((cretval .eq. '3') .and.
     $     (out .eq. '3') .and.
     $     (inout .eq. 'A'), test, .false.)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testlong(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval
      integer*8 out, inout, iretval, inval
      character*256 remoteURL
      
      inout = 3
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbacklong_f(obj, iretval,throwaway)
      call reporttest(iretval .eq. 3, test,
     $      .false.)
      call starttest(test)
      inval = 3
      call Args_Basic_passinlong_f(obj, inval, bretval,throwaway)
      call reporttest(bretval, test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passoutlong_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. 3), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passinoutlong_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. -3), test,
     $      .false.)
      call starttest(test)
      inval = 3
      call Args_Basic_passeverywherelong_f(obj, inval,
     $     out, inout, iretval,throwaway)
      call reporttest((iretval .eq. 3) .and.
     $     (out .eq. 3) .and.
     $     (inout .eq. 3), test,  .false.)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testfloat(test, python, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval, python
      real out, inout, fretval
      character*256 remoteURL
      
      inout = 3.1
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackfloat_f(obj, fretval,throwaway)
      call reporttest(fretval .eq. 3.1, test, .false.)
      call starttest(test)
      call Args_Basic_passinfloat_f(obj, 3.1, bretval,throwaway)
      call reporttest(bretval, test, python)
      call starttest(test)
      call Args_Basic_passoutfloat_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. 3.1), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passinoutfloat_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. -3.1), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passeverywherefloat_f(obj, 3.1,
     $     out, inout, fretval,throwaway)
      call reporttest((fretval .eq. 3.1) .and.
     $     (out .eq. 3.1) .and.
     $     (inout .eq. 3.1), test,
     $      python)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testdouble(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval
      double precision out, inout, dretval
      character*256 remoteURL
      
      inout = 3.14d0
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackdouble_f(obj, dretval,throwaway)
      call reporttest(dretval .eq. 3.14d0, test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passindouble_f(obj, 3.14d0, bretval,throwaway)
      call reporttest(bretval, test,
     $     .false.)
      call starttest(test)
      call Args_Basic_passoutdouble_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. 3.14d0), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passinoutdouble_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. -3.14d0), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passeverywheredouble_f(obj, 3.14d0,
     $     out, inout, dretval,throwaway)
      call reporttest((dretval .eq. 3.14d0) .and.
     $     (out .eq. 3.14d0) .and.
     $     (inout .eq. 3.14d0), test,
     $     .false.)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testfcomplex(test, python, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval, python
      complex in, out, inout, cretval
      character*256 remoteURL
      
      in = (3.1,3.1)
      inout = (3.1, 3.1)
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackfcomplex_f(obj, cretval,throwaway)
      call reporttest(cretval .eq. (3.1,3.1), test,
     $     .false.)
      call starttest(test)
      call Args_Basic_passinfcomplex_f(obj, in, bretval,throwaway)
      call reporttest(bretval, test,  python)
      call starttest(test)
      call Args_Basic_passoutfcomplex_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. (3.1,3.1)), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passinoutfcomplex_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. (3.1,-3.1)), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passeverywherefcomplex_f(obj, in,
     $     out, inout, cretval,throwaway)
      call reporttest((cretval .eq. (3.1,3.1)) .and.
     $     (out .eq. (3.1,3.1)) .and.
     $     (inout .eq. (3.1,3.1)), test,
     $    python)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      subroutine testdcomplex(test, remoteURL)
      implicit none
      integer*8 obj
      integer*8 throwaway
      integer*4 test
      logical bretval
      double complex in, out, inout, cretval
      character*256 remoteURL
      
      in = (3.14d0,3.14d0)
      inout = (3.14d0, 3.14d0)
      call makeObject(obj, remoteURL)
      call starttest(test)
      call Args_Basic_returnbackdcomplex_f(obj, cretval,throwaway)
      call reporttest(cretval .eq. (3.14d0,3.14d0), test,
     $      .false.)
      call starttest(test)
      call Args_Basic_passindcomplex_f(obj, in, bretval,throwaway)
      call reporttest(bretval, test,
     $    .false.)
      call starttest(test)
      call Args_Basic_passoutdcomplex_f(obj, out, bretval,throwaway)
      call reporttest(bretval .and. (out .eq. (3.14d0,3.14d0)),
     $     test,  .false.)
      call starttest(test)
      call Args_Basic_passinoutdcomplex_f(obj, inout, bretval,throwaway)
      call reporttest(bretval .and. (inout .eq. (3.14d0,-3.14d0)),
     $     test,  .false.)
      call starttest(test)
      call Args_Basic_passeverywheredcomplex_f(obj, in,
     $     out, inout, cretval,throwaway)
      call reporttest((cretval .eq. (3.14d0,3.14d0)) .and.
     $     (out .eq. (3.14d0,3.14d0)) .and.
     $     (inout .eq. (3.14d0,3.14d0)), test,
     $   .false.)
      call Args_Basic_deleteRef_f(obj,throwaway)
      end

      
      program argstest
      integer*4 test, i
      integer*8 tracker, throwaway
      character*80 language
      character*256 remoteURL, arg
      logical retval
      
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
     $        'sidlx.rmi.SimHandle', retval, throwaway)
         if (retval .neqv. .TRUE. ) then
            write(*,'(A)') 'sidl.rmi.ProtocolFactor.addProtocol()
     $       failed'
         endif
      endif
      
      language = ' '
      if (IArgc() .gt. 1) then
         call GetArg(1, language)
      endif
      test = 1
      call synch_RegOut_setExpectations_f(tracker, 40, throwaway)
      call synch_RegOut_writeComment_f(tracker,
     $     'Boolean tests', throwaway)
      call testbool(test, remoteURL)
      call synch_RegOut_writeComment_f(tracker, 'Character tests',
     $     throwaway)
      call testchar(test, remoteURL)
      call synch_RegOut_writeComment_f(tracker, 'Integer tests',
     $     throwaway)
      call testint(test, remoteURL)
      call synch_RegOut_writeComment_f(tracker, 'Long tests',
     $     throwaway)
      call testlong(test, remoteURL)
      call synch_RegOut_writeComment_f(tracker, 'Float tests',
     $     throwaway)
      call testfloat(test, 
     $     language .eq. 'Python', remote URL)
      call synch_RegOut_writeComment_f(tracker, 'Double tests',
     $     throwaway)
      call testdouble(test, remoteURL)
      call synch_RegOut_writeComment_f(tracker, 'Fcomplex tests',
     $     throwaway)
      call testfcomplex(test, 
     $     language .eq. 'Python', remoteURL)
      call synch_RegOut_writeComment_f(tracker, 'Dcomplex tests',
     $     throwaway)
      call testDcomplex(test, remoteURL)
      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker,throwaway)
      end
