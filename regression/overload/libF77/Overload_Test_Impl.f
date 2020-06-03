C       
C       File:          Overload_Test_Impl.f
C       Symbol:        Overload.Test-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Overload.Test
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Overload.Test" (version 1.0)
C       
C       This class is used as the work-horse, returning the value passed
C       in.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Overload_Test__ctor_fi(self, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Overload.Test._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Overload_Test__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test._ctor2)
C       Insert-Code-Here {Overload.Test._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Overload.Test._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Overload_Test__dtor_fi(self, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Overload.Test._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Overload_Test__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Overload.Test._load)
        end


C       
C       Method:  getValue[IntDouble]
C       

        subroutine Overload_Test_getValueIntDouble_fi(self, a, b, 
     &     retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in int a
        integer*4 :: a
C        in double b
        double precision :: b
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDouble)
        retval = a + b
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDouble)
        end


C       
C       Method:  getValue[DoubleInt]
C       

        subroutine Overload_Test_getValueDoubleInt_fi(self, a, b, 
     &     retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in double a
        double precision :: a
C        in int b
        integer*4 :: b
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleInt)
        retval = a + b
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleInt)
        end


C       
C       Method:  getValue[IntDoubleFloat]
C       

        subroutine Overload_Test_getValueIntDoubleFloat_fi(self, a, b, 
     &     c, retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in int a
        integer*4 :: a
C        in double b
        double precision :: b
C        in float c
        real :: c
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntDoubleFloat)
        retval = a + b + c
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueIntDoubleFloat)
        end


C       
C       Method:  getValue[DoubleIntFloat]
C       

        subroutine Overload_Test_getValueDoubleIntFloat_fi(self, a, b, 
     &     c, retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in double a
        double precision :: a
C        in int b
        integer*4 :: b
C        in float c
        real :: c
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleIntFloat)
        retval = a + b + c
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleIntFloat)
        end


C       
C       Method:  getValue[IntFloatDouble]
C       

        subroutine Overload_Test_getValueIntFloatDouble_fi(self, a, b, 
     &     c, retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in int a
        integer*4 :: a
C        in float b
        real :: b
C        in double c
        double precision :: c
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueIntFloatDouble)
        retval = a + b + c
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueIntFloatDouble)
        end


C       
C       Method:  getValue[DoubleFloatInt]
C       

        subroutine Overload_Test_getValueDoubleFloatInt_fi(self, a, b, 
     &     c, retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in double a
        double precision :: a
C        in float b
        real :: b
C        in int c
        integer*4 :: c
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueDoubleFloatInt)
        retval = a + b + c
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueDoubleFloatInt)
        end


C       
C       Method:  getValue[FloatIntDouble]
C       

        subroutine Overload_Test_getValueFloatIntDouble_fi(self, a, b, 
     &     c, retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in float a
        real :: a
C        in int b
        integer*4 :: b
C        in double c
        double precision :: c
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatIntDouble)
        retval = a + b + c
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatIntDouble)
        end


C       
C       Method:  getValue[FloatDoubleInt]
C       

        subroutine Overload_Test_getValueFloatDoubleInt_fi(self, a, b, 
     &     c, retval, exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in float a
        real :: a
C        in double b
        double precision :: b
C        in int c
        integer*4 :: c
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueFloatDoubleInt)
        retval = a + b + c
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueFloatDoubleInt)
        end


C       
C       Method:  getValue[Exception]
C       

        subroutine Overload_Test_getValueException_fi(self, v, retval, 
     &     exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in Overload.AnException v
        integer*8 :: v
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueException)
        call Overload_AnException_getNote_f(v, retval,exception)
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueException)
        end


C       
C       Method:  getValue[AClass]
C       

        subroutine Overload_Test_getValueAClass_fi(self, v, retval, 
     &     exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in Overload.AClass v
        integer*8 :: v
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueAClass)
        call Overload_AClass_getValue_f(v, retval, exception)
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueAClass)
        end


C       
C       Method:  getValue[BClass]
C       

        subroutine Overload_Test_getValueBClass_fi(self, v, retval, 
     &     exception)
        implicit none
C        in Overload.Test self
        integer*8 :: self
C        in Overload.BClass v
        integer*8 :: v
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.Test.getValueBClass)
        call Overload_BClass_getValue_f(v, retval, exception)
C       DO-NOT-DELETE splicer.end(Overload.Test.getValueBClass)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
