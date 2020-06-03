C     
C File:        inherittest.f
C Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
C Revision:    $Revision: 6813 $
C Date:        $Date: 2009-12-02 14:25:29 -0800 (Wed, 02 Dec 2009) $
C Description: Regression test to test FORTRAN calls to BABEL
C
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

      subroutine castcheck(partno, sourcename,
     $     destname, pointer)
      implicit none
      integer*4 partno
      character *(*) sourcename, destname
      character*(1024) buffer
      integer*8 pointer, tracker, tae
      include 'synch_ResultType.inc'
      partno = partno + 1
      call synch_RegOut_getInstance_f(tracker, tae)
      call synch_RegOut_startPart_f(tracker, partno, tae)
      call catch(tae)
      buffer = 'Casting ' // sourcename // ' to ' //
     $     destname
      call synch_RegOut_writeComment_f(tracker, buffer, tae)
      call catch(tae)
      if (pointer .ne. 0) then
         call synch_RegOut_endPart_f(tracker, partno, PASS,
     $        tae)
         call catch(tae)
      else
         call synch_RegOut_endPart_f(tracker, partno, FAIL,
     $        tae)
         call catch(tae)
      endif
      call synch_RegOut_deleteRef_f(tracker, tae)
      call catch(tae)
      end
      

      subroutine reporttest(partno, methodname,
     $     expectedresult, result)
      implicit none
      integer*4 partno, numpassed
      character *(*) methodname, expectedresult, result
      character*(1024) buffer
      integer*8 tracker, tae
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker, tae)
      partno = partno + 1
      call synch_RegOut_startPart_f(tracker, partno, tae)
      call catch(tae)
      buffer = 'Method Inherit_' // methodname //
     $     ' should return ' // expectedresult
      call synch_RegOut_writeComment_f(tracker, buffer, tae)
      call catch(tae)
      buffer = 'Method Inherit_' // methodname //
     $     ' returned ' //  result
      call synch_RegOut_writeComment_f(tracker, buffer, tae)
      call catch(tae)
      if (result .eq. expectedresult) then
         call synch_RegOut_endPart_f(tracker, partno, PASS,
     $        tae)
         call catch(tae)
      else
         call synch_RegOut_endPart_f(tracker, partno, FAIL,
     $        tae)
         call catch(tae)
      endif
      call synch_RegOut_deleteRef_f(tracker, tae)
      call catch(tae)
      end

      subroutine makeCObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_C__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_C__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeDObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_D__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_D__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeEObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_E__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_E__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeE2Object(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_E2__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_E2__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeFObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_F__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_F__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeF2Object(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_F2__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_F2__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeGObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_G__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_G__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeG2Object(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_G2__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_G2__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeIObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_I__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_I__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeJObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_J__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_J__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeKObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_K__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_K__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      subroutine makeLObject(obj, remoteURL)
      integer*8 obj
      character*256 remoteURL
      integer*8 throwaway
      if ( remoteURL .ne. '' ) then
         call Inherit_L__createRemote_f(obj,
     $        remoteURL,throwaway)
      else
         call Inherit_L__create_f(obj, throwaway)
      endif
      call catch(throwaway)
      end

      program inherittest
      implicit none
      integer*4 partno, i ,IArgc
      integer*8 object, altobject, interface, tracker, tae, throwaway
      character*32 strresult
      character*256 remoteURL, arg
      logical retval

      call synch_RegOut_getInstance_f(tracker, tae)
      partno = 0

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
      
      call synch_RegOut_setExpectations_f(tracker, 73, tae)
      call catch(tae)

      call makeCObject(object, remoteURL)
      call synch_RegOut_writeComment_f(tracker,
     $     'Class C:', tae)
      call catch(tae)

      call Inherit_C_c_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'C_c', 'C.c', strresult)
      call Inherit_C_deleteRef_f(object, tae)
      call catch(tae)

      call makeDObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class D: inheritance of interface A', tae)
      call catch(tae)

      call Inherit_D_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'D_a', 'D.a', strresult)

      call Inherit_D_d_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'D_d', 'D.d', strresult)
      

      call synch_RegOut_writeComment_f(tracker,
     $     'Class D: via interface A', tae)
      call catch(tae)

      call Inherit_A__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class D',
     $     'Interface A', interface)
      if (interface .ne. 0) then
         call Inherit_A_a_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'A_a', 'D.a', strresult)
         call Inherit_A_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif
      call Inherit_D_deleteRef_f(object, tae)
      call catch(tae)

      call makeEObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class E: inheritance of class C', tae)
      call catch(tae)

      call Inherit_E_c_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'E_c', 'C.c', strresult)

      call Inherit_E_e_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'E_e', 'E.e', strresult)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class E: via class C (C.c not overridden)', tae)
      call catch(tae)

      call Inherit_C__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class E',
     $     'Class C', altobject)
      if (altobject .ne. 0) then
         call Inherit_C_c_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'C_c', 'C.c', strresult)
         call Inherit_C_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif
      call Inherit_E_deleteRef_f(object, tae)
      call catch(tae)

      call makeE2Object(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class E2: inheritance of class C', tae)
      call catch(tae)

      call Inherit_E2_c_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'E2_c', 'E2.c', strresult)

      call Inherit_E2_e_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'E2_e', 'E2.e', strresult)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class E2: via class C (C.c overridden)', tae)
      call catch(tae)
      call Inherit_C__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class E2',
     $     'Class C', altobject)
      if (altobject .ne. 0) then
         call Inherit_C_c_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'C_c', 'E2.c', strresult)
         call Inherit_C_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif
      call Inherit_E2_deleteRef_f(object, tae)
      call catch(tae)

      call Inherit_E2_m_f(strresult, tae)
      call catch(tae)
      call reporttest(partno, 'E2_m', 'E2.m', strresult)

      call makeFObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class F: Multiple inheritance (no overriding)', tae)
      call catch(tae)

      call Inherit_F_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F_a', 'F.a', strresult)

      call Inherit_F_b_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F_b', 'F.b', strresult)

      call Inherit_F_c_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F_c', 'C.c', strresult)

      call Inherit_F_f_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F_f', 'F.f', strresult)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class F: via interface A', tae)
      call catch(tae)

      call Inherit_A__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class F',
     $     'Interface A', interface)
      if (interface .ne. 0) then
         call Inherit_A_a_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'A_a', 'F.a', strresult)
         call Inherit_A_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif


      call synch_RegOut_writeComment_f(tracker,
     $     'Class F: via interface B', tae)
      call catch(tae)

      call Inherit_B__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class F',
     $     'Interface B', interface)
      if (interface .ne. 0) then
         call Inherit_B_b_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'B_b', 'F.b', strresult)
         call Inherit_B_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif


      call synch_RegOut_writeComment_f(tracker,
     $     'Class F: via class C (no overloading of C.c)', tae)
      call catch(tae)

      call Inherit_C__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class F',
     $     'Class C', altobject)
      if (altobject .ne. 0) then
         call Inherit_C_c_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'C_c', 'C.c', strresult)
         call Inherit_C_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif


      call Inherit_F_deleteRef_f(object, tae)
      call catch(tae)

      call makeF2Object(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class F2: Multiple inheritance (overrides C.c)', tae)
      call catch(tae)

      call Inherit_F2_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F2_a', 'F2.a', strresult)

      call Inherit_F2_b_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F2_b', 'F2.b', strresult)

      call Inherit_F2_c_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F2_c', 'F2.c', strresult)

      call Inherit_F2_f_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'F2_f', 'F2.f', strresult)

      

      call synch_RegOut_writeComment_f(tracker,
     $     'Class F2: via interface A', tae)
      call catch(tae)

      call Inherit_A__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class F2',
     $     'Interface A', interface)
      if (interface .ne. 0) then
         call Inherit_A_a_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'A_a', 'F2.a', strresult)
         call Inherit_A_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif


      call synch_RegOut_writeComment_f(tracker,
     $     'Class F2: via interface B', tae)
      call catch(tae)

      call Inherit_B__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class F2',
     $     'Interface B', interface)
      if (interface .ne. 0) then
         call Inherit_B_b_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'B_b', 'F2.b', strresult)
         call Inherit_B_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif


      call synch_RegOut_writeComment_f(tracker,
     $     'Class F2: via class C (overloads C.c)', tae)
      call catch(tae)

      call Inherit_C__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class F2',
     $     'Class C', altobject)
      if (altobject .ne. 0) then
         call Inherit_C_c_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'C_c', 'F2.c', strresult)
         call Inherit_C_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif


      call Inherit_F2_deleteRef_f(object, tae)
      call catch(tae)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class G: indirect multiple inheritance (no overloads)',
     $     tae)
      call catch(tae)

      call makeGObject(object, remoteURL)
      call Inherit_G_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'G_a', 'D.a', strresult)

      call Inherit_G_d_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'G_d', 'D.d', strresult)

      call Inherit_G_g_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'G_g', 'G.g', strresult)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class G: via interface A', tae)
      call catch(tae)

      call Inherit_A__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class G',
     $     'Interface A', interface)
      if (interface .ne. 0) then
         call Inherit_A_a_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'A_a', 'D.a', strresult)
         call Inherit_A_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif

      call synch_RegOut_writeComment_f(tracker,
     $     'Class G: via class D', tae)
      call catch(tae)

      call Inherit_D__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class G',
     $     'Class D', altobject)
      if (altobject .ne. 0) then
         call Inherit_D_a_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'D_a', 'D.a', strresult)


         call Inherit_D_d_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'D_d', 'D.d', strresult)
         call Inherit_D_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif


      call Inherit_G_deleteRef_f(object, tae)
      call catch(tae)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class G2: indirect multiple inheritance (overloads)', tae)
      call catch(tae)

      call makeG2Object(object, remoteURL)
      call Inherit_G2_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'G2_a', 'G2.a', strresult)

      call Inherit_G2_d_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'G2_d', 'G2.d', strresult)

      call Inherit_G2_g_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'G2_g', 'G2.g', strresult)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class G2: via interface A', tae)
      call catch(tae)

      call Inherit_A__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class G2',
     $     'Interface A', interface)
      if (interface .ne. 0) then
         call Inherit_A_a_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'A_a', 'G2.a', strresult)
         call Inherit_A_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif


      call synch_RegOut_writeComment_f(tracker,
     $     'Class G2: via class D', tae)
      call catch(tae)

      call Inherit_D__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class G2',
     $     'Class D', altobject)
      if (altobject .ne. 0) then
         call Inherit_D_a_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'D_a', 'G2.a', strresult)


         call Inherit_D_d_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'D_d', 'G2.d', strresult)
         call Inherit_D_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif

      call Inherit_G2_deleteRef_f(object, tae)
      call catch(tae)

      call makeIObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class I:', tae)
      call catch(tae)

      call Inherit_I_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'I_a', 'I.a', strresult)


      call Inherit_I_h_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'I_h', 'I.h', strresult)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class I: via class H', tae)
      call catch(tae)

      call Inherit_H__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class I',
     $     'Class H', altobject)
      if (altobject .ne. 0) then
         call Inherit_H_a_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'H_a', 'I.a', strresult)


         call Inherit_H_h_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'H_h', 'I.h', strresult)
         call Inherit_H_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif

      call Inherit_I_deleteRef_f(object, tae)
      call catch(tae)


      call makeJObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class J: inheritance of class E2, implements A and B', tae)
      call catch(tae)

      call Inherit_J_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'J_a', 'J.a', strresult)

      call Inherit_J_b_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'J_b', 'J.b', strresult)
      
      call Inherit_J_j_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'J_j', 'J.j', strresult)

      call Inherit_J_c_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'J_c', 'J.E2.c', strresult)

      call Inherit_J_e_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'J_e', 'J.E2.e', strresult)
    
      call Inherit_J_deleteRef_f(object, tae)
      call catch(tae)

      call Inherit_J_m_f(strresult, tae)
      call catch(tae)
      call reporttest(partno, 'J_m', 'E2.m', strresult)


c     begin K test
      call makeKObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class K:', tae)
      call catch(tae)

      call Inherit_K_a_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'K_a', 'K.a', strresult)

      call Inherit_K_h_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'K_h', 'K.h', strresult)

      call Inherit_K_a2_f(object,0,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'K_a2', 'K.a2', strresult)

      call Inherit_K_k_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'K_k', 'K.k', strresult)


      call synch_RegOut_writeComment_f(tracker,
     $     'Class K: via class H', tae)
      call catch(tae)

      call Inherit_H__cast_f(object, altobject, tae)
      call catch(tae)
      call castcheck(partno, 'Class K',
     $     'Class H', altobject)
      if (altobject .ne. 0) then
         call Inherit_H_a_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'H_a', 'K.a', strresult)


         call Inherit_H_h_f(altobject,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'H_h', 'K.h', strresult)
         call Inherit_H_deleteRef_f(altobject, tae)
         call catch(tae)
         altobject = 0
      endif
      call synch_RegOut_writeComment_f(tracker,
     $     'Class K deleteRef:', tae)
      call catch(tae)
      call Inherit_K_deleteRef_f(object, tae)
      call catch(tae)
      call synch_RegOut_writeComment_f(tracker,
     $     'Class K post deleteRef:', tae)
      call catch(tae)


c     begin L test
      call makeLObject(object, remoteURL)

      call synch_RegOut_writeComment_f(tracker,
     $     'Class L:', tae)
      call catch(tae)

      call Inherit_L_aa_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'L_aa', 'L.a', strresult)

      call Inherit_L_a2_f(object,0,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'L_a2', 'L.a2', strresult)

      call Inherit_L_l_f(object,strresult, tae)
      call catch(tae)
      call reporttest(partno, 'L_l', 'L.l', strresult)

      call Inherit_A__cast_f(object, interface, tae)
      call catch(tae)
      call castcheck(partno, 'Class L',
     $     'Interface A', interface)
      if (interface .ne. 0) then
         call Inherit_A_a_f(interface,strresult, tae)
         call catch(tae)
         call reporttest(partno, 'A_a', 'L.a', strresult)
         call Inherit_A_deleteRef_f(interface, tae)
         call catch(tae)
         interface = 0
      endif

      call Inherit_I_deleteRef_f(object, tae)
      call catch(tae)



      call synch_RegOut_close_f(tracker, tae)
      call catch(tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      call catch(tae)
      end 
