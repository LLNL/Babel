#
# File:          gKnapsack_Impl.py
# Symbol:        knapsack.gKnapsack-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7321M trunk)
# Description:   Implementation of sidl class knapsack.gKnapsack in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""gKnapsack:  A good implementation of the knapsack interface.
"""

# DO-NOT-DELETE splicer.begin(_initial)
import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import zeros, int32
elif sidlPyArrays.type == "numeric":
  import Numeric
  zeros = Numeric.zeros
  int32 = Numeric.Int32

from types import NoneType

# DO-NOT-DELETE splicer.end(_initial)

import knapsack.gKnapsack
import knapsack.iKnapsack
import knapsack.kBadWeightExcept
import knapsack.kExcept
import knapsack.kSizeExcept
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
MAX_WEIGHTS = 10
L_BAD_TYPE = "Encountered unexpected type(s) during processing."
L_MAX_WEIGHTS = "Cannot exceed maximum number of weights."
L_POS_WEIGHTS = "Non-positive weights are NOT supported."

def savespace(o):
  try:
      o.savespace(1)
  except AttributeError:
      pass

def typecode(o):
  try:
      return o.typecode()
  except AttributeError:
      return o.dtype.char


def onlyPositive(weights):
  '''
  Determine if all entries in the list are positive, returning True
  if they are or False if they are not.
  '''
  onlyPos = False
  if weights != None and isinstance(weights, list):
    if len(weights) > 0:
      onlyPos = True
      for w in weights:
        if w <= 0:
          onlyPos = False
  else:
    # WARNING:  This should NEVER happen.
    ex = knapsack.kExcept.kExcept()
    ex.setNote(L_BAD_TYPE)
    ex.add(__name__, 0, "onlyPositive")
  return onlyPos


def sameWeights(nW, sW, num):
  '''
  Checks to see if the two lists match where order does not matter.
  '''
  same = False
  goodNW = (nW != None and isinstance(nW, list))
  goodSW = (sW != None and isinstance(sW, ArrayType) and len(sW.shape) == 1)
  if goodNW and goodSW:
    if num == s.shape[0]:
      p = zeros((num))
      for s in sW:
        for j in range(num):
          if (s == nW[j] and p[j] == 0):
            p[j] = 1
            break
      same = onlyPos(p)
    else:
      # TODO/TLD/TBD:  What type of exception should be raised here?
      ex = knapsack.kSizeExcept.kSizeExcept()
      ex.setNote(L_MAX_WEIGHTS)
      ex.add(__name__, 0, "sameWeights")
  else:
    # WARNING:  This should NEVER happen.
    ex = knapsack.kExcept.kExcept()
    ex.setNote(L_BAD_TYPE)
    ex.add(__name__, 0, "sameWeights")
  return onlyPos


def solve(weights, t, i, n):
  '''
  Recursive implementation of the simplified knapsack problem.

  Based on the algorithm defined in "Data Structures and Algorithms"
  by Aho, Hopcroft, and Ullman (c) 1983.
  '''
  has = False
  if weights != None and isinstance(weights, list):
    if t == 0:
      has = True
    elif (t < 0) or (i >= n):
      has = False
    elif solve(weights, t-weights[i], i+1, n):
      has = True
    else:
      has = solve(weights, t, i+1, n)
  else:
    # WARNING:  This should NEVER happen.
    ex = knapsack.kExcept.kExcept()
    ex.setNote(L_BAD_TYPE)
    ex.add(__name__, 0, "knapsack")
  return has

# DO-NOT-DELETE splicer.end(_before_type)

class gKnapsack(
# DO-NOT-DELETE splicer.begin(_inherits)
# Nothing to do here
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
gKnapsack:  A good implementation of the knapsack interface.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = knapsack.gKnapsack.gKnapsack(impl = self)
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
          if w[i] > 0:
            self.__weights[i] = w[i]
          else:
            ex = knapsack.kBadWeightExcept.kBadWeightExcept()
            ex.setNote(L_POS_WEIGHTS)
            ex.add(__name__, 0, "knapsack.gKnapsack.initialize")
            raise knapsack.kBadWeightExcept._Exception, ex
      else:
        ex = knapsack.kSizeExcept.kSizeExcept()
        ex.setNote(L_MAX_WEIGHTS)
        ex.add(__name__, 0, "knapsack.gKnapsack.initialize")
    else:
      # WARNING:  This should NEVER happen.
      ex = knapsack.kExcept.kExcept()
      ex.setNote(L_BAD_TYPE)
      ex.add(__name__, 0, "knapsack.gKnapsack.initialize")
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
