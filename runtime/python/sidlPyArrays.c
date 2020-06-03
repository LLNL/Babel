/*
 * File:        sidlPyArrays.c
 * Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7188 $
 * Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
 * Description: Runtime support routines to convert sidl arrays to/from Python
 *
 * This file provides functions to convert sidl arrays to Python with or
 * without borrowing data and functions to convert Python arrays to sidl
 * with or without borrowing. When borrowing data is not possible, data
 * is copied.
 * Copyright (c) 2000-2007, Lawrence Livermore National Security, LLC.
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by the Components Team <components@llnl.gov>
 * UCRL-CODE-2002-054
 * All rights reserved.
 * 
 * This file is part of Babel. For more information, see
 * http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
 * for Our Notice and the LICENSE file for the GNU Lesser General Public
 * License.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License (as published by
 * the Free Software Foundation) version 2.1 dated February 1999.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
 * conditions of the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

#define sidlPyArrays_MODULE 1
#include "sidlPyArrays.h"

#include "sidlObjA.h"
#include "sidlArray.h"
#include "sidl_bool_IOR.h"
#include "sidl_char_IOR.h"
#include "sidl_dcomplex_IOR.h"
#include "sidl_double_IOR.h"
#include "sidl_fcomplex_IOR.h"
#include "sidl_float_IOR.h"
#include "sidl_int_IOR.h"
#include "sidl_long_IOR.h"
#include "sidl_opaque_IOR.h"
#include "sidl_string_IOR.h"
#include "sidl_interface_IOR.h"
#include "sidl_BaseInterface_IOR.h"
#include "sidl_Python.h"

/* include Numerical Python header file */
#ifdef SIDL_HAVE_NUMPY
#include "oldnumeric.h"
#define SIDL_PY_STRIDE_T npy_intp
#define SIDL_PY_DIM_T npy_intp
#define SIDL_PY_SIMPLE_ALLOC PyArray_SimpleNew
#define SIDL_PY_DATA_ALLOC PyArray_SimpleNewFromData
#else
#ifdef SIDL_HAVE_NUMERIC_PYTHON
#include "Numeric/arrayobject.h"
#define SIDL_PY_STRIDE_T int
#define SIDL_PY_DIM_T int
#define SIDL_PY_SIMPLE_ALLOC PyArray_FromDims
#define SIDL_PY_DATA_ALLOC PyArray_FromDimsAndData
#else
#error Neither Numeric Python nor NumPy is installed.
#endif
#endif
#include <stdlib.h>
#include <string.h>

#if (PY_VERSION_HEX >= 0x02040000)
#define SIDL_RECORD_BEGIN Py_BEGIN_ALLOW_THREADS sidl_Python_LogUnlock(__func__, __FILE__, __LINE__)
#define SIDL_RECORD_END Py_END_ALLOW_THREADS sidl_Python_LogRelock(__func__, __FILE__, __LINE__)
#define SIDL_RECORD_ENSURE _gstate = PyGILState_Ensure(); sidl_Python_LogGILEnsure(__func__, __FILE__, __LINE__, (int)_gstate)
#define SIDL_RECORD_RELEASE PyGILState_Release(_gstate); sidl_Python_LogGILRelease(__func__, __FILE__, __LINE__, (int)_gstate)
#else
#define SIDL_RECORD_BEGIN
#define SIDL_RECORD_END
#define SIDL_RECORD_ENSURE
#define SIDL_RECORD_RELEASE
#endif

#if defined(__STDC_VERSION__) && (__STDC_VERSION__ >= 199901L)
#define sidlpyrestrict restrict
#else
#if defined(__GNUC__) && (__GNUC__ >= 3)
#define sidlpyrestrict __restrict__
#else
#define sidlpyrestrict
#endif
#endif

#ifndef Py_TYPE
#define Py_TYPE(ob) (((PyObject *)(ob))->ob_type)
#endif

static void
addPythonArrayModule(PyObject *module)
{
#ifdef SIDL_HAVE_NUMPY
  PyModule_AddStringConstant(module, "type", "numpy");
#else
#ifdef SIDL_HAVE_NUMERIC_PYTHON
  PyModule_AddStringConstant(module, "type", "numeric");
#else
  Py_INCREF(Py_None);
  PyModule_AddObject(module, "type", Py_None);
#endif
#endif
}

/* Python specializations of the basic array types */

struct sidl__array * 
sidl_python_smartCp(struct sidl__array *array) {
  sidl__array_addRef(array); 
  return array; 
}

#define specialized_array(sidlType, cType)                              \
struct sidl_python_## sidlType ##_array {                               \
  struct sidl_## sidlType ##__array  d_array;                           \
  PyObject                  *d_numarray;                                \
};                                                                      \
                                                                        \
void sidl_python_## sidlType ##_destroy(struct sidl__array *array)      \
{                                                                       \
  if (array) {                                                          \
    struct sidl_python_## sidlType ##_array *parray =                   \
      (struct sidl_python_## sidlType ##_array *)array;                 \
    PyGILState_STATE _gstate;                                           \
    SIDL_RECORD_ENSURE;                                                 \
    Py_XDECREF(parray->d_numarray);                                     \
    SIDL_RECORD_RELEASE;                                                \
    sidl__array_remove(array);                                          \
    free((void *)array);                                                \
  }                                                                     \
}                                                                       \
                                                                        \
static int32_t                                                          \
sidl_python_## sidlType ##_type(void) {                                 \
  return sidl_## sidlType ##_array;                                     \
}                                                                       \
                                                                        \
static const struct sidl__array_vtable                                  \
s_## sidlType ##_vtable = {                                             \
  sidl_python_## sidlType ##_destroy,                                   \
  sidl_python_smartCp,                                                  \
  sidl_python_## sidlType ##_type                                       \
};                                                                      \
                                                                        \
static struct sidl_## sidlType ##__array *                              \
sidl_python_## sidlType ##_create(const int32_t dimen,                  \
                          const int32_t lower[],                        \
                          const int32_t upper[],                        \
                          const int32_t stride[],                       \
                          cType         *first,                         \
                          PyObject      *pyobj) {                       \
  static const size_t arraySize =                                       \
    sizeof(struct sidl_python_## sidlType ##_array) +                   \
    (sizeof(int32_t) -                                                  \
     (sizeof(struct sidl_python_## sidlType ##_array)                   \
      % sizeof(int32_t))) % sizeof(int32_t);                            \
  struct sidl_python_## sidlType ##_array *result =                     \
    (struct sidl_python_## sidlType ##_array *)malloc                   \
    (arraySize + 3 * sizeof(int32_t) * dimen);                          \
  if (result) {                                                         \
    result->d_array.d_metadata.d_vtable = &s_## sidlType ##_vtable;     \
    result->d_array.d_metadata.d_dimen = dimen;                         \
    result->d_array.d_metadata.d_refcount = 1;                          \
    result->d_array.d_metadata.d_lower = (int32_t *)                    \
      ((char *)result + arraySize);                                     \
    result->d_array.d_metadata.d_upper =                                \
      result->d_array.d_metadata.d_lower + dimen;                       \
    result->d_array.d_metadata.d_stride =                               \
      result->d_array.d_metadata.d_upper + dimen;                       \
    memcpy(result->d_array.d_metadata.d_lower,                          \
           lower,sizeof(int32_t)*dimen);                                \
    memcpy(result->d_array.d_metadata.d_upper,                          \
           upper,sizeof(int32_t)*dimen);                                \
    memcpy(result->d_array.d_metadata.d_stride,                         \
           stride,sizeof(int32_t)*dimen);                               \
    result->d_array.d_firstElement = first;                             \
    sidl__array_add((struct sidl__array*)&(result->d_array));           \
    Py_XINCREF(pyobj);                                                  \
    result->d_numarray = pyobj;                                         \
  }                                                                     \
  return (struct sidl_## sidlType ##__array *)result;                   \
}

specialized_array(bool,sidl_bool)
specialized_array(char,char)
specialized_array(dcomplex,struct sidl_dcomplex)
specialized_array(double,double)
specialized_array(fcomplex,struct sidl_fcomplex)
specialized_array(float,float)
specialized_array(int,int32_t)
specialized_array(long,int64_t)

typedef struct {
  PyObject_HEAD
  struct sidl__array *d_array;
} SIDLArrayObject;

static void
sao_dealloc(SIDLArrayObject* self)
{
  if (self->d_array) {
    SIDL_RECORD_BEGIN;
    sidl__array_deleteRef(self->d_array);
    SIDL_RECORD_END;
  }
  Py_TYPE(self)->tp_free((PyObject*)self);
}

static PyObject *
sao_new(PyTypeObject *type, PyObject *args, PyObject *kwds)
{
  SIDLArrayObject* self;
  self = (SIDLArrayObject *)type->tp_alloc(type, 0);
  if (self)  self->d_array = NULL;
  return (PyObject *)self;
}

#ifndef PyVarObject_HEAD_INIT
#define PyVarObject_HEAD_INIT(type, size) \
        PyObject_HEAD_INIT(type) size,
#endif

static PyTypeObject sidlPyArrayType = {
  PyVarObject_HEAD_INIT(NULL, 0)
  "sidlPyArrays.SIDLArrayWrapper", /* tp_name */
  sizeof(SIDLArrayObject),      /* tp_basicsize */
  0,                            /* tp_itemsize */
  (destructor)sao_dealloc,      /* tp_dealloc*/
  0,                            /* tp_print*/
  0,                            /* tp_getattr*/
  0,                            /* tp_setattr*/
  0,                            /* tp_compare*/
  0,                            /* tp_repr*/
  0,                            /* tp_as_number*/
  0,                            /* tp_as_sequence*/
  0,                            /* tp_as_mapping*/
  0,                            /* tp_hash */
  0,                            /* tp_call*/
  0,                            /* tp_str*/
  0,                            /* tp_getattro*/
  0,                            /* tp_setattro*/
  0,                            /* tp_as_buffer*/
  Py_TPFLAGS_DEFAULT,           /* tp_flags*/
  "Python type to wrap up SIDL arrays. Not useful to end users.",              /* tp_doc */
  0,                            /* tp_traverse */
  0,                            /* tp_clear */
  0,                            /* tp_richcompare */
  0,                            /* tp_weaklistoffset */
  0,                            /* tp_iter */
  0,                            /* tp_iternext */
  0,                            /* tp_methods */
  0,                            /* tp_members */
  0,                            /* tp_getset */
  0,                            /* tp_base */
  0,                            /* tp_dict */
  0,                            /* tp_descr_get */
  0,                            /* tp_descr_set */
  0,                            /* tp_dictoffset */
  0,                            /* tp_init */
  0,                            /* tp_alloc */
  sao_new,                      /* tp_new */
};

#define sao_Check(op) (Py_TYPE(op) == &sidlPyArrayType)

/**
 * Convert the array description information from the Numerical Python array
 * into a form that sidl likes.  sidl needs a vector of lower and upper
 * index bounds, and a vector holding the stride.
 */
sidl_array__extract_python_info_RETURN
sidl_array__extract_python_info sidl_array__extract_python_info_PROTO
{
  if (PyArray_Check(pya)) {
    const PyArrayObject *array = (PyArrayObject *)pya;
    if (array->nd >= 0) {
      /* a zero dimensional Python array is like a scalar */
      const int32_t dimen = array->nd ? array->nd : 1;
      int32_t i;
      if (dimen > SIDL_MAX_ARRAY_DIMENSION) return 0;
      *dimension = dimen;
      lower[0] = upper[0] = 0;
      stride[0] = 1;
      for(i = 0; i < array->nd; ++i) {
        lower[i] = 0;
        upper[i] = array->dimensions[i] - 1;
        stride[i] = array->strides[i]/array->descr->elsize;
        if ((array->strides[i] % array->descr->elsize) != 0) {
          return 0;
        }
      }
      return 1;
    }
  }
  return 0;
}

/**
 * Return a true value iff the array is contiguous and in column-major order
 * (i.e. like FORTRAN).
 *
 *   stride    the stride for each dimension in bytes.
 *   numelem   the number of elements in each dimension.
 *   dim       the number of dimensions.
 *   elsize    the element size in bytes.
 */
static int
columnMajorOrder(const SIDL_PY_STRIDE_T     stride[],
                 const int32_t numelem[],
                 const int32_t dim,
                 const int32_t elsize)
{
  int32_t dimStride = elsize, i;
  for(i = 0; i < dim; ++i) {
    if (stride[i] != dimStride) return FALSE;
    dimStride *= numelem[i];
  }
  return TRUE;
}

/**
 * Return a true value iff the array is contiguous and in row-major order
 * (i.e. like C). 
 *
 *   stride    the stride for each dimension in bytes.
 *   numelem   the number of elements in each dimension.
 *   dim       the number of dimensions.
 *   elsize    the element size in bytes.
 */
static int
rowMajorOrder(const SIDL_PY_STRIDE_T     stride[],
              const int32_t numelem[],
              const int32_t dim,
              const int32_t elsize)
{
  int dimStride = elsize, i;
  for(i = dim - 1; i >= 0; --i) {
    if (stride[i] != dimStride) return FALSE;
    dimStride *= numelem[i];
  }
  return TRUE;
}

/**
 * Return the number elements in the array times elsize.  If elsize is the
 * element size in bytes, this will return the size of the array in bytes.
 * If elsize is 1, this returns the number of elements in the array.
 *
 *   numelem     the number of elements in each dimension. Array of dim
 *               integers.
 *   dim         the number of dimensions.
 *   elsize      to get the size of the array in bytes, pass in the
 *               size of an element in bytes. To get the number of
 *               elements, pass in 1.
 */
static size_t
arraySize(const int32_t numelem[],
          const int32_t dim,
          const int32_t elsize)
{
  size_t result = 0;
  if ((dim > 0) && (dim <= SIDL_MAX_ARRAY_DIMENSION)) {
    int i;
    result = elsize;
    for(i = 0; i < dim; ++i) {
      result *= numelem[i];
    }
  }
  return result;
}

/**
 * A parameterized function to copy one strided array into another.
 */
#define copyData_impl(name, type) \
static void copy_ ## name ## _data(char * sidlpyrestrict dest, \
                                   const SIDL_PY_STRIDE_T dstride[], /* not int32_t */ \
                                   const char * sidlpyrestrict src, \
                                   const SIDL_PY_STRIDE_T sstride[], /* not int32_t */ \
                                   const int32_t numelem[], \
                                   const int32_t dim) \
{ \
  size_t num = arraySize(numelem, dim, sizeof(type)); \
  if (num > 0) { \
    SIDL_RECORD_BEGIN; \
    if ((columnMajorOrder(sstride, numelem, dim, sizeof(type)) && \
         columnMajorOrder(dstride, numelem, dim, sizeof(type))) || \
        (rowMajorOrder(sstride, numelem, dim, sizeof(type)) && \
         rowMajorOrder(dstride, numelem, dim, sizeof(type)))) { \
      memcpy(dest, src, num); \
    } \
    else { \
      int32_t ind[SIDL_MAX_ARRAY_DIMENSION], i; \
      memset(ind, 0, sizeof(int32_t)*dim); \
      num /= sizeof(type); \
      while (num--) { \
        *((type * sidlpyrestrict)dest) = *((const type * sidlpyrestrict)src); \
        for (i = 0; i < dim ; ++i) { \
          dest += dstride[i]; \
          src += sstride[i]; \
          if (++(ind[i]) < numelem[i]) { \
            break; \
          } \
          else { \
            ind[i] = 0; \
            dest -= (numelem[i]*dstride[i]); \
            src -= (numelem[i]*sstride[i]); \
          } \
        } \
      } \
    } \
    SIDL_RECORD_END; \
  } \
}

copyData_impl(bool, int)
copyData_impl(char, char)
copyData_impl(int, int32_t)
#if (defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)) || (SIZEOF_SHORT == 8) || (SIZEOF_INT == 8) || (SIZEOF_LONG == 8)
copyData_impl(long, int64_t)
#endif
copyData_impl(float, float)
copyData_impl(double, double)
copyData_impl(fcomplex, struct sidl_fcomplex)
copyData_impl(dcomplex, struct sidl_dcomplex)

static 
struct sidl__array *
alreadySIDLArray(PyArrayObject *pya,
                 const size_t   dataSize,
                 const int      sidlArrayType,
                 const int      orderEnum)
{
  struct sidl__array *result = NULL;
  if (pya->base && sao_Check(pya->base) &&
      (sidl__array_type(((SIDLArrayObject *)(pya->base))->d_array) == sidlArrayType)) {
    result = ((SIDLArrayObject *)(pya->base))->d_array;
    {
      const int32_t dimen = sidlArrayDim(result);
      int i;
      if ((dimen != pya->nd) ||
          ((sidl_column_major_order == orderEnum) &&
           !sidl__array_isColumnOrder(result)) ||
          ((sidl_row_major_order == orderEnum) &&
           !sidl__array_isRowOrder(result)))
        return NULL;
      /* check strides and extents */
      for(i = 0; i < dimen; ++i) {
        if (!((sidlStride(result, i)*dataSize == pya->strides[i]) &&
              (sidlLength(result, i) == pya->dimensions[i]))) return NULL;
      }
      return sidl__array_smartCopy(result);
    }
  }
  return result;
}

/*
 * Parameterized function implementation to borrow a particular type
 * array.
 */
#define sidl_borrow_impl(stype,dtype,pytype) \
{\
  if (PyArray_Check(obj)) {\
    PyArrayObject *pya = (PyArrayObject *)obj;\
    if ((*array = (struct sidl_ ## stype ## __array *) \
         alreadySIDLArray(pya, sizeof(dtype), sidl_ ## stype ## _array, sidl_general_order)))\
      return 1;\
    if (pytype == pya->descr->type_num) { \
      int32_t dimension,\
         lower[SIDL_MAX_ARRAY_DIMENSION],\
         upper[SIDL_MAX_ARRAY_DIMENSION],\
         stride[SIDL_MAX_ARRAY_DIMENSION];\
      if (sidl_array__extract_python_info((PyObject *)pya, &dimension, \
                                          lower, upper, stride)) {\
        *array = sidl_python_ ## stype ## _create\
           (dimension, lower, upper, stride, (dtype *)pya->data, \
            (PyObject *)pya);\
        return (*array != NULL);\
      }\
    }\
  }\
  return sidl_ ## stype ## __clone_python_array(obj, array);\
}


sidl_bool__borrow_python_array_RETURN
sidl_bool__borrow_python_array sidl_bool__borrow_python_array_PROTO
sidl_borrow_impl(bool, sidl_bool, PyArray_INT)

sidl_char__borrow_python_array_RETURN
sidl_char__borrow_python_array sidl_char__borrow_python_array_PROTO
{
  if (PyArray_Check(obj)) {
    PyArrayObject *pya = (PyArrayObject *)obj;
    if ((*array = (struct sidl_char__array *)
         alreadySIDLArray(pya, sizeof(char), 
                          sidl_char_array,
                          sidl_general_order))) return 1;
    if ((PyArray_CHAR == pya->descr->type_num) ||
        (PyArray_UBYTE == pya->descr->type_num) ||
#ifdef SIDL_HAVE_NUMPY
        (NPY_STRING == pya->descr->type_num) ||
#endif
        (PyArray_SBYTE == pya->descr->type_num)) {
      int32_t dimension, 
        lower[SIDL_MAX_ARRAY_DIMENSION], 
        upper[SIDL_MAX_ARRAY_DIMENSION], 
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimension, 
                                          lower, upper, stride)) {
        *array = sidl_python_char_create(dimension, lower, upper, stride,
                                         (char *)pya->data, (PyObject*)pya);
        return (*array != NULL);
      }
    }
  }
  return sidl_char__clone_python_array(obj, array);
}

sidl_dcomplex__borrow_python_array_RETURN
sidl_dcomplex__borrow_python_array sidl_dcomplex__borrow_python_array_PROTO
sidl_borrow_impl(dcomplex, struct sidl_dcomplex, PyArray_CDOUBLE)

sidl_double__borrow_python_array_RETURN
sidl_double__borrow_python_array sidl_double__borrow_python_array_PROTO
sidl_borrow_impl(double, double, PyArray_DOUBLE)

sidl_fcomplex__borrow_python_array_RETURN
sidl_fcomplex__borrow_python_array sidl_fcomplex__borrow_python_array_PROTO
sidl_borrow_impl(fcomplex, struct sidl_fcomplex, PyArray_CFLOAT)

sidl_float__borrow_python_array_RETURN
sidl_float__borrow_python_array sidl_float__borrow_python_array_PROTO
sidl_borrow_impl(float, float, PyArray_FLOAT)

sidl_int__borrow_python_array_RETURN
sidl_int__borrow_python_array sidl_int__borrow_python_array_PROTO
{
  if (PyArray_Check(obj)) {
    PyArrayObject *pya = (PyArrayObject *)obj;
    if ((*array = (struct sidl_int__array *)
         alreadySIDLArray(pya, sizeof(int32_t), 
                          sidl_int_array, sidl_general_order))) return 1;
    if (0
#if SIZEOF_SHORT == 4
        || (PyArray_SHORT == pya->descr->type_num)
#ifdef PyArray_UNSIGNED_TYPES
        || (PyArray_USHORT == pya->descr->type_num)
#endif
#endif
#if SIZEOF_INT == 4
        || (PyArray_INT == pya->descr->type_num)
#ifdef PyArray_UNSIGNED_TYPES
        || (PyArray_UINT == pya->descr->type_num)
#endif
#endif
#if SIZEOF_LONG == 4
        || (PyArray_LONG == pya->descr->type_num)
#endif
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
        || (NPY_INT32 == pya->descr->type_num)
#endif
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_UINT32)
        || (NPY_UINT32 == pya->descr->type_num)
#endif
        ) {
      int32_t dimension, lower[SIDL_MAX_ARRAY_DIMENSION],
        upper[SIDL_MAX_ARRAY_DIMENSION],
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimension, 
                                          lower, upper, stride)) {
        *array = sidl_python_int_create(dimension, lower, upper, stride,
                                        (int32_t *)pya->data, (PyObject*)pya);
        return (*array != NULL);
      }
    }
  }
  return sidl_int__clone_python_array(obj, array);
}

sidl_long__borrow_python_array_RETURN
sidl_long__borrow_python_array sidl_long__borrow_python_array_PROTO
{
  if (PyArray_Check(obj)) {
    PyArrayObject *pya = (PyArrayObject *)obj;
    if ((*array = (struct sidl_long__array *)
         alreadySIDLArray(pya, sizeof(int64_t), 
                          sidl_long_array, sidl_general_order))) return 1;
    if (0
#if SIZEOF_SHORT == 8
        || (PyArray_SHORT == pya->descr->type_num)
#ifdef PyArray_UNSIGNED_TYPES
        || (PyArray_USHORT == pya->descr->type_num)
#endif
#endif
#if SIZEOF_INT == 8
        || (PyArray_INT == pya->descr->type_num)
#ifdef PyArray_UNSIGNED_TYPES
        || (PyArray_UINT == pya->descr->type_num)
#endif
#endif
#if SIZEOF_LONG == 8
        || (PyArray_LONG == pya->descr->type_num)
#endif
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
        || (NPY_INT64 == pya->descr->type_num)
#endif
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_UINT64)
        || (NPY_UINT64 == pya->descr->type_num)
#endif
        ) {
      int32_t dimension, lower[SIDL_MAX_ARRAY_DIMENSION],
        upper[SIDL_MAX_ARRAY_DIMENSION],
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimension, 
                                          lower, upper, stride)) {
        *array = sidl_python_long_create(dimension, lower, upper, stride,
                                         (int64_t *)pya->data, (PyObject*)pya);
        return (*array != NULL);
      }
    }
  }
  return sidl_long__clone_python_array(obj, array);
}

sidl_string__borrow_python_array_RETURN
sidl_string__borrow_python_array sidl_string__borrow_python_array_PROTO
{
  return sidl_string__clone_python_array(obj,array);
}

sidl_opaque__borrow_python_array_RETURN
sidl_opaque__borrow_python_array sidl_opaque__borrow_python_array_PROTO
{
  return sidl_opaque__clone_python_array(obj,array);
}

/*
 * Parameterized implementation of a clone function.
 */
#define ClonePython_impl(sidlname, sidltype, pyarraytype, order, orderenum) \
{ \
  int result = 0; \
  *array = 0; \
  if (obj == Py_None) { \
    result = 1; \
  } \
  else { \
    PyArrayObject *pya = \
      (PyArrayObject *)PyArray_FromObject(obj, pyarraytype, 0, 0); \
    if (pya) { \
      int32_t dimen,\
          lower[SIDL_MAX_ARRAY_DIMENSION],\
          upper[SIDL_MAX_ARRAY_DIMENSION],\
          stride[SIDL_MAX_ARRAY_DIMENSION]; \
      if ((*array = (struct sidl_ ## sidlname ## __array *) \
           alreadySIDLArray(pya, sizeof(sidltype), \
           sidl_ ## sidlname ## _array, orderenum))) { \
        Py_DECREF(pya); \
        return 1;\
      }\
      if (sidl_array__extract_python_info((PyObject *)pya, &dimen, \
                                          lower, upper, stride)) { \
        *array = \
           sidl_ ## sidlname ## __array_create ## order(dimen, lower, upper); \
        if (*array) { \
          int i; \
          SIDL_PY_STRIDE_T bytestride[SIDL_MAX_ARRAY_DIMENSION];	\
          int32_t numelem[SIDL_MAX_ARRAY_DIMENSION]; \
          for(i = 0; i < dimen; ++i){ \
            numelem[i] = 1 + upper[i] - lower[i]; \
            bytestride[i] = sizeof(sidltype)* \
               sidlStride(*array, i); \
          } \
          copy_ ## sidlname ## _data((char *)((*array)->d_firstElement),  \
                         bytestride, \
                         pya->data, \
                         pya->strides, \
                         numelem, \
                         dimen); \
          result = 1; \
        } \
      } \
      Py_DECREF(pya); \
    } \
  } \
  return result; \
}

sidl_bool__clone_python_array_column_RETURN
sidl_bool__clone_python_array_column sidl_bool__clone_python_array_column_PROTO
ClonePython_impl(bool, sidl_bool, PyArray_INT, Col, sidl_column_major_order)

sidl_char__clone_python_array_column_RETURN
sidl_char__clone_python_array_column sidl_char__clone_python_array_column_PROTO
ClonePython_impl(char, char, PyArray_CHAR, Col, sidl_column_major_order)

sidl_dcomplex__clone_python_array_column_RETURN
sidl_dcomplex__clone_python_array_column sidl_dcomplex__clone_python_array_column_PROTO
ClonePython_impl(dcomplex, struct sidl_dcomplex, PyArray_CDOUBLE, Col, sidl_column_major_order)

sidl_double__clone_python_array_column_RETURN
sidl_double__clone_python_array_column sidl_double__clone_python_array_column_PROTO
ClonePython_impl(double, double, PyArray_DOUBLE, Col, sidl_column_major_order)

sidl_fcomplex__clone_python_array_column_RETURN
sidl_fcomplex__clone_python_array_column sidl_fcomplex__clone_python_array_column_PROTO
ClonePython_impl(fcomplex, struct sidl_fcomplex, PyArray_CFLOAT, Col, sidl_column_major_order)

sidl_float__clone_python_array_column_RETURN
sidl_float__clone_python_array_column sidl_float__clone_python_array_column_PROTO
ClonePython_impl(float, float, PyArray_FLOAT, Col, sidl_column_major_order)

#if SIZEOF_SHORT == 4
sidl_int__clone_python_array_column_RETURN
sidl_int__clone_python_array_column sidl_int__clone_python_array_column_PROTO
ClonePython_impl(int, int32_t, PyArray_SHORT, Col, sidl_column_major_order)
#else
#if SIZEOF_INT == 4
sidl_int__clone_python_array_column_RETURN
sidl_int__clone_python_array_column sidl_int__clone_python_array_column_PROTO
ClonePython_impl(int, int32_t, PyArray_INT, Col, sidl_column_major_order)
#else
#if SIZEOF_LONG == 4
sidl_int__clone_python_array_column_RETURN
sidl_int__clone_python_array_column sidl_int__clone_python_array_column_PROTO
ClonePython_impl(int, int32_t, PyArray_LONG, Col, sidl_column_major_order)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
sidl_int__clone_python_array_column_RETURN
sidl_int__clone_python_array_column sidl_int__clone_python_array_column_PROTO
ClonePython_impl(int, int32_t, NPY_INT32, Col, sidl_column_major_order)
#else
#error No 32-bit integer available.
#endif
#endif
#endif
#endif

#if SIZEOF_SHORT == 8
sidl_long__clone_python_array_column_RETURN
sidl_long__clone_python_array_column sidl_long__clone_python_array_column_PROTO
ClonePython_impl(long, int64_t, PyArray_SHORT, Col, sidl_column_major_order)
#else
#if SIZEOF_INT == 8
sidl_long__clone_python_array_column_RETURN
sidl_long__clone_python_array_column sidl_long__clone_python_array_column_PROTO
ClonePython_impl(long, int64_t, PyArray_INT, Col, sidl_column_major_order)
#else
#if SIZEOF_LONG == 8
sidl_long__clone_python_array_column_RETURN
sidl_long__clone_python_array_column sidl_long__clone_python_array_column_PROTO
ClonePython_impl(long, int64_t, PyArray_LONG, Col, sidl_column_major_order)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
sidl_long__clone_python_array_column_RETURN
sidl_long__clone_python_array_column sidl_long__clone_python_array_column_PROTO
ClonePython_impl(long, int64_t, NPY_INT64, Col, sidl_column_major_order)
#else
static PyArrayObject *
toNumericArray(PyObject *obj)
{
  PyArrayObject *result = NULL;
  if (obj) {
    if (PyArray_Check(obj)) {
      result = (PyArrayObject *)obj;
      Py_INCREF(obj);
    }
    else {
      result = (PyArrayObject *)
        PyArray_FromObject(obj, PyArray_OBJECT, 0, 0);
    }
  }
  return result;
}

#define long_copy(srctype,convert) \
for(i = 0 ; i < size ; ++i ) { \
  *dest = (int64_t)convert(*((const srctype * sidlpyrestrict)src)); \
  for (j = 0 ;  j < dimen; ++j ) { \
    dest += stride[j]; \
    src  += srcstride[j]; \
    if ((++(ind[j])) < numelem[j]) { \
      break; \
    } \
    else { \
      ind[j] = 0; \
      dest -= (numelem[j]*stride[j]); \
      src  -= (numelem[j]*srcstride[j]); \
    } \
  } \
}

static int64_t
pythonToLong(const PyObject *obj)
{
  int64_t result = 0;
  if (obj) {
#if PY_MAJOR_VERSION < 3
    if (PyInt_Check(obj)) result = PyInt_AsLong((PyObject *)obj);
    else {
#endif
      PyObject *lnum = PyNumber_Long((PyObject *)obj);
      if (lnum) {
#if defined(HAVE_LONG_LONG)
        result = PyLong_AsLongLong(lnum);
#else
        result = PyLong_AsLong(lnum);
#endif
        Py_DECREF(lnum);
      }
#if PY_MAJOR_VERSION < 3
      else {
        lnum = PyNumber_Int((PyObject *)obj);
        if (lnum) {
          result = PyInt_AsLong(lnum);
          Py_DECREF(lnum);
        }
      }
    }
#endif
  }
  return result;
}


static void
clone_long_python(PyArrayObject *pya,
                  struct sidl_long__array *array,
                  const int32_t lower[],
                  const int32_t upper[],
                  const int32_t stride[])
{
  const int size = PyArray_Size((PyObject *)pya);
  const int32_t dimen = pya->nd;
  const int32_t sdim = sidl_long__array_dimen(array);
  int i, j;
  int64_t * sidlpyrestrict dest = sidl_long__array_first(array);
  const char * sidlpyrestrict src = pya->data;
  const SIDL_PY_STRIDE_T * sidlpyrestrict srcstride = pya->strides;
  int32_t * sidlpyrestrict ind =  malloc(sizeof(int32_t)*sdim);
  int32_t * sidlpyrestrict numelem = malloc(sizeof(int32_t)*sdim);
  if (ind && numelem) {
    memset(ind, 0, sizeof(int32_t)*sdim);
    for(i = 0; i < sdim; ++i ){
      numelem[i] = sidlLength(array,i);
    }
    switch(pya->descr->type_num) {
    case PyArray_CHAR:
      long_copy(char,);
      break;
    case PyArray_UBYTE:
      long_copy(unsigned char,);
      break;
    case PyArray_SBYTE:
      long_copy(signed char,);
      break;
    case PyArray_SHORT:
      long_copy(short,);
      break;
    case PyArray_INT:
      long_copy(int,);
      break;
    case PyArray_LONG:
      long_copy(long,);
      break;
    case PyArray_FLOAT:
      long_copy(float,);
      break;
    case PyArray_DOUBLE:
      long_copy(double,);
      break;
    case PyArray_OBJECT:
      long_copy(PyObject *,pythonToLong);
      break;
    }
  }
  if (ind)      free(ind);
  if (numelem)  free(numelem);
}

sidl_long__clone_python_array_column_RETURN
sidl_long__clone_python_array_column sidl_long__clone_python_array_column_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = 1;
  }
  else {
    PyArrayObject *pya = toNumericArray(obj);
    if (pya) {
      int32_t dimen;
      int32_t lower[SIDL_MAX_ARRAY_DIMENSION],
        upper[SIDL_MAX_ARRAY_DIMENSION],
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimen,
                                          lower, upper, stride)) {
        *array =
          sidl_long__array_createCol(dimen, lower, upper);
        clone_long_python(pya, *array, lower, upper, stride);
        result = 1;
      }
      Py_DECREF((PyObject *)pya);
    }
  }
  return result;
}
#endif
#endif
#endif
#endif


sidl_bool__clone_python_array_row_RETURN
sidl_bool__clone_python_array_row sidl_bool__clone_python_array_row_PROTO
ClonePython_impl(bool, sidl_bool, PyArray_INT, Row, sidl_row_major_order)

sidl_char__clone_python_array_row_RETURN
sidl_char__clone_python_array_row sidl_char__clone_python_array_row_PROTO
ClonePython_impl(char, char, PyArray_CHAR, Row, sidl_row_major_order)

sidl_dcomplex__clone_python_array_row_RETURN
sidl_dcomplex__clone_python_array_row sidl_dcomplex__clone_python_array_row_PROTO
ClonePython_impl(dcomplex, struct sidl_dcomplex, PyArray_CDOUBLE, Row, sidl_row_major_order)

sidl_double__clone_python_array_row_RETURN
sidl_double__clone_python_array_row sidl_double__clone_python_array_row_PROTO
ClonePython_impl(double, double, PyArray_DOUBLE, Row, sidl_row_major_order)

sidl_fcomplex__clone_python_array_row_RETURN
sidl_fcomplex__clone_python_array_row sidl_fcomplex__clone_python_array_row_PROTO
ClonePython_impl(fcomplex, struct sidl_fcomplex, PyArray_CFLOAT, Row, sidl_row_major_order)

sidl_float__clone_python_array_row_RETURN
sidl_float__clone_python_array_row sidl_float__clone_python_array_row_PROTO
ClonePython_impl(float, float, PyArray_FLOAT, Row, sidl_row_major_order)

#if SIZEOF_SHORT == 4
sidl_int__clone_python_array_row_RETURN
sidl_int__clone_python_array_row sidl_int__clone_python_array_row_PROTO
ClonePython_impl(int, int32_t, PyArray_SHORT, Row, sidl_row_major_order)
#else
#if SIZEOF_INT == 4
sidl_int__clone_python_array_row_RETURN
sidl_int__clone_python_array_row sidl_int__clone_python_array_row_PROTO
ClonePython_impl(int, int32_t, PyArray_INT, Row, sidl_row_major_order)
#else
#if SIZEOF_LONG == 4
sidl_int__clone_python_array_row_RETURN
sidl_int__clone_python_array_row sidl_int__clone_python_array_row_PROTO
ClonePython_impl(int, int32_t, PyArray_LONG, Row, sidl_row_major_order)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
sidl_int__clone_python_array_row_RETURN
sidl_int__clone_python_array_row sidl_int__clone_python_array_row_PROTO
ClonePython_impl(int, int32_t, NPY_INT32, Row, sidl_row_major_order)
#else
#error No 32-bit integer available.
#endif
#endif
#endif
#endif

#if SIZEOF_SHORT == 8
sidl_long__clone_python_array_row_RETURN
sidl_long__clone_python_array_row sidl_long__clone_python_array_row_PROTO
ClonePython_impl(long, int64_t, PyArray_SHORT, Row, sidl_row_major_order)
#else
#if SIZEOF_INT == 8
sidl_long__clone_python_array_row_RETURN
sidl_long__clone_python_array_row sidl_long__clone_python_array_row_PROTO
ClonePython_impl(long, int64_t, PyArray_INT, Row, sidl_row_major_order)
#else
#if SIZEOF_LONG == 8
sidl_long__clone_python_array_row_RETURN
sidl_long__clone_python_array_row sidl_long__clone_python_array_row_PROTO
ClonePython_impl(long, int64_t, PyArray_LONG, Row, sidl_row_major_order)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
sidl_long__clone_python_array_row_RETURN
sidl_long__clone_python_array_row sidl_long__clone_python_array_row_PROTO
ClonePython_impl(long, int64_t, NPY_INT64, Row, sidl_row_major_order)
#else
sidl_long__clone_python_array_row_RETURN
sidl_long__clone_python_array_row sidl_long__clone_python_array_row_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = 1;
  }
  else {
    PyArrayObject *pya = toNumericArray(obj);
    if (pya) {
      int32_t dimen, lower[SIDL_MAX_ARRAY_DIMENSION],
        upper[SIDL_MAX_ARRAY_DIMENSION],
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimen,
                                          lower, upper, stride)) {
        *array =
          sidl_long__array_createRow(dimen, lower, upper);
        clone_long_python(pya, *array, lower, upper, stride);
        result = 1;
      }
      Py_DECREF((PyObject *)pya);
    }
  }
  return result;
}
#endif
#endif
#endif
#endif

sidl_bool__clone_python_array_RETURN
sidl_bool__clone_python_array sidl_bool__clone_python_array_PROTO
ClonePython_impl(bool, sidl_bool, PyArray_INT, Row, sidl_general_order)

sidl_char__clone_python_array_RETURN
sidl_char__clone_python_array sidl_char__clone_python_array_PROTO
ClonePython_impl(char, char, PyArray_CHAR, Row, sidl_general_order)

sidl_dcomplex__clone_python_array_RETURN
sidl_dcomplex__clone_python_array sidl_dcomplex__clone_python_array_PROTO
ClonePython_impl(dcomplex, struct sidl_dcomplex, PyArray_CDOUBLE, Row, sidl_general_order)

sidl_double__clone_python_array_RETURN
sidl_double__clone_python_array sidl_double__clone_python_array_PROTO
ClonePython_impl(double, double, PyArray_DOUBLE, Row, sidl_general_order)

sidl_fcomplex__clone_python_array_RETURN
sidl_fcomplex__clone_python_array sidl_fcomplex__clone_python_array_PROTO
ClonePython_impl(fcomplex, struct sidl_fcomplex, PyArray_CFLOAT, Row, sidl_general_order)

sidl_float__clone_python_array_RETURN
sidl_float__clone_python_array sidl_float__clone_python_array_PROTO
ClonePython_impl(float, float, PyArray_FLOAT, Row, sidl_general_order)

#if SIZEOF_SHORT == 4
sidl_int__clone_python_array_RETURN
sidl_int__clone_python_array sidl_int__clone_python_array_PROTO
ClonePython_impl(int, int32_t, PyArray_SHORT, Row, sidl_general_order)
#elif SIZEOF_INT == 4
sidl_int__clone_python_array_RETURN
sidl_int__clone_python_array sidl_int__clone_python_array_PROTO
ClonePython_impl(int, int32_t, PyArray_INT, Row, sidl_general_order)
#elif SIZEOF_LONG == 4
sidl_int__clone_python_array_RETURN
sidl_int__clone_python_array sidl_int__clone_python_array_PROTO
ClonePython_impl(int, int32_t, PyArray_LONG, Row, sidl_general_order)
#elif defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
sidl_int__clone_python_array_RETURN
sidl_int__clone_python_array sidl_int__clone_python_array_PROTO
ClonePython_impl(int, int32_t, NPY_INT32, Row, sidl_general_order)
#else
#error No 32-bit integer available.
#endif

#if SIZEOF_SHORT == 8
sidl_long__clone_python_array_RETURN
sidl_long__clone_python_array sidl_long__clone_python_array_PROTO
ClonePython_impl(long, int64_t, PyArray_SHORT, Row, sidl_general_order)
#else
#if SIZEOF_INT == 8
sidl_long__clone_python_array_RETURN
sidl_long__clone_python_array sidl_long__clone_python_array_PROTO
ClonePython_impl(long, int64_t, PyArray_INT, Row, sidl_general_order)
#else
#if SIZEOF_LONG == 8
sidl_long__clone_python_array_RETURN
sidl_long__clone_python_array sidl_long__clone_python_array_PROTO
ClonePython_impl(long, int64_t, PyArray_LONG, Row, sidl_general_order)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
sidl_long__clone_python_array_RETURN
sidl_long__clone_python_array sidl_long__clone_python_array_PROTO
ClonePython_impl(long, int64_t, NPY_INT64, Row, sidl_general_order)
#else
sidl_long__clone_python_array_RETURN
sidl_long__clone_python_array sidl_long__clone_python_array_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = 1;
  }
  else {
    PyArrayObject *pya = toNumericArray(obj);
    if (pya) {
      int32_t dimen, lower[SIDL_MAX_ARRAY_DIMENSION], 
        upper[SIDL_MAX_ARRAY_DIMENSION],
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimen,
                                          lower, upper, stride)) {
        *array =
          sidl_long__array_createRow(dimen, lower, upper);
        clone_long_python(pya, *array, lower, upper, stride);
        result = 1;
      }
      Py_DECREF((PyObject *)pya);
    }
  }
  return result;
}
#endif
#endif
#endif
#endif


sidl_array__convert_python_RETURN
sidl_array__convert_python sidl_array__convert_python_PROTO
{
  int result = FALSE;
  if (PyArray_Check(pya_src)) {
    int i, j;
    const int size = PyArray_Size(pya_src);
    const PyArrayObject *pya = (PyArrayObject *)pya_src;
    int32_t *ind = malloc(sizeof(int32_t) * dimen);
    char *pydata = pya->data;
    memset(ind, 0, sizeof(int32_t) * dimen);
    if (size == 1) {              /* handle zero dimensional PyArray */
      if (!((*setfunc)(sidl_dest, ind, *(PyObject **)pydata))) {
        result = TRUE;
      }
    }
    else {
      for(i = 0; i < size; ++i){
        if ((*setfunc)(sidl_dest, ind, *(PyObject **)pydata)) {
          goto error;
        }
        for(j = 0; j < dimen; ++j) {
          pydata += pya->strides[j];
          if (++(ind[j]) < pya->dimensions[j]) {
            break;
          }
          else {
            ind[j] = 0;
            pydata -= (pya->dimensions[j]*pya->strides[j]);
          }
        }
      }
      result = TRUE;
    }
  error:
    free(ind);
  }
  return result;
}

int CopyOpaquePointer(void *array,
                      const int32_t *ind,
                      PyObject *data)
{
  if (PyCObject_Check(data)) {
    sidl_opaque__array_set((struct sidl_opaque__array*)array,
                           ind,
                           PyCObject_AsVoidPtr(data));
    return FALSE;
  }
  if (data == Py_None) {
    sidl_opaque__array_set((struct sidl_opaque__array*)array, ind, NULL);
    return FALSE;
  }
  return TRUE;
}

int CopyStringPointer(void *array,
                      const int32_t *ind,
                      PyObject *data)
{
  if (data == Py_None) {
    sidl_string__array_set((struct sidl_string__array*)array, ind, NULL);
    return FALSE;
  }
  else {
    PyObject *str = PyObject_Str(data);
    if (str) {
      sidl_string__array_set((struct sidl_string__array*)array, ind,
                             PyString_AsString(str));
      Py_DECREF(str);
      return FALSE;
    }
  }
  return TRUE;
}

sidl_opaque__clone_python_array_column_RETURN
sidl_opaque__clone_python_array_column sidl_opaque__clone_python_array_column_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = TRUE;
  }
  else {
    PyObject *pya = PyArray_FromObject(obj, PyArray_OBJECT, 0, 0);
    if (pya) {
      if (PyArray_OBJECT == ((PyArrayObject *)pya)->descr->type_num) {
        int dimen, lower[SIDL_MAX_ARRAY_DIMENSION], 
          upper[SIDL_MAX_ARRAY_DIMENSION],
          stride[SIDL_MAX_ARRAY_DIMENSION];
        if (sidl_array__extract_python_info(pya, &dimen, lower, upper,
                                            stride)) {
          *array = sidl_opaque__array_createCol(dimen, lower, upper);
          result = sidl_array__convert_python(pya, dimen, *array,
                                              CopyOpaquePointer);
          if (*array && !result) {
            sidl__array_deleteRef((struct sidl__array *)*array);
            *array = NULL;
          }
        }
      }
      Py_DECREF(pya);
    }
  }
  return result;
}

sidl_string__clone_python_array_column_RETURN
sidl_string__clone_python_array_column sidl_string__clone_python_array_column_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = TRUE;
  }
  else {
    PyObject *pya = PyArray_FromObject(obj, PyArray_OBJECT, 0, 0);
    if (pya) {
      if (PyArray_OBJECT == ((PyArrayObject *)pya)->descr->type_num) {
        int dimen, lower[SIDL_MAX_ARRAY_DIMENSION],
          upper[SIDL_MAX_ARRAY_DIMENSION],
          stride[SIDL_MAX_ARRAY_DIMENSION];
        if (sidl_array__extract_python_info(pya, &dimen, lower, upper,
                                            stride)) {
          *array = sidl_string__array_createCol(dimen, lower, upper);
          result = sidl_array__convert_python(pya, dimen, *array, 
                                              CopyStringPointer);
          if (*array && !result) {
            sidl__array_deleteRef((struct sidl__array *)*array);
            *array = NULL;
          }
        }
      }
      Py_DECREF(pya);
    }
  }
  return result;
}


sidl_opaque__clone_python_array_row_RETURN
sidl_opaque__clone_python_array_row sidl_opaque__clone_python_array_row_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = TRUE;
  }
  else {
    PyObject *pya = PyArray_FromObject(obj, PyArray_OBJECT, 0, 0);
    if (pya) {
      if (PyArray_OBJECT == ((PyArrayObject *)pya)->descr->type_num) {
        int dimen, lower[SIDL_MAX_ARRAY_DIMENSION], 
          upper[SIDL_MAX_ARRAY_DIMENSION],
          stride[SIDL_MAX_ARRAY_DIMENSION];
        if (sidl_array__extract_python_info(pya, &dimen, lower, upper,
                                            stride)) {
          *array = sidl_opaque__array_createRow(dimen, lower, upper);
          result = sidl_array__convert_python(pya, dimen, *array,
                                              CopyOpaquePointer);
          if (*array && !result) {
            sidl__array_deleteRef((struct sidl__array *)*array);
            *array = NULL;
          }
        }
      }
      Py_DECREF(pya);
    }
  }
  return result;
}

sidl_opaque__clone_python_array_RETURN
sidl_opaque__clone_python_array sidl_opaque__clone_python_array_PROTO
{
  return sidl_opaque__clone_python_array_row(obj,array);
}

sidl_string__clone_python_array_row_RETURN
sidl_string__clone_python_array_row sidl_string__clone_python_array_row_PROTO
{
  int result = 0;
  *array = NULL;
  if (obj == Py_None) {
    result = TRUE;
  }
  else {
    PyObject *pya = PyArray_FromObject(obj, PyArray_OBJECT, 0, 0);
    if (pya) {
      if (PyArray_OBJECT == ((PyArrayObject *)pya)->descr->type_num) {
        int dimen, lower[SIDL_MAX_ARRAY_DIMENSION], 
          upper[SIDL_MAX_ARRAY_DIMENSION], 
          stride[SIDL_MAX_ARRAY_DIMENSION];
        if (sidl_array__extract_python_info(pya, &dimen, lower, upper,
                                            stride)) {
          *array = sidl_string__array_createRow(dimen, lower, upper);
          result = sidl_array__convert_python(pya, dimen, *array, 
                                              CopyStringPointer);
          if (*array && !result) {
            sidl__array_deleteRef((struct sidl__array *)*array);
            *array = NULL;
          }
        }
      }
      Py_DECREF(pya);
    }
  }
  return result;
}

sidl_string__clone_python_array_RETURN
sidl_string__clone_python_array sidl_string__clone_python_array_PROTO
{
  return sidl_string__clone_python_array_row(obj, array);
}


#define CloneSIDL_impl(sidlname, sidltype, pyarraytype) \
static PyObject * \
clone_sidl_ ## sidlname ## _array(struct sidl_ ## sidlname ##__array *array) \
{ \
  PyArrayObject *pya = NULL; \
  if (array) { \
    const int dimen = sidlArrayDim(array); \
    if (dimen <= SIDL_MAX_ARRAY_DIMENSION) {\
      int i; \
      int32_t numelem[SIDL_MAX_ARRAY_DIMENSION]; \
      SIDL_PY_DIM_T pynumelem[SIDL_MAX_ARRAY_DIMENSION];	\
      SIDL_PY_STRIDE_T bytestride[SIDL_MAX_ARRAY_DIMENSION];	\
      for(i = 0; i < dimen; ++i){ \
        numelem[i] = sidlLength(array,i); \
        pynumelem[i] = (int)numelem[i]; \
        bytestride[i] = sizeof(sidltype)* \
          sidlStride(array, i); \
      } \
      pya = (PyArrayObject *)SIDL_PY_SIMPLE_ALLOC(dimen, pynumelem, pyarraytype); \
      if (pya) { \
        copy_ ## sidlname ## _data(pya->data, \
                       pya->strides, \
                       (char *)((array)->d_firstElement),  \
                       bytestride, \
                       numelem, \
                       dimen); \
      } \
    } \
    /* else dimen too big */ \
  } \
  else { \
    Py_INCREF(Py_None); \
    return Py_None; \
  } \
  return (PyObject *)pya; \
}

CloneSIDL_impl(bool, sidl_bool, PyArray_INT)
CloneSIDL_impl(char, char, PyArray_CHAR)
CloneSIDL_impl(double, double, PyArray_DOUBLE)
CloneSIDL_impl(dcomplex, struct sidl_dcomplex, PyArray_CDOUBLE)
CloneSIDL_impl(fcomplex, struct sidl_fcomplex, PyArray_CFLOAT)
CloneSIDL_impl(float, float, PyArray_FLOAT)
#if SIZEOF_SHORT == 4
CloneSIDL_impl(int, int32_t, PyArray_SHORT)
#else
#if SIZEOF_INT == 4
CloneSIDL_impl(int, int32_t, PyArray_INT)
#else
#if SIZEOF_LONG == 4
CloneSIDL_impl(int, int32_t, PyArray_LONG)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
CloneSIDL_impl(int, int32_t, NPY_INT32)
#else
#error No 32-bit integer type available.
#endif
#endif
#endif
#endif

#if SIZEOF_SHORT == 8
CloneSIDL_impl(long, int64_t, PyArray_SHORT)
#else
#if SIZEOF_INT == 8
CloneSIDL_impl(long, int64_t, PyArray_INT)
#else
#if SIZEOF_LONG == 8
CloneSIDL_impl(long, int64_t, PyArray_LONG)
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
CloneSIDL_impl(long, int64_t, NPY_INT64)
#else
static int
getAndConvertLong(void *array,
                  const int32_t *ind,
                  PyObject **dest)
{
  int64_t val =
    sidl_long__array_get((struct sidl_long__array *)array, ind);
  *dest = PyLong_FromLongLong(val);
  return FALSE;
}

static PyObject *
clone_sidl_long_array(struct sidl_long__array *array)
{
  PyObject *pya = NULL;
  if (array) {
    const int dimen = sidl_long__array_dimen(array);
    if (dimen <= SIDL_MAX_ARRAY_DIMENSION) {
      int i;
      int32_t lower[SIDL_MAX_ARRAY_DIMENSION];
      int32_t numelem[SIDL_MAX_ARRAY_DIMENSION];
      SIDL_PY_DIM_T pynumelem[SIDL_MAX_ARRAY_DIMENSION];
      for(i = 0 ; i < dimen; ++i ){
        lower[i] = sidl_long__array_lower(array, i);
        numelem[i] = sidlLength(array, i);
	pynumelem[i] = (SIDL_PY_DIM_T) numelem[i];
      }
      pya = SIDL_PY_SIMPLE_ALLOC(dimen, pynumelem, PyArray_OBJECT);
      if (pya) {
        if (!sidl_array__convert_sidl(pya, dimen, lower, 
                                      array->d_metadata.d_upper,
                                      numelem, array, getAndConvertLong)) {
          Py_DECREF(pya);
          pya = NULL;
        }
      }
    }
    else {
        PyErr_SetString(PyExc_ValueError,
                        "The SIDL array has more than 7 dimensions.");
    }
  }
  else {
    Py_INCREF(Py_None);
    pya = Py_None;
  }
  return pya;
}
#endif
#endif
#endif
#endif

static int
getAndConvertString(void *array, const int32_t *ind, PyObject **dest)
{
  char *str = 
    sidl_string__array_get((struct sidl_string__array *)array, ind);
  if (str) {
    *dest = PyString_FromString(str); 
    free(str);
  }
  else {
    Py_INCREF(Py_None);
    *dest = Py_None;
  }
  return FALSE;
}

static PyObject *
clone_sidl_string_array(struct sidl_string__array *array)
{
  PyObject *pya = NULL;
  if (array) {
    const int dimen = sidl_string__array_dimen(array);
    int i;
    int32_t *lower = malloc(sizeof(int32_t) * dimen);
    int32_t *upper = malloc(sizeof(int32_t) * dimen);
    int32_t *numelem = malloc(sizeof(int32_t) * dimen);
    SIDL_PY_DIM_T *pynumelem = malloc(sizeof(SIDL_PY_DIM_T) * dimen);
    for(i = 0; i < dimen; ++i) {
      lower[i] = sidl_string__array_lower(array, i);
      upper[i] = sidl_string__array_upper(array, i);
      numelem[i] = sidl_string__array_length(array, i);
      pynumelem[i] = (SIDL_PY_DIM_T)numelem[i];
    }
    pya = SIDL_PY_SIMPLE_ALLOC(dimen, pynumelem, PyArray_OBJECT);
    if (pya) {
      if (!sidl_array__convert_sidl(pya, dimen, lower, upper, 
                                    numelem, array, getAndConvertString)) {
        Py_DECREF(pya);
        pya = NULL;
      }
    }
    free(pynumelem);
    free(numelem);
    free(upper);
    free(lower);
  }
  else {
    Py_INCREF(Py_None);
    pya = Py_None;
  }
  return pya;
}

static int
getAndConvertOpaque(void *array, const int32_t *ind, PyObject **dest)
{
  void *vptr =
    sidl_opaque__array_get((struct sidl_opaque__array *)array, ind);
  if (vptr) {
    *dest = PyCObject_FromVoidPtr(vptr, NULL);
  }
  else {
    Py_INCREF(Py_None);
    *dest = Py_None;
  }
  return FALSE;
}

static PyObject *
clone_sidl_opaque_array(struct sidl_opaque__array *array)
{
  PyObject *pya = NULL;
  if (array) {
    const int dimen = sidl_opaque__array_dimen(array);
    int i;
    int32_t *lower = malloc(sizeof(int32_t) * dimen);
    int32_t *upper = malloc(sizeof(int32_t) * dimen);
    int32_t *numelem = malloc(sizeof(int32_t) * dimen);
    SIDL_PY_DIM_T *pynumelem = malloc(sizeof(SIDL_PY_DIM_T) * dimen);
    for(i = 0; i < dimen; ++i) {
      lower[i] = sidl_opaque__array_lower(array, i);
      upper[i] = sidl_opaque__array_upper(array, i);
      numelem[i] = 1 + upper[i] - lower[i];
      pynumelem[i] = (SIDL_PY_DIM_T)numelem[i];
    }
    pya = SIDL_PY_SIMPLE_ALLOC(dimen, pynumelem, PyArray_OBJECT);
    if (pya) {
      if (!sidl_array__convert_sidl(pya, dimen, lower, upper,
                                    numelem, array, getAndConvertOpaque)) {
        Py_DECREF(pya);
        pya = NULL;
      }
    }
    free(pynumelem);
    free(numelem);
    free(upper);
    free(lower);
  }
  else {
    Py_INCREF(Py_None);
    pya = Py_None;
  }
  return pya;
}

sidl_python_clone_array_RETURN
sidl_python_clone_array sidl_python_clone_array_PROTO
{
  if (array) {
    switch (sidl__array_type(array)) {
    case sidl_bool_array:
      return clone_sidl_bool_array((struct sidl_bool__array *)array);
    case sidl_char_array:
      return clone_sidl_char_array((struct sidl_char__array *)array);
    case sidl_dcomplex_array:
      return clone_sidl_dcomplex_array((struct sidl_dcomplex__array *)array);
    case sidl_double_array:
      return clone_sidl_double_array((struct sidl_double__array *)array);
    case sidl_fcomplex_array:
      return clone_sidl_fcomplex_array((struct sidl_fcomplex__array *)array);
    case sidl_float_array:
      return clone_sidl_float_array((struct sidl_float__array *)array);
    case sidl_int_array:
      return clone_sidl_int_array((struct sidl_int__array *)array);
    case sidl_long_array:
      return clone_sidl_long_array((struct sidl_long__array *)array);
    case sidl_opaque_array:
      return clone_sidl_opaque_array((struct sidl_opaque__array *)array);
    case sidl_string_array:
      return clone_sidl_string_array((struct sidl_string__array *)array);
    default:
      return NULL;                /* indicate an error */
    }
  }
  else {
    Py_INCREF(Py_None);
    return Py_None;
  }
}

sidl_python_copy_RETURN
sidl_python_copy sidl_python_copy_PROTO
{
  const int32_t src_type = sidl__array_type(src);
  const int32_t dest_type = sidl__array_type(dest);
  const int32_t srcDimen = sidlArrayDim(src);
  const int32_t destDimen = sidlArrayDim(dest);
  if ((src != dest) && (src_type == dest_type) && (srcDimen == destDimen)) {
    int i;
    int32_t numElem[SIDL_MAX_ARRAY_DIMENSION];
    int32_t newStart[SIDL_MAX_ARRAY_DIMENSION];
    for(i = 0 ; i < srcDimen; ++i) {
      newStart[i] = 0;
      numElem[i] = ((sidlLength(src, i) < sidlLength(dest,i)) ?
                    sidlLength(src, i) : sidlLength(dest, i));
    }
    switch(src_type) {
    case sidl_bool_array:
      {
        struct sidl_bool__array *psrc = 
          sidl_bool__array_slice((struct sidl_bool__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_bool__array *pdest =
          sidl_bool__array_slice((struct sidl_bool__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_bool__array_copy(psrc, pdest);
        sidl_bool__array_deleteRef(psrc);
        sidl_bool__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_char_array:
      {
        struct sidl_char__array *psrc = 
          sidl_char__array_slice((struct sidl_char__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_char__array *pdest =
          sidl_char__array_slice((struct sidl_char__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_char__array_copy(psrc, pdest);
        sidl_char__array_deleteRef(psrc);
        sidl_char__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_dcomplex_array:
      {
        struct sidl_dcomplex__array *psrc = 
          sidl_dcomplex__array_slice((struct sidl_dcomplex__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_dcomplex__array *pdest =
          sidl_dcomplex__array_slice((struct sidl_dcomplex__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_dcomplex__array_copy(psrc, pdest);
        sidl_dcomplex__array_deleteRef(psrc);
        sidl_dcomplex__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_double_array:
      {
        struct sidl_double__array *psrc = 
          sidl_double__array_slice((struct sidl_double__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_double__array *pdest =
          sidl_double__array_slice((struct sidl_double__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_double__array_copy(psrc, pdest);
        sidl_double__array_deleteRef(psrc);
        sidl_double__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_fcomplex_array:
      {
        struct sidl_fcomplex__array *psrc = 
          sidl_fcomplex__array_slice((struct sidl_fcomplex__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_fcomplex__array *pdest =
          sidl_fcomplex__array_slice((struct sidl_fcomplex__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_fcomplex__array_copy(psrc, pdest);
        sidl_fcomplex__array_deleteRef(psrc);
        sidl_fcomplex__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_float_array:
      {
        struct sidl_float__array *psrc = 
          sidl_float__array_slice((struct sidl_float__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_float__array *pdest =
          sidl_float__array_slice((struct sidl_float__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_float__array_copy(psrc, pdest);
        sidl_float__array_deleteRef(psrc);
        sidl_float__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_int_array:
      {
        struct sidl_int__array *psrc = 
          sidl_int__array_slice((struct sidl_int__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_int__array *pdest =
          sidl_int__array_slice((struct sidl_int__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_int__array_copy(psrc, pdest);
        sidl_int__array_deleteRef(psrc);
        sidl_int__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_long_array:
      {
        struct sidl_long__array *psrc = 
          sidl_long__array_slice((struct sidl_long__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_long__array *pdest =
          sidl_long__array_slice((struct sidl_long__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_long__array_copy(psrc, pdest);
        sidl_long__array_deleteRef(psrc);
        sidl_long__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_opaque_array:
      {
        struct sidl_opaque__array *psrc = 
          sidl_opaque__array_slice((struct sidl_opaque__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_opaque__array *pdest =
          sidl_opaque__array_slice((struct sidl_opaque__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_opaque__array_copy(psrc, pdest);
        sidl_opaque__array_deleteRef(psrc);
        sidl_opaque__array_deleteRef(pdest);
                                 
      }
      break;
    case sidl_string_array:
      {
        struct sidl_string__array *psrc = 
          sidl_string__array_slice((struct sidl_string__array *)src,
                                 srcDimen, numElem, NULL, NULL,
                                 newStart);
        struct sidl_string__array *pdest =
          sidl_string__array_slice((struct sidl_string__array *)dest,
                                destDimen, numElem, NULL, NULL,
                                newStart);
        sidl_string__array_copy(psrc, pdest);
        sidl_string__array_deleteRef(psrc);
        sidl_string__array_deleteRef(pdest);
                                 
      }
      break;
    }
  }
}

sidl_python_ensure_RETURN
sidl_python_ensure sidl_python_ensure_PROTO
{
  struct sidl__array *result = NULL;
  if (src) {
    const int order =(columnOrdering 
                      ? sidl_column_major_order 
                      : sidl_row_major_order);
    switch(sidl__array_type(src)){
    case sidl_bool_array:
      result = (struct sidl__array*)
        sidl_bool__array_ensure((struct sidl_bool__array*)src,
                                sidlArrayDim(src),order);
      break;
    case sidl_char_array:
      result = (struct sidl__array*)
        sidl_char__array_ensure((struct sidl_char__array*)src,
                                sidlArrayDim(src),order);
      break;
    case sidl_dcomplex_array:
      result = (struct sidl__array*)
        sidl_dcomplex__array_ensure((struct sidl_dcomplex__array*)src,
                                    sidlArrayDim(src),order);
      break;
    case sidl_double_array:
      result = (struct sidl__array*)
        sidl_double__array_ensure((struct sidl_double__array*)src,
                                  sidlArrayDim(src),order);
      break;
    case sidl_fcomplex_array:
      result = (struct sidl__array*)
        sidl_fcomplex__array_ensure((struct sidl_fcomplex__array*)src,
                                    sidlArrayDim(src),order);
      break;
    case sidl_float_array:
      result = (struct sidl__array*)
        sidl_float__array_ensure((struct sidl_float__array*)src,
                                 sidlArrayDim(src),order);
      break;
    case sidl_int_array:
      result = (struct sidl__array*)
        sidl_int__array_ensure((struct sidl_int__array*)src,
                               sidlArrayDim(src),order);
      break;
    case sidl_long_array:
      result = (struct sidl__array*)
        sidl_long__array_ensure((struct sidl_long__array*)src,
                                sidlArrayDim(src),order);
      break;
    case sidl_opaque_array:
      result = (struct sidl__array*)
        sidl_opaque__array_ensure((struct sidl_opaque__array*)src,
                                  sidlArrayDim(src),order);
      break;
    case sidl_string_array:
      result = (struct sidl__array*)
        sidl_string__array_ensure((struct sidl_string__array*)src,
                                  sidlArrayDim(src),order);
      break;
    case sidl_interface_array:
      result = (struct sidl__array*)
        sidl_interface__array_ensure((struct sidl_interface__array*)src,
                                     sidlArrayDim(src),order);
      break;
    }
    sidl__array_deleteRef(src);
  }
  return result;
}

sidl_array__convert_sidl_RETURN
sidl_array__convert_sidl sidl_array__convert_sidl_PROTO
{
  if (PyArray_Check(pya_dest)) {
    PyArrayObject *pya = (PyArrayObject *)pya_dest;
    size_t size = arraySize(numelem, dimen, 1);
    int i;
    char *dest = pya->data;
    while (size--) {
      if ((*getfunc)(sidl_src, lower, (PyObject **)dest))
        return FALSE;
      for(i = 0; i < dimen; ++i) {
        dest += pya->strides[i];
        if (++(lower[i]) <= upper[i]) {
          break;
        }
        else {
          dest -= pya->strides[i]*numelem[i];
          lower[i] -= numelem[i];
        }
      }
    }
    return TRUE;
  }
  return FALSE;
}

static PyObject*
createArrayHolder(struct sidl__array * const array)
{
  PyObject *result = sidlPyArrayType.tp_new(&sidlPyArrayType, NULL, NULL);
  if (result) {
    SIDLArrayObject *sao = (SIDLArrayObject *)result;  
    sao->d_array = array;
  }
  return result;
}

static PyObject *
borrow_sidl(struct sidl__array * const array,
            char * const dataPtr,
            const size_t dataSize,
            const int numpyType)
{
  const int dimen = sidlArrayDim(array);
  int i;
  SIDL_PY_DIM_T extent[SIDL_MAX_ARRAY_DIMENSION];
  PyObject *result;
  for(i = 0; i < dimen; ++i) {
    extent[i] = sidlLength(array, i);
  }
  result = SIDL_PY_DATA_ALLOC(dimen, extent, numpyType, dataPtr);
  if (result) {
    PyObject *sidlRef = createArrayHolder(array);
    if (sidlRef) {
      PyArrayObject *numpy = (PyArrayObject *)result;
      /* fix the strides to match the SIDL array */
      for(i = 0; i < dimen; ++i) {
        numpy->strides[i] = sidlStride(array,i) * dataSize;
      }
      /* set the CONTIGUOUS flag */
      if (sidl__array_isRowOrder(array)){
        numpy->flags |= CONTIGUOUS;
      }
      else {
        numpy->flags &= ~CONTIGUOUS;
      }
      numpy->base = sidlRef;
    }
    else {
      sidl__array_deleteRef(array);
      Py_DECREF(result);
      result = NULL;
    }
  }
  else {
    sidl__array_deleteRef(array);
  }
  return result;
}

sidl_python_borrow_array_RETURN
sidl_python_borrow_array sidl_python_borrow_array_PROTO
{ 
  if (array) {
    switch(sidl__array_type(array)) {
    case sidl_char_array:
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)(((struct sidl_char__array*)array)->d_firstElement),
                         sizeof(char), PyArray_CHAR);
    case sidl_dcomplex_array:
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)(((struct sidl_dcomplex__array*)array)->d_firstElement),
                          sizeof(struct sidl_dcomplex), PyArray_CDOUBLE);
    case sidl_double_array:
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_double__array*)array)->d_firstElement,
                         sizeof(double), PyArray_DOUBLE);
    case sidl_fcomplex_array:
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)(((struct sidl_fcomplex__array*)array)->d_firstElement),
                          sizeof(struct sidl_fcomplex), PyArray_CFLOAT);
    case sidl_float_array:
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_float__array*)array)->d_firstElement,
                         sizeof(float), PyArray_FLOAT);
    case sidl_int_array:
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_int__array*)array)->d_firstElement,
                         sizeof(int32_t),
#if SIZEOF_SHORT == 4
                         PyArray_SHORT
#else
#if SIZEOF_INT == 4
                         PyArray_INT
#else
#if SIZEOF_LONG == 4
                         PyArray_LONG
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
                         NPY_INT32
#else
#error No 32-bit integer available.
#endif
#endif
#endif
#endif
                         );
    case sidl_long_array:
#if SIZEOF_SHORT == 8
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_long__array*)array)->d_firstElement,
                         sizeof(int64_t),
                         PyArray_SHORT);
#else
#if SIZEOF_INT == 8
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_long__array*)array)->d_firstElement,
                         sizeof(int64_t),
                         PyArray_INT);
#else
#if SIZEOF_LONG == 8
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_long__array*)array)->d_firstElement,
                         sizeof(int64_t),
                         PyArray_LONG);
#else
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
      array = sidl__array_smartCopy(array);
      return borrow_sidl(array,
                         (char *)((struct sidl_long__array*)array)->d_firstElement,
                         sizeof(int64_t),
                         NPY_INT64);
#else
      return sidl_python_clone_array(array); 
#endif
#endif
#endif
#endif
    }
  }
  return sidl_python_clone_array(array); 
}

sidl_generic_borrow_python_array_RETURN
sidl_generic_borrow_python_array sidl_generic_borrow_python_array_PROTO
{
  if (PyArray_Check(obj)) {
    PyArrayObject *pya = (PyArrayObject *)obj;
    register const int typenum = pya->descr->type_num;
    if (typenum == PyArray_FLOAT) {
      return sidl_float__borrow_python_array
        (obj, (struct sidl_float__array **)array);
    }
    else if (typenum == PyArray_DOUBLE) {
      return sidl_double__borrow_python_array
        (obj, (struct sidl_double__array **)array);
    }
    else if (typenum == PyArray_CFLOAT) {
      return sidl_fcomplex__borrow_python_array
        (obj, (struct sidl_fcomplex__array **)array);
    }
    else if (typenum == PyArray_CDOUBLE) {
      return sidl_dcomplex__borrow_python_array
        (obj, (struct sidl_dcomplex__array **)array);
    }
    else if (0
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT32)
             || (typenum == NPY_INT32)
#endif
#if SIZEOF_SHORT == 4
             || (typenum == PyArray_SHORT)
#ifdef PyArray_UNSIGNED_TYPES
             || (typenum == PyArray_USHORT)
#endif
#endif
#if SIZEOF_INT == 4
             || (typenum == PyArray_INT)
#ifdef PyArray_UNSIGNED_TYPES
             || (typenum == PyArray_UINT)
#endif
#endif
#if SIZEOF_LONG == 4
             || (typenum == PyArray_LONG)
#endif
             ) {
      return sidl_int__borrow_python_array
        (obj, (struct sidl_int__array **)array);
    }
    else if (0 
#if defined(SIDL_HAVE_NUMPY) && defined(NPY_INT64)
             || (typenum == NPY_INT64)
#endif
#if SIZEOF_SHORT == 8
             || (typenum == PyArray_SHORT)
#ifdef PyArray_UNSIGNED_TYPES
             || (typenum == PyArray_USHORT)
#endif
#endif
#if SIZEOF_INT == 8
             || (typenum == PyArray_INT)
#ifdef PyArray_UNSIGNED_TYPES
             || (typenum == PyArray_UINT)
#endif
#endif
#if SIZEOF_LONG == 8
             || (typenum == PyArray_LONG)
#endif
             ) {
      return sidl_long__borrow_python_array
        (obj, (struct sidl_long__array **)array);
    }
    else if ( (typenum == PyArray_CHAR)
         || (typenum == PyArray_UBYTE)
         || (typenum == PyArray_SBYTE)
#if defined(SIDL_HAVE_NUMPY)
         || (typenum == NPY_STRING)
#endif
         ) {
      return sidl_char__borrow_python_array
        (obj, (struct sidl_char__array **)array);
    }
    else {
      return sidl_generic_clone_python_array(obj, array);
    }
  }
  return sidl_generic_clone_python_array(obj, array);
}

static int
convertObjectToBaseInterface(void *sidlarray, const int *ind, PyObject *pyobj)
{
  struct sidl_BaseInterface__object *sidlobj =
    sidl_Cast(pyobj, "sidl.BaseInterface");
  if (sidlobj || (Py_None == pyobj)) {
    sidl_interface__array_set((struct sidl_interface__array *)sidlarray,
                              ind, sidlobj);
    return FALSE;
  }
  return TRUE;
}

static
int
clone_object_array(PyArrayObject *pya, struct sidl__array **array)
{
  int result = 0;
  if (PyArray_Size((PyObject *)pya)) {
    PyObject *elem = *((PyObject **)(pya->data));
    if ((elem == Py_None) || 
        PyType_IsSubtype(Py_TYPE(elem), sidl_PyType())) {
      /* an array of sidl objects */
      int32_t dimen;
      int32_t lower[SIDL_MAX_ARRAY_DIMENSION], 
        upper[SIDL_MAX_ARRAY_DIMENSION], 
        stride[SIDL_MAX_ARRAY_DIMENSION];
      if (sidl_array__extract_python_info((PyObject *)pya, &dimen,
                                          lower, upper, stride)) {
        *array = (struct sidl__array *)
          sidl_interface__array_createRow(dimen, lower, upper);
        result = sidl_array__convert_python
          ((PyObject *)pya, dimen, *array, convertObjectToBaseInterface);
        if (*array && !result ) {
          sidl__array_deleteRef(*array);
          *array = NULL;
        }
      }
    }
    else {
      /* otherwise treat it as an array of longs */
      result = sidl_long__clone_python_array
        ((PyObject *)pya, (struct sidl_long__array **)array);
    }
  }
  else { /* empty list of what? */
    result = sidl_int__clone_python_array
      ((PyObject *)pya, (struct sidl_int__array **)array);
  }
  return result;
}

sidl_generic_clone_python_array_RETURN
sidl_generic_clone_python_array sidl_generic_clone_python_array_PROTO
{
  int result = 0;
  *array = 0;
  if (obj == Py_None) {
    result = 1;
  }
  else {
    PyArrayObject *pya = NULL;
    if (PyArray_Check(obj)) {
      pya = (PyArrayObject *)obj;
      Py_INCREF(pya);
    }
    else {
      pya = (PyArrayObject *)
        PyArray_FromObject(obj, PyArray_NOTYPE, 0, 0);
    }
    if (pya) {
      switch (pya->descr->type_num) {
      case PyArray_CHAR:
      case PyArray_UBYTE:
      case PyArray_SBYTE:
#ifdef SIDL_HAVE_NUMPY
    case NPY_STRING:
#endif
        result = sidl_char__clone_python_array
          (obj, (struct sidl_char__array **)array);
        break;
      case PyArray_SHORT:
#ifdef PyArray_UNSIGNED_TYPES
      case PyArray_USHORT:
#endif
#if SIZEOF_SHORT == 4
        result = sidl_int__clone_python_array
          (obj, (struct sidl_int__array **)array);
#else
#if SIZEOF_SHORT == 8
        result = sidl_long__clone_python_array
          (obj, (struct sidl_long__array **)array);
#endif
#endif
        break;
      case PyArray_INT:
#ifdef PyArray_UNSIGNED_TYPES
      case PyArray_UINT:
#endif
#if SIZEOF_INT == 4
        result = sidl_int__clone_python_array
          (obj, (struct sidl_int__array **)array);
#else
#if SIZEOF_INT == 8
        result = sidl_long__clone_python_array
          (obj, (struct sidl_long__array **)array);
#endif
#endif
        break; /* prevent default or fall through */
      case PyArray_LONG:
#if SIZEOF_LONG == 4
        result = sidl_int__clone_python_array
          (obj, (struct sidl_int__array **)array);
#else
#if SIZEOF_LONG == 8
        result = sidl_long__clone_python_array
          (obj, (struct sidl_long__array **)array);
#endif
#endif
        break; /* prevent default and fall through */
      case PyArray_FLOAT:
        result = sidl_float__clone_python_array
          (obj, (struct sidl_float__array **)array);
      case PyArray_DOUBLE:
        result = sidl_double__clone_python_array
          (obj, (struct sidl_double__array **)array);
      case PyArray_CFLOAT:
        result = sidl_fcomplex__clone_python_array
          (obj, (struct sidl_fcomplex__array **)array);
      case PyArray_CDOUBLE:
        result = sidl_dcomplex__clone_python_array
          (obj, (struct sidl_dcomplex__array **)array);
      case PyArray_OBJECT:
        result =  clone_object_array(pya, array);
      default:
        *array = 0;
        result = 0;
      }
      Py_DECREF(pya);
    }
  }
  return result;
}


static int
parseOrdering(PyObject *ordering,
              int *columnOrder)
{
  PyObject *strvalue = PyObject_Str(ordering);
  Py_XDECREF(ordering);
  *columnOrder = -1;
  if (strvalue) {
    const char *val = PyString_AsString(strvalue);
    if (!strcmp(val, "column")) {
      *columnOrder = 1;
    }
    else if (!strcmp(val, "row")) {
      *columnOrder = 0;
    }
    Py_DECREF(strvalue);
    if (-1 == *columnOrder) {
      PyErr_SetString(PyExc_ValueError,
                      "The ordering argument must be \"column\" or \"row\".");
    }
  }
  else {
    PyErr_SetString(PyExc_TypeError, 
                    "The ordering argument must be \"column\" or \"row\".");
  }
  return *columnOrder != -1;
}

static int
parseShape(PyObject *shape,
           int32_t *extents,
           int *dimen)
{
  int okay = 0;
  *dimen = 0;
  if (PySequence_Check(shape)) {
    const int len = PySequence_Length(shape);
    int i;
    for(i = 0; (i < len) && (i < 7); ++i) {
      PyObject *val = PySequence_GetItem(shape, i);
#if PY_MAJOR_VERSION < 3
      long ival = PyInt_AsLong(val);
#else
      long ival = PyLong_AsLong(val);
#endif
      if (ival == -1 && PyErr_Occurred()) {
        Py_XDECREF(val); okay = -1; break;
      }
      else {
        extents[i] = (int32_t)ival;
      }
      Py_XDECREF(val);
    }
    okay = (okay == 0) && (i == len) && (i > 0);
    Py_XDECREF(shape);
    if (okay) {
      *dimen = len;
    }
    else {
      if (!PyErr_Occurred()) {
        PyErr_SetString(PyExc_ValueError,
                        "The shape argument must be a list/tuple of 1 to 7 integers.");
      }
    }
  }
  else {
    Py_XDECREF(shape);
    PyErr_SetString(PyExc_TypeError,
                    "The shape argument must be a list or tuple.");
  }
  return okay;
}

static int
parseInitialValue(PyObject *iszero,
                  PyObject **initialValue)
{
  int okay = (iszero != NULL);
  *initialValue = iszero;
  return okay;
}

static int
parseDict(PyObject *keywords,
          int32_t *extents,
          int *dimen,
          int *columnOrder,
          PyObject **initialValue)
{
  int okay = 0;
  if (PyMapping_Check(keywords)) {
    const int len = PyMapping_Length(keywords);
    int counted = 0;
    okay = 1;
    if (extents && dimen) {
      PyObject *shape = PyMapping_GetItemString(keywords, "shape");
      if (shape) {
        if (parseShape(shape, extents, dimen)) {
          ++counted;
        }
        else {
          okay = 0;
        }
      }
      else {
        PyErr_Clear(); /* clear the key error from GetItemString failure */
        okay = 0;
        PyErr_SetString(PyExc_ValueError,
                        "Missing required shape argument.");
      }
    }
    if (okay && columnOrder) {
      PyObject *ordering = PyMapping_GetItemString(keywords, "ordering");
      if (ordering) {
        if (parseOrdering(ordering, columnOrder)) {
          ++counted;
        }
        else {
          okay = 0;
        }
      }
      else {
        PyErr_Clear(); /* clear the key error from GetItemString failure */
      }
    }
    if (okay && initialValue) {
      PyObject *value = PyMapping_GetItemString(keywords, "value");
      if (value) {
        parseInitialValue(value, initialValue);
        ++counted;
      }
      else {
        PyErr_Clear(); /* clear the key error from GetItemString failure */
      }
    }
    if (okay && (counted != len)) {
      okay = 0;
      PyErr_SetString(PyExc_ValueError,
                      "Wrong number of arguments.");
    }
  }
  else {
    if (extents) {
      PyErr_SetString(PyExc_ValueError,
                      "Missing required shape argument.");
    }
    else {
      okay = 1;
    }
  }
  return okay;
}

static int
parseShapeAndOrder(PyObject *args,
                   PyObject *keywords,
                   int32_t *extents,
                   int *dimen,
                   int *columnOrder,
                   PyObject **initialValue)
{
  if (PySequence_Check(args)) {
    switch(PySequence_Length(args)) {
    case 0:
      return parseDict(keywords, extents, dimen, columnOrder, initialValue);
    case 1:
      return
        parseShape(PySequence_GetItem(args, 0), extents, dimen) &&
        parseDict(keywords, NULL, NULL, columnOrder, initialValue);
    case 2:
      return 
        parseShape(PySequence_GetItem(args, 0), extents, dimen) &&
        parseOrdering(PySequence_GetItem(args, 1), columnOrder) &&
        parseDict(keywords, NULL, NULL, NULL, initialValue);
    case 3:
      return 
        parseShape(PySequence_GetItem(args, 0), extents, dimen) &&
        parseOrdering(PySequence_GetItem(args, 1), columnOrder) &&
        parseInitialValue(PySequence_GetItem(args, 2), initialValue) &&
        parseDict(keywords, NULL, NULL, NULL, NULL);
    default:
      PyErr_SetString(PyExc_ValueError, "Wrong number of arguments");
      return 0;
    }
  }
  else {
    return parseDict(keywords, extents, dimen, columnOrder, initialValue);
  }
}

static int
parseShapeAndValue(PyObject *args,
                   PyObject *keywords,
                   SIDL_PY_DIM_T *extents,
                   int *dimen,
                   PyObject **initialValue)
{
  int result = 0;
  int32_t sidlExtents[SIDL_MAX_ARRAY_DIMENSION];
  if (PySequence_Check(args)) {
    switch(PySequence_Length(args)) {
    case 0:
      result = parseDict(keywords, sidlExtents, dimen, NULL, initialValue);
      break;
    case 1:
      result = 
        parseShape(PySequence_GetItem(args, 0), sidlExtents, dimen) &&
        parseDict(keywords, NULL, NULL, NULL, initialValue);
      break;
    case 2:
      result =  
        parseShape(PySequence_GetItem(args, 0), sidlExtents, dimen) &&
        parseInitialValue(PySequence_GetItem(args, 1), initialValue) &&
        parseDict(keywords, NULL, NULL, NULL, NULL);
      break;
    default:
      PyErr_SetString(PyExc_ValueError, "Wrong number of arguments");
      result = 0;
      break;
    }
  }
  else {
    result = parseDict(keywords, sidlExtents, dimen, NULL, initialValue);
  }
  extents[0] = (SIDL_PY_DIM_T)sidlExtents[0];  extents[1] = (SIDL_PY_DIM_T)sidlExtents[1];
  extents[2] = (SIDL_PY_DIM_T)sidlExtents[2];  extents[3] = (SIDL_PY_DIM_T)sidlExtents[3];
  extents[4] = (SIDL_PY_DIM_T)sidlExtents[4];  extents[5] = (SIDL_PY_DIM_T)sidlExtents[5];
  extents[6] = (SIDL_PY_DIM_T)sidlExtents[6];
  return result;
}

static int32_t
numElem(const struct sidl__array * const array)
{
  int32_t size = ((sidlArrayDim(array) > 0) ? 1 : 0);
  int i;
  for(i = 0; i < sidlArrayDim(array); ++i) size *= sidlLength(array, i);
  return size;
}

static void
initializecharArray(struct sidl_char__array *array,
                      PyObject *initialValue)
{
  PyObject *strvalue = PyObject_Str(initialValue);
  if (strvalue) {
    const char *str = PyString_AsString(strvalue);
    const char val = *str;
    memset(array->d_firstElement, val, numElem((struct sidl__array*)array));
    Py_DECREF(strvalue);
  }
}

static void
initializedoubleArray(struct sidl_double__array *array,
                      PyObject *initialValue)
{
  const double value = PyFloat_AsDouble(initialValue);
  if (!((value == -1) && PyErr_Occurred())) {
    int32_t num = numElem((struct sidl__array *)array);
    double *ptr = array->d_firstElement;
    if (value == 0) memset(ptr, 0, sizeof(double)*num);
    else {
      while (num-- > 0) {
        *(ptr++) = value;
      }
    }
  }
}

static void
initializefloatArray(struct sidl_float__array *array,
                     PyObject *initialValue)
{
  const double value = PyFloat_AsDouble(initialValue);
  if (!((value == -1) && PyErr_Occurred())) {
    const float fvalue = (float)value;
    int32_t num = numElem((struct sidl__array *)array);
    float *ptr = array->d_firstElement;
    if (value == 0) memset(ptr, 0, sizeof(float)*num);
    else {
      while (num-- > 0) {
        *(ptr++) = fvalue;
      }
    }
  }
}

static void
initializeintArray(struct sidl_int__array *array,
                     PyObject *initialValue)
{
#if PY_MAJOR_VERSION < 3
  const long value= PyInt_AsLong(initialValue);
#else
  const long value= PyLong_AsLong(initialValue);
#endif
  if (!((value == -1) && PyErr_Occurred())) {
    const int32_t ivalue = (int32_t)value;
    int32_t num = numElem((struct sidl__array *)array);
    int32_t *ptr = array->d_firstElement;
    if (value == 0) memset(ptr, 0, sizeof(int32_t)*num);
    else {
      while (num-- > 0) {
        *(ptr++) = ivalue;
      }
    }
  }
}

static void
initializelongArray(struct sidl_long__array *array,
                    PyObject *initialValue)
{
#if defined(HAVE_LONG_LONG)
  const int64_t value= (int64_t)PyLong_AsLongLong(initialValue);
#else
  const int64_t value = (int64_t)PyLong_AsLong(initialValue);
#endif
  if (!((value == -1) && PyErr_Occurred())) {
    int32_t num = numElem((struct sidl__array *)array);
    int64_t *ptr = array->d_firstElement;
    if (value == 0) memset(ptr, 0, sizeof(int64_t)*num);
    else {
      while (num-- > 0) {
        *(ptr++) = value;
      }
    }
  }
}

static void
initializedcomplexArray(struct sidl_dcomplex__array *array,
                        PyObject *initialValue)
{
  struct sidl_dcomplex value;
  value.real = PyComplex_RealAsDouble(initialValue);
  if (!((value.real != -1) && PyErr_Occurred())) {
    value.imaginary = PyComplex_ImagAsDouble(initialValue);
    if (!PyErr_Occurred()) {
      int32_t num = numElem((struct sidl__array *)array);
      double *ptr = (double *)array->d_firstElement;
      if ((value.real == 0) && (value.imaginary == 0))
        memset(ptr, 0, sizeof(double)*2*num);
      else {
        while (num-- > 0) {
          *ptr = value.real;
          ptr[1] = value.imaginary;
          ptr += 2;
        }
      }
    }
  }
}

static void
initializefcomplexArray(struct sidl_fcomplex__array *array,
                        PyObject *initialValue)
{
  struct sidl_fcomplex value;
  value.real = (float)PyComplex_RealAsDouble(initialValue);
  if (!((value.real != -1) && PyErr_Occurred())) {
    value.imaginary = (float)PyComplex_ImagAsDouble(initialValue);
    if (!PyErr_Occurred()) {
      int32_t num = numElem((struct sidl__array *)array);
      float *ptr = (float *)array->d_firstElement;
      if ((value.real == 0) && (value.imaginary == 0))
        memset(ptr, 0, sizeof(float)*2*num);
      else {
        while (num-- > 0) {
          *ptr = value.real;
          ptr[1] = value.imaginary;
          ptr += 2;
        }
      }
    }
  }
}

#define createOrderedArray(sidltype, ctype) \
static PyObject * \
sidl_createPy ## sidltype (PyObject *_ignored,  \
                       PyObject *_args, PyObject *_kwdict) { \
  PyObject *result = NULL; \
  PyObject *initialValue = NULL; \
  int columnOrder = 0, dimen; \
  static const int32_t lower[SIDL_MAX_ARRAY_DIMENSION] = {0,0,0,0,0,0,0}; \
  int32_t extents[SIDL_MAX_ARRAY_DIMENSION]; \
  if (parseShapeAndOrder(_args, _kwdict, extents,  \
                         &dimen, &columnOrder, &initialValue)) { \
    struct sidl_ ## sidltype ## __array *array; \
    int i; \
    for(i = 0 ; i < dimen; ++i) --extents[i]; /* covert extents to upper */ \
    array =  \
      (columnOrder ? sidl_ ## sidltype ##__array_createCol(dimen, lower, extents) \
       : sidl_ ## sidltype ## __array_createRow(dimen, lower, extents)); \
    if (initialValue && array) { \
      initialize ## sidltype ## Array(array, initialValue); \
    }\
    if (!PyErr_Occurred()) { \
      result = sidl_python_borrow_array((struct sidl__array *)array); \
    } \
    sidl__array_deleteRef((struct sidl__array *)array); \
  } \
  Py_XDECREF(initialValue); \
  return result; \
}

createOrderedArray(double, double)
createOrderedArray(char, char)
createOrderedArray(float, float)
createOrderedArray(fcomplex, struct sidl_fcomplex)
createOrderedArray(dcomplex, struct sidl_dcomplex)
createOrderedArray(int, int32_t)
createOrderedArray(long, int64_t)

static void
initializeboolArray(PyObject *pya, PyObject *initialValue)
{
  PyArrayObject *array;
  if (PyArray_Check(pya)) {
    const int value = initialValue ? 
      (PyObject_IsTrue(initialValue) ? TRUE : FALSE) : FALSE;
    int numElem = PyArray_Size(pya);
    array = (PyArrayObject *)pya;
    if (!PyErr_Occurred() && (array->flags & CONTIGUOUS)) {
      int *ptr = (int *)array->data;
      while (numElem-- > 0) {
        *(ptr++) = value;
      }
    }
  }
}

static PyObject *
chooseOpaqueValue(PyObject *initialValue)
{
  if (initialValue) {
    return ((initialValue == Py_None)||PyCObject_Check(initialValue)) ?
      initialValue : NULL;
  }
  return Py_None;
}

static void
initializeopaqueArray(PyObject *pya, PyObject *initialValue)
{
  PyArrayObject *array;
  if (PyArray_Check(pya)) {
    initialValue = chooseOpaqueValue(initialValue);
    if (initialValue) {
      int numElem = PyArray_Size(pya);
      array = (PyArrayObject *)pya;
      if (!PyErr_Occurred() && (array->flags & CONTIGUOUS)) {
        PyObject **ptr = (PyObject **)array->data;
        while (numElem-- > 0) {
          Py_INCREF(initialValue);
          *(ptr++) = initialValue;
        }
      }
    }
    else {
      PyErr_SetString(PyExc_TypeError, "Opaque arrays can only be initialized with None or CObject type objects.");
    }
  }
}

static PyObject *
chooseStringValue(PyObject *initialValue)
{
  if (initialValue == Py_None) {
    Py_INCREF(initialValue);
    return initialValue;
  }
  else if (initialValue == NULL) {
    Py_INCREF(Py_None);
    return Py_None;
  }
  return PyObject_Str(initialValue);
}

static void
initializestringArray(PyObject *pya, PyObject *initialValue)
{
  PyArrayObject *array;
  if (PyArray_Check(pya)) {
    initialValue = chooseStringValue(initialValue);
    if (initialValue) {
      int numElem = PyArray_Size(pya);
      array = (PyArrayObject *)pya;
      if (!PyErr_Occurred() && (array->flags & CONTIGUOUS)) {
        PyObject **ptr = (PyObject **)array->data;
        while (numElem-- > 0) {
          Py_INCREF(initialValue);
          *(ptr++) = initialValue;
        }
      }
      Py_DECREF(initialValue);
    }
    else {
      PyErr_SetString(PyExc_TypeError, "String arrays can only be initialized with None or string values.");
    }
  }
}

#define createPythonArray(sidltype, pytype) \
static PyObject * \
sidl_createPy ## sidltype (PyObject *_ignored,  \
                       PyObject *_args, PyObject *_kwdict) { \
  PyObject *result = NULL; \
  PyObject *initialValue = NULL; \
  int dimen; \
  SIDL_PY_DIM_T extents[SIDL_MAX_ARRAY_DIMENSION];	   \
  if (parseShapeAndValue(_args, _kwdict, extents,		   \
                         &dimen, &initialValue)) { \
    result = SIDL_PY_SIMPLE_ALLOC(dimen, extents, pytype); \
    if (result) { \
      initialize ## sidltype ## Array(result, initialValue); \
    }\
  } \
  Py_XDECREF(initialValue); \
  if (result && PyErr_Occurred()) { \
    Py_DECREF(result); \
    result = NULL; \
  } \
  return result; \
}
createPythonArray(bool, PyArray_INT)
createPythonArray(opaque, PyArray_OBJECT)
createPythonArray(string, PyArray_OBJECT)


#define exportMethod(sidltype, capitaltype) \
{ "create" #capitaltype, (PyCFunction)sidl_createPy ## sidltype, \
  ( METH_VARARGS | METH_KEYWORDS), \
  "create" #capitaltype "(shape, [ordering, value])\n\
Create a " #sidltype " array of the shape indicated. If ordering is\n\
\"column\", the array will be allocated in column-major order.\n\
If ordering is \"row\" or unspecified, the array will be allocated\n\
in row-major order. If an ordering other than \"row\" or \"column\"\n\
is specified, a TypeError exception will be thrown. shape is a\n\
tuple or list of extents in each array dimension.\n\
If the value argument is given, the array elements will\n\
be initialized to value; otherwise, the initial value of the\n\
array elements is unpredictable." }

#define exportPyMethod(sidltype, capitaltype) \
{ "create" #capitaltype, (PyCFunction)sidl_createPy ## sidltype, \
  ( METH_VARARGS | METH_KEYWORDS), \
  "create" #capitaltype "(shape, [value])\n\
Create a " #sidltype " array of the shape indicated. shape is a\n\
tuple or list of extents in each array dimension.\n\
If the value argument is given, the array elements will\n\
be initialized to value; otherwise, the initial value of the\n\
array elements is None." }

static struct PyMethodDef spa_methods[] = {
  exportMethod(double, Double),
  exportMethod(float, Float),
  exportMethod(int, Int), 
  exportMethod(long, Long),
  exportMethod(fcomplex, Fcomplex),
  exportMethod(dcomplex, Dcomplex),
  exportMethod(char, Char),
  exportPyMethod(opaque,Opaque),
  exportPyMethod(string,String),
  { "createBool", (PyCFunction)sidl_createPybool, \
  ( METH_VARARGS | METH_KEYWORDS), \
  "createBool(shape, [value])\n\
Create a boolean array of the shape indicated. shape is a\n\
tuple or list of extents in each array dimension.\n\
If the value argument is given, the array elements will\n\
be initialized to value; otherwise, the initial value of the\n\
array elements is false(0)." },
  { NULL, NULL }
};

#if PY_MAJOR_VERSION >= 3
#define MOD_ERROR_VAL NULL
#define MOD_SUCCESS_VAL(val) val
#define MOD_INIT(name) PyMODINIT_FUNC PyInit_##name(void)
#define MOD_DEF(ob, name, doc, methods) \
          static struct PyModuleDef moduledef = { \
            PyModuleDef_HEAD_INIT, name, doc, -1, methods, }; \
          ob = PyModule_Create(&moduledef);
#else
#define MOD_ERROR_VAL
#define MOD_SUCCESS_VAL(val)
#define MOD_INIT(name) void init##name(void)
#define MOD_DEF(ob, name, doc, methods) \
          ob = Py_InitModule3(name, methods, doc);
#endif

MOD_INIT(sidlPyArrays)
{
  PyObject *module, *dict, *c_api;
  static void *spa_api[sidlPyArrays_API_pointers];
  MOD_DEF(module, "sidlPyArrays", "Python SIDL array support", spa_methods);
  if (module == NULL) return MOD_ERROR_VAL;
  import_array();
  dict = PyModule_GetDict(module);
  if (PyType_Ready(&sidlPyArrayType) < 0) return MOD_ERROR_VAL;
  Py_INCREF(&sidlPyArrayType);
  PyModule_AddObject(module, "SIDLArrayWrapper", (PyObject *)&sidlPyArrayType);
  addPythonArrayModule(module);
  spa_api[sidl_python_deleteRef_array_NUM] =
    (void *)sidl__array_deleteRef;
  spa_api[sidl_python_borrow_array_NUM] =
    (void *)sidl_python_borrow_array;
  spa_api[sidl_python_clone_array_NUM] =
    (void *)sidl_python_clone_array;
  spa_api[sidl_generic_clone_python_array_NUM] =
    (void *)sidl_generic_clone_python_array;
  spa_api[sidl_generic_borrow_python_array_NUM] =
    (void *)sidl_generic_borrow_python_array;
  spa_api[sidl_python_copy_NUM] =
    (void *)sidl_python_copy;
  spa_api[sidl_python_ensure_NUM] =
    (void *)sidl_python_ensure;
  spa_api[sidl_bool__borrow_python_array_NUM] =
    (void *)sidl_bool__borrow_python_array;
  spa_api[sidl_bool__clone_python_array_column_NUM] =
    (void *)sidl_bool__clone_python_array_column;
  spa_api[sidl_bool__clone_python_array_row_NUM] =
    (void *)sidl_bool__clone_python_array_row;
  spa_api[sidl_bool__clone_python_array_NUM] =
    (void *)sidl_bool__clone_python_array;
  spa_api[sidl_char__borrow_python_array_NUM] =
    (void *)sidl_char__borrow_python_array;
  spa_api[sidl_char__clone_python_array_column_NUM] =
    (void *)sidl_char__clone_python_array_column;
  spa_api[sidl_char__clone_python_array_row_NUM] =
    (void *)sidl_char__clone_python_array_row;
  spa_api[sidl_char__clone_python_array_NUM] =
    (void *)sidl_char__clone_python_array;
  spa_api[sidl_dcomplex__borrow_python_array_NUM] =
    (void *)sidl_dcomplex__borrow_python_array;
  spa_api[sidl_dcomplex__clone_python_array_column_NUM] =
    (void *)sidl_dcomplex__clone_python_array_column;
  spa_api[sidl_dcomplex__clone_python_array_row_NUM] =
    (void *)sidl_dcomplex__clone_python_array_row;
  spa_api[sidl_dcomplex__clone_python_array_NUM] =
    (void *)sidl_dcomplex__clone_python_array;
  spa_api[sidl_double__borrow_python_array_NUM] =
    (void *)sidl_double__borrow_python_array;
  spa_api[sidl_double__clone_python_array_column_NUM] =
    (void *)sidl_double__clone_python_array_column;
  spa_api[sidl_double__clone_python_array_row_NUM] =
    (void *)sidl_double__clone_python_array_row;
  spa_api[sidl_double__clone_python_array_NUM] =
    (void *)sidl_double__clone_python_array;
  spa_api[sidl_fcomplex__borrow_python_array_NUM] =
    (void *)sidl_fcomplex__borrow_python_array;
  spa_api[sidl_fcomplex__clone_python_array_column_NUM] =
    (void *)sidl_fcomplex__clone_python_array_column;
  spa_api[sidl_fcomplex__clone_python_array_row_NUM] =
    (void *)sidl_fcomplex__clone_python_array_row;
  spa_api[sidl_fcomplex__clone_python_array_NUM] =
    (void *)sidl_fcomplex__clone_python_array;
  spa_api[sidl_float__borrow_python_array_NUM] =
    (void *)sidl_float__borrow_python_array;
  spa_api[sidl_float__clone_python_array_column_NUM] =
    (void *)sidl_float__clone_python_array_column;
  spa_api[sidl_float__clone_python_array_row_NUM] =
    (void *)sidl_float__clone_python_array_row;
  spa_api[sidl_float__clone_python_array_NUM] =
    (void *)sidl_float__clone_python_array;
  spa_api[sidl_int__borrow_python_array_NUM] =
    (void *)sidl_int__borrow_python_array;
  spa_api[sidl_int__clone_python_array_column_NUM] =
    (void *)sidl_int__clone_python_array_column;
  spa_api[sidl_int__clone_python_array_row_NUM] =
    (void *)sidl_int__clone_python_array_row;
  spa_api[sidl_int__clone_python_array_NUM] =
    (void *)sidl_int__clone_python_array;
  spa_api[sidl_long__borrow_python_array_NUM] =
    (void *)sidl_long__borrow_python_array;
  spa_api[sidl_long__clone_python_array_column_NUM] =
    (void *)sidl_long__clone_python_array_column;
  spa_api[sidl_long__clone_python_array_row_NUM] =
    (void *)sidl_long__clone_python_array_row;
  spa_api[sidl_long__clone_python_array_NUM] =
    (void *)sidl_long__clone_python_array;
  spa_api[sidl_opaque__borrow_python_array_NUM] =
    (void *)sidl_opaque__borrow_python_array;
  spa_api[sidl_opaque__clone_python_array_column_NUM] =
    (void *)sidl_opaque__clone_python_array_column;
  spa_api[sidl_opaque__clone_python_array_row_NUM] =
    (void *)sidl_opaque__clone_python_array_row;
  spa_api[sidl_opaque__clone_python_array_NUM] =
    (void *)sidl_opaque__clone_python_array;
  spa_api[sidl_string__borrow_python_array_NUM] =
    (void *)sidl_string__borrow_python_array;
  spa_api[sidl_string__clone_python_array_column_NUM] =
    (void *)sidl_string__clone_python_array_column;
  spa_api[sidl_string__clone_python_array_row_NUM] =
    (void *)sidl_string__clone_python_array_row;
  spa_api[sidl_string__clone_python_array_NUM] =
    (void *)sidl_string__clone_python_array;
  spa_api[sidl_array__convert_python_NUM] =
    (void *)sidl_array__convert_python;
  spa_api[sidl_array__convert_sidl_NUM] =
    (void *)sidl_array__convert_sidl;
  spa_api[sidl_array__extract_python_info_NUM] =
    (void *)sidl_array__extract_python_info;
  c_api = PyCObject_FromVoidPtr((void *)spa_api, NULL);
  if (c_api) {
    PyDict_SetItemString(dict, "_C_API", c_api);
    Py_DECREF(c_api);
  }
  if (PyErr_Occurred()) {
    Py_FatalError("Can't initialize module sidlPyArrays.");
  }
  return MOD_SUCCESS_VAL(module);
}
