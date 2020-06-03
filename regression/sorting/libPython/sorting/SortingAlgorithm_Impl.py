#
# File:          SortingAlgorithm_Impl.py
# Symbol:        sorting.SortingAlgorithm-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.SortingAlgorithm in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""An abstract sorting algorithm.
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
import sorting.SortingAlgorithm
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import sorting.SimpleCounter
# DO-NOT-DELETE splicer.end(_before_type)

class SortingAlgorithm(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
An abstract sorting algorithm.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.SortingAlgorithm.SortingAlgorithm(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    self.d_swp = sorting.SimpleCounter.SimpleCounter()
    self.d_cmp = sorting.SimpleCounter.SimpleCounter()
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def getCompareCounter(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # sorting.Counter _return
    #

    """\
Return the comparison counter.
"""
# DO-NOT-DELETE splicer.begin(getCompareCounter)
    return self.d_cmp
# DO-NOT-DELETE splicer.end(getCompareCounter)

  def getSwapCounter(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # sorting.Counter _return
    #

    """\
Return the swap counter.
"""
# DO-NOT-DELETE splicer.begin(getSwapCounter)
    return self.d_swp
# DO-NOT-DELETE splicer.end(getSwapCounter)

  def reset(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Reset the comparison and swap counter.
"""
# DO-NOT-DELETE splicer.begin(reset)
    self.d_swp.reset()
    self.d_cmp.reset()
# DO-NOT-DELETE splicer.end(reset)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
