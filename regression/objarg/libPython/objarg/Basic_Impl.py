#
# File:          Basic_Impl.py
# Symbol:        objarg.Basic-v0.5
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class objarg.Basic in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
from types import NoneType
# DO-NOT-DELETE splicer.end(_initial)

import objarg.Basic
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Insert-Code-Here {_before_type} ()
# DO-NOT-DELETE splicer.end(_before_type)

class Basic(
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
      self.__IORself = objarg.Basic.Basic(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
# Insert-Code-Here {__init__} ()
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def passIn(self, o, inNotNull):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # sidl.BaseClass o
    # bool inNotNull
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
Return inNotNull == (o != NULL).
"""
# DO-NOT-DELETE splicer.begin(passIn)
    return (inNotNull and not isinstance(o,NoneType)) or not (inNotNull or not isinstance(o,NoneType))
# DO-NOT-DELETE splicer.end(passIn)

  def passInOut(self, o, inNotNull, outNotNull, retSame):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # sidl.BaseClass o
    # bool inNotNull
    # bool outNotNull
    # bool retSame
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, o)
    # bool _return
    # sidl.BaseClass o
    #

    """\
Return inNotNull == (o != NULL).  If outNotNull, the outgoing
value of o should not be NULL; otherwise, it will be NULL.
If outNotNull is true, there are two cases, it retSame is true
the incoming value of o will be returned; otherwise, a new
object will be allocated and returned.
"""
# DO-NOT-DELETE splicer.begin(passInOut)
    retval = (inNotNull and not isinstance(o,NoneType)) or not (inNotNull or not isinstance(o,NoneType))
    if outNotNull:
      if not retSame or isinstance(o, NoneType):
        o = sidl.BaseClass.BaseClass()
    else:
      o = None
    return (retval, o)
# DO-NOT-DELETE splicer.end(passInOut)

  def passOut(self, passOutNull):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool passOutNull
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # sidl.BaseClass o
    #

    """\
If passOutNull is true, a NULL value of o will be returned; otherwise,
a newly allocated object will be returned.
"""
# DO-NOT-DELETE splicer.begin(passOut)
    if (passOutNull):
      return None
    else:
      return sidl.BaseClass.BaseClass()
# DO-NOT-DELETE splicer.end(passOut)

  def retObject(self, retNull):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool retNull
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # sidl.BaseClass _return
    #

    """\
Return a NULL or non-NULL object depending on the value of retNull.
"""
# DO-NOT-DELETE splicer.begin(retObject)
    if (retNull):
      return None
    else:
      return sidl.BaseClass.BaseClass()
# DO-NOT-DELETE splicer.end(retObject)

# DO-NOT-DELETE splicer.begin(_final)
# Insert-Code-Here {_final} ()
# DO-NOT-DELETE splicer.end(_final)
