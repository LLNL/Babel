#
# File:          Fib_Impl.py
# Symbol:        Exceptions.Fib-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class Exceptions.Fib in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""This class holds the method <code>getFib</code> that generates the
requested Fibonacci numbers.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import Exceptions.Fib
import Exceptions.FibException
import Exceptions.NegativeValueException
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.MemAllocException
import sidl.RuntimeException
import sidl.SIDLException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
import Exceptions.TooDeepException
import Exceptions.TooBigException
# DO-NOT-DELETE splicer.end(_before_type)

class Fib(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
This class holds the method <code>getFib</code> that generates the
requested Fibonacci numbers.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = Exceptions.Fib.Fib(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Put your code here...
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def getFib(self, n, max_depth, max_value, depth):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int n
    # int max_depth
    # int max_value
    # int depth
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

    """\
<p>
Generate the requested Fibonacci number or generate Exceptions if
the input Fibonacci number is invalid or if any of the maximum depth
or maximum value parameters are exceeded.  The last argument of the
method should be zero.
</p>
<p>
The algorithm should be similar to the <code>Java</code> code below.
</p>
<pre>
public int getFib(int n, int max_depth, int max_value, int depth)
throws NegativeValueException, FibException {

if (n < 0) {
throw new NegativeValueException(\"n negative\");

} else if (depth > max_depth) {
throw new TooDeepException(\"too deep\");

} else if (n == 0) {
return 1;

} else if (n == 1) {
return 1;

} else {
int a = getFib(n-1, max_depth, max_value, depth+1);
int b = getFib(n-2, max_depth, max_value, depth+1);
if (a + b > max_value) {
throw new TooBigException(\"too big\");
}
return a + b;
}
} 
</pre>
"""
# DO-NOT-DELETE splicer.begin(getFib)
    if (n < 0):
      ex = Exceptions.NegativeValueException.NegativeValueException()
      ex.setNote("n negative")
      ex.add(__name__, 0, "Exceptions.Fib.getFib")
      raise Exceptions.NegativeValueException._Exception, ex
    if (depth > max_depth):
      ex =  Exceptions.TooDeepException.TooDeepException()
      ex.setNote("too deep")
      ex.add(__name__, 0, "Exceptions.Fib.getFib")
      raise Exceptions.FibException._Exception, ex
    if (n == 0):
      return 1
    if (n == 1):
      return 1
    try:
      a = self.__IORself.getFib(n - 1, max_depth, max_value, depth + 1)
      b = self.__IORself.getFib(n - 2, max_depth, max_value, depth + 1)
      if (a + b > max_value):
        ex = Exceptions.TooBigException.TooBigException()
        ex.setNote("too big")
        ex.add(__name__, 0, "Exceptions.Fib.getFib")
        raise Exceptions.FibException._Exception, ex
      return a + b
    except Exceptions.FibException._Exception, exc:
      exc.exception.add(__name__, 0, "Exceptions.Fib.getFib")
      raise Exceptions.FibException._Exception, exc
# DO-NOT-DELETE splicer.end(getFib)

  def noLeak(self, a1, a2, r1, r2, c1, c2, s1, s2, o1, o2):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # array<int,2,column-major> a1
    # array<int,2,column-major> a2
    # rarray<int,2> r1
    # rarray<int,2> r2
    # array<int> c1
    # array<int> c2
    # string s1
    # string s2
    # sidl.BaseClass o1
    # sidl.BaseClass o2
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, a2, a3, r2, c2, c3, s2, s3, o2, o3)
    # array<int,2,column-major> _return
    # array<int,2,column-major> a2
    # array<int,2,column-major> a3
    # rarray<int,2> r2
    # array<int> c2
    # array<int> c3
    # string s2
    # string s3
    # sidl.BaseClass o2
    # sidl.BaseClass o3
    #

    """\
Check for memory/reference leaks in the presence of an exception.
The impl will throw an exception and assign random values to
out parameters to prove that out values are ignored.
The intent is that row-major arrays should be passed to parameters
a1, a2, a3.
"""
# DO-NOT-DELETE splicer.begin(noLeak)
    # Python requires no cleanup
    myException = sidl.SIDLException.SIDLException()
    myException.setNote("This method must throw an exception.")
    raise  sidl.SIDLException._Exception, myException
# DO-NOT-DELETE splicer.end(noLeak)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
