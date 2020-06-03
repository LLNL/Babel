C       
C       File:          Inherit_F2_Impl.f
C       Symbol:        Inherit.F2-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Inherit.F2
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Inherit.F2" (version 1.1)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Inherit_F2__ctor_fi(self, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.F2._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Inherit_F2__ctor2_fi(self, private_data, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2._ctor2)
C       Insert-Code-Here {Inherit.F2._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Inherit.F2._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Inherit_F2__dtor_fi(self, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.F2._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Inherit_F2__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.F2._load)
        end


C       
C       Method:  c[]
C       

        subroutine Inherit_F2_c_fi(self, retval, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2.c)
        retval = 'F2.c'
C       DO-NOT-DELETE splicer.end(Inherit.F2.c)
        end


C       
C       Method:  a[]
C       

        subroutine Inherit_F2_a_fi(self, retval, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2.a)
        retval = 'F2.a'
C       DO-NOT-DELETE splicer.end(Inherit.F2.a)
        end


C       
C       Method:  b[]
C       

        subroutine Inherit_F2_b_fi(self, retval, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2.b)
        retval = 'F2.b'
C       DO-NOT-DELETE splicer.end(Inherit.F2.b)
        end


C       
C       Method:  f[]
C       

        subroutine Inherit_F2_f_fi(self, retval, exception)
        implicit none
C        in Inherit.F2 self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.F2.f)
        retval = 'F2.f'
C       DO-NOT-DELETE splicer.end(Inherit.F2.f)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
