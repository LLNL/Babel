#
# File:          numbertest_Impl.py
# Symbol:        enums.numbertest-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class enums.numbertest in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# insert code here
# DO-NOT-DELETE splicer.end(_initial)

import enums.number
import enums.numbertest
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# insert code here
# DO-NOT-DELETE splicer.end(_before_type)

class numbertest(
# DO-NOT-DELETE splicer.begin(_inherits)
# insert code here (name of type(s) to inherit from)
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
      self.__IORself = enums.numbertest.numbertest(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
# insert code here
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def returnback(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # enums.number _return
    #

# DO-NOT-DELETE splicer.begin(returnback)
    return enums.number.notOne
# DO-NOT-DELETE splicer.end(returnback)

  def passin(self, n):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.number n
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passin)
    return n == enums.number.notZero
# DO-NOT-DELETE splicer.end(passin)

  def passout(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, n)
    # bool _return
    # enums.number n
    #

# DO-NOT-DELETE splicer.begin(passout)
    return (True, enums.number.negOne)
# DO-NOT-DELETE splicer.end(passout)

  def passinout(self, n):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.number n
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, n)
    # bool _return
    # enums.number n
    #

# DO-NOT-DELETE splicer.begin(passinout)
    if n == enums.number.zero:      return (True, enums.number.notZero)
    elif n == enums.number.one:     return (True, enums.number.notOne)
    elif n == enums.number.negOne:  return (True, enums.number.notNeg)
    elif n == enums.number.notZero: return (True, enums.number.zero)
    elif n == enums.number.notOne:  return (True, enums.number.one)
    elif n == enums.number.notNeg:  return (True, enums.number.negOne)
    else: return (False, enums.number.negOne)

# DO-NOT-DELETE splicer.end(passinout)

  def passeverywhere(self, n1, n3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.number n1
    # enums.number n3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, n2, n3)
    # enums.number _return
    # enums.number n2
    # enums.number n3
    #

# DO-NOT-DELETE splicer.begin(passeverywhere)
    if n1 == enums.number.notZero: n1 = enums.number.notOne
    else: n1 = False

    n2 = enums.number.negOne

    if n3 == enums.number.zero:      return (n1, n2, enums.number.notZero)
    elif n3 == enums.number.one:     return (n1, n2, enums.number.notOne)
    elif n3 == enums.number.negOne:  return (n1, n2, enums.number.notNeg)
    elif n3 == enums.number.notZero: return (n1, n2, enums.number.zero)
    elif n3 == enums.number.notOne:  return (n1, n2, enums.number.one)
    elif n3 == enums.number.notNeg:  return (n1, n2, enums.number.negOne)
    else: return (False, n2, n3)

# DO-NOT-DELETE splicer.end(passeverywhere)

# DO-NOT-DELETE splicer.begin(_final)
# insert code here
# DO-NOT-DELETE splicer.end(_final)
