#
# File:          nwKnapsack_Impl.py
# Symbol:        knapsack.nwKnapsack-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7321M trunk)
# Description:   Implementation of sidl class knapsack.nwKnapsack in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""nwKnapsack:  An implementation of knapsack that drops about half 
of the input weights.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Nothing to do here
# DO-NOT-DELETE splicer.end(_initial)

import knapsack.iKnapsack
import knapsack.kBadWeightExcept
import knapsack.kExcept
import knapsack.kSizeExcept
import knapsack.nwKnapsack
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
def onlyPositiveSIDL(sW):
  '''
  Determine if all entries in the SIDL list are positive, returning True
  if they are or False if they are not.
  '''
  onlyPos = False
  if sW != None and isinstance(sW, ArrayType) and len(sW.shape) == 1:
    if len(sW) > 0:
      onlyPos = True
      for i in range(a.shape[0]):
        if w <= 0:
          onlyPos = False
  else:
    # WARNING:  This should NEVER happen.
    ex = knapsack.kExcept.kExcept()
    ex.setNote(L_BAD_TYPE)
    ex.add(__name__, 0, "onlyPositiveSIDL")
  return onlyPos


# DO-NOT-DELETE splicer.end(_before_type)

class nwKnapsack(
# DO-NOT-DELETE splicer.begin(_inherits)
# Nothing to do here
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
nwKnapsack:  An implementation of knapsack that drops about half 
of the input weights.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = knapsack.nwKnapsack.nwKnapsack(impl = self)
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
        if onlyPositiveSIDL(w):
          j = 0
          for i in range(a.shape[0]):
            if i%2:
              self.__weights[j++] = w[i]
        else:
          ex = knapsack.kBadWeightExcept.kBadWeightExcept()
          ex.setNote(L_POS_WEIGHTS)
          ex.add(__name__, 0, "knapsack.gKnapsack.initialize")
          raise knapsack.kBadWeightExcept._Exception, ex
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
