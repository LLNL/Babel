#
# File:          SortTest_Impl.py
# Symbol:        sorting.SortTest-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.SortTest in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Run a bunch of sorts through a stress test.
"""

# DO-NOT-DELETE splicer.begin(_initial)
import sys
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sorting.SortTest
import sorting.SortingAlgorithm
import sidl.NotImplementedException

# DO-NOT-DELETE splicer.begin(_before_static)
import sorting.Container
import sorting.CompInt
import sorting.Comparator
import sorting.IntegerContainer
from synch import RegOut

testSizes = [ 0, 1, 2, 3, 4, 7, 10, 51, 64 ]

def notSorted(cont, comp):
  size = cont.getLength()
  for i in xrange(1, size):
    if (cont.compare(i-1, i, comp) > 0):
      return 1
  return 0

def sortAndReport(alg, cont, comp, result):
  tracker = RegOut.getInstance()
  alg.reset()
  alg.sort(cont, comp)
  swpCnt = alg.getSwapCounter()
  cmpCnt = alg.getCompareCounter()
  tracker.writeComment("compares (" +  str(cmpCnt.getCount()) +
                 ") swaps (" + str(swpCnt.getCount()) + ")")
  if (notSorted(cont, comp)):
    
    tracker.writeComment("sort failed!!")
    result = 0
  return result
# DO-NOT-DELETE splicer.end(_before_static)

def stressTest(algs):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<sorting.SortingAlgorithm> algs
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Perform the array stress test.

Return true if all the algorithms work okay.
"""
# DO-NOT-DELETE splicer.begin(stressTest)
  result = 1
  tracker = RegOut.getInstance()
  for alg in algs:
    data = sorting.IntegerContainer.IntegerContainer()
    cont = sorting.Container.Container(data)
    intcomp = sorting.CompInt.CompInt()
    comp = sorting.Comparator.Comparator(intcomp)
    name = alg.getName()
    tracker.writeComment("****ALGORITHM IS " +  name + "****")
    for size in testSizes:
      intcomp.setSortIncreasing(1)
      tracker.writeComment("DATA SIZE " + str(size))
      data.setLength(size)
      result = sortAndReport(alg, cont, comp, result)
      tracker.writeComment("pre-sorted list")
      result = sortAndReport(alg, cont, comp, result)
      tracker.writeComment("reverse sorted list")
      intcomp.setSortIncreasing(0)
      result = sortAndReport(alg, cont, comp, result)
  sys.stdout.flush()
  return result
# DO-NOT-DELETE splicer.end(stressTest)


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Put your code here...
# DO-NOT-DELETE splicer.end(_before_type)

class SortTest(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Run a bunch of sorts through a stress test.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.SortTest.SortTest(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
