C       
C       File:          enums_cartest_Impl.f
C       Symbol:        enums.cartest-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for enums.cartest
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "enums.cartest" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine enums_cartest__ctor_fi(self, exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.cartest._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine enums_cartest__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest._ctor2)
C       Insert-Code-Here {enums.cartest._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(enums.cartest._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine enums_cartest__dtor_fi(self, exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.cartest._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine enums_cartest__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(enums.cartest._load)
        end


C       
C       Method:  returnback[]
C       

        subroutine enums_cartest_returnback_fi(self, retval, exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        out enums.car retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest.returnback)
        include 'enums_car.inc'
        retval = porsche
C       DO-NOT-DELETE splicer.end(enums.cartest.returnback)
        end


C       
C       Method:  passin[]
C       

        subroutine enums_cartest_passin_fi(self, c, retval, exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        in enums.car c
        integer*8 :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest.passin)
        include 'enums_car.inc'
        retval = (c .eq. mercedes)
C       DO-NOT-DELETE splicer.end(enums.cartest.passin)
        end


C       
C       Method:  passout[]
C       

        subroutine enums_cartest_passout_fi(self, c, retval, exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        out enums.car c
        integer*8 :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest.passout)
        include 'enums_car.inc'
        c = ford
        retval = .true.
C       DO-NOT-DELETE splicer.end(enums.cartest.passout)
        end


C       
C       Method:  passinout[]
C       

        subroutine enums_cartest_passinout_fi(self, c, retval, 
     &     exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        inout enums.car c
        integer*8 :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest.passinout)
        include 'enums_car.inc'
        if (c .eq. ford) then
           c = porsche
           retval = .true.
        else
           if (c .eq. porsche) then
              c = mercedes
              retval = .true.
           else
              if (c .eq. mercedes) then
                 retval = .true.
              else
                 retval = .false.
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(enums.cartest.passinout)
        end


C       
C       Method:  passeverywhere[]
C       

        subroutine enums_cartest_passeverywhere_fi(self, c1, c2, c3, 
     &     retval, exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        in enums.car c1
        integer*8 :: c1
C        out enums.car c2
        integer*8 :: c2
C        inout enums.car c3
        integer*8 :: c3
C        out enums.car retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest.passeverywhere)
        include 'enums_car.inc'
        c2 = ford
        if (c3 .eq. ford) then
           c3 = porsche
        else 
           if (c3 .eq. porsche) then
              c3 = mercedes
           else 
              if (c3 .ne. mercedes) then
                 retval = mercedes
                 return
              endif
           endif
        endif
        if (c1 .eq. mercedes) then
           retval = porsche
        else
           retval = mercedes
        endif
C       DO-NOT-DELETE splicer.end(enums.cartest.passeverywhere)
        end


C       
C       Method:  passarray[]
C       All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
C       in that order.
C       

        subroutine enums_cartest_passarray_fi(self, c1, c2, c3, retval, 
     &     exception)
        implicit none
C        in enums.cartest self
        integer*8 :: self
C        in array<enums.car> c1
        integer*8 :: c1
C        out array<enums.car> c2
        integer*8 :: c2
C        inout array<enums.car> c3
        integer*8 :: c3
C        out array<enums.car> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(enums.cartest.passarray)
        logical failed
        integer*4 length, i, dim, low
        integer*8 value, vals(3)
        include 'enums_car.inc'
        data vals / ford, mercedes, porsche /
        failed = .false.
        exception = 0
        dim = 0
        retval = 0
        c2 = 0
        if ((c1 .ne. 0) .and. (c3 .ne. 0)) then
           call enums_car__array_length_f(c1, dim, length)
           if (length .eq. 3) then
              call enums_car__array_length_f(c3, dim, length)
              if (length .eq. 3) then
                 call enums_car__array_create1d_f(length, c2)
                 call enums_car__array_create1d_f(length, retval)
                 do i = 0, 2
                    call enums_car__array_set1_f(c2, i, vals(i+1))
                    call enums_car__array_set1_f(retval, i, vals(i+1))
                    call enums_car__array_lower_f(c1, dim, low)
                    call enums_car__array_get1_f(c1, low+i, value)
                    failed = failed .or. (value .ne. vals(i+1))
                    call enums_car__array_lower_f(c3, dim, low)
                    call enums_car__array_get1_f(c3, low+i, value)
                    failed = failed .or. (value .ne. vals(i+1))
                 end do
                 if (failed) then
                    call enums_car__array_deleteRef_f(c2)
                    c2 = 0
                    call enums_car__array_deleteRef_f(retval)
                    retval = 0
                 endif
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(enums.cartest.passarray)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
