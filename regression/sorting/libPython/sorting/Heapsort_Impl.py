#
# File:          Heapsort_Impl.py
# Symbol:        sorting.Heapsort-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.Heapsort in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Heap sort
"""

# DO-NOT-DELETE splicer.begin(_initial)
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sorting.Comparator
import sorting.Container
import sorting.Counter
import sorting.Heapsort
import sorting.SortingAlgorithm
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
def remakeHeap(elem, comp, cmp, swp, last, first):
  half = (last >> 1) - 1
  while (first <= half):
    child = first + first + 1
    if ((child + 1) < last):
      cmp.inc()
      if (elem.compare(child, child+1, comp) < 0): child = child +1
    cmp.inc()
    if (elem.compare(first, child, comp) >= 0): return
    swp.inc()
    elem.swap(first, child)
    first = child
# DO-NOT-DELETE splicer.end(_before_type)

class Heapsort(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Heap sort
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.Heapsort.Heapsort(impl = self)
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
Sort elements using Heap Sort.
"""
# DO-NOT-DELETE splicer.begin(sort)
    size = elems.getLength()
    cmp = self.__IORself.getCompareCounter()
    swp = self.__IORself.getSwapCounter()
    for i in xrange((size >> 1) - 1, -1, -1):
      remakeHeap(elems, comp, cmp, swp, size, i)
    i = size - 1
    while (i > 0):
      swp.inc()
      elems.swap(0, i)
      remakeHeap(elems, comp, cmp, swp, i, 0)
      i = i - 1
# DO-NOT-DELETE splicer.end(sort)

  def getName(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

    """\
Return heap sorting.
"""
# DO-NOT-DELETE splicer.begin(getName)
    return "Heap sort"
# DO-NOT-DELETE splicer.end(getName)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
