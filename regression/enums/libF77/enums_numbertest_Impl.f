C       
C       File:          enums_numbertest_Impl.f
C       Symbol:        enums.numbertest-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for enums.numbertest
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "enums.numbertest" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine enums_numbertest__ctor_fi(self, exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.numbertest._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine enums_numbertest__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest._ctor2)
C       Insert-Code-Here {enums.numbertest._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(enums.numbertest._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine enums_numbertest__dtor_fi(self, exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.numbertest._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine enums_numbertest__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.numbertest._load)
        end


C       
C       Method:  returnback[]
C       

        subroutine enums_numbertest_returnback_fi(self, retval, 
     &     exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        out enums.number retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest.returnback)
        include 'enums_number.inc'
        retval = notOne
C       DO-NOT-DELETE splicer.end(enums.numbertest.returnback)
        end


C       
C       Method:  passin[]
C       

        subroutine enums_numbertest_passin_fi(self, n, retval, 
     &     exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        in enums.number n
        integer*8 :: n
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest.passin)
        include 'enums_number.inc'
        retval = (n .eq. notZero)
C       DO-NOT-DELETE splicer.end(enums.numbertest.passin)
        end


C       
C       Method:  passout[]
C       

        subroutine enums_numbertest_passout_fi(self, n, retval, 
     &     exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        out enums.number n
        integer*8 :: n
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest.passout)
        include 'enums_number.inc'
        n = negOne
        retval = .true.
C       DO-NOT-DELETE splicer.end(enums.numbertest.passout)
        end


C       
C       Method:  passinout[]
C       

        subroutine enums_numbertest_passinout_fi(self, n, retval, 
     &     exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        inout enums.number n
        integer*8 :: n
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest.passinout)
        include 'enums_number.inc'
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
C       DO-NOT-DELETE splicer.end(enums.numbertest.passinout)
        end


C       
C       Method:  passeverywhere[]
C       

        subroutine enums_numbertest_passeverywhere_fi(self, n1, n2, n3, 
     &     retval, exception)
        implicit none
C        in enums.numbertest self
        integer*8 :: self
C        in enums.number n1
        integer*8 :: n1
C        out enums.number n2
        integer*8 :: n2
C        inout enums.number n3
        integer*8 :: n3
C        out enums.number retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.numbertest.passeverywhere)
        include 'enums_number.inc'
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
C       DO-NOT-DELETE splicer.end(enums.numbertest.passeverywhere)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
