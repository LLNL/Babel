! 
! File:          hooks_Basics_Impl.F90
! Symbol:        hooks.Basics-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for hooks.Basics
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "hooks.Basics" (version 1.0)
! 


#include "hooks_Basics_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert-Code-Here {_miscellaneous_code_start} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine hooks_Basics__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics._ctor.use)
  ! Insert-Code-Here {hooks.Basics._ctor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(hooks.Basics._ctor.use)
  implicit none
  type(hooks_Basics_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics._ctor)
  type(hooks_Basics_wrap) :: dp
  allocate(dp%d_private_data)
  dp%d_private_data%num_prehooks = 0
  dp%d_private_data%num_posthooks = 0
  call hooks_Basics__set_data_m(self, dp)
! DO-NOT-DELETE splicer.end(hooks.Basics._ctor)
end subroutine hooks_Basics__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine hooks_Basics__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics._ctor2.use)
  ! Insert-Code-Here {hooks.Basics._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(hooks.Basics._ctor2.use)
  implicit none
  type(hooks_Basics_t) :: self
  ! in
  type(hooks_Basics_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics._ctor2)
! DO-NOT-DELETE splicer.end(hooks.Basics._ctor2)
end subroutine hooks_Basics__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine hooks_Basics__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics._dtor.use)
  ! Insert-Code-Here {hooks.Basics._dtor.use} (use statements)
  ! DO-NOT-DELETE splicer.end(hooks.Basics._dtor.use)
  implicit none
  type(hooks_Basics_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics._dtor)
  type(hooks_Basics_wrap) :: dp
  call hooks_Basics__get_data_m(self, dp)
  deallocate(dp%d_private_data)
! DO-NOT-DELETE splicer.end(hooks.Basics._dtor)
end subroutine hooks_Basics__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine hooks_Basics__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics._load.use)
  ! Insert-Code-Here {hooks.Basics._load.use} (use statements)
  ! DO-NOT-DELETE splicer.end(hooks.Basics._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics._load)
! DO-NOT-DELETE splicer.end(hooks.Basics._load)
end subroutine hooks_Basics__load_mi


! 
! Method:  aStaticMeth_pre[]
! Basic illustration of hooks for static methods.
! 

recursive subroutine hooks_Basics_aStaticMeth_pre_mi(i, io, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_pre.use)
  use synch_RegOut
  ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_pre.use)
  implicit none
  integer (kind=sidl_int) :: i
  ! in
  integer (kind=sidl_int) :: io
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_pre)
  num_prehooks_static = num_prehooks_static + 1
! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_pre)
end subroutine hooks_Basics_aStaticMeth_pre_mi


! 
! Method:  aStaticMeth[]
! Basic illustration of hooks for static methods.
! 

recursive subroutine hooks_Basics_aStaticMeth_mi(i, o, io, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth.use)
  use sidl
  !use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth.use)
  implicit none
  integer (kind=sidl_int) :: i
  ! in
  integer (kind=sidl_int) :: o
  ! out
  integer (kind=sidl_int) :: io
  ! inout
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth)
  o = num_prehooks_static
  io = num_posthooks_static
  retval = 1
  return
! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth)
end subroutine hooks_Basics_aStaticMeth_mi


! 
! Method:  aStaticMeth_post[]
! Basic illustration of hooks for static methods.
! 

recursive subroutine B_aStaticMeth_postywgp49zzy2_mi(i, o, io, retval,         &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_post.use)
  use synch_RegOut
  use synch_ResultType
  ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_post.use)
  implicit none
  integer (kind=sidl_int) :: i
  ! in
  integer (kind=sidl_int) :: o
  ! in
  integer (kind=sidl_int) :: io
  ! in
  integer (kind=sidl_int) :: retval
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_post)
  num_posthooks_static = num_posthooks_static + 1
! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_post)
end subroutine B_aStaticMeth_postywgp49zzy2_mi


! 
! Method:  aNonStaticMeth_pre[]
! Basic illustration of hooks for static methods.
! 

recursive subroutine aNonStaticMeth_prei9ypcj0wgu_mi(self, i, io, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_pre.use)
  use synch_RegOut
  ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_pre.use)
  implicit none
  type(hooks_Basics_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  integer (kind=sidl_int) :: io
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_pre)
  type(hooks_Basics_wrap) :: dp
  call hooks_Basics__get_data_m(self, dp)
  dp%d_private_data%num_prehooks = dp%d_private_data%num_prehooks + 1
! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_pre)
end subroutine aNonStaticMeth_prei9ypcj0wgu_mi


! 
! Method:  aNonStaticMeth[]
! Basic illustration of hooks for static methods.
! 

recursive subroutine hooks_Basics_aNonStaticMeth_mi(self, i, o, io, retval,    &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth.use)
  !use sidl
  !use synch_RegOut
  !use sidl_BaseInterface
  ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth.use)
  implicit none
  type(hooks_Basics_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  integer (kind=sidl_int) :: o
  ! out
  integer (kind=sidl_int) :: io
  ! inout
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth)
  type(hooks_Basics_wrap) :: dp
  call hooks_Basics__get_data_m(self, dp)
  o = dp%d_private_data%num_prehooks
  io = dp%d_private_data%num_posthooks
  retval = 1
  return
! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth)
end subroutine hooks_Basics_aNonStaticMeth_mi


! 
! Method:  aNonStaticMeth_post[]
! Basic illustration of hooks for static methods.
! 

recursive subroutine aNonStaticMeth_posw4ur7jgurj_mi(self, i, o, io, retval,   &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  use hooks_Basics_impl
  ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_post.use)
  use synch_RegOut
  use synch_ResultType
  ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_post.use)
  implicit none
  type(hooks_Basics_t) :: self
  ! in
  integer (kind=sidl_int) :: i
  ! in
  integer (kind=sidl_int) :: o
  ! in
  integer (kind=sidl_int) :: io
  ! in
  integer (kind=sidl_int) :: retval
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_post)
  type(hooks_Basics_wrap) :: dp
  call hooks_Basics__get_data_m(self, dp)
  dp%d_private_data%num_posthooks = dp%d_private_data%num_posthooks + 1
! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_post)
end subroutine aNonStaticMeth_posw4ur7jgurj_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert-Code-Here {_miscellaneous_code_end} (extra code)
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
