#
# File:          IntegerContainer_Impl.py
# Symbol:        sorting.IntegerContainer-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class sorting.IntegerContainer in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""Integer container.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sorting.Comparator
import sorting.Container
import sorting.IntegerContainer
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import zeros, object
  PyObject = object
elif sidlPyArrays.type == "numeric":
  from Numeric import zeros, PyObject
import sorting.Integer
import random
from synch import RegOut
from types import NoneType

try:
  # available in 2.0 and later
  shuffle = random.shuffle
except AttributeError:
  def shuffle(x):
    for i in xrange(len(x)-1, 0, -1):
      j = random.randint(0, i)
      x[i], x[j] = x[j], x[i]
# DO-NOT-DELETE splicer.end(_before_type)

class IntegerContainer(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
Integer container.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = sorting.IntegerContainer.IntegerContainer(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    self.d_elements = None
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def setLength(self, len):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int len
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
This sets the container length and pseudo-randomly orders the
Integer elements contained.
"""
# DO-NOT-DELETE splicer.begin(setLength)
    # create a 1-d Numeric Python array with size objects
    self.d_elements = zeros((len,), PyObject)
    for i in xrange(len):
      self.d_elements[i] = sorting.Integer.Integer()
      self.d_elements[i].setValue(i+1)
    shuffle(self.d_elements)
# DO-NOT-DELETE splicer.end(setLength)

  def getLength(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

    """\
Return the number of elements in the container.
"""
# DO-NOT-DELETE splicer.begin(getLength)
    if not isinstance(self.d_elements,NoneType):
      return self.d_elements.shape[0]
    else:
      return 0
# DO-NOT-DELETE splicer.end(getLength)

  def compare(self, i, j, comp):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    # int j
    # sorting.Comparator comp
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

    """\
Return -1 if element i is less than element j, 0 if element i
is equal to element j, or otherwise 1.
"""
# DO-NOT-DELETE splicer.begin(compare)
    size = self.__IORself.getLength()
    result = 0
    if ((0 <= i < size) and (0 <= j < size)):
      i1 = sidl.BaseInterface.BaseInterface(self.d_elements[i])
      i2 = sidl.BaseInterface.BaseInterface(self.d_elements[j])
      result = comp.compare(i1, i2)
    return result
# DO-NOT-DELETE splicer.end(compare)

  def swap(self, i, j):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    # int j
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Swap elements i and j.
"""
# DO-NOT-DELETE splicer.begin(swap)
    size = self.__IORself.getLength()
    if ((0 <= i < size) and (0 <= j < size)):
      self.d_elements[i], self.d_elements[j] = \
           self.d_elements[j], self.d_elements[i]
# DO-NOT-DELETE splicer.end(swap)

  def output(self, s, e):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int s
    # int e
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Print elements s through e-1
"""
# DO-NOT-DELETE splicer.begin(output)
    size = self.__IORself.getLength()
    listcontents = "list"
    for i in xrange(size):
      listcontents = listcontents + " " + str(self.d_elements[i])
    RegOut.getInstance().writeComment(listcontents);
# DO-NOT-DELETE splicer.end(output)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
