C       
C       File:          ArrayTest_ArrayOps_Impl.f
C       Symbol:        ArrayTest.ArrayOps-v1.3
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for ArrayTest.ArrayOps
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "ArrayTest.ArrayOps" (version 1.3)
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
      logical function isprime(num)
      implicit none
      integer*8 num
      integer*8 i
      i = 3
      do while (i*i .le. num)
         if (mod(num,i) .eq. 0) then
            isprime = .false.
            return
         endif
         i = i + 1
      enddo
      isprime = .true.
      return
      end
      

      integer*8 function nextprime(prev)
      implicit none
      integer*8 prev
      logical isprime
      if (prev .le. 1) then
         nextprime = 2
         return
      endif
      if (prev .eq. 2) then
         nextprime = 3
         return
      endif
      prev = prev + 2
      do while (.not. isprime(prev))
         prev = prev + 2
      enddo
      nextprime = prev
      return
      end

      subroutine force_float(f)
      real *4 f
      return
      end

      subroutine force_fcomplex(f)
      complex *8 f
      return
      end

      logical function iseven(num)
      integer*4 num
      iseven = (mod(num, 2) .eq. 0)
      return
      end

      integer*4 function arrayValue(dimen, ind)
      implicit none
      integer*4 dimen, result, i
      integer*4 ind(dimen)
      result = 1
      do i = 1, dimen
         result = result * (ind(i) + i)
      enddo
      arrayValue = result
      return
      end

      integer*4 function intFunc(dimen, ind)
      implicit none
      integer*4 dimen, ind(dimen), i
      integer*4 result 
      result = 1
      do i = 1, dimen
         result = result * (ind(i) + i)
      enddo
      intFunc = result
      return
      end

      logical function hasElements(dimen, lower, upper)
      implicit none
      integer*4 dimen, lower(dimen), upper(dimen)
      integer*4 i
      hasElements = .true.
      do i = 1, dimen
         if (lower(i) .gt. upper(i)) then
            hasElements = .false.
         endif
      enddo
      return
      end

      logical function nextElem(dimen, ind, lower, upper)
      implicit none
      integer*4 dimen
      integer*4 ind(dimen), lower(dimen), upper(dimen)
      integer*4 i
      i = 1
      ind(i) = ind(i) + 1
      do while ((i .le. dimen) .and. (ind(i) .gt. upper(i)))
         ind(i) = lower(i)
         i = i + 1
         if (i .le. dimen) then
            ind(i) = ind(i) + 1
         endif
      enddo
      nextElem = (i .le. dimen)
      return
      end

      subroutine createarraybytype(type, dimen, lower, upper, a)
      implicit none
      integer*4 type, dimen, lower(7), upper(7)
      integer*8 a
      if (type .eq. 1) then
         call sidl_bool__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 2) then
         call sidl_char__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 3) then
         call sidl_dcomplex__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 4) then
         call sidl_double__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 5) then
         call sidl_fcomplex__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 6) then
         call sidl_float__array_createrow_f(dimen, lower, upper,a)
      else if (type .eq. 7) then
         call sidl_int__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 8) then
         call sidl_long__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 9) then
         call sidl_opaque__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 10) then
         call sidl_string__array_createrow_f(dimen, lower, upper, a)
      else if (type .eq. 11) then
         call sidl_BaseInterface__array_createrow_f(dimen, 
     $        lower, upper, a)
      else
         a = 0
      end if
      end
      
      subroutine copyarraybytype(src, dest)
      integer*8 src, dest
      integer*4 type
      call sidl__array_type_f(src, type)
      if (type .eq. 1) then
         call sidl_bool__array_copy_f(src, dest)
      else if (type .eq. 2) then
         call sidl_char__array_copy_f(src, dest)
      else if (type .eq. 3) then
         call sidl_dcomplex__array_copy_f(src, dest)
      else if (type .eq. 4) then
         call sidl_double__array_copy_f(src, dest)
      else if (type .eq. 5) then
         call sidl_fcomplex__array_copy_f(src, dest)
      else if (type .eq. 6) then
         call sidl_float__array_copy_f(src, dest)
      else if (type .eq. 7) then
         call sidl_int__array_copy_f(src, dest)
      else if (type .eq. 8) then
         call sidl_long__array_copy_f(src, dest)
      else if (type .eq. 9) then
         call sidl_opaque__array_copy_f(src, dest)
      else if (type .eq. 10) then
         call sidl_string__array_copy_f(src, dest)
      else if (type .eq. 11) then
         call sidl_BaseInterface__array_copy_f(src, dest)
      endif
      end

      subroutine locMatrixMultiply(a,b,res,n,m,o)
      implicit none
        integer*4 n
        integer*4 m
        integer*4 o
C       inout rarray<int,3> a(n,m,o)
        integer*4 a(0:n-1, 0:m-1)
        integer*4 b(0:m-1, 0:o-1)
        integer*4 res(0:n-1, 0:o-1)
        integer*4 i,j,k,temp
        

        do i= 0,n-1
           do k = 0, o-1
              temp = 0
              do j = 0, m-1
                 temp = temp + (a(i,j) * b(j,k))
              enddo
              res(i,k) = temp
           enddo
        enddo
      end
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine ArrayTest_ArrayOps__ctor_fi(self, exception)
        implicit none
C        in ArrayTest.ArrayOps self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine ArrayTest_ArrayOps__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in ArrayTest.ArrayOps self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._ctor2)
C       Insert-Code-Here {ArrayTest.ArrayOps._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine ArrayTest_ArrayOps__dtor_fi(self, exception)
        implicit none
C        in ArrayTest.ArrayOps self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine ArrayTest_ArrayOps__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps._load)
        end


C       
C       Method:  checkBool[]
C       Return <code>true</code> iff the even elements are true and
C       the odd elements are false.
C       

        subroutine ArrayTest_ArrayOps_checkBool_fi(a, retval, exception)
        implicit none
C        in array<bool> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkBool)
        logical value1, value2
        integer*4 lower, upper, dimen, len, i, index(1)
        integer*4 testind
        logical isEven
        retval = .false.
        if (a .ne. 0) then
           call sidl_bool__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_bool__array_lower_f(a, 0, lower)
              call sidl_bool__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 index(1) = lower + i
                 call sidl_bool__array_get1_f(a, lower+i, value1)
                 call sidl_bool__array_get_f(a, index, value2)
                 if ((value1 .neqv. value2) .or.
     $                (value1 .neqv. isEven(lower + i))) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkBool)
        end


C       
C       Method:  checkChar[]
C       

        subroutine ArrayTest_ArrayOps_checkChar_fi(a, retval, exception)
        implicit none
C        in array<char> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkChar)
        character*64 testtext
        character value1, value2
        integer*4 lower, upper, dimen, len, i, index(1)
        integer*4 testind
        data testtext / 
     $'I''d rather write programs to write programs than write programs' 
     $ /
        retval = .false.
        if (a .ne. 0) then
           call sidl_char__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_char__array_lower_f(a, 0, lower)
              call sidl_char__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 index(1) = lower + i
                 call sidl_char__array_get1_f(a, lower+i, value1)
                 call sidl_char__array_get_f(a, index, value2)
                 testind = mod(i, 64) + 1
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. testtext(testind:testind))) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkChar)
        end


C       
C       Method:  checkInt[]
C       

        subroutine ArrayTest_ArrayOps_checkInt_fi(a, retval, exception)
        implicit none
C        in array<int> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkInt)
        integer*4 dimen, lower, upper, index(1), prime
        integer*4 refarray(1), low(1), up(1), stride(1)
        integer*4 value1, value2, value3, i
        integer*8 nextprime, tmp, refindex, ilong
        save refarray
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_int__array_lower_f(a, 0, lower)
              call sidl_int__array_upper_f(a, 0, upper)
              call sidl_int__array_access_f(a, refarray, low,
     $             up, stride, refindex)
              prime = 0
              do i = lower, upper
                 tmp = prime
                 prime = nextprime(tmp)
                 index(1) = i
                 call sidl_int__array_get1_f(a, i, value1)
                 call sidl_int__array_get_f(a, index, value2)
                 ilong = i
                 value3 = refarray(refindex + stride(1) *
     $                (ilong - low(1)))
                 if ((value1 .ne. value2) .or.
     $               (value2 .ne. value3) .or.
     $               (value1 .ne. prime)) then
                     retval = .false.
                     return
                  endif
               enddo
               retval = .true.
            endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkInt)
        end


C       
C       Method:  checkLong[]
C       

        subroutine ArrayTest_ArrayOps_checkLong_fi(a, retval, exception)
        implicit none
C        in array<long> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkLong)
        integer*4 dimen, lower, upper, i, index(1)
        integer*8 prime
        integer*8 value1, value2, nextprime
        retval = .false.
        if (a .ne. 0) then
           call sidl_long__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_long__array_lower_f(a, 0, lower)
              call sidl_long__array_upper_f(a, 0, upper)
              prime = 0
              do i = lower, upper
                 prime = nextprime(prime)
                 index(1) = i
                 call sidl_long__array_get1_f(a, i, value1)
                 call sidl_long__array_get_f(a, index, value2)
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. prime)) then
                     retval = .false.
                     return
                  endif
               enddo
               retval = .true.
            endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkLong)
        end


C       
C       Method:  checkString[]
C       

        subroutine ArrayTest_ArrayOps_checkString_fi(a, retval, 
     &     exception)
        implicit none
C        in array<string> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkString)
        character*9 testtext(10)
        character*2048 value1
        character*2048 value2
        integer*4 lower, upper, dimen, len, i, index(1), testind
        data testtext / 'I''d', 'rather', 'write', 'programs', 'to',
     $       'write', 'programs', 'than', 'write', 'programs.'/
        retval = .false.
        if (a .ne. 0) then
           call sidl_string__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_string__array_lower_f(a, 0, lower)
              call sidl_string__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 index(1) = lower + i
                 call sidl_string__array_get1_f(a, lower+i, value1)
                 call sidl_string__array_get_f(a, index, value2)
                 testind = mod(i, 10) + 1
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. testtext(testind))) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkString)
        end


C       
C       Method:  checkDouble[]
C       

        subroutine ArrayTest_ArrayOps_checkDouble_fi(a, retval, 
     &     exception)
        implicit none
C        in array<double> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDouble)
        double precision value1, value2, expectedvalue
        integer*4 lower, upper, dimen, len, i, index(1)
        retval = .false.
        if (a .ne. 0) then
           call sidl_double__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_double__array_lower_f(a, 0, lower)
              call sidl_double__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 expectedvalue = (2.0d0 ** (-i))
                 index(1) = lower + i
                 call sidl_double__array_get1_f(a,lower+i,value1)
                 call sidl_double__array_get_f(a,index,value2)
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. expectedvalue)) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDouble)
        end


C       
C       Method:  checkFloat[]
C       

        subroutine ArrayTest_ArrayOps_checkFloat_fi(a, retval, 
     &     exception)
        implicit none
C        in array<float> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFloat)
        real*4 value1, value2, expectedvalue
        integer*4 lower, upper, dimen, len, i, index(1)
        retval = .false.
        if (a .ne. 0) then
           call sidl_float__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_float__array_lower_f(a, 0, lower)
              call sidl_float__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 expectedvalue = (2.0e0 ** (-i))
                 call force_float(expectedvalue)
                 index(1) = lower + i
                 call sidl_float__array_get1_f(a,lower+i,value1)
                 call sidl_float__array_get_f(a,index,value2)
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. expectedvalue)) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFloat)
        end


C       
C       Method:  checkFcomplex[]
C       

        subroutine ArrayTest_ArrayOps_checkFcomplex_fi(a, retval, 
     &     exception)
        implicit none
C        in array<fcomplex> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkFcomplex)
        complex*8 value1, value2, expectedvalue
        integer*4 lower, upper, dimen, len, i, index(1)
        retval = .false.
        if (a .ne. 0) then
           call sidl_fcomplex__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_fcomplex__array_lower_f(a, 0, lower)
              call sidl_fcomplex__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 call force_fcomplex(expectedvalue)
                 expectedvalue = cmplx(2.0e0 ** i, 2.0e0 ** (-i))
                 index(1) = lower + i
                 call sidl_fcomplex__array_get1_f(a,lower+i,value1)
                 call sidl_fcomplex__array_get_f(a,index,value2)
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. expectedvalue)) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkFcomplex)
        end


C       
C       Method:  checkDcomplex[]
C       

        subroutine ArrayTest_ArrayOps_checkDcomplex_fi(a, retval, 
     &     exception)
        implicit none
C        in array<dcomplex> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkDcomplex)
        complex*16 value1, value2, expectedvalue
        integer*4 lower, upper, dimen, len, i, index(1)
        retval = .false.
        if (a .ne. 0) then
           call sidl_dcomplex__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_dcomplex__array_lower_f(a, 0, lower)
              call sidl_dcomplex__array_upper_f(a, 0, upper)
              len = (upper - lower) + 1
              do i = 0, len - 1
                 expectedvalue = cmplx(2.0d0 ** i, 2.0d0 ** (-i))
                 index(1) = lower + i
                 call sidl_dcomplex__array_get1_f(a,lower+i,value1)
                 call sidl_dcomplex__array_get_f(a,index,value2)
                 if ((value1 .ne. value2) .or.
     $               (value1 .ne. expectedvalue)) then
                    return
                 endif
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkDcomplex)
        end


C       
C       Method:  check2Int[]
C       

        subroutine ArrayTest_ArrayOps_check2Int_fi(a, retval, exception)
        implicit none
C        in array<int,2> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Int)
        integer*4 dimen, lower(2), upper(2), index(2), i, j
        integer*4 len(2)
        integer*4 value1, value2, expectedvalue
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 2) then
              do i = 1, dimen
                 call sidl_int__array_lower_f(a, i-1, lower(i))
                 call sidl_int__array_upper_f(a, i-1, upper(i))
                 len(i) = upper(i) - lower(i)
              enddo
              do i = 0, len(1)
                 index(1) = lower(1) + i
                 do j = 0, len(2)
                    expectedvalue = (2.0d0 ** abs(i-j))
                    index(2) = lower(2) + j
                    call sidl_int__array_get2_f(a,
     $                   index(1), index(2), value1)
                    call sidl_int__array_get_f(a, index, value2)
                    if ((value1 .ne. value2) .or.
     $                  (value1 .ne. expectedvalue)) then
                       return
                    endif
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Int)
        end


C       
C       Method:  check2Double[]
C       

        subroutine ArrayTest_ArrayOps_check2Double_fi(a, retval, 
     &     exception)
        implicit none
C        in array<double,2> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Double)
        integer*4 dimen, lower(2), upper(2), index(2), i, j
        integer*4 len(2)
        double precision value1, value2, expectedvalue
        retval = .false.
        if (a .ne. 0) then
           call sidl_double__array_dimen_f(a, dimen)
           if (dimen .eq. 2) then
              do i = 1, 2
                 call sidl_double__array_lower_f(a, i-1, lower(i))
                 call sidl_double__array_upper_f(a, i-1, upper(i))
                 len(i) = upper(i) - lower(i)
              enddo
              do i = 0, len(1)
                 index(1) = lower(1) + i
                 do j = 0, len(2)
                    expectedvalue = (2.0d0 ** (i-j))
                    index(2) = lower(2) + j
                    call sidl_double__array_get2_f(a,
     $                   index(1), index(2),
     $                   value1)
                    call sidl_double__array_get_f(a, index,
     $                   value2)
                    if ((value1 .ne. value2) .or.
     $                  (value1 .ne. expectedvalue)) then
                       return
                    endif
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Double)
        end


C       
C       Method:  check2Float[]
C       

        subroutine ArrayTest_ArrayOps_check2Float_fi(a, retval, 
     &     exception)
        implicit none
C        in array<float,2> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Float)
        integer*4 dimen, lower(2), upper(2), index(2), i, j
        integer*4 len(2)
        real*4 value1, value2, expectedvalue
        retval = .false.
        if (a .ne. 0) then
           call sidl_float__array_dimen_f(a, dimen)
           if (dimen .eq. 2) then
              do i = 1, 2
                 call sidl_float__array_lower_f(a, i-1, lower(i))
                 call sidl_float__array_upper_f(a, i-1, upper(i))
                 len(i) = upper(i) - lower(i)
              enddo
              do i = 0, len(1)
                 index(1) = lower(1) + i
                 do j = 0, len(2)
                    expectedvalue = (2.0e0 ** (i-j))
                    index(2) = lower(2) + j
                    call sidl_float__array_get2_f(a,
     $                   index(1), index(2),
     $                   value1)
                    call sidl_float__array_get_f(a, index,
     $                   value2)
                    if ((value1 .ne. value2) .or.
     $                  (value1 .ne. expectedvalue)) then
                       return
                    endif
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Float)
        end


C       
C       Method:  check2Fcomplex[]
C       

        subroutine ArrayTest_ArrayOps_check2Fcomplex_fi(a, retval, 
     &     exception)
        implicit none
C        in array<fcomplex,2> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Fcomplex)
        integer*4 dimen, lower(2), upper(2), index(2), i, j
        integer*4 len(2)
        complex*8 value1, value2, expectedvalue
        retval = .false.
        if (a .ne. 0) then
           call sidl_fcomplex__array_dimen_f(a, dimen)
           if (dimen .eq. 2) then
              do i = 1, 2
                 call sidl_fcomplex__array_lower_f(a, i-1, lower(i))
                 call sidl_fcomplex__array_upper_f(a, i-1, upper(i))
                 len(i) = upper(i) - lower(i)
              enddo
              do i = 0, len(1)
                 do j = 0, len(2)
                    expectedvalue = cmplx(2.0e0 ** i, 2.0e0 ** (-j))
                    index(1) = lower(1) + i
                    index(2) = lower(2) + j
                    call sidl_fcomplex__array_get2_f(a,
     $                   index(1), index(2),
     $                   value1)
                    call sidl_fcomplex__array_get_f(a, index,
     $                   value2)
                    if ((value1 .ne. value2) .or.
     $                  (value1 .ne. expectedvalue)) then
                       return
                    endif
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Fcomplex)
        end


C       
C       Method:  check2Dcomplex[]
C       

        subroutine ArrayTest_ArrayOps_check2Dcomplex_fi(a, retval, 
     &     exception)
        implicit none
C        in array<dcomplex,2> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2Dcomplex)
        integer*4 dimen, lower(2), upper(2), index(2), i, j
        integer*4 len(2)
        complex*16 value1, value2, expectedvalue
        retval = .false.
        if (a .ne. 0) then
           call sidl_dcomplex__array_dimen_f(a, dimen)
           if (dimen .eq. 2) then
              do i = 1, 2
                 call sidl_dcomplex__array_lower_f(a, i-1, lower(i))
                 call sidl_dcomplex__array_upper_f(a, i-1, upper(i))
                 len(i) = upper(i) - lower(i)
              enddo
              do i = 0, len(1)
                 do j = lower(2), upper(2)
                    expectedvalue = cmplx(2.0d0 ** i, 2.0d0 ** (-j))
                    index(1) = lower(1) + i
                    index(2) = lower(2) + j
                    call sidl_dcomplex__array_get2_f(a,
     $                   index(1), index(2),
     $                   value1)
                    call sidl_dcomplex__array_get_f(a, index,
     $                   value2)
                    if ((value1 .ne. value2) .or.
     $                  (value1 .ne. expectedvalue)) then
                       return
                    endif
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2Dcomplex)
        end


C       
C       Method:  check3Int[]
C       

        subroutine ArrayTest_ArrayOps_check3Int_fi(a, retval, exception)
        implicit none
C        in array<int,3> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check3Int)
        integer*4 dimen, ind(3), lower(3), upper(3)
        integer*4 i, j, k, expectedvalue, value1, value2
        integer*4 intFunc
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 3) then
              do i = 1, dimen
                 call sidl_int__array_lower_f(a, i-1, lower(i))
                 call sidl_int__array_upper_f(a, i-1, upper(i))
              enddo
              do i = lower(1), upper(1)
                 ind(1) = i
                 do j = lower(2), upper(2)
                    ind(2) = j
                    do k = lower(3), upper(3)
                       ind(3) = k
                       expectedvalue = intFunc(dimen, ind)
                       call sidl_int__array_get3_f(a, ind(1), 
     $                    ind(2), ind(3), value1)
                       call sidl_int__array_get_f(a, ind, 
     $                    value2)
                       if ((value1 .ne. value2) .or.
     $                     (value1 .ne. expectedvalue)) then
                          return
                       endif
                    enddo
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check3Int)
        end


C       
C       Method:  check4Int[]
C       

        subroutine ArrayTest_ArrayOps_check4Int_fi(a, retval, exception)
        implicit none
C        in array<int,4> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check4Int)
        integer*4 dimen, len(4), ind(4), lower(4), upper(4)
        integer*4 i, j, k, l, expectedvalue, value1, value2
        integer*4 intFunc
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 4) then
              do i = 1, dimen
                 call sidl_int__array_lower_f(a, i-1, lower(i))
                 call sidl_int__array_upper_f(a, i-1, upper(i))
                 len(i) = upper(i) - lower(i)
              enddo
              do i = 0, len(1)
                 ind(1) = lower(1) + i
                 do j = 0, len(2)
                    ind(2) = lower(2) + j
                    do k = 0, len(3)
                       ind(3) = lower(3) + k
                       do l = 0, len(4)
                          ind(4) = lower(4) + l
                          expectedvalue = intFunc(dimen, ind)
                          call sidl_int__array_get4_f(a, ind(1), 
     $                       ind(2), ind(3), ind(4), value1)
                          call sidl_int__array_get_f(a, ind, 
     $                       value2)
                          if ((value1 .ne. value2) .or.
     $                        (value1 .ne. expectedvalue)) then
                             return
                          endif
                       enddo
                    enddo
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check4Int)
        end


C       
C       Method:  check5Int[]
C       

        subroutine ArrayTest_ArrayOps_check5Int_fi(a, retval, exception)
        implicit none
C        in array<int,5> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check5Int)
        integer*4 i,j,k,l,m, dimen
        integer*4 value1, value2, value3
        integer*4 ind(5), alow(5), aup(5)
        integer*4 intFunc
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 5) then
              do i = 1, dimen
                 call sidl_int__array_lower_f(a, i-1, alow(i))
                 call sidl_int__array_upper_f(a, i-1, aup(i))
              enddo
              do i = alow(1), aup(1)
                 ind(1) = i
                 do j = alow(2), aup(2)
                    ind(2) = j
                    do k = alow(3), aup(3)
                       ind(3) = k
                       do l = alow(4), aup(4)
                          ind(4) = l
                          do m = alow(5), aup(5)
                             ind(5) = m
                             value3 = intFunc(dimen, ind)
                             call sidl_int__array_get5_f(a,i,j,k,l,m,
     $                            value1)
                             call sidl_int__array_get_f(a,ind, value2)
                             if ((value1 .ne. value2) .or.
     $                            (value1 .ne. value3)) then
                                return
                             endif
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check5Int)
        end


C       
C       Method:  check6Int[]
C       

        subroutine ArrayTest_ArrayOps_check6Int_fi(a, retval, exception)
        implicit none
C        in array<int,6> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check6Int)
        integer*4 i,j,k,l,m, n, dimen
        integer*4 value1, value2, value3
        integer*4 ind(6), alow(6), aup(6)
        integer*4 intFunc
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 6) then
              do i = 1, dimen
                 call sidl_int__array_lower_f(a, i-1, alow(i))
                 call sidl_int__array_upper_f(a, i-1, aup(i))
              enddo
              do i = alow(1), aup(1)
                 ind(1) = i
                 do j = alow(2), aup(2)
                    ind(2) = j
                    do k = alow(3), aup(3)
                       ind(3) = k
                       do l = alow(4), aup(4)
                          ind(4) = l
                          do m = alow(5), aup(5)
                             ind(5) = m
                             do n = alow(6), aup(6)
                                ind(6) = n
                                value3 = intFunc(dimen,ind)
                                call sidl_int__array_get6_f(a,i,j,k,l,
     $                               m,n,value1)
                                call sidl_int__array_get_f(a,ind,
     $                               value2)
                                if ((value1 .ne. value2) .or.
     $                               (value1 .ne. value3)) then
                                   return
                                endif
                             enddo
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check6Int)
        end


C       
C       Method:  check7Int[]
C       

        subroutine ArrayTest_ArrayOps_check7Int_fi(a, retval, exception)
        implicit none
C        in array<int,7> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check7Int)
        integer*4 i,j,k,l,m, n, o, dimen
        integer*4 value1, value2, value3
        integer*4 ind(7), alow(7), aup(7)
        integer*4 intFunc
        retval = .false.
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 7) then
              do i = 1, dimen
                 call sidl_int__array_lower_f(a, i-1, alow(i))
                 call sidl_int__array_upper_f(a, i-1, aup(i))
              enddo
              do i = alow(1), aup(1)
                 ind(1) = i
                 do j = alow(2), aup(2)
                    ind(2) = j
                    do k = alow(3), aup(3)
                       ind(3) = k
                       do l = alow(4), aup(4)
                          ind(4) = l
                          do m = alow(5), aup(5)
                             ind(5) = m
                             do n = alow(6), aup(6)
                                ind(6) = n
                                do o = alow(7), aup(7)
                                   ind(7) = o
                                   value3=intFunc(dimen,ind)
                                   call sidl_int__array_get7_f(a,i,j,k,
     $                                  l,m,n,o,value1)
                                   call sidl_int__array_get_f(a,ind,
     $                                  value2)
                                   if ((value1 .ne. value2) .or.
     $                                  (value1 .ne. value3)) then
                                      return
                                   endif
                                enddo
                             enddo
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check7Int)
        end


C       
C       Method:  check2String[]
C       

        subroutine ArrayTest_ArrayOps_check2String_fi(a, retval, 
     &     exception)
        implicit none
C        in array<string,2> a
        integer*8 :: a
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.check2String)
        character*9 testtext(10)
        character*2048 value1
        character*2048 value2
        integer*4 lower, upper, dimen, len, i,j,c, testind
        integer*4 lower2, upper2
        data testtext / 'I''d', 'rather', 'write', 'programs', 'to',
     $       'write', 'programs', 'than', 'write', 'programs.'/
        retval = .false.
        c = 0
        if (a .ne. 0) then
           call sidl_string__array_dimen_f(a, dimen)
           if (dimen .eq. 2) then
              call sidl_string__array_lower_f(a, 0, lower)
              call sidl_string__array_upper_f(a, 0, upper)
              call sidl_string__array_lower_f(a, 1, lower2)
              call sidl_string__array_upper_f(a, 1, upper2)
              do i = lower, upper
                 do j = lower2, upper2
                    call sidl_string__array_get2_f(a, i,j, value1)
                    testind = mod(c, 10) + 1
                    c = c + 1
                    if (value1 .ne. testtext(testind)) then
                       return
                    endif
                 enddo
              enddo
              retval = .true.
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.check2String)
        end


C       
C       Method:  checkObject[]
C       

        subroutine ArrayTest_ArrayOps_checkObject_fi(a, retval, 
     &     exception)
        implicit none
C        in array<ArrayTest.ArrayOps> a
        integer*8 :: a
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkObject)
        integer*4 dimen, lower, upper, i, index(1), prime
        integer*8 value1, value2, value3
        retval = 0
        if (a .ne. 0) then
           call ArrayTest_ArrayOps__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call ArrayTest_ArrayOps__array_lower_f(a, 0, lower)
              call ArrayTest_ArrayOps__array_upper_f(a, 0, upper)
              prime = 0
              do i = lower, upper
                 index(1) = i
                 call ArrayTest_ArrayOps__array_get1_f(a, i, value1)
                 call ArrayTest_ArrayOps__array_get_f(a, index, value2)
                 if (value1 .ne. value2) then
                    retval = -32000
                 else
                    if (value1 .ne. 0) then
                       call ArrayTest_ArrayOps__cast_f(value1, value3,
     $                      exception)
                       if (value3 .ne. 0) then
                          call ArrayTest_ArrayOps_deleteRef_f(value3, 
     $                         exception)
                          retval = retval + 1
                       endif
                    endif
                 endif
                 if (value1 .ne. 0) then
                    call ArrayTest_ArrayOps_deleteRef_f(value1, 
     $                   exception)
                 endif
                 if (value2 .ne. 0) then
                    call ArrayTest_ArrayOps_deleteRef_f(value2, 
     $                   exception)
                 endif
               enddo
            endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkObject)
        end


C       
C       Method:  reverseBool[]
C       

        subroutine ArrayTest_ArrayOps_reverseBool_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<bool> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseBool)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        logical buffer, extra
        if (a .ne. 0) then
           call sidl_bool__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_bool__array_lower_f(a, 0, lower)
              call sidl_bool__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkBool_f(a, retval, exception)
              if (newarray) then
                 call sidl_bool__array_createrow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_bool__array_get1_f(a, i, buffer)
                    call sidl_bool__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_bool__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_bool__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_bool__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_bool__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_bool__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseBool)
        end


C       
C       Method:  reverseChar[]
C       

        subroutine ArrayTest_ArrayOps_reverseChar_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<char> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseChar)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        character buffer, extra
        if (a .ne. 0) then
           call sidl_char__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_char__array_lower_f(a, 0, lower)
              call sidl_char__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkChar_f(a, retval, exception)
              if (newarray) then
                 call sidl_char__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_char__array_get1_f(a, i, buffer)
                    call sidl_char__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_char__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_char__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_char__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_char__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_char__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseChar)
        end


C       
C       Method:  reverseInt[]
C       

        subroutine ArrayTest_ArrayOps_reverseInt_fi(a, newArray, retval,
     &     exception)
        implicit none
C        inout array<int> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseInt)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        integer*4 buffer, extra
        if (a .ne. 0) then
           call sidl_int__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_int__array_lower_f(a, 0, lower)
              call sidl_int__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkInt_f(a, retval, exception)
              if (newarray) then
                 call sidl_int__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_int__array_get1_f(a, i, buffer)
                    call sidl_int__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_int__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_int__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_int__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_int__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_int__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseInt)
        end


C       
C       Method:  reverseLong[]
C       

        subroutine ArrayTest_ArrayOps_reverseLong_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<long> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseLong)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        integer*8 buffer, extra
        if (a .ne. 0) then
           call sidl_long__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_long__array_lower_f(a, 0, lower)
              call sidl_long__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkLong_f(a, retval, exception)
              if (newarray) then
                 call sidl_long__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_long__array_get1_f(a, i, buffer)
                    call sidl_long__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_long__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_long__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_long__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_long__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_long__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseLong)
        end


C       
C       Method:  reverseString[]
C       

        subroutine ArrayTest_ArrayOps_reverseString_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<string> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseString)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        character*2048 buffer
        character*2048 extra
        if (a .ne. 0) then
           call sidl_string__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_string__array_lower_f(a, 0, lower)
              call sidl_string__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkString_f(a, retval, 
     $             exception)
              if (newarray) then
                 call sidl_string__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_string__array_get1_f(a, i, buffer)
                    call sidl_string__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_string__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_string__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_string__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_string__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_string__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseString)
        end


C       
C       Method:  reverseDouble[]
C       

        subroutine ArrayTest_ArrayOps_reverseDouble_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<double> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDouble)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        double precision buffer, extra
        if (a .ne. 0) then
           call sidl_double__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_double__array_lower_f(a, 0, lower)
              call sidl_double__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkDouble_f(a, retval, 
     $             exception)
              if (newarray) then
                 call sidl_double__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_double__array_get1_f(a, i, buffer)
                    call sidl_double__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_double__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_double__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_double__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_double__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_double__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDouble)
        end


C       
C       Method:  reverseFloat[]
C       

        subroutine ArrayTest_ArrayOps_reverseFloat_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<float> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFloat)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        real*4 buffer, extra
        if (a .ne. 0) then
           call sidl_float__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_float__array_lower_f(a, 0, lower)
              call sidl_float__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkFloat_f(a, retval, exception)
              if (newarray) then
                 call sidl_float__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_float__array_get1_f(a, i, buffer)
                    call sidl_float__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_float__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_float__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_float__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_float__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_float__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFloat)
        end


C       
C       Method:  reverseFcomplex[]
C       

        subroutine ArrayTest_ArrayOps_reverseFcomplex_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<fcomplex> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseFcomplex)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        complex*8 buffer, extra
        if (a .ne. 0) then
           call sidl_fcomplex__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_fcomplex__array_lower_f(a, 0, lower)
              call sidl_fcomplex__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkFcomplex_f(a, retval, 
     $             exception)
              if (newarray) then
                 call sidl_fcomplex__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_fcomplex__array_get1_f(a, i, buffer)
                    call sidl_fcomplex__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_fcomplex__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_fcomplex__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_fcomplex__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_fcomplex__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_fcomplex__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseFcomplex)
        end


C       
C       Method:  reverseDcomplex[]
C       

        subroutine ArrayTest_ArrayOps_reverseDcomplex_fi(a, newArray, 
     &     retval, exception)
        implicit none
C        inout array<dcomplex> a
        integer*8 :: a
C        in bool newArray
        logical :: newArray
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.reverseDcomplex)
        integer*4 dimen, lower, upper, i, len
        integer*8 destarray
        complex*16 buffer, extra
        if (a .ne. 0) then
           call sidl_dcomplex__array_dimen_f(a, dimen)
           if (dimen .eq. 1) then
              call sidl_dcomplex__array_lower_f(a, 0, lower)
              call sidl_dcomplex__array_upper_f(a, 0, upper)
              call ArrayTest_ArrayOps_checkDcomplex_f(a, retval, 
     $             exception)
              if (newarray) then
                 call sidl_dcomplex__array_createRow_f(1,
     $                lower, upper, destarray)
                 do i = lower, upper
                    call sidl_dcomplex__array_get1_f(a, i, buffer)
                    call sidl_dcomplex__array_set1_f(destarray,
     $                   upper + lower - i, buffer)
                 enddo
                 call sidl_dcomplex__array_deleteRef_f(a)
                 a = destarray
              else
                 len = ((upper - lower + 1) / 2) - 1
                 do i = 0, len
                    call sidl_dcomplex__array_get1_f(a,
     $                   lower + i, buffer)
                    call sidl_dcomplex__array_get1_f(a,
     $                   upper - i, extra)

                    call sidl_dcomplex__array_set1_f(a,
     $                   lower + i, extra)
                    call sidl_dcomplex__array_set1_f(a,
     $                   upper - i, buffer)
                 enddo
              endif
           endif
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.reverseDcomplex)
        end


C       
C       Method:  createBool[]
C       

        subroutine ArrayTest_ArrayOps_createBool_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<bool> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createBool)
        integer*4 lower(1), upper(1), i, index(1), testind
        logical isEven
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_bool__array_createRow_f(1, lower, upper, retval)
           do i = 0, len - 1
              testind = mod(i, 64) + 1
              if (mod(i, 2) .eq. 0) then
                 call sidl_bool__array_set1_f(retval, i,
     $                isEven(i))
              else
                 index(1) = i
                 call sidl_bool__array_set_f(retval, index,
     $                isEven(i))
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createBool)
        end


C       
C       Method:  createChar[]
C       

        subroutine ArrayTest_ArrayOps_createChar_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<char> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createChar)
        character*64 testtext
        integer*4 lower(1), upper(1), i, index(1), testind
        data testtext / 
     $'I''d rather write programs to write programs than write programs' 
     $  /
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_char__array_createRow_f(1, lower, upper, retval)
           do i = 0, len - 1
              testind = mod(i, 64) + 1
              if (mod(i, 2) .eq. 0) then
                 call sidl_char__array_set1_f(retval, i,
     $                testtext(testind:testind))
              else
                 index(1) = i
                 call sidl_char__array_set_f(retval, index,
     $                testtext(testind:testind))
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createChar)
        end


C       
C       Method:  createInt[]
C       

        subroutine ArrayTest_ArrayOps_createInt_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<int> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createInt)
        integer*4 lower(1), upper(1), stride(1), index(1)
        integer*4 value, refarray(1), modval, i, divisor
        integer*8 nextprime, tmp, refindex, ilong
        save refarray
        retval = 0
        divisor = 3
        if (len .ge. 0) then
           lower(1) = 0
           value = 0
           upper(1) = len - 1
           call sidl_int__array_createRow_f(1, lower, upper, retval)
           call sidl_int__array_access_f(retval, refarray, lower,
     $          upper, stride, refindex)
           do i = 0, len - 1
              tmp = value
              value = nextprime(tmp)
              modval = mod(i, divisor)
              if (modval .eq. 0) then
                 call sidl_int__array_set1_f(retval, i, value)
              else
                 if (modval .eq. 1) then
                    index(1) = i
                    call sidl_int__array_set_f(retval, index, value)
                 else
                    ilong = i
                    refarray(refindex + stride(1)*(ilong - lower(1))) =
     $                   value
                 endif
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createInt)
        end


C       
C       Method:  createLong[]
C       

        subroutine ArrayTest_ArrayOps_createLong_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<long> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createLong)
        integer*4 lower(1), upper(1), i, index(1)
        integer*8 value, nextprime
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           value = 0
           upper(1) = len - 1
           call sidl_long__array_createRow_f(1, lower, upper, retval)
           do i = 0, len - 1
              value = nextprime(value)
              if (mod(i, 2) .eq. 0) then
                 call sidl_long__array_set1_f(retval, i, value)
              else
                 index(1) = i
                 call sidl_long__array_set_f(retval, index, value)
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createLong)
        end


C       
C       Method:  createString[]
C       

        subroutine ArrayTest_ArrayOps_createString_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<string> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createString)
        character*9 testtext(10)
        integer*4 lower(1), upper(1), i, index(1), testind
        data testtext / 'I''d', 'rather', 'write', 'programs', 'to',
     $       'write', 'programs', 'than', 'write', 'programs.'/
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_string__array_createRow_f(1, lower, upper, retval)
           do i = 0, len - 1
              testind = mod(i, 10) + 1
              if (mod(i, 2) .eq. 0) then
                 call sidl_string__array_set1_f(retval, i,
     $                testtext(testind))
              else
                 index(1) = i
                 call sidl_string__array_set_f(retval, index,
     $                testtext(testind))
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createString)
        end


C       
C       Method:  createDouble[]
C       

        subroutine ArrayTest_ArrayOps_createDouble_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<double> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDouble)
        integer*4 lower(1), upper(1), i, index(1)
        double precision value
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_double__array_createRow_f(1, lower, upper, retval)
           do i = 0, len - 1
              value = 2.0d0 ** (-i)
              if (mod(i, 2) .eq. 0) then
                 call sidl_double__array_set1_f(retval, i, value)
              else
                 index(1) = i
                 call sidl_double__array_set_f(retval, index, value)
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDouble)
        end


C       
C       Method:  createFloat[]
C       

        subroutine ArrayTest_ArrayOps_createFloat_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<float> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFloat)
        integer*4 lower(1), upper(1), i, index(1)
        real*4 value
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_float__array_createRow_f(1, lower, upper, retval)
           do i = 0, len - 1
              value = 2.0e0 ** (-i)
              if (mod(i, 2) .eq. 0) then
                 call sidl_float__array_set1_f(retval, i, value)
              else
                 index(1) = i
                 call sidl_float__array_set_f(retval, index, value)
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFloat)
        end


C       
C       Method:  createFcomplex[]
C       

        subroutine ArrayTest_ArrayOps_createFcomplex_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<fcomplex> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createFcomplex)
        integer*4 lower(1), upper(1), i, index(1)
        complex*8 value
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_fcomplex__array_createRow_f(1, lower, upper,
     $          retval)
           do i = 0, len - 1
              value = cmplx(2.0 ** i, 2.0 ** (-i))
              if (mod(i, 2) .eq. 0) then
                 call sidl_fcomplex__array_set1_f(retval, i, value)
              else
                 index(1) = i
                 call sidl_fcomplex__array_set_f(retval, index, value)
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createFcomplex)
        end


C       
C       Method:  createDcomplex[]
C       

        subroutine ArrayTest_ArrayOps_createDcomplex_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<dcomplex> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createDcomplex)
        integer*4 lower(1), upper(1), i, index(1)
        complex*16 value
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call sidl_dcomplex__array_createRow_f(1, lower, upper,
     $          retval)
           do i = 0, len - 1
              value = cmplx(2.0d0 ** i, 2.0d0 ** (-i))
              if (mod(i, 2) .eq. 0) then
                 call sidl_dcomplex__array_set1_f(retval, i, value)
              else
                 index(1) = i
                 call sidl_dcomplex__array_set_f(retval, index, value)
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createDcomplex)
        end


C       
C       Method:  createObject[]
C       

        subroutine ArrayTest_ArrayOps_createObject_fi(len, retval, 
     &     exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<ArrayTest.ArrayOps> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createObject)
        integer*4 lower(1), upper(1), i, index(1)
        integer*8 object
        retval = 0
        if (len .ge. 0) then
           lower(1) = 0
           upper(1) = len - 1
           call ArrayTest_ArrayOps__array_createRow_f(1, lower, upper,
     $          retval)
           do i = 0, len - 1
              call ArrayTest_ArrayOps__create_f(object, exception)
              if (mod(i, 2) .eq. 0) then
                 call ArrayTest_ArrayOps__array_set1_f(retval, i,
     $                object)
              else
                 index(1) = i
                 call ArrayTest_ArrayOps__array_set_f(retval, index,
     $                object)
              endif
              
              call ArrayTest_ArrayOps_deleteRef_f(object, exception)
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createObject)
        end


C       
C       Method:  create2Int[]
C       

        subroutine ArrayTest_ArrayOps_create2Int_fi(d1, d2, retval, 
     &     exception)
        implicit none
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out array<int,2> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Int)
        integer*4 lower(2), upper(2), index(2), i, j
        integer*4 value, modval
        retval = 0
        if ((d1 .ge. 0) .and. (d2 .ge. 0)) then
           lower(1) = 0
           upper(1) = d1 - 1
           lower(2) = 0
           upper(2) = d2 - 1
           value = 0
           call sidl_int__array_createRow_f(2, lower, upper, retval)
           do i = 0, upper(1)
              index(1) = i
              do j = 0, upper(2)
                 index(2) = j
                 value = (2.0d0 ** abs(i-j))
                 modval = mod(i, 2)
                 if (modval .eq. 0) then
                    call sidl_int__array_set2_f(retval, index(1), 
     $                   index(2), value)
                 else
                    call sidl_int__array_set_f(retval, index, 
     $                   value)
                 endif
              enddo
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Int)
        end


C       
C       Method:  create2Double[]
C       

        subroutine ArrayTest_ArrayOps_create2Double_fi(d1, d2, retval, 
     &     exception)
        implicit none
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out array<double,2> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Double)
        integer*4 lower(2), upper(2), index(2), i, j
        integer*4 modval
        double precision value
        retval = 0
        if ((d1 .ge. 0) .and. (d2 .ge. 0)) then
           lower(1) = 0
           upper(1) = d1 - 1
           lower(2) = 0
           upper(2) = d2 - 1
           value = 0
           call sidl_double__array_createRow_f(2, lower, upper, retval)
           do i = 0, upper(1)
              index(1) = i
              do j = 0, upper(2)
                 index(2) = j
                 value = (2.0d0 ** (i-j))
                 modval = mod(i, 2)
                 if (modval .eq. 0) then
                    call sidl_double__array_set2_f(retval, index(1), 
     $                   index(2), value)
                 else
                    call sidl_double__array_set_f(retval, index, 
     $                   value)
                 endif
              enddo
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Double)
        end


C       
C       Method:  create2Float[]
C       

        subroutine ArrayTest_ArrayOps_create2Float_fi(d1, d2, retval, 
     &     exception)
        implicit none
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out array<float,2> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Float)
        integer*4 lower(2), upper(2), index(2), i, j
        integer*4 modval
        real*4 value
        retval = 0
        if ((d1 .ge. 0) .and. (d2 .ge. 0)) then
           lower(1) = 0
           upper(1) = d1 - 1
           lower(2) = 0
           upper(2) = d2 - 1
           value = 0
           call sidl_float__array_createRow_f(2, lower, upper, retval)
           do i = 0, upper(1)
              index(1) = i
              do j = 0, upper(2)
                 index(2) = j
                 value = (2.0d0 ** (i-j))
                 modval = mod(i, 2)
                 if (modval .eq. 0) then
                    call sidl_float__array_set2_f(retval, index(1), 
     $                   index(2), value)
                 else
                    call sidl_float__array_set_f(retval, index, 
     $                   value)
                 endif
              enddo
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Float)
        end


C       
C       Method:  create2Dcomplex[]
C       

        subroutine ArrayTest_ArrayOps_create2Dcomplex_fi(d1, d2, retval,
     &     exception)
        implicit none
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out array<dcomplex,2> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Dcomplex)
        integer*4 lower(2), upper(2), index(2), i, j
        integer*4 modval
        complex*16 value
        retval = 0
        if ((d1 .ge. 0) .and. (d2 .ge. 0)) then
           lower(1) = 0
           upper(1) = d1 - 1
           lower(2) = 0
           upper(2) = d2 - 1
           value = 0
           call sidl_dcomplex__array_createRow_f(2, lower, upper,
     $          retval)
           do i = 0, upper(1)
              index(1) = i
              do j = 0, upper(2)
                 index(2) = j
                 value = cmplx(2.0d0 ** i, 2.0d0 ** (-j))
                 modval = mod(i, 2)
                 if (modval .eq. 0) then
                    call sidl_dcomplex__array_set2_f(retval, index(1), 
     $                   index(2), value)
                 else
                    call sidl_dcomplex__array_set_f(retval, index, 
     $                   value)
                 endif
              enddo
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Dcomplex)
        end


C       
C       Method:  create2Fcomplex[]
C       

        subroutine ArrayTest_ArrayOps_create2Fcomplex_fi(d1, d2, retval,
     &     exception)
        implicit none
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out array<fcomplex,2> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2Fcomplex)
        integer*4 lower(2), upper(2), index(2), i, j
        integer*4 modval
        complex*8 value
        retval = 0
        if ((d1 .ge. 0) .and. (d2 .ge. 0)) then
           lower(1) = 0
           upper(1) = d1 - 1
           lower(2) = 0
           upper(2) = d2 - 1
           value = 0
           call sidl_fcomplex__array_createRow_f(2, lower, upper,
     $          retval)
           do i = 0, upper(1)
              index(1) = i
              do j = 0, upper(2)
                 index(2) = j
                 value = cmplx(2.0d0 ** i, 2.0d0 ** (-j))
                 modval = mod(i, 2)
                 if (modval .eq. 0) then
                    call sidl_fcomplex__array_set2_f(retval, index(1), 
     $                   index(2), value)
                 else
                    call sidl_fcomplex__array_set_f(retval, index, 
     $                   value)
                 endif
              enddo
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2Fcomplex)
        end


C       
C       Method:  create2String[]
C       

        subroutine ArrayTest_ArrayOps_create2String_fi(d1, d2, retval, 
     &     exception)
        implicit none
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out array<string,2> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create2String)
        character*9 testtext(10)
        integer*4 lower(2), upper(2), i, j, c, testind
        data testtext / 'I''d', 'rather', 'write', 'programs', 'to',
     $       'write', 'programs', 'than', 'write', 'programs.'/
        retval = 0
        c = 0
        if (d1 .ge. 0 .and. d2 .ge. 0) then
           lower(1) = 0
           upper(1) = d1 - 1
           lower(2) = 0
           upper(2) = d2 - 1
           call sidl_string__array_createRow_f(2, lower, upper, retval)
           do i = 0, d1 - 1
              do j = 0, d2 - 1 
                 testind = mod(c, 10) + 1
                 call sidl_string__array_set2_f(retval, i, j,
     $                testtext(testind))
                 c = c + 1
              enddo
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create2String)
        end


C       
C       Method:  create3Int[]
C       

        subroutine ArrayTest_ArrayOps_create3Int_fi(retval, exception)
        implicit none
C        out array<int,3> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create3Int)
        integer*4 dimen, lower(3), upper(3), ind(3)
        integer*4 value, modval, i, j, k, intFunc
        dimen = 3
        do i = 1, dimen
           lower(i) = 0
           if (i .le. 2) then
              upper(i) = 3
           else
              upper(i) = 2
           endif
        enddo
        call sidl_int__array_createCol_f(dimen, lower, upper, retval)
        do i = 0, upper(1)
           ind(1) = i
           do j = 0, upper(2)
              ind(2) = j
              do k = 0, upper(3)
                 ind(3) = k
                 value = intFunc(dimen, ind)
                 modval = mod(i, 2)
                 if (modval .eq. 0) then
                    call sidl_int__array_set3_f(retval, ind(1), 
     $                 ind(2), ind(3), value)
                 else
                    call sidl_int__array_set_f(retval, ind, 
     $                 value)
                 endif
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create3Int)
        end


C       
C       Method:  create4Int[]
C       

        subroutine ArrayTest_ArrayOps_create4Int_fi(retval, exception)
        implicit none
C        out array<int,4> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create4Int)
        integer*4 dimen, lower(4), upper(4), ind(4)
        integer*4 value, modval, i, j, k, l, intFunc
        dimen = 4
        do i = 1, dimen
           lower(i) = 0
           if (i .le. 2) then
              upper(i) = 3
           else
              upper(i) = 2
           endif
        enddo
        call sidl_int__array_createRow_f(dimen, lower, upper, retval)
        do i = 0, upper(1)
           ind(1) = i
           do j = 0, upper(2)
              ind(2) = j
              do k = 0, upper(3)
                 ind(3) = k
                 do l = 0, upper(4)
                    ind(4) = l
                    value = intFunc(dimen, ind)
                    modval = mod(i, 2)
                    if (modval .eq. 0) then
                       call sidl_int__array_set4_f(retval, ind(1), 
     $                    ind(2), ind(3), ind(4), value)
                    else
                       call sidl_int__array_set_f(retval, ind, 
     $                    value)
                    endif
                 enddo
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create4Int)
        end


C       
C       Method:  create5Int[]
C       

        subroutine ArrayTest_ArrayOps_create5Int_fi(retval, exception)
        implicit none
C        out array<int,5> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create5Int)
        integer*4 dimen, lower(5), upper(5), ind(5)
        integer*4 value, modval, i, j, k, l, m, intFunc
        dimen = 5
        do i = 1, dimen
           lower(i) = 0
           if (i .le. 2) then
              upper(i) = 3
           else
              upper(i) = 2
           endif
        enddo
        call sidl_int__array_createRow_f(dimen, lower, upper, retval)
        do i = 0, upper(1)
           ind(1) = i
           do j = 0, upper(2)
              ind(2) = j
              do k = 0, upper(3)
                 ind(3) = k
                 do l = 0, upper(4)
                    ind(4) = l
                    do m = 0, upper(5)
                       ind(5) = m
                       value = intFunc(dimen, ind)
                       modval = mod(i, 2)
                       if (modval .eq. 0) then
                          call sidl_int__array_set5_f(retval, ind(1), 
     $                         ind(2), ind(3), ind(4), ind(5), value)
                       else
                          call sidl_int__array_set_f(retval, ind, 
     $                         value)
                       endif
                    enddo
                 enddo
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create5Int)
        end


C       
C       Method:  create6Int[]
C       

        subroutine ArrayTest_ArrayOps_create6Int_fi(retval, exception)
        implicit none
C        out array<int,6> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create6Int)
        integer*4 dimen, lower(6), upper(6), ind(6)
        integer*4 value, modval, i, j, k, l, m, n, intFunc
        dimen = 6
        do i = 1, dimen
           lower(i) = 0
           if (i .le. 2) then
              upper(i) = 3
           else
              upper(i) = 2
           endif
        enddo
        call sidl_int__array_createRow_f(dimen, lower, upper, retval)
        do i = 0, upper(1)
           ind(1) = i
           do j = 0, upper(2)
              ind(2) = j
              do k = 0, upper(3)
                 ind(3) = k
                 do l = 0, upper(4)
                    ind(4) = l
                    do m = 0, upper(5)
                       ind(5) = m
                       do n = 0, upper(6)
                          ind(6) = n
                          value = intFunc(dimen, ind)
                          modval = mod(i, 2)
                          if (modval .eq. 0) then
                             call sidl_int__array_set6_f(retval,
     $                            ind(1), ind(2), ind(3), ind(4),
     $                            ind(5), ind(6), value)
                          else
                             call sidl_int__array_set_f(retval, ind, 
     $                            value)
                          endif
                       enddo
                    enddo
                 enddo
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create6Int)
        end


C       
C       Method:  create7Int[]
C       

        subroutine ArrayTest_ArrayOps_create7Int_fi(retval, exception)
        implicit none
C        out array<int,7> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.create7Int)
        integer*4 dimen, lower(7), upper(7), ind(7)
        integer*4 value, modval, i, j, k, l, m, n, o, intFunc
        dimen = 7
        do i = 1, dimen
           lower(i) = 0
           if (i .le. 2) then
              upper(i) = 3
           else
              upper(i) = 2
           endif
        enddo
        call sidl_int__array_createCol_f(dimen, lower, upper, retval)
        do i = 0, upper(1)
           ind(1) = i
           do j = 0, upper(2)
              ind(2) = j
              do k = 0, upper(3)
                 ind(3) = k
                 do l = 0, upper(4)
                    ind(4) = l
                    do m = 0, upper(5)
                       ind(5) = m
                       do n = 0, upper(6)
                          ind(6) = n
                          do o = 0, upper(7)
                             ind(7) = o
                             value = intFunc(dimen, ind)
                             modval = mod(i, 2)
                             if (modval .eq. 0) then
                                call sidl_int__array_set7_f(retval,
     $                               ind(1), ind(2), ind(3), ind(4),
     $                               ind(5), ind(6), ind(7), value)
                             else
                                call sidl_int__array_set_f(retval, ind,
     $                               value)
                             endif
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.create7Int)
        end


C       
C       Method:  makeBool[]
C       

        subroutine ArrayTest_ArrayOps_makeBool_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<bool> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeBool)
        
        call ArrayTest_ArrayOps_createBool_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeBool)
        end


C       
C       Method:  makeChar[]
C       

        subroutine ArrayTest_ArrayOps_makeChar_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<char> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeChar)
    
        call ArrayTest_ArrayOps_createChar_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeChar)
        end


C       
C       Method:  makeInt[]
C       

        subroutine ArrayTest_ArrayOps_makeInt_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<int> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInt)
        call ArrayTest_ArrayOps_createInt_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInt)
        end


C       
C       Method:  makeLong[]
C       

        subroutine ArrayTest_ArrayOps_makeLong_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<long> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeLong)
        call ArrayTest_ArrayOps_createLong_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeLong)
        end


C       
C       Method:  makeString[]
C       

        subroutine ArrayTest_ArrayOps_makeString_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<string> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeString)
        call ArrayTest_ArrayOps_createString_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeString)
        end


C       
C       Method:  makeDouble[]
C       

        subroutine ArrayTest_ArrayOps_makeDouble_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<double> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDouble)
        call ArrayTest_ArrayOps_createDouble_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDouble)
        end


C       
C       Method:  makeFloat[]
C       

        subroutine ArrayTest_ArrayOps_makeFloat_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<float> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFloat)
        call ArrayTest_ArrayOps_createFloat_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFloat)
        end


C       
C       Method:  makeFcomplex[]
C       

        subroutine ArrayTest_ArrayOps_makeFcomplex_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<fcomplex> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeFcomplex)
        call ArrayTest_ArrayOps_createFcomplex_f(len,a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeFcomplex)
        end


C       
C       Method:  makeDcomplex[]
C       

        subroutine ArrayTest_ArrayOps_makeDcomplex_fi(len, a, exception)
        implicit none
C        in int len
        integer*4 :: len
C        out array<dcomplex> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeDcomplex)
        call ArrayTest_ArrayOps_createDcomplex_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeDcomplex)
        end


C       
C       Method:  makeInOutBool[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutBool_fi(a, len, 
     &     exception)
        implicit none
C        inout array<bool> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutBool)
        if (a .ne. 0) then
           call sidl_bool__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createBool_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutBool)
        end


C       
C       Method:  makeInOutChar[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutChar_fi(a, len, 
     &     exception)
        implicit none
C        inout array<char> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutChar)
        if (a .ne. 0) then
           call sidl_char__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createChar_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutChar)
        end


C       
C       Method:  makeInOutInt[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutInt_fi(a, len, exception)
        implicit none
C        inout array<int> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutInt)
        if (a .ne. 0) then
           call sidl_int__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createInt_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutInt)
        end


C       
C       Method:  makeInOutLong[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutLong_fi(a, len, 
     &     exception)
        implicit none
C        inout array<long> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutLong)
        if (a .ne. 0) then
           call sidl_long__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createLong_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutLong)
        end


C       
C       Method:  makeInOutString[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutString_fi(a, len, 
     &     exception)
        implicit none
C        inout array<string> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutString)
        if (a .ne. 0) then
           call sidl_string__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createString_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutString)
        end


C       
C       Method:  makeInOutDouble[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutDouble_fi(a, len, 
     &     exception)
        implicit none
C        inout array<double> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDouble)
        if (a .ne. 0) then
           call sidl_double__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createDouble_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDouble)
        end


C       
C       Method:  makeInOutFloat[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutFloat_fi(a, len, 
     &     exception)
        implicit none
C        inout array<float> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFloat)
        if (a .ne. 0) then
           call sidl_float__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createFloat_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFloat)
        end


C       
C       Method:  makeInOutDcomplex[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutDcomplex_fi(a, len, 
     &     exception)
        implicit none
C        inout array<dcomplex> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutDcomplex)
        if (a .ne. 0) then
           call sidl_dcomplex__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createDcomplex_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutDcomplex)
        end


C       
C       Method:  makeInOutFcomplex[]
C       

        subroutine ArrayTest_ArrayOps_makeInOutFcomplex_fi(a, len, 
     &     exception)
        implicit none
C        inout array<fcomplex> a
        integer*8 :: a
C        in int len
        integer*4 :: len
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOutFcomplex)
        if (a .ne. 0) then
           call sidl_fcomplex__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_createFcomplex_f(len, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOutFcomplex)
        end


C       
C       Method:  makeInOut2Int[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut2Int_fi(a, d1, d2, 
     &     exception)
        implicit none
C        inout array<int,2> a
        integer*8 :: a
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Int)
        if (a .ne. 0) then
           call sidl_int__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create2Int_f(d1, d2, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Int)
        end


C       
C       Method:  makeInOut2Double[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut2Double_fi(a, d1, d2, 
     &     exception)
        implicit none
C        inout array<double,2> a
        integer*8 :: a
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Double)
        if (a .ne. 0) then
           call sidl_double__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create2Double_f(d1, d2, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Double)
        end


C       
C       Method:  makeInOut2Float[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut2Float_fi(a, d1, d2, 
     &     exception)
        implicit none
C        inout array<float,2> a
        integer*8 :: a
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Float)
        if (a .ne. 0) then
           call sidl_float__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create2Float_f(d1, d2, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Float)
        end


C       
C       Method:  makeInOut2Dcomplex[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut2Dcomplex_fi(a, d1, d2, 
     &     exception)
        implicit none
C        inout array<dcomplex,2> a
        integer*8 :: a
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Dcomplex)
        if (a .ne. 0) then
           call sidl_dcomplex__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create2Dcomplex_f(d1, d2, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Dcomplex)
        end


C       
C       Method:  makeInOut2Fcomplex[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut2Fcomplex_fi(a, d1, d2, 
     &     exception)
        implicit none
C        inout array<fcomplex,2> a
        integer*8 :: a
C        in int d1
        integer*4 :: d1
C        in int d2
        integer*4 :: d2
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut2Fcomplex)
        if (a .ne. 0) then
           call sidl_fcomplex__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create2Fcomplex_f(d1, d2, a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut2Fcomplex)
        end


C       
C       Method:  makeInOut3Int[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut3Int_fi(a, exception)
        implicit none
C        inout array<int,3> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut3Int)
        if (a .ne. 0) then
           call sidl_int__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create3Int_f(a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut3Int)
        end


C       
C       Method:  makeInOut4Int[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut4Int_fi(a, exception)
        implicit none
C        inout array<int,4> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut4Int)
        if (a .ne. 0) then
           call sidl_int__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create4Int_f(a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut4Int)
        end


C       
C       Method:  makeInOut5Int[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut5Int_fi(a, exception)
        implicit none
C        inout array<int,5> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut5Int)
        if (a .ne. 0) then
           call sidl_int__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create5Int_f(a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut5Int)
        end


C       
C       Method:  makeInOut6Int[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut6Int_fi(a, exception)
        implicit none
C        inout array<int,6> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut6Int)
        if (a .ne. 0) then
           call sidl_int__array_deleteRef_f(a)
        endif
        call ArrayTest_ArrayOps_create6Int_f(a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut6Int)
        end


C       
C       Method:  makeInOut7Int[]
C       

        subroutine ArrayTest_ArrayOps_makeInOut7Int_fi(a, exception)
        implicit none
C        inout array<int,7> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.makeInOut7Int)
      if (a .ne. 0) then
         call sidl_int__array_deleteRef_f(a)
      endif
      call ArrayTest_ArrayOps_create7Int_f(a, exception)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.makeInOut7Int)
        end


C       
C       Method:  checkGeneric[]
C       Return as out parameters the type and dimension of the 
C       array passed in. If a is NULL, dimen == type == 0 on exit.
C       The contents of the array have the default values for a 
C       newly created array.
C       

        subroutine ArrayTest_ArrayOps_checkGeneric_fi(a, dmn, tp, 
     &     exception)
        implicit none
C        in array<> a
        integer*8 :: a
C        out int dmn
        integer*4 :: dmn
C        out int tp
        integer*4 :: tp
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkGeneric)
      if (a .ne. 0) then
         call sidl__array_dimen_f(a, dmn)
         call sidl__array_type_f(a, tp)
      else 
         dmn = 0
         tp = 0
      end if
     
      
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkGeneric)
        end


C       
C       Method:  createGeneric[]
C       Create an array of the type and dimension specified and
C       return it. A type of 0 causes a NULL array to be returned.
C       

        subroutine ArrayTest_ArrayOps_createGeneric_fi(dmn, tp, retval, 
     &     exception)
        implicit none
C        in int dmn
        integer*4 :: dmn
C        in int tp
        integer*4 :: tp
C        out array<> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.createGeneric)
C     Insert the implementation here...
      integer*4 lower(7), upper(7) 
      integer*4 i
      do i=1,7
         lower(i)=0
         upper(i)=2
      enddo
      call createarraybytype(tp, dmn, lower, upper, retval)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.createGeneric)
        end


C       
C       Method:  passGeneric[]
C       Testing passing generic arrays using every possible mode.
C       The returned array is a copy of inArg, so if inArg != NULL,
C       the return value should != NULL. outArg is also a copy of
C       inArg.
C       If inOutArg is NULL on entry, a 2-D array of int that should
C       pass check2Int is returned.
C       If inOutArg is not NULL on entry and its dimension is even,
C       it is returned unchanged; otherwise, NULL is returned.
C       

        subroutine ArrayTest_ArrayOps_passGeneric_fi(inArg, inOutArg, 
     &     outArg, retval, exception)
        implicit none
C        in array<> inArg
        integer*8 :: inArg
C        inout array<> inOutArg
        integer*8 :: inOutArg
C        out array<> outArg
        integer*8 :: outArg
C        out array<> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.passGeneric)
C     Insert the implementation here...
      integer*4 lower(7), upper(7), i, type, dimen, outdimen
      call sidl__array_type_f(inArg, type) 
      if (inArg .ne. 0) then    
         dimen = 0
         call sidl__array_dimen_f(inArg, dimen)
         do i=1, dimen+1 
            call sidl__array_lower_f(inArg, i-1, lower(i))
            call sidl__array_upper_f(inArg, i-1, upper(i))
         enddo
      end if
      call createarraybytype(type, dimen, lower, upper, retval)
      call createarraybytype(type, dimen, lower, upper, outArg)
      call copyarraybytype(inArg, retval)
      call copyarraybytype(inArg, outArg)
      
      if (inOutArg .ne. 0) then
         call sidl__array_dimen_f(inOutArg, outdimen)
         if (mod(outdimen, 2) .eq. 1) then  
            call sidl__array_deleteref_f(inOutArg)
            inOutArg = 0
         end if
      else 
         call ArrayTest_ArrayOps_create2Int_f(3, 3, inOutArg,
     $        exception)
      end if
      
      
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.passGeneric)
        end


C       
C       Method:  initRarray1Int[]
C       

        subroutine ArrayTest_ArrayOps_initRarray1Int_fi(a, n, exception)
        implicit none
C        in int n
        integer*4 :: n
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        inout rarray<int> a(n)
        integer*4 :: a(0:n-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Int)
        integer*4 value, i
        integer*8 nextprime, tmp
        if (n .ge. 0) then
           value = 0
           do i = 0, n - 1
              tmp = value
              value = nextprime(tmp)
              a(i) = value
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Int)
        end


C       
C       Method:  initRarray3Int[]
C       

        subroutine ArrayTest_ArrayOps_initRarray3Int_fi(a, n, m, o, 
     &     exception)
        implicit none
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        inout rarray<int,3> a(n,m,o)
        integer*4 :: a(0:n-1, 0:m-1, 0:o-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray3Int)
        integer*4 dimen,  ind(3)
        integer*4 value, modval, i, j, k, intFunc
        dimen = 3
        do i = 0, n-1
           ind(1) = i
           do j = 0, m-1
              ind(2) = j
              do k = 0, o-1
                 ind(3) = k
                 value = intFunc(dimen, ind)
                 a(i,j,k)=value
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray3Int)
        end


C       
C       Method:  initRarray7Int[]
C       

        subroutine ArrayTest_ArrayOps_initRarray7Int_fi(a, n, m, o, p, 
     &     q, r, s, exception)
        implicit none
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        in int p
        integer*4 :: p
C        in int q
        integer*4 :: q
C        in int r
        integer*4 :: r
C        in int s
        integer*4 :: s
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        inout rarray<int,7> a(n,m,o,p,q,r,s)
        integer*4 :: a(0:n-1, 0:m-1, 0:o-1, 0:p-1, 0:q-1, 0:r-1, 0:s-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray7Int)
        integer*4 dimen, lower(7), upper(7), ind(7)
        integer*4 value, modval, d, e, f, g, h, i, j, intFunc
        dimen = 7
        do d = 0, n-1
           ind(1) = d
           do e = 0, m-1
              ind(2) = e
              do f = 0, o-1
                 ind(3) = f
                 do g = 0, p-1
                    ind(4) = g
                    do h = 0, q-1
                       ind(5) = h
                       do i = 0, r-1
                          ind(6) = i
                          do j = 0, s-1
                             ind(7) = j
                             value = intFunc(dimen, ind)
                             a(d,e,f,g,h,i,j) = value
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
           enddo
        enddo
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray7Int)
        end


C       
C       Method:  initRarray1Double[]
C       

        subroutine ArrayTest_ArrayOps_initRarray1Double_fi(a, n, 
     &     exception)
        implicit none
C        in int n
        integer*4 :: n
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        inout rarray<double> a(n)
        double precision :: a(0:n-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Double)
        integer*4 i
        double precision value

        if (n .ge. 0) then
           do i = 0, n - 1
              value = 2.0d0 ** (-i*1.0)
              a(i) = value
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Double)
        end


C       
C       Method:  initRarray1Dcomplex[]
C       

        subroutine ArrayTest_ArrayOps_initRarray1Dcomplex_fi(a, n, 
     &     exception)
        implicit none
C        in int n
        integer*4 :: n
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        inout rarray<dcomplex> a(n)
        double complex :: a(0:n-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.initRarray1Dcomplex)
        integer*4 i
        complex*16 value

        if (n .ge. 0) then
           do i = 0, n - 1
              value = cmplx(2.0d0 ** i, 2.0d0 ** (-i))
              a(i) = value
           enddo
        endif
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.initRarray1Dcomplex)
        end


C       
C       Method:  checkRarray1Int[]
C       

        subroutine ArrayTest_ArrayOps_checkRarray1Int_fi(a, n, retval, 
     &     exception)
        implicit none
C        in int n
        integer*4 :: n
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int> a(n)
        integer*4 :: a(0:n-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Int)
        integer*4 i, prime
        integer*8 nextprime, tmp
        retval = .false.

        prime = 0
        do i = 0, n-1
           tmp = prime
           prime = nextprime(tmp)
           if (a(i) .ne. prime) then
              retval = .false.
              return
           endif
        enddo
        retval = .true.
        
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Int)
        end


C       
C       Method:  checkRarray3Int[]
C       

        subroutine ArrayTest_ArrayOps_checkRarray3Int_fi(a, n, m, o, 
     &     retval, exception)
        implicit none
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,3> a(n,m,o)
        integer*4 :: a(0:n-1, 0:m-1, 0:o-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray3Int)
        integer*4 i, j, k, expectedvalue, ind(3)
        integer*4 intFunc
        retval = .false.
        
        do i = 0, n-1
           ind(1) = i
           do j = 0, m-1
              ind(2) = j
              do k = 0, o-1
                 ind(3) = k
                 expectedvalue = intFunc(3, ind)
                 if (a(i,j,k) .ne. expectedvalue) then
                    return
                 endif
              enddo
           enddo
        enddo
        retval = .true.
        
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray3Int)
        end


C       
C       Method:  checkRarray7Int[]
C       

        subroutine ArrayTest_ArrayOps_checkRarray7Int_fi(a, n, m, o, p, 
     &     q, r, s, retval, exception)
        implicit none
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        in int p
        integer*4 :: p
C        in int q
        integer*4 :: q
C        in int r
        integer*4 :: r
C        in int s
        integer*4 :: s
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,7> a(n,m,o,p,q,r,s)
        integer*4 :: a(0:n-1, 0:m-1, 0:o-1, 0:p-1, 0:q-1, 0:r-1, 0:s-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray7Int)
        integer*4 d,e,f,g,h,i,j
        integer*4 value
        integer*4 ind(7), alow(7), aup(7)
        integer*4 intFunc
        retval = .false.

        do d = 0, n-1
           ind(1) = d
           do e = 0, m-1
              ind(2) = e
              do f = 0, o-1
                 ind(3) = f
                 do g = 0, p-1
                    ind(4) = g
                    do h = 0, q-1
                       ind(5) = h
                       do i = 0, r-1
                          ind(6) = i
                          do j = 0, s-1
                             ind(7) = j
                             value=intFunc(7,ind)
                             if (value .ne. a(d,e,f,g,h,i,j)) then
                                return
                             endif
                          enddo
                       enddo
                    enddo
                 enddo
              enddo
           enddo
        enddo
        retval = .true.
      
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray7Int)
        end


C       
C       Method:  checkRarray1Double[]
C       

        subroutine ArrayTest_ArrayOps_checkRarray1Double_fi(a, n, 
     &     retval, exception)
        implicit none
C        in int n
        integer*4 :: n
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<double> a(n)
        double precision :: a(0:n-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Double)
        double precision expectedvalue
        integer*4 i
        retval = .false.

        do i = 0, n - 1
           expectedvalue = (2.0d0 ** (-i))
           if (a(i) .ne. expectedvalue) then
              return
           endif
        enddo
        retval = .true.
        
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Double)
        end


C       
C       Method:  checkRarray1Dcomplex[]
C       

        subroutine ArrayTest_ArrayOps_checkRarray1Dcomplex_fi(a, n, 
     &     retval, exception)
        implicit none
C        in int n
        integer*4 :: n
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<dcomplex> a(n)
        double complex :: a(0:n-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkRarray1Dcomplex)
        complex*16 expectedvalue
        integer*4 i
        retval = .false.
        do i = 0, n - 1
           expectedvalue = cmplx(2.0d0 ** i, 2.0d0 ** (-i))
           if (a(i) .ne. expectedvalue) then
              return
           endif
        enddo
        retval = .true.
       
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkRarray1Dcomplex)
        end


C       
C       Method:  matrixMultiply[]
C       

        subroutine ArrayTest_ArrayOps_matrixMultiply_fi(a, b, res, n, m,
     &     o, exception)
        implicit none
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,2> a(n,m)
        integer*4 :: a(0:n-1, 0:m-1)
C        in rarray<int,2> b(m,o)
        integer*4 :: b(0:m-1, 0:o-1)
C        inout rarray<int,2> res(n,o)
        integer*4 :: res(0:n-1, 0:o-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.matrixMultiply)
        call locMatrixMultiply(a,b,res,n,m,o)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.matrixMultiply)
        end


C       
C       Method:  checkMatrixMultiply[]
C       

        subroutine ArrayTest_ArrayOps_checkMatrixMultiply_fi(a, b, res, 
     &     n, m, o, retval, exception)
        implicit none
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,2> a(n,m)
        integer*4 :: a(0:n-1, 0:m-1)
C        in rarray<int,2> b(m,o)
        integer*4 :: b(0:m-1, 0:o-1)
C        in rarray<int,2> res(n,o)
        integer*4 :: res(0:n-1, 0:o-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkMatrixMultiply)
        integer*4 test(0:n-1, 0:o-1)
        integer*4 i,j
        retval = .false.
        
        call locMatrixMultiply(a,b,test,n,m,o)
        do i = 0, n-1
           do j = 0, o-1
              if(test(i,j) .ne. res(i,j)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
        
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkMatrixMultiply)
        end


C       
C       Method:  mm[]
C       

        subroutine ArrayTest_ArrayOps_mm_fi(self, a, b, res, n, m, o, 
     &     exception)
        implicit none
C        in ArrayTest.ArrayOps self
        integer*8 :: self
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,2> a(n,m)
        integer*4 :: a(0:n-1, 0:m-1)
C        in rarray<int,2> b(m,o)
        integer*4 :: b(0:m-1, 0:o-1)
C        inout rarray<int,2> res(n,o)
        integer*4 :: res(0:n-1, 0:o-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.mm)
        call locMatrixMultiply(a,b,res,n,m,o)
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.mm)
        end


C       
C       Method:  checkmm[]
C       

        subroutine ArrayTest_ArrayOps_checkmm_fi(self, a, b, res, n, m, 
     &     o, retval, exception)
        implicit none
C        in ArrayTest.ArrayOps self
        integer*8 :: self
C        in int n
        integer*4 :: n
C        in int m
        integer*4 :: m
C        in int o
        integer*4 :: o
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,2> a(n,m)
        integer*4 :: a(0:n-1, 0:m-1)
C        in rarray<int,2> b(m,o)
        integer*4 :: b(0:m-1, 0:o-1)
C        in rarray<int,2> res(n,o)
        integer*4 :: res(0:n-1, 0:o-1)

C       DO-NOT-DELETE splicer.begin(ArrayTest.ArrayOps.checkmm)
        integer*4 test(0:n-1, 0:o-1)
        integer*4 i,j
        retval = .false.
        
        call locMatrixMultiply(a,b,test,n,m,o)
        do i = 0, n-1
           do j = 0, o-1
              if(test(i,j) .ne. res(i,j)) then
                 return
              endif
           enddo
        enddo
        retval = .true.
C       DO-NOT-DELETE splicer.end(ArrayTest.ArrayOps.checkmm)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
