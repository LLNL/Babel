! 
! File:          Overload_AnException_Impl.F03
! Symbol:        Overload.AnException-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.AnException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.AnException" (version 1.0)
! 
! This exception is passed into the overloaded method as an example
! of passing classes.
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Overload_AnException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Overload_AnException_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AnException
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Overload_AnException_t) :: Overload_AnException_impl_t
    ! DO-NOT-DELETE splicer.begin(Overload.AnException.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Overload.AnException.private_data)
  end type Overload_AnException_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Overload_AnException_impl_t), target :: obj
    type(Overload_AnException_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Overload_AnException_wrap
    call Overload_AnException_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.AnException._ctor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.AnException._ctor.use)
    implicit none
    !  in Overload.AnException self
    type(Overload_AnException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.AnException._ctor)
    ! insert code here (_ctor method)
    ! 
    call setNote(self, 'AnException', exception)
    ! DO-NOT-DELETE splicer.end(Overload.AnException._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.AnException._dtor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.AnException._dtor.use)
    implicit none
    !  in Overload.AnException self
    type(Overload_AnException_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.AnException._dtor)
    ! insert code here (_dtor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.AnException._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Overload.AnException._load.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.AnException._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.AnException._load)
    ! insert code here (_load method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.AnException._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Overload_AnException_Impl
