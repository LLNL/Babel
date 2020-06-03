! 
! File:          Inherit_J_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Inherit_J_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Inherit_J
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Inherit_J_t) :: Inherit_J_impl_t
    ! DO-NOT-DELETE splicer.begin(Inherit.J.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Inherit.J.private_data)
  end type Inherit_J_impl_t

contains

  type(Inherit_E2_t) function super(self)
    use Inherit_E2_type
    type(Inherit_J_impl_t), intent(in) :: self
    external Inherit_J_super
    call Inherit_J_super(self, super)
  end function super

  subroutine wrap(obj, self, exception)
    type(Inherit_J_impl_t), target :: obj
    type(Inherit_J_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Inherit_J_wrap
    call Inherit_J_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.J._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.J._ctor.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.J._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.J._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.J._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.J._dtor.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.J._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.J._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Inherit.J._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.J._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Inherit.J._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Inherit.J._load)
  end subroutine load_impl


  ! 
  ! Method:  j[]
  ! 

  function j_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.J.j.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.J.j.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.J.j)
    ! Insert the implementation here...
    retval = 'J.j'
    ! DO-NOT-DELETE splicer.end(Inherit.J.j)
  end function j_impl


  ! 
  ! Method:  e[]
  ! 

  function e_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.J.e.use)
    use sidl_BaseInterface
    use Inherit_E2
    ! DO-NOT-DELETE splicer.end(Inherit.J.e.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.J.e)
    ! Insert the implementation here...
    character*5 temp
    type (sidl_BaseInterface_t) :: throwaway_exception
    temp = e(super(self), throwaway_exception)
    retval = 'J.'//temp 
    ! DO-NOT-DELETE splicer.end(Inherit.J.e)
  end function e_impl


  ! 
  ! Method:  c[]
  ! 

  function c_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.J.c.use)
    use sidl_BaseInterface
    use Inherit_E2
    ! DO-NOT-DELETE splicer.end(Inherit.J.c.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.J.c)
    ! Insert the implementation here...
    character*5 temp
    type(sidl_BaseInterface_t) :: throwaway_exception
    temp = c(super(self), throwaway_exception)
    retval = 'J.'//temp 
    ! DO-NOT-DELETE splicer.end(Inherit.J.c)
  end function c_impl


  ! 
  ! Method:  a[]
  ! 

  function a_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.J.a.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.J.a.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.J.a)
    ! Insert the implementation here...
    retval = 'J.a'
    ! DO-NOT-DELETE splicer.end(Inherit.J.a)
  end function a_impl


  ! 
  ! Method:  b[]
  ! 

  function b_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Inherit.J.b.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Inherit.J.b.use)
    implicit none
    !  in Inherit.J self
    type(Inherit_J_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Inherit.J.b)
    ! Insert the implementation here...
    retval = 'J.b'
    ! DO-NOT-DELETE splicer.end(Inherit.J.b)
  end function b_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Inherit_J_Impl
