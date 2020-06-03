! 
! File:          Inherit_H_Impl.F90
! Symbol:        Inherit.H-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.H
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.H" (version 1.1)
! 


#include "Inherit_H_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Inherit_H__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_H
  use Inherit_H_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.H._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.H._ctor.use)
  implicit none
  type(Inherit_H_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.H._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.H._ctor)
end subroutine Inherit_H__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Inherit_H__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_H
  use Inherit_H_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.H._ctor2.use)
  ! Insert-Code-Here {Inherit.H._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.H._ctor2.use)
  implicit none
  type(Inherit_H_t) :: self
  ! in
  type(Inherit_H_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.H._ctor2)
! Insert-Code-Here {Inherit.H._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Inherit.H._ctor2)
end subroutine Inherit_H__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Inherit_H__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_H
  use Inherit_H_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.H._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.H._dtor.use)
  implicit none
  type(Inherit_H_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.H._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.H._dtor)
end subroutine Inherit_H__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Inherit_H__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_H
  use Inherit_H_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.H._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.H._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.H._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.H._load)
end subroutine Inherit_H__load_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
