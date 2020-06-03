#
# File:          Quicksort_Impl.py
# Symbol:        sorting.Quicksort-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.Quicksort in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Quick sort
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
import sorting.Quicksort
import sorting.SortingAlgorithm
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
def choosePivot(elems, comp, cmp, start, end):
  pivot = (start + end) >> 1
  if ((end - start) > 4):
    mid = pivot
    cmp.inc()
    if (elems.compare(start, mid, comp) <= 0):
      cmp.inc()
      if (elems.compare(mid, end - 1, comp) > 0):
        cmp.inc()
        if (elems.compare(start, end - 1, comp) < 0):
          pivot = end - 1
        else:
          pivot = start
    else:
      cmp.inc()
      if (elems.compare(mid, end - 1, comp) < 0):
        cmp.inc()
        if (elems.compare(start, end - 1, comp) > 0):
          pivot = end - 1
        else:
          pivot = start
  return pivot

def quickSort(elems, comp, cmp, swp, start, end):
  if ((end - start) > 1):
    pivot = choosePivot(elems, comp, cmp, start, end)
    i = start
    j = end
    if (pivot != start):
      swp.inc()
      elems.swap(start, pivot)
    while 1:
      j = j - 1
      cmp.inc()
      while (elems.compare(start, j, comp) < 0):
        j = j - 1
        cmp.inc()
      i = i + 1
      while (i < j):
        cmp.inc()
        if (elems.compare(start, i, comp) < 0): break
        i = i + 1
      if (i >= j): break
      swp.inc()
      elems.swap(i,j)
    if (j != start):
      swp.inc()
      elems.swap(start, j)
    quickSort(elems, comp, cmp, swp, start, j)
    quickSort(elems, comp, cmp, swp, j+1, end)
      
# DO-NOT-DELETE splicer.end(_before_type)

class Quicksort(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Quick sort
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.Quicksort.Quicksort(impl = self)
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
Sort elements using Quick Sort.
"""
# DO-NOT-DELETE splicer.begin(sort)
    size = elems.getLength()
    cmp = self.__IORself.getCompareCounter()
    swp = self.__IORself.getSwapCounter()
    quickSort(elems, comp, cmp, swp, 0, size)
# DO-NOT-DELETE splicer.end(sort)

  def getName(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

    """\
Return quick sorting.
"""
# DO-NOT-DELETE splicer.begin(getName)
    return "Quick sort"
# DO-NOT-DELETE splicer.end(getName)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
