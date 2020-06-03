! 
! File:          knapsack_kExcept_Impl.F90
! Symbol:        knapsack.kExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 0)
! Description:   Server-side implementation for knapsack.kExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "knapsack.kExcept" (version 1.0)
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "knapsack_kExcept_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Nothing to do here
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine knapsack_kExcept__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_kExcept
  use knapsack_kExcept_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.kExcept._ctor.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.kExcept._ctor.use)
  implicit none
  type(knapsack_kExcept_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.kExcept._ctor)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.kExcept._ctor)
end subroutine knapsack_kExcept__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine knapsack_kExcept__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_kExcept
  use knapsack_kExcept_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.kExcept._ctor2.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.kExcept._ctor2.use)
  implicit none
  type(knapsack_kExcept_t) :: self
  ! in
  type(knapsack_kExcept_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.kExcept._ctor2)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.kExcept._ctor2)
end subroutine knapsack_kExcept__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine knapsack_kExcept__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_kExcept
  use knapsack_kExcept_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.kExcept._dtor.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.kExcept._dtor.use)
  implicit none
  type(knapsack_kExcept_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.kExcept._dtor)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.kExcept._dtor)
end subroutine knapsack_kExcept__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine knapsack_kExcept__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use knapsack_kExcept
  use knapsack_kExcept_impl
  ! DO-NOT-DELETE splicer.begin(knapsack.kExcept._load.use)
  ! Nothing to do here
  ! DO-NOT-DELETE splicer.end(knapsack.kExcept._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(knapsack.kExcept._load)
! Nothing to do here
! DO-NOT-DELETE splicer.end(knapsack.kExcept._load)
end subroutine knapsack_kExcept__load_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Nothing to do here
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
