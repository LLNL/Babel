C       
C       File:          Overload_ParentTest_Impl.f
C       Symbol:        Overload.ParentTest-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Overload.ParentTest
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Overload.ParentTest" (version 1.0)
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

        subroutine Overload_ParentTest__ctor_fi(self, exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Overload_ParentTest__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest._ctor2)
C       Insert-Code-Here {Overload.ParentTest._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Overload.ParentTest._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Overload_ParentTest__dtor_fi(self, exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Overload.ParentTest._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Overload_ParentTest__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Overload.ParentTest._load)
        end


C       
C       Method:  getValue[]
C       

        subroutine Overload_ParentTest_getValue_fi(self, retval, 
     &     exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValue)
        retval = 1
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValue)
        end


C       
C       Method:  getValue[Int]
C       

        subroutine Overload_ParentTest_getValueInt_fi(self, v, retval, 
     &     exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in int v
        integer*4 :: v
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueInt)
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueInt)
        end


C       
C       Method:  getValue[Bool]
C       

        subroutine Overload_ParentTest_getValueBool_fi(self, v, retval, 
     &     exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in bool v
        logical :: v
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueBool)
C       Insert the implementation here...
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueBool)
        end


C       
C       Method:  getValue[Double]
C       

        subroutine Overload_ParentTest_getValueDouble_fi(self, v, 
     &     retval, exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in double v
        double precision :: v
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDouble)
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDouble)
        end


C       
C       Method:  getValue[Dcomplex]
C       

        subroutine Overload_ParentTest_getValueDcomplex_fi(self, v, 
     &     retval, exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in dcomplex v
        double complex :: v
C        out dcomplex retval
        double complex :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueDcomplex)
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueDcomplex)
        end


C       
C       Method:  getValue[Float]
C       

        subroutine Overload_ParentTest_getValueFloat_fi(self, v, retval,
     &     exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in float v
        real :: v
C        out float retval
        real :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFloat)
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFloat)
        end


C       
C       Method:  getValue[Fcomplex]
C       

        subroutine Overload_ParentTest_getValueFcomplex_fi(self, v, 
     &     retval, exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in fcomplex v
        complex :: v
C        out fcomplex retval
        complex :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueFcomplex)
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueFcomplex)
        end


C       
C       Method:  getValue[String]
C       

        subroutine Overload_ParentTest_getValueString_fi(self, v, 
     &     retval, exception)
        implicit none
C        in Overload.ParentTest self
        integer*8 :: self
C        in string v
        character*(*) :: v
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Overload.ParentTest.getValueString)
        retval = v
C       DO-NOT-DELETE splicer.end(Overload.ParentTest.getValueString)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
