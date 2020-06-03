! 
! File:          hooks_Basics_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module hooks_Basics_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use hooks_Basics
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 
  integer :: num_prehooks_static, num_posthooks_static
  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(hooks_Basics_t) :: hooks_Basics_impl_t
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.private_data)
     integer :: num_prehooks, num_posthooks
    ! DO-NOT-DELETE splicer.end(hooks.Basics.private_data)
  end type hooks_Basics_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(hooks_Basics_impl_t), target :: obj
    type(hooks_Basics_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external hooks_Basics_wrap
    call hooks_Basics_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert-Code-Here {_miscellaneous_code_start} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics._ctor.use)
    ! Insert-Code-Here {hooks.Basics._ctor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(hooks.Basics._ctor.use)
    implicit none
    !  in hooks.Basics self
    type(hooks_Basics_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics._ctor)
    self%num_prehooks = 0
    self%num_posthooks = 0
    ! DO-NOT-DELETE splicer.end(hooks.Basics._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics._dtor.use)
    ! Insert-Code-Here {hooks.Basics._dtor.use} (use statements)
    ! DO-NOT-DELETE splicer.end(hooks.Basics._dtor.use)
    implicit none
    !  in hooks.Basics self
    type(hooks_Basics_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics._dtor)
    ! DO-NOT-DELETE splicer.end(hooks.Basics._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics._load.use)
    ! Insert-Code-Here {hooks.Basics._load.use} (use statements)
    ! DO-NOT-DELETE splicer.end(hooks.Basics._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics._load)
    ! DO-NOT-DELETE splicer.end(hooks.Basics._load)
  end subroutine load_impl


  ! 
  ! Method:  aStaticMeth_pre[]
  ! Basic illustration of hooks for static methods.
  ! 

  subroutine aStaticMeth_pre_impl(i, io, exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_pre.use)
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_pre.use)
    implicit none
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  in int io
    integer (kind=sidl_int), intent(in) :: io
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_pre)
    num_prehooks_static = num_prehooks_static + 1
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_pre)
  end subroutine aStaticMeth_pre_impl


  ! 
  ! Method:  aStaticMeth[]
  ! Basic illustration of hooks for static methods.
  ! 

  function aStaticMeth_impl(i, o, io, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth.use)
    use sidl
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth.use)
    implicit none
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  out int o
    integer (kind=sidl_int), intent(out) :: o
    !  inout int io
    integer (kind=sidl_int), intent(inout) :: io
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth)
    o = num_prehooks_static
    io = num_posthooks_static
    retval = 1
    return
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth)
  end function aStaticMeth_impl


  ! 
  ! Method:  aStaticMeth_post[]
  ! Basic illustration of hooks for static methods.
  ! 

  subroutine aStaticMeth_post_impl(i, o, io, retval, exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_post.use)
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_post.use)
    implicit none
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  in int o
    integer (kind=sidl_int), intent(in) :: o
    !  in int io
    integer (kind=sidl_int), intent(in) :: io
    !  in int retval
    integer (kind=sidl_int), intent(in) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aStaticMeth_post)
    num_posthooks_static = num_posthooks_static + 1
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aStaticMeth_post)
  end subroutine aStaticMeth_post_impl


  ! 
  ! Method:  aNonStaticMeth_pre[]
  ! Basic illustration of hooks for static methods.
  ! 

  subroutine aNonStaticMeth_pre_impl(self, i, io, exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_pre.use)
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_pre.use)
    implicit none
    !  in hooks.Basics self
    type(hooks_Basics_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  in int io
    integer (kind=sidl_int), intent(in) :: io
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_pre)
    self%num_prehooks = self%num_prehooks + 1
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_pre)
  end subroutine aNonStaticMeth_pre_impl


  ! 
  ! Method:  aNonStaticMeth[]
  ! Basic illustration of hooks for static methods.
  ! 

  function aNonStaticMeth_impl(self, i, o, io, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth.use)
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth.use)
    implicit none
    !  in hooks.Basics self
    type(hooks_Basics_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  out int o
    integer (kind=sidl_int), intent(out) :: o
    !  inout int io
    integer (kind=sidl_int), intent(inout) :: io
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth)
    o = self%num_prehooks
    io = self%num_posthooks
    retval = 1
    return
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth)
  end function aNonStaticMeth_impl


  ! 
  ! Method:  aNonStaticMeth_post[]
  ! Basic illustration of hooks for static methods.
  ! 

  subroutine aNonStaticMeth_post_impl(self, i, o, io, retval, exception)
    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_post.use)
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_post.use)
    implicit none
    !  in hooks.Basics self
    type(hooks_Basics_impl_t) :: self
    !  in int i
    integer (kind=sidl_int), intent(in) :: i
    !  in int o
    integer (kind=sidl_int), intent(in) :: o
    !  in int io
    integer (kind=sidl_int), intent(in) :: io
    !  in int retval
    integer (kind=sidl_int), intent(in) :: retval
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(hooks.Basics.aNonStaticMeth_post)
    self%num_posthooks = self%num_posthooks + 1
    ! DO-NOT-DELETE splicer.end(hooks.Basics.aNonStaticMeth_post)
  end subroutine aNonStaticMeth_post_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert-Code-Here {_miscellaneous_code_end} (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module hooks_Basics_Impl
