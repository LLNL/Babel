C       
C       File:          Inherit_I_Impl.f
C       Symbol:        Inherit.I-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Inherit.I
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Inherit.I" (version 1.1)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Inherit_I__ctor_fi(self, exception)
        implicit none
C        in Inherit.I self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.I._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.I._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Inherit_I__ctor2_fi(self, private_data, exception)
        implicit none
C        in Inherit.I self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.I._ctor2)
C       Insert-Code-Here {Inherit.I._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Inherit.I._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Inherit_I__dtor_fi(self, exception)
        implicit none
C        in Inherit.I self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.I._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.I._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Inherit_I__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.I._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.I._load)
        end


C       
C       Method:  a[]
C       

        subroutine Inherit_I_a_fi(self, retval, exception)
        implicit none
C        in Inherit.I self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.I.a)
        retval = 'I.a'
C       DO-NOT-DELETE splicer.end(Inherit.I.a)
        end


C       
C       Method:  h[]
C       

        subroutine Inherit_I_h_fi(self, retval, exception)
        implicit none
C        in Inherit.I self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.I.h)
        retval = 'I.h'
C       DO-NOT-DELETE splicer.end(Inherit.I.h)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
