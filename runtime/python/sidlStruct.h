/*
 * File:        sidlStruct.h
 * Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
 * Release:     $Name$
 * Revision:    @(#) $Revision: 7188 $
 * Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
 * Description: A Python C extension module to support structs.
 *
 * Copyright (C) 2007, Lawrence Livermore National Security, LLC.
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

/**
 * This his defines the external API for a Python C extension module
 * to help wrap SIDL structs and Python C extennsion types.
 */
#ifndef included_sidlStruct_h
#define included_sidlStruct_h

#include "Python.h"
#include "sidlType.h"
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

struct sidl_Python_BasicStruct {
  PyObject_HEAD            /* standard Python object header */
  PyObject *d_dependency;  /* parent struct if non-NULL */
  void     *d_ior;         /* pointer to struct specific IOR data */
};

struct sidl_Python_StructInfo;

/**
 * A function pointer type used to retrieve "simple" parts of
 * a SIDL struct.
 */
typedef PyObject *(*sidlGetStructMember)(void *, 
                                         PyObject *,
                                         struct sidl_Python_StructInfo *);

/**
 * A function pointer type used to retrieve "simple" parts of
 * a SIDL struct.
 */
typedef PyObject *(*sidlGetStructMemberDeref)(void *);

/**
 * A function pointer type used to set "simple" parts of
 * a SIDL struct.
 */
typedef int (*sidlSetStructMember)(PyObject *, void *);

/**
 * A data structure used to specify the get/set and offset
 * a struct data element.
 */
struct sidl_Python_StructInfo {
  sidlGetStructMember d_get;           /* function to convert individual
                                          attribute to Python */
  sidlSetStructMember d_set;           /* function to set individual struct
                                          member from Python */
  ptrdiff_t           d_offset;
  sidlGetStructMemberDeref d_getDeref; /* function to convert individual
                                          attribute to Python */
};

#define sidlPyGetStructMember_NUM 0
#define sidlPyGetStructMember_RETURN PyObject *
#define sidlPyGetStructMember_PROTO (PyObject *structObj, struct sidl_Python_StructInfo *info)

#define sidlPySetStructMember_NUM 1
#define sidlPySetStructMember_RETURN int
#define sidlPySetStructMember_PROTO (PyObject *structObj, PyObject *value, struct sidl_Python_StructInfo *info)

#define sidlPyGetBool_NUM 2
#define sidlPyGetBool_RETURN PyObject *
#define sidlPyGetBool_PROTO (const sidl_bool *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetBool_NUM 3
#define sidlPySetBool_RETURN int
#define sidlPySetBool_PROTO (PyObject *pval, sidl_bool *value)

#define sidlPyGetChar_NUM 4
#define sidlPyGetChar_RETURN PyObject *
#define sidlPyGetChar_PROTO (const char *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetChar_NUM 5
#define sidlPySetChar_RETURN int
#define sidlPySetChar_PROTO (PyObject *pval, char *value)

#define sidlPyGetDcomplex_NUM 6
#define sidlPyGetDcomplex_RETURN PyObject *
#define sidlPyGetDcomplex_PROTO (const struct sidl_dcomplex *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetDcomplex_NUM 7
#define sidlPySetDcomplex_RETURN int
#define sidlPySetDcomplex_PROTO (PyObject *pval, struct sidl_dcomplex *value)

#define sidlPyGetDouble_NUM 8
#define sidlPyGetDouble_RETURN PyObject *
#define sidlPyGetDouble_PROTO (const double *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetDouble_NUM 9
#define sidlPySetDouble_RETURN int
#define sidlPySetDouble_PROTO (PyObject *pval, double *value)

#define sidlPyGetFcomplex_NUM 10
#define sidlPyGetFcomplex_RETURN PyObject *
#define sidlPyGetFcomplex_PROTO (const struct sidl_fcomplex *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetFcomplex_NUM 11
#define sidlPySetFcomplex_RETURN int
#define sidlPySetFcomplex_PROTO (PyObject *pval, struct sidl_fcomplex *value)

#define sidlPyGetFloat_NUM 12
#define sidlPyGetFloat_RETURN PyObject *
#define sidlPyGetFloat_PROTO (const float *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetFloat_NUM 13
#define sidlPySetFloat_RETURN int
#define sidlPySetFloat_PROTO (PyObject *pval, float *value)

#define sidlPyGetInt_NUM 14
#define sidlPyGetInt_RETURN PyObject *
#define sidlPyGetInt_PROTO (const int32_t *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetInt_NUM 15
#define sidlPySetInt_RETURN int
#define sidlPySetInt_PROTO (PyObject *pval, int32_t *value)

#define sidlPyGetLong_NUM 16
#define sidlPyGetLong_RETURN PyObject *
#define sidlPyGetLong_PROTO (const int64_t *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetLong_NUM 17
#define sidlPySetLong_RETURN int
#define sidlPySetLong_PROTO (PyObject *pval, int64_t *value)

#define sidlPyGetOpaque_NUM 18
#define sidlPyGetOpaque_RETURN PyObject *
#define sidlPyGetOpaque_PROTO (const void * *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetOpaque_NUM 19
#define sidlPySetOpaque_RETURN int
#define sidlPySetOpaque_PROTO (PyObject *pval, void * *value)

#define sidlPyGetString_NUM 20
#define sidlPyGetString_RETURN PyObject *
#define sidlPyGetString_PROTO (const char * *value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPySetString_NUM 21
#define sidlPySetString_RETURN int
#define sidlPySetString_PROTO (PyObject *pval, char * *value)

#define sidlPyGetDeref_NUM 22
#define sidlPyGetDeref_RETURN PyObject *
#define sidlPyGetDeref_PROTO (void * * value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPyGetNoDeref_NUM 23
#define sidlPyGetNoDeref_RETURN PyObject *
#define sidlPyGetNoDeref_PROTO (void * * value, PyObject *src, struct sidl_Python_StructInfo *info)

#define sidlPyGetStructBase_NUM 24
#define sidlPyGetStructBase_RETURN PyTypeObject *
#define sidlPyGetStructBase_PROTO (void)

#define sidlStruct__API_NUM 25

#ifdef sidlStruct_INTERNAL

#define import_sidlStruct() ;

static sidlPyGetStructMember_RETURN
sidlPyGetStructMember
sidlPyGetStructMember_PROTO;

static sidlPySetStructMember_RETURN
sidlPySetStructMember
sidlPySetStructMember_PROTO;

static sidlPyGetBool_RETURN
sidlPyGetBool
sidlPyGetBool_PROTO;

static sidlPySetBool_RETURN
sidlPySetBool
sidlPySetBool_PROTO;

static sidlPyGetChar_RETURN
sidlPyGetChar
sidlPyGetChar_PROTO;

static sidlPySetChar_RETURN
sidlPySetChar
sidlPySetChar_PROTO;

static sidlPyGetDcomplex_RETURN
sidlPyGetDcomplex
sidlPyGetDcomplex_PROTO;

static sidlPySetDcomplex_RETURN
sidlPySetDcomplex
sidlPySetDcomplex_PROTO;

static sidlPyGetDouble_RETURN
sidlPyGetDouble
sidlPyGetDouble_PROTO;

static sidlPySetDouble_RETURN
sidlPySetDouble
sidlPySetDouble_PROTO;

static sidlPyGetFcomplex_RETURN
sidlPyGetFcomplex
sidlPyGetFcomplex_PROTO;

static sidlPySetFcomplex_RETURN
sidlPySetFcomplex
sidlPySetFcomplex_PROTO;

static sidlPyGetFloat_RETURN
sidlPyGetFloat
sidlPyGetFloat_PROTO;

static sidlPySetFloat_RETURN
sidlPySetFloat
sidlPySetFloat_PROTO;

static sidlPyGetInt_RETURN
sidlPyGetInt
sidlPyGetInt_PROTO;

static sidlPySetInt_RETURN
sidlPySetInt
sidlPySetInt_PROTO;

static sidlPyGetLong_RETURN
sidlPyGetLong
sidlPyGetLong_PROTO;

static sidlPySetLong_RETURN
sidlPySetLong
sidlPySetLong_PROTO;

static sidlPyGetOpaque_RETURN
sidlPyGetOpaque
sidlPyGetOpaque_PROTO;

static sidlPySetOpaque_RETURN
sidlPySetOpaque
sidlPySetOpaque_PROTO;

static sidlPyGetString_RETURN
sidlPyGetString
sidlPyGetString_PROTO;

static sidlPySetString_RETURN
sidlPySetString
sidlPySetString_PROTO;

static sidlPyGetDeref_RETURN
sidlPyGetDeref
sidlPyGetDeref_PROTO;

static sidlPyGetNoDeref_RETURN
sidlPyGetNoDeref
sidlPyGetNoDeref_PROTO;

static sidlPyGetStructBase_RETURN
sidlPyGetStructBase
sidlPyGetStructBase_PROTO;

#else

static void **sidlStruct__API = NULL;

#define sidlPyGetStructMember \
(*((sidlPyGetStructMember_RETURN (*) \
   sidlPyGetStructMember_PROTO) \
   (sidlStruct__API[sidlPyGetStructMember_NUM])))

#define sidlPySetStructMember \
(*((sidlPySetStructMember_RETURN (*) \
   sidlPySetStructMember_PROTO) \
   (sidlStruct__API[sidlPySetStructMember_NUM])))

#define sidlPyGetBool \
  (*((sidlPyGetBool_RETURN (*) \
  sidlPyGetBool_PROTO) \
  (sidlStruct__API[sidlPyGetBool_NUM])))

#define sidlPySetBool \
  (*((sidlPySetBool_RETURN (*) \
  sidlPySetBool_PROTO) \
  (sidlStruct__API[sidlPySetBool_NUM])))

#define sidlPyGetChar \
  (*((sidlPyGetChar_RETURN (*) \
  sidlPyGetChar_PROTO) \
  (sidlStruct__API[sidlPyGetChar_NUM])))

#define sidlPySetChar \
  (*((sidlPySetChar_RETURN (*) \
  sidlPySetChar_PROTO) \
  (sidlStruct__API[sidlPySetChar_NUM])))

#define sidlPyGetDcomplex \
  (*((sidlPyGetDcomplex_RETURN (*) \
  sidlPyGetDcomplex_PROTO) \
  (sidlStruct__API[sidlPyGetDcomplex_NUM])))

#define sidlPySetDcomplex \
  (*((sidlPySetDcomplex_RETURN (*) \
  sidlPySetDcomplex_PROTO) \
  (sidlStruct__API[sidlPySetDcomplex_NUM])))

#define sidlPyGetDouble \
  (*((sidlPyGetDouble_RETURN (*) \
  sidlPyGetDouble_PROTO) \
  (sidlStruct__API[sidlPyGetDouble_NUM])))

#define sidlPySetDouble \
  (*((sidlPySetDouble_RETURN (*) \
  sidlPySetDouble_PROTO) \
  (sidlStruct__API[sidlPySetDouble_NUM])))

#define sidlPyGetFcomplex \
  (*((sidlPyGetFcomplex_RETURN (*) \
  sidlPyGetFcomplex_PROTO) \
  (sidlStruct__API[sidlPyGetFcomplex_NUM])))

#define sidlPySetFcomplex \
  (*((sidlPySetFcomplex_RETURN (*) \
  sidlPySetFcomplex_PROTO) \
  (sidlStruct__API[sidlPySetFcomplex_NUM])))

#define sidlPyGetFloat \
  (*((sidlPyGetFloat_RETURN (*) \
  sidlPyGetFloat_PROTO) \
  (sidlStruct__API[sidlPyGetFloat_NUM])))

#define sidlPySetFloat \
  (*((sidlPySetFloat_RETURN (*) \
  sidlPySetFloat_PROTO) \
  (sidlStruct__API[sidlPySetFloat_NUM])))

#define sidlPyGetInt \
  (*((sidlPyGetInt_RETURN (*) \
  sidlPyGetInt_PROTO) \
  (sidlStruct__API[sidlPyGetInt_NUM])))

#define sidlPySetInt \
  (*((sidlPySetInt_RETURN (*) \
  sidlPySetInt_PROTO) \
  (sidlStruct__API[sidlPySetInt_NUM])))

#define sidlPyGetLong \
  (*((sidlPyGetLong_RETURN (*) \
  sidlPyGetLong_PROTO) \
  (sidlStruct__API[sidlPyGetLong_NUM])))

#define sidlPySetLong \
  (*((sidlPySetLong_RETURN (*) \
  sidlPySetLong_PROTO) \
  (sidlStruct__API[sidlPySetLong_NUM])))

#define sidlPyGetOpaque \
  (*((sidlPyGetOpaque_RETURN (*) \
  sidlPyGetOpaque_PROTO) \
  (sidlStruct__API[sidlPyGetOpaque_NUM])))

#define sidlPySetOpaque \
  (*((sidlPySetOpaque_RETURN (*) \
  sidlPySetOpaque_PROTO) \
  (sidlStruct__API[sidlPySetOpaque_NUM])))

#define sidlPyGetString \
  (*((sidlPyGetString_RETURN (*) \
  sidlPyGetString_PROTO) \
  (sidlStruct__API[sidlPyGetString_NUM])))

#define sidlPySetString \
  (*((sidlPySetString_RETURN (*) \
  sidlPySetString_PROTO) \
  (sidlStruct__API[sidlPySetString_NUM])))

#define sidlPyGetDeref \
  (*((sidlPyGetDeref_RETURN (*) \
  sidlPyGetDeref_PROTO) \
  (sidlStruct__API[sidlPyGetDeref_NUM])))

#define sidlPyGetNoDeref \
  (*((sidlPyGetNoDeref_RETURN (*) \
  sidlPyGetNoDeref_PROTO) \
  (sidlStruct__API[sidlPyGetNoDeref_NUM])))

#define sidlPyGetStructBase \
  (*((sidlPyGetStructBase_RETURN (*) \
  sidlPyGetStructBase_PROTO) \
  (sidlStruct__API[sidlPyGetStructBase_NUM])))

#ifdef __cplusplus
}
#endif

#ifndef Py_TYPE
#define Py_TYPE(ob) (((PyObject *)(ob))->ob_type)
#endif

#ifdef HAVE_PTHREAD
#define import_sidlStruct() \
{ \
  pthread_mutex_t __sidl_pyapi_mutex = PTHREAD_MUTEX_INITIALIZER; \
  pthread_mutex_lock(&__sidl_pyapi_mutex); \
  if (!sidlStruct__API) { \
    PyObject *_imp_module = PyImport_ImportModule("sidlStruct"); \
    if (_imp_module != NULL) { \
      PyObject *_imp_module_dict = PyModule_GetDict(_imp_module); \
      PyObject *_imp_c_api_object = \
        PyDict_GetItemString(_imp_module_dict, "_C_API"); \
      if (_imp_c_api_object && PyCObject_Check(_imp_c_api_object)) { \
        sidlStruct__API = \
          (void **)PyCObject_AsVoidPtr(_imp_c_api_object); \
      } \
      else { fprintf(stderr, "babel: import_sidlStruct failed to lookup _C_API (%p %p %s).\n", _imp_c_api_object, _imp_c_api_object ? Py_TYPE(_imp_c_api_object) : NULL, _imp_c_api_object ? Py_TYPE(_imp_c_api_object)->tp_name : ""); } \
      Py_DECREF(_imp_module); \
    } else { fprintf(stderr, "babel: import_sidlStruct failed to import its module.\n"); \
      if (PyErr_Occurred()) { PyErr_Print(); PyErr_Clear();}\
    }\
  }\
  pthread_mutex_unlock(&__sidl_pyapi_mutex); \
  pthread_mutex_destroy(&__sidl_pyapi_mutex); \
}
#else /* !HAVE_PTHREAD */
#define import_sidlStruct() \
if (!sidlStruct__API) { \
  PyObject *_imp_module = PyImport_ImportModule("sidlStruct"); \
  if (_imp_module != NULL) { \
    PyObject *_imp_module_dict = PyModule_GetDict(_imp_module); \
    PyObject *_imp_c_api_object = \
      PyDict_GetItemString(_imp_module_dict, "_C_API"); \
    if (_imp_c_api_object && PyCObject_Check(_imp_c_api_object)) { \
      sidlStruct__API = \
        (void **)PyCObject_AsVoidPtr(_imp_c_api_object); \
    } \
    else { fprintf(stderr, "babel: import_sidlStruct failed to lookup _C_API (%p %p %s).\n", _imp_c_api_object, _imp_c_api_object ? Py_TYPE(_imp_c_api_object) : NULL, _imp_c_api_object ? Py_TYPE(_imp_c_api_object)->tp_name : ""); }	\
    Py_DECREF(_imp_module); \
  } else { fprintf(stderr, "babel: import_sidlStruct failed to import its module.\n"); \
    if (PyErr_Occurred()) { PyErr_Print(); PyErr_Clear();}\
  }\
}
#endif /* HAVE_PTHREAD */

#endif /* sidlStruct_INTERNAL */


#endif
