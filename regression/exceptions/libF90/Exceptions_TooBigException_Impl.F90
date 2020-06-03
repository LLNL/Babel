! 
! File:          Exceptions_TooBigException_Impl.F90
! Symbol:        Exceptions.TooBigException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.TooBigException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.TooBigException" (version 1.0)
! 
! This exception is thrown if the Fibonacci number is too large.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
#include "Exceptions_TooBigException_fAbbrev.h"
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

recursive subroutine TooBigExcept__ctorazi27pu4rz_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooBigException
  use Exceptions_TooBigException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._ctor.use)
  implicit none
  type(Exceptions_TooBigException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._ctor)
end subroutine TooBigExcept__ctorazi27pu4rz_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine TooBigExcep__ctor2rr73lj2_wa_mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooBigException
  use Exceptions_TooBigException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._ctor2.use)
  ! Insert-Code-Here {Exceptions.TooBigException._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._ctor2.use)
  implicit none
  type(Exceptions_TooBigException_t) :: self
  ! in
  type(Exceptions_TooBigException_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._ctor2)
! Insert-Code-Here {Exceptions.TooBigException._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._ctor2)
end subroutine TooBigExcep__ctor2rr73lj2_wa_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine TooBigExcept__dtorp8ssl4qdgk_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooBigException
  use Exceptions_TooBigException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._dtor.use)
  implicit none
  type(Exceptions_TooBigException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._dtor)
end subroutine TooBigExcept__dtorp8ssl4qdgk_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine TooBigExcept__load4o_q0pt_n2_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooBigException
  use Exceptions_TooBigException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._load)
end subroutine TooBigExcept__load4o_q0pt_n2_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
