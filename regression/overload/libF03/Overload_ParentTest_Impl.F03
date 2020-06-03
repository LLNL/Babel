! 
! File:          Overload_ParentTest_Impl.F03
! Symbol:        Overload.ParentTest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.ParentTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.ParentTest" (version 1.0)
! 
! This class is used as the work-horse, returning the value passed
! in.
! 


#include "Overload_ParentTest_fAbbrev.h"
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

module Overload_ParentTest_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_ParentTest
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Overload_ParentTest_t) :: Overload_ParentTest_impl_t
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.private_data)
  end type Overload_ParentTest_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Overload_ParentTest_impl_t), target :: obj
    type(Overload_ParentTest_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Overload_ParentTest_wrap
    call Overload_ParentTest_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor)
    ! insert code here (_ctor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor)
    ! insert code here (_dtor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._load.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest._load)
    ! insert code here (_load method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest._load)
  end subroutine load_impl


  ! 
  ! Method:  getValue[]
  ! 

  function getValue_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue)
    ! insert code here (getValue method)
    retval = 1
    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue)
  end function getValue_impl


  ! 
  ! Method:  getValue[Int]
  ! 

  function getValueInt_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in int v
    integer (kind=sidl_int), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt)
    ! insert code here (getValue method)
    retval = v
    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt)
  end function getValueInt_impl


  ! 
  ! Method:  getValue[Bool]
  ! 

  function getValueBool_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in bool v
    logical, intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool)
    ! insert code here (getValue method)
    ! 
    retval = v
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool)
  end function getValueBool_impl


  ! 
  ! Method:  getValue[Double]
  ! 

  function getValueDouble_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in double v
    real (kind=sidl_double), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble)
    ! insert code here (getValue method)
    ! 
    retval = v
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble)
  end function getValueDouble_impl


  ! 
  ! Method:  getValue[Dcomplex]
  ! 

  function getValueDcomplex_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in dcomplex v
    complex (kind=sidl_dcomplex), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    complex (kind=sidl_dcomplex) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex)
    ! insert code here (getValue method)
    ! 
    retval = v
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex)
  end function getValueDcomplex_impl


  ! 
  ! Method:  getValue[Float]
  ! 

  function getValueFloat_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in float v
    real (kind=sidl_float), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_float) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat)
    ! insert code here (getValue method)
    ! 
    retval = v
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat)
  end function getValueFloat_impl


  ! 
  ! Method:  getValue[Fcomplex]
  ! 

  function getValueFcomplex_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in fcomplex v
    complex (kind=sidl_fcomplex), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    complex (kind=sidl_fcomplex) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex)
    ! insert code here (getValue method)
    ! 
    retval = v
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex)
  end function getValueFcomplex_impl


  ! 
  ! Method:  getValue[String]
  ! 

  function getValueString_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString.use)
    implicit none
    !  in Overload.ParentTest self
    type(Overload_ParentTest_impl_t) :: self
    !  in string v
    character (len=*), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString)
    ! insert code here (getValue method)
    ! 
    retval = v
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString)
  end function getValueString_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Overload_ParentTest_Impl
