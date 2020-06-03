C       
C       File:          s_StructTest_Impl.f
C       Symbol:        s.StructTest-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for s.StructTest
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "s.StructTest" (version 1.0)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       insert code here (extra code)


C     support routines for structs of type s.Simple
      
      subroutine initSimple(s, exception)
      implicit none
C     out s.Simple s
      integer*8 s
C     out sidl.BaseInterface exception
      integer*8 exception

      integer*8 d_long

      integer*8 z_long
      include 's_Color.inc'

C     Variables are used instead of constants to ensure F77 infers the
C     correct function signature below (e.g., 32-bit vs 64-bit)
      d_long = 3
      z_long = 0
      call s_Simple_Init_f(s)
      call s_Simple_Set_d_bool_f(s, .true.)
      call s_Simple_Set_d_char_f(s, '3')
      call s_Simple_Set_d_dcomplex_f(s, (3.14d0, 3.14d0))
      call s_Simple_Set_d_fcomplex_f(s, (3.1, 3.1))
      call s_Simple_Set_d_double_f(s, 3.14d0)
      call s_Simple_Set_d_float_f(s, 3.1)
      call s_Simple_Set_d_opaque_f(s, z_long)
      call s_Simple_Set_d_int_f(s, 3)
      call s_Simple_Set_d_long_f(s, d_long)
      call s_Simple_Set_d_enum_f(s, blue)
      end

      subroutine invertSimple(s)
      implicit none
C     in s.Simple s
      integer*8 s
      
      include 's_Color.inc'
      logical d_bool
      character d_char
      double precision d_double
      real d_float
      integer*4 d_int
      integer*8 d_long
      double complex d_dcomplex
      complex d_fcomplex

      
      call s_Simple_Get_d_bool_f(s, d_bool)
      if (d_bool .eqv. .false.) then
         d_bool=.true.
      else
         d_bool=.false.
      endif
      call s_Simple_Set_d_bool_f(s, d_bool)
      
      call s_Simple_Get_d_double_f(s, d_double)
      d_double = -d_double
      call s_Simple_Set_d_double_f(s, d_double)

      call s_Simple_Get_d_float_f(s, d_float)
      d_float = -d_float
      call s_Simple_Set_d_float_f(s, d_float)

      call s_Simple_Get_d_int_f(s, d_int)
      d_int = -d_int
      call s_Simple_Set_d_int_f(s, d_int)

      call s_Simple_Get_d_long_f(s, d_long)
      d_long = -d_long
      call s_Simple_Set_d_long_f(s, d_long)

      call s_Simple_Get_d_dcomplex_f(s, d_dcomplex)
      d_dcomplex = conjg(d_dcomplex)
      call s_Simple_Set_d_dcomplex_f(s, d_dcomplex) 

      call s_Simple_Get_d_fcomplex_f(s, d_fcomplex)
      d_fcomplex = conjg(d_fcomplex)
      call s_Simple_Set_d_fcomplex_f(s, d_fcomplex)
      
      call s_Simple_Set_d_enum_f(s, red)

      call s_Simple_Get_d_char_f(s, d_char)
      if (d_char .ge. 'A' .and. d_char .le. 'Z') then
         d_char = char(ichar(d_char) + 32)
      else
         if (d_char .ge. 'a' .and. d_char .le. 'z') then
            d_char = char(ichar(d_char) - 32)
         endif
      endif
      call s_Simple_Set_d_char_f(s, d_char)
      
      end
      
      logical function checkSimple(s)
      implicit none
C          in s.Simple s
      integer*8 s

      logical d_bool
      character d_char
      double complex d_dcomplex
      double precision d_double
      real d_float
      complex d_fcomplex
      integer*4 d_int
      integer*8 d_long
      integer*8 d_opaque
      integer*8 d_enum

      double precision eps 
      parameter(eps=1E-6)

      include 's_Color.inc'

      call s_Simple_Get_d_bool_f(s, d_bool)
      call s_Simple_Get_d_char_f(s, d_char)
      call s_Simple_Get_d_dcomplex_f(s, d_dcomplex)
      call s_Simple_Get_d_double_f(s, d_double)
      call s_Simple_Get_d_float_f(s, d_float)
      call s_Simple_Get_d_fcomplex_f(s, d_fcomplex)
      call s_Simple_Get_d_int_f(s, d_int)
      call s_Simple_Get_d_long_f(s, d_long)
      call s_Simple_Get_d_opaque_f(s, d_opaque)
      call s_Simple_Get_d_enum_f(s, d_enum)
      
      if ((d_bool .eqv. .true.) .and. 
     &     (d_char .eq. '3' ) .and. 
     &     (abs(dble(d_dcomplex) - 3.14d0) .lt. eps) .and. 
     &     (abs(dimag(d_dcomplex) - 3.14d0) .lt. eps) .and. 
     &     (abs(d_double - 3.14d0) .lt. eps ) .and. 
     &     (abs(d_float - 3.1) .lt. eps ) .and. 
     &     (abs(real(d_fcomplex) - 3.1) .lt. eps) .and. 
     &     (abs(aimag(d_fcomplex) - 3.1) .lt. eps) .and. 
     &     (d_int .eq. 3 ) .and. 
     &     (d_long .eq. 3) .and. 
     &     (d_opaque .eq. 0) .and. 
     &     (d_enum .eq. blue )) then
         checkSimple=.true.
      else
         checkSimple=.false.
      endif
      return
      end
      
      logical function isLower(s)
      implicit none
      character*512 s
      integer*4 l, i

      l=len(s)
      do 10 i=1,l
         if(s(i:i) .le. 'Z' .and. s(i:i) .ge. 'A') then
            isLower = .false.
            return
         endif
 10   continue
      isLower = .true.
      return
      end
      
      subroutine toLower(s)
      implicit none
      character*512 s
      integer*4 l, i, offset
      l=len(s)
      offset=ichar('a') - ichar('A')
      do 10 i=1,l
         if(s(i:i) .le. 'Z' .and. s(i:i) .ge. 'A') then
            s(i:i)= char(ichar(s(i:i)) + offset)
         endif
 10   continue
      end

      subroutine toUpper(s)
      implicit none
      character*512 s
      integer*4 l, i, offset
      l=len(s)
      offset=ichar('a') - ichar('A')
      do 10 i=1,l
         if(s(i:i) .le. 'z' .and. s(i:i) .ge. 'a') then
            s(i:i)= char(ichar(s(i:i)) - offset)
         endif
 10   continue
      end
      
C     support routines for structs of type s.Hard
      subroutine initHard(s, exception)
        implicit none
C        out s.Simple s
        integer*8 s
C        out sidl.BaseInterface exception
        integer*8 exception

        integer*4 i
        integer*8 d_string
        integer*8 d_object
        integer*8 d_interface
        integer*8 d_array
        integer*8 d_objectArray

        call s_Hard_Init_f(s)
        
        call sidl_string__array_create1d_f(1, d_string)
        call sidl_string__array_set1_f(d_string, 0, 'Three')
        call s_Hard_Set_d_string_f(s, d_string)

        call sidl_BaseClass__create_f(d_object, exception)
        call s_Hard_Set_d_object_f(s, d_object)
        if (exception .ne. 0) then
           return
        endif
        call sidl_BaseInterface__cast_f(d_object,
     &       d_interface, exception)
        if (exception .ne. 0) then
           return
        endif
        call s_Hard_Set_d_interface_f(s, d_interface)

        call sidl_double__array_create1d_f(3, d_array)
        call sidl_double__array_set1_f(d_array, 0, 1.0d0)
        call sidl_double__array_set1_f(d_array, 1, 2.0d0)
        call sidl_double__array_set1_f(d_array, 2, 3.0d0)
        call s_Hard_Set_d_array_f(s, d_array)

        call sidl_BaseClass__array_create1d_f(3, d_objectArray)
        do 10 i = 0, 2
           call sidl_BaseClass__create_f(d_object, exception)
           if (exception .ne. 0) then
              return
           endif
           call sidl_BaseClass__array_set1_f(d_objectArray,
     &          i, d_object)
           call sidl_BaseClass_deleteRef_f(d_object, exception)
           if (exception .ne. 0) then
              return
           endif
 10     continue
        call s_Hard_Set_d_objectArray_f(s, d_objectArray)
        end

      subroutine invertHard(s)
C      in s.Hard s
      integer*8 s

      integer*8 d_object
      integer*8 d_interface
      integer*8 d_string
      integer*8 d_array
      integer*8 d_objectArray

      integer*8 exception, obj, null_obj
      real*8 d, t0, t1
      integer*4 i, j 
      character*512 str
      logical b
      logical isLower

      null_obj = 0
      
      call s_Hard_Get_d_object_f(s, d_object)
      call s_Hard_Get_d_interface_f(s, d_interface)
      call s_Hard_Get_d_string_f(s, d_string)
      call s_Hard_Get_d_array_f(s, d_array)
      call s_Hard_Get_d_objectArray_f(s, d_objectArray)
      
      if (d_string .ne. 0 ) then
         call sidl_string__array_dimen_f(d_string, i)
         call sidl_string__array_length_f(d_string, 0, j)
         if((i .eq. 1) .and. (j .eq. 1)) then
            call sidl_string__array_get1_f(d_string, 0, str)
            if(isLower(str)) then
               call toUpper(str)
            else
               call toLower(str)
            endif
            call sidl_string__array_set1_f(d_string, 0, str)
         endif
      endif

      if ((d_object .ne. 0 ).and. (d_interface .ne. 0)) then
         call sidl_BaseInterface_isSame_f(d_object, d_interface,
     &        b, exception)
         if (exception .eq. 0) then
            call sidl_BaseInterface_deleteRef_f(d_interface, exception)
            if (b .eqv. .true.) then
               call sidl_BaseClass__create_f(obj, exception)
               call sidl_BaseInterface__cast_f(obj,
     &              d_interface, exception)
               call sidl_BaseInterface_deleteRef_f(obj, exception)
            else
               call sidl_BaseInterface__cast_f(d_object,
     &              d_interface, exception)
            endif
            call s_Hard_Set_d_interface_f(s, d_interface)
         endif
      endif

      if(d_array .ne. 0) then
         call sidl_double__array_dimen_f(d_array, i)
         call sidl_double__array_length_f(d_array, 0, j)
         if ((i .eq. 1) .and. (j .eq. 3 )) then
            call sidl_double__array_get1_f(d_array, 0, t0)
            call sidl_double__array_get1_f(d_array, 2, t1)
            call sidl_double__array_set1_f(d_array, 0, t1)
            call sidl_double__array_set1_f(d_array, 2, t0)
         endif
      endif

      call sidl_BaseClass__array_dimen_f(d_objectArray, i)
      call sidl_BaseClass__array_length_f(d_objectArray, 0, j)
      if ((i .eq. 1) .and. (j .eq. 3 )) then
         call sidl_BaseClass__array_get1_f(d_objectArray, 1, obj)
         if (obj .ne. 0) then
            call sidl_BaseClass__array_set1_f(d_objectArray, 1,
     &           null_obj)
            call sidl_BaseInterface_deleteRef_f(obj, exception)
         else 
            call sidl_BaseClass__create_f(obj, exception)
            call sidl_BaseClass__array_set1_f(d_objectArray, 1,
     &           obj)
         endif
      endif
      
      end

      logical function checkHard(s)
      implicit none
C      in s.Hard s
      integer*8 s

      integer*8 d_object
      integer*8 d_interface
      integer*8 d_string
      integer*8 d_array
      integer*8 d_objectArray

      integer*8 exception, obj
      real*8 d
      integer*4 i
      character*512 str
      logical b
    
      call s_Hard_Get_d_object_f(s, d_object)
      call s_Hard_Get_d_interface_f(s, d_interface)
      call s_Hard_Get_d_string_f(s, d_string)
      call s_Hard_Get_d_array_f(s, d_array)
      call s_Hard_Get_d_objectArray_f(s, d_objectArray)

      exception = 0

      call sidl_double__array_dimen_f(d_array, i)
      if (i .ne. 1) then
         checkHard = .false.
         return
      endif

      call sidl_double__array_length_f(d_array, 0, i)
      if (i .ne. 3) then
         checkHard = .false.
         return
      endif

      do 10 i = 0, 2
         call sidl_double__array_get1_f(d_array, i, d)
         if (d .ne. 1.0 * (i + 1)) then
            checkHard = .false.
            return
         endif
 10   continue

      call sidl_string__array_dimen_f(d_string, i)
      if (i .ne. 1) then
         checkHard = .false.
         return
      endif

      call sidl_string__array_length_f(d_string, 0, i)
      if (i .ne. 1) then
         checkHard = .false.
         return
      endif

      call sidl_string__array_get1_f(d_string, 0, str)
      if (str .ne. 'Three') then
         checkHard = .false.
         return
      endif

C     check base class and interface
      if ((d_object .eq. 0) .or. (d_interface .eq. 0)) then
         checkHard = .false.
         return
      endif

      call sidl_BaseInterface_isSame_f(d_object, d_interface,
     &     b, exception)
      if ((exception .ne. 0) .or. (b .eqv. .false.)) then
         checkHard = .false.
         return
      endif


C     check for array of objects 
      call sidl_BaseClass__array_dimen_f(d_objectArray, i)
      if (i .ne. 1) then
         checkHard = .false.
         return
      endif

      call sidl_BaseClass__array_length_f(d_objectArray, 0, i)
      if (i .ne. 3) then
         checkHard = .false.
         return
      endif

      do 20 i = 0, 2
         call sidl_BaseClass__array_get1_f(d_objectArray, i, obj)
         if (obj .eq. 0) then
            checkHard = .false.
            return
         endif
         call sidl_BaseClass_isType_f(
     &        obj, 'sidl.BaseInterface', b, exception)
         if ((exception .ne. 0) .or. (b .eqv. .false.)) then
            checkHard = .false.
            return
         endif
         call sidl_BaseClass_deleteRef_f(obj, exception)
 20   continue

      checkHard=.true.      
      return
      end

      subroutine initCombined(s, exception)
      implicit none
C     out s.Combined s
      integer*8 s
C     out sidl.BaseInterface exception
      integer*8 exception

      integer*8 d_simple, d_hard
      call s_Combined_Get_d_simple_f(s, d_simple)
      call s_Combined_Get_d_hard_f(s, d_hard)
      call initSimple(d_simple, exception)
      call initHard(d_hard, exception)
      end
      
      subroutine invertCombined(s)
      implicit none
C     in s.Combined s
      integer*8 s

      integer*8 d_simple, d_hard, exception
      call s_Combined_Get_d_simple_f(s, d_simple)
      call s_Combined_Get_d_hard_f(s, d_hard)
      call invertSimple(d_simple)
      call invertHard(d_hard)
      end
      
      logical function checkCombined(s)
      implicit none
C          in s.Combined s
      integer*8 s

      integer*8 d_simple, d_hard
      logical checkSimple, checkHard
      
      call s_Combined_Get_d_simple_f(s, d_simple)
      if (checkSimple(d_simple) .eqv. .false.) then
         checkCombined = .false.
         return
      endif

      call s_Combined_Get_d_hard_f(s, d_hard)
      if (checkHard(d_hard) .eqv. .false.) then
         checkCombined = .false.
         return
      endif
      
      checkCombined=.true.
      return
      end

      logical function checkRarrays(s)
      implicit none
C          in s.Rarrays s
      integer*8 s
      integer*8 rarrayRaw
      integer*8 rarrayFix
      integer*4 d
      real*8 r1, r2, r3
      real*8 f1, f2, f3

      call s_Rarrays_Get_d_int_f(s, d)
      call s_Rarrays_Get_d_rarrayRaw_f(s, rarrayRaw)
      call s_Rarrays_Get_d_rarrayFix_f(s, rarrayFix)
      call sidl_double__array_get1_f(rarrayRaw, 0, r1)
      call sidl_double__array_get1_f(rarrayRaw, 1, r2)
      call sidl_double__array_get1_f(rarrayRaw, 2, r3)
      call sidl_double__array_get1_f(rarrayFix, 0, f1)
      call sidl_double__array_get1_f(rarrayFix, 1, f2)
      call sidl_double__array_get1_f(rarrayFix, 2, f3)
      checkRarrays=.false.

      if ((d .eq. 3) .and. 
     &    (r1 .eq. 1.0) .and. 
     &    (r2 .eq. 2.0) .and. 
     &    (r3 .eq. 3.0) .and. 
     &    (f1 .eq. 5.0) .and. 
     &    (f2 .eq. 10.0) .and. 
     &    (f3 .eq. 15.0)) then
         checkRarrays=.true.
      endif

      return
      
      end function checkRarrays

      subroutine invertRarrays(s)
      implicit none

C          inout s.Rarrays s
      integer*8 s
      integer*8 rarrayRaw
      integer*8 rarrayFix
      real*8 r1, r3
      real*8 f1, f3
    
      call s_Rarrays_Get_d_rarrayRaw_f(s, rarrayRaw)
      call s_Rarrays_Get_d_rarrayFix_f(s, rarrayFix)
      call sidl_double__array_get1_f(rarrayRaw, 0, r1)
      call sidl_double__array_get1_f(rarrayRaw, 2, r3)
      call sidl_double__array_get1_f(rarrayFix, 0, f1)
      call sidl_double__array_get1_f(rarrayFix, 2, f3)

      call sidl_double__array_set1_f(rarrayRaw, 0, r3)
      call sidl_double__array_set1_f(rarrayRaw, 2, r1)
      call sidl_double__array_set1_f(rarrayFix, 0, f3)
      call sidl_double__array_set1_f(rarrayFix, 2, f1)

      end subroutine invertRarrays
      
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine s_StructTest__ctor_fi(self, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest._ctor)
C       insert code here (_ctor method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine s_StructTest__ctor2_fi(self, private_data, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest._ctor2)
C       insert code here (_ctor2 method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine s_StructTest__dtor_fi(self, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest._dtor)
C       insert code here (_dtor method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine s_StructTest__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest._load)
C       insert code here (_load method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest._load)
        end


C       
C       Method:  returnEmpty[]
C       

        subroutine s_StructTest_returnEmpty_fi(self, retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Empty retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.returnEmpty)
C       insert code here (returnEmpty method)

C       Nothing to do here

C       DO-NOT-DELETE splicer.end(s.StructTest.returnEmpty)
        end


C       
C       Method:  passinEmpty[]
C       

        subroutine s_StructTest_passinEmpty_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Empty s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinEmpty)
C       insert code here (passinEmpty method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest.passinEmpty)
        end


C       
C       Method:  passoutEmpty[]
C       

        subroutine s_StructTest_passoutEmpty_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Empty s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passoutEmpty)
C       insert code here (passoutEmpty method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest.passoutEmpty)
        end


C       
C       Method:  passinoutEmpty[]
C       

        subroutine s_StructTest_passinoutEmpty_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        inout s.Empty s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinoutEmpty)
C       insert code here (passinoutEmpty method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest.passinoutEmpty)
        end


C       
C       Method:  passeverywhereEmpty[]
C       

        subroutine s_StructTest_passeverywhereEmpty_fi(self, s1, s2, s3,
     &     retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Empty s1
        integer*8 :: s1
C        out s.Empty s2
        integer*8 :: s2
C        inout s.Empty s3
        integer*8 :: s3
C        out s.Empty retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereEmpty)
C       insert code here (passeverywhereEmpty method)
C       
C       This method has not been implemented
C       

C       DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereEmpty)
        end


C       
C       Method:  returnSimple[]
C       

        subroutine s_StructTest_returnSimple_fi(self, retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Simple retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.returnSimple)
        call initSimple(retval, exception)
C       DO-NOT-DELETE splicer.end(s.StructTest.returnSimple)
        end


C       
C       Method:  passinSimple[]
C       

        subroutine s_StructTest_passinSimple_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Simple s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinSimple)
        logical checkSimple
        retval = checkSimple(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinSimple)
        end


C       
C       Method:  passoutSimple[]
C       

        subroutine s_StructTest_passoutSimple_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Simple s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passoutSimple)
        call initSimple(s1, exception)
        retval = .true.
C       DO-NOT-DELETE splicer.end(s.StructTest.passoutSimple)
        end


C       
C       Method:  passinoutSimple[]
C       

        subroutine s_StructTest_passinoutSimple_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        inout s.Simple s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinoutSimple)
        logical checkSimple
        retval=checkSimple(s1)
        call invertSimple(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinoutSimple)
        end


C       
C       Method:  passeverywhereSimple[]
C       

        subroutine s_StructTest_passeverywhereSimple_fi(self, s1, s2, 
     &     s3, retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Simple s1
        integer*8 :: s1
C        out s.Simple s2
        integer*8 :: s2
C        inout s.Simple s3
        integer*8 :: s3
C        out s.Simple retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereSimple)
        logical checkSimple
        call initSimple(s2, exception)
        call initSimple(retval, exception)
        if (checkSimple(s1) .and. checkSimple(s3)) then
           call invertSimple(s3)
        endif
C       DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereSimple)
        end


C       
C       Method:  returnHard[]
C       

        subroutine s_StructTest_returnHard_fi(self, retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Hard retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.returnHard)
        call initHard(retval, exception)
C       DO-NOT-DELETE splicer.end(s.StructTest.returnHard)
        end


C       
C       Method:  passinHard[]
C       

        subroutine s_StructTest_passinHard_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Hard s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinHard)
        logical checkHard
        retval = checkHard(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinHard)
        end


C       
C       Method:  passoutHard[]
C       

        subroutine s_StructTest_passoutHard_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Hard s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passoutHard)
        call initHard(s1, exception)
        retval = .true.
C       DO-NOT-DELETE splicer.end(s.StructTest.passoutHard)
        end


C       
C       Method:  passinoutHard[]
C       

        subroutine s_StructTest_passinoutHard_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        inout s.Hard s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinoutHard)
        logical checkHard
        retval=checkHard(s1)
        call invertHard(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinoutHard)
        end


C       
C       Method:  passeverywhereHard[]
C       

        subroutine s_StructTest_passeverywhereHard_fi(self, s1, s2, s3, 
     &     retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Hard s1
        integer*8 :: s1
C        out s.Hard s2
        integer*8 :: s2
C        inout s.Hard s3
        integer*8 :: s3
C        out s.Hard retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereHard)
        logical checkHard
        call initHard(s2, exception)
        call initHard(retval, exception)
        if (checkHard(s1) .and. checkHard(s3)) then
           call invertHard(s3)
        endif
C       DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereHard)
        end


C       
C       Method:  returnCombined[]
C       

        subroutine s_StructTest_returnCombined_fi(self, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Combined retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.returnCombined)
        call initCombined(retval, exception)
C       DO-NOT-DELETE splicer.end(s.StructTest.returnCombined)
        end


C       
C       Method:  passinCombined[]
C       

        subroutine s_StructTest_passinCombined_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Combined s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinCombined)
        logical checkCombined
        retval = checkCombined(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinCombined)
        end


C       
C       Method:  passoutCombined[]
C       

        subroutine s_StructTest_passoutCombined_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        out s.Combined s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passoutCombined)
        call initCombined(s1, exception)
        retval = .true.
C       DO-NOT-DELETE splicer.end(s.StructTest.passoutCombined)
        end


C       
C       Method:  passinoutCombined[]
C       

        subroutine s_StructTest_passinoutCombined_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        inout s.Combined s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinoutCombined)
        logical checkCombined
        retval=checkCombined(s1)
        call invertCombined(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinoutCombined)
        end


C       
C       Method:  passeverywhereCombined[]
C       

        subroutine s_StructTest_passeverywhereCombined_fi(self, s1, s2, 
     &     s3, retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Combined s1
        integer*8 :: s1
C        out s.Combined s2
        integer*8 :: s2
C        inout s.Combined s3
        integer*8 :: s3
C        out s.Combined retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereCombined)
        logical checkCombined
        call initCombined(s2, exception)
        call initCombined(retval, exception)
        if (checkCombined(s1) .and. checkCombined(s3)) then
           call invertCombined(s3)
        endif
C       DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereCombined)
        end


C       
C       Method:  passinRarrays[]
C       

        subroutine s_StructTest_passinRarrays_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Rarrays s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinRarrays)
C       insert code here (passinRarrays method)
        logical checkRarrays
        retval = checkRarrays(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinRarrays)
        end


C       
C       Method:  passinoutRarrays[]
C       

        subroutine s_StructTest_passinoutRarrays_fi(self, s1, retval, 
     &     exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        inout s.Rarrays s1
        integer*8 :: s1
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passinoutRarrays)
C       insert code here (passinoutRarrays method)
        logical checkRarrays
        retval = checkRarrays(s1)
        call invertRarrays(s1)
C       DO-NOT-DELETE splicer.end(s.StructTest.passinoutRarrays)
        end


C       
C       Method:  passeverywhereRarrays[]
C       

        subroutine s_StructTest_passeverywhereRarrays_fi(self, s1, s2, 
     &     retval, exception)
        implicit none
C        in s.StructTest self
        integer*8 :: self
C        in s.Rarrays s1
        integer*8 :: s1
C        inout s.Rarrays s2
        integer*8 :: s2
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(s.StructTest.passeverywhereRarrays)
C       insert code here (passeverywhereRarrays method)
        logical checkRarrays
        if (checkRarrays(s1) .and. checkRarrays(s2)) then
           call invertRarrays(s2)
           retval=.true.
        endif
C       DO-NOT-DELETE splicer.end(s.StructTest.passeverywhereRarrays)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       insert code here (extra code)
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
