! 
! File:          enums_numbertest_Impl.F03
! Symbol:        enums.numbertest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for enums.numbertest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "enums.numbertest" (version 1.0)
! 


#include "enums_numbertest_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "enums_number_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module enums_numbertest_Impl
  use sidl
  use enums_number
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(enums_numbertest_t) :: enums_numbertest_impl_t
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(enums.numbertest.private_data)
  end type enums_numbertest_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(enums_numbertest_impl_t), target :: obj
    type(enums_numbertest_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external enums_numbertest_wrap
    call enums_numbertest_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest._ctor.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.numbertest._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest._dtor.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.numbertest._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.numbertest._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest._load)
  end subroutine load_impl


  ! 
  ! Method:  returnback[]
  ! 

  function returnback_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.returnback.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest.returnback.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_enum) :: retval

    ! DO-NOT-DELETE splicer.begin(enums.numbertest.returnback)
    retval = notOne
    ! DO-NOT-DELETE splicer.end(enums.numbertest.returnback)
  end function returnback_impl


  ! 
  ! Method:  passin[]
  ! 

  function passin_impl(self, n, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passin.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passin.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  in enums.number n
    integer (kind=sidl_enum), intent(in) :: n
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passin)
    retval = (n .eq. notZero)
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passin)
  end function passin_impl


  ! 
  ! Method:  passout[]
  ! 

  function passout_impl(self, n, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passout.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  out enums.number n
    integer (kind=sidl_enum), intent(out) :: n
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passout)
    n = negOne
    retval = .true.
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passout)
  end function passout_impl


  ! 
  ! Method:  passinout[]
  ! 

  function passinout_impl(self, n, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passinout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passinout.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  inout enums.number n
    integer (kind=sidl_enum), intent(inout) :: n
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passinout)
    if (n .eq. zero) then
       n = notZero
    else
       if (n .eq. one) then
          n = notOne
       else
          if (n .eq. negOne) then
             n = notNeg
          else
             if (n .eq. notZero) then
                n = zero
             else
                if (n .eq. notOne) then
                   n = one
                else
                   if (n .eq. notNeg) then
                      n = negOne
                   else
                      retval = .false.
                      return
                   endif
                endif
             endif
          endif
       endif
    endif
    retval = .true.
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passinout)
  end function passinout_impl


  ! 
  ! Method:  passeverywhere[]
  ! 

  function passeverywhere_impl(self, n1, n2, n3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere.use)
    implicit none
    !  in enums.numbertest self
    type(enums_numbertest_impl_t) :: self
    !  in enums.number n1
    integer (kind=sidl_enum), intent(in) :: n1
    !  out enums.number n2
    integer (kind=sidl_enum), intent(out) :: n2
    !  inout enums.number n3
    integer (kind=sidl_enum), intent(inout) :: n3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_enum) :: retval

    ! DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere)
    n2 = negOne
    if (n3 .eq. zero) then
       n3 = notZero
    else
       if (n3 .eq. one) then
          n3 = notOne
       else
          if (n3 .eq. negOne) then
             n3 = notNeg
          else
             if (n3 .eq. notZero) then
                n3 = zero
             else
                if (n3 .eq. notOne) then
                   n3 = one
                else
                   if (n3 .eq. notNeg) then
                      n3 = negOne
                   else
                      retval = zero
                      return
                   endif
                endif
             endif
          endif
       endif
    endif
    if (n1 .eq. notZero) then
       retval = notOne
    else
       retval = one
    endif
    ! DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere)
  end function passeverywhere_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module enums_numbertest_Impl
