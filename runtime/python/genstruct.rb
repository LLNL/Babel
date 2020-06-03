#!/usr/bin/env ruby

list = [
  ["Boolean", "sidl_bool" ],
  ["Char", "char"],
  ["Dcomplex", "struct sidl_dcomplex"],
  ["Double", "double"],
  ["Fcomplex", "struct sidl_fcomplex"],
  ["Float", "float"],
  ["Int", "int32_t"],
  ["Long", "int64_t"],
  ["Opaque", "void *"],
  ["String", "char *"]
];

print <<EOF0
/*
 * File:        sidlStruct.h
 * Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
 * Release:     $Name$
 * Revision:    @(#) $Revision$
 * Date:        $Date$
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

/**
 * A function pointer type used to retrieve "simple" parts of
 * a SIDL struct.
 */
typedef PyObject *(*sidlGetStructMember)(void *);

/**
 * A function pointer type used to set "simple" parts of
 * a SIDL struct.
 */
typedef int (*sidlSetStructMember)(void *, PyObject *);

/**
 * A data structure used to specify the get/set and offset
 * a struct data element.
 */
struct sidl_Python_StructInfo {
  sidlGetStructMember d_get;   /* function to convert individual attribute
                                  to Python */
  sidlSetStructMember d_set;   /* function to set individual struct member
                                  from Python */
  ptrdiff_t           d_offset;
};

#define sidlPyGetStructMember_NUM 0
#define sidlPyGetStructMember_RETURN PyObject *
#define sidlPyGetStructMember_PROTO (PyObject *structObj, struct sidl_Python_StructInfo *info)

#define sidlPySetStructMember_NUM 1
#define sidlPySetStructMember_RETURN int
#define sidlPySetStructMember_PROTO (PyObject *structObj, PyObject *value, struct sidl_Python_StructInfo *info)

EOF0

count = 2
list.each { |i|
  print "#define sidlPyGet#{i[0]}_NUM ", count, "\n"
  print "#define sidlPyGet#{i[0]}_RETURN PyObject *\n"
  print "#define sidlPyGet#{i[0]}_PROTO (const #{i[1]} *value)\n\n"
  count += 1
  print "#define sidlPySet#{i[0]}_NUM ", count, "\n"
  print "#define sidlPySet#{i[0]}_RETURN int\n"
  print "#define sidlPySet#{i[0]}_PROTO (#{i[1]} *value, PyObject *pval)\n\n"
  count += 1
}
print <<EOF
#define sidlStruct__API_NUM #{count}

#ifdef sidlStruct_INTERNAL

#define sidlStruct__import() ;

static sidlPyGetStructMember_RETURN
sidlPyGetStructMember
sidlPyGetStructMember_PROTO;

static sidlPySetStructMember_RETURN
sidlPySetStructMember
sidlPySetStructMember_PROTO;

EOF

list.each { |i|
  print "static sidlPyGet#{i[0]}_RETURN\n"
  print "sidlPyGet#{i[0]}\n"
  print "sidlPyGet#{i[0]}_PROTO;\n\n"

  print "static sidlPySet#{i[0]}_RETURN\n"
  print "sidlPySet#{i[0]}\n"
  print "sidlPySet#{i[0]}_PROTO;\n\n"
}

print <<EOF2
#else

static void **sidlStruct__API = NULL;

#define static sidlPyGetStructMember \\
(*((sidlPyGetStructMember_RETURN (*) \\
   sidlPyGetStructMember_PROTO) \\
   (sidlStruct__API[sidlPyGetStructMember_NUM])))

#define static sidlPySetStructMember \\
(*((sidlPySetStructMember_RETURN (*) \\
   sidlPySetStructMember_PROTO) \\
   (sidlStruct__API[sidlPySetStructMember_NUM])))

EOF2

list.each { |i|
  print "#define sidlPyGet#{i[0]} \\\n"
  print "  (*((sidlPyGet#{i[0]}_RETURN (*) \\\n"
  print "  sidlPyGet#{i[0]}_PROTO) \\\n"
  print "  (sidlStruct__API[sidlPyGet#{i[0]}_NUM])))\n\n"
  print "#define sidlPySet#{i[0]} \\\n"
  print "  (*((sidlPySet#{i[0]}_RETURN (*) \\\n"
  print "  sidlPySet#{i[0]}_PROTO) \\\n"
  print "  (sidlStruct__API[sidlPySet#{i[0]}_NUM])))\n\n"
}

print <<EOF3
#ifdef __cplusplus
}
#endif

#ifndef Py_TYPE
#define Py_TYPE(ob) (((PyObject *)(ob))->ob_type)
#endif

#ifdef HAVE_PTHREAD
#define sidlStruct__import() \\
{ \\
  pthread_mutex_t __sidl_pyapi_mutex = PTHREAD_MUTEX_INITIALIZER; \\
  pthread_mutex_lock(&__sidl_pyapi_mutex); \\
  if (!sidlStruct__API) { \\
    PyObject *module = PyImport_ImportModule("sidlStruct"); \\
    if (module != NULL) { \\
      PyObject *module_dict = PyModule_GetDict(module); \\
      PyObject *c_api_object = \\
        PyDict_GetItemString(module_dict, "_C_API"); \\
      if (c_api_object && PyCObject_Check(c_api_object)) { \\
        sidlStruct__API = \\
          (void **)PyCObject_AsVoidPtr(c_api_object); \\
      } \\
      else { fprintf(stderr, "babel: sidlStruct__import failed to lookup _C_API (%p %p %s).\\n", c_api_object, c_api_object ? Py_TYPE(c_api_object) : NULL, c_api_object ? Py_TYPE(c_api_object)->tp_name : ""); }\\
      Py_DECREF(module); \\
    } else { fprintf(stderr, "babel: sidlStruct__import failed to import its module.\\n"); \\
      if (PyErr_Occurred()) { PyErr_Print(); PyErr_Clear();}\\
    }\\
  }\\
  pthread_mutex_unlock(&__sidl_pyapi_mutex); \\
  pthread_mutex_destroy(&__sidl_pyapi_mutex); \\
}
#else /* !HAVE_PTHREAD */
#define sidlStruct__import() \\
if (!sidlStruct__API) { \\
  PyObject *module = PyImport_ImportModule("sidlStruct"); \\
  if (module != NULL) { \\
    PyObject *module_dict = PyModule_GetDict(module); \\
    PyObject *c_api_object = \\
      PyDict_GetItemString(module_dict, "_C_API"); \\
    if (c_api_object && PyCObject_Check(c_api_object)) { \\
      sidlStruct__API = \\
        (void **)PyCObject_AsVoidPtr(c_api_object); \\
    } \\
    else { fprintf(stderr, "babel: sidlStruct__import failed to lookup _C_API (%p %p %s).\\n", c_api_object, c_api_object ? Py_TYPE(c_api_object) : NULL, c_api_object ? Py_TYPE(c_api_object)->tp_name : ""); }\\
    Py_DECREF(module); \\
  } else { fprintf(stderr, "babel: sidlStruct__import failed to import its module.\\n"); \\
    if (PyErr_Occurred()) { PyErr_Print(); PyErr_Clear();}\\
  }\\
}
#endif /* HAVE_PTHREAD */

#endif /* sidlStruct_INTERNAL */


#endif
EOF3
