#
# File:          Cstring_Impl.py
# Symbol:        Strings.Cstring-v1.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class Strings.Cstring in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Class to allow testing of string passing using every possible mode.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import Strings.Cstring
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import string
# DO-NOT-DELETE splicer.end(_before_type)

class Cstring(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Class to allow testing of string passing using every possible mode.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = Strings.Cstring.Cstring(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def returnback(self, nonNull):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool nonNull
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

    """\
If <code>nonNull</code> is <code>true</code>, this will
return \"Three\"; otherwise, it will return a NULL or empty string.
"""
# DO-NOT-DELETE splicer.begin(returnback)
    if (nonNull):
      return "Three"
    else:
      return None
# DO-NOT-DELETE splicer.end(returnback)

  def passin(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
This will return <code>true</code> iff <code>c</code> equals \"Three\".
"""
# DO-NOT-DELETE splicer.begin(passin)
    return c == "Three"
# DO-NOT-DELETE splicer.end(passin)

  def passout(self, nonNull):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool nonNull
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # string c
    #

    """\
If <code>nonNull</code> is <code>true</code>, this will return
\"Three\" in <code>c</code>; otherwise, it will return a null or
empty string. The return value is <code>false</code> iff 
the outgoing value of <code>c</code> is <code>null</code>.
"""
# DO-NOT-DELETE splicer.begin(passout)
    if (nonNull):
      return 1, "Three"
    else:
      return 0, None
# DO-NOT-DELETE splicer.end(passout)

  def passinout(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # string c
    #

# DO-NOT-DELETE splicer.begin(passinout)
    if (c and (len(c) > 0)):
      if ((c[0] >= "a") and (c[0] <= "z")):
        c = string.upper(c[0]) + c[1:]
      else:
        if ((c[0] >= "A") and (c[0] <= "Z")):
          c = string.lower(c[0]) + c[1:]
      c = c + "s"
      return 1, c
    else:
      return 0, c
# DO-NOT-DELETE splicer.end(passinout)

  def passeverywhere(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string c1
    # string c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # string _return
    # string c2
    # string c3
    #

# DO-NOT-DELETE splicer.begin(passeverywhere)
    result = None
    if (c3):
      if (len(c3) > 0):
        if ((c3[0] >= "a") and (c3[0] <= "z")):
          c3 = string.upper(c3[0]) + c3[1:]
        else:
          if ((c3[0] >= "A") and (c3[0] <= "Z")):
            c3 = string.lower(c3[0]) + c3[1:]
        c3 = c3[:-1]
      if (c1 == "Three"):
        result = "Three"
    return result, "Three", c3
# DO-NOT-DELETE splicer.end(passeverywhere)

  def mixedarguments(self, s1, c1, s2, c2):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string s1
    # char c1
    # string s2
    # char c2
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
 return true iff s1 == s2 and c1 == c2 
"""
# DO-NOT-DELETE splicer.begin(mixedarguments)
    return (s1 == s2) and (c1 == c2)
# DO-NOT-DELETE splicer.end(mixedarguments)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
