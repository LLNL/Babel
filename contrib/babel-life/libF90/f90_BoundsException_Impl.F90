! 
! File:          f90_BoundsException_Impl.F90
! Symbol:        f90.BoundsException-v2.0
! Symbol Type:   class
! Babel Version: 1.1.1
! Description:   Server-side implementation for f90.BoundsException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "f90.BoundsException" (version 2.0)
! 


#include "sidl_NotImplementedException_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "f90_BoundsException_fAbbrev.h"
#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "conway_BoundsException_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)




! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine f90_BoundsException__ctor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_BoundsException
  use f90_BoundsException_impl
  ! DO-NOT-DELETE splicer.begin(f90.BoundsException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.BoundsException._ctor.use)
  implicit none
  ! in
  type(f90_BoundsException_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.BoundsException._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(f90.BoundsException._ctor)
end subroutine f90_BoundsException__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine f90_BoundsException__ctor2_mi(self, private_data,         &
  exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_BoundsException
  use f90_BoundsException_impl
  ! DO-NOT-DELETE splicer.begin(f90.BoundsException._ctor2.use)
  ! Insert-Code-Here {f90.BoundsException._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.BoundsException._ctor2.use)
  implicit none
  ! in
  type(f90_BoundsException_t) :: self
  type(f90_BoundsException_wrap) :: private_data
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.BoundsException._ctor2)
! Insert-Code-Here {f90.BoundsException._ctor2} (_ctor2 method)
! 
! This method has not been implemented
! 

  type(sidl_BaseInterface_t) :: throwaway
  type(sidl_NotImplementedException_t) :: notImpl
  call new(notImpl, exception)
  call setNote(notImpl, 'Not Implemented', exception)
  call cast(notImpl, exception,throwaway)
  call deleteRef(notImpl,throwaway)
  return
! DO-NOT-DELETE splicer.end(f90.BoundsException._ctor2)
end subroutine f90_BoundsException__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine f90_BoundsException__dtor_mi(self, exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_BoundsException
  use f90_BoundsException_impl
  ! DO-NOT-DELETE splicer.begin(f90.BoundsException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(f90.BoundsException._dtor.use)
  implicit none
  ! in
  type(f90_BoundsException_t) :: self
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.BoundsException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(f90.BoundsException._dtor)
end subroutine f90_BoundsException__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine f90_BoundsException__load_mi(exception)
  use sidl
  use sidl_NotImplementedException
  use sidl_BaseInterface
  use sidl_RuntimeException
  use f90_BoundsException
  use f90_BoundsException_impl
  ! DO-NOT-DELETE splicer.begin(f90.BoundsException._load.use)
  ! Insert-Code-Here {f90.BoundsException._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(f90.BoundsException._load.use)
  implicit none
  ! out
  type(sidl_BaseInterface_t) :: exception

! DO-NOT-DELETE splicer.begin(f90.BoundsException._load)
! Insert-Code-Here {f90.BoundsException._load} (_load method)
! DO-NOT-DELETE splicer.end(f90.BoundsException._load)
end subroutine f90_BoundsException__load_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
