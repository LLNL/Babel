! 
! File:          Inherit_K_Impl.F90
! Symbol:        Inherit.K-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.K
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.K" (version 1.1)
! 


#include "Inherit_H_fAbbrev.h"
#include "Inherit_A2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_K_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Inherit_K__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K._ctor.use)
  ! Insert-Code-Here {Inherit.K._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K._ctor.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K._ctor)
! Insert-Code-Here {Inherit.K._ctor} (_ctor method)
! DO-NOT-DELETE splicer.end(Inherit.K._ctor)
end subroutine Inherit_K__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Inherit_K__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K._ctor2.use)
  ! Insert-Code-Here {Inherit.K._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K._ctor2.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  type(Inherit_K_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K._ctor2)
! Insert-Code-Here {Inherit.K._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Inherit.K._ctor2)
end subroutine Inherit_K__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Inherit_K__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K._dtor.use)
  ! Insert-Code-Here {Inherit.K._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K._dtor.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K._dtor)
! Insert-Code-Here {Inherit.K._dtor} (_dtor method)
! DO-NOT-DELETE splicer.end(Inherit.K._dtor)
end subroutine Inherit_K__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Inherit_K__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K._load.use)
  ! Insert-Code-Here {Inherit.K._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K._load)
! Insert-Code-Here {Inherit.K._load} (_load method)
! DO-NOT-DELETE splicer.end(Inherit.K._load)
end subroutine Inherit_K__load_mi


! 
! Method:  a[2]
! 

recursive subroutine Inherit_K_a2_mi(self, i, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K.a2.use)
  ! Insert-Code-Here {Inherit.K.a2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K.a2.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K.a2)
  retval = 'K.a2'
! DO-NOT-DELETE splicer.end(Inherit.K.a2)
end subroutine Inherit_K_a2_mi


! 
! Method:  a[]
! 

recursive subroutine Inherit_K_a_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K.a.use)
  ! Insert-Code-Here {Inherit.K.a.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K.a.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K.a)
  retval = 'K.a'
! DO-NOT-DELETE splicer.end(Inherit.K.a)
end subroutine Inherit_K_a_mi


! 
! Method:  h[]
! 

recursive subroutine Inherit_K_h_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K.h.use)
  ! Insert-Code-Here {Inherit.K.h.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K.h.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K.h)
  retval = 'K.h'
! DO-NOT-DELETE splicer.end(Inherit.K.h)
end subroutine Inherit_K_h_mi


! 
! Method:  k[]
! 

recursive subroutine Inherit_K_k_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_K
  use Inherit_K_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.K.k.use)
  ! Insert-Code-Here {Inherit.K.k.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.K.k.use)
  implicit none
  type(Inherit_K_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.K.k)
  retval = 'K.k'
! DO-NOT-DELETE splicer.end(Inherit.K.k)
end subroutine Inherit_K_k_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
