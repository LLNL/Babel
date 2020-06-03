#
# File:          ArrayOps_Impl.py
# Symbol:        ArrayTest.ArrayOps-v1.3
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class ArrayTest.ArrayOps in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
import sidlPyArrays
if sidlPyArrays.type == "numpy":
 import numpy
 zeros = numpy.zeros
 float32 = numpy.float32
 ones = numpy.ones
 int32 = numpy.int32
 int64 = numpy.int64
 float64 = numpy.float64
 float32 = numpy.float32
 complex64 = numpy.complex64
 complex128 = numpy.complex128
 ArrayType = numpy.ndarray
 objectArray = numpy.object
 strArray = numpy.str
elif sidlPyArrays.type == "numeric":
 import Numeric
 int32 = Numeric.Int32
 int64 = Numeric.Int32
 float32 = Numeric.Float32
 float64 = Numeric.Float64
 strArray = Numeric.Character
 complex64 = Numeric.Complex32
 complex128 = Numeric.Complex
 zeros = Numeric.zeros
 ones = Numeric.ones
 ArrayType = Numeric.ArrayType
 objectArray = Numeric.PyObject
from types import NoneType

stringData = ( "I'd", "rather", "write", "programs", "to", "write",
               "programs", "than", "write", "programs." )
stringDataLen = len(stringData)
              

charData = "I'd rather write programs to write programs than write programs."
charDataLen = len(charData)

def savespace(o):
  try:
      o.savespace(1)
  except AttributeError:
      pass

def typecode(o):
  try:
      return o.typecode()
  except AttributeError:
      return o.dtype.char

def powTwo(i):
    result = 1.0
    if (i >= 0):
      while (i):
        result = result *2
        i = i - 1
    else:
      while (i):
        result = result * 0.5
        i = i + 1
    return result

def powTwoF(i):
  result = zeros((1,), float32)
  savespace(result)
  result[0] = powTwo(i)
  return result[0]

def isEven(num):
  return (num & 1) == 0

def isPrime(num):
  i = 3l
  while (i*i <= num):
    if ((num % i) == 0):
      return 0
    i = i + 1
  return 1

def nextPrime(prev):
  if (prev <= 1l):
    return 2l
  else:
    if (prev == 2l):
      return 3l
    else:
      prev = prev + 2l
      while (not isPrime(prev)):
        prev = prev + 2l
  return prev

def reverseArray(array, newArray):
  if (newArray):
    copy = zeros(array.shape, typecode(array))
    savespace(copy)
    if (typecode(array) == 'c'):
        for i in range(array.shape[0]):
            copy[-(i+1)] = str(array[i])
    else:
        for i in range(array.shape[0]):
            copy[-(i+1)] = array[i]
  else:
    if (typecode(array) == 'c'):
        for i in range(array.shape[0] >> 1):
            array[-(i+1)], array[i] =  str(array[i]), str(array[-(i+1)])
    else:
        for i in range(array.shape[0] >> 1):
            array[-(i+1)], array[i] =  array[i], array[-(i+1)]
    copy = array
  return 1, copy

def arrayValue(l):
  result = 1
  add = 1
  for i in l:
    result = result * (i + add)
    add = add + 1
  return result

#Gives the approprite numeric array typecode for the given sidl typecode.
def sidlTypeToNumType(tp):

    if (tp == 1 or tp == 7):
        return int32
    elif (tp == 8):
        return int64
    elif (tp > 8):
        return objectArray
    elif (tp == 2):
        return strArray
    elif (tp == 3):
        return complex128
    elif (tp == 4):
        return float64
    elif (tp == 5):
        return complex64
    elif (tp == 6):
        return float32
    else:
        return objectArray

# this function makes a guess at the sidl type we're talking about from the
# numeric area type code.  It will be wrong for bools, longs,
# opaques, and strings.
def numTypeToSidlType(tp):
    if (tp == 'i'):
        return 7
    elif (tp == 'O'):
        return 11
    elif (tp == 'c'):
        return 2
    elif (tp == 'D'):
        return 3
    elif (tp == 'd'):
        return 4
    elif (tp == 'F'):
        return 5
    elif (tp == 'f'):
        return 6
    elif (tp == 'S'):
        return 2
    elif (tp == 'q'):
        return 8
    elif (tp == 'l'):
        return 7
    else:
        return 11
    

def createArrayByType(tp,ashape):
    result = ones(ashape, sidlTypeToNumType(tp))
    savespace(result)
    return result

def locMatrixMultiply(a, b, res):
    if(a.shape[0] == res.shape[0] and
       a.shape[1] == b.shape[0] and
       b.shape[1] == res.shape[1]):
        for i in range(a.shape[0]):
            for k in range(b.shape[1]):
                temp = 0;
                for j in range(a.shape[1]):
                    temp += (a[i,j] * b[j,k])
                res[i,k]=temp;
    return                
      
# DO-NOT-DELETE splicer.end(_initial)

import ArrayTest.ArrayOps
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException

# DO-NOT-DELETE splicer.begin(_before_static)
# Put your code here...
# DO-NOT-DELETE splicer.end(_before_static)

def checkBool(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<bool> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

  """\
Return <code>true</code> iff the even elements are true and
the odd elements are false.
"""
# DO-NOT-DELETE splicer.begin(checkBool)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (a[i] != isEven(i)):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkBool)

def checkChar(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<char> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkChar)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (a[i] != charData[i % charDataLen]):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkChar)

def checkInt(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkInt)
  if (isinstance(a,ArrayType) and (len(a.shape) == 1)):
    prime = nextPrime(0)
    for i in range(a.shape[0]):
      if (a[i] != prime):
        return 0
      prime = nextPrime(prime)
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkInt)

def checkLong(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<long> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkLong)
  if (isinstance(a,ArrayType) and (len(a.shape) == 1)):
    prime = nextPrime(0)
    for i in range(a.shape[0]):
      if (a[i] != prime):
        return 0
      prime = nextPrime(prime)
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkLong)

def checkString(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<string> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkString)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (a[i] != stringData[i % stringDataLen]):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkString)

def checkDouble(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<double> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkDouble)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (a[i] != powTwo(-i)):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkDouble)

def checkFloat(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<float> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkFloat)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (a[i] != powTwoF(-i)):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkFloat)

def checkFcomplex(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<fcomplex> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkFcomplex)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (not ((a[i].real == powTwoF(i)) and (a[i].imag == powTwoF(-i)))):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkFcomplex)

def checkDcomplex(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<dcomplex> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkDcomplex)
  if (isinstance(a,ArrayType) and len(a.shape) == 1):
    for i in range(a.shape[0]):
      if (not ((a[i].real == powTwo(i)) and (a[i].imag == powTwo(-i)))):
        return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(checkDcomplex)

def check2Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check2Int)
  if (isinstance(a,ArrayType) and len(a.shape) == 2):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        if (a[i,j] != powTwo(abs(i-j))):
          return 0
    return 1
  return 0
# DO-NOT-DELETE splicer.end(check2Int)

def check2Double(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<double,2> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check2Double)
  if (isinstance(a,ArrayType) and (len(a.shape) == 2)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        if (a[i,j] != powTwo(i-j)):
            return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check2Double)

def check2Float(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<float,2> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check2Float)
  if (isinstance(a,ArrayType) and (len(a.shape) == 2)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        if (a[i,j] != powTwoF(i-j)):
            return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check2Float)

def check2Fcomplex(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<fcomplex,2> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check2Fcomplex)
  if (isinstance(a,ArrayType) and (len(a.shape) == 2)):
      for i in range(a.shape[0]):
          for j in range(a.shape[1]):
              if ((a[i,j].real != powTwoF(i)) or
                  (a[i,j].imag != powTwoF(-j))):
                return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check2Fcomplex)

def check2Dcomplex(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<dcomplex,2> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check2Dcomplex)
  if (isinstance(a,ArrayType) and (len(a.shape) == 2)):
      for i in range(a.shape[0]):
          for j in range(a.shape[1]):
              if ((a[i,j].real != powTwo(i)) or
                  (a[i,j].imag != powTwo(-j))):
                return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check2Dcomplex)

def check3Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,3> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check3Int)
  if (isinstance(a,ArrayType) and (len(a.shape) == 3)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        for k in range(a.shape[2]):
          if (a[i,j,k] != arrayValue((i,j,k))):
            return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check3Int)

def check4Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,4> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check4Int)
  if (isinstance(a,ArrayType) and (len(a.shape) == 4)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        for k in range(a.shape[2]):
          for l in range(a.shape[3]):
            if (a[i,j,k,l] != arrayValue((i,j,k,l))):
              return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check4Int)

def check5Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,5> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check5Int)
  if (isinstance(a,ArrayType) and (len(a.shape) == 5)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        for k in range(a.shape[2]):
          for l in range(a.shape[3]):
            for m in range(a.shape[4]):
              if (a[i,j,k,l,m] != arrayValue((i,j,k,l,m))):
                return 0
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check5Int)

def check6Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,6> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check6Int)
  if (isinstance(a,ArrayType) and (len(a.shape) == 6)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        for k in range(a.shape[2]):
          for l in range(a.shape[3]):
            for m in range(a.shape[4]):
              for n in range(a.shape[5]):  
                if (a[i,j,k,l,m,n] != arrayValue((i,j,k,l,m,n))):
                  return 0           
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check6Int)

def check7Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,7> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check7Int)
  if (isinstance(a,ArrayType) and (len(a.shape) == 7)):
    for i in range(a.shape[0]):
      for j in range(a.shape[1]):
        for k in range(a.shape[2]):
          for l in range(a.shape[3]):
            for m in range(a.shape[4]):
              for n in range(a.shape[5]):
                for o in range(a.shape[5]):
                  if (a[i,j,k,l,m,n,o] != arrayValue((i,j,k,l,m,n,o))):
                    return 0  
  else:
    return 0
  return 1
# DO-NOT-DELETE splicer.end(check7Int)

def check2String(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<string,2> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(check2String)
  c = 0
  if (isinstance(a,ArrayType) and len(a.shape) == 2):
      for i in range(a.shape[0]):
          for j in range(a.shape[1]):
              if (a[i,j] != stringData[c % stringDataLen]):
                  return 0
              ++c
      return 1
  return 0
# DO-NOT-DELETE splicer.end(check2String)

def checkObject(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<ArrayTest.ArrayOps> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # int _return
  #

# DO-NOT-DELETE splicer.begin(checkObject)
  count = 0
  if (isinstance(a,ArrayType) and (len(a.shape) == 1)):
    for i in range(a.shape[0]):
      obj = a[i]
      if (obj and ArrayTest.ArrayOps.ArrayOps(obj)):
        count = count + 1
  return count
# DO-NOT-DELETE splicer.end(checkObject)

def reverseBool(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<bool> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<bool> a
  #

# DO-NOT-DELETE splicer.begin(reverseBool)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseBool)

def reverseChar(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<char> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<char> a
  #

# DO-NOT-DELETE splicer.begin(reverseChar)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseChar)

def reverseInt(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<int> a
  #

# DO-NOT-DELETE splicer.begin(reverseInt)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseInt)

def reverseLong(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<long> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<long> a
  #

# DO-NOT-DELETE splicer.begin(reverseLong)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseLong)

def reverseString(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<string> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<string> a
  #

# DO-NOT-DELETE splicer.begin(reverseString)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseString)

def reverseDouble(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<double> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<double> a
  #

# DO-NOT-DELETE splicer.begin(reverseDouble)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseDouble)

def reverseFloat(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<float> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<float> a
  #

# DO-NOT-DELETE splicer.begin(reverseFloat)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseFloat)

def reverseFcomplex(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<fcomplex> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<fcomplex> a
  #

# DO-NOT-DELETE splicer.begin(reverseFcomplex)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseFcomplex)

def reverseDcomplex(a, newArray):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<dcomplex> a
  # bool newArray
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, a)
  # bool _return
  # array<dcomplex> a
  #

# DO-NOT-DELETE splicer.begin(reverseDcomplex)
  return reverseArray(a, newArray)
# DO-NOT-DELETE splicer.end(reverseDcomplex)

def createBool(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<bool> _return
  #

# DO-NOT-DELETE splicer.begin(createBool)
  result = None
  if (len >= 0):
      result = zeros((len, ), int32)
      savespace(result)
      for i in range(len):
          result[i] = isEven(i)
  return result
# DO-NOT-DELETE splicer.end(createBool)

def createChar(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<char> _return
  #

# DO-NOT-DELETE splicer.begin(createChar)
  result = None
  if (len >= 0):
      result = zeros((len, ), 'c')
      savespace(result)
      for i in range(len):
          result[i] = charData[i % charDataLen]
  return result
# DO-NOT-DELETE splicer.end(createChar)

def createInt(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int> _return
  #

# DO-NOT-DELETE splicer.begin(createInt)
  result = None
  if (len >= 0):
      result = zeros((len, ), int32)
      savespace(result)
      prime = 0
      for i in range(len):
          prime = nextPrime(prime)
          result[i] = prime
  return result
# DO-NOT-DELETE splicer.end(createInt)

def createLong(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<long> _return
  #

# DO-NOT-DELETE splicer.begin(createLong)
  result = None
  if (len >= 0):
      result = zeros((len, ), int32)
      savespace(result)
      prime = 0
      for i in range(len):
          prime = nextPrime(prime)
          result[i] = prime
  return result
# DO-NOT-DELETE splicer.end(createLong)

def createString(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<string> _return
  #

# DO-NOT-DELETE splicer.begin(createString)
  result = None
  if (len >= 0):
      result = zeros((len, ), objectArray)
      savespace(result)
      for i in range(len):
          result[i] = stringData[i % stringDataLen]
  return result
# DO-NOT-DELETE splicer.end(createString)

def createDouble(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<double> _return
  #

# DO-NOT-DELETE splicer.begin(createDouble)
  result = None
  if (len >= 0):
      result = zeros((len, ), float64)
      savespace(result)
      for i in range(len):
          result[i] = powTwo(-i)
  return result
# DO-NOT-DELETE splicer.end(createDouble)

def createFloat(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<float> _return
  #

# DO-NOT-DELETE splicer.begin(createFloat)
  result = None
  if (len >= 0):
      result = zeros((len, ), float32)
      savespace(result)
      for i in range(len):
          result[i] = powTwo(-i)
  return result
# DO-NOT-DELETE splicer.end(createFloat)

def createFcomplex(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<fcomplex> _return
  #

# DO-NOT-DELETE splicer.begin(createFcomplex)
  result = None
  if (len >= 0):
      result = zeros((len,), complex64)
      savespace(result)
      for i in range(result.shape[0]):
          result[i] = powTwo(i) + powTwo(-i)*1j
  return result
# DO-NOT-DELETE splicer.end(createFcomplex)

def createDcomplex(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<dcomplex> _return
  #

# DO-NOT-DELETE splicer.begin(createDcomplex)
  result = None
  if (len >= 0):
      result = zeros((len,), complex128)
      savespace(result)
      for i in range(result.shape[0]):
          result[i] = powTwo(i) + powTwo(-i)*1j
  return result
# DO-NOT-DELETE splicer.end(createDcomplex)

def createObject(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<ArrayTest.ArrayOps> _return
  #

# DO-NOT-DELETE splicer.begin(createObject)
  result = None
  if (len >= 0):
      result = zeros((len, ), objectArray)
      savespace(result)
      for i in range(len):
          result[i] = ArrayTest.ArrayOps.ArrayOps()
  return result
# DO-NOT-DELETE splicer.end(createObject)

def create2Int(d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2> _return
  #

# DO-NOT-DELETE splicer.begin(create2Int)
  result = None
  if (d1 >= 0 and d2 >= 0):
      result = zeros((d1, d2, ), int32)
      savespace(result)
      for i in range(d1):
          for j in range(d2):
              result[i,j] = powTwo(abs(i-j))
  return result
# DO-NOT-DELETE splicer.end(create2Int)

def create2Double(d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<double,2> _return
  #

# DO-NOT-DELETE splicer.begin(create2Double)
  result = None
  if (d1 >= 0 and d2 >= 0):
      result = zeros((d1, d2, ), float64)
      savespace(result)
      for i in range(d1):
          for j in range(d2):
              result[i,j] = powTwo(i-j)
  return result
# DO-NOT-DELETE splicer.end(create2Double)

def create2Float(d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<float,2> _return
  #

# DO-NOT-DELETE splicer.begin(create2Float)
  result = None
  if (d1 >= 0 and d2 >= 0):
      result = zeros((d1, d2, ), float32)
      savespace(result)
      for i in range(d1):
          for j in range(d2):
              result[i,j] = powTwoF(i-j)
  return result
# DO-NOT-DELETE splicer.end(create2Float)

def create2Dcomplex(d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<dcomplex,2> _return
  #

# DO-NOT-DELETE splicer.begin(create2Dcomplex)
  result = None
  if (d1 >= 0 and d2 >= 0):
      result = zeros((d1, d2, ), complex128)
      savespace(result)
      for i in range(d1):
          for j in range(d2):
              result[i,j] = complex(powTwo(i), powTwo(-j))
  return result
# DO-NOT-DELETE splicer.end(create2Dcomplex)

def create2Fcomplex(d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<fcomplex,2> _return
  #

# DO-NOT-DELETE splicer.begin(create2Fcomplex)
  result = None
  if (d1 >= 0 and d2 >= 0):
      result = zeros((d1, d2, ), complex64)
      savespace(result)
      for i in range(d1):
          for j in range(d2):
              result[i,j] = complex(powTwoF(i), powTwoF(-j))
  return result
# DO-NOT-DELETE splicer.end(create2Fcomplex)

def create2String(d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<string,2> _return
  #

# DO-NOT-DELETE splicer.begin(create2String)
  result = None
  c = 0
  if (d1 >= 0 and d2 >= 0):
      result = zeros((d1,d2, ), objectArray)
      savespace(result)
      for i in range(d1):
          for j in range(d2):
              result[i,j] = stringData[c % stringDataLen]
              ++c
  return result
# DO-NOT-DELETE splicer.end(create2String)

def create3Int():
  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,3> _return
  #

# DO-NOT-DELETE splicer.begin(create3Int)
  result = zeros((3, 3, 2, ), int32)
  savespace(result)
  for i in range(3):
    for j in range(3):
      for k in range(2):
        result[i,j,k] = arrayValue((i,j,k))
  return result
# DO-NOT-DELETE splicer.end(create3Int)

def create4Int():
  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,4> _return
  #

# DO-NOT-DELETE splicer.begin(create4Int)
  result = zeros((3, 3, 2, 2, ), int32)
  savespace(result)
  for i in range(3):
    for j in range(3):
      for k in range(2):
        for l in range(2):
          result[i,j,k,l] = arrayValue((i,j,k,l))
  return result
# DO-NOT-DELETE splicer.end(create4Int)

def create5Int():
  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,5> _return
  #

# DO-NOT-DELETE splicer.begin(create5Int)
  result = zeros((3, 3, 2, 2, 2, ), int32)
  savespace(result)
  for i in range(3):
    for j in range(3):
      for k in range(2):
        for l in range(2):
          for m in range(2):  
            result[i,j,k,l,m] = arrayValue((i,j,k,l,m))
  return result
# DO-NOT-DELETE splicer.end(create5Int)

def create6Int():
  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,6> _return
  #

# DO-NOT-DELETE splicer.begin(create6Int)
  result = zeros((3, 3, 2, 2, 2, 2, ), int32)
  savespace(result)
  for i in range(3):
    for j in range(3):
      for k in range(2):
        for l in range(2):
          for m in range(2):
            for n in range(2):
              result[i,j,k,l,m,n] = arrayValue((i,j,k,l,m,n))
  return result
# DO-NOT-DELETE splicer.end(create6Int)

def create7Int():
  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,7> _return
  #

# DO-NOT-DELETE splicer.begin(create7Int)
  result = zeros((3, 3, 2, 2, 2, 2, 2, ), int32)
  savespace(result)
  for i in range(3):
    for j in range(3):
      for k in range(2):
        for l in range(2):
          for m in range(2):
            for n in range(2):
              for o in range(2):
                result[i,j,k,l,m,n,o] = arrayValue((i,j,k,l,m,n,o))
  return result
# DO-NOT-DELETE splicer.end(create7Int)

def makeBool(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<bool> a
  #

# DO-NOT-DELETE splicer.begin(makeBool)
  return ArrayTest.ArrayOps.createBool(len)
# DO-NOT-DELETE splicer.end(makeBool)

def makeChar(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<char> a
  #

# DO-NOT-DELETE splicer.begin(makeChar)
  return ArrayTest.ArrayOps.createChar(len)
# DO-NOT-DELETE splicer.end(makeChar)

def makeInt(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int> a
  #

# DO-NOT-DELETE splicer.begin(makeInt)
  return ArrayTest.ArrayOps.createInt(len)
# DO-NOT-DELETE splicer.end(makeInt)

def makeLong(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<long> a
  #

# DO-NOT-DELETE splicer.begin(makeLong)
  return ArrayTest.ArrayOps.createLong(len)
# DO-NOT-DELETE splicer.end(makeLong)

def makeString(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<string> a
  #

# DO-NOT-DELETE splicer.begin(makeString)
  return ArrayTest.ArrayOps.createString(len)
# DO-NOT-DELETE splicer.end(makeString)

def makeDouble(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<double> a
  #

# DO-NOT-DELETE splicer.begin(makeDouble)
  return ArrayTest.ArrayOps.createDouble(len)
# DO-NOT-DELETE splicer.end(makeDouble)

def makeFloat(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<float> a
  #

# DO-NOT-DELETE splicer.begin(makeFloat)
  return ArrayTest.ArrayOps.createFloat(len)
# DO-NOT-DELETE splicer.end(makeFloat)

def makeFcomplex(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<fcomplex> a
  #

# DO-NOT-DELETE splicer.begin(makeFcomplex)
  return ArrayTest.ArrayOps.createFcomplex(len)
# DO-NOT-DELETE splicer.end(makeFcomplex)

def makeDcomplex(len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<dcomplex> a
  #

# DO-NOT-DELETE splicer.begin(makeDcomplex)
  return ArrayTest.ArrayOps.createDcomplex(len)
# DO-NOT-DELETE splicer.end(makeDcomplex)

def makeInOutBool(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<bool> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<bool> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutBool)
  return ArrayTest.ArrayOps.createBool(len)
# DO-NOT-DELETE splicer.end(makeInOutBool)

def makeInOutChar(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<char> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<char> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutChar)
  return ArrayTest.ArrayOps.createChar(len)
# DO-NOT-DELETE splicer.end(makeInOutChar)

def makeInOutInt(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutInt)
  return ArrayTest.ArrayOps.createInt(len)
# DO-NOT-DELETE splicer.end(makeInOutInt)

def makeInOutLong(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<long> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<long> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutLong)
  return ArrayTest.ArrayOps.createLong(len)
# DO-NOT-DELETE splicer.end(makeInOutLong)

def makeInOutString(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<string> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<string> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutString)
  return ArrayTest.ArrayOps.createString(len)
# DO-NOT-DELETE splicer.end(makeInOutString)

def makeInOutDouble(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<double> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<double> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutDouble)
  return ArrayTest.ArrayOps.createDouble(len)
# DO-NOT-DELETE splicer.end(makeInOutDouble)

def makeInOutFloat(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<float> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<float> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutFloat)
  return ArrayTest.ArrayOps.createFloat(len)
# DO-NOT-DELETE splicer.end(makeInOutFloat)

def makeInOutDcomplex(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<dcomplex> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<dcomplex> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutDcomplex)
  return ArrayTest.ArrayOps.createDcomplex(len)
# DO-NOT-DELETE splicer.end(makeInOutDcomplex)

def makeInOutFcomplex(a, len):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<fcomplex> a
  # int len
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<fcomplex> a
  #

# DO-NOT-DELETE splicer.begin(makeInOutFcomplex)
  return ArrayTest.ArrayOps.createFcomplex(len)
# DO-NOT-DELETE splicer.end(makeInOutFcomplex)

def makeInOut2Int(a, d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,2> a
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,2> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut2Int)
  return ArrayTest.ArrayOps.create2Int(d1,d2)
# DO-NOT-DELETE splicer.end(makeInOut2Int)

def makeInOut2Double(a, d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<double,2> a
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<double,2> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut2Double)
  return ArrayTest.ArrayOps.create2Double(d1,d2)
# DO-NOT-DELETE splicer.end(makeInOut2Double)

def makeInOut2Float(a, d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<float,2> a
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<float,2> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut2Float)
  return ArrayTest.ArrayOps.create2Float(d1,d2)
# DO-NOT-DELETE splicer.end(makeInOut2Float)

def makeInOut2Dcomplex(a, d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<dcomplex,2> a
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<dcomplex,2> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut2Dcomplex)
  return ArrayTest.ArrayOps.create2Dcomplex(d1,d2)
# DO-NOT-DELETE splicer.end(makeInOut2Dcomplex)

def makeInOut2Fcomplex(a, d1, d2):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<fcomplex,2> a
  # int d1
  # int d2
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<fcomplex,2> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut2Fcomplex)
  return ArrayTest.ArrayOps.create2Fcomplex(d1,d2)
# DO-NOT-DELETE splicer.end(makeInOut2Fcomplex)

def makeInOut3Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,3> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,3> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut3Int)
  return ArrayTest.ArrayOps.create3Int()
# DO-NOT-DELETE splicer.end(makeInOut3Int)

def makeInOut4Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,4> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,4> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut4Int)
  return ArrayTest.ArrayOps.create4Int()
# DO-NOT-DELETE splicer.end(makeInOut4Int)

def makeInOut5Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,5> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,5> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut5Int)
  return ArrayTest.ArrayOps.create5Int()
# DO-NOT-DELETE splicer.end(makeInOut5Int)

def makeInOut6Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,6> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,6> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut6Int)
  return ArrayTest.ArrayOps.create6Int()
# DO-NOT-DELETE splicer.end(makeInOut6Int)

def makeInOut7Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<int,7> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<int,7> a
  #

# DO-NOT-DELETE splicer.begin(makeInOut7Int)
  return ArrayTest.ArrayOps.create7Int()
# DO-NOT-DELETE splicer.end(makeInOut7Int)

def checkGeneric(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (dmn, tp)
  # int dmn
  # int tp
  #

  """\
Return as out parameters the type and dimension of the 
array passed in. If a is NULL, dimen == type == 0 on exit.
The contents of the array have the default values for a 
newly created array.
"""
# DO-NOT-DELETE splicer.begin(checkGeneric)
  # Put your code here...

  if(isinstance(a,NoneType)):
      return (0,0)

  dmn = len(a.shape);
  tp = numTypeToSidlType(typecode(a));  #Take a guess at the correct type!
  
  return (dmn,tp)

# DO-NOT-DELETE splicer.end(checkGeneric)

def createGeneric(dmn, tp):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int dmn
  # int tp
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # array<> _return
  #

  """\
Create an array of the type and dimension specified and
return it. A type of 0 causes a NULL array to be returned.
"""
# DO-NOT-DELETE splicer.begin(createGeneric)
  # Put your code here...
  ashape = []; #the shape of the array, made up from the dimension
  if(dmn < 1 or tp < 1):
      return None;
  
  for i in range(dmn):
      ashape.append(3);

  return createArrayByType(tp, ashape);

# DO-NOT-DELETE splicer.end(createGeneric)

def passGeneric(inArg, inOutArg):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # array<> inArg
  # array<> inOutArg
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, inOutArg, outArg)
  # array<> _return
  # array<> inOutArg
  # array<> outArg
  #

  """\
Testing passing generic arrays using every possible mode.
The returned array is a copy of inArg, so if inArg != NULL,
the return value should != NULL. outArg is also a copy of
inArg.
If inOutArg is NULL on entry, a 2-D array of int that should
pass check2Int is returned.
If inOutArg is not NULL on entry and its dimension is even,
it is returned unchanged; otherwise, NULL is returned.
"""
# DO-NOT-DELETE splicer.begin(passGeneric)
  # Put your code here...
  ret = None;
  outArg = None;
  if (not isinstance(inArg,NoneType)):
      ret = inArg.copy();
      outArg = inArg.copy();

  if(not isinstance(inOutArg,NoneType)):
      if(isOdd(len(inOutArg.shape))):
          inOutArg = None;
  else:
      inOutArg = ArrayTest.ArrayOps.create2Int(3,3);
                
  return (ret,inOutArg, outArg);
          
  
# DO-NOT-DELETE splicer.end(passGeneric)

def initRarray1Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # rarray<int> a
  #

# DO-NOT-DELETE splicer.begin(initRarray1Int)
  # Put your code here...

  if (a.shape[0] >= 0):
      prime = 0
      for i in range(a.shape[0]):
          prime = nextPrime(prime)
          a[i] = prime
                
  return a  
# DO-NOT-DELETE splicer.end(initRarray1Int)

def initRarray3Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int,3> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # rarray<int,3> a
  #

# DO-NOT-DELETE splicer.begin(initRarray3Int)
  # Put your code here...
  for i in range(a.shape[0]):
    for j in range(a.shape[1]):
      for k in range(a.shape[2]):
        a[i,j,k] = arrayValue((i,j,k))
        
  return a
 
# DO-NOT-DELETE splicer.end(initRarray3Int)

def initRarray7Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int,7> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # rarray<int,7> a
  #

# DO-NOT-DELETE splicer.begin(initRarray7Int)
  # Put your code here...
  for i in range(a.shape[0]):
    for j in range(a.shape[1]):
      for k in range(a.shape[2]):
        for l in range(a.shape[3]):
          for m in range(a.shape[4]):
            for n in range(a.shape[5]):
              for o in range(a.shape[6]):
                a[i,j,k,l,m,n,o] = arrayValue((i,j,k,l,m,n,o))
  return a
# DO-NOT-DELETE splicer.end(initRarray7Int)

def initRarray1Double(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<double> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # rarray<double> a
  #

# DO-NOT-DELETE splicer.begin(initRarray1Double)
  # Put your code here...
  if (a.shape[0] >= 0):
      for i in range(a.shape[0]):
          a[i] = powTwo(-i)
  return a          
# DO-NOT-DELETE splicer.end(initRarray1Double)

def initRarray1Dcomplex(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<dcomplex> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # rarray<dcomplex> a
  #

# DO-NOT-DELETE splicer.begin(initRarray1Dcomplex)
  # Put your code here...
  if (a.shape[0] >= 0):
      for i in range(a.shape[0]):
          a[i] = powTwo(i) + powTwo(-i)*1j
  return a
# DO-NOT-DELETE splicer.end(initRarray1Dcomplex)

def checkRarray1Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkRarray1Int)
  # Put your code here...
  return checkInt(a)
# DO-NOT-DELETE splicer.end(checkRarray1Int)

def checkRarray3Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int,3> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkRarray3Int)
  # Put your code here...
  return check3Int(a)
# DO-NOT-DELETE splicer.end(checkRarray3Int)

def checkRarray7Int(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int,7> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkRarray7Int)
  # Put your code here...
  return check7Int(a)
# DO-NOT-DELETE splicer.end(checkRarray7Int)

def checkRarray1Double(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<double> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkRarray1Double)
  # Put your code here...
  return checkDouble(a)
# DO-NOT-DELETE splicer.end(checkRarray1Double)

def checkRarray1Dcomplex(a):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<dcomplex> a
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkRarray1Dcomplex)
  # Put your code here...
  return checkDcomplex(a)
# DO-NOT-DELETE splicer.end(checkRarray1Dcomplex)

def matrixMultiply(a, b, res):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int,2> a
  # rarray<int,2> b
  # rarray<int,2> res
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # rarray<int,2> res
  #

# DO-NOT-DELETE splicer.begin(matrixMultiply)
  # Put your code here...
  locMatrixMultiply(a,b,res)
  return res
# DO-NOT-DELETE splicer.end(matrixMultiply)

def checkMatrixMultiply(a, b, res):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # rarray<int,2> a
  # rarray<int,2> b
  # rarray<int,2> res
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # bool _return
  #

# DO-NOT-DELETE splicer.begin(checkMatrixMultiply)
  # Put your code here...
  test = zeros((a.shape[0], b.shape[1], ), int32)
  savespace(test)
  locMatrixMultiply(a,b,test);
  for i in range(a.shape[0]):
    for j in range(b.shape[1]):
      if (test[i,j] != res[i,j]):
        return 0
  return 1
# DO-NOT-DELETE splicer.end(checkMatrixMultiply)


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Put your code here...
# DO-NOT-DELETE splicer.end(_before_type)

class ArrayOps(
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
      self.__IORself = ArrayTest.ArrayOps.ArrayOps(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def mm(self, a, b, res):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # rarray<int,2> a
    # rarray<int,2> b
    # rarray<int,2> res
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # rarray<int,2> res
    #

# DO-NOT-DELETE splicer.begin(mm)
    # Insert-Code-Here {mm} ()
    pass
# DO-NOT-DELETE splicer.end(mm)

  def checkmm(self, a, b, res):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # rarray<int,2> a
    # rarray<int,2> b
    # rarray<int,2> res
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

# DO-NOT-DELETE splicer.begin(checkmm)
    # Insert-Code-Here {checkmm} ()
    pass
# DO-NOT-DELETE splicer.end(checkmm)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
