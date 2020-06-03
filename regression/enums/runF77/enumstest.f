C
C File:       enumstests.f
C Copyright:  (c) 2010 Lawrence Livermore National Security, LLC
C Revision:   @(#) $Revision$
C Date:       $Date$
C Description:Exercise the FORTRAN interface
C
C

 
      subroutine reporttest(test, number, python, tracker)
      implicit none
      integer*4       number
      logical         test, python
      integer*8       tracker 
      integer*8       throwaway
      include   'synch_ResultType.inc'

      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_startPart_f(tracker, number, throwaway)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        throwaway)
      else
      if (python) then
         call synch_RegOut_endPart_f(tracker, number, XFAIL,
     $        throwaway)
      else
         call synch_RegOut_endPart_f(tracker, number, FAIL,
     $        throwaway)
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

C --------------------------------------

      logical function toBool(enumval)
      implicit none
      integer*8 enumval
      if (enumval .eq. 0) then
         toBool = .false.
      else
         toBool = .true.
      end if 
      end function toBool

      subroutine checkColors(test, python, tracker, remoteURL)
      implicit none
      integer*4 test
      logical python
      character*256 remoteURL
      logical toBool
      integer*8 barray, tracker
      integer*8 throwaway
      integer*8 out, inout
C     enums.colorwheel obj
      integer*8        obj
      integer*8 retval

      include 'enums_color.inc'
      out = -5
      inout = green

      if ( remoteURL .ne. '' ) then
         call enums_colorwheel__createRemote_f(obj,remoteURL,throwaway)
      else
         call enums_colorwheel__create_f(obj,throwaway)
      endif
      call catch(throwaway)
    
      call enums_colorwheel_returnback_f(obj, retval, throwaway)
      call reporttest(retval .eq. violet, test, python, tracker)
      call enums_colorwheel_passin_f(obj, blue, retval, throwaway)
      call reporttest(toBool(retval), test, python, tracker)
      call enums_colorwheel_passout_f(obj, out, retval, throwaway)
      call reporttest(toBool(retval) .and. (out .eq. violet), 
     $     test, python, tracker)
      call enums_colorwheel_passinout_f(obj, inout, retval, throwaway)
      call reporttest(toBool(retval) .and. (inout .eq. red), 
     $     test, python, tracker)
      call enums_colorwheel_passeverywhere_f(obj, blue, out, inout,
     $     retval, throwaway)
      call reporttest((retval .eq. violet) 
     $     .and. (out .eq. violet) 
     $     .and. (inout .eq. green),
     $     test, python, tracker)
      
      end

C --------------------------------------

      subroutine checkCars(test, python, tracker, remoteURL)
      implicit none
      integer*4 test
      logical python
      logical toBool
      integer*8 barray, tracker
      character*256 remoteURL
      integer*8 throwaway
      integer*8 out, inout
C     enums.car obj
      integer*8  obj
      integer*8 retval, ref
      integer*8 array_in, array_out, array_inout
      integer*8 c1, c2, c3, result
      integer*4 i

      include 'enums_car.inc'
      out = -5 
      inout = ford
      if ( remoteURL .ne. '' ) then
         call enums_cartest__createRemote_f(obj,remoteURL,throwaway)
      else
         call enums_cartest__create_f(obj,throwaway)
      endif
      call catch(throwaway)
    
      call enums_cartest_returnback_f(obj, retval, throwaway)
      call reporttest(retval .eq. porsche, test, python, tracker)
      call enums_cartest_passin_f(obj, mercedes, retval, throwaway)
      call reporttest(toBool(retval), test, python, tracker)
      call enums_cartest_passout_f(obj, out, retval, throwaway)
      call reporttest(toBool(retval) .and. (out .eq. ford), 
     $     test, python, tracker)
      call enums_cartest_passinout_f(obj, inout, retval, throwaway)
      call reporttest(toBool(retval) .and. (inout .eq. porsche), 
     $     test, python, tracker)
      call enums_cartest_passeverywhere_f(obj, mercedes, out, inout,
     $     retval, throwaway)
      call reporttest((retval .eq. porsche) 
     $     .and. (out .eq. ford) 
     $     .and. (inout .eq. mercedes),
     $     test, python, tracker)

      call enums_car__array_create1d_f(3, array_in)
      call enums_car__array_create1d_f(3, array_inout)

      call enums_car__array_set1_f(array_in, 0, ford)
      call enums_car__array_set1_f(array_in, 1, mercedes)
      call enums_car__array_set1_f(array_in, 2, porsche)

      call enums_car__array_copy_f(array_in, array_inout)

      call enums_cartest_passarray_f(obj, array_in, array_out,
     $     array_inout, retval, throwaway)
      result = 1
      do i = 0, 2
         call enums_car__array_get1_f(array_in, i, ref)
         call enums_car__array_get1_f(retval, i, c1)
         call enums_car__array_get1_f(array_out, i, c2)
         call enums_car__array_get1_f(array_inout, i, c3)
         if ((c1 .ne. ref) .or. (c2 .ne. ref) .or. (c3 .ne. ref)) then
            result = 0
         end if
      end do
      call reporttest(toBool(result), test, python, tracker)

      call enums_car__array_deleteref_f(array_in, throwaway)
      call enums_car__array_deleteref_f(array_out, throwaway)
      call enums_car__array_deleteref_f(array_inout, throwaway)   
      call enums_car__array_deleteref_f(retval, throwaway)

      ! TODO: write generic enumarray testcases

      end

C --------------------------------------

      subroutine checkNumbers(test, python, tracker, remoteURL)
      implicit none
      integer*4 test
      logical python
      logical toBool
      integer*8 barray, tracker
      character*256 remoteURL
      integer*8 throwaway
      integer*8 out, inout
C     enums.colorwheel obj
      integer*8        obj
      integer*8 retval

      include 'enums_number.inc'
      out = -5
      inout = zero
      if ( remoteURL .ne. '' ) then
         call enums_numbertest__createRemote_f(obj,remoteURL,throwaway)
      else
         call enums_numbertest__create_f(obj,throwaway)
      endif
      call catch(throwaway)   

      call enums_numbertest_returnback_f(obj, retval, throwaway)
      call reporttest(retval .eq. notOne, test, python, tracker)
      call enums_numbertest_passin_f(obj, notZero, retval, throwaway)
      call reporttest(toBool(retval), test, python, tracker)
      call enums_numbertest_passout_f(obj, out, retval, throwaway)
      call reporttest(toBool(retval) .and. (out .eq. negOne), 
     $     test, python, tracker)
      call enums_numbertest_passinout_f(obj, inout, retval, throwaway)
      call reporttest(toBool(retval) .and. (inout .eq. notZero), 
     $     test, python, tracker)
      call enums_numbertest_passeverywhere_f(obj, notZero, out, inout,
     $     retval, throwaway)
      call reporttest((retval .eq. notOne) 
     $     .and. (out .eq. negOne) 
     $     .and. (inout .eq. zero),
     $     test, python, tracker)
      
      end
C --------------------------------------

      program enumstests
      implicit none
      integer*8 tracker, throwaway
      integer*4 test, IArgc, i
      character*80 language
      character*256 remoteURL, arg
      logical ispython, retval

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
      language = ' '
      if (IArgc() .gt. 1) then
         call GetArg(1, language)
      endif
      ispython = language .eq. 'Python'
      test = 1
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_setExpectations_f(tracker, 16, throwaway)

      call synch_RegOut_writeComment_f(tracker, 
     $     'undefined integer values', throwaway)
      call CheckColors(test, ispython, tracker, remoteURL)

      call synch_RegOut_writeComment_f(tracker, 
     $     'partially defined integer values', throwaway)
      call CheckCars(test, ispython, tracker, remoteURL)

      call synch_RegOut_writeComment_f(tracker, 
     $     'fully defined integer values', throwaway)
      call CheckNumbers(test, ispython, tracker, remoteURL)

      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)

      end 
