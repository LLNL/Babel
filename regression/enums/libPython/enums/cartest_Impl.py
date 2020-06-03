#
# File:          cartest_Impl.py
# Symbol:        enums.cartest-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class enums.cartest in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# insert code here
# DO-NOT-DELETE splicer.end(_initial)

import enums.car
import enums.cartest
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import array, object
  PyObject = object
elif sidlPyArrays.type == "numeric":
  from Numeric import array, PyObject
# DO-NOT-DELETE splicer.end(_before_type)

class cartest(
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
      self.__IORself = enums.cartest.cartest(impl = self)
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
    # enums.car _return
    #

# DO-NOT-DELETE splicer.begin(returnback)
    return enums.car.porsche
# DO-NOT-DELETE splicer.end(returnback)

  def passin(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.car c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passin)
    return c == enums.car.mercedes
# DO-NOT-DELETE splicer.end(passin)

  def passout(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # enums.car c
    #

# DO-NOT-DELETE splicer.begin(passout)
    return (True, enums.car.ford)
# DO-NOT-DELETE splicer.end(passout)

  def passinout(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.car c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # enums.car c
    #

# DO-NOT-DELETE splicer.begin(passinout)
    if c == enums.car.ford: return (True, enums.car.porsche)
    elif c == enums.car.porsche: return (True, enums.car.mercedes)
    elif c == enums.car.mercedes: return (True, enums.car.mercedes)
    else: return (False, c)
# DO-NOT-DELETE splicer.end(passinout)

  def passeverywhere(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.car c1
    # enums.car c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # enums.car _return
    # enums.car c2
    # enums.car c3
    #

# DO-NOT-DELETE splicer.begin(passeverywhere)
    if c1 == enums.car.mercedes: r = enums.car.porsche
    else: r = False

    c2 = enums.car.ford

    if   c3 == enums.car.ford:     return (r, c2, enums.car.porsche)
    elif c3 == enums.car.porsche:  return (r, c2, enums.car.mercedes)
    elif c3 == enums.car.mercedes: return (r, c2, enums.car.mercedes)
    else: return (False, c2, c3)

# DO-NOT-DELETE splicer.end(passeverywhere)

  def passarray(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # array<enums.car> c1
    # array<enums.car> c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # array<enums.car> _return
    # array<enums.car> c2
    # array<enums.car> c3
    #

    """\
All incoming/outgoing arrays should be [ ford, mercedes, porsche ]
in that order.
"""
# DO-NOT-DELETE splicer.begin(passarray)
    reference = [ enums.car.ford, enums.car.mercedes, enums.car.porsche ]

    if c1.tolist() == reference and c3.tolist() == reference:
      retval = array(reference)
    else:
      retval = array([])

    return (retval, array(reference), c3)
# DO-NOT-DELETE splicer.end(passarray)

# DO-NOT-DELETE splicer.begin(_final)
# insert code here
# DO-NOT-DELETE splicer.end(_final)
