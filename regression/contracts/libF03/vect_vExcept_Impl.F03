! 
! File:          vect_vExcept_Impl.F03
! Symbol:        vect.vExcept-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for vect.vExcept
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "vect.vExcept" (version 1.0)
! 


#include "sidl_io_Deserializer_fAbbrev.h"
#include "sidl_io_Serializer_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "vect_vExcept_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_io_Serializable_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module vect_vExcept_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use vect_vExcept
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(vect_vExcept_t) :: vect_vExcept_impl_t
    ! DO-NOT-DELETE splicer.begin(vect.vExcept.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(vect.vExcept.private_data)
  end type vect_vExcept_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(vect_vExcept_impl_t), target :: obj
    type(vect_vExcept_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external vect_vExcept_wrap
    call vect_vExcept_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(vect.vExcept._ctor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(vect.vExcept._ctor.use)
    implicit none
    !  in vect.vExcept self
    type(vect_vExcept_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(vect.vExcept._ctor)
    ! insert code here (_ctor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(vect.vExcept._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(vect.vExcept._dtor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(vect.vExcept._dtor.use)
    implicit none
    !  in vect.vExcept self
    type(vect_vExcept_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(vect.vExcept._dtor)
    ! insert code here (_dtor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(vect.vExcept._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(vect.vExcept._load.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(vect.vExcept._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(vect.vExcept._load)
    ! insert code here (_load method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(vect.vExcept._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module vect_vExcept_Impl
