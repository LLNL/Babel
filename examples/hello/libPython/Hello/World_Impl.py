#
# File:          World_Impl.py
# Symbol:        Hello.World-v1.2
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7399M trunk)
# Description:   Implementation of sidl class Hello.World in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# DO-NOT-DELETE splicer.end(_initial)

import Hello.World
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# DO-NOT-DELETE splicer.end(_before_type)

class World(
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
      self.__IORself = Hello.World.World(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    pass
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def getMsg(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

# DO-NOT-DELETE splicer.begin(getMsg)
    return "Hello world!"
# DO-NOT-DELETE splicer.end(getMsg)

  def foo(self, i, io):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    # int io
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, o, io)
    # int _return
    # int o
    # int io
    #

# DO-NOT-DELETE splicer.begin(foo)
    return (0, 0, io)
# DO-NOT-DELETE splicer.end(foo)

# DO-NOT-DELETE splicer.begin(_final)
# DO-NOT-DELETE splicer.end(_final)
