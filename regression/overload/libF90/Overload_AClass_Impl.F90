! 
! File:          Overload_AClass_Impl.F90
! Symbol:        Overload.AClass-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.AClass
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.AClass" (version 1.0)
! 
! This class is passed into the overloaded method as an example
! of passing classes.
! 


#include "Overload_AClass_fAbbrev.h"
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

recursive subroutine Overload_AClass__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_AClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AClass._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AClass._ctor.use)
  implicit none
  type(Overload_AClass_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AClass._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.AClass._ctor)
end subroutine Overload_AClass__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Overload_AClass__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_AClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AClass._ctor2.use)
  ! Insert-Code-Here {Overload.AClass._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Overload.AClass._ctor2.use)
  implicit none
  type(Overload_AClass_t) :: self
  ! in
  type(Overload_AClass_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AClass._ctor2)
! Insert-Code-Here {Overload.AClass._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Overload.AClass._ctor2)
end subroutine Overload_AClass__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Overload_AClass__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_AClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AClass._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AClass._dtor.use)
  implicit none
  type(Overload_AClass_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AClass._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.AClass._dtor)
end subroutine Overload_AClass__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Overload_AClass__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_AClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AClass._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AClass._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AClass._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.AClass._load)
end subroutine Overload_AClass__load_mi


! 
! Method:  getValue[]
! 

recursive subroutine Overload_AClass_getValue_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_AClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AClass.getValue.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AClass.getValue.use)
  implicit none
  type(Overload_AClass_t) :: self
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AClass.getValue)
  retval = 2
! DO-NOT-DELETE splicer.end(Overload.AClass.getValue)
end subroutine Overload_AClass_getValue_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
