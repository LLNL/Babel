C
C File:       structtest.f
C Copyright:  (c) 2009 Lawrence Livermore National Security, LLC
C Revision:   @(#) $Revision: 6183 $
C Date:       $Date: 2009-05-26 15:41:51 -0600 (Tue, 26 May 2009) $
C Description:Exercise the FORTRAN interface
C

C     support routines for structs of type s.Simple
      
      logical function checkSimple_f77c(s)
      implicit none
C          in s.Simple s
      integer*8 s

      logical d_bool
      character d_char
      double complex d_dcomplex
      double precision d_double
      real d_float
      complex d_fcomplex
      integer*4 d_int
      integer*8 d_long
      integer*8 d_opaque
      integer*8 d_enum

      double precision eps 
      parameter(eps=1E-6)

      include 's_Color.inc'

      call s_Simple_Get_d_bool_f(s, d_bool)
      call s_Simple_Get_d_char_f(s, d_char)
      call s_Simple_Get_d_dcomplex_f(s, d_dcomplex)
      call s_Simple_Get_d_double_f(s, d_double)
      call s_Simple_Get_d_float_f(s, d_float)
      call s_Simple_Get_d_fcomplex_f(s, d_fcomplex)
      call s_Simple_Get_d_int_f(s, d_int)
      call s_Simple_Get_d_long_f(s, d_long)
      call s_Simple_Get_d_opaque_f(s, d_opaque)
      call s_Simple_Get_d_enum_f(s, d_enum)
      
      if ((d_bool .eqv. .true.) .and. 
     &     (d_char .eq. '3' ) .and. 
     &     (abs(dble(d_dcomplex) - 3.14d0) .lt. eps) .and. 
     &     (abs(dimag(d_dcomplex) - 3.14d0) .lt. eps) .and. 
     &     (abs(d_double - 3.14d0) .lt. eps ) .and. 
     &     (abs(d_float - 3.1) .lt. eps ) .and. 
     &     (abs(real(d_fcomplex) - 3.1) .lt. eps) .and. 
     &     (abs(aimag(d_fcomplex) - 3.1) .lt. eps) .and. 
     &     (d_int .eq. 3 ) .and. 
     &     (d_long .eq. 3) .and. 
     &     (d_opaque .eq. 0) .and. 
     &     (d_enum .eq. blue )) then
         checkSimple_f77c=.true.
      else
         checkSimple_f77c=.false.
      endif
      return
      end

      logical function checkSimpleInv_f77c(s)
      implicit none
C          in s.Simple s
      integer*8 s

      logical d_bool
      character d_char
      double complex d_dcomplex
      double precision d_double
      real d_float
      complex d_fcomplex
      integer*4 d_int
      integer*8 d_long
      integer*8 d_opaque
      integer*8 d_enum

      double precision eps 
      parameter(eps=1E-6)

      include 's_Color.inc'

      call s_Simple_Get_d_bool_f(s, d_bool)
      call s_Simple_Get_d_char_f(s, d_char)
      call s_Simple_Get_d_dcomplex_f(s, d_dcomplex)
      call s_Simple_Get_d_double_f(s, d_double)
      call s_Simple_Get_d_float_f(s, d_float)
      call s_Simple_Get_d_fcomplex_f(s, d_fcomplex)
      call s_Simple_Get_d_int_f(s, d_int)
      call s_Simple_Get_d_long_f(s, d_long)
      call s_Simple_Get_d_opaque_f(s, d_opaque)
      call s_Simple_Get_d_enum_f(s, d_enum)
      
      if ((d_bool .eqv. .false.) .and. 
     &     (d_char .eq. '3' ) .and. 
     &     (abs(dble(d_dcomplex) - 3.14d0) .lt. eps) .and. 
     &     (abs(dimag(d_dcomplex) + 3.14d0) .lt. eps) .and. 
     &     (abs(d_double + 3.14d0) .lt. eps ) .and. 
     &     (abs(d_float + 3.1) .lt. eps ) .and. 
     &     (abs(real(d_fcomplex) - 3.1) .lt. eps) .and. 
     &     (abs(aimag(d_fcomplex) + 3.1) .lt. eps) .and. 
     &     (d_int .eq. -3 ) .and. 
     &     (d_long .eq. -3) .and. 
     &     (d_opaque .eq. 0) .and. 
     &     (d_enum .eq. red )) then
         checkSimpleInv_f77c=.true.
      else
         checkSimpleInv_f77c=.false.
      endif
      return
      end

      logical function checkHard_f77c(s)
      implicit none
C      in s.Hard s
      integer*8 s

      integer*8 d_object
      integer*8 d_interface
      integer*8 d_string
      integer*8 d_array
      integer*8 d_objectArray

      integer*8 exception, obj
      real*8 d
      integer*4 i
      character*512 str
      logical b
    
      call s_Hard_Get_d_object_f(s, d_object)
      call s_Hard_Get_d_interface_f(s, d_interface)
      call s_Hard_Get_d_string_f(s, d_string)
      call s_Hard_Get_d_array_f(s, d_array)
      call s_Hard_Get_d_objectArray_f(s, d_objectArray)

      exception = 0

      call sidl_double__array_dimen_f(d_array, i)
      if (i .ne. 1) then
         checkHard_f77c = .false.
         return
      endif

      call sidl_double__array_length_f(d_array, 0, i)
      if (i .ne. 3) then
         checkHard_f77c = .false.
         return
      endif

      do 10 i = 0, 2
         call sidl_double__array_get1_f(d_array, i, d)
         if (d .ne. 1.0 * (i + 1)) then
            checkHard_f77c = .false.
            return
         endif
 10   continue

      call sidl_string__array_dimen_f(d_string, i)
      if (i .ne. 1) then
         checkHard_f77c = .false.
         return
      endif

      call sidl_string__array_length_f(d_string, 0, i)
      if (i .ne. 1) then
         checkHard_f77c = .false.
         return
      endif

      call sidl_string__array_get1_f(d_string, 0, str)
      if (str .ne. 'Three') then
         checkHard_f77c = .false.
         return
      endif

C     check base class and interface
      if ((d_object .eq. 0) .or. (d_interface .eq. 0)) then
         checkHard_f77c = .false.
         return
      endif

      call sidl_BaseInterface_isSame_f(d_object, d_interface,
     &     b, exception)
      if ((exception .ne. 0) .or. (b .eqv. .false.)) then
         checkHard_f77c = .false.
         return
      endif


C     check for array of objects 
      call sidl_BaseClass__array_dimen_f(d_objectArray, i)
      if (i .ne. 1) then
         checkHard_f77c = .false.
         return
      endif

      call sidl_BaseClass__array_length_f(d_objectArray, 0, i)
      if (i .ne. 3) then
         checkHard_f77c = .false.
         return
      endif

      do 20 i = 0, 2
         call sidl_BaseClass__array_get1_f(d_objectArray, i, obj)
         if (obj .eq. 0) then
            checkHard_f77c = .false.
            return
         endif
         call sidl_BaseClass_isType_f(
     &        obj, 'sidl.BaseInterface', b, exception)
         if ((exception .ne. 0) .or. (b .eqv. .false.)) then
            checkHard_f77c = .false.
            return
         endif
         call sidl_BaseClass_deleteRef_f(obj, exception)
 20   continue

      checkHard_f77c = .true.      
      return
      end


      logical function checkHardInv_f77c(s)
      implicit none
C      in s.Hard s
      integer*8 s

      integer*8 d_object
      integer*8 d_interface
      integer*8 d_string
      integer*8 d_array
      integer*8 d_objectArray

      integer*8 exception, obj
      real*8 d
      integer*4 i
      character*512 str
      logical b, result
    
      call s_Hard_Get_d_object_f(s, d_object)
      call s_Hard_Get_d_interface_f(s, d_interface)
      call s_Hard_Get_d_string_f(s, d_string)
      call s_Hard_Get_d_array_f(s, d_array)
      call s_Hard_Get_d_objectArray_f(s, d_objectArray)

      exception = 0

      call sidl_double__array_dimen_f(d_array, i)
      if (i .ne. 1) then
         checkHardInv_f77c = .false.
         return
      endif

      call sidl_double__array_length_f(d_array, 0, i)
      if (i .ne. 3) then
         checkHardInv_f77c = .false.
         return
      endif

      do 10 i = 0, 2
         call sidl_double__array_get1_f(d_array, i, d)
         if (d .ne. 3.0 - (1.0 * i)) then
            checkHardInv_f77c = .false.
            return
         endif
 10   continue

      call sidl_string__array_dimen_f(d_string, i)
      if (i .ne. 1) then
         checkHardInv_f77c = .false.
         return
      endif

      call sidl_string__array_length_f(d_string, 0, i)
      if (i .ne. 1) then
         checkHardInv_f77c = .false.
         return
      endif

      call sidl_string__array_get1_f(d_string, 0, str)
      if (str .ne. 'three') then
         checkHardInv_f77c = .false.
         return
      endif

C     check base class and interface
      if ((d_object .eq. 0) .or. (d_interface .eq. 0)) then
         checkHardInv_f77c = .false.
         return
      endif

      call sidl_BaseInterface_isSame_f(d_object, d_interface,
     &     b, exception)
      if ((exception .ne. 0) .or. (b .eqv. .true.)) then
         checkHardInv_f77c = .false.
         return
      endif


C     check for array of objects 
      call sidl_BaseClass__array_dimen_f(d_objectArray, i)
      if (i .ne. 1) then
         checkHardInv_f77c = .false.
         return
      endif

      call sidl_BaseClass__array_length_f(d_objectArray, 0, i)
      if (i .ne. 3) then
         checkHardInv_f77c = .false.
         return
      endif

      do 20 i = 0, 2
         call sidl_BaseClass__array_get1_f(d_objectArray, i, obj)
         result = .true.
         if(i .eq. 1) then
            if (obj .ne. 0) then
               result = .false.
            endif
         else
            if (obj .eq. 0) then
               result = .false.
            else
               call sidl_BaseClass_isType_f(
     &              obj, 'sidl.BaseClass', b, exception)
               if ((exception .ne. 0) .or. (b .eqv. .false.)) then
                  result = .false.
               endif
            endif
         endif
         
         if(obj .ne. 0) then
            call sidl_BaseClass_deleteRef_f(obj, exception)
         endif

         if(result .eqv. .false.) then
            checkHardInv_f77c = .false.
            return
         endif 
         
 20   continue

      checkHardInv_f77c = .true.      
      return
      end
      
      logical function checkCombined_f77c(s)
      implicit none
C          in s.Combined s
      integer*8 s

      integer*8 d_simple, d_hard
      logical checkSimple_f77c, checkHard_f77c
      
      call s_Combined_Get_d_simple_f(s, d_simple)
      if (checkSimple_f77c(d_simple) .eqv. .false.) then
         checkCombined_f77c = .false.
         return
      endif

      call s_Combined_Get_d_hard_f(s, d_hard)
      if (checkHard_f77c(d_hard) .eqv. .false.) then
         checkCombined_f77c = .false.
         return
      endif
      
      checkCombined_f77c=.true.
      return
      end

      logical function checkCombinedInv_f77c(s)
      implicit none
C          in s.Combined s
      integer*8 s

      integer*8 d_simple, d_hard
      logical checkSimpleInv_f77c, checkHardInv_f77c
      
      call s_Combined_Get_d_simple_f(s, d_simple)
      if (checkSimpleInv_f77c(d_simple) .eqv. .false.) then
         checkCombinedInv_f77c = .false.
         return
      endif

      call s_Combined_Get_d_hard_f(s, d_hard)
      if (checkHardInv_f77c(d_hard) .eqv. .false.) then
         checkCombinedInv_f77c = .false.
         return
      endif
      
      checkCombinedInv_f77c=.true.
      return
      end

      subroutine initRarrays_f77c(s)
      implicit none

C          inout s.Rarrays s
      integer*8 s, exception
      integer*8 rarrayRaw
      integer*8 rarrayFix
      real*8 r1, r3
      real*8 f1, f3

      call s_Rarrays_Set_d_int_f(s, 3)    

C     Alloc d_rarrayRaw
      call s_Rarrays_rarray_alloc_f(s, exception)
      call catch(exception)

      call s_Rarrays_Get_d_rarrayRaw_f(s, rarrayRaw)
      call s_Rarrays_Get_d_rarrayFix_f(s, rarrayFix)
      call sidl_double__array_set1_f(rarrayRaw, 0, 1d0)
      call sidl_double__array_set1_f(rarrayRaw, 1, 2d0)
      call sidl_double__array_set1_f(rarrayRaw, 2, 3d0)
      call sidl_double__array_set1_f(rarrayFix, 0, 5d0)
      call sidl_double__array_set1_f(rarrayFix, 1, 10d0)
      call sidl_double__array_set1_f(rarrayFix, 2, 15d0)

      end subroutine initRarrays_f77c
      

      logical function checkRarrays_f77c(s)
      implicit none
C          in s.Rarrays s
      integer*8 s
      integer*8 rarrayRaw
      integer*8 rarrayFix
      integer*4 d
      real*8 r1, r2, r3
      real*8 f1, f2, f3

      call s_Rarrays_Get_d_int_f(s, d)
      call s_Rarrays_Get_d_rarrayRaw_f(s, rarrayRaw)
      call s_Rarrays_Get_d_rarrayFix_f(s, rarrayFix)
      call sidl_double__array_get1_f(rarrayRaw, 0, r1)
      call sidl_double__array_get1_f(rarrayRaw, 1, r2)
      call sidl_double__array_get1_f(rarrayRaw, 2, r3)
      call sidl_double__array_get1_f(rarrayFix, 0, f1)
      call sidl_double__array_get1_f(rarrayFix, 1, f2)
      call sidl_double__array_get1_f(rarrayFix, 2, f3)
      checkRarrays_f77c=.false.

      if ((d .eq. 3) .and. 
     &    (r1 .eq. 1.0) .and. 
     &    (r2 .eq. 2.0) .and. 
     &    (r3 .eq. 3.0) .and. 
     &    (f1 .eq. 5.0) .and. 
     &    (f2 .eq. 10.0) .and. 
     &    (f3 .eq. 15.0)) then
         checkRarrays_f77c=.true.
      endif

      return
      
      end function checkRarrays_f77c

      logical function checkRarraysInv_f77c(s)
      implicit none
C          in s.Rarrays s
      integer*8 s
      integer*8 rarrayRaw
      integer*8 rarrayFix
      integer*4 d
      real*8 r1, r2, r3
      real*8 f1, f2, f3

      call s_Rarrays_Get_d_int_f(s, d)
      call s_Rarrays_Get_d_rarrayRaw_f(s, rarrayRaw)
      call s_Rarrays_Get_d_rarrayFix_f(s, rarrayFix)
      call sidl_double__array_get1_f(rarrayRaw, 0, r1)
      call sidl_double__array_get1_f(rarrayRaw, 1, r2)
      call sidl_double__array_get1_f(rarrayRaw, 2, r3)
      call sidl_double__array_get1_f(rarrayFix, 0, f1)
      call sidl_double__array_get1_f(rarrayFix, 1, f2)
      call sidl_double__array_get1_f(rarrayFix, 2, f3)
      checkRarraysInv_f77c=.false.

      if ((d .eq. 3) .and. 
     &    (r1 .eq. 3.0) .and. 
     &    (r2 .eq. 2.0) .and. 
     &    (r3 .eq. 1.0) .and. 
     &    (f1 .eq. 15.0) .and. 
     &    (f2 .eq. 10.0) .and. 
     &    (f3 .eq. 5.0)) then
         checkRarraysInv_f77c=.true.
      endif

      return
      
      end function checkRarraysInv_f77c

C     ==============================

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
         call s_StructTest__createRemote_f(obj, remoteURL, throwaway)
      else
         call s_StructTest__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end
      
      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker, throwaway
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_startPart_f(tracker, number, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      end

      subroutine reporttest(test, number, python)
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

      program structtest
      implicit none
      integer*4 test, i, IArgc
      integer*8 obj, tracker, throwaway, exception
      character*80  language
      character*256 remoteURL, arg
      logical retval
      logical checkSimple_f77c, checkHard_f77c, checkCombined_f77c
      logical checkSimpleInv_f77c, checkHardInv_f77c
      logical checkRarrays_f77c, checkRarraysInv_f77c
      logical checkCombinedInv_f77c

      integer*8 o1, o2, o3, o4
      
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
      if ( remoteURL .ne. '' ) then
         call synch_RegOut_setExpectations_f(tracker, 12, throwaway)
      else
         call synch_RegOut_setExpectations_f(tracker, 27, throwaway)
      endif            

      call makeObject(obj, remoteURL)

C     ================================================================
      call synch_RegOut_writeComment_f(tracker, 'Testing s.Empty',
     $     throwaway)

      call s_Empty_Alloc_f(o1, exception)
      call catch(exception)
      call s_Empty_Alloc_f(o2, exception)
      call catch(exception)
      call s_Empty_Alloc_f(o3, exception)
      call catch(exception)
      call s_Empty_Alloc_f(o4, exception)
      call catch(exception)
      
      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing returnEmpty', throwaway)
      call s_StructTest_returnEmpty_f(obj, o1, exception)
      call catch(exception)
      retval = .true.
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passinEmpty', throwaway)
      call s_StructTest_passinEmpty_f(obj, o1, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passoutEmpty', throwaway)
      call s_StructTest_passoutEmpty_f(obj, o2, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passinoutEmpty', throwaway)
      call s_StructTest_passinoutEmpty_f(obj, o2, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)
      
      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passoutEmpty', throwaway)
      call s_StructTest_passoutEmpty_f(obj, o3, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passeverywhereEmpty', throwaway)
      call s_StructTest_passeverywhereEmpty_f(obj, o1, o2, o3, o4,
     $     exception)
      call catch(exception)
      call reporttest(retval, test, .false.)
      
      call s_Empty_Dealloc_f(o1, exception)
      call catch(exception)
      call s_Empty_Dealloc_f(o2, exception)
      call catch(exception)
      call s_Empty_Dealloc_f(o3, exception)
      call catch(exception)
      call s_Empty_Dealloc_f(o4, exception)
      call catch(exception)

      

C     ================================================================
      call synch_RegOut_writeComment_f(tracker, 'Testing s.Simple',
     $     throwaway)

      call s_Simple_Alloc_f(o1, exception)
      call catch(exception)
      call s_Simple_Alloc_f(o2, exception)
      call catch(exception)
      call s_Simple_Alloc_f(o3, exception)
      call catch(exception)
      call s_Simple_Alloc_f(o4, exception)
      call catch(exception)
      
      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing returnSimple', throwaway)
      call s_StructTest_returnSimple_f(obj, o1, exception)
      call catch(exception)
      retval = checkSimple_f77c(o1)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passinSimple', throwaway)
      call s_StructTest_passinSimple_f(obj, o1, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passoutSimple', throwaway)
      call s_StructTest_passoutSimple_f(obj, o2, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passinoutSimple', throwaway)
      call s_StructTest_passinoutSimple_f(obj, o2, retval, exception)
      call catch(exception)
      retval = retval .and. checkSimpleInv_f77c(o2)
      call reporttest(retval, test, .false.)
      
      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passoutSimple', throwaway)
      call s_StructTest_passoutSimple_f(obj, o3, retval, exception)
      call catch(exception)
      call reporttest(retval, test, .false.)

      call starttest(test)
      call synch_RegOut_writeComment_f(tracker,
     $     'executing passeverywhereSimple', throwaway)
      call s_StructTest_passeverywhereSimple_f(obj, o1, o2, o3, o4,
     $     exception)
      call catch(exception)
      retval = checkSimple_f77c(o4) .and.
     $     checkSimple_f77c(o1) .and.
     $     checkSimpleInv_f77c(o3)
      call reporttest(retval, test, .false.)
      
      call s_Simple_Dealloc_f(o1, exception)
      call catch(exception)
      call s_Simple_Dealloc_f(o2, exception)
      call catch(exception)
      call s_Simple_Dealloc_f(o3, exception)
      call catch(exception)
      call s_Simple_Dealloc_f(o4, exception)
      call catch(exception)


C     some elements in s.Hard can't be passed as they are not
C     serializable, so skip these tests for RMI
C     ================================================================
      if ( remoteURL .eq. '' ) then
         call synch_RegOut_writeComment_f(tracker, 'Testing s.Hard',
     $        throwaway)

         call s_Hard_Alloc_f(o1, exception)
         call catch(exception)
         call s_Hard_Alloc_f(o2, exception)
         call catch(exception)
         call s_Hard_Alloc_f(o3, exception)
         call catch(exception)
         call s_Hard_Alloc_f(o4, exception)
         call catch(exception)
      
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing returnHard', throwaway)
         call s_StructTest_returnHard_f(obj, o1, exception)
         call catch(exception)
         retval = checkHard_f77c(o1)
         call reporttest(retval, test, .false.)
         
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passinHard', throwaway)
         call s_StructTest_passinHard_f(obj, o1, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passoutHard', throwaway)
         call s_StructTest_passoutHard_f(obj, o2, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passinoutHard', throwaway)
         call s_StructTest_passinoutHard_f(obj, o2, retval, exception)
         call catch(exception)
         retval = retval .and. checkHardInv_f77c(o2)
         call reporttest(retval, test, .false.)
      
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passoutHard', throwaway)
         call s_StructTest_passoutHard_f(obj, o3, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passeverywhereHard', throwaway)
         call s_StructTest_passeverywhereHard_f(obj, o1, o2, o3, o4,
     $        exception)
         call catch(exception)
         retval = checkHard_f77c(o4) .and.
     $        checkHard_f77c(o1) .and.
     $        checkHardInv_f77c(o3)
         call reporttest(retval, test, .false.)
         
         call s_Hard_Dealloc_f(o1, exception)
         call catch(exception)
         call s_Hard_Dealloc_f(o2, exception)
         call catch(exception)
         call s_Hard_Dealloc_f(o3, exception)
         call catch(exception)
         call s_Hard_Dealloc_f(o4, exception)
         call catch(exception)
      endif

C     ================================================================
      if ( remoteURL .eq. '' ) then
         call synch_RegOut_writeComment_f(tracker, 'Testing s.Rarray',
     $        throwaway)

         call s_Rarrays_Alloc_f(o1, exception)
         call catch(exception)
         call s_Rarrays_Alloc_f(o2, exception)
         call catch(exception)
      
         call initRarrays_f77c(o1)
         
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passinRarrays', throwaway)
         call s_StructTest_passinRarrays_f(obj, o1, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passinoutRarrays', throwaway)
         call s_StructTest_passinoutRarrays_f(obj, o1, retval, 
     $        exception)
         call catch(exception)
         retval = retval .and. checkRarraysInv_f77c(o1)
         call reporttest(retval, test, .false.)
      
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passeverywhereRarrays', throwaway)
         call s_Rarrays_rarray_dealloc_f(o1, exception)
         call catch(exception)
         call initRarrays_f77c(o1)
         call initRarrays_f77c(o2)
         call s_StructTest_passeverywhereRarrays_f(obj, o1, o2, 
     $        retval, exception)
         call catch(exception)
         retval = checkRarrays_f77c(o1) .and.
     $        checkRarraysInv_f77c(o2)
         call reporttest(retval, test, .false.)
         
         call s_Rarrays_rarray_dealloc_f(o1, exception)
         call catch(exception)
         call s_Rarrays_rarray_dealloc_f(o2, exception)
         call catch(exception)
         call s_Rarrays_Dealloc_f(o1, exception)
         call catch(exception)
         call s_Rarrays_Dealloc_f(o2, exception)
         call catch(exception)
      endif

C     ================================================================
      if ( remoteURL .eq. '' ) then
         call synch_RegOut_writeComment_f(tracker, 'Testing s.Combined',
     $        throwaway)
         
         call s_Combined_Alloc_f(o1, exception)
         call catch(exception)
         call s_Combined_Alloc_f(o2, exception)
         call catch(exception)
         call s_Combined_Alloc_f(o3, exception)
         call catch(exception)
         call s_Combined_Alloc_f(o4, exception)
         call catch(exception)
         
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing returnCombined', throwaway)
         call s_StructTest_returnCombined_f(obj, o1, exception)
         call catch(exception)
         retval = checkCombined_f77c(o1)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passinCombined', throwaway)
         call s_StructTest_passinCombined_f(obj, o1, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passoutCombined', throwaway)
         call s_StructTest_passoutCombined_f(obj, o2, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passinoutCombined', throwaway)
         call s_StructTest_passinoutCombined_f(obj, o2, retval, 
     $        exception)
         call catch(exception)
         retval = retval .and. checkCombinedInv_f77c(o2)
         call reporttest(retval, test, .false.)
         
         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passoutCombined', throwaway)
         call s_StructTest_passoutCombined_f(obj, o3, retval, exception)
         call catch(exception)
         call reporttest(retval, test, .false.)

         call starttest(test)
         call synch_RegOut_writeComment_f(tracker,
     $        'executing passeverywhereCombined', throwaway)
         call s_StructTest_passeverywhereCombined_f(obj, o1, o2, o3, o4,
     $        exception)
         call catch(exception)
         retval = checkCombined_f77c(o4) .and.
     $        checkCombined_f77c(o1) .and.
     $        checkCombinedInv_f77c(o3)
         call reporttest(retval, test, .false.)
         
         call s_Combined_Dealloc_f(o1, exception)
         call catch(exception)
         call s_Combined_Dealloc_f(o2, exception)
         call catch(exception)
         call s_Combined_Dealloc_f(o3, exception)
         call catch(exception)
         call s_Combined_Dealloc_f(o4, exception)
         call catch(exception)
      endif
      
C     ================================================================
      
      call synch_RegOut_writeComment_f(tracker, 'Cleaning up',
     $     throwaway)
      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker,throwaway)

      
      end
