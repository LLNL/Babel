C       
C       File:          Hello_World_Impl.f
C       Symbol:        Hello.World-v1.2
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7399M trunk)
C       Description:   Server-side implementation for Hello.World
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Hello.World" (version 1.2)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Hello_World__ctor_fi(self, exception)
        implicit none
C        in Hello.World self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Hello.World._ctor)
        exception = 0
C       DO-NOT-DELETE splicer.end(Hello.World._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Hello_World__ctor2_fi(self, private_data, exception)
        implicit none
C        in Hello.World self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Hello.World._ctor2)
        exception = 0
C       DO-NOT-DELETE splicer.end(Hello.World._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Hello_World__dtor_fi(self, exception)
        implicit none
C        in Hello.World self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Hello.World._dtor)
        exception = 0
C       DO-NOT-DELETE splicer.end(Hello.World._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Hello_World__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Hello.World._load)
        exception = 0
C       DO-NOT-DELETE splicer.end(Hello.World._load)
        end


C       
C       Method:  getMsg[]
C       

        subroutine Hello_World_getMsg_fi(self, retval, exception)
        implicit none
C        in Hello.World self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Hello.World.getMsg)
        retval = 'Hello world!'
        exception = 0
C       DO-NOT-DELETE splicer.end(Hello.World.getMsg)
        end


C       
C       Method:  foo[]
C       

        subroutine Hello_World_foo_fi(self, i, o, io, retval, exception)
        implicit none
C        in Hello.World self
        integer*8 :: self
C        in int i
        integer*4 :: i
C        out int o
        integer*4 :: o
C        inout int io
        integer*4 :: io
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Hello.World.foo)
        exception = 0
C       DO-NOT-DELETE splicer.end(Hello.World.foo)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
