#
# File:          colorwheel_Impl.py
# Symbol:        enums.colorwheel-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class enums.colorwheel in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# insert code here
# DO-NOT-DELETE splicer.end(_initial)

import enums.color
import enums.colorwheel
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

class colorwheel(
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
      self.__IORself = enums.colorwheel.colorwheel(impl = self)
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
    # enums.color _return
    #

# DO-NOT-DELETE splicer.begin(returnback)
    return enums.color.violet

# DO-NOT-DELETE splicer.end(returnback)

  def passin(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.color c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(passin)
    return ( c == enums.color.blue )
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
    # enums.color c
    #

# DO-NOT-DELETE splicer.begin(passout)
    return (True, enums.color.violet)
# DO-NOT-DELETE splicer.end(passout)

  def passinout(self, c):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.color c
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c)
    # bool _return
    # enums.color c
    #

# DO-NOT-DELETE splicer.begin(passinout)
    if c == enums.color.red:      return (True, enums.color.green ) 
    elif c == enums.color.orange: return (True, enums.color.blue  ) 
    elif c == enums.color.yellow: return (True, enums.color.violet) 
    elif c == enums.color.green:  return (True, enums.color.red   ) 
    elif c == enums.color.blue:   return (True, enums.color.orange) 
    elif c == enums.color.violet: return (True, enums.color.red   ) 
    else: return ( False, c )
# DO-NOT-DELETE splicer.end(passinout)

  def passeverywhere(self, c1, c3):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # enums.color c1
    # enums.color c3
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, c2, c3)
    # enums.color _return
    # enums.color c2
    # enums.color c3
    #

# DO-NOT-DELETE splicer.begin(passeverywhere)
    if c1 == enums.color.blue: _c1 = enums.color.violet
    else: _c1 = False;
    c2 = enums.color.violet

    if c3 == enums.color.red:      _c3 = enums.color.green  
    elif c3 == enums.color.orange: _c3 = enums.color.blue   
    elif c3 == enums.color.yellow: _c3 = enums.color.violet 
    elif c3 == enums.color.green:  _c3 = enums.color.red    
    elif c3 == enums.color.blue:   _c3 = enums.color.orange 
    elif c3 == enums.color.violet: _c3 = enums.color.red    
    else: return (False, c2, c3)
    return (_c1, c2, _c3) 
# DO-NOT-DELETE splicer.end(passeverywhere)

# DO-NOT-DELETE splicer.begin(_final)
# insert code here
# DO-NOT-DELETE splicer.end(_final)
