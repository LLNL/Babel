#
# File:          npKnapsack_Impl.py
# Symbol:        knapsack.npKnapsack-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7321M trunk)
# Description:   Implementation of sidl class knapsack.npKnapsack in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""npKnapsack:  An implementation of knapsack that allows non-positive
weights.
"""

# DO-NOT-DELETE splicer.begin(_initial)
import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import zeros, int32
elif sidlPyArrays.type == "numeric":
  import Numeric
  zeros = Numeric.zeros
  int32 = Numeric.Int32

import knapsack.gKnapsack_impl

from types import NoneType

# DO-NOT-DELETE splicer.end(_initial)

import knapsack.iKnapsack
import knapsack.kBadWeightExcept
import knapsack.kExcept
import knapsack.kSizeExcept
import knapsack.npKnapsack
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.InvViolation
import sidl.PostViolation
import sidl.PreViolation
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Hopefully nothing to do here
# DO-NOT-DELETE splicer.end(_before_type)

class npKnapsack(
# DO-NOT-DELETE splicer.begin(_inherits)
# Nothing to do here
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
npKnapsack:  An implementation of knapsack that allows non-positive
weights.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = knapsack.npKnapsack.npKnapsack(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    self.__weights = []
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def initialize(self, w):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # array<int> w
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Initialize the knapsack with the specified weights, w.
"""
# DO-NOT-DELETE splicer.begin(initialize)
    if isinstance(w, ArrayType) and len(w.shape) == 1:
      if w.shape[0] <= MAX_WEIGHTS:
        for i in range(a.shape[0]):
          self.__weights[i] = w[i]
      else:
        ex = knapsack.kSizeExcept.kSizeExcept()
        ex.setNote(L_MAX_WEIGHTS)
        ex.add(__name__, 0, "knapsack.npKnapsack.initialize")
    else:
      # WARNING:  This should NEVER happen.
      ex = knapsack.kExcept.kExcept()
      ex.setNote(L_BAD_TYPE)
      ex.add(__name__, 0, "knapsack.npKnapsack.initialize")
    return
# DO-NOT-DELETE splicer.end(initialize)

  def onlyPosWeights(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
Return TRUE if all weights in the knapsack are positive;
otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(onlyPosWeights)
    return onlyPositive(self.__weights)
# DO-NOT-DELETE splicer.end(onlyPosWeights)

  def hasWeights(self, w):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # array<int> w
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
Return TRUE if all of the specified weights, w, are in the knapsack
or there are no specified weights; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(hasWeights)
    return sameWeights(self.__weights, w, len(self.__weights))
# DO-NOT-DELETE splicer.end(hasWeights)

  def hasSolution(self, t):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int t
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
Return TRUE if there is a solution for the specified target
weight; otherwise, return FALSE.  Recall a solution is a
subset of weights that total exactly to the specified target
weight.
"""
# DO-NOT-DELETE splicer.begin(hasSolution)
    return solve(self.__weights, t, 0, len(self.__weights))
# DO-NOT-DELETE splicer.end(hasSolution)

# DO-NOT-DELETE splicer.begin(_final)
# Nothing to do here
# DO-NOT-DELETE splicer.end(_final)
