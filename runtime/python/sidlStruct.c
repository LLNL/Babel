/*
 * File:        sidlStruct.c
 * Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
 * Release:     $Name$
 * Revision:    @(#) $Revision: 6981 $
 * Date:        $Date: 2010-11-10 16:36:21 -0800 (Wed, 10 Nov 2010) $
 * Description: A Python C extension module to support structs.
 *
 */

#define sidlStruct_INTERNAL
#include "sidlStruct.h"
#include "sidl_String.h"
#include <limits.h>
#include <float.h>

#ifndef INT32_MIN
#define INT32_MIN (-2147483647-1)
#endif
#ifndef INT32_MAX
#define INT32_MAX (2147483647)
#endif
#ifndef INT64_MIN
#if defined(HAVE_LONG_LONG) && SIZEOF_LONG_LONG == 8
#define INT64_MIN (-9223372036854775807LL-1)
#else
#define INT64_MIN (-9223372036854775807L-1)
#endif
#endif
#ifndef INT64_MAX
#if defined(HAVE_LONG_LONG) && SIZEOF_LONG_LONG == 8
#define INT64_MAX (9223372036854775807LL)
#else
#define INT64_MAX (9223372036854775807L)
#endif
#endif

#if PY_MAJOR_VERSION >= 3
static
#else
staticforward 
#endif
PyTypeObject sidlPythonStructBaseType;
#ifndef Py_TYPE
#define Py_TYPE(ob) (((PyObject*)(ob))->ob_type)

#define is_SIDLstruct(v) ((v) && PyType_IsSubtype(Py_TYPE(v), \
&sidlPythonStructBaseType))


sidlPyGetStructMember_RETURN
sidlPyGetStructMember
sidlPyGetStructMember_PROTO
{
  PyObject *result = NULL;
  if (is_SIDLstruct(structObj) && info) {
    char *data = 
      (char *)(((struct sidl_Python_BasicStruct *)structObj)->d_ior);
    result = (*info->d_get)(data + info->d_offset, structObj, info);
  }
  else {
    PyErr_SetString(PyExc_ValueError, "Improper arguments to sidlGetStructMember");
  }
  return result;
}

sidlPySetStructMember_RETURN
sidlPySetStructMember
sidlPySetStructMember_PROTO
{
  int result = -1; /* indicate an error by default */
  if (is_SIDLstruct(structObj) && info) {
    char *data = 
      (char *)(((struct sidl_Python_BasicStruct *)structObj)->d_ior);
    result = !(*info->d_set)(value, (void *)(data + info->d_offset));
  }
  else {
    PyErr_SetString(PyExc_ValueError, "Improper arguments to sidlSetStructMember");
  }
  return result;
}

sidlPyGetBool_RETURN
sidlPyGetBool
sidlPyGetBool_PROTO
{
  return PyBool_FromLong((long)(*value));
}


sidlPySetBool_RETURN
sidlPySetBool
sidlPySetBool_PROTO
{
  int result = -1;
  switch(PyObject_IsTrue(pval)) {
  case 0:
    *value = FALSE;
    break;
  case 1:
    *value = TRUE;
    break;
  default:
    result = 0;
    break;
  }
  return result;
}


sidlPyGetChar_RETURN
sidlPyGetChar
sidlPyGetChar_PROTO
{
  return PyString_FromStringAndSize((char *)value, 1);
}


sidlPySetChar_RETURN
sidlPySetChar
sidlPySetChar_PROTO
{
  int result;
  if (PyString_Check(pval) && (PyString_Size(pval) == 1)) {
    *value = PyString_AsString(pval)[0];
    result = -1;
  }
  else {
    PyErr_BadArgument();
    result = 0;
  }
  return result;
}


sidlPyGetDcomplex_RETURN
sidlPyGetDcomplex
sidlPyGetDcomplex_PROTO
{
  return PyComplex_FromDoubles(value->real, value->imaginary);
}


sidlPySetDcomplex_RETURN
sidlPySetDcomplex
sidlPySetDcomplex_PROTO
{
  int result = -1;
  if (PyComplex_Check(pval)) {
    value->real = PyComplex_RealAsDouble(pval);
    value->imaginary = PyComplex_ImagAsDouble(pval);
  }
  else if (PyFloat_Check(pval)) {
    value->real = PyFloat_AS_DOUBLE(pval);
    value->imaginary = 0;
  }
  else if (PyLong_Check(pval)) {
    value->real = PyLong_AsDouble(pval);
    if ((value->real == -1.0) && PyErr_Occurred()) result = 0;
    value->imaginary = 0;
  }
#if PY_MAJOR_VERSION < 3
  else if (PyInt_Check(pval)) {
    value->real = PyInt_AS_LONG(pval);
    value->imaginary = 0;
  }
#endif
  else {
    PyErr_BadArgument();
    result = 0;
  }
  return result;
}


sidlPyGetDouble_RETURN
sidlPyGetDouble
sidlPyGetDouble_PROTO
{
  return PyFloat_FromDouble(*value);
}


sidlPySetDouble_RETURN
sidlPySetDouble
sidlPySetDouble_PROTO
{
  int result = -1;
  if (PyFloat_Check(pval)) {
    *value = PyFloat_AS_DOUBLE(pval);
  }
  else if (PyLong_Check(pval)) {
    *value = PyLong_AsDouble(pval);
    if (((*value) == -1.0) && PyErr_Occurred()) result = 0;
  }
#if PY_MAJOR_VERSION < 3
  else if (PyInt_Check(pval)) {
    *value = PyInt_AS_LONG(pval);
  }
#endif
  else {
    PyErr_BadArgument();
    result = 0;
  }
  return result;
}

sidlPyGetFcomplex_RETURN
sidlPyGetFcomplex
sidlPyGetFcomplex_PROTO
{
  return PyComplex_FromDoubles((double)value->real, (double)value->imaginary);
}


sidlPySetFcomplex_RETURN
sidlPySetFcomplex
sidlPySetFcomplex_PROTO
{
  double rvalue, ivalue=0;
  int result = -1;
  if (PyComplex_Check(pval)) {
    rvalue = PyComplex_RealAsDouble(pval);
    ivalue = PyComplex_ImagAsDouble(pval);
  }
  else if (PyFloat_Check(pval)) {
    rvalue = PyFloat_AS_DOUBLE(pval);
  }
  else if (PyLong_Check(pval)) {
    rvalue = PyLong_AsDouble(pval);
    if ((rvalue == -1.0) && PyErr_Occurred()) {
      result = 0;
    }
  }
#if PY_MAJOR_VERSION < 3
  else if (PyInt_Check(pval)) {
    rvalue = PyInt_AS_LONG(pval);
  }
#endif
  else {
    rvalue = 0;
    PyErr_BadArgument();
    result = 0;
  }
  if (result) {
    if ((rvalue <= FLT_MAX) && (rvalue >= -FLT_MAX) && (ivalue <= FLT_MAX) && (ivalue >= -FLT_MAX)) {
      value->real = (float)rvalue;
      value->imaginary = (float)ivalue;
    }
    else {
      PyErr_SetString(PyExc_OverflowError, "Value exceeds maximum float size");
      result = 0;
    }
  }
  return result;
}


sidlPyGetFloat_RETURN
sidlPyGetFloat
sidlPyGetFloat_PROTO
{
  return PyFloat_FromDouble((double)(*value));
}


sidlPySetFloat_RETURN
sidlPySetFloat
sidlPySetFloat_PROTO
{
  double dvalue;
  int result = -1;
  if (PyFloat_Check(pval)) {
    dvalue = PyFloat_AS_DOUBLE(pval);
  }
  else if (PyLong_Check(pval)) {
    dvalue = PyLong_AsDouble(pval);
    if ((dvalue == -1.0) && PyErr_Occurred()) {
      result = 0;
    }
  }
#if PY_MAJOR_VERSION < 3
  else if (PyInt_Check(pval)) {
    dvalue = (double)PyInt_AS_LONG(pval);
  }
#endif
  else {
    dvalue = 0;
    PyErr_BadArgument();
    result = 0;
  }
  if (result) {
    if ((dvalue <= FLT_MAX) && (dvalue >= -FLT_MAX)) {
      *value = (float)dvalue;
    }
    else {
      PyErr_SetString(PyExc_OverflowError, "Value exceeds maximum float size");
      result = 0;
    }
  }
  return result;
}


sidlPyGetInt_RETURN
sidlPyGetInt
sidlPyGetInt_PROTO
{
#if SIZEOF_LONG >= 4
#if PY_MAJOR_VERSION < 3
  return PyInt_FromLong(*value);
#else
  return PyLong_FromLong(*value);
#endif
#else
#if PY_MAJOR_VERSION < 3
  if ((*value <= LONG_MAX) && (*value >= LONG_MIN)) return PyInt_FromLong((long)*value);
#else
  if ((*value <= LONG_MAX) && (*value >= LONG_MIN)) return PyLong_FromLong((long)*value);
#endif
  else {
#ifdef HAVE_LONG_LONG
    return PyLong_FromLongLong(*value);
#else
    PyErr_SetString(PyExc_OverflowError, "Conversion of SIDL int to Python long causes overflow");
    return NULL;
#endif
  }
#endif
}

#if SIZEOF_LONG > 4
static int
coerceLongToSIDLInt(int32_t *value, long lval) {
  if ((lval <= INT32_MAX) && (lval >= INT32_MIN)) { /* safe 32-bit */
    *value = (int32_t)lval;
    return 1;
  }
  else {
    PyErr_SetString(PyExc_OverflowError, 
                    "Python value exceeds maximum 32-bit integer size");
    return 0;
  }
}
#endif


sidlPySetInt_RETURN
sidlPySetInt
sidlPySetInt_PROTO
{
  int result = -1;
  if (PyLong_Check(pval)) {
#if (SIZEOF_LONG >= 4) || !defined(HAVE_LONG_LONG)
    long lval = PyLong_AsLong(pval);
    if ((lval == -1) && PyErr_Occurred()) {
      result = 0;
    }
    else {
#if SIZEOF_LONG <= 4
      *value = lval;
#else
      result = coerceLongToSIDLInt(value, lval);
#endif
    }
#else
    PY_LONG_LONG llval = PyLong_AsLongLong(pval);
    if ((llval == -1) && PyErr_Occurred()) {
      result = 0;
    }
    else {
      if ((llval <= INT32_MAX) && (llval >=  INT32_MIN)) {
        *value = (int32_t)llval;
      }
      else {
        result = 0;
        PyErr_SetString(PyExc_OverflowError,
                        "Python value exceeds maximum 32-bit integer size");
      }
    }
#endif
  }
#if PY_MAJOR_VERSION < 3
  else if (PyInt_Check(pval)) {
#if SIZEOF_LONG <= 4
    *value = PyInt_AS_LONG(pval);
#else
    result = coerceLongToSIDLInt(value, PyInt_AS_LONG(pval));
#endif
  }
#endif
  else {
    PyErr_BadArgument();
    result = 0;
  }
  return result;
}


sidlPyGetLong_RETURN
sidlPyGetLong
sidlPyGetLong_PROTO
{
#if SIZEOF_LONG >= 8
#if PY_MAJOR_VERSION < 3
  return PyInt_FromLong(*value);
#else
  return PyLong_FromLong(*value);
#endif
#else
#if PY_MAJOR_VERSION < 3
  if ((*value <= LONG_MAX) && (*value >= LONG_MIN)) return PyInt_FromLong((long)*value);
#else
  if ((*value <= LONG_MAX) && (*value >= LONG_MIN)) return PyLong_FromLong((long)*value);
#endif
  else {
#ifdef HAVE_LONG_LONG
    return PyLong_FromLongLong(*value);
#else
    PyErr_SetString(PyExc_OverflowError, "Conversion of SIDL long to Python long causes overflow");
    return NULL;
#endif
  }
#endif
}

#if SIZEOF_LONG > 8
static int
coerceLongToSIDLLong(int64_t *value, long lval) {
  if ((lval <= INT64_MAX) && (lval >= INT64_MIN)) { /* safe 64-bit */
    *value = (int64_t)lval;
    return -1;
  }
  else {
    PyErr_SetString(PyExc_OverflowError, 
                    "Python value exceeds maximum 64-bit integer size");
    return 0;
  }
}
#endif

sidlPySetLong_RETURN
sidlPySetLong
sidlPySetLong_PROTO
{
  int result = -1;
  if (PyLong_Check(pval)) {
#if (SIZEOF_LONG >= 8) || !defined(HAVE_LONG_LONG)
    long lval = PyLong_AsLong(pval);
    if ((lval == -1) && PyErr_Occurred()) {
      result = 0;
    }
    else {
#if SIZEOF_LONG <= 8
      *value = lval;
#else
      result = coerceLongToSIDLLong(value, lval);
#endif
    }
#else /* (SIZEOF_LONG < 8) && defined(HAVE_LONG_LONG) */
    PY_LONG_LONG llval = PyLong_AsLongLong(pval);
    if ((llval == -1) && PyErr_Occurred()) {
      result = 0;
    }
    else {
      if ((llval <= INT64_MAX) && (llval >=  INT64_MIN)) {
        *value = (int64_t)llval;
      }
      else {
        result = 0;
        PyErr_SetString(PyExc_OverflowError,
                        "Python value exceeds maximum 64-bit integer size");
      }
    }
#endif
  }
#if PY_MAJOR_VERSION < 3
  else if (PyInt_Check(pval)) {
#if SIZEOF_LONG <= 8
    *value = PyInt_AS_LONG(pval);
#else
    result = coerceLongToSIDLLong(value, PyInt_AS_LONG(pval));
#endif
  }
#endif
  else {
    PyErr_BadArgument();
    result = 0;
  }
  return result;
}


sidlPyGetOpaque_RETURN
sidlPyGetOpaque
sidlPyGetOpaque_PROTO
{
  if (*value) {
    return PyCObject_FromVoidPtr((void *)(*value),NULL);
  }
  else {
    Py_INCREF(Py_None);
    return Py_None;
  }
}


sidlPySetOpaque_RETURN
sidlPySetOpaque
sidlPySetOpaque_PROTO
{
  int result;
  if (pval == Py_None) {
    *value = NULL;
    result = -1;
  }
  else if (PyCObject_Check(pval)) {
    *value = PyCObject_AsVoidPtr(pval);
    result = -1;
  }
  else {
    PyErr_BadArgument();
    result = 0;
  }
  return result;
}


sidlPyGetString_RETURN
sidlPyGetString
sidlPyGetString_PROTO
{
  if (*value) {
    return PyString_FromString(*value);
  }
  else {
    Py_INCREF(Py_None);
    return Py_None;
  }
}


sidlPySetString_RETURN
sidlPySetString
sidlPySetString_PROTO
{
  int result = -1;
  if (*value) {
    free(*value);
  }
  *value = NULL;
  if (pval != Py_None) {
    if (PyString_Check(pval)) {
      const char *str = PyString_AsString(pval);
      if (str) {
        *value = sidl_String_strdup(str);
      }
      else {
        result = 0;
      }
    }
    else {
      PyErr_BadArgument();
      result = 0;
    }
  }
  return result;
}

sidlPyGetDeref_RETURN
sidlPyGetDeref
sidlPyGetDeref_PROTO
{
  return (*info->d_getDeref)(*value);
}

sidlPyGetNoDeref_RETURN
sidlPyGetNoDeref
sidlPyGetNoDeref_PROTO
{
  return (*info->d_getDeref)(value);
}

sidlPyGetStructBase_RETURN
sidlPyGetStructBase
sidlPyGetStructBase_PROTO
{
  Py_INCREF(&sidlPythonStructBaseType);
  return &sidlPythonStructBaseType;
}

#ifndef PyVarObject_HEAD_INIT
#define PyVarObject_HEAD_INIT(type, size) \
        PyObject_HEAD_INIT(type) size,
#endif

static PyTypeObject sidlPythonStructBaseType = {
  /* type header */
  PyVarObject_HEAD_INIT(NULL, 0)
  "sidlStruct.BaseStruct",
  sizeof(struct sidl_Python_BasicStruct),
  0,

  /* standard methods */
  (destructor) 0, /* call when reference count == 0 */
  (printfunc) 0,
  (getattrfunc) 0,
  (setattrfunc) 0,
  (cmpfunc) 0,
  (reprfunc) 0,
 
  /* type categories */
  (PyNumberMethods *) 0,
  (PySequenceMethods *) 0,
  (PyMappingMethods *) 0,

  /* more methods */
  (hashfunc)   0,
  (ternaryfunc) 0,
  (reprfunc)   0, /* to string */
  (getattrofunc) 0,
  (setattrofunc) 0,
  
  (PyBufferProcs *) 0,
  Py_TPFLAGS_DEFAULT | Py_TPFLAGS_BASETYPE,
  /* documentation string */
  "This is a Python base type for all Python-wrapped SIDL structs.",
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
  0,                            /* tp_new */
};

static PyMethodDef _sidlStructModuleMethods[] = {
  {NULL, NULL}
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

MOD_INIT(sidlStruct)
{
  PyObject *module, *dict, *c_api;
  static void *ExternalAPI[sidlStruct__API_NUM];
  MOD_DEF(module, "sidlStruct", "\
\
Basic capabilities needed by most SIDL structs.", _sidlStructModuleMethods);
  if (module == NULL) return MOD_ERROR_VAL;

  dict = PyModule_GetDict(module);
  ExternalAPI[sidlPyGetStructMember_NUM] = (void *)sidlPyGetStructMember;
  ExternalAPI[sidlPySetStructMember_NUM] = (void *)sidlPySetStructMember;
  ExternalAPI[sidlPyGetBool_NUM] = (void *)sidlPyGetBool;
  ExternalAPI[sidlPySetBool_NUM] = (void *)sidlPySetBool;
  ExternalAPI[sidlPyGetChar_NUM] = (void *)sidlPyGetChar;
  ExternalAPI[sidlPySetChar_NUM] = (void *)sidlPySetChar;
  ExternalAPI[sidlPyGetDcomplex_NUM] = (void *)sidlPyGetDcomplex;
  ExternalAPI[sidlPySetDcomplex_NUM] = (void *)sidlPySetDcomplex;
  ExternalAPI[sidlPyGetDouble_NUM] = (void *)sidlPyGetDouble;
  ExternalAPI[sidlPySetDouble_NUM] = (void *)sidlPySetDouble;
  ExternalAPI[sidlPyGetFcomplex_NUM] = (void *)sidlPyGetFcomplex;
  ExternalAPI[sidlPySetFcomplex_NUM] = (void *)sidlPySetFcomplex;
  ExternalAPI[sidlPyGetFloat_NUM] = (void *)sidlPyGetFloat;
  ExternalAPI[sidlPySetFloat_NUM] = (void *)sidlPySetFloat;
  ExternalAPI[sidlPyGetInt_NUM] = (void *)sidlPyGetInt;
  ExternalAPI[sidlPySetInt_NUM] = (void *)sidlPySetInt;
  ExternalAPI[sidlPyGetLong_NUM] = (void *)sidlPyGetLong;
  ExternalAPI[sidlPySetLong_NUM] = (void *)sidlPySetLong;
  ExternalAPI[sidlPyGetOpaque_NUM] = (void *)sidlPyGetOpaque;
  ExternalAPI[sidlPySetOpaque_NUM] = (void *)sidlPySetOpaque;
  ExternalAPI[sidlPyGetString_NUM] = (void *)sidlPyGetString;
  ExternalAPI[sidlPySetString_NUM] = (void *)sidlPySetString;
  ExternalAPI[sidlPyGetDeref_NUM] = (void *)sidlPyGetDeref;
  ExternalAPI[sidlPyGetNoDeref_NUM] = (void *)sidlPyGetNoDeref;
  ExternalAPI[sidlPyGetStructBase_NUM] = (void *)sidlPyGetStructBase;
  if (PyType_Ready(&sidlPythonStructBaseType) < 0) return MOD_ERROR_VAL;
  Py_INCREF(&sidlPythonStructBaseType);
  PyModule_AddObject(module, "BaseStruct", 
                     (PyObject *)&sidlPythonStructBaseType);
  c_api = PyCObject_FromVoidPtr((void *)ExternalAPI, NULL);
  PyDict_SetItemString(dict, "_C_API", c_api);
  Py_XDECREF(c_api);
  return MOD_SUCCESS_VAL(module);
}
