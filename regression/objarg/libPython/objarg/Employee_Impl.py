#
# File:          Employee_Impl.py
# Symbol:        objarg.Employee-v0.5
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class objarg.Employee in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


"""This object type holds the basic information about an employee:
name, age, salary and marital status.  This object exists purely
to serve as a test case for sidl.  It is not intended for serious
use.
"""

# DO-NOT-DELETE splicer.begin(_initial)
# Put your code here...
# DO-NOT-DELETE splicer.end(_initial)

import objarg.Employee
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

class Employee(
# DO-NOT-DELETE splicer.begin(_inherits)
#
# Name of type(s) to inherit from.
# If desired, please change `PlaceHolder' to your own base class.
#

PlaceHolder
# DO-NOT-DELETE splicer.end(_inherits)
  ):
  """\
This object type holds the basic information about an employee:
name, age, salary and marital status.  This object exists purely
to serve as a test case for sidl.  It is not intended for serious
use.
"""

# All calls to sidl methods should use __IORself

# Normal Babel creation pases in an IORself. If IORself == None
# that means this Impl class is being constructed for native delegation
  def __init__(self, IORself = None):
    if (IORself == None):
      self.__IORself = objarg.Employee.Employee(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    self.name = None
    self.salary = None
    self.status = None
    self.age = None
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def init(self, name, age, salary, status):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string name
    # int age
    # float salary
    # char status
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # bool _return
    #

    """\
Provide the data for the employee object to hold.
<code>false</code> is returned when the data was unacceptable.
<code>true</code> means the employee object was successfully
initialized.
"""
# DO-NOT-DELETE splicer.begin(init)
    self.name = name
    self.age = age
    self.salary = salary
    self.status = status
    return 1
# DO-NOT-DELETE splicer.end(init)

  def setName(self, name):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # string name
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Change the name of an employee.
"""
# DO-NOT-DELETE splicer.begin(setName)
    self.name = name
# DO-NOT-DELETE splicer.end(setName)

  def getName(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # string _return
    #

    """\
Return the name of an employee.
"""
# DO-NOT-DELETE splicer.begin(getName)
    return self.name
# DO-NOT-DELETE splicer.end(getName)

  def setAge(self, age):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int age
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Change the age of an employee.
"""
# DO-NOT-DELETE splicer.begin(setAge)
    self.age = age
# DO-NOT-DELETE splicer.end(setAge)

  def getAge(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # int _return
    #

    """\
Return the age of an employee.
"""
# DO-NOT-DELETE splicer.begin(getAge)
    return self.age
# DO-NOT-DELETE splicer.end(getAge)

  def setSalary(self, salary):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # float salary
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Set an employee's salary.
"""
# DO-NOT-DELETE splicer.begin(setSalary)
    self.salary = salary
# DO-NOT-DELETE splicer.end(setSalary)

  def getSalary(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # float _return
    #

    """\
Return an employee's salary.
"""
# DO-NOT-DELETE splicer.begin(getSalary)
    return self.salary
# DO-NOT-DELETE splicer.end(getSalary)

  def setStatus(self, status):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # char status
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Set an employee's marital status.
"""
# DO-NOT-DELETE splicer.begin(setStatus)
    self.status = status
# DO-NOT-DELETE splicer.end(setStatus)

  def getStatus(self):
    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # char _return
    #

    """\
Return an employee's marital status.
"""
# DO-NOT-DELETE splicer.begin(getStatus)
    return self.status
# DO-NOT-DELETE splicer.end(getStatus)

# DO-NOT-DELETE splicer.begin(_final)
# Put your code here...
# DO-NOT-DELETE splicer.end(_final)
