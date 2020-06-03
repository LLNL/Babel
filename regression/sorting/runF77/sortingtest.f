c
c     File:       sorting.f
c     Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision$
c     Date:       $Date$
c     Description:Exercise the FORTRAN interface for sorting
c
c
      subroutine starttest(number)
      implicit none
      integer*4 number
      integer*8 tracker, tae
      call synch_RegOut_getInstance_f(tracker, tae)
      call synch_RegOut_startPart_f(tracker, number, tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      end

      subroutine reporttest(test, number)
      implicit none
      integer*8 tracker, tae
      integer*4 number
      logical test
      include 'synch_ResultType.inc'
      call synch_RegOut_getInstance_f(tracker, tae)
      if (test) then
         call synch_RegOut_endPart_f(tracker, number, PASS,
     $        tae)
      else
         call synch_RegOut_endPart_f(tracker, number, FAIL,
     $        tae)
      endif
      call synch_RegOut_deleteRef_f(tracker, tae)
      number = number + 1
      end

      subroutine testsort(test)
      implicit none
      integer*8 algs, merge, quick, heap, alg, tae
      integer*4 test
      logical retval

      call sorting_SortingAlgorithm__array_create1d_f(3, algs)
      call starttest(test)
      call sorting_Mergesort__create_f(merge, tae)
      call reporttest(merge .ne. 0, test)
      call starttest(test)
      call sorting_Quicksort__create_f(quick, tae)
      call reporttest(quick .ne. 0, test)
      call starttest(test)
      call sorting_Heapsort__create_f(heap, tae)
      call reporttest(heap .ne. 0, test)

      call sorting_SortingAlgorithm__cast_f(merge, alg, tae)
      call sorting_SortingAlgorithm__array_set1_f(algs, 0, alg)
      call sorting_SortingAlgorithm_deleteRef_f(alg, tae)
      call sorting_SortingAlgorithm__cast_f(heap, alg, tae)
      call sorting_SortingAlgorithm__array_set1_f(algs, 1, alg)
      call sorting_SortingAlgorithm_deleteRef_f(alg,tae)
      call sorting_SortingAlgorithm__cast_f(quick, alg,tae)
      call sorting_SortingAlgorithm__array_set1_f(algs, 2, alg)
      call sorting_SortingAlgorithm_deleteRef_f(alg,tae)
c     remove extraneous references
      call sorting_Mergesort_deleteRef_f(merge, tae)
      call sorting_Quicksort_deleteRef_f(quick, tae)
      call sorting_Heapsort_deleteRef_f(heap, tae)

      call starttest(test)
      call sorting_SortTest_stressTest_f(algs, retval, tae)
      call reporttest(retval, test)
      call sorting_SortingAlgorithm__array_deleteRef_f(algs)

      end

      program sortingtest
      implicit none
      integer*4 test
      integer*8 tracker, tae
      test = 1
      call synch_RegOut_getInstance_f(tracker, tae)
      call synch_RegOut_setExpectations_f(tracker, 4, tae)
      call synch_RegOut_writeComment_f(tracker,
     $      'Sort tests', tae)
      call testsort(test)
      call synch_RegOut_close_f(tracker, tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      end
