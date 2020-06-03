C       
C       File:          Inherit_E_Impl.f
C       Symbol:        Inherit.E-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Inherit.E
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Inherit.E" (version 1.1)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Inherit_E__ctor_fi(self, exception)
        implicit none
C        in Inherit.E self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.E._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.E._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Inherit_E__ctor2_fi(self, private_data, exception)
        implicit none
C        in Inherit.E self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.E._ctor2)
C       Insert-Code-Here {Inherit.E._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Inherit.E._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Inherit_E__dtor_fi(self, exception)
        implicit none
C        in Inherit.E self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.E._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.E._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Inherit_E__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.E._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.E._load)
        end


C       
C       Method:  e[]
C       

        subroutine Inherit_E_e_fi(self, retval, exception)
        implicit none
C        in Inherit.E self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.E.e)
        retval = 'E.e'
C       DO-NOT-DELETE splicer.end(Inherit.E.e)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
