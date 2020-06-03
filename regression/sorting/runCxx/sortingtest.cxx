/*
 * File:        sorting.cc
 * Copyright:   (c) 2002 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7467 $
 * Date:        $Date: 2012-05-03 12:43:22 -0700 (Thu, 03 May 2012) $
 * Description: Simple C++ driver for the sort regression test
 *
 */

#include <stdlib.h>
#include <time.h>
#ifdef _AIX
/* work around for #define realloc rpl_realloc on AIX */
#include <cstdlib>
#endif

#include "sorting_SortTest.hxx"
#include "sorting_Mergesort.hxx"
#include "sorting_Quicksort.hxx"
#include "sorting_Heapsort.hxx"
#include "sorting_SortingAlgorithm.hxx"
#include "synch.hxx"
#include <cstdio>

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif

/**
 * Fill the stack with random junk.
 */
int clearstack(int magicNumber) {
  int chunk[2048], i;
  for(i = 0; i < 2048; i++){
    chunk[i] = rand() + magicNumber;
  }
  for(i = 0; i < 16; i++){
    magicNumber += chunk[rand() & 2047];
  }
  return magicNumber;
}


#define MYASSERT( AAA ) \
  magicNumber = clearstack(magicNumber); \
  synch::RegOut::getInstance().startPart( ++part_no ); \
  synch::RegOut::getInstance().writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  synch::RegOut::getInstance().endPart(part_no, result);

int main(int argc, char **argv)
{
  synch::RegOut tracker = synch::RegOut::getInstance();
  int magicNumber = 1;
  synch::ResultType result = synch::ResultType_PASS;
  int part_no = 0;
  sorting::Mergesort merge = sorting::Mergesort::_create();
  sorting::Quicksort quick = sorting::Quicksort::_create();
  sorting::Heapsort heap = sorting::Heapsort::_create();
  sidl::array<sorting::SortingAlgorithm> algs = sidl::array<sorting::SortingAlgorithm>::create1d(3);
  srandom(time(NULL));
  tracker.setExpectations(4);
  MYASSERT(merge._not_nil());
  MYASSERT(quick._not_nil());
  MYASSERT(heap._not_nil());
  algs.set(0, merge);
  algs.set(1, quick);
  algs.set(2, heap);
  MYASSERT(sorting::SortTest::stressTest(algs));  
  tracker.close();  // require when using singleton
  return 0;
}
