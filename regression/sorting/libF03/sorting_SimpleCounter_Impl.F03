! 
! File:          sorting_SimpleCounter_Impl.F03
! Symbol:        sorting.SimpleCounter-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for sorting.SimpleCounter
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "sorting.SimpleCounter" (version 0.1)
! 
! Simple counter
! 


#include "sorting_Counter_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sorting_SimpleCounter_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module sorting_SimpleCounter_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use sorting_SimpleCounter
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(sorting_SimpleCounter_t) :: sorting_SimpleCounter_impl_t
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.private_data)
    ! insert code here (private data members)
    integer(kind=sidl_int) :: count
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.private_data)
  end type sorting_SimpleCounter_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(sorting_SimpleCounter_impl_t), target :: obj
    type(sorting_SimpleCounter_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external sorting_SimpleCounter_wrap
    call sorting_SimpleCounter_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor.use)
    implicit none
    !  in sorting.SimpleCounter self
    type(sorting_SimpleCounter_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._ctor)
    self%count = 0
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._dtor.use)
    implicit none
    !  in sorting.SimpleCounter self
    type(sorting_SimpleCounter_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._dtor)
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter._load)
  end subroutine load_impl


  ! 
  ! Method:  reset[]
  ! Set the count to zero.
  ! 

  subroutine reset_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.reset.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.reset.use)
    implicit none
    !  in sorting.SimpleCounter self
    type(sorting_SimpleCounter_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.reset)
    self%count = 0
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.reset)
  end subroutine reset_impl


  ! 
  ! Method:  getCount[]
  ! Return the current count.
  ! 

  function getCount_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.getCount.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.getCount.use)
    implicit none
    !  in sorting.SimpleCounter self
    type(sorting_SimpleCounter_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.getCount)
    retval = self%count
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.getCount)
  end function getCount_impl


  ! 
  ! Method:  inc[]
  ! Increment the count (i.e. add one).
  ! 

  function inc_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.inc.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.inc.use)
    implicit none
    !  in sorting.SimpleCounter self
    type(sorting_SimpleCounter_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(sorting.SimpleCounter.inc)
    self%count = self%count + 1
    retval = self%count
    ! DO-NOT-DELETE splicer.end(sorting.SimpleCounter.inc)
  end function inc_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module sorting_SimpleCounter_Impl
