! 
! File:          Overload_ParentTest_Impl.F90
! Symbol:        Overload.ParentTest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.ParentTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.ParentTest" (version 1.0)
! 
! This class is used as the work-horse, returning the value passed
! in.
! 


#include "Overload_ParentTest_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Overload_ParentTest__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor)
end subroutine Overload_ParentTest__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Overload_ParentTest__ctor2_mi(self, private_data,         &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor2.use)
  ! Insert-Code-Here {Overload.ParentTest._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor2.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  type(Overload_ParentTest_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor2)
! Insert-Code-Here {Overload.ParentTest._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor2)
end subroutine Overload_ParentTest__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Overload_ParentTest__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor)
end subroutine Overload_ParentTest__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Overload_ParentTest__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.ParentTest._load)
end subroutine Overload_ParentTest__load_mi


! 
! Method:  getValue[]
! 

recursive subroutine Overload_ParentTest_getValue_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue)
  retval = 1
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue)
end subroutine Overload_ParentTest_getValue_mi


! 
! Method:  getValue[Int]
! 

recursive subroutine Parent_getValueInt03srw0x4an_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  integer (kind=sidl_int) :: v
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt)
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt)
end subroutine Parent_getValueInt03srw0x4an_mi


! 
! Method:  getValue[Bool]
! 

recursive subroutine Paren_getValueBooliplci5czp1_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  logical :: v
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool)
! Insert the implementation here...
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool)
end subroutine Paren_getValueBooliplci5czp1_mi


! 
! Method:  getValue[Double]
! 

recursive subroutine Par_getValueDouble0z0lefjp7z_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  real (kind=sidl_double) :: v
  ! in
  real (kind=sidl_double) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble)
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble)
end subroutine Par_getValueDouble0z0lefjp7z_mi


! 
! Method:  getValue[Dcomplex]
! 

recursive subroutine P_getValueDcomplexbui0hkubvj_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  complex (kind=sidl_dcomplex) :: v
  ! in
  complex (kind=sidl_dcomplex) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex)
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex)
end subroutine P_getValueDcomplexbui0hkubvj_mi


! 
! Method:  getValue[Float]
! 

recursive subroutine Pare_getValueFloatm9g6j384ap_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  real (kind=sidl_float) :: v
  ! in
  real (kind=sidl_float) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat)
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat)
end subroutine Pare_getValueFloatm9g6j384ap_mi


! 
! Method:  getValue[Fcomplex]
! 

recursive subroutine P_getValueFcomplex8ekcizexpt_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  complex (kind=sidl_fcomplex) :: v
  ! in
  complex (kind=sidl_fcomplex) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex)
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex)
end subroutine P_getValueFcomplex8ekcizexpt_mi


! 
! Method:  getValue[String]
! 

recursive subroutine Par_getValueString17no6_u9pi_mi(self, v, retval,          &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  use Overload_ParentTest_impl
  ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString.use)
  implicit none
  type(Overload_ParentTest_t) :: self
  ! in
  character (len=*) :: v
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString)
  retval = v
! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString)
end subroutine Par_getValueString17no6_u9pi_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
