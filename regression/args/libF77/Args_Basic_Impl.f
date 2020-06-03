C       
C       File:          Args_Basic_Impl.f
C       Symbol:        Args.Basic-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Args.Basic
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Args.Basic" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert-Code-Here {_miscellaneous_code_start} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Args_Basic__ctor_fi(self, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic._ctor)
C       DO-NOT-DELETE splicer.end(Args.Basic._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Args_Basic__ctor2_fi(self, private_data, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic._ctor2)
C       DO-NOT-DELETE splicer.end(Args.Basic._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Args_Basic__dtor_fi(self, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic._dtor)
C       DO-NOT-DELETE splicer.end(Args.Basic._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Args_Basic__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic._load)
C       DO-NOT-DELETE splicer.end(Args.Basic._load)
        end


C       
C       Method:  returnbackbool[]
C       

        subroutine Args_Basic_returnbackbool_fi(self, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool)
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool)
        end


C       
C       Method:  passinbool[]
C       

        subroutine Args_Basic_passinbool_fi(self, b, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in bool b
        logical :: b
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinbool)
        if (b) then
           retval = .true.
        else
           retval = .false.
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passinbool)
        end


C       
C       Method:  passoutbool[]
C       

        subroutine Args_Basic_passoutbool_fi(self, b, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out bool b
        logical :: b
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool)
        b = .true.
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutbool)
        end


C       
C       Method:  passinoutbool[]
C       

        subroutine Args_Basic_passinoutbool_fi(self, b, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout bool b
        logical :: b
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool)
        b = .not. b
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool)
        end


C       
C       Method:  passeverywherebool[]
C       

        subroutine Args_Basic_passeverywherebool_fi(self, b1, b2, b3, 
     &     retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in bool b1
        logical :: b1
C        out bool b2
        logical :: b2
C        inout bool b3
        logical :: b3
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool)
        b2 = .true.
        b3 = .not. b3
        if (b1) then
           retval = .true.
        else
           retval = .false.
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool)
        end


C       
C       Method:  returnbackchar[]
C       

        subroutine Args_Basic_returnbackchar_fi(self, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out char retval
        character :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar)
        retval = '3'
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar)
        end


C       
C       Method:  passinchar[]
C       

        subroutine Args_Basic_passinchar_fi(self, c, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in char c
        character :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinchar)
        retval = (c .eq. '3')
C       DO-NOT-DELETE splicer.end(Args.Basic.passinchar)
        end


C       
C       Method:  passoutchar[]
C       

        subroutine Args_Basic_passoutchar_fi(self, c, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out char c
        character :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar)
        c = '3'
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutchar)
        end


C       
C       Method:  passinoutchar[]
C       

        subroutine Args_Basic_passinoutchar_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout char c
        character :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar)
        retval = .false.
        if (c .ge. 'A' .and. c .le. 'Z') then
           c = char(ichar(c) + 32)
           retval = .true.
        else
           if (c .ge. 'a' .and. c .le. 'z') then
              c = char(ichar(c) - 32)
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar)
        end


C       
C       Method:  passeverywherechar[]
C       

        subroutine Args_Basic_passeverywherechar_fi(self, c1, c2, c3, 
     &     retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in char c1
        character :: c1
C        out char c2
        character :: c2
C        inout char c3
        character :: c3
C        out char retval
        character :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar)
        retval = ' '
        c2 = '3'
        if (c3 .ge. 'A' .and. c3 .le. 'Z') then
           c3 = char(ichar(c3) + 32)
        else
           if (c3 .ge. 'a' .and. c3 .le. 'z') then
              c3 = char(ichar(c3) - 32)
           endif
        endif
        if (c1 .eq. '3') then
           retval = '3'
        else
           retval = ' '
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar)
        end


C       
C       Method:  returnbackint[]
C       

        subroutine Args_Basic_returnbackint_fi(self, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint)
        retval = 3
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackint)
        end


C       
C       Method:  passinint[]
C       

        subroutine Args_Basic_passinint_fi(self, i, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in int i
        integer*4 :: i
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinint)
        retval = (i .eq. 3)
C       DO-NOT-DELETE splicer.end(Args.Basic.passinint)
        end


C       
C       Method:  passoutint[]
C       

        subroutine Args_Basic_passoutint_fi(self, i, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out int i
        integer*4 :: i
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutint)
        i = 3
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutint)
        end


C       
C       Method:  passinoutint[]
C       

        subroutine Args_Basic_passinoutint_fi(self, i, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout int i
        integer*4 :: i
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint)
        i = -i
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutint)
        end


C       
C       Method:  passeverywhereint[]
C       

        subroutine Args_Basic_passeverywhereint_fi(self, i1, i2, i3, 
     &     retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in int i1
        integer*4 :: i1
C        out int i2
        integer*4 :: i2
C        inout int i3
        integer*4 :: i3
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint)
        i2 = 3
        i3 = -i3
        if (i1 .eq. 3) then
           retval = 3
        else
           retval = 0
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint)
        end


C       
C       Method:  returnbacklong[]
C       

        subroutine Args_Basic_returnbacklong_fi(self, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out long retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong)
        retval = 3
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong)
        end


C       
C       Method:  passinlong[]
C       

        subroutine Args_Basic_passinlong_fi(self, l, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in long l
        integer*8 :: l
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinlong)
        retval = (l .eq. 3)
C       DO-NOT-DELETE splicer.end(Args.Basic.passinlong)
        end


C       
C       Method:  passoutlong[]
C       

        subroutine Args_Basic_passoutlong_fi(self, l, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out long l
        integer*8 :: l
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong)
        l = 3
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutlong)
        end


C       
C       Method:  passinoutlong[]
C       

        subroutine Args_Basic_passinoutlong_fi(self, l, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout long l
        integer*8 :: l
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong)
        l = -l
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong)
        end


C       
C       Method:  passeverywherelong[]
C       

        subroutine Args_Basic_passeverywherelong_fi(self, l1, l2, l3, 
     &     retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in long l1
        integer*8 :: l1
C        out long l2
        integer*8 :: l2
C        inout long l3
        integer*8 :: l3
C        out long retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong)
        l2 = 3
        l3 = -l3
        if (l1 .eq. 3) then
           retval = 3
        else
           retval = 0
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong)
        end


C       
C       Method:  returnbackfloat[]
C       

        subroutine Args_Basic_returnbackfloat_fi(self, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out float retval
        real :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat)
        retval = 3.1
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat)
        end


C       
C       Method:  passinfloat[]
C       

        subroutine Args_Basic_passinfloat_fi(self, f, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in float f
        real :: f
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat)
        retval = (f .eq. 3.1)
C       DO-NOT-DELETE splicer.end(Args.Basic.passinfloat)
        end


C       
C       Method:  passoutfloat[]
C       

        subroutine Args_Basic_passoutfloat_fi(self, f, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out float f
        real :: f
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat)
        f = 3.1
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat)
        end


C       
C       Method:  passinoutfloat[]
C       

        subroutine Args_Basic_passinoutfloat_fi(self, f, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout float f
        real :: f
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat)
        f = -f
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat)
        end


C       
C       Method:  passeverywherefloat[]
C       

        subroutine Args_Basic_passeverywherefloat_fi(self, f1, f2, f3, 
     &     retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in float f1
        real :: f1
C        out float f2
        real :: f2
C        inout float f3
        real :: f3
C        out float retval
        real :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat)
        f2 = 3.1
        f3 = -f3
        if (f1 .eq. 3.1) then
           retval = 3.1
        else
           retval = 0.0
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat)
        end


C       
C       Method:  returnbackdouble[]
C       

        subroutine Args_Basic_returnbackdouble_fi(self, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble)
        retval = 3.14d0
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble)
        end


C       
C       Method:  passindouble[]
C       

        subroutine Args_Basic_passindouble_fi(self, d, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in double d
        double precision :: d
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passindouble)
        retval = (d .eq. 3.14d0)
C       DO-NOT-DELETE splicer.end(Args.Basic.passindouble)
        end


C       
C       Method:  passoutdouble[]
C       

        subroutine Args_Basic_passoutdouble_fi(self, d, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out double d
        double precision :: d
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble)
        d = 3.14d0
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble)
        end


C       
C       Method:  passinoutdouble[]
C       

        subroutine Args_Basic_passinoutdouble_fi(self, d, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout double d
        double precision :: d
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble)
        d = -d
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble)
        end


C       
C       Method:  passeverywheredouble[]
C       

        subroutine Args_Basic_passeverywheredouble_fi(self, d1, d2, d3, 
     &     retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in double d1
        double precision :: d1
C        out double d2
        double precision :: d2
C        inout double d3
        double precision :: d3
C        out double retval
        double precision :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble)
        d2 = 3.14d0
        d3 = -d3
        if (d1 .eq. 3.14d0) then
           retval = 3.14d0
        else
           retval = 0.0d0
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble)
        end


C       
C       Method:  returnbackfcomplex[]
C       

        subroutine Args_Basic_returnbackfcomplex_fi(self, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out fcomplex retval
        complex :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex)
        retval = (3.1, 3.1)
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex)
        end


C       
C       Method:  passinfcomplex[]
C       

        subroutine Args_Basic_passinfcomplex_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in fcomplex c
        complex :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex)
        if (real(c) .eq. 3.1 .and. aimag(c) .eq. 3.1) then
           retval = .true.
        else
           retval = .false.
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex)
        end


C       
C       Method:  passoutfcomplex[]
C       

        subroutine Args_Basic_passoutfcomplex_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out fcomplex c
        complex :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex)
        c = (3.1, 3.1)
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex)
        end


C       
C       Method:  passinoutfcomplex[]
C       

        subroutine Args_Basic_passinoutfcomplex_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout fcomplex c
        complex :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex)
        c = conjg(c)
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex)
        end


C       
C       Method:  passeverywherefcomplex[]
C       

        subroutine Args_Basic_passeverywherefcomplex_fi(self, c1, c2, 
     &     c3, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in fcomplex c1
        complex :: c1
C        out fcomplex c2
        complex :: c2
C        inout fcomplex c3
        complex :: c3
C        out fcomplex retval
        complex :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex)
        c2 = (3.1, 3.1)
        c3 = conjg(c3)
        if (real(c1) .eq. 3.1 .and. aimag(c1) .eq. 3.1) then
           retval = c1
        else
           retval = (0.0, 0.0)
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex)
        end


C       
C       Method:  returnbackdcomplex[]
C       

        subroutine Args_Basic_returnbackdcomplex_fi(self, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out dcomplex retval
        double complex :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex)
        retval = (3.14d0, 3.14d0)
C       DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex)
        end


C       
C       Method:  passindcomplex[]
C       

        subroutine Args_Basic_passindcomplex_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in dcomplex c
        double complex :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex)
        if (3.14d0 .eq. dreal(c) .and.
     $       3.14d0 .eq. dimag(c)) then
           retval = .true.
        else
           retval = .false.
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex)
        end


C       
C       Method:  passoutdcomplex[]
C       

        subroutine Args_Basic_passoutdcomplex_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        out dcomplex c
        double complex :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex)
        c = (3.14d0, 3.14d0)
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex)
        end


C       
C       Method:  passinoutdcomplex[]
C       

        subroutine Args_Basic_passinoutdcomplex_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        inout dcomplex c
        double complex :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex)
        c = conjg(c)
        retval = .true.
C       DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex)
        end


C       
C       Method:  passeverywheredcomplex[]
C       

        subroutine Args_Basic_passeverywheredcomplex_fi(self, c1, c2, 
     &     c3, retval, exception)
        implicit none
C        in Args.Basic self
        integer*8 :: self
C        in dcomplex c1
        double complex :: c1
C        out dcomplex c2
        double complex :: c2
C        inout dcomplex c3
        double complex :: c3
C        out dcomplex retval
        double complex :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex)
        c2 = (3.14d0, 3.14d0)
        c3 = conjg(c3)
        if (3.14d0 .eq. dreal(c1) .and.
     $       3.14d0 .eq. dimag(c1)) then
           retval = c2
        else
           retval = (0.0d0, 0.0d0)
        endif
C       DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert-Code-Here {_miscellaneous_code_end} (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
