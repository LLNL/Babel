C
C File:       arraystests.f
C Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
C Revision:   @(#) $Revision: 7450 $
C Date:       $Date: 2012-04-19 09:08:27 -0700 (Thu, 19 Apr 2012) $
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

      subroutine my_force_float(f)
      real f
      return
      end 

      subroutine my_force_fcomplex(f)
      complex  f
      return
      end 

      subroutine checkBoolArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway

      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createBool_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkBool_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseBool_f(barray, .true., retval,
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeBool_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkBool_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseBool_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkBool_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeBool_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseBool_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkBool_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeBool_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 


      subroutine checkCharArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway
       
      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createChar_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkChar_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseChar_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeChar_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkChar_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseChar_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkChar_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeChar_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseChar_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkChar_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeChar_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkIntArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway

      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createInt_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkInt_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseInt_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeInt_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkInt_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseInt_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkInt_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeInt_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseInt_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkInt_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeInt_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkLongArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway
       
      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createLong_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkLong_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseLong_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeLong_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkLong_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseLong_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkLong_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeLong_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseLong_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkLong_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeLong_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkStringArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway
       
      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createString_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkString_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseString_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeString_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkString_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseString_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkString_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeString_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseString_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkString_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeString_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkDoubleArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway

      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createDouble_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkDouble_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseDouble_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeDouble_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkDouble_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseDouble_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkDouble_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeDouble_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseDouble_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkDouble_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeDouble_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkFloatArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway

      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createFloat_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkFloat_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseFloat_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeFloat_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkFloat_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseFloat_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkFloat_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeFloat_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseFloat_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkFloat_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeFloat_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkFcomplexArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway

      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createFcomplex_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkFcomplex_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseFcomplex_f(barray, .true., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeFcomplex_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkFcomplex_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseFcomplex_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkFcomplex_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeFcomplex_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseFcomplex_f(barray, .false., retval,
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkFcomplex_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeFcomplex_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkDcomplexArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway
       
      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_createDcomplex_f(217,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkDcomplex_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseDcomplex_f(barray, .true., retval,
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeDcomplex_f(218, barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)

      call ArrayTest_ArrayOps_checkDcomplex_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_reverseDcomplex_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkDcomplex_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeDcomplex_f(9, barray, throwaway)

      call ArrayTest_ArrayOps_reverseDcomplex_f(barray, .false., retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_checkDcomplex_f(barray, retval, throwaway)
      call reporttest(.not. retval, test,  python, tracker)
      call sidl_bool__array_deleteRef_f(barray)
      
      barray = 0
      
      call ArrayTest_ArrayOps_makeDcomplex_f(-1, barray, throwaway)
      call reporttest(barray .eq. 0, test,  python, tracker)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine check2DoubleArrays(test,  python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  barray, tracker
      integer*8       throwaway

      barray = 0
C      call sidl_bool_array_set_null(barray)

      call ArrayTest_ArrayOps_create2Double_f(21,17,barray, throwaway)
      call reporttest(barray .ne. 0, test,  python, tracker)
      
      call ArrayTest_ArrayOps_check2Double_f(barray, retval, throwaway)
      call reporttest(retval, test,  python, tracker)
      
C      call sidl_bool__array_deleteRef_f(barray)
      
      if (barray .ne. 0) then
        call sidl_bool__array_deleteRef_f(barray)
      endif
  
      end 

      subroutine checkRarrays(test, python, tracker)
      implicit none
      integer*4 test
      logical  python, retval
      integer*8  iarray, darray, dcarray, tracker
      integer*4 ir(0:99), ir3(0:5, 0:3, 0:3)
      integer*4 ir7(0:2, 0:2, 0:2, 0:2, 0:3, 0:3, 0:3)
      double precision dr(0:99)
      double complex dcr(0:99)
      integer*8       throwaway

      call ArrayTest_ArrayOps_initRarray1Int_f(ir, 100, throwaway)
      call ArrayTest_ArrayOps_checkRarray1Int_f(ir, 100, retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      
      call ArrayTest_ArrayOps_initRarray3Int_f(ir3, 6,4,4, throwaway)
      call ArrayTest_ArrayOps_checkRarray3Int_f(ir3, 6,4,4, retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)

      call ArrayTest_ArrayOps_initRarray7Int_f(ir7, 3,3,3,3,4,4,4, 
     $     throwaway)
      call ArrayTest_ArrayOps_checkRarray7Int_f(ir7,3,3,3,3,4,4,4,
     $     retval, throwaway)
      call reporttest(retval, test,  python, tracker)

      call ArrayTest_ArrayOps_initRarray1Double_f(dr, 100, throwaway)
      call ArrayTest_ArrayOps_checkRarray1Double_f(dr, 100, retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)

      call ArrayTest_ArrayOps_initRarray1Dcomplex_f(dcr, 100, throwaway)
      call ArrayTest_ArrayOps_checkRarray1Dcomplex_f(dcr, 100, retval, 
     $     throwaway)
      call reporttest(retval, test,  python, tracker)
      end


      program arraystests
      implicit none
      integer*4 test
      integer*8 tracker, throwaway
      character*80  language
      logical ispython
      language = ' '
C      if (IArgc() .eq. 1) then
C        call GetArg(1, language)
C      endif
      ispython = language .eq. 'Python'
      test = 1
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_setExpectations_f(tracker, 97, throwaway)

      call synch_RegOut_writeComment_f(tracker, 'Boolean tests', 
     $     throwaway)
      call CheckBoolArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'Char tests', throwaway)
      call CheckCharArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'Int tests', throwaway)
      call CheckIntArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'Long tests', throwaway)
      call CheckLongArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'String tests', 
     $     throwaway)
      call CheckStringArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'Double tests', 
     $     throwaway)
      call CheckDoubleArrays(test,  ispython, tracker)
      
      call synch_RegOut_writeComment_f(tracker, 'Float tests', 
     $     throwaway)
      call CheckFloatArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'Fcomplex tests',
     $     throwaway)
      call CheckFcomplexArrays(test,  ispython, tracker)
      
      call synch_RegOut_writeComment_f(tracker, 'Dcomplex tests', 
     $     throwaway)
      call CheckDcomplexArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, '2D double tests', 
     $     throwaway)
      call Check2DoubleArrays(test,  ispython, tracker)

      call synch_RegOut_writeComment_f(tracker, 'Rarray tests', 
     $     throwaway)
      call CheckRarrays(test,  ispython, tracker)

      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)

      end 
