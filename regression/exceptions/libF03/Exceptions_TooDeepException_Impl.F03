! 
! File:          Exceptions_TooDeepException_Impl.F03
! Symbol:        Exceptions.TooDeepException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.TooDeepException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.TooDeepException" (version 1.0)
! 
! This exception is thrown if the Fibonacci recursion is too deep.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
#include "Exceptions_TooDeepException_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Exceptions_TooDeepException_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooDeepException
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Exceptions_TooDeepException_t) ::                              &
    Exceptions_TooDeepException_impl_t
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException.private_data)
  end type Exceptions_TooDeepException_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Exceptions_TooDeepException_impl_t), target :: obj
    type(Exceptions_TooDeepException_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Exceptions_TooDeepException_wrap
    call Exceptions_TooDeepException_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor.use)
    implicit none
    !  in Exceptions.TooDeepException self
    type(Exceptions_TooDeepException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._dtor.use)
    implicit none
    !  in Exceptions.TooDeepException self
    type(Exceptions_TooDeepException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.TooDeepException._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooDeepException._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Exceptions_TooDeepException_Impl
