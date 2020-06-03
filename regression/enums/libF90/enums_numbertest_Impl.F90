! 
! File:          enums_numbertest_Impl.F90
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
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine enums_numbertest__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest._ctor.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.numbertest._ctor)
end subroutine enums_numbertest__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine enums_numbertest__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest._ctor2.use)
  ! Insert-Code-Here {enums.numbertest._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(enums.numbertest._ctor2.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  type(enums_numbertest_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest._ctor2)
! Insert-Code-Here {enums.numbertest._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(enums.numbertest._ctor2)
end subroutine enums_numbertest__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine enums_numbertest__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest._dtor.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.numbertest._dtor)
end subroutine enums_numbertest__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine enums_numbertest__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.numbertest._load)
end subroutine enums_numbertest__load_mi


! 
! Method:  returnback[]
! 

recursive subroutine enums_numbertest_returnback_mi(self, retval, exception)
  use sidl
  use enums_number
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest.returnback.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest.returnback.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  integer (kind=sidl_enum) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest.returnback)
  retval = notOne
! DO-NOT-DELETE splicer.end(enums.numbertest.returnback)
end subroutine enums_numbertest_returnback_mi


! 
! Method:  passin[]
! 

recursive subroutine enums_numbertest_passin_mi(self, n, retval, exception)
  use sidl
  use enums_number
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest.passin.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest.passin.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  integer (kind=sidl_enum) :: n
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest.passin)
  retval = (n .eq. notZero)
! DO-NOT-DELETE splicer.end(enums.numbertest.passin)
end subroutine enums_numbertest_passin_mi


! 
! Method:  passout[]
! 

recursive subroutine enums_numbertest_passout_mi(self, n, retval, exception)
  use sidl
  use enums_number
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest.passout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest.passout.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  integer (kind=sidl_enum) :: n
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.numbertest.passout)
  n = negOne
  retval = .true.
! DO-NOT-DELETE splicer.end(enums.numbertest.passout)
end subroutine enums_numbertest_passout_mi


! 
! Method:  passinout[]
! 

recursive subroutine enums_numbertest_passinout_mi(self, n, retval, exception)
  use sidl
  use enums_number
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest.passinout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest.passinout.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  integer (kind=sidl_enum) :: n
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

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
end subroutine enums_numbertest_passinout_mi


! 
! Method:  passeverywhere[]
! 

recursive subroutine num_passeverywhere4hq06ew8ad_mi(self, n1, n2, n3, retval, &
  exception)
  use sidl
  use enums_number
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_numbertest
  use enums_numbertest_impl
  ! DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere.use)
  implicit none
  type(enums_numbertest_t) :: self
  ! in
  integer (kind=sidl_enum) :: n1
  ! in
  integer (kind=sidl_enum) :: n2
  ! out
  integer (kind=sidl_enum) :: n3
  ! inout
  integer (kind=sidl_enum) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

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
end subroutine num_passeverywhere4hq06ew8ad_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
