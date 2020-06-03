! 
! File:          Exceptions_FibException_Impl.F90
! Symbol:        Exceptions.FibException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.FibException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.FibException" (version 1.0)
! 
! This exception is a base class for the Fibonacci Exceptions that are
! thrown if the value is too large or the recursion depth is too deep.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
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

recursive subroutine FibException__ctorp9ljx7kpwx_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_FibException
  use Exceptions_FibException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor.use)
  implicit none
  type(Exceptions_FibException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor)
end subroutine FibException__ctorp9ljx7kpwx_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine FibExceptio__ctor26777awr2xp_mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_FibException
  use Exceptions_FibException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor2.use)
  ! Insert-Code-Here {Exceptions.FibException._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor2.use)
  implicit none
  type(Exceptions_FibException_t) :: self
  ! in
  type(Exceptions_FibException_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor2)
! Insert-Code-Here {Exceptions.FibException._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor2)
end subroutine FibExceptio__ctor26777awr2xp_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine FibException__dtordl1_8jib6d_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_FibException
  use Exceptions_FibException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.FibException._dtor.use)
  implicit none
  type(Exceptions_FibException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.FibException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.FibException._dtor)
end subroutine FibException__dtordl1_8jib6d_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine FibException__loada3a7xos9rt_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_FibException
  use Exceptions_FibException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.FibException._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.FibException._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.FibException._load)
end subroutine FibException__loada3a7xos9rt_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
