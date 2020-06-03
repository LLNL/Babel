! 
! File:          Overload_AnException_Impl.F90
! Symbol:        Overload.AnException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.AnException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.AnException" (version 1.0)
! 
! This exception is passed into the overloaded method as an example
! of passing classes.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Overload_AnException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Overload_AnException__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AnException
  use Overload_AnException_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AnException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AnException._ctor.use)
  implicit none
  type(Overload_AnException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AnException._ctor)
  call setNote(self, 'AnException', exception)
! DO-NOT-DELETE splicer.end(Overload.AnException._ctor)
end subroutine Overload_AnException__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Overload_AnException__ctor2_mi(self, private_data,        &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AnException
  use Overload_AnException_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AnException._ctor2.use)
  ! Insert-Code-Here {Overload.AnException._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Overload.AnException._ctor2.use)
  implicit none
  type(Overload_AnException_t) :: self
  ! in
  type(Overload_AnException_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AnException._ctor2)
! Insert-Code-Here {Overload.AnException._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Overload.AnException._ctor2)
end subroutine Overload_AnException__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Overload_AnException__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AnException
  use Overload_AnException_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AnException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AnException._dtor.use)
  implicit none
  type(Overload_AnException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AnException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.AnException._dtor)
end subroutine Overload_AnException__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Overload_AnException__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AnException
  use Overload_AnException_impl
  ! DO-NOT-DELETE splicer.begin(Overload.AnException._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Overload.AnException._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Overload.AnException._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Overload.AnException._load)
end subroutine Overload_AnException__load_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
