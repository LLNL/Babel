! 
! File:          Overload_BClass_Impl.F03
! Symbol:        Overload.BClass-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.BClass
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.BClass" (version 1.0)
! 
! This class is passed into the overloaded method as another example
! of passing classes.
! 


#include "Overload_AClass_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Overload_BClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Overload_BClass_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_BClass
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Overload_BClass_t) :: Overload_BClass_impl_t
    ! DO-NOT-DELETE splicer.begin(Overload.BClass.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Overload.BClass.private_data)
  end type Overload_BClass_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Overload_BClass_impl_t), target :: obj
    type(Overload_BClass_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Overload_BClass_wrap
    call Overload_BClass_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.BClass._ctor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.BClass._ctor.use)
    implicit none
    !  in Overload.BClass self
    type(Overload_BClass_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.BClass._ctor)
    ! insert code here (_ctor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.BClass._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.BClass._dtor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.BClass._dtor.use)
    implicit none
    !  in Overload.BClass self
    type(Overload_BClass_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.BClass._dtor)
    ! insert code here (_dtor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.BClass._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Overload.BClass._load.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.BClass._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.BClass._load)
    ! insert code here (_load method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.BClass._load)
  end subroutine load_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Overload_BClass_Impl
