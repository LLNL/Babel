// 
// File:          sorting_IntegerContainer_Impl.cxx
// Symbol:        sorting.IntegerContainer-v0.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for sorting.IntegerContainer
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "sorting_IntegerContainer_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sorting_Comparator_hxx
#include "sorting_Comparator.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._includes)
#include <stdio.h>
#include "synch_RegOut.hxx"

static std::string
intToString(int i)
{
  char buf[64];
  sprintf(buf,"%d", i);
  return buf;
}
// DO-NOT-DELETE splicer.end(sorting.IntegerContainer._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
sorting::IntegerContainer_impl::IntegerContainer_impl() : StubBase(
  reinterpret_cast< void*>(::sorting::IntegerContainer::_wrapObj(
  reinterpret_cast< void*>(this))),false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._ctor2)
  // Insert-Code-Here {sorting.IntegerContainer._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._ctor2)
}

// user defined constructor
void sorting::IntegerContainer_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._ctor)
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._ctor)
}

// user defined destructor
void sorting::IntegerContainer_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._dtor)
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._dtor)
}

// static class initializer
void sorting::IntegerContainer_impl::_load() {
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * This sets the container length and pseudo-randomly orders the
 * Integer elements contained.
 */
void
sorting::IntegerContainer_impl::setLength_impl (
  /* in */int32_t len ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.setLength)
  int32_t i, j;
  d_elements = ::sidl::array< ::sorting::Integer>::create1d(len);
  for(i = 0; i < len; ++i){
    ::sorting::Integer intObj = ::sorting::Integer::_create();
    intObj.setValue(i);
    d_elements.set(i, intObj);
  }
  for(i = len - 1; i > 0; --i) {
    j = random() % (i + 1);
    if (j != i) {
      swap(i, j);
    }
  }
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.setLength)
}

/**
 * Return the number of elements in the container.
 */
int32_t
sorting::IntegerContainer_impl::getLength_impl () 

{
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.getLength)
  return 1 + d_elements.upper(0) - d_elements.lower(0);
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.getLength)
}

/**
 * Return -1 if element i is less than element j, 0 if element i
 * is equal to element j, or otherwise 1.
 */
int32_t
sorting::IntegerContainer_impl::compare_impl (
  /* in */int32_t i,
  /* in */int32_t j,
  /* in */::sorting::Comparator& comp ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.compare)
  ::sidl::BaseInterface i1(d_elements.get(i));
  ::sidl::BaseInterface i2(d_elements.get(j));
  return comp.compare(i1, i2);
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.compare)
}

/**
 * Swap elements i and j.
 */
void
sorting::IntegerContainer_impl::swap_impl (
  /* in */int32_t i,
  /* in */int32_t j ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.swap)
  const int32_t len = getLength();
  if (i >= 0 && j >= 0 && i < len && j < len) {
    ::sorting::Integer 
      i1(d_elements.get(i)),
      i2(d_elements.get(j));
    d_elements.set(i, i2);
    d_elements.set(j, i1);
  }
  else {
    std::string msg = 
      std::string("sorting::IntegerContainer::swap index out of bounds ") +
      intToString(i) + ", " + intToString(j) + ") len (" + 
      intToString(len) + ")\n";
    ::synch::RegOut::getInstance().writeComment(msg);
    ::synch::RegOut::getInstance().forceFailure();
  }
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.swap)
}

/**
 * Print elements s through e-1
 */
void
sorting::IntegerContainer_impl::output_impl (
  /* in */int32_t s,
  /* in */int32_t e ) 
{
  // DO-NOT-DELETE splicer.begin(sorting.IntegerContainer.output)
  std::string msg = "list";
  while (s < e) {
    msg += ' ';
    msg += intToString(d_elements.get(s++).getValue());
  }
  ::synch::RegOut::getInstance().writeComment(msg);
  // DO-NOT-DELETE splicer.end(sorting.IntegerContainer.output)
}


// DO-NOT-DELETE splicer.begin(sorting.IntegerContainer._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(sorting.IntegerContainer._misc)

