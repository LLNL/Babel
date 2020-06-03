C       
C       File:          enums_colorwheel_Impl.f
C       Symbol:        enums.colorwheel-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for enums.colorwheel
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "enums.colorwheel" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine enums_colorwheel__ctor_fi(self, exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.colorwheel._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine enums_colorwheel__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel._ctor2)
C       Insert-Code-Here {enums.colorwheel._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(enums.colorwheel._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine enums_colorwheel__dtor_fi(self, exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.colorwheel._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine enums_colorwheel__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.colorwheel._load)
        end


C       
C       Method:  returnback[]
C       

        subroutine enums_colorwheel_returnback_fi(self, retval, 
     &     exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        out enums.color retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel.returnback)
        include 'enums_color.inc'
        retval = violet
C       DO-NOT-DELETE splicer.end(enums.colorwheel.returnback)
        end


C       
C       Method:  passin[]
C       

        subroutine enums_colorwheel_passin_fi(self, c, retval, 
     &     exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        in enums.color c
        integer*8 :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel.passin)
        include 'enums_color.inc'
        retval = (c .eq. blue)
C       DO-NOT-DELETE splicer.end(enums.colorwheel.passin)
        end


C       
C       Method:  passout[]
C       

        subroutine enums_colorwheel_passout_fi(self, c, retval, 
     &     exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        out enums.color c
        integer*8 :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel.passout)
        include 'enums_color.inc'
        c = violet
        retval = .true.
C       DO-NOT-DELETE splicer.end(enums.colorwheel.passout)
        end


C       
C       Method:  passinout[]
C       

        subroutine enums_colorwheel_passinout_fi(self, c, retval, 
     &     exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        inout enums.color c
        integer*8 :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel.passinout)
        include 'enums_color.inc'
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
C       DO-NOT-DELETE splicer.end(enums.colorwheel.passinout)
        end


C       
C       Method:  passeverywhere[]
C       

        subroutine enums_colorwheel_passeverywhere_fi(self, c1, c2, c3, 
     &     retval, exception)
        implicit none
C        in enums.colorwheel self
        integer*8 :: self
C        in enums.color c1
        integer*8 :: c1
C        out enums.color c2
        integer*8 :: c2
C        inout enums.color c3
        integer*8 :: c3
C        out enums.color retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.colorwheel.passeverywhere)
        include 'enums_color.inc'
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
C       DO-NOT-DELETE splicer.end(enums.colorwheel.passeverywhere)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
