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
import string

def createCol(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_createcol_f,
                  sidl_%s__ARRAY_CREATECOL_F,
                  sidl_%s__array_createCol_f)
  (int32_t *dimen, int32_t lower[], int32_t upper[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_createCol(*dimen, lower, upper);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype)

def createRow(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_createrow_f,
                  sidl_%s__ARRAY_CREATEROW_F,
                  sidl_%s__array_createRow_f)
  (int32_t *dimen, int32_t lower[], int32_t upper[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_createRow(*dimen, lower, upper);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype)

def create1d(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_create1d_f,
                  sidl_%s__ARRAY_CREATE1D_F,
                  sidl_%s__array_create1d_f)
  (int32_t *len, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_create1d(*len);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype)

def createRow2d(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_create2drow_f,
                  sidl_%s__ARRAY_CREATE2DROW_F,
                  sidl_%s__array_create2dRow_f)
  (int32_t *m, int32_t *n, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_create2dRow(*m, *n);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype)

def createCol2d(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_create2dcol_f,
                  sidl_%s__ARRAY_CREATE2DCOL_F,
                  sidl_%s__array_create2dCol_f)
  (int32_t *m, int32_t *n, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_create2dCol(*m, *n);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype)

def copyArray(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_copy_f,
                  sidl_%s__ARRAY_COPY_F,
                  sidl_%s__array_copy_f)
  (int64_t *src, int64_t *dest)
{
  sidl_%s__array_copy((struct sidl_%s__array*)(ptrdiff_t)*src,
                      (struct sidl_%s__array*)(ptrdiff_t)*dest);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype, arraytype, arraytype)

def ensureArray(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_ensure_f,
                  sidl_%s__ARRAY_ENSURE_F,
                  sidl_%s__array_ensure_f)
  (int64_t *src, int32_t *dimen, int32_t *ordering, int64_t *result)
{
  *result = (ptrdiff_t)
     sidl_%s__array_ensure((struct sidl_%s__array*)(ptrdiff_t)*src,
                           *dimen, (int)*ordering);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype, arraytype)

def smartCopyArray(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_smartcopy_f,
                  sidl_%s__ARRAY_SMARTCOPY_F,
                  sidl_%s__array_smartCopy_f)
  (int64_t *src, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_smartCopy((struct sidl_%s__array*)(ptrdiff_t)*src);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype, arraytype)

def slice(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_slice_f,
                  sidl_%s__ARRAY_SLICE_F,
                  sidl_%s__array_slice_f)
  (int64_t *src, int32_t *dimen, int32_t numElem[], int32_t srcStart[],
   int32_t srcStride[], int32_t newStart[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_slice((struct sidl_%s__array *)(ptrdiff_t)*src,
                         *dimen, numElem, srcStart, srcStride, newStart);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype, arraytype)

def borrow(arraytype,datatype):
  print """
void
sidlFortranSymbol(sidl_%s__array_borrow_f,
                  sidl_%s__ARRAY_BORROW_F,
                  sidl_%s__array_borrow_f)
  (%s *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_%s__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, datatype, arraytype)

def destroy(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_deleteref_f,
                  sidl_%s__ARRAY_DELETEREF_F,
                  sidl_%s__array_deleteRef_f)
  (int64_t *array)
{
  sidl_%s__array_deleteRef((struct sidl_%s__array*)(ptrdiff_t)*array);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype, arraytype)

def addRef(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_addref_f,
                  sidl_%s__ARRAY_ADDREF_F,
                  sidl_%s__array_addRef_f)
  (int64_t *array)
{
  sidl_%s__array_addRef((struct sidl_%s__array*)(ptrdiff_t)*array);
}
""" % (string.lower(arraytype),
       string.upper(arraytype), arraytype, arraytype, arraytype)

def get(arraytype, datatype, casttype, num):
  print """
void
sidlFortranSymbol(sidl_%s__array_get%d_f,
                  sidl_%s__ARRAY_GET%d_F,
                  sidl_%s__array_get%d_f)
  (int64_t *array,""" % (string.lower(arraytype), num,
   string.upper(arraytype), num, arraytype, num)
  for i in range(1, num+1):
    print "   int32_t *i%d," % (i)
  print """   %s *result)
{
  *result = %s
    sidl_%s__array_get%d((struct sidl_%s__array *)(ptrdiff_t)*array,"""  % (datatype,
      casttype, arraytype, num, arraytype)
  for i in (range(1, num)):
    print "     *i%d," % i
  print """     *i%d);
}""" % num

def genericget(arraytype, datatype, casttype):
  print """
void
sidlFortranSymbol(sidl_%s__array_get_f,
                  sidl_%s__ARRAY_GET_F,
                  sidl_%s__array_get_f)
  (int64_t *array, int32_t indices[], %s *value)
{
  *value = %s
     sidl_%s__array_get((struct sidl_%s__array *)(ptrdiff_t)*array, indices);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, datatype, casttype, arraytype,
        arraytype)


def set(arraytype, datatype, casttype, num):
  print """
void
sidlFortranSymbol(sidl_%s__array_set%d_f,
                  sidl_%s__ARRAY_SET%d_F,
                  sidl_%s__array_set%d_f)
  (int64_t *array,""" % (string.lower(arraytype), num,
   string.upper(arraytype), num, arraytype, num)
  for i in range(1, num+1):
    print "   int32_t *i%d," % (i)
  print """   %s *value)
{
  sidl_%s__array_set%d((struct sidl_%s__array *)(ptrdiff_t)*array,"""  % (datatype,
      arraytype, num, arraytype)
  for i in (range(1, num+1)):
    print "   *i%d," % i
  print """   %s*value);
}""" % casttype

def genericset(arraytype, datatype, casttype):
  print """
void
sidlFortranSymbol(sidl_%s__array_set_f,
                  sidl_%s__ARRAY_SET_F,
                  sidl_%s__array_set_f)
  (int64_t *array, int32_t indices[], %s *value)
{
  sidl_%s__array_set((struct sidl_%s__array *)(ptrdiff_t)*array, indices, %s *value);
}""" % (string.lower(arraytype),
        string.upper(arraytype), arraytype, datatype, arraytype, arraytype,
        casttype)

def isColumnOrder(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_iscolumnorder_f,
                  sidl_%s__ARRAY_ISCOLUMNORDER_F,
                  sidl_%s__array_isColumnOrder_f)
  (int64_t *array, sidl_bool *result)
{
  *result = (
    sidl_%s__array_isColumnOrder((struct sidl_%s__array *)(ptrdiff_t)*array)
    ? SIDL_F77_TRUE : SIDL_F77_FALSE);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, arraytype, arraytype)

def isRowOrder(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_isroworder_f,
                  sidl_%s__ARRAY_ISROWORDER_F,
                  sidl_%s__array_isRowOrder_f)
  (int64_t *array, sidl_bool *result)
{
  *result = (
    sidl_%s__array_isRowOrder((struct sidl_%s__array *)(ptrdiff_t)*array)
    ? SIDL_F77_TRUE : SIDL_F77_FALSE);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, arraytype, arraytype)

def dimen(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_dimen_f,
                  sidl_%s__ARRAY_DIMEN_F,
                  sidl_%s__array_dimen_f)
  (int64_t *array, int32_t *result)
{
  *result =
    sidl_%s__array_dimen((struct sidl_%s__array *)(ptrdiff_t)*array);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, arraytype, arraytype)

def stride(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_stride_f,
                  sidl_%s__ARRAY_STRIDE_F,
                  sidl_%s__array_stride_f)
  (int64_t *array, int32_t *index, int32_t *result)
{
  *result =
    sidl_%s__array_stride((struct sidl_%s__array *)(ptrdiff_t)*array, *index);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, arraytype, arraytype)

def lower(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_lower_f,
                  sidl_%s__ARRAY_LOWER_F,
                  sidl_%s__array_lower_f)
  (int64_t *array, int32_t*ind, int32_t *result)
{
  *result =
    sidl_%s__array_lower((struct sidl_%s__array *)(ptrdiff_t)*array, *ind);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, arraytype, arraytype)

def upper(arraytype):
  print """
void
sidlFortranSymbol(sidl_%s__array_upper_f,
                  sidl_%s__ARRAY_UPPER_F,
                  sidl_%s__array_upper_f)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_%s__array_upper((struct sidl_%s__array *)(ptrdiff_t)*array, *ind);
}""" % (string.lower(arraytype),
   string.upper(arraytype), arraytype, arraytype, arraytype)

def raw(arraytype, datatype):
  print """
/**
 * NOTE: THERE IS THE POTENTIAL FOR POINTER ALIGNMENT PROBLEMS WITH
 * THIS FUNCTION.  If index is zero on return, the array pointer
 * and ref are not properly aligned!
 */
void
sidlFortranSymbol(sidl_%s__array_access_f,
                  sidl_%s__ARRAY_ACCESS_F,
                  sidl_%s__array_access_f)
  (int64_t *array,
   %s *ref,
   int32_t * restrict lower,
   int32_t * restrict upper,
   int32_t * restrict stride,
   int32_t * restrict index)
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
    *index = ((%s *)ary->d_firstElement - ref) + 1;
  }
  else {
    *index = 0;
  }
}
""" % (string.lower(arraytype), string.upper(arraytype), arraytype,
       datatype, arraytype, arraytype, datatype, datatype)

def stringget(num):
  print """
void
sidlFortranSymbol(sidl_string__array_get%d_f,
                  sidl_STRING__ARRAY_GET%d_F,
                  sidl_string__array_get%d_f)
  (int64_t *array,""" % ( num, num, num )
  for i in range(1, num+1):
    print "   int32_t *i%d," % (i)
  print """   SIDL_F77_String result
   SIDL_F77_STR_NEAR_LEN_DECL(result)
   SIDL_F77_STR_FAR_LEN_DECL(result))
{
  char *tmp =
    sidl_string__array_get%d((struct sidl_string__array *)(ptrdiff_t)
	                     *array,"""  % ( num, )
  for i in (range(1, num)):
    print "     *i%d," % i
  print """     *i%d);
  sidl_copy_c_str(
    SIDL_F77_STR(result),
    SIDL_F77_STR_LEN(result),
    tmp);
  free((void *)tmp);
}""" % num

def stringgenericget():
  print """
void
sidlFortranSymbol(sidl_string__array_get_f,
                  sidl_STRING__ARRAY_GET_F,
                  sidl_string__array_get_f)
  (int64_t *array, int32_t indices[], SIDL_F77_String value
   SIDL_F77_STR_NEAR_LEN_DECL(value)
   SIDL_F77_STR_FAR_LEN_DECL(value))
{
  char *tmp =
     sidl_string__array_get((struct sidl_string__array *)(ptrdiff_t)
                            *array, indices);
  sidl_copy_c_str(
    SIDL_F77_STR(value),
    SIDL_F77_STR_LEN(value),
    tmp);
  free((void *)tmp);
}"""


def stringset(num):
  print """
void
sidlFortranSymbol(sidl_string__array_set%d_f,
                  sidl_STRING__ARRAY_SET%d_F,
                  sidl_string__array_set%d_f)
  (int64_t *array,""" % (num, num, num)
  for i in range(1, num+1):
    print "   int32_t *i%d," % (i)
  print """   SIDL_F77_String value
   SIDL_F77_STR_NEAR_LEN_DECL(value)
   SIDL_F77_STR_FAR_LEN_DECL(value))
{
  char *tmp = sidl_copy_f77_str(SIDL_F77_STR(value),
                                SIDL_F77_STR_LEN(value));
  sidl_string__array_set%d((struct sidl_string__array *)(ptrdiff_t)
                           *array,"""  % (num , )
  for i in (range(1, num+1)):
    print "   *i%d," % i
  print """   tmp);
  free(tmp);
}"""

def stringgenericset():
  print """
void
sidlFortranSymbol(sidl_string__array_set_f,
                  sidl_STRING__ARRAY_SET_F,
                  sidl_string__array_set_f)
  (int64_t *array, int32_t indices[], 
   SIDL_F77_String value
   SIDL_F77_STR_NEAR_LEN_DECL(value)
   SIDL_F77_STR_FAR_LEN_DECL(value))
{
  char *tmp = sidl_copy_f77_str(SIDL_F77_STR(value),
                                SIDL_F77_STR_LEN(value));
  sidl_string__array_set((struct sidl_string__array *)(ptrdiff_t)
                     *array, indices, tmp);
  free(tmp);
}"""

def charget(num):
  print """
void
sidlFortranSymbol(sidl_char__array_get%d_f,
                  sidl_CHAR__ARRAY_GET%d_F,
                  sidl_char__array_get%d_f)
  (int64_t *array,""" % ( num, num, num )
  for i in range(1, num+1):
    print "   int32_t *i%d," % (i)
  print """#ifdef SIDL_F77_CHAR_AS_STRING
   SIDL_F77_String result
   SIDL_F77_STR_NEAR_LEN_DECL(result)
   SIDL_F77_STR_FAR_LEN_DECL(result)
#else
   char *result;
#endif
   )
{
#ifdef SIDL_F77_CHAR_AS_STRING
  *SIDL_F77_STR(result) =
#else
  *result =
#endif
    sidl_char__array_get%d((struct sidl_char__array *)(ptrdiff_t)
	                     *array,"""  % ( num, )
  for i in (range(1, num)):
    print "     *i%d," % i
  print """     *i%d);
}""" % num

def chargenericget():
  print """
void
sidlFortranSymbol(sidl_char__array_get_f,
                  sidl_CHAR__ARRAY_GET_F,
                  sidl_char__array_get_f)
  (int64_t *array, int32_t indices[],
#ifdef SIDL_F77_CHAR_AS_STRING
   SIDL_F77_String result
   SIDL_F77_STR_NEAR_LEN_DECL(result)
   SIDL_F77_STR_FAR_LEN_DECL(result)
#else
   char *result
#endif
   )
{
#ifdef SIDL_F77_CHAR_AS_STRING
  *SIDL_F77_STR(result) =
#else
  *result =
#endif
     sidl_char__array_get((struct sidl_char__array *)(ptrdiff_t)
                            *array, indices);
}"""


def charset(num):
  print """
void
sidlFortranSymbol(sidl_char__array_set%d_f,
                  sidl_CHAR__ARRAY_SET%d_F,
                  sidl_char__array_set%d_f)
  (int64_t *array,""" % (num, num, num)
  for i in range(1, num+1):
    print "   int32_t *i%d," % (i)
  print """#ifdef SIDL_F77_CHAR_AS_STRING
   SIDL_F77_String value
   SIDL_F77_STR_NEAR_LEN_DECL(value)
   SIDL_F77_STR_FAR_LEN_DECL(value)
#else
   char *value
#endif
   )
{
  char tmp =
#ifdef SIDL_F77_CHAR_AS_STRING
   *SIDL_F77_STR(value);
#else
   *value;
#endif
  sidl_char__array_set%d((struct sidl_char__array *)(ptrdiff_t)
                           *array,"""  % (num , )
  for i in (range(1, num+1)):
    print "   *i%d," % i
  print """   tmp);
}"""

def chargenericset():
  print """
void
sidlFortranSymbol(sidl_char__array_set_f,
                  sidl_CHAR__ARRAY_SET_F,
                  sidl_char__array_set_f)
  (int64_t *array, int32_t indices[], 
#ifdef SIDL_F77_CHAR_AS_STRING
   SIDL_F77_String value
   SIDL_F77_STR_NEAR_LEN_DECL(value)
   SIDL_F77_STR_FAR_LEN_DECL(value)
#else
   char *value
#endif
   )
{
  sidl_char__array_set((struct sidl_char__array *)(ptrdiff_t)
                     *array, indices,
#ifdef SIDL_F77_CHAR_AS_STRING
  *SIDL_F77_STR(value)
#else
  *value
#endif
   );
}"""



if __name__ == '__main__':
  print """/*********************************************************************
* File:        sidlfortarray.c
* Copyright:   (C) 2001 Lawrence Livermore National Security, LLC
* Description: FORTRAN API for arrays of basic types
* Revision:    $Revision: 7188 $
* Automatically Generated by genf77arrays.py
**********************************************************************/

#include "sidl_header.h"
#include "babel_config.h"
#include "sidlfortran.h"
#include "sidlType.h"
#include "sidlArray.h"
#include <stdlib.h>
#include <stddef.h>
"""

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
              'double'    : '',
              'int'      : '',
             'long'     : '',
              'opaque'   : '(ptrdiff_t)'}
  setcast = { 'bool'     : '',
              'dcomplex' : '',
              'fcomplex' : '',
              'float'    : '',
              'double'    : '',
              'int'      : '',
             'long'     : '',
              'opaque'   : '(void *)(ptrdiff_t)'}
  chartypes = { 'char'  : 'char',
                'string': 'char' }
  limited = ( 'bool', 'opaque' )
  keys = types.keys();
  keys.sort()
  for type in keys:
    createCol(type)
    createRow(type)
    create1d(type)
    createRow2d(type)
    createCol2d(type)
    copyArray(type)
    ensureArray(type)
    smartCopyArray(type)
    slice(type)
    isColumnOrder(type)
    isRowOrder(type)
    dimen(type)
    stride(type)
    lower(type)
    upper(type)
    destroy(type)
    addRef(type)
    if (not (type in limited)):
      borrow(type, types[type])
      raw(type, reftypes[type])
    for j in range(1, 5):
      get(type, types[type], getcast[type], j)
      set(type, types[type], setcast[type], j)
    genericset(type, types[type], setcast[type])
    genericget(type, types[type], getcast[type])

  keys = chartypes.keys()
  keys.sort()
  for type in keys:
    createCol(type)
    createRow(type)
    create1d(type)
    createRow2d(type)
    createCol2d(type)
    copyArray(type)
    ensureArray(type)
    smartCopyArray(type)
    slice(type)
    isColumnOrder(type)
    isRowOrder(type)
    dimen(type)
    stride(type)
    lower(type)
    upper(type)
    destroy(type)
  for j in range(1, 5):
    stringget(j)
    stringset(j)
  stringgenericget()
  stringgenericset()
  for j in range(1, 5):
    charget(j)
    charset(j)
  chargenericget()
  chargenericset()


