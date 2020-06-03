! 
! File:          Overload_BClass_Impl.F90
! Symbol:        Overload.BClass-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.BClass
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.BClass" (version 1.0)
! 
! This class is passed into the overloaded method as another example
! of passing classes.
! 


#include "Overload_AClass_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Overload_BClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Overload_BClass__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_BClass
  use Overload_BClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.BClass._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.BClass._ctor.use)
  implicit none
  type(Overload_BClass_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.BClass._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.BClass._ctor)
end subroutine Overload_BClass__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Overload_BClass__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_BClass
  use Overload_BClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.BClass._ctor2.use)
  ! Insert-Code-Here {Overload.BClass._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Overload.BClass._ctor2.use)
  implicit none
  type(Overload_BClass_t) :: self
  ! in
  type(Overload_BClass_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.BClass._ctor2)
! Insert-Code-Here {Overload.BClass._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Overload.BClass._ctor2)
end subroutine Overload_BClass__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Overload_BClass__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_BClass
  use Overload_BClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.BClass._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.BClass._dtor.use)
  implicit none
  type(Overload_BClass_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.BClass._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.BClass._dtor)
end subroutine Overload_BClass__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Overload_BClass__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_BClass
  use Overload_BClass_impl
  ! DO-NOT-DELETE splicer.begin(Overload.BClass._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.BClass._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.BClass._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.BClass._load)
end subroutine Overload_BClass__load_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
