! 
! File:          Inherit_E_Impl.F90
! Symbol:        Inherit.E-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.E
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.E" (version 1.1)
! 


#include "Inherit_E_fAbbrev.h"
#include "Inherit_C_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Inherit_E__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E
  use Inherit_E_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.E._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.E._ctor.use)
  implicit none
  type(Inherit_E_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.E._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.E._ctor)
end subroutine Inherit_E__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Inherit_E__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E
  use Inherit_E_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.E._ctor2.use)
  ! Insert-Code-Here {Inherit.E._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.E._ctor2.use)
  implicit none
  type(Inherit_E_t) :: self
  ! in
  type(Inherit_E_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.E._ctor2)
! Insert-Code-Here {Inherit.E._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Inherit.E._ctor2)
end subroutine Inherit_E__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Inherit_E__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E
  use Inherit_E_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.E._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.E._dtor.use)
  implicit none
  type(Inherit_E_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.E._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.E._dtor)
end subroutine Inherit_E__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Inherit_E__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E
  use Inherit_E_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.E._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.E._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.E._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.E._load)
end subroutine Inherit_E__load_mi


! 
! Method:  e[]
! 

recursive subroutine Inherit_E_e_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_E
  use Inherit_E_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.E.e.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.E.e.use)
  implicit none
  type(Inherit_E_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.E.e)
  retval = 'E.e'
! DO-NOT-DELETE splicer.end(Inherit.E.e)
end subroutine Inherit_E_e_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
