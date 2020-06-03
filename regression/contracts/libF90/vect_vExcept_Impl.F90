! 
! File:          vect_vExcept_Impl.F90
! Symbol:        vect.vExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for vect.vExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "vect.vExcept" (version 1.0)
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "vect_vExcept_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! insert code here (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine vect_vExcept__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use vect_vExcept
  use vect_vExcept_impl
  ! DO-NOT-DELETE splicer.begin(vect.vExcept._ctor.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(vect.vExcept._ctor.use)
  implicit none
  type(vect_vExcept_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(vect.vExcept._ctor)
! insert code here (_ctor method)
! 
! This method has not been implemented
! 

! DO-NOT-DELETE splicer.end(vect.vExcept._ctor)
end subroutine vect_vExcept__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine vect_vExcept__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use vect_vExcept
  use vect_vExcept_impl
  ! DO-NOT-DELETE splicer.begin(vect.vExcept._ctor2.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(vect.vExcept._ctor2.use)
  implicit none
  type(vect_vExcept_t) :: self
  ! in
  type(vect_vExcept_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(vect.vExcept._ctor2)
! insert code here (_ctor2 method)
! 
! This method has not been implemented
! 

! DO-NOT-DELETE splicer.end(vect.vExcept._ctor2)
end subroutine vect_vExcept__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine vect_vExcept__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use vect_vExcept
  use vect_vExcept_impl
  ! DO-NOT-DELETE splicer.begin(vect.vExcept._dtor.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(vect.vExcept._dtor.use)
  implicit none
  type(vect_vExcept_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(vect.vExcept._dtor)
! insert code here (_dtor method)
! 
! This method has not been implemented
! 

! DO-NOT-DELETE splicer.end(vect.vExcept._dtor)
end subroutine vect_vExcept__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine vect_vExcept__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use vect_vExcept
  use vect_vExcept_impl
  ! DO-NOT-DELETE splicer.begin(vect.vExcept._load.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(vect.vExcept._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(vect.vExcept._load)
! insert code here (_load method)
! 
! This method has not been implemented
! 

! DO-NOT-DELETE splicer.end(vect.vExcept._load)
end subroutine vect_vExcept__load_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! insert code here (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
