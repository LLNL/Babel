! 
! File:          enums_colorwheel_Impl.F90
! Symbol:        enums.colorwheel-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for enums.colorwheel
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "enums.colorwheel" (version 1.0)
! 


#include "enums_color_fAbbrev.h"
#include "enums_colorwheel_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine enums_colorwheel__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel._ctor.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.colorwheel._ctor)
end subroutine enums_colorwheel__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine enums_colorwheel__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor2.use)
  ! Insert-Code-Here {enums.colorwheel._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(enums.colorwheel._ctor2.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  type(enums_colorwheel_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor2)
! Insert-Code-Here {enums.colorwheel._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(enums.colorwheel._ctor2)
end subroutine enums_colorwheel__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine enums_colorwheel__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel._dtor.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.colorwheel._dtor)
end subroutine enums_colorwheel__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine enums_colorwheel__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(enums.colorwheel._load)
end subroutine enums_colorwheel__load_mi


! 
! Method:  returnback[]
! 

recursive subroutine enums_colorwheel_returnback_mi(self, retval, exception)
  use sidl
  use enums_color
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel.returnback.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  integer (kind=sidl_enum) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback)
  retval = violet
! DO-NOT-DELETE splicer.end(enums.colorwheel.returnback)
end subroutine enums_colorwheel_returnback_mi


! 
! Method:  passin[]
! 

recursive subroutine enums_colorwheel_passin_mi(self, c, retval, exception)
  use sidl
  use enums_color
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passin.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel.passin.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  integer (kind=sidl_enum) :: c
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel.passin)
  retval = (c .eq. blue)
! DO-NOT-DELETE splicer.end(enums.colorwheel.passin)
end subroutine enums_colorwheel_passin_mi


! 
! Method:  passout[]
! 

recursive subroutine enums_colorwheel_passout_mi(self, c, retval, exception)
  use sidl
  use enums_color
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel.passout.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  integer (kind=sidl_enum) :: c
  ! out
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel.passout)
  c = violet
  retval = .true.
! DO-NOT-DELETE splicer.end(enums.colorwheel.passout)
end subroutine enums_colorwheel_passout_mi


! 
! Method:  passinout[]
! 

recursive subroutine enums_colorwheel_passinout_mi(self, c, retval, exception)
  use sidl
  use enums_color
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel.passinout.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  integer (kind=sidl_enum) :: c
  ! inout
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout)
  retval = .true.
  if (c .eq. red) then
     c = green
  else
     if (c .eq. orange) then
        c = blue
     else
        if (c .eq. yellow) then
           c = violet
        else
           if (c .eq. green) then
              c = red
           else
              if (c .eq. blue) then
                 c = orange
              else
                 if (c .eq. violet) then
                    c = yellow
                 else
                    retval = .false.
                    return
                 endif
              endif
           endif
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(enums.colorwheel.passinout)
end subroutine enums_colorwheel_passinout_mi


! 
! Method:  passeverywhere[]
! 

recursive subroutine col_passeverywherec7hi9hfxrr_mi(self, c1, c2, c3, retval, &
  exception)
  use sidl
  use enums_color
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  use enums_colorwheel_impl
  ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere.use)
  implicit none
  type(enums_colorwheel_t) :: self
  ! in
  integer (kind=sidl_enum) :: c1
  ! in
  integer (kind=sidl_enum) :: c2
  ! out
  integer (kind=sidl_enum) :: c3
  ! inout
  integer (kind=sidl_enum) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere)
  c2 = violet
  if (c3 .eq. red) then
     c3 = green
  else
     if (c3 .eq. orange) then
        c3 = blue
     else
        if (c3 .eq. yellow) then
           c3 = violet
        else
           if (c3 .eq. green) then
              c3 = red
           else
              if (c3 .eq. blue) then
                 c3 = orange
              else
                 if (c3 .eq. violet) then
                    c3 = yellow
                 else
                    retval = yellow
                    return
                 endif
              endif
           endif
        endif
     endif
  endif
  if (c1 .eq. blue) then
     retval = violet
  else
     retval = yellow
  endif
! DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere)
end subroutine col_passeverywherec7hi9hfxrr_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
