#
# File:          Basic_Impl.py
# Symbol:        Args.Basic-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class Args.Basic in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# Insert-Code-Here {_initial} ()
# DO-NOT-DELETE splicer.end(_initial)

import Args.Basic
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import re
import string

import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import zeros, float32
elif sidlPyArrays.type == "numeric":
  import Numeric
  zeros = Numeric.zeros
  float32 = Numeric.Float32

def toFloat(d):
  tmp = zeros((1,), float32)
  try:
    tmp[0] = d
  except AttributeError:
    # don't die!
    tmp[0] = d
  return float(tmp[0])
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
      self.__IORself = Args.Basic.Basic(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
# Insert-Code-Here {__init__} ()
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def returnbackbool(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(returnbackbool)
    return 1
# DO-NOT-DELETE splicer.end(returnbackbool)

  def passinbool(self, b):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool b
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passinbool)
    return b
# DO-NOT-DELETE splicer.end(passinbool)

  def passoutbool(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, b)
    # bool _return
    # bool b
    #

# DO-NOT-DELETE splicer.begin(passoutbool)
    return (1, 1)
# DO-NOT-DELETE splicer.end(passoutbool)

  def passinoutbool(self, b):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool b
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, b)
    # bool _return
    # bool b
    #

# DO-NOT-DELETE splicer.begin(passinoutbool)
    return (1, not b)
# DO-NOT-DELETE splicer.end(passinoutbool)

  def passeverywherebool(self, b1, b3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool b1
    # bool b3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, b2, b3)
    # bool _return
    # bool b2
    # bool b3
    #

# DO-NOT-DELETE splicer.begin(passeverywherebool)
    return (b1, 1, not b3)
# DO-NOT-DELETE splicer.end(passeverywherebool)

  def returnbackchar(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # char _return
    #

# DO-NOT-DELETE splicer.begin(returnbackchar)
    return "3"
# DO-NOT-DELETE splicer.end(returnbackchar)

  def passinchar(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # char c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passinchar)
    return c == "3"
# DO-NOT-DELETE splicer.end(passinchar)

  def passoutchar(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # char c
    #

# DO-NOT-DELETE splicer.begin(passoutchar)
    return (1, "3")
# DO-NOT-DELETE splicer.end(passoutchar)

  def passinoutchar(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # char c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # char c
    #

# DO-NOT-DELETE splicer.begin(passinoutchar)
    if re.match("^[a-z]$", c):
      c = string.upper(c)
    else:
      if re.match("^[A-Z]$", c):
        c = string.lower(c)
      else:
        return (0, c)
    return (1, c)
# DO-NOT-DELETE splicer.end(passinoutchar)

  def passeverywherechar(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # char c1
    # char c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # char _return
    # char c2
    # char c3
    #

# DO-NOT-DELETE splicer.begin(passeverywherechar)
    if re.match("^[a-z]$", c3):
      c3 = string.upper(c3)
    else:
      if re.match("^[A-Z]$", c3):
        c3 = string.lower(c3)
    if (c1 == "3"):
      retval = "3"
    else:
      retval = 0
    return (retval, "3", c3)
# DO-NOT-DELETE splicer.end(passeverywherechar)

  def returnbackint(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

# DO-NOT-DELETE splicer.begin(returnbackint)
    return 3
# DO-NOT-DELETE splicer.end(returnbackint)

  def passinint(self, i):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passinint)
    return i == 3
# DO-NOT-DELETE splicer.end(passinint)

  def passoutint(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, i)
    # bool _return
    # int i
    #

# DO-NOT-DELETE splicer.begin(passoutint)
    return (1, 3)
# DO-NOT-DELETE splicer.end(passoutint)

  def passinoutint(self, i):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, i)
    # bool _return
    # int i
    #

# DO-NOT-DELETE splicer.begin(passinoutint)
    return (1, -i)
# DO-NOT-DELETE splicer.end(passinoutint)

  def passeverywhereint(self, i1, i3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i1
    # int i3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, i2, i3)
    # int _return
    # int i2
    # int i3
    #

# DO-NOT-DELETE splicer.begin(passeverywhereint)
    if (i1 == 3):
      retval = 3
    else:
      retval = 0
    return (retval, 3, -i3)
# DO-NOT-DELETE splicer.end(passeverywhereint)

  def returnbacklong(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # long _return
    #

# DO-NOT-DELETE splicer.begin(returnbacklong)
    return 3l
# DO-NOT-DELETE splicer.end(returnbacklong)

  def passinlong(self, l):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # long l
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passinlong)
    return l == 3
# DO-NOT-DELETE splicer.end(passinlong)

  def passoutlong(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, l)
    # bool _return
    # long l
    #

# DO-NOT-DELETE splicer.begin(passoutlong)
    return (1, 3l)
# DO-NOT-DELETE splicer.end(passoutlong)

  def passinoutlong(self, l):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # long l
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, l)
    # bool _return
    # long l
    #

# DO-NOT-DELETE splicer.begin(passinoutlong)
    return (1, -l)
# DO-NOT-DELETE splicer.end(passinoutlong)

  def passeverywherelong(self, l1, l3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # long l1
    # long l3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, l2, l3)
    # long _return
    # long l2
    # long l3
    #

# DO-NOT-DELETE splicer.begin(passeverywherelong)
    if (l1 == 3):
      retval = 3l
    else:
      retval = 0l
    return (retval, 3l, -l3)
# DO-NOT-DELETE splicer.end(passeverywherelong)

  def returnbackfloat(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # float _return
    #

# DO-NOT-DELETE splicer.begin(returnbackfloat)
    return 3.1
# DO-NOT-DELETE splicer.end(returnbackfloat)

  def passinfloat(self, f):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float f
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passinfloat)
    return f == toFloat(3.1)
# DO-NOT-DELETE splicer.end(passinfloat)

  def passoutfloat(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, f)
    # bool _return
    # float f
    #

# DO-NOT-DELETE splicer.begin(passoutfloat)
    return (1, 3.1)
# DO-NOT-DELETE splicer.end(passoutfloat)

  def passinoutfloat(self, f):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float f
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, f)
    # bool _return
    # float f
    #

# DO-NOT-DELETE splicer.begin(passinoutfloat)
    return (1, -f)
# DO-NOT-DELETE splicer.end(passinoutfloat)

  def passeverywherefloat(self, f1, f3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float f1
    # float f3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, f2, f3)
    # float _return
    # float f2
    # float f3
    #

# DO-NOT-DELETE splicer.begin(passeverywherefloat)
    if (f1 == toFloat(3.1)):
      retval = toFloat(3.1)
    else:
      retval = 0
    return (retval, 3.1, -f3)
# DO-NOT-DELETE splicer.end(passeverywherefloat)

  def returnbackdouble(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # double _return
    #

# DO-NOT-DELETE splicer.begin(returnbackdouble)
    return 3.14
# DO-NOT-DELETE splicer.end(returnbackdouble)

  def passindouble(self, d):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double d
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passindouble)
    return d == 3.14
# DO-NOT-DELETE splicer.end(passindouble)

  def passoutdouble(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, d)
    # bool _return
    # double d
    #

# DO-NOT-DELETE splicer.begin(passoutdouble)
    return (1, 3.14)
# DO-NOT-DELETE splicer.end(passoutdouble)

  def passinoutdouble(self, d):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double d
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, d)
    # bool _return
    # double d
    #

# DO-NOT-DELETE splicer.begin(passinoutdouble)
    return (1, -d)
# DO-NOT-DELETE splicer.end(passinoutdouble)

  def passeverywheredouble(self, d1, d3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # double d1
    # double d3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, d2, d3)
    # double _return
    # double d2
    # double d3
    #

# DO-NOT-DELETE splicer.begin(passeverywheredouble)
    if (d1 == 3.14):
      retval = 3.14
    else:
      retval = 0
    return (retval, 3.14, -d3)
# DO-NOT-DELETE splicer.end(passeverywheredouble)

  def returnbackfcomplex(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # fcomplex _return
    #

# DO-NOT-DELETE splicer.begin(returnbackfcomplex)
    return 3.1 + 3.1j
# DO-NOT-DELETE splicer.end(returnbackfcomplex)

  def passinfcomplex(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # fcomplex c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passinfcomplex)
    return c == (toFloat(3.1) + toFloat(3.1) * 1j)
# DO-NOT-DELETE splicer.end(passinfcomplex)

  def passoutfcomplex(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # fcomplex c
    #

# DO-NOT-DELETE splicer.begin(passoutfcomplex)
    return (1, 3.1 + 3.1j)
# DO-NOT-DELETE splicer.end(passoutfcomplex)

  def passinoutfcomplex(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # fcomplex c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # fcomplex c
    #

# DO-NOT-DELETE splicer.begin(passinoutfcomplex)
    c = c.real - 1j*c.imag
    return (1, c)
# DO-NOT-DELETE splicer.end(passinoutfcomplex)

  def passeverywherefcomplex(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # fcomplex c1
    # fcomplex c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # fcomplex _return
    # fcomplex c2
    # fcomplex c3
    #

# DO-NOT-DELETE splicer.begin(passeverywherefcomplex)
    c3 = c3.real - 1j*c3.imag
    if (c1 == (toFloat(3.1) + toFloat(3.1) * 1j)):
      return (c1, (3.1 + 3.1j), c3)
    else:
      return (0+0j, (3.1 + 3.1j), c3)
# DO-NOT-DELETE splicer.end(passeverywherefcomplex)

  def returnbackdcomplex(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # dcomplex _return
    #

# DO-NOT-DELETE splicer.begin(returnbackdcomplex)
    return 3.14 + 3.14j
# DO-NOT-DELETE splicer.end(returnbackdcomplex)

  def passindcomplex(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # dcomplex c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passindcomplex)
    return c == (3.14 + 3.14j)
# DO-NOT-DELETE splicer.end(passindcomplex)

  def passoutdcomplex(self):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # dcomplex c
    #

# DO-NOT-DELETE splicer.begin(passoutdcomplex)
    return (1, 3.14 + 3.14j)
# DO-NOT-DELETE splicer.end(passoutdcomplex)

  def passinoutdcomplex(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # dcomplex c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # dcomplex c
    #

# DO-NOT-DELETE splicer.begin(passinoutdcomplex)
    c = c.real - 1j*c.imag
    return (1, c)
# DO-NOT-DELETE splicer.end(passinoutdcomplex)

  def passeverywheredcomplex(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # dcomplex c1
    # dcomplex c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # dcomplex _return
    # dcomplex c2
    # dcomplex c3
    #

# DO-NOT-DELETE splicer.begin(passeverywheredcomplex)
    c3 = c3.real - 1j*c3.imag
    if (c1 == (3.14 + 3.14j)):
      return (c1, (3.14 + 3.14j), c3)
    else:
      return (0+0j, (3.14 + 3.14j), c3)
# DO-NOT-DELETE splicer.end(passeverywheredcomplex)

# DO-NOT-DELETE splicer.begin(_final)
# Insert-Code-Here {_final} ()
# DO-NOT-DELETE splicer.end(_final)
