!     
! File:        inherittest.F90
! Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
! Revision:    $Revision$
! Date:        $Date$
! Description: Regression test to test FORTRAN calls to BABEL
!
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_rmi_ProtocolFactory_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "Inherit_B_fAbbrev.h"
#include "Inherit_C_fAbbrev.h"
#include "Inherit_D_fAbbrev.h"
#include "Inherit_E2_fAbbrev.h"
#include "Inherit_E_fAbbrev.h"
#include "Inherit_F2_fAbbrev.h"
#include "Inherit_F_fAbbrev.h"
#include "Inherit_G2_fAbbrev.h"
#include "Inherit_G_fAbbrev.h"
#include "Inherit_H_fAbbrev.h"
#include "Inherit_I_fAbbrev.h"
#include "Inherit_J_fAbbrev.h"
#include "Inherit_K_fAbbrev.h"
#include "Inherit_L_fAbbrev.h"

subroutine castcheck(partno, sourcename, destname, notnull, &
	castexc)
  use sidl
  use synch_RegOut
  use synch_ResultType
  use sidl_BaseInterface
  use sidl_ClassInfo
  implicit none
  integer (kind=sidl_int)  :: partno
  character (len=*)                  :: sourcename, destname
  character (len=1024)               :: buffer, classname
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: castexc
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(sidl_ClassInfo_t) :: classinfo
  logical                         :: notnull
  partno = partno + 1
  tracker = getInstance(throwaway_exception)
  call startPart(tracker, partno, throwaway_exception)
  buffer = 'Casting ' // sourcename //  ' to '// destname
  call writeComment(tracker, buffer, throwaway_exception)
  if (not_null(castexc)) then
     call cast(castexc, classinfo, throwaway_exception)
     if (not_null(classinfo)) then
        classname = getName(classinfo, throwaway_exception)
        buffer = 'Cast threw exception ' // classname
        call writeComment(tracker, buffer, throwaway_exception)
        call deleteRef(classinfo, throwaway_exception)
     endif
     call endPart(tracker, partno, FAIL, throwaway_exception)
  else
     if (notnull) then
        call endPart(tracker, partno, PASS, throwaway_exception)
     else
        call endPart(tracker, partno, FAIL, throwaway_exception)
     endif
  endif
  call deleteRef(tracker, throwaway_exception)
end subroutine castcheck


subroutine reporttest(partno, methodname, expectedresult, result)
  use sidl
  use synch_RegOut
  use synch_ResultType
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: partno
  type(sidl_BaseInterface_t) :: throwaway_exception
  character (len=*)                 :: methodname, expectedresult, result
  character (len=1024)               :: buffer
  type(synch_RegOut_t) :: tracker
  partno = partno + 1
  tracker = getInstance(throwaway_exception)
  call startPart(tracker, partno, throwaway_exception)
  buffer = 'Method Inherit_' // methodname // ' should return '//expectedresult
  call writeComment(tracker, buffer, throwaway_exception)
  buffer = 'Method Inherit_' // methodname // ' returned '// result
  call writeComment(tracker, buffer, throwaway_exception)
  if (result .eq. expectedresult) then
     call endPart(tracker, partno, PASS, throwaway_exception)
  else
     call endPart(tracker, partno, FAIL, throwaway_exception)
  endif
  call deleteRef(tracker, throwaway_exception)
end subroutine reporttest

subroutine makeCObject(obj, remoteURL)
  use Inherit_C
  type(Inherit_C_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeCObject

subroutine makeDObject(obj, remoteURL)
  use Inherit_D
  type(Inherit_D_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeDObject

subroutine makeEObject(obj, remoteURL)
  use Inherit_E
  type(Inherit_E_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeEObject

subroutine makeE2Object(obj, remoteURL)
  use Inherit_E2
  type(Inherit_E2_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeE2Object

subroutine makeFObject(obj, remoteURL)
  use Inherit_F
  type(Inherit_F_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeFObject

subroutine makeF2Object(obj, remoteURL)
  use Inherit_F2
  type(Inherit_F2_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeF2Object

subroutine makeGObject(obj, remoteURL)
  use Inherit_G
  type(Inherit_G_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeGObject

subroutine makeG2Object(obj, remoteURL)
  use Inherit_G2
  type(Inherit_G2_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeG2Object

subroutine makeIObject(obj, remoteURL)
  use Inherit_I
  type(Inherit_I_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeIObject

subroutine makeJObject(obj, remoteURL)
  use Inherit_J
  type(Inherit_J_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeJObject

subroutine makeKObject(obj, remoteURL)
  use Inherit_K
  type(Inherit_K_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeKObject

subroutine makeLObject(obj, remoteURL)
  use Inherit_L
  type(Inherit_L_t) :: obj
  character (len=256) :: remoteURL
  type(sidl_BaseInterface_t) throwaway
  if ( remoteURL .ne. '' ) then
     call new(obj, remoteURL, throwaway)
  else
     call new(obj, throwaway)
  endif
end subroutine makeLObject

program inherittest
  use sidl
  use synch_RegOut
  use Inherit_A
  use Inherit_B
  use Inherit_C
  use Inherit_D
  use Inherit_E
  use Inherit_E2
  use Inherit_F
  use Inherit_F2
  use Inherit_G
  use Inherit_G2
  use Inherit_H
  use Inherit_I
  use Inherit_J, jm => m
  use Inherit_K
  use Inherit_L
  use sidl_BaseInterface
  use sidl_rmi_ProtocolFactory
  implicit none
  integer (kind=sidl_int)  :: partno
  character (len=32)              :: strresult
  type(Inherit_A_t)  :: A_object
  type(Inherit_B_t)  :: B_object
  type(Inherit_C_t)  :: C_object
  type(Inherit_D_t)  :: D_object
  type(Inherit_E_t)  :: E_object
  type(Inherit_E2_t) :: E2_object
  type(Inherit_F_t)  :: F_object
  type(Inherit_F2_t) :: F2_object
  type(Inherit_G_t)  :: G_object
  type(Inherit_G2_t) :: G2_object
  type(Inherit_H_t)  :: H_object
  type(Inherit_I_t)  :: I_object
  type(Inherit_J_t)  :: J_object
  type(Inherit_K_t)  :: K_object
  type(Inherit_L_t)  :: L_object
  character (len=256) :: remoteURL, arg
  integer :: i,IArgc
  logical :: retval

  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  partno = 0
  tracker = getInstance(throwaway_exception)

  !Parse the command line  to see if we are running RMI tests
  remoteURL = ''
  do i = 1, IArgc()
     call GetArg(i, arg)
     if ( arg(1:6) .eq. '--url=' ) then
        remoteURL = arg(7:)
     endif
  end do

  !Setup RMI if necessary
  if ( remoteURL .ne. '' ) then
     call GetArg(0, arg)
     call replaceMagicVars(tracker, remoteURL, arg, throwaway_exception)
     print *,'using remote URL ', remoteURL
     print *,'registering RMI protocol simhandle'
     retval = addProtocol('simhandle', 'sidlx.rmi.SimHandle', throwaway_exception)
     if (retval .neqv. .true.) then
        print *,'sidl.rmi.ProtocolFactor.addProtocol() failed'
     endif
  endif

  call setExpectations(tracker, 73_sidl_int, throwaway_exception)

  call makeCObject(C_object, remoteURL)
  call writeComment(tracker, 'Class C:', throwaway_exception)
  strresult = c(C_object, throwaway_exception)
  call reporttest(partno, 'C_c', 'C.c', strresult)
  call deleteRef(C_object, throwaway_exception)

  call makeDObject(D_object, remoteURL)
  call writeComment(tracker, 'Class D: inheritance of interface A', &
       throwaway_exception)
  strresult = a(D_object, throwaway_exception)
  call reporttest(partno, 'D_a', 'D.a', strresult)
  strresult = d(D_object, throwaway_exception)
  call reporttest(partno, 'D_d', 'D.d', strresult)

  call writeComment(tracker, 'Class D: via interface A', &
       throwaway_exception)
  call cast(D_object, A_object, throwaway_exception)
  call castcheck(partno, 'Class D', 'Interface A', &
       not_null(A_object), throwaway_exception)
  if (not_null(A_object)) then
     strresult = a(A_object, throwaway_exception)
     call reporttest(partno, 'A_a', 'D.a', strresult)
     call deleteRef(A_object, throwaway_exception)
     call set_null(A_object)
  endif
  call deleteRef(D_object, throwaway_exception)

  call makeEObject(E_object, remoteURL)
  call writeComment(tracker, 'Class E: inheritance of class C', &
       throwaway_exception)
  strresult = c(E_object, throwaway_exception)
  call reporttest(partno, 'E_c', 'C.c', strresult)
  strresult = e(E_object, throwaway_exception)
  call reporttest(partno, 'E_e', 'E.e', strresult)

  call writeComment(tracker, 'Class E: via class C (C.c not overridden)', &
       throwaway_exception)
  call cast(E_object, C_object, throwaway_exception)
  call castcheck(partno, 'Class E', 'Class C', not_null(C_object), &
	throwaway_exception)
  if (not_null(C_object)) then
     strresult = c(C_object, throwaway_exception)
     call reporttest(partno, 'C_c', 'C.c', strresult)
     call deleteRef(C_object, throwaway_exception)
     call set_null(C_object)
  endif
  call deleteRef(E_object, throwaway_exception)

  call makeE2Object(E2_object, remoteURL)
  call writeComment(tracker, 'Class E2: inheritance of class C', &
       throwaway_exception)
  strresult = c(E2_object, throwaway_exception)
  call reporttest(partno, 'E2_c', 'E2.c', strresult)
  strresult = e(E2_object, throwaway_exception)
  call reporttest(partno, 'E2_e', 'E2.e', strresult)

  call writeComment(tracker, 'Class E2: via class C (C.c overridden)', &
       throwaway_exception)
  call cast(E2_object, C_object, throwaway_exception)
  call castcheck(partno, 'Class E2', 'Class C', not_null(C_object), &
	throwaway_exception)
  if (not_null(C_object)) then
     strresult = c(C_object, throwaway_exception)
     call reporttest(partno, 'C_c', 'E2.c', strresult)
     call deleteRef(C_object, throwaway_exception)
     call set_null(C_object)
  endif
  call deleteRef(E2_object, throwaway_exception)

  strresult = m(throwaway_exception)
  call reporttest(partno, 'E2_m', 'E2.m', strresult)

  call makeFObject(F_object, remoteURL)
  call writeComment(tracker, 'Class F: Multiple inheritance (no overriding)',&
       throwaway_exception)
  strresult = a(F_object, throwaway_exception)
  call reporttest(partno, 'F_a', 'F.a', strresult)
  strresult = b(F_object, throwaway_exception)
  call reporttest(partno, 'F_b', 'F.b', strresult)
  strresult = c(F_object, throwaway_exception)
  call reporttest(partno, 'F_c', 'C.c', strresult)
  strresult = f(F_object, throwaway_exception)
  call reporttest(partno, 'F_f', 'F.f', strresult)
  call writeComment(tracker, 'Class F: via interface A', &
       throwaway_exception)
  call cast(F_object, A_object, throwaway_exception)
  call castcheck(partno, 'Class F', 'Interface A', &
       not_null(A_object), throwaway_exception)
  if (not_null(A_object)) then
     strresult = a(A_object, throwaway_exception)
     call reporttest(partno, 'A_a', 'F.a', strresult)
     call deleteRef(A_object, throwaway_exception)
     call set_null(A_object)
  endif


  call writeComment(tracker, 'Class F: via interface B', &
       throwaway_exception)
  call cast(F_object, B_object, throwaway_exception)
  call castcheck(partno, 'Class F', 'Interface B', &
       not_null(B_object), throwaway_exception)
  if (not_null(B_object)) then
     strresult = b(B_object, throwaway_exception)
     call reporttest(partno, 'B_b', 'F.b', strresult)
     call deleteRef(B_object, throwaway_exception)
     call set_null(B_object)
  endif

  call writeComment(tracker, 'Class F: via class C (no overloading of C.c)',&
       throwaway_exception)
  call cast(F_object, C_object, throwaway_exception)
  call castcheck(partno, 'Class F', 'Class C', not_null(C_object), &
	throwaway_exception)
  if (not_null(C_object)) then
     strresult = c(C_object, throwaway_exception)
     call reporttest(partno, 'C_c', 'C.c', strresult)
     call deleteRef(C_object, throwaway_exception)
     call set_null(C_object)
  endif

  call deleteRef(F_object, throwaway_exception)

  call makeF2Object(F2_object, remoteURL)
  call writeComment(tracker, 'Class F2: Multiple inheritance (overrides C.c)',&
       throwaway_exception)
  strresult = a(F2_object, throwaway_exception)
  call reporttest(partno, 'F2_a', 'F2.a', strresult)
  strresult = b(F2_object, throwaway_exception)
  call reporttest(partno, 'F2_b', 'F2.b', strresult)
  strresult = c(F2_object, throwaway_exception)
  call reporttest(partno, 'F2_c', 'F2.c', strresult)
  strresult = f(F2_object, throwaway_exception)
  call reporttest(partno, 'F2_f', 'F2.f', strresult)

  call writeComment(tracker, 'Class F2: via interface A', &
       throwaway_exception)
  call cast(F2_object, A_object, throwaway_exception)
  call castcheck(partno, 'Class F2', 'Interface A', &
       not_null(A_object), throwaway_exception)
  if (not_null(A_object)) then
     strresult = a(A_object, throwaway_exception)
     call reporttest(partno, 'A_a', 'F2.a', strresult)
     call deleteRef(A_object, throwaway_exception)
     call set_null(A_object)
  endif

  call writeComment(tracker, 'Class F2: via interface B', &
       throwaway_exception)
  call cast(F2_object, B_object, throwaway_exception)
  call castcheck(partno, 'Class F2', 'Interface B', &
       not_null(B_object), throwaway_exception)
  if (not_null(B_object)) then
     strresult = b(B_object, throwaway_exception)
     call reporttest(partno, 'B_b', 'F2.b', strresult)
     call deleteRef(B_object, throwaway_exception)
     call set_null(B_object)
  endif

  call writeComment(tracker, 'Class F2: via class C (overloads C.c)', &
       throwaway_exception)
  call cast(F2_object, C_object, throwaway_exception)
  call castcheck(partno, 'Class F2', 'Class C', &
       not_null(C_object), throwaway_exception)
  if (not_null(C_object)) then
     strresult = c(C_object, throwaway_exception)
     call reporttest(partno, 'C_c', 'F2.c', strresult)
     call deleteRef(C_object, throwaway_exception)
     call set_null(C_object)
  endif

  call deleteRef(F2_object, throwaway_exception)

  call writeComment(tracker, &
       'Class G: indirect multiple inheritance (no overloads)',&
       throwaway_exception)
  call makeGObject(G_object, remoteURL)
  strresult = a(G_object, throwaway_exception)
  call reporttest(partno, 'G_a', 'D.a', strresult)
  strresult = d(G_object, throwaway_exception)
  call reporttest(partno, 'G_d', 'D.d', strresult)
  strresult = g(G_object, throwaway_exception)
  call reporttest(partno, 'G_g', 'G.g', strresult)

  call writeComment(tracker, 'Class G: via interface A', &
       throwaway_exception)
  call cast(G_object, A_object, throwaway_exception)
  call castcheck(partno, 'Class G', 'Interface A', &
       not_null(A_object), throwaway_exception)
  if (not_null(A_object)) then
     strresult = a(A_object, throwaway_exception)
     call reporttest(partno, 'A_a', 'D.a', strresult)
     call deleteRef(A_object, throwaway_exception)
     call set_null(A_object)
  endif
  call writeComment(tracker, 'Class G: via class D', &
       throwaway_exception)
  call cast(G_object, D_object, throwaway_exception)
  call castcheck(partno, 'Class G', 'Class D', &
       not_null(D_object), throwaway_exception)
  if (not_null(D_object)) then
     strresult = a(D_object, throwaway_exception)
     call reporttest(partno, 'D_a', 'D.a', strresult)
     strresult = d(D_object, throwaway_exception)
     call reporttest(partno, 'D_d', 'D.d', strresult)
     call deleteRef(D_object, throwaway_exception)
     call set_null(D_object)
  endif

  call deleteRef(G_object, throwaway_exception)

  call writeComment(tracker,  &
       'Class G2: indirect multiple inheritance (overloads)', &
       throwaway_exception)
  call makeG2Object(G2_object, remoteURL)
  strresult = a(G2_object, throwaway_exception)
  call reporttest(partno, 'G2_a', 'G2.a', strresult)
  strresult = d(G2_object, throwaway_exception)
  call reporttest(partno, 'G2_d', 'G2.d', strresult)
  strresult = g(G2_object, throwaway_exception)
  call reporttest(partno, 'G2_g', 'G2.g', strresult)

  call writeComment(tracker, 'Class G2: via interface A', &
       throwaway_exception)
  call cast(G2_object, A_object, throwaway_exception)
  call castcheck(partno, 'Class G2', 'Interface A', &
       not_null(A_object), throwaway_exception)
  if (not_null(A_object)) then
     strresult = a(A_object, throwaway_exception)
     call reporttest(partno, 'A_a', 'G2.a', strresult)
     call deleteRef(A_object, throwaway_exception)
     call set_null(A_object)
  endif

  call writeComment(tracker, 'Class G2: via class D', &
       throwaway_exception)
  call cast(G2_object, D_object, throwaway_exception)
  call castcheck(partno, 'Class G2', 'Class D', &
       not_null(D_object), throwaway_exception)
  if (not_null(D_object)) then
     strresult = a(D_object, throwaway_exception)
     call reporttest(partno, 'D_a', 'G2.a', strresult)
     strresult = d(D_object, throwaway_exception)
     call reporttest(partno, 'D_d', 'G2.d', strresult)
     call deleteRef(D_object, throwaway_exception)
     call set_null(D_object)
  endif
  call deleteRef(G2_object, throwaway_exception)

  call makeIObject(I_object, remoteURL)
  call writeComment(tracker, 'Class I:', &
       throwaway_exception)
  strresult = a(I_object, throwaway_exception)
  call reporttest(partno, 'I_a', 'I.a', strresult)
  strresult = h(I_object, throwaway_exception)
  call reporttest(partno, 'I_h', 'I.h', strresult)

  call writeComment(tracker, 'Class I: via class H', &
       throwaway_exception)
  call cast(I_object, H_object, throwaway_exception)
  call castcheck(partno, 'Class I', 'Class H', &
       not_null(H_object), throwaway_exception)
  
  if (not_null(H_object)) then
     strresult = a(H_object, throwaway_exception)
     call reporttest(partno, 'H_a', 'I.a', strresult)
     strresult = h(H_object, throwaway_exception)
     call reporttest(partno, 'H_h', 'I.h', strresult)
     call deleteRef(H_object, throwaway_exception)
     call set_null(H_object)
  endif
  call deleteRef(I_object, throwaway_exception)

!J object test
  call makeJObject(J_object, remoteURL)
  call writeComment(tracker, 'Class J:', &
       throwaway_exception)
  strresult = a(J_object, throwaway_exception)
  call reporttest(partno, 'J_a', 'J.a', strresult)
  strresult = b(J_object, throwaway_exception)
  call reporttest(partno, 'J_b', 'J.b', strresult)
  strresult = j(J_object, throwaway_exception)
  call reporttest(partno, 'J_j', 'J.j', strresult)
  strresult = c(J_object, throwaway_exception)
  call reporttest(partno, 'J_c', 'J.E2.c', strresult)
  strresult = e(J_object, throwaway_exception)
  call reporttest(partno, 'J_e', 'J.E2.e', strresult)
  call deleteRef(J_object, throwaway_exception)

  strresult = jm(throwaway_exception)
  call reporttest(partno, 'J_m', 'E2.m', strresult)

!K object test
  call makeKObject(K_object, remoteURL)
  call writeComment(tracker, 'Class K:', &
       throwaway_exception)
  strresult = a(K_object, throwaway_exception)
  call reporttest(partno, 'K_a', 'K.a', strresult)
  !a2 test (overloaded)
  strresult = a(K_object,0_sidl_int, throwaway_exception)
  call reporttest(partno, 'K_a2', 'K.a2', strresult)
  strresult = h(K_object, throwaway_exception)
  call reporttest(partno, 'K_h', 'K.h', strresult)
  strresult = k(K_object, throwaway_exception)
  call reporttest(partno, 'K_k', 'K.k', strresult)

  call writeComment(tracker, 'Class K: via class H', &
       throwaway_exception)
  call cast(K_object, H_object, throwaway_exception)
  call castcheck(partno, 'Class K', 'Class H', &
       not_null(H_object), throwaway_exception)
  
  if (not_null(H_object)) then
     strresult = a(H_object, throwaway_exception)
     call reporttest(partno, 'H_a', 'K.a', strresult)
     strresult = h(H_object, throwaway_exception)
     call reporttest(partno, 'H_h', 'K.h', strresult)
     call deleteRef(H_object, throwaway_exception)
     call set_null(H_object)
  endif
  call deleteRef(K_object, throwaway_exception)

!L object test
  call makeLObject(L_object, remoteURL)
  call writeComment(tracker, 'Class L:', &
       throwaway_exception)
  strresult = a(L_object, throwaway_exception)
  call reporttest(partno, 'L_a', 'L.a', strresult)
  !a2 test (overloaded)
  strresult = a(L_object,0_sidl_int, throwaway_exception)
  call reporttest(partno, 'L_a2', 'L.a2', strresult)
  strresult = l(L_object, throwaway_exception)
  call reporttest(partno, 'L_l', 'L.l', strresult)

  call writeComment(tracker, 'Class L: via interface A', &
       throwaway_exception)
  call cast(L_object, A_object, throwaway_exception)
  call castcheck(partno, 'Class L', 'Class A', &
       not_null(A_object), throwaway_exception)
  
  if (not_null(A_object)) then
     strresult = a(A_object, throwaway_exception)
     call reporttest(partno, 'A_a', 'L.a', strresult)
     call deleteRef(A_object, throwaway_exception)
     call set_null(A_object)
  endif
  call deleteRef(L_object, throwaway_exception)


  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program inherittest
