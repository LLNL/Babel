C       
C       File:          Inherit_J_Impl.f
C       Symbol:        Inherit.J-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Inherit.J
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Inherit.J" (version 1.1)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Inherit_J__ctor_fi(self, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.J._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Inherit_J__ctor2_fi(self, private_data, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J._ctor2)
C       Insert-Code-Here {Inherit.J._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Inherit.J._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Inherit_J__dtor_fi(self, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.J._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Inherit_J__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.J._load)
        end


C       
C       Method:  j[]
C       

        subroutine Inherit_J_j_fi(self, retval, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J.j)
        retval = 'J.j'
C       DO-NOT-DELETE splicer.end(Inherit.J.j)
        end


C       
C       Method:  e[]
C       

        subroutine Inherit_J_e_fi(self, retval, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J.e)
        character*5 temp
        integer*4 i, j
        integer*8 throwaway
C        retval = 'J.xxx'
        call Inherit_J_super_e_f(self, temp, throwaway)
        retval = 'J.'//temp 
C        retval(3:5) = temp       
C        do i = 1, 3
C           j = i+2
C           retval(j) = temp(i)
C        enddo

C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Inherit.J.e)
        end


C       
C       Method:  c[]
C       

        subroutine Inherit_J_c_fi(self, retval, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J.c)
C       Insert the implementation here...
        character*5 temp
        integer*4 i, j
        integer*8 throwaway
C        retval = 'J.xxx'
        call Inherit_J_super_c_f(self, temp, throwaway)
        retval = 'J.'//temp 
C        retval(3:5) = temp
C        do i = 1, 3
C           j = i+2
C           retval(j) = temp(i)
C        enddo
C       DO-NOT-DELETE splicer.end(Inherit.J.c)
        end


C       
C       Method:  a[]
C       

        subroutine Inherit_J_a_fi(self, retval, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J.a)
C       Insert the implementation here...
        retval = 'J.a'
C       DO-NOT-DELETE splicer.end(Inherit.J.a)
        end


C       
C       Method:  b[]
C       

        subroutine Inherit_J_b_fi(self, retval, exception)
        implicit none
C        in Inherit.J self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Inherit.J.b)
C       Insert the implementation here...
        retval = 'J.b'
C       DO-NOT-DELETE splicer.end(Inherit.J.b)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
