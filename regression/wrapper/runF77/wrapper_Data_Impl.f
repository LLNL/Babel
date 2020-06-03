C       
C       File:          wrapper_Data_Impl.f
C       Symbol:        wrapper.Data-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for wrapper.Data
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "wrapper.Data" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert-Code-Here {_miscellaneous_code_start} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine wrapper_Data__ctor_fi(self, exception)
        implicit none
C        in wrapper.Data self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.Data._ctor)
        
C       DO-NOT-DELETE splicer.end(wrapper.Data._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine wrapper_Data__ctor2_fi(self, private_data, exception)
        implicit none
C        in wrapper.Data self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.Data._ctor2)
        integer*8 a_string, pdata
        character*80 d_string, d_ctortest
        call sidl_opaque__array_get1_f(private_data, 0, a_string)

        call sidl_string__array_set1_f(a_string, 1, 'ctor was run') 
        
C       DO-NOT-DELETE splicer.end(wrapper.Data._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine wrapper_Data__dtor_fi(self, exception)
        implicit none
C        in wrapper.Data self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.Data._dtor)
        integer*8 pdata, a_int, a_string

        call wrapper_Data__get_data_f(self, pdata)
        call sidl_opaque__array_get1_f(pdata, 0, a_string)
        call sidl_opaque__array_get1_f(pdata, 1, a_int)

        call sidl_string__array_deleteRef_f(a_string, exception)
        call sidl_int__array_deleteRef_f(a_int, exception)
        call sidl_opaque__array_deleteRef_f(pdata, exception)

C       DO-NOT-DELETE splicer.end(wrapper.Data._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine wrapper_Data__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.Data._load)
C       Insert-Code-Here {wrapper.Data._load} (_load method)
C       DO-NOT-DELETE splicer.end(wrapper.Data._load)
        end


C       
C       Method:  setString[]
C       

        subroutine wrapper_Data_setString_fi(self, s, exception)
        implicit none
C        in wrapper.Data self
        integer*8 :: self
C        in string s
        character*(*) :: s
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.Data.setString)
        integer*8 data, a_string

        call wrapper_Data__get_data_f(self, data)
        if (data .ne. 0) then
           call sidl_opaque__array_get1_f(data, 0, a_string)
           call sidl_string__array_set1_f(a_string, 0, s)
        endif

C       DO-NOT-DELETE splicer.end(wrapper.Data.setString)
        end


C       
C       Method:  setInt[]
C       

        subroutine wrapper_Data_setInt_fi(self, i, exception)
        implicit none
C        in wrapper.Data self
        integer*8 :: self
C        in int i
        integer*4 :: i
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.Data.setInt)
        integer*8 data, a_int

        call wrapper_Data__get_data_f(self, data)
        if (data .ne. 0) then
           call sidl_opaque__array_get1_f(data, 1, a_int)
           call sidl_int__array_set1_f(a_int, 0, i)
        endif

C       DO-NOT-DELETE splicer.end(wrapper.Data.setInt)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert-Code-Here {_miscellaneous_code_end} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
