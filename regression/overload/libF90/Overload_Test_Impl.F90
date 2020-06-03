! 
! File:          Overload_Test_Impl.F90
! Symbol:        Overload.Test-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.Test
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.Test" (version 1.0)
! 
! This class is used as the work-horse, returning the value passed
! in.
! 


#include "Overload_ParentTest_fAbbrev.h"
#include "Overload_AClass_fAbbrev.h"
#include "Overload_Test_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Overload_BClass_fAbbrev.h"
#include "Overload_AnException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Overload_Test__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test._ctor.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.Test._ctor)
end subroutine Overload_Test__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Overload_Test__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test._ctor2.use)
  ! Insert-Code-Here {Overload.Test._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Overload.Test._ctor2.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  type(Overload_Test_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test._ctor2)
! Insert-Code-Here {Overload.Test._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Overload.Test._ctor2)
end subroutine Overload_Test__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Overload_Test__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test._dtor.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.Test._dtor)
end subroutine Overload_Test__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Overload_Test__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.Test._load)
end subroutine Overload_Test__load_mi


! 
! Method:  getValue[IntDouble]
! 

recursive subroutine getValueIntDoublerd34dbz9jls_mi(self, a, b, retval,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  integer (kind=sidl_int) :: a
  ! in
  real (kind=sidl_double) :: b
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble)
  retval = a + b
! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble)
end subroutine getValueIntDoublerd34dbz9jls_mi


! 
! Method:  getValue[DoubleInt]
! 

recursive subroutine getValueDoubleInt_q3fsx81gvi_mi(self, a, b, retval,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  real (kind=sidl_double) :: a
  ! in
  integer (kind=sidl_int) :: b
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt)
  retval = a + b
! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt)
end subroutine getValueDoubleInt_q3fsx81gvi_mi


! 
! Method:  getValue[IntDoubleFloat]
! 

recursive subroutine getValueIntDoubleFcskufgz9sq_mi(self, a, b, c, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  integer (kind=sidl_int) :: a
  ! in
  real (kind=sidl_double) :: b
  ! in
  real (kind=sidl_float) :: c
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat)
  retval = a + b + c
! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat)
end subroutine getValueIntDoubleFcskufgz9sq_mi


! 
! Method:  getValue[DoubleIntFloat]
! 

recursive subroutine getValueDoubleIntFt6g5k8522j_mi(self, a, b, c, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  real (kind=sidl_double) :: a
  ! in
  integer (kind=sidl_int) :: b
  ! in
  real (kind=sidl_float) :: c
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat)
  retval = a + b + c
! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat)
end subroutine getValueDoubleIntFt6g5k8522j_mi


! 
! Method:  getValue[IntFloatDouble]
! 

recursive subroutine getValueIntFloatDowgcor0ywbd_mi(self, a, b, c, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  integer (kind=sidl_int) :: a
  ! in
  real (kind=sidl_float) :: b
  ! in
  real (kind=sidl_double) :: c
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble)
  retval = a + b + c
! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble)
end subroutine getValueIntFloatDowgcor0ywbd_mi


! 
! Method:  getValue[DoubleFloatInt]
! 

recursive subroutine getValueDoubleFloau2t4dhu7tm_mi(self, a, b, c, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  real (kind=sidl_double) :: a
  ! in
  real (kind=sidl_float) :: b
  ! in
  integer (kind=sidl_int) :: c
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt)
  retval = a + b + c
! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt)
end subroutine getValueDoubleFloau2t4dhu7tm_mi


! 
! Method:  getValue[FloatIntDouble]
! 

recursive subroutine getValueFloatIntDoztrk04qk4x_mi(self, a, b, c, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  real (kind=sidl_float) :: a
  ! in
  integer (kind=sidl_int) :: b
  ! in
  real (kind=sidl_double) :: c
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble)
  retval = a + b + c
! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble)
end subroutine getValueFloatIntDoztrk04qk4x_mi


! 
! Method:  getValue[FloatDoubleInt]
! 

recursive subroutine getValueFloatDoublt47zq8i7bz_mi(self, a, b, c, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  real (kind=sidl_float) :: a
  ! in
  real (kind=sidl_double) :: b
  ! in
  integer (kind=sidl_int) :: c
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt)
  retval = a + b + c
! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt)
end subroutine getValueFloatDoublt47zq8i7bz_mi


! 
! Method:  getValue[Exception]
! 

recursive subroutine getValueExceptionjjnkngq3yn7_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AnException
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueException.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueException.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  type(Overload_AnException_t) :: v
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueException)
  call getNote(v, retval,exception)
! DO-NOT-DELETE splicer.end(Overload.Test.getValueException)
end subroutine getValueExceptionjjnkngq3yn7_mi


! 
! Method:  getValue[AClass]
! 

recursive subroutine Overload_Test_getValueAClass_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  type(Overload_AClass_t) :: v
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass)
  call getValue(v, retval,exception)
! DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass)
end subroutine Overload_Test_getValueAClass_mi


! 
! Method:  getValue[BClass]
! 

recursive subroutine Overload_Test_getValueBClass_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_BClass
  use Overload_Test
  use Overload_Test_impl
  ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass.use)
  implicit none
  type(Overload_Test_t) :: self
  ! in
  type(Overload_BClass_t) :: v
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass)
  call getValue(v, retval, exception)
! DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass)
end subroutine Overload_Test_getValueBClass_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
