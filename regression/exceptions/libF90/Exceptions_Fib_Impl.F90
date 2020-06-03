! 
! File:          Exceptions_Fib_Impl.F90
! Symbol:        Exceptions.Fib-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Exceptions.Fib
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Exceptions.Fib" (version 1.0)
! 
! This class holds the method <code>getFib</code> that generates the
! requested Fibonacci numbers.
! 


#include "Exceptions_Fib_fAbbrev.h"
#include "Exceptions_FibException_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "Exceptions_NegativeValueException_fAbbrev.h"
#include "sidl_MemAllocException_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
#include "Exceptions_TooBigException_fAbbrev.h"
#include "Exceptions_TooDeepException_fAbbrev.h"
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



! 
! Method:  _ctor[]
! Class constructor called when the class is created.
! 

recursive subroutine Exceptions_Fib__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_Fib
  use Exceptions_Fib_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor.use)
  implicit none
  type(Exceptions_Fib_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor)
! Nothing needed here...
! DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor)
end subroutine Exceptions_Fib__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Exceptions_Fib__ctor2_mi(self, private_data, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_Fib
  use Exceptions_Fib_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor2.use)
  ! Insert-Code-Here {Exceptions.Fib._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor2.use)
  implicit none
  type(Exceptions_Fib_t) :: self
  ! in
  type(Exceptions_Fib_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.Fib._ctor2)
! Insert-Code-Here {Exceptions.Fib._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Exceptions.Fib._ctor2)
end subroutine Exceptions_Fib__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Exceptions_Fib__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_Fib
  use Exceptions_Fib_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib._dtor.use)
  implicit none
  type(Exceptions_Fib_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.Fib._dtor)
! Nothing needed here...
! DO-NOT-DELETE splicer.end(Exceptions.Fib._dtor)
end subroutine Exceptions_Fib__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Exceptions_Fib__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_Fib
  use Exceptions_Fib_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.Fib._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Exceptions.Fib._load)
end subroutine Exceptions_Fib__load_mi


! 
! Method:  getFib[]
! <p>
! Generate the requested Fibonacci number or generate Exceptions if
! the input Fibonacci number is invalid or if any of the maximum depth
! or maximum value parameters are exceeded.  The last argument of the
! method should be zero.
! </p>
! <p>
! The algorithm should be similar to the <code>Java</code> code below.
! </p>
! <pre>
! public int getFib(int n, int max_depth, int max_value, int depth)
! throws NegativeValueException, FibException {
! 
! if (n < 0) {
! throw new NegativeValueException("n negative");
! 
! } else if (depth > max_depth) {
! throw new TooDeepException("too deep");
! 
! } else if (n == 0) {
! return 1;
! 
! } else if (n == 1) {
! return 1;
! 
! } else {
! int a = getFib(n-1, max_depth, max_value, depth+1);
! int b = getFib(n-2, max_depth, max_value, depth+1);
! if (a + b > max_value) {
! throw new TooBigException("too big");
! }
! return a + b;
! }
! } 
! </pre>
! 

recursive subroutine Exceptions_Fib_getFib_mi(self, n, max_depth, max_value,   &
  depth, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_Fib
  use Exceptions_FibException
  use Exceptions_NegativeValueException
  use sidl_MemAllocException
  use Exceptions_Fib_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib.getFib.use)
  use Exceptions_TooBigException
  use Exceptions_TooDeepException
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib.getFib.use)
  implicit none
  type(Exceptions_Fib_t) :: self
  ! in
  integer (kind=sidl_int) :: n
  ! in
  integer (kind=sidl_int) :: max_depth
  ! in
  integer (kind=sidl_int) :: max_value
  ! in
  integer (kind=sidl_int) :: depth
  ! in
  integer (kind=sidl_int) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Exceptions.Fib.getFib)
  type(Exceptions_NegativeValueException_t) :: negexc
  type(Exceptions_TooDeepException_t) :: deepexc
  type(Exceptions_TooBigException_t) :: toobigexc
  type(Exceptions_FibException_t) :: fibexc
  type(sidl_BaseInterface_t) :: throwaway
  character (len=*) myfilename 
  parameter(myfilename='Exceptions_Fib_Impl.f')
  integer(kind=sidl_int) a, b
  retval = 0_sidl_int
  if (n .lt. 0_sidl_int) then
     call new(negexc, throwaway)
     if (not_null(negexc)) then
        call setNote(negexc, &
             'called with negative n', throwaway)
        call add( &
             negexc, myfilename, 57_sidl_int, 'Exceptions_Fib_getFib_impl',&
             throwaway)
        call cast(negexc, exception,throwaway)
        call deleteRef(negexc,throwaway)
        return
     endif
  else if (depth .gt. max_depth) then
     call new(deepexc,throwaway)
     if (not_null(deepexc)) then
        call setNote( deepexc, &
             'exceeded specified recursion depth',throwaway)
        call add( deepexc, &
             myfilename, 70_sidl_int, 'Exceptions_Fib_getFib_impl', &
             throwaway)
        call cast(deepexc, exception,throwaway)
        call deleteRef(deepexc,throwaway)
        return
     endif
  else if (n .eq. 0_sidl_int) then
     retval = 1_sidl_int
  else if (n .eq. 1_sidl_int) then
     retval = 1_sidl_int
  else 
   ! 
   ! Note that we must call the stub version of this method
   ! because g77 does not (currently) support recursion.
   ! 
     call getFib(self, &
          n-1_sidl_int, &
          max_depth, & 
          max_value, &
          depth+1_sidl_int, & 
          a, &
          exception)
     call cast(exception, fibexc,throwaway)
     if (not_null(fibexc)) then
        call add( &
             fibexc, &
             myfilename, &
             90_sidl_int, &
             'Exceptions_Fib_getFib_impl',&
             throwaway)
        call deleteRef(fibexc,throwaway)
        return
     endif
   ! 
   ! Note that we must call the stub version of this method
   ! because g77 does not (currently) support recursion.
   ! 
     call getFib(self, &
          n-2_sidl_int, &
          max_depth, &
          max_value, &
          depth+1_sidl_int, &
          b, &
          exception)
     call cast(exception, fibexc,throwaway)
     if (not_null(fibexc)) then
        call add( &
             fibexc, &
             myfilename, &
             105_sidl_int,  &
             'Exceptions_Fib_getFib_impl',&
             throwaway)
        call deleteRef(fibexc,throwaway)
        return
     endif
     retval = a+b
     if (retval .gt. max_value) then
        retval = 0_sidl_int
        call new(toobigexc,throwaway)
        if (not_null(toobigexc)) then
           call setNote( &
                toobigexc, &
                'result exceeds specified maximum value',throwaway)
           call add( &
                toobigexc, &
                myfilename, &
                103_sidl_int, &
                'Exceptions_Fib_getFib_impl',throwaway)
           call cast(toobigexc, exception,throwaway)
           call deleteRef(toobigexc,throwaway)
           return
        endif
     endif
  endif
! DO-NOT-DELETE splicer.end(Exceptions.Fib.getFib)
end subroutine Exceptions_Fib_getFib_mi


! 
! Method:  noLeak[]
! Check for memory/reference leaks in the presence of an exception.
! The impl will throw an exception and assign random values to
! out parameters to prove that out values are ignored.
! The intent is that row-major arrays should be passed to parameters
! a1, a2, a3.
! 

recursive subroutine Exceptions_Fib_noLeak_mi(self, a1, a2, a3, r1, r2, m, n,  &
  c1, c2, c3, s1, s2, s3, o1, o2, o3, retval, exception)
  use sidl
  use sidl_BaseClass
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Exceptions_Fib
  use sidl_SIDLException
  use sidl_int_array
  use Exceptions_Fib_impl
  ! DO-NOT-DELETE splicer.begin(Exceptions.Fib.noLeak.use)
  ! Insert-Code-Here {Exceptions.Fib.noLeak.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Exceptions.Fib.noLeak.use)
  implicit none
  type(Exceptions_Fib_t) :: self
  ! in
  type(sidl_int_2d) :: a1
  ! in
  type(sidl_int_2d) :: a2
  ! inout
  type(sidl_int_2d) :: a3
  ! out
  integer (kind=sidl_int) :: m
  ! in
  integer (kind=sidl_int) :: n
  ! in
  type(sidl_int_1d) :: c1
  ! in
  type(sidl_int_1d) :: c2
  ! inout
  type(sidl_int_1d) :: c3
  ! out
  character (len=*) :: s1
  ! in
  character (len=*) :: s2
  ! inout
  character (len=*) :: s3
  ! out
  type(sidl_BaseClass_t) :: o1
  ! in
  type(sidl_BaseClass_t) :: o2
  ! inout
  type(sidl_BaseClass_t) :: o3
  ! out
  type(sidl_int_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out
  integer (kind=sidl_int), target, dimension(0:m-1, 0:n-1) :: r1
  ! in
  integer (kind=sidl_int), target, dimension(0:m-1, 0:n-1) :: r2
  ! inout

! DO-NOT-DELETE splicer.begin(Exceptions.Fib.noLeak)
  type(sidl_BaseInterface_t) :: throwaway
  type(sidl_SIDLException_t) :: myExc
  if (not_null(a2)) then
    call deleteRef(a2)
    call set_null(a2)
  endif	
  call set_null(a3)
  if (not_null(c2)) then
     call deleteRef(c2)
     call set_null(c2)
  endif
  call set_null(c3)
  if (not_null(o2)) then
     call deleteRef(o2, throwaway)
     call set_null(o2)
  endif
  call set_null(o3)
  call set_null(retval)
  call new(myExc, throwaway)
  call setNote(myExc, 'Must thrown', throwaway)
  call cast(myExc, exception,throwaway)
  call deleteRef(myExc,throwaway)
  return
! DO-NOT-DELETE splicer.end(Exceptions.Fib.noLeak)
end subroutine Exceptions_Fib_noLeak_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Nothing needed here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
