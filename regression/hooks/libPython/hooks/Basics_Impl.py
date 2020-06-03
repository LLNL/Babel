#
# File:          Basics_Impl.py
# Symbol:        hooks.Basics-v1.0
# Symbol Type:   class
# Babel Version: 2.0.0 (Revision: 7463M trunk)
# Description:   Implementation of sidl class hooks.Basics in Python.
# 
# WARNING: Automatically generated; changes will be lost
# 
#


# DO-NOT-DELETE splicer.begin(_initial)
# Insert-Code-Here {_initial} ()
# DO-NOT-DELETE splicer.end(_initial)

import hooks.Basics
import sidl.BaseClass
import sidl.BaseInterface
import sidl.ClassInfo
import sidl.RuntimeException
import sidl.NotImplementedException

# DO-NOT-DELETE splicer.begin(_before_static)
import synch
from synch import RegOut
from synch.ResultType import *

num_prehooks_static = 0
num_posthooks_static = 0
# DO-NOT-DELETE splicer.end(_before_static)

def aStaticMeth_pre(i, io):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int i
  # int io
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # None
  #

  """\
Basic illustration of hooks for static methods.
"""
# DO-NOT-DELETE splicer.begin(aStaticMeth_pre)
  #
  # This method has not been implemented
  #
  global num_prehooks_static
  num_prehooks_static += 1
  return
# DO-NOT-DELETE splicer.end(aStaticMeth_pre)

def aStaticMeth(i, io):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int i
  # int io
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # (_return, o, io)
  # int _return
  # int o
  # int io
  #

  """\
Basic illustration of hooks for static methods.
"""
# DO-NOT-DELETE splicer.begin(aStaticMeth)
  #
  # This method has not been implemented
  #
  return (1,num_prehooks_static,num_posthooks_static);
# DO-NOT-DELETE splicer.end(aStaticMeth)

def aStaticMeth_post(i, o, io, _retval):
  #
  # sidl EXPECTED INCOMING TYPES
  # ============================
  # int i
  # int o
  # int io
  # int _retval
  #

  #
  # sidl EXPECTED RETURN VALUE(s)
  # =============================
  # None
  #

  """\
Basic illustration of hooks for static methods.
"""
# DO-NOT-DELETE splicer.begin(aStaticMeth_post)
  #
  # This method has not been implemented
  #
  global num_posthooks_static
  num_posthooks_static += 1
  return
# DO-NOT-DELETE splicer.end(aStaticMeth_post)


class PlaceHolder:
  """work around a limitation of the grammar of Python < 2.5"""
  pass

# DO-NOT-DELETE splicer.begin(_before_type)
# Insert-Code-Here {_before_type} ()
# DO-NOT-DELETE splicer.end(_before_type)

class Basics(
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
      self.__IORself = hooks.Basics.Basics(impl = self)
    else:
      self.__IORself = IORself
# DO-NOT-DELETE splicer.begin(__init__)
    # Insert-Code-Here {__init__} ()
    self.num_prehooks = 0
    self.num_posthooks = 0
# DO-NOT-DELETE splicer.end(__init__)

# Returns the IORself (client stub) of the Impl, mainly for use
# with native delegation
  def _getStub(self):
    return self.__IORself

  def aNonStaticMeth_pre(self, i, io):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    # int io
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Basic illustration of hooks for static methods.
"""
# DO-NOT-DELETE splicer.begin(aNonStaticMeth_pre)
    #
    # This method has not been implemented
    #
    self.num_prehooks += 1
    return
# DO-NOT-DELETE splicer.end(aNonStaticMeth_pre)

  def aNonStaticMeth(self, i, io):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    # int io
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # (_return, o, io)
    # int _return
    # int o
    # int io
    #

    """\
Basic illustration of hooks for static methods.
"""
# DO-NOT-DELETE splicer.begin(aNonStaticMeth)
    #
    # This method has not been implemented
    #
    return (1,self.num_prehooks,self.num_posthooks)
# DO-NOT-DELETE splicer.end(aNonStaticMeth)

  def aNonStaticMeth_post(self, i, o, io, _retval):
    #
    # sidl EXPECTED INCOMING TYPES
    # ============================
    # int i
    # int o
    # int io
    # int _retval
    #

    #
    # sidl EXPECTED RETURN VALUE(s)
    # =============================
    # None
    #

    """\
Basic illustration of hooks for static methods.
"""
# DO-NOT-DELETE splicer.begin(aNonStaticMeth_post)
    #
    # This method has not been implemented
    #
    self.num_posthooks += 1
    return
# DO-NOT-DELETE splicer.end(aNonStaticMeth_post)

# DO-NOT-DELETE splicer.begin(_final)
# Insert-Code-Here {_final} ()
# DO-NOT-DELETE splicer.end(_final)
