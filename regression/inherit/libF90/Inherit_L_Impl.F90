! 
! File:          Inherit_L_Impl.F90
! Symbol:        Inherit.L-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.L
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.L" (version 1.1)
! 


#include "Inherit_A2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Inherit_L_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Inherit_L__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L._ctor.use)
  ! Insert-Code-Here {Inherit.L._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L._ctor.use)
  implicit none
  type(Inherit_L_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L._ctor)
! Insert-Code-Here {Inherit.L._ctor} (_ctor method)
! DO-NOT-DELETE splicer.end(Inherit.L._ctor)
end subroutine Inherit_L__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Inherit_L__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L._ctor2.use)
  ! Insert-Code-Here {Inherit.L._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L._ctor2.use)
  implicit none
  type(Inherit_L_t) :: self
  ! in
  type(Inherit_L_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L._ctor2)
! Insert-Code-Here {Inherit.L._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Inherit.L._ctor2)
end subroutine Inherit_L__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Inherit_L__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L._dtor.use)
  ! Insert-Code-Here {Inherit.L._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L._dtor.use)
  implicit none
  type(Inherit_L_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L._dtor)
! Insert-Code-Here {Inherit.L._dtor} (_dtor method)
! DO-NOT-DELETE splicer.end(Inherit.L._dtor)
end subroutine Inherit_L__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Inherit_L__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L._load.use)
  ! Insert-Code-Here {Inherit.L._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L._load)
! Insert-Code-Here {Inherit.L._load} (_load method)
! DO-NOT-DELETE splicer.end(Inherit.L._load)
end subroutine Inherit_L__load_mi


! 
! Method:  a[a]
! 

recursive subroutine Inherit_L_aa_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L.aa.use)
  ! Insert-Code-Here {Inherit.L.aa.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L.aa.use)
  implicit none
  type(Inherit_L_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L.aa)
  retval = 'L.a'
! DO-NOT-DELETE splicer.end(Inherit.L.aa)
end subroutine Inherit_L_aa_mi


! 
! Method:  a[2]
! 

recursive subroutine Inherit_L_a2_mi(self, i, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L.a2.use)
  ! Insert-Code-Here {Inherit.L.a2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L.a2.use)
  implicit none
  type(Inherit_L_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L.a2)
  retval = 'L.a2'
! DO-NOT-DELETE splicer.end(Inherit.L.a2)
end subroutine Inherit_L_a2_mi


! 
! Method:  l[]
! 

recursive subroutine Inherit_L_l_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_L
  use Inherit_L_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.L.l.use)
  ! Insert-Code-Here {Inherit.L.l.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.L.l.use)
  implicit none
  type(Inherit_L_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.L.l)
  retval = 'L.l'
! DO-NOT-DELETE splicer.end(Inherit.L.l)
end subroutine Inherit_L_l_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
