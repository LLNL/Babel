C       
C       File:          Exceptions_FibException_Impl.f
C       Symbol:        Exceptions.FibException-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Exceptions.FibException
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Exceptions.FibException" (version 1.0)
C       
C       This exception is a base class for the Fibonacci Exceptions that are
C       thrown if the value is too large or the recursion depth is too deep.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Exceptions_FibException__ctor_fi(self, exception)
        implicit none
C        in Exceptions.FibException self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Exceptions_FibException__ctor2_fi(self, private_data,
     &     exception)
        implicit none
C        in Exceptions.FibException self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.FibException._ctor2)
C       Insert-Code-Here {Exceptions.FibException._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Exceptions.FibException._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Exceptions_FibException__dtor_fi(self, exception)
        implicit none
C        in Exceptions.FibException self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.FibException._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Exceptions.FibException._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Exceptions_FibException__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.FibException._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Exceptions.FibException._load)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
