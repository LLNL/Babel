C       
C       File:          Strings_Cstring_Impl.f
C       Symbol:        Strings.Cstring-v1.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Strings.Cstring
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Strings.Cstring" (version 1.1)
C       
C       Class to allow testing of string passing using every possible mode.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Strings_Cstring__ctor_fi(self, exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Strings.Cstring._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Strings_Cstring__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor2)
C       Insert-Code-Here {Strings.Cstring._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Strings.Cstring._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Strings_Cstring__dtor_fi(self, exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Strings.Cstring._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Strings_Cstring__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Strings.Cstring._load)
        end


C       
C       Method:  returnback[]
C       If <code>nonNull</code> is <code>true</code>, this will
C       return "Three"; otherwise, it will return a NULL or empty string.
C       

        subroutine Strings_Cstring_returnback_fi(self, nonNull, retval, 
     &     exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        in bool nonNull
        logical :: nonNull
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback)
        if (nonNull) then
           retval = 'Three'
        else
           retval = ''
        endif
C       DO-NOT-DELETE splicer.end(Strings.Cstring.returnback)
        end


C       
C       Method:  passin[]
C       This will return <code>true</code> iff <code>c</code> equals "Three".
C       

        subroutine Strings_Cstring_passin_fi(self, c, retval, exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        in string c
        character*(*) :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring.passin)
        retval = (c .eq. 'Three')
C       DO-NOT-DELETE splicer.end(Strings.Cstring.passin)
        end


C       
C       Method:  passout[]
C       If <code>nonNull</code> is <code>true</code>, this will return
C       "Three" in <code>c</code>; otherwise, it will return a null or
C       empty string. The return value is <code>false</code> iff 
C       the outgoing value of <code>c</code> is <code>null</code>.
C       

        subroutine Strings_Cstring_passout_fi(self, nonNull, c, retval, 
     &     exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        in bool nonNull
        logical :: nonNull
C        out string c
        character*(*) :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring.passout)
        if (nonNull) then
           c = 'Three'
           retval = .true.
        else
           c = ''
           retval = .false.
        endif
C       DO-NOT-DELETE splicer.end(Strings.Cstring.passout)
        end


C       
C       Method:  passinout[]
C       

        subroutine Strings_Cstring_passinout_fi(self, c, retval, 
     &     exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        inout string c
        character*(*) :: c
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout)
        integer i
        retval = .false.
        if (len(c) .ge. 1) then
           do i = len(c), 1, -1
              if (c(i:i) .ne. ' ') then
                 if (i .lt. len(c)) then
                    c(i+1:i+1) = 's'
                    retval = .true.
                 endif
                 goto 100
              endif
           end do
 100       if (c(1:1) .ge. 'a' .and. c(1:1) .le. 'z') then
              c(1:1) = char(ichar(c(1:1)) - 32)
           else
              if (c(1:1) .ge. 'A' .and. c(1:1) .le. 'Z') then
                 c(1:1) = char(ichar(c(1:1)) + 32)
              endif
           endif
        else
           retval = .false.
        endif
C       DO-NOT-DELETE splicer.end(Strings.Cstring.passinout)
        end


C       
C       Method:  passeverywhere[]
C       

        subroutine Strings_Cstring_passeverywhere_fi(self, c1, c2, c3, 
     &     retval, exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        in string c1
        character*(*) :: c1
C        out string c2
        character*(*) :: c2
C        inout string c3
        character*(*) :: c3
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere)
        integer i
        c2 = 'Three'
        if (c1 .eq. 'Three') then
           retval = 'Three'
        else
           retval = ''
        endif
        if (len(c3) .ge. 1) then
           do i = len(c3), 1, -1
              if (c3(i:i) .ne. ' ') then
                 c3(i:i) = ' '
                 goto 100
              endif
           end do
 100       if (c3(1:1) .ge. 'a' .and. c3(1:1) .le. 'z') then
              c3(1:1) = char(ichar(c3(1:1)) - 32)
           else
              if (c3(1:1) .ge. 'A' .and. c3(1:1) .le. 'Z') then
                 c3(1:1) = char(ichar(c3(1:1)) + 32)
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere)
        end


C       
C       Method:  mixedarguments[]
C        return true iff s1 == s2 and c1 == c2 
C       

        subroutine Strings_Cstring_mixedarguments_fi(self, s1, c1, s2, 
     &     c2, retval, exception)
        implicit none
C        in Strings.Cstring self
        integer*8 :: self
C        in string s1
        character*(*) :: s1
C        in char c1
        character :: c1
C        in string s2
        character*(*) :: s2
C        in char c2
        character :: c2
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments)
        retval = ((c1 .eq. c2) .and. (s1 .eq. s2))
C       DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
