#
# File:          IntOrderTest_Impl.py
# Symbol:        Ordering.IntOrderTest-v0.1
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class Ordering.IntOrderTest in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""This class provides methods to verify that the array ordering
capabilities work for arrays of int.
"""

# DO-NOT-DELETE splicer.begin(_initial)
import sidlPyArrays
if sidlPyArrays.type == "numpy":
  from numpy import zeros, int32
elif sidlPyArrays.type == "numeric":
  import Numeric
  zeros = Numeric.zeros
  int32 = Numeric.Int32
# DO-NOT-DELETE splicer.end(_initial)

import Ordering.IntOrderTest
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException

# DO-NOT-DELETE splicer.begin(_before_static)
def iFunc(ind):
  res = 0
  for i in range(len(ind)):
    res = res + (i+1) * ind[i]
  return res

def isIMatrix(A):
  if (len(A.shape) == 1):
    for i in range(A.shape[0]):
      if (A[i] != iFunc((i,))):
        return 0
  else:
    if (len(A.shape) == 2):
      for i in range(A.shape[0]):
        for j in range(A.shape[1]):
          if (A[i,j] != iFunc((i, j))):
              return 0
    else:
      if (len(A.shape) == 4):
        for i in range(A.shape[0]):
          for j in range(A.shape[1]):
            for k in range(A.shape[2]):
              for l in range(A.shape[3]):
                if (A[i,j,k,l] != iFunc((i, j, k, l))):
                    return 0
      else:
        return 0
  return 1
# DO-NOT-DELETE splicer.end(_before_static)

def makeColumnIMatrix(size, useCreateCol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int size
  # bool useCreateCol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2,column-major> _return
  #

  """\
Create a column-major matrix satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(makeColumnIMatrix)
  res = zeros((size, size), int32)
  for i in range(size):
    for j in range(size):
      res[i,j] = iFunc((i,j))
  return res
# DO-NOT-DELETE splicer.end(makeColumnIMatrix)

def makeRowIMatrix(size, useCreateRow):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int size
  # bool useCreateRow
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2,row-major> _return
  #

  """\
Create a row-major matrix satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(makeRowIMatrix)
  res = zeros((size, size), int32)
  for i in range(size):
    for j in range(size):
      res[i,j] = iFunc((i,j))
  return res
# DO-NOT-DELETE splicer.end(makeRowIMatrix)

def makeIMatrix(size, useCreateColumn):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int size
  # bool useCreateColumn
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,4> _return
  #

  """\
Create a 4-D matrix satisfying condition I.  Each dimension has
size elements numbers 0 through size-1.
"""
# DO-NOT-DELETE splicer.begin(makeIMatrix)
  res = zeros((size, size, size, size), int32)
  for i in range(size):
    for j in range(size):
      for k in range(size):
        for l in range(size):
          res[i,j,k,l] = iFunc((i,j,k,l))
  return res
# DO-NOT-DELETE splicer.end(makeIMatrix)

def createColumnIMatrix(size, useCreateCol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int size
  # bool useCreateCol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2,column-major> res
  #

  """\
Create a column-major matrix satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(createColumnIMatrix)
  if (useCreateCol):
    return makeColumnIMatrix(size, 1)
  else:
    return makeRowIMatrix(size, 1)
# DO-NOT-DELETE splicer.end(createColumnIMatrix)

def createRowIMatrix(size, useCreateRow):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int size
  # bool useCreateRow
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2,row-major> res
  #

  """\
Create a row-major matrix satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(createRowIMatrix)
  if (useCreateRow):
    return makeRowIMatrix(size, 1)
  else:
    return makeColumnIMatrix(size, 1)
# DO-NOT-DELETE splicer.end(createRowIMatrix)

def ensureColumn(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2,column-major> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2,column-major> a
  #

  """\
Make sure an array is column-major.  No changes to the dimension or
values in a are made.
"""
# DO-NOT-DELETE splicer.begin(ensureColumn)
  return a
# DO-NOT-DELETE splicer.end(ensureColumn)

def ensureRow(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2,row-major> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2,row-major> a
  #

  """\
Make sure an array is row-major.  No changes to the dimension or
values in a are made.
"""
# DO-NOT-DELETE splicer.begin(ensureRow)
  return a
# DO-NOT-DELETE splicer.end(ensureRow)

def isIMatrixOne(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isIMatrixOne)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isIMatrixOne)

def isColumnIMatrixOne(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,column-major> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming column-major array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isColumnIMatrixOne)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isColumnIMatrixOne)

def isRowIMatrixOne(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,row-major> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming row-major array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isRowIMatrixOne)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isRowIMatrixOne)

def isIMatrixTwo(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isIMatrixTwo)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isIMatrixTwo)

def isColumnIMatrixTwo(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2,column-major> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming column-major array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isColumnIMatrixTwo)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isColumnIMatrixTwo)

def isRowIMatrixTwo(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2,row-major> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming row-major array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isRowIMatrixTwo)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isRowIMatrixTwo)

def isIMatrixFour(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,4> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isIMatrixFour)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isIMatrixFour)

def isColumnIMatrixFour(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,4,column-major> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming column-major array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isColumnIMatrixFour)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isColumnIMatrixFour)

def isRowIMatrixFour(A):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,4,row-major> A
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation sees
an incoming row-major array satisfying condition I.
"""
# DO-NOT-DELETE splicer.begin(isRowIMatrixFour)
  return isIMatrix(A)                 
# DO-NOT-DELETE splicer.end(isRowIMatrixFour)

def isSliceWorking(useCreateCol):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # bool useCreateCol
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the implementation of slice
and smart copy is correct.
"""
# DO-NOT-DELETE splicer.begin(isSliceWorking)
  return 1
# DO-NOT-DELETE splicer.end(isSliceWorking)


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Put your code here...
# DO-NOT-DELETE splicer.end(_before_type)

class IntOrderTest(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
This class provides methods to verify that the array ordering
capabilities work for arrays of int.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = Ordering.IntOrderTest.IntOrderTest(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
