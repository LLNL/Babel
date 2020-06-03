// 
// File:          sorting_SortTest_Impl.cxx
// Symbol:        sorting.SortTest-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.SortTest
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_SortTest_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sorting_SortingAlgorithm_hxx
#include "sorting_SortingAlgorithm.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(sorting.SortTest._includes)
#ifndef included_sorting_Counter_hh
#include "sorting_Counter.hxx"
#endif
#include <stdlib.h>
#include <stdio.h>
#include "sorting_CompInt.hxx"
#include "sorting_IntegerContainer.hxx"
#include "synch_RegOut.hxx"

static std::string
intToString(int i)
{
  char buf[64];
  sprintf(buf, "%d", i);
  return buf;
}

static const int32_t s_testSizes[] = {
  0,
  1,
  2,
  3,
  4,
  7,
  10,
  51,
  64,
  -1
};

static bool
notSorted(::sorting::Container  &cont,
          ::sorting::Comparator &comp)
{
  const int32_t length = cont.getLength();
  for(int32_t i = 1 ; i < length ; ++i ){
    if (cont.compare(i-1, i, comp) > 0) return true;
  }
  return false;
}

static void
sortAndReport(::sorting::SortingAlgorithm &alg,
	      ::sorting::Container        &cont,
	      ::sorting::Comparator       &comp, 
	      bool                   &result)
{
  ::sorting::Counter swpCnt;
  ::sorting::Counter cmpCnt;
  alg.reset();
  alg.sort(cont, comp);
  swpCnt = alg.getSwapCounter();
  cmpCnt = alg.getCompareCounter();
  std::string buf = std::string("compares (") + 
    intToString(cmpCnt.getCount()) + ") swaps (" +
    intToString(swpCnt.getCount()) + ")";
  ::synch::RegOut::getInstance().writeComment(buf);
  if (notSorted(cont, comp)) {
    ::synch::RegOut::getInstance().writeComment("sort failed!!");
    result = false;
  }
}
// DO-NOT-DELETE splicer.end(sorting.SortTest._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::SortTest_impl::SortTest_impl() : StubBase(reinterpret_cast< void*>(
  ::sorting::SortTest::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.SortTest._ctor2)
  // Insert-Code-Here {sorting.SortTest._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.SortTest._ctor2)
}

// user defined constructor
void sorting::SortTest_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.SortTest._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(sorting.SortTest._ctor)
}

// user defined destructor
void sorting::SortTest_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.SortTest._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(sorting.SortTest._dtor)
}

// static class initializer
void sorting::SortTest_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.SortTest._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.SortTest._load)
}

// user defined static methods:
/**
 * Perform the array stress test.
 * 
 * Return true if all the algorithms work okay.
 */
bool
sorting::SortTest_impl::stressTest_impl (
  /* in array<sorting.SortingAlgorithm> */::sidl::array< 
    ::sorting::SortingAlgorithm>& algs ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.SortTest.stressTest)
  bool result = false;
  if (algs._not_nil()) {
    const int32_t lower = algs.lower(0);
    const int32_t upper = algs.upper(0);
    int32_t i,j;
    result = true;
    for(i = lower; i <= upper; ++i) {
      ::sorting::SortingAlgorithm alg = algs.get(i);
      if (alg._not_nil()) {
        ::sorting::IntegerContainer data = ::sorting::IntegerContainer::_create();
	::sorting::CompInt intcomp = ::sorting::CompInt::_create();
        std::string name = alg.getName();
        j = 0;
	std::string buf = "****ALGORITHM IS " + name + "****";
	::synch::RegOut::getInstance().writeComment(buf);
        while (s_testSizes[j] >= 0) {
	  ::synch::RegOut tracker = ::synch::RegOut::getInstance();
	  std::string buf2 = "DATA SIZE " + intToString(s_testSizes[j]);
	  intcomp.setSortIncreasing(true);
	  tracker.writeComment(buf2);
	  data.setLength(s_testSizes[j]);
	  sortAndReport(alg, data, intcomp, result);
	  tracker.writeComment("pre-sorted list");
	  sortAndReport(alg, data, intcomp, result);
	  tracker.writeComment("reverse sorted list");
          intcomp.setSortIncreasing(false);
	  sortAndReport(alg, data, intcomp, result);
          ++j;
        }
      }
      else{
        result = false;
      }
    }
  }
  return result;
  // DO-NOT-DELETE splicer.end(sorting.SortTest.stressTest)
}


// user defined non-static methods: (none)

// DO-NOT-DELETE splicer.begin(sorting.SortTest._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.SortTest._misc)

