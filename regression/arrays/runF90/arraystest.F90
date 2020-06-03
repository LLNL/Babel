!
! File:       arraystests.F90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision: 7450 $
! Date:       $Date: 2012-04-19 09:08:27 -0700 (Thu, 19 Apr 2012) $
! Description:Exercise the FORTRAN interface
!
!
#include "ArrayTest_ArrayOps_fAbbrev.h"
#include "sidl_bool_fAbbrev.h"
#include "sidl_char_fAbbrev.h"
#include "sidl_dcomplex_fAbbrev.h"
#include "sidl_double_fAbbrev.h"
#include "sidl_fcomplex_fAbbrev.h"
#include "sidl_float_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
#include "sidl_long_fAbbrev.h"
#include "sidl_string_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"

subroutine starttest(number)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  integer (kind=sidl_int) :: number
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  call getInstance(tracker, throwaway_exception)
  call startPart(tracker, number, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end subroutine starttest

subroutine reporttest(test, number,  python)
  use sidl
  use synch_RegOut
  use synch_ResultType
  use sidl_BaseInterface
  integer (kind=sidl_int) :: number
  logical                             :: test, python
!    implicit none
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  call getInstance(tracker, throwaway_exception)
  if (test) then
     call endPart(tracker, number, PASS, throwaway_exception)
  else
     if (python) then
        call endPart(tracker, number, XFAIL, throwaway_exception)
     else
        call endPart(tracker, number, FAIL, throwaway_exception)
     endif
  endif
  call deleteRef(tracker, throwaway_exception)
  number = number + 1
end subroutine reporttest

logical function my_isprime(num)
  use sidl
  integer (kind=sidl_long) :: num
!    implicit none
  integer (kind=sidl_long) :: i
  i = 3_sidl_long
  do while (i*i .le. num)
     if (mod(num,i) .eq. 0_sidl_long) then
        my_isprime = .false.
        return
     endif
     i = i + 1_sidl_long
  enddo
  my_isprime = .true.
  return
end function my_isprime


integer (kind=selected_int_kind(18)) function my_nextprime(prev)
  use sidl
  integer (kind=sidl_long) :: prev
!    implicit none
  logical :: my_isprime
  if (prev .le. 1_sidl_long) then
     my_nextprime = 2_sidl_long
     return
  endif
  if (prev .eq. 2_sidl_long) then
     my_nextprime = 3_sidl_long
     return
  endif
  prev = prev + 2_sidl_long
  do while (.not. my_isprime(prev))
     prev = prev + 2_sidl_long
  enddo
  my_nextprime = prev
  return
end function my_nextprime

subroutine my_force_float(f)
  use sidl
  real(kind=sidl_float) :: f
  return
end subroutine my_force_float

subroutine my_force_fcomplex(f)
  use sidl
  complex(kind=sidl_fcomplex) :: f
  return
end subroutine my_force_fcomplex

subroutine checkBoolArrays(test,  python)
  use sidl
  use sidl_bool_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(sidl_bool_1d) :: barray
  
  call set_null(barray)

  call starttest(test)
  call createBool(217_sidl_int,barray, throwaway_exception)
  call reporttest(not_null(barray), test,  python)

  call starttest(test)
  call checkBool(barray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseBool(barray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(barray)

  call set_null(barray)

  call makeBool(218_sidl_int, barray, throwaway_exception)

  call starttest(test)
  call checkBool(barray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseBool(barray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkBool(barray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(barray)

  call set_null(barray)

  call makeBool(9_sidl_int, barray, throwaway_exception)
  call starttest(test)
  call reverseBool(barray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkBool(barray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  call deleteRef(barray)

  call set_null(barray)

  call starttest(test)
  call makeBool(-1_sidl_int, barray, throwaway_exception)
  call reporttest(is_null(barray), test,  python)
  
  if (not_null(barray)) then
     call deleteRef(barray)
  endif
  
end subroutine CheckBoolArrays

subroutine CheckCharArrays(test,  python)
  use sidl
  use sidl_char_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_char_1d) :: carray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(carray)

  call starttest(test)
  call createChar(217_sidl_int,carray, throwaway_exception)
  call reporttest(not_null(carray), test,  python)

  call starttest(test)
  call checkChar(carray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseChar(carray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(carray)

  call set_null(carray)

  call makeChar(218_sidl_int, carray, throwaway_exception)

  call starttest(test)
  call checkChar(carray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseChar(carray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkChar(carray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(carray)

  call set_null(carray)

  call makeChar(9_sidl_int, carray, throwaway_exception)
  call starttest(test)
  call reverseChar(carray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkChar(carray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(carray)

  call set_null(carray)

  call starttest(test)
  call makeChar(-1_sidl_int, carray, throwaway_exception)
  call reporttest(is_null(carray), test,  python)
  
  if (not_null(carray)) then
     call deleteRef(carray)
  endif
  
end subroutine CheckCharArrays

subroutine CheckIntArrays(test,  python)
  use sidl
  use sidl_int_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_int_1d) :: iarray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(iarray)

  call starttest(test)
  call createInt(217_sidl_int,iarray, throwaway_exception)
  call reporttest(not_null(iarray), test,  python)

  call starttest(test)
  call checkInt(iarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseInt(iarray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(iarray)

  call set_null(iarray)

  call makeInt(218_sidl_int, iarray, throwaway_exception)

  call starttest(test)
  call checkInt(iarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseInt(iarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkInt(iarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(iarray)

  call set_null(iarray)

  call makeInt(9_sidl_int, iarray, throwaway_exception)
  call starttest(test)
  call reverseInt(iarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkInt(iarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(iarray)

  call set_null(iarray)

  call starttest(test)
  call makeInt(-1_sidl_int, iarray, throwaway_exception)
  call reporttest(is_null(iarray), test,  python)
  
  if (not_null(iarray)) then
     call deleteRef(iarray)
  endif
  
end subroutine CheckIntArrays

subroutine CheckLongArrays(test,  python)
  use sidl
  use sidl_long_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_long_1d) :: larray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(larray)

  call starttest(test)
  call createLong(217_sidl_int,larray, throwaway_exception)
  call reporttest(not_null(larray), test,  python)

  call starttest(test)
  call checkLong(larray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseLong(larray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(larray)

  call set_null(larray)

  call makeLong(218_sidl_int, larray, throwaway_exception)

  call starttest(test)
  call checkLong(larray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseLong(larray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkLong(larray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(larray)

  call set_null(larray)

  call makeLong(9_sidl_int, larray, throwaway_exception)
  call starttest(test)
  call reverseLong(larray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkLong(larray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(larray)

  call set_null(larray)

  call starttest(test)
  call makeLong(-1_sidl_int, larray, throwaway_exception)
  call reporttest(is_null(larray), test,  python)
  
  if (not_null(larray)) then
     call deleteRef(larray)
  endif
  
end subroutine CheckLongArrays

subroutine CheckStringArrays(test,  python)
  use sidl
  use sidl_string_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_string_1d) :: sarray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(sarray)

  call starttest(test)
  call createString(217_sidl_int,sarray, throwaway_exception)
  call reporttest(not_null(sarray), test,  python)

  call starttest(test)
  call checkString(sarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseString(sarray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(sarray)

  call set_null(sarray)

  call makeString(218_sidl_int, sarray, throwaway_exception)

  call starttest(test)
  call checkString(sarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseString(sarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkString(sarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(sarray)

  call set_null(sarray)

  call makeString(9_sidl_int, sarray, throwaway_exception)
  call starttest(test)
  call reverseString(sarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkString(sarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(sarray)

  call set_null(sarray)

  call starttest(test)
  call makeString(-1_sidl_int, sarray, throwaway_exception)
  call reporttest(is_null(sarray), test,  python)
  
  if (not_null(sarray)) then
     call deleteRef(sarray)
  endif
  
end subroutine CheckStringArrays

subroutine CheckDoubleArrays(test,  python)
  use sidl
  use sidl_double_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_double_1d) :: darray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(darray)

  call starttest(test)
  call createDouble(217_sidl_int,darray, throwaway_exception)
  call reporttest(not_null(darray), test,  python)

  call starttest(test)
  call checkDouble(darray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseDouble(darray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(darray)

  call set_null(darray)

  call makeDouble(218_sidl_int, darray, throwaway_exception)

  call starttest(test)
  call checkDouble(darray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseDouble(darray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkDouble(darray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(darray)

  call set_null(darray)

  call makeDouble(9_sidl_int, darray, throwaway_exception)
  call starttest(test)
  call reverseDouble(darray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkDouble(darray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(darray)

  call set_null(darray)

  call starttest(test)
  call makeDouble(-1_sidl_int, darray, throwaway_exception)
  call reporttest(is_null(darray), test,  python)
  
  if (not_null(darray)) then
     call deleteRef(darray)
  endif
  
end subroutine CheckDoubleArrays

subroutine CheckFloatArrays(test,  python)
  use sidl
  use sidl_float_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_float_1d) :: farray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(farray)

  call starttest(test)
  call createFloat(217_sidl_int,farray, throwaway_exception)
  call reporttest(not_null(farray), test,  python)

  call starttest(test)
  call checkFloat(farray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseFloat(farray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(farray)

  call set_null(farray)

  call makeFloat(218_sidl_int, farray, throwaway_exception)

  call starttest(test)
  call checkFloat(farray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseFloat(farray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkFloat(farray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(farray)

  call set_null(farray)

  call makeFloat(9_sidl_int, farray, throwaway_exception)
  call starttest(test)
  call reverseFloat(farray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkFloat(farray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(farray)

  call set_null(farray)

  call starttest(test)
  call makeFloat(-1_sidl_int, farray, throwaway_exception)
  call reporttest(is_null(farray), test,  python)
  
  if (not_null(farray)) then
     call deleteRef(farray)
  endif
  
end subroutine CheckFloatArrays

subroutine CheckFcomplexArrays(test,  python)
  use sidl
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_fcomplex_1d) :: fcarray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(fcarray)

  call starttest(test)
  call createFcomplex(217_sidl_int,fcarray, throwaway_exception)
  call reporttest(not_null(fcarray), test,  python)

  call starttest(test)
  call checkFcomplex(fcarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseFcomplex(fcarray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(fcarray)

  call set_null(fcarray)

  call makeFcomplex(218_sidl_int, fcarray, throwaway_exception)

  call starttest(test)
  call checkFcomplex(fcarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseFcomplex(fcarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkFcomplex(fcarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(fcarray)

  call set_null(fcarray)

  call makeFcomplex(9_sidl_int, fcarray, throwaway_exception)
  call starttest(test)
  call reverseFcomplex(fcarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkFcomplex(fcarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(fcarray)

  call set_null(fcarray)

  call starttest(test)
  call makeFcomplex(-1_sidl_int, fcarray, throwaway_exception)
  call reporttest(is_null(fcarray), test,  python)
  
  if (not_null(fcarray)) then
     call deleteRef(fcarray)
  endif
  
end subroutine CheckFcomplexArrays

subroutine CheckDcomplexArrays(test,  python)
  use sidl
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
!    implicit none
  logical ::  retval
  type(sidl_dcomplex_1d) :: dcarray
  type(sidl_BaseInterface_t) :: throwaway_exception
  call set_null(dcarray)

  call starttest(test)
  call createDcomplex(217_sidl_int,dcarray, throwaway_exception)
  call reporttest(not_null(dcarray), test,  python)

  call starttest(test)
  call checkDcomplex(dcarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseDcomplex(dcarray, .true., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call deleteRef(dcarray)

  call set_null(dcarray)

  call makeDcomplex(218_sidl_int, dcarray, throwaway_exception)

  call starttest(test)
  call checkDcomplex(dcarray, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call reverseDcomplex(dcarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call checkDcomplex(dcarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  
  call deleteRef(dcarray)

  call set_null(dcarray)

  call makeDcomplex(9_sidl_int, dcarray, throwaway_exception)
  call starttest(test)
  call reverseDcomplex(dcarray, .false., retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  call checkDcomplex(dcarray, retval, throwaway_exception)
  call reporttest(.not. retval, test,  python)
  call deleteRef(dcarray)

  call set_null(dcarray)

  call starttest(test)
  call makeDcomplex(-1_sidl_int, dcarray, throwaway_exception)
  call reporttest(is_null(dcarray), test,  python)
  
  if (not_null(dcarray)) then
     call deleteRef(dcarray)
  endif
  
end subroutine CheckDcomplexArrays

subroutine Check2DoubleArrays(test, python)
  use sidl
  use sidl_double_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in) :: python
!    implicit none
  integer :: i, j
  logical ::  retval
  real(kind=sidl_double) :: tmp
  type(sidl_double_2d) :: darray
  type(sidl_BaseInterface_t) :: throwaway_exception

  call starttest(test)
  call create2Double(17_sidl_int,35_sidl_int,darray, throwaway_exception)
  call reporttest(not_null(darray), test,  python)

  call starttest(test)
  call check2Double(darray, retval, throwaway_exception)
  call reporttest(retval, test,  python)

  call starttest(test)
  do i = 0, 16
     do j = 0, 34
        darray%d_data(i,j) = 2.0**(i-j)
     enddo
  enddo
  
  call check2Double(darray, retval, throwaway_exception)
  call reporttest(retval, test,  python)


  call deleteRef(darray)
  call set_null(darray)

  call starttest(test)
  call create2Double(-1_sidl_int, -1_sidl_int, darray, throwaway_exception)
  call reporttest(is_null(darray), test,  python)

end subroutine Check2DoubleArrays

subroutine CheckGenericArrays(test,  python)
  use sidl
  use sidl_int_array
  use sidl_array_array
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  implicit none
  integer(kind=sidl_int), intent(inout) :: test
  logical, intent(in)  :: python
  logical ::  retval
  type(sidl_int_1d) :: icarray
  type(sidl_int_2d) :: i2array
  type(sidl_bool_1d) :: barray
  type(sidl_bool_2d) :: b2array
  type(sidl__array)  :: garray, garrayret, garrayout, garrayinout
  integer(kind=sidl_int) :: dim, enumtype
  type(sidl_BaseInterface_t) :: throwaway_exception

  dim = 1_sidl_int
  enumtype = 7_sidl_int

  call set_null(icarray)
  call set_null(garray)
  call set_null(garrayret)
  call set_null(garrayout)
  call set_null(garrayinout)
  

  call starttest(test)
  call reporttest(is_null(garray), test,  python)
  
  call starttest(test)
  call createInt(217_sidl_int,icarray, throwaway_exception)
  call reporttest(not_null(icarray), test,  python)

  call starttest(test)
  call cast(icarray, garray)
  call reporttest(not_null(garray), test,  python)
  call starttest(test)
  call checkGeneric(garray,dim, enumtype, throwaway_exception)
  call reporttest(dim .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call reporttest(enumtype .eq. 7_sidl_int, test,  python)

  call deleteref(icarray)
  call set_null(garray)
  call set_null(icarray)

  !Create a bool array with createGeneric
  call starttest(test)
  call createGeneric(1_sidl_int, 1_sidl_int, garray, throwaway_exception)

  call reporttest(not_null(garray), test,  python)
  call starttest(test)
  call checkGeneric(garray,dim, enumtype, throwaway_exception)
  call reporttest(dim .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call reporttest(enumtype .eq. 1_sidl_int, test,  python)
  call cast(garray, barray)
 
  call starttest(test)
  call reporttest(not_null(garray), test,  python)

  call starttest(test)
  call passGeneric(garray, garrayinout, garrayout, garrayret, throwaway_exception)
  call reporttest(not_null(garray), test,  python)
  call starttest(test)
  call reporttest(not_null(garrayret), test,  python)
!  call addref(garrayret)
  call starttest(test)
  call reporttest(not_null(garrayout), test,  python)
  call starttest(test)
  call reporttest(not_null(garrayinout), test,  python)

  call starttest(test)
  call checkGeneric(garray,dim, enumtype, throwaway_exception)
  call reporttest(dim .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call reporttest(enumtype .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call cast(garray, barray)
  call reporttest(not_null(barray), test,  python)

  call starttest(test)
  call checkGeneric(garrayret,dim, enumtype, throwaway_exception)
  call reporttest(dim .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call reporttest(enumtype .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call cast(garrayret, barray)
  call reporttest(not_null(barray), test,  python)
  if (not_null(garrayret)) then
     call deleteRef(garrayret)
     call set_null(garrayret)
  end if

  call starttest(test)
  call checkGeneric(garrayout,dim, enumtype, throwaway_exception)
  call reporttest(dim .eq. 1_sidl_int, test,  python)
  call starttest(test)
  call reporttest(enumtype .eq. 1_sidl_int, test,  python)
  call cast(garrayout, barray)
  call starttest(test)
  call reporttest(not_null(barray), test,  python)
  if (not_null(garrayout)) then
     call deleteRef(garrayout)
     call set_null(garrayout)
  endif

  call starttest(test)
  call checkGeneric(garrayinout,dim, enumtype, throwaway_exception)
  call reporttest(dim .eq. 2_sidl_int, test,  python)
  call starttest(test)
  call cast(garrayinout, i2array)
  call reporttest(not_null(i2array), test,  python)
  if (not_null(garrayinout)) then
     call deleteRef(garrayinout)
     call set_null(garrayinout)
  end if
  if (not_null(garray)) then
     call deleteRef(garray)
     call set_null(garray)
  endif
end subroutine CheckGenericArrays


subroutine checkRarrays(test, python)
  use sidl
  use ArrayTest_ArrayOps
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: test
  logical python, retval
  integer (kind=sidl_int) :: ir(0:99), ir3(0:5, 0:3, 0:3)
  integer (kind=sidl_int) :: ir7(0:2, 0:2, 0:2, 0:2, 0:3, 0:3, 0:3)
  real (kind=sidl_double), dimension(0:99) :: dr(0:99)
  complex (kind=sidl_dcomplex), dimension(0:99) :: dcr
  type(sidl_BaseInterface_t) :: throwaway_exception

  call starttest(test)
  call initRarray1Int(ir, throwaway_exception)
  call checkRarray1Int(ir, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call initRarray3Int(ir3, throwaway_exception)
  call checkRarray3Int(ir3, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call initRarray7Int(ir7, throwaway_exception)
  call checkRarray7Int(ir7,  retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call initRarray1Double(dr, throwaway_exception)
  call checkRarray1Double(dr, retval, throwaway_exception)
  call reporttest(retval, test,  python)
  
  call starttest(test)
  call initRarray1Dcomplex(dcr, throwaway_exception)
  call checkRarray1Dcomplex(dcr, retval, throwaway_exception)
  call reporttest(retval, test,  python)
end subroutine checkRarrays



program arraystests
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  integer (kind=sidl_int) :: test
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(synch_RegOut_t) :: tracker
  character (len=80)             :: language
  logical                        :: ispython
  language = ' '
  if (IArgc() .ge. 1) then
     call GetArg(1, language)
	write (*,*) language
  endif
  ispython = language .eq. 'Python'
  test = 1
  call getInstance(tracker, throwaway_exception)
  call setExpectations(tracker,114_sidl_int, throwaway_exception)

  call writeComment(tracker, 'Boolean tests', throwaway_exception)
  call CheckBoolArrays(test,  ispython)

  call writeComment(tracker, 'Char tests', throwaway_exception)
  call CheckCharArrays(test,  ispython)

  call writeComment(tracker, 'Int tests', throwaway_exception)
  call CheckIntArrays(test,  ispython)

  call writeComment(tracker, 'Long tests', throwaway_exception)
  call CheckLongArrays(test,  ispython)

  call writeComment(tracker, 'String tests', throwaway_exception)
  call CheckStringArrays(test,  ispython)

  call writeComment(tracker, 'Double tests', throwaway_exception)
  call CheckDoubleArrays(test,  ispython)

  call writeComment(tracker, 'Float tests', throwaway_exception)
  call CheckFloatArrays(test,  ispython)

  call writeComment(tracker, 'Fcomplex tests', throwaway_exception)
  call CheckFcomplexArrays(test,  ispython)

  call writeComment(tracker, 'Dcomplex tests', throwaway_exception)
  call CheckDcomplexArrays(test,  ispython)

  call writeComment(tracker, '2D double tests', throwaway_exception)
  call Check2DoubleArrays(test,  ispython)
  
  call writeComment(tracker, 'Generic tests', throwaway_exception)
  call CheckGenericArrays(test,  ispython)

  call writeComment(tracker, 'Rarray tests', throwaway_exception)
  call CheckRarrays(test,  ispython)

  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program arraystests
