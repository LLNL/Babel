! 
! File:          Overload_Test_Impl.F03
! Symbol:        Overload.Test-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Overload.Test
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Overload.Test" (version 1.0)
! 
! This class is used as the work-horse, returning the value passed
! in.
! 


#include "Overload_ParentTest_fAbbrev.h"
#include "Overload_AClass_fAbbrev.h"
#include "Overload_Test_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "Overload_BClass_fAbbrev.h"
#include "Overload_AnException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Overload_Test_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Overload_AClass
  use Overload_AnException
  use Overload_BClass
  use Overload_Test
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Overload_Test_t) :: Overload_Test_impl_t
    ! DO-NOT-DELETE splicer.begin(Overload.Test.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Overload.Test.private_data)
  end type Overload_Test_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Overload_Test_impl_t), target :: obj
    type(Overload_Test_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Overload_Test_wrap
    call Overload_Test_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.Test._ctor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test._ctor.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.Test._ctor)
    ! insert code here (_ctor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Overload.Test._dtor.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test._dtor.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.Test._dtor)
    ! insert code here (_dtor method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Overload.Test._load.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Overload.Test._load)
    ! insert code here (_load method)
    ! 
    ! This method has not been implemented
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test._load)
  end subroutine load_impl


  ! 
  ! Method:  getValue[IntDouble]
  ! 

  function getValueIntDouble_impl(self, a, b, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in int a
    integer (kind=sidl_int), intent(in) :: a
    !  in double b
    real (kind=sidl_double), intent(in) :: b
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble)
    ! insert code here (getValue method)
    retval = a + b
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble)
  end function getValueIntDouble_impl


  ! 
  ! Method:  getValue[DoubleInt]
  ! 

  function getValueDoubleInt_impl(self, a, b, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in double a
    real (kind=sidl_double), intent(in) :: a
    !  in int b
    integer (kind=sidl_int), intent(in) :: b
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt)
    ! insert code here (getValue method)
    retval = a + b
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt)
  end function getValueDoubleInt_impl


  ! 
  ! Method:  getValue[IntDoubleFloat]
  ! 

  function getValueIntDoubleFloat_impl(self, a, b, c, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in int a
    integer (kind=sidl_int), intent(in) :: a
    !  in double b
    real (kind=sidl_double), intent(in) :: b
    !  in float c
    real (kind=sidl_float), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat)
    ! insert code here (getValue method)
    retval = a + b + c
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat)
  end function getValueIntDoubleFloat_impl


  ! 
  ! Method:  getValue[DoubleIntFloat]
  ! 

  function getValueDoubleIntFloat_impl(self, a, b, c, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in double a
    real (kind=sidl_double), intent(in) :: a
    !  in int b
    integer (kind=sidl_int), intent(in) :: b
    !  in float c
    real (kind=sidl_float), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat)
    ! insert code here (getValue method)
    retval = a + b + c
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat)
  end function getValueDoubleIntFloat_impl


  ! 
  ! Method:  getValue[IntFloatDouble]
  ! 

  function getValueIntFloatDouble_impl(self, a, b, c, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in int a
    integer (kind=sidl_int), intent(in) :: a
    !  in float b
    real (kind=sidl_float), intent(in) :: b
    !  in double c
    real (kind=sidl_double), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble)
    ! insert code here (getValue method)
    retval = a + b + c
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble)
  end function getValueIntFloatDouble_impl


  ! 
  ! Method:  getValue[DoubleFloatInt]
  ! 

  function getValueDoubleFloatInt_impl(self, a, b, c, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in double a
    real (kind=sidl_double), intent(in) :: a
    !  in float b
    real (kind=sidl_float), intent(in) :: b
    !  in int c
    integer (kind=sidl_int), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt)
    ! insert code here (getValue method)
    retval = a + b + c
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt)
  end function getValueDoubleFloatInt_impl


  ! 
  ! Method:  getValue[FloatIntDouble]
  ! 

  function getValueFloatIntDouble_impl(self, a, b, c, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in float a
    real (kind=sidl_float), intent(in) :: a
    !  in int b
    integer (kind=sidl_int), intent(in) :: b
    !  in double c
    real (kind=sidl_double), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble)
    ! insert code here (getValue method)
    retval = a + b + c
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble)
  end function getValueFloatIntDouble_impl


  ! 
  ! Method:  getValue[FloatDoubleInt]
  ! 

  function getValueFloatDoubleInt_impl(self, a, b, c, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in float a
    real (kind=sidl_float), intent(in) :: a
    !  in double b
    real (kind=sidl_double), intent(in) :: b
    !  in int c
    integer (kind=sidl_int), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    real (kind=sidl_double) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt)
    ! insert code here (getValue method)
    retval = a + b + c
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt)
  end function getValueFloatDoubleInt_impl


  ! 
  ! Method:  getValue[Exception]
  ! 

  function getValueException_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueException.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueException.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in Overload.AnException v
    type(Overload_AnException_t), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    character (len=sidl_f03_str_minsize) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueException)
    ! insert code here (getValue method)
    retval = getNote(v, exception)
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueException)
  end function getValueException_impl


  ! 
  ! Method:  getValue[AClass]
  ! 

  function getValueAClass_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in Overload.AClass v
    type(Overload_AClass_t), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass)
    ! insert code here (getValue method)
    retval = getValue(v, exception)
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass)
  end function getValueAClass_impl


  ! 
  ! Method:  getValue[BClass]
  ! 

  function getValueBClass_impl(self, v, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass.use)
    ! 
    ! Add use statements here
    ! 

    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass.use)
    implicit none
    !  in Overload.Test self
    type(Overload_Test_impl_t) :: self
    !  in Overload.BClass v
    type(Overload_BClass_t), intent(in) :: v
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_int) :: retval

    ! DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass)
    ! insert code here (getValue method)
    retval = getValue(v, exception)
    ! DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass)
  end function getValueBClass_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! insert code here (extra code)
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Overload_Test_Impl
