C       
C       File:          Inherit_F_Impl.f
C       Symbol:        Inherit.F-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Inherit.F
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Inherit.F" (version 1.1)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Inherit_F__ctor_fi(self, exception)
        implicit none
C        in Inherit.F self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.F._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Inherit_F__ctor2_fi(self, private_data, exception)
        implicit none
C        in Inherit.F self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F._ctor2)
C       Insert-Code-Here {Inherit.F._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Inherit.F._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Inherit_F__dtor_fi(self, exception)
        implicit none
C        in Inherit.F self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.F._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Inherit_F__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.F._load)
        end


C       
C       Method:  f[]
C       

        subroutine Inherit_F_f_fi(self, retval, exception)
        implicit none
C        in Inherit.F self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F.f)
        retval = 'F.f'
C       DO-NOT-DELETE splicer.end(Inherit.F.f)
        end


C       
C       Method:  a[]
C       

        subroutine Inherit_F_a_fi(self, retval, exception)
        implicit none
C        in Inherit.F self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F.a)
        retval = 'F.a'
C       DO-NOT-DELETE splicer.end(Inherit.F.a)
        end


C       
C       Method:  b[]
C       

        subroutine Inherit_F_b_fi(self, retval, exception)
        implicit none
C        in Inherit.F self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F.b)
        retval = 'F.b'
C       DO-NOT-DELETE splicer.end(Inherit.F.b)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
