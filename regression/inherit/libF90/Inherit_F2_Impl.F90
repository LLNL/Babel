! 
! File:          Inherit_F2_Impl.F90
! Symbol:        Inherit.F2-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.F2
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.F2" (version 1.1)
! 


#include "Inherit_C_fAbbrev.h"
#include "Inherit_F2_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_A_fAbbrev.h"
#include "Inherit_B_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Inherit_F2__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2._ctor.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.F2._ctor)
end subroutine Inherit_F2__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Inherit_F2__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2._ctor2.use)
  ! Insert-Code-Here {Inherit.F2._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.F2._ctor2.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  type(Inherit_F2_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2._ctor2)
! Insert-Code-Here {Inherit.F2._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Inherit.F2._ctor2)
end subroutine Inherit_F2__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Inherit_F2__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2._dtor.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.F2._dtor)
end subroutine Inherit_F2__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Inherit_F2__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.F2._load)
end subroutine Inherit_F2__load_mi


! 
! Method:  c[]
! 

recursive subroutine Inherit_F2_c_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2.c.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2.c.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2.c)
  retval = 'F2.c'
! DO-NOT-DELETE splicer.end(Inherit.F2.c)
end subroutine Inherit_F2_c_mi


! 
! Method:  a[]
! 

recursive subroutine Inherit_F2_a_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2.a.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2.a.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2.a)
  retval = 'F2.a'
! DO-NOT-DELETE splicer.end(Inherit.F2.a)
end subroutine Inherit_F2_a_mi


! 
! Method:  b[]
! 

recursive subroutine Inherit_F2_b_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2.b.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2.b.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2.b)
  retval = 'F2.b'
! DO-NOT-DELETE splicer.end(Inherit.F2.b)
end subroutine Inherit_F2_b_mi


! 
! Method:  f[]
! 

recursive subroutine Inherit_F2_f_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_F2
  use Inherit_F2_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.F2.f.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.F2.f.use)
  implicit none
  type(Inherit_F2_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.F2.f)
  retval = 'F2.f'
! DO-NOT-DELETE splicer.end(Inherit.F2.f)
end subroutine Inherit_F2_f_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
