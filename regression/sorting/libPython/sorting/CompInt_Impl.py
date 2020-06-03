#
# File:          CompInt_Impl.py
# Symbol:        sorting.CompInt-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.CompInt in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Compare two Integer's.  By default, this will sort in increasing order.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sorting.CompInt
import sorting.Comparator
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import sorting.Integer
# DO-NOT-DELETE splicer.end(_before_type)

class CompInt(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Compare two Integer's.  By default, this will sort in increasing order.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.CompInt.CompInt(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    self.d_increasing = 1
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def setSortIncreasing(self, increasing):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # bool increasing
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
If increasing is true, this will cause the comparator to
report a normal definition of less than; otherwise, it will
reverse the normal ordering.
"""
# DO-NOT-DELETE splicer.begin(setSortIncreasing)
    self.d_increasing = increasing
# DO-NOT-DELETE splicer.end(setSortIncreasing)

  def compare(self, i1, i2):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # sidl.BaseInterface i1
    # sidl.BaseInterface i2
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

    """\
This method is used to define an ordering of objects.  This method
will return -1 if i1 < i2, 0 if i1 = i2; and 1 if i1 > i2.
"""
# DO-NOT-DELETE splicer.begin(compare)
    result = 0
    int1 = sorting.Integer.Integer(i1)
    int2 = sorting.Integer.Integer(i2)
    if (int1 and int2):
      if (int1.getValue() > int2.getValue()):
        result = 1
      if (int1. getValue() < int2.getValue()):
        result = -1
      if (not self.d_increasing):
        result = -result
    return result
# DO-NOT-DELETE splicer.end(compare)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
