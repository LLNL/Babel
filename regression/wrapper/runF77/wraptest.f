c
c     File:       wraptest.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 5006 $
c     Date:       $Date: 2005-11-01 15:20:38 -0800 (Tue, 01 Nov 2005) $
c     Description:Exercise the FORTRAN interface
c
c
      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker, throwaway
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_startPart_f(tracker, number, throwaway)
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      end

      subroutine reporttest(test, number,  python)
      implicit none
      integer*4 number
      integer*8 tracker, throwaway
      logical test, python
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker, throwaway)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        throwaway)
      else
         if (python) then
            call synch_RegOut_endPart_f(tracker, number, XFAIL,
     $           throwaway)
         else
            call synch_RegOut_endPart_f(tracker, number, FAIL,
     $           throwaway)
         endif
      endif
      call synch_RegOut_deleteRef_f(tracker, throwaway)
      number = number + 1
      end

      program wraptest
      implicit none
      integer*4 test
      integer*8 tracker, throwaway
      integer*8 data, user, pdata, backup
      integer*8 a_string, a_int
      integer*4 d_int
      character*80 d_string
      character*80 d_ctortest
      character*80 d_silly
      integer*8 d_test
      test = 1
      call synch_RegOut_getInstance_f(tracker, throwaway)
      call synch_RegOut_setExpectations_f(tracker, 5, throwaway)
      call synch_RegOut_writeComment_f(tracker,
     $     'Data Wrapping tests', throwaway)

c     pdata is the internal data, and holds two arrays, string an int.
      call sidl_opaque__array_create1d_f(2, pdata)
      call sidl_string__array_create1d_f(2, a_string)
      call sidl_int__array_create1d_f(1, a_int)

c     Initialize to bad values
      d_string = 'wrong!'
      d_ctortest = 'certainly not'
      d_int = 0

c     initialize the data arrays
      call sidl_string__array_set1_f(a_string, 0, d_string)
      call sidl_string__array_set1_f(a_string, 1, d_ctortest)
      call sidl_int__array_set1_f(a_int, 0, d_int)

c     initilize pdata
      call sidl_opaque__array_set1_f(pdata, 0, a_string)
      call sidl_opaque__array_set1_f(pdata, 1, a_int)

      call starttest(test)
      call wrapper_User__create_f(user, throwaway)
      call reporttest(user .ne. 0, test, .false.)

c     wrap pdata in the Data object
      call starttest(test)
      backup = pdata

c     private data first, then the object being created
      data = 0
      call wrapper_Data__wrapObj_f(pdata, data, throwaway)
      call reporttest(data .ne. 0, test, .false.)

      call starttest(test)
      call wrapper_Data__get_data_f(data, pdata)
      call sidl_opaque__array_get1_f(pdata, 0, a_string)
      call sidl_string__array_get1_f(a_string, 1, d_ctortest)
      call reporttest(d_ctortest .eq. 'ctor was run', test, .false.)

      call wrapper_User_accept_f(user, data, throwaway)

      call starttest(test)
      call sidl_string__array_get1_f(a_string, 0, d_string)
      call reporttest(d_string .eq. 'Hello World!', test, .false.)

      call starttest(test)
      call sidl_int__array_get1_f(a_int, 0, d_int)
      call reporttest(d_int .eq. 3, test, .false.)

      call wrapper_User_deleteRef_f(user, throwaway)

      call wrapper_Data_deleteRef_f(data, throwaway)

      call synch_RegOut_close_f(tracker, throwaway)
      call synch_RegOut_deleteRef_f(tracker,throwaway)
      end
