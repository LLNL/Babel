! 
! File:          ArrayTest_ArrayOps_Impl.F90
! Symbol:        ArrayTest.ArrayOps-v1.3
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for ArrayTest.ArrayOps
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "ArrayTest.ArrayOps" (version 1.3)
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "ArrayTest_ArrayOps_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sidl_double_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
#include "sidl_char_fAbbrev.h"
#include "sidl_dcomplex_fAbbrev.h"
#include "sidl_float_fAbbrev.h"
#include "sidl_bool_fAbbrev.h"
#include "sidl_string_fAbbrev.h"
#include "sidl_long_fAbbrev.h"
#include "sidl_fcomplex_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
#include "babel_config.h"

!subroutine output(tex)
!  use synch_RegOut
!  implicit none
!  character (len=*) :: tex
!  type(synch_RegOut_t) :: tracker
!  call getInstance(tracker)
!  call writecomment(tracker, tex)

!end subroutine output


logical function isprime(num)
  use sidl
  implicit none
  integer (kind=sidl_long) :: num
  integer (kind=sidl_long) :: i
  i = 3_sidl_long
  do while (i*i .le. num)
     if (mod(num,i) .eq. 0_sidl_long) then
        isprime = .false.
        return
     endif
     i = i + 1_sidl_long
  enddo
  isprime = .true.
  return
end function isprime


integer (kind=selected_int_kind(18)) function nextprime(prev)
  use sidl
  implicit none
  integer (kind=sidl_long) :: prev
  logical :: isprime
  if (prev .le. 1_sidl_long) then
     nextprime = 2_sidl_long
     return
  endif
  if (prev .eq. 2_sidl_long) then
     nextprime = 3_sidl_long
     return
  endif
  prev = prev + 2_sidl_long
  do while (.not. isprime(prev))
     prev = prev + 2_sidl_long
  enddo
  nextprime = prev
  return
end function nextprime

subroutine force_float(f)
  use sidl
  real(kind=sidl_float) :: f
  return
end subroutine force_float

subroutine force_fcomplex(f)
  use sidl
  complex(kind=sidl_fcomplex) :: f
  return
end subroutine force_fcomplex

logical function iseven(num)
  use sidl
  integer (kind=sidl_int)  :: num
  iseven = (mod(num, 2_sidl_int) .eq. 0_sidl_int)
  return
end function iseven

integer (kind=selected_int_kind(9)) function arrayValue(dim, ind)
  use sidl
  implicit none
  integer (kind=sidl_int) :: dim, result, i
  integer (kind=sidl_int) :: ind(dim)
  result = 1_sidl_int
  do i = 1_sidl_int, dim
     result = result * (ind(i) + i)
  enddo
  arrayValue = result
end function arrayValue

integer (kind=selected_int_kind(9)) function intFunc(dim, ind)
  use sidl
  implicit none
  integer (kind=sidl_int) :: dim, ind(dim), i, result
  result = 1_sidl_int
  do i = 1_sidl_int, dim
    result = result * (ind(i) + i)
  enddo
  intFunc = result
  return
end

logical function hasElements(dim, low, up)
  use sidl
  implicit none
  integer (kind=sidl_int) :: dim, low(dim), up(dim)
  integer (kind=sidl_int) :: i
  hasElements = .true.
  do i = 1_sidl_int, dim
     if (low(i) .gt. up(i)) then
        hasElements = .false.
     endif
  enddo
end function hasElements

logical function nextElem(dim, ind, low, up)
  use sidl
  implicit none
  integer (kind=sidl_int) :: dim
  integer (kind=sidl_int) :: ind(dim), low(dim), up(dim)
  integer (kind=sidl_int) :: i
  i = 1_sidl_int
  ind(i) = ind(i) + 1_sidl_int
  do while ((i .le. dim) .and. (ind(i) .gt. up(i)))
     ind(i) = low(i)
     i = i + 1_sidl_int
     if (i .le. dim) then
        ind(i) = ind(i) + 1_sidl_int
     endif
  enddo
  nextElem = (i .le. dim)
end function nextElem

subroutine createarraybytype(tp, dmn, lw, up, a)
  use sidl
  use sidl_array_array
  use sidl_BaseInterface_type
  use sidl_BaseInterface_array
  use sidl_ClassInfo_type
  use sidl_char_array
  use sidl_double_array
  use sidl_string_array
  use sidl_long_array
  use sidl_int_array
  use sidl_bool_array
  use sidl_float_array
  use sidl_opaque_array
  use sidl_fcomplex_array
  use sidl_dcomplex_array
  implicit none
  type(sidl__array), intent(out) :: a
  integer (kind=sidl_int) :: tp, dmn, lw(7), up(7)
  
  type(sidl_bool_1d) :: b1
  type(sidl_bool_2d) :: b2
  type(sidl_bool_3d) :: b3
  type(sidl_bool_4d) :: b4
  type(sidl_bool_5d) :: b5
  type(sidl_bool_6d) :: b6
  type(sidl_bool_7d) :: b7

  type(sidl_char_1d) :: c1
  type(sidl_char_2d) :: c2
  type(sidl_char_3d) :: c3
  type(sidl_char_4d) :: c4
  type(sidl_char_5d) :: c5
  type(sidl_char_6d) :: c6
  type(sidl_char_7d) :: c7

  type(sidl_dcomplex_1d) :: dc1
  type(sidl_dcomplex_2d) :: dc2
  type(sidl_dcomplex_3d) :: dc3
  type(sidl_dcomplex_4d) :: dc4
  type(sidl_dcomplex_5d) :: dc5
  type(sidl_dcomplex_6d) :: dc6
  type(sidl_dcomplex_7d) :: dc7

  type(sidl_double_1d) :: d1
  type(sidl_double_2d) :: d2
  type(sidl_double_3d) :: d3
  type(sidl_double_4d) :: d4
  type(sidl_double_5d) :: d5
  type(sidl_double_6d) :: d6
  type(sidl_double_7d) :: d7

  type(sidl_fcomplex_1d) :: fc1
  type(sidl_fcomplex_2d) :: fc2
  type(sidl_fcomplex_3d) :: fc3
  type(sidl_fcomplex_4d) :: fc4
  type(sidl_fcomplex_5d) :: fc5
  type(sidl_fcomplex_6d) :: fc6
  type(sidl_fcomplex_7d) :: fc7

  type(sidl_float_1d) :: f1
  type(sidl_float_2d) :: f2
  type(sidl_float_3d) :: f3
  type(sidl_float_4d) :: f4
  type(sidl_float_5d) :: f5
  type(sidl_float_6d) :: f6
  type(sidl_float_7d) :: f7

  type(sidl_int_1d) :: i1
  type(sidl_int_2d) :: i2
  type(sidl_int_3d) :: i3
  type(sidl_int_4d) :: i4
  type(sidl_int_5d) :: i5
  type(sidl_int_6d) :: i6
  type(sidl_int_7d) :: i7

  type(sidl_long_1d) :: l1
  type(sidl_long_2d) :: l2
  type(sidl_long_3d) :: l3
  type(sidl_long_4d) :: l4
  type(sidl_long_5d) :: l5
  type(sidl_long_6d) :: l6
  type(sidl_long_7d) :: l7

  type(sidl_opaque_1d) :: o1
  type(sidl_opaque_2d) :: o2
  type(sidl_opaque_3d) :: o3
  type(sidl_opaque_4d) :: o4
  type(sidl_opaque_5d) :: o5
  type(sidl_opaque_6d) :: o6
  type(sidl_opaque_7d) :: o7

  type(sidl_string_1d) :: s1
  type(sidl_string_2d) :: s2
  type(sidl_string_3d) :: s3
  type(sidl_string_4d) :: s4
  type(sidl_string_5d) :: s5
  type(sidl_string_6d) :: s6
  type(sidl_string_7d) :: s7

  type(sidl_baseinterface_1d) :: bi1
  type(sidl_baseinterface_2d) :: bi2
  type(sidl_baseinterface_3d) :: bi3
  type(sidl_baseinterface_4d) :: bi4
  type(sidl_baseinterface_5d) :: bi5
  type(sidl_baseinterface_6d) :: bi6
  type(sidl_baseinterface_7d) :: bi7

  select case(tp)
  case (1_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,b1)
        call cast(b1, a)
        return
     case (2_sidl_int)
        call createCol(lw,up,b2)
        call cast(b2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,b3)
        call cast(b3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,b4)
        call cast(b4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,b5)
        call cast(b5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,b6)
        call cast(b6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,b7)
        call cast(b7, a)
        return
     end select
  case (2_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,c1)
        call cast(c1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,c2)
        call cast(c2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,c3)
        call cast(c3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,c4)
        call cast(c4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,c5)
        call cast(c5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,c6)
        call cast(c6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,c7)
        call cast(c7, a)
        return
     end select
  case (3_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,dc1)
        call cast(dc1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,dc2)
        call cast(dc2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,dc3)
        call cast(dc3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,dc4)
        call cast(dc4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,dc5)
        call cast(dc5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,dc6)
        call cast(dc6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,dc7)
        call cast(dc7, a)
        return
     end select
  case (4_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,d1)
        call cast(d1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,d2)
        call cast(d2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,d3)
        call cast(d3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,d4)
        call cast(d4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,d5)
        call cast(d5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,d6)
        call cast(d6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,d7)
        call cast(d7, a)
        return
     end select
  case (5_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,fc1)
        call cast(fc1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,fc2)
        call cast(fc2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,fc3)
        call cast(fc3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,fc4)
        call cast(fc4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,fc5)
        call cast(fc5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,fc6)
        call cast(fc6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,fc7)
        call cast(fc7, a)
        return
     end select

  case (6_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,f1)
        call cast(f1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,f2)
        call cast(f2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,f3)
        call cast(f3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,f4)
        call cast(f4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,f5)
        call cast(f5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,f6)
        call cast(f6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,f7)
        call cast(f7, a)
        return
     end select

  case (7_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,i1)
        call cast(i1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,i2)
        call cast(i2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,i3)
        call cast(i3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,i4)
        call cast(i4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,i5)
        call cast(i5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,i6)
        call cast(i6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,i7)
        call cast(i7, a)
        return
     end select
  case (8_sidl_int)
    select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,l1)
        call cast(l1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,l2)
        call cast(l2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,l3)
        call cast(l3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,l4)
        call cast(l4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,l5)
        call cast(l5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,l6)
        call cast(l6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,l7)
        call cast(l7, a)
        return
     end select
  case (9_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,o1)
        call cast(o1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,o2)
        call cast(o2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,o3)
        call cast(o3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,o4)
        call cast(o4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,o5)
        call cast(o5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,o6)
        call cast(o6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,o7)
        call cast(o7, a)
        return
     end select

  case (10_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,s1)
        call cast(s1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,s2)
        call cast(s2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,s3)
        call cast(s3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,s4)
        call cast(s4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,s5)
        call cast(s5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,s6)
        call cast(s6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,s7)
        call cast(s7, a)
        return
     end select

  case (11_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call createRow(lw,up,bi1)
        call cast(bi1, a)
        return
     case (2_sidl_int)
        call createRow(lw,up,bi2)
        call cast(bi2, a)
        return
     case (3_sidl_int)
        call createRow(lw,up,bi3)
        call cast(bi3, a)
        return
     case (4_sidl_int)
        call createRow(lw,up,bi4)
        call cast(bi4, a)
        return
     case (5_sidl_int)
        call createRow(lw,up,bi5)
        call cast(bi5, a)
        return
     case (6_sidl_int)        
        call createRow(lw,up,bi6)
        call cast(bi6, a)
        return
     case (7_sidl_int)
        call createRow(lw,up,bi7)
        call cast(bi7, a)
        return
     end select
 
  case default
     !set_null(a)
  end select

end subroutine createarraybytype

subroutine copyarraybytype(src, lw, up, dest)
  use sidl
  use sidl_array_array
  use sidl_BaseInterface_type
  use sidl_BaseInterface_array
  use sidl_ClassInfo_type
  use sidl_char_array
  use sidl_double_array
  use sidl_string_array
  use sidl_long_array
  use sidl_int_array
  use sidl_bool_array
  use sidl_float_array
  use sidl_opaque_array
  use sidl_fcomplex_array
  use sidl_dcomplex_array
  implicit none
  type(sidl__array) :: src, dest
  integer (kind=sidl_int) :: tp, dmn, lw(7), up(7), i
  
  type(sidl_bool_1d) :: b1
  type(sidl_bool_2d) :: b2
  type(sidl_bool_3d) :: b3
  type(sidl_bool_4d) :: b4
  type(sidl_bool_5d) :: b5
  type(sidl_bool_6d) :: b6
  type(sidl_bool_7d) :: b7

  type(sidl_char_1d) :: c1
  type(sidl_char_2d) :: c2
  type(sidl_char_3d) :: c3
  type(sidl_char_4d) :: c4
  type(sidl_char_5d) :: c5
  type(sidl_char_6d) :: c6
  type(sidl_char_7d) :: c7

  type(sidl_dcomplex_1d) :: dc1
  type(sidl_dcomplex_2d) :: dc2
  type(sidl_dcomplex_3d) :: dc3
  type(sidl_dcomplex_4d) :: dc4
  type(sidl_dcomplex_5d) :: dc5
  type(sidl_dcomplex_6d) :: dc6
  type(sidl_dcomplex_7d) :: dc7

  type(sidl_double_1d) :: d1
  type(sidl_double_2d) :: d2
  type(sidl_double_3d) :: d3
  type(sidl_double_4d) :: d4
  type(sidl_double_5d) :: d5
  type(sidl_double_6d) :: d6
  type(sidl_double_7d) :: d7

  type(sidl_fcomplex_1d) :: fc1
  type(sidl_fcomplex_2d) :: fc2
  type(sidl_fcomplex_3d) :: fc3
  type(sidl_fcomplex_4d) :: fc4
  type(sidl_fcomplex_5d) :: fc5
  type(sidl_fcomplex_6d) :: fc6
  type(sidl_fcomplex_7d) :: fc7

  type(sidl_float_1d) :: f1
  type(sidl_float_2d) :: f2
  type(sidl_float_3d) :: f3
  type(sidl_float_4d) :: f4
  type(sidl_float_5d) :: f5
  type(sidl_float_6d) :: f6
  type(sidl_float_7d) :: f7

  type(sidl_int_1d) :: i1
  type(sidl_int_2d) :: i2
  type(sidl_int_3d) :: i3
  type(sidl_int_4d) :: i4
  type(sidl_int_5d) :: i5
  type(sidl_int_6d) :: i6
  type(sidl_int_7d) :: i7

  type(sidl_long_1d) :: l1
  type(sidl_long_2d) :: l2
  type(sidl_long_3d) :: l3
  type(sidl_long_4d) :: l4
  type(sidl_long_5d) :: l5
  type(sidl_long_6d) :: l6
  type(sidl_long_7d) :: l7

  type(sidl_opaque_1d) :: o1
  type(sidl_opaque_2d) :: o2
  type(sidl_opaque_3d) :: o3
  type(sidl_opaque_4d) :: o4
  type(sidl_opaque_5d) :: o5
  type(sidl_opaque_6d) :: o6
  type(sidl_opaque_7d) :: o7

  type(sidl_string_1d) :: s1
  type(sidl_string_2d) :: s2
  type(sidl_string_3d) :: s3
  type(sidl_string_4d) :: s4
  type(sidl_string_5d) :: s5
  type(sidl_string_6d) :: s6
  type(sidl_string_7d) :: s7

  type(sidl_baseinterface_1d) :: bi1
  type(sidl_baseinterface_2d) :: bi2
  type(sidl_baseinterface_3d) :: bi3
  type(sidl_baseinterface_4d) :: bi4
  type(sidl_baseinterface_5d) :: bi5
  type(sidl_baseinterface_6d) :: bi6
  type(sidl_baseinterface_7d) :: bi7

  type(sidl_bool_1d) :: b1d
  type(sidl_bool_2d) :: b2d
  type(sidl_bool_3d) :: b3d
  type(sidl_bool_4d) :: b4d
  type(sidl_bool_5d) :: b5d
  type(sidl_bool_6d) :: b6d
  type(sidl_bool_7d) :: b7d

  type(sidl_char_1d) :: c1d
  type(sidl_char_2d) :: c2d
  type(sidl_char_3d) :: c3d
  type(sidl_char_4d) :: c4d
  type(sidl_char_5d) :: c5d
  type(sidl_char_6d) :: c6d
  type(sidl_char_7d) :: c7d

  type(sidl_dcomplex_1d) :: dc1d
  type(sidl_dcomplex_2d) :: dc2d
  type(sidl_dcomplex_3d) :: dc3d
  type(sidl_dcomplex_4d) :: dc4d
  type(sidl_dcomplex_5d) :: dc5d
  type(sidl_dcomplex_6d) :: dc6d
  type(sidl_dcomplex_7d) :: dc7d

  type(sidl_double_1d) :: d1d
  type(sidl_double_2d) :: d2d
  type(sidl_double_3d) :: d3d
  type(sidl_double_4d) :: d4d
  type(sidl_double_5d) :: d5d
  type(sidl_double_6d) :: d6d
  type(sidl_double_7d) :: d7d

  type(sidl_fcomplex_1d) :: fc1d
  type(sidl_fcomplex_2d) :: fc2d
  type(sidl_fcomplex_3d) :: fc3d
  type(sidl_fcomplex_4d) :: fc4d
  type(sidl_fcomplex_5d) :: fc5d
  type(sidl_fcomplex_6d) :: fc6d
  type(sidl_fcomplex_7d) :: fc7d

  type(sidl_float_1d) :: f1d
  type(sidl_float_2d) :: f2d
  type(sidl_float_3d) :: f3d
  type(sidl_float_4d) :: f4d
  type(sidl_float_5d) :: f5d
  type(sidl_float_6d) :: f6d
  type(sidl_float_7d) :: f7d

  type(sidl_int_1d) :: i1d
  type(sidl_int_2d) :: i2d
  type(sidl_int_3d) :: i3d
  type(sidl_int_4d) :: i4d
  type(sidl_int_5d) :: i5d
  type(sidl_int_6d) :: i6d
  type(sidl_int_7d) :: i7d

  type(sidl_long_1d) :: l1d
  type(sidl_long_2d) :: l2d
  type(sidl_long_3d) :: l3d
  type(sidl_long_4d) :: l4d
  type(sidl_long_5d) :: l5d
  type(sidl_long_6d) :: l6d
  type(sidl_long_7d) :: l7d

  type(sidl_opaque_1d) :: o1d
  type(sidl_opaque_2d) :: o2d
  type(sidl_opaque_3d) :: o3d
  type(sidl_opaque_4d) :: o4d
  type(sidl_opaque_5d) :: o5d
  type(sidl_opaque_6d) :: o6d
  type(sidl_opaque_7d) :: o7d

  type(sidl_string_1d) :: s1d
  type(sidl_string_2d) :: s2d
  type(sidl_string_3d) :: s3d
  type(sidl_string_4d) :: s4d
  type(sidl_string_5d) :: s5d
  type(sidl_string_6d) :: s6d
  type(sidl_string_7d) :: s7d

  type(sidl_baseinterface_1d) :: bi1d
  type(sidl_baseinterface_2d) :: bi2d
  type(sidl_baseinterface_3d) :: bi3d
  type(sidl_baseinterface_4d) :: bi4d
  type(sidl_baseinterface_5d) :: bi5d
  type(sidl_baseinterface_6d) :: bi6d
  type(sidl_baseinterface_7d) :: bi7d

  tp = type(src)
  dmn = dimen(src)
  select case(tp)
  case (1_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null(b1)
        call cast(src, b1)
        call createRow(lw, up, b1d)
        call copy(b1, b1d)
        call cast(b1d, dest)
        return
     case (2_sidl_int)
        call set_null( b2)
        call cast(src, b2)
        call createRow(lw,up, b2d)
        call copy(b2, b2d)
        call cast(b2d, dest)
        return
     case (3_sidl_int)
        call set_null( b3)
        call cast(src, b3)
        call createRow(lw,up, b3d)
        call copy(b3, b3d)
        call cast(b3d, dest)
        return
     case (4_sidl_int)
        call set_null( b4)
        call cast(src, b4)
        call createRow(lw,up, b4d)
        call copy(b4, b4d)
        call cast(b4d, dest)
        return
     case (5_sidl_int)
        call set_null( b5)
        call cast(src, b5)
        call createRow(lw,up, b5d)
        call copy(b5, b5d)
        call cast(b5d, dest)
        return
     case (6_sidl_int)   
        call set_null( b6)
        call cast(src, b6)
        call createRow(lw,up, b6d)
        call copy(b6, b6d)
        call cast(b6d, dest)
        return
     case (7_sidl_int)
        call set_null( b7)
        call cast(src, b7)
        call createRow(lw,up, b7d)
        call copy(b7, b7d)
        call cast(b7d, dest)
        return
     end select

   case (2_sidl_int)

     select case(dmn)
     case (1_sidl_int)
        call set_null( c1)
        call cast(src, c1)
        call createRow(lw,up, c1d)
        call copy(c1, c1d)
        call cast(c1d, dest)
        return
     case (2_sidl_int)
        call set_null( c2)
        call cast(src, c2)
        call createRow(lw,up, c2d)
        call copy(c2, c2d)
        call cast(c2d, dest)
        return
     case (3_sidl_int)
        call set_null( c3)
        call cast(src, c3)
        call createRow(lw,up, c3d)
        call copy(c3, c3d)
        call cast(c3d, dest)
        return
     case (4_sidl_int)
        call set_null( c4)
        call cast(src, c4)
        call createRow(lw,up, c4d)
        call copy(c4, c4d)
        call cast(c4d, dest)
        return
     case (5_sidl_int)
        call set_null( c5)
        call cast(src, c5)
        call createRow(lw,up, c5d)
        call copy(c5, c5d)
        call cast(c5d, dest)
        return
     case (6_sidl_int)        
        call set_null( c6)
        call cast(src, c6)
        call createRow(lw,up, c6d)
        call copy(c6, c6d)
        call cast(c6d, dest)
        return
     case (7_sidl_int)
        call set_null( c7)
        call cast(src, c7)
        call createRow(lw,up, c7d)
        call copy(c7, c7d)
        call cast(c7d, dest)
        return
     end select
  case (3_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( dc1)
        call cast(src, dc1)
        call createRow(lw,up, dc1d)
        call copy(dc1, dc1d)
        call cast(dc1d, dest)
        return
     case (2_sidl_int)
        call set_null( dc2)
        call cast(src, dc2)
        call createRow(lw,up, dc2d)
        call copy(dc2, dc2d)
        call cast(dc2d, dest)
        return
     case (3_sidl_int)
        call set_null( dc3)
        call cast(src, dc3)
        call createRow(lw,up, dc3d)
        call copy(dc3, dc3d)
        call cast(dc3d, dest)
        return
     case (4_sidl_int)
        call set_null( dc4)
        call cast(src, dc4)
        call createRow(lw,up, dc4d)
        call copy(dc4, dc4d)
        call cast(dc4d, dest)
        return
     case (5_sidl_int)
        call set_null( dc5)
        call cast(src, dc5)
        call createRow(lw,up, dc5d)
        call copy(dc5, dc5d)
        call cast(dc5d, dest)
        return
     case (6_sidl_int)   
        call set_null( dc6)
        call cast(src, dc6)
        call createRow(lw,up, dc6d)
        call copy(dc6, dc6d)
        call cast(dc6d, dest)
        return
     case (7_sidl_int)
        call set_null( dc7)
        call cast(src, dc7)
        call createRow(lw,up, dc7d)
        call copy(dc7, dc7d)
        call cast(dc7d, dest)
        return
     end select
  case (4_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( d1)
        call cast(src, d1)
        call createRow(lw,up, d1d)
        call copy(d1, d1d)
        call cast(d1d, dest)
        return
     case (2_sidl_int)
        call set_null( d2)
        call cast(src, d2)
        call createRow(lw,up, d2d)
        call copy(d2, d2d)
        call cast(d2d, dest)
        return
     case (3_sidl_int)
        call set_null( d3)
        call cast(src, d3)
        call createRow(lw,up, d3d)
        call copy(d3, d3d)
        call cast(d3d, dest)
        return
     case (4_sidl_int)
        call set_null( d4)
        call cast(src, d4)
        call createRow(lw,up, d4d)
        call copy(d4, d4d)
        call cast(d4d, dest)
        return
     case (5_sidl_int)
        call set_null( d5)
        call cast(src, d5)
        call createRow(lw,up, d5d)
        call copy(d5, d5d)
        call cast(d5d, dest)
        return
     case (6_sidl_int)   
        call set_null( d6)
        call cast(src, d6)
        call createRow(lw,up, d6d)
        call copy(d6, d6d)
        call cast(d6d, dest)
        return
     case (7_sidl_int)
        call set_null( d7)
        call cast(src, d7)
        call createRow(lw,up, d7d)
        call copy(d7, d7d)
        call cast(d7d, dest)
        return
     end select
     
  case (5_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( fc1)
        call cast(src, fc1)
        call createRow(lw,up, fc1d)
        call copy(fc1, fc1d)
        call cast(fc1d, dest)
        return
     case (2_sidl_int)
        call set_null( fc2)
        call cast(src, fc2)
        call createRow(lw,up, fc2d)
        call copy(fc2, fc2d)
        call cast(fc2d, dest)
        return
     case (3_sidl_int)
        call set_null( fc3)
        call cast(src, fc3)
        call createRow(lw,up, fc3d)
        call copy(fc3, fc3d)
        call cast(fc3d, dest)
        return
     case (4_sidl_int)
        call set_null( fc4)
        call cast(src, fc4)
        call createRow(lw,up, fc4d)
        call copy(fc4, fc4d)
        call cast(fc4d, dest)
        return
     case (5_sidl_int)
        call set_null( fc5)
        call cast(src, fc5)
        call createRow(lw,up, fc5d)
        call copy(fc5, fc5d)
        call cast(fc5d, dest)
        return
     case (6_sidl_int)   
        call set_null( fc6)
        call cast(src, fc6)
        call createRow(lw,up, fc6d)
        call copy(fc6, fc6d)
        call cast(fc6d, dest)
        return
     case (7_sidl_int)
        call set_null( fc7)
        call cast(src, fc7)
        call createRow(lw,up, fc7d)
        call copy(fc7, fc7d)
        call cast(fc7d, dest)
        return
     end select

  case (6_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( f1)
        call cast(src, f1)
        call createRow(lw,up, f1d)
        call copy(f1, f1d)
        call cast(f1d, dest)
        return
     case (2_sidl_int)
        call set_null( f2)
        call cast(src, f2)
        call createRow(lw,up, f2d)
        call copy(f2, f2d)
        call cast(f2d, dest)
        return
     case (3_sidl_int)
        call set_null( f3)
        call cast(src, f3)
        call createRow(lw,up, f3d)
        call copy(f3, f3d)
        call cast(f3d, dest)
        return
     case (4_sidl_int)
        call set_null( f4)
        call cast(src, f4)
        call createRow(lw,up, f4d)
        call copy(f4, f4d)
        call cast(f4d, dest)
        return
     case (5_sidl_int)
        call set_null( f5)
        call cast(src, f5)
        call createRow(lw,up, f5d)
        call copy(f5, f5d)
        call cast(f5d, dest)
        return
     case (6_sidl_int)   
        call set_null( f6)
        call cast(src, f6)
        call createRow(lw,up, f6d)
        call copy(f6, f6d)
        call cast(f6d, dest)
        return
     case (7_sidl_int)
        call set_null( f7)
        call cast(src, f7)
        call createRow(lw,up, f7d)
        call copy(f7, f7d)
        call cast(f7d, dest)
        return
     end select
  case (7_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( i1)
        call cast(src, i1)
        call createRow(lw,up, i1d)
        call copy(i1, i1d)
        call cast(i1d, dest)
        return
     case (2_sidl_int)
        call set_null( i2)
        call cast(src, i2)
        call createRow(lw,up, i2d)
        call copy(i2, i2d)
        call cast(i2d, dest)
        return
     case (3_sidl_int)
        call set_null( i3)
        call cast(src, i3)
        call createRow(lw,up, i3d)
        call copy(i3, i3d)
        call cast(i3d, dest)
        return
     case (4_sidl_int)
        call set_null( i4)
        call cast(src, i4)
        call createRow(lw,up, i4d)
        call copy(i4, i4d)
        call cast(i4d, dest)
        return
     case (5_sidl_int)
        call set_null( i5)
        call cast(src, i5)
        call createRow(lw,up, i5d)
        call copy(i5, i5d)
        call cast(i5d, dest)
        return
     case (6_sidl_int)   
        call set_null( i6)
        call cast(src, i6)
        call createRow(lw,up, i6d)
        call copy(i6, i6d)
        call cast(i6d, dest)
        return
     case (7_sidl_int)
        call set_null( i7)
        call cast(src, i7)
        call createRow(lw,up, i7d)
        call copy(i7, i7d)
        call cast(i7d, dest)
        return
     end select

  case (8_sidl_int)

     select case(dmn)
     case (1_sidl_int)
        call set_null( l1)
        call cast(src, l1)
        call createRow(lw,up, l1d)
        call copy(l1, l1d)
        call cast(l1d, dest)
        return
     case (2_sidl_int)
        call set_null( l2)
        call cast(src, l2)
        call createRow(lw,up, l2d)
        call copy(l2, l2d)
        call cast(l2d, dest)
        return
     case (3_sidl_int)
        call set_null( l3)
        call cast(src, l3)
        call createRow(lw,up, l3d)
        call copy(l3, l3d)
        call cast(l3d, dest)
        return
     case (4_sidl_int)
        call set_null( l4)
        call cast(src, l4)
        call createRow(lw,up, l4d)
        call copy(l4, l4d)
        call cast(l4d, dest)
        return
     case (5_sidl_int)
        call set_null( l5)
        call cast(src, l5)
        call createRow(lw,up, l5d)
        call copy(l5, l5d)
        call cast(l5d, dest)
        return
     case (6_sidl_int)   
        call set_null( l6)
        call cast(src, l6)
        call createRow(lw,up, l6d)
        call copy(l6, l6d)
        call cast(l6d, dest)
        return
     case (7_sidl_int)
        call set_null( l7)
        call cast(src, l7)
        call createRow(lw,up, l7d)
        call copy(l7, l7d)
        call cast(l7d, dest)
        return
     end select

  case (9_sidl_int)

     select case(dmn)
     case (1_sidl_int)
        call set_null( o1)
        call cast(src, o1)
        call createRow(lw,up, o1d)
        call copy(o1, o1d)
        call cast(o1d, dest)
        return
     case (2_sidl_int)
        call set_null( o2)
        call cast(src, o2)
        call createRow(lw,up, o2d)
        call copy(o2, o2d)
        call cast(o2d, dest)
        return
     case (3_sidl_int)
        call set_null( o3)
        call cast(src, o3)
        call createRow(lw,up, o3d)
        call copy(o3, o3d)
        call cast(o3d, dest)
        return
     case (4_sidl_int)
        call set_null( o4)
        call cast(src, o4)
        call createRow(lw,up, o4d)
        call copy(o4, o4d)
        call cast(o4d, dest)
        return
     case (5_sidl_int)
        call set_null( o5)
        call cast(src, o5)
        call createRow(lw,up, o5d)
        call copy(o5, o5d)
        call cast(o5d, dest)
        return
     case (6_sidl_int)   
        call set_null( o6)
        call cast(src, o6)
        call createRow(lw,up, o6d)
        call copy(o6, o6d)
        call cast(o6d, dest)
        return
     case (7_sidl_int)
        call set_null( o7)
        call cast(src, o7)
        call createRow(lw,up, o7d)
        call copy(o7, o7d)
        call cast(o7d, dest)
        return
     end select

  case (10_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( s1)
        call cast(src, s1)
        call createRow(lw,up, s1d)
        call copy(s1, s1d)
        call cast(s1d, dest)
        return
     case (2_sidl_int)
        call set_null( s2)
        call cast(src, s2)
        call createRow(lw,up, s2d)
        call copy(s2, s2d)
        call cast(s2d, dest)
        return
     case (3_sidl_int)
        call set_null( s3)
        call cast(src, s3)
        call createRow(lw,up, s3d)
        call copy(s3, s3d)
        call cast(s3d, dest)
        return
     case (4_sidl_int)
        call set_null( s4)
        call cast(src, s4)
        call createRow(lw,up, s4d)
        call copy(s4, s4d)
        call cast(s4d, dest)
        return
     case (5_sidl_int)
        call set_null( s5)
        call cast(src, s5)
        call createRow(lw,up, s5d)
        call copy(s5, s5d)
        call cast(s5d, dest)
        return
     case (6_sidl_int)   
        call set_null( s6)
        call cast(src, s6)
        call createRow(lw,up, s6d)
        call copy(s6, s6d)
        call cast(s6d, dest)
        return
     case (7_sidl_int)
        call set_null( s7)
        call cast(src, s7)
        call createRow(lw,up, s7d)
        call copy(s7, s7d)
        call cast(s7d, dest)
        return
     end select

  case (11_sidl_int)
     select case(dmn)
     case (1_sidl_int)
        call set_null( bi1)
        call cast(src, bi1)
        call createRow(lw,up, bi1d)
        call copy(bi1, bi1d)
        call cast(bi1d, dest)
        return
     case (2_sidl_int)
        call set_null( bi2)
        call cast(src, bi2)
        call createRow(lw,up, bi2d)
        call copy(bi2, bi2d)
        call cast(bi2d, dest)
        return
     case (3_sidl_int)
        call set_null( bi3)
        call cast(src, bi3)
        call createRow(lw,up, bi3d)
        call copy(bi3, bi3d)
        call cast(bi3d, dest)
        return
     case (4_sidl_int)
        call set_null( bi4)
        call cast(src, bi4)
        call createRow(lw,up, bi4d)
        call copy(bi4, bi4d)
        call cast(bi4d, dest)
        return
     case (5_sidl_int)
        call set_null( bi5)
        call cast(src, bi5)
        call createRow(lw,up, bi5d)
        call copy(bi5, bi5d)
        call cast(bi5d, dest)
        return
     case (6_sidl_int)   
        call set_null( bi6)
        call cast(src, bi6)
        call createRow(lw,up, bi6d)
        call copy(bi6, bi6d)
        call cast(bi6d, dest)
        return
     case (7_sidl_int)
        call set_null( bi7)
        call cast(src, bi7)
        call createRow(lw,up, bi7d)
        call copy(bi7, bi7d)
        call cast(bi7d, dest)
        return
     end select
  case default
     dest%d_array = 0
  end select

end subroutine copyarraybytype

! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine ArrayTest_ArrayOps__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor.use)
  implicit none
  type(ArrayTest_ArrayOps_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor)
  ! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor)
end subroutine ArrayTest_ArrayOps__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine ArrayTest_ArrayOps__ctor2_mi(self, private_data,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor2.use)
  ! Insert-Code-Here {ArrayTest.ArrayOps._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor2.use)
  implicit none
  type(ArrayTest_ArrayOps_t) :: self
  ! in
  type(ArrayTest_ArrayOps_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor2)
! Insert-Code-Here {ArrayTest.ArrayOps._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor2)
end subroutine ArrayTest_ArrayOps__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine ArrayTest_ArrayOps__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._dtor.use)
  implicit none
  type(ArrayTest_ArrayOps_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._dtor)
  ! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._dtor)
end subroutine ArrayTest_ArrayOps__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine ArrayTest_ArrayOps__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._load)
  ! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._load)
end subroutine ArrayTest_ArrayOps__load_mi


! 
! Method:  checkBool[]
! Return <code>true</code> iff the even elements are true and
! the odd elements are false.
! 

recursive subroutine ArrayTest_ArrayOps_checkBool_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_bool_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkBool.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkBool.use)
  implicit none
  type(sidl_bool_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkBool)
  logical :: value1, value2
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1)
  integer (kind=sidl_int) :: testind
  logical :: isEven
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1
        do i = 0_sidl_int, len - 1
           index(1) = low + i
           call get(a, index(1), value1)
           call get(a, index, value2)
           if ((value1 .neqv. value2) &
                .or. (value1 .neqv. isEven(low + i))) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkBool)
end subroutine ArrayTest_ArrayOps_checkBool_mi


! 
! Method:  checkChar[]
! 

recursive subroutine ArrayTest_ArrayOps_checkChar_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_char_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkChar.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkChar.use)
  implicit none
  type(sidl_char_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkChar)
  character (len=64) :: testtext
  character :: value1, value2
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1)
  integer (kind=sidl_int) :: testind
  data testtext / 'I''d rather write programs to write programs than write programs' /
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1_sidl_int
        do i = 0_sidl_int, len - 1_sidl_int
           index(1) = low + i
           call get(a, low+i, value1)
           call get(a, index, value2)
           testind = mod(i, 64_sidl_int) + 1_sidl_int
           if ((value1 .ne. value2) &
                .or. (value1 .ne. testtext(testind:testind))) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkChar)
end subroutine ArrayTest_ArrayOps_checkChar_mi


! 
! Method:  checkInt[]
! 

recursive subroutine ArrayTest_ArrayOps_checkInt_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkInt.use)
  implicit none
  type(sidl_int_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkInt)
  integer (kind=sidl_int) :: dim, low, up, i, index(1), prime
  integer (kind=sidl_int) :: value1, value2, value4
  integer (kind=sidl_long) :: nextprime, tmp
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        prime = 0_sidl_int
        do i = low, up
           tmp = prime
           prime = nextprime(tmp)
           index(1) = i
           call get(a, i, value1)
           call get(a, index, value2)
           value4 = a%d_data(i)
           if ((value1 .ne. value2) &
                .or. (value2 .ne. value4) &
                .or. (value1 .ne. prime)) then
              retval = .false.
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkInt)
end subroutine ArrayTest_ArrayOps_checkInt_mi


! 
! Method:  checkLong[]
! 

recursive subroutine ArrayTest_ArrayOps_checkLong_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_long_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkLong.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkLong.use)
  implicit none
  type(sidl_long_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkLong)
  integer (kind=sidl_int) :: dim, low, up, i, index(1)
  integer (kind=sidl_long) :: prime
  integer (kind=sidl_long) :: value1, value2, nextprime, value3
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        prime = 0_sidl_long
        do i = low, up
           prime = nextprime(prime)
           index(1) = i
           call get(a, i, value1)
           call get(a, index, value2)
           value3 = a%d_data(i)
           if ((value1 .ne. value2) &
                .or. (value1 .ne. value3) &
                .or. (value1 .ne. prime)) then
              retval = .false.
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkLong)
end subroutine ArrayTest_ArrayOps_checkLong_mi


! 
! Method:  checkString[]
! 

recursive subroutine ArrayO_checkStringqtpwhw66ji_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkString.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkString.use)
  implicit none
  type(sidl_string_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkString)
  character (len=9) :: testtext(10)
  character (len=2048) :: value1
  character (len=2048) :: value2
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1), testind
  data testtext / 'I''d', 'rather', 'write', 'programs', 'to', 'write', 'programs', 'than', 'write', 'programs.'/
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1_sidl_int
        do i = 0_sidl_int, len - 1_sidl_int
           index(1) = low + i
           call get(a, low+i, value1)
           call get(a, index, value2)
           testind = mod(i, 10_sidl_int) + 1_sidl_int
           if ((value1 .ne. value2) & 
                .or. (value1 .ne. testtext(testind))) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkString)
end subroutine ArrayO_checkStringqtpwhw66ji_mi


! 
! Method:  checkDouble[]
! 

recursive subroutine ArrayO_checkDoubleht03bd1cj8_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDouble.use)
  implicit none
  type(sidl_double_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDouble)
  real(kind=sidl_double) :: value1, value2, value3, expectedvalue
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1)
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1_sidl_int
        do i = 0_sidl_int, len - 1_sidl_int
           expectedvalue = (2.0_sidl_double ** (-i))
           index(1) = low + i
           call get(a,low+i,value1)
           call get(a,index,value2)
           value3 = a%d_data(low+i)
           if ((value1 .ne. value2) &
                .or. (value1 .ne. value3) &
                .or. (value1 .ne. expectedvalue)) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDouble)
end subroutine ArrayO_checkDoubleht03bd1cj8_mi


! 
! Method:  checkFloat[]
! 

recursive subroutine ArrayOp_checkFloat81idm2qyci_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFloat.use)
  implicit none
  type(sidl_float_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFloat)
  real(kind=sidl_float) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1)
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1_sidl_int
        do i = 0_sidl_int, len - 1_sidl_int
           expectedvalue = (2.0 ** (-i))
           call force_float(expectedvalue)
           index(1) = low + i
           call get(a,low+i,value1)
           call get(a,index,value2)
           value3 = a%d_data(low+i)
           if ((value1 .ne. value2) &
                .or. (value1 .ne. value3) &
                .or. (value1 .ne. expectedvalue)) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFloat)
end subroutine ArrayOp_checkFloat81idm2qyci_mi


! 
! Method:  checkFcomplex[]
! 

recursive subroutine Arra_checkFcomplexpmjhp5xo5y_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFcomplex.use)
  implicit none
  type(sidl_fcomplex_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFcomplex)
  complex(kind=sidl_fcomplex) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1)
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1_sidl_int
        do i = 0_sidl_int, len - 1_sidl_int
           call force_fcomplex(expectedvalue)
           expectedvalue = cmplx(2.0_sidl_float ** i, 2.0_sidl_float ** (-i))
           index(1) = low + i
           call get(a,low+i,value1)
           call get(a,index,value2)
           value3 = a%d_data(low+i)
           if ((value1 .ne. value2) &
                .or. (value1 .ne. value3) &
                .or.  (value1 .ne. expectedvalue)) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFcomplex)
end subroutine Arra_checkFcomplexpmjhp5xo5y_mi


! 
! Method:  checkDcomplex[]
! 

recursive subroutine Arra_checkDcomplexjn5f7v75je_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDcomplex.use)
  implicit none
  type(sidl_dcomplex_1d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDcomplex)
  complex(kind=sidl_dcomplex) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: low, up, dim, len, i, index(1)
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        len = (up - low) + 1_sidl_int
        do i = 0_sidl_int, len - 1_sidl_int
           expectedvalue = cmplx(2.0_sidl_double ** i, 2.0_sidl_double ** (-i))
           index(1) = low + i
           call get(a,low+i,value1)
           call get(a,index,value2)
           value3 = a%d_data(low+i)
           if ((value1 .ne. value2) &
                .or. (value1 .ne. value3) &
                .or. (value1 .ne. expectedvalue)) then
              return
           endif
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDcomplex)
end subroutine Arra_checkDcomplexjn5f7v75je_mi


! 
! Method:  check2Int[]
! 

recursive subroutine ArrayTest_ArrayOps_check2Int_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Int.use)
  implicit none
  type(sidl_int_2d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Int)
  integer (kind=sidl_int) :: dim, low(2), up(2), ind(2), i, j
  integer (kind=sidl_int) :: len(2)
  integer (kind=sidl_int) :: value1, value2, expectedvalue, value3
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
    if (dim .eq. 2_sidl_int) then
      do i = 1_sidl_int, dim
         low(i) = lower(a, i-1_sidl_int)
         up(i) = upper(a, i-1_sidl_int)
         len(i) = up(i) - low(i)
      enddo
      do i = 0_sidl_int, len(1)
        ind(1) = low(1) + i
        do j = 0_sidl_int, len(2)
          expectedvalue = (2.0_sidl_double ** abs(i-j))
          ind(2) = low(2) + j
          call get(a, ind(1), ind(2), value1)
          call get(a, ind, value2)
          value3 = a%d_data(ind(1), ind(2))
          if ((value1 .ne. value2) .or. &
               (value1 .ne. value3) .or. &
               (value1 .ne. expectedvalue)) then
            return
          endif
        enddo
      enddo
      retval = .true.
    endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Int)
end subroutine ArrayTest_ArrayOps_check2Int_mi


! 
! Method:  check2Double[]
! 

recursive subroutine Array_check2Double7081y8i9gp_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Double.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Double.use)
  implicit none
  type(sidl_double_2d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Double)
  integer (kind=sidl_int)   :: dim, low(2), up(2), index(2), i, j
  integer (kind=sidl_int)   :: len(2)
  real(kind=sidl_double) :: value1, value2, expectedvalue, value3
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 2_sidl_int) then
        do i = 1_sidl_int, 2_sidl_int
           low(i) = lower(a, i-1_sidl_int)
           up(i) = upper(a, i-1_sidl_int)
           len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           index(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              expectedvalue = (2.0_sidl_double ** (i-j))
              index(2) = low(2) + j
              call get(a, index(1), index(2), value1)
              call get(a, index, value2)
              value3 = a%d_data(index(1), index(2))
              if ((value1 .ne. value2) &
                   .or. (value1 .ne. value3) &
                   .or.  (value1 .ne. expectedvalue)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Double)
end subroutine Array_check2Double7081y8i9gp_mi


! 
! Method:  check2Float[]
! 

recursive subroutine ArrayO_check2Floatme7av9ys4q_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Float.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Float.use)
  implicit none
  type(sidl_float_2d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Float)
  integer (kind=sidl_int)  :: dim, low(2), up(2), index(2), i, j
  integer (kind=sidl_int)  :: len(2)
  real (kind=sidl_float) :: value1, value2, expectedvalue, value3
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 2_sidl_int) then
        do i = 1_sidl_int, 2_sidl_int
           low(i) = lower(a, i-1_sidl_int)
           up(i) = upper(a, i-1_sidl_int)
           len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           index(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              expectedvalue = (2.0 ** (i-j))
              index(2) = low(2) + j
              call get(a, index(1), index(2), value1)
              call get(a, index, value2)
              value3 = a%d_data(index(1), index(2))
              if ((value1 .ne. value2) &
                   .or. (value1 .ne. value3) &
                   .or. (value1 .ne. expectedvalue)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Float)
end subroutine ArrayO_check2Floatme7av9ys4q_mi


! 
! Method:  check2Fcomplex[]
! 

recursive subroutine Arr_check2Fcomplexiayu9iwvsb_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Fcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Fcomplex.use)
  implicit none
  type(sidl_fcomplex_2d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Fcomplex)
  integer (kind=sidl_int)     :: dim, low(2), up(2), index(2), i, j
  integer (kind=sidl_int)     :: len(2)
  complex (kind=sidl_fcomplex) :: value1, value2, expectedvalue, value3
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 2_sidl_int) then
        do i = 1_sidl_int, 2_sidl_int
           low(i) = lower(a, i-1_sidl_int)
           up(i) = upper(a, i-1_sidl_int)
           len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           do j = 0_sidl_int, len(2)
              expectedvalue = cmplx(2.0 ** i, 2.0 ** (-j))
              index(1) = low(1) + i
              index(2) = low(2) + j
              call get(a, index(1), index(2), value1)
              call get(a, index, value2)
              value3 = a%d_data(index(1), index(2))
              if ((value1 .ne. value2) &
                   .or. (value1 .ne. value2) &
                   .or.  (value1 .ne. expectedvalue)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Fcomplex)
end subroutine Arr_check2Fcomplexiayu9iwvsb_mi


! 
! Method:  check2Dcomplex[]
! 

recursive subroutine Arr_check2Dcomplexvwom7ctsrx_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Dcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Dcomplex.use)
  implicit none
  type(sidl_dcomplex_2d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Dcomplex)
  integer (kind=sidl_int)       :: dim, low(2), up(2), index(2), i, j
  integer (kind=sidl_int)       :: len(2)
  complex (kind=sidl_dcomplex) :: value1, value2, expectedvalue, value3
  retval = .false.
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 2_sidl_int) then
        do i = 1_sidl_int, 2_sidl_int
           low(i) = lower(a, i-1_sidl_int)
           up(i) = upper(a, i-1_sidl_int)
           len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           do j = low(2), up(2)
              expectedvalue = cmplx(2.0_sidl_double ** i, 2.0_sidl_double ** (-j))
              index(1) = low(1) + i
              index(2) = low(2) + j
              call get(a, index(1), index(2), value1)
              call get(a, index, value2)
              value3 = a%d_data(index(1), index(2))
              if ((value1 .ne. value2) &
                   .or. (value1 .ne. value3) &
                   .or. (value1 .ne. expectedvalue)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Dcomplex)
end subroutine Arr_check2Dcomplexvwom7ctsrx_mi


! 
! Method:  check3Int[]
! 

recursive subroutine ArrayTest_ArrayOps_check3Int_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check3Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check3Int.use)
  implicit none
  type(sidl_int_3d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check3Int)
  integer (kind=sidl_int) :: dim, len(3), ind(3), low(3), up(3)
  integer (kind=sidl_int) :: i, j, k
  integer (kind=sidl_int) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 3_sidl_int) then
        do i = 1_sidl_int, dim
            low(i) = lower(a, i-1_sidl_int)
            up(i) = upper(a, i-1_sidl_int)
            len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           ind(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              ind(2) = low(2) + j
              do k = 0_sidl_int, len(3)
                 ind(3) = low(3) + k
                 expectedvalue = intFunc(dim, ind)
                 call get(a, ind(1), ind(2), ind(3), value1)
                 call get(a, ind, value2)
                 value3 = a%d_data(ind(1), ind(2), ind(3))
                 if (    (value1 .ne. value2) &
                      .or. (value1 .ne. value3) &
                      .or. (value1 .ne. expectedvalue)) then
                    return
                 endif
              enddo
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check3Int)
end subroutine ArrayTest_ArrayOps_check3Int_mi


! 
! Method:  check4Int[]
! 

recursive subroutine ArrayTest_ArrayOps_check4Int_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check4Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check4Int.use)
  implicit none
  type(sidl_int_4d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check4Int)
  integer (kind=sidl_int) :: dim, len(4), ind(4), low(4), up(4)
  integer (kind=sidl_int) :: i, j, k, l
  integer (kind=sidl_int) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 4_sidl_int) then
        do i = 1_sidl_int, dim
            low(i) = lower(a, i-1_sidl_int)
            up(i) = upper(a, i-1_sidl_int)
            len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           ind(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              ind(2) = low(2) + j
              do k = 0_sidl_int, len(3)
                 ind(3) = low(3) + k
                 do l = 0_sidl_int, len(4)
                    ind(4) = low(4) + l
                    expectedvalue = intFunc(dim, ind)
                    call get(a, ind(1), ind(2), ind(3), ind(4), value1)
                    call get(a, ind, value2)
                    value3 = a%d_data(ind(1), ind(2), ind(3), ind(4))
                    if (    (value1 .ne. value2) &
                         .or. (value1 .ne. value3) &
                         .or. (value1 .ne. expectedvalue)) then
                       return
                    endif
                 enddo
              enddo
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check4Int)
end subroutine ArrayTest_ArrayOps_check4Int_mi


! 
! Method:  check5Int[]
! 

recursive subroutine ArrayTest_ArrayOps_check5Int_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check5Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check5Int.use)
  implicit none
  type(sidl_int_5d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check5Int)
  integer (kind=sidl_int) :: dim, len(5), ind(5), low(5), up(5)
  integer (kind=sidl_int) :: i, j, k, l, m
  integer (kind=sidl_int) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 5_sidl_int) then
        do i = 1_sidl_int, dim
            low(i) = lower(a, i-1_sidl_int)
            up(i) = upper(a, i-1_sidl_int)
            len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           ind(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              ind(2) = low(2) + j
              do k = 0_sidl_int, len(3)
                 ind(3) = low(3) + k
                 do l = 0_sidl_int, len(4)
                    ind(4) = low(4) + l
                    do m = 0_sidl_int, len(5)
                       ind(5) = low(5) + m
                       expectedvalue = intFunc(dim, ind)
                       call get(a, ind(1), ind(2), ind(3), ind(4), ind(5), value1)
                       call get(a, ind, value2)
                       value3 = a%d_data(ind(1), ind(2), ind(3), ind(4), ind(5))
                       if (    (value1 .ne. value2) &
                         .or. (value1 .ne. value3) &
                         .or. (value1 .ne. expectedvalue)) then
                          return
                       endif
                    enddo
                 enddo
              enddo
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check5Int)
end subroutine ArrayTest_ArrayOps_check5Int_mi


! 
! Method:  check6Int[]
! 

recursive subroutine ArrayTest_ArrayOps_check6Int_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check6Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check6Int.use)
  implicit none
  type(sidl_int_6d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check6Int)
  integer (kind=sidl_int) :: dim, len(6), ind(6), low(6), up(6)
  integer (kind=sidl_int) :: i, j, k, l, m, n
  integer (kind=sidl_int) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 6_sidl_int) then
        do i = 1_sidl_int, dim
            low(i) = lower(a, i-1_sidl_int)
            up(i) = upper(a, i-1_sidl_int)
            len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           ind(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              ind(2) = low(2) + j
              do k = 0_sidl_int, len(3)
                 ind(3) = low(3) + k
                 do l = 0_sidl_int, len(4)
                    ind(4) = low(4) + l
                    do m = 0_sidl_int, len(5)
                       ind(5) = low(5) + m
                       do n = 0_sidl_int, len(6)
                          ind(6) = low(6) + n
                          expectedvalue = intFunc(dim, ind)
                          call get(a, ind(1), ind(2), ind(3), ind(4), ind(5), ind(6), value1)
                          call get(a, ind, value2)
                          value3 = a%d_data(ind(1), ind(2), ind(3), ind(4), ind(5), ind(6))
                          if (    (value1 .ne. value2) &
                               .or. (value1 .ne. value3) &
                               .or. (value1 .ne. expectedvalue)) then
                             return
                          endif
                       enddo
                    enddo
                 enddo
              enddo
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check6Int)
end subroutine ArrayTest_ArrayOps_check6Int_mi


! 
! Method:  check7Int[]
! 

recursive subroutine ArrayTest_ArrayOps_check7Int_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check7Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check7Int.use)
  implicit none
  type(sidl_int_7d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check7Int)
  integer (kind=sidl_int) :: dim, len(7), ind(7), low(7), up(7)
  integer (kind=sidl_int) :: i, j, k, l, m, n, o
  integer (kind=sidl_int) :: value1, value2, expectedvalue, value3
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 7_sidl_int) then
        do i = 1_sidl_int, dim
           low(i) = lower(a, i-1_sidl_int)
           up(i) = upper(a, i-1_sidl_int)
           len(i) = up(i) - low(i)
        enddo
        do i = 0_sidl_int, len(1)
           ind(1) = low(1) + i
           do j = 0_sidl_int, len(2)
              ind(2) = low(2) + j
              do k = 0_sidl_int, len(3)
                 ind(3) = low(3) + k
                 do l = 0_sidl_int, len(4)
                    ind(4) = low(4) + l
                    do m = 0_sidl_int, len(5)
                       ind(5) = low(5) + m
                       do n = 0_sidl_int, len(6)
                          ind(6) = low(6) + n
                          do o = 0_sidl_int, len(7)
                             ind(7) = low(7) + o
                             expectedvalue = intFunc(dim, ind)
                             call get(a, ind(1), ind(2), ind(3), ind(4), ind(5), ind(6), ind(7), value1)
                             call get(a, ind, value2)
                             value3 = a%d_data(ind(1), ind(2), ind(3), ind(4), ind(5), ind(6), ind(7))
                             if (    (value1 .ne. value2) &
                                  .or. (value1 .ne. value3) &
                                  .or. (value1 .ne. expectedvalue)) then
                                return
                             endif
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check7Int)
end subroutine ArrayTest_ArrayOps_check7Int_mi


! 
! Method:  check2String[]
! 

recursive subroutine Array_check2String3re43s7_q7_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2String.use)
  ! Insert-Code-Here {ArrayTest.ArrayOps.check2String.use} (use statements)
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2String.use)
  implicit none
  type(sidl_string_2d) :: a
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2String)
  character (len=9) :: testtext(10)
  character (len=2048) :: value1
  character (len=2048) :: value2
  integer (kind=sidl_int) :: low, up, dim, len, i, j, c, testind
  integer (kind=sidl_int) :: low2, up2
  data testtext / 'I''d', 'rather', 'write', 'programs', 'to', 'write', 'programs', 'than', 'write', 'programs.'/
  c = 0_sidl_int
  retval = .false.
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 2_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        low2 = lower(a,1_sidl_int)
        up2 = upper(a,1_sidl_int)
        do i = low, up
           do j = low2, up2
              call get(a, i, j, value1)
              testind = mod(c, 10_sidl_int) + 1_sidl_int
              c = c + 1_sidl_int
              if (value1 .ne. testtext(testind)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2String)
end subroutine Array_check2String3re43s7_q7_mi


! 
! Method:  checkObject[]
! 

recursive subroutine ArrayO_checkObject275yd2pmke_mi(a, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use ArrayTest_ArrayOps_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkObject.use)
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkObject.use)
  implicit none
  type(ArrayTest_ArrayOps_1d) :: a
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkObject)
  integer (kind=sidl_int) :: dim, low, up, i, index(1), prime
  type(ArrayTest_ArrayOps_t) :: value1, value2
  logical isone
  retval = 0_sidl_int
  if (not_null(a)) then
     dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low = lower(a, 0_sidl_int)
        up = upper(a, 0_sidl_int)
        prime = 0_sidl_int
        do i = low, up
           index(1) = i
           call get(a, i, value1)
           call get(a, index, value2)
           if (value1%d_ior .ne. value2%d_ior) then
              retval = -32000_sidl_int
           else
              if (not_null(value1)) then
                 call isType(value1, 'ArrayTest.ArrayOps', isone,exception)
                 if (isone) then
                    retval = retval + 1_sidl_int
                 endif
              endif
           endif
           if (not_null(value1)) then
              call deleteRef(value1,exception)
           endif
           if (not_null(value2)) then
              call deleteRef(value2,exception)
           endif
        enddo
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkObject)
end subroutine ArrayO_checkObject275yd2pmke_mi


! 
! Method:  reverseBool[]
! 

recursive subroutine ArrayO_reverseBoolgymo0nngeh_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_bool_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseBool.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseBool.use)
  implicit none
  type(sidl_bool_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseBool)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_bool_1d) :: destarray
  logical buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkBool(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, &
                   up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseBool)
end subroutine ArrayO_reverseBoolgymo0nngeh_mi


! 
! Method:  reverseChar[]
! 

recursive subroutine ArrayO_reverseCharlkg8mhipko_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_char_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseChar.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseChar.use)
  implicit none
  type(sidl_char_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseChar)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_char_1d) :: destarray
  character :: buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkChar(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray,up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseChar)
end subroutine ArrayO_reverseCharlkg8mhipko_mi


! 
! Method:  reverseInt[]
! 

recursive subroutine ArrayOp_reverseIntkxwfbbpb16_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseInt.use)
  implicit none
  type(sidl_int_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseInt)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_int_1d) :: destarray
  integer (kind=sidl_int) :: buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkInt(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseInt)
end subroutine ArrayOp_reverseIntkxwfbbpb16_mi


! 
! Method:  reverseLong[]
! 

recursive subroutine ArrayO_reverseLongnxvgf_63g6_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_long_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseLong.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseLong.use)
  implicit none
  type(sidl_long_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseLong)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_long_1d) :: destarray
  integer (kind=sidl_long) :: buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkLong(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseLong)
end subroutine ArrayO_reverseLongnxvgf_63g6_mi


! 
! Method:  reverseString[]
! 

recursive subroutine Arra_reverseStringgoh6ljf81q_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseString.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseString.use)
  implicit none
  type(sidl_string_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseString)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_string_1d) :: destarray
  character (len=2048):: buffer
  character (len=2048) :: extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkString(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseString)
end subroutine Arra_reverseStringgoh6ljf81q_mi


! 
! Method:  reverseDouble[]
! 

recursive subroutine Arra_reverseDoubletak4ebvws0_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDouble.use)
  implicit none
  type(sidl_double_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDouble)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_double_1d) :: destarray
  real(kind=sidl_double) :: buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkDouble(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDouble)
end subroutine Arra_reverseDoubletak4ebvws0_mi


! 
! Method:  reverseFloat[]
! 

recursive subroutine Array_reverseFloatznhcr92afl_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFloat.use)
  implicit none
  type(sidl_float_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFloat)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_float_1d) :: destarray
  real(kind=sidl_float) buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkFloat(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFloat)
end subroutine Array_reverseFloatznhcr92afl_mi


! 
! Method:  reverseFcomplex[]
! 

recursive subroutine Ar_reverseFcomplex3ygx3y26n0_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFcomplex.use)
  implicit none
  type(sidl_fcomplex_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFcomplex)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_fcomplex_1d) :: destarray
  complex(kind=sidl_fcomplex) :: buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkFcomplex(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFcomplex)
end subroutine Ar_reverseFcomplex3ygx3y26n0_mi


! 
! Method:  reverseDcomplex[]
! 

recursive subroutine Ar_reverseDcomplex27ddkoywyz_mi(a, newArray, retval,      &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDcomplex.use)
  implicit none
  type(sidl_dcomplex_1d) :: a
  ! inout
  logical :: newArray
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDcomplex)
  integer (kind=sidl_int) :: dim, low(1), up(1), i, len
  type(sidl_dcomplex_1d) :: destarray
  complex(kind=sidl_dcomplex) :: buffer, extra
  if (not_null(a)) then
      dim = dimen(a)
     if (dim .eq. 1_sidl_int) then
        low(1) = lower(a, 0_sidl_int)
        up(1) = upper(a, 0_sidl_int)
        call checkDcomplex(a, retval, exception)
        if (newarray) then
           call createRow(low, up, destarray)
           do i = low(1), up(1)
              call get(a, i, buffer)
              call set(destarray, up(1) + low(1) - i, buffer)
           enddo
           call deleteRef(a)
           a = destarray
        else
           len = ((up(1) - low(1) + 1_sidl_int) / 2_sidl_int) - 1_sidl_int
           do i = 0_sidl_int, len
              call get(a, low(1) + i, buffer)
              call get(a, up(1) - i, extra)

              call set(a, low(1) + i, extra)
              call set(a, up(1) - i, buffer)
           enddo
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDcomplex)
end subroutine Ar_reverseDcomplex27ddkoywyz_mi


! 
! Method:  createBool[]
! 

recursive subroutine ArrayOp_createBoolqh7dzxxaoi_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_bool_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createBool.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createBool.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_bool_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createBool)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), testind
  logical :: isEven
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        testind = mod(i, 64_sidl_int) + 1_sidl_int
        if (mod(i, 2_sidl_int) .eq. 0_sidl_int) then
           call set(retval, i, isEven(i))
        else
           index(1) = i
           call set(retval, index, isEven(i))
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createBool)
end subroutine ArrayOp_createBoolqh7dzxxaoi_mi


! 
! Method:  createChar[]
! 

recursive subroutine ArrayOp_createChargrqa0qqx35_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_char_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createChar.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createChar.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_char_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createChar)
  character (len=64) :: testtext
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), testind
  data testtext / 'I''d rather write programs to write programs than write programs' /
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        testind = mod(i, 64_sidl_int) + 1_sidl_int
        if (mod(i, 2_sidl_int) .eq. 0_sidl_int) then
           call set(retval, i, testtext(testind:testind))
        else
           index(1) = i
           call set(retval, index, testtext(testind:testind))
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createChar)
end subroutine ArrayOp_createChargrqa0qqx35_mi


! 
! Method:  createInt[]
! 

recursive subroutine ArrayTest_ArrayOps_createInt_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createInt.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_int_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createInt)
  integer (kind=sidl_int) :: i, low(1), up(1), index(1)
  integer (kind=sidl_int) :: value, modval
  integer (kind=sidl_long) :: nextprime, tmp
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     value = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        tmp = value
        value = nextprime(tmp)
        modval = mod(i, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
           call set(retval, i, value)
        else
           if (modval .eq. 1_sidl_int) then
              index(1) = i
              call set(retval, index, value)
           else
	      retval%d_data(i) = value
           endif
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createInt)
end subroutine ArrayTest_ArrayOps_createInt_mi


! 
! Method:  createLong[]
! 

recursive subroutine ArrayOp_createLongau7gsrji_x_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_long_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createLong.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createLong.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_long_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createLong)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), modval
  integer (kind=sidl_long) :: value, nextprime
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     value = 0_sidl_long
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        value = nextprime(value)
        modval = mod(i, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
           call set(retval, i, value)
        else
           if (modval .eq. 1_sidl_int) then
              index(1) = i
              call set(retval, index, value)
           else
              retval%d_data(i) = value
           endif
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createLong)
end subroutine ArrayOp_createLongau7gsrji_x_mi


! 
! Method:  createString[]
! 

recursive subroutine Array_createStringgncdbm9unz_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createString.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createString.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_string_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createString)
  character (len=9) :: testtext(10)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), testind
  data testtext / 'I''d', 'rather', 'write', 'programs', 'to', 'write', &
       'programs', 'than', 'write', 'programs.'/
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        testind = mod(i, 10_sidl_int) + 1_sidl_int
        if (mod(i, 2_sidl_int) .eq. 0_sidl_int) then
           call set(retval, i, testtext(testind))
        else
           index(1) = i
           call set(retval, index, testtext(testind))
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createString)
end subroutine Array_createStringgncdbm9unz_mi


! 
! Method:  createDouble[]
! 

recursive subroutine Array_createDoubleocanws5yj__mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDouble.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_double_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDouble)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), modval
  real(kind=sidl_double) :: value
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        value = 2.0_sidl_double ** (-i)
        modval = mod(i, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
           call set(retval, i, value)
        else
           if (modval .eq. 1_sidl_int) then
              index(1) = i
              call set(retval, index, value)
           else
              retval%d_data(i) = value
           endif
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDouble)
end subroutine Array_createDoubleocanws5yj__mi


! 
! Method:  createFloat[]
! 

recursive subroutine ArrayO_createFloatbxvfxabr56_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFloat.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_float_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFloat)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), modval
  real(kind=sidl_float) :: value
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        value = 2.0_sidl_float ** (-i)
        modval = mod(i, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
           call set(retval, i, value)
        else
           if (modval .eq. 1_sidl_int) then
              index(1) = i
              call set(retval, index, value)
           else
              retval%d_data(i) = value
           endif
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFloat)
end subroutine ArrayO_createFloatbxvfxabr56_mi


! 
! Method:  createFcomplex[]
! 

recursive subroutine Arr_createFcomplex6h5qchwr1t_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFcomplex.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_fcomplex_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFcomplex)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1), modval
  complex(kind=sidl_fcomplex) :: value
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        value = cmplx(2.0_sidl_float ** i, 2.0_sidl_float ** (-i))
        modval = mod(i, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
           call set(retval, i, value)
        else
           if (modval .eq. 1_sidl_int) then
              index(1) = i
              call set(retval, index, value)
           else
              retval%d_data(i) = value
           endif
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFcomplex)
end subroutine Arr_createFcomplex6h5qchwr1t_mi


! 
! Method:  createDcomplex[]
! 

recursive subroutine Arr_createDcomplex2117rrwnvr_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDcomplex.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_dcomplex_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDcomplex)
  integer (kind=sidl_int)  :: low(1), up(1), i, index(1), modval
  complex(kind=sidl_dcomplex) :: value
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        value = cmplx(2.0_sidl_double ** i, 2.0_sidl_double ** (-i))
        modval = mod(i, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
           call set(retval, i, value)
        else
           if (modval .eq. 1_sidl_int) then
              index(1) = i
              call set(retval, index, value)
           else
              retval%d_data(i) = value
           endif
        endif
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDcomplex)
end subroutine Arr_createDcomplex2117rrwnvr_mi


! 
! Method:  createObject[]
! 

recursive subroutine Array_createObject4cnhf0d9y2_mi(len, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use ArrayTest_ArrayOps_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createObject.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createObject.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(ArrayTest_ArrayOps_1d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createObject)
  integer (kind=sidl_int) :: low(1), up(1), i, index(1)
  type(ArrayTest_ArrayOps_t) :: object
  call set_null(retval)
  if (len .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = len - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, len - 1_sidl_int
        call new(object, exception)
        if (mod(i, 2_sidl_int) .eq. 0_sidl_int) then
           call set(retval, i, object)
        else
           index(1) = i
           call set(retval, index, object)
        endif

        call deleteRef(object, exception)
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createObject)
end subroutine Array_createObject4cnhf0d9y2_mi


! 
! Method:  create2Int[]
! 

recursive subroutine ArrayOp_create2Intmumtywacyp_mi(d1, d2, retval,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Int.use)
  implicit none
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_int_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Int)
  integer (kind=sidl_int) :: low(2), up(2), index(2), i, j
  integer (kind=sidl_int) :: value, modval
  call set_null(retval)
  if ((d1 .ge. 0_sidl_int) .and. (d2 .ge. 0_sidl_int)) then
     low(1) = 0_sidl_int
     up(1) = d1 - 1_sidl_int
     low(2) = 0_sidl_int
     up(2) = d2 - 1_sidl_int
     value = 0_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, up(1)
        index(1) = i
        do j = 0_sidl_int, up(2)
           index(2) = j
           value = (2.0_sidl_double ** abs(i-j))
           modval = mod(i + j, 3_sidl_int)
           if (modval .eq. 0_sidl_int) then
              call set(retval, index(1), index(2), value)
           else
              if (modval .eq. 1_sidl_int) then
                 call set(retval, index, value)
              else
                 retval%d_data(i, j) = value
              endif
           endif
        enddo
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Int)
end subroutine ArrayOp_create2Intmumtywacyp_mi


! 
! Method:  create2Double[]
! 

recursive subroutine Arra_create2Doublewgvbfvoevx_mi(d1, d2, retval,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Double.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Double.use)
  implicit none
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_double_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Double)
  integer (kind=sidl_int) :: low(2), up(2), index(2), i, j
  integer (kind=sidl_int) :: modval
  double precision value
  call set_null(retval)
  if ((d1 .ge. 0_sidl_int) .and. (d2 .ge. 0_sidl_int)) then
     low(1) = 0_sidl_int
     up(1) = d1 - 1_sidl_int
     low(2) = 0_sidl_int
     up(2) = d2 - 1_sidl_int
     value = 0_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, up(1)
        index(1) = i
        do j = 0_sidl_int, up(2)
           index(2) = j
           value = (2.0_sidl_double ** (i-j))
           modval = mod(i + j, 3_sidl_int)
           if (modval .eq. 0_sidl_int) then
              call set(retval, index(1), index(2), value)
           else
              if (modval .eq. 1_sidl_int) then
                 call set(retval, index, value)
              else
                 retval%d_data(i, j) = value
              endif
           endif
        enddo
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Double)
end subroutine Arra_create2Doublewgvbfvoevx_mi


! 
! Method:  create2Float[]
! 

recursive subroutine Array_create2Floatxtc5m3ogpa_mi(d1, d2, retval,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Float.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Float.use)
  implicit none
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_float_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Float)
  integer (kind=sidl_int)  :: low(2), up(2), index(2), i, j
  integer (kind=sidl_int)  :: modval
  real (kind=sidl_float) :: value
  call set_null(retval)
  if ((d1 .ge. 0_sidl_int) .and. (d2 .ge. 0_sidl_int)) then
     low(1) = 0_sidl_int
     up(1) = d1 - 1_sidl_int
     low(2) = 0_sidl_int
     up(2) = d2 - 1_sidl_int
     value = 0_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, up(1)
        index(1) = i
        do j = 0_sidl_int, up(2)
           index(2) = j
           value = (2.0_sidl_float ** (i-j))
           modval = mod(i+j, 3_sidl_int)
           if (modval .eq. 0_sidl_int) then
              call set(retval, index(1), index(2), value)
           else
              if (modval .eq. 1_sidl_int) then
                 call set(retval, index, value)
              else
                 retval%d_data(i, j) = value
              endif
           endif
        enddo
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Float)
end subroutine Array_create2Floatxtc5m3ogpa_mi


! 
! Method:  create2Dcomplex[]
! 

recursive subroutine Ar_create2Dcomplexe22dd91s69_mi(d1, d2, retval,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Dcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Dcomplex.use)
  implicit none
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_dcomplex_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Dcomplex)
  integer (kind=sidl_int)       :: low(2), up(2), index(2), i, j
  integer (kind=sidl_int)       :: modval
  complex (kind=sidl_dcomplex) :: value
  call set_null(retval)
  if ((d1 .ge. 0_sidl_int) .and. (d2 .ge. 0_sidl_int)) then
     low(1) = 0_sidl_int
     up(1) = d1 - 1_sidl_int
     low(2) = 0_sidl_int
     up(2) = d2 - 1_sidl_int
     value = 0
     call createRow(low, up, retval)
     do i = 0_sidl_int, up(1)
        index(1) = i
        do j = 0_sidl_int, up(2)
           index(2) = j
           value = cmplx(2.0_sidl_double ** i, 2.0_sidl_double ** (-j))
           modval = mod(i + j, 3_sidl_int)
           if (modval .eq. 0_sidl_int) then
              call set(retval, index(1), index(2), value)
           else
              if (modval .eq. 1_sidl_int) then
                 call set(retval, index, value)
              else
                 retval%d_data(i, j) = value
              endif
           endif
        enddo
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Dcomplex)
end subroutine Ar_create2Dcomplexe22dd91s69_mi


! 
! Method:  create2Fcomplex[]
! 

recursive subroutine Ar_create2Fcomplexb8gunt105c_mi(d1, d2, retval,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Fcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Fcomplex.use)
  implicit none
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_fcomplex_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Fcomplex)
  integer (kind=sidl_int)     :: low(2), up(2), index(2), i, j
  integer (kind=sidl_int)     :: modval
  complex (kind=sidl_fcomplex) :: value
  call set_null(retval)
  if ((d1 .ge. 0_sidl_int) .and. (d2 .ge. 0_sidl_int)) then
     low(1) = 0_sidl_int
     up(1) = d1 - 1_sidl_int
     low(2) = 0_sidl_int
     up(2) = d2 - 1_sidl_int
     value = 0
     call createRow(low, up, retval)
     do i = 0_sidl_int, up(1)
        index(1) = i
        do j = 0_sidl_int, up(2)
           index(2) = j
           value = cmplx(2.0_sidl_float ** i, 2.0_sidl_float ** (-j))
           modval = mod(i + j, 3_sidl_int)
           if (modval .eq. 0_sidl_int) then
              call set(retval, index(1), index(2), value)
           else
              if (modval .eq. 1_sidl_int) then
                 call set(retval, index, value)
              else
                 retval%d_data(i,j) = value
              endif
           endif
        enddo
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Fcomplex)
end subroutine Ar_create2Fcomplexb8gunt105c_mi


! 
! Method:  create2String[]
! 

recursive subroutine Arra_create2Stringwqsqcdd0c__mi(d1, d2, retval,           &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2String.use)
  ! Insert-Code-Here {ArrayTest.ArrayOps.create2String.use} (use statements)
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2String.use)
  implicit none
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_string_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2String)
  character (len=9) :: testtext(10)
  integer (kind=sidl_int) :: low(2), up(2), i, j, c, testind
  data testtext / 'I''d', 'rather', 'write', 'programs', 'to', 'write', &
       'programs', 'than', 'write', 'programs.'/
  call set_null(retval)
  c = 0_sidl_int
  if (d1 .ge. 0_sidl_int .or. d2 .ge. 0_sidl_int) then
     low(1) = 0_sidl_int
     up(1) = d1 - 1_sidl_int
     low(2) = 0_sidl_int
     up(2) = d2 - 1_sidl_int
     call createRow(low, up, retval)
     do i = 0_sidl_int, d1 - 1_sidl_int
        do j = 0_sidl_int, d2 - 1_sidl_int
           testind = mod(c, 10_sidl_int) + 1_sidl_int
           c = c + 1_sidl_int
           call set(retval, i, j, testtext(testind))
        enddo
     enddo
  endif
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2String)
end subroutine Arra_create2Stringwqsqcdd0c__mi


! 
! Method:  create3Int[]
! 

recursive subroutine ArrayOp_create3Int4ikxjsbhe8_mi(retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create3Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create3Int.use)
  implicit none
  type(sidl_int_3d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create3Int)
  integer (kind=sidl_int)  :: dim, low(3), up(3), ind(3)
  integer (kind=sidl_int)  :: value, modval, i, j, k, intFunc
  dim = 3_sidl_int
  do i = 1_sidl_int, dim
    low(i) = 0_sidl_int
    if (i .le. 2_sidl_int) then
      up(i) = 3_sidl_int
    else
      up(i) = 2_sidl_int
    endif
  enddo
  call createRow(low, up, retval)
  do i = 0_sidl_int, up(1)
    ind(1) = i
    do j = 0_sidl_int, up(2)
      ind(2) = j
      do k = 0_sidl_int, up(3)
        ind(3) = k
        value = intFunc(dim, ind)
        modval = mod(i+j+k, 3_sidl_int)
        if (modval .eq. 0_sidl_int) then
          call set(retval, ind(1), ind(2), ind(3), value)
        else
           if (modval .eq. 1_sidl_int) then
              call set(retval, ind, value)
           else
              retval%d_data(i,j,k) = value
           endif
        endif
      enddo
    enddo
  enddo
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create3Int)
end subroutine ArrayOp_create3Int4ikxjsbhe8_mi


! 
! Method:  create4Int[]
! 

recursive subroutine ArrayOp_create4Inten8imvrfau_mi(retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create4Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create4Int.use)
  implicit none
  type(sidl_int_4d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create4Int)
  integer (kind=sidl_int)  :: dim, low(4), up(4), ind(4)
  integer (kind=sidl_int)  :: value, modval, i, j, k, l, intFunc
  dim = 4_sidl_int
  do i = 1_sidl_int, dim
    low(i) = 0_sidl_int
    if (i .le. 2_sidl_int) then
      up(i) = 3_sidl_int
    else
      up(i) = 2_sidl_int
    endif
  enddo
  call createRow(low, up, retval)
  do i = 0_sidl_int, up(1)
    ind(1) = i
    do j = 0_sidl_int, up(2)
      ind(2) = j
      do k = 0_sidl_int, up(3)
        ind(3) = k
        do l = 0_sidl_int, up(4)
          ind(4) = l
          value = intFunc(dim, ind)
          modval = mod(i+j+k+l, 3_sidl_int)
          if (modval .eq. 0_sidl_int) then
            call set(retval, ind(1), ind(2), ind(3), ind(4), value)
          else
             if (modval .eq. 1_sidl_int) then
                call set(retval, ind, value)
             else
                retval%d_data(i,j,k,l) = value
             endif
          endif
        enddo
      enddo
    enddo
  enddo
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create4Int)
end subroutine ArrayOp_create4Inten8imvrfau_mi


! 
! Method:  create5Int[]
! 

recursive subroutine ArrayOp_create5Intezrd12u4cz_mi(retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create5Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create5Int.use)
  implicit none
  type(sidl_int_5d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create5Int)
  integer (kind=sidl_int)  :: dim, low(5), up(5), ind(5)
  integer (kind=sidl_int)  :: value, modval, i, j, k, l, m, intFunc
  dim = 5_sidl_int
  do i = 1_sidl_int, dim
     low(i) = 0_sidl_int
     if (i .le. 2_sidl_int) then
        up(i) = 3_sidl_int
     else
        up(i) = 2_sidl_int
     endif
  enddo
  call createRow(low, up, retval)
  do i = 0_sidl_int, up(1)
     ind(1) = i
     do j = 0_sidl_int, up(2)
        ind(2) = j
        do k = 0_sidl_int, up(3)
           ind(3) = k
           do l = 0_sidl_int, up(4)
              ind(4) = l
              do m = 0_sidl_int, up(5)
                 ind(5) = m
                 value = intFunc(dim, ind)
                 modval = mod(i+j+k+l+m, 3_sidl_int)
                 if (modval .eq. 0_sidl_int) then
                    call set(retval, ind(1), ind(2), ind(3), ind(4), ind(5), value)
                 else
                    if (modval .eq. 1_sidl_int) then
                       call set(retval, ind, value)
                    else
                       retval%d_data(i,j,k,l,m) = value
                    endif
                 endif
              enddo
           enddo
        enddo
     enddo
  enddo
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create5Int)
end subroutine ArrayOp_create5Intezrd12u4cz_mi


! 
! Method:  create6Int[]
! 

recursive subroutine ArrayOp_create6Intfsnv4mqvwk_mi(retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create6Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create6Int.use)
  implicit none
  type(sidl_int_6d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create6Int)
  integer (kind=sidl_int)  :: dim, low(6), up(6), ind(6)
  integer (kind=sidl_int)  :: value, modval, i, j, k, l, m, n, intFunc
  dim = 6
  do i = 1_sidl_int, dim
     low(i) = 0_sidl_int
     if (i .le. 2_sidl_int) then
        up(i) = 3_sidl_int
     else
        up(i) = 2_sidl_int
     endif
  enddo
  call createRow(low, up, retval)
  do i = 0_sidl_int, up(1)
     ind(1) = i
     do j = 0_sidl_int, up(2)
        ind(2) = j
        do k = 0_sidl_int, up(3)
           ind(3) = k
           do l = 0_sidl_int, up(4)
              ind(4) = l
              do m = 0_sidl_int, up(5)
                 ind(5) = m
                 do n = 0_sidl_int, up(6)
                    ind(6) = n
                    value = intFunc(dim, ind)
                    modval = mod(i+j+k+l+m+n, 3_sidl_int)
                    if (modval .eq. 0_sidl_int) then
                       call set(retval, ind(1), ind(2), ind(3), ind(4), ind(5), ind(6), value)
                    else
                       if (modval .eq. 1_sidl_int) then
                          call set(retval, ind, value)
                       else
                          retval%d_data(i,j,k,l,m,n) = value
                       endif
                    endif
                 enddo
              enddo
           enddo
        enddo
     enddo
  enddo
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create6Int)
end subroutine ArrayOp_create6Intfsnv4mqvwk_mi


! 
! Method:  create7Int[]
! 

recursive subroutine ArrayOp_create7Intv3xup3rl7f_mi(retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create7Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create7Int.use)
  implicit none
  type(sidl_int_7d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create7Int)
  integer (kind=sidl_int)  :: dim, low(7), up(7), ind(7)
  integer (kind=sidl_int)  :: value, modval, i, j, k, l, m, n, o, intFunc
  dim = 7_sidl_int
  do i = 1_sidl_int, dim
     low(i) = 0_sidl_int
     if (i .le. 2_sidl_int) then
        up(i) = 3_sidl_int
     else
        up(i) = 2_sidl_int
     endif
  enddo
  call createRow(low, up, retval)
  do i = 0_sidl_int, up(1)
     ind(1) = i
     do j = 0_sidl_int, up(2)
        ind(2) = j
        do k = 0_sidl_int, up(3)
           ind(3) = k
           do l = 0_sidl_int, up(4)
              ind(4) = l
              do m = 0_sidl_int, up(5)
                 ind(5) = m
                 do n = 0_sidl_int, up(6)
                    ind(6) = n
                    do o = 0_sidl_int, up(7)
                       ind(7) = o
                       value = intFunc(dim, ind)
                       modval = mod(i+j+k+l+m+n+o, 3_sidl_int)
                       if (modval .eq. 0_sidl_int) then
                          call set(retval, ind(1), ind(2), ind(3), ind(4), ind(5), ind(6), ind(7), value)
                       else
                          if (modval .eq. 1_sidl_int) then
                             call set(retval, ind, value)
                          else
                             retval%d_data(i,j,k,l,m,n,o) = value
                          endif
                       endif
                    enddo
                 enddo
              enddo
           enddo
        enddo
     enddo
  enddo
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create7Int)
end subroutine ArrayOp_create7Intv3xup3rl7f_mi


! 
! Method:  makeBool[]
! 

recursive subroutine ArrayTest_ArrayOps_makeBool_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_bool_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeBool.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeBool.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_bool_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeBool)
  call createBool(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeBool)
end subroutine ArrayTest_ArrayOps_makeBool_mi


! 
! Method:  makeChar[]
! 

recursive subroutine ArrayTest_ArrayOps_makeChar_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_char_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeChar.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeChar.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_char_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeChar)
  call createChar(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeChar)
end subroutine ArrayTest_ArrayOps_makeChar_mi


! 
! Method:  makeInt[]
! 

recursive subroutine ArrayTest_ArrayOps_makeInt_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInt.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_int_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInt)
  call createInt(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInt)
end subroutine ArrayTest_ArrayOps_makeInt_mi


! 
! Method:  makeLong[]
! 

recursive subroutine ArrayTest_ArrayOps_makeLong_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_long_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeLong.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeLong.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_long_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeLong)
  call createLong(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeLong)
end subroutine ArrayTest_ArrayOps_makeLong_mi


! 
! Method:  makeString[]
! 

recursive subroutine ArrayOp_makeStringucptewb9ad_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeString.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeString.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_string_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeString)
  call createString(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeString)
end subroutine ArrayOp_makeStringucptewb9ad_mi


! 
! Method:  makeDouble[]
! 

recursive subroutine ArrayOp_makeDouble7j4r5nh156_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDouble.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_double_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDouble)
  call createDouble(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDouble)
end subroutine ArrayOp_makeDouble7j4r5nh156_mi


! 
! Method:  makeFloat[]
! 

recursive subroutine ArrayTest_ArrayOps_makeFloat_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFloat.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_float_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFloat)
  call createFloat(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFloat)
end subroutine ArrayTest_ArrayOps_makeFloat_mi


! 
! Method:  makeFcomplex[]
! 

recursive subroutine Array_makeFcomplexv0425q5_2o_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFcomplex.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_fcomplex_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFcomplex)
  call createFcomplex(len,a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFcomplex)
end subroutine Array_makeFcomplexv0425q5_2o_mi


! 
! Method:  makeDcomplex[]
! 

recursive subroutine Array_makeDcomplexp093yr7xy7_mi(len, a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDcomplex.use)
  implicit none
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_dcomplex_1d) :: a
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDcomplex)
  call createDcomplex(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDcomplex)
end subroutine Array_makeDcomplexp093yr7xy7_mi


! 
! Method:  makeInOutBool[]
! 

recursive subroutine Arra_makeInOutBool5jfcbo_ube_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_bool_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutBool.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutBool.use)
  implicit none
  type(sidl_bool_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutBool)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createBool(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutBool)
end subroutine Arra_makeInOutBool5jfcbo_ube_mi


! 
! Method:  makeInOutChar[]
! 

recursive subroutine Arra_makeInOutChartpjt62yvqd_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_char_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutChar.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutChar.use)
  implicit none
  type(sidl_char_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutChar)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createChar(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutChar)
end subroutine Arra_makeInOutChartpjt62yvqd_mi


! 
! Method:  makeInOutInt[]
! 

recursive subroutine Array_makeInOutIntyo69yq7z9p_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutInt.use)
  implicit none
  type(sidl_int_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutInt)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createInt(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutInt)
end subroutine Array_makeInOutIntyo69yq7z9p_mi


! 
! Method:  makeInOutLong[]
! 

recursive subroutine Arra_makeInOutLonghy687ifd8__mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_long_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutLong.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutLong.use)
  implicit none
  type(sidl_long_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutLong)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createLong(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutLong)
end subroutine Arra_makeInOutLonghy687ifd8__mi


! 
! Method:  makeInOutString[]
! 

recursive subroutine Ar_makeInOutString_uzdlcqlye_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_string_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutString.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutString.use)
  implicit none
  type(sidl_string_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutString)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createString(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutString)
end subroutine Ar_makeInOutString_uzdlcqlye_mi


! 
! Method:  makeInOutDouble[]
! 

recursive subroutine Ar_makeInOutDouble045y36r_il_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDouble.use)
  implicit none
  type(sidl_double_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDouble)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createDouble(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDouble)
end subroutine Ar_makeInOutDouble045y36r_il_mi


! 
! Method:  makeInOutFloat[]
! 

recursive subroutine Arr_makeInOutFloati3hp8_tmhu_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFloat.use)
  implicit none
  type(sidl_float_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFloat)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createFloat(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFloat)
end subroutine Arr_makeInOutFloati3hp8_tmhu_mi


! 
! Method:  makeInOutDcomplex[]
! 

recursive subroutine makeInOutDcomplexuq8uzvc62wd_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDcomplex.use)
  implicit none
  type(sidl_dcomplex_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDcomplex)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createDcomplex(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDcomplex)
end subroutine makeInOutDcomplexuq8uzvc62wd_mi


! 
! Method:  makeInOutFcomplex[]
! 

recursive subroutine makeInOutFcomplexnnmei919ckr_mi(a, len, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFcomplex.use)
  implicit none
  type(sidl_fcomplex_1d) :: a
  ! inout
  integer (kind=sidl_int) :: len
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFcomplex)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call createFcomplex(len, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFcomplex)
end subroutine makeInOutFcomplexnnmei919ckr_mi


! 
! Method:  makeInOut2Int[]
! 

recursive subroutine Arra_makeInOut2Intar5f7eylp9_mi(a, d1, d2, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Int.use)
  implicit none
  type(sidl_int_2d) :: a
  ! inout
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Int)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create2Int(d1, d2, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Int)
end subroutine Arra_makeInOut2Intar5f7eylp9_mi


! 
! Method:  makeInOut2Double[]
! 

recursive subroutine A_makeInOut2Doublebgpvhq3prh_mi(a, d1, d2, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Double.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Double.use)
  implicit none
  type(sidl_double_2d) :: a
  ! inout
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Double)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create2Double(d1, d2, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Double)
end subroutine A_makeInOut2Doublebgpvhq3prh_mi


! 
! Method:  makeInOut2Float[]
! 

recursive subroutine Ar_makeInOut2Floatq670n4ukao_mi(a, d1, d2, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_float_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Float.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Float.use)
  implicit none
  type(sidl_float_2d) :: a
  ! inout
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Float)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create2Float(d1, d2, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Float)
end subroutine Ar_makeInOut2Floatq670n4ukao_mi


! 
! Method:  makeInOut2Dcomplex[]
! 

recursive subroutine makeInOut2Dcomplexckhu3osbep_mi(a, d1, d2, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Dcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Dcomplex.use)
  implicit none
  type(sidl_dcomplex_2d) :: a
  ! inout
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Dcomplex)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create2Dcomplex(d1, d2, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Dcomplex)
end subroutine makeInOut2Dcomplexckhu3osbep_mi


! 
! Method:  makeInOut2Fcomplex[]
! 

recursive subroutine makeInOut2Fcomplexxh7zrwg3ge_mi(a, d1, d2, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_fcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Fcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Fcomplex.use)
  implicit none
  type(sidl_fcomplex_2d) :: a
  ! inout
  integer (kind=sidl_int) :: d1
  ! in
  integer (kind=sidl_int) :: d2
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Fcomplex)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create2Fcomplex(d1, d2, a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Fcomplex)
end subroutine makeInOut2Fcomplexxh7zrwg3ge_mi


! 
! Method:  makeInOut3Int[]
! 

recursive subroutine Arra_makeInOut3Intxzlzgrsum3_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut3Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut3Int.use)
  implicit none
  type(sidl_int_3d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut3Int)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create3Int(a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut3Int)
end subroutine Arra_makeInOut3Intxzlzgrsum3_mi


! 
! Method:  makeInOut4Int[]
! 

recursive subroutine Arra_makeInOut4Intiw_x5vq0ti_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut4Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut4Int.use)
  implicit none
  type(sidl_int_4d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut4Int)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create4Int(a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut4Int)
end subroutine Arra_makeInOut4Intiw_x5vq0ti_mi


! 
! Method:  makeInOut5Int[]
! 

recursive subroutine Arra_makeInOut5Intz0fj9a5vg8_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut5Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut5Int.use)
  implicit none
  type(sidl_int_5d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut5Int)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create5Int(a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut5Int)
end subroutine Arra_makeInOut5Intz0fj9a5vg8_mi


! 
! Method:  makeInOut6Int[]
! 

recursive subroutine Arra_makeInOut6Intxec69frccs_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut6Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut6Int.use)
  implicit none
  type(sidl_int_6d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut6Int)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create6Int(a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut6Int)
end subroutine Arra_makeInOut6Intxec69frccs_mi


! 
! Method:  makeInOut7Int[]
! 

recursive subroutine Arra_makeInOut7Int6u8dj9s6ad_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut7Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut7Int.use)
  implicit none
  type(sidl_int_7d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut7Int)
  if (not_null(a)) then
     call deleteRef(a)
  endif
  call create7Int(a, exception)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut7Int)
end subroutine Arra_makeInOut7Int6u8dj9s6ad_mi


! 
! Method:  checkGeneric[]
! Return as out parameters the type and dimension of the 
! array passed in. If a is NULL, dimen == type == 0 on exit.
! The contents of the array have the default values for a 
! newly created array.
! 

recursive subroutine Array_checkGenericd1y8aqvsz6_mi(a, dmn, tp, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_array_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkGeneric.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkGeneric.use)
  implicit none
  type(sidl__array) :: a
  ! in
  integer (kind=sidl_int) :: dmn
  ! out
  integer (kind=sidl_int) :: tp
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkGeneric)
! Insert the implementation here...

  if (not_null(a)) then
     dmn = dimen(a)
     tp = type(a)
  else 
     dmn = 0_sidl_int
     tp = 0_sidl_int
  end if
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkGeneric)
end subroutine Array_checkGenericd1y8aqvsz6_mi


! 
! Method:  createGeneric[]
! Create an array of the type and dimension specified and
! return it. A type of 0 causes a NULL array to be returned.
! 

recursive subroutine Arra_createGenericza7tz4ksso_mi(dmn, tp, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_array_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createGeneric.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createGeneric.use)
  implicit none
  integer (kind=sidl_int) :: dmn
  ! in
  integer (kind=sidl_int) :: tp
  ! in
  type(sidl__array) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createGeneric)
  ! Insert the implementation here...
  integer (kind=sidl_int) :: lw(7), up(7) 
  integer (kind=sidl_int) :: i
  type(sidl_bool_2d) :: x
  do i=1_sidl_int,7_sidl_int
     lw(i)=0_sidl_int
     up(i)=2_sidl_int
  enddo
  !  call createRow(lw,up,x)
  !  call cast(x,retval)
  call createarraybytype(tp, dmn, lw, up, retval);
  
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createGeneric)
end subroutine Arra_createGenericza7tz4ksso_mi


! 
! Method:  passGeneric[]
! Testing passing generic arrays using every possible mode.
! The returned array is a copy of inArg, so if inArg != NULL,
! the return value should != NULL. outArg is also a copy of
! inArg.
! If inOutArg is NULL on entry, a 2-D array of int that should
! pass check2Int is returned.
! If inOutArg is not NULL on entry and its dimension is even,
! it is returned unchanged; otherwise, NULL is returned.
! 

recursive subroutine ArrayO_passGenericwdjmxoh8x1_mi(inArg, inOutArg, outArg,  &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_array_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.passGeneric.use)
  use sidl_int_array
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.passGeneric.use)
  implicit none
  type(sidl__array) :: inArg
  ! in
  type(sidl__array) :: inOutArg
  ! inout
  type(sidl__array) :: outArg
  ! out
  type(sidl__array) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.passGeneric)
  ! Insert the implementation here...

  type(sidl_int_2d) :: i2array
  integer (kind=sidl_int) :: lw(7), up(7), i, tp, dmn, outdimen

  tp = type(inArg) 
  if (not_null(inArg)) then    
     dmn = 0_sidl_int
     dmn = dimen(inArg);
     do i=1_sidl_int, dmn+1_sidl_int
        lw(i) = lower(inArg, i-1_sidl_int)
        up(i) = upper(inArg, i-1_sidl_int)
     enddo
  end if
  
  call set_null(retval)
  call copyarraybytype(inArg, lw, up, retval);
  call set_null(outArg)
  call copyarraybytype(inArg, lw, up, outArg);
  if (not_null(inOutArg)) then
     outdimen = dimen(inOutArg)
     if (mod(outdimen, 2_sidl_int) .eq. 1_sidl_int) then
        call deleteref(inOutArg);
        call set_null(inOutArg);
     end if
  else 
     call create2Int(3_sidl_int, 3_sidl_int, i2array, exception);
     call cast(i2array, inOutArg);
  end if
  
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.passGeneric)
end subroutine ArrayO_passGenericwdjmxoh8x1_mi


! 
! Method:  initRarray1Int[]
! 

recursive subroutine Arr_initRarray1Intr5gikmh2h0_mi(a, n, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Int.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1) :: a
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Int)
  integer (kind=sidl_int) :: value, i
  integer (kind=sidl_long) :: nextprime, tmp
  if (n .ge. 0_sidl_int) then
     value = 0_sidl_int
     do i = 0_sidl_int, n - 1_sidl_int
        tmp = value
        value = nextprime(tmp)
        a(i) = value
     enddo
  endif
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Int)
end subroutine Arr_initRarray1Intr5gikmh2h0_mi


! 
! Method:  initRarray3Int[]
! 

recursive subroutine Arr_initRarray3Intjwja9lszfc_mi(a, n, m, o, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray3Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray3Int.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1, 0:o-1) :: a
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray3Int)

  integer (kind=sidl_int) :: ind(3)
  integer (kind=sidl_int) :: value, modval, i, j, k, intFunc

  do i = 0_sidl_int, n-1_sidl_int
     ind(1) = i
     do j = 0_sidl_int, m-1_sidl_int
        ind(2) = j
        do k = 0_sidl_int, o-1_sidl_int
           ind(3) = k
           value = intFunc(3_sidl_int, ind)
           a(i,j,k)=value
        enddo
     enddo
  enddo
  ! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray3Int)
end subroutine Arr_initRarray3Intjwja9lszfc_mi


! 
! Method:  initRarray7Int[]
! 

recursive subroutine Arr_initRarray7Intkao7vazq6m_mi(a, n, m, o, p, q, r, s,   &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray7Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray7Int.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  integer (kind=sidl_int) :: p
  ! in
  integer (kind=sidl_int) :: q
  ! in
  integer (kind=sidl_int) :: r
  ! in
  integer (kind=sidl_int) :: s
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1, 0:o-1, 0:p-1,       &
    0:q-1, 0:r-1, 0:s-1) :: a
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray7Int)
  integer (kind=sidl_int) :: ind(7)
  integer (kind=sidl_int) :: value, modval, d, e, f, g, h, i, j, intFunc
  do d = 0_sidl_int, n-1_sidl_int
     ind(1) = d
     do e = 0_sidl_int, m-1_sidl_int
        ind(2) = e
        do f = 0_sidl_int, o-1_sidl_int
           ind(3) = f
           do g = 0_sidl_int, p-1_sidl_int
              ind(4) = g
              do h = 0_sidl_int, q-1_sidl_int
                 ind(5) = h
                 do i = 0_sidl_int, r-1_sidl_int
                    ind(6) = i
                    do j = 0_sidl_int, s-1_sidl_int
                       ind(7) = j
                       value = intFunc(7_sidl_int, ind)
                       a(d,e,f,g,h,i,j) = value
                    enddo
                 enddo
              enddo
           enddo
        enddo
     enddo
  enddo
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray7Int)
end subroutine Arr_initRarray7Intkao7vazq6m_mi


! 
! Method:  initRarray1Double[]
! 

recursive subroutine initRarray1Doublen5sxxzsz_c8_mi(a, n, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Double.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Double.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  real (kind=sidl_double), target, dimension(0:n-1) :: a
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Double)

  integer (kind=sidl_int) :: i
  real (kind=sidl_double) :: value

  if (n .ge. 0_sidl_int) then
     do i = 0_sidl_int, n - 1_sidl_int
        value = 2.0_sidl_double ** (-i*1.0)
        a(i) = value
     enddo
  endif
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Double)
end subroutine initRarray1Doublen5sxxzsz_c8_mi


! 
! Method:  initRarray1Dcomplex[]
! 

recursive subroutine initRarray1Dcomplevs7rtjmqbl_mi(a, n, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Dcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Dcomplex.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  complex (kind=sidl_dcomplex), target, dimension(0:n-1) :: a
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Dcomplex)
 
  integer (kind=sidl_int) :: i
  complex (kind=sidl_dcomplex) :: value

  if (n .ge. 0_sidl_int) then
     do i = 0_sidl_int, n - 1_sidl_int
        value = cmplx(2.0_sidl_double ** i, 2.0_sidl_double ** (-i))
        a(i) = value
     enddo
  endif
  
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Dcomplex)
end subroutine initRarray1Dcomplevs7rtjmqbl_mi


! 
! Method:  checkRarray1Int[]
! 

recursive subroutine Ar_checkRarray1Int9vwqpxnxpn_mi(a, n, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Int.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1) :: a
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Int)
  integer (kind=sidl_int) :: i, prime
  integer (kind=sidl_long) :: nextprime, tmp

  retval = .false.
  prime = 0_sidl_int
  do i = 0_sidl_int, n-1_sidl_int
     tmp = prime
     prime = nextprime(tmp)
     if (a(i) .ne. prime) then
        retval = .false.
        return
     endif
  enddo
  retval = .true.
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Int)
end subroutine Ar_checkRarray1Int9vwqpxnxpn_mi


! 
! Method:  checkRarray3Int[]
! 

recursive subroutine Ar_checkRarray3Inteky23o1ltn_mi(a, n, m, o, retval,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray3Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray3Int.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1, 0:o-1) :: a
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray3Int)
  integer (kind=sidl_int) :: i, j, k, expectedvalue, ind(3)
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  
  do i = 0_sidl_int, n-1_sidl_int
     ind(1) = i
     do j = 0_sidl_int, m-1_sidl_int
        ind(2) = j
        do k = 0_sidl_int, o-1_sidl_int
           ind(3) = k
           expectedvalue = intFunc(3_sidl_int, ind)
           if (a(i,j,k) .ne. expectedvalue) then
              return
           endif
        enddo
     enddo
  enddo
  retval = .true.
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray3Int)
end subroutine Ar_checkRarray3Inteky23o1ltn_mi


! 
! Method:  checkRarray7Int[]
! 

recursive subroutine Ar_checkRarray7Int1glebxan93_mi(a, n, m, o, p, q, r, s,   &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray7Int.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray7Int.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  integer (kind=sidl_int) :: p
  ! in
  integer (kind=sidl_int) :: q
  ! in
  integer (kind=sidl_int) :: r
  ! in
  integer (kind=sidl_int) :: s
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1, 0:o-1, 0:p-1,       &
    0:q-1, 0:r-1, 0:s-1) :: a
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray7Int)
  integer (kind=sidl_int) ::  d,e,f,g,h,i,j
  integer (kind=sidl_int) :: value
  integer (kind=sidl_int) :: ind(7), alow(7), aup(7)
  integer (kind=sidl_int) :: intFunc
  retval = .false.
  
  do d = 0_sidl_int, n-1_sidl_int
     ind(1) = d
     do e = 0_sidl_int, m-1_sidl_int
        ind(2) = e
        do f = 0_sidl_int, o-1_sidl_int
           ind(3) = f
           do g = 0_sidl_int, p-1_sidl_int
              ind(4) = g
              do h = 0_sidl_int, q-1_sidl_int
                 ind(5) = h
                 do i = 0_sidl_int, r-1_sidl_int
                    ind(6) = i
                    do j = 0_sidl_int, s-1_sidl_int
                       ind(7) = j
                       value=intFunc(7_sidl_int,ind)
                       if (value .ne. a(d,e,f,g,h,i,j)) then
                          return
                       endif
                    enddo
                 enddo
              enddo
           enddo
        enddo
     enddo
  enddo
  retval = .true.
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray7Int)
end subroutine Ar_checkRarray7Int1glebxan93_mi


! 
! Method:  checkRarray1Double[]
! 

recursive subroutine checkRarray1Doubleznjn8hgixv_mi(a, n, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_double_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Double.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Double.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  real (kind=sidl_double), target, dimension(0:n-1) :: a
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Double)

  real (kind=sidl_double) :: expectedvalue
  integer (kind=sidl_int) ::  i
  retval = .false.
  
  do i = 0_sidl_int, n - 1_sidl_int
     expectedvalue = (2.0_sidl_double ** (-i))
     if (a(i) .ne. expectedvalue) then
        return
     endif
  enddo
  retval = .true.
  
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Double)
end subroutine checkRarray1Doubleznjn8hgixv_mi


! 
! Method:  checkRarray1Dcomplex[]
! 

recursive subroutine checkRarray1Dcompljc_853ps4k_mi(a, n, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_dcomplex_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Dcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Dcomplex.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  complex (kind=sidl_dcomplex), target, dimension(0:n-1) :: a
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Dcomplex)

  complex (kind=sidl_dcomplex) :: expectedvalue
  integer (kind=sidl_int) :: i
  retval = .false.
  do i = 0_sidl_int, n - 1_sidl_int
     expectedvalue = cmplx(2.0_sidl_double ** i, 2.0_sidl_double ** (-i))
     if (a(i) .ne. expectedvalue) then
        return
     endif
  enddo
  retval = .true.
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Dcomplex)
end subroutine checkRarray1Dcompljc_853ps4k_mi


! 
! Method:  matrixMultiply[]
! 

recursive subroutine Arr_matrixMultiply419jg3a5yu_mi(a, b, res, n, m, o,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.matrixMultiply.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.matrixMultiply.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1) :: a
  ! in
  integer (kind=sidl_int), target, dimension(0:m-1, 0:o-1) :: b
  ! in
  integer (kind=sidl_int), target, dimension(0:n-1, 0:o-1) :: res
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.matrixMultiply)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.matrixMultiply)
end subroutine Arr_matrixMultiply419jg3a5yu_mi


! 
! Method:  checkMatrixMultiply[]
! 

recursive subroutine checkMatrixMultiplpuxdl07ngd_mi(a, b, res, n, m, o,       &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkMatrixMultiply.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkMatrixMultiply.use)
  implicit none
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1) :: a
  ! in
  integer (kind=sidl_int), target, dimension(0:m-1, 0:o-1) :: b
  ! in
  integer (kind=sidl_int), target, dimension(0:n-1, 0:o-1) :: res
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkMatrixMultiply)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkMatrixMultiply)
end subroutine checkMatrixMultiplpuxdl07ngd_mi


! 
! Method:  mm[]
! 

recursive subroutine ArrayTest_ArrayOps_mm_mi(self, a, b, res, n, m, o,        &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.mm.use)
  ! Insert-Code-Here {ArrayTest.ArrayOps.mm.use} (use statements)
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.mm.use)
  implicit none
  type(ArrayTest_ArrayOps_t) :: self
  ! in
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1) :: a
  ! in
  integer (kind=sidl_int), target, dimension(0:m-1, 0:o-1) :: b
  ! in
  integer (kind=sidl_int), target, dimension(0:n-1, 0:o-1) :: res
  ! inout

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.mm)
! Insert-Code-Here {ArrayTest.ArrayOps.mm} (mm method)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.mm)
end subroutine ArrayTest_ArrayOps_mm_mi


! 
! Method:  checkmm[]
! 

recursive subroutine ArrayTest_ArrayOps_checkmm_mi(self, a, b, res, n, m, o,   &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use ArrayTest_ArrayOps
  use sidl_int_array
  use ArrayTest_ArrayOps_impl
  ! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkmm.use)
  ! Insert-Code-Here {ArrayTest.ArrayOps.checkmm.use} (use statements)
  ! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkmm.use)
  implicit none
  type(ArrayTest_ArrayOps_t) :: self
  ! in
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: o
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:n-1, 0:m-1) :: a
  ! in
  integer (kind=sidl_int), target, dimension(0:m-1, 0:o-1) :: b
  ! in
  integer (kind=sidl_int), target, dimension(0:n-1, 0:o-1) :: res
  ! in

! DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkmm)
! Insert-Code-Here {ArrayTest.ArrayOps.checkmm} (checkmm method)
! DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkmm)
end subroutine ArrayTest_ArrayOps_checkmm_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
