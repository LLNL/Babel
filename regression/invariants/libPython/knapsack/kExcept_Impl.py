#
# File:          kExcept_Impl.py
# Symbol:        knapsack.kExcept-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7321M trunk)
# Description:   Implementation of sidl class knapsack.kExcept in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# Nothing to do here
# DO-NOT-DELETE splicer.end(_initial)

import knapsack.kExcept
import sidl.BaseClass
import sidl.BaseException
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.SIDLException
import sidl.io.Deserializer
import sidl.io.Serializable
import sidl.io.Serializer
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Nothing to do here
# DO-NOT-DELETE splicer.end(_before_type)

class kExcept(
# DO-NOT-DELETE splicer.begin(_inherits)
# Nothing to do here 
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
      self.__IORself = knapsack.kExcept.kExcept(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
# Nothing to do here
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

# DO-NOT-DELETE splicer.begin(_final)
# Nothing to do here
# DO-NOT-DELETE splicer.end(_final)
