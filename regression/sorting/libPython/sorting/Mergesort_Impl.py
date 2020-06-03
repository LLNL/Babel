#
# File:          Mergesort_Impl.py
# Symbol:        sorting.Mergesort-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.Mergesort in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Merge sort
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sorting.Comparator
import sorting.Container
import sorting.Counter
import sorting.Mergesort
import sorting.SortingAlgorithm
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
def mergeLists(elems, comp, cmp, swp, start, mid, end):
  while ((start < mid) and (mid < end)):
    cmp.inc()
    if (elems.compare(start, mid, comp) > 0):
      for j in xrange(mid, start, -1):
        swp.inc()
        elems.swap(j, j - 1)
      mid = mid + 1
    start = start + 1

def mergeSort(elems, comp, cmp, swp, start, end):
  if ((end - start) > 1):
    mid = (start + end) >> 1
    mergeSort(elems, comp, cmp, swp, start, mid)
    mergeSort(elems, comp, cmp, swp, mid, end)
    mergeLists(elems, comp, cmp, swp, start, mid, end)
    
# DO-NOT-DELETE splicer.end(_before_type)

class Mergesort(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Merge sort
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.Mergesort.Mergesort(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def sort(self, elems, comp):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # sorting.Container elems
    # sorting.Comparator comp
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Sort elements using Merge Sort.
"""
# DO-NOT-DELETE splicer.begin(sort)
    size = elems.getLength()
    cmp = self.__IORself.getCompareCounter()
    swp = self.__IORself.getSwapCounter()
    mergeSort(elems, comp, cmp, swp, 0, size)
# DO-NOT-DELETE splicer.end(sort)

  def getName(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

    """\
Return merge sorting.
"""
# DO-NOT-DELETE splicer.begin(getName)
    return "Merge sort"
# DO-NOT-DELETE splicer.end(getName)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
