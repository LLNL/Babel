! 
! File:          Exceptions_TooDeepException_Impl.F90
! Symbol:        Exceptions.TooDeepException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.TooDeepException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.TooDeepException" (version 1.0)
! 
! This exception is thrown if the Fibonacci recursion is too deep.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
#include "Exceptions_TooDeepException_fAbbrev.h"
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

recursive subroutine TooDeepExcep__ctorvq_7l0guc8_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooDeepException
  use Exceptions_TooDeepException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor.use)
  implicit none
  type(Exceptions_TooDeepException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor)
end subroutine TooDeepExcep__ctorvq_7l0guc8_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine TooDeepExce__ctor2h6983tynhi_mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooDeepException
  use Exceptions_TooDeepException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor2.use)
  ! Insert-Code-Here {Exceptions.TooDeepException._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor2.use)
  implicit none
  type(Exceptions_TooDeepException_t) :: self
  ! in
  type(Exceptions_TooDeepException_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor2)
! Insert-Code-Here {Exceptions.TooDeepException._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor2)
end subroutine TooDeepExce__ctor2h6983tynhi_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine TooDeepExcep__dtorawstc3_c45_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooDeepException
  use Exceptions_TooDeepException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._dtor.use)
  implicit none
  type(Exceptions_TooDeepException_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._dtor)
end subroutine TooDeepExcep__dtorawstc3_c45_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine TooDeepExcep__loadwufqj9owx2_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooDeepException
  use Exceptions_TooDeepException_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._load)
end subroutine TooDeepExcep__loadwufqj9owx2_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
