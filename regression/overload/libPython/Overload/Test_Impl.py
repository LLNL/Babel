#
# File:          Test_Impl.py
# Symbol:        Overload.Test-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class Overload.Test in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""This class is used as the work-horse, returning the value passed
in.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import Overload.AClass
import Overload.AnException
import Overload.BClass
import Overload.ParentTest
import Overload.Test
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Put your code here...
# DO-NOT-DELETE splicer.end(_before_type)

class Test(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
This class is used as the work-horse, returning the value passed
in.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = Overload.Test.Test(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def getValueIntDouble(self, a, b):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int a
    # double b
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueIntDouble)
    return a + b;
# DO-NOT-DELETE splicer.end(getValueIntDouble)

  def getValueDoubleInt(self, a, b):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double a
    # int b
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueDoubleInt)
    return a + b;
# DO-NOT-DELETE splicer.end(getValueDoubleInt)

  def getValueIntDoubleFloat(self, a, b, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int a
    # double b
    # float c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueIntDoubleFloat)
    return a + b + c;
# DO-NOT-DELETE splicer.end(getValueIntDoubleFloat)

  def getValueDoubleIntFloat(self, a, b, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double a
    # int b
    # float c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueDoubleIntFloat)
    return a + b + c;
# DO-NOT-DELETE splicer.end(getValueDoubleIntFloat)

  def getValueIntFloatDouble(self, a, b, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int a
    # float b
    # double c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueIntFloatDouble)
    return a + b + c;
# DO-NOT-DELETE splicer.end(getValueIntFloatDouble)

  def getValueDoubleFloatInt(self, a, b, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double a
    # float b
    # int c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueDoubleFloatInt)
    return a + b + c;
# DO-NOT-DELETE splicer.end(getValueDoubleFloatInt)

  def getValueFloatIntDouble(self, a, b, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float a
    # int b
    # double c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueFloatIntDouble)
    return a + b + c;
# DO-NOT-DELETE splicer.end(getValueFloatIntDouble)

  def getValueFloatDoubleInt(self, a, b, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float a
    # double b
    # int c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueFloatDoubleInt)
    return a + b + c;
# DO-NOT-DELETE splicer.end(getValueFloatDoubleInt)

  def getValueException(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # Overload.AnException v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

# DO-NOT-DELETE splicer.begin(getValueException)
    return v.getNote();
# DO-NOT-DELETE splicer.end(getValueException)

  def getValueAClass(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # Overload.AClass v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

# DO-NOT-DELETE splicer.begin(getValueAClass)
    return v.getValue();
# DO-NOT-DELETE splicer.end(getValueAClass)

  def getValueBClass(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # Overload.BClass v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

# DO-NOT-DELETE splicer.begin(getValueBClass)
    return v.getValue();
# DO-NOT-DELETE splicer.end(getValueBClass)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
