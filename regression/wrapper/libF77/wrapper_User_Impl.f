C       
C       File:          wrapper_User_Impl.f
C       Symbol:        wrapper.User-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for wrapper.User
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "wrapper.User" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert-Code-Here {_miscellaneous_code_start} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine wrapper_User__ctor_fi(self, exception)
        implicit none
C        in wrapper.User self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.User._ctor)
C       Insert-Code-Here {wrapper.User._ctor} (_ctor method)
C       DO-NOT-DELETE splicer.end(wrapper.User._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine wrapper_User__ctor2_fi(self, private_data, exception)
        implicit none
C        in wrapper.User self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.User._ctor2)
C       Insert-Code-Here {wrapper.User._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(wrapper.User._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine wrapper_User__dtor_fi(self, exception)
        implicit none
C        in wrapper.User self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.User._dtor)
C       Insert-Code-Here {wrapper.User._dtor} (_dtor method)
C       DO-NOT-DELETE splicer.end(wrapper.User._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine wrapper_User__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.User._load)
C       Insert-Code-Here {wrapper.User._load} (_load method)
C       DO-NOT-DELETE splicer.end(wrapper.User._load)
        end


C       
C       Method:  accept[]
C       

        subroutine wrapper_User_accept_fi(self, data, exception)
        implicit none
C        in wrapper.User self
        integer*8 :: self
C        in wrapper.Data data
        integer*8 :: data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(wrapper.User.accept)
        call wrapper_Data_setString_f(data, 'Hello World!', exception)
        call wrapper_Data_setInt_f(data, 3, exception)
C       DO-NOT-DELETE splicer.end(wrapper.User.accept)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert-Code-Here {_miscellaneous_code_end} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
