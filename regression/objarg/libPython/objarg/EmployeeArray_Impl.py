#
# File:          EmployeeArray_Impl.py
# Symbol:        objarg.EmployeeArray-v0.5
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class objarg.EmployeeArray in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""This class manages a collection of employees.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import objarg.Employee
import objarg.EmployeeArray
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Put your code here...
# DO-NOT-DELETE splicer.end(_before_type)

class EmployeeArray(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
This class manages a collection of employees.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = objarg.EmployeeArray.EmployeeArray(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    self.employees = []
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def getLength(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

    """\
Return the number of employees in the employee array.
"""
# DO-NOT-DELETE splicer.begin(getLength)
    return len(self.employees)
# DO-NOT-DELETE splicer.end(getLength)

  def at(self, idx):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int idx
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # objarg.Employee _return
    #

    """\
Return the employee in position <code>idx</code> where
<code>idx</code> ranges from 1 to the length of the array.
If <code>idx</code> is outside the range of the array (i.e.
less than or equal to zero or greater than the current number
of elements in the array), this method returns a NULL
employee object.
"""
# DO-NOT-DELETE splicer.begin(at)
    if (idx >= 1 and idx <= len(self.employees)):
        return self.employees[idx-1]
    return None
# DO-NOT-DELETE splicer.end(at)

  def appendEmployee(self, e):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # objarg.Employee e
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
Add an employee onto the end of the array.  It is perfectly
legal to add the same employee multiple times.
<code>true</code> is returned when the append was successful;
otherwise, <code>false</code> is returned to indicate
failure.  This method will not add a NULL employee.
"""
# DO-NOT-DELETE splicer.begin(appendEmployee)
    if (e):
        self.employees.append(e)
        return 1
    return 0
# DO-NOT-DELETE splicer.end(appendEmployee)

  def findByName(self, name):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string name
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, e)
    # int _return
    # objarg.Employee e
    #

    """\
Find the first employee in the array that has a name matching
<code>name</code>.  If a match exists, the index is returned,
and the employee is returned in parameter <code>e</code>.

If no match exists, 0 is returned, and <code>e</code> is NULL.
"""
# DO-NOT-DELETE splicer.begin(findByName)
    index = 1
    for e in self.employees:
        if (e.getName() == name):
            return index, e
        index = index + 1
    return 0, None
# DO-NOT-DELETE splicer.end(findByName)

  def promoteToMaxSalary(self, e):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # objarg.Employee e
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, e)
    # bool _return
    # objarg.Employee e
    #

    """\
Determine the maximum salary in the array. If the maximum
salary in the array is greater than the current salary of
<code>e</code>, the salary of <code>e</code> will be 
increased to the maximum salary in the array.  If the
array is empty, no change will be made to <code>e</code>.

This method returns <code>true</code> iff the salary of
<code>e</code> is modified.
"""
# DO-NOT-DELETE splicer.begin(promoteToMaxSalary)
    if (e):
      maxSalary = -1.0e37
      for i in self.employees:
        if (i.getSalary() > maxSalary):
          maxSalary = i.getSalary()
      if (maxSalary > e.getSalary()):
        e.setSalary(maxSalary)
        return 1, e
    return 0, e
# DO-NOT-DELETE splicer.end(promoteToMaxSalary)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
