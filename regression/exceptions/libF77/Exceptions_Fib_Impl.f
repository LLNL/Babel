C       
C       File:          Exceptions_Fib_Impl.f
C       Symbol:        Exceptions.Fib-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Exceptions.Fib
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Exceptions.Fib" (version 1.0)
C       
C       This class holds the method <code>getFib</code> that generates the
C       requested Fibonacci numbers.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Nothing needed here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Exceptions_Fib__ctor_fi(self, exception)
        implicit none
C        in Exceptions.Fib self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor)
C       Nothing needed here...
C       DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Exceptions_Fib__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in Exceptions.Fib self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor2)
C       Insert-Code-Here {Exceptions.Fib._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Exceptions_Fib__dtor_fi(self, exception)
        implicit none
C        in Exceptions.Fib self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.Fib._dtor)
C       Nothing needed here...
C       DO-NOT-DELETE splicer.end(Exceptions.Fib._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Exceptions_Fib__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.Fib._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Exceptions.Fib._load)
        end


C       
C       Method:  getFib[]
C       <p>
C       Generate the requested Fibonacci number or generate Exceptions if
C       the input Fibonacci number is invalid or if any of the maximum depth
C       or maximum value parameters are exceeded.  The last argument of the
C       method should be zero.
C       </p>
C       <p>
C       The algorithm should be similar to the <code>Java</code> code below.
C       </p>
C       <pre>
C       public int getFib(int n, int max_depth, int max_value, int depth)
C       throws NegativeValueException, FibException {
C       
C       if (n < 0) {
C       throw new NegativeValueException("n negative");
C       
C       } else if (depth > max_depth) {
C       throw new TooDeepException("too deep");
C       
C       } else if (n == 0) {
C       return 1;
C       
C       } else if (n == 1) {
C       return 1;
C       
C       } else {
C       int a = getFib(n-1, max_depth, max_value, depth+1);
C       int b = getFib(n-2, max_depth, max_value, depth+1);
C       if (a + b > max_value) {
C       throw new TooBigException("too big");
C       }
C       return a + b;
C       }
C       } 
C       </pre>
C       

        subroutine Exceptions_Fib_getFib_fi(self, n, max_depth, 
     &     max_value, depth, retval, exception)
        implicit none
C        in Exceptions.Fib self
        integer*8 :: self
C        in int n
        integer*4 :: n
C        in int max_depth
        integer*4 :: max_depth
C        in int max_value
        integer*4 :: max_value
C        in int depth
        integer*4 :: depth
C        out int retval
        integer*4 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Exceptions.Fib.getFib)
        character*(*) myfilename 
        parameter(myfilename='Exceptions_Fib_Impl.f')
        integer*4 a, b
        integer*8 throwaway_exception
        retval = 0
        if (n .lt. 0) then
          call Exceptions_NegativeValueException__create_f
     $          (exception, throwaway_exception)
          if (exception .ne. 0) then
            call Exceptions_NegativeValueException_setNote_f(
     $             exception, 
     $             'called with negative n',
     $            throwaway_exception)
            call Exceptions_NegativeValueException_add_f(
     $             exception, 
     $             myfilename,
     $             57, 
     $             'Exceptions_Fib_getFib_impl',
     $           throwaway_exception)
            return
          endif
        else if (depth .gt. max_depth) then
          call Exceptions_TooDeepException__create_f
     $          (exception, throwaway_exception)
          if (exception .ne. 0) then
            call Exceptions_TooDeepException_setNote_f(
     $             exception, 
     $             'exceeded specified recursion depth',
     $            throwaway_exception)
            call Exceptions_TooDeepException_add_f(
     $             exception, 
     $             myfilename,
     $             70, 
     $             'Exceptions_Fib_getFib_impl',
     $           throwaway_exception)
            return
          endif
        else if (n .eq. 0) then
          retval = 1
        else if (n .eq. 1) then
          retval = 1
        else 
C       
C       Note that we must call the stub version of this method
C       because g77 does not (currently) support recursion.
C       
          call Exceptions_Fib_getFib_f(self, 
     $                                    n-1, 
     $                                    max_depth, 
     $                                    max_value,
     $                                    depth+1, 
     $                                    a, 
     $                                    exception)
          if (exception .ne. 0) then
            call sidl_SIDLException_add_f(
     $             exception, 
     $             myfilename,
     $             90, 
     $             'Exceptions_Fib_getFib_impl',
     $            throwaway_exception)
            return
          endif
C       
C       Note that we must call the stub version of this method
C       because g77 does not (currently) support recursion.
C       
          call Exceptions_Fib_getFib_f(self, 
     $                                    n-2, 
     $                                    max_depth, 
     $                                    max_value,
     $                                    depth+1, 
     $                                    b, 
     $                                    exception)
          if (exception .ne. 0) then
            call sidl_SIDLException_add_f(
     $             exception, 
     $             myfilename,
     $             105, 
     $             'Exceptions_Fib_getFib_impl',
     $            throwaway_exception)
            return
          endif
          retval = a+b
          if (retval .gt. max_value) then
            retval = 0
            call Exceptions_TooBigException__create_f
     $           (exception, throwaway_exception)
            if (exception .ne. 0) then
              call Exceptions_TooBigException_setNote_f(
     $               exception, 
     $               'result exceeds specified maximum value',
     $              throwaway_exception)
              call Exceptions_TooBigException_add_f(
     $               exception, 
     $               myfilename,
     $               103, 
     $               'Exceptions_Fib_getFib_impl',
     $             throwaway_exception)
              return
            endif
          endif
        endif
C       DO-NOT-DELETE splicer.end(Exceptions.Fib.getFib)
        end


C       
C       Method:  noLeak[]
C       Check for memory/reference leaks in the presence of an exception.
C       The impl will throw an exception and assign random values to
C       out parameters to prove that out values are ignored.
C       The intent is that row-major arrays should be passed to parameters
C       a1, a2, a3.
C       

        subroutine Exceptions_Fib_noLeak_fi(self, a1, a2, a3, r1, r2, m,
     &     n, c1, c2, c3, s1, s2, s3, o1, o2, o3, retval, exception)
        implicit none
C        in Exceptions.Fib self
        integer*8 :: self
C        in array<int,2,column-major> a1
        integer*8 :: a1
C        inout array<int,2,column-major> a2
        integer*8 :: a2
C        out array<int,2,column-major> a3
        integer*8 :: a3
C        in int m
        integer*4 :: m
C        in int n
        integer*4 :: n
C        in array<int> c1
        integer*8 :: c1
C        inout array<int> c2
        integer*8 :: c2
C        out array<int> c3
        integer*8 :: c3
C        in string s1
        character*(*) :: s1
C        inout string s2
        character*(*) :: s2
C        out string s3
        character*(*) :: s3
C        in sidl.BaseClass o1
        integer*8 :: o1
C        inout sidl.BaseClass o2
        integer*8 :: o2
C        out sidl.BaseClass o3
        integer*8 :: o3
C        out array<int,2,column-major> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception
C        in rarray<int,2> r1(m,n)
        integer*4 :: r1(0:m-1, 0:n-1)
C        inout rarray<int,2> r2(m,n)
        integer*4 :: r2(0:m-1, 0:n-1)

C       DO-NOT-DELETE splicer.begin(Exceptions.Fib.noLeak)
        integer*8 throwaway
        if (a2 .ne. 0) then
          call sidl_int__array_deleteRef_f(a2)
          a2 = 1
        endif
        a3 = 1
        if (c2 .ne. 0) then
          call sidl_int__array_deleteRef_f(c2)
          c2 = 1
        endif
        c3 = 1
        if (o2 .ne. 0) then
           call sidl_BaseClass_deleteRef_f(o2, throwaway)
        endif
        o2 = 1
        o3 = 1
        retval = 1
        call sidl_SIDLException__create_f
     $      (exception, throwaway)
        if (exception .ne. 0) then
           call sidl_SIDLException_setNote_f(
     $         exception,
     $         'This method should throw an exception.',
     $         throwaway)
        endif
        return
C       DO-NOT-DELETE splicer.end(Exceptions.Fib.noLeak)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Nothing needed here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
