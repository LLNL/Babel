/*
 * File:          sidl_ClassInfoI_Impl.c
 * Symbol:        sidl.ClassInfoI-v0.9.17
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Release:       $Name:  $
 * Revision:      @(#) $Id: sidl_ClassInfoI_Impl.c 7465 2012-05-02 20:05:00Z adrian $
 * Description:   Server-side implementation for sidl.ClassInfoI
 * 
 * Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC.
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by the Components Team <components@llnl.gov>
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
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidl.ClassInfoI" (version 0.9.17)
 * 
 *  
 * An implementation of the <code>ClassInfo</code> interface. This
 * provides methods to set all the attributes that are read-only in
 * the <code>ClassInfo</code> interface.
 */

#include "sidl_ClassInfoI_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._includes) */
#include <stdlib.h>
#include <stdio.h>
#include "sidl_String.h"
/* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI__ctor(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._ctor) */
  struct sidl_ClassInfoI__data *data = (struct sidl_ClassInfoI__data*)
    malloc(sizeof(struct sidl_ClassInfoI__data));
  if (data) {
    data->d_IOR_major = data->d_IOR_minor = -1;
    data->d_classname = data->d_classversion = NULL;
  } else {
    sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
    sidl_MemAllocException_setNote(ex, "Out of memory.", _ex);
    sidl_MemAllocException_add(ex, __FILE__, __LINE__, "ClassInfoI__ctor", _ex);
    *_ex = (sidl_BaseInterface)ex;
  }
  sidl_ClassInfoI__set_data(self, data);
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI__ctor2(
  /* in */ sidl_ClassInfoI self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._ctor2) */
  /* Insert-Code-Here {sidl.ClassInfoI._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI__dtor(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._dtor) */
  struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
  if (data) {
    sidl_String_free(data->d_classname);
    sidl_String_free(data->d_classversion);
    free((void *)data);
  }
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._dtor) */
  }
}

/*
 * Set the name of the class.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI_setName"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI_setName(
  /* in */ sidl_ClassInfoI self,
  /* in */ const char* name,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI.setName) */
  struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
  if (data) {
    sidl_String_free(data->d_classname);
    data->d_classname = sidl_String_strdup(name);
  }
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI.setName) */
  }
}

/*
 * Set the version number of the class.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI_setVersion"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI_setVersion(
  /* in */ sidl_ClassInfoI self,
  /* in */ const char* ver,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI.setVersion) */
    struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
    if (data) {
      sidl_String_free(data->d_classversion);
      data->d_classversion = sidl_String_strdup(ver);
    }
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI.setVersion) */
  }
}

/*
 * Set the IOR major and minor version numbers.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI_setIORVersion"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_ClassInfoI_setIORVersion(
  /* in */ sidl_ClassInfoI self,
  /* in */ int32_t major,
  /* in */ int32_t minor,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI.setIORVersion) */
  struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
  if (data) {
    data->d_IOR_major = major;
    data->d_IOR_minor = minor;
  }

    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI.setIORVersion) */
  }
}

/*
 * Return the name of the class.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI_getName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_ClassInfoI_getName(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI.getName) */
  struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
  return sidl_String_strdup(data ? data->d_classname : NULL);
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI.getName) */
  }
}

/*
 * Return the version number of the class. This should be a string
 * with a sequence of numbers separated by periods.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI_getVersion"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_ClassInfoI_getVersion(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI.getVersion) */
    struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
    return sidl_String_strdup(data ? data->d_classversion : NULL);
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI.getVersion) */
  }
}

/*
 * Get the version of the intermediate object representation.
 * This will be in the form of major_version.minor_version.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_ClassInfoI_getIORVersion"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_ClassInfoI_getIORVersion(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI.getIORVersion) */
  int32_t major, minor;
  char buf[34];
  struct sidl_ClassInfoI__data *data = sidl_ClassInfoI__get_data(self);
  major = (data ? data->d_IOR_major : -1);
  minor = (data ? data->d_IOR_minor : -1);
  sprintf(buf, "%d.%d", major, minor);
  return sidl_String_strdup(buf);
    /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI.getIORVersion) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

