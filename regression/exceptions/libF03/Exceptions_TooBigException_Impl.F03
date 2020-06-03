! 
! File:          Exceptions_TooBigException_Impl.F03
! Symbol:        Exceptions.TooBigException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.TooBigException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.TooBigException" (version 1.0)
! 
! This exception is thrown if the Fibonacci number is too large.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
#include "Exceptions_TooBigException_fAbbrev.h"
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

module Exceptions_TooBigException_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_TooBigException
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Exceptions_TooBigException_t) ::                               &
    Exceptions_TooBigException_impl_t
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException.private_data)
  end type Exceptions_TooBigException_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Exceptions_TooBigException_impl_t), target :: obj
    type(Exceptions_TooBigException_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Exceptions_TooBigException_wrap
    call Exceptions_TooBigException_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._ctor.use)
    implicit none
    !  in Exceptions.TooBigException self
    type(Exceptions_TooBigException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._dtor.use)
    implicit none
    !  in Exceptions.TooBigException self
    type(Exceptions_TooBigException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.TooBigException._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.TooBigException._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Exceptions_TooBigException_Impl
