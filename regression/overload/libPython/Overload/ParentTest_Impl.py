#
# File:          ParentTest_Impl.py
# Symbol:        Overload.ParentTest-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class Overload.ParentTest in Python.
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

import Overload.ParentTest
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

class ParentTest(
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
      self.__IORself = Overload.ParentTest.ParentTest(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def getValue(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

# DO-NOT-DELETE splicer.begin(getValue)
    return 1;
# DO-NOT-DELETE splicer.end(getValue)

  def getValueInt(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

# DO-NOT-DELETE splicer.begin(getValueInt)
    return v;
# DO-NOT-DELETE splicer.end(getValueInt)

  def getValueBool(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(getValueBool)
    return v;
# DO-NOT-DELETE splicer.end(getValueBool)

  def getValueDouble(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(getValueDouble)
    return v;
# DO-NOT-DELETE splicer.end(getValueDouble)

  def getValueDcomplex(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # dcomplex v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # dcomplex _return
    #

# DO-NOT-DELETE splicer.begin(getValueDcomplex)
    return v;
# DO-NOT-DELETE splicer.end(getValueDcomplex)

  def getValueFloat(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # float _return
    #

# DO-NOT-DELETE splicer.begin(getValueFloat)
    return v;
# DO-NOT-DELETE splicer.end(getValueFloat)

  def getValueFcomplex(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # fcomplex v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # fcomplex _return
    #

# DO-NOT-DELETE splicer.begin(getValueFcomplex)
    return v;
# DO-NOT-DELETE splicer.end(getValueFcomplex)

  def getValueString(self, v):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string v
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

# DO-NOT-DELETE splicer.begin(getValueString)
    return v;
# DO-NOT-DELETE splicer.end(getValueString)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
