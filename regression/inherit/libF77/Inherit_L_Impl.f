C       
C       File:          Inherit_L_Impl.f
C       Symbol:        Inherit.L-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Inherit.L
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Inherit.L" (version 1.1)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert-Code-Here {_miscellaneous_code_start} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Inherit_L__ctor_fi(self, exception)
        implicit none
C        in Inherit.L self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L._ctor)
C       Insert-Code-Here {Inherit.L._ctor} (_ctor method)
C       DO-NOT-DELETE splicer.end(Inherit.L._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Inherit_L__ctor2_fi(self, private_data, exception)
        implicit none
C        in Inherit.L self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L._ctor2)
C       Insert-Code-Here {Inherit.L._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Inherit.L._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Inherit_L__dtor_fi(self, exception)
        implicit none
C        in Inherit.L self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L._dtor)
C       Insert-Code-Here {Inherit.L._dtor} (_dtor method)
C       DO-NOT-DELETE splicer.end(Inherit.L._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Inherit_L__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L._load)
C       Insert-Code-Here {Inherit.L._load} (_load method)
C       DO-NOT-DELETE splicer.end(Inherit.L._load)
        end


C       
C       Method:  a[a]
C       

        subroutine Inherit_L_aa_fi(self, retval, exception)
        implicit none
C        in Inherit.L self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L.aa)
        retval = 'L.a'
C       DO-NOT-DELETE splicer.end(Inherit.L.aa)
        end


C       
C       Method:  a[2]
C       

        subroutine Inherit_L_a2_fi(self, i, retval, exception)
        implicit none
C        in Inherit.L self
        integer*8 :: self
C        in int i
        integer*4 :: i
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L.a2)
        retval = 'L.a2'
C       DO-NOT-DELETE splicer.end(Inherit.L.a2)
        end


C       
C       Method:  l[]
C       

        subroutine Inherit_L_l_fi(self, retval, exception)
        implicit none
C        in Inherit.L self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.L.l)
        retval = 'L.l'
C       DO-NOT-DELETE splicer.end(Inherit.L.l)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert-Code-Here {_miscellaneous_code_end} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
