! 
! File:          enums_colorwheel_Impl.F03
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
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module enums_colorwheel_Impl
  use sidl
  use enums_color
  use sidl_BaseInterface
  use sidl_RuntimeException
  use enums_colorwheel
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(enums_colorwheel_t) :: enums_colorwheel_impl_t
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.private_data)
  end type enums_colorwheel_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(enums_colorwheel_impl_t), target :: obj
    type(enums_colorwheel_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external enums_colorwheel_wrap
    call enums_colorwheel_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel._ctor.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel._dtor.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(enums.colorwheel._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel._load)
  end subroutine load_impl


  ! 
  ! Method:  returnback[]
  ! 

  function returnback_impl(self, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.returnback.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_enum) :: retval

    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback)
    retval = violet
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.returnback)
  end function returnback_impl


  ! 
  ! Method:  passin[]
  ! 

  function passin_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passin.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.passin.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  in enums.color c
    integer (kind=sidl_enum), intent(in) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passin)
    retval = (c .eq. blue)
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.passin)
  end function passin_impl


  ! 
  ! Method:  passout[]
  ! 

  function passout_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.passout.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  out enums.color c
    integer (kind=sidl_enum), intent(out) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passout)
    c = violet
    retval = .true.
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.passout)
  end function passout_impl


  ! 
  ! Method:  passinout[]
  ! 

  function passinout_impl(self, c, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.passinout.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  inout enums.color c
    integer (kind=sidl_enum), intent(inout) :: c
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

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
  end function passinout_impl


  ! 
  ! Method:  passeverywhere[]
  ! 

  function passeverywhere_impl(self, c1, c2, c3, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere.use)
    implicit none
    !  in enums.colorwheel self
    type(enums_colorwheel_impl_t) :: self
    !  in enums.color c1
    integer (kind=sidl_enum), intent(in) :: c1
    !  out enums.color c2
    integer (kind=sidl_enum), intent(out) :: c2
    !  inout enums.color c3
    integer (kind=sidl_enum), intent(inout) :: c3
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    integer (kind=sidl_enum) :: retval

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
  end function passeverywhere_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module enums_colorwheel_Impl
