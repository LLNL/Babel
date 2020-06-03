! 
! File:          Exceptions_NegativeValueException_Impl.F90
! Symbol:        Exceptions.NegativeValueException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.NegativeValueException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.NegativeValueException" (version 1.0)
! 
! This exception is thrown if the value for which the Fibonacci number
! is requested is negative.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_NegativeValueException_fAbbrev.h"
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

recursive subroutine NegativeValu__ctor6sicsqoam0_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_NegativeValueException
  use Exceptions_NegativeValueException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._ctor.use)
  implicit none
  type(Exceptions_NegativeValueException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._ctor)
end subroutine NegativeValu__ctor6sicsqoam0_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine NegativeVal__ctor26bv7x8m142_mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_NegativeValueException
  use Exceptions_NegativeValueException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._ctor2.use)
  ! Insert-Code-Here {Exceptions.NegativeValueException._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._ctor2.use)
  implicit none
  type(Exceptions_NegativeValueException_t) :: self
  ! in
  type(Exceptions_NegativeValueException_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._ctor2)
! Insert-Code-Here {Exceptions.NegativeValueException._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._ctor2)
end subroutine NegativeVal__ctor26bv7x8m142_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine NegativeValu__dtor21rvvxpqxq_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_NegativeValueException
  use Exceptions_NegativeValueException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._dtor.use)
  implicit none
  type(Exceptions_NegativeValueException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._dtor)
end subroutine NegativeValu__dtor21rvvxpqxq_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine NegativeValu__load4cr6zxe3ct_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_NegativeValueException
  use Exceptions_NegativeValueException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.NegativeValueException._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.NegativeValueException._load)
end subroutine NegativeValu__load4cr6zxe3ct_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
