#
# File:          Utils_Impl.py
# Symbol:        vect.Utils-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class vect.Utils in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# Nothing needed here.
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.PostViolation
import sidl.PreViolation
import sidl.RuntimeException
import vect.BadLevel
import vect.Utils
import vect.vDivByZeroExcept
import vect.vNegValExcept
import sidl.NotImplementedException

# DO-NOT-DELETE splicer.begin(_before_static)
import re
import math
import string

import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import zeros, float64, ndarray
  ArrayType = ndarray
elif sidlPyArrays.type == "numeric":
  import Numeric
  zeros = Numeric.zeros
  float64 = Numeric.Float64
  ArrayType = Numeric.ArrayType

#
# Some handy utilities from the arrays regression tests
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

def createDouble(len):
  result = None
  if (len >= 0):
    result = zeros((len, ), float64)
    savespace(result)
  return result

def createDouble2d(len):
  result = None
  if (len >= 0):
    result = zeros((len, len, ), float64)
    savespace(result)
  return result

# DO-NOT-DELETE splicer.end(_before_static)

def vuIsZero(u, tol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # double tol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
boolean result operations 
Return TRUE if the specified vector is the zero vector, within the
given tolerance level; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(vuIsZero)
  isZ = 0

  if (isinstance(u, ArrayType) and (len(u.shape) == 1)):
    isZ = 1
    for i in range(u.shape[0]):
      if (math.fabs(u[i]) > math.fabs(tol)):
        isZ = 0
        break

  return isZ
# DO-NOT-DELETE splicer.end(vuIsZero)

def vuIsUnit(u, tol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # double tol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return TRUE if the specified vector is the unit vector, within the
given tolerance level; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(vuIsUnit)
  isU = 0

  if ( isinstance(u, ArrayType) and (len(u.shape) == 1) ):
    if ( math.fabs(vuNorm(u, tol, 0) - 1.0) <= math.fabs(tol) ):
      isU = 1

  return isU
# DO-NOT-DELETE splicer.end(vuIsUnit)

def vuAreEqual(u, v, tol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # double tol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return TRUE if the specified vectors are equal, within the given
tolerance level; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(vuAreEqual)
  areEq = 0

  if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
    if ( (len(u.shape) == 1) and (len(v.shape) == 1) ):
      if (u.shape[0] == v.shape[0]):
        areEq = 1
        for i in range(u.shape[0]):
          if (math.fabs(u[i] - v[i]) > math.fabs(tol)):
            areEq = 0
            break

  return areEq
# DO-NOT-DELETE splicer.end(vuAreEqual)

def vuAreOrth(u, v, tol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # double tol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return TRUE if the specified vectors are orthogonal, within the given
tolerance; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(vuAreOrth)
  areOrth = 0

  if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
    absVal = math.fabs(vuDot(u, v, tol, 0))
    if (absVal <= math.fabs(tol)):
      areOrth = 1

  return areOrth
# DO-NOT-DELETE splicer.end(vuAreOrth)

def vuSchwarzHolds(u, v, tol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # double tol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return TRUE if the Schwarz (or Cauchy-Schwarz) inequality holds, within
the given tolerance; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(vuSchwarzHolds)
  sHolds = 0

  if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
    if ( (len(u.shape) == 1) and (len(v.shape) == 1) ):
      if (u.shape[0] == v.shape[0]):
        absDot   = math.fabs(vuDot(u, v, tol, 0))
        absNorms = math.fabs(vuNorm(u, tol, 0) * vuNorm(v, tol, 0))
        if (absDot <= absNorms + math.fabs(tol)):
          sHolds = 1

  return sHolds
# DO-NOT-DELETE splicer.end(vuSchwarzHolds)

def vuTriIneqHolds(u, v, tol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # double tol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return TRUE if the Minkowski (or triangle) inequality holds, within the
given tolerance; otherwise, return FALSE.
"""
# DO-NOT-DELETE splicer.begin(vuTriIneqHolds)
  tiHolds = 0

  if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
    if ( (len(u.shape) == 1) and (len(v.shape) == 1) ):
      if (u.shape[0] == v.shape[0]):
        absNormSum  = math.fabs(vuNorm(vuSum(u,v,0), tol, 0))
        absSumNorms = math.fabs(vuNorm(u, tol, 0) + vuNorm(v, tol, 0))
        if (absNormSum <= absSumNorms + math.fabs(tol)):
          tiHolds = 1

  return tiHolds
# DO-NOT-DELETE splicer.end(vuTriIneqHolds)

def vuNorm(u, tol, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # double tol
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # double _return
  #

  """\
double result operations 
Return the norm (or length) of the specified vector.

Note that the size exception is given here simply because the
implementation is leveraging the vuDot() method.  Also the tolerance
is included to enable the caller to specify the tolerance used in
contract checking.
"""
# DO-NOT-DELETE splicer.begin(vuNorm)
  res = 0.0

  if (badLevel == vect.BadLevel.NoVio):
    if (isinstance(u, ArrayType)):
      dot = vuDot(u, u, tol, 0)
      if (dot > 0.0):
        res = math.sqrt(dot)
      elif (dot < 0.0):
        res = -5.0
        exc =  vect.vNegValExcept.vNegValExcept()
        exc.setNote("vuNorm: vNegValExcept: Cannot sqrt() a negative value.")
        exc.add(__name__, 0, "vect.Utils.vuNorm")
        raise vect.vNegValExcept._Exception, exc
      else:
        res = 0.0
    else:
      res = 0.0
  elif (badLevel == vect.BadLevel.NegRes):
    res = -5.0
  elif (badLevel == vect.BadLevel.PosRes):
    res = 5.0
  elif (badLevel == vect.BadLevel.ZeroRes):
    res = 0.0
  else:
    res = -5.0
  
  return res
# DO-NOT-DELETE splicer.end(vuNorm)

def vuDot(u, v, tol, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # double tol
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # double _return
  #

  """\
Return the dot (, inner, or scalar) product of the specified vectors.
"""
# DO-NOT-DELETE splicer.begin(vuDot)
  dot = 0.0

  if (badLevel == vect.BadLevel.NoVio):
    if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
      if ( (len(u.shape) == 1) and (len(v.shape) == 1) ):
        if (u.shape[0] == v.shape[0]):
          for i in range(u.shape[0]):
            dot += u[i] * v[i]
        else:
          dot = 0.0
      else:
        dot = 0.0
    else:
      dot = 0.0
  elif (badLevel == vect.BadLevel.NegRes):
    dot = -5.0
  elif (badLevel == vect.BadLevel.PosRes):
    dot = 5.0
  elif (badLevel == vect.BadLevel.ZeroRes):
    dot = 0.0
  else:
    dot = -5.0
  
  return dot
# DO-NOT-DELETE splicer.end(vuDot)

def vuProduct(a, u, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # double a
  # array<> u
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<> _return
  #

  """\
vector result operations 
Return the (scalar) product of the specified vector.
"""
# DO-NOT-DELETE splicer.begin(vuProduct)
  prod = None

  if (isinstance(u, ArrayType)):
    lenU = u.shape[0]
  else:
    lenU = 0

  if (badLevel == vect.BadLevel.NoVio):
    if (isinstance(u, ArrayType)):
      if (len(u.shape) == 1):
        prod = createDouble(lenU)
        for i in range(lenU):
          prod[i] = a * u[i]
      else:
        prod = None
    else:
      prod = None
  elif (badLevel == vect.BadLevel.NullRes):
    prod = None
  elif (badLevel == vect.BadLevel.TwoDRes):
    prod = createDouble2d(lenU)
  elif (badLevel == vect.BadLevel.WrongSizeRes):
    prod = createDouble(lenU+5)
  else:
    prod = None
  
  return prod
# DO-NOT-DELETE splicer.end(vuProduct)

def vuNegate(u, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<> _return
  #

  """\
Return the negation of the specified vector.
"""
# DO-NOT-DELETE splicer.begin(vuNegate)
  negU = None

  if (isinstance(u, ArrayType)):
    lenU = u.shape[0]
  else:
    lenU = 0

  if (badLevel == vect.BadLevel.NoVio):
    if (isinstance(u, ArrayType)):
      if (len(u.shape) == 1):
        negU = vuProduct(-1.0, u, 0)
      else:
        negU = None
    else:
      negU = None
  elif (badLevel == vect.BadLevel.NullRes):
    negU = None
  elif (badLevel == vect.BadLevel.TwoDRes):
    negU = createDouble2d(lenU)
  elif (badLevel == vect.BadLevel.WrongSizeRes):
    negU = createDouble(lenU+5)
  else:
    negU = None
  
  return negU
# DO-NOT-DELETE splicer.end(vuNegate)

def vuNormalize(u, tol, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # double tol
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<> _return
  #

  """\
Return the normalizaton of the specified vector.

Note the tolerance is included because the implementation invokes 
vuDot().
"""
# DO-NOT-DELETE splicer.begin(vuNormalize)
  prod = None

  if (isinstance(u, ArrayType)):
    lenU = u.shape[0]
  else:
    lenU = 0

  if (badLevel == vect.BadLevel.NoVio):
    if (isinstance(u, ArrayType)):
      if (len(u.shape) == 1):
        val = vuNorm(u, tol, 0)
        if (val != 0.0):
          prod = vuProduct(1.0/val, u, 0)
        else:
          prod = None
          exc  =  vect.vDivByZeroExcept.vDivByZeroExcept()
          exc.setNote("vuNormalize: vDivByZeroExcept: Cannot divide by zero.")
          exc.add(__name__, 0, "vect.Utils.vuNormalize")
          raise vect.vDivByZeroExcept._Exception, exc
      else:
        prod = None
    else:
      prod = None
  elif (badLevel == vect.BadLevel.NullRes):
    prod = None
  elif (badLevel == vect.BadLevel.TwoDRes):
    prod = createDouble2d(lenU)
  elif (badLevel == vect.BadLevel.WrongSizeRes):
    prod = createDouble(lenU+5)
  else:
    prod = None
  
  return prod
# DO-NOT-DELETE splicer.end(vuNormalize)

def vuSum(u, v, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<> _return
  #

  """\
Return the sum of the specified vectors.
"""
# DO-NOT-DELETE splicer.begin(vuSum)
  aSum = None

  if (isinstance(u, ArrayType)):
    lenU = u.shape[0]
  else:
    lenU = 0

  if (badLevel == vect.BadLevel.NoVio):
    if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
      if ( (len(u.shape) == 1) and (len(v.shape) == 1) ):
        if (lenU == v.shape[0]):
          aSum = createDouble(lenU)
          for i in range(lenU):
            aSum[i] = u[i] + v[i]
        else:
          aSum = None
      else:
        aSum = None
    else:
      aSum = None
  elif (badLevel == vect.BadLevel.NullRes):
    aSum = None
  elif (badLevel == vect.BadLevel.TwoDRes):
    aSum = createDouble2d(lenU)
  elif (badLevel == vect.BadLevel.WrongSizeRes):
    aSum = createDouble(lenU+5)
  else:
    aSum = None
  
  return aSum
# DO-NOT-DELETE splicer.end(vuSum)

def vuDiff(u, v, badLevel):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> u
  # array<> v
  # vect.BadLevel badLevel
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<> _return
  #

  """\
Return the difference of the specified vectors.
"""
# DO-NOT-DELETE splicer.begin(vuDiff)
  diff = None

  if (isinstance(u, ArrayType)):
    lenU = u.shape[0]
  else:
    lenU = 0

  if (badLevel == vect.BadLevel.NoVio):
    if ( isinstance(u, ArrayType) and isinstance(v, ArrayType) ):
      if ( (len(u.shape) == 1) and (len(v.shape) == 1) ):
        if (lenU == v.shape[0]):
          diff = createDouble(lenU)
          for i in range(u.shape[0]):
            diff[i] = u[i] - v[i]
        else:
          diff = None
      else:
        diff = None
    else:
      diff = None
  elif (badLevel == vect.BadLevel.NullRes):
    diff = None
  elif (badLevel == vect.BadLevel.TwoDRes):
    diff = createDouble2d(lenU)
  elif (badLevel == vect.BadLevel.WrongSizeRes):
    diff = createDouble(lenU+5)
  else:
    diff = None
  
  return diff
# DO-NOT-DELETE splicer.end(vuDiff)


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Nothing needed here.
# DO-NOT-DELETE splicer.end(_before_type)

class Utils(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = vect.Utils.Utils(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
# Nothing needed here.
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

# DO-NOT-DELETE splicer.begin(_final)
# Nothing needed here.
# DO-NOT-DELETE splicer.end(_final)
