! 
! File:          Inherit_J_Impl.F90
! Symbol:        Inherit.J-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Inherit.J
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Inherit.J" (version 1.1)
! 


#include "Inherit_C_fAbbrev.h"
#include "Inherit_J_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_E2_fAbbrev.h"
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

recursive subroutine Inherit_J__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J._ctor.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.J._ctor)
end subroutine Inherit_J__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Inherit_J__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J._ctor2.use)
  ! Insert-Code-Here {Inherit.J._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Inherit.J._ctor2.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  type(Inherit_J_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J._ctor2)
! Insert-Code-Here {Inherit.J._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Inherit.J._ctor2)
end subroutine Inherit_J__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Inherit_J__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J._dtor.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.J._dtor)
end subroutine Inherit_J__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Inherit_J__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Inherit.J._load)
end subroutine Inherit_J__load_mi


! 
! Method:  j[]
! 

recursive subroutine Inherit_J_j_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J.j.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J.j.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J.j)
! Insert the implementation here...
  retval = 'J.j'
! DO-NOT-DELETE splicer.end(Inherit.J.j)
end subroutine Inherit_J_j_mi


! 
! Method:  e[]
! 

recursive subroutine Inherit_J_e_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J.e.use)
  use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(Inherit.J.e.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J.e)
! Insert the implementation here...
  character*5 temp
  type (sidl_BaseInterface_t) :: throwaway_exception
  call super_e(self, temp, throwaway_exception)
  retval = 'J.'//temp 
! DO-NOT-DELETE splicer.end(Inherit.J.e)
end subroutine Inherit_J_e_mi


! 
! Method:  c[]
! 

recursive subroutine Inherit_J_c_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J.c.use)
  use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(Inherit.J.c.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J.c)
! Insert the implementation here...
  character*5 temp
  type(sidl_BaseInterface_t) :: throwaway_exception
  call super_c(self, temp, throwaway_exception)
  retval = 'J.'//temp 
! DO-NOT-DELETE splicer.end(Inherit.J.c)
end subroutine Inherit_J_c_mi


! 
! Method:  a[]
! 

recursive subroutine Inherit_J_a_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J.a.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J.a.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J.a)
! Insert the implementation here...
  retval = 'J.a'
! DO-NOT-DELETE splicer.end(Inherit.J.a)
end subroutine Inherit_J_a_mi


! 
! Method:  b[]
! 

recursive subroutine Inherit_J_b_mi(self, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  use Inherit_J_impl
  ! DO-NOT-DELETE splicer.begin(Inherit.J.b.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.J.b.use)
  implicit none
  type(Inherit_J_t) :: self
  ! in
  character (len=*) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Inherit.J.b)
! Insert the implementation here...
  retval = 'J.b'
! DO-NOT-DELETE splicer.end(Inherit.J.b)
end subroutine Inherit_J_b_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
