#!/usr/bin/python
#
# File:        genfortranarrays.py
# Revision:    @(#)$Revision: 7188 $
# Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
# Description: Python to generate the array handling routines for Fortran
#
# Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC
# Produced at the Lawrence Livermore National Laboratory.
# Written by the Components Team <components@llnl.gov>
# UCRL-CODE-2002-054
# All rights reserved.
# 
# This file is part of Babel. For more information, see
# http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
# for Our Notice and the LICENSE file for the GNU Lesser General Public
# License.
# 
# This program is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License (as published by
# the Free Software Foundation) version 2.1 dated February 1999.
# 
# This program is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
# conditions of the GNU Lesser General Public License for more details.
# 
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
#

import string
import sha
import sys

# the following list must match the one in
# gov/llnl/babel/backend/mangler/FortranMangler.java
# it's the list of distinct characters in FORTRAN
__fortranChars = "abcdefghijklmnopqrstuvwxyz0123456789_"
# must agree with gov.llnl.babel.backend.fortran.AbbrevHeader.java
__maxLength = 31
# must agree with gov.llnl.babel.backend.fortran.AbbrevHeader.java
__maxUnmangled = 21

# these types don't get borrow & access
limited = ( 'bool', 'opaque' )

# output device
output = None

def longName(symbol, method, suffix):
  return symbol.replace('.', '_') + "__array_" + method + suffix

def calculateDigest(symbol, method, array, suffix):
  bigint = 0L
  digest = sha.new()
  digest.update(symbol)
  digest.update(method)
  digest.update(array)
  digest.update(suffix)
  for i in digest.digest():
    bigint = (bigint << 8) | ord(i)
  bigint = abs(bigint)
  return bigint

def truncateSymbol(symbol, maxLength):
  result = ""
  if (maxLength > 0):
    tokens = symbol.split('.')
    add  = []
    tokens.reverse()
    for i in tokens:
      if (maxLength - len(i) - 1 >= 0):
        add = add + i
        maxLength = maxLength - len(i) - 1
      else:
        break
    if (len(add) > 0):
      add.reverse()
      for i in add:
        result = result + i + "_"
    else:
      if (len(tokens) > 0):
        result = tokens[0][0:maxLength]
  return result

# this method makes some assumptions about the length of methods
def shortArrayName(symbol, method, suffix):
  """\
A reimplementation of gov.llnl.babel.backend.mangler.ShaMangler.shortArrayName."""
  if (len(symbol) + len(method) + len(suffix) + 7 >= __maxLength):
    digest = calculateDigest(symbol, method, "_ary_", suffix)
    symbol = truncateSymbol(symbol, __maxUnmangled - len(method) -
                            len(suffix) - 8)
    result = symbol
    if (len(result) > 0):
      result = result + "_ary_"
    else:
      result = result + "ary_"
    result = result + method
    mangledChars = __maxLength - len(suffix) - len(result)
    while (mangledChars > 0):
      mangledChars = mangledChars - 1
      char = digest % len(__fortranChars)
      digest = digest / len(__fortranChars)
      result = result + __fortranChars[char]
    result = result + suffix
    return result
  else:
    return longName(symbol, method, suffix)

#----------------------------------------------------------------
def createCol(arraytype, fortversion, suffix):
  output.write("""
void
SIDLFortran%sSymbol(sidl_%s__array_createcol_%s,
                    SIDL_%s__ARRAY_CREATECOL_%s,
                    sidl_%s__array_createCol_%s)
  (int32_t *dimen, int32_t lower[], int32_t upper[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_createCol(*dimen, lower, upper);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype))


#----------------------------------------------------------------
def createColRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_createcol_%s,
                    SIDL_%s__ARRAY_CREATECOL_%s,
                    sidl_%s__array_createCol_%s)
  (int32_t *dimen, int32_t lower[], int32_t upper[], 
   struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_createCol(*dimen, lower, upper),
    *dimen, result);
    
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def createRow(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_createrow_%s,
                    SIDL_%s__ARRAY_CREATEROW_%s,
                    sidl_%s__array_createRow_%s)
  (int32_t *dimen, int32_t lower[], int32_t upper[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_createRow(*dimen, lower, upper);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype))

#----------------------------------------------------------------
def createRowRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_createrow_%s,
                    SIDL_%s__ARRAY_CREATEROW_%s,
                    sidl_%s__array_createRow_%s)
  (int32_t *dimen, int32_t lower[], int32_t upper[], 
   struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_createRow(*dimen, lower, upper),
    *dimen, result);
    
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def create1d(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_create1d_%s,
                    SIDL_%s__ARRAY_CREATE1D_%s,
                    sidl_%s__array_create1d_%s)
  (int32_t *len, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_create1d(*len);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype))


#----------------------------------------------------------------
def create1dRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_create1d_%s,
                    SIDL_%s__ARRAY_CREATE1D_%s,
                    sidl_%s__array_create1d_%s)
  (int32_t *len, struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_create1d(*len), 1, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def createRow2d(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_create2drow_%s,
                    SIDL_%s__ARRAY_CREATE2DROW_%s,
                    sidl_%s__array_create2dRow_%s)
  (int32_t *m, int32_t *n, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_create2dRow(*m, *n);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype))


#----------------------------------------------------------------
def createRow2dRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_create2drow_%s,
                    SIDL_%s__ARRAY_CREATE2DROW_%s,
                    sidl_%s__array_create2dRow_%s)
  (int32_t *m, int32_t *n, struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_create2dRow(*m, *n), 2, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def createCol2d(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_create2dcol_%s,
                    SIDL_%s__ARRAY_CREATE2DCOL_%s,
                    sidl_%s__array_create2dCol_%s)
  (int32_t *m, int32_t *n, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_create2dCol(*m, *n);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype))


#----------------------------------------------------------------
def createCol2dRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_create2dcol_%s,
                    SIDL_%s__ARRAY_CREATE2DCOL_%s,
                    sidl_%s__array_create2dCol_%s)
  (int32_t *m, int32_t *n, struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_create2dCol(*m, *n), 2, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def copyArray(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_copy_%s,
                    SIDL_%s__ARRAY_COPY_%s,
                    sidl_%s__array_copy_%s)
  (int64_t *src, int64_t *dest)
{
  sidl_%s__array_copy((struct sidl_%s__array*)(ptrdiff_t)*src,
                      (struct sidl_%s__array*)(ptrdiff_t)*dest);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype, arraytype))


#----------------------------------------------------------------
def ensureArray(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_ensure_%s,
                    SIDL_%s__ARRAY_ENSURE_%s,
                    sidl_%s__array_ensure_%s)
  (int64_t *src, int32_t *dimen, int32_t *ordering, int64_t *result)
{
  *result = (ptrdiff_t)
     sidl_%s__array_ensure((struct sidl_%s__array*)(ptrdiff_t)*src,
                           *dimen, (int)*ordering);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def ensureArrayRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_ensure_%s,
                    SIDL_%s__ARRAY_ENSURE_%s,
                    sidl_%s__array_ensure_%s)
  (int64_t *src, int32_t *dimen, int32_t *ordering, 
   struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
     sidl_%s__array_ensure((struct sidl_%s__array*)(ptrdiff_t)*src,
                           *dimen, (int)*ordering),
     *dimen, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype, arraytype))


#----------------------------------------------------------------
def smartCopyArray(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_smartcopy_%s,
                    SIDL_%s__ARRAY_SMARTCOPY_%s,
                    sidl_%s__array_smartCopy_%s)
  (int64_t *src, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_smartCopy((struct sidl_%s__array*)(ptrdiff_t)*src);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def smartCopyArrayRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_smartcopy_%s,
                    SIDL_%s__ARRAY_SMARTCOPY_%s,
                    sidl_%s__array_smartCopy_%s)
  (int64_t *src, int32_t *dimen, struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_smartCopy((struct sidl_%s__array*)(ptrdiff_t)*src),
    *dimen, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype, arraytype))


#----------------------------------------------------------------
def slice(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_slice_%s,
                    SIDL_%s__ARRAY_SLICE_%s,
                    sidl_%s__array_slice_%s)
  (int64_t *src, int32_t *dimen, int32_t numElem[], int32_t srcStart[],
   int32_t srcStride[], int32_t newStart[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_slice((struct sidl_%s__array *)(ptrdiff_t)*src,
                         *dimen, numElem, srcStart, srcStride, newStart);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def sliceRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_slice_%s,
                    SIDL_%s__ARRAY_SLICE_%s,
                    sidl_%s__array_slice_%s)
  (int64_t *src, int32_t *dimen, int32_t numElem[], int32_t srcStart[],
   int32_t srcStride[], int32_t newStart[], struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_slice((struct sidl_%s__array *)(ptrdiff_t)*src,
                         *dimen, numElem, srcStart, srcStride, newStart),
    *dimen, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype, arraytype))


#----------------------------------------------------------------
def borrow(arraytype, datatype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_borrow_%s,
                    SIDL_%s__ARRAY_BORROW_%s,
                    sidl_%s__array_borrow_%s)
  (%s *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), datatype, arraytype))


#----------------------------------------------------------------
def borrowRaw(arraytype, datatype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_borrow_%s,
                    SIDL_%s__ARRAY_BORROW_%s,
                    sidl_%s__array_borrow_%s)
  (%s *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], struct sidl_fortran_array *result)
{
  sidl_%s__array_convert2f90(
    sidl_%s__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride),
    *dimen, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), datatype, arraytype, arraytype))


#----------------------------------------------------------------
def cborrow(arraytype, datatype, fortversion, suffix):
  output.write( """
static struct sidl_%s__array *
smartCp(struct sidl_%s__array *a) {
  sidl_%s__array_addRef(a);
  return a;
}

static struct sidl__array_vtable CommonBlockVtable = {
  NULL, NULL, NULL
};

void
SIDLFortran%sSymbol(sidl_%s__array_cborrow_%s,
                    SIDL_%s__ARRAY_CBORROW_%s,
                    sidl_%s__array_cborrow_%s)
  (%s *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], int64_t *result)
{
  struct sidl_%s__array *a = 
    sidl_%s__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride);
  if (a) {
    if (CommonBlockVtable.d_destroy == NULL) {
      CommonBlockVtable.d_destroy = a->d_metadata.d_vtable->d_destroy;
      CommonBlockVtable.d_smartcopy = (struct sidl__array *(*)(struct sidl__array *))smartCp;
      CommonBlockVtable.d_arraytype = a->d_metadata.d_vtable->d_arraytype;
    }
    a->d_metadata.d_vtable = &CommonBlockVtable;
  }
  *result = (ptrdiff_t)a;
}

""" % (arraytype, arraytype, arraytype,
       fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), datatype, arraytype, arraytype))


#----------------------------------------------------------------
def cborrowRaw(arraytype, datatype, fortversion, suffix):
  output.write( """
static struct sidl_%s__array *
smartCp(struct sidl_%s__array *a) {
  sidl_%s__array_addRef(a);
  return a;
}

static struct sidl__array_vtable CommonBlockVtable = {
  NULL, NULL, NULL
};

void
SIDLFortran%sSymbol(sidl_%s__array_cborrow_%s,
                    SIDL_%s__ARRAY_CBORROW_%s,
                    sidl_%s__array_cborrow_%s)
  (%s *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], struct sidl_fortran_array *result)
{
  struct sidl_%s__array *a = 
    sidl_%s__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride);
  if (a) {
    if (CommonBlockVtable.d_destroy == NULL) {
      CommonBlockVtable.d_destroy = a->d_metadata.d_vtable->d_destroy;
      CommonBlockVtable.d_smartcopy = (struct sidl__array *(*)(struct sidl__array *))smartCp;
      CommonBlockVtable.d_arraytype = a->d_metadata.d_vtable->d_arraytype;
    }
    a->d_metadata.d_vtable = &CommonBlockVtable;
  }
  sidl_%s__array_convert2f90(a, *dimen, result);
}

""" % (arraytype, arraytype, arraytype,
       fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), datatype, arraytype, arraytype, arraytype))

#----------------------------------------------------------------
def cborrowRawF03(arraytype, datatype, fortversion, suffix):
  output.write( """
static struct sidl_%s__array *
smartCp(struct sidl_%s__array *a) {
  sidl_%s__array_addRef(a);
  return a;
}

static struct sidl__array_vtable CommonBlockVtable = {
  NULL, NULL, NULL
};

struct sidl_%s__array *
sidl_%s__array_cborrow_f03(%s *firstElement,
                           int32_t dimen,
                           int32_t lower[],
                           int32_t upper[],
                           int32_t stride[])
{
  struct sidl_%s__array *a = 
    sidl_%s__array_borrow(firstElement, dimen, lower, upper, stride);
  if (a) {
    if (CommonBlockVtable.d_destroy == NULL) {
      CommonBlockVtable.d_destroy = a->d_metadata.d_vtable->d_destroy;
      CommonBlockVtable.d_smartcopy = (struct sidl__array *(*)(struct sidl__array *))smartCp;
      CommonBlockVtable.d_arraytype = a->d_metadata.d_vtable->d_arraytype;
    }
    a->d_metadata.d_vtable = &CommonBlockVtable;
  }
  return a;
}

""" % (arraytype, arraytype, arraytype, arraytype, arraytype,
       datatype, arraytype, arraytype))
  

#----------------------------------------------------------------
def destroy(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_deleteref_%s,
                    SIDL_%s__ARRAY_DELETEREF_%s,
                    sidl_%s__array_deleteRef_%s)
  (int64_t *array)
{
  sidl_%s__array_deleteRef((struct sidl_%s__array*)(ptrdiff_t)*array);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def addRef(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_addref_%s,
                    SIDL_%s__ARRAY_ADDREF_%s,
                    sidl_%s__array_addRef_%s)
  (int64_t *array)
{
  sidl_%s__array_addRef((struct sidl_%s__array*)(ptrdiff_t)*array);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype,
       string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def get(arraytype, datatype, casttype, num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_get%d_%s,
                    SIDL_%s__ARRAY_GET%d_%s,
                    sidl_%s__array_get%d_%s)
  (int64_t *array,""" % (fortversion, string.lower(arraytype), num, 
                         string.lower(suffix), string.upper(arraytype), num, 
                         string.upper(suffix), arraytype, num, 
                         string.lower(suffix)))
  for i in range(1, num+1):
    output.write( "   int32_t *i%d,\n" % (i))
  output.write( """   %s *result)
{
  *result = %s
    sidl_%s__array_get%d((struct sidl_%s__array *)(ptrdiff_t)*array,\n"""  % (datatype,
      casttype, arraytype, num, arraytype))
  for i in (range(1, num)):
    output.write( "     *i%d,\n" % i)
  output.write( """     *i%d);
}

""" % num)


#----------------------------------------------------------------
def genericget(arraytype, datatype, casttype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_get_%s,
                    SIDL_%s__ARRAY_GET_%s,
                    sidl_%s__array_get_%s)
  (int64_t *array, int32_t indices[], %s *value)
{
  *value = %s
     sidl_%s__array_get((struct sidl_%s__array *)(ptrdiff_t)*array, indices);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), datatype, casttype, arraytype, arraytype))


#----------------------------------------------------------------
def boolget(arraytype, datatype, casttype, num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_get%d_%s,
                    SIDL_%s__ARRAY_GET%d_%s,
                    sidl_%s__array_get%d_%s)
  (int64_t *array,""" % (fortversion, string.lower(arraytype), num, 
                         string.lower(suffix), string.upper(arraytype), num, 
                         string.upper(suffix), arraytype, num, 
                         string.lower(suffix)))
  for i in range(1, num+1):
    output.write( "   int32_t *i%d,\n" % (i))
  output.write( """   SIDL_F%s_Bool *result)
{
  %s tmp =
    sidl_%s__array_get%d((struct sidl_%s__array *)(ptrdiff_t)*array,"""  % (
    fortversion, datatype, arraytype, num, arraytype))
  for i in (range(1, num)):
    output.write( "     *i%d,\n" % i)
  output.write( """     *i%d);
  *result = (tmp ? SIDL_F%s_TRUE : SIDL_F%s_FALSE);
}

""" % (num, fortversion, fortversion))


#----------------------------------------------------------------
def boolgenericget(arraytype, datatype, casttype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_get_%s,
                    SIDL_%s__ARRAY_GET_%s,
                    sidl_%s__array_get_%s)
  (int64_t *array, int32_t indices[], SIDL_F%s_Bool *value)
{
  %s tmp = 
     sidl_%s__array_get((struct sidl_%s__array *)(ptrdiff_t)*array, indices);
  *value = (tmp ? SIDL_F%s_TRUE : SIDL_F%s_FALSE);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), fortversion, datatype, arraytype, arraytype,
        fortversion, fortversion))


#----------------------------------------------------------------
def set(arraytype, datatype, casttype, num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_set%d_%s,
                    SIDL_%s__ARRAY_SET%d_%s,
                    sidl_%s__array_set%d_%s)
  (int64_t *array,""" % (fortversion, string.lower(arraytype), num, 
                         string.lower(suffix), string.upper(arraytype), num, 
                         string.upper(suffix), arraytype, num,
                         string.lower(suffix)))
  for i in range(1, num+1):
    output.write( "   int32_t *i%d,\n" % (i))
  output.write( """   %s *value)
{
  sidl_%s__array_set%d((struct sidl_%s__array *)(ptrdiff_t)*array,
"""  % (datatype,
      arraytype, num, arraytype))
  for i in (range(1, num+1)):
    output.write( "   *i%d,\n" % i)
  output.write( """   %s*value);
}

""" % (casttype))


#----------------------------------------------------------------
def genericset(arraytype, datatype, casttype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_set_%s,
                    SIDL_%s__ARRAY_SET_%s,
                    sidl_%s__array_set_%s)
  (int64_t *array, int32_t indices[], %s *value)
{
  sidl_%s__array_set((struct sidl_%s__array *)(ptrdiff_t)*array, indices, %s *value);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
        string.upper(arraytype), string.upper(suffix), arraytype,
        string.lower(suffix), datatype, arraytype, arraytype, casttype))


#----------------------------------------------------------------
def boolset(arraytype, datatype, casttype, num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_set%d_%s,
                    SIDL_%s__ARRAY_SET%d_%s,
                    sidl_%s__array_set%d_%s)
  (int64_t *array,""" % (fortversion, string.lower(arraytype), num, 
                         string.lower(suffix), string.upper(arraytype), num, 
                         string.upper(suffix), arraytype, num,
                         string.lower(suffix)))
  for i in range(1, num+1):
    output.write("   int32_t *i%d,\n" % (i))
  output.write( """   SIDL_F%s_Bool *value)
{
  %s tmp = ((*value == SIDL_F%s_TRUE) ? TRUE : FALSE);
  sidl_%s__array_set%d((struct sidl_%s__array *)(ptrdiff_t)*array,
"""  % (fortversion,
      datatype, fortversion, arraytype, num, arraytype))
  for i in (range(1, num+1)):
    output.write( "   *i%d,\n" % i)
  output.write( """   tmp);
}
""")


#----------------------------------------------------------------
def boolgenericset(arraytype, datatype, casttype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_set_%s,
                    SIDL_%s__ARRAY_SET_%s,
                    sidl_%s__array_set_%s)
  (int64_t *array, int32_t indices[], SIDL_F%s_Bool *value)
{
  %s tmp = ((*value == SIDL_F%s_TRUE) ? TRUE : FALSE);
  sidl_%s__array_set((struct sidl_%s__array *)(ptrdiff_t)*array, indices, tmp);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
        string.upper(arraytype), string.upper(suffix), arraytype,
        string.lower(suffix), fortversion, datatype,
        fortversion, arraytype, arraytype))


#----------------------------------------------------------------
def isColumnOrder(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_iscolumnorder_%s,
                    SIDL_%s__ARRAY_ISCOLUMNORDER_%s,
                    sidl_%s__array_isColumnOrder_%s)
  (int64_t *array, SIDL_F%s_Bool *result)
{
  *result = (
    sidl_%s__array_isColumnOrder((struct sidl_%s__array *)(ptrdiff_t)*array)
    ? SIDL_F%s_TRUE : SIDL_F%s_FALSE);
}
""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), fortversion,
       arraytype, arraytype, fortversion, fortversion))


#----------------------------------------------------------------
def isRowOrder(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_isroworder_%s,
                    SIDL_%s__ARRAY_ISROWORDER_%s,
                    sidl_%s__array_isRowOrder_%s)
  (int64_t *array, SIDL_F%s_Bool *result)
{
  *result = (
    sidl_%s__array_isRowOrder((struct sidl_%s__array *)(ptrdiff_t)*array)
    ? SIDL_F%s_TRUE : SIDL_F%s_FALSE);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), fortversion,
       arraytype, arraytype, fortversion, fortversion))


#----------------------------------------------------------------
def cast_f77(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_cast_%s,
                    SIDL_%s__ARRAY_CAST_%s,
                    sidl_%s__array_cast_%s)
  (int64_t *array, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_cast((struct sidl__array *)(ptrdiff_t)*array);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype))

def cast_f90(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_cast_%s,
                    SIDL_%s__ARRAY_CAST_%s,
                    sidl_%s__array_cast_%s)
  (int64_t *array, int32_t *dimen, int64_t *result)
{
  struct sidl_%s__array *tmp =
    sidl_%s__array_cast((struct sidl__array *)(ptrdiff_t)*array);
  *result = ((tmp && (sidlArrayDim(tmp) == *dimen)) ? (ptrdiff_t)tmp : 0);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))

def cast_f03(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran90Symbol(sidl_%s__array_bindc_cast_%s,
                    SIDL_%s__ARRAY_BINDC_CAST_%s,
                    sidl_%s__array_bindc_cast_%s)
  (struct sidl__array **array, int32_t *dimen, struct sidl_fortran2003_array *result)
{
  struct sidl_%s__array *tmp = sidl_%s__array_cast(*array);
  if(tmp && sidlArrayDim(tmp) != *dimen) tmp = NULL;
  (void) sidl_%s__array_convert2f03(tmp, *dimen, result);
}

""" % (string.lower(arraytype), string.lower(suffix),
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), arraytype, arraytype, arraytype))

  
def castRaw(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_cast_%s,
                    SIDL_%s__ARRAY_CAST_%s,
                    sidl_%s__array_cast_%s)
  (int64_t *array, int32_t *dimen, struct sidl_fortran_array *result)
{
  (void)sidl_%s__array_convert2f90(
    sidl_%s__array_cast((struct sidl__array *)(ptrdiff_t)*array),
    *dimen, result);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def dimen(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_dimen_%s,
                    SIDL_%s__ARRAY_DIMEN_%s,
                    sidl_%s__array_dimen_%s)
  (int64_t *array, int32_t *result)
{
  *result =
    sidl_%s__array_dimen((struct sidl_%s__array *)(ptrdiff_t)*array);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def stride(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_stride_%s,
                    SIDL_%s__ARRAY_STRIDE_%s,
                    sidl_%s__array_stride_%s)
  (int64_t *array, int32_t *index, int32_t *result)
{
  *result =
    sidl_%s__array_stride((struct sidl_%s__array *)(ptrdiff_t)*array, *index);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def getArrayType(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_type_%s,
                    SIDL_%s__ARRAY_TYPE_%s,
                    sidl_%s__array_type_%s)
  (int64_t *array, int32_t *result)
{
  *result =
    sidl_%s__array_type((struct sidl_%s__array *)(ptrdiff_t)*array);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))

#----------------------------------------------------------------
def lower(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_lower_%s,
                    SIDL_%s__ARRAY_LOWER_%s,
                    sidl_%s__array_lower_%s)
  (int64_t *array, int32_t*ind, int32_t *result)
{
  *result =
    sidl_%s__array_lower((struct sidl_%s__array *)(ptrdiff_t)*array, *ind);
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def upper(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_upper_%s,
                    SIDL_%s__ARRAY_UPPER_%s,
                    sidl_%s__array_upper_%s)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_%s__array_upper((struct sidl_%s__array *)(ptrdiff_t)*array, *ind);
}
""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def length(arraytype, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_%s__array_length_%s,
                    SIDL_%s__ARRAY_LENGTH_%s,
                    sidl_%s__array_length_%s)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_%s__array_length((struct sidl_%s__array *)(ptrdiff_t)*array, *ind);
}
""" % (fortversion, string.lower(arraytype), string.lower(suffix),
   string.upper(arraytype), string.upper(suffix), arraytype, 
   string.lower(suffix), arraytype, arraytype))


#----------------------------------------------------------------
def raw(arraytype, datatype, fortversion, suffix):
  output.write( """
/**
 * NOTE: THERE IS THE POTENTIAL FOR POINTER ALIGNMENT PROBLEMS WITH
 * THIS FUNCTION.  If index is zero on return, the array pointer
 * and ref are not properly aligned!
 */
void
SIDLFortran%sSymbol(sidl_%s__array_access_%s,
                    SIDL_%s__ARRAY_ACCESS_%s,
                    sidl_%s__array_access_%s)
  (int64_t *array,
   %s *ref,
   int32_t * restrict lower,
   int32_t * restrict upper,
   int32_t * restrict stride,
   int64_t * restrict index)
{
  struct sidl_%s__array *ary = (struct sidl_%s__array *)
    (ptrdiff_t)*array;
  const int32_t dimen = sidlArrayDim(ary);
  int32_t i;
  for(i = 0; i < dimen; ++i){
    lower[i] = sidlLower(ary, i);
    upper[i] = sidlUpper(ary, i);
    stride[i] = sidlStride(ary, i);
  }
  if (((((ptrdiff_t)(ary->d_firstElement)) -
        ((ptrdiff_t)ref)) %% sizeof(%s)) == 0) {
    *index = ((%s *)(ary->d_firstElement) - ref) + 1;
  }
  else {
    *index = 0;
  }
}

""" % (fortversion, string.lower(arraytype), string.lower(suffix), 
       string.upper(arraytype), string.upper(suffix), arraytype, 
       string.lower(suffix), datatype, arraytype, arraytype, datatype, datatype))


#----------------------------------------------------------------
def stringget(num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_string__array_get%d_%s,
                    SIDL_STRING__ARRAY_GET%d_%s,
                    sidl_string__array_get%d_%s)
  (int64_t *array,""" % ( fortversion, num, string.lower(suffix), num, 
                          string.upper(suffix), num, string.lower(suffix)))
  for i in range(1, num+1):
    output.write( "   int32_t *i%d,\n" % (i))
  output.write( """   SIDL_F%s_String result
   SIDL_F%s_STR_NEAR_LEN_DECL(result)
   SIDL_F%s_STR_FAR_LEN_DECL(result))
{
  char *tmp =
    sidl_string__array_get%d((struct sidl_string__array *)(ptrdiff_t)
	                     *array,
"""  % ( fortversion, fortversion, 
                                             fortversion, num, ))
  for i in (range(1, num)):
    output.write( "     *i%d,\n" % i)
  output.write( """     *i%d);
  sidl_copy_c_str(
    SIDL_F%s_STR(result),
    (size_t)SIDL_F%s_STR_LEN(result),
    tmp);
  free((void *)tmp);
}

""" % (num, fortversion, fortversion))


#----------------------------------------------------------------
def stringgenericget(fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_string__array_get_%s,
                    SIDL_STRING__ARRAY_GET_%s,
                    sidl_string__array_get_%s)
  (int64_t *array, int32_t indices[], SIDL_F%s_String value
   SIDL_F%s_STR_NEAR_LEN_DECL(value)
   SIDL_F%s_STR_FAR_LEN_DECL(value))
{
  char *tmp =
     sidl_string__array_get((struct sidl_string__array *)(ptrdiff_t)
                            *array, indices);
  sidl_copy_c_str(
    SIDL_F%s_STR(value),
    (size_t)SIDL_F%s_STR_LEN(value),
    tmp);
  free((void *)tmp);
}
""" % (fortversion, string.lower(suffix), string.upper(suffix), 
        string.lower(suffix), fortversion, fortversion, fortversion,
        fortversion, fortversion))


#----------------------------------------------------------------
def stringset(num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_string__array_set%d_%s,
                    SIDL_STRING__ARRAY_SET%d_%s,
                    sidl_string__array_set%d_%s)
  (int64_t *array,
""" % (fortversion, num, string.lower(suffix), num, 
                         string.upper(suffix), num, string.lower(suffix)))
  for i in range(1, num+1):
    output.write( "   int32_t *i%d,\n" % (i))
  output.write( """   SIDL_F%s_String value
   SIDL_F%s_STR_NEAR_LEN_DECL(value)
   SIDL_F%s_STR_FAR_LEN_DECL(value))
{
  char *tmp = sidl_copy_fortran_str(SIDL_F%s_STR(value),
                                    (ptrdiff_t)SIDL_F%s_STR_LEN(value));
  sidl_string__array_set%d((struct sidl_string__array *)(ptrdiff_t)
                           *array,
"""  % (fortversion, fortversion, fortversion,
                                          fortversion, fortversion, num , ))
  for i in (range(1, num+1)):
    output.write( "   *i%d,\n" % i)
  output.write( """   tmp);
  free(tmp);
}
""" )


#----------------------------------------------------------------
def stringgenericset(fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_string__array_set_%s,
                    SIDL_STRING__ARRAY_SET_%s,
                    sidl_string__array_set_%s)
  (int64_t *array, int32_t indices[], 
   SIDL_F%s_String value
   SIDL_F%s_STR_NEAR_LEN_DECL(value)
   SIDL_F%s_STR_FAR_LEN_DECL(value))
{
  char *tmp = sidl_copy_fortran_str(SIDL_F%s_STR(value),
                                    (ptrdiff_t)SIDL_F%s_STR_LEN(value));
  sidl_string__array_set((struct sidl_string__array *)(ptrdiff_t)
                     *array, indices, tmp);
  free(tmp);
}
""" % (fortversion, string.lower(suffix), string.upper(suffix), 
        string.lower(suffix), fortversion, fortversion, fortversion, 
        fortversion, fortversion))


#----------------------------------------------------------------
def charget(num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_char__array_get%d_%s,
                    SIDL_CHAR__ARRAY_GET%d_%s,
                    sidl_char__array_get%d_%s)
  (int64_t *array,
""" % ( fortversion, num, string.lower(suffix), num, 
                          string.upper(suffix), num, string.lower(suffix) ))
  for i in range(1, num+1):
    output.write("   int32_t *i%d,\n" % (i))
  output.write( """#ifdef SIDL_F%s_CHAR_AS_STRING
   SIDL_F%s_String result
   SIDL_F%s_STR_NEAR_LEN_DECL(result)
   SIDL_F%s_STR_FAR_LEN_DECL(result)
#else
   char *result;
#endif
   )
{
#ifdef SIDL_F%s_CHAR_AS_STRING
  *SIDL_F%s_STR(result) =
#else
  *result =
#endif
    sidl_char__array_get%d((struct sidl_char__array *)(ptrdiff_t)
	                     *array,
"""  % ( fortversion, fortversion, 
                                             fortversion, fortversion,
                                             fortversion, fortversion, num, ))
  for i in (range(1, num)):
    output.write( "     *i%d,\n" % i)
  output.write( """     *i%d);
}
""" % num)


#----------------------------------------------------------------
def chargenericget(fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_char__array_get_%s,
                    SIDL_CHAR__ARRAY_GET_%s,
                    sidl_char__array_get_%s)
  (int64_t *array, int32_t indices[],
#ifdef SIDL_F%s_CHAR_AS_STRING
   SIDL_F%s_String result
   SIDL_F%s_STR_NEAR_LEN_DECL(result)
   SIDL_F%s_STR_FAR_LEN_DECL(result)
#else
   char *result
#endif
   )
{
#ifdef SIDL_F%s_CHAR_AS_STRING
  *SIDL_F%s_STR(result) =
#else
  *result =
#endif
     sidl_char__array_get((struct sidl_char__array *)(ptrdiff_t)
                            *array, indices);
}
""" % (fortversion, string.lower(suffix), string.upper(suffix), 
        string.lower(suffix), fortversion, fortversion, fortversion,
        fortversion, fortversion, fortversion))


#----------------------------------------------------------------
def charset(num, fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_char__array_set%d_%s,
                    SIDL_CHAR__ARRAY_SET%d_%s,
                    sidl_char__array_set%d_%s)
  (int64_t *array,
""" % (fortversion, num, string.lower(suffix), num, 
                         string.upper(suffix), num, string.lower(suffix)))
  for i in range(1, num+1):
    output.write( "   int32_t *i%d,\n" % (i))
  output.write( """#ifdef SIDL_F%s_CHAR_AS_STRING
   SIDL_F%s_String value
   SIDL_F%s_STR_NEAR_LEN_DECL(value)
   SIDL_F%s_STR_FAR_LEN_DECL(value)
#else
   char *value
#endif
   )
{
  char tmp =
#ifdef SIDL_F%s_CHAR_AS_STRING
   *SIDL_F%s_STR(value);
#else
   *value;
#endif
  sidl_char__array_set%d((struct sidl_char__array *)(ptrdiff_t)
                           *array,
"""  % (fortversion, fortversion, fortversion,
                                          fortversion, fortversion, fortversion,
                                          num , ))
  for i in (range(1, num+1)):
    output.write( "   *i%d,\n" % i)
  output.write( """   tmp);
}
""")


#----------------------------------------------------------------
def chargenericset(fortversion, suffix):
  output.write( """
void
SIDLFortran%sSymbol(sidl_char__array_set_%s,
                    SIDL_CHAR__ARRAY_SET_%s,
                    sidl_char__array_set_%s)
  (int64_t *array, int32_t indices[], 
#ifdef SIDL_F%s_CHAR_AS_STRING
   SIDL_F%s_String value
   SIDL_F%s_STR_NEAR_LEN_DECL(value)
   SIDL_F%s_STR_FAR_LEN_DECL(value)
#else
   char *value
#endif
   )
{
  sidl_char__array_set((struct sidl_char__array *)(ptrdiff_t)
                     *array, indices,
#ifdef SIDL_F%s_CHAR_AS_STRING
  *SIDL_F%s_STR(value)
#else
  *value
#endif
   );
}
""" % (fortversion, string.lower(suffix), string.upper(suffix), 
        string.lower(suffix), fortversion, fortversion, fortversion, 
        fortversion, fortversion, fortversion))

def writeLine(file, fullName, abbrev):
  file.write("#define ")
  file.write(fullName)
  file.write(" ")
  file.write(abbrev)
  file.write("\n")

def writeEntry(file, fullName, abbrev):
  monocase = fullName.lower()
  writeLine(file, fullName, abbrev)
  if (monocase != fullName):
    writeLine(file, monocase, abbrev.lower())
  monocase = fullName.upper()
  if (monocase != fullName):
    writeLine(file, monocase, abbrev.upper())
  file.write("\n")

def writeHeader(type, elementType, fortversion):
  output.write( """/*********************************************************************
* File:        sidl_%s_fStub.c
* Copyright:   (C) 2001-2004 Lawrence Livermore National Security, LLC
* Description: FORTRAN API for arrays of %s
* Revision:    $Revision: 7188 $
*
* AUTOMATICALLY GENERATED BY genfortranarrays.py
**********************************************************************/

#include "sidl_header.h"
#ifndef included_babel_config_h
#include "babel_config.h"
#endif
#include "sidlfortran.h"
#ifndef included_sidlType_h
#include "sidlType.h"
#endif
#ifndef included_sidlArray_h
#include "sidlArray.h"
#endif
""" % (type, elementType))
  if (fortversion == "90"):
    output.write("""#ifndef FORTRAN90_DISABLED
#include "sidlf90array.h"
#endif
""")
  if (fortversion == "03"):
    output.write("""#ifndef FORTRAN03_DISABLED
#include "sidlf03array.h"
#endif
""")
  output.write("""#include <stdlib.h>
#include <stddef.h>
""")

def writeAbbrevFile(type, fortversion, suffix):
  methods = [ 'createCol', 'createRow', 'create1d', 'create2dRow',
              'create2dCol', 'copy', 'ensure', 'smartCopy',
              'slice', 'deleteRef', 'addRef',
              'get1', 'get2', 'get3', 'get4', 'get',
              'set1', 'set2', 'set3', 'set4', 'set',
              'isColumnOrder', 'isRowOrder', 'dimen',
              'stride', 'lower', 'upper', 'length', 'cast' ]
  if (not (type in limited)):
    methods = methods + [ 'borrow', 'cborrow', 'access' ]
  methods.sort()

  filename = "f" + fortversion + "/sidl_" + type + "_fAbbrev.h"
            
  file = open(filename, "w")
  if (fortversion == "03"):
    # must agree with gov.llnl.babel.backend.fortran.AbbrevHeader.java
    __maxLength = 63
    __maxUnmangled = 31
    file.write("""#ifdef __FORTRAN03__
#define sidl_%s_array sidl_%s_array_F03
#endif /*__FORTRAN03__*/
""" % (type, type))
  else:
    # must agree with gov.llnl.babel.backend.fortran.AbbrevHeader.java
    __maxLength = 31
    __maxUnmangled = 21
  
  
  symbol = "sidl." + type
  for m in methods:
    fullName = longName(symbol, m, suffix)
    if (len(fullName) > __maxLength):
      abbrev = shortArrayName(symbol, m, suffix)
      writeEntry(file, fullName, abbrev)
  file.close()
  file = None

if __name__ == '__main__':

  types = { 'bool'     : 'sidl_bool',
            'dcomplex' : 'struct sidl_dcomplex',
            'fcomplex' : 'struct sidl_fcomplex',
	    'double'   : 'double',
            'float'    : 'float',
            'int'      : 'int32_t',
            'long'     : 'int64_t',
            'opaque'   : 'int64_t'}
  reftypes = { 'bool'     : 'sidl_bool',
               'dcomplex' : 'double',
               'fcomplex' : 'struct sidl_fcomplex',
               'float'    : 'float',
               'double'   : 'double',
               'int'      : 'int32_t',
               'long'     : 'int64_t',
               'opaque'   : 'int64_t'}
  getcast = { 'bool'     : '',
              'dcomplex' : '',
              'fcomplex' : '',
              'float'    : '',
              'double'   : '',
              'int'      : '',
              'long'     : '',
              'opaque'   : '(ptrdiff_t)'}
  setcast = { 'bool'     : '',
              'dcomplex' : '',
              'fcomplex' : '',
              'float'    : '',
              'double'   : '',
              'int'      : '',
              'long'     : '',
              'opaque'   : '(void *)(ptrdiff_t)'}
  chartypes = { 'char'  : 'char',
                'string': 'char' }
  flavors = { '77' : 'f',
              '77_31' : 'f',
              '90' : 'm' ,
              '03' : 'c' }
  simpleversion = { '77' : '77',
                    '77_31' : '77',
                    '90' : '90',
                    '03' : '03'}
  alltypes = types.keys() + chartypes.keys()
  alltypes.sort();
  for type in alltypes:
    writeAbbrevFile(type, '03', '_c')
    writeAbbrevFile(type, '90', '_m')
    writeAbbrevFile(type, '77_31', '_f')

  keys = types.keys()
  keys.sort()
  for type in keys:
    fkeys = flavors.keys()
    fkeys.sort()
    for fortversion in fkeys:
      output = open("f" + fortversion + "/sidl_" + type + "_fStub.c", "w")
      simple = simpleversion[fortversion]
      writeHeader(type, type, fortversion)
      output.write("""

#ifndef FORTRAN%s_DISABLED
/*---------------------------------------------------------------------*
 * Fortran %s Array Routines
 *---------------------------------------------------------------------*/

""" % (simple, fortversion))
      if (fortversion != "77"):
        output.write( '#include "sidl_' + type + '_fAbbrev.h"\n')

      if (fortversion == "03"):
        if (not (type in limited)):
          cborrowRawF03(type, types[type], simple, flavors[fortversion])
          cast_f03(type, simple, flavors[fortversion])
      else:
        if ((fortversion == "90") and not (type in limited)):
          createColRaw(type, simple, flavors[fortversion])
          createRowRaw(type, simple, flavors[fortversion])
          create1dRaw(type, simple, flavors[fortversion])
          createRow2dRaw(type, simple, flavors[fortversion])
          createCol2dRaw(type, simple, flavors[fortversion])
          ensureArrayRaw(type, simple, flavors[fortversion])
          smartCopyArrayRaw(type, simple, flavors[fortversion])
          sliceRaw(type, simple, flavors[fortversion])
          castRaw(type, simple, flavors[fortversion])
        else:
          createCol(type, simple, flavors[fortversion])
          createRow(type, simple, flavors[fortversion])
          create1d(type, simple, flavors[fortversion])
          createRow2d(type, simple, flavors[fortversion])
          createCol2d(type, simple, flavors[fortversion])
          ensureArray(type, simple, flavors[fortversion])
          smartCopyArray(type, simple, flavors[fortversion])
          slice(type, simple, flavors[fortversion])
          if (fortversion == "90"):
            cast_f90(type, simple, flavors[fortversion])
          else:
            cast_f77(type, simple, flavors[fortversion])
        copyArray(type, simple, flavors[fortversion])
        isColumnOrder(type, simple, flavors[fortversion])
        isRowOrder(type, simple, flavors[fortversion])
        dimen(type, simple, flavors[fortversion])
        stride(type, simple, flavors[fortversion])
        lower(type, simple, flavors[fortversion])
        upper(type, simple, flavors[fortversion])
        length(type, simple, flavors[fortversion])
        destroy(type, simple, flavors[fortversion])
        addRef(type, simple, flavors[fortversion])
        if (not (type in limited)):
          if (fortversion == "90"):
            borrowRaw(type, types[type], simple, flavors[fortversion])
            cborrowRaw(type, types[type], simple, flavors[fortversion])
          else:
            borrow(type, types[type], simple, flavors[fortversion])
            cborrow(type, types[type], simple, flavors[fortversion])
          raw(type, reftypes[type], simple, flavors[fortversion])
        if (type == 'bool'):
          for j in range(1, 8):
            boolget(type, types[type], getcast[type], j, simple, 
                flavors[fortversion])
            boolset(type, types[type], setcast[type], j, simple, 
                flavors[fortversion])
          boolgenericset(type, types[type], setcast[type], simple, 
                     flavors[fortversion])
          boolgenericget(type, types[type], getcast[type], simple, 
                     flavors[fortversion])
        else:
          for j in range(1, 8):
            get(type, types[type], getcast[type], j, simple, 
                flavors[fortversion])
            set(type, types[type], setcast[type], j, simple, 
                flavors[fortversion])
          genericset(type, types[type], setcast[type], simple, 
                     flavors[fortversion])
          genericget(type, types[type], getcast[type], simple, 
                     flavors[fortversion])
      output.write( """

#endif /* not FORTRAN%s_DISABLED */
""" % (simple))
      output.close()
    
  keys = chartypes.keys()
  keys.sort()
  for type in keys:
    fkeys = flavors.keys()
    fkeys.sort()
    for fortversion in fkeys:
      output = open("f" + fortversion + "/sidl_" + type + "_fStub.c", "w")
      simple = simpleversion[fortversion]
      writeHeader(type, type, fortversion)
      output.write("""

#ifndef FORTRAN%s_DISABLED
/*---------------------------------------------------------------------*
 * Fortran %s Array Routines
 *---------------------------------------------------------------------*/

""" % (simple, fortversion))
      if (fortversion != "77"):
        output.write( '#include "sidl_' + type + '_fAbbrev.h"\n')

      if (fortversion != "03"):
        createCol(type, simple, flavors[fortversion])
        createRow(type, simple, flavors[fortversion])
        create1d(type, simple, flavors[fortversion])
        createRow2d(type, simple, flavors[fortversion])
        createCol2d(type, simple, flavors[fortversion])
        copyArray(type, simple, flavors[fortversion])
        ensureArray(type, simple, flavors[fortversion])
        smartCopyArray(type, simple, flavors[fortversion])
        slice(type, simple, flavors[fortversion])
        if (fortversion == "90"):
          cast_f90(type, simple, flavors[fortversion])
        else:
          cast_f77(type, simple, flavors[fortversion])
        isColumnOrder(type, simple, flavors[fortversion])
        isRowOrder(type, simple, flavors[fortversion])
        dimen(type, simple, flavors[fortversion])
        stride(type, simple, flavors[fortversion])
        lower(type, simple, flavors[fortversion])
        upper(type, simple, flavors[fortversion])
        length(type, simple, flavors[fortversion])
        destroy(type, simple, flavors[fortversion])
        addRef(type, simple, flavors[fortversion])
        if type == "string":
          for j in range(1, 8):
            stringget(j, simple, flavors[fortversion])
            stringset(j, simple, flavors[fortversion])
          stringgenericget(simple, flavors[fortversion])
          stringgenericset(simple, flavors[fortversion])
        else:
          for j in range(1, 8):
            charget(j, simple, flavors[fortversion])
            charset(j, simple, flavors[fortversion])
          chargenericget(simple, flavors[fortversion])
          chargenericset(simple, flavors[fortversion])
      output.write( """

#endif /* not FORTRAN%s_DISABLED */
""" % (simple))
      output.close()

  fkeys = flavors.keys()
  fkeys.sort()
  for fortversion in fkeys:
    output = open("f" + fortversion + "/sidl_array_fStub.c", "w")
    simple = simpleversion[fortversion]
    writeHeader("array", "unspecified elements", fortversion)
    output.write("""

#ifndef FORTRAN%s_DISABLED
/*---------------------------------------------------------------------*
 * Fortran %s Array Routines
 *---------------------------------------------------------------------*/

""" % (simple, fortversion))
    type = ''
    if (fortversion != "03"):
      getArrayType(type, simple, flavors[fortversion])
      smartCopyArray(type, simple, flavors[fortversion])
      isColumnOrder(type, simple, flavors[fortversion])
      isRowOrder(type, simple, flavors[fortversion])
      dimen(type, simple, flavors[fortversion])
      stride(type, simple, flavors[fortversion])
      lower(type, simple, flavors[fortversion])
      upper(type, simple, flavors[fortversion])
      length(type, simple, flavors[fortversion])
      destroy(type, simple, flavors[fortversion])
      addRef(type, simple, flavors[fortversion])
    output.write( """

#endif /* not FORTRAN%s_DISABLED */
""" % (simple))
    output.close()
    # fix up the number of underbars
    input = open("f" + fortversion + "/sidl_array_fStub.c", "r")
    contents = input.read()
    input.close()
    output = open("f" + fortversion + "/sidl_array_fStub.c", "w")
    output.write(contents.replace('___', '__'))
    output.close()
    output = open("f" + fortversion + "/sidl_array_fAbbrev.h", "w")
    if (fortversion == "03"):
      output.write("""#ifdef __FORTRAN03__
#define sidl_array_array sidl_array_array_F03
#endif /*__FORTRAN03__*/
""")
    output.close()
    contents = None
