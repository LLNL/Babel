#
# File:          User_Impl.py
# Symbol:        wrapper.User-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class wrapper.User in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# Insert-Code-Here {_initial} ()
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import wrapper.Data
import wrapper.User
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Insert-Code-Here {_before_type} ()
# DO-NOT-DELETE splicer.end(_before_type)

class User(
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
      self.__IORself = wrapper.User.User(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Insert-Code-Here {__init__} ()
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def accept(self, data):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # wrapper.Data data
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

# DO-NOT-DELETE splicer.begin(accept)
    # Insert-Code-Here {accept} ()
    data.setString("Hello World!")
    data.setInt(3)
# DO-NOT-DELETE splicer.end(accept)

# DO-NOT-DELETE splicer.begin(_final)
# Insert-Code-Here {_final} ()
# DO-NOT-DELETE splicer.end(_final)
