!
! File:       sorting.f90
! Copyright:  (c) 2002 Lawrence Livermore National Security, LLC
! Revision:   @(#) $Revision$
! Date:       $Date$
! Description:Exercise the FORTRAN interface for sorting
!
!
#include "sorting_SortingAlgorithm_fAbbrev.h"
#include "sorting_Mergesort_fAbbrev.h"
#include "sorting_Quicksort_fAbbrev.h"
#include "sorting_Heapsort_fAbbrev.h"
#include "sorting_SortTest_fAbbrev.h"
#include "synch_RegOut_fAbbrev.h"
#include "synch_ResultType_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"

subroutine starttest(number)
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: number
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  tracker = getInstance(throwaway_exception)
  call startPart(tracker, number, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end subroutine starttest

subroutine reporttest(test, number)
  use sidl
  use synch_RegOut
  use synch_ResultType
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: number
  logical                        :: test
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  tracker = getInstance(throwaway_exception)
  if (test) then
     call endPart(tracker, number, PASS, throwaway_exception)
  else
     call endPart(tracker, number, FAIL, throwaway_exception)
  endif
  number = number + 1
  call deleteRef(tracker, throwaway_exception)
end subroutine reporttest

subroutine testsort(test)
  use sidl
  use sorting_SortingAlgorithm
  use sorting_Quicksort
  use sorting_Heapsort
  use sorting_Mergesort
  use sorting_SortTest
  use sidl_BaseInterface
  use sorting_SortingAlgorithm_array
  implicit none
  type(sorting_SortingAlgorithm_1d) :: algs
  type(sorting_SortingAlgorithm_t) :: alg
  type(sidl_BaseInterface_t) :: throwaway_exception
  type(sorting_Mergesort_t) :: merge
  type(sorting_Heapsort_t) :: heap
  type(sorting_Quicksort_t) :: quick
  integer (kind=sidl_int)  :: test
  logical                         :: retval

  call create1d(3_sidl_int, algs)
  call starttest(test)
  call new(merge, throwaway_exception)
  call reporttest(not_null(merge), test)
  call starttest(test)
  call new(quick, throwaway_exception)
  call reporttest(not_null(quick), test)
  call starttest(test)
  call new(heap, throwaway_exception)
  call reporttest(not_null(heap), test)

  call cast(merge, alg, throwaway_exception)
  call set(algs, 0_sidl_int, alg)
  call deleteRef(alg, throwaway_exception)
  call cast(heap, alg, throwaway_exception)
  call set(algs, 1_sidl_int, alg)
  call deleteRef(alg, throwaway_exception)
  call cast(quick, alg, throwaway_exception)
  call set(algs, 2_sidl_int, alg)
  call deleteRef(alg, throwaway_exception)
  ! remove extraneous references
  call deleteRef(merge, throwaway_exception)
  call deleteRef(quick, throwaway_exception)
  call deleteRef(heap, throwaway_exception)

  call starttest(test)
  retval = stressTest(algs, throwaway_exception)
  call reporttest(retval, test)
  call deleteRef(algs)
end subroutine testsort

program sortingtest
  use sidl
  use synch_RegOut
  use sidl_BaseInterface
  implicit none
  integer (kind=sidl_int) :: test
  type(synch_RegOut_t) :: tracker
  type(sidl_BaseInterface_t) :: throwaway_exception
  test = 1
  tracker = getInstance(throwaway_exception)
  call setExpectations(tracker, 4_sidl_int, throwaway_exception)
  call writeComment(tracker, 'Sort tests', throwaway_exception)
  call testsort(test)
  call close(tracker, throwaway_exception)
  call deleteRef(tracker, throwaway_exception)
end program sortingtest
