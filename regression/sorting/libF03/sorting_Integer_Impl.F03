! 
! File:          sorting_Integer_Impl.F03
! Symbol:        sorting.Integer-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.Integer
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.Integer" (version 0.1)
! 
! An object to hold a simple integer.
! 


#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sorting_Integer_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module sorting_Integer_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_Integer
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(sorting_Integer_t) :: sorting_Integer_impl_t
    ! DO-NOT-DELETE splicer.begin(sorting.Integer.private_data)
    ! insert code here (private data members)
    integer(kind=sidl_int) :: d_value
    ! DO-NOT-DELETE splicer.end(sorting.Integer.private_data)
  end type sorting_Integer_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(sorting_Integer_impl_t), target :: obj
    type(sorting_Integer_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external sorting_Integer_wrap
    call sorting_Integer_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Integer._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Integer._ctor.use)
    implicit none
    !  in sorting.Integer self
    type(sorting_Integer_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Integer._ctor)
    self%d_value = 0
    ! DO-NOT-DELETE splicer.end(sorting.Integer._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Integer._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Integer._dtor.use)
    implicit none
    !  in sorting.Integer self
    type(sorting_Integer_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Integer._dtor)
    ! DO-NOT-DELETE splicer.end(sorting.Integer._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Integer._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Integer._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Integer._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(sorting.Integer._load)
  end subroutine load_impl


  ! 
  ! Method:  getValue[]
  ! 

  function getValue_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(sorting.Integer.getValue.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Integer.getValue.use)
    implicit none
    !  in sorting.Integer self
    type(sorting_Integer_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(sorting.Integer.getValue)
    retval = self%d_value
    ! DO-NOT-DELETE splicer.end(sorting.Integer.getValue)
  end function getValue_impl


  ! 
  ! Method:  setValue[]
  ! 

  subroutine setValue_impl(self, value, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.Integer.setValue.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.Integer.setValue.use)
    implicit none
    !  in sorting.Integer self
    type(sorting_Integer_impl_t) :: self
    !  in int value
    integer (kind=sidl_int), intent(in) :: value
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.Integer.setValue)
    self%d_value = value
    ! DO-NOT-DELETE splicer.end(sorting.Integer.setValue)
  end subroutine setValue_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module sorting_Integer_Impl
