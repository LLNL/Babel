! 
! File:          Exceptions_FibException_Impl.F03
! Symbol:        Exceptions.FibException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.FibException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.FibException" (version 1.0)
! 
! This exception is a base class for the Fibonacci Exceptions that are
! thrown if the value is too large or the recursion depth is too deep.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
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

module Exceptions_FibException_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_FibException
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Exceptions_FibException_t) :: Exceptions_FibException_impl_t
    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException.private_data)
  end type Exceptions_FibException_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Exceptions_FibException_impl_t), target :: obj
    type(Exceptions_FibException_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Exceptions_FibException_wrap
    call Exceptions_FibException_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor.use)
    implicit none
    !  in Exceptions.FibException self
    type(Exceptions_FibException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException._dtor.use)
    implicit none
    !  in Exceptions.FibException self
    type(Exceptions_FibException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Exceptions.FibException._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Exceptions.FibException._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Exceptions_FibException_Impl
